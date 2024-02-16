package contracts.findstockholdertypes

import org.springframework.cloud.contract.spec.Contract

[
//        Contract.make {
//            request {
//                method(POST())
//                headers {
//                    contentType(applicationJson())
//                }
//                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/finddocumentdetails')),
//                        producer("stock/av/private/v1/stock/finddocumentdetails")))
//                multipart([
//                        companyCode      : named(
//                                name: value(consumer(regex(nonEmpty())), producer('0')),
//                                content: value(consumer(regex(nonEmpty())), producer('XX'))
//                        ),
//                        airlineIdentifier: named(
//                                name: value(consumer(regex(nonEmpty())), producer('1')),
//                                content: value(consumer(regex(nonEmpty())), producer('1003'))
//                        ),
//                        documentNumber   : named(
//                                name: value(consumer(regex(nonEmpty())), producer('2')),
//                                content: value(consumer(regex(nonEmpty())), producer('2000200'))
//                        )
//                ])
//            }
//            response {
//                status 200
//                body(file("finddocumentdetails.json"))
//
//            }
//        }
]