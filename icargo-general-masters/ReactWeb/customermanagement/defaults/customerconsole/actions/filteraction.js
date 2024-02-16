import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { getMoney } from 'icoreact/lib/ico/framework/action/moneyaction';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import * as types from '../actiontypes.js'; 

export function clearFilter(values) {
    const { dispatch } = values;
    dispatch({ type: types._CLEAR_FILTER, response: {} });
}

export function onList(values) {
    const { dispatch, getState } = values;
    const state = getState();
    if (state.form.customerConsoleFilter.values) {
        const url = 'rest/customermanagement/defaults/customerconsole/getCustomerDetails';
        const data = {}
        data.customerCode = state.form.customerConsoleFilter.values.inboundAccNo
        return makeRequest({
            url, data: data
        }).then(function (response) {
            handleOnListResponse(dispatch, response);
            return response;
        }).catch(error => {
            return error;
        });

    }
    else {
        dispatch(requestValidationError('Customer Code is mandatory', ''));
        dispatch({ type: types._NO_FILTER_SELECTED });
        return Promise.resolve({});
    }

}

function handleOnListResponse(dispatch, response) {
    if (!isEmpty(response) && response.results && response.results[0].customerDetails) {
        dispatch(asyncDispatch(getMoney)(response.results[0].customerDetails.billingCurrency))
        dispatch({ type: types._LIST_SUCCESS, data: response.results[0].customerDetails });
    }
    else {
        dispatch({ type: types._INVALID_CUSTOMER });
    }
}
export function changeFilterMode(mode) {
    return { type: types._ONFILTER_CLICK, mode };
}

export function getBillingInvoiceDetails(values) {
    const { dispatch, getState,args } = values;
    const state = getState();
    const pageNumber = args && args.pageNumber ? args.pageNumber : state.invoiceReducer.pageNumber;
    const pageSize = args && args.pageSize ? args.pageSize : state.invoiceReducer.pageSize;
    const url = 'rest/customermanagement/defaults/customerconsole/getBillingInvoiceDetails';
    const data = {}
    data.customerCode = state.form.customerConsoleFilter.values.inboundAccNo
    data.pageNumber=pageNumber
    data.pageSize=pageSize
    return makeRequest({
        url, data: data
    }).then(function (response) {
         if (!response.errors && args && args.mode === 'EXPORT'){
             console.log(response.results[0].billingInvoiceDetails.invoiceDetails);
             return response.results[0].billingInvoiceDetails.invoiceDetails;
         }
        handleGetInvoiceDetailsResponse(dispatch, response);
        return response;
    }).catch(error => {
        return error;
    });

}

function handleGetInvoiceDetailsResponse(dispatch, response) {
    if (!isEmpty(response) && response.results && response.results[0]) {
        dispatch({ type: 'BILLING_INVOICE_DETAILS_SUCCESS', data: response.results[0] });
    }

}




