
const intialState = {
  relisted : false, 
  displayMode:'initial',
  airportCode:'',
  selectedMailbag:[],
  damageDetail:[],
  oneTimeValues:{},
  actualWeight:'',
  showReturn: false,
  showReassign: false,
  showTransfer: false,
  showViewDamage:false,
  tableFilter:{},
  sort: {},
  postalCodes: {},
  scanDate:'',
  scanTime:'',
  ownAirlineCode:'',
  partnerCarriers:{},
  dummyAirportForDomMail:null
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case 'SCREENLOAD_SUCCESS':
      return {...state,airportCode:action.data.mailbagFilter.airportCode,relisted: true,
        oneTimeValues:action.data.oneTimeValues, postalCodes:action.data.postalAdministrations,dummyAirportForDomMail:action.data.dummyAirportForDomMail?action.data.dummyAirportForDomMail:null};
    case 'RETURN':
      return {...state,showReturn:true,showReassign: false, showTransfer: false};
    case 'REASSIGN':
      return {...state,showReturn:false,showReassign: true, showTransfer: false};
    case 'TRANSFER':
      return {...state,showReturn:false,showReassign: false, showTransfer: true};
    case 'CLOSE_POP_UP':
      return {...state,showReturn:false,showReassign: false, showTransfer: false, damageDetail:[],showViewDamage:false};
    case 'LIST_FILTER':
      return {...state,tableFilter:action.mailbagTableFilter};
    case 'CLEAR_TABLE_FILTER':
      return {...state,tableFilter:{}};
    case 'UPDATE_SORT_VARIABLE':
      return {...state, sort: action.data}
    case 'RETURN_SUCCESS':
      return {...state, showReturn: false,damageDetail:action.damagedMailbags,selectedMailbag:action.selectedMailbags}
       case 'NO_DAMAGE_DETAILS':
       return {...state,damageDetail:[],showViewDamage:false};
       case 'VIEW_DAMAGE_SUCCESS':
       return{...state,showViewDamage:true}
    case 'REASSIGN_SUCCESS':
      return {...state, showReassign: false}
    case 'TRANSFER_SUCCESS':
      return {...state, showTransfer:false}
    case 'VALIDATION_SUCCESS':
      return {...state, 
        scanDate:action.scanDate, 
        scanTime:action.scanTime,
        postalCodes:action.paCodes,
        ownAirlineCode:action.ownAirlineCode,
        partnerCarriers:action.partnerCarriers}
    case 'REASSIGN_FAIL':
      return {...state}
    case 'RETURN_FAIL':
        return {...state}
    case 'TRANSFER_FAIL':
        return {...state}
    case 'ULD_EXIST':
        return {...state}
    default:
      return state;
  }
}
 
export default commonReducer;


