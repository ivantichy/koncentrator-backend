#!/bin/bash
# common_name subvpn_name subvpn_type
# $1          $2 $3

mv -f /etc/openvpn/$3/$2/profiles/$1 /etc/openvpn/$3/$2/profiles/$1_

if [ $? -ne 0 ]; then exit 1;  fi

(echo kill $1; echo exit) | nc localhost 12345
