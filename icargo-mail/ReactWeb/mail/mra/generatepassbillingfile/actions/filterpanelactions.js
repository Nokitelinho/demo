import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { LIST_ACTION_URL, CLEAR_FILTER, LIST_SUCCESS,TOGGLE_SCREEN_MODE,SHOULD_SERVE_MOCKED_RESPONSE,POPULATE_PERIOD_ACTION_URL } from '../constants';
import { getMockedListResponse } from '../temp/sample-data/list_response';
import * as constant from '../constants/constants';
import { change} from 'icoreact/lib/ico/framework/component/common/form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function clearFilter(values) {
    const { dispatch } = values;
    dispatch({ type: CLEAR_FILTER, response: {} });
}
export function changeScreenMode(mode) {
    return { type:TOGGLE_SCREEN_MODE, data: mode };
}

const mockResponse = (request) => {
    return getMockedListResponse();
}

export function generatepassbillingfileScreenList(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = LIST_ACTION_URL;
    const request = {};
    const makeAPICall = !SHOULD_SERVE_MOCKED_RESPONSE;
    return (makeAPICall ? makeRequest({
        url,
        data: request
    }) : mockResponse(request)).then(function (response) {
        handleScreenListResponse(dispatch, response);
        return response;
    })
        .catch(error => {
            return error;
        });
}

function handleScreenListResponse(dispatch, response) {
    if (response.results) {
        dispatch(constructScreenListSuccess(response.results[0]));
    }
}
function constructScreenListSuccess(data) {
    return { type: LIST_SUCCESS, data };
}


export function populatePeriodNumberAndDate(values) {

    const { args,dispatch, getState } = values;
    const state = getState();
    const filterPanelReducer = state.filterpanelreducer;
    const source= args.populateSource
    const filter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:'';
    const filterValues = {passFilter:{country:filter.country , gpaCode:filter.gpacode, periodNumber:filter.periodnumber,
        fromBillingDate:filter.fromDate,toBillingDate:filter.toDate, fileName:filter.filename,
        includeNewInvoice:filter.includenewinvoice},populateSource:source,validPeriodNumber:filterPanelReducer.isValidPeriodNumber,validBillingPeriod:filterPanelReducer.isValidBillingPeriod}
    const url = POPULATE_PERIOD_ACTION_URL

    if( (filter.fromDate&&filter.toDate) || (filter.periodnumber && filter.periodnumber!="") )  {

    if(filter.fromDate&&filter.toDate) {
        var from = new Date(filter.fromDate);
        var to = new Date(filter.toDate);
        if(to<from){
            return Promise.reject(new Error("To Date cannot be less than From Date"));
        }
    } 

    return makeRequest({
        url,
        data: {...filterValues}
    }).then(function (response) {
        handlePopulatePeriodResponse(dispatch, response);
        return response;
    })

}else{
    return Promise.resolve({})
    
}

}

function handlePopulatePeriodResponse(dispatch, response) {
    if (response.results) {
        const filterValues = response.results[0]?response.results[0].passFilter:'';
        dispatch(change('filterPanelForm', 'periodnumber',filterValues.periodNumber));
        dispatch(change('filterPanelForm', 'fromDate',filterValues.fromBillingDate));
        dispatch(change('filterPanelForm', 'toDate',filterValues.toBillingDate));
        dispatch({type:constant.POPULATE_PERIOD,data:response.results[0]});
    }
}



