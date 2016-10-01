#!/bin/bash
# arguments subvpn_type server_name

subvpn_type=$1;
server_name=$2;
 
echo $subvpn_type
echo $server_name

if [ -z "$server_name" ]; then
    echo "server_name is empty"
    exit 1
fi


if [ -z "$subvpn_type" ]; then
    echo "subvpn_type is empty"
    exit 1
fi

rootdir=/etc/openvpn/easy-rsa/2.0

if [ ! -d "$rootdir/generate/$subvpn_type" ]
  then
  echo "type does not exist"
  exit 1
fi

#if [ -d "$rootdir/instances/$subvpn_type/$server_name" ]
#  then
#  echo "name already exist"
#  exit 1
#fi


cd "$rootdir/generate/$subvpn_type"

#. ./vars
#./clean-all
#mkdir -p "$rootdir/instances/$subvpn_type/$server_name"
#cp -r -f $rootdir/generate/$subvpn_type/* $rootdir/instances/$subvpn_type/$server_name

cd $rootdir/instances/$subvpn_type/$server_name

echo pwd=`pwd`
#echo "$rootdir/instances/$subvpn_type/$server_name"


if [ `pwd` != "$rootdir/instances/$subvpn_type/$server_name" ]
  then
  echo "omg.. path is wrong"
  exit 1
fi

. ./pkitool --initca $server_name


/usr/sbin/openvpn --genkey --secret $rootdir/instances/$subvpn_type/$server_name/keys/ta.key

cd $rootdir/instances/$subvpn_type/$server_name/keys

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









