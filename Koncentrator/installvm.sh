#!/bin/bash
apt-get update -q
apt-get upgrade -y -q
apt-get install build-essential module-assistant -y -q
apt-get install build-essential linux-headers-$(uname -r) -y -q
m-a prepare
mount /media/cdrom
sh /media/cdrom/VBoxLinuxAdditions.run
mkdir ~/Koncentrator
#mount -t vboxsf Koncentrator ~/Koncentrator

apt-get install apt-transport-https ca-certificates -y -q
apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
echo "deb https://apt.dockerproject.org/repo debian-jessie main" > /etc/apt/sources.list.d/docker.list
apt-get update -q
apt-get install docker-engine -y -q

cp /etc/rc.local /etc/rc.local.bak
echo "mount -t vboxsf Koncentrator /root/Koncentrator" > /etc/rc.local

echo "check for errors and reboot the machine + allow root login"



