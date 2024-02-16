## Installing Docker in Dev EC2 Machines for iCargo user icargoadm

- Install Docker and configure groups and users

```shell
sudo yum install docker
# For some unexplained reason the default group docker can`t be added for icargoadm user, hence changing this to dockerroot
sudo groupadd dockerroot
sudo usermod -aG dockerroot icargoadm

cat << EOF >> /etc/systemd/system/docker.socket.d/override.conf
[Socket]
SocketGroup=dockerroot
EOF

```

- Install IBS root certificates and IBS docker proxy certificates, 10.246.8.59 is the docker proxy
```shell
sudo curl http://10.246.8.59/ca.crt > /etc/pki/ca-trust/source/anchors/docker_registry_proxy.crt
sudo curl http://10.246.8.59/star_IBS-ROOT-CA.crt > /etc/pki/ca-trust/source/anchors/IBS-ROOT-CA.crt
sudo update-ca-trust force-enable
```

- Edit docker configurations to use proxy and tcc insecure registry
```shell
# Edit daemon.json to add the below entries
sudo cat << EOF >> /etc/docker/daemon.json
{
"insecure-registries" : ["192.168.49.43"],
"data-root": "/data/docker-lib",
"group": "dockerroot"
}
EOF

# Edit sysconfig/docker to configure the proxies
sudo cat << EOD >> /etc/sysconfig/docker
HTTP_PROXY=http://10.246.8.59:3128/
HTTPS_PROXY=http://10.246.8.59:3128/
NO_PROXY=192.168.49.43,*.ap-south-1.amazonaws.com,13.232.222.26
EOD
```

- Reload daemon, enable and restart
```shell
sudo systemctl daemon-reload
sudo systemctl restart docker
sudo systemctl status docker
sudo systemctl enable docker
```