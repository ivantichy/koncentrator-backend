#!/bin/bash

#common_name=PBLv1-172_17_0_129.public.koncentrator.cz.koncentrator.cz
#path tmpfile
#$1      $2

common_name2=$(echo $common_name | cut -d. -f1)
common_name2=${common_name2//_/.}

echo "common_name2=$common_name2"

if [[ "$common_name2" == PBLv1-TUN-BASIC ]];
then
/etc/openvpn/$1/cmds/loglogin.sh $1 $common_name $ifconfig_pool_remote_ip $dev $proto $trusted_ip:$trusted_port
if [ $? -ne 0 ]; then exit 1;  fi
exit 0
fi

if [  ! -f /etc/openvpn/$1/profiles/$common_name2 ];
then
echo "Unknown common name $common_name"
/etc/openvpn/cmds/loginvalidlogin.sh $1 $common_name $localip $dev $proto $trusted_ip:$trusted_port
exit 1
fi

profilefile=`cat /etc/openvpn/$1/profiles/$common_name2`
if [ $? -ne 0 ]; then exit 1;  fi

echo "profilefile = $profilefile"

# common_name ip_remote ip_local subvpn_name
# $1 		  $2        $3       $4  
# echo $1;$2;$3;$4> /etc/openvpn/$5/profiles/$1
# common_name;ip_remote;ip_local;subvpn_name
pcommon_name=$(echo $profilefile | cut -d";" -f1) 
if [ $? -ne 0 ]; then exit 1;  fi
pip_remote=$(echo $profilefile | cut -d";" -f2) 
if [ $? -ne 0 ]; then exit 1;  fi
pip_local=$(echo $profilefile | cut -d";" -f3) 
if [ $? -ne 0 ]; then exit 1;  fi
psubvpn_name=$(echo $profilefile | cut -d";" -f4) 
if [ $? -ne 0 ]; then exit 1;  fi

 
 
#	localip=$(echo $common_name2 | cut -d- -f2)
#	prefix=$(echo $localip | cut -d. -f1,2,3)
#	last=$(echo $localip | cut -d. -f4)
#	remoteip=$prefix.$(expr $last - 1)

	echo "localip = $pip_local"
	echo "remoteip = $pip_remote"

	echo "ifconfig-push $pip_local $pip_remote" > $2
	# if not tun0
	sudo route add $pip_local/32 dev $dev || true

	/etc/openvpn/$1/cmds/loglogin.sh $1 $common_name $pip_local $dev $proto $trusted_ip:$trusted_port
if [ $? -ne 0 ]; then exit 1;  fi

exit 0
fi
echo "Something wrong in client-connect"
exit 1
