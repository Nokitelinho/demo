package contracts.deletestock


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([])
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestock')),
                        producer("stock/av/private/v1/stock/deletestock"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("deleteStockRequestBody1.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestock')),
                        producer("stock/av/private/v1/stock/deletestock"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_006",
                        "error_description": "Range {0} not found.",
                        "error_type"       : "ERROR"
                ])
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("deleteStockRequestBody2.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestock')),
                        producer("stock/av/private/v1/stock/deletestock"))
            }
            response {
                status 204
            }
        }
]