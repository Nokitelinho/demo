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



const dispatchData = (payload) =>
  {
      const {dispatch,response,type}=payload;

      switch(type){

        case ActionType.ERROR_SHOW :
            dispatch({type});
            break;
        case ActionType.LIST_DSN_SUCCESS :
            if(!isEmpty(response.results)){
                dispatch({type,data:response.results[0],summaryFilter:response.summaryFilter})
            }
            break;
        case ActionType.CLEAR_FILTER :
            dispatch({type})
            dispatch(reset(Forms.CARDIT_DSN_FILTER));
            break;
        case ActionType.SCREENLOAD_SUCCESS :
            dispatch({type,data:response.results[0]});
            break;
        case ActionType.UPDATE_SORT_VARIABLE :
            dispatch({type,data:response});
            break;
        case ActionType.SAVE_FILTER :
            dispatch({type,data:response});
            break;
        case ActionType.CLEAR_TABLE_FILTER :
            dispatch({type});
            dispatch(reset(Forms.CARDIT_DSN_TABLE_FILTER));
            break;
        case ActionType.SAVE_SELECTED_INDEXES :
            dispatch({type,data:response});
            break;
        case ActionType.CLEAR_DSN_TABLE :
            dispatch({type})
        case ActionType.SAVE_SELECTED_MAILBAGVOS :
            dispatch({type,data:response});
            break;
        default:
            break;
      }
  };