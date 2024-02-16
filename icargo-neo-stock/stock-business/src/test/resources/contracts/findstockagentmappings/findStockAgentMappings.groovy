package contracts.findstockagentmappings

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockagentmappings')),
                        producer("stock/av/private/v1/stock/findstockagentmappings")))
                body([
                        "companyCode": "AV",
                        "agentCode":"T1001",
                        "pageNumber":1
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
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockagentmappings')),
                        producer("stock/av/private/v1/stock/findstockagentmappings")))
                body([
                        "companyCode": "WRONG",
                        "pageNumber":1
                ])
            }
            response {
                status 200
                body(file("emptyResponse.json"))
            }
        }
]