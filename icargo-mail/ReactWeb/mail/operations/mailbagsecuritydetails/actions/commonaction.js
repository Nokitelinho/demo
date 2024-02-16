import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { listmailbagSecurityDetails } from '../actions/filteraction';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';



export function onCloseFunction() {
    navigateToScreen('home.jsp', {});
}

export function onPrint(values) {
    const { getState } = values;
    const state = getState();

    const printReportDetails = {mailbagId: state.listmailbagReducer.mailbagId }
    const url = 'rest/mail/operations/mailbagsecuritydetails/printMailbagSecurityDetails';
    return makeRequest({
        url, data: { ...printReportDetails }
    }).then(function (response) {
        console.log('responseresponse:>', response);
        return response;
    })
        .catch(error => {
            return new Error("Screening information not captured from airport, Cannot be displayed");
        });
}
export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            console.log("inside warning on ok");
            if (action.executionContext) {
                const warningCode = action.warningCode;
				if (warningCode[0] === "mail.operations.ux.mailbagsecuritydetails.msg.err.noMailbag") {
                    dispatch({ type: 'WARNING_TRIGGER'});
                    dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
				}
                if (warningCode[0] === "mail.operations.screening.delete"||  
                    warningCode[0] === "mail.operations.consigner.delete" ) {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(() => {
                        dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
                      });
                }              
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] === "mail.operations.screening.delete"||  
                    warningCode[0] === "mail.operations.consigner.delete" ){
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
                }
            }
            break;
        default:
            break;
                }
}