package contracts.findstockholdertypes

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholdertypes/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/findstockholdertypes/NONAME")))
//            }
//            response {
//                status 200
//                body([])
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholdertypes/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/findstockholdertypes/DNAU")))
//            }
//            response {
//                status 200
//                body([
//                        "companyCode"       : "DNAU",
//                        "stockHolderType"   : "H",
//                        "priority"          : 1,
//                        "ignoreWarnings"    : false,
//                        "authorize_warnings": false
//                ])
//            }
//        }
]