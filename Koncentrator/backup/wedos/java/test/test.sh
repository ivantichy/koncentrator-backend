#!/bin/bash
set -ex

mydir=`pwd`

sudo iptables --list > iptablesorigo.log

cd /etc/openvpn

find > $mydir/treeorigo.log

cd $mydir




wget "http://127.0.0.1:9081/generateserver/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=tun-basic-default&domain=koncentrator.cz&server_valid_days=3650" -O server1


wget "http://127.0.0.1:9081/generateprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test1&domain=koncentrator.cz&valid_days=90" -O profile1 -T 10
wget "http://127.0.0.1:9081/generateprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test2&domain=koncentrator.cz&valid_days=90" -O profile2 -T 10
wget "http://127.0.0.1:9081/generateprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test3&domain=koncentrator.cz&valid_days=90" -O profile3 -T 10
wget "http://127.0.0.1:9081/generateprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test4&domain=koncentrator.cz&valid_days=90" -O profile4 -T 10
wget "http://127.0.0.1:9081/generateprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test4&domain=koncentrator.cz&valid_days=90" -O profile4dup -T 10

################################

#curl -m 10 -f -v -X "PUT" "http://172.16.0.1:8081/initsubvpn/?subvpn_name=tun-basic-default-123456&subvpn_type=tun-basic"
#echo

curl -m 10 -f -v -X"PUT" "http://127.0.0.1:9082/createsubvpn/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_range=172.21.1.0/24"
echo

curl -m 10 -f -v -X "PUT" "http://127.0.0.1:9082/createprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_remote=172.21.1.1&ip_local=172.21.1.2&common_name=test1"
echo
curl -m 10 -f -v -X "PUT" "http://127.0.0.1:9082/createprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_remote=172.21.1.5&ip_local=172.21.1.6&common_name=test2"
echo
curl -m 10 -f -v -X "PUT" "http://127.0.0.1:9082/createprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_remote=172.21.1.9&ip_local=172.21.1.10&common_name=test3"
echo
curl -m 10 -f -v -X "PUT" "http://127.0.0.1:9082/createprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_remote=172.21.1.13&ip_local=172.21.1.14&common_name=test4"
echo

curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blockprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test1"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deleteprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test1"
echo

curl -m 10 -f -v -X "PUT" "http://127.0.0.1:9082/createalliance/?subvpn_name1=tun-basic-default&subvpn_type1=tun-basic&subvpn_name2=tun-basic-default&subvpn_type2=tun-basic&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo
curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blockalliance/?subvpn_name1=tun-basic-default&subvpn_type1=tun-basic&subvpn_name2=tun-basic-default&subvpn_type2=tun-basic&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deletealliance/?subvpn_name1=tun-basic-default&subvpn_type1=tun-basic&subvpn_name2=tun-basic-default&subvpn_type2=tun-basic&ip_range1=172.21.1.0/24&ip_range2=172.2.1.0/24"
echo

curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blockprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test2"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deleteprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test2"
echo

curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blockprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test3"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deleteprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test3"
echo

curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blockprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test4"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deleteprofile/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&common_name=test4"
echo


curl -m 10 -f -v -X "POST" "http://127.0.0.1:9082/blocksubvpn/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_range=172.21.1.0/24"
echo
curl -m 10 -f -v -X "DELETE" "http://127.0.0.1:9082/deletesubvpn/?subvpn_name=tun-basic-default&subvpn_type=tun-basic&ip_range=172.21.1.0/24"
echo




sudo iptables --list > iptablesnew.log

cd /etc/openvpn

find > $mydir/treenew.log

cd $mydir



#diff ./iptablesorigo.log ./iptablesnew.log

#diff ./treeorigo.log ./treenew.log


