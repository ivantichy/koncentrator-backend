#!/bin/bash

mydir=`pwd`

iptables --list > iptablesorigo.log

cd /etc/openvpn

find > $mydir/treeorigo.log

cd $mydir

wget "http://172.20.1.6:8081/generateprofile/?subvpn_name=tun-basic-default&common_name=XAAAAAAA&domain=koncentrator.cz&valid_days=90" -O profile1 -nv
wget "http://172.20.1.6:8081/generateprofile/?subvpn_name=tun-basic-default&common_name=XAAAAAAB&domain=koncentrator.cz&valid_days=90" -O profile2 -nv
wget "http://172.20.1.6:8081/generateprofile/?subvpn_name=tun-basic-default&common_name=XAAAAAAC&domain=koncentrator.cz&valid_days=90" -O profile3 -nv
wget "http://172.20.1.6:8081/generateprofile/?subvpn_name=tun-basic-default&common_name=XAAAAAAD&domain=koncentrator.cz&valid_days=90" -O profile4 -nv

################################

curl -X "PUT" "http://172.16.0.1:8081/initsubvpn/?subvpn_name=tun-basic-default-default"
echo
curl -X "PUT" "http://172.16.0.1:8081/createsubvpn/?subvpn_name=tun-basic-default&ip_range=172.21.1.0/24"
echo


curl -X "PUT" "http://172.16.0.1:8081/createprofile/?subvpn_name=tun-basic-default&ip_remote=172.21.1.1&ip_local=172.21.1.2&common_name=test1"
echo
curl -X "PUT" "http://172.16.0.1:8081/createprofile/?subvpn_name=tun-basic-default&ip_remote=172.21.1.5&ip_local=172.21.1.6&common_name=test2"
echo
curl -X "PUT" "http://172.16.0.1:8081/createprofile/?subvpn_name=tun-basic-default&ip_remote=172.21.1.9&ip_local=172.21.1.10&common_name=test3"
echo
curl -X "PUT" "http://172.16.0.1:8081/createprofile/?subvpn_name=tun-basic-default&ip_remote=172.21.1.13&ip_local=172.21.1.14&common_name=test4"
echo

curl -X "POST" "http://172.16.0.1:8081/blockprofile/?subvpn_name=tun-basic-default&common_name=test1"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deleteprofile/?subvpn_name=tun-basic-default&common_name=test1"
echo


curl -X "PUT" "http://172.16.0.1:8081/createalliance/?subvpn_name=tun-basic-default&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo
curl -X "POST" "http://172.16.0.1:8081/blockalliance/?subvpn_name=tun-basic-default&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deletealliance/?subvpn_name=tun-basic-default&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo


curl -X "POST" "http://172.16.0.1:8081/blockprofile/?subvpn_name=tun-basic-default&common_name=test2"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deleteprofile/?subvpn_name=tun-basic-default&common_name=test2"
echo

curl -X "POST" "http://172.16.0.1:8081/blockprofile/?subvpn_name=tun-basic-default&common_name=test3"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deleteprofile/?subvpn_name=tun-basic-default&common_name=test3"
echo

curl -X "POST" "http://172.16.0.1:8081/blockprofile/?subvpn_name=tun-basic-default&common_name=test4"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deleteprofile/?subvpn_name=tun-basic-default&common_name=test4"
echo


curl -X "POST" "http://172.16.0.1:8081/blocksubvpn/?subvpn_name=tun-basic-default&ip_range=172.21.1.0/24"
echo
curl -X "DELETE" "http://172.16.0.1:8081/deletesubvpn/?subvpn_name=tun-basic-default&ip_range=172.21.1.0/24"
echo




iptables --list > iptablesnew.log

cd /etc/openvpn

find > $mydir/treenew.log

cd $mydir



diff ./iptablesorigo.log ./iptablesnew.log

diff ./treeorigo.log ./treenew.log

