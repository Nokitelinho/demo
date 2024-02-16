import { ActionType } from '../constants/constants.js';
import { handleResponse} from './handleresponse.js';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { clearError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';


export function toggleFilter(screenFilterMode) {  
    return {type: ActionType.TOGGLE_FILTER,screenFilterMode };
  }

//For Clear 
export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch(clearError());
    const payLoad={type: ActionType.CLEAR_FILTER,dispatch}
    handleResponse(payLoad);
}

//For Add payment
export function addPayment(values){
    const {dispatch } = values;
    const payLoad={ type: ActionType.ADD_PAYMENT, dispatch }
    handleResponse(payLoad);
}
//For List
export function listPaymentDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    let defaultPageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;
    let paymentBatchFilter = (state.form.paymentBatchFilter.values)?state.form.paymentBatchFilter.values:{};
    paymentBatchFilter.displayPage=displayPage;
    paymentBatchFilter.defaultPageSize=defaultPageSize;

    const data = {paymentBatchFilter};
    const url = 'rest/mail/mra/receivablemanagement/advancepayment/list';

    return makeRequest({
        url,
        data: {...data}
    }).then(function(response) {
        if (args && args.mode === 'EXPORT') {
            let batchdata = response&&response.results&&response.results[0]&&response.results[0].paymentBatchDetails?
                response.results[0].paymentBatchDetails:null;
            if(batchdata==null){
                return response
            }
            return batchdata;
        } 
        const payLoad={ type: ActionType.LIST_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })  


   
}

