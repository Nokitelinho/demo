import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import { dispatchAction,asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { listCarditEnquiry } from './filteraction';

export function screenLoad(args) {
    const{dispatch}=args;
    const url='rest/mail/operations/carditenquiry/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })   

}


export function handleScreenloadResponse(dispatch, response) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));
       
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function onClose(values) {
    const { args, dispatch, getState } = values;
    const state = getState();

    let isPopup=state.__commonReducer.screenConfig?state.__commonReducer.screenConfig.isPopup:'';
    if(isPopup == "true") closePopupWindow();  
    else
    navigateToScreen('home.jsp', {});
}

export function navigateActions(values) {
    const { args,dispatch, getState} = values;
    const state = getState();

    const index = args.index;
    const mailbags = state.filterReducer.mailbagsdetails.results[index];
    const conDocNo = mailbags.consignmentNumber;
    const paCode = mailbags.paCode;
    //const consignmentFilter = {conDocNo: conDocNo, paCode:paCode};
    const data = { conDocNo: conDocNo, paCode:paCode, fromScreen : "mail.operations.ux.carditenquiry", pageURL: "mail.operations.ux.carditenquiry" }
    navigateToScreen("mail.operations.ux.consignment.screenload.do", data);  
}

export const openListAWBPopup=(values)=>{
    const {dispatch,getState} = values;
    const state = getState();
    const selectedMailbagIndex = state.filterReducer.selectedMailbagIndex;
    var airportCode = state.commonReducer.airportCode;
    const carditFilter = state.filterReducer.filterValues;
    const selectedMailbags=[];
    var throwValidations = true;
    if(selectedMailbagIndex.length=== 0 && !isEmpty(carditFilter.conDocNo)){
        throwValidations=false;
        if(state.filterReducer.mailbagsdetails&&state.filterReducer.mailbagsdetails.results!=null){
            const length = state.filterReducer.mailbagsdetails.results.length;
            for(var i=0; i<length;i++){
                if(state.filterReducer.mailbagsdetails.results[i].shipmentPrefix !==null ||
                    state.filterReducer.mailbagsdetails.results[i].masterDocumentNumber !==null){
                        return Promise.reject(new Error('Please detach already attached mailbag(s) of the consignment'));
                    }else{
                        dispatch({type:'SELECTED_MAILBAGS_FOR_LISTAWB',selectedMailbags});
                    }
            }
        }
    }
    if(throwValidations){
    if(!selectedMailbagIndex.length>0){
            return Promise.reject(new Error('Please select at least one row'));
    }else{
        for(var i=0; i<selectedMailbagIndex.length;i++) {
            if(state.filterReducer.mailbagsdetails.results[selectedMailbagIndex[i]].shipmentPrefix !==null ||
                 state.filterReducer.mailbagsdetails.results[selectedMailbagIndex[i]].masterDocumentNumber !==null){
                        return Promise.reject(new Error('Please detach already attached mailbag(s)'));
                }else{
                    selectedMailbags.push(state.filterReducer.mailbagsdetails.results[selectedMailbagIndex[i]]); 
                }
            }
            if(selectedMailbags!==null)
            dispatch({type:'SELECTED_MAILBAGS_FOR_LISTAWB',selectedMailbags});
        }
    }
    var url = "addons.mail.operations.mailawbbooking.defaultscreenload.do?fromScreen=mail.operations.carditenquiry&isPopup=true&airportCode="+airportCode+'&selectedMailbags='+selectedMailbags+'&carditFilter='+carditFilter;
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds : ['CMP_MAIL_OPERATIONS_MAILAWBBOOKING_CLOSE'],
        popupTitle: 'List AWB'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
    return Promise.resolve({});
    
}

export const navigationHandler = (action, dispatch) => {
    switch (action.type) {
        case "CLOSE_UX_POPUP":
            if (action.data === "reload_listcarditenquiry") {
                dispatch(asyncDispatch(listCarditEnquiry)({mode:'LIST'}))
            }
            break;
        default:
            break;
    }
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case '__DELEGATE_WARNING_ONOK':
            if (action.executionContext) {
                const warningCode = action.warningCode[0];
                if (warningCode === "mail.operations.carditdsnenquiry.relistwarning") {
                    dispatch(asyncDispatch(listCarditEnquiry)({'displayPage':'1',mode:'LIST'}))
                }
            }
            break;
        case '__DELEGATE_WARNING_ONCANCEL':
            if (action.executionContext) {
            }
            break;
        default:
            break;
    }
}