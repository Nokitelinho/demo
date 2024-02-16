package contracts.maintainccalist

import org.springframework.cloud.contract.spec.Contract

[

        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089381",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "A",
                        "cca_status"            : "N",
                        "cca_number"            : "CCA000002",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "C",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 0,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089381",
                        "cca_ref_number"        : "CCA000002",
                        "status_message"        : "CCA000002 Saved successfully"
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
                body([
                        "cca_number"            : "CCA000002",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089381"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089381",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : anyNumber(),
                                "net_value_import"             : anyNumber(),
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
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
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "A",
                        "cca_status"            : "A",
                        "cca_number"            : "CCA000004",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",

                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_ref_number"        : "CCA000004",
                        "status_message"        : "CCA000004 Approved successfully"

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
                body([
                        "cca_number"            : "CCA000004",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : anyNumber(),
                                "net_value_import"             : anyNumber(),
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
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
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089384",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "A",
                        "cca_status"            : "R",
                        "cca_number"            : "CCA000005",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089384",
                        "cca_ref_number"        : "CCA000005",
                        "status_message"        : "CCA000005 Rejected successfully"

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
                body([
                        "cca_number"            : "CCA000005",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089384"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089384",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : anyNumber(),
                                "net_value_import"             : anyNumber(),
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
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
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "C",
                        "cca_status"            : "C",
                        "cca_number"            : "CCA000005",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "net_charge"    : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_ref_number"        : "CCA000005",
                        "status_message"        : "CCA000005 Recommended successfully"

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
                body([
                        "cca_number"            : "CCA000005",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : anyNumber(),
                                "net_value_import"             : anyNumber(),
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
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
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "D",
                        "cca_status"            : "D",
                        "cca_number"            : "CCA000006",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_ref_number"        : "CCA000006",
                        "status_message"        : "CCA000006 Deleted successfully"

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
                body([
                        "cca_number"            : "CCA000006",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "shipping_date"  : "01-01-2022",
                                "net_value_export"             : anyNumber(),
                                "net_value_import"             : anyNumber(),
                                "cca_awb_charges": [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
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
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "D",
                        "cca_status"            : "I",
                        "cca_number"            : "CCA000077",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "net_value_export"             : 2.0,
                                "net_value_import"             : 1.0,
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "shipping_date"                : "01-01-2022",
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 3000.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 3000.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_ref_number"        : "CCA000077",
                        "status_message"        : "CCA000077 Initiated successfully"

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
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "awb_serial_number"     : 6201,
                        "cca_type"              : "D",
                        "cca_status"            : "D",
                        "cca_number"            : "CCA000007",
                        "cca_issue_datetime_utc": "2021-04-21T10:56:09",
                        "cca_remarks"           : "CCA to correct weight charge",
                        "cca_reason_codes"      : [
                                [
                                        "parameter_code": "01"
                                ],
                                [
                                        "parameter_code": "02"
                                ],
                                [
                                        "parameter_code": "03"
                                ],
                                [
                                        "parameter_code": "04"
                                ],
                                [
                                        "parameter_code": "05"
                                ],
                                [
                                        "parameter_code": "07"
                                ],
                                [
                                        "parameter_code": "VD"
                                ]
                        ],
                        "original_awbdata"      : [
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 300.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 148703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "handling_code"                : "PEF",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 300.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]

                        ],
                        "revised_awbdata"       : [
                                "currency"                     : "EUR",
                                "awb_freight_payment_type"     : "PP",
                                "awb_other_charge_payment_type": "CC",
                                "weight"                       : 300.0,
                                "volume"                       : 10.0,
                                "volumetric_weight"            : 1000.0,
                                "adjusted_weight"              : 1000.0,
                                "chargeable_weight"            : 4000.0,
                                "iata_rate"                    : 0,
                                "market_rate"                  : 0,
                                "wgt_charge"                   : 158703.88,
                                "val_charge"                   : 0,
                                "net_charge"                   : 0,
                                "discount_amount"              : 0,
                                "commission_amount"            : 0,
                                "agent_code"                   : "DHLCDG001",
                                "inbound_customer_code"        : "DHLCDG001",
                                "outbound_customer_code"       : "DHLCDG001",
                                "origin"                       : "CDG",
                                "destination"                  : "DXB",
                                "pieces"                       : 15,
                                "service_cargo_class"          : "",
                                "handling_code"                : "PEF",
                                "cca_awb_details"              : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 300.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_rates"                : [
                                        [
                                                "rate_type"     : "IATA",
                                                "charge"        : 250,
                                                "rate"          : 2.5,
                                                "commodity_code": "GEN"
                                        ]
                                ],
                                "cca_awb_charges"              : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "unit_of_measure"       : [
                                "weight"       : "K",
                                "volume"       : "B",
                                "length"       : "C",
                                "currency_code": "USD"
                        ]
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status 200
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_ref_number"        : "CCA000007"

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
                body([
                        "cca_number"            : "CCA000007",
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383"
                ])
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getccadetails)")), producer('/cca/av/private/v1/cca/getccadetails')))
            }
            response {
                status 200
                body([
                        "cca_number"            : $((regex('.*'))),
                        "shipment_prefix"       : "134",
                        "master_document_number": "79089383",
                        "cca_issue_date"        : ((regex('.*'))),
                        "cca_source"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_status"            : $((regex('^[a-zA-Z_ ]*$'))),
                        "cca_value"             : $((regex('.*'))),
                        "original_awbdata"      : [
                                "weight"           : 300.0,
                                "volume"           : 10.0,
                                "volumetric_weight": 1000.0,
                                "adjusted_weight"  : 1000.0,
                                "chargeable_weight": 4000.0,
                                "handling_code"    : "PEF",
                                "cca_awb_details"  : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 300.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 4000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_charges"  : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ],
                        "revised_awbdata"       : [
                                "weight"           : 300.0,
                                "volume"           : 10.0,
                                "volumetric_weight": 1000.0,
                                "adjusted_weight"  : 1000.0,
                                "chargeable_weight": 1000.0,
                                "handling_code"    : "PEF",
                                "cca_awb_details"  : [
                                        [
                                                "number_of_pieces"    : 15,
                                                "commodity_code"      : "DGR",
                                                "weight_of_shipment"  : 300.0,
                                                "volume_of_shipment"  : 10.0,
                                                "chargeable_weight"   : 1000.0,
                                                "dimensions"          : [],
                                                "shipment_description": "DANGEROUS GOODS",
                                                "display_volume_unit" : "B",
                                                "volumetric_weight"   : 1000.0,
                                                "adjusted_weight"     : 1000.0
                                        ]
                                ],
                                "cca_awb_charges"  : [
                                        [
                                                "charge"          : 4564.56,
                                                "due_carrier_flag": true,
                                                "due_agent_flag"  : false,
                                                "charge_head"     : "FUEL CHARGE",
                                                "charge_head_code": "MY"
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }

]
