import * as types from '../actiontypes.js'; 


const intialState = {
  inboundAccNo: '',
  filterDisplayMode: 'initial',
  noRecordFound: true,
  customerDetails: {},
  ageingReceivables: [],
  receivablesCreditInfo: {},
  statusView: []
}

const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case types._CLEAR_FILTER:
      return intialState;
    case types._LIST_SUCCESS:
      return { ...state,inboundAccNo:action.data.customerCode, filterDisplayMode: 'list', noRecordFound: false, customerDetails: action.data };
    case types._ONFILTER_CLICK:
      return { ...state, filterDisplayMode: action.mode };
    case types._NO_FILTER_SELECTED:
      return intialState;
    case types._INVALID_CUSTOMER:
      return { ...state, filterDisplayMode: 'list', noRecordFound: true, customerDetails: {} }
    case types._BILLING_INVOICE_DETAILS_SUCCESS:
      return { ...state, ageingReceivables:action.data.billingInvoiceDetails.receivablesAgeing?action.data.billingInvoiceDetails.receivablesAgeing:[],statusView: action.data.billingInvoiceDetails.statusView ? action.data.billingInvoiceDetails.statusView : [], receivablesCreditInfo: action.data.billingInvoiceDetails.receivablesCreditInfo ? action.data.billingInvoiceDetails.receivablesCreditInfo : {} }

    default:
      return state;
  }
}

export default filterReducer;