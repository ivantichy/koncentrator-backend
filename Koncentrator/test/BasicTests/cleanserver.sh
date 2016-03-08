#!/bin/bash
set -ex

home=`pwd`

rm -r -f /etc/openvpn/*
cd ./Koncentrator/FS/VPN
cp  -r -f * /

cd ..
cd ..
cd ..

cd ./Koncentrator/FS/CERT
cp -r -f * /

cd /etc/openvpn
#find . -type f | grep .sh | xargs  -n1 chmod +x $1
find . -type f | xargs  -n1 chmod +x $1

sudo iptables-restore /etc/iptables/rules.v4.backup

sudo iptables --list -v

cd $home

java -classpath "Koncentrator/*:Koncentrator/lib/*" cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapterTest tun-basic-12345 tun-basic override