#!/bin/bash
APPVER=0.1.0
SRCPATH=$(pwd)
cd ../airtime-common
mvn clean install
cd ../airtime-report
mvn clean install
echo "Unzipping the package ..."
unzip $SRCPATH/target/airtime-report-$APPVER-bin.zip -d /opt
if [ ! "$?" = 0 ]; then
	echo "ERROR Failed to unzip the package."
	exit 1;
fi;

DEPLOYDIR=/opt/airtime-report-$APPVER
chmod a+x $DEPLOYDIR/bin/airtime_report.sh
chmod a+x $DEPLOYDIR/bin/airtime_alert.sh
echo "Deploy successfully to $DEPLOYDIR"

