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
                parameter 'awb': value(consumer(matching("w+-{1}w+")), producer("134-78999257"))
            }
        }
    }
    response {
        status 200
        body([
                [
                        "awb_number"              : "13478999257",
                        "pieces"                  : 300,
                        "stated_weight"           : 23.4,
                        "stated_volume"           : 2.2,
                        "units_of_measure": [
                                "weight": "K",
                                "volume": "B"
                        ],
                        "special_handling_code"   : "GEN",
                        "product_name"            : "REGULAR",
                        "shipment_description"    : "CONSOLIDATION",
                        "origin_airport_code"     : "CPH",
                        "destination_airport_code": "SOF",
                        "milestones"              : [
                                [
                                        "milestone": regex('(ACCEPTED|DEPARTED|ARRIVED|DELIVERED)'),
                                        "status"   : regex('(to do|in progress|done)')
                                ]
                        ],
                        "departure_time"          : regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                        "departure_time_postfix"  : "S",
                        "arrival_time"            : regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                        "arrival_time_postfix"    : "S",
                        "transit_stations"        : [
                                "number_of_flights": 1,
                                "stops"            : []
                        ]
                ]
        ])
        headers {
            contentType(applicationJson())
        }
    }
    priority 1
}