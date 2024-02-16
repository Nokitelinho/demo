import org.springframework.cloud.contract.spec.Contract

[

        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCARequest2.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body(file("saveCCAResponse2.json"))
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
                body(file("saveCCARequest3.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body(file("saveCCAResponse3.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getrelatedcca/[A-Z0-9]{3,4}/{1}[A-Z0-9]{1,20})")), producer('/cca/av/private/v1/cca/getrelatedcca/134/39001202')))
            }
            response {
                status 200
                body([
                        [
                                "shipment_prefix"       : "134",
                                "master_document_number": "39001202",
                                "cca_number"            : $((regex('.*'))),
                                "cca_issue_date"        : ((regex('.*'))),
                                "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                                "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                                "cca_value"             : $((regex('.*')))
                        ],
                        [
                                "shipment_prefix"       : "134",
                                "master_document_number": "39001202",
                                "cca_number"            : $((regex('.*'))),
                                "cca_issue_date"        : ((regex('.*'))),
                                "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                                "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                                "cca_value"             : $((regex('.*')))
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }

]
