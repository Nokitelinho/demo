package contracts.validatestockforvoiding

import org.springframework.cloud.contract.spec.Contract

[
	Contract.make {
		request {
			method(POST())
			headers {
				contentType(applicationJson())
			}
			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/validatestockforvoiding')),
					producer('stock/av/private/v1/stock/validatestockforvoiding')))
			body(file("request0.json"))
		}
		response {
			status 200
			body(file("response0.json"))
			headers {
				contentType(applicationJson())
			}
		}
	}
]