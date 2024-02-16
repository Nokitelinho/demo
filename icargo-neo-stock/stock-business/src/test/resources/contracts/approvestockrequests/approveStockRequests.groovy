package contracts.approvestockrequests

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/approvestockrequests')),
                        producer("stock/av/private/v1/stock/approvestockrequests")))
                body([
                        "companyCode":"AV",
                        "approverCode":"ALLOCATE4",
                        "stockRequests": [
                                ["companyCode":"AV",
                                 "requestRefNumber":"ALLOCATE1006",
                                 "airlineIdentifier":"1000"
                                ]
                        ]
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
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/approvestockrequests')),
                        producer("stock/av/private/v1/stock/approvestockrequests")))
                body([
                        "companyCode":"AV1",
                        "approverCode":"HQ1",
                        "stockRequests": [
                                ["companyCode":"AV",
                                 "requestRefNumber":"127-123456792",
                                 "airlineIdentifier":"1134"
                                ]
                        ]
                ])
            }
            response {
                status 400
                body(file("approveStockRequestsErrorResponse.json"))
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/approvestockrequests')),
                        producer("stock/av/private/v1/stock/approvestockrequests")))
                body([
                        "companyCode":"AV",
                        "approverCode":"ALLOCATE4",
                        "stockRequests": [
                                ["companyCode":"AV-test",
                                 "requestRefNumber":"ALLOCATE1006-test",
                                 "airlineIdentifier":"1000"
                                ]
                        ]
                ])
            }
            response {
                status 400
                body(file("approveStockRequestsErrorResponse2.json"))
            }
        }
]