

const intialState = {

    screenMode: 'initial',
    mailbagSecurityDetails: [],
    screeningDetails: [],
    consignerDetails: [],
    initialValues: null,
    oneTimeValues: [],
	malseqnum:'',
    origin:'',
    destination:'',
    mailbagId:'', 
    airportCode:'',
    StatusParty:'',
	securityStatusCode:'',
    timeZone:''

}




const listmailbagReducer = (state = intialState, action) => {
    switch (action.type) {

        case 'LIST_SUCCESS':
            return { ...state, screenMode: 'display', mailbagSecurityDetails: action.mailbagSecurityDetails, screeningDetails: action.screeningDetails, consignerDetails: action.consignerDetails, oneTimeValues: action.oneTimeValues,malseqnum:action.malseqnum,origin:action.origin,destination:action.destination,mailbagId:action.mailbagId, airportCode : action.airportCode,StatusParty:action.StatusParty,securityStatusCode:action.securityStatusCode,timeZone:action.timeZone  }

        case 'CLEAR_FILTER':
            return intialState;

        case 'CLEAR_TABLE':
            return intialState;

        default:
            return state
    }
}

export default listmailbagReducer;