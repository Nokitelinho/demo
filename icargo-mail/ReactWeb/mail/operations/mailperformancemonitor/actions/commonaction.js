import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup'
import {reset} from 'redux-form';

// For ScreenLoad
export function screenLoad(values) {
    const { dispatch } = values;
    const url = 'rest/mail/operations/mailperformancemonitor/screenload';
    return makeRequest({
        url, data: {}
    }).then(function (response) {
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

//On close of screen
export function onCloseFunction() {

    navigateToScreen('home.jsp', {});
}

//Table filter - clear
export const onClearTableFilter = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    switch (state.commonReducer.activeTab) {
        case 'MISSING_ORIGIN_SCAN': {
            state.commonReducer.tableFilterMissOrgScan = {};
            break;
        }
        case 'MISSING_DESTINATION_SCAN': { state.commonReducer.tableFilterMissDestScan = {}; break; }
        case 'MISSING_BOTH_SCAN': { state.commonReducer.tableFilterMissBothScan = {}; break; }
        case 'ON_TIME_MAILBAGS': { state.commonReducer.tableFilterOnTime = {}; break; }
        case 'DELAYED_MAILBAGS': { state.commonReducer.tableFilterNotOnTime = {}; break; }
        case 'RAISED_MAILBAGS': { state.commonReducer.tableFilterRaised = {}; break; }
        case 'APPROVED_MAILBAGS': { state.commonReducer.tableFilterAccepted = {}; break; }
        case 'REJECTED_MAILBAGS': { state.commonReducer.tableFilterDenied = {}; break; }
    }
    dispatch({ type: 'CLEAR_TABLE_FILTER' });
    dispatch(reset('mailbagTableFilter'));
}

export function toggleFilter(screenMode) {  
  return {type: 'TOGGLE_FILTER',screenMode };
}

//Table filter - apply
export function onApplyTableFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const tableFilter = (state.form.mailbagTableFilter.values) ? state.form.mailbagTableFilter.values : {}
    state.commonReducer.currentTableFilter = tableFilter;
    if (!isEmpty(tableFilter)) {
        switch (state.commonReducer.activeTab) {
            case 'MISSING_ORIGIN_SCAN': {
                state.commonReducer.tableFilterMissOrgScan = tableFilter;
                break;
            }
            case 'MISSING_DESTINATION_SCAN': { state.commonReducer.tableFilterMissDestScan = tableFilter; break; }
            case 'MISSING_BOTH_SCAN': { state.commonReducer.tableFilterMissBothScan = tableFilter; break; }
            case 'ON_TIME_MAILBAGS': { state.commonReducer.tableFilterOnTime = tableFilter; break; }
            case 'DELAYED_MAILBAGS': { state.commonReducer.tableFilterNotOnTime = tableFilter; break; }
            case 'RAISED_MAILBAGS': { state.commonReducer.tableFilterRaised = tableFilter; break; }
            case 'APPROVED_MAILBAGS': { state.commonReducer.tableFilterAccepted = tableFilter; break; }
            case 'REJECTED_MAILBAGS': { state.commonReducer.tableFilterDenied = tableFilter; break; }

        }
    }
    dispatch({ type: 'APPLY_TABLE_FILTER', tableFilter });
    //  return Object.keys(tableFilter).length;
}

//For table sort
export function updateSortVariables(data) {
    const { args,dispatch, getState } = data;
    const state = getState();
    const updatedSort = args?args:{}
    state.commonReducer.sort = updatedSort;
    if (!isEmpty(updatedSort)) {
        switch (state.commonReducer.activeTab) {
            case 'MISSING_ORIGIN_SCAN': {
                state.commonReducer.sortMissOrgScan = updatedSort;
                break;
            }
            case 'MISSING_DESTINATION_SCAN': { state.commonReducer.sortFilterMissDestScan = updatedSort; break; }
            case 'MISSING_BOTH_SCAN': { state.commonReducer.sortFilterMissBothScan = updatedSort; break; }
            case 'ON_TIME_MAILBAGS': { state.commonReducer.sortFilterOnTime = updatedSort; break; }
            case 'DELAYED_MAILBAGS': { state.commonReducer.sortFilterNotOnTime = updatedSort; break; }
            case 'RAISED_MAILBAGS': { state.commonReducer.sortFilterRaised = updatedSort; break; }
            case 'APPROVED_MAILBAGS': { state.commonReducer.sortFilterAccepted = updatedSort; break; }
            case 'REJECTED_MAILBAGS': { state.commonReducer.tableFilterDenied = updatedSort; break; }

        }
    }
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}

//This method will be called for every tab change
export function onChangeTab(values) {
    const { args, dispatch, getState } = values;
    const currentTab = args;
    const state = getState();
    let resultFilter = {};
    let resultSort = {};
    let resultPaginatedMailbags = {};
    var isReListRequired = false;
    if (!isEmpty(currentTab)) {
        switch (currentTab) {
            case 'MISSING_ORIGIN_SCAN': {
                resultFilter = state.commonReducer.tableFilterMissOrgScan;
                resultSort = state.commonReducer.sortMissOrgScan;
                resultPaginatedMailbags = state.commonReducer.paginatedMailbagsMissOrgScan;
                if(!state.filterReducer.mailbagsdetails.missingOriginScanMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'MISSING_DESTINATION_SCAN': {
                resultFilter = state.commonReducer.tableFilterMissDestScan;
                resultSort = state.commonReducer.sortFilterMissDestScan;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsMissDestScan;
                if(!state.filterReducer.mailbagsdetails.missingArrivalScanMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'MISSING_BOTH_SCAN': {
                resultFilter = state.commonReducer.tableFilterMissBothScan;
                resultSort = state.commonReducer.sortFilterMissBothScan;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsMissBothScan;
                if(!state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'ON_TIME_MAILBAGS': {
                resultFilter = state.commonReducer.tableFilterOnTime;
                resultSort = state.commonReducer.sortFilterOnTime;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsOnTime;
                if(!state.filterReducer.mailbagsdetails.onTimeMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'DELAYED_MAILBAGS': {
                resultFilter = state.commonReducer.tableFilterNotOnTime;
                resultSort = state.commonReducer.sortFilterNotOnTime;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsNotOnTime;
                if(!state.filterReducer.mailbagsdetails.delayedMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'RAISED_MAILBAGS': {
                resultFilter = state.commonReducer.tableFilterRaised;
                resultSort = state.commonReducer.sortFilterRaised;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsRaised;
                if(!state.filterReducer.mailbagsdetails.raisedMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'APPROVED_MAILBAGS': {
                resultFilter = state.commonReducer.tableFilterAccepted;
                resultSort = state.commonReducer.sortFilterAccepted;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsAccepted;
                if(!state.filterReducer.mailbagsdetails.approvedMailbags){
                    isReListRequired=true;
                }
                break;
            }
            case 'REJECTED_MAILBAGS': {
                resultFilter = state.commonReducer.tableFilterDenied;
                resultSort = state.commonReducer.sortFilterDenied;
                 resultPaginatedMailbags =state.commonReducer.paginatedMailbagsDenied;
                if(!state.filterReducer.mailbagsdetails.deniedMailbags){
                    isReListRequired=true;
                }
                break;
            }

        }
    }
    dispatch({ type: 'CHANGE_TAB', currentTab: currentTab, 
    currentTableFilter: resultFilter, 
    sort: resultSort, 
    paginatedMailbags:resultPaginatedMailbags
 });
    //  return Object.keys(tableFilter).length;
    if(isReListRequired==true){
        listMailbagDetailsOnTabChange(values);
    }

}

//History icon at table row
export function openHistoryPopup(values) {
    const { args, dispatch, getState } = values;
    let state = getState();
    const index = args.index;
    let mailbagId='';
    let mailSequenceNumber='';
    let isPopup = 'Y';
    
    if (!isEmpty(state.filterReducer.mailbagsdetails)) {
    switch (state.commonReducer.activeTab) {
      case 'MISSING_ORIGIN_SCAN': {
          if(state.filterReducer.mailbagsdetails.missingOriginScanMailbags){
             mailbagId=state.filterReducer.mailbagsdetails.missingOriginScanMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.missingOriginScanMailbags.results[index].mailSequenceNumber;
         }
            break;
      }
      case 'MISSING_DESTINATION_SCAN':{ 
        if(state.filterReducer.mailbagsdetails.missingArrivalScanMailbags){
            mailbagId=state.filterReducer.mailbagsdetails.missingArrivalScanMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.missingArrivalScanMailbags.results[index].mailSequenceNumber;
            }
        break;
      }
      case 'MISSING_BOTH_SCAN': {
        if(state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags){
                  mailbagId=state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags.results[index].mailSequenceNumber;
         }
            break;
      }
      case 'ON_TIME_MAILBAGS': {
        if(state.filterReducer.mailbagsdetails.onTimeMailbags){
                   mailbagId=state.filterReducer.mailbagsdetails.onTimeMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.onTimeMailbags.results[index].mailSequenceNumber;
         }
            break;
      }
      case 'DELAYED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.delayedMailbags){
               mailbagId=state.filterReducer.mailbagsdetails.delayedMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.delayedMailbags.results[index].mailSequenceNumber;
         }
            break;
    }
      case 'RAISED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.raisedMailbags){
               mailbagId=state.filterReducer.mailbagsdetails.raisedMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.raisedMailbags.results[index].mailSequenceNumber;
         }
            break;
    }
      case 'APPROVED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.approvedMailbags){
             mailbagId=state.filterReducer.mailbagsdetails.approvedMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.approvedMailbags.results[index].mailSequenceNumber;
         }
            break;
    }
      case 'REJECTED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.deniedMailbags){
                 mailbagId=state.filterReducer.mailbagsdetails.deniedMailbags.results[index].mailbagId;
             mailSequenceNumber=state.filterReducer.mailbagsdetails.deniedMailbags.results[index].mailSequenceNumber;
         }
            break;
    }

  }
}

    const fromScreenId='mail.operations.ux.mailperformancemonitor';
    var url = 'mail.operations.ux.mbHistory.list.do?formCount=1&mailbagId='+mailbagId+'&fromScreenId='+fromScreenId+'&mailSequenceNumber='+mailSequenceNumber+'&isPopUp='+isPopup;
    var closeButtonIds = ['btnClose'];
    var optionsArray = {
    url,
    dialogWidth: "900",
    dialogHeight: "625",
    closeButtonIds: closeButtonIds,
    popupTitle: 'Mailbag History'
}

dispatch(dispatchAction(openPopup)(optionsArray));

}


//On tab change, if new tab is empty it will call list at server
function listMailbagDetailsOnTabChange(values) {
   
    const { dispatch, getState } = values;
    const state = getState();
    const mailMonitorFilter = (state.form.mailPerformanceMonitorFilter.values) ? state.form.mailPerformanceMonitorFilter.values : {}
    mailMonitorFilter.displayPage = 1;
    mailMonitorFilter.type = state.commonReducer.activeTab;
    const data = { mailMonitorFilter };
    let existingMailBagDetails =  state.filterReducer.mailbagsdetails;
    const newTab = state.commonReducer.activeTab;
    const url = 'rest/mail/operations/mailperformancemonitor/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {


        handleResponseForListOnTabChange(dispatch, response, data, existingMailBagDetails,newTab,state);
        return response
    })
        .catch(error => {
            return error;
        });
}

function handleResponseForListOnTabChange(dispatch, response, data, existingMailBagDetails, newTab,state) {
    if (!isEmpty(response.results)) {
        const mailbagsdetails = response.results[0];
        let paginatedMailbags = {};
        if (!isEmpty(newTab)) {
            switch (newTab) {
                case 'MISSING_ORIGIN_SCAN': {
                    existingMailBagDetails.missingOriginScanMailbags = mailbagsdetails.missingOriginScanMailbags;
                      state.commonReducer.paginatedMailbags = mailbagsdetails.missingOriginScanMailbags;
                      paginatedMailbags = mailbagsdetails.missingOriginScanMailbags;
                    break;
                }
                case 'MISSING_DESTINATION_SCAN': {
                    existingMailBagDetails.missingArrivalScanMailbags = mailbagsdetails.missingArrivalScanMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.missingArrivalScanMailbags;
                     paginatedMailbags = mailbagsdetails.missingArrivalScanMailbags;
                    break;
                }
                case 'MISSING_BOTH_SCAN': {
                    existingMailBagDetails.missingOriginAndArrivalScanMailbags = mailbagsdetails.missingOriginAndArrivalScanMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.missingOriginAndArrivalScanMailbags;
                     paginatedMailbags = mailbagsdetails.missingOriginAndArrivalScanMailbags;
                    break;
                }
                case 'ON_TIME_MAILBAGS': {
                    existingMailBagDetails.onTimeMailbags = mailbagsdetails.onTimeMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.onTimeMailbags;
                     paginatedMailbags = mailbagsdetails.onTimeMailbags;
                    break;
                }
                case 'DELAYED_MAILBAGS': {
                    existingMailBagDetails.delayedMailbags = mailbagsdetails.delayedMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.delayedMailbags;
                     paginatedMailbags = mailbagsdetails.delayedMailbags;
                    break;
                }
                case 'RAISED_MAILBAGS': {
                    existingMailBagDetails.raisedMailbags = mailbagsdetails.raisedMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.raisedMailbags;
                     paginatedMailbags = mailbagsdetails.raisedMailbags;
                    break;
                }
                case 'APPROVED_MAILBAGS': {
                    existingMailBagDetails.approvedMailbags = mailbagsdetails.approvedMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.approvedMailbags;
                     paginatedMailbags = mailbagsdetails.approvedMailbags;
                    break;
                }
                case 'REJECTED_MAILBAGS': {
                    existingMailBagDetails.deniedMailbags = mailbagsdetails.deniedMailbags;
                     state.commonReducer.paginatedMailbags = mailbagsdetails.deniedMailbags;
                     paginatedMailbags = mailbagsdetails.deniedMailbags;
                    break;
                }

            }

        }
        const mailMonitorSummary = response.results[0].mailMonitorSummary;

        if (existingMailBagDetails != null) {

            dispatch({ type: 'LIST_ON_TAB_CHANGE_SUCCESS', existingMailBagDetails, mailMonitorSummary, data, paginatedMailbags });

        }
        else {
            dispatch({ type: 'NO_DATA_LISTONTABCHANGE', data });
        }


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: 'RETAIN_VALUES_LISTONTABCHANGE' });
        }
    }
}