package contracts.validatestockholdertypecode

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("validatestockholdertypecode0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockholdertypecode')),
                        producer("stock/av/private/v1/stock/validatestockholdertypecode")))
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
                body(file("validatestockholdertypecode1.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockholdertypecode')),
                        producer("stock/av/private/v1/stock/validatestockholdertypecode")))
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
                body(file("validatestockholdertypecode2.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockholdertypecode')),
                        producer("stock/av/private/v1/stock/validatestockholdertypecode")))
            }
            response {
                status 400
                body([
                        "errorGroup": "NEO_STOCK_011",
                        "error_code": "NEO_STOCK_011",
                        "error_description": "Invalid Stock Holder.",
                        "error_type": "ERROR",
                        "error_data": $((regex('.*')))
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
