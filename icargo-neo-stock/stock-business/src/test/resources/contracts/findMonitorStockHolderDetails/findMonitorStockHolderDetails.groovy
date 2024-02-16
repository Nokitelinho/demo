package contracts.findMonitorStockHolderDetails

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("findMonitorStockHolder0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findmonitoringstockholderdetails')),
                        producer("stock/av/private/v1/stock/findmonitoringstockholderdetails")))
            }
            response {
                status 200
                body([
                        "stockHolderCode": "ALLOCATE4",
                        "documentType": "AWB",
                        "documentSubType": "S",
                        "availableStock": 0,
                        "allocatedStock": 0,
                        "requestsReceived": 0,
                        "requestsPlaced": 60,
                        "phyAllocatedStock": 0,
                        "phyAvailableStock": 0,
                        "manAllocatedStock": 0,
                        "manAvailableStock": 0,
                        "stockHolderType": "H",
                        "ignoreWarnings": false,
                        "authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("findMonitorStockHolder1.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findmonitoringstockholderdetails')),
                        producer("stock/av/private/v1/stock/findmonitoringstockholderdetails")))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_011",
                        "error_description": "Invalid Stock Holder.",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
