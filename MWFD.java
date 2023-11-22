// Modified Worst Fit Decreasing

// Pseudocode:
// Require: List of hosts and VMs
// Ensure: Allocation dictionary containing the mapping between host and VM.

// allocated = {}
// sort VMs in non increasing order of its CPU requirements
// for each VM in the list of VMs do:
//   allocatedHost = NULL
//   maxPower = Double.MIN_VALUE
//   for each host in the list of hosts do:
//       if host is appropriate for VM then:
//           power1 = host.getPower()
//           power2 = power_after_allocation(host,vm)
//           powerDifference = power2-power1
//           if powerDifference >= maxPower then:
//               allocatedHost = host
//               maxPower=powerDifference
//       if allocatedHost!= NULL then:
//         add the mapping betweeb the allocated host and VM during allocation
// return allocation


protected List<Map<String, Object>> getNewVmPlacement(List<? extends Vm> vmsToMigrate, Set<? extends Host> excludedHosts) {
	    List<Map<String, Object>> migrationMap = new LinkedList<>();
	    PowerVmList.sortByCpuUtilization(vmsToMigrate);
	    
	    for (Vm vm : vmsToMigrate) {
	        double maxPowerDiff = Double.MIN_VALUE;
	        PowerHost allocatedHost = null;

	        for (PowerHost host : this.<PowerHost>getHostList()) {
	            if (!excludedHosts.contains(host) && host.isSuitableForVm(vm)) {
	                double power1 = host.getPower();
	                allocatedHost = host;
	                double power2 = host.getPower();
//	                double power2 = powerAfterAllocation(host, vm);
	                double powerDiff = power2 - power1;
	                allocatedHost = null;

	                if (powerDiff >= maxPowerDiff) {
	                    allocatedHost = host;
	                    maxPowerDiff = powerDiff;
	                }
	            }
	        }

	        if (allocatedHost != null) {
	            allocatedHost.vmCreate(vm);
	            Log.printLine("VM #" + vm.getId() + " allocated to host #" + allocatedHost.getId());

	            Map<String, Object> migrate = new HashMap<>();
	            migrate.put("vm", vm);
	            migrate.put("host", allocatedHost);
	            migrationMap.add(migrate);
	        }
	    }

	    return migrationMap;
	}
