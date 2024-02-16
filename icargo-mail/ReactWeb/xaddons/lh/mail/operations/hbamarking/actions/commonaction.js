import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';

export function screenLoad(values) {
    const {dispatch, getState } = values;
    const state = getState(); 
    const url='rest/xaddons/mail/operations/hbamarking/screenload';
    let uldReferenceNumber=state.commonReducer.uldReferenceNumber
    return makeRequest({
        url,data:{uldReferenceNumber}
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

export function changePosition (values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const position = args;
    dispatch( { type : 'CHANGE_POSITION', data:position });
}

export function changeHbaType (values){
    const { args, dispatch, getState } = values;
    const hbaType = args;
    dispatch( { type : 'CHANGE_HBA_TYPE' , data:hbaType});
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case '__DELEGATE_WARNING_ONOK':
            if (action.executionContext) {
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

export function markAsHbaAction(values){
    const {dispatch, getState } = values;
    const state = getState(); 
  let uldReferenceNumber=state.commonReducer.uldReferenceNumber;
  let flightNumber = state.commonReducer.flightNumber;
  let flightSequenceNumber = state.commonReducer.flightSequenceNumber;
  let legSerialNumber=state.commonReducer.legSerialNumber;
  let carrierId=state.commonReducer.carrierId;
  let carrierCode=state.commonReducer.carrierCode;
  let assignedPort=state.commonReducer.assignedPort;
  let containerNumber=state.commonReducer.containerNumber;
  let type=state.commonReducer.type;
    const position = state.commonReducer.position;
    const hbaType = state.commonReducer.hbaType;
    const operationFlag = state.commonReducer.operationFlag;
   const data = {position, hbaType, uldReferenceNumber, operationFlag, flightNumber, flightSequenceNumber, legSerialNumber,
    carrierId, carrierCode, assignedPort, containerNumber, type}
    const url='rest/xaddons/mail/operations/hbamarking/markHba';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleHbaMarkingResponse(response, state);
        return response;
    })
    .catch(error => {
        return error;
    });  
}

export function handleHbaMarkingResponse(response, state) {
     if(response.status==='success')  {
        let isPopup=state.__commonReducer.screenConfig?state.__commonReducer.screenConfig.isPopup:'';
        if(isPopup == "true") {
            closePopupWindow();  
        }  else
        navigateToScreen('home.jsp', {});
     } 
   
}
        


