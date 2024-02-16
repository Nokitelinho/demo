import * as constant from '../constants/constants'
const intialState = {
  //flightActinBtn:false,
  mailbagData:[],
  dsnData:[],
  activeMailbagAddTab:'NORMAL_VIEW',
  containerSelected:[],
  damageClose:false,
  damageDetails:[],
  arrivalDetailsInVoToBeReused:[],
  changeFlightClose:false,
  showTransferClose:false,
  attachClose:false,
  mailDataAfterFilter:[],
  filterValues:{},
  filterFlag:false,
  showDamageClose:false,
  oneTimeValues:{},
  updatedMailbags:null,
  flightDetailsToBeReused:{},
  attachRoutingClose:false,
  indexDetails:{},
  showDeliverMail:false,
  showReadyForDel:false,
  showScanTimePanel:false,
  dsnFilterValues:{},
  dsnFilterFlag:false,
  dsnDataAfterFilter:{},
  addContainerButtonShow:true,
  addMailPopUpShow:false,
  transferManifestVO:{},
  transferFilterType:'C',
  ownAirlineCode:'',
  partnerCarriers:{},
  enableDeliveryPopup : true,
  destination:null,
  excelMailbags:[],
  flightnumber:{},
  showRemoveMailPanel: false,
  carrierCode:null,
  flightno:{},
  damageDetail:[],
   validateDeliveryMailBagFlag:false,
   masterDocumentFlag:'Y'
}

const mailbagReducer = (state = intialState, action) => {

  switch (action.type) {
    case constant.LIST_SUCCESS:
    return {...state,  mailbagData:action.mailbagData,dsnData:action.dsnData, filterValues:{}, dsnFilterValues:{}}
      case constant.CHANGE_DETAILPANEL_MODE:
    return {...state,  mailbagData:action.mailbagData,containerSelected:action.containerSelected,dsnData:action.dsnData}
    case constant.ON_CONTAINER_CLICK:
    return {...state,  mailbagData:action.mailbagData,dsnData:action.dsnData,
              containerSelected:action.containerSelected}
    case constant.MAIL_DSN_LIST:
    return {...state,  mailbagData:action.mailDetails,dsnData:action.dsnDetails,mailDataAfterFilter:[],filterFlag:false}
    case constant.UNDO_ARRIVAL:
      return state
    case constant.ATTACH_ROUTING:
      return {...state,  attachRoutingClose:action.attachRoutingMalClose}  
    case constant.ARRIVE_MAIL:
      return state
    case constant.DELIVER_MAIL:
      return state
    case constant.MAILBAG_TRANSFER:
      return { ...state, showTransferClose:action.showTransferClose ,transferFilterType:'C',
      ownAirlineCode:action.ownAirlineCode,
      partnerCarriers:action.partnerCarriers};
    case constant.RETURN:
      return state
    case constant.READY_FOR_DELIVERY:
      return state
    case constant.DAMAGE_CAPTURE:
      return {...state,  damageDetails:action.damageDetails}
    case constant.CHANGE_SCAN_TIME:
      return state
    case constant.VIEW_MAIL_HISTORY:
      return state
    case constant.CLEAR_FILTER:
      return intialState
    case constant.APPLY_MAILBAG_FILTER:
      return {...state,filterValues:action.filterValues}
    case constant.CLEAR_MAIL_FILTER:
      return {...state,filterValues:{}}
    case constant.LIST_CONTAINERS:
      return {...state,filterValues:{}, dsnFilterValues:{}}
    case constant.CHANGE_ADD_MAILBAG_TAB:
      return { ...state,activeMailbagAddTab:action.currentTab}
    case constant.UPDATE_MAIL_DETLS:
        return { ...state, mailbagData:action.mailbagData,containerSelected:action.containerSelected };
    case constant.LIST_CONTAINERS_POPUP:
        return { ...state, arrivalDetailsInVoToBeReused:action.arrivalDetailsInVoToBeReused,
                    flightDetailsToBeReused:action.flightDetailsToBeReused,addContainerButtonShow:action.addContainerButtonShow,destination:action.destination,carrierCode:action.carrierCode };
    case constant.CHANGE_FLIGHT:
        return { ...state, changeFlightClose:action.changeFlightClose };
    case constant.CHANGE_FLIGHT_SAVE:
        return { ...state, changeFlightClose:action.changeFlightClose };
    case constant.TRANSFER_MANIFEST:
      return {...state,transferManifestVO : action.transferManifestVO };
    case constant.TRANSFER_SAVE:
      return { ...state, showTransferClose:action.showTransferClose,carrierCode:null,destination:null };
    case constant.DAMAGE_LIST:
      return { ...state, showDamageClose:true,oneTimeValues:action.oneTimeValues };
    case constant.DAMAGE_CAPTURE_CLOSE:
      return { ...state, showDamageClose: action.showDamageClose};
    case constant.UPDATE_MAILBAGS_IN_ADD_POPUP:
      return{...state,updatedMailbags:action.updatedMailbags}
      case constant.ATTACH_ROUT_CLOSE:
      return {...state,attachRoutingClose:action.attachRoutingMalClose} 
      case constant.ATTACH_ROUTING_SAVE:
      return {...state,  attachRoutingClose:action.attachRoutingMalClose} 
      case constant.ATTACH_AWB_MAL_CLOSE:
      return { ...state,attachClose:action.attachMalClose}
      case constant.ATTACH_AWB_MAL:
      return {...state,  attachClose:action.attachMalClose}
      case constant.SELECT_MAIL_ALL:
        return { ...state, mailbagData:action.mailDetails,indexDetails:action.indexDetails }; 
      case constant.SELECT_DSN_ALL:
        return { ...state, dsnData:action.dsnDetails,indexDetails:action.indexDetails }; 
    case constant.CLEAR_MAL_SELECT_ALL:
          return { ...state,indexDetails:{}};
      case constant.DELIVER_CLOSE:
      return { ...state, showDeliverMail:action.showDeliverMail,
        enableDeliveryPopup: action.showDeliverMail ? state.enableDeliveryPopup: true };
      case constant.SET_DELIVERY_POPUP_FIELDS_STATUS:
        return {
          ...state,
          enableDeliveryPopup: action.data
        }
      case constant.READY_FOR_DEL_CLOSE:
      return { ...state, showReadyForDel:action.showReadyForDel };
      case constant.CHANGE_SCAN_TIME_CLOSE:
      return { ...state, showScanTimePanel:action.showScanTimePanel };
      case constant.REMOVE_MAILBAG:
      return { ...state, showRemoveMailPanel:action.showRemoveMailPanel };
      case constant.APPLY_MAIL_SORT:
      return { ...state, mailbagData:action.mailDataAfterSort };  
      case constant.APPLY_DSN_FILTER:
      return {...state,dsnFilterValues:action.dsnFilterValues} 
      case constant.CLEAR_DSN_FILTER:
      return {...state,dsnFilterValues:{}} 
      case constant.APPLY_DSN_SORT:
      return { ...state, dsnData:action.dsnDataAfterSort };
      case constant.ERROR_SHOW:
      return { ...state };
      case constant.ADD_MAIL_POPUP_SHOW:
      return {...state, addMailPopUpShow:action.addMailPopUpShow};
      case constant.ATTACH_AWB_SAVED:
      return { ...state, attachClose:false};
      case constant.TRANSFER_POPUP_CLEAR:
        return { ...state, arrivalDetailsInVoToBeReused:action.arrivalDetailsInVoToBeReused,transferFilterType:action.transferFilterType,
                    flightDetailsToBeReused:action.flightDetailsToBeReused,addContainerButtonShow:action.addContainerButtonShow ,destination:null, flightnumber:{},carrierCode:null};
      case constant.TRANSFER_MANIFEST:
        return {...state,transferManifestVO : action.transferManifestVO };
      case 'UPDATE_EXCEL_MAILBAGS': 
       return {...state, excelMailbags:action.mailbags}
      case constant.EMPTY_FLIGHTS: 
       return { ...state, 
        mailbagData:{}
        }
      case constant.CLEAR_TRANSFER_FORM: 
       return {...state, flightnumber:action.flightnumber} 
      case constant.CLEAR_CHANGE_FLIGHT:
        return {...state, flightno:action.flightno} 
		
		   case constant.VALIDATE_MAILBAG:
        return {...state, validateDeliveryMailBagFlag:action.validateDeliveryMailBagFlag,masterDocumentFlag:action.masterDocumentFlag} 
		
       default:
      return state
  }
}
export default mailbagReducer;