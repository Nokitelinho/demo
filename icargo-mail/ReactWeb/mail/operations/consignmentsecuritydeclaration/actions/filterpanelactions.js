import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { resetForm } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';


export function onclearDetails(values) {
    const { dispatch } = values;
    dispatch(resetForm("screeningDetailsTable"));
    dispatch(resetForm("consignerDetailstable"));
    dispatch(resetForm("securityexemptiontable"));
    dispatch(resetForm("applicableregulationtable"));
    dispatch(reset('addScreeingButtonPanelform'));
    dispatch(reset('addConsignerButtonPanelForm'));
    dispatch(reset('addExemptionButtonPanelForm'));
    dispatch(reset('addApplicableRegulationPanelform'));
    dispatch(reset('filterPanelForm'));
    dispatch(reset('detailPanelForm'));
    dispatch({ type: 'CLEAR_FILTER' });
}

export function toggleFilter(screenMode) {
    return { type: 'TOGGLE_FILTER', screenMode };
}


export function onlistConsignment(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const consignmentFilter = state.form.filterPanelForm.values?state.form.filterPanelForm.values:(state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:'')
    const filterValues = { conDocNo : state.form.filterPanelForm.values ?consignmentFilter.consignDocNo :(state.filterpanelreducer.navigationFilter?state.filterpanelreducer.navigationFilter:''),
                           paCode: consignmentFilter.paCode?consignmentFilter.paCode:(state.filterpanelreducer.paCode?state.filterpanelreducer.paCode:'')}

    if(state.form.filterPanelForm.values.consignDocNo==="" || filterValues.paCode===""){
        return Promise.reject(new Error("Please Enter Consignment Document Number and PA Code"));
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
        const applicableRegulation =[];
        const securityExemption =[];
        const ScreeningDetails =[];
        const consignmentNumber= response.results[0].conDocNo;
        const ScreeningDetailsList = response.results[0].consignmentScreeningDetail;
        const companyCode = response.results[0].companyCode;
        const paCode = response.results[0].paCode;
        const consignmentDate = response.results[0].consignDate;
        const consignmentOrigin = response.results[0].consignmentOrigin;
        const consigmentDest = response.results[0].consigmentDest;
        const securityStatusCode = response.results[0].securityStatusCode;
        const consignmentSequenceNumber = response.results[0].consignmentSequenceNumber;
        const routingInConsignmentVOs = response.results[0].routingInConsignmentVO;
        const category = response.results[0].mailCategory;
        const timeZone=response.results[0].timeZone;
        const fullStatedBags = response.results[0].statedBags;
        const fullStatedWeight = response.results[0].statedWeight;
        const screeningLocation = response.results[0].airportCode;
        const securityStatusParty=response.results[0].loginUser;
        const csgIssuerName= response.results[0].csgIssuerName;
        const mstAddionalSecurityInfo=response.results[0].mstAddionalSecurityInfo;
        let today = new Date()
        const months = {
            0: 'Jan',
            1: 'Feb',
            2: 'Mar',
            3: 'Apr',
            4: 'May',
            5: 'Jun',
            6: 'Jul',
            7: 'Aug',
            8: 'Sep',
            9: 'Oct',
            10: 'Nov',
            11: 'Dec',
          }
        const monthName = months[today.getMonth()];
        let StatusDate = today.getDate() + '-' + monthName  + '-' + today.getFullYear();
        let t=new Date(today.toLocaleString('en-US', { timeZone: timeZone }));
        let  time= (((t.getMinutes().toString()).length)==1)?( t.getHours() + ':0' + t.getMinutes()) :( t.getHours() + ':' + t.getMinutes()) ;
         ScreeningDetailsList.map((value)=>{
                 if(value.securityReasonCode === "CS"){
                    ConsignerDetails.push(value)
                 }
                if(value.securityReasonCode === "SE"){
                    securityExemption.push(value)
                }
                if(value.securityReasonCode === "SM"){
                    ScreeningDetails.push(value)
                }
                if(value.securityReasonCode === "AR"){
                    applicableRegulation.push(value)
                }
            })

       

        if (action === 'LIST') {
            dispatch({ type: 'LIST_SUCCESS',consignmentNumber, ScreeningDetails, 
            companyCode, paCode,consignmentDate,consignmentOrigin, consigmentDest,securityStatusCode,
            consignmentSequenceNumber, ConsignerDetails, securityExemption, ScreeningDetailsList,
            category, screeningLocation, routingInConsignmentVOs , csgIssuerName,mstAddionalSecurityInfo,
            fullStatedBags, fullStatedWeight ,filterList:true,securityStatusParty,applicableRegulation,timeZone,StatusDate,time,showITable: false});
    
	        setTimeout(() => {
            dispatch({ type: 'LIST_SUCCESS',consignmentNumber, ScreeningDetails, 
            companyCode, paCode,consignmentDate,consignmentOrigin, consigmentDest,securityStatusCode,
            consignmentSequenceNumber, ConsignerDetails, securityExemption, ScreeningDetailsList,
            category, screeningLocation, routingInConsignmentVOs , csgIssuerName,mstAddionalSecurityInfo,
            fullStatedBags, fullStatedWeight ,filterList:true,securityStatusParty,applicableRegulation,timeZone,StatusDate,time,showITable: true});    
            }, 200)
        }
    }

}



