package contracts.getshipmentdescription.getshipmentsplits

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should return the shipment splits by AWB')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200220'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_status": regex('(Partially Arrived|Arrived|Partially Departed|Departed|Partially Delivered|Delivered)'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": [
                                                        [
                                                                "item_id": regex('\\d+(_\\d+)?'),
                                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                                "milestone_status": regex('(Partially Arrived|Arrived|Partially Departed|Departed|Partially Delivered|Delivered)'),
                                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                                "milestone_time_postfix": regex('(A|S)'),
                                                                "pieces": anyNumber(),
                                                                "carrier_code": regex('[A-Z]+'),
                                                                "flight_number": anyNonBlankString(),
                                                                "sub_splits": []
                                                        ]
                                                ]
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },
        Contract.make {
            description('Should return the shipment splits by AWB with no events happened')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200221'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": []
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },
        Contract.make {
            description('Should return the shipment splits by AWB with correct DST statuses')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200222'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_status": regex('(Partially Arrived|Arrived|Partially Departed|Departed|Partially Delivered|Delivered)'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": [
                                                        [
                                                                "item_id": regex('\\d+(_\\d+)?'),
                                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                                "milestone_status": regex('(Partially Arrived|Arrived|Partially Departed|Departed|Partially Delivered|Delivered)'),
                                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                                "milestone_time_postfix": regex('(A|S)'),
                                                                "pieces": anyNumber(),
                                                                "carrier_code": regex('[A-Z]+'),
                                                                "flight_number": anyNonBlankString(),
                                                                "sub_splits": []
                                                        ]
                                                ]
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return normalized shipment splits by AWB')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200223'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": [
                                                        [
                                                                "item_id": regex('\\d+(_\\d+)?'),
                                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                                "milestone_time_postfix": regex('(A|S)'),
                                                                "pieces": anyNumber(),
                                                                "carrier_code": regex('[A-Z]+'),
                                                                "flight_number": anyNonBlankString(),
                                                                "sub_splits": []
                                                        ]
                                                ]
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return the shipment splits by AWB considering DRN milestone code')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200224'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_status": regex('(Partially Arrived|Arrived|Partially Departed|Departed|Partially Delivered|Delivered)'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": []
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return Delivery pieces for normalized by Origin Splits')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200225'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "sub_splits": [
                                                        [
                                                                "item_id": regex('\\d+(_\\d+)?'),
                                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                                "milestone_time_postfix": regex('(A|S)'),
                                                                "pieces": anyNumber(),
                                                                "carrier_code": regex('[A-Z]+'),
                                                                "flight_number": anyNonBlankString(),
                                                                "sub_splits": []
                                                        ]
                                                ]
                                        ]
                                ]
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return Actual Flight data')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/shipments/splits/\\w+-{1}\\w+')),producer('/tracking/av/private/v1/shipments/splits/020-32200226'))
            }
            response {
                status 200
                body([
                        [
                                "split_number"              : anyNumber(),
                                "pieces"                    : anyNumber(),
                                "transit_stations"          : [
                                        "number_of_flights" : anyNumber(),
                                        "stops": [regex('[A-Z]{3}')]
                                ],
                                "milestone_status"           : regex('(Delivered|In progress)'),
                                "split_details": [
                                        [
                                                "item_id": regex('\\d+(_\\d+)?'),
                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                "milestone_time_postfix": regex('(A|S)'),
                                                "pieces": anyNumber(),
                                                "carrier_code": regex('[A-Z]+'),
                                                "flight_number": anyNonBlankString(),
                                                "actual_flight_data": [
                                                        "carrier_code": regex('[A-Z]+'),
                                                        "flight_number": anyNonBlankString(),
                                                        "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}')
                                                ],
                                                "sub_splits": [
                                                        [
                                                                "item_id": regex('\\d+(_\\d+)?'),
                                                                "next_item_id": regex('\\d+(_\\d+)?'),
                                                                "origin_airport_code": regex('[A-Z]{3}'),
                                                                "milestone_time":  regex('\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}'),
                                                                "milestone_time_postfix": regex('(A|S)'),
                                                                "pieces": anyNumber(),
                                                                "carrier_code": regex('[A-Z]+'),
                                                                "flight_number": anyNonBlankString(),
                                                                "sub_splits": []
                                                        ]
                                                ]
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
]
