#!/bin/bash
# Creates the router configuration file for the CyBearPit N3

# WAN addresses
DHCP_ADDR=192.168.7.100
GATEWAY=172.25.22.1

# Router addresses
WAN_ADDR=172.25.22.2
WAN_MASK=255.255.255.0
WAN_VLAN=4094

LAN_ADDR=10.128.0.1
LAN_MASK=255.128.0.0
LAN_VLAN=400

GAME_MASK=255.255.0.0

# Router networks
GAME_NET=10.128.0.0
GAME_BITS=0.127.255.255

TEAM_BITS=0.0.255.255

# Start the configuration file
printf '%s\n' "#BEGIN: 07.63.06.0002"
printf '%s\n' ""
printf '%s\n' "begin"
printf '%s\n' "# ***** NON-DEFAULT CONFIGURATION *****"
printf '%s\n' "# Chassis Firmware Revision:  07.63.06.0002"
printf '%s\n' "!"
printf '%s\n' "#  SLOT   TYPE"
printf '%s\n' "#  ___    ________________"
printf '%s\n' "#   1     7G4270-12"
printf '%s\n' "#   2     7G4202-30"
printf '%s\n' "#   3    "
printf '%s\n' "!"

# System-level configuration
printf '%s\n' "!"
printf '%s\n' "# system"
printf '%s\n' "set system contact \"Baylor Cyber - Nathaniel Stickney\""
printf '%s\n' "set system location \"Hankamer 276\""
printf '%s\n' "set system login admin super-user enable password :b70453acc5ed07357c1c6ccbd65f00d14b0a8ff602a9d02617884808:1:"
printf '%s\n' "set system login ro read-only disable password :a479cee8e80be4b2f4adbcd28e4d790fe644899e:"
printf '%s\n' "set system login rw read-write enable password :5e4ac668284d66951fcac6e7832f2f297f10551a8e2e40ef267b1f1f:1:"
printf '%s\n' "!"
printf '%s\n' "# license"
printf '%s\n' "!"

# Start router configuration
printf '%s\n' "!" "# modal configuration" "!" "configure terminal" "!"

# IP access lists (management, etc)
printf '%s\n' "ip access-list standard AllowMgmt"
#printf '%s\n' "  permit $MGMT_NET $MGMT_BITS" 
printf '%s\n' "  permit $GAME_NET $GAME_BITS"
printf '%s\n' "  exit"
printf '%s\n' "!"

# IP access lists (teams on vlans)
for i in $(seq -f "%02g" 1 99); do
	printf '%s\n' "ip access-list standard AllowTeam$i"
	printf '%s\n' "  permit 10.$i.0.0 $TEAM_BITS" "  exit"
done
printf '%s\n' "!"

# WAN VLAN interface
printf '%s\n' "interface vlan.0.$WAN_VLAN" "  description \"Uplink\""
printf '%s\n' "  ip address $WAN_ADDR $WAN_MASK primary"
printf '%s\n' "  no ip proxy-arp"
printf '%s\n' "  no shutdown"
printf '%s\n' "  exit"
printf '%s\n' "!"

# LAN VLAN interface
printf '%s\n' "interface vlan.0.$LAN_VLAN" "  description \"Game Network\""
printf '%s\n' "  ip address $LAN_ADDR $LAN_MASK primary"
printf '%s\n' "  no ip proxy-arp"
printf '%s\n' "  no shutdown"
printf '%s\n' "  exit"
printf '%s\n' "!"

# VLAN interfaces (teams)
for i in $(seq -f "%02g" 1 99); do
	printf '%s\n' "interface vlan.0.1$i"
	printf '%s\n' "  description \"Team $i WAN\""
	printf '%s\n' "  ip address 10.$i.0.1 $GAME_MASK primary"
	printf '%s\n' "  ip access-group AllowMgmt in"
	printf '%s\n' "  ip access-group AllowMgmt out"
	printf '%s\n' "  ip helper-address $DHCP_ADDR"
	printf '%s\n' "  no ip proxy-arp"
	printf '%s\n' "  no shutdown"
	printf '%s\n' "  exit"
	printf '%s\n' "interface vlan.0.2$i"
	printf '%s\n' "  description \"Team $i LAN\""
#	printf '%s\n' "  no ip proxy-arp"
	printf '%s\n' "  no shutdown"
	printf '%s\n' "  exit"
	printf '%s\n' "interface vlan.0.3$i"
	printf '%s\n' "  description \"Team $i DMZ\""
#	printf '%s\n' "  no ip proxy-arp"
	printf '%s\n' "  no shutdown"
	printf '%s\n' "  exit"
	printf '%s\n' "!"
done

# Routes
printf '%s\n' "ip route 0.0.0.0/0 $GATEWAY interface vlan.0.$WAN_VLAN"
printf '%s\n' "!"

# End the CLI session?
printf '%s\n' "exit"
printf '%s\n' "!"

# Set the banner
printf '%s\n' "set banner motd \"CyBearPit N3 Router\""
printf '%s\n' "!"

# Ports (WAN and game net)
printf '%s\n' "set port alias vlan.0.$WAN_VLAN \"WAN (Uplink)\"" 
printf '%s\n' "set port alias vlan.0.$GAME_VLAN \"LAN (Gamenet)\"" 
printf '%s\n' "!"

# Ports (teams)
for i in $(seq -f "%02g" 1 99); do
	printf '%s\n' "set port alias vlan.0.1$i \"Team $i WAN\""
	printf '%s\n' "set port alias vlan.0.2$i \"Team $i LAN\""
	printf '%s\n' "set port alias vlan.0.3$i \"Team $i DMZ\""
done

# Ports (physical)
printf '%s\n' "set port vlan ge.2.1 201"
printf '%s\n' "set port vlan ge.2.2 101"
printf '%s\n' "set port vlan ge.2.3 202"
printf '%s\n' "set port vlan ge.2.4 102"
printf '%s\n' "set port vlan ge.2.5 203"
printf '%s\n' "set port vlan ge.2.6 103"
printf '%s\n' "set port vlan ge.2.7 204"
printf '%s\n' "set port vlan ge.2.8 104"
printf '%s\n' "set port vlan ge.2.9 205"
printf '%s\n' "set port vlan ge.2.10 105"
printf '%s\n' "set port vlan ge.2.11 206"
printf '%s\n' "set port vlan ge.2.12 106"
printf '%s\n' "set port vlan ge.2.13 207"
printf '%s\n' "set port vlan ge.2.14 107"
printf '%s\n' "set port vlan ge.2.15 208"
printf '%s\n' "set port vlan ge.2.16 108"
printf '%s\n' "set port vlan ge.2.17 209"
printf '%s\n' "set port vlan ge.2.18 109"
printf '%s\n' "set port vlan ge.2.19 210"
printf '%s\n' "set port vlan ge.2.20 110"
#printf '%s\n' "set port vlan ge.2.21 0"
printf '%s\n' "set port vlan ge.2.22 0"
#printf '%s\n' "set port vlan ge.2.23 0"
printf '%s\n' "set port vlan ge.2.24 100-199,$WAN_VLAN"
printf '%s\n' "set port vlan ge.2.25 200-299"
printf '%s\n' "set port vlan ge.2.26 300-399"
printf '%s\n' "set port vlan ge.2.27 $LAN_VLAN"
printf '%s\n' "set port vlan ge.2.28 $LAN_VLAN"
printf '%s\n' "set port vlan ge.2.29 $LAN_VLAN"
printf '%s\n' "set port vlan ge.2.30 $LAN_VLAN,$WAN_VLAN"
