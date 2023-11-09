// MFPED (Medium-Fit Power Efficient Decreasing) Algorithm

// In MFPED, a host is better than another host for VM allocation if its CPU utilization level distance from the desired level is less than that of the other host (line in algorithm).
// In case two hosts have levels with equal distance from the desired level, then the one that has a higher PE is chosen (line )

// Pseudocode:
// Input: hostList, vmList
// Output: vmPlacement
// LD = 0.6; // The desired level is set to 0.6

// sort vmList in the order of decreasing CPU utilization;
// foreach vm in vmList do
//   minDiff = MAX; //MAX is the maximum number
//   allocatedHost = NULL;
//   foreach host in hostList do
//       if host is active and has enough resource for vm then
//             diff = |getUtilization(host)-LD|;
//             if diff < minDiff then
//                  allocatedHost = host;
//                  minDiff = diff;
//             else
//                  if diff == minDiff then
//                     //PE(.) is the power efficiency of a host
//                     if PE(host) > PE(allocatedHost) then
//                           allocatedHost = host;
//                     end
//                  end
//             end
//       end
//   end
//   if allocatedHost != NULL then
//         add(allocatedHost,vm) to vmPlacement;
//   end
// end

// Result: vmPlacement

// This is the functinon code
protected List<Map<String, Object>> getNewVmPlacement(List<? extends Vm> vmsToMigrate, Set<? extends Host> excludedHosts) {
	    List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
	    PowerVmList.sortByCpuUtilization(vmsToMigrate);
	    double LD = 0.6;
	    for (Vm vm : vmsToMigrate) {
	        double minDiff = Double.MAX_VALUE;
	        PowerHost allocatedHost = null;
	        for (PowerHost host : this.<PowerHost>getHostList()) {
//	        	if (host.isActive() && !excludedHosts.contains(host) && host.isSuitableForVm(vm)) {
	            if (!host.isFailed() && !excludedHosts.contains(host) && host.isSuitableForVm(vm)) {
	                double diff = Math.abs(host.getUtilizationOfCpu() - LD);
	                if (diff < minDiff) {
	                    allocatedHost = host;
	                    minDiff = diff;
	                } else if (diff == minDiff && host.getPower() > allocatedHost.getPower()) {
	                    allocatedHost = host;
	                }
	            }
	        }
	        if (allocatedHost != null) {
	            allocatedHost.vmCreate(vm);
	            Log.printLine("VM #" + vm.getId() + " allocated to host #" + allocatedHost.getId());

	            Map<String, Object> migrate = new HashMap<String, Object>();
	            migrate.put("vm", vm);
	            migrate.put("host", allocatedHost);
	            migrationMap.add(migrate);
	        }
	    }
	    return migrationMap;
	}
