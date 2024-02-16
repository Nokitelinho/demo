package contracts.getreason

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("saveCCARequest1.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status OK()
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "89898989",
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
                body(file("saveCCARequest2.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status OK()
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "89898989",
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
                body(file("saveCCARequest3.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
            }
            response {
                status OK()
                body([
                        "shipment_prefix"       : "134",
                        "master_document_number": "89898989",
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
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAListView)")), producer('/cca/av/private/v1/cca/getCCAListView')))
                body(file("ccaListViewFilterRequest.json"))
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
                        "edges"         : [
                                [
                                        "currency"                 : $((regex('.*'))),
                                        "cca_number"               : "CCI000013",
                                        "shipment_prefix"          : "134",
                                        "master_document_number"   : "89898989",
                                        "cca_master_serial_number" : $((regex('^[1-9]\\d*$'))),
                                        "cca_status"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "cca_type"                 : $((regex('.*'))),
                                        "total_non_awb_charge"     : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "outbound_customer_code"   : "DHLCDG001",
                                        "inbound_customer_code"    : "DHLCDG001",
                                        "cca_attachments"          : [
                                                [
                                                        "name"    : "print_button",
                                                        "format"  : "pdf",
                                                        "location": "icargoneoits3/CCA/CCA000004/print_button.pdf"
                                                ],
                                                [
                                                        "location": "icargoneoits3/CCA/CCA000003/document.png",
                                                        "name"    : "document",
                                                        "format"  : "png"
                                                ],
                                                [
                                                        "location": "icargoneoits3/CCA/CCA000004/e79a948f-4aa9-402c-9d4d-21cd0e2d39eb.pdf",
                                                        "name"    : "e79a948f-4aa9-402c-9d4d-21cd0e2d39eb",
                                                        "format"  : "pdf"
                                                ]
                                        ],
                                        "agent_details"            : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "inbound_customer_details" : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "outbound_customer_details": [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "import_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "export_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "net_value_export"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "net_value_import"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "cca_reason"               : $((regex('.*'))),
                                        "cca_assignee"             : $((regex('.*'))),
                                        "cca_source"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "cca_issue_date"           : $((regex('.*'))),
                                        "agent_code"               : "DHLCDG001"
                                ],
                                [
                                        "currency"                 : $((regex('.*'))),
                                        "cca_number"               : "CCI000012",
                                        "shipment_prefix"          : "134",
                                        "master_document_number"   : "89898989",
                                        "cca_master_serial_number" : $((regex('^[1-9]\\d*$'))),
                                        "cca_status"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "cca_type"                 : $((regex('.*'))),
                                        "total_non_awb_charge"     : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "outbound_customer_code"   : "DHLCDG001",
                                        "inbound_customer_code"    : "DHLCDG001",
                                        "agent_details"            : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "inbound_customer_details" : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "outbound_customer_details": [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "export_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "import_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "net_value_export"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "net_value_import"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "cca_reason"               : $((regex('.*'))),
                                        "cca_assignee"             : $((regex('.*'))),
                                        "cca_source"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "agent_code"               : "DHLCDG001",
                                        "cca_issue_date"           : $((regex('.*')))
                                ],
                                [
                                        "currency"                 : $((regex('.*'))),
                                        "cca_number"               : "CCI000011",
                                        "shipment_prefix"          : "134",
                                        "master_document_number"   : "89898989",
                                        "cca_master_serial_number" : $((regex('^[1-9]\\d*$'))),
                                        "cca_type"                 : $((regex('.*'))),
                                        "cca_status"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "total_non_awb_charge"     : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "outbound_customer_code"   : "DHLCDG001",
                                        "inbound_customer_code"    : "DHLCDG001",
                                        "agent_details"            : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "inbound_customer_details" : [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "outbound_customer_details": [
                                                "customer_name": "DHL WORLDWIDE INTERNATIONAL",
                                                "station_code" : "DFW"
                                        ],
                                        "export_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "import_billing_status"    : $((regex('^[a-zA-Z_ ]*$'))),
                                        "net_value_export"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "net_value_import"         : $((regex('(\\d+([.]\\d*)?([eE][+-]?\\d+)?|[.]\\d+([eE][+-]?\\d+)?)'))),
                                        "cca_reason"               : $((regex('.*'))),
                                        "cca_assignee"             : $((regex('.*'))),
                                        "cca_source"               : $((regex('^[a-zA-Z_ ]*$'))),
                                        "agent_code"               : "DHLCDG001",
                                        "cca_issue_date"           : $((regex('.*'))),
                                ]
                        ],
                        "total_elements": $((regex('^\\d+$'))),
                        "total_pages"   : $((regex('^\\d+$')))
                ])
            }
        },
        Contract.make {
            request {
                method(POST())
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAListView)")), producer('/cca/av/private/v1/cca/getCCAListView')))
                body(file("ccaListViewFilterRequestNotFound.json"))
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
