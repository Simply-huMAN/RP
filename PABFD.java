// PABFD (Power Aware Best Fit Decreasing)

//Pseudo Code
// Input: hostList, vmList
// Output: vmPlacement

// sort vmList in the order of decreasing CPU utilization
// foreach vm in vmList do:
//   minPower = MAX;
//   allocatedHost = NULL;
//   foreach host in hostList do
//      if host has enough resources for vm then
//           power = estimatePower(host,vm);
//           if power < minPower then
//               allocatedHost = host;
//               minPower = power;
//           end
//      end
//   end
//   if allocatedHost != NULL then
//             add(allocatedHost,vm) to vmPlacement;
//   end

//   Result: vmPlacement


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.lists.PowerVmList;

//This is just the function code
  protected List<Map<String, Object>> getNewVmPlacement(
  			List<? extends Vm> vmsToMigrate,
  			Set<? extends Host> excludedHosts) {
  		List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
  		PowerVmList.sortByCpuUtilization(vmsToMigrate);
  		for (Vm vm : vmsToMigrate) {
  			PowerHost allocatedHost = findHostForVm(vm, excludedHosts);
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
