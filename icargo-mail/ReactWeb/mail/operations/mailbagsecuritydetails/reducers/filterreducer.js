const intialState = {


    screenMode: 'initial',
    mailbagId: '',
    origin: '',
    destination: '',
    airportCode:'',
    filterList: false,
    screeningLocation: '',
    securityStatusParty:'',
	showPopover: false,
    timeZone:'',
    warningFlag: false
}


const filterReducer = (state = intialState, action) => {

    switch (action.type) {
        case 'CHANGE_MODE':
            return { ...state, editMode: true, screenMode: action.mode }
        case 'LIST_SUCCESS':
            return { ...state, screenMode: 'display',showPopover: false, filterList: 'true', mailbagId: action.mailbagId, origin: action.origin, destination: action.destination,screeningLocation : action.airportCode,securityStatusParty: action.StatusParty,timeZone:action.timeZone}

        case 'CLEAR_FILTER':
            return intialState;
			
        case 'POP_OVER_OPEN':
            return { ...state, showPopover: true }
        case 'POP_OVER_CLOSE':
            return { ...state, showPopover: false }
        case 'WARNING_TRIGGER':
            return { ...state, warningFlag:true}
        case 'WARNING_TRIGGER_OFF':
            return { ...state, warningFlag:false}
        default:
            return state

    }
}


export default filterReducer;
