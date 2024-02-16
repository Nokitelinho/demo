import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util'
import * as constant from '../constants/constants'
let currentDate = getCurrentDate();

const intialState ={  
  screenMode: 'initial',
  filterValues:{
    flightnumber:{     
        carrierCode:'',
        flightNumber:'',
        flightDate:''
    },
    port:'',
    fromDate:'',
    toDate:'',
    mailstatus:'',  
    transfercarrier:'',
    pa:'',
    mailFlightChecked:false,
    operatingReference:''
 },
  filterVlues:{
    flightnumber:{     
        carrierCode:'',
        flightNumber:'',
        flightDate:''
    },
    port:'',
    fromDate:'',
    toDate:'',
    mailstatus:'',  
    transfercarrier:'',
    pa:'',
    mailFlightChecked:false,
    operatingReference:''
 },
 navigationFilter:{},
  summaryFilter:{}
}
const filterReducer = (state = intialState, action) => {
 
  switch (action.type) {
    case constant.LIST_SUCCESS:
      // return {...state,        
      //   screenMode: 'display',
      //   //filterVlues:action.filterValues
      //    filterVlues:{
      //      flightnumber:action.filterValues?action.filterValues.flightnumber:null,
      //      port:action.filterValues?action.filterValues.port:null,
      //      fromDate:action.filterValues?action.filterValues.fromDate:null,
      //      toDate:action.filterValues?action.filterValues.toDate:null,
      //      mailstatus:action.filterValues?action.filterValues.mailstatus:null,
      //      transfercarrier:action.filterValues?action.filterValues.transfercarrier:null,
      //      pa:action.filterValues?action.filterValues.pa:null
      //    }          
      // };
      return {...state,        
        screenMode: 'display',
        //filterVlues : action.filterValues ? action.filterValues  : {}
           filterVlues : {flightnumber:action.filterValues?action.filterValues.flightnumber:null,
            port:action.filterValues?action.filterValues.port:null,
            fromDate:action.filterValues?action.filterValues.fromDate:null,
            toDate:action.filterValues?action.filterValues.toDate:null,
            mailstatus:action.filterValues?action.filterValues.mailstatus:null,
            transfercarrier:action.filterValues?action.filterValues.transfercarrier:null,
            pa:action.filterValues?action.filterValues.pa:null,...action.filterValues},
            filterValues:{flightnumber:action.filterValues?action.filterValues.flightnumber:null,
            port:action.filterValues?action.filterValues.port:null,
            fromDate:action.filterValues?action.filterValues.fromDate:null,
            toDate:action.filterValues?action.filterValues.toDate:null,
            mailstatus:action.filterValues?action.filterValues.mailstatus:null,
            transfercarrier:action.filterValues?action.filterValues.transfercarrier:null,
            pa:action.filterValues?action.filterValues.pa:null,
            mailFlightChecked:action.filterValues?action.filterValues.mailFlightChecked:null,
            operatingReference: action.filterValues?action.filterValues.operatingReference:'',
          },
          summaryFilter:action.summaryFilter

      };
    case constant.CHANGE_SCREEN_MODE:
      return {...state,        
        screenMode: action.mode
      };
    
      case constant.CLEAR_FILTER:
      return {
		  ...state,  
        screenMode: 'initial',
        filterVlues: {
          flightnumber: {
            carrierCode: '',
            flightNumber: '',
            flightDate: ''
          },
          fromDate: currentDate,
          toDate: currentDate,
          mailstatus: '',
          transfercarrier: '',
          pa: '',
          mailFlightChecked:false,
          operatingReference: ''
        },
         summaryFilter:{}
      }

      case constant.SCREEN_LOAD_SUCCESS:
      return {...state,filterVlues:action.filterVlues}

    case constant.CLEAR_TABLE:
      return intialState; 
      case 'SECURITY_FILTER':
                return {...state,navigationFilter:{flightnumber:{carrierCode:action.data.carrierCode,flightNumber:action.data.flightNumber,flightDate:action.data.flightDate},airportCode:action.data.airportCode,fromDate:action.data.fromDate,toDate:action.data.toDate}, fromScreen:action.data.fromScreen}
    
    default:
      return state;
  }
}


export default filterReducer;