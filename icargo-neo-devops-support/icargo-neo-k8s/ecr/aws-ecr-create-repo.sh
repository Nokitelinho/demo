#!/bin/bash
REPOSITORY_NAME=$1
REGION="ap-south-1"

if [[ ${REPOSITORY_NAME} == "" ]]; then
        echo "Usage $0 <repository-name>"
        exit 1
fi

#aws ecr delete-repository --repository-name ${repo}

aws ecr create-repository --repository-name ${REPOSITORY_NAME} --image-tag-mutability MUTABLE --image-scanning-configuration scanOnPush=true --encryption-configuration encryptionType=AES256 --region ${REGION} \
 --tags '[ { "Key": "Owner", "Value": "Shinu K John" }, { "Key": "CostCenter", "Value": "iCargo" }, { "Key": "Environment", "Value": "IT-DEV" }, { "Key": "Name", "Value": "iCargo Neo ECR" }, { "Key": "Project", "Value": "iCargo-Neo-Development" } ]'

cat > $$-repo-policy.json<<EOT
{
  "Version": "2008-10-17",
  "Statement": [
    {
      "Sid": "Read_Only_All_IBS",
      "Effect": "Allow",
      "Principal": "*",
      "Action": [
        "ecr:BatchCheckLayerAvailability",
        "ecr:BatchGetImage",
        "ecr:DescribeImages",
        "ecr:DescribeRepositories",
        "ecr:GetDownloadUrlForLayer",
        "ecr:GetRepositoryPolicy",
        "ecr:ListImages"
      ],
      "Condition": {
        "StringEquals": {
          "aws:PrincipalOrgID": "o-8b6wknm0ed"
        }
      }
    }
  ]
}
EOT

aws ecr set-repository-policy \
    --repository-name ${REPOSITORY_NAME} --region ${REGION} \
    --policy-text "file://$$-repo-policy.json"

rm -f $$-repo-policy.json

cat > $$-repo-lifecycle.json<<EOT
{
  "rules": [
    {
      "rulePriority": 1,
      "description": "keep last 20 images",
      "selection": {
        "tagStatus": "any",
        "countType": "imageCountMoreThan",
        "countNumber": 20
      },
      "action": {
        "type": "expire"
      }
    }
  ]
}
EOT

aws ecr put-lifecycle-policy --region ${REGION} \
    --repository-name ${REPOSITORY_NAME} \
    --lifecycle-policy-text "file://$$-repo-lifecycle.json"

rm -f $$-repo-lifecycle.json
