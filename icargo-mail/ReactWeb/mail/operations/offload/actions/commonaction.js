import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { change, focus } from 'icoreact/lib/ico/framework/component/common/form';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {reset} from 'redux-form';


// For ScreenLoad
export function screenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = 'rest/mail/operations/offload/screenload';
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
    return { type: 'SCREENLOAD_SUCCESS',data };
}




export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}

export function onCloseFunction(values) {
    const { dispatch, getState } = values;
    let state = getState();
    let fromScreen=state.commonReducer.fromScreen;
    if(fromScreen==='CONTAINER_ENQUIRY'){
        const params = {
            fromScreen: "OFFLOAD"
        }
        navigateToScreen('mail.operations.ux.containerenquiry.defaultscreenload.do', params);
    }
    else if(fromScreen==='MAILBAG_ENQUIRY'){
        const params = {
            fromScreen: "OFFLOAD"
        }
        navigateToScreen('mail.operations.ux.mailbagenquiry.defaultscreenload.do', params);
    }
    else if(fromScreen==='MAIL_OUTBOUND'){
        const params = {
            fromScreen: "OFFLOAD"
        }
        navigateToScreen('mail.operations.ux.outboundscreenload.do', params);
    }
    else{
     navigateToScreen('home.jsp', {});
    }
}


    //For Container funnel filter clear
    export const onClearContainerFilter=(values)=> {
        const {dispatch} = values;  
        dispatch({ type: 'CLEAR_TABLE_FILTER'});
        dispatch(reset('ContainerTableFilter'));   
    }
    //For Container funnel filter apply
    export function applyContainerFilter(values) {
        const {dispatch, getState } = values;
        const state = getState();
        const tableFilter  = (state.form.ContainerTableFilter.values)?state.form.ContainerTableFilter.values:{}
        const { flightnumber, ...rest } = tableFilter;
        
        const ContainerTableFilter={...flightnumber,...rest};
        dispatch( { type: 'LIST_FILTER',ContainerTableFilter});
        return Object.keys(ContainerTableFilter).length;
    }

    //For Mail funnel filter clear
    export const onClearMailFilter=(values)=> {
        const {dispatch} = values;  
        dispatch({ type: 'CLEAR_TABLE_FILTER'});
        dispatch(reset('MailTableFilter'));   
    }
    //For Mail funnel filter apply
    export function applyMailFilter(values) {
        const {dispatch, getState } = values;
        const state = getState();
        const tableFilter  = (state.form.MailTableFilter.values)?state.form.MailTableFilter.values:{}
        const { flightnumber, ...rest } = tableFilter;
        
        const MailTableFilter={...flightnumber,...rest};
        dispatch( { type: 'LIST_MAILFILTER',MailTableFilter});
        return Object.keys(MailTableFilter).length;
    
    }

    //For DSN funnel filter clear
    export const onClearDSNFilter=(values)=> {
        const {dispatch} = values;  
        dispatch({ type: 'CLEAR_TABLE_FILTER'});
        dispatch(reset('DSNTableFilter'));   
    }
    
    //For DSN funnel filter apply
    export function applyDSNFilter(values) {
        const {dispatch, getState } = values;
        const state = getState();
        const tableFilter  = (state.form.DSNTableFilter.values)?state.form.DSNTableFilter.values:{}
        const { flightnumber, ...rest } = tableFilter;
        
        const DSNTableFilter={...flightnumber,...rest};
        dispatch( { type: 'LIST_DSNFILTER',DSNTableFilter});
        return Object.keys(DSNTableFilter).length;
       
    }
    // Offload Action
    export function onOffloadFunction(values){
        const { args, dispatch, getState } = values;
        const state = getState();
    let indexes = [];
    let count = 0;
    let selectedOffloadDetails = [];
        const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
        const defaultPageSize = args && args.defaultPageSize ? args.defaultPageSize : state.filterReducer.defaultPageSize; 
    let offloadFilter = null;
    let offloadMailFilter = null;
    let selectedOffloadmailDetails = [];


    if (state.commonReducer.activeOffloadTab === 'CONTAINER_VIEW') {
        offloadFilter = (state.form.offloadFilter && state.form.offloadFilter.values) ? state.form.offloadFilter.values : null;
        const carrierCode = (state.form.offloadFilter && state.form.offloadFilter.values) ? state.form.offloadFilter.values.flightnumber.carrierCode : '';
        offloadFilter.flightCarrierCode = carrierCode;
        const flightNumber = (state.form.offloadFilter && state.form.offloadFilter.values) ? state.form.offloadFilter.values.flightnumber.flightNumber : '';
        offloadFilter.flightNumber = flightNumber;
        const flightDate = (state.form.offloadFilter && state.form.offloadFilter.values) ? state.form.offloadFilter.values.flightnumber.flightDate : '';
        offloadFilter.flightDate = flightDate;
        let listedOffloadDetails = (state.form.containertable && state.form.containertable.values.containertable) ? state.form.containertable.values.containertable : null;

        offloadFilter.type = 'U';
        offloadFilter.displayPage = displayPage;
        offloadFilter.defaultPageSize = defaultPageSize;
        indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];


        for (let i = 0; i < indexes.length; i++) {
            selectedOffloadDetails[count++] = listedOffloadDetails[indexes[i]];
        }
    }
    // if selected tab is mail
    else {

        offloadMailFilter = (state.form.offloadMailFilter && state.form.offloadMailFilter.values) ? state.form.offloadMailFilter.values : null;
        const carrierCodeMail = (state.form.offloadMailFilter && state.form.offloadMailFilter.values) ? state.form.offloadMailFilter.values.flightnumber.carrierCode : '';
        offloadMailFilter.flightCarrierCode = carrierCodeMail;
        const flightNumberMail = (state.form.offloadMailFilter && state.form.offloadMailFilter.values) ? state.form.offloadMailFilter.values.flightnumber.flightNumber : '';
        offloadMailFilter.flightNumber = flightNumberMail;
        const flightDateMail = (state.form.offloadMailFilter && state.form.offloadMailFilter.values) ? state.form.offloadMailFilter.values.flightnumber.flightDate : '';
        offloadMailFilter.flightDate = flightDateMail;
        offloadMailFilter.type = 'M';
        indexes = (state.filterReducer.selectedMailbagIndex) ? state.filterReducer.selectedMailbagIndex : [];
        let listedOffloadMailDetails = (state.form.mailtable && state.form.mailtable.values.mailtable) ? state.form.mailtable.values.mailtable : null;
        for (let i = 0; i < indexes.length; i++) {
            selectedOffloadmailDetails[count++] = listedOffloadMailDetails[indexes[i]];
        }
    }


        if(offloadFilter == null){
            offloadFilter=offloadMailFilter;   
        selectedOffloadDetails = selectedOffloadmailDetails;
        }
        const data = {offloadFilter,selectedOffloadDetails};
        const url = 'rest/mail/operations/offload/offloadAction';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
        handleResponse(dispatch, response, data, displayPage);
            return response
        })
        .catch(error => {
            return error;
        });
      }


export function handleResponse(dispatch, response) {

    if (response.status == "offload_success") {
        return { type: 'OFFLOAD_SUCCESS' };
    }


}
  
export const warningHandler = (action, dispatch,getState) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                    dispatch(dispatchAction(action.executionContext.functionRecord)(action.executionContext.args));
                } 
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const state = getState();
                const prevActiveTab=state.commonReducer.activeOffloadTab;
                    dispatch({type:'CHANGE_OFFLOAD_TAB',currentTab: prevActiveTab });
            }
            break;
        default:
            break;
    }
}