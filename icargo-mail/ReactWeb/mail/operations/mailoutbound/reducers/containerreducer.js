import * as constant from '../constants/constants';
const intialState ={
   vieMode:'initial',
   flightContainers:[],
   carrierContainers:[],
   mailbags:[],
   // this saves the selected flight/carrier. 
   mailAcceptance:'',
   mailcontainer:'',
   flightsArray:[],
   carriersArray:[],
   //index of selected containers
   selectedContainerIndex:[],
   containerTableFilter:{},
   //if no containers added, the page number should be set to 1. Otherwise,on adding a new container the container display page will be null
   containerDisplayPage:'1',
   // to add Container
   addContainerShow:false,
   selectedContainer:null,
   //add/modeify container
   selectedContainerforModify:'',
   //This is  to identify if the add container popup is opened on 'ADD' or 'MODIY' mode
   containerActionMode:'',
   enableContainerSave:false,
   selectedContCount:'',
   containerinerOneTimeValues:[],
   newContainerDetails:[],
   hasAddContainerPanelLoaded:'',
   selectedFlightIndex:'',
   PABuiltCheckFlag:'',
   toFlightDetails:{},
   tableFilter:{},
   //reassign
   isValidationSuccess:false,
   //attach
   attachAwbDetails:[],
   attachAwbOneTimeValues:[],
   attachawbListSuccess:false,
   attachClose:false,
   containerDetailsToBeReused:[],
   //attachRouting
   attachroutingListSuccess:false,
   attachRoutingClose:false,
   attachRoutingDetails:[],
   attachRoutingOneTimeValues:[],
   createMailInConsignmentVOs:[],
  //reassign
  showReassignFlag:false,
  sort: {
  },
  containers:[],
  scanDate:'',
  scanTime:'',
  destination:'',
  showReassignFlightFlag:false,
  flightDetails:[],
  selectedFlightIndexReassign:[],
  reassignFromDate:'',
  reassignToDate:'',
  index:'',
  multipleFlag:false,
  uldToBarrowAllow:'N',
  defaultPou:'',
  isBarrow : false,
  reassignFilter:'',
  modifiedMailbags:[],
  showTransferFlightFlag:false
  }

  
const containerReducer = (state = intialState, action) => {
  switch (action.type) {
   
    case constant.LIST_CONTAINER:
      return{...state,vieMode:'container',containerDisplayPage:action.containerDisplayPage?action.containerDisplayPage:'1',flightContainers:action.mailAcceptance.containerPageInfo ? action.mailAcceptance.containerPageInfo:'',mailAcceptance:action.mailAcceptance,flightsArray:action.flightsArray,selectedFlightIndex:action.flightIndex,selectedContainerIndex:[], containers:action.mailAcceptance.containerPageInfo ? action.mailAcceptance.containerPageInfo.results:'',tableFilter:action.containerFilterValues}
    case constant.LIST_CONTAINER_CARRIER:
      return{...state,vieMode:'container',containerDisplayPage:action.containerDisplayPage?action.containerDisplayPage:'1',carrierContainers:action.mailContainer.containerPageInfo,mailcontainer:action.mailContainer,carriersArray:action.carriersArray,selectedContainerIndex:[]}
    case constant.LIST_MAILBAGS:
      return{...state,selectedContCount:action.selectedContCount,selectedContainer:action.containerInfo}
    case constant.SAVE_SELECTED_CONTAINER_INDEX:
      return {...state,selectedContainerIndex: action.indexes}
    case constant.LIST_CONTAINER_FILTER:
      return {...state,containerTableFilter:action.containerTableFilter} 
    case constant.CLEAR_CONTAINER_FILTER:
    return { ...state,flightContainers: action.containerDataAfterFilter,tableFilter:action.containerFilter}
   case constant.CONTAINER_MODIFY:
   return { ...state,selectedContainerforModify:action.item}
   
    case constant.SCREENLOAD_ADD_MODIFY_CONTAINER:
       if (action.mode === constant.ADD_CONTAINER) {
             return{...state,mailAcceptance:action.mailAcceptance?action.mailAcceptance:'',containerinerOneTimeValues:action.oneTimeValues,addContainerShow:true,PABuiltCheckFlag:false,containerActionMode:action.mode,selectedContainerforModify:'',enableContainerSave:false, defaultPou:action.mailAcceptance.pouList && action.mailAcceptance.pouList[0]?action.mailAcceptance.pouList[0]:''}
        }
       if (action.mode === constant.MODIFY) {  
             return{...state,mailAcceptance:action.mailAcceptance?action.mailAcceptance:'',containerinerOneTimeValues:action.oneTimeValues,addContainerShow:true,PABuiltCheckFlag:action.selectedContainer.paBuiltFlagValue,containerActionMode:action.mode, selectedContainerforModify:action.selectedContainer, enableContainerSave:true,uldToBarrowAllow:action.uldToBarrowAllow,scanDate:action.currentDate,scanTime:action.currentTime, isBarrow:action.isBarrow,modifiedMailbags:action.modifiedMailbags}
        }
    case constant.LIST_NEW_CONTAINER_SUCESS:
    return{...state,enableContainerSave:true,selectedContainerforModify:action.selectedContainer}
    case constant.ADD_CONTAINER_POPUPCLOSE:
      return {...state,addContainerShow:false,selectedContainerforModify:action.item, isBarrow:false}
    case constant.POPULATE_MAILBAGS:
     return{...state,newContainerDetails:action.newContainersDetails}
    case constant.ADDCONTAINER_LOADED:
     return {...state,hasAddContainerPanelLoaded: action.loaded }
    case constant.PABUILT_FLAG:
      return {...state, PABuiltCheckFlag:action.data}  
    case constant.REASSIGN_SUCCESS:
      return {...state, isValidationSuccess:false,toFlightDetails:action.data.reassignContainer,showReassignFlag:false,showReassignFlightFlag:false,containerDetailsToBeReused:[],flightDetails:{}}; 
    case constant.UNASSIGN_SUCCESS:
      return {...state};

    case constant.ATTACH_AWB:
      return {...state,  attachAwbDetails:action.attachAwbDetails,attachClose:true,
                containerDetailsToBeReused:action.containerDetailsToBeReused,attachAwbOneTimeValues:action.attachAwbOneTimeValues}
    case constant.ATTACH_AWB_LIST:
      return {...state,  attachAwbDetails:action.attachAwbDetails,attachClose:action.attachConClose,
        containerDetailsToBeReused:action.containerDetailsToBeReused,attachawbListSuccess:true}
    case constant.ATTACH_AWB_SAVE:
      return { ...state,attachClose:false,attachawbListSuccess:false}
    case constant.ATTACH_AWB_CLOSE:
      return { ...state,attachClose:false,attachawbListSuccess:false}
    case constant.ATTACH_ROUTING:
      return {...state,attachRoutingDetails:action.attachRoutingDetails,attachRoutingClose:true,attachRoutingOneTimeValues:action.oneTimeValues,
                containerDetailsToBeReused:action.containerDetailsToBeReused,createMailInConsignmentVOs:action.createMailInConsignmentVOs }
    case constant.ATTACH_ROUTING_LIST:
      return {...state,  attachRoutingDetails:action.attachRoutingDetails,
                consignmentDocumentVO:action.consignmentDocumentVO,attachroutingListSuccess:true,
                attachRoutingOneTimeValues:action.oneTimeValues}
  
    case constant.ATTACH_ROUTING_SAVE:
      return {...state,  attachRoutingClose:false,attachroutingListSuccess:false}
    case constant.ATTACH_ROUTING_CLOSE:
      return { ...state,attachRoutingClose:false,attachRoutingDetails:{},attachroutingListSuccess:false}
   
    
    case constant.REASSIGN_CLOSE:
      return {...state,showReassignFlag:false,showReassignFlightFlag:false,containerDetailsToBeReused:[],selectedFlightIndexReassign: [],flightDetails:{}};
      case  constant.VALIDATECONTAINER_SUCCESS:
       return {...state,showReassignFlag:true,containerDetailsToBeReused:action.selectedContainerData, scanDate:action.scanDate, scanTime:action.scanTime,reassignFilter:action.reassignFilter} ;
       case constant.UPDATE_CONTAINER_SORT_VARIABLE:
       return { ...state,sort: action.data} 
       case constant.CLEAR_AWB:
       return {...state,  attachAwbDetails:action.attachAwbDetails}
       case constant.CLEAR_ATTACH_ROUTING:
       return {...state,  attachRoutingForm:action.attachRoutingForm,attachRoutingDetails:action.routingDetail,attachroutingListSuccess:false}
       case constant.LIST_DISABLE:
       return{...state,attachroutingListSuccess:true}
       case constant.APPLY_CONTAINER_SORT:
       return { ...state,flightContainers: action.containerDataAfterSort}
       case constant.APPLY_CONTAINER_FILTER:
       return { ...state,flightContainers: action.containerDataAfterFilter,tableFilter:action.containerDataAfterFilter}
       case  constant.VALIDATE_CONTAINER_REASSIGN_FLIGHT_SUCCESS:
       return {...state,showReassignFlightFlag:true,containerDetailsToBeReused:action.selectedContainerData, scanDate:action.scanDate, scanTime:action.scanTime,reassignFromDate:action.reassignFromDate,reassignToDate:action.reassignToDate,selectedContainer:action.selectedContainer,multipleFlag:action.multipleFlag,reassignFilter:action.reassignFilter} ;
       case constant.LIST_DETAILS_REASSIGN_FLIGHT:
       return { ...state, flightDetails:action.mailflightspage}
       case constant.LIST_CAPACITY_REASSIGN_FLIGHT:  
    {
      return {
        ...state,
        flightDetails: addSegmentDetails(state.flightDetails,action.flightCapacityDetails)
       
       }
     
    }
    case constant.SAVE_SELECTED_FLIGHT_INDEX:
      return {...state,selectedFlightIndexReassign: action.indexes}
    case constant.CLEAR_REASSIGN_FLIGHT_PANEL:
      return {...state,selectedFlightIndexReassign: [],flightDetails:{} }  
    case constant.BARROW_CHECK:
      return {...state,isBarrow: action.isBarrow }   
    case constant.VALIDATEFORTRANSFER_SUCCESS:
      return {...state,showTransferFlag:true,containerDetailsToBeReused:action.selectedContainerData, scanDate:action.scanDate, scanTime:action.scanTime};     
    case constant.TRANSFER_CLOSE:
    return {...state,showTransferFlag:false,showTransferFlightFlag:false,containerDetailsToBeReused:[],selectedFlightIndexReassign: [],flightDetails:{}};
    case constant.TRANSFERSAVE_SUCCESS:
       return {...state,showTransferFlag:false,showTransferFlightFlag:false,containerDetailsToBeReused:[],selectedFlightIndexReassign: [],flightDetails:{}};
    case  constant.VALIDATE_CONTAINER_TRANSFER_FLIGHT_SUCCESS:
       return {...state,showTransferFlightFlag:true,containerDetailsToBeReused:action.selectedContainerData, scanDate:action.scanDate, scanTime:action.scanTime,reassignFromDate:action.reassignFromDate,reassignToDate:action.reassignToDate,selectedContainer:action.selectedContainer,multipleFlag:action.multipleFlag,reassignFilter:action.reassignFilter} ;
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