#!/bin/bash
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
LAUNCHER="com.mbv.airtime.alert.launcher.AlertLauncher"
JAVACP="${RUNPATH}/config:$(echo $RUNPATH/lib/*.jar . | sed 's/ /:/g')"
java -cp $JAVACP $LAUNCHER

