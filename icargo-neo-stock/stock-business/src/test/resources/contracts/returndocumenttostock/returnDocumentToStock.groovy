package contracts.returndocumenttostock

import org.springframework.cloud.contract.spec.Contract

[
         Contract.make {
             request {
                 method(POST())
                headers {
                     contentType(applicationJson())
                 }
                 urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/returndocumenttostock')),
                         producer("stock/av/private/v1/stock/returndocumenttostock")))
                         body([
 							    "companyCode": "IBS",
 							    "stockHolderCode": "STKHLD",
 							    "documentType": "AWB",
 							    "documentSubType": "S",
 							    "airlineIdentifier": 1777,
 							    "ranges": [
 							        [
 							            "startRange": "1005002"
 							        ]
 							    ]
                	 ])
                 }
             response {
                 status 204
             }
         },
         Contract.make {
             request {
                 method(POST())
                headers {
                     contentType(applicationJson())
                 }
                 urlPath($(consumer(regex('stock/[0-9a-z]+/private/v1/stock/returndocumenttostock')),
                         producer("stock/av/private/v1/stock/returndocumenttostock")))
                         body([
 							    "companyCode": "IBS",
 							    "stockHolderCode": "STKHLD",
 							    "documentType": "AWB",
 							    "documentSubType": "S",
 							    "airlineIdentifier": 1777,
 							    "ranges": [
 							        [
 							            "startRange": "1001000"
 							        ]
 							    ]
                	 ])
                 }
             response {
                 status 204
             }
         }
]