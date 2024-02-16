## weblogic installation for iCargo

in this context the literals used are 	

# LOCAL :- the new machine to which the weblogic installation is to be done
# FROM_MACHINE_IP :- the ip of the source machine from where the binaries of softwares are to be copied.

# 1. copy the required file directories from an already running machine to the LOCAL 
	=> instead of copying as a directory, first zip all the directories as a single zip file, and then copy.
	=> the directories to be copy from the FROM_MACHINE_IP are 
			- /opt/jdk1.8.0_271
			- /opt/neo-txprobe-aggregator
			- /opt/icob
			- /opt/weblogic
			
			* the command that can be used from the local machine is 
				ssh FROM_MACHINE_IP "cd opt; tar -cf /tmp/files2copy.tar jdk1.8.0_271 neo-txprobe-aggregator  icob weblogic"
				