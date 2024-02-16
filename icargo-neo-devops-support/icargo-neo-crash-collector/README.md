## Permissions to access S3 Buckets

The crash-collector pod requires read write permissions on the S3 bucket. This is given to the service account in which
the crash-collector is deployed. 

Following are the options to give permission.

1. The below command uses [eksctl](https://eksctl.io/usage/iamserviceaccounts/) to overwrite the default service account for icargo-system namespace, the IAM S3 policy has to be created prior. The crash-collector pod has to be restarted afterwards for the change to take effect.
```shell
eksctl create iamserviceaccount --cluster=<cluster-name> --namespace=icargo-system --name=default --attach-policy-arn=arn:aws:iam::<AWS_ACCOUNT_ID>:policy/S3_Policy_Name --override-existing-serviceaccounts --approve
```

2. To restart the daemonset 
```shell
kubectl rollout restart daemonset -n icargo-system icargo-neo-crash-collector
```
