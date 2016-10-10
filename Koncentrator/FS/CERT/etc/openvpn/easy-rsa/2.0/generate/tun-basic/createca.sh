#!/bin/bash
# arguments subvpn_type ca_name

subvpn_type=$1;
ca_name=$2;
 

if [ -z "$ca_name" ]; then
    echo "ca_name is empty"
    exit 1
fi


if [ -z "$subvpn_type" ]; then
    echo "subvpn_type is empty"
    exit 1
fi

rootdir=/etc/openvpn/easy-rsa/2.0

cd $rootdir/instances/$subvpn_type/$ca_name

./pkitool --initca $ca_name 2>  ./output.err

/usr/sbin/openvpn --genkey --secret $rootdir/instances/$subvpn_type/$ca_name/keys/ta.key

cd $rootdir/instances/$subvpn_type/$ca_name/keys

if [ ! -s "./ta.key" ]; then
    echo "ta.key not OK"
    exit 1
fi


if [ ! -s "./ca.crt" ]; then
    echo "ca.crt not OK"
    exit 1
fi


if [ ! -s "./ca.key" ]; then
    echo "ca.key not OK"
    exit 1
fi


if [ ! -s "./dh2048.pem" ]; then
    echo "dh2048.pem not OK"
    exit 1
fi









