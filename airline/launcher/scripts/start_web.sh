#!/bin/bash
NAME="webapp"
DESC="Webapp service"

home=`dirname "${BASH_SOURCE-$0}"`
home=`cd "$home">/dev/null; pwd`

CLASSPATH=$home:$home/lib/*:$home/bin/*

JAVA_OPTS="-Dlog4j.configuration=config/log4j.properties -Dairline.configuration=config/airline.properties"

java -cp $CLASSPATH $JAVA_OPTS com.mbv.airline.launcher.Jetty
