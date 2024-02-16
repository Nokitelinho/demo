import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction'
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import * as constant from '../constants/constants';
import { isDirty } from 'redux-form';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



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

export function onClose(values) {
    const {dispatch , getState } = values;
    const state = getState();
    let unSavedDataExists = false;
    let screeningDetailsTable = state.form.screeningDetailsTable&&state.form.screeningDetailsTable.values.screeningDetailsTable?state.form.screeningDetailsTable.values.screeningDetailsTable:''
    if(isDirty("securityexemptionform")(state) ||isDirty("applicableregulationinfo")(state)){
        unSavedDataExists = true;
    }

    [screeningDetailsTable].map((value) =>{
        Object.values(value).map(function(item){
            if(item.opFlag){
                unSavedDataExists = true;
            }
        })
    })

    if(unSavedDataExists){
        if(state.filterpanelreducer.fromScreen==="mail.operations.ux.consignment"){
            const conDocNo = state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:'';
            const paCode = state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:'';
            const data ={fromScreen : "mail.operations.ux.consignmentsecuritydeclaration",conDocNo,paCode}
            navigateToScreen("mail.operations.ux.consignment.screenload.do", data);
        }else{
        dispatch(requestWarning([{ description: "Unsaved Data exists. Do you want to continue?" }], { functionRecord: closeScreen ,args:{warningCode:"close"}}))
        }
        }
        else{
            if(state.filterpanelreducer.fromScreen==="mail.operations.ux.consignment"){
                const conDocNo = state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:'';
                const paCode = state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:'';
                const data ={fromScreen : "mail.operations.ux.consignmentsecuritydeclaration",conDocNo,paCode}
            navigateToScreen("mail.operations.ux.consignment.screenload.do", data);
            }else{
           navigateToScreen('home.jsp', {});
            }
        }
    }
    

    export function closeScreen(values) {
    
        navigateToScreen('home.jsp', {});
    }

    export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode === "close") {
                    dispatch(dispatchAction(closeScreen)());
                }
            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode == 'mail.operations.consignmentsecuritydeclaration.oncancel') {
                }
            }
            break;
        default:
            break;
    }
}

export function onSaveDetails(values){
    const {dispatch , getState} = values;
    const state = getState();
    const consignmentScreeningDetail = state.filterpanelreducer.ScreeningDetails?state.filterpanelreducer.ScreeningDetails:''
    const paCode = state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:''
    const conDocNo = state.filterpanelreducer.consignmentNumber?state.filterpanelreducer.consignmentNumber:''
    const consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber?state.filterpanelreducer.consignmentSequenceNumber:''
    const deletedRows = state.filterpanelreducer.deletedRows?state.filterpanelreducer.deletedRows:''
    const ConsignerDetails = state.filterpanelreducer.ConsignerDetails? state.filterpanelreducer.ConsignerDetails:''
    // const securityExemption = state.form.securityexemptionform && state.form.securityexemptionform.anyTouched? state.form.securityexemptionform.values:''

    // const applicableRegTransportDirection = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegTransportDirection?state.form.applicableregulationinfo.values.applicableRegTransportDirection:state.filterpanelreducer.applicableRegTransportDirection;
    // const applicableRegBorderAgencyAuthority = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegBorderAgencyAuthority?state.form.applicableregulationinfo.values.applicableRegBorderAgencyAuthority:state.filterpanelreducer.applicableRegBorderAgencyAuthority;
    // const applicableRegReferenceID = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegReferenceID?state.form.applicableregulationinfo.values.applicableRegReferenceID:state.filterpanelreducer.applicableRegReferenceID;
    // const applicableRegFlag = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegFlag?state.form.applicableregulationinfo.values.applicableRegFlag:state.filterpanelreducer.applicableRegFlag;

    const securityExemption = state.form.securityexemptionform && state.form.securityexemptionform.anyTouched?state.form.securityexemptionform.values:
    state.detailspanelreducer.ExemptionForm&&state.detailspanelreducer.ExemptionForm.values?state.detailspanelreducer.ExemptionForm.values:''

    const applicableRegTransportDirection = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegTransportDirection?
    state.form.applicableregulationinfo.values.applicableRegTransportDirection:state.filterpanelreducer.applicableRegTransportDirection?state.filterpanelreducer.applicableRegTransportDirection:
    state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegTransportDirection?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegTransportDirection:''
           
    const applicableRegBorderAgencyAuthority = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegBorderAgencyAuthority?
    state.form.applicableregulationinfo.values.applicableRegBorderAgencyAuthority:state.filterpanelreducer.applicableRegBorderAgencyAuthority?state.filterpanelreducer.applicableRegBorderAgencyAuthority:
    state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegBorderAgencyAuthority?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegBorderAgencyAuthority:''

    const applicableRegReferenceID = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegReferenceID?
    state.form.applicableregulationinfo.values.applicableRegReferenceID:state.filterpanelreducer.applicableRegReferenceID?state.filterpanelreducer.applicableRegReferenceID:
    state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegReferenceID?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegReferenceID:''
    
    const applicableRegFlag = state.form.applicableregulationinfo&&state.form.applicableregulationinfo.anyTouched&&state.form.applicableregulationinfo.values.applicableRegFlag?
    state.form.applicableregulationinfo.values.applicableRegFlag:state.filterpanelreducer.applicableRegFlag?state.filterpanelreducer.applicableRegFlag:
    state.detailspanelreducer.ApplicableInfoForm&&state.detailspanelreducer.ApplicableInfoForm.values&&state.detailspanelreducer.ApplicableInfoForm.values.applicableRegFlag?state.detailspanelreducer.ApplicableInfoForm.values.applicableRegFlag:'';

    if(state.filterpanelreducer.deletedRows.length> 0){
        deletedRows.map((value) =>{
            consignmentScreeningDetail.push(value);
        })
    }

    if(state.filterpanelreducer.ConsignerDetails.length> 0){
        ConsignerDetails.map((value) =>{
            if(value.opFlag === "I")
            consignmentScreeningDetail.push(value);
        })
    }

    // To add Security Exemption Details
    if(securityExemption){  
        [securityExemption].map((value)=>{
            value.opFlag = 'I'
            value.paCode = state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:''
            value.consignmentNumber =state.filterpanelreducer.consignmentNumber?state.filterpanelreducer.consignmentNumber:''
            value.consignmentSequenceNumber = state.filterpanelreducer.consignmentSequenceNumber?state.filterpanelreducer.consignmentSequenceNumber:''
            value.paCode = state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:''
            value.screeningLocation = state.filterpanelreducer.screeningLocation?state.filterpanelreducer.screeningLocation:''
            value.securityReasonCode = "SE"
        })
        consignmentScreeningDetail.push(securityExemption);
    }

     const url = 'rest/mail/operations/consignmentsecuritydeclaration/saveScreeningDetails';
    return makeRequest({
        url,
        data: {consignmentScreeningDetail, paCode, conDocNo,consignmentSequenceNumber,applicableRegTransportDirection,applicableRegBorderAgencyAuthority,
            applicableRegReferenceID,applicableRegFlag}
    }).then(function (response)  {
        handleListResponse(dispatch);
        return response;
    })
        .catch(error => {
            return error;
        });
}

function handleListResponse(dispatch){
    dispatch({type:constant.CLEAR_DELETED_ROWS, isSaved:true});
}

export function onPrint(values){
    const {getState} = values;
    const state = getState();
    const consignmentFilter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:{}
    const filterValues = {conDocNo : consignmentFilter.consignDocNo}
    const url = 'rest/mail/operations/consignmentsecuritydeclaration/printScreeningDetails';
    return makeRequest({
        url, data: {...filterValues}
    }).then(function (response) {
        console.log('responseresponse:>',response);
        return response;
    })
    .catch(error => {
        //  if(error){
        //     return Promise.resolve(new Error("Screening information not captured from airport, Cannot be displayed"))
        //     //error= Promise.reject(new Error("Screening information not captured from airport, Cannot be displayed"));
            
        //  }
        return new Error("Screening information not captured from airport, Cannot be displayed");
    });
}