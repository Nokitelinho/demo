package contracts.findnextdocumentnumber

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		request {
			method(POST())
			headers {
				contentType(applicationJson())
			}
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/nextawb/')),
					producer("stock/av/private/v1/stock/nextawb/")))
			body([		
						"company_code":"AV",
					 	"airline_identifier":1134,
					 	"stock_owner":"AIEDXB",
					 	"awb_destination":"DXB",
					 	"awb_origin":"CDG"		
			])
		}
		response {
			status 200
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
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/nextawb/')),
					producer("stock/av/private/v1/stock/nextawb/")))
			body([		
						"company_code":"AV",
					 	"airline_identifier":1000,
					 	"stock_owner":"AIEDXB",
					 	"awb_destination":"DXB",
					 	"awb_origin":"CDG"		
			])
		}
		response {
			status 400
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
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/nextawb/')),
					producer("stock/av/private/v1/stock/nextawb/")))
			body([		
						"company_code":"AV",
					 	"airline_identifier":0000,
					 	"stock_owner":"AIEDXB",
					 	"awb_destination":"DXB",
					 	"awb_origin":"CDG"		
			])
		}
		response {
			status 500
			headers {
				contentType(applicationJson())
			}
		}
	}

]