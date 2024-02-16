package contracts.createhistory


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("viewRange.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/viewrange')),
                        producer("stock/av/private/v1/stock/viewrange"))
            }
            response {
                status 200
                body([

                        "availableRanges"   : [[
                                                       "airlineIdentifier"     : 0,
                                                       "startRange"            : "10000001",
                                                       "endRange"              : "20000001",
                                                       "asciiStartRange"       : 0,
                                                       "asciiEndRange"         : 0,
                                                       "numberOfDocuments"     : 1000001,
                                                       "avlNumberOfDocuments"  : 0,
                                                       "allocNumberOfDocuments": 0,
                                                       "usedNumberOfDocuments" : 0,
                                                       "stockAcceptanceDate"   : [
                                                               "stn" : "***",
                                                               "loc" : "NONE",
                                                               "epoc": 1692568800000,
                                                               "tz"  : "CET",
                                                               "time": true,
                                                               "ver" : true
                                                       ],
                                                       "masterDocumentNumbers" : ["1005000"],
                                                       "ignoreWarnings"        : false,
                                                       "authorize_warnings"    : false,
                                                       "isBlackList"           : false,
                                                       "isManual"              : false
                                               ]],
                        "allocatedRanges"   : [[
                                                       "stockHolderCode"       : "VR",
                                                       "airlineIdentifier"     : 0,
                                                       "startRange"            : "10000001",
                                                       "endRange"              : "20000001",
                                                       "asciiStartRange"       : 0,
                                                       "asciiEndRange"         : 0,
                                                       "numberOfDocuments"     : 1000001,
                                                       "avlNumberOfDocuments"  : 0,
                                                       "allocNumberOfDocuments": 0,
                                                       "usedNumberOfDocuments" : 0,
                                                       "stockAcceptanceDate"   : [
                                                               "stn" : "***",
                                                               "loc" : "NONE",
                                                               "epoc": 1692568800000,
                                                               "tz"  : "CET",
                                                               "time": true,
                                                               "ver" : true
                                                       ],
                                                       "ignoreWarnings"        : false,
                                                       "authorize_warnings"    : false,
                                                       "isBlackList"           : false,
                                                       "isManual"              : false
                                               ]],
                        "ignoreWarnings"    : false,
                        "manual"            : false,
                        "authorize_warnings": false

                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]