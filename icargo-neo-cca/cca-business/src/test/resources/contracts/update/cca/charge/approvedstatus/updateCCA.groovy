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
			body(file("saveCCAApprovedRequest.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
			"shipment_prefix": "134",
			"master_document_number": "10000000",
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
					"master_document_number":"10000000"
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
			body(file("updateCCAApprovedStatusRequest.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
			body([
				"shipment_prefix": "134",
				"master_document_number": "10000000",
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
					"master_document_number":"10000000"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("updatedCCAApprovedStatusResponse.json"))
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 5
		request {
			method('POST')
			headers {
				contentType(applicationJson())
			}
			body(file("updateChargesWithApprovedStatusRequest.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
		}
		response {
			status 200
			body([
					"shipment_prefix": "134",
					"master_document_number": "10000000",
					"cca_ref_number":$((regex('.*'))),
					"status_message":$((regex('.*')))
			])
			headers {
				contentType(applicationJson())
			}
		}
	},
	Contract.make {
		priority 6
		request {
			method('POST')
			headers {
				contentType(applicationJson())
			}
			body([
					"cca_number":"CCI000001",
					"shipment_prefix":"134",
					"master_document_number":"10000000"
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
		}
		response {
			status 200
			body(file("updatedChargesWithApprovedStatusResponse.json"))
			headers {
				contentType(applicationJson())
			}
		}
	}
]
