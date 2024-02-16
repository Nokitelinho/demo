import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { resetForm } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';


export function onclearDetails(values) {
    const { dispatch } = values;
    dispatch(resetForm("screeningDetailsTable"));
    dispatch(resetForm("consignerDetailstable"));
    dispatch(reset('securityexemptionform'));
    dispatch(reset('applicableregulationinfo'));
    dispatch(reset('filterPanelForm'));
    dispatch({ type: 'CLEAR_FILTER' });
}

export function toggleFilter(screenMode) {
    return { type: 'TOGGLE_FILTER', screenMode };
}


export function onlistConsignment(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const consignmentFilter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:(state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:'')
    const filterValues = { conDocNo : state.form.filterPanelForm.values ?consignmentFilter.consignDocNo :(state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:'')}

    if(state.form.filterPanelForm.values.consignDocNo===""){
        return Promise.reject(new Error("Please Enter Consignment Document Number"));
    }

    const url = 'rest/mail/operations/consignmentsecuritydeclaration/listScreeningDetails';
    return makeRequest({
        url,
        data: {...filterValues}
    }).then(function (response)  {
        handleListResponse(dispatch, response, "LIST");
        return response;
    })
        .catch(error => {
            return error;
        });
}

function handleListResponse(dispatch, response, action) {
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const ConsignerDetails =[];
        const secExemption =[];
        const securityExemption ={};
        const ScreeningDetails =[];
        const consignmentNumber= response.results[0].conDocNo;
        const ScreeningDetailsList = response.results[0].consignmentScreeningDetail;
        //const securityExemptionreason = response.results[0].securityExemptionreason;
        //const securityExemptionAuthority = response.results[0].securityExemptionAuthority;
        //const securityExemptionRegulation = response.results[0].securityExemptionRegulation;
        const companyCode = response.results[0].companyCode;
        const paCode = response.results[0].paCode;
        const consignmentDate = response.results[0].consignDate;
        const consignmentOrigin = response.results[0].consignmentOrigin;
        const consigmentDest = response.results[0].consigmentDest;
        const securityStatusCode = response.results[0].securityStatusCode;
        const consignmentSequenceNumber = response.results[0].consignmentSequenceNumber;
        const routingInConsignmentVOs = response.results[0].routingInConsignmentVO;
        const category = response.results[0].mailCategory;
        const applicableRegTransportDirection =response.results[0].applicableRegTransportDirection;
        const applicableRegBorderAgencyAuthority =response.results[0].applicableRegBorderAgencyAuthority;
        const applicableRegReferenceID = response.results[0].applicableRegReferenceID;
        const applicableRegFlag = response.results[0].applicableRegFlag;
        const fullStatedBags = response.results[0].statedBags;
        const fullStatedWeight = response.results[0].statedWeight;
        const screeningLocation = response.results[0].airportCode;
        
         ScreeningDetailsList.map((value)=>{
                 if(value.securityReasonCode === "CS"){
                    ConsignerDetails.push(value)
                 }
                if(value.securityReasonCode === "SE"){
                    secExemption.push(value)
                }
                if(value.securityReasonCode === "SM"){
                    ScreeningDetails.push(value)
                }
            })

        Object.assign(securityExemption, secExemption) 

        if (action === 'LIST') {
            dispatch({ type: 'LIST_SUCCESS',consignmentNumber, ScreeningDetails, /*securityExemptionreason, securityExemptionAuthority, securityExemptionRegulation,*/
            companyCode, paCode,consignmentDate,consignmentOrigin, consigmentDest,securityStatusCode,consignmentSequenceNumber, ConsignerDetails, securityExemption, ScreeningDetailsList,
             category, screeningLocation, routingInConsignmentVOs , applicableRegTransportDirection,applicableRegBorderAgencyAuthority, applicableRegReferenceID, applicableRegFlag, fullStatedBags, fullStatedWeight ,filterList:true});
    
        }
    }

}



