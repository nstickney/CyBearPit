#!/bin/bash
# Creates the router configuration file for the CyBearPit N3

# External addresses
DHCP_ADDR=192.168.7.100
GATEWAY=172.25.22.1

# Router addresses
WAN_ADDR=172.25.22.2
WAN_MASK=255.255.255.0
WAN_VLAN=4094

GAME_ADDR=10.128.0.1
GAME_BITS=0.127.255.255
GAME_MASK=255.128.0.0
GAME_VLAN=400
GAME_NET=10.128.0.0

TEAM_BITS=0.0.255.255
TEAM_MASK=255.255.0.0

# Number of teams to create VLANs for (MINIMUM 1, MAXIMUM 99)
if [[ ! "$1" =~ ^-?[0-9]+$ ]]; then
	>&2 echo "ERROR: First argument ($1) is the number of teams"
	exit
elif [[ "$1" -lt 1 ]] || [[ "$1" -gt 99 ]]; then
	>&2 echo "ERROR: Number of teams must be between 1 and 99"
	exit
else
	NUM_TEAMS=$1
fi
FIX_TEAMS=$(printf "%02d" "$NUM_TEAMS")

# Start the configuration file
echo "#BEGIN: 07.63.06.0002"
echo ""
echo "begin"

# Useful information
echo "# ***** NON-DEFAULT CONFIGURATION *****"
echo "# Chassis Firmware Revision:  07.63.06.0002"
echo "!"
echo "#  SLOT   TYPE"
echo "#  ___    ________________"
echo "#   1     7G4270-12"
echo "#   2     7G4202-30"
echo "#   3    "
echo "!"

# System-level configuration
echo "# system"
echo "set system contact \"Baylor Cyber - Nathaniel Stickney\""
echo "set system location \"Hankamer 276\""
echo "set system login admin super-user enable password :b70453acc5ed07357c1c6ccbd65f00d14b0a8ff602a9d02617884808:1:"
echo "set system login ro read-only disable password :a479cee8e80be4b2f4adbcd28e4d790fe644899e:"
echo "set system login rw read-write enable password :5e4ac668284d66951fcac6e7832f2f297f10551a8e2e40ef267b1f1f:1:"
echo "!"

# Start router configuration
echo "!"
echo "# modal configuration"
echo "!"
echo "configure terminal"
echo "!"

# IP access lists (management, etc)
echo "ip access-list standard AllowMgmt"
#echo "  permit $MGMT_NET $MGMT_BITS" 
echo "  permit $GAME_NET $GAME_BITS"
echo "  exit"
echo "!"

# IP access lists (teams on vlans)
for i in $(seq -f "%02g" 1 "$NUM_TEAMS"); do
	echo "ip access-list standard AllowTeam$i"
	echo "  permit 10.$i.0.0 $TEAM_BITS"
	echo "  exit"
done
echo "!"

# WAN VLAN interface
echo "interface vlan.0.$WAN_VLAN"
echo "  description \"WAN (External)\""
echo "  ip address $WAN_ADDR $WAN_MASK primary"
echo "  no ip proxy-arp"
echo "  no shutdown"
echo "  exit"
echo "!"

# LAN VLAN interface
echo "interface vlan.0.$GAME_VLAN"
echo "  description \"LAN (Game Network)\""
echo "  ip address $GAME_ADDR $GAME_MASK primary"
echo "  no ip proxy-arp"
echo "  no shutdown"
echo "  exit"
echo "!"

# VLAN interfaces (teams)
for i in $(seq -f "%02g" 1 "$NUM_TEAMS"); do
	echo "interface vlan.0.1$i"
	echo "  description \"Team $i WAN\""
	echo "  ip address 10.$i.0.1 $TEAM_MASK primary"
	echo "  ip access-group AllowMgmt in"
	echo "  ip access-group AllowMgmt out"
	echo "  ip helper-address $DHCP_ADDR"
	echo "  no ip proxy-arp"
	echo "  no shutdown"
	echo "  exit"
	echo "interface vlan.0.2$i"
	echo "  description \"Team $i LAN\""
#	echo "  no ip proxy-arp"
	echo "  no shutdown"
	echo "  exit"
	echo "interface vlan.0.3$i"
	echo "  description \"Team $i DMZ\""
#	echo "  no ip proxy-arp"
	echo "  no shutdown"
	echo "  exit"
	echo "!"
done

# Routes
echo "ip route 0.0.0.0/0 $GATEWAY interface vlan.0.$WAN_VLAN"
echo "!"

# End the CLI session?
echo "exit"
echo "!"

# Set the banner
echo "# banner"
echo "set banner motd \"CyBearPit N3 Router\""
echo "!"

# Ports (WAN and game net)
echo "# ports"
echo "set port alias vlan.0.$WAN_VLAN \"WAN (External)\"" 
echo "set port alias vlan.0.$GAME_VLAN \"LAN (Game Network)\"" 
echo "!"

# Ports (teams)
for i in $(seq -f "%02g" 1 "$NUM_TEAMS"); do
	echo "set port alias vlan.0.1$i \"Team $i WAN\""
	echo "set port alias vlan.0.2$i \"Team $i LAN\""
	echo "set port alias vlan.0.3$i \"Team $i DMZ\""
	echo "!"
done

# Ports (physical)
echo "set port vlan ge.2.1 201"
echo "set port vlan ge.2.2 101"
echo "set port vlan ge.2.3 202"
echo "set port vlan ge.2.4 102"
echo "set port vlan ge.2.5 203"
echo "set port vlan ge.2.6 103"
echo "set port vlan ge.2.7 204"
echo "set port vlan ge.2.8 104"
echo "set port vlan ge.2.9 205"
echo "set port vlan ge.2.10 105"
echo "set port vlan ge.2.11 206"
echo "set port vlan ge.2.12 106"
echo "set port vlan ge.2.13 207"
echo "set port vlan ge.2.14 107"
echo "set port vlan ge.2.15 208"
echo "set port vlan ge.2.16 108"
echo "set port vlan ge.2.17 209"
echo "set port vlan ge.2.18 109"
echo "set port vlan ge.2.19 210"
echo "set port vlan ge.2.20 110"
#echo "set port vlan ge.2.21 0"
echo "set port vlan ge.2.22 $WAN_VLAN"
#echo0 "set port vlan ge.2.23 0"
echo "set port vlan ge.2.24 101-1$FIX_TEAMS,$WAN_VLAN"
echo "set port vlan ge.2.25 201-2$FIX_TEAMS"
echo "set port vlan ge.2.26 301-3$FIX_TEAMS"
echo "set port vlan ge.2.27 $GAME_VLAN"
echo "set port vlan ge.2.28 $GAME_VLAN"
echo "set port vlan ge.2.29 $GAME_VLAN"
echo "set port vlan ge.2.30 $GAME_VLAN,$WAN_VLAN"
echo "!"

# Prompt
echo "# prompt"
echo "set prompt CyBearPit-N3"
echo "!"

# SNMP
echo "# snmp"
echo "set snmp view viewname All subtree 1"
echo "set snmp view viewname All subtree 0.0"
echo "!"

# SSH
echo "# ssh"
echo "set ssh disabled"
echo "!"

# telnet
echo "# telnet"
echo "set telnet disable inbound"
echo "set telnet disable outbound"
echo "!"

# Timezone
echo "# timezone"
echo "set timezone 'Cen' -6 0"
echo "!"

# Create and name VLANs
echo "# vlan"
echo "set vlan create 101-1$FIX_TEAMS,201-2$FIX_TEAMS,301-3$FIX_TEAMS,$WAN_VLAN"
echo "!"
echo "set vlan name $WAN_VLAN \"WAN (External)\""
echo "set vlan name $GAME_VLAN \"LAN (Game Network)\""
for i in $(seq -f "%02g" 1 "$NUM_TEAMS"); do
	echo "set vlan name 1$i \"Team $i WAN\""
	echo "set vlan name 2$i \"Team $i LAN\""
	echo "set vlan name 3$i \"Team $i DMZ\""
done
echo "!"

# Set VLAN egress
echo "clear vlan egress 1 ge.2.1-30"
echo "set vlan egress 201 ge.2.1 untagged"
echo "set vlan egress 101 ge.2.2 untagged"
echo "set vlan egress 202 ge.2.3 untagged"
echo "set vlan egress 102 ge.2.4 untagged"
echo "set vlan egress 203 ge.2.5 untagged"
echo "set vlan egress 103 ge.2.6 untagged"
echo "set vlan egress 204 ge.2.7 untagged"
echo "set vlan egress 104 ge.2.8 untagged"
echo "set vlan egress 205 ge.2.9 untagged"
echo "set vlan egress 105 ge.2.10 untagged"
echo "set vlan egress 206 ge.2.11 untagged"
echo "set vlan egress 106 ge.2.12 untagged"
echo "set vlan egress 207 ge.2.13 untagged"
echo "set vlan egress 107 ge.2.14 untagged"
echo "set vlan egress 208 ge.2.15 untagged"
echo "set vlan egress 108 ge.2.16 untagged"
echo "set vlan egress 209 ge.2.17 untagged"
echo "set vlan egress 109 ge.2.18 untagged"
echo "set vlan egress 210 ge.2.19 untagged"
echo "set vlan egress 110 ge.2.20 untagged"
# ge.2.21 unused
echo "set vlan egress $WAN_VLAN ge.2.22 untagged"
# ge.2.23 unused
# ge.2.24 $WAN_VLAN and 100-199, tagged
echo "set vlan egress $GAME_VLAN ge.2.25 untagged"
# ge.2.26 200-299, tagged
echo "set vlan egress $GAME_VLAN ge.2.27 untagged"
# ge.2.28 300-399, tagged
# ge.2.29 $WAN_VLAN and $GAME_VLAN, tagged
echo "set vlan egress $GAME_VLAN ge.2.30 untagged"
echo "!"

# Create VLAN iterfaces
echo "set vlan interface $WAN_VLAN create"
echo "set vlan interface $GAME_VLAN create"
for i in {1..3}; do
	for j in $(seq -f "%02g" 1 "$NUM_TEAMS"); do
		echo "set vlan interface $i$j create"
	done
done
echo "!"

# WebView
echo "# webview"
echo "set webview disable"
echo "!"

# End the file
echo "end"
echo ""
echo "#END: 00488b9153a97cf0d011c1729c42fc35"
