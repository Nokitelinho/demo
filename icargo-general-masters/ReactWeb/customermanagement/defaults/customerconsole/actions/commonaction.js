import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen,closeScreenWithDialog } from 'icoreact/lib/ico/framework/action/navigateaction';
import { requestValidationError, requestInfoWithAutoClose, requestError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import * as types from '../actiontypes.js'; 

export function onClose() {
    //navigateToScreen('home.jsp', {});
    closeScreenWithDialog('customerConsoleFilter', 'home.jsp', {});
}
export function listCCADetails(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    if ( state.invoiceReducer.selectedInvoice.length > 1||state.invoiceReducer.selectedInvoice.length===0) {
        dispatch(requestValidationError('Please select one Invoice.', ''));
    }
    else {
        const url = 'rest/customermanagement/defaults/customerconsole/getCCADetails';
        const invoiceNumber = state.invoiceReducer.selectedInvoice[0].invoiceNumber
        return makeRequest({
            url, data: { invoiceNumber }
        }).then(function (response) {
            if (args && args.mode === 'EXPORT') {
                return response.results[0].ccaDetails
            }
            handleOnListCCAResponse(dispatch, response);
            return response;
        }).catch(error => {
            return error;
        });


    }
}
function handleOnListCCAResponse(dispatch, response) {
    if (response && response.results && response.results[0].ccaDetails) {
        dispatch({ type: types._SELECT_LIST_CCA, ccaDetails: response.results[0].ccaDetails });
    }
    else {
        if (response.results[0].ccaDetails === null) {
            dispatch({ type: types._SELECT_LIST_CCA, ccaDetails: [] })
        }

    }
}
export function closeCCADetails(values) {
    const { dispatch } = values;
    dispatch({ type: types._CLOSE_LIST_CCA });
}
export function listPaymentDetails(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const customerCode= state.filterReducer.customerDetails.customerCode;
    if ( state.invoiceReducer.selectedInvoice.length > 1||state.invoiceReducer.selectedInvoice.length===0) {
        dispatch(requestValidationError('Please select one Invoice.', ''));
    }
    else {
        const url = 'rest/customermanagement/defaults/customerconsole/getPaymentDetails';
        return makeRequest({
            url, data: { invoiceDetails: state.invoiceReducer.selectedInvoice[0] ,customerCode:customerCode}
        }).then(function (response) {
            handleOnListPaymentResponse(dispatch, response);
            return response;
        }).catch(error => {
            return error;
        });
    }
}
function handleOnListPaymentResponse(dispatch, response) {
    if (response && response.results && response.results[0].paymentDetails) {
        dispatch({ type:types._SELECT_PAYMENT_DETAILS, paymentDetails: response.results[0].paymentDetails });
    }
}
export function closePaymentDetails(values) {
    const { dispatch } = values;
    dispatch({ type: types._CLOSE_PAYMENT_DETAILS });
}

export function getAccountStatement(values) {
    const { dispatch,getState,args } = values;
    const state = getState();
    const data = {}
     data.customerCode = state.form.customerConsoleFilter.values.inboundAccNo 
    data.exportMode=args
    var url = 'rest/customermanagement/defaults/customerconsole/getAccountStatement';
    return makeRequest({
        url, data, download:true
    }).then(function (response) {
        handleDownloadFileResponse(dispatch, response);
        return response
    }).catch(error => {
        return error;
    });
}

function handleDownloadFileResponse(dispatch, response) {
    if (isEmpty(response)) {
        dispatch({ type: types._DOWNLOAD_FILE_SUCCESS })
    }
}


export function navigateActions(values) {
    const { dispatch, getState, args } = values;
    const state = getState();
    const customerCode = state.filterReducer.customerDetails.customerCode;

    if (args && (args === "CD" || args === "UP")) {
        if (args === "CD") {
            navigateToCustomerMaster(customerCode)
        }
        else {
            navigateToListSettlementBatch(customerCode)
        }
        dispatch({ type:types._NAVIGATE_SUCCESS });
    }
    else {

        if (state.invoiceReducer.selectedInvoice.length === 0) {
            dispatch(requestValidationError('Please select an Invoice.', ''));
        }
        else {

            if (args === "OR") {
                navigateToOutstandingReceivables(dispatch, state.invoiceReducer.selectedInvoice,customerCode);
            }
            else {
                if (state.invoiceReducer.selectedInvoice.length > 1) {
                    dispatch(requestValidationError('Please select one Invoice.', ''));

                }
                else {
                    navigateToInvoiceDetails(state.invoiceReducer.selectedInvoice[0], customerCode)
                    dispatch({ type: types._NAVIGATE_SUCCESS });
                }
            }
        }
    }
}

function navigateToCustomerMaster(customerCode) {
    const data = { customerCode: customerCode, pageURL: "customermanagement.defaults.customerconsole" }
    navigateToScreen("customermanagement.defaults.listcustomerregistratrion.do", data);
}
function navigateToListSettlementBatch(customerCode) {
    const params = { fromScreen: "customermanagement.defaults.customerconsole", customerCode: customerCode, status: "U", source: "LB" }
    navigateToScreen("cra.agentbilling.noncass.ux.listsettlementbatchesscreenload.do", params);
}
function navigateToOutstandingReceivables(dispatch, invoice,customerCode) {
    let billingType = "";
    let invoiceNumber = "";
    let navigateFlag = false;
    for (var i = 0; i < invoice.length; i++) {
        if (i === 0) {
            billingType = invoice[i].billingType
            invoiceNumber = invoice[i].invoiceNumber
            navigateFlag = true
        }
        else {
            if (billingType != invoice[i].billingType) {
                dispatch(requestValidationError('Please select invoices of same billing type.', ''));
                navigateFlag = false
                break;

            }
            else {
                invoiceNumber = invoiceNumber + "," + invoice[i].invoiceNumber
            }
        }
    }
    if (navigateFlag) {
        const url = 'rest/customermanagement/defaults/customerconsole/reminderList';
        return makeRequest({
            url, data: {}
        }).then(function (response) {
            let data;  
            if(billingType == "CE" || billingType == "CI"){
              data = { approvalParam: response.results[0].reminderListApprovalParameter, typeOfBilling: billingType, invoiceNumber: invoiceNumber}
            }else{
              data = { approvalParam: response.results[0].reminderListApprovalParameter, typeOfBilling: billingType, invoiceNumber: invoiceNumber,agentCode: customerCode}
            }
            navigateToScreen("cra.agentbilling.defaults.reminderlist.list.do?navigationMode=LIST", data);
            dispatch({ type:types._NAVIGATE_SUCCESS });
            return response;
        }).catch(error => {
            return error;
        });
    }


}
function navigateToInvoiceDetails(selectedInvoice, customerCode) {
    let data = {}

    if (selectedInvoice.billingType === "NE" || selectedInvoice.billingType === "RE") {
        data = { invoiceNo: selectedInvoice.invoiceNumber, totalsFlag: "totals", lastPageNum: 0, displayPage: 1, parentId: "customermanagement.defaults.customerconsole" }
        navigateToScreen("cra.agentbilling.noncass.exportbillinginvoicedetails.do?paginationMode=LIST", data)
    }
    else if (selectedInvoice.billingType === "NI") {
        data = { invoiceNo: selectedInvoice.invoiceNumber, statusFlag: "total_Required", lastPageNum: 0, displayPage: 1, parentScreen: "customermanagement.defaults.customerconsole" }
        navigateToScreen("cra.agentbilling.noncass.listccbillinginvoice.do?paginationMode=LIST", data)
    }
    else if (selectedInvoice.billingType === "CE" || selectedInvoice.billingType === "CI") {
        data = { pageType: "Both", country: selectedInvoice.countryCode, billFrom: selectedInvoice.periodFromDate, billTo: selectedInvoice.periodToDate, cassBillingType: selectedInvoice.billingType === 'CE' ? 'E' : 'I', hiddenAdjFlgReq: selectedInvoice.adjustmentFlag, fromScreen: "customermanagement.defaults.customerconsole" }
        navigateToScreen("cra.agentbilling.cass.viewcassbillingfile.viewcassbillingfile.do", data)
    }
}
export function emailAccountStatement(values) {
  const { dispatch, getState, args } = values;
  const state = getState();
  const data = {};
  data.customerCode = state.form.customerConsoleFilter.values.inboundAccNo;
  var url =
    "rest/customermanagement/defaults/customerconsole/emailaccountstatement";
  return makeRequest({
    url,
    data,
  })
    .then(function (response) {
      if (types._RESPONSE_SUCCESS === response.status) {
        return dispatch(requestInfoWithAutoClose(["Email Sent Successfully"]));
      } else if (types._RESPONSE_FAILURE === response.status) {
        return dispatch(requestError(["Valid distribution mode not opted for the agent."]));
      }
    })
    .catch((error) => {
      return error;
    });
}