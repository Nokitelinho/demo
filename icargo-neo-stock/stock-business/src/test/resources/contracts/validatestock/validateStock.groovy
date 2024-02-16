package contracts.validatestock

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		request {
			method(POST())
			headers {
				contentType(applicationJson())
			}
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stockcheck/[0-9]{3}/[0-9]{8}')),
					producer('stock/av/private/v1/stockcheck/134/94490922')))
			body([		
					"customer_code" : $(consumer(regex('.*')),producer("DHLCDG"))
			])
		}
		response {
			status 200
			body([
					"stockHolderCode": $(consumer("DHLCDG"),producer(regex('.*'))),
					"document_subtype": $(consumer("S"),producer(regex('[A-Z]{1}'))),
					"agent_details": [
						[
							"agentCode": $(consumer("DHLCDG"),producer(regex('.*'))),
							"stockCustomerCode": $(consumer("DHLCDG"),producer(regex('.*'))),
						]
					],
					"product_stocks": [
						[
							"product_code": $(consumer("GEN"),producer(regex('[A-Z]{3}'))),
							"product_name": $(consumer("GENERAL CARGO"),producer(regex('.*')))
						]
					]
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
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stockcheck/[0-9]{3}/[0-9]{8}')),
					producer("stock/av/private/v1/stockcheck/134/88893383")))
			body([
					"customer_code" : $(consumer(regex('.*')),producer("DHLCDG"))
			])
		}
		response {
			status 400
			body([
					"error_code": "AWB_BLACKLISTED",
					"error_description": $((regex('.*'))),
					"error_type": $((regex('.*'))),
					"error_data": $((regex('.*')))
			])
			headers {
				contentType(applicationJson())
			}
		}
	}
]