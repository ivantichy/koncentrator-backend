#!/bin/bash

#arguments subvpn_type server_name ca_name
subvpn_type=$1;
server_name=$2;
ca_name=$3;



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

if [ ! -d "$rootdir/instances/$subvpn_type/$ca_name" ]
  then
  echo "ca_name does not exist"
  exit 1
fi


cd "$rootdir/instances/$subvpn_type/$ca_name"

./pkitool --server $server_name  2>  ./output.err

cd keys

if [ ! -s "./$server_name.key" ]; then
    echo "$server_name.key not OK"
    exit 1
fi

if [ ! -s "./$server_name.crt" ]; then
    echo "$server_name.crt not OK"
    exit 1
fi

