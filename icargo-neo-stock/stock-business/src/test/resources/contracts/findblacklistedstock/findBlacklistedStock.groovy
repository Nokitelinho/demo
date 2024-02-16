//package contracts.findblacklistedstock
//
//import org.springframework.cloud.contract.spec.Contract
//
//[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findblacklistedstock')),
//                        producer("stock/av/private/v1/stock/findblacklistedstock")))
//            }
//            response {
//                status 200
//                body(file("findBlacklistedStockOkResponse.json"))
//            }
//        },
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findblacklistedstock')),
//                        producer("stock/av/private/v1/stock/findblacklistedstock")))
//            }
//            response {
//                status 204
//                body(file("findBlacklistedStockEmptyResponse.json"))
//            }
//        }
//]

