package com.mbv.airline.sabre.actors;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import scala.concurrent.duration.Duration;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.sabre.SpringExt;
import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.TerminalConfig;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.DesignatePrinters;
import com.mbv.airline.sabre.commands.Ignore;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.commands.SignIn;
import com.mbv.airline.sabre.commands.SignOut;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class TerminalPool extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(TerminalPool.class);

	private LinkedList<ActorRef> childQueue;
	private ApplicationContext appCtx;
	public static SenderManager senderManager;

	public TerminalPool() {
		childQueue = new LinkedList<ActorRef>();
	}

	public void preStart() {
		logger.info(self().path() + " started");
		appCtx = SpringExt.Extention.get(context().system()).getAppCtx();
		senderManager = (SenderManager) appCtx.getBean(SenderManager.class);
	}

	public void postStop() {
		logger.info(self().path() + " stopped");
	}

	@Override
	public void onReceive(Object message) {
		// TODO Auto-generated method stub
		if (message instanceof ActorRef) {
			if (!childQueue.contains(getSender())) {
				childQueue.addLast(getSender());
			}
			logger.info(getSender().path() + " add to pool");
		} else if (message instanceof Command) {
			dispatch((Command) message);
		} else if (message instanceof Attach) {
			TerminalConfig config = (TerminalConfig) message;
			try {
				context().actorOf(Props.create(TerminalActor.class, config),
						config.getUsername());
			} catch (Exception ex) {
				logger.error(ex.toString());
			}
		} else if (message instanceof Detach) {
			this.dettach(((Detach) message).getUsername());
		} else if (message instanceof Status) {
			getSender().tell(this.statusString(), getSelf());
		}
	}

	private void dettach(String childName) {
		for (ActorRef child : getContext().getChildren()) {
			if (child.path().name().equals(childName)) {
				childQueue.remove(child);
				child.tell(PoisonPill.getInstance(), getSelf());
				return;
			}
		}
	}

	private String statusString() {
		StringBuilder sb = new StringBuilder();
		for (ActorRef child : getContext().getChildren()) {
			sb.append(child.path().name() + "\t");
			if (childQueue.contains(child)) {
				sb.append("Active\n");
			} else {
				sb.append("N/A\n");
			}
		}
		return sb.toString();
	}

	private void dispatch(Command command) {
		ActorRef child = childQueue.pollFirst();
		if (child != null) {
			child.tell(command, getSender());
			childQueue.addLast(child);
		} else {
			getSender().tell(command.execute(null), getSelf());
		}
	}

	private static class TerminalActor extends UntypedActor {
		private static final Logger logger = LoggerFactory
				.getLogger(TerminalActor.class);
		private Terminal terminal;

		private Cancellable tick;

		public TerminalActor(TerminalConfig config) {
			this.terminal = new Terminal(config);
		}

		public void preStart() {
			logger.info("{} starting ...", self().path());

			try {
				if (!terminal.open())
					throw new Exception("Cannot open terminal");
				Result result = (new SignIn(senderManager)).execute(this.terminal);
				if (result.getStatus() != Code.SUCCESS)
					throw new Exception("SignIn Error - " + result.getStatus());
							
				context().parent().tell(getSelf(), getSelf());
				logger.info("{} started", getSelf().path());

				tick = getContext()
						.system()
						.scheduler()
						.schedule(Duration.create(60, TimeUnit.SECONDS),
								Duration.create(60, TimeUnit.SECONDS),
								getSelf(), new Ignore(),
								getContext().dispatcher(), null);
			} catch (Exception ex) {
				logger.error("{} failed: {}", getSelf().path(), ex.getMessage());
				context().stop(getSelf());
			}
		}
		
		public void postRestart(){
			context().stop(getSelf());
		}
		
		public void postStop() {
			try {
				tick.cancel();
				new SignOut().execute(terminal);
				this.terminal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("{} stopped", getSelf().path());
		}

		@Override
		public void onReceive(Object message) throws Exception {
			logger.info("[ {} ] [ {} ] [ {} ] {}", getSelf().path(),
					getSender().path(), message.getClass().getName(),
					message.toString());
			if (message instanceof Command) {
				Result result = ((Command) message).execute(this.terminal);
				getSender().tell(result, getSelf());
			}

			if (this.terminal != null && this.terminal.isError()) {
				this.terminal.open();
				Result result = (new SignIn(senderManager)).execute(this.terminal);
				if (result.getStatus() == Code.ERROR) {
					context().parent().tell(
							new Detach(getSelf().path().name()), getSelf());
					this.terminal = null;
				}
			}
		}
	}

	private boolean SignInAndDesignPrinter(Terminal terminal) {
		Command cmd = new SignIn(senderManager);
		Result result = cmd.execute(terminal);
		cmd = new DesignatePrinters();
		result = cmd.execute(terminal);

		return true;
	}

	public static class Attach extends TerminalConfig {
	}

	public static class Detach {
		private String username;

		public Detach(String username) {
			this.username = username;
		}

		public String getUsername() {
			return username;
		}
	}

	public static class Status {
	}
}
