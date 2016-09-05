#!/bin/bash

type_name=$1;
name=$2;
#common_name=$3;

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

if [ ! -d "$rootdir/generate/$type_name" ]
  then
  echo "type does not exist"
  exit 1
fi

#if [ -d "$rootdir/instances/$type_name/$name" ]
#  then
#  echo "name already exist"
#  exit 1
#fi


cd "$rootdir/generate/$type_name"

#. ./vars
#./clean-all
#mkdir -p "$rootdir/instances/$type_name/$name"
#cp -r -f $rootdir/generate/$type_name/* $rootdir/instances/$type_name/$name

cd $rootdir/instances/$type_name/$name

echo pwd=`pwd`
#echo "$rootdir/instances/$type_name/$name"


if [ `pwd` != "$rootdir/instances/$type_name/$name" ]
  then
  echo "omg.. path is wrong"
  exit 1
fi

. ./pkitool --initca $name


/usr/sbin/openvpn --genkey --secret $rootdir/instances/$type_name/$name/keys/ta.key

cd $rootdir/instances/$type_name/$name/keys

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









