To add a new Stub
-----

1. Edit src/etc/stubs-runner.conf and add the new stub details
2. Bump the image version in docker.sh and k8s.yml (container/image) files
3. Rebuild the image by running `docker.sh <container-registry>`
4. Update the ports in the deployment and service sections in k8s.yml  
5. Update the deployment by running `kubectl apply -f k8s.yml`


