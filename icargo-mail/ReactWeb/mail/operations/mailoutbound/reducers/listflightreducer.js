import * as constant from '../constants/constants';
const intialState ={
   vieMode:'initial',
   defaultTableFilter: {},
   tableFilter: {},
   carrierFilter: {},
   preadviceshow:'',
   selectedFlight:[],
   //index saved for flight multiselect
   selectedFlights:[],
   sort: {
   },
   carrierSort: {
  },
   saveIndexForPrint:''
  
  


  }

  
const listFlightReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.SAVE_SELECTED :
      return {...state, selectedFlights:action.data}
    case constant.PREADVICE_POPUP:
      return {...state,preadviceshow:true,selectedFlight:action.mailAcceptance}
    case constant.PREADVICE_OK:
      return {...state,preadviceshow:false}
    case constant.SAVE_INDEX_FORPRINT:
      return {...state,saveIndexForPrint:action.index}
    case constant.LIST_DETAILS_FLIGHT:
      return {...state,tableFilter:action.tableFilter }
    case constant.LIST_CARRIER_FILTER:
      return {...state,carrierFilter:action.tableFilter}
    case constant.SAVE_INITIAL_FLIGHT_FILTER: 
      return {...state,
        defaultTableFilter:action.data,
        tableFilter: action.data
      }
    case constant.LIST_DETAILS_CARRIER: 
      return {
        ...state,
        carrierFilter: { 
          upliftAirport: action.data.flightCarrierFilter ?action.data.flightCarrierFilter.airportCode:'', 
          carrierCode: action.data.flightCarrierFilter ?action.data.flightCarrierFilter.carrierCode:'',
          destination: action.data.flightCarrierFilter ?action.data.flightCarrierFilter.destination:'' }
      }
    case constant.CLEAR_FLIGHT_FILTER :
      return {...state,tableFilter:{}}; 
    case constant.CLEAR_CARRIER_FILTER :
      return {...state,carrierFilter:{}}; 
    case constant.CLEAR_SELECTED_FLIGHTS :
      return{...state,selectedFlights:[]}
    case constant.UPDATE_FLIGHT_SORT_VARIABLE:
      return { ...state,sort: action.data}  
    case constant.UPDATE_CARRIER_SORT_VARIABLE:
      return { ...state,carrierSort: action.data}
    case constant.CLEAR_FLIGHT_TABLE_DATA : 
      return  {...state,tableFilter:action.tableFilter}
    default:
      return state
  }
}


export default listFlightReducer;