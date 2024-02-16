package contracts.monitorStock

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("deleteRequest.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockagentmappings')),
                        producer("stock/av/private/v1/stock/savestockagentmappings")))
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
                body(file("insertRequest.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockagentmappings')),
                        producer("stock/av/private/v1/stock/savestockagentmappings")))
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
                body(file("updateRequest.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockagentmappings')),
                        producer("stock/av/private/v1/stock/savestockagentmappings")))
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
                body(file("insertRequestWithError.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockagentmappings')),
                        producer("stock/av/private/v1/stock/savestockagentmappings")))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_015",
                        "error_description": "stockcontrol.defaults.duplicateagentfound",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
