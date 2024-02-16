import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { ActionType,Forms } from '../constants/constants.js'
import { requestSuccess } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


/**
 * Method to handle the responses from the action classes.
 * Responses are dispatched to the reducers.
 */
export const handleResponse = (payload) =>{
    const {response}=payload;
    if (!isEmpty(response)) {
        if(!isEmpty(response.errors)){
            const {type,...rest}=payload;
            dispatchData({type: ActionType.ERROR_SHOW,...rest});
        }
        else{
            dispatchData(payload);
        }
    }
    else{
        dispatchData(payload);
    }
}

export const dispatchData = (payload) => {
    const {dispatch,response,type}=payload;

    switch(type){
        case ActionType.ERROR_SHOW :
            dispatch({type});
            break;
        case ActionType.SCREENLOAD_SUCCESS :
            dispatch({type,data:response.results[0]});
            break;
        case ActionType.LIST_SUCCESS :
                if(!isEmpty(response.results)){
                    dispatch({type,data:response.results[0]})
                }
            break;            
        case ActionType.CLEAR_FILTER :
            dispatch({type})
            dispatch(reset(Forms.PAYMENT_BATCH_FILTER));
            break;            
        case ActionType.ADD_PAYMENT :
            dispatch({type})
            break;
        case ActionType.ADD_PAY_CLS :
            dispatch({type})
            break;
        case ActionType.CREATE_SUCCESS:
        let message={'msgkey':'mail.mra.receivablemanagement.err.savesuccess','defaultMessage':'Saved Successfullly'};
        dispatch(requestSuccess(message));
        setTimeout(() => {
            dispatch({type,data:response.results[0]})
          }, 2000)
            break;
        case ActionType.EDT_BTH :
            dispatch({type,data:response.results[0]})
            break;
        case ActionType.OK_SUCCESS :
            dispatch({type,data:response.results[0]})   
            break;     
        default:
            break;            
    }
};