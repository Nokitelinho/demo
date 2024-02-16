
import { SCREEN_MODE } from '../constants';
import * as constant from '../constants/constants';

const initialState = {
    screenMode: 'edit',
    screenFilter: {},
    ScreeningDetailsList:[],
    ScreeningDetails:[],
    securityExemptionreason:'',
    securityExemptionAuthority:'',
    securityExemptionRegulation:'',
    consignmentNumber:'',
    companyCode:'',
    paCode:'',
    consignmentDate:'',
    consignmentOrigin:'',
    consigmentDest:'',
    securityStatusCode:'',
    filterList:false,
    ConsignerDetails:[],
    editPopup:false,
    editPopupcons:false,
    editPopupex:false,
    editPopupreg:false,
    deletedRows:[],
    securityExemption:[],
    navigationFilter:'',
    routingInConsignmentVOs:[],
    category:'',
    fullpiecesBags:'',
    isSaved:'',
    applicableRegulation:[],
    timeZone:'' ,
    csgIssuerName:'',
    mstAddionalSecurityInfo:''
};

const filterpanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state
            };
        case 'TOGGLE_FILTER':
            return { ...state, screenMode: action.screenMode };
        case 'CLEAR_FILTER':
            return {...initialState, 
                screenMode:'initial'};

        case 'LIST_SUCCESS':
            return {
                ...state,
                screenMode: SCREEN_MODE.DISPLAY, 
                ScreeningDetails:action.ScreeningDetails,
                consignmentNumber:action.consignmentNumber,
                companyCode:action.companyCode,
                paCode:action.paCode,
                consignmentDate:action.consignmentDate,
                consignmentOrigin:action.consignmentOrigin,
                consigmentDest:action.consigmentDest,
                securityStatusCode:action.securityStatusCode,
                consignmentSequenceNumber:action.consignmentSequenceNumber,
                filterList:true,
                ConsignerDetails:action.ConsignerDetails,
                securityExemption:action.securityExemption,
                ScreeningDetailsList:action.ScreeningDetailsList,
                routingInConsignmentVOs:action.routingInConsignmentVOs,
                category:action.category,
                fullpiecesBags:action.fullStatedBags,
                screeningLocation : action.screeningLocation,
                securityStatusParty: action.securityStatusParty,
                applicableRegulation:action.applicableRegulation,
                timeZone:action.timeZone,
                csgIssuerName:action.csgIssuerName ,
                StatusDate:action.StatusDate,
                timedetailspanel:action.time,
                mstAddionalSecurityInfo:action.mstAddionalSecurityInfo,
				navigationFilter:action.consignmentNumber,
				showITable: action.showITable
            };

            case 'SAVE_SUCCESS':
            return {...state,addButtonShow:false,editPopup:false,showPopover: false, editPopupcons:false,editPopupex:false,editPopupreg:false, isEdit:'N',addbutton:false, addButtonShow:false} 
        
            
            case 'DELETE_SUCCESS':
                return {...state,addButtonShow:false,editPopup:false,showPopover: false,editPopupcons:false,editPopupex:false,editPopupreg:false,isEdit:'N'}        
            case 'SECURITY_FILTER':
                return {...state,navigationFilter:action.data.conDocNo, paCode: action.data.paCode, fromScreen:action.data.fromScreen}
        default:
            return state;
    }
}

export default filterpanelreducer;