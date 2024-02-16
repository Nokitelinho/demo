import * as constant from '../constants/constants'
const intialState = {
  containerData:[],
  containerDetailsInVo:[],
  containerDetail:{},
  arrivalDetailsInVo:[],
  saveClose:false,
  activeMailbagTab:'MAIL_VIEW',
  attachClose:false,
  changeContainerClose:false,
  attachAwbDetails:[],
  containerDetailsToBeReused:[],
  containerVosToBeReused:[],
  transferContainerClose:false,
  attachRoutingClose:false,
  attachRoutingDetails:[],
  createMailInConsignmentVOs:[],
  filterValues:{},
  containerDataAfterFilter:[],
  filterFlag:false,
  oneTimeValues:{},
  date:'',
  time:'',
  indexDetails:{},
  addContainerClose:false,
  showContReadyForDel:false,
  listEnable:false,
  awbListDisable:false,
  destToPopulate:'',
  showDeliverMail:false,
  isDisabled:false,
  displayPage:1,
  selectedContainerIndex:[],
  showTransferFlag:false,
  scanTime:'',
  showTransferFlightFlag:false,
  scanDate:'',
  reassignFromDate:'',
  reassignToDate:'',
  containerDetails:[],
  flightDetails:[],
  selectedFlightIndexReassign:[],
  containerList:[],
  uldTobarrow:'N',
  enableDeliveryPopup: false,
  showRemoveContainerPanel: false,
  retainValidation : false
}

const containerReducer = (state = intialState, action) => {

  switch (action.type) {  
     case constant.LIST_SUCCESS:
    return {...state,  containerData:action.containerDetails,containerDetail:action.containerDetail,filterValues:{},
            time:action.time,date:action.date,displayPage:action.containerDetails&&action.containerDetails.pageNumber}
    case constant.CON_LIST_ON_PAG_NXT:
    return {...state,  containerData:action.containerDetails,containerDetail:action.containerDetail,
            time:action.time,date:action.date,displayPage:action.containerDetails.pageNumber}
    case constant.LIST_CONTAINERS:
    return {...state,  containerData:action.containerDetails,containerDetail:action.containerDetail, selectedContainerIndex:[],
            time:action.time,date:action.date,displayPage:action.containerDetails.pageNumber, containerList:action.containerList}
    case constant.MAIL_DSN_LIST:
    return {...state,  containerDetail:action.containerDetail}
    case constant.CHANGE_FLIGHT:
     return {...state,  changeContainerClose:action.changeContainerClose,
                containerDetailsToBeReused:action.containerDetailsToBeReused}
    case constant.UNDO_ARRIVAL:
      return state
    case constant.ACQUIT_ULD:
      return {...state, }
    case constant.ARRIVE_MAIL:
      return state
    case constant.DELIVER_CONTAINER:
      return {...state, showDeliverMail:action.showDeliverMail,
        enableDeliveryPopup: action.showDeliverMail ? state.enableDeliveryPopup: false}
    case constant.SET_DELIVERY_POPUP_CONT_FIELDS_STATUS:
      return {
        ...state,
        enableDeliveryPopup: action.data
      }
    case constant.ATTACH_ROUTING:
      return {...state,  attachRoutingDetails:action.attachRoutingDetails,attachRoutingClose:action.attachRoutingConClose,
                createMailInConsignmentVOs:action.createMailInConsignmentVOs,
               containerDetailsToBeReused:action.containerDetailsToBeReused,oneTimeValues:action.oneTimeValues,listEnable:false}
    case constant.ATTACH_ROUTING_LIST:
      return {...state,  attachRoutingDetails:action.attachRoutingDetails,listEnable:action.listEnable,
                consignmentDocumentVO:action.consignmentDocumentVO,oneTimeValues:action.oneTimeValues}
    case constant.ATTACH_ROUTING_SAVE:
      return {...state,  attachRoutingClose:action.attachRoutingConClose,listEnable:false,consignmentDocumentVO:[]}
      case constant.CHANGE_FLIGHT_SAVE:
      return {...state,  changeContainerClose:action.changeContainerClose}
    case constant.ATTACH_AWB:
      return {...state,  attachAwbDetails:action.attachAwbDetails,attachClose:action.attachConClose,
                containerDetailsToBeReused:action.containerDetailsToBeReused,awbListDisable:false}
    case constant.ATTACH_AWB_LIST_CON:
      return {...state,  attachAwbDetails:action.attachAwbDetails,attachClose:action.attachConClose,
                containerDetailsToBeReused:action.containerDetailsToBeReused,awbListDisable:true}
    case constant.ATTACH_AWB_LIST_MAL:
      return {...state,  attachAwbDetails:action.attachAwbDetails,
                containerDetailsToBeReused:action.containerDetailsToBeReused,awbListDisable:true}
    case constant.TRANSFER:
       return {...state, transferContainerClose:action.transferContainerClose,
                containerDetailsToBeReused:action.containerDetailsToBeReused,destToPopulate:action.containerDetailsToBeReused[0]&&
                      action.containerDetailsToBeReused[0].destination?action.containerDetailsToBeReused[0].destination:''}
    case constant.TRANSFER_CLOSE:
       return {...state, transferContainerClose:action.transferContainerClose}
    case constant.CLEAR_FILTER:
      return intialState
    case constant.READY_FOR_DELIVERY:
      return state
    case constant.MAIL_MODE:
      return{...state,activeMailbagTab:action.mode}  
   /* case 'SELECT_ALL_CONTAINER':
      return {...state, check:action.check,arrayOfIndices:action.arrayOfIndices}*/
    case constant.ADD_SUCCESS:
      return constant.ATTACH_AWB_CLOSE
    case constant.CHANGE_DETAILPANEL_MODE:
      return { ...state, containerData:action.containerDetails,
        containerDetailsInVo:action.containerDetailsInVo,
        arrivalDetailsInVo:action.arrivalDetailsInVo,
            time:action.time,date:action.date,displayPage:action.containerDetails.pageNumber };
    case constant.APPLY_CONTAINER_FILTER:
      return { ...state,filterValues:action.filterValues}
    case constant.CLEAR_CONTAINER_FILTER:
      return { ...state,filterValues:{}}
    case constant.UPDATE_MAIL_DETLS:
        return { ...state, containerData:action.containerDetails,
        containerDetailsInVo:action.containerDetailsInVo,
        arrivalDetailsInVo:action.arrivalDetailsInVo };
    case constant.DAMAGE_CAPTURE:
      return {...state,  containerDetailsToBeReused:action.containerDetailsToBeReused}
    case constant.ATTACH_AWB_CLOSE:
      return { ...state,attachClose:action.attachConClose}
    case constant.ATTACH_ROUT_CLOSE:
      return {...state,attachRoutingClose:action.attachRoutingConClose}  
    case constant.LIST_CONTAINERS_POPUP:
        return { ...state, containerVosToBeReused:action.containerVosToBeReused };  
    case constant.SELECT_ALL:
        return { ...state, containerData:action.containerDetails,indexDetails:action.indexDetails }; 
    case constant.CLEAR_SELECT_ALL:
          return { ...state,indexDetails:{}};
    case constant.APPLY_CONTAINER_SORT:
        return { ...state, containerData:action.containerDataAfterSort };
    case constant.ADD_CONTAINER_SHOW :
          return {...state, addContainerClose:action.data,isDisabled:false };
    case constant.READY_FOR_CONT_DEL_CLOSE:
      return { ...state, showContReadyForDel:action.showContReadyForDel };   
      case constant.CLEAR_ATTACHROUTING_DATA:
      return {...state,attachRoutingDetails:[],consignmentDocumentVO:[],listEnable:true};
      case constant.ATTACH_AWB_SAVED:
      return { ...state, attachClose:false}    
    case constant.CON_LIST_ALONE:
          return {...state, containerData:action.containerDetails}
    case constant.POPULATE_ULD:
        return {...state, isDisabled:action.args}
    case constant.SAVE_SELECTED_CONTAINER_INDEX:
        return {...state, selectedContainerIndex:action.selectedIndex, retainValidation:action.retainValidation}
    case constant.VALIDATEFORTRANSFER_SUCCESS:
        return {...state,showTransferFlag:true,scanTime:action.currentTime, scanDate:action.data.results[0].transferForm.scanDate, containerDetails:action.selectedContainerData}; 
    case constant.VALIDATEFORTRANSFER_FLIGHT_SUCCESS:
        return {...state,showTransferFlightFlag:true, scanDate:action.data.results[0].transferForm.scanDate, scanTime:action.data.results[0].transferForm.mailScanTime, reassignFromDate:action.data.results[0].transferForm.reassignFromDate,reassignToDate:action.data.results[0].transferForm.reassignToDate, containerDetails:action.selectedContainerData,uldTobarrow:action.uldTobarrow}; 
    case constant.LIST_DETAILS_TRANSFER_FLIGHT:
        return { ...state, flightDetails:action.mailflightspage}
        case constant.LIST_CAPACITY_TRANSFER_FLIGHT:  
     {
       return {
         ...state,
         flightDetails: addSegmentDetails(state.flightDetails,action.flightCapacityDetails)
        }
      
     }
    case constant.SAVE_SELECTED_FLIGHT_INDEX:
       return {...state,selectedFlightIndexReassign: action.indexes}
    case constant.CLEAR_TRANSFER_FLIGHT_PANEL:
       return {...state,selectedFlightIndexReassign: [],flightDetails:{} }    
    case constant.TRANSFERSAVE_SUCCESS:
       return {...state,showTransferFlag:false, showTransferFlightFlag:false, flightDetails:[], selectedFlightIndexReassign:[], selectedContainerIndex:[]};
    case constant.CLOSE:
       return {...state,showTransferFlag:false, flightDetails:[], selectedFlightIndexReassign:[], showTransferFlightFlag:false};      
    case constant.TRANSFER_POPUP_CLEAR:
       return { ...state,containerVosToBeReused:[]  }
    case constant.EMPTY_FLIGHTS: 
       return { ...state, 
           containerData:{},selectedContainerIndex:[]
           }
    case constant.REMOVE_CONTAINER:
        return { ...state, 
          showRemoveContainerPanel:action.showRemoveContainerPanel 
        };
    case constant.TRANSFER_SAVE:
    return { ...state, containerVosToBeReused:[] };
    case constant.CLEAR_CONTAINERDETAILS_TRANSFER:
    return {...state,containerVosToBeReused:[]} 
    default:
      return state
  }
}


export default containerReducer;

function addSegmentDetails(flightDetails, flightCapacityDetails) {
  let flights=flightDetails.results;
  flights = flights.map((flight) => {
    let flightpk=flight.flightNumber + flight.flightDate
    return {
      ...flight,
      capacityDetails: flightCapacityDetails[flightpk]
  };
  })
  flightDetails.results=flights;
  return flightDetails;
}