package contracts.getshipmentdescription.getshipmentactivities

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should return the shipment activities by AWB')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/activities/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/activities/134-78999256'))
            }
            response {
                status 200
                body([
                        [
                                "event"                       : anyOf('RCS', 'DEP', 'ARR', 'DLV'),
                                "pieces"                      : anyNumber(),
                                "airport_code"                : regex('\\w{3}'),
                                "event_time"                  : regex('\\d{2} \\w{3} \\d{4}, \\d{2}:\\d{2}'),
                                "event_time_utc"              : regex('\\d{2} \\w{3} \\d{4}, \\d{2}:\\d{2}'),
                                "weight"                      : anyNumber(),
                                "weight_unit"                 : anyOf('K', null),
                                "flight"          : [
                                        "flight_carrier_code" : "TK",
                                        "flight_number"       : regex('\\w{2}-\\d{7}'),
                                        "origin"              : regex('\\w{3}'),
                                        "destination"         : regex('\\w{3}')
                                ],
                        ]

                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },
        Contract.make {
            description('Should return the shipment activities by AWB')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/activities/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/activities/134-78999258'))
            }
            response {
                status 200
                body([
                        [
                                "event"                          : 'RCT',
                                "pieces"                         : 200,
                                "airport_code"                   : 'TLL',
                                "event_time"                     : regex('\\d{2} \\w{3} \\d{4}, \\d{2}:\\d{2}'),
                                "event_time_utc"                 : regex('\\d{2} \\w{3} \\d{4}, \\d{2}:\\d{2}'),
                                "weight"                         : 1.5,
                                "weight_unit"                    : 'K',
                                "from_carrier"                   : 'carrier_1',
                                "to_carrier"                     : 'carrier_2',
                                "flight"          : [
                                        "flight_carrier_code" : "carrier_2"
                                ]
                        ]

                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        }

]
