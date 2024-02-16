package contracts.savestockrequestdetails


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("updateStockRequestNotFoundStockRequest.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockrequestdetails')),
                        producer("stock/av/private/v1/stock/savestockrequestdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_007",
                        "error_description": "Stock request not found.",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("updateStockRequest.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockrequestdetails')),
                        producer("stock/av/private/v1/stock/savestockrequestdetails"))
            }
            response {
                status 200
                body('127-3333333')
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveStockRequestNotFoundStock.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockrequestdetails')),
                        producer("stock/av/private/v1/stock/savestockrequestdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_003",
                        "error_description": "Stock not found.",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveStockRequest.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockrequestdetails')),
                        producer("stock/av/private/v1/stock/savestockrequestdetails"))
            }
            response {
                status 200
                headers {
                    contentType(applicationJson())
                }
            }
        }
]