iptables -A INPUT -i tun+ -p icmp --icmp-type 8 -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT

iptables -A INPUT -i tun+ -p icmp --icmp-type 0 -j ACCEPT

iptables -A OUTPUT -o tun+ -p icmp --icmp-type 8 -m state --state NEW,ESTABLISHED,RELATED -j ACCEPT

iptables -A OUTPUT -o tun+ -p icmp --icmp-type 0 -j ACCEPT

sudo iptables-save > /etc/iptables/rules.v4
