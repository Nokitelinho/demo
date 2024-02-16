
import { SCREEN_MODE } from '../constants';
import * as constant from '../constants/constants';

const initialState = {
    activeTab: 'ScreeningDetails',
    addbutton:'',
    addButtonShow:false,
    newScreeningDetails:[],
    ScreeningDetails:[],
    newConsignerDetails:[],
    editPopup:false,
    editedDetail:[],
    rowIndex:'',
    FullPiecesFlag: false,
    ExemptionForm:'',
    ApplicableInfoForm:''
};

const detailspanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case constant.CHANGE_TAB:
        return { ...state,activeTab: action.currentTab, ExemptionForm: action.securityExemptionFormValues,
            ApplicableInfoForm:action.applicaleRegFormvalues}

        // return { ...state,activeTab: action.currentTab}

        case constant.ADD_BUTTON:
        return { ...state,addbutton: action.activebutton, addButtonShow:action.addButtonShow}

        case constant.CLOSE_BUTTON:
        return { ...state,addButtonShow:false,editPopup:false}

        case constant.NEW_SCREENING_DETAILS:  //For closing Add popup on OK click
        return{...state, addButtonShow:false,editPopup:false}

        case constant.NEW_CONSIGNER_DETAILS:  //For closing Add popup on OK click
        return{...state, addButtonShow:false,editPopup:false}

        case 'CLEAR_FILTER':  //In case of new Row added
            return {...initialState};

        case constant.EDIT_DETAILS:
        return{...state,editPopup:true,rowIndex:action.index, EditScreeningDetails:action.EditScreeningDetails?action.EditScreeningDetails:''};

        case constant.NEW_DETAILS:
        return{...state, editPopup:false};

        case constant.FULL_PIECES_FLAG:
        return {...state, FullPiecesFlag:action.data}  

        default:
            return state;
    }
}
export default detailspanelreducer;
