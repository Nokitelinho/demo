package contracts.findstockrangehistoryforpage


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("request.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrangehistoryforpage')),
                        producer("stock/av/private/v1/stock/findstockrangehistoryforpage"))
            }
            response {
                status 200
                body(file("response.json"))
                headers {
                    contentType(applicationJson())
                }
            }
        }
]