package contracts.update.cca

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		priority 1
		request {
			method('POST')
			headers {
				contentType(applicationJson())
				}
			body(file("createCCARequest.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
			"shipment_prefix": "134",
			"master_document_number": "40000000",
			"cca_ref_number": "CCI001111",
			"status_message": $((regex('.*')))
			])
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 2
		request {
			method('POST')
			headers {
				contentType(applicationJson())
			}
			body([
					"cca_number": "CCI001111",
					"shipment_prefix": "134",
					"master_document_number": "40000000"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("createdCCAResponse.json"))
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 3
		request {
			method('POST')
			headers {
				contentType(applicationJson())
				}
			body(file("updateCCARequest.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
				"shipment_prefix": "134",
				"master_document_number": "40000000",
				"cca_ref_number": "CCI001111",
				"status_message": $((regex('.*')))
            ])
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 4
		request {
			method('POST')
			headers {
				contentType(applicationJson())
			}
			body([
					"cca_number": "CCI001111",
					"shipment_prefix": "134",
					"master_document_number": "40000000"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("updatedCCAResponse.json"))
			headers {
				contentType(applicationJson())
			}
		}
	}
]
