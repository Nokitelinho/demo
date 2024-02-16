import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            label 'sendCcaApprovedEvent'
            input {
                triggeredBy('sendCcaApprovedEvent()')
            }
            outputMessage {
                sentTo('av-cca-domain-events')
                body $(consumer([

                        "authorize_warnings" : false,
                        "shipment_prefix" : "134",
                        "master_document_number" : "74490953",
                        "original_awbdata" : [
                            "currency" : "USD",
                            "weight" : 1575.0,
                            "volume" : 0.1,
                            "origin" : "CDG",
                            "destination" : "DXB",
                            "pieces" : 15,
                            "awb_freight_payment_type" : "PP",
                            "awb_other_charge_payment_type" : "CC",
                            "volumetric_weight" : 0.1,
                            "adjusted_weight" : 0.1,
                            "chargeable_weight" : 1575.0,
                            "iata_rate" : 0.0,
                            "market_rate" : 2508.5,
                            "wgt_charge" : 0.0,
                            "val_charge" : 0.0,
                            "net_charge" : 148703.88,
                            "discount_amount" : 0.0,
                            "commission_amount" : 0.0,
                            "inbound_customer_code" : "FFFABRAJ",
                            "outbound_customer_code" : "DHLABRAJ",
                            "agent_code" : "AIEDXB",
                            "cca_awb_details" : [ [
                                                      "number_of_pieces" : 10,
                                                      "commodity_code" : "GEN",
                                                      "weight_of_shipment" : 1575.0,
                                                      "volume_of_shipment" : 0.1,
                                                      "chargeable_weight" : 1575.0,
                                                      "volumetric_weight" : 0.0,
                                                      "adjusted_weight" : 0.0
                                                  ] ],
                            "cca_awb_rates" : [ [
                                                    "displayChargeableWeight" : 0.0,
                                                    "charge" : 10.0,
                                                    "rate" : 0.0,
                                                    "rate_type" : "IATA",
                                                    "commodity_code" : "GEN",
                                                    "chargeable_weight" : 1575.0,
                                                    "net_charge" : 0.0,
                                                    "discount_percentage" : 0.0,
                                                    "minimum_charge_applied_flag" : false
                                                ], [
                                                    "displayChargeableWeight" : 0.0,
                                                    "charge" : 10.0,
                                                    "rate" : 0.0,
                                                    "rate_type" : "MKT",
                                                    "commodity_code" : "GEN",
                                                    "chargeable_weight" : 1575.0,
                                                    "net_charge" : 0.0,
                                                    "discount_percentage" : 0.0,
                                                    "minimum_charge_applied_flag" : false
                                                ] ],
                            "cca_awb_charges": [ [
                                                    "charge"          : 10,
                                                    "due_carrier_flag": true,
                                                    "due_agent_flag"  : false,
                                                    "charge_head"     : "Flat Charge",
                                                    "charge_head_code": "FC"
                                                ], [
                                                    "charge"          : 10,
                                                    "due_carrier_flag": false,
                                                    "due_agent_flag"  : true,
                                                    "charge_head"     : "Other Charge C",
                                                    "charge_head_code": "OTHC"
                                                ] ],
                            "tax_amount" : 10.0,
                            "dvfor_carriage" : 0.0,
                            "insurance_amount" : 0.0,
                            "total_due_agt_ccf_chg" : 12.0,
                            "total_other_charges" : 56.36
                        ],
                        "revised_awbdata" : [
                            "currency" : "USD",
                            "weight" : 1500.0,
                            "volume" : 0.1,
                            "origin" : "CDG",
                            "destination" : "DXB",
                            "pieces" : 15,
                            "awb_freight_payment_type" : "PP",
                            "awb_other_charge_payment_type" : "CC",
                            "volumetric_weight" : 0.1,
                            "adjusted_weight" : 0.1,
                            "chargeable_weight" : 1500.0,
                            "iata_rate" : 0.0,
                            "market_rate" : 2508.5,
                            "wgt_charge" : 0.0,
                            "val_charge" : 0.0,
                            "net_charge" : 148703.88,
                            "discount_amount" : 0.0,
                            "commission_amount" : 0.0,
                            "inbound_customer_code" : "FFFABRAJ",
                            "outbound_customer_code" : "DHLABRAJ",
                            "agent_code" : "AIEDXB",
                            "cca_awb_details" : [ [
                                                      "number_of_pieces" : 15,
                                                      "commodity_code" : "GEN",
                                                      "weight_of_shipment" : 1500.0,
                                                      "volume_of_shipment" : 0.1,
                                                      "chargeable_weight" : 1500.0,
                                                      "volumetric_weight" : 0.0,
                                                      "adjusted_weight" : 0.0
                                                  ] ],

                            "cca_awb_rates" : [ [
                                                    "displayChargeableWeight" : 0.0,
                                                    "charge" : 10.0,
                                                    "rate" : 0.0,
                                                    "rate_type" : "IATA",
                                                    "commodity_code" : "GEN",
                                                    "chargeable_weight" : 1500.0,
                                                    "net_charge" : 0.0,
                                                    "discount_percentage" : 0.0,
                                                    "minimum_charge_applied_flag" : false
                                                ], [
                                                    "displayChargeableWeight" : 0.0,
                                                    "charge" : 15.0,
                                                    "rate" : 0.0,
                                                    "rate_type" : "MKT",
                                                    "commodity_code" : "GEN",
                                                    "chargeable_weight" : 1500.0,
                                                    "net_charge" : 0.0,
                                                    "discount_percentage" : 0.0,
                                                    "minimum_charge_applied_flag" : false
                                                ] ],
                            "cca_awb_charges": [ [
                                                         "charge"          : 15,
                                                         "due_carrier_flag": true,
                                                         "due_agent_flag"  : false,
                                                         "charge_head"     : "Flat Charge",
                                                         "charge_head_code": "FC"
                                                 ], [
                                                         "charge"          : 10,
                                                         "due_carrier_flag": false,
                                                         "due_agent_flag"  : true,
                                                         "charge_head"     : "Transportation Charge",
                                                         "charge_head_code": "TC"
                                                 ] ],
                            "tax_amount" : 15.0,
                            "dvfor_carriage" : 0.0,
                            "insurance_amount" : 0.0,
                            "total_due_agt_ccf_chg" : 15.0,
                            "total_other_charges" : 56.36
                        ],
                        "cca_type" : "A",
                        "cca_status" : "A",
                        "cca_remarks" : "add new CCA",
                        "auto_calculate_tax" : "Y",
                        "cca_value" : 0.0,
                        "total_non_awb_charges" : 0.0,
                        "unit_of_measure": [
                            "currency_code": "USD",
                            "volume": "B",
                            "weight": "K"
                        ]

                ]),producer(nonEmpty()))
                headers {
                    messagingContentType(applicationJson())
                    header('EVENT_TYPE',$(producer(regex('.*')), consumer('CCAApprovedEvent')))
                    header('Event-Source','neo-cca-business')
                }
            }
        },
        Contract.make {
            label 'sendCcaUpdateEvent'
            input {
                triggeredBy('sendCcaUpdateEvent()')
            }
        },
        Contract.make {
            label 'sendCcaDeleteEvent'
            input {
                triggeredBy('sendCcaDeleteEvent()')
            }
        },
        Contract.make {
            label 'sendCcaCreateEvent'
            input {
                triggeredBy('sendCcaCreateEvent()')
            }
        }
]