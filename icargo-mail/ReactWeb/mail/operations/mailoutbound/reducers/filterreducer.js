import * as constant from '../constants/constants';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();
const intialState = {
  screenMode: 'initial',
  //filterValues: {
   // port:'',
   // fromDate:'',
   // toDate:'',
  //},
  flightData: 'initial',
  carditView: 'initial',
  flightDetails:[],
  carrierDetails:[],
  filterValues: {},
  flightDisplayPage: '1',
  carrierDisplayPage:'1',
  flightActionsEnabled:'true',
  wareHouses:[],
  summaryFilter:{},
  showPopOverFlag:false,
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.LIST_DETAILS_FLIGHT:
      let flightnumber = { flightNumber:action.data.flightCarrierFilter.flightNumber || '',flightDate:action.data.flightCarrierFilter.flightDate ||'',carrierCode: action.data.flightCarrierFilter.carrierCode ||''}
      return { ...state, screenMode: 'display', flightData: 'display', filterValues: {...action.data.flightCarrierFilter,flightnumber}, flightDisplayPage: action.data.flightCarrierFilter.flightDisplayPage ,flightDetails:action.mailflightspage,wareHouses:action.wareHouses,flightActionsEnabled:action.flightActionsEnabled,summaryFilter:action.summaryFilter?action.summaryFilter:''}
    case constant.LIST_CAPACITY_FLIGHT:  
    {
      return {
        ...state,
        flightDetails: addSegmentDetails(state.flightDetails,action.flightCapacityDetails)
       }
     
    }
    case constant.LIST_VOLUME_FLIGHT:  
    {
      return {
        ...state,
        flightDetails: addSegmentDetailsForVol(state.flightDetails,action.flightVolumeDetails)
       }
   
    }
      case constant.LIST_DETAILS_CARRIER:
      return { ...state, screenMode: 'display', flightData: 'display', filterValues: action.data.flightCarrierFilter, carrierDisplayPage: action.data.flightCarrierFilter.carrierDisplayPage, carrierDetails:action.mailcarrierspage,wareHouses:action.wareHouses,flightActionsEnabled:action.flightActionsEnabled}
    case constant.CLEAR_FILTER:
      return intialState;
    case constant.TOGGLE_FILTER:
      return {
        ...state,
        screenMode: action.screenMode
      };
    case constant.CLEAR_FLIGHT_TABLE_DATA : 
      return  {
        ...state,
        flightDetails:[],
        carrierDetails:[]
      }
    case constant.APPLY_FLIGHT_SORT:
      return { ...state,flightDetails: action.flightDataAfterSort}
	 case constant.APPLY_CARRIER_SORT:
     return { ...state,carrierDetails: action.carrierDataAfterSort}
    case 'SHOW_POPOVER':
      return {...state,showPopOverFlag:true} ;
    case 'CLOSE_POPOVER':
      return {...state,showPopOverFlag:false} ;
      case constant.LIST_PREADVICE_FLIGHT:  
      {
        return {
          ...state,
          flightDetails: addPreAdviceDetails(state.flightDetails,action.flightPreAdviceDetails)
         }
      }
    default:
      return state
  }
}

export default filterReducer;

function addSegmentDetails(flightDetails, flightCapacityDetails) {
  let flights=flightDetails.results;
  flights = flights.map((flight) => {
    let flightpk=flight.flightNumber + flight.flightDate
    return {
      ...flight,
      capacityDetails: flightCapacityDetails[flightpk]
  };
  })
  flightDetails.results=flights;
  return flightDetails;
}
function addSegmentDetailsForVol(flightDetails, flightVolumeDetails) {
  let flights=flightDetails.results;
  flights = flights.map((flight) => {
    let flightpk=flight.flightNumber + flight.flightSequenceNumber;
    return {
      ...flight,
      volumeDetails: flightVolumeDetails[flightpk]
  };
  })
  flightDetails.results=flights;
  return flightDetails;
}
function addPreAdviceDetails(flightDetails, flightPreAdviceDetails) {
  let flights=flightDetails.results;
  flights = flights.map((flight) => {
    let flightpk= flight.flightNumber + flight.carrierId  + flight.flightSequenceNumber 
    let preAdviceDetails= flightPreAdviceDetails[flightpk];
    if(preAdviceDetails!=undefined && preAdviceDetails!=null){
     let preadvice=preAdviceDetails.preadvice;
    return {
      ...flight,preadvice:preadvice
  };  
  }
  else{
    return {
      ...flight}
  }
  })
  flightDetails.results=flights;
  return flightDetails;
}