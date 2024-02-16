package contracts.findstockagentmappings

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/getstockagentmappings')),
                        producer("stock/av/private/v1/stock/getstockagentmappings")))
                body([
                        "companyCode": "AV",
                        "stockHolderCode":"VALIDATE"
                ])
            }
            response {
                status 200
                body(file("okResponse.json"))
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/getstockagentmappings')),
                        producer("stock/av/private/v1/stock/getstockagentmappings")))
                body([
                        "companyCode": "WRONG"
                ])
            }
            response {
                status 200
                body([])
            }
        }
]