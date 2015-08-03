#!/bin/sh
NAME="sabre"
DESC="Sabre Daemon Service"

# The path to Jsvc
EXEC="/usr/bin/jsvc"

# The path to the folder containing MyDaemon.jar
HOME=`dirname "${BASH_SOURCE-$0}"`
HOME=`cd "$HOME">/dev/null; pwd`

# The path to the folder containing the java runtime
JAVA_HOME="/usr/lib/jvm/java-6-openjdk-amd64"

# Our classpath including our jar file and the Apache Commons Daemon library
CLASS_PATH="$HOME/bin/*:$HOME/libs/*:$HOME/config:$HOME/config/*"

# The fully qualified name of the class to execute
CLASS="com.mbv.airline.sabre.Main"

# The file that will contain our process identification number (pid) for other scripts/programs that need to access it.
PID="$HOME/$NAME.pid"

# System.out writes to this file...
LOG_OUT="$HOME/system.log"

JAVA_OPTIONS="-Dsabre.configuration=$HOME/config/sabre.conf -Dlog4j.configuration=$HOME/config/log4j.properties"

jsvc_exec()
{
    cd $HOME
    $EXEC -home $JAVA_HOME -cp $CLASS_PATH -errfile $LOG_OUT -pidfile $PID $1 $JAVA_OPTIONS $CLASS
}

case "$1" in
    start)
        echo "Starting the $DESC..."        

        # Start the service
        jsvc_exec

        echo "The $DESC has started."
    ;;
    stop)
        echo "Stopping the $DESC..."

        # Stop the service
        jsvc_exec "-stop"

        echo "The $DESC has stopped."
    ;;
    restart)
        if [ -f "$PID" ]; then

            echo "Restarting the $DESC..."

            # Stop the service
            jsvc_exec "-stop"

            # Start the service
            jsvc_exec

            echo "The $DESC has restarted."
        else
            echo "Daemon not running, no action taken"
            exit 1
        fi
            ;;
    *)
    echo "Usage: $HOME/$NAME {start|stop|restart}" >&2
    exit 3
    ;;
esac
