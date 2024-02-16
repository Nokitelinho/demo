package contracts.deletedocumentfromstock


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletedocumentfromstock')),
                        producer("stock/av/private/v1/stock/deletedocumentfromstock"))
                        body([
                        	    "documentNumber": "0000011",
    							"shipmentPrefix": "777"
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
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletedocumentfromstock')),
                        producer("stock/av/private/v1/stock/deletedocumentfromstock"))
                        body([
                        	    "documentNumber": "ABCDEFGH",
    							"shipmentPrefix": "777"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup": "NEO_STOCK_023",
                        "error_code": "NEO_STOCK_023",
                        "error_description": "AWB number not found in anystock",
                        "error_type": "ERROR",
                        "error_data": [
                                      "ABCDEFGH"
                        ]
                     ])
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletedocumentfromstock')),
                        producer("stock/av/private/v1/stock/deletedocumentfromstock"))
                        body([
                        	    "documentNumber": "0000011",
    							"shipmentPrefix" : "777",
   								"stockOwner":"ABINASH"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup": "NEO_STOCK_035",
        				"error_code": "NEO_STOCK_035",
        				"error_description": "Document Owner does not matched with Stockholder Code",
        				"error_type": "ERROR",
        				"error_data": [
            				"0000011",
            				"ABC"
        				]
                     ])
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/deletedocumentfromstock')),
                        producer("stock/av/private/v1/stock/deletedocumentfromstock"))
                        body([
                        	    "documentNumber": "0000011",
    							"shipmentPrefix" : "777",
    							"stockOwner":"ABINASH1",
    							"documentType":"AWB"
                        ])
            }
            response {
                status 400
                body([
                        "errorGroup": "NEO_STOCK_024",
        				"error_code": "NEO_STOCK_024",
        				"error_description": "Stock holder not found for agent",
        				"error_type": "ERROR",
        				"error_data": [
            				"0000011"
        				]
                     ])
            }
        }
        
]        