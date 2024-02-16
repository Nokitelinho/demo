import * as constant from '../constants/constants';
const intialState ={
   vieMode:'initial',
   flightDetails:[],
   mailBags:[],
   mailBagsdsnView:[],
   containersArray:[],
   selectedContainer:'',
   mailbagsDisplayPage:'1',
   mailbagsDSNDisplayPage:'1',
   activeMailbagTab:'MAIL_VIEW',
   activeMailbagAddTab:'NORMAL_VIEW',
   tableFilter:{},
    // to add/modify Mailbag
   addMailbagShow:false,
   addModifyFlag:'',
   density:'',
  // selectedContainer:'',
  // selectedContCount:'',
  mailbagOneTimeValues:[],
  remarkspopup:false,
  scanTimePopup:false,
  mailbagReturnPopup:false,
  mailbagReturnDamageFlag:'',
  currentDate:'',
  currentTime:'',
  defaultWeightUnit:'',
  stationVolUnt:'',
  previousRowWeight:'',
  // newContainerDetails:[],
  // hasAddContainerPanelLoaded:'',
  //Integration wil mailbag enquiry
  //save selected mail bag index
   selectedMailbagsIndex:[],
   containerDetails:[],
   reassignContainers:[],
   flightValidation:null,
  
  selectedMail:null,
  selectedMailbags:[],
  selectedMailbag:[],
  damageDetail:[],
  checkedMailIndex:[],
  //To get import mailbags
  showImportPopup:false,
  containerJnyID:null,
  importedmailbags:[],
  importedMailbagDetails :[],
  newMailbags:[],
  //to populate mailbag, weight,dsn etc
  updatedMailbags:[],
  postalCodes: {},
  showReassign:false,
  existingMailbagPresent:false,
  existingMailbags:[],
  addedMailbags:[],
  allMailbags:[],
  reassignFilterType:'F',
  pous:null,
  pou:null,
  isValidFlight:true,
  flightnumber:{},
  carrierCode:'',
  dsnFilterValues: {},
  transferFilterType :'C',
  showTransfer:false,
  flightDetailsToBeReused:{},
  addContainerButtonShow:true,
  transferFilterType:'C',
  ownAirlineCode:'',
  partnerCarriers:{},
  transferContainers:[] ,
  containerDisplayPageForTransfer:'1'  ,
  disableForModify:false,
  dsnFilterValues:{},
  allDsnList:[]
  }

  
const mailbagReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.LIST_MAILBAGS:
      return {...state,selectedContainer:action.containerInfo? action.containerInfo:'',mailBags:action.containerInfo?action.containerInfo.mailbagpagelist:'',containersArray:action.containersArray?action.containersArray:'',activeMailbagTab:'MAIL_VIEW',mailbagsDisplayPage:action.mailbagsDisplayPage?action.mailbagsDisplayPage:'1',selectedMailbagsIndex:[], allMailBags:action.containerInfo && action.containerInfo.mailbagpagelist?action.containerInfo.mailbagpagelist.results:''}   
    case constant.LIST_MAILBAGS_DSN:
       return {...state,selectedContainer:action.containerInfo?action.containerInfo:'',mailBagsdsnView:action.containerInfo?action.containerInfo.dsnviewpagelist:'',activeMailbagTab:'DSN_VIEW',mailbagsDSNDisplayPage:action.mailbagsDSNDisplayPage?action.mailbagsDSNDisplayPage:'1', allDsnList:action.containerInfo && action.containerInfo.dsnviewpagelist ? action.containerInfo.dsnviewpagelist.results:''}   
    case constant.CHANGE_MAILBAG_TAB:
      return { ...state,activeMailbagTab: action.data}
    case constant.SAVE_SELECTED_MAILBAGS_INDEX:
      return {...state,selectedMailbagsIndex: action.indexes}
    case constant.CHANGE_ADD_MAILBAG_TAB:
        return { ...state,activeMailbagAddTab: action.currentTab}
    case constant.ADD_MAILBAG_POPUPCLOSE:
      return {...state,addMailbagShow:false,existingMailbags:[],existingMailbagPresent:false,disableForModify:false}
    case constant.SCREENLOAD_ADD_MODIFY_MAILBAG:
      if (action.mode === constant.ADD_MAILBAG) {
            return{...state,mailbagOneTimeValues:action.oneTimeValues,addMailbagShow:true,density:action.density,addModifyFlag:action.mode,selectedMailbags:[],currentDate:action.currentDate, currentTime:action.currentTime,defaultWeightUnit:action.defWeightUnit,previousRowWeight:action.defWeightUnit,stationVolUnt:action.stationVolUnt}
       }
      if (action.mode === constant.MODIFY_MAILBAG) {  
        return {...state, mailbagOneTimeValues:action.oneTimeValues,selectedMailbags:action.selectedMailbags,addMailbagShow:true,density:action.density,addModifyFlag:action.mode,defaultWeightUnit:action.defWeightUnit,stationVolUnt:action.stationVolUnt,currentDate:action.currentDate, currentTime:action.currentTime}
       }
    case constant.ADD_MAILBAG_SUCCESS:
      return{...state,addMailbagShow:false,disableForModify:false}
    case constant.UPDATE_MAILBAGS_IN_ADD_POPUP:
      return{...state,updatedMailbags:action.updatedMailbags,previousRowWeight:action.updatedMailbags[action.updatedMailbags.length-1].mailbagWeight.displayUnit}
    case constant.SHOW_IMPORT_POPUP :
      return{...state,showImportPopup:true,containerJnyID:action.containerJnyID}
    case constant.CLOSE_IMPORT_POPUP :
      return{...state,showImportPopup:false,addModifyFlag: constant.ADD_MAILBAG,selectedContainer:action.selectedContainer,importedMailbagDetails:action.importedMailbagDetails}
    case constant.IMPORT_MAILBAGS:
       return {...state,importedMailbagDetails:action.importMailbags,showImportPopup:false}
    case  constant.REMARKS_POPUP_OPEN:
       return {...state,selectedMailbags:action.selectedMailbags,remarkspopup:true}
    case  constant.REMARKS_CLOSE:
      return {...state,remarkspopup:false,selectedMailbags:[]}
    case  constant.SCAN_TIME_POPUP_OPEN:
       return {...state,selectedMailbags:action.selectedMailbags,currentDate:action.currentDate, currentTime:action.currentTime,scanTimePopup:true}
    case  constant.SCAN_TIME_CLOSE:
       return {...state,scanTimePopup:false,selectedMailbags:[]}
    case  constant.DELETE_MAILBAGS:
       return {...state,selectedMailbags:action.selectedMailbags}
    case constant.FLIGHT_SUCCESS:
       return {...state,flightValidation:action.flightValidation,selectedMailbags: action.selectedMailbags,isValidFlight:false,pou:action.pou,pous:action.pous, flightnumber:action.flightnumber}       
   case constant.LIST_REASSIGN_CONTAINER:
       return {...state,reassignContainers:action.containerDetails, isValidFlight:false,reassignFilterType:action.assigned,destination:action.destination, carrierCode:action.inputCarrierCode}
    case constant.NO_CONTAINER:
      return {...state, reassignContainers:[], destination:action.inputDestination, isValidFlight:false, reassignFilterType:action.assigned, carrierCode:action.inputCarrierCode}  
       case constant.REASSIGN_POPUP_OPEN:
      return{...state,showReassign:true,selectedMailbags:action.selectedMailbags,currentDate:action.currentDate, currentTime:action.currentTime};
      case constant.CONTAINER_REASSIGN_SUCCESS:
      return {...state, isValidationSuccess:false,showReassign:false, reassignContainers:[],isValidFlight:true,
              reassignFilterType:'F',destination:'',carrierCode:'', flightnumber:{}};
      case constant.CLOSE_POP_UP :
      return {...state,showReassign:false,reassignContainers:[], reassignFilterType:'F', isValidFlight:true};
      case constant.VALIDATE_FLIGHT:
       return {...state,flightValidation:action.flightValidation,selectedMailbags: action.selectedMailbags}
     case constant.MAILBAG_RETURN_OPEN:
       if (action.mode === constant.RETURN) {
        return {...state,mailbagReturnDamageFlag:'Return',mailbagReturnPopup:true,selectedMailbags:action.selectedMailbags,postalCodes:action.postalAdministrations&&action.postalAdministrations,selectedMail:action.selectedMail}
      }
      if (action.mode === constant.DAMAGE_CAPTURE) {  
        return {...state,mailbagReturnDamageFlag:'Damage',mailbagReturnPopup:true,selectedMailbags:action.selectedMailbags,postalCodes:action.postalAdministrations&&action.postalAdministrations,selectedMail:action.selectedMail}
      }
   case constant.RETURN_POPUP_CLOSE :
    return {...state,mailbagReturnPopup:false,selectedMailbags:[],mailbagReturnDamageFlag:''}
   case constant.RETURN_CLOSE :
     return{...state,mailbagReturnPopup:false}
   case constant.LIST_FILTER:
      return {...state,tableFilter:action.mailbagFilter} 
   case constant.CLEAR_TABLE_FILTER :
      return {...state,tableFilter:{}}; 
    case constant.EXISTING_MAILBAG_POPUP_OPEN :
      return{...state,addMailbagShow:true,existingMailbags:action.existingMailbags,existingMailbagPresent:true, addedMailbags:action.addedMailbags,activeMailbagAddTab:'NORMAL_VIEW'}
    case constant.EXISTING_MAILBAG_POPUP_CLOSE :
      return{...state,addMailbagShow:false,existingMailbags:[],existingMailbagPresent:false,disableForModify:false};
    case constant.APPLY_MAILBAG_SORT :
      return{...state,mailBags:action.mailbagDataAfterSort};
    case constant.APPLY_MAILBAG_FILTER :
      return{...state,tableFilter:action.mailbagfilter};
    case constant.APPLY_MAILBAG_DSN_FILTER :
      return{...state,dsnFilterValues:action.dsnFilterValues};
    case constant.SAVE_SELECTED_CONTAINER_INDEX : 
      return {...state,tableFilter:{},dsnFilterValues:{},mailbagsDSNDisplayPage:1}
    case constant.LIST_CONTAINER : 
      return {...state,tableFilter:{},dsnFilterValues:{},mailbagsDSNDisplayPage:1}
      case constant.CLEAR_REASSIGN_FORM:
        return {...state, reassignContainers:[],destination:'',
        carrierCode:'',
        flightnumber:{},
        transferFilterType:action.transferFilterType,
        reassignFilterType:action.reassignFilterType,
        isValidFlight:true};  
    case constant.MAIL_CLEAR :
      return {...state,mailBags:null,mailBagsdsnView:null}

    case constant.TRANSFER_POPUP_OPEN:
      return{...state,showTransfer:true,selectedMailbags:action.selectedMailbags,currentDate:action.currentDate, currentTime:action.currentTime,ownAirlineCode:action.ownAirlineCode,partnerCarriers:action.partnerCarriers};
    case constant.LIST_CONTAINERS_POPUP:
      return { ...state, transferContainers:action.containerVosToBeReused,
                  flightDetailsToBeReused:action.flightDetailsToBeReused,addContainerButtonShow:action.addContainerButtonShow,destination:action.destination,containerDisplayPageForTransfer:action.containerDisplayPage};
    case constant.TRANSFER_POPUP_CLEAR:
        return { ...state, transferContainers:action.transferContainers,transferFilterType:action.transferFilterType,
          flightDetailsToBeReused:action.flightDetailsToBeReused,addContainerButtonShow:action.addContainerButtonShow ,destination:null, flightnumber:{}};
    case constant.CLEAR_TRANSFER_FORM: 
          return {...state, flightnumber:action.flightnumber} 
    case constant.TRANSFER_SAVE:
          return { ...state, showTransfer:false,transferFilterType:'C' };
    case constant.CHECK_MODIFIED_MAIL:
          return { ...state, showTransfer:false,disableForModify:action.disableForModify }; 
	case constant.APPLY_DSN_FILTER:
      return{...state,mailBagsdsnView:action.dsnDataAfterFilter,dsnFilterValues:action.dsnFilterValues};
      case constant.LIST_DETAILS_FLIGHT:
      return{...state,importedMailbagDetails:[]};
      case constant.LIST_DETAILS_CARRIER:
      return{...state,importedMailbagDetails:[]};      
      case constant.CLEAR_FILTER:
      return{...state,importedMailbagDetails:[]};      
    default:
      return state
  }
}


export default mailbagReducer;
