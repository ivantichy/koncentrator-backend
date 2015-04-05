#!/bin/bash

set -ex 
set -o pipefail

rm -R -f /etc/openvpn/easy-rsa/2.0/instances/*

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest  




