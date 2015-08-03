#!/bin/bash
NAME="air_service"
DESC="Air service"

home=`dirname "${BASH_SOURCE-$0}"`
home=`cd "$home">/dev/null; pwd`

classpath=$home:$home/lib/*:$home/bin/*

JAVA_OPTS="-Dlog4j.configuration=config/log4j.properties -Dairline.configuration=config/airline.properties"

java -cp $classpath $JAVA_OPTS com.mbv.airline.launcher.Console

