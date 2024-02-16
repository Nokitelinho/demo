import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { ActionType, Urls, Errors , Constants, Key } from '../constants/constants.js'
import { handleResponse} from './handleresponse.js'
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { attachAwb} from './detailsaction.js';


export const screenLoad=(args) => {

    const{dispatch}=args;
    const url=Urls.SCREENLOAD_MAIL_AWB_BOOKING;
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        const payLoad={ type: ActionType.SCREENLOAD_SUCCESS, response, dispatch }
        handleResponse(payLoad);
        return response;
    })   

}

export const onClose=() =>{
    window.parent.popupUtils.getPopupReference().dialog('close')
}


export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case Constants.__DELEGATE_WARNING_ONOK:
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] === Key.DEST_AWB_WARN) {
                    dispatch(asyncDispatch(attachAwb)({warningFlag:Constants.CONFIRM_CODE}));;
                }
            }
            break;
        case Constants.__DELEGATE_WARNING_ONCANCEL:
            if (action.executionContext) {
            }
            break;
        default:
            break;
    }
}



