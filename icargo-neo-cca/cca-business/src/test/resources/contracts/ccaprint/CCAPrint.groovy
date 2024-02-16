import org.springframework.cloud.contract.spec.Contract

[
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("saveCCARequestPayTypePP.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
			response {
				status 200
				body([
						"shipment_prefix"       : "134",
						"master_document_number": "79088645",
						"cca_ref_number"        : $((regex('.*'))),
						"status_message"        : $((regex('.*')))

				])
				headers {
					contentType(applicationJson())
				}
			}
		},
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("saveCCARequestPayTypeCC.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
			response {
				status 200
				body([
						"shipment_prefix"       : "134",
						"master_document_number": "79088645",
						"cca_ref_number"        : $((regex('.*'))),
						"status_message"        : $((regex('.*')))

				])
				headers {
					contentType(applicationJson())
				}
			}
		},
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("saveCCARequestPayTypePC.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
			response {
				status 200
				body([
						"shipment_prefix"       : "134",
						"master_document_number": "79088645",
						"cca_ref_number"        : $((regex('.*'))),
						"status_message"        : $((regex('.*')))

				])
				headers {
					contentType(applicationJson())
				}
			}
		},
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("CCAPrintPayTypePP.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/generateccaprint)")), producer('/cca/av/private/v1/cca/generateccaprint')))
			}
			response {
				status 200

			}
		},
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("CCAPrintPayTypeCC.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/generateccaprint)")), producer('/cca/av/private/v1/cca/generateccaprint')))
			}
			response {
				status 200

			}
		},
		Contract.make {
			request {
				method('POST')
				headers {
					contentType(applicationJson())
				}
				body(file("CCAPrintPayTypePC.json"))
				urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/generateccaprint)")), producer('/cca/av/private/v1/cca/generateccaprint')))
			}
			response {
				status 200

			}
		}

		, Contract.make {
	request {
		method('POST')
		headers {
			contentType(applicationJson())
		}
		body(file("CCAPrintNotExistingCCANumberError.json"))
		urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/generateccaprint)")), producer('/cca/av/private/v1/cca/generateccaprint')))
	}
	response {
		status 400
		body([
				"error_code": "NEO_CCA_014",
				"error_description": "CCA not found",
				"error_type": "ERROR"
		])
	}
}

]