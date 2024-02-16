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
			body(file("saveCCAMarketAndIataRates.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
			"shipment_prefix": "134",
			"master_document_number": "40000001",
			"cca_ref_number":$((regex('.*'))),
			"status_message":$((regex('.*')))
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
					"cca_number":"CCI000001",
					"shipment_prefix":"134",
					"master_document_number":"40000001"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("savedCCAResponse.json"))
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
			body(file("updateCCAMarketAndIataRates.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
				"shipment_prefix": "134",
				"master_document_number": "40000001",
				"cca_ref_number":$((regex('.*'))),
				"status_message":$((regex('.*')))
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
					"cca_number":"CCI000001",
					"shipment_prefix":"134",
					"master_document_number":"40000001"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("updatedCCAMarketAndIataRatesResponse.json"))
			headers {
				contentType(applicationJson())
			}
		}
	}
]