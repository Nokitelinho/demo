import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isDirty } from 'redux-form';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
export function screenLoad(args) {
    const { dispatch } = args;
    const url = 'rest/mail/operations/mailboxId/screenload';
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
        dispatch({ type: 'TOGGLE_MESSAGING_OPTION', data: "Y" });

    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function onCloseFunction(values) {
 const { dispatch, getState } = values;
    const state = getState();
 let unSavedDataExists = false;
 if (isDirty("mailboxidPanel")(state) || isDirty("mailBoxIdTable")(state)) {
 unSavedDataExists = true;
 }
if(state.form.mailBoxIdTable&&state.form.mailBoxIdTable.values) {
    for (var i=0;i < state.form.mailBoxIdTable.values.mailBoxIdTable.length; i++) {
    if (state.form.mailBoxIdTable.values.mailBoxIdTable[i].__opFlag) {
    unSavedDataExists = true;
    break;
      }
    }
  }
 if(unSavedDataExists){
 dispatch(requestWarning([{ description: "Unsaved Data exists. Do you want to continue?" }], { functionRecord: closeScreen ,args:{warningCode:"close"}}))
 }
 else{
        navigateToScreen('home.jsp', {});
}
}

export function closeScreen(values) {
    
    navigateToScreen('home.jsp', {});
}

export function deleteRows(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const rowIndex = state.commonReducer.selectedMailEventIndex ? state.commonReducer.selectedMailEventIndex : [];
    if (rowIndex.length > 0) {

        dispatch(deleteRow("mailBoxIdTable", rowIndex))

    } else {
        dispatch(requestValidationError('Please select atleast one row to delete', ''));
    }
}

export function getTotalCount(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let selectedMailbagsIndex = [];
    const mailEvents = (state.form.mailBoxIdTable && state.form.mailBoxIdTable.values && state.form.mailBoxIdTable.values.mailBoxIdTable) ?
        state.form.mailBoxIdTable.values.mailBoxIdTable : [];
    const count = mailEvents.length;
    for (let i = 0; i < count; i++) {
        selectedMailEventIndex.push(i);
    }
    dispatch({ type: 'SELECTED_COUNT', selectedMailEventIndex });
}

export function saveSelectedMailEventIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailEventIndex ? state.commonReducer.selectedMailEventIndex : [];

    indexes.push(index);
    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes });
}
export const warningHandler = (action, dispatch) => {
    
 
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                const argument = action.executionContext.args.warningCode;
                if (warningCode[0] === "Mail Box Id does not exist. Do you want to create a new one?") {
                    dispatch({ type: 'NEW_MAILBOX_ID'});
                } 
                else if(argument === "close"){
                    dispatch(dispatchAction(closeScreen)());
                }
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if ( warningCode[0] == "Mail Box Id does not exist. Do you want to create a new one?") {
                    
                dispatch({type: 'CLEAR_FILTER'});
                }
            }
            break;
        default:
            break;
    }
}
export function saveUnselectedMailEventIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailEventIndex ? state.commonReducer.selectedMailEventIndex : [];

    if (index > -1) {
        let ind = -1;
        for (let i = 0; i < indexes.length; i++) {
            var element = indexes[i];
            if (element === index) {
                ind = i;
                break;
            }
        }
        if (ind > -1) {
            indexes.splice(ind, 1);
        }
    } else {
        indexes = [];
    }


    dispatch({ type: 'SAVE_SELECTED_INDEX', indexes });
}


export function saveMailboxData(values) {
    const { args, dispatch, getState, } = values;
    const state = getState();
    let data={};
        const mailboxId = state.form.mailboxidFilter.values.mailboxId;
        const mailEvents = (state.form.mailBoxIdTable && state.form.mailBoxIdTable.values && state.form.mailBoxIdTable.values.mailBoxIdTable) ?
            state.form.mailBoxIdTable.values.mailBoxIdTable : [];
        const mailboxName = state.form.mailboxidPanel.values.mailboxName;
        const messagingEnabled = state.form.mailboxidPanel.values.messagingEnabled;
        const msgEventLocationNeeded = state.form.mailboxidPanel.values.msgEventLocationNeeded;
        const ownerCode = state.form.mailboxidPanel.values.ownerCode;
        const partialResdit = state.form.mailboxidPanel.values.partialResdit;
        const resditTriggerPeriod = state.form.mailboxidPanel.values.resditTriggerPeriod;
        const resditversion = state.form.mailboxidPanel.values.resditversion;
        const mailboxOwner = state.form.mailboxidPanel.values.mailboxOwner;
        const remarks = state.form.mailboxidPanel.values.remarks;
        const mailboxStatus = state.filterReducer.mailboxStatus?state.filterReducer.mailboxStatus:'INACTIVE';
        if("P"===state.form.mailboxidPanel.values.messagingEnabled && 0===state.form.mailBoxIdTable.values.mailBoxIdTable.length)
        {
            return Promise.reject(new Error("Please Click Add and Enter Table Details"));
        }
        if(state.form.mailboxidPanel.values.messagingEnabled==="P")
        {
            data = { mailboxStatus,mailboxId,mailboxOwner, mailEvents, mailboxName, messagingEnabled, msgEventLocationNeeded, ownerCode, partialResdit, resditTriggerPeriod, resditversion, remarks };
        }
        else
        {
            data = { mailboxStatus,mailboxId ,mailboxOwner, mailboxName, messagingEnabled, msgEventLocationNeeded, ownerCode, partialResdit, resditTriggerPeriod, resditversion, remarks };
        }
        if(!state.form.mailboxidPanel.values.mailboxName){
            return Promise.reject(new Error("Please enter MailBox Name"));
        }
        if(!state.form.mailboxidPanel.values.ownerCode){
            return Promise.reject(new Error("Please enter Owner Code"));
        }
        // if(!state.form.mailboxidPanel.values.resditTriggerPeriod){
        //     return Promise.reject(new Error("Please enter Resdit Triggering Period"));
        // }
        if(("P"===state.form.mailboxidPanel.values.messagingEnabled || "Y"===state.form.mailboxidPanel.values.messagingEnabled) && !state.form.mailboxidPanel.values.resditversion){
            return Promise.reject(new Error("Please enter Resdit Version"));
        }
        if(!state.form.mailboxidPanel.values.mailboxOwner){
            return Promise.reject(new Error("Please enter MailBox Owner"));
        }
        if(state.form.mailboxidPanel.values.messagingEnabled==="P")
        {
            let i=0;
            for(i=0 ; i<state.form.mailBoxIdTable.values.mailBoxIdTable.length ; i++)
            {
                if(!state.form.mailBoxIdTable.values.mailBoxIdTable[i].mailCategory){
            return Promise.reject(new Error("Please enter Mail Category"));
        }
                if(!state.form.mailBoxIdTable.values.mailBoxIdTable[i].mailClass){
            return Promise.reject(new Error("Please enter Sub Class"));
                }
            }
        }
    const url = 'rest/mail/operations/mailboxId/save';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        return response;
    })
        .catch(error => {
            return error;
        });
}

export function Active(values){
    const { args, dispatch, getState, } = values;
    const state = getState();
    let operationalFlag='';
        const mailboxId = state.filterReducer.filterValues;
        const mailEvents = state.filterReducer.mailEvents;
        const mailboxName = state.filterReducer.mailboxName;
        const messagingEnabled = state.filterReducer.messagingEnabled;
        const msgEventLocationNeeded = state.filterReducer.msgEventLocationNeeded;
        const ownerCode =state.filterReducer.ownerCode;
        const partialResdit = state.filterReducer.partialResdit;
        const resditTriggerPeriod = state.filterReducer.resditTriggerPeriod;
        const resditversion =state.filterReducer.resditversion;
        const mailboxOwner =state.filterReducer.mailboxOwner;
        const oldStatus = state.filterReducer.mailboxStatus;
        let mailboxStatus = '';
        oldStatus === 'ACTIVE'? mailboxStatus='INACTIVE':mailboxStatus='ACTIVE';
        oldStatus==='ACTIVE'? operationalFlag='ACTIVE':operationalFlag='INACTIVE';
        const data = { mailboxStatus,mailboxId,mailboxOwner, mailEvents,operationalFlag, mailboxName, messagingEnabled, msgEventLocationNeeded, ownerCode, partialResdit, resditTriggerPeriod, resditversion, status };
    
    const url = 'rest/mail/operations/mailboxId/save';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        return response;
    })
        .catch(error => {
            return error;
        });
}