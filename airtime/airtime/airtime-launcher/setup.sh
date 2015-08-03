#!/bin/bash

NOARG=1
CLEANCLASSES=1
SETUPDB=1
REMOVEDB=0
OFFLINE=0
DEPLOY=0

APPVER="0.1.0"


CURPATH="$(pwd)"
SRCPATH="${0#./}"
base="${SRCPATH%/*}"

if [ "$base" = "$SRCPATH" ] ; then
{ cd "$CURPATH" ; SRCPATH=$(pwd) ; }
else
{ cd $CURPATH/$base ; SRCPATH=$(pwd) ; }
fi ;

#echo $CURPATH
echo "Setting up airtime-launcher system in $SRCPATH"
DEPLOYDIR="/opt/airtime-launcher-$APPVER"

echo
echo
echo ============================================================
echo compile daemon binary
echo ============================================================
if [ -f "$SRCPATH/src/main/bin/airtime_launcher" ];
then
	echo "Daemon Binary was built. Skipping ..."
else
	echo "Building Daemon Binary ..."
	if [ "$JAVA_HOME" = "" ]; then
		echo "ERROR: JAVA_HOME is not set! Have you installed java?"
		exit 1;
	fi;
	if [ ! -f "/data/trunk/opensrc/commons-daemon-1.0.1.tar.gz" ]; then
		echo "ERROR: /data/trunk/opensrc/commons-daemon-1.0.1.tar.gz is not found. make sure you have the latest source"
		exit 1;
	fi;
	cd /data/trunk/opensrc
	tar xzf commons-daemon-1.0.1.tar.gz
	cd commons-daemon-1.0.1/bin
	tar xzf jsvc.tar.gz
	cd jsvc-src
	chmod a+x configure
	./configure --with-java=$JAVA_HOME
	make
	cp jsvc $SRCPATH/src/main/bin/airtime_launcher
fi;



echo "Compiling JAVA SOURCES ..."

mvn clean package

if [ ! "$?" = 0 ]; then
	echo "ERROR: failed to compile Java source. Please check with developer."
	exit 3;
fi;


echo
echo
echo ============================================================
echo deploy package
echo ============================================================
echo "Cleaning $DEPLOYDIR ..." 
rm -fr $DEPLOYDIR

echo "Unzipping the package ..."
unzip $SRCPATH/target/airtime-launcher-$APPVER-bin.zip -d /opt
if [ ! "$?" = 0 ]; then
	echo "ERROR Failed to unzip the package."
	exit 1;
fi;

echo "Copying loader library from opensrc ... "
cp /data/trunk/opensrc/commons-daemon-1.0.1/commons-daemon.jar $DEPLOYDIR/lib
if [ ! "$?" = 0 ]; then
	echo "ERROR Failed to copy the library."
	exit 1;
fi;

if [ ! -d "$DEPLOYDIR/log" ]; then
	mkdir $DEPLOYDIR/log
fi;

chmod a+x $DEPLOYDIR/bin/airtime_launcher.sh
echo "Deploy successfully to $DEPLOYDIR"

