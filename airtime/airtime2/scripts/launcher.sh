#! /bin/bash

BASEPATH=`dirname "$0"`

JSVC="$BASEPATH/jsvc/jsvc"
JAVACP="$BASEPATH/configs/:$BASEPATH/bin/*:$BASEPATH/lib/*"
PIDFILE="$BASEPATH/launcher.pid"
OUTFILE="$BASEPATH/launcher.log"
MAINCLASS="com.mbv.airtime2.launcher.Main"

case "$1" in
	"start")
		if [ -f $PIDFILE ]; then
			echo "FAILED: Launcher is running."
			exit 1;
		fi;
		echo "Starting Launcher ..."
		$JSVC -home $JAVA_HOME -cp $JAVACP -pidfile $PIDFILE -outfile $OUTFILE -errfile $OUTFILE $MAINCLASS
		if [ ! "$?" = 0 ]; then
			echo "FAILED: Cannot start Launcher (view $OUTFILE)."
		else
			echo "Launcher is started successfully."
		fi;
		exit $?
	;;
	"stop")
		echo "Stopping Launcher ..."
		if [ ! -f $PIDFILE ]; then
			echo "FAILED: Cannot find pid file $PIDFILE."
			exit 1;
		fi;
		$JSVC -home $JAVA_HOME -cp $JAVACP -pidfile $PIDFILE -outfile $OUTFILE -errfile $OUTFILE -stop $MAINCLASS
		if [ ! "$?" = 0 ]; then
			echo "FAILED: Cannot stop Launcher (view $OUTFILE)."
		else
			echo "Launcher is stopped successfully."
		fi;
		exit $?
	;;
	*)
		echo "usage: $0 {start|stop}"
	;;
esac
#./jsvc -home $JAVA_HOME -cp commons-daemon-1.0.11.jar:./bin/*:./configs:./lib/*:. -errfile err.log -pidfile launcher.pid com.mbv.airtime2.launcher.Main

