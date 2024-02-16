import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {reset} from 'redux-form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

export function screenLoad(args){
    const {dispatch}=args;
const url='rest/mail/operations/mailtransitoverview/fetchDetailsOnScreenload';
return makeRequest({
    url,data:{}
}).then(function(response){
    handleScreenResponse(dispatch,response);
    return response;
});
}

export function handleScreenResponse(dispatch,response){
    if(response.results){
        dispatch(screenLoadSuccess(response.results[0]));//dbt
       
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}
export const warningHandler = (action, dispatch) => {
    switch (action.type) {}
}
export function onCloseFunction(values) {
    
    navigateToScreen('home.jsp', {});
}
export function navigateToMailInbound(values){
    const { getState } = values;
    const state = getState();
    const airportCode = state.form.mailTransitFilterForm.values?state.form.mailTransitFilterForm.values.airportCode:'';
    const fromDate = state.form.mailTransitFilterForm.values?state.form.mailTransitFilterForm.values.fromDate:'';
    const toDate = state.form.mailTransitFilterForm.values?state.form.mailTransitFilterForm.values.toDate:'';
    const flightNumber = state.form.mailTransitFilterForm.values.flightnumber.flightNumber?state.form.mailTransitFilterForm.values.flightnumber.flightNumber:'';
    const carrierCode=state.form.mailTransitFilterForm.values.flightnumber.carrierCode?state.form.mailTransitFilterForm.values.flightnumber.carrierCode:'';
    const flightDate=state.form.mailTransitFilterForm.values.flightnumber.flightDate?state.form.mailTransitFilterForm.values.flightnumber.flightDate:'';
    // const data ={conDocNo : conDocNo , paCode : paCode , fromScreen : "mail.operations.ux.consignment", pageURL: "mail.operations.ux.consignment"}
    //navigateToScreen("mail.operations.ux.consignmentsecuritydeclaration.defaultscreenload.do", data);  
    const data ={ airportCode:airportCode, fromDate:fromDate,toDate:toDate,flightNumber:flightNumber,carrierCode:carrierCode,flightDate:flightDate,fromScreen : "mail.operations.ux.mailtransitoverview", pageURL: "mail.operations.ux.mailtransitoverview"}
    navigateToScreen("mail.operations.ux.mailinbound.mailinboundscreenload.do", data); 
}
export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}
export const onClearMailTransitFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_TABLE_FILTER'});  
    dispatch(reset('mailTransitTableFilterForm'));
}
export function onApplyMailTransitFilter(values) {
    const {dispatch, getState } = values;
    const state = getState();
    var tableFilter  = (state.form.mailTransitTableFilterForm.values)?state.form.mailTransitTableFilterForm.values:{}
   
    if(!isEmpty(tableFilter)) {
        if( (tableFilter.carrierCode=="")) {  
            delete tableFilter.carrierCode
    }
        if( (tableFilter.mailBagDestination=="")) {  
            delete tableFilter.mailBagDestination
    }
        if( (tableFilter.totalNoImportBags=="")) {  
            delete tableFilter.totalNoImportBags
    }
        if( (tableFilter.totalWeightImportBags=="")) {  
            delete tableFilter.totalWeightImportBags
    }
        if( (tableFilter.countOfExportNonAssigned=="")) {  
            delete tableFilter.countOfExportNonAssigned
    }
    if( (tableFilter.totalWeightOfExportNotAssigned=="")) {  
        delete tableFilter.totalWeightOfExportNotAssigned
}
        if( (tableFilter.freeSaleULDPostnLDP=="")) {  
            delete tableFilter.freeSaleULDPostnLDP
    }
    if( (tableFilter.freeSaleULDPostnMDP=="")) {  
        delete tableFilter.freeSaleULDPostnMDP
    }
}
    tableFilter.undefined?delete tableFilter.undefined:''; 
    const { flightnumber, ...rest } = tableFilter;
    const mailTransitTableFilter={...flightnumber,...rest};
    dispatch( { type: 'LIST_FILTER',mailTransitTableFilter});
    return Object.keys(mailTransitTableFilter).length;               
}
