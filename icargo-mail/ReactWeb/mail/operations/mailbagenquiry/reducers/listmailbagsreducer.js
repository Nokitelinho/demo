const intialState ={
   mailbagsdetails:[],
   selectedMailbags:[],
   selectedContainer:{},
   damageDetails:[],
   containerDetails:[],
   onwardRouting:[],
   popupMode: '',
   selectedIndex:'',
   flightValidation:null,
   isValidFlight:true,
   selectedMailbagIndex:[],
   pous:null,
   pou:null,
   destination:'',
   carrierCode:'',
   reassignFilterType:'F',
   flightnumber:{},
   transferFilterType :'C',
   pageSize:100,
   showModifyPopup:false
  }



const listmailbagsreducer = (state = intialState, action) => {
  switch (action.type) {
    case 'LIST_SUCCESS':
      return {...state,mailbagsdetails:action.mailbags,pageSize:action.pageSize, transferFilterType :'C'} 
    case 'DAMAGE_SUCCESS':
      return {...state,damageDetails:action.damagedMailbags,popupMode:'DAMAGE'}
    case 'VIEW_DAMAGE':
      return {...state,selectedMailbags:action.selectedMailbags}   
    case 'CLEAR_FILTER':
      return intialState;
    case 'CLEAR_TABLE':
      return intialState;   
    case 'FLIGHT_SUCCESS':
      return {...state,flightValidation:action.flightValidation,selectedMailbags: action.selectedMailbags,pous: action.pous,pou: action.pou,isValidFlight:false, flightnumber:action.flightnumber,transferFilterType :'F'}       
    case 'LIST_CONTAINER':
      return {...state,containerDetails:action.containerDetails, destination:action.destination, carrierCode:action.carrierCode, reassignFilterType:action.assigned, isValidFlight:false}   
    case  'NO_CONTAINER' :
      return {...state,containerDetails:[], destination:action.inputDestination, isValidFlight:false, reassignFilterType:action.assigned, carrierCode:action.inputCarrierCode}   
    case  'DO_RETURN' :
      return {...state,damageDetails:action.damagedMailbags}   
    case  'DO_REASSIGN' :
      return {...state}  
    case  'DO_TRANSFER' :
      return {...state}  
    case 'ON_ROW_SELECT':
      return {...state,selectedMailbags : action.selectedMailbagsArr}   
    case  'NO_DAMAGE_DATA' :
      return {...state,selectedMailbags:[]};
    case 'REASSIGN_SUCCESS':
      return {...state, containerDetails:[]}
    case 'TRANSFER_SUCCESS':
      return {...state, containerDetails:[],destination:'',
      carrierCode:'',
      flightnumber:{}
    } 
    case  'ERR_DATA' :
      return {...state}; 
    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailbagIndex:action.indexes}
    case 'CLOSE_POP_UP':
        return {...state,containerDetails:[], transferFilterType :'C', reassignFilterType:'F', isValidFlight:true,selectedMailbags:[]};
    case 'RETURN_SUCCESS':
        return {...state, containerDetails:[]}; 
    case 'CLEAR_REASSIGN_FORM':
        return {...state, containerDetails:[],destination:'',
        carrierCode:'',
        flightnumber:{},
        transferFilterType:action.transferFilterType,
        reassignFilterType:action.reassignFilterType,
        isValidFlight:true};   
    case 'CLEAR_CONTAINERDETAILS_TRANSFER':
        return {...state,containerDetails:[],isValidFlight:true} 
        case 'SHOW_MODIFY_POPUP':
        return {...state,selectedMailbags:action.selectedMailbags,showModifyPopup:true}
        case 'MODIFY_ORG_AND_DEST_SUCCESS':
        return {...state,showModifyPopup:false}  
             
        case 'CLOSE_MODIFY_POPUP':
        return {...state,showModifyPopup:false} 
        case 'CLOSE_IMAGE_POP_UP' :
        return {...state,containerDetails:[], transferFilterType :'C', reassignFilterType:'F', isValidFlight:true};
   
    default:
      return state
  }
}


export default listmailbagsreducer;