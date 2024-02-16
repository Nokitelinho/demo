package contracts.findstockholderlov

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
                 	"pageNumber":1,
				    "companyCode":"IBS",
				    "stockHolderType":"S"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholders')),
                        producer("stock/av/private/v1/stock/findstockholders")))
            }
            response {
                status 200
                body(file("findStockHolders_Response_1.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        }, Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
                 	"pageNumber":1,
				    "companyCode":"IBS",
				    "stockHolderType":"H",
				    "stockHolderCode":"TEST4"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholders')),
                        producer("stock/av/private/v1/stock/findstockholders")))
            }
            response {
                status 200
                body(file("findStockHolders_Response_2.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
