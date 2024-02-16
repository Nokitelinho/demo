import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { SCREENLOAD_SUCCESS} from '../constants';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction'
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { getMockedScreenLoadResponse } from '../temp/sample-data/screenload_response';
import * as constant from '../constants/constants';


export function generatepassbillingfileScreenLoad(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const url = 'rest/mail/mra/gpabilling/generatepassbillingfile/screenload';
    return makeRequest({
        url, data: {}
    }).then(function (response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })
}

function handleScreenloadResponse(dispatch, response) {

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
                if (warningCode === "mail.mra.generatepassbillingfile.onok") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode == 'mail.mra.generatepassbillingfile.oncancel') {
                }
            }
            break;
        default:
            break;
    }
}

export function onGenerate(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const filterPanelReducer = state.filterpanelreducer;

    if(!filterPanelReducer.isValidPeriodNumber){
        return Promise.reject(new Error("Invalid Period Number"));
    }else if(!filterPanelReducer.isValidBillingPeriod){
        return Promise.reject(new Error("Invalid Billing Period"));
    }

    const filter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:'';
    const filterValues = {passFilter:{country:filter.country , gpaCode:filter.gpacode, periodNumber:filter.periodnumber,
        fromBillingDate:filter.fromDate,toBillingDate:filter.toDate, fileName:filter.filename,
        includeNewInvoice:filter.includenewinvoice}}
    const url = 'rest/mail/mra/gpabilling/generatepassbillingfile/generate';
    return makeRequest({
        url,
        data: {...filterValues}
    }).then(function (response) {
        handleGenerateResponse(dispatch, response);
        return response;
    })
}

function handleGenerateResponse(dispatch, response) {
    if (response.results) {
        dispatch({type:constant.GENERATE_SUCCESS});
    }
}

export function onGenerateLog(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const mra ='M'
    const processType = 'PA'
    const params = {
    subsystem:mra,
    invoiceType:processType,
    parentScreen: "Generate PASS Billing File" ,
    fromScreen:'PASS_BILLING',
    numericalScreenId: 'MRA082'
    }
    var url = "cra.defaults.invoicegenerationlog.list.do"
    navigateToScreen(url, params);
}