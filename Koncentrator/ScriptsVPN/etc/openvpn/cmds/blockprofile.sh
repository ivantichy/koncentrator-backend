#!/bin/bash
# common_name subvpn_name
# $1          $2 

mv -f /etc/openvpn/$2/profiles/$1 /etc/openvpn/$2/profiles/$1_

if [ $? -ne 0 ]; then exit 1;  fi

(echo kill $1; echo exit) | nc localhost 12345
