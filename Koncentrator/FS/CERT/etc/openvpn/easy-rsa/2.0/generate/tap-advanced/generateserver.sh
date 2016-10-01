#!/bin/sh

#arguments subvpn_type server_name
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

if [ ! -d "$rootdir/instances/$subvpn_type" ]
  then
  echo "type does not exist"
  exit 1
fi

if [ ! -d "$rootdir/instances/$subvpn_type/$server_name" ]
  then
  echo "name does not exist"
  exit 1
fi


echo ehm, jdeme na to
#cd "$rootdir/generate/$subvpn_type"

#. ./vars
#./clean-all

#cp -r "$rootdir/generate/$subvpn_type" "$rootdir/instances/$subvpn_type/$server_name"
cd "$rootdir/instances/$subvpn_type/$server_name"

echo pwd=`pwd`
#echo "$rootdir/instances/$subvpn_type/$server_name"


if [ `pwd` != "$rootdir/instances/$subvpn_type/$server_name" ]
  then
  echo "omg.. path is wrong"
  exit 1
fi

#. ./vars
#openssl dhparam -out ./keys/dh2048.pem 2048
#./pkitool --initca $server_name
./pkitool --server $server_name
#openvpn --genkey --secret ./keys/ta.key

cd keys

if [ ! -s "./$server_name.key" ]; then
    echo "$server_name.key not OK"
    exit 1
fi

if [ ! -s "./$server_name.crt" ]; then
    echo "$server_name.crt not OK"
    exit 1
fi

