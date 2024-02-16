package contracts.savecca

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCARequestActual.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089382",
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
                body(file("saveCCARequestInternalWithCCADetailsNull.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089382",
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
                body(file("saveApproveCCARequestWithNullDimensions.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089382",
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
                body(file("saveCCARequestInternal.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089382",
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
                body(file("saveApproveCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
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
                body(file("saveRejectCCARequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089384",
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
                body(file("saveCCARequestUpdateExistingChargeToZero.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089382",
                        "cca_ref_number"        : $((regex('.*'))),
                        "status_message"        : $((regex('.*')))
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Charge is gonna be updated to zero.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCARequestUpdateNonExistingChargeToZero.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_CCA_002",
                        "error_type"       : "ERROR",
                        "error_description": "Amount cannot be empty"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Charge is duplicated.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCARequestDuplicateCharge.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_CCA_001",
                        "error_type"       : "ERROR",
                        "error_description": "Duplicated charge"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Pieces equal zero. Origin and Destination the same.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidPiecesSameOriginAndDestinationRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_003",
                                "error_type"       : "ERROR",
                                "error_description": "Revised pieces cannot be zero"
                        ],
                        [
                                "error_code"       : "NEO_CCA_004",
                                "error_type"       : "ERROR",
                                "error_description": "Origin and Destination cannot be Same"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Origin empty.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidOriginRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_005",
                                "error_type"       : "ERROR",
                                "error_description": "Revised Origin cannot be blank"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Destination empty.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidDestinationRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_006",
                                "error_type"       : "ERROR",
                                "error_description": "Revised Destination cannot be blank"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Pieces equal zero. Origin and Destination the same.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidReasonCodesServiceCargoRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_012",
                                "error_type"       : "ERROR",
                                "error_description": "CCA Reason is mandatory"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Inbound Customer is empty for CC/CP/PC AWB\'s')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidInboundOutboundCustomerCodeRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_010",
                                "error_type"       : "ERROR",
                                "error_description": "Inbound Customer is mandatory for CC/CP/PC AWB's"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Service class is empty.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidCargoClassRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_013",
                                "error_type"       : "ERROR",
                                "error_description": "Freight Charges should be zero for Service Cargo Shipments"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Gross Wt cannot is zero.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidGrossWeightRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_007",
                                "error_type"       : "ERROR",
                                "error_description": "Revised Gross Wt cannot be zero"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            description('Should no save CCA. Chargeable Wt cannot is zero.')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCAInvalidChargeableWeightRequest.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 400
                body([
                        [
                                "error_code"       : "NEO_CCA_008",
                                "error_type"       : "ERROR",
                                "error_description": "Revised Chg.Wt cannot be zero"
                        ]
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
                body(file("saveCCARequestWithNewWorkFlowStep.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089391",
                        "cca_ref_number"        : "CCI000003",
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
                body(file("saveCCARequestWithRejectWorkFlowStep.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089391",
                        "cca_ref_number"        : "CCI000003",
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
                body(file("saveCCARequestWithApproveWorkFlowStep.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089391",
                        "cca_ref_number"        : "CCI000003",
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
                body(file("saveCCARequestForWithoutCCfeeCalculation.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "40000002",
                        "cca_ref_number"        : $((regex('.*'))),
                        "status_message"        : $((regex('.*')))
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        },

]
