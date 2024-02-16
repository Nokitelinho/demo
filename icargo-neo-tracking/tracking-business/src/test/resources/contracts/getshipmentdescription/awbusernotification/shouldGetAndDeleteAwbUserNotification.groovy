package contracts.getshipmentdescription.getshipperconsigneedetails

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should return user milestones notification by AWB')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999255')))

            }
            response {
                status 200
                body([
                        "notifications"             : ["DEP"        : true,
                                                       "RCS"        : true,
                                                       "ARR"        : false,
                                                       "DLV"        : true
                        ],
                        "emails"                    : ["user@domain.com", "user2@gmail.com"],
                        "tracking_awb_serial_number": 1
                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should delete user awb notifications by tracking awb serial number')
            request {
                method('DELETE')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[0-9]+)")), producer('/tracking/av/private/v1/shipments/notifications/1')))
            }
            response {
                status 204
            }
            priority 1
        },

        Contract.make {
            description('Should return default model if No AWB user notification found')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999255')))

            }
            response {
                status 200
                body([
                        "notifications": ["DEP"        : false,
                                          "RCS"        : false,
                                          "ARR"        : false,
                                          "DLV"        : false
                        ],
                        "emails"       : []
                ])
            }
            priority 1
        },

        Contract.make {
            description('Should return not found if No AWB found for the shipment key')
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/999-99999999')))

            }
            response {
                status 404
            }
            priority 1
        }

]
