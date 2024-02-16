package contracts.getreason

import org.springframework.cloud.contract.spec.Contract

[

        Contract.make {
            request {
                method('GET')
                headers {
                    contentType(applicationJson())
                }
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/reasoncodes/available)")), producer('/cca/av/private/v1/cca/reasoncodes/available')))
            }
            response {
                status 200
                body([
                        [
                                "parameter_code"       : "01",
                                "parameter_description": "Change in Gross/Chargeable Weight"
                        ],
                        [
                                "parameter_code"       : "02",
                                "parameter_description": "Change in Charges - Weight/Other Charges"
                        ],
                        [
                                "parameter_code"       : "03",
                                "parameter_description": "Change in Billing Type"
                        ],
                        [
                                "parameter_code"       : "04",
                                "parameter_description": "Change in Agent"
                        ],
                        [
                                "parameter_code"       : "05",
                                "parameter_description": "Others"
                        ],
                        [
                                "parameter_code"       : "07",
                                "parameter_description": "From CASS Link"
                        ],
                        [
                                "parameter_code"       : "VD",
                                "parameter_description": "Auto CCA As Part Of Voiding"
                        ]
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
