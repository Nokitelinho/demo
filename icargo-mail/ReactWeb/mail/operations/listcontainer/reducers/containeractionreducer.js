const intialState ={
  reassignContainer:{},
  toFlightDetails:{},
  transferForm:{},
  selectedContainerForOffload:null,
  showTransferFlag:false,
  showReassignFlag:false,
  reassignFlag:null,
  selectedContainer:null,
  scanDate:'',
  scanTime:'',
  destination:'',
  showReassignFlightFlag:false,
  showTransferFlightFlag:false,
  flightDetails:[],
  selectedFlightIndexReassign:[],
  reassignFromDate:'',
  multipleFlag:false,
  reassignToDate:'',
  containerDetails:[],
  uldTobarrow:'N',
  reassignFilter:'C',
  selectedContainerForRelease:null
}



const containeractionreducer = (state = intialState, action) => {
  switch (action.type) { 
    case 'REASSIGN_SUCCESS':
     return {...state, toFlightDetails:action.data.reassignContainer,showReassignFlag:false,showReassignFlightFlag:false, flightDetails:[], selectedFlightIndexReassign:[]}; 	
    case 'VIEWMAILBAG_SUCCESS':
     return {...state}; 
    case 'UNASSIGN_SUCCESS':
     return {...state}; 	
    case 'VALIDATEFORTRANSFER_SUCCESS':
     return {...state,showTransferFlag:true,scanTime:action.currentTime}; 
    case 'VALIDATEFORTRANSFER_FLIGHT_SUCCESS':
     return {...state,showTransferFlightFlag:true, scanDate:action.data.results[0].transferForm.scanDate, scanTime:action.data.results[0].transferForm.mailScanTime, reassignFromDate:action.data.results[0].transferForm.reassignFromDate,reassignToDate:action.data.results[0].transferForm.reassignToDate, containerDetails:action.selectedContainerData,uldTobarrow:action.data.results[0].transferForm.uldToBarrow}; 
    case 'TRANSFERSAVE_SUCCESS':
     return {...state,showTransferFlag:false, showTransferFlightFlag:false}; 
    case 'CLOSE':
     return {...state,showTransferFlag:false, showReassignFlag:false,showReassignFlightFlag:false, flightDetails:[], selectedFlightIndexReassign:[], showTransferFlightFlag:false};
    case 'VALIDATECONTAINER_SUCCESS':
      return {...state, reassignFilter:'C',showReassignFlag:true,reassignFlag:action.data.reassignFlag,selectedContainer:action.data.selectedContainer, scanDate:action.data.results[0].reassignContainer.scanDate, scanTime:action.data.results[0].reassignContainer.mailScanTime} ;
    case 'SELECT_CONTAINER_OFFLOAD':
      return {...state,selectedContainerForOffload:action.selectedContainer};
      case  'VALIDATE_CONTAINER_REASSIGN_FLIGHT_SUCCESS':
       return {...state, reassignFilter:'F',showReassignFlightFlag:true,reassignFlag:action.data.reassignFlag,selectedContainer:action.data.selectedContainer, scanDate:action.data.results[0].reassignContainer.scanDate, scanTime:action.data.results[0].reassignContainer.mailScanTime, reassignFromDate:action.data.results[0].reassignContainer.fromDate,reassignToDate:action.data.results[0].reassignContainer.reassignToDate,multipleFlag:action.multipleFlag} ;
       case 'LIST_DETAILS_REASSIGN_FLIGHT':
       return { ...state, flightDetails:action.mailflightspage}
       case 'LIST_CAPACITY_REASSIGN_FLIGHT':  
    {
      return {
        ...state,
        flightDetails: addSegmentDetails(state.flightDetails,action.flightCapacityDetails)
       }
     
    }
    case 'SAVE_SELECTED_FLIGHT_INDEX':
      return {...state,selectedFlightIndexReassign: action.indexes}
      case 'CLEAR_REASSIGN_FLIGHT_PANEL':
      return {...state,selectedFlightIndexReassign: [],flightDetails:{} }
      case 'CLEAR_TRANSFER_FLIGHT_PANEL':
      return {...state,selectedFlightIndexReassign: [],flightDetails:{} }
      case 'RELEASE_SUCCESS':
     return {...state};
     case 'SELECT_CONTAINER_RELEASE':
     return {...state,selectedContainerForRelease:action.selectedContainer};
    default:
     return state;
  }
}


export default containeractionreducer;

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