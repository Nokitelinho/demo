package contracts.findstockrangehistoryforpage


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("requestFindStockHistory.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrangehistory')),
                        producer("stock/av/private/v1/stock/findstockrangehistory"))
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
                body(file("requestFindStockUtilisationHistory.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockrangehistory')),
                        producer("stock/av/private/v1/stock/findstockrangehistory"))
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