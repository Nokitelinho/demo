const intialState ={
  mailbagFilter:{},
   screenMode: 'initial',  
    filterValues :{},
    tableFilter:{},
    summaryFilter:{},
    showPopOverFlag:false,
  noData:false,
  capFlag:false,
  fromInbound:false,
  mailbagIdsFromInbound:[],
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return {...state,screenMode: action.screenMode }
    case 'CLEAR_FILTER':
      return intialState;  
    case 'LIST_SUCCESS':
      return {...state,screenMode:'display',filterValues:action.data.mailbagFilter, summaryFilter:action.summaryFilter, capFlag:action.capFlag} 
   case 'LIST_FILTER':
      return {...state,tableFilter:action.mailbagTableFilter} 
    case 'CLEAR_TABLE_FILTER':
      return {...state,tableFilter:{}};  
    
    case 'SHOW_MAILBAG_PAGE_WITH_FILTER':
      return {
        ...state, filterValues: {
          'containerNo': action.data.containerNumber,
          flightnumber: {
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber!='-1' ? action.data.flightNumber:'',
          'flightDate': action.data.flightDate!='null' ? action.data.flightDate:''
          },
          fromDate:action.data.fromDate!='null'?action.data.fromDate:'',
          scanPort: action.data.airport,
          toDate:action.data.toDate!='null'?action.data.toDate:'',
          undefined: action.data.flightDate,
          containerFilter:action.data.containerFilter,
			containerAssignedOn:action.data.containerAssignedOn,
          airportCode:action.data.assignedPort,
        }
      }
    case 'SHOW_POPOVER':
        return {...state,showPopOverFlag:true} ;
    case 'CLOSE_POPOVER':
        return {...state,showPopOverFlag:false} ;   
  case 'NO_DATA':
      return {...state,screenMode:'initial',filterValues:action.data.mailbagFilter,noData:true};  		
     case 'SHOW_MAILBAG_PAGE_FROM_OFFLOAD':
      return {
        ...state, filterValues: {
          fromScreen:action.data.fromScreen
        }
      }
      case 'FILTER_FRM_INBOUND':
      return {
        ...state, filterValues: {
          'containerNo':'', //action.data.containerNumber,
          flightnumber: {
          'carrierCode': '',//action.data.carrierCode,
          'flightNumber': '',//action.data.flightNumber,
          'flightDate': '',//action.data.flightDate
          },
          fromDate: action.data.fromDate,
          scanPort: action.data.airport,
          toDate: action.data.toDate,
          undefined: action.data.flightDate,
          containerFilter:action.data.containerFilter,
          mailbagId:'',//action.data.mailBagId
          mailbagIdsFromInbound:action.data.mailBagId,
          valildationforimporthandling:action.data.valildationforimporthandling,
        },
        fromInbound:true
      }
      case 'FILTER_FRM_OUTBOUND':
      return {
        ...state, filterValues: {
          'containerNo': action.data.containerNumber,
          flightnumber: {
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber,
          'flightDate': action.data.flightDate
          },
          fromDate: action.data.fromDate,
          scanPort: action.data.airport,
          toDate: action.data.toDate,
          undefined: action.data.flightDate,
          containerFilter:action.data.containerFilter,
        },
        fromInbound:true
      }   
    default:
      return state
  }
}


export default filterReducer;