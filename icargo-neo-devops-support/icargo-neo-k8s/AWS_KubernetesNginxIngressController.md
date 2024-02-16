# Kubernetes Nginx Ingress Controller

Kubernetes Nginx Ingress controller is a kubernetes managed ingress controller based on nginx proxy. This is a more feature
rich controller supporting advanced functionalities like blue green and canary deployments.

## Project Home
[Project Home](https://github.com/kubernetes/ingress-nginx/)

## Documentation 
- [Documentation Root](https://kubernetes.github.io/ingress-nginx/)
- [Annotations Reference](https://kubernetes.github.io/ingress-nginx/user-guide/nginx-configuration/annotations/)


## Installation
```shell
curl -XGET https://github.com/kubernetes/ingress-nginx/releases/download/helm-chart-4.2.0/ingress-nginx-4.2.0.tgz -o ingress-nginx-4.2.0.tgz
tar -xzf ingress-nginx-4.2.0.tgz
helm install -n k8s-nginx-nfr -f icargo-neo-k8s/eks/nginx-ingress/k8s-ingress-nginx.yml --set controller.ingressClassResource.name=k8s-nginx-nfr --set controller.kind=Deployment --set controller.scope.namespace=nfr icargo-k8s-nginx ingress-nginx/ingress-nginx
```

### Notes
- Tweak the values above to change the ingress controller deployment namespace, ingress class name and watched namespace
- Use controller.kind = DaemonSet ; for production deployments
- Edit k8s-ingress-nginx.yml to enable metrics and prometheus integration

### Edit namespace to add the below labels for prometheus scrapping.

```yaml
apiVersion: v1
kind: Namespace
metadata:
  creationTimestamp: "2022-03-30T06:31:09Z"
  labels:
    kubernetes.io/metadata.name: nfr
    release: icargo-nfr
    tenant: av
    # The labels to be added for scarping to be enabled.
    alertmonitor: enabled
    podmonitor: enabled
    probemonitor: enabled
    rulemonitor: enabled
    servicemonitor: enabled
  name: nfr
```

### Grafana Dashboards
```shell
curl -XGET https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/grafana/dashboards/nginx.json -o k8s-nginx-ingress.json
curl -XGET https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/grafana/dashboards/request-handling-performance.json -o k8s-nginx-ingress-request-handling.json

```