
import { SCREEN_MODE } from '../constants';
import * as constant from '../constants/constants';

const initialState = {
    activeTab: 'ScreeningDetails',
    addbutton:'',
    addButtonShow:false,
    newScreeningDetails:[],
    ScreeningDetails:[],
    ConsignerDetails:[],
    newConsignerDetails:[],
    editPopup:false,
    editPopupcons:false,
    editPopupex:false,
    editPopupreg:false,
    editedDetail:[],
    editedDetailcons:[],
    editedDetailexemption:[],
    rowIndex:'',
    FullPiecesFlag: false,
    ExemptionForm:'',
    ApplicableInfoForm:'',
    isEdit : 'N',
    isDelete :'N',
    securityStatusDate:'',
    time:'',
    rowIndex : 0,
    securityExemption:[],
    newSecurityExemption:[],
    showPopover: false,
    StatusDate:'',
    timedetailspanel:'',
    mstAddionalSecurityInfo:'',
    showPopoverscode: false,
    showITable: true	
};

const detailspanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case 'POP_OVER_OPEN':
        return { ...state, showPopover: true }
        case 'POP_OVER_CLOSE':
        return { ...state, showPopover: false }
        case 'POP_OVER_OPEN_SCODE':
        return { ...state, showPopoverscode: true }
        case 'POP_OVER_CLOSE_SCODE':
        return { ...state, showPopoverscode: false }
		case 'ADD_CONSIGNER_DETAILS':
        return { ...state,addbutton: action.activebutton, addButtonShow:action.addButtonShow}
        case 'ADD_EXEMPTION_DETAILS':
        return { ...state,addbutton: action.activebutton, addButtonShow:action.addButtonShow}
        case 'ADD_REGULATION_DETAILS':
        return { ...state,addbutton: action.activebutton, addButtonShow:action.addButtonShow}
        case constant.ADD_BUTTON:
        return { ...state,addbutton: action.activebutton, addButtonShow:action.addButtonShow,  securityStatusDate:action.StatusDate,time:action.time}

        case constant.CLOSE_BUTTON:
        return { ...state,addbutton:false,addButtonShow:false,editPopup:false,editPopupcons:false,editPopupex:false,editPopupreg:false,showPopover: false,showPopoverscode:false}

        case constant.EDIT_DETAILS:
        return{...state,editPopup:true,rowIndex:action.index, EditScreeningDetails:action.EditScreeningDetails?action.EditScreeningDetails:'',time:action.time};

        case 'EDIT_SCREEN_DETAILS':
        return { ...state, initialValues: action.screeningDetails, addButtonShow:true,editPopup:true,isEdit:'Y',securityStatusDate:action.StatusDate,time:action.time }
        case 'EDIT_CONSINOR_DETAILS':
        return { ...state,editPopupcons:true,initialValues: action.EditConsignerDetails,addbutton: 'EditConsignerDetails', addButtonShow:true,rowIndex:action.index, EditConsignerDetails:action.EditConsignerDetails?action.EditConsignerDetails:''}
        case 'EDIT_EXEMPTION_DETAILS':
        return { ...state,editPopupex:true,initialValues: action.EditsecurityExemption,addbutton: 'EditExemptionDetails', addButtonShow:true,rowIndex:action.index, EditsecurityExemption:action.EditsecurityExemption?action.EditsecurityExemption:''}
        case 'EDIT_REGULATION_DETAILS':
        return { ...state,editPopupreg:true,initialValues: action.EditRegulationDetails,addbutton: 'EditRegulationDetails', addButtonShow:true,rowIndex:action.index, EditRegulationDetails:action.EditRegulationDetails?action.EditRegulationDetails:''}
       
        default:
            return state;
    }
}
export default detailspanelreducer;
