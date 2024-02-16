# Neo Snow Adapter Helm Chart


## SNS
When using the sns notification function do the following pre-requisites

### AWS IAM
Find the OIDC Provider ID and edit below accordingly
Refer https://docs.aws.amazon.com/eks/latest/userguide/iam-roles-for-service-accounts-technical-overview.html

Note: To configure the IAM roles & policies you will need an AWS identity with the required privileges. 

#### Create a role to attach to the service account used by this snow adapter 
- Edit aws a/c id and oidc-provider-id as required
```shell
aws iam create-role --role-name icargo-system-sns-role --assume-role-policy-document \
'{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Federated": "arn:aws:iam::<aws-account-id>:oidc-provider/<oidc-provider-id>"
      },
      "Action": "sts:AssumeRoleWithWebIdentity",
      "Condition": {
        "StringEquals": {
          "<oidc-provider-id>:sub": "system:serviceaccount:icargo-system:neo-snow-adapter",
          "<oidc-provider-id>:aud": "sts.amazonaws.com"
        }
      }
    }
  ]
}'
```

#### Create a policy with the required SNS actions allowed
```shell
aws iam create-policy --policy-name icargo-system-sns-policy --policy-document \
'{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
            "sns:CreateTopic",
            "sns:ListTopics",
            "sns:ListSubscriptionsByTopic",
            "sns:SetTopicAttributes",
            "sns:GetTopicAttributes",
            "sns:RemovePermission",
            "sns:AddPermission",
            "sns:ListSubscriptions",
            "sns:ListTagsForResource",
            "sns:Unsubscribe",
            "sns:UntagResource",
            "sns:TagResource",
            "sns:SetSubscriptionAttributes",
            "sns:SetSMSAttributes",
            "sns:GetSMSAttributes",
            "sns:GetSubscriptionAttributes",            
            "sns:DeleteTopic",
            "sns:Publish",
            "sns:Subscribe"
            ],
      "Resource": "*"
    }
  ]
}'
```

Note the policy-arn from the output of the command above
#### Attach this policy to the role
- Edit arn as required
```shell
aws iam attach-role-policy --policy-arn "arn:aws:iam::xxxxx:policy/icargo-system-sns-policy" --role-name "icargo-system-sns-role"
```

#### Create Service Account
Provide aws account id 
- Edit arn as required
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  annotations:
    eks.amazonaws.com/role-arn: "arn:aws:iam::<aws-account-id>:role/icargo-system-sns-role"
  name: neo-snow-adapter
  namespace: icargo-system    

```


## Installation
The neo-snow-adapter k8s deployment mounts two config-maps that can be used to
 - customise the alert notification configurations
 - customise the mapping from prometheus alert names to relatable names

 The config-maps can be overridden by supplying a values-snow.yml file configured as below

 ```yaml
 snowAdapter:
  # Spring profile to be used. Appended to the config map mounted application.yml
  springProfile: dev

  ##Values to be over-ridden in the config map mounted application.yml
  configOverrides:
    #Example shown below.Edit as required
    notify:
      snow:
        enabled: false
        #Instance name of SNOW url https://<instance-name>.service-now.com
        instanceName: ibsplcdev 
        #Source name to be set in the SNOW event
        sourceName: 
        #Basic Auth for the SNOW Rest Ednpoing https://<instance-name>.service-now.com/api/global/em/jsonv2
        basic-auth:
          userId: 
          password: 
      aws:
        sns:
          enabled: true
          email:
            active:
              #default email dsitribution list (as csv) if no specific ones configured
              default:
              #<prom-alert-severity>:<emails as csv>
              #critical:
              #error:
              #warning:
              #info:
            inactive:
            # - list of alert names that should not be alerted
            # - <alert-name>
            # - <alert-name>  
 ```

```shell
    helm upgrade -i neo-snow-adaper <path to chart> -f values-snow.yml
```



### To Test
To test you can publish a sample alert event to the /receive end-point
```shell
echo  \
'
    {
    	"status": "firing",
        "commonLabels": {
            "alertname": "TargetDown",
            "job": "icargo-prod/auth-service-business-podmonitor-qf",
            "namespace": "icargo-prod",
            "prometheus": "prometheus/prometheus-kube-prometheus-prometheus",
            "severity": "warning"
        },
        "alerts" : [ {
            "status" : "firing",
            "labels" : {
            "alertname" : "TargetDown",
            "job" : "icargo-prod/auth-service-business-podmonitor-qf",
            "namespace" : "icargo-prod",
            "prometheus" : "prometheus/prometheus-kube-prometheus-prometheus",
            "severity" : "warning"
            },
            "annotations" : {
            "message" : "100% of the icargo-prod/auth-service-business-podmonitor-qf/ targets in icargo-prod namespace are down."
            },
            "startsAt" : "2021-03-28T14:46:13.576Z",
            "endsAt" : "0001-01-01T00:00:00Z"
        }
        ]
    }
 ' \
| http POST :8080/snow-adapter/api/alertmanager/receive
```

### To Test sending an alert to alertmanager and check if its being forwarded correctly to the alert receivers edit and run
```shell
./send_alert_to_mgr.sh
```