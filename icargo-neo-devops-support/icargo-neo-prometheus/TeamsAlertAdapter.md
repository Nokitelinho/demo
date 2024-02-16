## Microsoft Teams Webhook Integration

Microsoft teams channel can be configured as a prometheus alert destination. 

### Configure webhook for the teams channel.
A webhook url needs to be configured for the teams channel. Check the instructions https://docs.microsoft.com/en-us/microsoftteams/platform/webhooks-and-connectors/how-to/add-incoming-webhook

### Install teams msteams alert receiver in K8s

Refer parent project https://github.com/prometheus-msteams/prometheus-msteams and https://github.com/prometheus-msteams/prometheus-msteams/blob/master/chart/prometheus-msteams/README.md

```shell
# Add the repo
helm repo add prometheus-msteams https://prometheus-msteams.github.io/prometheus-msteams/
# Install the chart a sample config.yaml is provided below
helm upgrade --install prometheus-msteams --namespace icargo-system -f config.yaml prometheus-msteams/prometheus-msteams
```


```yaml
# config.yaml
---
replicaCount: 1
image:
  repository: quay.io/prometheusmsteams/prometheus-msteams
  tag: v1.5.0

connectors:
  # in alertmanager, this will be used as http://prometheus-msteams.icargo-system.svc.cluster.local:2000/alert
  - alert: 'https://outlook.office.com/webhook/xxxx/xxxx'
 #- foo: https://outlook.office.com/webhook/xxxx/xxxx

# extraEnvs is useful for adding extra environment variables such as proxy settings
extraEnvs:
#  HTTP_PROXY: http://corporateproxy:8080
#  HTTPS_PROXY: http://corporateproxy:8080
container:
  additionalArgs:
#    - -debug

# Enable metrics for prometheus operator
metrics:
  serviceMonitor:
    enabled: false 
    additionalLabels:
      release: prometheus # change this accordingly
    scrapeInterval: 30s

```

### Add the alert manager configurations in application and prometheus namespaces (kubectl create -f )

```yaml
# Alertmanager configuraion in prometheus ns, this is typically for infra alerts
apiVersion: monitoring.coreos.com/v1alpha1
kind: AlertmanagerConfig
metadata:
  name: "msteams-alert-config"
  namespace: "prometheus"
spec:
  route:
    groupBy: ['alertname']
    receiver: 'msteams-adapter'
  receivers:
    - name: 'msteams-adapter'
      webhookConfigs:
        - sendResolved: true
          url: "http://prometheus-msteams.icargo-system.svc.cluster.local:2000/alert"
          maxAlerts: 1
---
apiVersion: monitoring.coreos.com/v1alpha1
kind: AlertmanagerConfig
metadata:
  name: "msteams-alert-config"
  namespace: <provide the application namespace here>
spec:
  route:
    groupBy: ['alertname']
    receiver: 'msteams-adapter'
  receivers:
    - name: 'msteams-adapter'
      webhookConfigs:
        - sendResolved: true
          url: "http://prometheus-msteams.icargo-system.svc.cluster.local:2000/alert"
          maxAlerts: 1
```
