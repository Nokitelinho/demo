package contracts.calculatecca

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		priority 0
		request {
			method('POST')
			headers {
				contentType(applicationJson())
				}
			body(file("calculateAutoTotalTaxNoCcaRequest_0.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/calculatetaxes)")), producer('/cca/av/private/v1/cca/calculatetaxes')))
			}
		response {
			status 200
			body([
					"shipment_prefix"       : "134",
					"master_document_number": "39001100"
			])
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 1
		request {
			method('POST')
			headers {
				contentType(applicationJson())
				}
			body(file("calculateAutoTotalTaxYesCcaRequest_1.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/calculatetaxes)")), producer('/cca/av/private/v1/cca/calculatetaxes')))
			}
		response {
			status 200
			body([
					"shipment_prefix"       : "134",
					"master_document_number": "39001100",
					"auto_calculate_tax": "Y"
			])
			headers {
				contentType(applicationJson())
			}
		}
	}
]
