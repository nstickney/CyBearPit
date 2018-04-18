#!/bin/bash

for i in {10..99}; do
	printf '%s\n' "interface vlan.0.1$i"
	printf '%s\n' "  description \"Team $i External\""
	printf '%s\n' "  ip address 10.1.$i.1 255.255.255.0 primary"
	printf '%s\n' "  ip access-group AllowMgmt in"
	printf '%s\n' "  ip access-group AllowMgmt out"
	printf '%s\n' "  ip helper-address 192.168.7.100"
	printf '%s\n' "  no ip proxy-arp"
	printf '%s\n' "  no shutdown"
	printf '%s\n' "  exit"
done

