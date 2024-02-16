package contracts.createhistory


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("rejectStockRequests.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/rejectstockrequests')),
                        producer("stock/av/private/v1/stock/rejectstockrequests"))
            }
            response {
                status 204
            }
        }
]