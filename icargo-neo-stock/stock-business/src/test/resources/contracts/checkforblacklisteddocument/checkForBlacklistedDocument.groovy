package contracts.checkforblacklisteddocument

//import org.springframework.cloud.contract.spec.Contract
//
//[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/checkforblacklisteddocument')),
//                        producer('stock/av/private/v1/checkforblacklisteddocument')))
//                body([
//
//                ])
//            }
//            response {
//                status 200
//                body([
//                        true
//                ])
//                headers {
//                    contentType(applicationJson())
//                }
//            }
//        }
//]