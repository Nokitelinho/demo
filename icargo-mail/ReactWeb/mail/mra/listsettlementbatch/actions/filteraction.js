import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

export function toggleFilter(screenMode) {  
  return {type: 'TOGGLE_FILTER',screenMode };
}

export function clearFilter(values) {
    const { dispatch } = values;
    dispatch({ type: 'CLEAR_FILTER', response: {} });
}

export function validateForm(data) {
    let isValid = true;
    let error = ""

    if (!data.fromDate) {
        isValid = false;
        error = "From Date,"
    }
    if (!data.toDate) {
        isValid = false;
        error = error + "To Date,"
    }
    if (!isValid) {
        error = "Please select " + error.substring(0, error.length - 1) + "."
    }
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export function listSettlementBatch(values) {
    const { args, dispatch, getState } = values;
    const state = getState();  
    let listSettlementBatchFilter = {}   
    if (args && args.autoList) {
        listSettlementBatchFilter = state.filterReducer.filterValues
    }
    else {
    listSettlementBatchFilter=state.form.listSettlementBatchFilter.values
     }
    const data = { listSettlementBatchFilter };
    const url = 'rest/mail/mra/receivablemanagement/listsettlementbatch/listsettlementbatches';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleListBatchResponse(dispatch, response, data);
        return response
    }).catch(error => {
        return error;
    });
}

function handleListBatchResponse(dispatch, response, data) {
    if ((response.results)) {
        if (response.results[0].batchLists.length === 0) {
            dispatch({ type: 'CLEAR_TABLE', data });
        } else {
            dispatch(listBatchSuccess(response.results[0]), data)
        }
    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type:'CLEAR_TABLE', data });
        }
    }
}

function listBatchSuccess(data) {
    return ({ type: 'LISTBATCH_SUCCESS', data });
}

export function loadBatchDetail(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    const selectedBatchId = args && args.selectedBatchId ? args.selectedBatchId : state.filterReducer.selectedBatchId;
    const pageNumber = args && args.pageNumber ? args.pageNumber : state.filterReducer.pageNumber;
    const batchListMode = args && args.batchListMode ? args.batchListMode : state.filterReducer.batchListMode;
       
    let listSettlementbatchesFilter = state.filterReducer.filterValues;   
    const url = 'rest/mail/mra/receivablemanagement/listsettlementbatch/listsettlementbatchdetails';
    const pageSize =args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;
    const batchList = state.filterReducer.batchLists;
    
    const selectedBatch = batchList.find((element) => {
        return  element.batchId + '~' +element.gpaCode+'~'+ element.batchSequenceNum == selectedBatchId });

    return makeRequest({
        url,
        data: { selectedBatchId, pageNumber, pageSize, listSettlementbatchesFilter,selectedBatch }
    }).then(function (response) {
        if (response.errors) {
            dispatch({ type:'LISTBATCHDETAIL_ERROR' , selectedBatchId, pageNumber, batchListMode,pageSize,selectedBatch})
            return response.errors;
        }
       else {
           if (args && args.mode === 'EXPORT') {
                const batchDetails = { ...response.results[0].batchDetails }
                let exportBatchDetails = { ...response.results[0].batchDetails }
                let exportDataCount = batchDetails.results.length;                 
                return exportBatchDetails;
            }
            handleListBatchDetailsResponse(response, dispatch, pageNumber, batchListMode,selectedBatch);
        }
        return response

    }).catch(error => {
        return error;
    });
}

function handleListBatchDetailsResponse(response, dispatch, pageNumber, batchListMode,selectedBatch) {
    if (!isEmpty(response.results)) {
        const batchDetails = response.results[0].batchDetails
        const selectedBatchId = response.results[0].selectedBatchId
      dispatch({ type: 'LISTBATCHDETAIL_SUCCESS', batchDetails, selectedBatchId, pageNumber, batchListMode ,selectedBatch});
    }
    else {
        if (!isEmpty(response.errors)) {
            return ({ type: 'CLEAR_DETAIL_TABLE' });
        }
    }
}
