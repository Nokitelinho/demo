import React, { Fragment } from 'react';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import commonReducer from '../reducers/commonreducer';
import listmailbagReducer from '../reducers/listmailbagreducer';

export const addScreenigDetails = (values) => {
    const { args, dispatch,getState } = values;
    const mode = args;
    const state = getState();
    let today = new Date();
        const months = {
            0: 'Jan',
            1: 'Feb',
            2: 'Mar',
            3: 'Apr',
            4: 'May',
            5: 'Jun',
            6: 'Jul',
            7: 'Aug',
            8: 'Sep',
            9: 'Oct',
            10: 'Nov',
            11: 'Dec',
          };
        const monthName = months[today.getMonth()];
        let StatusDate = today.getDate() + '-' + monthName  + '-' + today.getFullYear();
        let t=new Date(today.toLocaleString('en-US', { timeZone: state.listmailbagReducer.timeZone }));
        if(((t.getMinutes().toString()).length)==1)
        {
            const time= t.getHours() + ':0' + t.getMinutes() ;
            dispatch({ type: 'ADD_SCREENING_DETAILS', mode,time,StatusDate });
        }
        else{
        const time= t.getHours() + ':' + t.getMinutes() ;
    dispatch({ type: 'ADD_SCREENING_DETAILS', mode,time,StatusDate });
        }
}

export const addConsignorDetails = (values) => {
    const { args, dispatch } = values;
    const mode = args;
    dispatch({ type: 'ADD_CONSIGNOR_DETAILS', mode });
}

export const editScreeningDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mode = args;
    const screeningDetails = state.listmailbagReducer.screeningDetails[mode.index]
    dispatch({ type: 'EDIT_SCREEN_DETAILS', mode, screeningDetails });
}

export const editConsignorDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mode = args;
    const consignerDetails = state.listmailbagReducer.consignerDetails[mode.index]
    dispatch({ type: 'EDIT_CONSINOR_DETAILS', mode, consignerDetails });
}

export const deleteScreeningDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mode = args;
    const screeningDetails = state.listmailbagReducer.screeningDetails[mode.index]
    const sernum= screeningDetails.serialNumber;
    let screeningDetailpopup ={sernum};
    const data ={screeningDetailpopup};
   
const url = 'rest/mail/operations/mailbagsecuritydetails/deleteDetails';
return makeRequest({
    url, data: { ...data }
}).then(function (response) {
        const payLoad={ type: 'DELETE_SCREEN_DETAILS', response, dispatch }
        handleResponse(payLoad);
        return response;
    })
        .catch(error => {
            return error;
        });
}

export const deleteConsignorDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    const mode = args;
    const consignerDetails = state.listmailbagReducer.consignerDetails[mode.index]
    const sernum= consignerDetails.serialNumber;
    let consignorDetailpopup ={sernum};
    const data ={consignorDetailpopup};
   
    const url = 'rest/mail/operations/mailbagsecuritydetails/deleteDetails';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
            const payLoad={ type: 'DELETE_CONSINOR_DETAILS', response, dispatch }
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
            dispatchData({type: ActionType.ERROR_SHOW,...rest});
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