import React, { Fragment } from 'react';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import commonReducer from '../reducers/commonreducer';
import listmailbagReducer from '../reducers/listmailbagreducer';
import { isNull, isNullOrUndefined } from '../../../../node_modules/util';

export function saveNewScreeningDetails(values) {
    const { args, dispatch, getState, } = values;
    const state = getState();
    const location = (state.form.screeningDetailsPopupForm.values!=undefined)?state.form.screeningDetailsPopupForm.values.screeningLocation:null;
    if(location==null||location=="")
    {
        return Promise.reject(new Error('Please enter location'));
    }
    const method = state.form.screeningDetailsPopupForm.values.screeningMethodCode;
    if(method==null||method=="")
    {
        return Promise.reject(new Error('Please enter method'));
    }
    const securityStatus = state.form.screeningDetailsPopupForm.values.securityStatusParty;
    if(securityStatus==null||securityStatus=="")
    {
        return Promise.reject(new Error('Please enter securityStatusParty'));
    }
    const date = state.form.screeningDetailsPopupForm.values.securityStatusDate;
    const time = state.form.screeningDetailsPopupForm.values.time;
    if(date==null||date==""||time==null||time=="")
    {
        return Promise.reject(new Error('Please add date and time'));
    }
    const status = state.form.screeningDetailsPopupForm.values.result;
    if(status==null||status=="")
    {
        return Promise.reject(new Error('Please select status'));
    }
   
    const mailbagid= state.form.mailbagSecurityFilter.values.mailbagId;
    const editStatus =state.commonReducer.isEdit=='N'?'N':'Y';
    const sernum=(state.commonReducer.initialValues!=null && state.commonReducer.initialValues.serialNumber!=undefined)?state.commonReducer.initialValues.serialNumber:"0";
    
    const mailseqnum=state.listmailbagReducer.malseqnum;
    let screeningDetailpopup ={location,method,securityStatus,date,time,status,mailbagid,editStatus,mailseqnum,sernum};
    const data ={screeningDetailpopup};

   
const url = 'rest/mail/operations/mailbagsecuritydetails/savescreeningDetailsPopup';
return makeRequest({
    url, data: { ...data }
}).then(function (response) {
        const payLoad={ type: 'SAVE_SUCCESS', response, dispatch }
        handleResponse(payLoad);
        return response;
    })
        .catch(error => {
            return error;
        });
}

export function saveNewConsignorDetails(values) {
    const { args, dispatch, getState, } = values;
    const state = getState();
if( state.form.consignorDetailsPopupForm.values==undefined)
{
    return Promise.reject(new Error('Please enter values'));
}
    const agenttype = state.form.consignorDetailsPopupForm.values.agenttype;
        if( agenttype==undefined || agenttype== null || agenttype=="")
    {
        return Promise.reject(new Error('Please enter values for Agent Type'));
    }
    const agentId = state.form.consignorDetailsPopupForm.values.agentId;
    if( agentId==undefined || agentId== null || agentId=="")
    {
        return Promise.reject(new Error('Please enter values for Agent Id'));
    }
    const isoCountryCode = state.form.consignorDetailsPopupForm.values.isoCountryCode;
    const expiryDate = state.form.consignorDetailsPopupForm.values.expiryDate;
    const mailbagid= state.form.mailbagSecurityFilter.values.mailbagId;
    const mailseqnum=state.listmailbagReducer.malseqnum;
    const editStatus = state.commonReducer.isEdit == 'N'?'N':'Y';
    const sernum=(state.commonReducer.initialValues!=null)?state.commonReducer.initialValues.serialNumber:"0";
    let consignorDetailpopup ={agenttype,agentId,isoCountryCode,expiryDate,mailbagid,editStatus,mailseqnum,sernum};
    const data ={consignorDetailpopup};
   
const url = 'rest/mail/operations/mailbagsecuritydetails/saveconsignorDetailsPopup';
return makeRequest({
    url, data: { ...data }
}).then(function (response) {
        const payLoad={ type: 'SAVE_SUCCESS_CONSIGNOR', response, dispatch }
        handleResponse(payLoad);
        return response;
    })
        .catch(error => {
            return error;
        });
}


function handleResponse(payLoad) { 
    const {response}=payLoad;
    if (!isEmpty(response)) {
        if(!isEmpty(response.errors)){
            const {type,...rest}=payLoad;
            dispatchData({type: 'ERROR_SHOW',...rest});
        }
        else{
            dispatchData(payLoad);
        }
    }
    else{
        dispatchData(payLoad);
    }
}

export const dispatchData = (payload) => {
    const {dispatch,response,type}=payload;
    dispatch({type}) 
}