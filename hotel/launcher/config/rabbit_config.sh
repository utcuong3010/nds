#!/bin/bash
SERVER_NAME="localhost"
RABBIT_USER="cuongtruong"
RABBIT_PASS="123456"
RABBIT_VHOSTS="cuongtruong"

#Exchanges
EXCHANGE_NAME="HotelService"
TYPE_EXCHANGE="direct"

#Queues
AGODA="Agoda"
IVIVU="Ivivu"

#Routing_key
RAGO="Agoda"
RBL="Ivivu"
RVN="VN"

#Connect rabbitmq server
CONNECT="-H $SERVER_NAME -u $RABBIT_USER -p $RABBIT_PASS -V $RABBIT_VHOSTS"

#create exchange
rabbitmqadmin $CONNECT declare exchange name=$EXCHANGE_NAME type=$TYPE_EXCHANGE

#create queues
rabbitmqadmin $CONNECT declare queue name=$VJ durable=true
rabbitmqadmin $CONNECT declare queue name=$BL durable=true
rabbitmqadmin $CONNECT declare queue name=$VNS durable=true

#binding queues
rabbitmqadmin $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$VJ" routing_key="$RVJ"
rabbitmqadmin $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$BL" routing_key="$RBL"
rabbitmqadmin $CONNECT declare binding source="$EXCHANGE_NAME" destination_type="queue" destination="$VNS" routing_key="$RVN"
