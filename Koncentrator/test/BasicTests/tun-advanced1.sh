#!/bin/bash

set -ex

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest &

sleep 10

curl -v -f -X PUT 127.0.0.1:10001/test -d @./$(dirname $BASH_SOURCE)/test.json

