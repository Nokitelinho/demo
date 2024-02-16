## Steps to install IBM MQ Server in Kubernetes for Development Integration

- Download the IBM Charts by cloning the repository. The chart location for IBM MQ is 'stable/ibm-mqadvanced-server-dev'
```shell
git clone --depth 1 https://github.com/IBM/charts.git
```
- Create the namespace for installing IBM MQ.
```shell
kubectl create namespace ibm-mq
```
- Configure the kubernetes secrets, this will configure the passwords for the admin user and application user(for connecting to MQ)
```shell
kubectl create -f eks/ibm-mq/secret.yaml
```
The passwords configured in the file is 
```text
adminPassword: "aWJtLW1xLWFkbWluCg==" # ibm-mq-admin
appPassword: "aWJtLW1xLWFwcAo=" # ibm-mq-app
```

- Edit helm values file for customising the queue manager names etc. (eks/ibm-mq/values.yaml)
- Install the helm chart
```shell
helm install -f eks/ibm-mq/values.yaml --namespace ibm-mq icargo-neo stable/ibm-mqadvanced-server-dev
```

The service can be changed to Loadbalancer type if required to make it accessible externally.
The web management console is available in ```https://icargo-neo-ibm-mq:9443/ibmmq/console/login.html```

Connections Details (default)

```text
Queue Manager : ICARGONEOQM
Server Connection Channel : DEV.APP.SVRCONN
User : app
Password : ibm-mq-app
Host:Port (K8s Internal) : icargo-neo-ibm-mq.ibm-mq.svc.cluster.local:1414
```

Queues created through the console has to be edited to add `app` user to allow connecting to the queue. This is done by 
Selecting the queue/topic -> Actions -> View Configuration -> Security (tab) -> Add, Use user app and give all permissions.


## Steps to install IBM MQ Server in docker

- Download the image
```shell
docker pull ibmcom/mq:9.2.3.0-r1-amd64
```
- Configure the volume for storing MQ data (for os user icargoadm)
```shell
sudo mkdir /opt/ibmmq
sudo chown icargoadm:icargoadm /opt/ibmmq
mkdir /opt/ibmmq/data
docker volume create --name ibmmq_qm1data --opt type=none --opt device=/opt/ibmmq/data --opt o=bind
```
- Create a start script for starting the mq (start-mq.sh)
```shell
#!/usr/bin/env bash
docker run --detach --name ibm_mq_server --rm --volume ibmmq_qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --env LICENSE=accept --env MQ_QMGR_NAME=ICARGONEOQM --env MQ_ENABLE_METRICS=false --env MQ_ADMIN_PASSWORD=ibm-mq-admin --env MQ_APP_PASSWORD=ibm-mq-app ibmcom/mq:9.2.3.0-r1-amd64
```

- The container can be stopped by executing the below command
```shell
docker stop $(docker ps -qf 'name=ibm_mq_server')
```

- Sample systemd unit file (/etc/systemd/system/ibm-mq.service)
```text
[Unit]
Description=IBM MQ Server
After=docker.service
BindsTo=docker.service
ReloadPropagatedFrom=docker.service

[Service]
Type=forking
User=icargoadm
ExecStart=/usr/bin/docker run --detach --name ibm_mq_server --rm --volume ibmmq_qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --env LICENSE=accept --env MQ_QMGR_NAME=ICARGONEOQM --env MQ_ENABLE_METRICS=false --env MQ_ADMIN_PASSWORD=ibm-mq-admin --env MQ_APP_PASSWORD=ibm-mq-app ibmcom/mq:9.2.3.0-r1-amd64
ExecStop=/usr/bin/docker stop $(/usr/bin/docker ps -qf 'name=ibm_mq_server')
RestartSec=30
Restart=yes

[Install]
WantedBy=multi-user.target
```
```shell
sudo systemctl enable ibm-mq.service
```
