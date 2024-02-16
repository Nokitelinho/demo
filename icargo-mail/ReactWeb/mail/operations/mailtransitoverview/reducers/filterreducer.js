const initialState={
mailTransitFilter:{},
screenMode:'initial',
mailTransitDetails:[],
airportCode:'',
navigationFilter:{},
tableFilter:{},
fromDate:'',
toDate:'',
capacityDetailsPerRow:{},
capacityDetailsPerRowMap :{},
capacityDetailsKey:{},
onRowDoubleClick:false,
pageCount:0
}

const filterReducer=(state=initialState,action)=>{
    switch (action.type){

       
                case 'TOGGLE_FILTER':
                  return {...state,screenMode: action.screenMode};
                case 'LIST_SUCCESS':
                  return {...state,mailTransitDetails:action.mailTransits,mailTransitFilter:action.data.mailTransitFilter,screenMode:'display'};
                case 'LIST_FILTER':
                  return {...state,tableFilter:action.mailbagTableFilter} 
                case 'CLEAR_TABLE_FILTER':
                  return {...state,tableFilter:{}};
                case 'CLEAR_FILTER':
                    return {...state,relisted:true,mailTransitDetails:[], fromDate:action.fromDate,toDate:action.toDate,capacityDetailsPerRow:{},capacityDetailsPerRowMap:{},screenMode:'initial',pageCount:0}; 
                case 'INBOUND':
                      return {...state,navigationFilter:{flightnumber:{carrierCode:action.data.carrierCode,flightNumber:action.data.flightNumber,flightDate:action.data.flightDate},airportCode:action.data.airportCode,fromDate:action.data.fromDate,toDate:action.data.toDate}, fromScreen:action.data.fromScreen}
    case 'LIST_CAPPACITY_DETAILS':
    return {
      ...state,
      mailTransitDetails: addSegmentDetails(state.mailTransitDetails,action.flightCapacityDetails),screenMode: 'display' ,
     };
     case 'UPDATE_CAPACITY_DETAILS_KEY':
      return {
        ...state,
        capacityDetailsKey: updateSegmentDetailKey(action.rowData,state.capacityDetailsKey)
       };
      case 'LIST_CAPPACITY_DETAILS_ROW':
       return {
         ...state,
         capacityDetailsPerRowMap: addSegmentDetailMap(action.rowData,action.flightCapacityDetails, state.capacityDetailsPerRowMap) ,onRowDoubleClick:!state.onRowDoubleClick
        };
        case 'UPDATE_NEXT_PAGE_VALUE':
                  return {...state,pageCount: state.pageCount+1};
                default:
                return state
                
    }
}

export default filterReducer;
function addSegmentDetails(mailTransitDetails, flightCapacityDetails) {
  let mailTrans=mailTransitDetails.results;
  mailTrans = mailTrans.map((mailTra) => {
    let mailTransPK=mailTra.carrierCode+'-'+ mailTra.mailBagDestination
    return {
      ...mailTra,
      capacityDetails: flightCapacityDetails.mailTansitCapMap[mailTransPK]
  };
  })
  mailTransitDetails.results=mailTrans;
  console.log(mailTransitDetails);
  return mailTransitDetails;
}
function updateSegmentDetailKey(rowData, capacityDetailsKey) {
  let mailTransPK = rowData.carrierCode + '-' + rowData.mailBagDestination;

  if (!capacityDetailsKey) {
    capacityDetailsKey = {}; // Initialize as an empty object
  }

  if (!capacityDetailsKey.hasOwnProperty(mailTransPK)) {
    capacityDetailsKey[mailTransPK] = mailTransPK; // Set mailTransPK as the value
  }

  return capacityDetailsKey; // Return the updated capacityDetailsKey
}

function addSegmentDetailMap(mailTransitDetails, flightCapacityDetails, capacityDetailsPerRowMap) {
  let mailTransPK = mailTransitDetails.carrierCode + '-' + mailTransitDetails.mailBagDestination;
  let capacityDetails = flightCapacityDetails.mailTansitCapMap[mailTransPK];
  if (!capacityDetailsPerRowMap) {
    capacityDetailsPerRowMap = {}; // Initialize as an empty object
  }
  /* if(!capacityDetailsPerRowMap.hasOwnProperty(mailTransPK)){ */
  capacityDetailsPerRowMap[mailTransPK] = capacityDetails;
  // } Add the new entry
  console.log(capacityDetailsPerRowMap);

  return capacityDetailsPerRowMap;
}
