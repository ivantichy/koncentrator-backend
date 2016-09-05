#!/bin/bash
# common_name ip_remote ip_local subvpn_name subvpn_type
# $1                    $2       $3      $4 $5

echo "$1;$2;$3;$4"

if [ -f /etc/openvpn/$5/$4/profiles/$1 ];
then
	echo "Profile already exists"
	exit 1
fi

echo "$1;$2;$3;$4" > /etc/openvpn/$5/$4/profiles/$1

