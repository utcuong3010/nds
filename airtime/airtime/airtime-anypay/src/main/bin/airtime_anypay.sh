#! /bin/bash

CURPATH="$(pwd)"
BINPATH="${0#./}"
base="${BINPATH%/*}"
scriptfile="${0%./}"

if [ "${0:0:1}" = "/" ] ; then
{	
	cd "${base}"; BINPATH=$(pwd); 
}
else {
	if [ "$base" = "$BINPATH" ] ; then
	{ cd "$CURPATH" ; BINPATH=$(pwd) ; }
	else
	{ cd $CURPATH/$base ; BINPATH=$(pwd) ; }
	fi ;
}
fi

cd "$BINPATH/.."
RUNPATH=$(pwd)
BINCMD="$RUNPATH/bin/airtime_anypay"
PIDFILE="$BINPATH/airtime_anypay.pid"
LAUNCHER="com.mbv.anypay.queue.AirtimeAnypayLauncher"
CMDOPTS=""

#echo "${RUNPATH}"

if [ "$DEBUG" = "1" ]; then
	CMDOPTS+="-debug -verbose "
fi;

#cd $RUNPATH/lib
JAVACP="${RUNPATH}/config:$(echo $RUNPATH/lib/*.jar . | sed 's/ /:/g')"
#echo "Java ClassPATH" $JAVACP

#cd $RUNPATH

if [ ! $BINCMD = "java" ]; then
	CMDOPTS+="-cp $JAVACP -wait 30  -pidfile $PIDFILE"
else
	CMDOPTS+="-cp $JAVACP"	
fi;
case "$1" in
	"start")
		if [ -f $PIDFILE ]; then
			echo "FAILED: The service is running."
			exit 1;
		fi;
		echo "Starting airtime_anypay ..."
		$BINCMD $CMDOPTS $LAUNCHER
		if [ ! "$?" = 0 ]; then
			echo "Failed to start airtime_anypay please check the log files!"
		else
			echo "airtime_anypay has been started successfully."
		fi;
		exit $?
	;;
	"stop")
		echo "Stopping airtime_anypay ..."
		if [ ! -f $PIDFILE ]; then
			echo "FAILED: no pid file found. Process may not start yet."
			exit 1;
		fi;
		$BINCMD $CMDOPTS -stop $LAUNCHER
		if [ ! "$?" = 0 ]; then
			echo "Failed to stop airtime_anypay."
		else
			echo "airtime_anypay hase been stopped successfully."
		fi;
		exit $?
	;;
	"restart")
		$CURPATH/$0 stop
		$CURPATH/$0 start		
	;;
	*)
		echo "usage: $0 {start|stop|restart}"
	;;
esac

