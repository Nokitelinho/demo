import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

const intialState = {
  relisted : false,  
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  assignedTo:'',
  filterType:'',
  selectedContainerData:[],
  isValidationSuccess:false,
  toFlightDetails:{},
  showPopOverFlag:false,
  selectedContainerDataByIndex:[],
  selectedMailbagIndex:[],
  filterValuesFromInbound:{},
  fromInbound:false,
  flightDetailsFromInbound:{},
  filterValuesFromOutbound:{},
  fromOutbound:false,
  filterIntialValues:{},
  weightScaleAvailable:false,
  uldToBarrowAllow:'N',
  barrowToUld:false,
  uldToBarrow: false,
  defaultOperatingReference:'',
  fromOffload:false,
  warningDestination:''
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {    
    case 'SCREENLOAD_SUCCESS':
      return {...state,relisted : true,airportCode:action.data.containerFilter.airport,oneTimeValues:action.data.oneTimeValues, carrierCode: action.data.containerFilter.carrierCode, filterIntialValues:{assignedTo:"ALL", fromDate:action.data.fromDate, toDate:action.data.toDate, operationType:'O', showEmpty: 'Y', airport:action.data.containerFilter.airport, hbaMarked: 'N'},weightScaleAvailable:action.data.weightScaleAvailable,uldToBarrowAllow:action.data.uldToBarrowAllow, defaultOperatingReference: action.data.defaultOperatingReference};
	  case 'CHANGESEARCH_FILTER':
      return {...state,assignedTo:action.data.assignedTo} ;
    case 'CHANGEINPUT_TYPE':
      return {...state,filterType:action.data.filterType} ;    
    case 'SELECT_CONTAINER':
      return {...state,selectedContainerData:action.containerDetails} ; 
    case 'SHOW_POPOVER':
      return {...state,showPopOverFlag:true} ;
    case 'CLOSE_POPOVER':
      return {...state,showPopOverFlag:false} ;   
    case 'CONTENT_ID_SAVE_SUCCESS':
      return {...state} ;
    case 'VALIDATEFORTRANSFER_SUCCESS':
      return {...state, selectedContainerDataByIndex:action.selectedContainerData} ; 
    case 'VALIDATEFORTRANSFER_FLIGHT_SUCCESS':
      return {...state, selectedContainerDataByIndex:action.selectedContainerData} ;
    case 'TRANSFERSAVE_SUCCESS':
      return {...state,selectedContainerDataByIndex:[]}; 
    case 'CLOSE':
      return {...state,selectedContainerDataByIndex:[]};
    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailbagIndex:action.indexes}  
    case 'FILTER_FRM_INBOUND'  :
      return {...state, filterValuesFromInbound:action.data,fromInbound:true,
              flightDetailsFromInbound: action.flightDetails }
    case 'CLEAR_FILTER':
      return {...state, filterValuesFromInbound:{},fromInbound:false, fromOutbound:false,fromOffload:false, flightDetailsFromInbound:{}, filterValuesFromOutbound:{}, filterIntialValues:{assignedTo:"ALL", fromDate:action.data.fromDate, toDate:action.data.toDate, operationType:'O', showEmpty: 'Y', airport:state.airportCode} };
    case 'FILTER_FRM_OUTBOUND'  :
      return {...state, filterValuesFromOutbound:action.data,fromOutbound:true }
      case 'LIST_SUCCESS':
      return {...state,fromOutbound:false,fromOffload:false, filterIntialValues:action.containerFilter}
      case 'CONTAINER_CONVERSION':
      return  { 
        ...state,uldToBarrow:action.uldToBarrow,barrowToUld:action.barrowToUld
      }
      case 'BULK_CONVERSION':
      return  { 
        ...state,barrowToUld:action.barrowToUld
      }
      case 'ULD_CONVERSION':
      return  { 
        ...state,uldToBarrow:action.uldToBarrow
      }
      case 'FILTER_FRM_OFFLOAD'  :
      return {...state, fromOffload:true }
      case 'SAVE_WARNING_DESTINATION'  :
        return {...state, warningDestination:action.warningDestination }
	 case 'CLEAR_TABLE'  :
        return {...state, fromOutbound:false }
    default:
      return state;
  }
}
 
export default commonReducer;


