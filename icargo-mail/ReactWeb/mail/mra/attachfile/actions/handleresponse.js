import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { ActionType,Forms } from '../constants/constants.js'

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
        case ActionType.SCREENLOAD_SUCCESS :
            dispatch({type,data:response.results[0]});
            break;          
        default:
            break;            
    }
};