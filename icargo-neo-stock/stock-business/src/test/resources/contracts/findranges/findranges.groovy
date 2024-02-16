package contracts.findranges

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("findranges0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findranges')),
                        producer("stock/av/private/v1/stock/findranges")))
            }
            response {
                status 200
                body([
                        [
                                "companyCode":"AV",
                                "stockHolderCode":"ALLOCATE5",
                                "documentType":"AWB",
                                "documentSubType":"S",
                                "airlineIdentifier":1191,
                                "startRange":"3000550",
                                "endRange":"3000648",
                                "asciiStartRange":0,
                                "asciiEndRange":0,
                                "numberOfDocuments":99,
                                "lastUpdateTime":[
                                        "stn":"***",
                                        "loc":"NONE",
                                        "epoc":1674045566000,
                                        "tz":"CET",
                                        "time":true,
                                        "ver":true
                                ],
                                "avlNumberOfDocuments":0,
                                "allocNumberOfDocuments":0,
                                "usedNumberOfDocuments":0,
                                "ignoreWarnings":false,
                                "authorize_warnings":false,
                                "isBlackList":false,
                                "isManual":false
                        ]
                ]
                )
                headers {
                    contentType(applicationJson())
                }
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("findranges1.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findranges')),
                        producer("stock/av/private/v1/stock/findranges")))
            }
            response {
                status 200
                body([])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]
