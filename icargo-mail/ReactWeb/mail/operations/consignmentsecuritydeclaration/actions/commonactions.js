import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction'
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import * as constant from '../constants/constants';
import { isDirty } from 'redux-form';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { onlistConsignment } from './filterpanelactions';

export function ScreenLoad(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const navigation = state.filterpanelreducer.navigationFilter?true:false;
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/screenloadScreeningDetails';
    return makeRequest({
        url, data: {}
    }).then(function (response) {
        handleScreenloadResponse(dispatch, response, navigation);
        return response;
    })

}

 function handleScreenloadResponse(dispatch, response, navigation) {
    if (response.results) {
        if(navigation){
            dispatch(screenLoadSuccessNavigation(response.results[0]));
        }else{
        dispatch(screenLoadSuccess(response.results[0]));
        }
    }
}

export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function screenLoadSuccessNavigation(data){
    return { type: 'SCREENLOAD_SUCCESS_NAVIGATION', data };
}


    export function closeScreen(values) {  
        const { dispatch, getState } = values;
    let state = getState();
    let fromScreen = state.filterpanelreducer.fromScreen;
    if (fromScreen === 'mail.operations.ux.consignment') {
        const params = {
            conDocNo: state.filterpanelreducer.navigationFilter,
            paCode: state.filterpanelreducer.paCode,
            fromScreen:'mail.operations.ux.consignmentsecuritydeclaration'
        }
        navigateToScreen('mail.operations.ux.consignment.screenload.do',params);
    }
    else {
        navigateToScreen('home.jsp', {});

    }
    }


export function onPrint(values){
    const {getState} = values;
    const state = getState();
    const consignmentFilter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:{}
    const filterValues = {conDocNo : consignmentFilter.consignDocNo,paCode: consignmentFilter.paCode}
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/printScreeningDetails';
    return makeRequest({
        url, data: {...filterValues}
    }).then(function (response) {
        console.log('responseresponse:>',response);
        return response;
    })
    .catch(error => {
        return new Error("Screening information not captured from airport, Cannot be displayed");
    });
}
export function onSave(values){
    const { args, dispatch, getState } = values;
    const state = getState();
const mstAddionalSecurityInfo =state.form.detailPanelForm.values.mstAddionalSecurityInfo;
const consignmentNumber= state.filterpanelreducer.consignmentNumber;
const consignmentSequenceNumber=state.filterpanelreducer.consignmentSequenceNumber;
const paCode= state.filterpanelreducer.paCode;
let screeningDetails={mstAddionalSecurityInfo,consignmentNumber,consignmentSequenceNumber,paCode};
const data ={screeningDetails};
const url = 'rest/mail/operations/consignmentsecuritydeclaration/additionalsecurityinfo';
return makeRequest({
  url, data: { ...data }
}).then(function (response) {
      const payLoad={ type: 'SAVE_SUCCESS', response, dispatch };
       dispatchData(payLoad);
       return response;
   })
       .catch(error => {
           return error;
       });
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] === "mail.operations.screening.delete" ||
                 warningCode[0] === "mail.operations.consigner.delete" || 
                warningCode[0] === "mail.operations.exemption.delete"||
                warningCode[0] === "mail.operations.regulation.delete") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(() => {
                        dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'}))
                      });
                }
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if ( warningCode[0] === "mail.operations.screening.delete" ||
                     warningCode[0] === "mail.operations.consigner.delete" || 
                     warningCode[0] === "mail.operations.exemption.delete" ||
                     warningCode[0] === "mail.operations.regulation.delete") {
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch(asyncDispatch(onlistConsignment)({mode:'LIST'}))                
                }
            }
            break;
        default:
            break;
    }
}
export const dispatchData = (payload) => {
    const {dispatch,response,type}=payload;
    dispatch({type}) 
}