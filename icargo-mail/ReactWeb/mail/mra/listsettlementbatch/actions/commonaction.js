import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestSuccess } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function screenLoad(args) {
    const{dispatch}=args;
    const url='rest/mail/mra/receivablemanagement/listsettlementbatch/screenload';
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
    const { getState } = values
    const state = getState();
    if (state.filterReducer.fromScreen && state.filterReducer.fromScreen === 'AdvancePayment') {
        const params = {
            fromScreen: "MailSettlementDetails"
        }
        navigateToScreen("mail.mra.receivablemanagement.ux.advancepayment.defaultscreenload.do", params)
    }
    else {
    navigateToScreen('home.jsp', {});
}
}

export function saveSelectedBatchIndexes(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const indexes = args;
    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes });
}
export function clearBatch(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    const selectedBatchId = args && args.selectedBatchId ? args.selectedBatchId : state.filterReducer.selectedBatchId;
    let listSettlementbatchesFilter = state.filterReducer.filterValues; 
    const batchDetail = state.filterReducer.batchDetailsList.results;
    
    let selectedBatchDetail=[];
    const indexes = state.commonReducer.selectedIndex ? state.commonReducer.selectedIndex : [];
    const selectedBatchStatus = state.filterReducer.selectedBatch.batchStatus;
    const selectedBatchDate =state.filterReducer.selectedBatch.batchDate;
    
    if(indexes == undefined || isEmpty(indexes)){ 
        dispatch(requestValidationError('Please select a row', ''));  
        return Promise.resolve("Error");  
    }else{   
     if(selectedBatchStatus == 'DL'){
        dispatch(requestValidationError('Clear Batch cannot be performed on deleted batch', ''));  
        return Promise.resolve("Error");  
       }else{
        for(let ind of indexes){
            selectedBatchDetail.push(batchDetail[ind]);
        }   
}
    }
   
    const url='rest/mail/mra/receivablemanagement/listsettlementbatch/clearbatch';  
    return makeRequest({
        url,data:{selectedBatchId,listSettlementbatchesFilter,selectedBatchDetail,selectedBatchDate,selectedBatchStatus}
    }).then(function(response) {
        handleclearBatchResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });
}
export function handleclearBatchResponse(dispatch, response) {
   if (!isEmpty(response.errors)) {
    dispatch(requestValidationError(response.errors.ERROR[0].description, ''));  
    return Promise.resolve("Error");
    }else{
    let message={'msgkey':'mail.mra.receivablemanagement.err.clearBatch','defaultMessage':'Clear Balance performed Successfullly'};
    dispatch(requestSuccess(message));

    }
}