package contracts.getccanumbers

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCcaReferenceNumber)")), producer('/cca/av/private/v1/cca/getCcaReferenceNumber')))
                body([
                        "shipment_prefix": "134",
                        "master_document_number": "79089383",
                        "cca_type": "I",
                ])
            }
            response {
                status OK()
                headers {
                    contentType(applicationJson())
                }
                body([

                            "authorize_warnings": false,
                            "document_owner_id": 1134,
                            "shipment_prefix": "134",
                            "master_document_number": "79089383",
                            "cca_number":  $((regex('.*'))),
                            "cca_issue_date":  $((regex('.*'))),
                            "cca_type": "I",
                            "cca_status": $((regex('.*'))),
                            "cca_reason_codes": [
                                [
                                    "parameter_code": ""
                                ]
                        ],
                            "auto_calculate_tax":  $((regex('.*'))),
                            "cca_source": "CRA",
                            "cca_value": 0.0,
                            "total_non_awb_charges": 0.0,
                            "cca_issue_datetime_utc": $((regex('.*')))

                ])
            }
        }
]