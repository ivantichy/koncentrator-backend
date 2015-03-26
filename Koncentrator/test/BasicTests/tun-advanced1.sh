#!/bin/bash

set -ex

java -classpath ./Koncentrator/*;./Koncentrator/lib/* cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest &

curl -v -f -X PUT 127.0.0.1:10001/test -d @Koncentrator/target/classes/cz/ivantichy/httpapi/handlers/vpnapi/test.json

