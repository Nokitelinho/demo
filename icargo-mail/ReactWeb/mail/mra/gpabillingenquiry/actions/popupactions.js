import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {  CLOSE_SURCHARGE_POPUP,CLOSE_STATUS_POPUP,CHANGE_STATUS_SAVE,CLOSE_BILLING_POPUP } from '../constants/constants';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import listBillingDetails from './filteraction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


export function saveChangeStatusPopup(values) {
    const {dispatch,getState} = values;
    const state = getState();
    let data={} 
    let indexes=[]; 
    let selectedBillingDetails=[];
    const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
    indexes=state.filterReducer.selectedMailbagIndex;    
    for(var i=0; i<indexes.length;i++) {
        selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
    }
    let remarks=[];
    let billingStatus=[];
    remarks=state.form.statusMailForm.values.remarks;
    billingStatus=state.form.statusMailForm.values.billingStatus.value?state.form.statusMailForm.values.billingStatus.value:state.form.statusMailForm.values.billingStatus;
    data ={remarks,billingStatus,selectedBillingDetails};

     return makeRequest({
             url: 'rest/mail/mra/gpabilling/gpabillingenquiry/changestatussave',
             data: {...data}
         }).then(function (response) {
             handleResponse(dispatch, response, CHANGE_STATUS_SAVE);            
             return response;
         })
         .catch(error => {
             return error;
     });
}

function handleResponse(dispatch, response, action){
    if(action === CHANGE_STATUS_SAVE && !isEmpty(response.results)){
        dispatch({ type: CHANGE_STATUS_SAVE, data: response.results[0]})
       // dispatch(asyncDispatch(listBillingDetails)({'displayPage':'1',mode:'LIST'}))
    }
    
}

export function oncloseSurchargePopup(values) {
        const { dispatch } = values;
    dispatch({ type: CLOSE_SURCHARGE_POPUP, data: {} })
}

export function oncloseBillingPopup(values) {
        const { dispatch } = values;
    dispatch({ type: CLOSE_BILLING_POPUP, data: {} })



}

export function oncloseChangeStatusPopup(values) {
        const { dispatch } = values;
    dispatch({ type: CLOSE_STATUS_POPUP, data: {} })
}




