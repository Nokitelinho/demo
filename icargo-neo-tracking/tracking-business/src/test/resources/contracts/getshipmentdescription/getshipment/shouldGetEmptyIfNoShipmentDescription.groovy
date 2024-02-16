package contracts.getshipmentdescription.getshipment

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description('Should return empty if no shipment details by AWB\'s')
    request {
        method('GET')
        headers {
            contentType(applicationJson())
        }
        urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments)")), producer('/tracking/av/private/v1/shipments'))) {
            queryParameters {
                parameter 'awb': value(consumer(matching("w+-{1}w+")), producer("111-11111111"))
            }
        }
    }
    response {
        status 200
        body([[]])
        headers {
            contentType(applicationJson())
        }
    }
    priority 1
}

Contract.make {
    description('Should return empty if no shipment details by AWB\'s')
    request {
        method('GET')
        headers {
            contentType(applicationJson())
        }
        urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments)")), producer('/tracking/av/private/v1/shipments'))) {
            queryParameters {
                parameter 'awb': value(consumer(matching("[0-9]+")), producer(null))
            }
        }
    }
    response {
        status 200
        body([[]])
        headers {
            contentType(applicationJson())
        }
    }
    priority 1
}
