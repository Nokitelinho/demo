package contracts.checkstock


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("blackListStock0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/blackliststock')),
                        producer('stock/av/private/v1/stock/blackliststock')))
            }
            response {
                status 204
            }
        }
]