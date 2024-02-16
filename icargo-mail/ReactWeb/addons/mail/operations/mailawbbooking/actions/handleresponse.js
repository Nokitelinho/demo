import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ActionType,Key } from '../constants/constants.js';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { attachAwb} from './detailsaction.js';

/**
 * Method to handle the responses from the action classes.
 * Responses are dispatched to the reducers.
 */
export const handleResponse = (payload) =>{
    const {response,dispatch}=payload;
    if (!isEmpty(response)) {
        if(!isEmpty(response.errors)){
            const {type,...rest}=payload;
            if(response.errors.ERROR && response.errors.ERROR[0].code === 'No Results Found')
                dispatch({type:'ERROR_NO_RECORDS'})
            if(response.errors.WARNING && response.errors.WARNING[0].code === Key.DEST_AWB_WARN){
                dispatch(requestWarning([{code:Key.DEST_AWB_WARN,description:"The destination of mailbags are different from the AWB Destination.Do you want to continue?"}],
                {functionRecord:attachAwb}))
            }
            else
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



const dispatchData = (payload) => {
      const {dispatch,response,type}=payload;

      switch(type){

        case ActionType.ERROR_SHOW :
            dispatch({type});
            break;
        case ActionType.LIST_AWB_SUCCESS :
            if(!isEmpty(response.results)){
                dispatch({type,data:response.results[0],summaryFilter:response.summaryFilter})
            }
            break;
        case ActionType.SCREENLOAD_SUCCESS :
            dispatch({type,data:response.results[0]});
            break;
        case ActionType.ATTACH_AWB :
		window.parent.appBridge.dispatchAction({ type: "CLOSE_UX_POPUP", data: "reload_listcarditenquiry" });
            window.parent.popupUtils.getPopupReference().dialog('close')
        case 'LIST_LOAD_PLAN_SUCCESS':
            if(!isEmpty(response.results)){
                dispatch({type,data:response.results[0],loadPlanSummaryFilter:response.loadPlanSummaryFilter})
            }
            break; 
        case 'ATTACH_LOAD_PLAN_AWB':
		window.parent.appBridge.dispatchAction({ type: "CLOSE_UX_POPUP", data: "reload_listcarditenquiry" });
            window.parent.popupUtils.getPopupReference().dialog('close')
            break;
        case 'LIST_MANIFEST_SUCCESS':
            if (!isEmpty(response.results)) {
                dispatch({ type, data: response.results[0], manifestSummaryFilter: response.manifestSummaryFilter })
            }
            break;
        case 'ATTACH_MANIFEST_AWB':
		window.parent.appBridge.dispatchAction({ type: "CLOSE_UX_POPUP", data: "reload_listcarditenquiry" });
            window.parent.popupUtils.getPopupReference().dialog('close')
        default:
            break;
      }
  };