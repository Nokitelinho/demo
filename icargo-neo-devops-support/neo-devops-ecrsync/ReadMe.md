# neo-devops-ecrsync

Utility that syncs images between two ECR registres, given a BOM file from the Release Manager app. Intended to be used
to replicate images from the Engg. ECR registry in ap-south-1 to an ECR registry provisioned in the AWS Account for the
customer. 

# Usage

```shell
java -jar /neo-devops-ecrsync-1.0.0-SNAPSHOT.jar <arguments as below>

usage: ecrsync
-d,--debug               Simulation. Does not do the actual sync
-f,--bom <arg>           Bom File yaml. E.g ./bom.yaml
-n,--no-delete           Never delete target image even if it exists
-s,--source <arg>        Source ECR Repository URL. E.g
141807520248.dkr.ecr.ap-south-1.amazonaws.com
-sfx,--sourcePfx <arg>   Prefix to be added to source Image.Will be
appended with a trailing/. E.g neoicargo
-t,--target <arg>        Target ECR Repository URL. E.g
612067252072.dkr.ecr.ap-southeast-2.amazonaws.com

-tfx,--targetPfx <arg>   Prefix to be added to target Image.Will be
appended with a trailing/. E.g neoicargo
-tifx,--targetImagePfx <arg>   Prefix to be added to target Image Name.
E.g saas_icargo_neo_
-v,--verbose             Turn on verbose logging
```

**Note:** When running from the command line make sure
1. AWS Credentials have been set-up for use by the AWS CLI
2. Have the docker-ecr-credential-helper installed. Refer [GitHub - awslabs/amazon-ecr-credential-helper: Automatically gets credentials for Amazon ECR on docker push/docker pull](https://github.com/awslabs/amazon-ecr-credential-helper)
3. (or) Have a docker config populated using aws ecr get-login-password to target registry. Refer [Using Amazon ECR with the AWS CLI - Amazon ECR](https://docs.aws.amazon.com/AmazonECR/latest/userguide/getting-started-cli.html#cli-authenticate-registry)


## Using with AWS Code-pipelines
To use with AWS code-pipeline copy the target jar to a S3 bucket and have the jar downloaded and executed in the buildspec yaml of
the codebuild project.

When used with AWS code-pipelines the IAM service role auto-created for the code-build project has to be assigned
with
1. the required IAM policy actions to allow ECR push/pull functions
2. S3 read only access

### Create an IAM policy with the required ECR actions
```shell
aws iam create-policy --policy-name icargo-system-ecr-policy --policy-document \
'{
    "Version": "2012-10-17",
    "Statement": [
        {
        	"Effect": "Allow",
			"Action": [
				"ecr:BatchCheckLayerAvailability",
				"ecr:BatchDeleteImage",
				"ecr:BatchGetImage",
				"ecr:CompleteLayerUpload",
				"ecr:DescribeImageScanFindings",
				"ecr:DescribeImages",
				"ecr:DescribeRegistry",
				"ecr:DescribeRepositories",
				"ecr:GetAuthorizationToken",
				"ecr:GetDownloadUrlForLayer",
				"ecr:InitiateLayerUpload",
				"ecr:ListImages",
				"ecr:ListTagsForResource",
				"ecr:PutImage",
				"ecr:StartImageScan",
				"ecr:TagResource",
				"ecr:UntagResource",
				"ecr:UploadLayerPart"
			],
 			"Resource": "*"	
        }
    ]
}'
```
Note down the Policy ARN obtained above
### Attach policy to the AWS code-build service role 
```shell
aws iam attach-role-policy --policy-arn <policy-arn from above> --role-name <codebuild-service-role>

```
### Attach AWS Managed policy for S3 read-only access to the AWS code-build service role
```shell
aws iam attach-role-policy --policy-arn "arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess" --role-name <codebuild-service-role>
```

## Have the code-pipeline get triggered by a commit to a AWS CodeCommit repository
Configure the source of the code-pipeline to be an AWS CodeCommit repository with the following structure
```shell
qf-dtd-ecrsync    <- A code repo per application (Application in Release Manager)
├── buildspec-sync.yml   <- the codebuild buildspec file used by the AWS code-pipleine
├── bom.yaml   <- BOM from Release Manager App
└── env.sh
```

## A sample buildspec yaml that can be used in the AWS code-build project is as below
```yaml
version: 0.2
phases:
  install:
    commands:
      - curl -sS -o docker-credential-ecr-login https://amazon-ecr-credential-helper-releases.s3.us-east-2.amazonaws.com/0.5.0/linux-amd64/docker-credential-ecr-login
      - chmod +x ./docker-credential-ecr-login    
      - export PATH=$PWD:$PATH   
      - mkdir app

  pre_build:
    commands:
      - echo "For $CODEBUILD_SRC_DIR"
      - ls -lF $CODEBUILD_SRC_DIR
      - . $CODEBUILD_SRC_DIR/env.sh      
      - echo "Download ${S3_URL_NEO_ECRSYNC_UTIL_JAR} from S3"
      - aws s3 cp ${S3_URL_NEO_ECRSYNC_UTIL_JAR} ./app/ --region ${AWS_REGION} --quiet
      - echo "Check fo Java"
      - java -version

  build:
    commands:
      - set 
      - echo "Starting sync from ${ECR_SOURCE_REGISTRY} to ${ECR_TARGET_REGISTRY}"
      - ls -l ./app
      - java -jar ./app/${NEO_ECRSYNC_UTIL_JAR} -v -s ${ECR_SOURCE_REGISTRY} -t ${ECR_TARGET_REGISTRY} -tfx ${ECR_TARGET_IMAGE_PFX} -f $CODEBUILD_SRC_DIR/${BOM}  ;retc=$?
      - exit $retc



  post_build:
    commands:
      - echo Build completed on `date`
```
The env.sh that the buildspec refers to is
```shell
#!/bin/bash
BOM="bom.yaml"
ECR_SOURCE_REGISTRY="141807520248.dkr.ecr.ap-south-1.amazonaws.com"
ECR_TARGET_REGISTRY="612067252072.dkr.ecr.ap-southeast-2.amazonaws.com"
ECR_TARGET_IMAGE_PFX="neoicargo"
NEO_ECRSYNC_UTIL_JAR="neo-devops-ecrsync-1.0.0-SNAPSHOT.jar"
S3_URL_NEO_ECRSYNC_UTIL_JAR="s3://qf-dtd-s3/${NEO_ECRSYNC_UTIL_JAR}"
AWS_REGION="ap-southeast-2"


echo "Deployment BOM       : ${BOM}"
echo "ECR Source Registry  : ${ECR_SOURCE_REGISTRY}"
echo "ECR Target Registry  : ${ECR_TARGET_REGISTRY}"
echo "ECR Target Image Pfx : ${ECR_TARGET_IMAGE_PFX}"
echo "S3 URL For Sync Util : ${S3_URL_NEO_ECRSYNC_UTIL_JAR}"
```