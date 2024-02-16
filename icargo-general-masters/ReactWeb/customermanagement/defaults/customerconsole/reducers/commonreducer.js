
import * as types from '../actiontypes.js'; 


const intialState = {
  relisted: true,
  showListCCA: false,
  ccaDetails: [],
  paymentDetails: []

}

const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    case types._SELECT_LIST_CCA:
      return { ...state, showListCCA: true, ccaDetails: action.ccaDetails }
    case types._CLOSE_LIST_CCA:
      return { ...state, showListCCA: false, ccaDetails: [] }
    case types._SELECT_PAYMENT_DETAILS:
      return { ...state, showPaymentDetails: true, paymentDetails: action.paymentDetails }
    case types._CLOSE_PAYMENT_DETAILS:
      return { ...state, showPaymentDetails: false, paymentDetails: [] }
    case types._DOWNLOAD_FILE_SUCCESS:
    return state
    default:
      return state;
  }
}

export default commonReducer;