package contracts.findstockholdertypes

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findpriorities/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/findpriorities/NONAME")))
//                body(["HQ"])
//            }
//            response {
//                status 200
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findPriorities/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/findPriorities/AV")))
//            }
//            response {
//                status 500
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findpriorities/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/findpriorities/AV")))
//                body(["HQ"])
//            }
//            response {
//                status 200
//                body([
//                        "stockHolderCode"   : "HQ",
//                        "stockHolderType"   : "H",
//                        "priority"          : 1,
//                        "ignoreWarnings"    : false,
//                        "authorize_warnings": false
//                ])
//            }
//        }
]