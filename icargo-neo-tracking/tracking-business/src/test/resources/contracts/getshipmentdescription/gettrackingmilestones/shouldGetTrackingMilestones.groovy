package contracts.getshipmentdescription.getshipmentactivities

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description('Should return the tracking milestones')
            request {
                method(GET())
                headers {
                    contentType(applicationJson())
                }
                url $(consumer(regex('/tracking/[0-9a-z]+/private/v1/tracking/milestonemaster')),producer('/tracking/av/private/v1/tracking/milestonemaster'))
            }
            response {
                status 200
                body([
                        [											   
							"milestone_code": regex('.*'),
							"milestone_description": regex('.*'),
							"milestone_type": anyOf('I', 'E', 'T'),
							"milestone_shipment_type": "A",
							"activity_view_flag": regex(anyBoolean()),
							"email_notification_flag": regex(anyBoolean())
                        ]

                ])
                headers {
                    contentType(applicationJson())
                }
            }
            priority 1
        }

]
