import org.springframework.cloud.contract.spec.Contract

[

	Contract.make {
		request {
			method('POST')
			headers {
				contentType(applicationJson())
				}
			body(file("saveCCARequest1.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
				body(file("saveCCAResponse1.json"))
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
			body(file("saveCCARequest2.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
				body(file("saveCCAResponse2.json"))
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
				"cca_number":"CCA"
					])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAList)")), producer('/cca/av/private/v1/cca/getCCAList')))
			}
		response {
			status 200
				body([
				[
				"cca_number":$((regex('.*'))),
				"shipment_prefix":"134",
				"master_document_number":"39001202",
				"cca_issue_date":((regex('.*'))),
				"cca_source":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_status":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_value":$((regex('.*')))
				],
				[
				"cca_number":$((regex('.*'))),
				"shipment_prefix":"134",
				"master_document_number":"39001201",
				"cca_issue_date":((regex('.*'))),
				"cca_source":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_status":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_value":$((regex('.*')))
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
				"cca_number":"CCA",
				"shipment_prefix":"134",
				"master_document_number":"39001202",
					])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAList)")), producer('/cca/av/private/v1/cca/getCCAList')))
			}
		response {
			status 200
				body([
				[
				"cca_number":$((regex('.*'))),
				"shipment_prefix":"134",
				"master_document_number":"39001202",
				"cca_issue_date":((regex('.*'))),
				"cca_source":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_status":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_value":$((regex('.*')))
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
			body(file("saveCCARequest5.json"))
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
				body(file("saveCCAResponse5.json"))
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
			body([

					"shipment_prefix":"134",
				"master_document_number":"94490915",
					])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAList)")), producer('/cca/av/private/v1/cca/getCCAList')))
			}
		response {
			status 200
				body([
				[
				"cca_number":$((regex('.*'))),
				"shipment_prefix":"134",
				"master_document_number":"94490915",
				"cca_issue_date":((regex('.*'))),
				"cca_source":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_status":"N",
				"cca_value": $((regex('[+-]?([0-9]*[.])?[0-9]+')))
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
			"shipment_prefix": "134",
    "master_document_number": "94490910",
    "awb_serial_number": 6201,
	"cca_type" :"A",
	"cca_status":"R",
	"cca_number":"CCA000001",
	"cca_issue_datetime_utc": "2021-04-21T10:56:09",
	"cca_remarks":"CCA to correct weight charge",
	"cca_reason_codes" : [
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
    "original_awbdata": [
        "shipping_date": "01-01-2022",
        "currency": "EUR",
        "awb_freight_payment_type": "PP",
        "awb_other_charge_payment_type": "CC",
        "weight": 3000.0,
        "volume": 10.0,
        "volumetric_weight": 1000.0,
        "adjusted_weight": 1000.0,
        "chargeable_weight": 4000.0,
        "iata_rate": 0,
        "market_rate": 0,
        "wgt_charge": 148703.88,
        "val_charge": 0,
        "net_charge": 0,
        "discount_amount": 0,
        "commission_amount": 0,
		"agent_code": "DHLCDG001",
		"inbound_customer_code" : "DHLCDG001",
		"outbound_customer_code" : "DHLCDG001",
        "origin": "CDG",
        "destination": "DXB",
        "pieces": 15,
        "service_cargo_class": "",
		"net_value_export": 2.0,
		"net_value_import": 1.0,
        "cca_awb_details": [
            [
                "number_of_pieces": 15,
                "commodity_code": "DGR",
                "weight_of_shipment": 3000.0,
                "volume_of_shipment": 10.0,
                "chargeable_weight": 4000.0,
                "dimensions": [],
                "shipment_description": "DANGEROUS GOODS",
                "display_volume_unit": "B",
                "volumetric_weight": 1000.0,
                "adjusted_weight": 1000.0
            ]
        ],
        "cca_awb_rates":[
        	[
        	"rate_type":"IATA",
        	"net_charge":250,
        	"rate":2.5,
        	"commodity_code":"GEN"
        ]
        	],
        	"cca_awb_charges":[
        		[
        			"charge": 4564.56,
                    "due_carrier_flag": true,
                    "due_agent_flag": false,
                    "charge_head": "FUEL CHARGE",
                    "charge_head_code": "MY"
        		]
        		]

    ],
	 "revised_awbdata": [
		"shipping_date": "01-01-2022",
        "currency": "EUR",
        "awb_freight_payment_type": "PP",
        "awb_other_charge_payment_type": "CC",
        "weight": 3000.0,
        "volume": 10.0,
        "volumetric_weight": 1000.0,
        "adjusted_weight": 1000.0,
        "chargeable_weight": 4000.0,
        "iata_rate": 0,
        "market_rate": 0,
        "wgt_charge": 158703.88,
        "val_charge": 0,
        "net_charge": 0,
        "discount_amount": 0,
        "commission_amount": 0,
		"agent_code": "DHLCDG001",
		"inbound_customer_code" : "DHLCDG001",
		"outbound_customer_code" : "DHLCDG001",
		"origin": "CDG",
        "destination": "DXB",
        "pieces": 15,
        "service_cargo_class": "C",
        "cca_awb_details": [
            [
                "number_of_pieces": 15,
                "commodity_code": "DGR",
                "weight_of_shipment": 3000.0,
                "volume_of_shipment": 10.0,
                "chargeable_weight": 4000.0,
                "dimensions": [],
                "shipment_description": "DANGEROUS GOODS",
                "display_volume_unit": "B",
                "volumetric_weight": 1000.0,
                "adjusted_weight": 1000.0
            ]
        ],
        "cca_awb_rates":[
        	[
        	"rate_type":"IATA",
        	"charge":0,
        	"rate":2.5,
        	"commodity_code":"GEN"
        ]
        	],
        	"cca_awb_charges":[
        		[
        			"charge": 4564.56,
                    "due_carrier_flag": true,
                    "due_agent_flag": false,
                    "charge_head": "FUEL CHARGE",
                    "charge_head_code": "MY"
        		]
        		]
    ],
     "unit_of_measure":[
      "weight":"K",
      "volume":"B",
      "length":"C",
	"currency_code":"USD"
   ]
			])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/saveCCA)")), producer('/cca/av/private/v1/cca/saveCCA')))
			}
		response {
			status 200
				body(file("saveCCAResponse4.json"))
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
			body([

			 "shipment_prefix":$(consumer($(regex('^[0-9]*$'))),producer("134")),
			 "master_document_number":$(consumer($(regex('^[0-9]*$'))),producer("94490910")),

					])
			urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/getCCAList)")), producer('/cca/av/private/v1/cca/getCCAList')))
			}
		response {
			status 200
				body([
				[
				"cca_number":$((regex('.*'))),
				"shipment_prefix":fromRequest().body('$.shipment_prefix'),
				"master_document_number":fromRequest().body('$.master_document_number'),
				"cca_issue_date":((regex('.*'))),
				"cca_source":$((regex('^[a-zA-Z_ ]*$'))),
				"cca_status":"R",
				"cca_value": $((regex('[+-]?([0-9]*[.])?[0-9]+')))
				]
				])
			headers {
				contentType(applicationJson())
			}
		}
	}

]
