package contracts.findawbstockdetailsforprint

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findawbstockdetailsforprint')),
                        producer("stock/ibs/private/v1/stock/findawbstockdetailsforprint")))
                body(file("findAWBStockDetailsForPrint.json"))
            }
            response {
                status 200
                body([
                		[  
                                "stockHolderCode":"HQ",
                                "stockHolderName":"stkname",
                                "documentType":"AWB",
                                "airlineIdentifier":1191,
                                "startRange":"3000550",
                                "endRange":"3000648",
                                "numberOfDocuments":99
                        ]
                ])
            }
        }
]