package contracts.findapprovercode

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method('POST')
//                headers {
//                    contentType(applicationJson())
//                }
//                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findapprovercode/IBS/APAC/1172/AWB/S')),
//                        producer("stock/av/private/v1/stock/findapprovercode/IBS/APAC/1172/AWB/S"))
//            }
//            response {
//                status 200
//                headers {
//                    contentType(applicationJson())
//                }
//                body("HQ")
//            }
//        },
//        Contract.make {
//            request {
//                method('POST')
//                headers {
//                    contentType(applicationJson())
//                }
//                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findapprovercode/IBS/NONAME/1172/AWB/S')),
//                        producer("stock/av/private/v1/stock/findapprovercode/IBS/NONAME/1172/AWB/S"))
//            }
//            response {
//                status 204
//            }
//        },
//        Contract.make {
//            request {
//                method('POST')
//                headers {
//                    contentType(applicationJson())
//                }
//                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findapprovercode/IBS/APAC/1172/AWB/')),
//                        producer("stock/av/private/v1/stock/findapprovercode/IBS/APAC/1172/AWB/"))
//            }
//            response {
//                status 500
//                headers {
//                    contentType(applicationJson())
//                }
//            }
//        }
]