## Steps to setup a new iCargo Neo K8s Environment

1. Configure IAM OIDC provider for your cluster https://docs.aws.amazon.com/eks/latest/userguide/enable-iam-roles-for-service-accounts.html
2. An Aws Loadbalancer controller policy has to be configured. Refer to document https://docs.aws.amazon.com/eks/latest/userguide/aws-load-balancer-controller.html
   Aws Ingress Controller needs to be installed irrespective of if alb is used in the application or not.
3. Install NginxIngressController ( Refer Document AWS_NginxIngressController.md )
4. Install Kubernetes Dashboard (Optional) https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
