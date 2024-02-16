# Deployment Controller

The functions of the deployment controller are

* Act as a k8s init container, the dependencies for the services are ensured to be healthy before the service is started
  * The dependencies are resolved either by passing the service_type of the container
  * The dependencies are explicitly stated eg: as service names
  * Mandatory dependencies like config-server, kafka, redis, databases 
* Provide dynamic watch features ( deployed in the icargo-system namespace )
  * Watch the deployment namespace for activity which trigger actions like restart GW services on bff service refresh
  * Health management of services ie disable health on infra services being unavailable
  * Restart services when infra services are restored  
  * CrashLoopBack email notifications
  
  
# Input Details for the server
* need to have the config server details
* the application profile
* tenants configured
* command to execute which is wired as @Condition property
  * init-container
  * controller daemon

## For Init container mode
* (Max Wait time for the service to come up) The list of the services to watch -> deployments -> health endpoint
* Some form of data sync between multiple instances of service startup to identify deadlocks

## Notes
* The container needs additional privileges to invoke the k8s apis. Create a ClusterRoleBinding and target it to the iCargo service account 
  Eg :
  `kubectl create clusterrolebinding icargo-app-clsadm-crb --clusterrole=cluster-admin --serviceaccount=neo-ns:icargo-app-sa`