package contracts.getshipmentdescription.getshipperconsigneedetails

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should save user notification by AWB and validate emails')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999256')))
                body([
                        "notifications": ["DEP"        : false,
                                          "RCS"        : true,
                                          "ARR": true,
                                          "DLV"        : true
                        ],
                        "emails"       : ["user@domain.com", "user2@gmail.com", "invalid@email", "@invalid@email.com"],
                ])
            }
            response {
                status 200
                body([
                        "notifications"             : ["DEP"        : false,
                                                       "RCS"        : true,
                                                       "ARR": true,
                                                       "DLV"        : true
                        ],
                        "emails"                    : ["user@domain.com", "user2@gmail.com"],
                        "tracking_awb_serial_number": 2

                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },
        Contract.make {
            description('Should update user notification by AWB')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999256')))
                body([
                        "notifications": ["DEP"        : false,
                                          "RCS"        : true,
                                          "ARR"		   : false,
                                          "DLV"        : true
                        ],
                        "emails"       : ["user@domain.com"],
                ])
            }
            response {
                status 200
                body([
                        "notifications"             : ["DEP"        : false,
                                                       "RCS"        : true,
                                                       "ARR"	    : false,
                                                       "DLV"        : true
                        ],
                        "emails"                    : ["user@domain.com"],
                        "tracking_awb_serial_number": 2

                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should return error if No milestones or emails provided')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999255')))
                body([
                        "notifications": ["DEP"        : false,
                                          "RCS"        : false,
                                          "ARR": false,
                                          "DLV"        : false
                        ],
                        "emails"       : ["user@domain.com"],
                ])
            }
            response {
                status 400
                body([["error_code"       : "NEO_TRK_002",
                       "error_description": "Email addresses and notification milestones shouldn't be empty!"]])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        },

        Contract.make {
            description('Should throw error if no valid emails provided')
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/tracking/[0-9a-z]+/private/v1/shipments/notifications/[A-Z0-9]{3,4}-{1}[A-Z0-9]{1,20})")), producer('/tracking/av/private/v1/shipments/notifications/134-78999255')))
                body([
                        "notifications": ["DEP"        : false,
                                          "RCS"        : true,
                                          "ARR": true,
                                          "DLV"        : false
                        ],
                        "emails"       : ["@user@domain.com", "invalid@com"],
                ])
            }
            response {
                status 400
                body(["error_code"       : "NEO_TRK_003",
                      "error_description": "There are no valid emails provided"])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        }
]
