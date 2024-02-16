import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {reset} from 'redux-form';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions'; 
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


export function screenLoad(values) {
    const {dispatch} = values;  
    const containerFilter  = {}
    const url='rest/mail/operations/containerenquiry/screenload';
    return makeRequest({
        url,data:{containerFilter}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })   
}

export function validateContainerForReassign(values) {    
    const {dispatch, getState , args } = values;
    const state = getState();     
    let selectedContainer=null;
    let flag=null;
    let selectedContainerData=[]
    let multipleFlag=false;
    //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    if(!isEmpty(args)){
      selectedContainer = args.data;
      flag = args.flag;
    }
    const mode = args.mode;
    if(flag!=null && flag!='REASSIGN_IND'){
    const indexes = state.commonReducer.selectedMailbagIndex;
    
    for(var i=0; i<indexes.length;i++) {
        selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
    }
    if(selectedContainerData.length===0){
        selectedContainerData = (state.commonReducer)?state.commonReducer.selectedContainerDataByIndex:{};
    }
    multipleFlag=true;
 }
   else{
    selectedContainerData.push(selectedContainer);
    }
    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/validateContainerForReassign';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        response={...response,reassignFlag:flag,selectedContainer:selectedContainerData}
        handlevalidateContainerResponse(dispatch, response, mode,multipleFlag);
        return response;
    })   
}

export function unassignContainer(values) {    
    const {args, dispatch, getState } = values;
    const state = getState();     
    let indexes=[]; 
     let selectedContainerData=[]
    if(args.target.dataset.index) 
        indexes.push(args.target.dataset.index);
    else
      indexes = state.commonReducer.selectedMailbagIndex;
     for(var i=0; i<indexes.length;i++) {
         selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
     }
    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/unassignContainer';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleUnassignContainerResponse(dispatch, response);
        return response;
    })   
}

/*export function viewMailBag(values){
    const { args, dispatch, getState } = values;
    const state = getState();     
    const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     const url='rest/mail/operations/containerenquiry/viewMailBag';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleViewMailBagResponse(dispatch, response);
        return response;
    }) 
    .catch(error => {
        return { type:'VIEWMAILBAG_ERROR',error};
    });    
}*/
export function validateContainerForTransfer(values){
    const {args, dispatch ,getState } = values;
    const state = getState();
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     const indexes = state.commonReducer.selectedMailbagIndex;
     const {index, mode} = args;
     let selectedContainerData=[];
     for(var i=0; i<indexes.length;i++) {
         selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
     }
    if(selectedContainerData.length===0){
        selectedContainerData = [state.listcontainerreducer.containerDetails.results[index]];
    }
    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/validateContainerForTransfer';
      return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handlevalidateContainerTransferResponse(dispatch, response, selectedContainerData, mode);
        return response;
    })
      .catch(error => {
        return { type:'TRANSFER_ERROR',error};
    });   
}

export function transferContainerAction(values){
    const {dispatch,getState,args} =values;
    const state =getState();
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     const indexes = state.commonReducer.selectedMailbagIndex;
     let selectedContainerData=[]
     for(var i=0; i<indexes.length;i++) {
         selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
     }
    const transferForm  = (state.form.transferForm.values)?state.form.transferForm.values:{}
    const flightNumber=(state.form.transferForm.values) ? state.form.transferForm.values.flightnumber:{};
    const onwardRouting=(state.form.onwardRouting) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }


    transferForm.assignedto = state.form.transferForm.values.transferFilterType;
    transferForm.reassignedto = 'DESTINATION';

    if(!isEmpty(flightNumber)) {
      transferForm.reassignedto = 'FLIGHT';
      transferForm.assignedto = 'FLIGHT';
      transferForm.flightNumber=flightNumber.flightNumber;
      transferForm.flightDate=flightNumber.flightDate;
      transferForm.flightCarrierCode=flightNumber.carrierCode;
    }

    if(selectedContainerData.length===0){
        selectedContainerData = (state.commonReducer)?state.commonReducer.selectedContainerDataByIndex:{};
    }
    let screenWarning=false;
    if(args&&args.screenWarning){
        screenWarning= args.screenWarning;
    }
    transferForm.flightPou = transferForm.destination;
    transferForm.fromScreen='MTK058';
    const data = {selectedContainerData,transferForm, onwardRouting,screenWarning};
       const url='rest/mail/operations/containerenquiry/transferContainerSave';
      return makeRequest({
         url,data: {...data}
    }).then(function(response) {
        console.log(response);
        handlevalidateContainerTransferSaveResponse(dispatch, response);
        return response;
    })
      .catch(error => {
        return { type:'TRANSFERSAVE_ERROR',error};
    });   

}
export function markUnmarkULDFullIndicator(values) {
    const { args, getState, dispatch } = values;
    const state = getState();
    let indexes = [];
    let selectedContainerData = [];
    if (args.index) {
        indexes.push(args.index);
    }

    for (var i = 0; i < indexes.length; i++) {
        selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
        let uldFulIndFlag = 'N';
        if (args.mode == 'ULD_MARK_FULL') {
            uldFulIndFlag = 'Y';
        }
        selectedContainerData[i].uldFulIndFlag = uldFulIndFlag;

    }

    const validateData = { selectedContainerData };
    const url = 'rest/mail/operations/containerenquiry/markUnmarkIndicator';
    return makeRequest({
        url, data: { ...validateData }
    }).then(function (response) {
        return response;
    })
        .catch(error => {
            return error;
        });
}
export function printuldtag(values){
    
   const {args, dispatch ,getState } = values;
    const state = getState();
     const indexes = state.commonReducer.selectedMailbagIndex;
     const {index, mode} = args;
     let selectedContainerData=[];
     if(index) {
      selectedContainerData = [state.listcontainerreducer.containerDetails.results[index]];
      }
     if(selectedContainerData.length===0){
        for(var i=0; i<indexes.length;i++) {
         selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
       }
    }
    let flightDataNotPresent = false;
    if(selectedContainerData.length>0){
        for(var i=0; i<selectedContainerData.length;i++) {
            if(selectedContainerData[i].flightDate==null || selectedContainerData[i].flightDate.length<=1) {
                flightDataNotPresent=true;
            }
        }
    }
    if(flightDataNotPresent){
        dispatch(requestValidationError("Please select a container with Flight details", ''));
    }else{
        const data = {selectedContainerData};
        const url = 'rest/xaddons/mail/operations/printuldtag';
        return makeRequest({
            url, data: { ...data }
        }).then(function (response) {
            openULDTagPopup(response);
            return response;
        })
            .catch(error => {
                return error;
            }); 
    }
   

}
export function openULDTagPopup(response){
    var uldlabeldetails=response.results[0];
    var redirecturl=response.results[1];
    
    var url = redirecturl +"?"+ uldlabeldetails;
    var windowFeatures = 'width=1150,height=560'; 
  
    window.open(url, '_blank', windowFeatures);
}
export function checkCloseFlight(values) {
    const { args, getState, dispatch } = values;
    const state = getState();
    let indexes = [];
    let data = {};
    let selectedContainerData = [];
    if (args.index) {
        indexes.push(args.index);
    }
    for (var i = 0; i < indexes.length; i++) {
        selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
    }
    data = { selectedContainerData };
    const url = 'rest/mail/operations/containerenquiry/checkCloseFlight';
    return makeRequest({
        url, data: { ...data }
    }).then(function (response) {
        return response;
    })
        .catch(error => {
            return error;
        });
}

/*export function onClose(){
    console.log("common action close");
      return{ type : 'CLOSE'}
}

/*export const  onClose=(values)=>{
     const { args, dispatch, getState } = values;
     const state = getState(); 
     dispatch( { type: 'CLOSE'});  
      //return{ type :'CLOSE'}
}*/
/*export function handleViewMailBagResponse(dispatch,response){
   if (response.status==='viewmailbag_success') {
        dispatch(viewMailBagSuccess(response.status));       
    } 
}
export function viewMailBagSuccess(data){
    return {type : 'VIEWMAILBAG_SUCCESS',date}
}*/
export function handlevalidateContainerTransferSaveResponse(dispatch,response){
   if (response.status==='transfersave_success') {
        dispatch(transferContainerSuccess(response.status));       
    } 
}

export function handleScreenloadResponse(dispatch, response) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));
       
    }
}
export function handlevalidateContainerResponse(dispatch, response, mode,multipleFlag) {
    if (response.status==='success') {
        if(response.selectedContainer.length>0){
            for(let i=0;i< response.selectedContainer.length;i++){
            if(response.selectedContainer[i].type === 'B') {
                response.selectedContainer[i].barrowFlag=true;
            }
            else {
                response.selectedContainer[i].barrowFlag=false;
            } 
            if(response.selectedContainer[i].paBuiltFlag==='Y') {
                response.selectedContainer[i].isPaBuilt=true;
            }
            else {
                response.selectedContainer[i].isPaBuilt=false;
            } 
        }
        }
        if(mode==='CARRIER'){
            dispatch(validateContainerSuccess(response));
        }else if(mode==='FLIGHT'){
           
            dispatch(validateContainerSuccessReassignToFlight(response,multipleFlag));
        }
               
    }
}
export function handlevalidateContainerTransferResponse(dispatch, response, selectedContainerData, mode){
    if(response.status === 'success'){
        let currentTime= response.results[0].transferForm.mailScanTime;
        if(mode === 'CARRIER'){
            dispatch(validateContainerSuccessForTransfer(selectedContainerData,currentTime));
        }else if(mode === 'FLIGHT'){
            dispatch(validateContainerSuccessForTransferToFlight(selectedContainerData,currentTime, response));
        }
       
            
    }
}
export function transferContainerSuccess(data){
    return{ type : 'TRANSFERSAVE_SUCCESS', data }
}
export function validateContainerSuccessForTransfer(selectedContainerData,currentTime){
    return { type: 'VALIDATEFORTRANSFER_SUCCESS', selectedContainerData , currentTime};
}
export function validateContainerSuccessForTransferToFlight(selectedContainerData,currentTime, data){
    if(selectedContainerData.length>0){
        for(let i=0;i< selectedContainerData.length;i++){
        if(selectedContainerData[i].paBuiltFlag==='Y') {
            selectedContainerData[i].isPaBuilt=true;
        }
        else {
            selectedContainerData[i].isPaBuilt=false;
        } 
    }
    }
   
    return { type: 'VALIDATEFORTRANSFER_FLIGHT_SUCCESS', selectedContainerData , currentTime, data};
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}
export function validateContainerSuccess(data) {
    return { type: 'VALIDATECONTAINER_SUCCESS', data };
}
export function validateContainerSuccessReassignToFlight(data,multipleFlag) {
    return { type: 'VALIDATE_CONTAINER_REASSIGN_FLIGHT_SUCCESS', data,multipleFlag};
}
export function handleUnassignContainerResponse(dispatch,response) {
    if(response.status==='success')  
    dispatch(validateUnassignContainer(response.status));   
}
export function validateUnassignContainer(data){   
   return { type: 'UNASSIGN_SUCCESS', data };
}

export const selectContainers=(values)=> {
   const { args, dispatch} = values;
   const containerDetails= args; 
   dispatch( { type: 'SELECT_CONTAINER', containerDetails});      
}

export const selectContainersForOffload=(values)=> {
    const { args, dispatch} = values;
    const selectedContainer= args; 
    dispatch( { type: 'SELECT_CONTAINER_OFFLOAD', selectedContainer});      
 }

 export const selectContainersForRelease=(values)=> {
    const { args, dispatch} = values;
    const selectedContainer= args; 
    dispatch( { type: 'SELECT_CONTAINER_RELEASE', selectedContainer}); 
 }
export function reassignContainerAction(values){
    const {dispatch, getState,args } = values;
    const state = getState(); 
    const reassignFlag = state.containeractionreducer.reassignFlag;
    const selectedContainer = state.containeractionreducer.selectedContainer;
    const filterType=(state.form.reassignContainer.values) ? state.form.reassignContainer.values.reassignFilterType:{};
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     const indexes = state.commonReducer.selectedMailbagIndex;
     let selectedContainerData=[]
     if(reassignFlag!=null && reassignFlag=='REASSIGN_IND' && selectedContainer!=null){
        selectedContainerData=selectedContainer;
     }
     else{
     for(var i=0; i<indexes.length;i++) {
         selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
     }
     }
    //const data = {selectedContainerData};
    const reassignContainer  = (state.form.reassignContainer.values)?state.form.reassignContainer.values:{}
    const flightNumber=(state.form.reassignContainer.values) ? state.form.reassignContainer.values.flightnumber:{};
    const onwardRouting=(state.form.onwardRouting) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }

  if(!isEmpty(flightNumber)) {
      reassignContainer.flightNumber=flightNumber.flightNumber;
      reassignContainer.flightDate=flightNumber.flightDate;
      reassignContainer.carrierCode=flightNumber.carrierCode;
    } else if(filterType==='C'){
        reassignContainer.carrierCode=state.form.reassignContainer.values.flightCarrierCode;
    }
    reassignContainer.assignedto = state.form.reassignContainer.values.reassignFilterType;
    if(!reassignContainer.destination){
        return Promise.reject(new Error("Please enter destination")); 
    }
    for(let i=0; i<selectedContainerData.length; i++){
        selectedContainerData[i].remarks = reassignContainer.remarks;
    }
      let warningMessagesStatus ={};
       
    if(args&&args.screenWarning){
        screenWarning= args.screenWarning;
        warningMessagesStatus = { ["mail.operations.securityscreeningwarning"]: 'Y' };

    }
    const data = {selectedContainerData,reassignContainer, onwardRouting,warningMessagesStatus};
    const url='rest/mail/operations/containerenquiry/reassignContainer';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleReassignContainerResponse(dispatch, response);
        return response;
    })
    .catch(error => {
        return error;
    });  
}

export function handleReassignContainerResponse(dispatch,response){
    if(response.status=="reassign_success"){
        dispatch(reassignContainerSuccess(response.status));
        dispatch( {type:'CONTAINER_CONVERSION',uldToBarrow:false,barrowToUld:false})
    }
}

export function reassignContainerSuccess(data){
    return {type:'REASSIGN_SUCCESS', data};
}


export function onCloseFunction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();

    let isPopup=state.__commonReducer.screenConfig?state.__commonReducer.screenConfig.isPopup:'';

    if(isPopup == "true") closePopupWindow();  //Added by A-7929 as part of ICRD-ICRD-334270
    else
    navigateToScreen('home.jsp', {});
}

export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}

export const onClearContainerFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_TABLE_FILTER'});
    dispatch(reset('ContainerDetailsFilter'));   
}

export function applyContainerFilter(values) {
    const {dispatch, getState } = values;
    const state = getState();
  //  const {displayPage,action} = args;
    const tableFilter  = (state.form.ContainerTableFilter.values)?state.form.ContainerTableFilter.values:{}
    tableFilter.undefined?delete tableFilter.undefined:'';
    const { flightnumber, ...rest } = tableFilter;
    const ContainerTableFilter={...flightnumber,...rest};
    dispatch( { type: 'LIST_FILTER',ContainerTableFilter, tableFilter});
    return Object.keys(ContainerTableFilter).length;

   
}

export const onInputChangeSearchmode=(values)=> {
	const {dispatch, getState } = values;
	const state = getState();	
    const assignedTo=(state.form.ContainerTableFilter.values) ? state.form.ContainerTableFilter.values.assignedTo:{};
    dispatch({ type: 'CHANGESEARCH_FILTER_TABLE',data: {assignedTo}});
}

//Content id save
export function contentId(values){
    const {getState,args} = values;
    const state = getState();    
    const index = args;
    //const containers=state.form.containertable?state.form.containertable.values.containertable :null;
    const containers=state.listcontainerreducer?state.listcontainerreducer.containerDetails.results:null;
    const contentId= state.form.containertable&& state.form.containertable.values?state.form.containertable.values.containertable[index].contentId:'';
    const selectedContainerData=[containers[index]];
    selectedContainerData[0].contentId=contentId;

    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/contentIdSave';
      return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleSaveContentID(dispatch, response);
        return response;
    })
      .catch(error => {
        return { type:'CONTENT_ID_SAVE_ERROR',error};
    });   
}
export function handleSaveContentID(dispatch,response){
    if(response.status=="success"){
        dispatch(savecontentid(response.status));
    }
}

export function savecontentid(data){
    return {type:'CONTENT_ID_SAVE_SUCCESS', data};
}

export function saveActualWeight(values) {
   // const { getState,args } = values;
    const {dispatch, getState , args } = values;
    const state = getState();
    const index= args.rowIndex;
    // let mailbagId="";
    const containers=state.form.containertable?state.form.containertable.values.containertable :null;
    //const containers=state.listcontainerreducer.containerDetails?state.listcontainerreducer.containerDetails.results:null;
    //const actualweight = args.actualWeight; 
    const selectedContainerData=[containers[index]];
    //selectedContainerData[0].actualWeight = actualweight;
    const data = { selectedContainerData };
    const url='rest/mail/operations/containerenquiry/saveActualWeight';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        return response;
    }) 
    .catch(error => {
        return error;
    });
    }

    export function validateActualWeight(data) {
        let isValid = true;
        let error = "";
        let isNum=true;
    
        if(data==undefined) {
            isValid = false;
        }else{
            if(data==="") {
                isValid = false;
                }
               else if(isNaN(data)){
                if(data.includes(".")&& !isNaN(data)){
                    isNum=true; 
                }
                else{
                    isNum=false;
                }
               } 
               
            }
    
        let validObject = {
            valid: isValid,
            msg: error,
            numeric:isNum
        }
        return validObject;
    }

    export const warningHandler = (action, dispatch) => {
        switch (action.type) {
            case "__DELEGATE_WARNING_ONOK":
                if (action.executionContext) {
                    const warningCode = action.warningCode;
                    if (warningCode[0] === "mail.operations.container.actualweight") {
                        dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                    }
                    if (warningCode[0] ==="mail.operations.container.actualweightnotpresent") {
                        dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                            dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':1,'pageSize':10,action:'LIST'}))            
                          });
                    }
                    if(warningCode == "mail.operations.container.uldwarning"){
                        dispatch({ type: 'ULD_CONVERSION', uldToBarrow:true });
                        dispatch(asyncDispatch(action.executionContext.functionRecord)({uldToBarrow:true,warningFlag:action.executionContext.args.warningFlag}));
                    
                       
                         }
                         if(warningCode=="mail.operations.container.barrowwarning"){
                             dispatch({ type: 'BULK_CONVERSION' , barrowToUld:true });
                            dispatch(asyncDispatch(action.executionContext.functionRecord)({barrowToUld:true,warningFlag:action.executionContext.args.warningFlag}));
                      
                         }
                         if (warningCode == "mail.operations.securityscreeningwarning") {
                            action.executionContext.args.screenWarning=true;
                            dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                                dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':1,'pageSize':10,action:'LIST'}))            
                              });
                        }
                }
                break;
            case "__DELEGATE_WARNING_ONCANCEL":
                if (action.executionContext) {
                    const warningCode = action.warningCode;
                    if (warningCode == 'mail.operations.container.actualweight') {
                        dispatch(asyncDispatch(onlistContainerDetails)({action:'LIST'}));
                    }
                }
                break;
            default:
                break;
        }
    }

    export function onlistContainerDetails(values) {
        const { args, dispatch, getState } = values;
        const state = getState();
        const {displayPage,action} = args;
        const containerFilter  = (state.form.containerFilter.values)?state.form.containerFilter.values:{};
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
        //Added by A-8893 for release container starts
        const showUnreleased=state.form.containerFilter.values.showUnreleased; 
        if(showUnreleased)
        state.form.containerFilter.values.showUnreleased='Y';
        //Added by A-8893 for release container ends
        if(isSubGroupEnabled('SINGAPORE_SPECIFIC')){
        const subclassGroup=state.form.containerFilter.values.subclassGroup;
        if(subclassGroup)
        state.form.containerFilter.values.subclassGroup='EMS';
        }else{
            state.form.containerFilter.values.subclassGroup='OTHERS';   
          }
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
            handleResponse(dispatch, response,action,screenMode,containerFilter);
            return response;
        })
        .catch(error => {
            return error;
        });
    }

    function handleResponse(dispatch,response,action,screenMode,containerFilter) {
    
        if(!isEmpty(response.results)){
            let companyCode = '';
            const {containerDetails} = response.results[0];
            const data = response.results[0].containerFilter;
            const summaryFilter = makeSummaryFilter(data);

            if(containerDetails && containerDetails.results){
                companyCode = containerDetails.results[0].companyCode;

            }
            if(containerFilter && containerFilter.destination === null){
                containerFilter.destination = ''
            }
            if (action==='LIST') {
               dispatch( { type: 'LIST_SUCCESS', pageSize:data.pageSize,containerDetails,screenMode, data, summaryFilter,companyCode, containerFilter});
               dispatch({
                type: "@@redux-form/INITIALIZE", meta: { form: 'containerdetail', keepDirty: true },
                payload: containerDetails.results});
                dispatch({ type: "@@redux-form/RESET", meta: { form: 'containerdetail' } })
            }       
        } else {
            if(!isEmpty(response.errors)){
                 dispatch( { type: 'CLEAR_TABLE'});
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


    //The main outbound list
export const listFlightDetailsForReassignAndTransfer = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let displayPage='';
   // const mode = state.commonReducer.displayMode;
    let mode=state.commonReducer.displayMode;
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    const assignTo = state.commonReducer.flightCarrierflag;
    let flightCarrierFilter = {};
    let panel = '';

    if(args && args.panel){
        panel = args.panel;
    }
    
if(panel === 'reassign'){
    flightCarrierFilter = (state.form.reassignContainerToFlight && state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values : {}
}else if(panel === 'transfer'){
    flightCarrierFilter = (state.form.transferContainerToFlight && state.form.transferContainerToFlight.values) ? state.form.transferContainerToFlight.values : {}
}
    
    if(!flightCarrierFilter){
    if (!flightCarrierFilter.flightnumber.flightDate || !flightCarrierFilter.flightnumber.flightNumber) {
        if(!flightCarrierFilter.flightnumber.flightDate && !flightCarrierFilter.flightnumber.flightNumber) {
           if (!(flightCarrierFilter.fromDate && flightCarrierFilter.toDate)) {
            return Promise.reject(new Error("Please enter Flight date and Flight number or Flight date range"));
           }
        }
        else if(!flightCarrierFilter.flightnumber.flightDate){
            return Promise.reject(new Error("Please enter Flight Date"));
        }
        else if (!flightCarrierFilter.flightnumber.flightNumber){
            return Promise.reject(new Error("Please enter Flight Number"));
        }
    }
}

    if((flightCarrierFilter.fromDate && flightCarrierFilter.toDate)){
        const fromDate = new Date(flightCarrierFilter.fromDate);
        const toDate = new Date(flightCarrierFilter.toDate);
        const diffTime = Math.abs(toDate - fromDate);
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        if(diffDays>2){
            return Promise.reject(new Error("Date difference cannot be greater than 2 days"));
        } 
    }
    
    flightCarrierFilter.assignTo = 'F';
    flightCarrierFilter.airportCode=loggedAirport;
    flightCarrierFilter.filterType = 'F';
    //Added by A-8893 as part of IASCB-33763 starts
    flightCarrierFilter.fromTime='00:00',
    flightCarrierFilter.toTime='23:59',
  //Added by A-8893 as part of IASCB-33763 ends
    flightCarrierFilter.destination=flightCarrierFilter.dest?flightCarrierFilter.dest:'';
    flightCarrierFilter.carrierCode?flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode:flightCarrierFilter.carrierCode='AA';
    let flightNumber = {};
    if(panel === 'reassign'){
        flightNumber = (state.form.reassignContainerToFlight && state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values.flightnumber : {};
    }else if(panel === 'transfer'){
        flightNumber = (state.form.transferContainerToFlight && state.form.transferContainerToFlight.values) ? state.form.transferContainerToFlight.values.flightnumber : {};
    }
    if(flightCarrierFilter.airportCode ===loggedAirport ){
        flightActionsEnabled='true';
    }
        if (!isEmpty(flightNumber) && (flightNumber.carrierCode)) {
            flightCarrierFilter.flightNumber = flightNumber.flightNumber;
            flightCarrierFilter.flightDate = flightNumber.flightDate;
            flightCarrierFilter.carrierCode = flightNumber.carrierCode
        }
        if(args) {
            displayPage = args.displayPage;
            //for 1st page listing
           if(args.mode==='display') {
              mode='display';
          }
           flightCarrierFilter.flightDisplayPage = displayPage;
       }
      else
    {
       flightCarrierFilter.flightDisplayPage = state.filterReducer.flightDisplayPage;
       mode = state.commonReducer.displayMode;
       displayPage=state.filterReducer.flightDisplayPage
    }
    flightCarrierFilter.operatingReference = state.commonReducer.defaultOperatingReference;    
    const data = { flightCarrierFilter };
    const url = 'rest/mail/operations/outbound/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(!response.results){
            return Promise.reject(new Error("No results found"));
        }
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
            if(!mailflightspage){
                return Promise.reject(new Error("No results found"));
            }
            handleResponseForReassignToFlight(dispatch, response, data,mode,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

export const fetchFlightCapacityDetails = (values) =>{
    const {  dispatch, getState } = values;
    const state = getState();
   // const mode = state.commonReducer.displayMode;
    const flightCarrierFilter = (state.form.reassignContainerToFlight && state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values : {}
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    if(flightCarrierFilter.airportCode === loggedAirport ){
        flightActionsEnabled='true';
    }
    flightCarrierFilter.assignTo = 'F';
    flightCarrierFilter.airportCode=loggedAirport;
    flightCarrierFilter.filterType = 'F';
    flightCarrierFilter.destination=flightCarrierFilter.dest?flightCarrierFilter.dest:'';
    flightCarrierFilter.carrierCode?flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode:flightCarrierFilter.carrierCode='AA';
   
    let List=state.containeractionreducer.flightDetails?state.containeractionreducer.flightDetails.results:[];
    let mailAcceptanceList=[];
    for (var i = 0; i < List.length; i++) {
        let acceptance={};
        acceptance.companyCode=List[i].companyCode;
        acceptance.carrierId=List[i].carrierId;
        acceptance.flightRoute=List[i].flightRoute;
        acceptance.flightNumber=List[i].flightNumber;
        acceptance.flightSequenceNumber=List[i].flightSequenceNumber;
        acceptance.flightDate=List[i].flightDate;
        mailAcceptanceList.push(acceptance);
    }
 // const data = { mailAcceptanceList:{...mailAcceptanceList,pouList: null,preadvice: null,preassignFlag: null,totalContainerWeight: null, totalCapacity:null,totalWeight: null,numericalScreenId: null,containerPageInfo:null, mailCapacity:null,mailbags:null,flightpk:null,containerDetails:null},flightCarrierFilter };
 const data = { mailAcceptanceList,flightCarrierFilter}
 const url = 'rest/mail/operations/outbound/fetchFlightCapacityDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        
        handleResponseForReassignToFlight(dispatch, response, data, 'FETCH_CAPACITY',flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

function handleResponseForReassignToFlight(dispatch, response, data, mode,flightActionsEnabled) {
    
    if (!isEmpty(response.results)) {
        if(mode === 'FETCH_CAPACITY') {
            const { flightCapacityDetails } = response.results[0];
             dispatch({ type: 'LIST_CAPACITY_REASSIGN_FLIGHT',flightCapacityDetails,mode});
       }else{
            const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
            dispatch({ type: 'LIST_DETAILS_REASSIGN_FLIGHT', mailflightspage, wareHouses, data, mode,flightActionsEnabled });
       }
    }
}

export const clearReassignFlightPanel = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'CLEAR_REASSIGN_FLIGHT_PANEL' });
    dispatch(reset('reassignContainerToFlight'));

}

export const clearTransferFlightPanel = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: 'CLEAR_TRANSFER_FLIGHT_PANEL' });
    dispatch(reset('transferContainerToFlight'));

}

export function reassignContainerToFlightAction(values){
    const {dispatch, getState ,args} = values;
    const state = getState(); 
    let selectedContainerData=[];
    selectedContainerData=state.containeractionreducer?state.containeractionreducer.selectedContainer:[];
    const containerDetails = state.form.containerDetails?(state.form.containerDetails.values.containerDetails):[];
    let loggedAirport= state.commonReducer.airportCode;
    let reassignFilter=state.containeractionreducer.reassignFilter;
    let  reassignContainerToFlightOrCarrier={};
    let pou = '';
    let selectedFlight={};
    if(reassignFilter==='F'){
    const selectedFlightIndex=state.containeractionreducer?state.containeractionreducer.selectedFlightIndexReassign:[];
   
    if(selectedFlightIndex.length!==1){
        return Promise.reject(new Error("Please select one of the flight details"));
    }

    selectedFlight=state.containeractionreducer?state.containeractionreducer.flightDetails.results[selectedFlightIndex[0]]:{};

   
    // const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    //const data = {selectedContainerData};
    reassignContainerToFlightOrCarrier  = (state.form.reassignContainerToFlight.values)?state.form.reassignContainerToFlight.values:{}
    const flightNumber=(state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values.flightnumber:{};
   


    const flightRoute = selectedFlight.flightRoute;
    const pous = flightRoute.split("-");
    
    for(let k=0; k<pous.length; k++){
        if(pous[k] === loggedAirport){
            pou = pous[k+1];
            break;
        }
    }
}
    else if(reassignFilter==='C'){
        reassignContainerToFlightOrCarrier=(state.form.reassignContainer.values)?state.form.reassignContainer.values:{};
        pou=reassignContainerToFlightOrCarrier.destination;
      }
    let onwardRouting=(state.form.onwardRouting && state.form.onwardRouting.values) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }
    let reassignContainer = {};
    if(reassignFilter==='F'){
    reassignContainer.assignedto = "F";
    reassignContainer.carrierCode=selectedFlight.carrierCode;
    reassignContainer.destination = pou;
    reassignContainer.flightDate=selectedFlight.flightDate;
    reassignContainer.flightNumber=selectedFlight.flightNumber;
    reassignContainer.flightSeqNumber=selectedFlight.flightSequenceNumber
    reassignContainer.reassignFilterType= "F";
    }
    else if(reassignFilter==='C'){
        reassignContainer.assignedto = "C";
        reassignContainer.reassignFilterType= "C";
        reassignContainer.destination=reassignContainerToFlightOrCarrier.destination;
        reassignContainer.carrierCode=reassignContainerToFlightOrCarrier.flightCarrierCode;
    
    }
    reassignContainer.mailScanTime = reassignContainerToFlightOrCarrier.mailScanTime;
    reassignContainer.scanDate=reassignContainerToFlightOrCarrier.scanDate;
    reassignContainer.remarks=reassignContainerToFlightOrCarrier.Remarks;

    if (!(reassignContainer.destination)) {
        return Promise.reject(new Error("Please enter Destination"));
       }
       if(!isNaN(reassignContainerToFlightOrCarrier.actualWeight)){
        reassignContainerToFlightOrCarrier.actualWeight= reassignContainerToFlightOrCarrier.actualWeight.toString();
        if(reassignContainerToFlightOrCarrier.actualWeight>0 && reassignContainerToFlightOrCarrier.actualWeight.includes(".")){
        const num = reassignContainerToFlightOrCarrier.actualWeight.split(".")[0];
        const dec = reassignContainerToFlightOrCarrier.actualWeight.split(".")[1];
          if(dec.includes(".")){
             return Promise.reject(new Error("Invalid weight format."));
          }
        }
      }
       else {
            return Promise.reject(new Error("Invalid weight format."));  
       }
      let  actualWeight=0;   
      if(reassignContainerToFlightOrCarrier.actualWeight) {
        actualWeight=parseInt(reassignContainerToFlightOrCarrier.actualWeight);
      }
             /*Added by A-8893 for IASCB-38895 starts
           * This forloop and checks are used for showing warning in the case of uld to barrow and barrow to uld conversion.
           * here we check theer is any change made in the checkbox .
           * if yes then will check is it ticked or unchecked
           * and on the basis we will set the boolean uldTobulkValidaion and bulkToUldValidaion true or false
*/
      let uldBarrowCheck=false;
      let uldTobulkValidaion=false;
      let bulkToUldValidaion=false;
      let containerNumbers=null;
   for(let i=0;i< containerDetails.length;i++){
       for(let j=0; j<selectedContainerData.length; j++){
           if(containerDetails[i].containerNumber===selectedContainerData[j].containerNumber){
            if(containerDetails[i].barrowFlag!=selectedContainerData[j].barrowFlag){
               uldBarrowCheck=true;
           if(containerDetails[i].barrowFlag){
               
            selectedContainerData[j].uldTobarrow=true;
               uldTobulkValidaion=true;
               if(containerNumbers===null){
                   containerNumbers=containerDetails[i].containerNumber;
               }
               else{
               containerNumbers=containerNumbers+" , "+containerDetails[i].containerNumber;
               }
           }
           else{
            selectedContainerData[j].barrowToUld=true;
               bulkToUldValidaion=true;
           }
         }
       }
    } 
   }
     /*
          * Here if the uldvalidation is true then check wherthr the reducer value is true or false
          * it will be false in the initial case and will update the value as true after the ok click inside the warning popup
          * like the same way bulkToUldValidaion will also check
          * if there is no change in the checkbox. then will not need to show any validation.
          * so in that case we directly set  isValidation as true and will call the server side method 
           */
   let isValidation=false;
   let uldToBarrow=state.commonReducer.uldToBarrow;
 let barrowToUld=state.commonReducer.barrowToUld;
        if(uldTobulkValidaion&&uldBarrowCheck){
            if(!uldToBarrow){
             dispatch(requestWarning([{code:"mail.operations.container.uldwarning", description:"The selected ULD container("+containerNumbers+") will be converted as Bulk. Do you want to continue?"}],{functionRecord:reassignContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
            }
            else if(bulkToUldValidaion&&!barrowToUld){
                 dispatch(requestWarning([{code:"mail.operations.container.barrowwarning", description:"The selected bulk container will be converted as ULD. Do you want to continue?"}],{functionRecord:reassignContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
            }
            else{
             isValidation=true;
            }
         }
       else if(bulkToUldValidaion&&uldBarrowCheck){
         if(!barrowToUld){
             dispatch(requestWarning([{code:"mail.operations.container.barrowwarning", description:"The selected bulk container will be converted as ULD. Do you want to continue?"}],{functionRecord:reassignContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
         }
         else{
             isValidation=true;
         } }
            else{
             isValidation=true;
            }
             //Added by A-8893 forIASCB-38895 ends
      for(let i=0; i<selectedContainerData.length; i++){
        for(let j=0; j<containerDetails.length; j++){
            if(selectedContainerData[i].containerNumber === containerDetails[j].containerNumber){
                if(reassignFilter==='F'){
                if(!containerDetails[j].pou){
                    if(selectedContainerData[i].type ==='B'){
                        selectedContainerData[i].pou = pou;
                        selectedContainerData[i].destination = containerDetails[j].destination;
                        selectedContainerData[i].finalDestination = containerDetails[j].destination;
                    }else{
                        selectedContainerData[i].pou = pou;
                        selectedContainerData[i].destination = containerDetails[j].destination;
                        selectedContainerData[i].finalDestination = containerDetails[j].destination;
                    }
                }else{
                    selectedContainerData[i].pou = containerDetails[j].pou;
                    selectedContainerData[i].destination = containerDetails[j].destination;
                    selectedContainerData[i].finalDestination = containerDetails[j].destination;
                }
            }
            
            else{
                selectedContainerData[i].destination = containerDetails[j].destination;
                selectedContainerData[i].finalDestination = containerDetails[j].destination;
            }
                selectedContainerData[i].actualWeight = containerDetails[j].actualWeight;
                selectedContainerData[i].contentId = containerDetails[j].contentId;
                selectedContainerData[i].remarks = containerDetails[j].Remarks;
            }
        }
    }

    let actualWeightFlag = true;
    if(state.commonReducer.weightScaleAvailable&&args&&!args.warningFlag&&reassignFilter==='F'){
        for(let i=0; i<selectedContainerData.length; i++){
        if(selectedContainerData[i].actualWeight==0){
            actualWeightFlag = false;
       dispatch(requestWarning([{code:"mail.operations.container.actualweight", description:"Actual weight is not captured. Do you want to continue?"}],{functionRecord:reassignContainerToFlightAction, args:{warningFlag:true}}));
       }
    }
   }

   if(actualWeightFlag){
        actualWeight=parseInt(selectedContainerData[0].actualWeight);
   }
   const warningDestination=reassignContainer.destination;
   dispatch({ type: 'SAVE_WARNING_DESTINATION', warningDestination});
   let warningMessagesStatus ={};
   if(args&&args.screenWarning){
       warningMessagesStatus = { ["mail.operations.securityscreeningwarning"]: 'Y' };
       reassignContainer.destination=state.commonReducer.warningDestination!=''?
       state.commonReducer.warningDestination:warningDestination;

   }
   if(isValidation||!uldBarrowCheck){
   if (reassignFilter==='C'||args.warningFlag||!state.commonReducer.weightScaleAvailable ||actualWeight!=0){
    const data = {selectedContainerData,reassignContainer,onwardRouting,warningMessagesStatus};
    const url='rest/mail/operations/containerenquiry/reassignContainer';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleReassignContainerResponse(dispatch, response);
        return response;
    })  
  }
}
}

export function transferContainerToFlightAction(values){
    const {dispatch, getState ,args} = values;
    const state = getState(); 
    let  containerearray = state.form.containerDetails.values&&state.form.containerDetails.values.containerDetails?(state.form.containerDetails.values.containerDetails):[];
   let uldCheck=false;
    if (containerearray.length > 0) {
        for (let item in containerearray) {
            if (containerearray[item].barrowFlag) {
                uldCheck=true;
                 }
        }
    }
    if(args.uldToBarrow||!uldCheck){
    let selectedContainerData=[];
    selectedContainerData=state.containeractionreducer?state.containeractionreducer.selectedContainer:[];

    const selectedFlightIndex=state.containeractionreducer?state.containeractionreducer.selectedFlightIndexReassign:[];

    if(selectedFlightIndex.length!==1){
        return Promise.reject(new Error("Please select one of the flight details"));
    }

    let selectedFlight={};
    selectedFlight=state.containeractionreducer?state.containeractionreducer.flightDetails.results[selectedFlightIndex[0]]:{};

   
    // const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    //const data = {selectedContainerData};
    const transferContainerToFlight  = (state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values:{}
    const containerDetails = state.form.containerDetails?(state.form.containerDetails.values.containerDetails):[];
    let onwardRouting=(state.form.onwardRouting && state.form.onwardRouting.values) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }

    /*transferForm.reassignedto = 'FLIGHT';
    transferForm.assignedto = 'FLIGHT';
    transferForm.flightNumber=flightNumber.flightNumber;
    transferForm.flightDate=flightNumber.flightDate;
    transferForm.flightCarrierCode=flightNumber.carrierCode;*/

    let loggedAirport= state.commonReducer.airportCode;
    const flightRoute = selectedFlight.flightRoute;
    const pous = flightRoute.split("-");
    let pou = '';
    for(let k=0; k<pous.length; k++){
        if(pous[k] === loggedAirport){
            pou = pous[k+1];
            break;
        }
    }

    let transferForm = {}
    transferForm.reassignedto = 'FLIGHT';
    transferForm.assignedto = 'FLIGHT';
    transferForm.flightNumber=selectedFlight.flightNumber;
    transferForm.flightDate=selectedFlight.flightDate;
    transferForm.flightCarrierCode=selectedFlight.carrierCode;
    transferForm.flightSeqNumber=selectedFlight.flightSequenceNumber;
    transferForm.scanDate = transferContainerToFlight.scanDate;
    transferForm.mailScanTime = transferContainerToFlight.mailScanTime;
    if(!containerDetails[0].pou || containerDetails[0].pou === loggedAirport){
        transferForm.flightPou = pou;
    }else{
        transferForm.flightPou = containerDetails[0].pou;
    } 
    
    transferForm.destination = containerDetails[0].destination;

    if(!selectedContainerData || selectedContainerData.length===0){
        selectedContainerData = (state.commonReducer)?state.commonReducer.selectedContainerDataByIndex:{};
    }
    /*reassignContainer.carrierCode=selectedFlight.carrierCode;
    reassignContainer.destination = reassignContainerToFlight.destination;
    reassignContainer.flightDate=selectedFlight.flightDate;
    reassignContainer.flightNumber=selectedFlight.flightNumber;
    reassignContainer.mailScanTime = reassignContainerToFlight.mailScanTime;
    reassignContainer.reassignFilterType= "F";
    reassignContainer.scanDate=reassignContainerToFlight.scanDate;
    reassignContainer.remarks=reassignContainerToFlight.Remarks;


    if (!(reassignContainer.destination)) {
        return Promise.reject(new Error("Please enter Destination"));
       }
       if(!isNaN(reassignContainerToFlight.actualWeight)){
        reassignContainerToFlight.actualWeight= reassignContainerToFlight.actualWeight.toString();
        if(reassignContainerToFlight.actualWeight>0 && reassignContainerToFlight.actualWeight.includes(".")){
        const num = reassignContainerToFlight.actualWeight.split(".")[0];
        const dec = reassignContainerToFlight.actualWeight.split(".")[1];
          if(dec.includes(".")){
             return Promise.reject(new Error("Invalid weight format."));
          }
        }
      }
       else {
            return Promise.reject(new Error("Invalid weight format."));  
       }
      let  actualWeight=0;   
      if(reassignContainerToFlight.actualWeight) {
        actualWeight=parseInt(reassignContainerToFlight.actualWeight);
      }
    if(state.commonReducer.weightScaleAvailable&&args&&!args.warningFlag){
       if(actualWeight==0){
       dispatch(requestWarning([{code:"mail.operations.container.actualweightnotpresent", description:"Actual weight is not captured. Do you want to continue?"}],{functionRecord:reassignContainerToFlightAction, args:{warningFlag:true}}));
       }
   }
   if (args.warningFlag||!state.commonReducer.weightScaleAvailable||actualWeight!=0){
    for(let i=0; i<selectedContainerData.length; i++){
        selectedContainerData[i].remarks = reassignContainer.remarks;
        if(actualWeight==0 && state.containeractionreducer.selectedContainer &&state.containeractionreducer.selectedContainer.actualweight &&state.containeractionreducer.selectedContainer.actualweight !==reassignContainerToFlight.actualWeight){
           selectedContainerData[i].actualWeightUpdated=true; 
        }
        selectedContainerData[i].actualWeight = reassignContainerToFlight.actualWeight;
        selectedContainerData[i].contentId = reassignContainerToFlight.contentId;
    }*/

    for(let i=0; i<selectedContainerData.length; i++){
        for(let j=0; j<containerDetails.length; j++){
            if(selectedContainerData[i].containerNumber === containerDetails[j].containerNumber){
                if(!containerDetails[j].pou || containerDetails[j].pou === loggedAirport){
                    selectedContainerData[i].pou = pou;
                }else{
                    selectedContainerData[i].pou = containerDetails[j].pou;
                }                
                selectedContainerData[i].destination = containerDetails[j].destination;
                selectedContainerData[i].actualWeight = containerDetails[j].actualWeight;
                selectedContainerData[i].contentId = containerDetails[j].contentId;
                selectedContainerData[i].remarks = containerDetails[j].remarks;
				if(args.uldToBarrow&&containerDetails[j].barrowFlag){
                    selectedContainerData[i].type='B';
                    selectedContainerData[i].uldTobarrow=true;
                }
            }
        }
    }
    let screenWarning=false;
    if(args&&args.screenWarning){
        screenWarning= args.screenWarning;
    }
    transferForm.fromScreen='MTK058';
    const data = {selectedContainerData,transferForm, onwardRouting,screenWarning};
    //const data = {selectedContainerData,reassignContainer,onwardRouting};
    const url='rest/mail/operations/containerenquiry/transferContainerSave';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handlevalidateContainerTransferSaveResponse(dispatch, response);
        return response;
    })  
}
else{
    dispatch(requestWarning([{ code: "mail.operations.container.uldwarning", description: "The selected ULD container will be converted as Bulk. Do you want to continue?" }], { functionRecord: transferContainerToFlightAction, args: { warningFlag: true } }));                
          
}
  }
  export function releaseContainer(values) {  
    const {dispatch, getState } = values;
        const state = getState();   
         let selectedContainerData=[]
        const indexes = state.commonReducer.selectedMailbagIndex;
    
         for(var i=0; i<indexes.length;i++) {
             selectedContainerData.push(state.listcontainerreducer.containerDetails.results[indexes[i]]);
         }
         if(selectedContainerData.length===0){
             selectedContainerData = (state.containeractionreducer)?state.containeractionreducer.selectedContainerForRelease:[];
         }
        const data = {selectedContainerData};
        const url='rest/mail/operations/containerenquiry/releaseContainer';
        return makeRequest({
            url,data: {...data}
        }).then(function(response) {
            handleReleaseContainerResponse(dispatch, response);
            return response;
        })
    
    }
    export function handleReleaseContainerResponse(dispatch,response) {
        if(response.status==='success')  
        dispatch(validateContainerForRelease(response.status));   
    }
            
    export function validateContainerForRelease(data){   
       return { type: 'RELEASE_SUCCESS', data };
    }