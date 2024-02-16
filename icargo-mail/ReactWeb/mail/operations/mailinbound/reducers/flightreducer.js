import * as constant from '../constants/constants'
const intialState = {
  //flightActinBtn:false,
  flightData:[],
  flightDetails:[],
  flightOperationDetails:[],
  discrepancyData:[],
  showDiscrepancy:false,
  filterValues:{},
  flightDataAfterFilter:[],
  filterFlag:false,
  indexDetails:{},
  pageSize:'100',

}

const flightDetailsReducer = (state = intialState, action) => {

  switch (action.type) {
     case constant.LIST_SUCCESS:
    return {...state,  flightData:action.mailinboundDetails,flightDetails:action.flightDetails,pageSize:action.pageSize}
    case constant.LIST_CONTAINERS:
    return {...state,  flightDetails:action.flightDetails}
    case constant.LIST_CONTAINERS_ONADD_SUCCESS:
    return {...state,  flightDetails:action.flightDetails}
    case constant.CHANGE_DETAILPANEL_MODE:
      return { ...state, flightDetails:action.flightDetails,flightOperationDetails:action.flightOperationDetails };
    case constant.CLOSE_FLIGHT:
      return { ...state};
    case constant.OPEN_FLIGHT:
      return { ...state};
    case constant.AUTO_ATTACH_AWB:
      return state
    case constant.GENERATE_MANIFEST:
      return state
    case constant.ATTACH_AWB:
      return state
    case constant.DISCREPANCY:
      return state
    case constant.CLEAR_FILTER:
      return intialState
    case constant.LOAD_DISCREPANCY:
      return {...state,discrepancyData:action.discrepancyData,showDiscrepancy:true }
    case constant.CLOSE_DISCREPANCY:
      return {...state,showDiscrepancy:false } 
    case constant.UPDATE_MAIL_DETLS:
        return { ...state, flightOperationDetails:action.flightOperationDetails }; 
    case constant.APPLY_FLIGHT_FILTER:
       return {...state,filterValues:action.data} 
    case constant.CLEAR_FLIGHT_FILTER:
       return {...state,filterValues:{} } 
      case constant.SELECT_FLIGHT_ALL:
        return { ...state, flightData:action.flightData,indexDetails:action.indexDetails }; 
    case constant.CLEAR_FLIGHT_SELECT_ALL:
          return { ...state,indexDetails:{}};
    case constant.APPLY_FLIGHT_SORT:
      return {...state,flightData:action.flightDataAfterSort } 
    case constant.FLIGHT_LIST_ALONE:
        return {...state,flightData:action.mailinboundDetails}
    case constant.EMPTY_FLIGHTS: 
        return { ...state, 
             flightData:{}
            }
    default:
      return state
  }
}


export default flightDetailsReducer;