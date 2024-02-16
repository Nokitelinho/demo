package contracts.createhistory


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/U')),
                        producer("stock/av/private/v1/stock/createhistory/U"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/P')),
                        producer("stock/av/private/v1/stock/createhistory/P"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/A')),
                        producer("stock/av/private/v1/stock/createhistory/A"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/V')),
                        producer("stock/av/private/v1/stock/createhistory/V"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBodyInvalidStockControlFor.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/P')),
                        producer("stock/av/private/v1/stock/createhistory/P"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBodyWithoutStockControlFor.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/P')),
                        producer("stock/av/private/v1/stock/createhistory/P"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBodyWithoutStockControlFor.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/U')),
                        producer("stock/av/private/v1/stock/createhistory/U"))
            }
            response {
                status 500
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/NONE')),
                        producer("stock/av/private/v1/stock/createhistory/NONE"))
            }
            response {
                status 204
            }
        },
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createHistoryRequestBody1.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/createhistory/U')),
                        producer("stock/av/private/v1/stock/createhistory/U"))
            }
            response {
                status 204
            }
        }
]