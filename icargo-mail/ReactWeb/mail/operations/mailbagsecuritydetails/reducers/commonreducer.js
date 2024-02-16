


const intialState = {

    screeningDetailPopupShow: false,
    consignorDetailsPopupShow: false,
    selectedMailbagsIndex: [],
    initialValues: [],
    isEdit : 'N',
    isDelete :'N',
    screeningLocation :'',
    securityStatusParty:'',
    securityStatusDate:'',
   time:'',
    status:''
}
const commonReducer = (state = intialState, action) => {

    switch (action.type) {
        case 'LIST_SUCCESS':
            return { ...state, screeningLocation : action.airportCode, securityStatusParty: action.StatusParty,securityStatusDate:action.StatusDate,time:action.StatusTime}

        case 'ADD_SCREENING_DETAILS':
            return { ...state, screeningDetailPopupShow: true, initialValues: {screeningLocation:state.screeningLocation,securityStatusParty: state.securityStatusParty,securityStatusDate:action.StatusDate,time:action.time,result:'P'}}

        case 'ADD_CONSIGNOR_DETAILS':
            return { ...state, consignorDetailsPopupShow: true, initialValues: null }

        case 'CLEAR_FILTER':
            return intialState;

        case 'ADD_CONTAINER_POPUPCLOSE':
            return { ...state, screeningDetailPopupShow: false, consignorDetailsPopupShow: false, initialValues: null }

        case 'SELECTED_MAILBAG_INDEX':
            return { ...state, selectedMailbagIndex: action.indexes }

        case 'EDIT_SCREEN_DETAILS':
            return { ...state, initialValues: action.screeningDetails, screeningDetailPopupShow: true,isEdit:'Y' }

        case 'EDIT_CONSINOR_DETAILS':
            return { ...state, initialValues: action.consignerDetails, consignorDetailsPopupShow: true,isEdit:'Y' }
        
        case 'DELETE_SCREEN_DETAILS':
            return { ...state, initialValues: action.screeningDetails,isDelete:'Y' }

        case 'DELETE_CONSINOR_DETAILS':
            return { ...state, initialValues: action.consignerDetails,isDelete:'Y' }
        
        case 'SAVE_SUCCESS':
            return {...state,screeningDetailPopupShow:false,isEdit:'N'} 
        
        case 'SAVE_SUCCESS_CONSIGNOR':
            return {...state,consignorDetailsPopupShow:false,isEdit:'N'} 
            
        default:
            return state
    }
}

export default commonReducer;
