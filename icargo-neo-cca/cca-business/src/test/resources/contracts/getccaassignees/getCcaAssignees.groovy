package contracts.getccaassignees

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccaassignees)")), producer('/cca/av/private/v1/cca/getccaassignees')))
                body([
                        "first"             : "10",
                        "cursor"            : null,
                        "filter"            : [],
                        "search"            : "",
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
                                                "user_name" : anyNonEmptyString(),
                                                "user_code"    : anyNonEmptyString()
                                        ]
                                ]
                        ]
                ])
            }
        }
]