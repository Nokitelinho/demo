**Logstash helm chart to use with AWS Elastic Search Service**

- Build image using the Dockerfile
- Set the AWS ES VPC end-point & region in a values file
- if IAM security is enabled on the AWS ES domain set the property awsElasticSearch.useAmazonLogstashPlugin to true
- set properties awsAccessKeyId & awsSecretAccessKey


