package contracts.allocatestock

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange0.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange1.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange2.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange3.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange4.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange5.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange8.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange9.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange10.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange11.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange12.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange13.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange14.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange15.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange16.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange17.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange18.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange19.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockSaveRange20.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 200
            }
        },
        Contract.make {
            request {
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockValidationStockRangeUtilization.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_001",
                        "error_description": "Utilisation exists for the selected ranges.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationNotFoundStockHolder.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_004",
                        "error_description": "Stock Holder does not exist.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationWithEndRangeNull.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
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
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockValidationBlackList.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_005",
                        "error_description": "Range {0} contains blacklisted stock.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationInvalidStock.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_006",
                        "error_description": "Range {0} not found.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationNotFoundStockHolderWhileDeplete.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
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
                method(POST())
                headers {
                    contentType(applicationJson())
                }
                body(file("allocateStockValidationStockPeriod.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_009",
                        "error_description": "{0} cannot be reused within {1} years.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationStockHolderAlreadyDeleted.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_008",
                        "error_description": "StockHolder already deleted.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationDuplicateRange.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_010",
                        "error_description": "Range {0} already exists in the system.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateStockValidationStockRequestNotFound.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_007",
                        "error_description": "Stock request not found."
                ])
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
                body(file("allocateStockValidationEndRangeGreaterThanStartRange.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 400
                body([
                        "error_code"       : "NEO_STOCK_002",
                        "error_description": "End range less than start range.",
                        "error_type"       : "ERROR"
                ])
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
                body(file("allocateValidateStockRequestOALNotFound.json"))
                urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/allocatestock')),
                        producer('stock/av/private/v1/stock/allocatestock')))
            }
            response {
                status 500
                body([
                        "error_code"       : "OP_FAILED"
                ])
                headers {
                    contentType(applicationJson())
                }
            }
        }

]