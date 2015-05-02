#!/bin/bash
set -ex 
set -o pipefail
rm *.json


## tap-advanced

echo tap-advanced

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tap-advanced-12345 tap-advanced 


# toto do ca.json prida parametr a ulozi to zase jako ca.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_server 123.123.123.123
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json ip_mask 255.255.0.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json node 1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_device tap1
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_management_port 20001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_port 15001
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_protocol udp
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter ca.json server_domain_name tap-advanced.koncentrator.cz

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter server.json server_commands ""

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapterTest2 ca.json

exit

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateServerAdapterTest server.json


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter server.json ip_server 111.111.111.111
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter server.json ip_mask 255.255.255.0
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.UpdateServerAdapterTest server.json


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_remote 10.10.10.10
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_local 10.10.10.11
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json profile_commands ""

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.CreateProfileAdapterTest profile.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_remote 10.10.12.10
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.support.JSON.test.JSONAddParameter profile.json ip_local 10.10.12.11
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.UpdateProfileAdapterTest profile.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.BlockProfileAdapterTest profile.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.DeleteProfileAdapterTest tap-advanced-12345 tap-advanced test

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.BlockSubVPNAdapterTest ca.json
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.httpapi.handlers.vpnapi.DeleteSubVPNAdapterTest tap-advanced-12345 tap-advanced








