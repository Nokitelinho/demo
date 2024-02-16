package contracts.findstockrequestdetails


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrequestdetails')),
                        producer("stock/av/private/v1/stock/findstockrequestdetails")))
                body([
                        "requestRefNumber": "ALLOCATE1005",
                        "companyCode"     : "AV"
                ])
            }
            response {
                status 200
                body(file("findStockRequestDetailsOkResponse.json"))
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrequestdetails')),
                        producer("stock/av/private/v1/stock/findstockrequestdetails")))
                body([
                        "companyCode": "INVALID"
                ])
            }
            response {
                status 200
                body(file("findStockRequestDetailsEmptyResponse.json"))
            }
        }
]