#!/bin/bash
set -ex

sudo -i -u java << EOF

cd $WORKSPACE/Koncentrator/FS/VPN
cp -v -r -f * /
cd /etc/openvpn
find . -type f | grep .sh | xargs  -n1 chmod +x $1 -v

sudo iptables-restore /etc/iptables/rules.v4.backup

EOF