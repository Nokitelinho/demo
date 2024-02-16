import React, { Fragment } from 'react';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';





export function listmailbagSecurityDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();

    const mailbagSecurityFilter = state.form.mailbagSecurityFilter.values ? state.form.mailbagSecurityFilter.values : null;

    if (isEmpty(mailbagSecurityFilter)) {
        return Promise.reject(new Error("Please enter mailbagId"));
    }

    const warningFlag = state.filterReducer.warningFlag;
    const { displayPage, action } = args;
    const pageSize = args && args.pageSize ? args.pageSize : 1;
    const data = { displayPage, pageSize, ...mailbagSecurityFilter,warningFlag};
    const url = 'rest/mail/operations/mailbagsecuritydetails/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, data, action);
        return response
    })
        .catch(error => {
            return error;
        });

}

export const onChangeScreenMode = (values) => {
    const { args, dispatch } = values;
    const mode = args;
    dispatch({ type: 'CHANGE_MODE', mode });
}

function handleResponse(dispatch, response, data, action) {


        if ((!isEmpty(response.results) && response.status == 'success') || response.results[0].warningFlag === true) {
        const mailbagId = response.results[0].mailbagId;
        const consignmentScreeningDetailVos = response.results[0].consignmentScreeningDetail;
        const origin = response.results[0].origin;
        const destination = response.results[0].destination;
		const malseqnum = response.results[0].malseqnum;
        const airportCode = response.results[0].airportCode;
        const mailbagSecurityDetails = consignmentScreeningDetailVos;
        const oneTimeValues = response.results[0].oneTimeValues;
        const StatusParty=response.results[0].loginUser;
		const securityStatusCode = response.results[0].securityStatusCode;
		const timeZone=response.results[0].timeZone;
        let consignerDetails =[];
        let screeningDetails= [];
        let screenDetailType = '';
        if(mailbagSecurityDetails != null && mailbagSecurityDetails.length > 0){
         consignerDetails = mailbagSecurityDetails.filter((value) => {
            if (value.screenDetailType == 'CS')
                return value;
        }
        )

         screeningDetails = mailbagSecurityDetails.filter((value) => {
            if (value.screenDetailType == 'SM')
                return value;
        }
        )
    }
    else{
        consignerDetails.screenDetailType= 'CS';
        screeningDetails.screenDetailType = 'SM';
    }
        if (action === 'LIST') {

            dispatch({ type: 'LIST_SUCCESS', mailbagSecurityDetails, mailbagId, origin, destination, consignerDetails, screeningDetails, oneTimeValues,malseqnum,airportCode,StatusParty,securityStatusCode,timeZone });
        }
    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: 'CLEAR_TABLE' });
        }
    }
}



export const clearPanelFilter = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'CLEAR_FILTER' });
    dispatch(reset('mailbagSecurityFilter'));

}
export const onSelect = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'POP_OVER_OPEN' });
}

export const onOKClick = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'POP_OVER_CLOSE' });
}

export const onClose = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'POP_OVER_CLOSE' });
}

export const onSaveSecurityStatus = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();

    const { action } = args;
    const securityStatusCode = state.form.securityPopOverForm.values ? state.form.securityPopOverForm.values : "";

    if (isEmpty(securityStatusCode)) {
        return Promise.reject(new Error("Please select security status code"));
    }
    const mailbagId = state.listmailbagReducer.mailbagId ? state.listmailbagReducer.mailbagId : null;
    const malseqnum = state.listmailbagReducer.malseqnum ? state.listmailbagReducer.malseqnum : null;
    const data = { ...securityStatusCode, mailbagId, malseqnum };
    const url = 'rest/mail/operations/mailbagsecuritydetails/saveSecurityStatusCode';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {

        return response
    })
        .catch(error => {
            return error;
        });
}