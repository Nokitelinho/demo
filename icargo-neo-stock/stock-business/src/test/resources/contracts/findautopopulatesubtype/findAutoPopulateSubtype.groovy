package contracts.findautopopulatesubtype

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findautopopulatesubtype')),
                        producer("stock/av/private/v1/stock/findautopopulatesubtype")))
                body([
                
                	"companyCode": "IBS",
    				"stockOwner": "AGT345",
    				"documentType": "AWB"
                ])
            }
            response {
                status 200
                body('S')
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findautopopulatesubtype')),
                        producer("stock/av/private/v1/stock/findautopopulatesubtype")))
                body([
                
                	"companyCode": "IBS",
    				"stockOwner": "AGT34",
    				"documentType": "AWB"
                ])
            }
            response {
                status 400
                body([
                        "error_code": "NEO_STOCK_021",
        				"error_description": "Invalid Stock Holder For Agent.",
        				"error_type": "ERROR"
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
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findautopopulatesubtype')),
                        producer("stock/av/private/v1/stock/findautopopulatesubtype")))
                body([
                
                	"companyCode": "IB",
    				"stockOwner": "AGT345",
    				"documentType": "AWB"
                ])
            }
            response {
                status 400
                body([
                        "error_code": "NEO_STOCK_021",
        				"error_description": "Invalid Stock Holder For Agent.",
        				"error_type": "ERROR"
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
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findautopopulatesubtype')),
                        producer("stock/av/private/v1/stock/findautopopulatesubtype")))
                body([
                
                	"companyCode": "IBS",
    				"stockOwner": "AGT345",
    				"documentType": "INVOICE"
                ])
            }
            response {
                status 204
            }
        }
]