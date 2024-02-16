package contracts.findcustomerstockdetails

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		request {
			method(POST())
			headers {
				contentType(applicationJson())
			}
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findcustomerstockdetails')),
					producer("stock/av/private/v1/stock/findcustomerstockdetails")))
			body([		
						"companyCode":"IBS",
						"customerCode":"CARGOMST"
					 			
			])
		}
		response {
			status 200
			body(file("findCustomerStockDetailsResponse.json"))
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
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findcustomerstockdetails')),
					producer("stock/av/private/v1/stock/findcustomerstockdetails")))
			body([		
						"companyCode":"IBS",
						"customerCode":"CARGOMS"
					 			
			])
		}
		response {
			status 200
			body([
                        "error_code": "NEO_STOCK_004",
        				"error_description": "Stock Holder does not exist.",
        				"error_type": "ERROR"
                ])
			headers {
				contentType(applicationJson())
			}
		}
	}
]