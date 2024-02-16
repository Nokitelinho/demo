### Bitnami WordPress installation

1. Bitnami wordpress installation github repo
```text
https://github.com/helm/charts/tree/master/stable/wordpress
```
2. Edit wordpress-values.yaml to configure the mariadb connection details
3. Create namespace icargo-help in kubernetes
```shell
kubectl create ns icargo-help
```
4. Download the helm chart
```shell
helm repo add bitnami https://charts.bitnami.com/bitnami
```
5. Install the helm chart
```shell
helm install -n icargo-help -f wordpress-values.yaml icargo-wordpress bitnami/wordpress
```
6. Edit the service to add it as as an nlb, add the below annotations
```text
service.beta.kubernetes.io/aws-load-balancer-internal: true
service.beta.kubernetes.io/aws-load-balancer-type: nlb-ip
```
