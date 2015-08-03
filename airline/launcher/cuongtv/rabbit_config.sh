#!/bin/bash
SERVER_NAME="localhost"
RABBIT_USER="admin"
RABBIT_PASS="123456"
RABBIT_VHOSTS="cuongtruong"

#Exchanges
EXCHANGE_NAME="AirService"
TYPE_EXCHANGE="direct"

#Queues
VJ="Vietjet"
BL="Jetstar"
VNS="VN_Sabre"

#Routing_key
RVJ="VJ"
RBL="BL"
RVN="VN"

#Connect rabbitmq server
CONNECT="-H $SERVER_NAME -u $RABBIT_USER -p $RABBIT_PASS -V $RABBIT_VHOSTS"

#create exchange
rabbitmqadmin.sh $CONNECT declare exchange name=$EXCHANGE_NAME type=$TYPE_EXCHANGE

#create queues
rabbitmqadmin.sh $CONNECT declare queue name=$VJ durable=true
rabbitmqadmin.sh $CONNECT declare queue name=$BL durable=true
rabbitmqadmin.sh $CONNECT declare queue name=$VNS durable=true

#binding queues
rabbitmqadmin.sh $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$VJ" routing_key="$RVJ"
rabbitmqadmin.sh $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$BL" routing_key="$RBL"
rabbitmqadmin.sh $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$VNS" routing_key="$RVN"


#######################Checking toool####

#Queues
AG="Checking_Tool"

#Routing_key
RAG="CT"

#Connect rabbitmq server
CONNECT="-H $SERVER_NAME -u $RABBIT_USER -p $RABBIT_PASS -V $RABBIT_VHOSTS"

#create exchange
#rabbitmqadmin.sh $CONNECT declare exchange name=$EXCHANGE_NAME type=$TYPE_EXCHANGE

#create queues
rabbitmqadmin.sh $CONNECT declare queue name=$AG durable=true

#binding queues
rabbitmqadmin.sh $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$AG" routing_key="$RAG"


