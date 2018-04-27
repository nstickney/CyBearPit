#!/bin/bash

set_up_vm() {
	printf '%s' " ==> VM $2: $3 on $6"
	qm clone $1 $2 --name $3 --pool $4 --target $5
	printf '%s' "  ..."
	qm set $2 --net0 virtio,bridge=$6
	printf '%s\n' "  ... done"
}

echo "=== Active Directory Testing Scenario ==="

set_up_vm 1003 500 AD-1 InfosecTeam pve-00 vmbr2
set_up_vm 1002 501 AD-2 InfosecTeam pve-02 vmbr2
set_up_vm 1010 510 AD-Client-1 InfosecTeam pve-03 vmbr3
set_up_vm 1012 511 AD-Client-2 InfosecTeam pve-03 vmbr3
set_up_vm 2052 512 AD-Client-3 InfosecTeam pve-04 vmbr3
set_up_vm 2223 513 AD-Client-4 InfosecTeam pve-04 vmbr3
