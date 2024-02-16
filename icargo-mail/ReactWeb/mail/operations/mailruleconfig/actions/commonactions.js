import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { SCREENLOAD_ACTION_URL, SCREENLOAD_SUCCESS, SHOULD_SERVE_MOCKED_RESPONSE ,SAVE_ACTION_URL} from '../constants';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction'
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { getMockedScreenLoadResponse } from '../temp/sample-data/screenload_response';

const mockResponse = (request) => {
    return getMockedScreenLoadResponse();
}

export function mailruleconfigScreenLoad(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = SCREENLOAD_ACTION_URL;
    const request = {};
    const makeAPICall = !SHOULD_SERVE_MOCKED_RESPONSE;
    return makeRequest({
        url,
        data: request
    }).then(function (response) {
        handletScreenloadResponse(dispatch, response);
        return response;
    })
        .catch(error => {
            return error;
        });
}

function handletScreenloadResponse(dispatch, response) {

    if (response.results) {
        dispatch(constructScreenLoadSuccess(response.results[0]));
    }
}

function constructScreenLoadSuccess(data) {
    return { type: SCREENLOAD_SUCCESS, data };
}

export function onClose(values) {
    const { getState } = values;
    const state = getState();
    navigateToScreen('home.jsp', {});
}

export const warningHandler = (action, dispatch) => {
    debugger;
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode === "mail.operations.mailruleconfig.onok") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }
                if (warningCode[0] === "Mail Box Id does not exist. Do you want to create a new one?") {
                    dispatch(dispatchAction(action.executionContext.functionRecord)({validation:false}));
               
                } 
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode == 'mail.operations.mailruleconfig.oncancel') {
                }
               
            }
            break;
        default:
            break;
    }
}
export function saveMailRuleConfig(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = SAVE_ACTION_URL;
    let mailRuleConfigList= state.filterpanelreducer.newMailRuleList;
   
    const request = { mailRuleConfigList };
    return makeRequest({
        url,
        data: { ...request }
    }).then(function (response) {
       // handleScreenSaveResponse(dispatch, response);
        return response;
    })
        .catch(error => {
            return error;
        });
}