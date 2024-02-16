package contracts.getshipmentdescription.getshipperconsigneedetails

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should return the shipper and consignee details by AWB')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/shipperconsigneedetails/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/shipperconsigneedetails/134-78999256'))) {
                }
            }
            response {
                status 200
                body([
                        "shipper_details": [
                                "name"    : "DHL ABRAJ SERVICE CENTER",
                                "code"    : "DHLABRAJ",
                                "address" : "Shipper Str.4 ",
                                "country" : "LB",
                                "state"   : "kerala",
                                "city"    : "shipper city",
                                "zip_code": "17042",
                        ],
                        consignee_details: [
                                "name"    : "AIR EXPRESS INTERNATIONAL",
                                "code"    : "CCBEY",
                                "address" : "test",
                                "country" : "IN",
                                "state"   : "consignee state",
                                "city"    : "consignee city",
                                "zip_code": "99999"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return the shipper and consignee details partially by AWB')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/shipperconsigneedetails/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/shipperconsigneedetails/134-78999255'))) {
                }
            }
            response {
                status 200
                body([
                        "shipper_details": [
                                "code": "DHLGER"
                        ],
                        consignee_details: [
                                "code": "DHLFRA"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return null by existing AWB if no Personal data found')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/shipperconsigneedetails/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/shipperconsigneedetails/134-78999257'))) {
                }
            }
            response {
                status 204
            }
            priority 1
        },

        Contract.make {
            description('Should return Not found exception by non existing AWB')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/shipperconsigneedetails/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/shipperconsigneedetails/999-9999'))) {
                }
            }
            response {
                status 404
            }
            priority 1
        }
]