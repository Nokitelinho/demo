import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import { warningHandler } from '../actions/commonaction';
import { resetForm } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


export function toggleFilter(screenMode) {
    return { type: 'TOGGLE_FILTER', screenMode };
}
export function messagingOptionChange(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
        // state.filterReducer.mailboxOwner = state.form.mailboxidPanel.values.mailboxOwner;
        // state.filterReducer.mailboxName = state.form.mailboxidPanel.values.mailboxName;
        // state.filterReducer.ownerCode = state.form.mailboxidPanel.values.ownerCode;
        // state.filterReducer.resditTriggerPeriod = state.form.mailboxidPanel.values.resditTriggerPeriod;
        // state.filterReducer.resditversion = state.form.mailboxidPanel.values.resditversion;
        // state.filterReducer.partialResdit = state.form.mailboxidPanel.values.partialResdit;
        // state.filterReducer.msgEventLocationNeeded = state.form.mailboxidPanel.values.msgEventLocationNeeded;
        // state.filterReducer.messagingEnabled = args;
        const data = args;

    
    dispatch( { type: 'TOGGLE_MESSAGING_OPTION', data });
}




export function listMailboxDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode = state.filterReducer.screenMode
    // const {displayPage,action} = args;
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
    const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;
    const mailboxidFilter = (state.form.mailboxidFilter.values) ? state.form.mailboxidFilter.values : {}
    mailboxidFilter.displayPage = displayPage;
    mailboxidFilter.pageSize = pageSize;
    const filtervalues = { mailboxId: mailboxidFilter.mailboxId };
    if(!state.form.mailboxidFilter.values){
        return Promise.reject(new Error("Please Enter MailBox ID"));
    }
    dispatch({ type: 'TOGGLE_MESSAGING_OPTION', data: "P" })
    const url = 'rest/mail/operations/mailboxId/list';
    return makeRequest({
        url,
        data: { ...filtervalues }
    }).then(function (response) {

        handleResponse(dispatch, response, "LIST");
        return response

    })
        .catch(error => {
            return error;
        });

}

function handleResponse(dispatch, response, action) {

    if (!isEmpty(response.results)) {
        console.log(response.results[0]);
        const mailboxName = response.results[0].mailboxName;
        const ownerCode = response.results[0].ownerCode;
        const partialResdit = response.results[0].partialResdit;
        const msgEventLocationNeeded = response.results[0].msgEventLocationNeeded;
        const resditTriggerPeriod = response.results[0].resditTriggerPeriod;
        const mailEvents = response.results[0].mailEvents;
        const messagingEnabled = response.results[0].messagingEnabled;
        const filterValues = response.results[0].mailboxId;
        const resditversion = response.results[0].resditversion;
        const mailboxOwner = response.results[0].mailboxOwner;
        const mailboxStatus = response.results[0].mailboxStatus;
        const remarks = response.results[0].remarks;

        if (action === 'LIST') {
            dispatch({ type: 'LIST_SUCCESS', mailboxName, ownerCode,mailboxOwner, mailboxStatus, partialResdit, msgEventLocationNeeded, resditTriggerPeriod, resditversion, mailEvents, messagingEnabled, filterValues, remarks });

        }


    }

}

export const clearPanelFilter = (values) => {
    const { dispatch } = values;
    dispatch(resetForm("mailBoxIdTable"));
    dispatch(reset('mailboxidFilter'));
    dispatch(reset('mailboxidPanel'));
    dispatch({ type: 'CLEAR_FILTER' });

}

