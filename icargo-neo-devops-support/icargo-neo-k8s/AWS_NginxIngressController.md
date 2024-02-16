# Pre- Requisites 
## VPC Considerations
Verify tagging requirements on VPC as mentioned here https://docs.aws.amazon.com/eks/latest/userguide/network_reqs.html

## AWS Load Balancer Controller
- Install the AWS Load Balancer Controller as detailed here https://docs.aws.amazon.com/eks/latest/userguide/aws-load-balancer-controller.html
- In summary the steps are
  1. Create an IAM OIDC provider (if it does not already exist)
  2. Create an IAM  policy for the AWS Load Balancer Controller that allows it to make calls to AWS APIs on your behalf
  3. Create an IAM role with this policy
  4. Create a service account called aws-load-balancer-controller in kube-system namespace and annotate the service account  with this role
  5. Install CRDs
  6. Install the AWS Load Balancer Controller using helm
**Note**: For Stepa 1-3 you would need help of the DC team to perform the steps

# Tips
1. When having multiple environments in a k8s cluster have these environments as different namespaces
2. Have one Nginx ingress controller corresponding to an environment (i.e watching a corresponding namespace)
3. Install Nginx ingress controller into its own namespace

# Installation
- Detailed steps here https://docs.nginx.com/nginx-ingress-controller/installation/installation-with-helm/



Example:
```
git clone https://github.com/nginxinc/kubernetes-ingress/
cd kubernetes-ingress;
git checkout release-1.11

 helm upgrade -i -n nginx-ingress --skip-crds --set controller.watchNamespace="icargo-stg" --set controller.ingressClass="nginx-stg" --set controller.setAsDefaultIngress=false --set controller.kind=deployment --set config.name=nginx-icargo-stg-config nginx-icargo-stg ./kubernetes-ingress/deployments/helm-chart -f values.yml 
```

Note:
- Install after cloning the repo. Use the branch release-1.11.3 [git checkout -b origin/release-1.11.3] (this has been tested to work with kubernetes versions  1.18)
- Use the values file in eks/nginx-ingress/
- Create the namespace object manually prior to doing the helm install. ( kubectl create -f ns.yml)
```
apiVersion: v1
kind: Namespace
metadata:
  name: "nginx-ingress"
  labels: # Labels are optional
    release: "icargo-stg"
```
- The default ingressClass is "nginx". When having multiple ingress controllers (for e.g for different env's that are co-located) have different values for these (for e.g nginx-stg in the example above). The Ingress resources should be installed with the corresponding class name in the property "ingressClassName"

```
apiVersion: networking.k8s.io/v1beta1 
kind: Ingress
metadata:
....

spec:
  ingressClassName: "nginx-stg"
```
- setAsDefaultIngress should be true only for one Ingress Controller 
- This ingress controller only watches a specific name-space for Ingress resources e.g icargo-stg in the example above 
- The values file defaults the deployment type to daemonset. However a daemonset can be used only if there is ONE ingress controller deployed on the cluster. Otherwise use a deployment. **Use  daemonset for production** 
