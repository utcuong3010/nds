package com.mbv.airline.sabre;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import akka.actor.ActorSystem;

import com.mbv.airline.sabre.actors.TerminalPool.Attach;
import com.mbv.airline.sabre.actors.agent.CloseReportAgent;

public class Main implements Daemon {
	AnnotationConfigApplicationContext ctx = null;

    private static CloseReportAgent closeReportAgent;
    
    private static Attach attachConfig;
	
	public void setAttachConfig(Attach attachConfig) {
		this.attachConfig = attachConfig;
	}

	@Override
	public void destroy() {
		ctx = null;
        closeReportAgent.stop();
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
				
	}

	@Override
	public void start() throws Exception {
		ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
        closeReportAgent.start();
        
        // attach Terminal at starting...

        ActorSystem actorSystem = ctx.getBean(ActorSystem.class);
        actorSystem.actorSelection("/user/SabreTerminals").tell(attachConfig, null);	
	}

	@Override
	public void stop() throws Exception {
		ctx.close();
        closeReportAgent.stop();
	}

    public void setCloseReportAgent(CloseReportAgent reportAgent) {
        this.closeReportAgent = reportAgent;
    }

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Main.class);
        try {
            Main console = new Main();
            console.start();
        } catch (Exception e) {
            logger.info("Error starting application", e);
            System.exit(1);
        }
    }
}
