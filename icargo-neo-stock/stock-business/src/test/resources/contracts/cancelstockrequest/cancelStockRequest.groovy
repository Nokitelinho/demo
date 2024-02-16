package contracts.cancelstockrequest

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/cancelstockrequest')),
                        producer("stock/av/private/v1/stock/cancelstockrequest")))
                body([
                        "companyCode"      : "AV",
                        "requestRefNumber" : "ALLOCATE1002",
                        "airlineIdentifier": "1000"
                ])
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/cancelstockrequest')),
                        producer("stock/av/private/v1/stock/cancelstockrequest")))
                body([
                        "companyCode"      : "INVALID",
                        "requestRefNumber" : "128-00000000",
                        "airlineIdentifier": "2000"
                ])
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_007",
                        "error_description": "Stock request not found."
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/cancelstockrequest')),
                        producer("stock/av/private/v1/stock/cancelstockrequest")))
                body([
                        "requestRefNumber" : "128-00000000"
                ])
            }
            response {
                status 500
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/cancelstockrequest')),
                        producer("stock/av/private/v1/stock/cancelstockrequest")))
                body([
                        "companyCode"      : "AV",
                        "requestRefNumber" : "127-3333333",
                        "airlineIdentifier": "1001"
                ])
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_019",
                        "error_description": "The status of the selected request is not NEW is displayed. Cancelation is not completed."
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]