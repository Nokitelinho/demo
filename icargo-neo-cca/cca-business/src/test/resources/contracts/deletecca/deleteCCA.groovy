package contracts.deletecca

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            priority 0
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("0_saveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : $((regex('^[0-9]*$'))),
                        "master_document_number": $((regex('^[0-9]*$'))),
                        "cca_ref_number"        : $((regex('.*'))),
                        "status_message"        : $((regex('.*')))
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            priority 1
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("1_saveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : $((regex('^[0-9]*$'))),
                        "master_document_number": $((regex('^[0-9]*$'))),
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
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/deletecca)")), producer('/cca/av/private/v1/cca/deletecca')))
                body(file("2_deleteCCARequest.json"))
            }
            response {
                status OK()
                body([
                        "edges" : [
                                [
                                        "shipment_prefix"       : "134",
                                        "master_document_number": "79089385",
                                        "cca_number"            : "CCA000009",
                                        "cca_status"            : "D",
                                        "status_message"        : anyNonEmptyString()
                                ]
                        ],
                        "errors": [
                                [
                                        "error_code"       : anyNonBlankString(),
                                        "errorGroup"       : anyNonBlankString(),
                                        "error_description": anyNonBlankString(),
                                        "error_type"       : "ERROR",
                                        "error_data"       : ["CCA000010"]
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
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/deletecca)")), producer('/cca/av/private/v1/cca/deletecca')))
                body(file("3_deleteCCARequest.json"))
            }
            response {
                status OK()
                body([
                        "edges" : [],
                        "errors": []
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method(POST())
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAListView)")), producer('/cca/av/private/v1/cca/getCCAListView')))
                body(file("4_ccaListViewRequest.json"))
                headers {
                    contentType(applicationJson())
                }
            }
            response {
                status OK()
                headers {
                    contentType(applicationJson())
                }
                body([
                        "edges"         : [],
                        "total_elements": 0,
                        "total_pages"   : 0
                ])
            }
        }
]
