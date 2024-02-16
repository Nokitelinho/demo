import * as types from '../actiontypes.js'; 


const intialState = {
    invoiceDisplayMode: 'min',
    invoiceDetails: [],
    statusCount: {},
    invoiceFilter: 'ALL',
    invoiceDetailsPage:[],
    pageNumber:1,
    pageSize:'25',
    selectedInvoice: []
}

const invoiceReducer = (state = intialState, action) => {
    switch (action.type) {
        case types._ON_INVOICE_DISPLAY_CHANGE:
            return { ...state, invoiceDisplayMode: action.mode, selectedInvoice: [] };
        case types._CHANGE_INVOICE_FILTER:
            return { ...state, invoiceFilter: action.data, selectedInvoice: [] }
        case types._CLEAR_FILTER:
            return intialState;
        case types._SELECT_INVOICE:
            return { ...state, selectedInvoice: action.data }
        case types._BILLING_INVOICE_DETAILS_SUCCESS:
            return { ...state,selectedInvoice:[], invoiceDetailsPage:action.data.billingInvoiceDetails.invoiceDetails?action.data.billingInvoiceDetails.invoiceDetails:[],invoiceDetails: action.data.billingInvoiceDetails.invoiceDetails? action.data.billingInvoiceDetails.invoiceDetails.results : [], statusCount: action.data.billingInvoiceDetails.statusCount ? action.data.billingInvoiceDetails.statusCount : {}}
        case types._NAVIGATE_SUCCESS:
            return { ...state, selectedInvoice: [] }
        default:
            return state;
    }
}

export default invoiceReducer;