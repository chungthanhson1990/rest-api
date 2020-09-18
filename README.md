ASSUMPTION: 
1. All credentials (AWS Credentials, Docker, Git) are set on Jenkins
2. Code quality, code coverage can be monitored with JaCoCo, SonarQube or other similar tools.

VERSIONING:
Format for versioning would be 0.x.y.RELEASE for master prod branch.

DEPLOYMENT STEPS:
1. When a git commit is done, it will trigger a build from Jenkins.

2. Jenkins build the application (skip tests)

3. Jenkins run the unit & integration tests, if successful, will continue, otherwise, the build will fail.

4. Docker build the images.

5. Push to Docker.

6. Kubernetes cluster is provisioned by Terraform on AWS EKS. This cluster is deployed after a Docker build is successful and ready to be deployed.

7. After the Kubernetes cluster is deployed, Jenkins will apply the Deployment.yaml file to deploy the docker images to the cluster (replicaSet is set as 2).

RISKS:
As the pipeline will fail if any of the steps failed, hence, there might be discrepancies between the latest commit Sha and the current value that the API displays if the build failed.