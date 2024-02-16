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
					  "documentNumber": "2000001",
					  "documentType":"AWB",
					  "airlineIdentifier":1003
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 200
                body([
				        "documentType": "AWB",
					    "stockHolderCode": "HELLO",
					    "documentSubType": "S",
					    "documentNumber": "2000001",
					    "stockHolderType": "H",
					    "status": "INSTOCK",
					    "agentDetails": [
					        [
					            "agentCode": "AIEDXB",
					            "customerCode": "AIEDXB",
					            "agentName": "AIR EXPRESS INTERNATIONAL",
					            "authorize_warnings": false
					        ]
					    ],
					    "authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
                 	  "companyCode": "IBS",
					  "documentNumber": "2437000",
					  "documentType":"AWB",
					  "airlineIdentifier":1173
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
				   		"error_code": "NEO_STOCK_023",
						"error_description": "AWB number not found in anystock",
						"error_type": "ERROR"

                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
                 	  "documentNumber": "2000001",
					  "documentType":"AWB",
					  "airlineIdentifier":1003,
					  "stockOwner":"A1001"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
			   			"error_code": "NEO_STOCK_004",
						"error_description": "Stock Holder does not exist.",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544542",
					  "documentType":"AWB",
					  "shipmentPrefix":134,
					  "stockOwner":"DHLCDG"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 200
                body([
					"documentType": "AWB",
					"stockHolderCode": "VALIDATE",
					"documentSubType": "R",
					"documentNumber": "4544542",
					"agentDetails": [
					    [
					        "agentCode": "DHLCDG",
					        "customerCode": "DHLCDG",
					        "agentName": "DHL WORLDWIDE INTERNATIONAL",
					        "authorize_warnings": false
					    ]
					],
					"authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544542",
					  "documentType":"AWB",
					  "shipmentPrefix":134
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 200
                body([
					"documentType": "AWB",
					"stockHolderCode": "VALIDATE",
					"documentSubType": "R",
					"documentNumber": "4544542",
					"stockHolderType": "H",
					"status": "INSTOCK",
					"agentDetails": [
					    [
					        "agentCode": "AIEDXB",
					        "customerCode": "AIEDXB",
					        "agentName": "AIR EXPRESS INTERNATIONAL",
					        "authorize_warnings": false
					    ],
					    [
					        "agentCode": "DHLCDG",
					        "customerCode": "DHLCDG",
					        "agentName": "DHL WORLDWIDE INTERNATIONAL",
					        "authorize_warnings": false
					    ]
					],
					"authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544542",
					  "documentType":"AWB",
					  "shipmentPrefix":134,
					  "stockOwner":"DHLCDG1"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
			   			"error_code": "NEO_STOCK_024",
						"error_description": "Stock holder not found for agent",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544551",
					  "documentType":"AWB",
					  "airlineIdentifier":1777
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
			   			"error_code": "NEO_STOCK_004",
						"error_description": "Stock Holder does not exist.",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "3544551",
					  "documentType":"EBT",
					  "shipmentPrefix":134,
					  "stockOwner":"VALIDATE"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 200
                body([
			   			 "authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544551",
					  "documentType":"COU",
					  "shipmentPrefix":134,
					   "stockOwner":"VALIDATE"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 200
                body([
			   			"authorize_warnings": false
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "2544544",
					  "documentType":"INVOICE",
					  "shipmentPrefix":134,
					   "stockOwner":"VALIDATE"
					  
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
                		"error_code": "NEO_STOCK_003",
						"error_description": "Stock not found.",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "2212210",
					  "documentType":"AWB1",
					  "airlineIdentifier":1134
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
			   			"error_code": "NEO_STOCK_020",
						"error_description": "Already BlackListed Range.",
						"error_type": "ERROR"
                ])
               
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4784786",
					  "documentType":"EBT",
					  "shipmentPrefix":134,
					   "stockOwner":"VALIDATE"
					  
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
                		"error_code": "NEO_STOCK_030",
						"error_description": "EBT not found in any stock",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4784786",
					  "documentType":"COU",
					  "shipmentPrefix":134,
					   "stockOwner":"VALIDATE"
					  
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
                		"error_code": "NEO_STOCK_028",
						"error_description": "courier not found in any stock",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "3544551",
					  "documentType":"EBT",
					  "shipmentPrefix":134,
					  "stockOwner":"VALIDATE1"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
               			"error_code": "NEO_STOCK_031",
						"error_description": "EBT not available with stockholder",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },  Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					  "documentNumber": "4544551",
					  "documentType":"COU",
					  "shipmentPrefix":134,
					   "stockOwner":"VALIDATE1"
                ])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatedocument')),
                        producer("stock/av/private/v1/stock/validatedocument")))
            }
            response {
                status 400
                body([
			   		    "error_code": "NEO_STOCK_029",
						"error_description": "courier not available with stockholder",
						"error_type": "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
