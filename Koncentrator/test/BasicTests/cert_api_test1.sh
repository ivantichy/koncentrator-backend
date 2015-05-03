#!/bin/bash

set -ex 
set -o pipefail


java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test2 tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapterTest test tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tap-advanced-12345 tap-advanced 
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test2 tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tap-advanced-12345 tap-advanced
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapterTest tap-advanced-12345 tap-advanced

rm *.json

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest testx tun-basic-12345 tun-basic 
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test2 tun-basic-12345 tun-basic
java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapterTest test tun-basic-12345 tun-basic
