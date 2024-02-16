package contracts.validateagentforstockholder

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body([
					"agentDetails": [
					[
						"companyCode":"IBS",
						"agentCode": "AGT678"
					]
			],
			"stockHolderCode":"STK678",
			"documentType":"AWB"
			])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validateagentforstockholder')),
                        producer("stock/av/private/v1/stock/validateagentforstockholder")))
            }
            response {
                status 204
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
                body([
					"agentDetails": [
					[
						"companyCode":"IBS",
						"agentCode": "AGENT"
					]
			],
			"stockHolderCode":"STK678",
			"documentType":"AWB"
			])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validateagentforstockholder')),
                        producer("stock/av/private/v1/stock/validateagentforstockholder")))
            }
            response {
                status 400
                body([
                        "error_code": "NEO_STOCK_020",
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
                body([
					"agentDetails": [
					[
						"companyCode":"IBS",
						"agentCode": "AGT678"
					]
			],
			"stockHolderCode":"HQK",
			"documentType":"AWB"
			])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validateagentforstockholder')),
                        producer("stock/av/private/v1/stock/validateagentforstockholder")))
            }
            response {
                status 204
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
                body([
					"agentDetails": [
					[
						"companyCode":"IBS",
						"agentCode": "AGT678"
					]
			],
			"stockHolderCode":"HQ",
			"documentType":"ABC"
			])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validateagentforstockholder')),
                        producer("stock/av/private/v1/stock/validateagentforstockholder")))
            }
            response {
                status 400
                body([
                        "error_code": "NEO_STOCK_020",
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
                body([
					"agentDetails": [
					[
						"companyCode":"IBS",
						"agentCode": "AGT678"
					]
			],
			"stockHolderCode":"HQ",
			"documentType":"AWB"
			])
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validateagentforstockholder')),
                        producer("stock/av/private/v1/stock/validateagentforstockholder")))
            }
            response {
                status 204
                headers {
                    contentType(applicationJson())
                }
            }
		}
		
]

