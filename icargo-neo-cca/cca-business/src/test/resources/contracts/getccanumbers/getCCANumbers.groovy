package contracts.getccanumbers

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCANumbers)")), producer('/cca/av/private/v1/cca/getCCANumbers')))
                body([
                        "first"             : "10",
                        "cursor"            : null,
                        "filter"            : [],
                        "search"            : "CCI",
                        "load_from_filter"  : false
                ])
            }
            response {
                status OK()
                headers {
                    contentType(applicationJson())
                }
                body([
                        "edges" : [
                                [
                                        "node": [
                                                "cca_number_id" : $((regex('\\d{1,}'))),
                                                "cca_number"    : $((regex("CCI.*")))
                                        ]
                                ]
                        ]
                ])
            }
        }
]