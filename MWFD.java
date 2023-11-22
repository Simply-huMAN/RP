// Modified Worst Fit Decreasing

Pseudocode:
Require: List of hosts and VMs
Ensure: Allocation dictionary containing the mapping between host and VM.

allocated = {}
sort VMs in non increasing order of its CPU requirements
for each VM in the list of VMs do:
  allocatedHost = NULL
  maxPower = Double.MIN_VALUE
  for each host in the list of hosts do:
      if host is appropriate for VM then:
          power1 = host.getPower()
          power2 = power_after_allocation(host,vm)
          powerDifference = power2-power1
          if powerDifference >= maxPower then:
              allocatedHost = host
              maxPower=powerDifference
      if allocatedHost!= NULL then:
        add the mapping betweeb the allocated host and VM during allocation
return allocation
