package contracts.findstockholderdetails

import org.springframework.cloud.contract.spec.Contract

[
//	Contract.make {
//		request {
//			method(POST())
//			headers {
//				contentType(multipartFormData())
//			}
//			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholderdetails')),
//					producer("stock/av/private/v1/stock/findstockholderdetails")))
//			multipart([
//					companyCode: named(
//							name: value(consumer(regex(nonEmpty())), producer('0')),
//							content: value(consumer(regex(nonEmpty())), producer('IBS'))
//					),
//					stockHolderCode: named(
//							name: value(consumer(regex(nonEmpty())), producer('1')),
//							content: value(consumer(regex(nonEmpty())), producer('HQ'))
//					)
//			])
//		}
//		response {
//			status 200
//			body([
//                    "companyCode"       : "IBS",
//                    "stockHolderCode"   : "HQ",
//                    "stockHolderType"   : $((regex('^(H|R|S|A)$'))),
//                    "stockHolderName"   : $((regex('.*'))),
//                    "controlPrivilege"  : $((regex('.*'))),
//                    "lastUpdateUser"    : $((regex('.*'))),
//                    "lastUpdateTime"    : $((regex('.*'))),
//                    "authorize_warnings": $((regex('^(true|false)$'))),
//                    "stock"             : [
//                            [
//                                    "companyCode"         : "IBS",
//                                    "airlineIdentifier"   : 1173,
//                                    "stockHolderCode"     : "HQ",
//                                    "documentType"        : "AWB",
//                                    "documentSubType"     : "S",
//                                    "reorderLevel"        : $((regex('^\\d+$'))),
//                                    "reorderQuantity"     : $((regex('^\\d+$'))),
//                                    "stockApproverCompany": $((regex('.*'))),
//                                    "autoprocessQuantity" : $((regex('^\\d+$'))),
//					           "ignoreWarnings": $((regex('^(true|false)$'))),
//					           "isAutoPopulateFlag": $((regex('^(true|false)$'))),
//					           "isAutoRequestFlag": $((regex('^(true|false)$'))),
//					           "isReorderAlertFlag": $((regex('^(true|false)$'))),
//					           "authorize_warnings": $((regex('^(true|false)$')))
//					        ],
//							[
//									"companyCode": "IBS",
//									"airlineIdentifier": 1172,
//									"stockHolderCode": "HQ",
//									"documentType": "AWB",
//									"documentSubType": "S",
//									"reorderLevel": $((regex('^\\d+$'))),
//									"reorderQuantity": $((regex('^\\d+$'))),
//									"stockApproverCompany": $((regex('.*'))),
//									"autoprocessQuantity": $((regex('^\\d+$'))),
//									"ignoreWarnings": $((regex('^(true|false)$'))),
//									"isAutoPopulateFlag": $((regex('^(true|false)$'))),
//									"isAutoRequestFlag": $((regex('^(true|false)$'))),
//									"isReorderAlertFlag": $((regex('^(true|false)$'))),
//									"authorize_warnings": $((regex('^(true|false)$')))
//							]
//					]
//			])
//			headers {
//				contentType(applicationJson())
//			}
//		}
//	},
//	Contract.make {
//		request {
//			method(POST())
//			headers {
//				contentType(applicationJson())
//			}
//			urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/findstockholderdetails/[a-zA-Z]*/[a-zA-Z]*')),
//					producer("stock/av/private/v1/stock/findstockholderdetails/IBS/AV")))
//		}
//		response {
//			status 204
//		}
//	}
]