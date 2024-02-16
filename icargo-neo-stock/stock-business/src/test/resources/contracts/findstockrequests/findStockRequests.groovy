package contracts.findstockrequests

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrequests/1')),
//                        producer("stock/av/private/v1/stock/findstockrequests/1")))
//                body([
//                        "companyCode": "AV",
//                        "pageSize"   : 1
//                ])
//            }
//            response {
//                status 200
//                body(file("findStockRequestsOkResponse.json"))
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrequests/1')),
//                        producer("stock/av/private/v1/stock/findstockrequests/1")))
//                body([
//                        "companyCode": "WRONG",
//                        "pageSize"   : 2
//                ])
//            }
//            response {
//                status 200
//                body(file("findStockRequestsEmptyResponse.json"))
//            }
//        }
]