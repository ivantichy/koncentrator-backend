#!/bin/bash
cd /home/java
java -classpath "*" cz.ivantichy.koncentrator.vpnapi.config.VPNAPIRunner config.conf 9082 &> vpn.log &
