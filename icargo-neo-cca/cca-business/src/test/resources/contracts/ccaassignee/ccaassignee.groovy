package contracts.ccaassignee

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
            request {
                method(PUT())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/ccaassignee)")), producer('/cca/av/private/v1/cca/ccaassignee')))
                body(file("1_updateCcaAssignee.json"))
            }
            response {
                status OK()
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "79089390",
                        "cca_ref_number": "CCA000014",
                        "status_message": "CCA000014 assignee is successfully updated."
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method(POST())
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
                body(file("2_getCcaDetails.json"))
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
                        "shipment_prefix"          : "134",
                        "master_document_number"   : "79089390",
                        "cca_number"               : "CCA000014",
                        "assignee"                 : "Mr.Assignee"
                ])
            }
        }
]
