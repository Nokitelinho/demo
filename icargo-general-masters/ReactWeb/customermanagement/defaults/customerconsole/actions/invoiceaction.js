import * as types from '../actiontypes.js';

export function changeInvoiceDisplayMode(mode) {
    return { type: types._ON_INVOICE_DISPLAY_CHANGE, mode };
}
export function filterInvoiceDetails(values) {
    const { dispatch, args } = values;
    dispatch({ type: types._CHANGE_INVOICE_FILTER, data: args })
}


export function selectInvoice(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    let selectedInvoice = args?args: state.invoiceReducer.selectedInvoice
  

    dispatch({ type: types._SELECT_INVOICE, data: selectedInvoice })
}

export function getIndex(value, arr, prop) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i][prop] === value) {
            return i;
        }
    }
    return -1;
}
