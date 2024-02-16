package contracts.validatestockholders

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockholders/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/validatestockholders/AV")))
//                body(["HQ", "VB"])
//            }
//            response {
//                status 204
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockholders/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/validatestockholders/AV")))
//                body(["HQ", "VB", "VB2"])
//            }
//            response {
//                status 400
//                body([
//                        "error_code"       : "NEO_STOCK_011",
//                        "error_description": $((regex('.*'))),
//                        "error_type"       : $((regex('.*'))),
//                        "error_data"       : $((regex('.*')))
//                ])
//                headers {
//                    contentType(applicationJson())
//                }
//            }
//        }
]