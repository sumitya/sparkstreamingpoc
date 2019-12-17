#!/bin/bash

COUNT=`wc -l sample_apache_logs.log|awk '{print $1}'`

for ((i=1;i<=${COUNT};i++))
do
    sed -n ${i}p sample_apache_logs.log >> APACHE_LOGS.log
    printf "Writing the logs to Apache_Logs.log file, Now Sleeping for 500 millisec \n"
    sleep 0.5
done