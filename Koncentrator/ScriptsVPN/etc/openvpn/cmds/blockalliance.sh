#!/bin/bash
# subvpn_name ip_range1 ip_range2
echo $1 $2 $3
device=`cat /etc/openvpn/$1/device`
fail=0

sudo iptables -C FORWARD -i $device -o $device -s $2 -d $3 -j ACCEPT
if [ $? -ne 0 ]; then fail=1;
else
  sudo iptables -D FORWARD -i $device -o $device -s $2 -d $3 -j ACCEPT
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $3 -d $2 -j ACCEPT
if [ $? -ne 0 ]; then fail=1;
else
  sudo iptables -D FORWARD -i $device -o $device -s $3 -d $2 -j ACCEPT
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $2 -d $3 -j DROP
if [ $? -eq 0 ]; then fail=1;
else
  sudo iptables -I FORWARD -i $device -o $device -s $2 -d $3 -j DROP
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $3 -d $2 -j DROP
if [ $? -eq 0 ]; then fail=1;
else
  sudo iptables -I FORWARD -i $device -o $device -s $3 -d $2 -j DROP
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables-save > /etc/iptables/rules.v4
if [ $? -ne 0 ]; then fail=1; fi


if [ $fail -ne 0 ]; then exit 1; fi
