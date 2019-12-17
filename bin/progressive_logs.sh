#!/bin/bash

#  netcat(nc) is used port scanning, port redirection, as a port listener
printf "Progressively sending the logs to port 7777 \n"
tail -f APACHE_LOGS.log | nc -lk 7777