package contracts.getnetvalues

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("0_saveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089389",
                        "cca_ref_number"        : "CCA000013",
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
                body(file("0_saveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getnetvalues)")), producer('/cca/av/private/v1/cca/getnetvalues')))
            }
            response {
                status 200
                body([
                        "net_value_export"      : anyNumber(),
                        "net_value_import"      : anyNumber()
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]