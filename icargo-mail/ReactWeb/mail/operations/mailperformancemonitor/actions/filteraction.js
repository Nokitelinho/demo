import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';

export function onToggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }


//For Clear filter at filter panel
export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('mailPerformanceMonitorFilter'));
  
}

//For List at filter panel/export at table custom header
export function listMailbagDetails(values) {
    const { args, dispatch, getState } = values;
    let state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
    const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize
    const mailMonitorFilter = (state.form.mailPerformanceMonitorFilter.values) ? state.form.mailPerformanceMonitorFilter.values : {}
    
    if(!state.form.mailPerformanceMonitorFilter.values.station){
        return Promise.reject(new Error("Please enter station"));
    }
    else if(!state.form.mailPerformanceMonitorFilter.values.fromDate || !state.form.mailPerformanceMonitorFilter.values.toDate ){
            return Promise.reject(new Error( "Please enter  Date  range" ));   
    }
    else if(!state.form.mailPerformanceMonitorFilter.values.paCode ){
        return Promise.reject(new Error( "Please enter  PA Code" ));   
    }
    
    mailMonitorFilter.displayPage = displayPage;
    mailMonitorFilter.pageSize = pageSize;
    mailMonitorFilter.type = state.commonReducer.activeTab;
    const currentTab = state.commonReducer.activeTab;
    const data = { mailMonitorFilter };
    const url = 'rest/mail/operations/mailperformancemonitor/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {

        if (args && args.mode === 'EXPORT') {

           let exportMailbags={};
          // const currentTab = state.commonReducer.activeTab;
           switch (currentTab) {
            case 'MISSING_ORIGIN_SCAN': {
                exportMailbags = response.results[0].missingOriginScanMailbags;       
                break;
            }
            case 'MISSING_DESTINATION_SCAN': {
                exportMailbags = response.results[0].missingArrivalScanMailbags;        
                break;
            }
            case 'MISSING_BOTH_SCAN': {
                 exportMailbags = response.results[0].missingOriginAndArrivalScanMailbags;
                 break;
            }
            case 'ON_TIME_MAILBAGS': {
                 exportMailbags = response.results[0].onTimeMailbags;
                break;
            }
            case 'DELAYED_MAILBAGS': {
                 exportMailbags = response.results[0].delayedMailbags;
                break;
            }
            case 'RAISED_MAILBAGS': {
                 exportMailbags = response.results[0].raisedMailbags;
                break;
            }
            case 'APPROVED_MAILBAGS': {
                 exportMailbags = response.results[0].approvedMailbags;
                break;
            }
            case 'REJECTED_MAILBAGS': {
                 exportMailbags = response.results[0].deniedMailbags;
                break;
            }

        }

            return exportMailbags;
        }//export ends

        switch (currentTab) {
            case 'MISSING_ORIGIN_SCAN': {
                state.commonReducer.paginatedMailbags = response.results[0].missingOriginScanMailbags;       
                break;
            }
            case 'MISSING_DESTINATION_SCAN': {
                state.commonReducer.paginatedMailbags = response.results[0].missingArrivalScanMailbags;        
                break;
            }
            case 'MISSING_BOTH_SCAN': {
                state.commonReducer.paginatedMailbags = response.results[0].missingOriginAndArrivalScanMailbags;
                 break;
            }
            case 'ON_TIME_MAILBAGS': {
                state.commonReducer.paginatedMailbags = response.results[0].onTimeMailbags;
                break;
            }
            case 'DELAYED_MAILBAGS': {
                state.commonReducer.paginatedMailbags = response.results[0].delayedMailbags;
                break;
            }
            case 'RAISED_MAILBAGS': {
                state.commonReducer.paginatedMailbags = response.results[0].raisedMailbags;
                break;
            }
            case 'APPROVED_MAILBAGS': {
                state.commonReducer.paginatedMailbags = response.results[0].approvedMailbags;
                break;
            }
            case 'REJECTED_MAILBAGS': {
                state.commonReducer.paginatedMailbags = response.results[0].deniedMailbags;
                break;
            }

        }
        handleResponse(dispatch, response, args.mode, data);
        return response
    })
        .catch(error => {
            return error;
        });
}

function handleResponse(dispatch, response, action, data) {
    if (!isEmpty(response.results)) {
        const mailbagsdetails = response.results[0];
        const mailMonitorSummary = response.results[0].mailMonitorSummary;
        
        if (action=='LIST') {
            if (mailbagsdetails != null) {
                dispatch({ type: 'LIST_SUCCESS', mailbagsdetails, mailMonitorSummary, data });
            }
            else {
                dispatch({ type: 'NO_DATA', data });
            }
        }

    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: 'RETAIN_VALUES' });
        }
    }
}






