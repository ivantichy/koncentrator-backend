#!/bin/bash

set -ex

rm -R -f /etc/openvpn/tun-advanced_tun-advanced-12345
rm -R -f /etc/openvpn/instances/tun-advanced/*


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest &

sleep 10

curl -v -f -X PUT 127.0.0.1:10001/test -d @$(dirname $BASH_SOURCE)/test.json


set +e

curl -v -f -X PUT 127.0.0.1:10001/test -d @$(dirname $BASH_SOURCE)/test.json

[ $? = 0 ] && exit 1

exit 0

 



