import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { handleResponse} from './handleresponse.js'
import { ActionType } from '../constants/constants.js'
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import {reset} from 'redux-form';

// For ScreenLoad
export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();    
    const url='rest/mail/mra/defaults/attachfile/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        const payLoad={ type: ActionType.SCREENLOAD_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })   
}

//For Close
export function onClose(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let isPopup=state.__commonReducer.screenConfig?state.__commonReducer.screenConfig.isPopup:'';
    if(isPopup == "true") closePopupWindow(); 
    else
    navigateToScreen('home.jsp', {});
}
//For Add File
export function onAddFile(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let filterFromAdvpay = (state.commonReducer.filterValuesFromAdvpay)?state.commonReducer.filterValuesFromAdvpay:{};
    const data = {filterFromAdvpay}
    const url = 'rest/mail/mra/defaults/attachfile/excelupload';
    return makeRequest({
        url, data: {...data}
    }).then(function (response) {
        handleExcelUploadResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
function handleExcelUploadResponse(dispatch, response) {
    if(!isEmpty(response)){
        if(!isEmpty(response.errors)){
            dispatch({type: ActionType.ERROR_SHOW});
        }
    }else {
        dispatch({ type: ActionType.EXCEL_UPLOAD_SUCCESS })
    }
   dispatch(dispatchAction(clearExcelUploadDetails)()); 
}
export function clearExcelUploadDetails(values) {
    const { dispatch } = values;
    dispatch(reset('attachFileFilter'));
}