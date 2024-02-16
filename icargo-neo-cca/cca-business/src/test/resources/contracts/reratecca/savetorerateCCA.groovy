package contracts.reratecca

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
            method('POST')
            headers {
                contentType(applicationJson())
            }
            body(file("saveCCARequestActual.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/FRT')))
        }
        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                        "cca_awb_rates": $((regex('.*')))
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
            body(file("saveCCARequestActual.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/OTH')))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_charges": $((regex('.*')))
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
            body(file("saveCCARequestActual.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/AWB')))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_rates": $((regex('.*'))),
                            "cca_awb_charges": $((regex('.*')))
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
            body(file("saveCCARequestActual.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/' + null)))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_rates": $((regex('.*'))),
                            "cca_awb_charges": $((regex('.*')))
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
            body(file("saveCCARequestActualWithNotEmptyServiceCargoClass.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/AWB')))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_rates": $((regex('.*'))),
                            "cca_awb_charges": $((regex('.*')))
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
            body(file("saveCCARequestActualWithNotAllInAttribute.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/OTH')))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_rates": $((regex('.*'))),
                            "cca_awb_charges": $((regex('.*')))
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
            body(file("saveCCARequestActualWithEmptyRatingDetailVO.json"))
            urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reratecca/[a-zA-Z]*)")), producer('/cca/av/private/v1/cca/reratecca/AWB')))
        }

        response {
            status 200
            body([
                    "shipment_prefix"       : "134",
                    "master_document_number": "94490900",
                    "revised_awbdata": [
                            "cca_awb_rates": $((regex('.*'))),
                            "cca_awb_charges": $((regex('.*')))
                    ]
            ])
            headers {
                contentType(applicationJson())
            }
        }
    }

]
