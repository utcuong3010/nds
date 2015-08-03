package com.mbv.airline.sabre;

import java.util.HashSet;
import java.util.Properties;

import akka.actor.ActorSystem;

import com.mbv.airline.sabre.actors.TerminalPool.Detach;
import com.mbv.airline.sabre.actors.TerminalPool.Attach;
import com.sabre.gds.GDSCache;
import com.sabre.gds.GDSError;
import com.sabre.jsapi.Log.Level;
import com.sabre.jsapi.Message;
import com.sabre.jsapi.SABREException;
import com.sabre.jsapi.SABREManager;
import com.sabre.jsapi.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Terminal {
    private static final Logger LOG = LoggerFactory.getLogger(Terminal.class);
    private static final String SABRE_URL = "sabre:hssp:uii_host://access.sabre.com/ldap.useserver=true;ldap.usefile=true;ldap.dns=hsspconfig.sabre.com;ldap.port=389;ldap.o=sabre.com;ldap.ou=HSSP;ldap.cn=ASMapping;ldap.objectclass=hssp-mapping;keepalive=true;key=F0C9D5E3D9";
    private static HashSet<String> mdrSet = new HashSet<String>();


    @Autowired
    private ActorSystem actorSystem;
    
    private TerminalConfig config;
    private Session session;

    static {
        try {
            SABREManager.configureLogging(true, "log4j.properties", false, Level.WARN, false, null, false, false);
        } catch (Exception ex) {
            LOG.error("TERMINAL ERROR", ex);
        }
    }

    public Terminal(TerminalConfig config) {
        this.config = config;
    }

    public TerminalConfig getConfig() {
        return config;
    }

    public boolean open() {
        if (this.session != null) this.close();

        Properties info = new Properties();
        info.put("profile", "VNPSAGSA");
        try {
            this.session = SABREManager.getSession(SABRE_URL, info);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public void close() {
        if (this.session == null) return;

        //(new SignOut()).execute(this);

        try {
            this.session.close();
        } catch (SABREException e) {
            LOG.error(e.getMessage());
        }
        this.session = null;
    }

    public boolean isError() {
        if (this.session == null) return true;
        return false;
    }

    private static final Character CROSS_LORRAINE_CHAR = new Character('\u0081'); //'
    private static final Character GDS_ITEM_DELIM_CHAR = new Character('\u0086'); //+
    private static final Character FIELD_CHAR = new Character('\u0083'); //^
    private static final Character END_OF_MESSAGE_CHAR = new Character('\u0089');
    private static final String END_OF_MESSAGE_STR = new String(END_OF_MESSAGE_CHAR.toString() + END_OF_MESSAGE_CHAR.toString());

    public void send(String command) throws TerminalException {
        if (session == null) throw new TerminalException("TERMINAL ERROR", "VNA session not found");

        command = command.toUpperCase();
        command = command.replace('\'', Message.CROSS_LORRAINE_CHAR);
        command = command.replaceAll("\\'", CROSS_LORRAINE_CHAR.toString());
        command = command.replaceAll("\\+", GDS_ITEM_DELIM_CHAR.toString());
        command = command.replaceAll("\\^", FIELD_CHAR.toString());

        if (!command.contains("AG<"))
            LOG.info("[{}] sends: {}", this.config.getUsername(), command);
        else
            LOG.info("[{}] sends: {}", this.config.getUsername(), "command sign in");

        Message request = new Message(command);
        try {
            this.session.send(request);
        } catch (Exception e) {
            throw new TerminalException("TERMINAL SEND COMMAND ERROR", e);
        }
    }

    public String receive() throws TerminalException {
        String retval;
        try {
            retval = session.receive().getText();
        } catch (SABREException e) {
            throw new TerminalException("TERMINAL SESSION GET TEXT ERROR", e);
        }

        LOG.debug("[{}] received: {}", this.config.getUsername(), retval);

        //Text Message
        if ((retval.length() <= 22) || (retval.charAt(22) != GDS_ITEM_DELIM_CHAR)) {
            if (retval.endsWith("\n\u0092")) retval = retval.substring(0, retval.length() - 2);
            LOG.info("[{}] received {} from sabre server", this.config.getUsername(), retval);
            if(retval.contains("SIGN IN A")) {
            	LOG.info("[{}] DETACH from SabreTerminals ...", this.config.getUsername());
            	actorSystem.actorSelection("/user/SabreTerminals").tell(new Detach(this.config.getUsername()), null);
            	//LOG.info("[****] VINHLOG: ATTACH SabreTerminas starting...");
            	actorSystem.actorSelection("/user/SabreTerminals").tell((Attach)this.config, null);
            	//LOG.info("[****] VINHLOG: ATTACH SabreTerminas finished.");
            	throw new TerminalException("TERMINAL SESSION EXPIRED");
            }
            return retval;
        } else {
            LOG.info("[{}] received response from sabre server", this.config.getUsername());
        }

        //GDS Message
        String partialResponse = retval;
        while (partialResponse.indexOf(END_OF_MESSAGE_STR) < 0) {
            try {
                partialResponse = this.session.receive().getText();
            } catch (SABREException e) {
                throw new TerminalException(e);
            }
            LOG.debug("[{}] received: {}", this.config.getUsername(), partialResponse);
            retval = retval.substring(0, retval.length() - 1) + partialResponse.substring(8);
        }
        loadMDR(retval);
        return retval;
    }

    private boolean isLoadingMDR = false;

    private void loadMDR(String response) throws TerminalException {
        if (isLoadingMDR == true) return;
        this.isLoadingMDR = true;
        try {
            String mdrId = response.substring(8, 22);
            String mdrVersion = response.substring(23, 25);
            if (!mdrSet.contains(mdrId + mdrVersion)) {
                this.send("++REQ/LOAD" + mdrId + mdrVersion);
                String mdr = this.receive();
                if (GDSCache.addMDR(mdrId, mdrVersion, mdr, true, true) == GDSError.GDS_OK) {
                    mdrSet.add(mdrId + mdrVersion);
                }
            }
        } catch (Exception e) {
            throw new TerminalException("TERMINAL LOAD MDR ERROR", e);
        }
        this.isLoadingMDR = false;
    }

    public static class TerminalException extends Exception {
        private String label = "TERMINAL ERROR";

        public TerminalException() {
            // TODO Auto-generated constructor stub
        }

        public TerminalException(String message) {
            super(message);
        }

        public TerminalException(Exception exp) {
            super(exp);
        }

        public TerminalException(String message, Throwable e) {
            super(message, e);
        }

        public TerminalException(String message, String ex) {
            super(message, new Throwable(ex));
        }

        public String getMessage() {
            return label + ":     " + getCause().toString();
        }
    }
}
