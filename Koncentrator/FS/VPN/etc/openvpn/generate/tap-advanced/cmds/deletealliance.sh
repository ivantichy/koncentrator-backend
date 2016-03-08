#!/bin/bash
# subvpn_name1 subvpn_type1 subvpn_name2 subvpn_type2 ip_range1 ip_range2
echo $1 $2 $3 $4 $5 $6
device=`cat /etc/openvpn/instances/$2/$1/device`
fail=0

exit 0
sudo iptables -C FORWARD -i $device -o $device -s $5 -d $6 -j ACCEPT
if [ $? -eq 0 ]; then fail=1;
  sudo iptables -D FORWARD -i $device -o $device -s $5 -d $6 -j ACCEPT
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $6 -d $5 -j ACCEPT
if [ $? -eq 0 ]; then fail=1;
  sudo iptables -D FORWARD -i $device -o $device -s $6 -d $5 -j ACCEPT
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $5 -d $6 -j DROP
if [ $? -ne 0 ]; then fail=1;
  else
  sudo iptables -D FORWARD -i $device -o $device -s $5 -d $6 -j DROP
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables -C FORWARD -i $device -o $device -s $6 -d $5 -j DROP
if [ $? -ne 0 ]; then fail=1;
  else
  sudo iptables -D FORWARD -i $device -o $device -s $6 -d $5 -j DROP
  if [ $? -ne 0 ]; then fail=1; fi
fi

sudo iptables-save > /etc/iptables/rules.v4
if [ $? -ne 0 ]; then fail=1; fi


if [ $fail -ne 0 ]; then exit 1; fi
