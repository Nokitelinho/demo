import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { change } from 'icoreact/lib/ico/framework/component/common/form';
import {reset} from 'redux-form';

export function onMailBagAction(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const { selectedMail, action } = args;
    const mailbags = (state.listmailbagsreducer.mailbagsdetails.results) ? state.listmailbagsreducer.mailbagsdetails.results : {}
    let selectedMailbags = [];
    let url = '';
    let selectedMailbagsArr =[];
    let data = { selectedMailbags };

    if (action === 'VIEW_DAMAGE'){
        if(selectedMail !== '-1') {
        //selectedMailbags = [];
        selectedMailbags.push(mailbags[selectedMail]);
        }else{
            selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
        }
        url = 'rest/mail/operations/mailbagenquiry/viewMailbagDamage';
    }
    else if (action === 'RETURN'){
        if(selectedMail !== '-1') {
            selectedMailbags.push(mailbags[selectedMail]);
        }else{
            selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
        }
        url = 'rest/mail/operations/mailbagenquiry/viewMailbagDamage';
    } 
    else if (action=== 'DELIVER_MAIL'){
        if(selectedMail[0] !== -1) {
        //selectedMailbags = [];
        if(selectedMail.length>1){
            for(let i=0;i<selectedMail.length;i++){
                selectedMailbags.push(mailbags[selectedMail[i]]);
            }
        }else{
            selectedMailbags.push(mailbags[selectedMail]);
        }        
        }else{
            selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
        }
        data = { selectedMailbags }
        url = 'rest/mail/operations/mailbagenquiry/deliverMailbags';

    }  else if (action=== 'OFFLOAD'){
            if(selectedMail[0] !== -1 && selectedMail!=null) {
                if(Array.isArray(selectedMail)){
                    selectedMail.forEach(function(element) {
                        selectedMailbags.push(mailbags[element]);
                    }, this);  
                }
                else{
                    selectedMailbags.push(mailbags[selectedMail]);
                } 
            }
            else{
                selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
            }
            url = 'rest/mail/operations/mailbagenquiry/offloadMailbags';
    }
    else if (action=== 'VIEW_MAIL_HISTORY'){
            url = 'mailtracking.defaults.mbHistory.list.do?MailSequenceNumber="+1';
            var closeButtonIds = [];
            var optionsArray = {
                url,
                dialogWidth: "1300",
                dialogHeight: "600",
                closeButtonIds,
                popupTitle: 'View Mailbag History'
            }
            dispatch(dispatchAction(openPopup)(optionsArray));

    }else if (action==='MODIFY_ORG_DST'){
        let dummyAirportForDomMail=state.commonReducer.dummyAirportForDomMail?state.commonReducer.dummyAirportForDomMail:null;
        if(selectedMail[0] !== -1) {
            if(selectedMail.length>1){
                for(let i=0;i<selectedMail.length;i++){
                    selectedMailbags.push(mailbags[selectedMail[i]]);
                }
            }else{
                selectedMailbags.push(mailbags[selectedMail]);
            }        
            }else{
                selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
            }
            /* if(dummyAirportForDomMail!==null){
            selectedMailbags=selectedMailbags.filter((value)=>{if( value.mailorigin===dummyAirportForDomMail||value.mailDestination===dummyAirportForDomMail) return value});
            } */
            if(!selectedMailbags ||selectedMailbags.length===0){
                const message = 'Please select  a mailbag to update origin or destination';
                const target = '';
                dispatch(requestValidationError(message, target));
            }
            else{
            dispatch({ type: 'SHOW_MODIFY_POPUP',selectedMailbags}); 
            }

    }
    else{
        selectedMailbagsArr.push(mailbags[selectedMail]);
         dispatch({ type: 'ON_ROW_SELECT',selectedMailbagsArr});
    }
   if ((action === 'VIEW_DAMAGE') || (action === 'DELIVER_MAIL') || (action==='RETURN')){
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, action);
        return response
    })
        .catch(error => {
            return error;
        });
   }

   if((action === 'OFFLOAD')){
       return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, action);
        return response
    })
        .catch(error => {
            return error;
        });
   }
}
export function navigateToOffload(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const { selectedMail, action } = args;
    const mailbags = (state.listmailbagsreducer.mailbagsdetails.results) ? state.listmailbagsreducer.mailbagsdetails.results : {}
    let selectedMailbags = [];
    let mailBags="";
     if(selectedMail !== '-1' && selectedMail!=null){ 
        if(Array.isArray(selectedMail)){
                selectedMail.forEach(function(element) {
                    selectedMailbags.push(mailbags[element]);
                }, this);  
        }
        else{
             selectedMailbags.push(mailbags[selectedMail]);
        }
    }   
    else{
        selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    }
    if(selectedMailbags.length>1){
        selectedMailbags.forEach(function(element) {
            mailBags=mailBags+element.mailbagId+',';
        }, this);
    }
    else{
        mailBags=selectedMailbags[0].mailbagId;
    }
    const data = {  carrierCode: selectedMailbags[0].carrierCode,  mailbags:mailBags,
        flightNumber: selectedMailbags[0].flightNumber, flightDate: selectedMailbags[0].flightDate, fromScreen : "mail.operations.ux.mailbagenquiry",
        pageURL: "mail.operations.ux.mailbagenquiry" }
    navigateToScreen("mail.operations.ux.offload.defaultscreenload.do", data);
}

export function doReturnMail(values) {

    const { dispatch, getState } = values;
    const state = getState();
    let selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    let url = '';
    let damagedMailbagList = state.form.damageData.values.damageData;
    console.log("tt",state.form);
    let damagedMailbags =[];
    if(damagedMailbagList!=null && damagedMailbagList.length>0){
    for(let i=0;i< damagedMailbagList.length;i++){
        let damageData={
        fileName :  damagedMailbagList[i].fileData&&damagedMailbagList[i].fileData.length>0?damagedMailbagList[i].fileData[0].name:null,
        damageCode :  damagedMailbagList[i].damageCode,
        remarks : damagedMailbagList[i].remarks};
        damagedMailbags.push(damageData);
    }
}
    let oneTimeValues = state.commonReducer.oneTimeValues;
    let returnMailFlag = (state.form.returnMailForm.values)?((state.form.returnMailForm.values.isReturnMail)?'Y':'N'):'N';
    //selectedMailbags = [mailbags[selectedMail]];
    const data = { selectedMailbags, damagedMailbags, oneTimeValues,returnMailFlag };


    url = 'rest/mail/operations/mailbagenquiry/returnMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(response.status === 'success'){
            dispatch({ type: 'RETURN_SUCCESS',data });
            return response;
        }else{
            dispatch( { type: 'RETURN_FAIL',data});
            return response
        }
    })
        .catch(error => {
            return error;
        });


}

export function validateFlight(values) {

    const {args,dispatch, getState } = values;
    const state = getState();
    const {fromScreen,action} = args;
    let selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';

    if (fromScreen === 'REASSIGN') {
        assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
        flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
        
        flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
        destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';
    } else {

        carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
        flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
        assignToFlight = state.form.transferForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';

    }
    let oneTimeValues = state.commonReducer.oneTimeValues;
    //selectedMailbags = [mailbags[selectedMail]];
    const data = { selectedMailbags, oneTimeValues,carrierCode, flightCarrierCode, flightNumber, assignToFlight, flightDate, destination };


    url = 'rest/mail/operations/mailbagenquiry/validateFlightDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, action);
        return response
    })
        .catch(error => {
            return error;
        });


}
export function onreassignMailSave(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const { containerIndex, action } = args;
    const flightValidation = (state.listmailbagsreducer.flightValidation) ? state.listmailbagsreducer.flightValidation : {}
    const containers = (state.listmailbagsreducer.containerDetails) ? state.listmailbagsreducer.containerDetails : {}
    let selectedContainer = (state.listmailbagsreducer.selectedContainer) ? state.listmailbagsreducer.selectedContainer : {}
    let selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';

    scanDate = state.form.reassignForm.values.scanDate?state.form.reassignForm.values.scanDate:'';
    scanTime = state.form.reassignForm.values.scanTime?state.form.reassignForm.values.scanTime:'';
    carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
    flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
    destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';

    let oneTimeValues = state.commonReducer.oneTimeValues;
    selectedContainer = containers[containerIndex];
    let warningMessagesStatus ={};
    if(args&&args.screenWarning){
        warningMessagesStatus = { ["mail.operations.securityscreeningwarning"]: 'Y' };

    }
    const data = { scanDate, scanTime, flightValidation,selectedMailbags, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer ,warningMessagesStatus};

    if(selectedContainer === undefined){
        const message = 'Please select a row';
        const target = '';
        dispatch(requestValidationError(message, target));
       return Promise.resolve("Error"); 
    }
    url = 'rest/mail/operations/mailbagenquiry/reassignMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(!isEmpty(response.errors)){
            dispatch( { type: 'REASSIGN_FAIL',data});
            return response;
        }else{
            dispatch({ type: 'REASSIGN_SUCCESS' });
            return response
        } 
    })
        .catch(error => {
            return error;
        });


}
    export function onTransferMailSave(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const { containerIndex, action } = args;
    const flightValidation = (state.listmailbagsreducer.flightValidation) ? state.listmailbagsreducer.flightValidation : {}
    const containers = (state.listmailbagsreducer.containerDetails) ? state.listmailbagsreducer.containerDetails : {}
    let selectedContainer = (state.listmailbagsreducer.selectedContainer) ? state.listmailbagsreducer.selectedContainer : {}
    let selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';
    let upliftAirport ='';

    scanDate = state.form.transferForm.values.scanDate?state.form.transferForm.values.scanDate:'';
    scanTime = state.form.transferForm.values.scanTime?state.form.transferForm.values.scanTime:'';
    carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
    flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.transferForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';
    destination = state.form.transferForm.values.destination?state.form.transferForm.values.destination:'';
    upliftAirport = state.form.transferForm.values.uplift?state.form.transferForm.values.uplift:'';

    let oneTimeValues = state.commonReducer.oneTimeValues;
    selectedContainer = containers[containerIndex];
    let warningMessagesStatus ={};
    if(args&&args.screenWarning){
        warningMessagesStatus = { ["mail.operations.securityscreeningwarning"]: 'Y' };

    }
    const data = { scanDate, scanTime, flightValidation,selectedMailbags, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer,upliftAirport,warningMessagesStatus };
    
    if(assignToFlight==='FLIGHT'){
        if(selectedContainer === undefined){
            const message = 'Please select a row';
            const target = '';
            dispatch(requestValidationError(message, target));
           return Promise.resolve("Error"); 
        }
    }else{
        if(carrierCode === undefined || carrierCode === ''){
            const message = 'Please enter carrier code';
            const target = '';
            dispatch(requestValidationError(message, target));
           return Promise.resolve("Error"); 
        }
    }

    url = 'rest/mail/operations/mailbagenquiry/transferMailbags';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(!isEmpty(response.errors)){
            dispatch( { type: 'TRANSFER_FAIL',data});
            return response;
        }else{
            dispatch({ type: 'TRANSFER_SUCCESS' });
            return response
        }
    })
        .catch(error => {
            return error;
        });


}
     export function saveNewContainer(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const {  action,fromScreen } = args;
    const flightValidation = (state.listmailbagsreducer.flightValidation) ? state.listmailbagsreducer.flightValidation : {}
    let selectedContainer=  (state.listmailbagsreducer.selectedContainer) ? state.listmailbagsreducer.selectedContainer : {}
     let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';
if("TRANSFER" === fromScreen){
    
    scanDate = state.form.transferForm.values.scanDate?state.form.transferForm.values.scanDate:'';
    scanTime = state.form.transferForm.values.scanTime?state.form.transferForm.values.scanTime:'';
    let transferFilterType =state.form.transferForm.values.transferFilterType?state.form.transferForm.values.transferFilterType:'C';
    if(transferFilterType==='F'){
    carrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
    flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
    assignToFlight =  'FLIGHT' ;
    flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';
    destination = state.form.transferForm.values.uldDestination?state.form.transferForm.values.uldDestination:'';
    }
    if(transferFilterType==='C'){
        carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
        flightDate ='';
        flightNumber ='';
        assignToFlight =  'CARRIER' ;
        destination =state.form.transferForm.values.destination?state.form.transferForm.values.destination:'';
    }
    if(!destination || destination===''){
        return Promise.reject(new Error("Please enter destination")); 
    }
   
   
         selectedContainer.containerNumber = state.form.transferForm.values.uldNumber?state.form.transferForm.values.uldNumber:'';
         selectedContainer.type = state.form.transferForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.transferForm.values.uldRemarks?state.form.transferForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.transferForm.values.pou?state.form.transferForm.values.pou:'';
         selectedContainer.uldDestination = state.form.transferForm.values.uldDestination?state.form.transferForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.transferForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.transferForm.values.uldDestination?state.form.transferForm.values.uldDestination:'';
   
}else{
    scanDate = state.form.reassignForm.values.scanDate?state.form.reassignForm.values.scanDate:'';
    scanTime = state.form.reassignForm.values.scanTime?state.form.reassignForm.values.scanTime:'';
    carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
    flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';
    assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
    flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
    destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';
   
    if('FLIGHT'=== assignToFlight){
         selectedContainer.containerNumber = state.form.reassignForm.values.uldNumber?state.form.reassignForm.values.uldNumber:'';
         selectedContainer.type = state.form.reassignForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.reassignForm.values.uldRemarks?state.form.reassignForm.values.uldRemarks:'';
         selectedContainer.pou = state.form.reassignForm.values.pou?state.form.reassignForm.values.pou:'';
         selectedContainer.uldDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.reassignForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
    }else{
        selectedContainer.containerNumber = state.form.reassignForm.values.uldNumber?state.form.reassignForm.values.uldNumber:'';
         selectedContainer.type = state.form.reassignForm.values.barrowFlag===true?'B':'U';
         selectedContainer.remarks = state.form.reassignForm.values.uldRemarks?state.form.reassignForm.values.uldRemarks:'';
         selectedContainer.pou = '';
         selectedContainer.uldDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
         selectedContainer.paBuiltFlag = state.form.reassignForm.values.paBuilt===true?'Y':'N';        
         selectedContainer.finalDestination = state.form.reassignForm.values.uldDestination?state.form.reassignForm.values.uldDestination:'';
    }
}
    
   
    let oneTimeValues = state.commonReducer.oneTimeValues;
   
 
         selectedContainer.operationFlag = 'I';
         selectedContainer.flightNumber = flightNumber;
         selectedContainer.carrierCode = carrierCode;
         selectedContainer.assignedPort = state.commonReducer.airportCode;
         let showWarning='Y';
         if(args.showWarning){
             showWarning= args.showWarning;
         }
    const data = { showWarning,scanDate, scanTime, flightValidation, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer };


    url = 'rest/mail/operations/mailbagenquiry/saveContainerDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(!isEmpty(response.errors)){
            dispatch( { type: 'ULD_EXIST'});
            return response;
       }else{
        if("TRANSFER" === fromScreen){
            state.form.transferForm.values.uldNumber = '';
            state.form.transferForm.values.barrowFlag =false;
            state.form.transferForm.values.uldRemarks ='';
            state.form.transferForm.values.pou ='';
            state.form.transferForm.values.uldDestination ='';
            state.form.transferForm.values.paBuilt='';        
            //state.form.transferForm.values.uldDestination ='';
        }else{
            state.form.reassignForm.values.uldNumber = '';
            state.form.reassignForm.values.barrowFlag =false;
            state.form.reassignForm.values.uldRemarks ='';
            state.form.reassignForm.values.pou ='';
            'FLIGHT'=== assignToFlight?state.form.reassignForm.values.uldDestination ='' : '';
            state.form.reassignForm.values.paBuilt='';        
            //state.form.reassignForm.values.uldDestination ='';
        }

        handleSaveNewContainer(dispatch, response, action,fromScreen);
        return response;
       }
        
    })
        .catch(error => {
            return error;
        });


}
 export const onMailRowSelect=(values)=> {
      const { args, dispatch, getState } = values;
      const state = getState();
      const  {isRowSelected, rowIdx } = args;
      const selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : [];
      const mailbags = (state.listmailbagsreducer.mailbagsdetails.results) ? state.listmailbagsreducer.mailbagsdetails.results : {};
      let selectedMailbagsArr = Object.assign([],selectedMailbags);  
     
    if(isRowSelected){
        if(rowIdx===-1){
            selectedMailbagsArr = Object.assign([],mailbags);
        }else{
          selectedMailbagsArr.push(mailbags[rowIdx]);
        }
    }else{
       if(rowIdx===-1){
            selectedMailbagsArr = [];
        }else{
            selectedMailbagsArr.splice(rowIdx, 1);
        } 
    }
     dispatch({ type: 'ON_ROW_SELECT',selectedMailbagsArr});
     
}
export function listContainers(values) {

    const {args,dispatch, getState } = values;
    const state = getState();
    const {  action,fromScreen } = args;
    let selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
    const flightValidation = (state.listmailbagsreducer.flightValidation) ? state.listmailbagsreducer.flightValidation : {}
    let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let upliftAirport ='';
    fromScreen === 'REASSIGN'?assignToFlight = state.form.reassignForm.values.reassignFilterType === 'F' ? 'FLIGHT' : 'CARRIER':"";

    if (fromScreen === 'REASSIGN') {
        
         if('FLIGHT'=== assignToFlight){
			carrierCode = state.form.reassignForm.values.flightnumber ? state.form.reassignForm.values.flightnumber.carrierCode : '';
      flightCarrierCode = state.form.reassignForm.values.flightnumber ? state.form.reassignForm.values.flightnumber.carrierCode : '';
      flightNumber = state.form.reassignForm.values.flightnumber ? state.form.reassignForm.values.flightnumber.flightNumber : '';
      flightDate = state.form.reassignForm.values.flightnumber ? state.form.reassignForm.values.flightnumber.flightDate : '';
      destination = state.form.reassignForm.values.destination ? state.form.reassignForm.values.destination : '';

          }
        carrierCode = state.form.reassignForm.values.carrierCode?state.form.reassignForm.values.carrierCode:'';
        flightCarrierCode = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightNumber:'';        
        flightDate = state.form.reassignForm.values.flightnumber?state.form.reassignForm.values.flightnumber.flightDate:'';
        destination = state.form.reassignForm.values.destination?state.form.reassignForm.values.destination:'';
    } else {

        carrierCode = state.form.transferForm.values.carrierCode?state.form.transferForm.values.carrierCode:'';
        flightCarrierCode = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightNumber:'';
        assignToFlight = state.form.transferForm.values.transferFilterType === 'F' ? 'FLIGHT' : 'CARRIER';
        flightDate = state.form.transferForm.values.flightnumber?state.form.transferForm.values.flightnumber.flightDate:'';
        destination = state.form.transferForm.values.destination?state.form.transferForm.values.destination:'';
        upliftAirport = state.form.transferForm.values.uplift?state.form.transferForm.values.uplift:'';
    }
    let oneTimeValues = state.commonReducer.oneTimeValues;
    //selectedMailbags = [mailbags[selectedMail]];
    const data = { selectedMailbags, oneTimeValues, carrierCode, flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, flightValidation,upliftAirport };


    url = 'rest/mail/operations/mailbagenquiry/validateMailbagDetailsForEnquiry';

    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, action, destination, assignToFlight, carrierCode);
        return response
    })
        .catch(error => {
            return error;
        });


}

 function  buildPous (data,port){
        let destArray=data.split('-');
        destArray.reverse();
        const index = destArray.indexOf(port);
        destArray.splice(index);
        let dest=[];
        destArray.forEach(function(element) {
            dest.push({label:element,value:element});
        }, this);
        return dest.reverse();

    }

function handleResponse(dispatch, response, action, inputDestination, assignToFlight, inputCarrierCode) {
    
    if (!isEmpty(response.results)) {
        console.log(response.results[0]);
        if (action === 'VIEW_DAMAGE'|| (action==='RETURN')) {
            const { damagedMailbags} = response.results[0];
            dispatch({ type: 'DAMAGE_SUCCESS', damagedMailbags });
             if(action === 'VIEW_DAMAGE'){
                   
                dispatch({ type: 'VIEW_DAMAGE_SUCCESS'});
            }
        } else if (action === 'DELIVER_MAIL') { 
            dispatch({ type: 'DELIVER_SUCCESS' });
        } else if (action === 'VALIDATE_FLIGHT') {
            const { flightValidation, selectedMailbags,port, flightCarrierCode, flightDate, flightNumber} = response.results[0];
            const flightnumber = {carrierCode:flightCarrierCode, flightDate: flightDate, flightNumber:flightNumber};
            const pous=buildPous(flightValidation.flightRoute,port);

            let pou=null;
            if (!isEmpty(pous)) {
                pou=pous[0].value;
            }

            dispatch({ type: 'FLIGHT_SUCCESS', flightValidation, selectedMailbags,pous,pou, flightnumber });
        } else if (action === 'LIST_CONTAINER') {
            const { containerDetails, destination, flightValidation, carrierCode, assignToFlight } = response.results[0];
            let assigned = '';
            assignToFlight==='FLIGHT'?assigned='F':assigned='C';
            dispatch({ type: 'LIST_CONTAINER', containerDetails, destination, flightValidation, carrierCode, assigned });
        }else if (action === 'DO_RETURN') { 
            dispatch({ type: 'RETURN_SUCCESS' });
         } else {
            const { mailbags} = response.results[0];
            if (action === 'LIST' && mailbags.results>0) {
                dispatch({ type: 'LIST_SUCCESS', mailbags });
            }
        }

    } else {
        if (!isEmpty(response.errors)) {
            if (action === 'VIEW_DAMAGE') {
                dispatch({ type: 'NO_DAMAGE_DETAILS' });
                dispatch({ type: 'NO_DAMAGE_DATA' });
            }else if (action === 'LIST_CONTAINER') {
                let assigned = '';
                assignToFlight==='FLIGHT'?assigned='F':assigned='C';
                dispatch({ type: 'NO_CONTAINER' , inputDestination, assigned, inputCarrierCode});
            }else if (action === 'DO_RETURN') {
                dispatch({ type: 'ERR_DATA' });
            }else if (action === 'DO_REASSIGN') {
                dispatch({ type: 'ERR_DATA' });
           }else if ((action === 'DO_TRANSFER') || (action === 'VALIDATE_FLIGHT')) {
                dispatch({ type: 'ERR_DATA' });
            }else if (action === 'DELIVER_MAIL') {
                dispatch({ type: 'ERR_DATA' });
            }else if (action === 'OFFLOAD') {
                dispatch({ type: 'ERR_DATA' });
            }else {
                dispatch({ type: 'CLEAR_TABLE' });
            }

        }else{
            if (action === 'VIEW_DAMAGE') {
                dispatch({ type: 'NO_DAMAGE_DETAILS' });
                dispatch({ type: 'NO_DAMAGE_DATA' });
                dispatch(requestValidationError('No damage details found for the mailbag',''))
            }
            else if(action==='SAVE_CONTAINER'){
                dispatch(asyncDispatch(listContainers)({ response, action: 'LIST_CONTAINER', fromScreen: 'TRANSFER' }))
         }
         
        }
    }
}
export function pabuiltUpdate(values) {
    const { args, dispatch,getState } = values;
    const state = getState();
    if(args.barrowCheck){
    dispatch(change('transferForm','paBuilt', false)); 
    dispatch(change( 'reassignForm','paBuilt', false));
    }
  }
   function handleSaveNewContainer(dispatch, response, action,fromScreen){
   if(action==='SAVE_CONTAINER'){
    dispatch(asyncDispatch(listContainers)({ response, action: 'LIST_CONTAINER', fromScreen:fromScreen}))
}
}
export function updateOrgAndDest(values) {
    const { getState,args,dispatch } = values;
    const state = getState();
    const index= args.rowIndex;
    const malOrgAndDest = args.malOrgAndDest; 
    const isOrgUpd = args.isOrgUpd; 
    // let mailbagId="";
   // const mailbags=state.form.mailbagtable?state.form.mailbagtable.values.mailbagtable :null;
   const mailbags=state.listmailbagsreducer.mailbagsdetails&&state.listmailbagsreducer.mailbagsdetails.results?state.listmailbagsreducer.mailbagsdetails.results:null;
    const selectedMailbags=[mailbags[index]];
    if(isOrgUpd){
    selectedMailbags[0].mailorigin = malOrgAndDest;
    selectedMailbags[0].originUpdate=true;
    }
    else{
     selectedMailbags[0].mailDestination = malOrgAndDest;
    }
    const data = { selectedMailbags };
    const url='rest/mail/operations/mailbagenquiry/updateOriginAndDest';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        if(!isEmpty(response.errors)){
            let dummyAirportForDomMail=state.commonReducer.dummyAirportForDomMail?state.commonReducer.dummyAirportForDomMail:null;
            if(isOrgUpd){
                selectedMailbags[0].mailorigin =dummyAirportForDomMail;
                }
                else{
                 selectedMailbags[0].mailDestination =dummyAirportForDomMail;
                }
                dispatch(change('mailbagtable', `mailbagtable.${index}.mailDestination`,dummyAirportForDomMail));
            return response;
        } 
       
        return response;
    }) 
    .catch(error => {
        return error;
    });
    }
export function validateOrgDest(malOrgAndDest,isOrgUpd){
        let error = ""
        let isValid = true;
        if(malOrgAndDest===undefined||malOrgAndDest===""){
            isValid=false;
            if(isOrgUpd){
                error="Please enter Origin Airport";
            } 
            else{
                error="Please enter Destination Airport"; 
            }
          }
        let validObject = {
            valid: isValid,
            msg: error
        }
        return validObject;
    
}
export function validateOrgDestForMultiple(data) {
    let error = ""
    let isValid = true;
    let tabledata=[];
    tabledata = data;
    for(var i=0;i<tabledata.length;i++) {
        let mailbag=tabledata[i];
        if(mailbag.mailorigin===undefined||mailbag.mailorigin===""){
            isValid=false;
            error="Please enter Origin Airport";
        }
        else if(mailbag.mailDestination===undefined||mailbag.mailDestination===""){
            isValid=false;
            error="Please enter Destination Airport"; 
        }
    }
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;

}
export function  onSaveOrgAndDest(values){
    const { dispatch, getState,args } = values;
    const state = getState();
    let mailbagslist=[];
    let selectedMailbag=[];
    let selectedMailbags=[];
    let isUpdated=false;
    let mailbags=[];
     mailbagslist=state.form.newMailbagsTable?state.form.newMailbagsTable.values.newMailbagsTable :null
     mailbags=state.listmailbagsreducer.selectedMailbags?[...state.listmailbagsreducer.selectedMailbags]:null;
     selectedMailbag=mailbags.map(item=>{
         return {...item }
     });

    for(let i=0; i<selectedMailbag.length; i++){
        for(let j=0; j<mailbagslist.length; j++){
            if(selectedMailbag[i].mailbagId==mailbagslist[j].mailbagId) {
                if(selectedMailbag[i].mailorigin!=mailbagslist[j].mailorigin){
                    selectedMailbag[i].mailorigin =mailbagslist[j].mailorigin ;
                    isUpdated=true;
                    selectedMailbag[i].originUpdate=true;
                }
                 if(selectedMailbag[i].mailDestination!=mailbagslist[j].mailDestination){
                    selectedMailbag[i].mailDestination =mailbagslist[j].mailDestination ;
                    isUpdated=true;
                    selectedMailbag[i].destinationUpdate=true;
                }
                if (isUpdated){
                    selectedMailbags.push(selectedMailbag[i]);
                }
            }
        }
    }
    if(isUpdated){
    const data = { selectedMailbags };
    const url='rest/mail/operations/mailbagenquiry/updateOriginAndDest';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) { 
        if(isEmpty(response.errors)){
            dispatch({ type: 'MODIFY_ORG_AND_DEST_SUCCESS' });
            state.form.newMailbagsTable?dispatch(reset('newMailbagsTable')):'';
            return response;
        } 
        return response;
    }) 
    .catch(error => {
        return error;
    });
    }
    else{
        return Promise.reject(new Error("Please update Origin or Destination")); 
        
    }
         
}