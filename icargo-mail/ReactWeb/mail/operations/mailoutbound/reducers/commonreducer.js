import * as constant from '../constants/constants';

const intialState = {
  relisted : false,
  airportCode:'',
  flightCarrierCode:'',
  isCarrierDefault:'',
  oneTimeValues:[],
  flightCarrierflag: 'F',
  displayMode:'initial',
  flightCarrierView:'initial',
  carditView:'initial',
  flightPK: '',
  activeMainTab: 'CARDIT_LIST',
  activeCarditTab:'GROUP',
  warningMessagesStatus: {},
  isNavigation: false,
  defaultPageSize: 100,
  weightScaleAvailable:false,
  enableDeviationListTab: false,
  stationWeightUnit :'',
  uldToBarrowAllow:'N',
  uldToBarrow:false,
  barrowToUld:false,
  mandateScanTime: false,
  popupAction:null
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.SCREENLOAD_SUCCESS:
      return {...state,airportCode:action.data.airportCode,fromDate:action.data.fromDate,toDate:action.data.toDate,flightCarrierCode:action.data.carrierCode,isCarrierDefault:action.data.isCarrierDefault,flightCarrierflag:action.data.isCarrierDefault==='Y'?'C':'F',relisted: true,oneTimeValues:action.data.oneTimeValues,isNavigation:true, defaultPageSize: action.data.defaultPageSize,weightScaleAvailable:action.data.weightScaleAvailable,enableDeviationListTab:action.data.enableDeviationListTab,mandateScanTime:action.data.mandateScanTime, stationWeightUnit:action.data.stationWeightUnit,
                defaultOperatingReference: action.data.defaultOperatingReference ,uldToBarrowAllow:action.data.uldToBarrowAllow};
    case constant.CHOOSE_FILTER:
      return {...state,flightCarrierflag:action.data};
    case constant.LIST_DETAILS_FLIGHT:
      return {...state,displayMode:action.mode,flightCarrierView:'Flight'};
    case constant.LIST_DETAILS_CARRIER:
      return {...state,displayMode:action.mode,flightCarrierView:'Carrier'};
    case constant.TOGGLE_FLIGHT_PANEL:
     return {...state,displayMode:'display'};
    case constant.LIST_CONTAINER:
      return {...state,        
        displayMode: action.mode
       
      };
    
    case constant.TOGGLE_CARDIT_VIEW:
      return {...state,  
        carditView: (state.carditView=='initial') ? 'expanded':(state.carditView=='expanded')?'initial':'expanded',
        activeMainTab: 'CARDIT_LIST',
        activeCarditTab:'GROUP'
   	  }
    case constant.CHANGE_CARDITLYINGLIST_TAB:
      return { ...state,activeMainTab: action.data
      }
    
  case constant.ADD_CONTAINER_POPUPCLOSE:
      return {...state,warningMessagesStatus: {},popupAction:null}
    
    case constant.SET_WARNING_MAP:
      const updatedMap = {
          ...state.warningMessagesStatus,
          ...action.warningMapValue
      }
      return {
          ...state,
          warningMessagesStatus: updatedMap
      }
    case constant.CLEAR_FILTER:
      return { ...state,displayMode:'initial',
      flightCarrierView:'initial',
      carditView:'initial'
      }
    case constant.CLEAR_FLIGHT_TABLE_DATA:
        return  { 
          ...state,
        }
    case constant.CLEAR_FLIGHT_FILTER: 
        return {
          ...state,
          activeMainTab: 'CARDIT_LIST',
          activeCarditTab:'GROUP',
        }
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

        case constant.CONTAINER_REASSIGN_SUCCESS:
        return {...state,warningMessagesStatus: {}}
        case constant.UPDATE_POPUP_HANDLER:
        return {...state,popupAction:action.fromPopup?"embargo":null}
        case constant.ADD_MAILBAG_SUCCESS: 
        return {...state, popupAction:null}
        
    default:
      return state;
  }
}
 
export default commonReducer;


