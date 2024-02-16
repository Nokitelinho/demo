import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { dispatchAction, asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function onlistContainerDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const {displayPage,action} = args;
    let containerFilter  = (state.form.containerFilter.values)?state.form.containerFilter.values:{};
    const fromOffload = state.commonReducer.fromOffload;
    if (fromOffload){
        containerFilter = state.listcontainerreducer.filterDetails? state.listcontainerreducer.filterDetails:'';
    }
    //Added by A-8672 for mailoutbound starts
    const fromOutbound = state.commonReducer.fromOutbound;
    const fromInbound = state.commonReducer.fromInbound;
    if(isSubGroupEnabled('LUFTHANSA_SPECIFIC')){
        containerFilter.hbaMarkedFlag=state.form.containerFilter.values.hbaMarked; 
    }
    if(fromOutbound){
        containerFilter = state.commonReducer.filterValuesFromOutbound?state.commonReducer.filterValuesFromOutbound:'';
        containerFilter.hbaMarkedFlag=null;
        containerFilter.destination === 'null'? containerFilter.destination=null:'';
            if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
                containerFilter.subclassGroup='NONEMS';
            } else{
                containerFilter.subclassGroup='OTHERS';
            }
    } 
     if(fromInbound){
        containerFilter = state.commonReducer.filterValuesFromInbound?state.commonReducer.filterValuesFromInbound:'';
    } 
    //Added by A-8672 for mailoutbound ends
    const pageSize = args && args.pageSize ? args.pageSize : state.listcontainerreducer.pageSize; 
    containerFilter.pageSize=pageSize;
    if(displayPage) {
        containerFilter.displayPage=displayPage;
    }
    const screenMode=state.listcontainerreducer.screenMode
    const flightNumber=(state.form.containerFilter.values) ? state.form.containerFilter.values.flightnumber:{};
    const showEmpty=state.form.containerFilter.values.showEmpty; 
    if(showEmpty)
    state.form.containerFilter.values.showEmpty='Y';
     //Added by A-8893 forcontainerRelease starts
    const showUnreleased=state.form.containerFilter.values.showUnreleased; 
    if(showUnreleased)
    state.form.containerFilter.values.showUnreleased='Y';
      //Added by A-8893 forcontainerRelease ends
      if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
    const subclassGroup=state.form.containerFilter.values.subclassGroup;
    if(subclassGroup)
    state.form.containerFilter.values.subclassGroup='EMS';
          else
          state.form.containerFilter.values.subclassGroup='NONEMS';  
      }
      else{
        state.form.containerFilter.values.subclassGroup='OTHERS';    
      }
      if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
      let unplannedContainers=(state.form.containerFilter.values.showUnplanedContainer==true)?'Y':'N';
      containerFilter.unplannedContainers=unplannedContainers;
      }
	//Added by A-8164 for mailinbound starts
    const containersToList= state.commonReducer.filterValuesFromInbound&&state.commonReducer.filterValuesFromInbound.containersToBeSaved?
            state.commonReducer.filterValuesFromInbound.containersToBeSaved:[];
   // containerFilter={...containerFilter,containersToList};
    const flightDetailsFromInbound=state.commonReducer.flightDetailsFromInbound?state.commonReducer.flightDetailsFromInbound:{};
    if(!isEmpty(flightDetailsFromInbound)) {
        containerFilter={...containerFilter,assignedTo:'FLT',flightNumber:flightDetailsFromInbound.flightNumber,
                        flightDate:flightDetailsFromInbound.flightDate,flightCarrierCodeFromInbound:flightDetailsFromInbound.carrierCode,
                       operationType:'I',subclassGroup:"OTHERS",showEmpty:'Y'}
      }
     //Added by A-8164 for mailinbound ends
    state.form.containertable?dispatch(reset('containertable')):'';
    if(!isEmpty(flightNumber)) {
      containerFilter.flightNumber=flightNumber.flightNumber;
      containerFilter.flightDate=flightNumber.flightDate;
    }    

    const data = {containerFilter};
    const url = 'rest/mail/operations/containerenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
         if (args && args.mode === 'EXPORT') {
                let containerDetails = response.results[0].containerDetails
                return containerDetails;
        }
        
        handleResponse(dispatch, response,action,screenMode, containerFilter);
        state.form.containertable?dispatch(reset('containertable')):'';
        return response;
    })
    .catch(error => {
        return error;
    });
}

export function viewMailbag(rowData) {
    const { args } = rowData
    const url = 'rest/mail/operations/containerenquiry/viewmailbag';
    const data = {parentScreen: 'containerenquiry',containerNumber: rowData.containerNumber}
	
    return makeRequest({
        url,
        data: { flightSegments: args.flightPKs}
    })
        .then(function (response) {
            console.log(response);
            navigateToScreen("qualitymanagement.defaults.servicerecovery.list.do",data);
            return response;
        })
        .catch(error => {
            return error;
        });
}
/*export const transferContainerAction=(values)=> {
	const { args, dispatch, getState } = values;
	const state = getState();		
    const filterType=(state.form.reassignForm.values) ? state.form.reassignForm.values.filterType:{};
    const data = {reassignForm};
	
    dispatch({ type: 'CHANGEINPUT_TYPE',data: {filterType}});
}*/




export const onInputChangeSearchmode=(values)=> {
	const {dispatch, getState } = values;
	const state = getState();
    const assignedTo=(state.form.containerFilter.values) ? state.form.containerFilter.values.assignedTo:{};
    dispatch({ type: 'CHANGESEARCH_FILTER',data: {assignedTo}});
}


export const clearFilter=(values)=> {
    const {dispatch, getState} = values;
    const state = getState(); 
    const fromDate = state.commonReducer.filterIntialValues.fromDate;
    const toDate = state.commonReducer.filterIntialValues.toDate; 
    dispatch({ type: 'CLEAR_FILTER', data:{fromDate,toDate}});
    dispatch(reset('containerFilter'));
    dispatch({ type: 'CHANGESEARCH_FILTER',data: 'ALL'});
    //dispatch(asyncDispatch(screenLoad)());    

}

export function toggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }

function handleResponse(dispatch,response,action,screenMode, containerFilter) {
    
    if(!isEmpty(response.results)){
        let companyCode = '';
        console.log(response.results[0]);
        const {containerDetails} = response.results[0];
        const data = response.results[0].containerFilter;
        const estimatedChargePrivilage=response.results[0].estimatedChargePrivilage;
        const summaryFilter = makeSummaryFilter(data);
        if(containerDetails && containerDetails.results){
            companyCode = containerDetails.results[0].companyCode;
        }
        if(containerFilter.destination === null){
            containerFilter.destination = ''
        }
	    if(!(containerFilter.subclassGroup && containerFilter.subclassGroup.length > 0 && containerFilter.subclassGroup === 'EMS')){
            containerFilter.subclassGroup=false;
        }
        if (action==='LIST') {
           dispatch( { type: 'LIST_SUCCESS',pageSize:data.pageSize, containerDetails,screenMode, data, summaryFilter, companyCode, containerFilter,estimatedChargePrivilage});
           dispatch({
            type: "@@redux-form/INITIALIZE", meta: { form: 'containerdetail', keepDirty: true },
            payload: containerDetails.results});
            dispatch({ type: "@@redux-form/RESET", meta: { form: 'containerdetail' } })
        }       
    } else {
        if(!isEmpty(response.errors)){
			 if(!(containerFilter.subclassGroup && containerFilter.subclassGroup.length > 0 && containerFilter.subclassGroup === 'EMS')){
                containerFilter.subclassGroup=false;
            }
             dispatch( { type: 'CLEAR_TABLE'});
        }
    }
}

export function navigateActions(values) {
    const { args,dispatch, getState} = values;
    const state = getState();
    let containerDetailsForOffload=[];
    if(state.containeractionreducer.selectedContainerForOffload!=null){
        containerDetailsForOffload.push(state.containeractionreducer.selectedContainerForOffload);
    }
    let containerDetails = state.containeractionreducer.selectedContainerForOffload!=null?
                        containerDetailsForOffload:state.commonReducer.selectedContainerData;
    const airport = state.listcontainerreducer.filterDetails.airport;
    const fromDate = state.listcontainerreducer.filterDetails.fromDate;
    const toDate = state.listcontainerreducer.filterDetails.toDate;

    if(args.fromButton==='FROM_OFFLOAD'){
        navigateToOffload(containerDetails, airport, fromDate, toDate);
    }
    else{
        const index = args.index;
        containerDetails = state.listcontainerreducer.containerDetails.results[index];
        const containerFilter = (state.form && state.form.containerFilter)?state.form.containerFilter.values:null;
        navigateToMailBags(containerDetails, airport, fromDate, toDate,containerFilter)
    }
        dispatch({ type: 'NAVIGATE_SUCCESS' });
  
}

export function validateContainerOffload(values){
    const { args,dispatch, getState} = values;
    const state = getState();
    let containerDetailsForOffload=[];
    if(state.containeractionreducer.selectedContainerForOffload!=null){
        containerDetailsForOffload.push(state.containeractionreducer.selectedContainerForOffload);
    }
    const containerDetails = state.containeractionreducer.selectedContainerForOffload!=null?
                        containerDetailsForOffload:state.commonReducer.selectedContainerData;
    const validateData={selectedContainerData:containerDetails};
        const url='rest/mail/operations/containerenquiry/offloadContainer';

        return makeRequest({
            url,data: {...validateData}
        }).then(function(response) {
            return response;
        })
        .catch(error => {
            return error;
        });
}

function navigateToMailBags(containerDetails, airport, fromDate, toDate,containerFilter) {
    const data = { containerNumber: containerDetails.containerNumber, carrierCode: containerDetails.carrierCode,
        flightNumber: containerDetails.flightNumber, flightDate: containerDetails.flightDate, fromScreen : "mail.operations.ux.containerenquiry",
        airport: airport, fromDate: fromDate, toDate: toDate,containerFilter:containerFilter,
		containerAssignedOn:containerDetails.assignedOn,assignedPort:containerDetails.assignedPort,
        pageURL: "mail.operations.ux.containerenquiry" }
    navigateToScreen("mail.operations.ux.mailbagenquiry.defaultscreenload.do", data);
}
function navigateToOffload(containerDetails, airport, fromDate, toDate) {
    let containers='';
    if(containerDetails.length>1){
        containerDetails.forEach(function(element) {
            containers=containers+element.containerNumber+',';
        }, this);
    }else{
        containers=containerDetails[0].containerNumber;
    }
    const data = { containerNumber: containers, carrierCode: containerDetails[0].carrierCode,
        flightNumber: containerDetails[0].flightNumber, flightDate: containerDetails[0].flightDate, fromScreen : "mail.operations.ux.containerenquiry",
        pageURL: "mail.operations.ux.containerenquiry" }
    navigateToScreen("mail.operations.ux.offload.defaultscreenload.do", data);
}

export function markAsHBAFlag(values){
    let selectedContainerData=[]
    const {dispatch,getState, args} =values;
    const state = getState();
    const selectedContainer = args.data;
    const mode = args.mode;
    selectedContainerData.push(selectedContainer);
    const data = {selectedContainerData,mode};
    const url='rest/mail/operations/containerenquiry/validateContainerForMarkingHba';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        response={...response,selectedContainer:selectedContainer}
        handlevalidateContainerForMarkingHBA(dispatch, response,mode);
        return response;
    })
    .catch(error => {
        return error;
    });
}
export function handlevalidateContainerForMarkingHBA(dispatch, response,mode) {
    if (response.status==='success') {
        if (response.selectedContainer&&mode==='UNMARK_HBA'){
            let uldReferenceNumber=response.selectedContainer.uldReferenceNo;
            let flightNumber = response.selectedContainer.flightNumber;
            let flightSequenceNumber =response.selectedContainer.flightSequenceNumber;
            let legSerialNumber=response.selectedContainer.legSerialNumber;
            let carrierId=response.selectedContainer.carrierId;
            let carrierCode=response.selectedContainer.carrierCode;
            let assignedPort=response.selectedContainer.acceptedPort;
            let containerNumber=response.selectedContainer.containerNumber;
			const type = response.selectedContainer.type;
            const operationFlag = 'D'
             const data = { uldReferenceNumber, operationFlag, flightNumber, flightSequenceNumber, legSerialNumber,
              carrierId, carrierCode, assignedPort, containerNumber,type}
              const url='rest/xaddons/mail/operations/hbamarking/markHba';
               return makeRequest({
                  url,data: {...data}
              }).then(function(response) {
                  handleHbaMarkingResponse(response, state);
                  return response;
              })
              .catch(error => {
                  return error;
              }); 
        }
    else{
    const selectedContainer = response.selectedContainer;
    const uldReferenceNo = selectedContainer.uldReferenceNo
    const flightNumber = selectedContainer.flightNumber;
	const flightSequenceNumber = selectedContainer.flightSequenceNumber;
	const legSerialNumber = selectedContainer.legSerialNumber;
	const carrierId = selectedContainer.carrierId;
    const carrierCode = selectedContainer.carrierCode;
    const assignedPort = selectedContainer.acceptedPort;
    const containerNumber = selectedContainer.containerNumber;
    const type = selectedContainer.type;
    const clsflg=selectedContainer.expClsFlg;
   var url = "xaddons.mail.operations.hbamarking.defaultscreenload.do?isPopup=true&uldReferenceNumber="+uldReferenceNo+
   "&flightNumber="+flightNumber+"&flightSequenceNumber="+flightSequenceNumber+"&legSerialNumber="+legSerialNumber+"&carrierId="+carrierId+
   "&carrierCode="+carrierCode+"&assignedPort="+assignedPort+"&containerNumber="+containerNumber+"&type="+type;
    var closeButtonIds = ['btnClose'];
    var optionsArray = {
        url,
        dialogWidth: "550",
        dialogHeight: "200",
        closeButtonIds: closeButtonIds,
        popupTitle: 'HBA Position and Type'
        }
        dispatch(dispatchAction(openPopup)(optionsArray))
    }       
               
    }
}
function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    if(data.containerNo && data.containerNo.length > 0){
        filter={...filter, containerNo:data.containerNo};
        count++;
    }
    if(data.fromDate && data.fromDate.length > 0){
        filter={...filter, fromDate:data.fromDate};
        count++;
    }
    if(data.toDate && data.toDate.length > 0){
        filter={...filter, toDate:data.toDate};
        count++;
    }
    if(data.airport && data.airport.length > 0){
        filter={...filter, airport:data.airport};
        count++;
    }
    if(data.assignedBy && data.assignedBy.length > 0){
        filter={...filter, assignedBy:data.assignedBy};
        count++;
    }
    if(data.assignedTo && data.assignedTo.length > 0){
        filter={...filter, assignedTo:data.assignedTo};
        count++;
    }
    if(data.destination && data.destination.length > 0){
        if(count<6){
            filter={...filter, destination:data.destination};
            count++;
        }else{
            popOver={...popOver, destination:data.destination};
            popoverCount++;
        }  
    }
    if(data.toBeTransfered && data.toBeTransfered.length > 0 && data.toBeTransfered === 'true'){
        if(count<6){
            filter={...filter, toBeTransfered:data.toBeTransfered};
            count++;
        }else{
            popOver={...popOver, toBeTransfered:data.toBeTransfered};
            popoverCount++;
        }  
    }
    if(data.notClosedFlag && data.notClosedFlag.length > 0 && data.notClosedFlag === 'true'){
        if(count<6){
            filter={...filter, notClosedFlag:data.notClosedFlag};
            count++;
        }else{
            popOver={...popOver, notClosedFlag:data.notClosedFlag};
            popoverCount++;
        }  
    }
    if(data.mailAcceptedFlag && data.mailAcceptedFlag.length > 0 && data.mailAcceptedFlag === 'true'){
        if(count<6){
            filter={...filter, mailAcceptedFlag:data.mailAcceptedFlag};
            count++;
        }else{
            popOver={...popOver, mailAcceptedFlag:data.mailAcceptedFlag};
            popoverCount++;
        }  
    }
    if(data.showEmpty && data.showEmpty.length > 0 && data.showEmpty === 'Y'){
        if(count<6){
            filter={...filter, showEmpty:data.showEmpty};
            count++;
        }else{
            popOver={...popOver, showEmpty:data.showEmpty};
            popoverCount++;
        }  
    }
    if(data.showUnreleased && data.showUnreleased.length > 0 && data.showUnreleased === 'Y'){
        if(count<6){
            filter={...filter, showUnreleased:data.showUnreleased};
            count++;
        }else{
            popOver={...popOver, showUnreleased:data.showUnreleased};
            popoverCount++;
        }  
    }
    if(isSubGroupEnabled('LUFTHANSA_SPECIFIC')){
    if(data.hbaMarkedFlag && data.hbaMarkedFlag.length > 0 && data.hbaMarkedFlag === 'Y'){
        if(count<6){
            filter={...filter, hbaMarked:data.hbaMarkedFlag};
            count++;
        }else{
            popOver={...popOver, hbaMarked:data.hbaMarkedFlag};
            popoverCount++;
        }  
    }
}
    if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
    if(data.subclassGroup && data.subclassGroup.length > 0 && data.subclassGroup === 'EMS'){
        if(count<6){
            filter={...filter, subclassGroup:data.subclassGroup};
            count++;
        }else{
            popOver={...popOver, subclassGroup:data.subclassGroup};
            popoverCount++;
        }  
    }
}
if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
if(data.unplannedContainers && data.unplannedContainers.length > 0 && data.unplannedContainers === 'Y'){
    if(count<6){
        filter={...filter, unplannedContainers:data.unplannedContainers};
        count++;
    }else{
        popOver={...popOver, unplannedContainers:data.unplannedContainers};
            popoverCount++;
        }  
    }
}
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}