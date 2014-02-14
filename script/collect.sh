#!/bin/sh
export LOCAL_MAC=`ifconfig  wlan0  | grep 'ether' | tr -s ' ' | cut -d ' ' -f3`
export SERVER_URL=http://localhost:8080/server/rest/data
stdbuf -oL tshark -i wlan0 -I -f 'broadcast' -R 'wlan.fc.type == 0 && wlan.fc.subtype == 4' -T fields -e frame.time_epoch -e wlan.sa -e radiotap.dbm_antsignal -e radiotap.channel.freq | java -jar collector-1.0-SNAPSHOT-jar-with-dependencies.jar $LOCAL_MAC $SERVER_URL