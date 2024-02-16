import { ActionType } from '../constants/constants.js';
import { handleResponse} from './handleresponse.js';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';

//For closing of advance payment pop up
export function closeAddPaymentPopup(values) {
    const {dispatch } = values;
    const payLoad={ type: ActionType.ADD_PAY_CLS, dispatch }
    handleResponse(payLoad);
}

//For Creating New Batch ID
export function createPaymentPopup(values){
    const {dispatch,getState} = values;
    const state = getState();
    let addPaymentFilter = (state.form.addPaymentPopUp.values)?state.form.addPaymentPopUp.values:{};
    const data ={addPaymentFilter};
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/create';
    return makeRequest({
        url,
        data: {...data}
    }).then(function(response) {
        const payLoad={ type: ActionType.CREATE_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })  

}
//For updating amount and currency
export function okPaymentPopup(values){
    const {dispatch,getState} = values;
    const state = getState(); 
    let addPaymentFilter = (state.form.addPaymentPopUp.values)?state.form.addPaymentPopUp.values:{};
    addPaymentFilter.batchID = state.filterReducer.paymentbatchdetail.batchID;
    addPaymentFilter.poaCod = state.filterReducer.paymentbatchdetail.paCode;
    addPaymentFilter.batchDate=state.filterReducer.paymentbatchdetail.date;
    const data ={addPaymentFilter};
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/ok';
    return makeRequest({
        url,
        data: {...data}
    }).then(function(response) {
        const payLoad={ type: ActionType.OK_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    }) 
}





