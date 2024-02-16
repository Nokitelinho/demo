
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
    deletedRows:[],
    securityExemption:{},
    navigationFilter:'',
    routingInConsignmentVOs:[],
    category:'',
    applicableRegTransportDirection:'',
    applicableRegBorderAgencyAuthority:'',
    applicableRegReferenceID:'',
    applicableRegFlag:'',
    fullPiecesWeight:'',
    fullpiecesBags:'',
    isSaved:'',
    screeningLocation:''
};

const filterpanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state
            };

        case 'CLEAR_FILTER':
            return {...initialState, 
                screenMode:'initial'};

        case 'LIST_SUCCESS':
            return {
                ...state,
                screenMode: SCREEN_MODE.DISPLAY, 
                ScreeningDetails:action.ScreeningDetails,
                //securityExemptionreason:action.securityExemptionreason,
                //securityExemptionAuthority:action.securityExemptionAuthority,
                //securityExemptionRegulation:action.securityExemptionRegulation,
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
                applicableRegTransportDirection :action.applicableRegTransportDirection,
                applicableRegBorderAgencyAuthority :action.applicableRegBorderAgencyAuthority,
                applicableRegReferenceID : action.applicableRegReferenceID,
                applicableRegFlag : action.applicableRegFlag,
                fullPiecesWeight:action.fullStatedWeight,
                fullpiecesBags:action.fullStatedBags,
                screeningLocation : action.screeningLocation
            };

            case 'TOGGLE_FILTER':
            return { ...state, screenMode: action.screenMode };

            case constant.NEW_SCREENING_DETAILS:
                return{...state, ScreeningDetails:(action.newScreeningDetails)?action.ScreeningDetails.concat(action.newScreeningDetails):action.ScreeningDetails,
                    fullBags:action.fullBags , fullWeight:action.fullWeight
                }

            case constant.NEW_CONSIGNER_DETAILS:
                return{...state, ConsignerDetails:(action.newConsignerDetails)?action.ConsignerDetails.concat(action.newConsignerDetails):action.ConsignerDetails}    

            case constant.NEW_DETAILS:
                return{...state, ScreeningDetails:action.modifiedList?action.modifiedList : action.ScreeningDetails}

            case constant.DELETE_DETAILS:
                return{...state, ScreeningDetails:action.ScreeningDetails?action.ScreeningDetails:'' , deletedRows:((action.deletedRows).push(action.deletedRow?action.deletedRow:''))?action.deletedRows:''}    

            case constant.ADD_DELETE_DETAILS:
                return{...state, ScreeningDetails:action.ScreeningDetails?action.ScreeningDetails:'' } ;
                
            case constant.CLEAR_DELETED_ROWS:
                return{...state , deletedRows:[], isSaved:action.isSaved}; 
            
            case 'SECURITY_FILTER':
                return {...state, navigationFilter: action.data.conDocNo, fromScreen:action.data.fromScreen};

            case constant.SCREENLOAD_SUCCESS_NAVIGATION:
                return{...initialState,fromScreen:state.fromScreen,navigationFilter:state.navigationFilter};    
                    
        default:
            return state;
    }
}

export default filterpanelreducer;