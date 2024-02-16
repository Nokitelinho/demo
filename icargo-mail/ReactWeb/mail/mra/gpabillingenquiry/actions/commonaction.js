import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import {change,focus } from 'icoreact/lib/ico/framework/component/common/form';
import { asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { SCREENLOAD_SUCCESS} from '../constants/constants';

// For ScreenLoad
export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();    
    const url='rest/mail/mra/gpabilling/gpabillingenquiry/screenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })   
}
export function handleScreenloadResponse(dispatch, response) {
    if (response.status=="success") {
        dispatch(screenLoadSuccess(response.results[0])); 
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS' , data };
}

//For Close
export function onClose() {
    navigateToScreen('home.jsp', {});
}