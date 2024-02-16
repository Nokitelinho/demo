let response = {
    "disableVOtoString": true,
    "status": "success",
    "results": [
        {
            "companyCode": "IBS",
        }
    ]
};

let result = [
    { ooe: "AAA", doe: "DD1", contractCarrier: "AA", originAirport: "AAA", destinationAirport: "DD7", categoryCode: "A", subclass: 'CB', xxresdit: 'No', mailboxId: 'ABX' ,status:'Active'},
    { ooe: "AAB", doe: "DD2", contractCarrier: "AA", originAirport: "AAB", destinationAirport: "DD5", categoryCode: "A", subclass: 'CB', xxresdit: 'Yes', mailboxId: 'ACX',status:'Inactive' },
  ];
  
export function getMockedListResponse() {
    return result ;
        
    
}