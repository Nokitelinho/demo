package contracts.audit

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            priority 0
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("1_createCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
                        "status_message": "CCA000001 Saved successfully"
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
                body(file("2_initiateCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
                        "status_message": "CCA000001 Initiated successfully"
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
                body(file("3_recommendCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
                        "status_message": "CCA000001 Recommended successfully"
                ])
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
                body(file("4_updateCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
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
                body(file("5_approveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
                        "status_message": "CCA000001 Approved successfully"
                ])
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
                body(file("6_deleteCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "40000000",
                        "cca_ref_number": "CCA000001",
                        "status_message": "CCA000001 Deleted successfully"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
