# Prometheus Stack
Installation Steps for
1.  [kube-prometheus-stack](https://github.com/prometheus-community/helm-charts/tree/main/charts/kube-prometheus-stack)
2. iCargo Neo Service Now Adapter Application
3. Default AlertManager Configuration

## Install kube-prometheus-stack
a. Create namespace with required labels
```
    kubectl apply -f manifests/namespace.yaml
```
b. Install kube-prometheus chart
```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm -n prometheus install prometheus-release prometheus-community/kube-prometheus-stack -f values-prometheus.yml --set grafana.adminPassword=<set password>
```
grafana.adminPassword is used to set the password for the "admin" user of the grafana web-ui
**Dependencies**
By default this chart installs additional, dependent charts:

- kubernetes/kube-state-metrics
- prometheus-community/prometheus-node-exporter
- grafana/grafana

### Storage
- the prometheus deployment has a volumeClaimTemplate with a size of 60g specified (Refer Prom-Storage-Sizing.xls for sizing guidelines)
- on AWS EKS this will create an EBS volume of this size
- set the property prometheus.volumeClaimTemplate.spec.resources.requests.storage to override
```
e.g: set as 30G
helm -n prometheus install prometheus-release... --set prometheus.volumeClaimTemplate.spec.resources.requests.storage=30Gi
```
- Grafana & Alert Manager have volumeClaimTemplates with a size of 5G each specified
### Access
By default the installation configures AWS NLBs for access to Grafana, Prometheus & AlertManager web-uis. This behaviour can be overridden ny setting the following properties during the helm install
```
--set prometheus.service.type=ClusterIP
--set alertmanager.service.type=ClusterIP
--set grafana.service.type=ClusterIP
```
## Install the neo-snow-adapter
Install using the helm chart provided in ./neo-snow-adapter
- Provide a values file with the following properties configured as required
```yaml
snowAdapter:
  springProfile: dev # The environment profile
  configOverrides:
    snow:
      #Instance name of SNOW url https://<instance-name>.service-now.com
      instanceName: ibsplcdev 
      #Node name to be set in the SNOW event
      nodeName: 
      #Source name to be set in the SNOW event
      sourceName: 
      #Basic Auth for the SNOW Rest Ednpoing https://<instance-name>.service-now.com/api/global/em/jsonv2
      basic-auth:
        userId: 
        password: 
  mappingsOverrides:
    snow:      
      type-mappings:
        #Altername in Prometheus (As defined in the Prom Rule) to Type name as to be mapped in SNOW
        TargetDown : 'Availability'
```
```
    helm install neo-snow-adapter ./neo-snow-adapter -f <values-file-dev.yml>
```
**Note:** The snow adapter configs are set-up as  ConfigMaps (by default called neo-snow-adapter-app-configmap & neo-snow-adapter-mappings-configmap) in the namespace icargo-system. This can be tweaked later as required by editing the configmap
```shell
kubectl -n icargo-system edit ConfigMap neo-snow-adapter-mappings-configmap
```
## Tweak AlertManagerConfigs
- By default two AlertManagerConfigs are installed - one in the prometheus namespace and another in the iCargo Neo application namespace (property prometheus.enabled should be set to true when installing using icargo-neo-helm. By default it is false)
- These configs by default have the neo-snow-adapter as the sole alert receiver
- You can edit these configs to add other additional alert receivers (e.g email) 
```shell
kubectl -n icargo-prod edit AlertManagerConfig icargo-prod-alert-config
and
kubectl -n prometheus edit AlertManagerConfig infra-alert-config
```
For example to add an email receiver for all application alerts (editing AlertManagerConfig in the application namespace)
```yaml
...
spec:
  route:
    #Set a default email receiver
    receiver: 'ibs-helpdesk-emails'
  receivers:
    - name: 'ibs-helpdesk-emails'
        # email configuration attributes 
        emailConfigs:
        - requireTLS: false
            sendResolved: true
            smarthost: 'UNKOWN_HOST:25'
            to: 'changeme@ibsplc.com' 
            authIdentity:
            authPassword:
            authSecret:
            authUsername:
```
- Refer [crd-alertmanagerconfigs](https://github.com/prometheus-community/helm-charts/blob/main/charts/kube-prometheus-stack/crds/crd-alertmanagerconfigs.yaml) for the available configuration attributes
