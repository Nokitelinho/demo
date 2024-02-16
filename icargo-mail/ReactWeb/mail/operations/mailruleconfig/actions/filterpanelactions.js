import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { LIST_ACTION_URL, CLEAR_FILTER, LIST_SUCCESS, TOGGLE_SCREEN_MODE, SHOULD_SERVE_MOCKED_RESPONSE } from '../constants';
import { getMockedListResponse } from '../temp/sample-data/list_response';


export function clearFilter(values) {
    const { dispatch } = values;
    dispatch({ type: CLEAR_FILTER, response: {} });
}
export function changeScreenMode(mode) {
    return { type: TOGGLE_SCREEN_MODE, data: mode };
}

const mockResponse = (request) => {
    return getMockedListResponse();
}

export function mailruleconfigScreenList(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const url = LIST_ACTION_URL;
    let mailRuleConfigFilter = (state.form.filterPanelForm.values) ? state.form.filterPanelForm.values : state.filterpanelreducer.screenFilter

    const request = { mailRuleConfigFilter };
    return makeRequest({
        url,
        data: { ...request }
    }).then(function (response) {
        handleScreenListResponse(dispatch, response,state);
        return response;
    })
        .catch(error => {
            return error;
        });
}

function handleScreenListResponse(dispatch, response,state) {
    if (response.results) {
        dispatch(constructScreenListSuccess(response.results[0],state));
    }
}
function constructScreenListSuccess(data,state) {
   let tableVlues=  getMockedListResponse();
   let listValues = state.filterpanelreducer.mailRuleConfigList ? state.filterpanelreducer.mailRuleConfigList : [];
     for(let i=0;i<tableVlues.length;i++){
        listValues.push(tableVlues[i])
      }
    return { type: LIST_SUCCESS, data ,listValues};
}


