package contracts.findavailableranges

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findavailableranges')),
                        producer("stock/av/private/v1/stock/findavailableranges")))
                body(file("findAvailableRangesRequestBody.json"))
            }
            response {
                status 200
                body(file("findAvailableRangesOkResponse.json"))
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findavailableranges')),
                        producer("stock/av/private/v1/stock/findavailableranges")))
                body([
                        "companyCode": "WRONG",
                        "pageNumber": 1
                ])
            }
            response {
                status 200
                body(file("findAvailableRangesEmptyResponse.json"))
            }
        }
]