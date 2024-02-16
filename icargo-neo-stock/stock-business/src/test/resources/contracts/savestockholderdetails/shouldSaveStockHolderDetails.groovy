package contracts.createhistory


import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method('POST')
                headers {
                    contentType(applicationJson())
                }
                body(file("createStockHolderDetailsValidationDuplicateStockHolder.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_016",
                        "error_description": "Duplicate Stock holder exists.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("createStockHolderDetailsWithStocksWithoutStockAgent0.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
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
                body(file("createStockHolderDetailsWithStocksWithoutStockAgent1.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
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
                body(file("createStockHolderDetailsWithStocksWithStockAgent.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
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
                body(file("createStockHolderDetailsValidationDuplicateStockAgent.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_015",
                        "error_description": "stockcontrol.defaults.duplicateagentfound",
                        "error_type"       : "ERROR"
                ])
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
                body(file("updateStockHolderDetailsWithStocks.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
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
                body(file("updateStockHolderDetailsValidationCannotDeleteStockWithRange.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_017",
                        "error_description": "Cannot delete stock holders having stock.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("createStockHolderDetailsValidationHeadQuarteAlreadyDefined.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_014",
                        "error_description": "HeadQuarter Already Defined.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("updateStockHolderValidationStockNotFound.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_003",
                        "error_description": "Stock not found.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("updateStockHolderDetailsValidationStockHolderNotFound.json"))
                url $(consumer(regex('stock/[0-9a-z]+/private/v1/stock/savestockholderdetails')),
                        producer("stock/av/private/v1/stock/savestockholderdetails"))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_011",
                        "error_description": "Invalid Stock Holder.",
                        "error_type"       : "ERROR"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }
]