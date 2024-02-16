#!/usr/bin/env bash

name='sample-infra-service' #$RANDOM
# kubectl port-forward -n prometheus alertmanager-pxxxxxx 9093:9093
url='http://localhost:9093/api/v1/alerts'
namespace='prometheus' # The namespace from which the alert needs to be triggered

echo "firing up alert $name" 

# change url o
curl -XPOST $url -d "[{ 
	\"status\": \"firing\",
	\"labels\": {
		\"alertname\": \"$name\",
		\"namespace\": \"$namespace\",
		\"pod\": \"pod-$name\",
		\"service\": \"my-service\",
		\"severity\":\"warning\",
		\"instance\": \"$name.example.net\"
	},
	\"annotations\": {
		\"summary\": \"High latency is high!\",
		\"description\": \"High latency is high!\"
	},
	\"generatorURL\": \"http://prometheus.int.example.net/<generating_expression>\"
}]"

echo ""

echo "press enter to resolve alert"
read

echo "sending resolve"
curl -XPOST $url -d "[{ 
	\"status\": \"resolved\",
	\"labels\": {
		\"alertname\": \"$name\",
		\"service\": \"my-service\",
		\"namespace\": \"$namespace\",
		\"pod\": \"pod-$name\",
		\"severity\":\"warning\",
		\"instance\": \"$name.example.net\"
	},
	\"annotations\": {
		\"summary\": \"High latency is high!\",
		\"description\": \"High latency is high!\"
	},
	\"generatorURL\": \"http://prometheus.int.example.net/<generating_expression>\"
}]"

echo ""

