package contracts.monitorStock

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("monitorStock0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/monitorstock')),
                        producer("stock/av/private/v1/stock/monitorstock")))
            }
            response {
                status 200
                body(file("monitorStock3.json"))
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
                body(file("monitorStock1.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/monitorstock')),
                        producer("stock/av/private/v1/stock/monitorstock")))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_011",
                        "error_description": "Invalid Stock Holder.",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
