package contracts.validatenumericcode


import org.springframework.cloud.contract.spec.Contract

//[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatenumericcode/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/validatenumericcode/IBS/123")))
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
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatenumericcode/[a-zA-Z]*')),
//                        producer("stock/av/private/v1/stock/validatenumericcode/IBS/123")))
//            }
//            response {
//                status 400
//                body([
//                        "error_code"       : "shared.airline.invalidairline",
//                        "error_description": "shared.airline.invalidairline"
//                ])
//                headers {
//                    contentType(applicationJson())
//                }
//            }
//        }
//]