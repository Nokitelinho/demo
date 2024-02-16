package contracts.getshipmentdescription.getshipment

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description('Should return the shipment details by AWB\'s')
    request {
        method('GET')
        headers {
            contentType(applicationJson())
        }
        urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments)")), producer('/tracking/av/private/v1/shipments'))) {
            queryParameters {
                parameter 'awb': value(consumer(matching("w+-{1}w+")), producer("020-32200220"))
            }
        }
    }
    response {
        status 200
        body([
                [
                        "awb_number"              : "02032200220",
                        "pieces"                  : 478,
                        "stated_weight"           : 23.4,
                        "stated_volume"           : 2.2,
                        "units_of_measure": [
                                "weight": "K",
                                "volume": "B"
                        ],
                        "special_handling_code"   : "GEN",
                        "product_name"            : "REGULAR",
                        "shipment_description"    : "CONSOLIDATION",
                        "origin_airport_code"     : "BOM",
                        "destination_airport_code": "FRA",
                        "milestones"              : [
                                [
                                        "milestone": regex('(ACCEPTED|DEPARTED|ARRIVED|DELIVERED)'),
                                        "status"   : regex('(to do|in progress|done)')
                                ]
                        ],
                        "departure_time"          : "10-01-2021 00:30:00",
                        "departure_time_postfix"  : "A",
                        "arrival_time"            : "10-01-2021 17:00:00",
                        "arrival_time_postfix"    : "S",
                        "transit_stations"        : [
                                "number_of_flights": 8,
                                "stops"            : [
                                        "DXB",
                                        "AUH",
                                        "CDG",
                                        "MUC"
                                ]
                        ]
                ]
        ])
        headers {
            contentType(applicationJson())
        }
    }
    priority 1
}
