#!/bin/bash
cd /home/java
java -classpath "*" cz.ivantichy.koncentrator.vpnapi.config.CERTAPIRunner 9081 &> cert.log &
