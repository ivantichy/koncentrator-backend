#!/bin/bash

set -ex 
set -o pipefail


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test2 tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tun-basic-12345 tun-basic 
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test2 tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapterTest tun-basic-12345 tun-basic

