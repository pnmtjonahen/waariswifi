#!/bin/sh
export JAVA_HOME=/home/ordina/jdk1.8.0
export PATH=$JAVA_HOME/bin:$PATH

export LOCAL_MAC=`ifconfig  wlan0  | grep 'ether' | tr -s ' ' | cut -d ' ' -f3`
export EMAIL=pnmtjonahen@gmail.com
export PASSWD=1qaz2wsx
java -jar SendMail-1.0-SNAPSHOT-jar-with-dependencies.jar $EMAIL $PASSWD $LOCAL_MAC