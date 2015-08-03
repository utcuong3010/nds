#!/bin/bash

HOME=`dirname "${BASH_SOURCE-$0}"`
HOME=`cd "$HOME">/dev/null; pwd`
VPNCONF=$HOME/config/AITSVPN.conf
LOG_PATH=/tmp/logs/sabre
LOG=sabre-vpn.log
SERVER=access.sabre.com
PORT=389

NOW=$(date +"%Y-%m-%d %T")

if [ ! -e $LOG_PATH/$LOG ]; then
        mkdir -p $LOG_PATH && touch $LOG_PATH/$LOG
fi


echo "quit" | telnet $SERVER $PORT | grep "Escape character is"

if [ "$?" -ne 0 ]; then
        echo "[$NOW] Connection to $SERVER on port $PORT failed" >> $LOG_PATH/$LOG
        echo "Connection to $SERVER on port $PORT failed"
        vpnc-disconnect >> $LOG_PATH/$LOG
        vpnc $VPNCONF >> $LOG_PATH/$LOG
        exit 1
else
        echo "[$NOW] Connection to $SERVER on port $PORT succeeded" >> $LOG_PATH/$LOG
        echo "Connection to $SERVER on port $PORT succeeded"
        exit 0
fi
