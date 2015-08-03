#!/bin/bash
SERVER_NAME="localhost"
RABBIT_USER="admin"
RABBIT_PASS="123456"
RABBIT_VHOSTS="cuongtruong"

#Exchanges
EXCHANGE_NAME="AirService"
TYPE_EXCHANGE="direct"

#Queues
AG="Checking_Tool"

#Routing_key
RAG="CT"

#Connect rabbitmq server
CONNECT="-H $SERVER_NAME -u $RABBIT_USER -p $RABBIT_PASS -V $RABBIT_VHOSTS"

#create exchange
#rabbitmqadmin $CONNECT declare exchange name=$EXCHANGE_NAME type=$TYPE_EXCHANGE

#create queues
rabbitmqadmin $CONNECT declare queue name=$AG durable=true

#binding queues
rabbitmqadmin $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$AG" routing_key="$RAG"
