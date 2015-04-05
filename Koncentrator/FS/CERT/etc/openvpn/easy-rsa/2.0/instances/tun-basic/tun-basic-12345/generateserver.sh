#!/bin/sh

type_name=$1;
name=$2;
common_name=$3;

echo $common_name 
echo $name
echo $type_name

if [ -z "$name" ]; then
    echo "name is empty"
    exit 1
fi


if [ -z "$type_name" ]; then
    echo "type_name is empty"
    exit 1
fi

rootdir=/etc/openvpn/easy-rsa/2.0

if [ ! -d "$rootdir/instances/$type_name" ]
  then
  echo "type does not exist"
  exit 1
fi

if [ ! -d "$rootdir/instances/$type_name/$name" ]
  then
  echo "name does not exist"
  exit 1
fi


echo ehm, jdeme na to
#cd "$rootdir/generate/$type_name"

#. ./vars
#./clean-all

#cp -r "$rootdir/generate/$type_name" "$rootdir/instances/$type_name/$name"
cd "$rootdir/instances/$type_name/$name"

echo pwd=`pwd`
#echo "$rootdir/instances/$type_name/$name"


if [ `pwd` != "$rootdir/instances/$type_name/$name" ]
  then
  echo "omg.. path is wrong"
  exit 1
fi

#. ./vars
#openssl dhparam -out ./keys/dh2048.pem 2048
#./pkitool --initca $name
./pkitool --server $common_name
#openvpn --genkey --secret ./keys/ta.key

cd keys

if [ ! -s "./$name.key" ]; then
    echo "$name.key not OK"
    exit 1
fi

if [ ! -s "./$name.crt" ]; then
    echo "$name.crt not OK"
    exit 1
fi

