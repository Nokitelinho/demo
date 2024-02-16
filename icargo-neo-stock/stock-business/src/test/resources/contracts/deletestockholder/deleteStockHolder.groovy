package contracts.deletestock


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestockholder')),
                        producer("stock/av/private/v1/stock/deletestockholder"))
                        body([
                        	    "companyCode": "IBS",
    							"stockHolderCode": "VIMMY"
                        ])
            }
            response {
                status 204
            }
        },
       Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestockholder')),
                        producer("stock/av/private/v1/stock/deletestockholder"))
                        body([
                        	    "companyCode": "AV",
    							"stockHolderCode": "VALIDATE"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup":"NEO_STOCK_032",
                        "error_code":"NEO_STOCK_032",
                        "error_description":"Agent mapping exists for stockholder",
                        "error_type":"ERROR"
                     ])
            }
        },
       Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestockholder')),
                        producer("stock/av/private/v1/stock/deletestockholder"))
                        body([
                        	    "companyCode": "IBS",
    							"stockHolderCode": "AGT64"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup":"NEO_STOCK_033",
                        "error_code":"NEO_STOCK_033",
                        "error_description":"Stock Holder is an approver",
                        "error_type":"ERROR"
                     ])
            }
        },
       Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestockholder')),
                        producer("stock/av/private/v1/stock/deletestockholder"))
                        body([
                        	    "companyCode": "AV",
    							"stockHolderCode": "ALLOCATE5"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup":"NEO_STOCK_034",
                        "error_code":"NEO_STOCK_034",
                        "error_description":"Range exists for stock holder",
                        "error_type":"ERROR"
                     ])
            }
        },
       Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletestockholder')),
                        producer("stock/av/private/v1/stock/deletestockholder"))
                        body([
                        	    "companyCode": "AV",
    							"stockHolderCode": "VALIDATE1"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup":"NEO_STOCK_004",
                        "error_code":"NEO_STOCK_004",
                        "error_description":"Stock Holder does not exist.",
                        "error_type":"ERROR"
                     ])
            }
        }
]