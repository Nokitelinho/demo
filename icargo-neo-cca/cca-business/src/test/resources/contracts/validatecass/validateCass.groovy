package contracts.update.cca

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("ccaCassValidationDataRequest1.json"))
                urlPath($(consumer(regex("(/cca/[0-9a-z]+/private/v1/cca/validateCass)")), producer('/cca/av/private/v1/cca/validateCass')))
            }
            response {
                status OK()
                body([
                        "is_agent_cass": $((regex('^(true|false)$')))
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
