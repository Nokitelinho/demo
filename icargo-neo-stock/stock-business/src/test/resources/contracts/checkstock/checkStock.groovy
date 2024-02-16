//package contracts.checkstock
//
//
//import org.springframework.cloud.contract.spec.Contract
//
//[
//        Contract.make {
//            request {
//                method('POST')
//                headers {
//                    contentType 'multipart/mixed'
//                }
//                multipart([
//                        Map.of("0", "AV", "1", "HQ", "2", "AWB", "3", "S")
//                ])
//                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/checkstock')),
//                        producer("stock/av/private/v1/stock/checkstock"))
//            }
//            response {
//                status 204
//            }
//        }
//        Contract.make {
//            request {
//                method('POST')
//                headers {
//                    contentType(applicationJson())
//                }
//                body(file("deleteStockRequestBody1.json"))
//                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/checkstock')),
//                        producer("stock/av/private/v1/stock/checkstock"))
//            }
//            response {
//                status 400
//                body([
//                        "error_code"       : "NEO_STOCK_003",
//                        "error_description": "Stock not found."
//                ])
//            }
//        }
//]