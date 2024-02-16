package contracts.createhistory


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("findTotalNoOfDocuments.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findtotalnoofdocuments')),
                        producer("stock/av/private/v1/stock/findtotalnoofdocuments"))
            }
            response {
                status 200
                body(1000001)
                headers {
                    contentType(applicationJson())
                }
            }
        }
]