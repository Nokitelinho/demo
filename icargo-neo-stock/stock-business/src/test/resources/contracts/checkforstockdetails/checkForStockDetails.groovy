package contracts.checkforstockdetails


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("request.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/isstockdetailsexists')),
                        producer("stock/av/private/v1/stock/isstockdetailsexists"))
            }
            response {
                status 200
                body(file("response.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("request2.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/isstockdetailsexists')),
                        producer("stock/av/private/v1/stock/isstockdetailsexists"))
            }
            response {
                status 200
                body(file("response2.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        }
]