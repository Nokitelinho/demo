const intialState ={
   mailbagsdetails:[],
   routingdetails:[],
   consignmentmodel:{},
   dataList:[],
   activeMailbagAddTab:'NORMAL_VIEW',
   screenMode:'initial',
   excelMailbags:new Array({'mailbagId':'', 'ooe':'', 'doe':'','mailCategoryCode':'','mailSubclass':'','year':'','despatchSerialNumber':'','receptacleSerialNumber':'','highestNumberedReceptacle':'','registeredOrInsuredIndicator':'',
   'mailbagWeight':'', 'RI':'', 'Wt':'', 'ULD No':'','mailOrigin':'','mailDestination':'','reqDeliveryTime':'','transWindowEndTime':'','mailServiceLevel':''
   }),
   lastRowData:{},
   addMultipleData:{},
   rsnData:[],
   formData:[],
   isDomestic:false,
   routingList:[],
   typeDisableFlag:true,
   oneTimeMailClass:[],
   navigationFilter:{},
   diableMailbagLevel:true,
   editMode:true,
   fromScreen:'',
   deletedMailbagslist:[]
  }



const listconsignmentreducer = (state = intialState, action) => {
  switch (action.type) {
    case 'LIST_SUCCESS':
      return {...state,mailbagsdetails:action.mailbags,routingdetails:action.routingdetails,consignmentmodel:action.consignment, navigationFilter:action.consignmentFilter,
        dataList:action.dataList,screenMode:'display', excelMailbags:action.excelMailbags, isDomestic:action.isDomestic,oneTimeMailClass:action.oneTimeMailClass,activeMailbagAddTab:action.view,
        typeDisableFlag:(action.consignment.type==='CN46')?false:true,diableMailbagLevel:false,editMode:false}
    case 'CHANGE_ADD_MAILBAG_TAB':
      return { ...state,activeMailbagAddTab: action.currentTab}
    case 'SCREENLOAD_SUCCESS':
      return intialState;
    case 'LAST_ROW':
      return {...state,lastRowData:action.curMailBag} 
    case 'ADD_MULTIPLE_DATA':
      return {...state,addMultipleData:action.addMultipleData, rsnData:action.rsnData}
    case 'ADD_MULTIPLE_MAILBAG':
      return {...state,mailbagsdetails:action.mailbagslist,routingdetails:action.routingList,consignmentmodel:action.consignment}
    case 'UPDATE_MAILBAGS_TABLE':
      return {...state,mailbagsdetails:action.updatedMailbags} 
    case 'CLEAR_FILTER':
      return intialState;
    case 'FORM_DATA':
      return {...state,formData:action.mailbagslist,routingList:action.routingList,deletedMailbagslist:action.deletedMailbagslist};
    case 'DELETE_CONSIGNMENT_SUCCESS':
        return intialState;  
    case 'TYPE_FLAG':
        return {...state,typeDisableFlag:action.data}   
    case 'SCREENLOAD_SUCCESS_NAVIGATION':
        return{...intialState,fromScreen:state.fromScreen,navigationFilter:state.navigationFilter}
    case 'CONSIGNMENT_FILTER':
      return {...state, navigationFilter: {conDocNo: action.data.conDocNo, paCode: action.data.paCode}, fromScreen:action.data.fromScreen}
      case 'CHANGE_MODE' :
      return  {...state,editMode:true,screenMode: action.mode}
    default:
      return state
  }
}


export default listconsignmentreducer;