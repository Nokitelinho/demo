


const intialState = {
    relisted : false, 
    screenMode:'initial',
    oneTimeValues:{},
    displayPage:'',
    pageSize:100,
    hbaType:null,
    position:null,
    uldReferenceNumber:'',
    operationFlag:'',
    flightNumber:'',
    flightSequenceNumber:'',
    legSerialNumber:'',
    carrierId:'',
    carrierCode:'',
    assignedPort:'',
    containerNumber:'',
    type:''
}
const commonReducer = (state = intialState, action) => {

    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
        return {...state,relisted : true,oneTimeValues:action.data.oneTimeValues, hbaType:action.data.hbaType, position:action.data.position, operationFlag:(action.data.hbaType && action.data.position)?'U':'I'};
        case 'TOGGLE_FILTER':
        return {...state,screenMode: action.screenMode }  
        case 'CHANGE_POSITION':
        return {...state,position:action.data.position}
        case 'CHANGE_HBA_TYPE':
        return {...state,hbaType:action.data.hbaType}
        case 'CONTAINER_DETAILS_UPDATE'  :
      return {...state, uldReferenceNumber:action.data.uldReferenceNumber, flightNumber:action.data.flightNumber,
        flightSequenceNumber:action.data.flightSequenceNumber,legSerialNumber:action.data.legSerialNumber,
        carrierId:action.data.carrierId,carrierCode:action.data.carrierCode,
        assignedPort:action.data.assignedPort,containerNumber:action.data.containerNumber,
        type:action.data.type}
        default:
            return state
    }
}

export default commonReducer;
