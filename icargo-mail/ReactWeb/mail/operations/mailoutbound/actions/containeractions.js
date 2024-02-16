import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { change } from 'icoreact/lib/ico/framework/component/common/form';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { listmailbagsinContainers,listContainersinFlight} from './flightlistactions.js';
import {listDetails} from './filteraction.js';
import { applyCarditFilter } from './carditaction.js';
import {applyLyingListFilter} from './lyinglistaction.js'
import { applyDeviationListFilter } from './deviationlistaction'
import { reset } from 'redux-form';
import * as constant from '../constants/constants';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions'

export function checkCloseFlight(values) {
	const {getState,dispatch,args} = values;
    const state = getState();
    let data={}
    let url = '';
    let mode=args?args.mode :'';
    let mailAcceptance='';
    let selectedContainer={}
    let containerDetailsCollection=[];
    let existingMailbags=[];
    let actionType=null;
    let indexes=[];
  if(args.actions=='CONTAINER_LEVEL') {
    if(args.index) {
        indexes.push(args.index);
        selectedContainer = state.containerReducer.flightContainers.results[args.index];
        if(mode===constant.ADD_CONTAINER){
            selectedContainer={selectedContainer:null,mailbagpagelist:null,totalWeight:null}
         }else{ 
            selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null}       
         }
    }
    else {
        if(mode!=constant.ADD_CONTAINER){
         indexes=state.containerReducer.selectedContainerIndex;
        }
       
    }
    for(var i=0; i<indexes.length;i++) {
       
            containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
           
    }
   }
    const flightCarrierflag=state.commonReducer.flightCarrierflag
    
    if(flightCarrierflag==='F') {
        mailAcceptance= state.containerReducer.mailAcceptance
    }
    if(args.actions=='MAILBAG_LEVEL') { 
     selectedContainer=state.containerReducer.selectedContainer
     if(mode===constant.ADD_CONTAINER){
        selectedContainer={selectedContainer:null,mailbagpagelist:null,totalWeight:null}
     }else{ 
        selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null}       
     } 
     containerDetailsCollection.push(selectedContainer);
    }
    
    if(mode===constant.MODIFY_MAILBAG){
        actionType=constant.MODIFY_MAILBAG;
        if(args.index) {
            indexes.push(args.index);
           
        }
        else {
            indexes=state.mailbagReducer.selectedMailbagsIndex;
           
        }
        
        for(var i=0; i<indexes.length;i++) {
            existingMailbags.push(state.mailbagReducer.mailBags.results[indexes[i]]);
            
        }

    }
    
    
   url = 'rest/mail/operations/outbound/checkCloseFlight';
   data={mailAcceptance : {...mailAcceptance ,containerPageInfo:null},containerDetailsCollection,flightCarrierflag,existingMailbags,actionType:mode}

    return makeRequest({
    url,
    data: {...data}
}).then(function (response) {
    if(mode===constant.MODIFY_MAILBAG){
        handleResponseForModify(dispatch,response,constant.CHECK_MODIFIED_MAIL);
    }
    handleResponse(dispatch,response,constant.CHECK_CLOSE_FLIGHT);
    return response
})
.catch(error => {
    return error;
});

}


export function performContainerAction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let indexes=[];
    let data={}
    let url = '';
    let mode=args.mode;
    let mailAcceptance=''
    let selectedContainer = '';
    const containerDetailsCollection=[];
    let selectedContainerData=[];
    const flightCarrierflag=state.commonReducer.flightCarrierflag
    let multipleFlag=args.multipleFlag;
    if(args.index) {
        indexes.push(args.index);
        selectedContainer = state.containerReducer.flightContainers.results[args.index];
       
    }
    else {
        indexes=state.containerReducer.selectedContainerIndex;
       
    }
    
    
    for(var i=0; i<indexes.length;i++) {
       
            containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
            selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
       
    }
    let containerDetailsinReducer=state.containerReducer.selectedContainer;
    mailAcceptance= state.containerReducer.mailAcceptance
    
    if(mode===constant.ADD_CONTAINER){
        const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,constant.ADD_CONTAINER)
                    return response;
                })
                .catch(error => {
                    return error;
                });
    }
    else if(mode===constant.DELETE_CONTAINER){
    
        const url = 'rest/mail/operations/outbound/deletecontainer';
        const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null},containerInfo:selectedContainer}
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,constant.DELETE_CONTAINER)
                    return response;
                })
                .catch(error => {
                    return error;
                });
    }
  else  if(mode===constant.MODIFY) {
    
    data={mailAcceptance : {...mailAcceptance ,flightCarrierflag,containerPageInfo:null},containerDetailsCollection}
    url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseOnModify(dispatch,response,selectedContainer,containerDetailsinReducer,constant.MODIFY);
        return response
    })
    .catch(error => {
        return error;
    })

  }
  else if(mode===constant.REASSIGN || mode===constant.TRANSFER) {
    
    data={selectedContainerData,mode}
    url = 'rest/mail/operations/containerenquiry/validateContainerForReassign';
    return makeRequest({
        url,
        data: {...data,mode}
    }).then(function (response) {
		selectedContainerData = response&&response.results&&response.results[0].selectedContainerData;
        handlevalidateContainerResponse(dispatch, response,selectedContainerData, mode,selectedContainer,multipleFlag);
        //handleResponseOnReassign(dispatch,response,selectedContainerData,'REASSIGN');
        return response
    })
    .catch(error => {
        return error;
    });
    
  }
  else if(mode===constant.REASSIGN_FLIGHT||mode==constant.TRANSFER_FLIGHT) {
    
    data={selectedContainerData,mode}
    url = 'rest/mail/operations/containerenquiry/validateContainerForReassign';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        selectedContainerData = response&&response.results&&response.results[0].selectedContainerData;
        handlevalidateContainerResponse(dispatch, response,selectedContainerData, mode,selectedContainer,multipleFlag);
        //handleResponseOnReassign(dispatch,response,selectedContainerData,'REASSIGN');
        return response
    })
    .catch(error => {
        return error;
    });
    
  }
  else if(mode===constant.UNASSIGN) {
    
    data={selectedContainerData}
    url = 'rest/mail/operations/containerenquiry/unassignContainer';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch,response,constant.UNASSIGN);
        return response
    })
    .catch(error => {
        return error;
    });
    
  }
  else if(mode===constant.ATTACH_AWB) {
    data={containerDetailsCollection, fromContainerTab:true}
    url = 'rest/mail/operations/outbound/screenloadattachawb';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch,response,constant.ATTACH_AWB);
        return response
    })
    .catch(error => {
        return error;
    });
    
  }
  else if(mode===constant.DETACH_AWB) {
    let containerDetailsCollection=[]
    let selectedContainer=state.containerReducer.selectedContainer
    selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null,
        mailBagDetailsCollection:state.mailbagReducer.allMailBags!=null?state.mailbagReducer.allMailBags:null}
        containerDetailsCollection.push(selectedContainer);
    data={containerDetailsCollection}
    url = 'rest/mail/operations/outbound/detachAWBCommand';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch,response,constant.DETACH_AWB);
        return response
    })
    .catch(error => {
        return error;
    });
  }
  else if(mode===constant.ATTACH_ROUTING) {
    
    data={mailAcceptance : {...mailAcceptance , containerPageInfo:null},containerDetailsCollection}
    url = 'rest/mail/operations/outbound/validateAttachRouting';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch,response,constant.ATTACH_ROUTING);
        return response
    })
    .catch(error => {
        return error;
    });
    
  }



}

export const onListContainer=(values)=> {
    const {dispatch, getState } = values;
    const state = getState();
    let mailAcceptance={};
    let url='';
    const newContainerInfo = state.form.addcontainerForm.values;
    if(newContainerInfo.barrowFlag) {
        newContainerInfo.type='B';
    } else {
        newContainerInfo.type='U';
    }
    if(newContainerInfo.paBuiltFlagValue)
    newContainerInfo.paBuiltFlag = 'Y';
    else
    newContainerInfo.paBuiltFlag = 'N';
     
    const flightCarrierflag=state.commonReducer.flightCarrierflag
    mailAcceptance= state.containerReducer.mailAcceptance
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
    const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null},newContainerInfo,flightCarrierflag,warningMessagesStatus}
    url = 'rest/mail/operations/outbound/onListNewContainer';
  
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,constant.LIST_NEW_CONTAINER);
        return response
    })
    .catch(error => {
        return error;
    });
}
export function handleResponse(dispatch,response,mode) {
    
    let mismatchInPieceOrWeightStatus=false;
    if (response.errors!=null && mode === constant.ATTACH_AWB_LIST) {
    let errors=response.errors.ERROR;
    for(var i=0; i<errors.length;i++) {
        if( errors[i].code== 'mailtracking.defaults.attachawb.msg.err.invalidshipmentpieceorweight'){
            mismatchInPieceOrWeightStatus=true;
        }
    }   
    }
    if(response.errors==null||mismatchInPieceOrWeightStatus==true){
       
        if(mode===constant.ADD_CONTAINER || mode===constant.MODIFY||mode===constant.DELETE_CONTAINER) {
            let {mailAcceptance,oneTimeValues} = response.results[0];
            dispatch( { type: constant.SCREENLOAD_ADD_MODIFY_CONTAINER, mailAcceptance,oneTimeValues,mode
            //,selectedContainer,mode
        });
        }
        else if(mode===constant.LIST_NEW_CONTAINER) {
            const warningMapValue = { ['mail.operations.warning.uld']: 'N' };
            dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
            let {selectedContainer} = response.results[0];
            if(selectedContainer.type === 'B') {
                selectedContainer.barrowFlag=true;
            }
            else {
                selectedContainer.barrowFlag=false;
            } 
            if(selectedContainer.paBuiltFlag==='Y'||selectedContainer.paBuiltFlag==='true') {
                selectedContainer.paBuiltFlagValue=true;
            }
            else {
                selectedContainer.paBuiltFlagValue=false;
            }  
           dispatch( { type: constant.LIST_NEW_CONTAINER_SUCESS,selectedContainer});
        }
       
        else if(mode===constant.UNASSIGN) {
        if(response.status==='success')  
          dispatch(validateUnassignContainer(response.status));   
        }
        else if(mode === constant.ATTACH_AWB) { 
            dispatch({type:  constant.ATTACH_AWB,attachClose:true,attachAwbDetails:response.results[0].attachAwbDetails,
                        containerDetailsToBeReused:response.results[0].containerDetailsCollection,attachAwbOneTimeValues:response.results[0].oneTimeValues});
        }
        else if(mode === constant.DETACH_AWB){
            dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                dispatch(asyncDispatch(listmailbagsinContainers)());
              });
        }

        else if(mode === constant.ATTACH_AWB_LIST) { 
            dispatch({type: constant.ATTACH_AWB_LIST,attachAwbDetails:response.results[0].attachAwbDetails,attachConClose:true,
            containerDetailsToBeReused:response.results[0].containerDetailsCollection});
        }
        else if(mode===constant.ATTACH_ROUTING_SAVE){
            if(response.status==="success"){
                dispatch({type: constant.ATTACH_ROUTING_SAVE});
               } 
               else {
                dispatch({type: constant.ATTACH_ROUTING_ERROR}); 
            }
        }
        else if(mode === constant.ATTACH_AWB_SAVE) { 

            dispatch({type: constant.ATTACH_AWB_SAVE,attachConClose:false,attachClose:false});
        }
        else if (mode === constant.ATTACH_ROUTING) {
            if(response.status==="success"){
            dispatch({type: constant.ATTACH_ROUTING, attachRoutingClose: true, attachRoutingDetails:response.results[0].attachRoutingDetails,oneTimeValues:response.results[0].oneTimeValues,
                         containerDetailsToBeReused:response.results[0].containerDetailsCollection,createMailInConsignmentVOs: response.results[0].createMailInConsignmentVOs,});
           } 
           else {
            dispatch({type: constant.ATTACH_ROUTING_ERROR}); 
        }  
        }
        else if (mode === constant.ATTACH_ROUTING_LIST) {
            if(response.status==="success"){
            dispatch({type: constant.ATTACH_ROUTING_LIST, attachRoutingClose: true, attachRoutingDetails:response.results[0].attachRoutingDetails,
                         createMailInConsignmentVOs: response.results[0].createMailInConsignmentVOs,oneTimeValues:response.results[0].oneTimeValues});
           } 
           else {
            dispatch({type: constant.ATTACH_ROUTING_ERROR}); 
        }  
        }
        else if(mode===constant.CHECK_CLOSE_FLIGHT) {

        }
        
    }
    
}

export function handleResponseOnModify(dispatch,response,selectedContainer,containerDetailsinReducer,mode) {
    
    let isBarrow = false;
    let modifiedMailbags=[];
    if(!isEmpty(response.results)){
        if(mode===constant.MODIFY) {
            if(selectedContainer.type === 'B') {
                isBarrow = true;
                selectedContainer.barrowFlag=true;
            }
            else {
                selectedContainer.barrowFlag=false;
            } 
            if(selectedContainer.paBuiltFlag==='Y') {
                selectedContainer.paBuiltFlagValue=true;
            }
            else {
                selectedContainer.paBuiltFlagValue=false;
            } 
            modifiedMailbags=response.results[0].mailbags;
            const {mailAcceptance,oneTimeValues,uldToBarrowAllow,currentDate,currentTime} = response.results[0];
            dispatch( { type: constant.SCREENLOAD_ADD_MODIFY_CONTAINER, mailAcceptance,oneTimeValues,selectedContainer,mode	,uldToBarrowAllow,currentDate,currentTime, isBarrow,modifiedMailbags
        });
        let destination=containerDetailsinReducer.destination;
                dispatch(change(constant.ADD_CONTAINER_FORM,'destination',destination)); 
        }
   }
}


export const onLoadAddModifyContPopup=(values)=> {
    const { dispatch, getState, args} = values;
    const state = getState();
    let mailAcceptance=''
    mailAcceptance= state.containerReducer.mailAcceptance
    let mode = args.mode;
  //  mailFlight.containerPageInfo=null;
    const data={mailAcceptance : {...mailAcceptance , containerPageInfo:null}}
    const url = 'rest/mail/operations/outbound/screenloadAddModifyContainer';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseOnScreenload(dispatch, response,mode);
        return response
    })
    .catch(error => {
        return error;
    });
 
      
}

function handleResponseOnScreenload(dispatch,response,selectedContainer,mode) {
    
    if(!isEmpty(response.results)){
       if(mode===constant.ADD_CONTAINER || mode===constant.MODIFY) {
        const {mailAcceptance,oneTimeValues} = response.results[0];
        dispatch( { type: constant.SCREENLOAD_ADD_MODIFY_CONTAINER, mailAcceptance,oneTimeValues,selectedContainer,mode});
        }
        
        if(!isEmpty(response.errors)){
            // dispatch( { type: 'CLEAR_TABLE'});
        }
   }
}



export const onSaveContainer=(values)=> {
    const {args,dispatch, getState } = values;
    let paBuiltFlagUpdate=false;
    let paContainerNumberUpdate=false;
    if(args.paBuiltFlagUpdate) {
        paBuiltFlagUpdate=args.paBuiltFlagUpdate;
        
    }
    if(args.paContainerNumberUpdate) {
     paContainerNumberUpdate=args.paContainerNumberUpdate;
    }
    const state = getState();
    let mailAcceptance='';
    let url='';
    let data={}
    let mailbags = [];
   let containerActionMode=state.containerReducer.containerActionMode;
    let newContainerInfo = state.form.addcontainerForm.values;
    const flightCarrierflag=state.commonReducer.flightCarrierflag;
    //Added by A-7929 as part of ICRD-342117 starts....
    const pabuildFlag = newContainerInfo.paBuiltFlagValue;
    if(pabuildFlag)
    newContainerInfo.paBuiltFlag = 'Y';
    else
    newContainerInfo.paBuiltFlag = 'N';
     //Added by A-7929 as part of ICRD-342117 ends....
    if(newContainerInfo.barrowFlag) {
        newContainerInfo.type='B';
    } else {
        newContainerInfo.type='U';
    }

    if(newContainerInfo.type ==='B' && newContainerInfo.paBuiltFlag === 'Y'){
        return Promise.reject(new Error("Barrows cannot be PA built")); 
    }
	if(newContainerInfo.paBuiltFlag === 'Y' && newContainerInfo.paCode===null){
		return Promise.reject(new Error("PA code mandatory for PA Built container")); 
	}
    
    let selectedContainer= state.containerReducer.selectedContainerforModify? state.containerReducer.selectedContainerforModify:'';
     let flightCarrierFilter = {};
    flightCarrierFilter.assignTo = state.commonReducer.flightCarrierflag
    mailAcceptance= state.containerReducer.mailAcceptance
   //  if(containerActionMode === 'ADD_CONTAINER') {
    //    data={mailAcceptance,newContainerInfo,flightCarrierFilter}
    //    url = 'rest/mail/operations/outbound/onAddContainer';
   // }
  if(containerActionMode === 'MODIFY') {
    if( state.commonReducer.flightCarrierflag==='F'&&(newContainerInfo.type==='B')&& newContainerInfo.destination!= newContainerInfo.pou){
        return Promise.reject(new Error("POU and destination should be same"));    
    }  
    if(selectedContainer.paCode!= newContainerInfo.paCode) {
        paBuiltFlagUpdate=true;
    }
   
    let mailbagslist =[]
 
      mailbagslist=state.containerReducer.modifiedMailbags

      
       if(!isEmpty(mailbagslist)) { 
        if(paContainerNumberUpdate) {
            // Pa-built container changed to non pa-built container and warning show up. User selects NO in the warning popup
            mailbagslist=mailbagslist.map((value)=>({ ...value,carrier:state.form.addcontainerForm.values.transferFromCarrier,operationFlag: 'U',paCode: newContainerInfo.paBuiltFlag =="Y" ? newContainerInfo.paCode:value.paCode,paContainerNumberUpdate:true,paBuiltFlagUpdate:paBuiltFlagUpdate,acceptancePostalContainerNumber:null})); 
        }
        else {
                mailbagslist=mailbagslist.map((value)=>({ ...value,carrier:state.form.addcontainerForm.values.transferFromCarrier,operationFlag: 'U',paCode: newContainerInfo.paBuiltFlag=="Y" ? newContainerInfo.paCode:value.paCode,paBuiltFlagUpdate:paBuiltFlagUpdate,paContainerNumberUpdate:true,acceptancePostalContainerNumber:newContainerInfo.containerNumber})); 
        }
       mailbags.push(...mailbagslist);
      }
      //the container pacode need to be changed only if the pa built checkbox is checked. IASCB-100612
      newContainerInfo={...newContainerInfo,paCode:newContainerInfo.paBuiltFlag=="Y" ? newContainerInfo.paCode : selectedContainer.paCode}
     
    }
        let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
        selectedContainer={...selectedContainer,mailbagdsnviewpagelist:null,mailbagpagelist:null}
        data={mailAcceptance,newContainerInfo,selectedContainer,flightCarrierflag,mailbags,warningMessagesStatus}
        url='rest/mail/operations/outbound/onAddContainer'
   // }
     
    
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseonVerify(dispatch, response, newContainerInfo);
        return response
    })
    .catch(error => {
        return error;
    });
}




function handleResponseonVerify(dispatch,response, newContainerInfo) {
    
    if (response.status==='success') {
       dispatch( { type: constant.ADD_CONTAINER_POPUPCLOSE});
    } else {
        if(newContainerInfo.paBuiltFlag==='Y') {
            newContainerInfo.paBuiltFlag=true;
            }
            else {
                newContainerInfo.paBuiltFlag=false;
            }
    }
 
}



export const clickPABuilt = (values) => {
    const { args, dispatch } = values;
    if(args == true) {
    dispatch({ type: constant.PABUILT_FLAG, data: true });
    } else {
         dispatch({ type: constant.PABUILT_FLAG, data: false });
    }
}

export const populateDestForBarrow = (values) => {
    const { args, dispatch,getState } = values;
    const state = getState();
    if(state.form.addcontainerForm.values.barrowFlag === true)
    dispatch(change(constant.ADD_CONTAINER_FORM,'destination', args.dest));
}

export const populateDestForBULKContainer =(values)=>{
    const { args, dispatch ,getState} = values;
     const state = getState();
     let item=state.containerReducer.selectedContainerforModify;
    
     if(state.commonReducer.flightCarrierflag==='F'){
    if(args.barrowCheck){
        item.destination=args.dest;
    dispatch(change(constant.ADD_CONTAINER_FORM,'destination', args.dest)); 
    } else {
     let destination=state.containerReducer.selectedContainer.destination;
     item.destination=destination;
     dispatch(change(constant.ADD_CONTAINER_FORM,'destination',destination)); 
    }
}
//item.barrowFlag=args.barrowCheck;
dispatch( { type: constant.CONTAINER_MODIFY, item});
}
export const selectContainers=(values)=> {
    const { args, dispatch} = values;
 //   const flightCarrierFilter  = (state.form.outboundFilter.values)?state.form.outboundFilter.values:{}
 //   const flightNumber=(state.form.outboundFilter.values) ? state.form.outboundFilter.values.flightnumber:{};
//    if(!isEmpty(flightNumber)) {
 //     flightCarrierFilter.flightNumber=flightNumber.flightNumber;
 //     flightCarrierFilter.flightDate=flightNumber.flightDate;
 //   }
   const containerDetails= args[0];
   const mailbags =containerDetails.mailDetails
   dispatch( { type: constant.LIST_MAILBAGS, mailbags});
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});
      
}

//integrated actions


export function unassignContainer(values) {    
    const {args, dispatch, getState } = values;
    const state = getState();    
    let indexes=[]; 
    // let selectedContainer = '';
    const containerDetailsCollection=[];
    let selectedContainerData=[];
    if(args.index) {
        indexes.push(args.index);
      
        // selectedContainer = state.containerReducer.flightContainers.results[args.index];
      
    }
    else {
        indexes=state.containerReducer.selectedContainerIndex;
       
    }
    
    
    for(var i=0; i<indexes.length;i++) {
        
            containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
            selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
       
    }
  //  const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/unassignContainer';
    return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleUnassignContainerResponse(dispatch, response);
        return response;
    })   
}

export function handleScreenloadResponse(dispatch, response) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));
       
    }
}

export function handlevalidateContainerResponse(dispatch, response,selectedContainerData, mode,selectedContainer,multipleFlag) {
    if (response.status==='success') {
        const scanDate = response.results?response.results[0].reassignContainer.scanDate:'';
        const scanTime = response.results?response.results[0].reassignContainer.mailScanTime:'';
        const reassignFromDate = response.results?response.results[0].reassignContainer.fromDate:''; 
        const reassignToDate = response.results?response.results[0].reassignContainer.reassignToDate:'';
        if(selectedContainerData.length>0){
            for(let i=0;i< selectedContainerData.length;i++){
            if(selectedContainerData[i].type === 'B') {
                selectedContainerData[i].barrowFlag=true;
            }
            else {
                selectedContainerData[i].barrowFlag=false;
            } 
            if(selectedContainerData[i].paBuiltFlag==='Y') {
                selectedContainerData[i].isPaBuilt=true;
            }
            else {
                selectedContainerData[i].isPaBuilt=false;
            } 
        }
        }
        if(mode===constant.REASSIGN||mode===constant.TRANSFER){
            dispatch(validateContainerSuccess(response.status,selectedContainerData,scanDate, scanTime,mode));
        }else if(mode===constant.REASSIGN_FLIGHT||mode===constant.TRANSFER_FLIGHT){
        
            dispatch(validateContainerSuccessReassignFlight(response.status,selectedContainerData,scanDate, scanTime,reassignFromDate,reassignToDate,selectedContainer,multipleFlag,mode));
        }
               
    }
}

export function validateContainerSuccess(data,selectedContainerData,scanDate, scanTime,mode) {
    if(mode===constant.REASSIGN){
    return { type: constant.VALIDATECONTAINER_SUCCESS, selectedContainerData,scanDate, scanTime,reassignFilter:'C' };
    }
    else{
    return { type: constant.VALIDATEFORTRANSFER_SUCCESS, selectedContainerData ,scanDate, scanTime};
    }
}

export function validateContainerSuccessReassignFlight(data,selectedContainerData,scanDate, scanTime,reassignFromDate,reassignToDate,selectedContainer,multipleFlag,mode) {
   if(mode===constant.REASSIGN_FLIGHT){
    return { type: constant.VALIDATE_CONTAINER_REASSIGN_FLIGHT_SUCCESS, selectedContainerData,scanDate, scanTime ,reassignFromDate,reassignToDate,selectedContainer,multipleFlag,reassignFilter:'F'};
   }
   else{ 
    return { type: constant.VALIDATE_CONTAINER_TRANSFER_FLIGHT_SUCCESS, selectedContainerData,scanDate, scanTime ,reassignFromDate,reassignToDate,selectedContainer,multipleFlag,reassignFilter:'F'};
   }
}

export function screenLoadSuccess(data) {
    return { type: constant.SCREENLOAD_SUCCESS, data };
}

export function handleUnassignContainerResponse(dispatch,response) {
    if(response.status==='success')  
    dispatch(validateUnassignContainer(response.status));   
}
export function validateUnassignContainer(data){   
   return { type: constant.UNASSIGN_SUCCESS, data };
}

export const selectContainerssss=(values)=> {
   const {dispatch} = values;
   dispatch( { type: constant.SELECT_CONTAINER});      
}


export function handleReassignContainerResponse(dispatch,response){
    if(response.status=="reassign_success"){
        dispatch(reassignContainerSuccess(response.status));
        dispatch( {type:constant.CONTAINER_CONVERSION,uldToBarrow:false,barrowToUld:false})
        const warningMapValue = { ['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
    }
}

export function reassignContainerSuccess(data){
    return {type:constant.REASSIGN_SUCCESS, data};
}

//attach

export function listAwbDetailsAction(values){
    const {dispatch, getState } = values;
    const state = getState();
    let containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
    let attachAwbDetails=state.containerReducer?state.containerReducer.attachAwbDetails:null;
    const documentNumber=(state.form.attachAwbDetails.values.masterDocumentNumber)?
    state.form.attachAwbDetails.values.masterDocumentNumber:null;
    const shipmentPrefix=(state.form.attachAwbDetails.values.shipmentPrefix)?
    state.form.attachAwbDetails.values.shipmentPrefix:null;
    attachAwbDetails={...attachAwbDetails,masterDocumentNumber:documentNumber,shipmentPrefix:shipmentPrefix}
    const data={containerDetailsCollection,attachAwbDetails};

    const action=constant.ATTACH_AWB_LIST;
    const url = 'rest/mail/operations/outbound/listattachawb';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });


}
export function clearAWB(values){
    const {dispatch, getState } = values;
    const state = getState();
    let attachAwbDetails=state.form.attachAwbDetails.values?state.form.attachAwbDetails.values:null;
    attachAwbDetails.masterDocumentNumber='';
    dispatch({type: constant.CLEAR_AWB,attachAwbDetails});
    
}
export function clearAttachRouting(values){
    const {dispatch, getState } = values;
    const state = getState();
    let attachRoutingForm=state.form.attachRoutingForm.values?state.form.attachRoutingForm.values:null;
    attachRoutingForm.consignemntNumber='';
    attachRoutingForm.paCode='';
    let routingDetail=state.containerReducer.attachRoutingDetails?state.containerReducer.attachRoutingDetails:null;
    routingDetail.consignemntNumber='',
    routingDetail.paCode='',
    routingDetail.consignmentDate='';
    routingDetail.consignmentType='';
    routingDetail.remarks='';
  
    dispatch({type: constant.CLEAR_ATTACH_ROUTING,attachRoutingForm,routingDetail});
    
}

export function saveAwbDetailsAction(values){
    const {dispatch, getState } = values;
    const state = getState();
    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
      const attachAwbDetails=state.containerReducer?state.containerReducer.attachAwbDetails:null;
   // let containerDetailsVosIterate=(state.containerReducer)?state.containerReducer.arrivalDetailsInVo.containerDetails:{};
   // const containerNumber=containerDetailsCollection?containerDetailsCollection[0].containerno:'';
   // const containerDetailsVos=[];
   // let containerDetailsVo=null;
   // containerDetailsVosIterate.forEach(function(element) {
    //    if(element.containerNumber===containerNumber){
    //        containerDetailsVo=element;
    //    }
    //}, this);
    //containerDetailsVos.push(containerDetailsVo);
	const activeTab = state.mailbagReducer&&state.mailbagReducer.activeMailbagTab;
	const data={containerDetailsCollection,attachAwbDetails,activeTab}; 

    const action=constant.ATTACH_AWB_SAVE;
    const url = 'rest/mail/operations/outbound/saveattachawb';
     return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch({type:constant.ERROR_SHOW});
                        return response;
                    }
                    else 
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });
                

}


export function listAttachRoutingAction(values){
    
    const {dispatch, getState } = values;
    const state = getState();
     
    const attachRoutingDetails=(state.form.attachRoutingForm)?
                                    state.form.attachRoutingForm.values:null;
    const createMailInConsignmentVOs=state.containerReducer?(state.containerReducer.createMailInConsignmentVOs):{};
    const data={attachRoutingDetails:attachRoutingDetails,createMailInConsignmentVOs:createMailInConsignmentVOs}
    const url = 'rest/mail/operations/outbound/listAttachRouting';
    // const actionType=constant.ATTACH_ROUTING_LIST;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,constant.ATTACH_ROUTING_LIST)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

}

export function saveAttachRoutingAction(values){

    const { dispatch, getState } = values;
    const state = getState();
    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};

    let attachRoutingDetails=(state.form.attachRoutingForm)?
                                    state.form.attachRoutingForm.values:null;
    let onwardRouting=(state.form.onwardRouting)?
                                    state.form.onwardRouting.values.onwardRouting:{};
    let isAvailable = false;
    if (onwardRouting) {
        for (var i = 0; i < onwardRouting.length; i++) {
            if (onwardRouting[i].carrierCode &&onwardRouting[i].carrierCode != ('' || null)) {
                isAvailable = true;
            }
        }
    }
    if(!isAvailable){
        onwardRouting={};
    }
    let deletedRoutingDetails=(state.form.onwardRouting)?
                                    state.form.onwardRouting.values.deleted:{};
    let onwardRoutingSelected=[];
    if(onwardRouting&& onwardRouting.length>0){
    onwardRouting.forEach(function(element) {
        if((element.carrierCode+element.flightNumber+element.flightDate+element.origin+
                element.destination)!=null){
            onwardRoutingSelected.push({...element,operationFlag:element.__opFlag,operationalStatus:element.__opFlag});
        }
    }, this);
}
    if(!isEmpty(deletedRoutingDetails)) {
        deletedRoutingDetails.forEach(function(element) {
            onwardRoutingSelected.push({...element,operationFlag:'D',operationalStatus:'D'});       
        }, this);
    }
    attachRoutingDetails={...attachRoutingDetails,onwardRouting:onwardRoutingSelected};
    // const consignmentDocumentVO=(state.containerReducer)?state.containerReducer.consignmentDocumentVO:[];
    const data={attachRoutingDetails,containerDetailsCollection}
    const url = 'rest/mail/operations/outbound/saveAttachRouting';
    const actionType=constant.ATTACH_ROUTING_SAVE;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

}

export function reassignContainerAction(values){
    const {dispatch, getState } = values;
    const state = getState(); 
    const filterType=(state.form.reassignContainer.values) ? state.form.reassignContainer.values.reassignFilterType:{};
    let selectedContainerData=[];
    selectedContainerData=state.containerReducer?state.containerReducer.containerDetailsToBeReused:[];

   
    // const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    //const data = {selectedContainerData};
    const reassignContainer  = (state.form.reassignContainer.values)?state.form.reassignContainer.values:{}
    const flightNumber=(state.form.reassignContainer.values) ? state.form.reassignContainer.values.flightnumber:{};
    let onwardRouting=(state.form.onwardRouting && state.form.onwardRouting.values) ? state.form.onwardRouting.values.onwardRouting:[];

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
    for(let i=0; i<selectedContainerData.length; i++){
        selectedContainerData[i].remarks = reassignContainer.remarks;
    }
    if (!(reassignContainer.destination)) {
        return Promise.reject(new Error("Please enter Destination"));
       }
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
    const data = { selectedContainerData, reassignContainer, onwardRouting ,warningMessagesStatus};
    const url='rest/mail/operations/containerenquiry/reassignContainer';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handleReassignContainerResponse(dispatch, response);
        return response;
    })   
}


//On clicking container filter popup
/*export function onApplyContainerFilter(values) {
    const {dispatch, getState } = values;
    const state = getState();
  //  const {displayPage,action} = args;
    const containerTableFilter  = (state.form.containerFilter.values)?state.form.containerFilter.values:{}
    dispatch( { type: constant.LIST_CONTAINER_FILTER,containerTableFilter});
    return Object.keys(containerTableFilter).length;
   
}

//On clearing container filter popup
export function onClearContainerFilter(values){
    const {dispatch} = values;  
    dispatch({ type: constant.CLEAR_CONTAINER_FILTER});
    dispatch(reset('containerFilter'));
      
    
}*/
//to sort container details
export function updateContainerSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: constant.UPDATE_CONTAINER_SORT_VARIABLE, data: data.args })

}
export function validateToAssign(rowIndex,activeTab,carditlist,lyinglists,carditIndexes,lyinglistindexes) {
   let mailbags=[];
   let valid= true;
   if(activeTab===constant.CARDIT_LIST) {
      for(var i=0; i<carditIndexes.length;i++) {
          let index=carditIndexes[i];
          mailbags.push(carditlist.results[index]);
      }
   }
   else {
    for(var i=0; i<lyinglistindexes.length;i++) {
        let index=lyinglistindexes[i];
        mailbags.push(lyinglists.results[index]);
    } 
   }
   mailbags = mailbags.filter((value) => {if(value.accepted=== 'Y') return value});
      
  if (mailbags.length > 0) {
      valid=false;
  }
  return valid;
}

export function assignCarditMails(values) {
    const {dispatch, getState, args } = values;
    let state  = getState();
    let mailbags=[];
    if(state.commonReducer.activeMainTab ===constant.CARDIT_LIST) {
        let selectedCarditMailbags=state.carditReducer.selectedCarditIndex;
        for(var i=0;i<selectedCarditMailbags.length;i++) {
            let index=selectedCarditMailbags[i];
            mailbags.push(state.carditReducer.carditMailbags.results[index]);
        }
    } else if(state.commonReducer.activeMainTab === 'DEVIATION_LIST') {
        let selectedDeviationList=state.deviationListReducer.selectedDeviationList;
        let deviationlistMailbags = state.deviationListReducer.deviationlistMailbags.results ? state.deviationListReducer.deviationlistMailbags.results : [];
        for(var i=0;i<selectedDeviationList.length;i++) {
            const found = deviationlistMailbags.find(element => element.mailSequenceNumber == selectedDeviationList[i]);
            if(found) {
                mailbags.push(found);
            }      
        }
    } else {
        let selectedLyingMailbags=state.lyingListReducer.selectedLyinglistIndex;
        for(var i=0;i<selectedLyingMailbags.length;i++) {
            let index=selectedLyingMailbags[i];
            mailbags.push(state.lyingListReducer.lyinglistMailbags.results[index]);
        }
    }
    //check mail conatins atleast one not accepted mailbags
    let conatinsNotAcceptedMailBags = false;
    for(var i=0;i<mailbags.length;i++) 
    {
        if(mailbags[i].accepted!=='Y') {
            conatinsNotAcceptedMailBags  = true;
            break;
        }
    }
    if(conatinsNotAcceptedMailBags) {
        dispatch(dispatchAction(updateScanTime)({...args}))
    } else {
        dispatch(dispatchAction(assignToContainers)({...args}))
    }
    
}

export function updateScanTime(values) {
    const {dispatch,args } = values;
    dispatch({ type: constant.ASSIGN_SCAN_TIME_POPUP_OPEN, data: args })
}

export function closeAssignScanTimePopup(values) {
    const {dispatch } = values;
    dispatch({ type: constant.ASSIGN_SCAN_TIME_CLOSE })
}

export function continueAssignToContainer(values) {
    const { dispatch, getState,args } = values;
    const state = getState();
    let assignData = state.carditReducer.assignData
    if(state.form && state.form.scanTimeForm) {
        let scannedDate=state.form.scanTimeForm.values.scanDate?state.form.scanTimeForm.values.scanDate:''
        let scannedTime=state.form.scanTimeForm.values.scanTime?state.form.scanTimeForm.values.scanTime: ''  
        if(scannedDate.trim().length >0 && scannedTime.trim().length>0) {
            assignData.scannedDate = scannedDate.trim();
            assignData.scannedTime = scannedTime.trim();
        } else {
            //added as part of IASCB-48444
            if(state.commonReducer.mandateScanTime === true) {
                dispatch(requestValidationError(' Scan date and time is mandatory', ''));
                return;
            }       
        }
            
    }
    dispatch({ type: constant.ASSIGN_SCAN_TIME_CLOSE })
    //timer added to fix IASCB-42552
    setTimeout(()=> {
        dispatch(dispatchAction(assignToContainers)({...assignData}))
    },1000)
    //
    
}
export function assignToContainers(values) {
    const {dispatch, getState,args } = values;
    const state = getState();
    let selectedContainer={}
    let mailbags=[];
    const mailAcceptance= state.containerReducer.mailAcceptance;

    let fromDeviationList = false;
    let actionType = '';
    if(state.commonReducer.activeMainTab ===constant.CARDIT_LIST) {
    let selectedCarditMailbags=state.carditReducer.selectedCarditIndex;
    for(var i=0;i<selectedCarditMailbags.length;i++) {
        let index=selectedCarditMailbags[i];
        mailbags.push(state.carditReducer.carditMailbags.results[index]);
    }


    for(let j=0; j<mailbags.length; j++){
        mailbags[j].fromPanel = 'CARDIT';
    }
    actionType = 'CARDIT';
} else if(state.commonReducer.activeMainTab === 'DEVIATION_LIST') {
    let selectedDeviationList=state.deviationListReducer.selectedDeviationList;
    let deviationlistMailbags = state.deviationListReducer.deviationlistMailbags.results ? state.deviationListReducer.deviationlistMailbags.results : [];
    for(var i=0;i<selectedDeviationList.length;i++) {
        const found = deviationlistMailbags.find(element => element.mailSequenceNumber == selectedDeviationList[i]);
        if(found) {
            mailbags.push(found);
        }      
    }
    for(let j=0; j<mailbags.length; j++){
        mailbags[j].fromPanel = 'DEVIATIONPANEL';
    }
    fromDeviationList = true;
} else {
    let selectedLyingMailbags=state.lyingListReducer.selectedLyinglistIndex;
    for(var i=0;i<selectedLyingMailbags.length;i++) {
        let index=selectedLyingMailbags[i];
        mailbags.push(state.lyingListReducer.lyinglistMailbags.results[index]);
    }
    for(let j=0; j<mailbags.length; j++){
        mailbags[j].fromPanel = 'LYINGLIST';
    }
}
let showWarning='Y';
if(args.showWarning){
    showWarning= args.showWarning;
}

let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
   // mailbagslist=state.form.newMailbagsTable?state.form.newMailbagsTable.values.newMailbagsTable :null
   let scannedDate = args.currentDate;
   let scannedTime = '00:00:00';
   if(args.scannedDate) {
      scannedDate = args.scannedDate;
   }
   if(args.scannedTime) {
    scannedTime = args.scannedTime;
 }
 selectedContainer=state.containerReducer.flightContainers.results[args.rowIndex];
    mailbags = mailbags.map((value) => ({ ...value,operationFlag:'I',weight:null,volume:null,scannedDate:scannedDate,scannedTime:scannedTime,paCode:selectedContainer.paCode ? selectedContainer.paCode : value.paCode,paBuiltFlagUpdate: selectedContainer.paBuiltFlag =="Y" ? true:false,acceptancePostalContainerNumber:selectedContainer.paBuiltFlag =="Y" ? selectedContainer.containerNumber:value.acceptancePostalContainerNumber}));
    const flightCarrierflag = state.commonReducer.flightCarrierflag
   
      
     const activeTab=   state.commonReducer.activeMainTab;
    const data = { selectedContainer: { ...selectedContainer,operationFlag:'U',containerOperationFlag:'U',mailbagdsnviewpagelist: null, mailbagpagelist: null ,dsnviewpagelist:null}, flightCarrierflag,mailAcceptance : {...mailAcceptance, fromDeviationList:fromDeviationList , containerPageInfo:null},mailbags,showWarning, actionType, warningMessagesStatus}
      // const data={containerInfo,flightCarrierflag,newMailbag,mailFlight}
      /*
      const url = 'rest/mail/operations/outbound/AddMailbag';
      return makeRequest({
          url,
          data: { ...data }
      }).then(function (response) {
          handleResponseonAdding(dispatch, response,activeTab);
          return response
      })
          .catch(error => {
              return error;
          });
     */
    dispatch(asyncDispatch(addMailbagRequest)({ data }))
}

function addMailbagRequest(values) {
    const { dispatch, getState,args } = values;
    const state = getState();
    const activeTab=   state.commonReducer.activeMainTab;
    let data  = {}
    let showWarning = 'Y';
    if(args.showWarning){
        showWarning= args.showWarning;
    }
    if(args.data) {
        data = args.data
    }

    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;

    const url = 'rest/mail/operations/outbound/AddMailbag';
      return makeRequest({
          url,
          data: { ...data,showWarning:showWarning, warningMessagesStatus: warningMessagesStatus }
      }).then(function (response) {
          handleResponseonAdding(dispatch, response,activeTab);
          return response
      })
          .catch(error => {
              return error;
    });
}


function handleResponseonAdding(dispatch, response,activeTab) {

    if (response.status==='success' && !isEmpty(response.results)) {
        // const {mailFlight,oneTimeValues,...rest} = response.results[0];
        dispatch({ type: constant.ADD_MAILBAG_SUCCESS });
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});
        if(response.status === "success") {
            if(activeTab === constant.CARDIT_LIST) {
          dispatch(asyncDispatch(applyCarditFilter)()).then(() => {
            dispatch(asyncDispatch(listDetails)()).then(() => {
               dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                  dispatch(asyncDispatch(listmailbagsinContainers)());
               })
            })
          }) 
        } else if(activeTab === 'DEVIATION_LIST') {
            dispatch(asyncDispatch(applyDeviationListFilter)()).then(() => {
              dispatch(asyncDispatch(listDetails)()).then(() => {
                 dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                    dispatch(asyncDispatch(listmailbagsinContainers)());
                 })
              })
            }) 
          } 
        else {
          dispatch(asyncDispatch(applyLyingListFilter)()).then(() => {
            dispatch(asyncDispatch(listDetails)()).then(() => {
               dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                  dispatch(asyncDispatch(listmailbagsinContainers)());
               })
            })
          }) 
        }
         }

    } else if(isEmpty(response.errors) && !isEmpty(response.results) && response.results[0].existingMailbagFlag ==='Y'){   
        let existingMailbags = response.results[0].existingMailbags
        let addedMailbags =response.results[0].mailbags
        dispatch({ type: constant.EXISTING_MAILBAG_POPUP_OPEN ,existingMailbags,addedMailbags});
      
    }  
    else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}

export function handleResponseOnReassign(dispatch,response,selectedContainerData,mode) {

    if (!isEmpty(response.results)) {
       if(mode===constant.REASSIGN) {
            if (response.status==='success') {
                return { type: constant.VALIDATECONTAINER_SUCCESS, selectedContainerData };
               
            }
        }
    }
}

export function validateAttachRoutingForm(filter) {
    let isValid = true;
    let error = ""
    if (!filter) {
        isValid = false;
        error = "Please enter filter values"
    } else {
              if (!filter.consignemntNumber) {
                 isValid = false;
                 error = "Please provide the mandatory fields"
                  
            }else if (!filter.paCode) {
                isValid = false;
                error = "Please provide the mandatory fields"
                 
        } 
 
    }


    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}


export function validateContainerOffload(values){
    const { args, getState } = values;
    const state = getState();
    let indexes=[];
    // let data={}
    // let selectedContainer = '';
    const containerDetailsCollection=[];
    let selectedContainerData=[];
    if(args.index) {
        indexes.push(args.index);
        // selectedContainer = state.containerReducer.flightContainers.results[args.index];
       
    }
    else {
        indexes=state.containerReducer.selectedContainerIndex;
       
    }
    for(var i=0; i<indexes.length;i++) {
       
            containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
            selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
       
    }
    const validateData={selectedContainerData};
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


export function navigateActions(values) {
    const { args, getState } = values;
    const state = getState();
    let indexes=[];
    const containerDetailsCollection=[];
    let selectedContainerData=[];
    if(args.index) {
        indexes.push(args.index);
        // selectedContainer = state.containerReducer.flightContainers.results[args.index];
       
    }
    else {
        indexes=state.containerReducer.selectedContainerIndex;
       
    }
    for(var i=0; i<indexes.length;i++) {
       
            containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
            selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
       
    }
    
    navigateToOffload(selectedContainerData);
   
    
  
}

function navigateToOffload(containerDetails) {
    let containers='';
    if(containerDetails.length>1){
        containerDetails.forEach(function(element) {
            containers=containers+element.containerNumber+',';
        }, this);
    }else{
        containers=containerDetails[0].containerNumber;
    }
    // let containerSelected = {};
    
    const data = {
        carrierCode: containerDetails[0].carrierCode,
        flightNumber: containerDetails[0].flightNumber,
        flightDate: containerDetails[0].flightDate,
        containerNumber: containers, 
        fromScreen: "mail.operations.ux.mailoutbound.container",
        pageURL: "mail.operations.ux.mailoutbound",
        operation: "offloadContainer"
    }
  //  if(mailAcceptance.flightOperationalStatus === 'C') {
    
    navigateToScreen("mail.operations.ux.offload.defaultscreenload.do", data);
  //  }
   // else {
      //  dispatch(requestValidationError('The container cannot be offloaded from an opened flight', ''));
    //}
}

// function navigateToOffload(containerDetails, airport, fromDate, toDate) {
//     let containers='';
//     if(containerDetails.length>1){
//         containerDetails.forEach(function(element) {
//             containers=containers+element.containerNumber+',';
//         }, this);
//     }else{
//         containers=containerDetails[0].containerNumber;
//     }
//     const data = { containerNumber: containers, carrierCode: containerDetails[0].carrierCode,
//         flightNumber: containerDetails[0].flightNumber, flightDate: containerDetails[0].flightDate, fromScreen : "mail.operations.ux.containerenquiry",
//         pageURL: "mail.operations.ux.containerenquiry" }
//     navigateToScreen("mail.operations.ux.offload.defaultscreenload.do", data);
// }
export function markUnmarkULDFullIndicator(values) {
    const { args, getState, dispatch } = values;
    const state = getState();
    let indexes = [];
    const containerDetailsCollection = [];
    let selectedContainerData = [];
    if (args.index) {
        indexes.push(args.index);
    }
    else {
        indexes = state.containerReducer.selectedContainerIndex;
    }
    for (var i = 0; i < indexes.length; i++) {
        containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
        selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
        let uldFulIndFlag = 'N';
        if (args.mode == 'ULD_MARK_FULL') {
            uldFulIndFlag = 'Y';
        }
        selectedContainerData[i].uldFulIndFlag = uldFulIndFlag;

    }

    const validateData = { selectedContainerData };
    const url = 'rest/mail/operations/outbound/markUnmarkIndicator';
    return makeRequest({
        url, data: { ...validateData }
    }).then(function (response) {
        return response;
    })
        .catch(error => {
            return error;
        });
}

export function allContainerIconAction(values){   
    const { dispatch, getState } = values;
    const state = getState();
       
    
    let mailoutboundDetails=state.containerReducer&&state.containerReducer.mailAcceptance?(state.containerReducer.mailAcceptance):{};
    if(isEmpty(mailoutboundDetails)){
        return;
    }
    const filterValues=state.filterReducer.filterValues;
    //const fromDate=filterValues.fromDate?filterValues.fromDate:''
    //const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.airportCode?filterValues.airportCode:'';
    const carrierCode=mailoutboundDetails.carrierCode?mailoutboundDetails.carrierCode:'';
    const flightNumber=mailoutboundDetails.flightNumber?mailoutboundDetails.flightNumber:'';
    const flightDate = mailoutboundDetails.flightDate?mailoutboundDetails.flightDate.split(' ')[0]:'';
    const assignedTo = state.commonReducer.flightCarrierflag==='F'?'FLT':'DESTN';
    const destination = state.containerReducer.mailAcceptance.flightDestination?state.containerReducer.mailAcceptance.flightDestination:null;
     var url = "mail.operations.ux.containerenquiry.defaultscreenload.do?fromScreen=MailOutbound&isPopup=true&airport="+airportCode+'&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate
                            +'&type=O'+'&assignedTo='+assignedTo+'&destination='+destination;
  
    var optionsArray = {
        url,
        dialogWidth: "990",
        dialogHeight: "550",
    
        popupTitle: 'All Containers'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}


export function onApplyContainerSort(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const getContainerSortDetails = () => args;
    const getContainerDetails = () =>  state.containerReducer && state.containerReducer.flightContainers ? 
    state.containerReducer.flightContainers.results : [];
    const getContainerSortedDetails = createSelector([getContainerSortDetails, getContainerDetails], (sortDetails, containers) => {
        if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
            containers.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
                if(data1===null){
                    data1='';
                }    
                if(data2===null){
                    data2='';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                }
                return sortVal;
            });
        }
        return containers;
    });

    let containerData=getContainerSortedDetails();
    let pagedContainerData= state.containerReducer && state.containerReducer.flightContainers ? 
            state.containerReducer.flightContainers : []; 
    pagedContainerData={...pagedContainerData,results:containerData};
    dispatch({type:'APPLY_CONTAINER_SORT',containerDataAfterSort:pagedContainerData});
    dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:'0',mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
}

export function onClearContainerFilterPanel(values) {
    const {  dispatch } = values;
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'containerFilter', keepDirty: true },
        payload: {}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'containerFilter' } })
}

export function onApplyContainerFilter(values){
    //  const {  dispatch, getState } = values;
    //  const formValues= getState().form.containerFilter.values;
    //  const tableFilter  = ( getState().form.containerFilter.values)? getState().form.containerFilter.values:{}
    // tableFilter.undefined?delete tableFilter.undefined:''; 
    // const { ...rest } = tableFilter;
    // const containerFilter={...rest};
    //  if(isEmpty(formValues.containerNumber)){
    //     return;
    // }
    //  const state=getState();
    //  const getTableResults = () =>
    //  state.containerReducer && state.containerReducer.flightContainers ? 
    //         state.containerReducer.flightContainers.results : [];

    // let containerData=[];      
    // const getTableFilter = () => 
    // state.form.containerFilter.values ? 
    //       state.form.containerFilter.values : {};

    // const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
    //     if (!isEmpty(filterValues)) {
      
    //         return results.filter((obj) => {  
    //            const anotherObj = { ...obj, ...filterValues}; 
    //            if(JSON.stringify(obj)===JSON.stringify(anotherObj))
    //                 return true;
    //            else 
    //               return false 
    //           } )
          
    //         } else {
    //             return results;
    //         }

    //  });
    
    // containerData=getDetails();
    // let pagedContainerData= state.containerReducer && state.containerReducer.flightContainers ? 
    //         state.containerReducer.flightContainers : []; 
    // pagedContainerData={...pagedContainerData,results:containerData};
    // dispatch({type: constant.APPLY_CONTAINER_FILTER,containerDataAfterFilter:pagedContainerData,containerFilter});
    // dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:'0', mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));


    const { args, dispatch, getState } = values;
    const state = getState();
    const  flightCarrierflag = state.filterReducer.filterValues ? state.filterReducer.filterValues.filterType : state.commonReducer.flightCarrierflag
    let containerDisplay = state.containerReducer.containerDisplayPage;
    let selectedFlightIndex = state.containerReducer.selectedFlightIndex;
  let containerDisplayPage='';
   let flightIndex = '';
    let mailAcceptance = {};
    let flightnotAlready=true;

   
    if (containerDisplay!=null && selectedFlightIndex!=null){
    
        if (flightCarrierflag === 'F') {
            if(selectedFlightIndex!=undefined) {
            flightIndex=selectedFlightIndex;
            mailAcceptance = state.filterReducer.flightDetails.results[selectedFlightIndex];
            }
            else {
               // mailAcceptance = state.containerReducer.mailAcceptance;
                flightIndex = state.containerReducer.selectedFlightIndex;
                mailAcceptance = state.filterReducer.flightDetails.results[flightIndex];
            }
        }
        else if (flightCarrierflag === 'C') {
            if(selectedFlightIndex!=undefined) {
                flightIndex=selectedFlightIndex;
                mailAcceptance = state.filterReducer.carrierDetails.results[selectedFlightIndex];
                }
                else {
                    mailAcceptance = state.containerReducer.mailAcceptance;
                }
               
        }
        containerDisplayPage=containerDisplay;
    }
    else {
        flightIndex = state.containerReducer.selectedFlightIndex;
        if (flightCarrierflag === 'F') {
            mailAcceptance = state.filterReducer.flightDetails.results[flightIndex];
        }
        if (flightCarrierflag === 'C') {
            mailAcceptance = state.filterReducer.carrierDetails.results[flightIndex];
            
        }
        containerDisplayPage=state.containerReducer.containerDisplayPage;
    }
    let containerPageInfo=mailAcceptance && mailAcceptance.containerPageInfo;
    const flightsArray = state.containerReducer.flightsArray; 
	//funnelfilter
   //const formValues= getState().form.containerFilter.values;
    let containerFilterValues= state.form.containerFilter.values;
    // const tableFilter  = ( getState().form.containerFilter.values)? getState().form.containerFilter.values:{}
   // tableFilter.undefined?delete tableFilter.undefined:''; 
   // const { ...rest } = tableFilter;
    //const containerFilter={...rest};
    // if(isEmpty(formValues.containerNumber)){
      //  return;
   // }
    // const getTableResults = () =>
    // state.containerReducer && state.containerReducer.flightContainers ? 
        //    state.containerReducer.flightContainers.results : [];

    //let containerData=[];      
    
   
    mailAcceptance = Object.assign({},{...mailAcceptance})
    let flightCarrierFilter = {};
    if(args.action ==='APPLY_FILTER'){
        mailAcceptance.containerNumber = containerFilterValues && containerFilterValues.containerNumber ? containerFilterValues.containerNumber:null;
        mailAcceptance.rdtDate = containerFilterValues && containerFilterValues.rdtDate ? containerFilterValues.rdtDate :null;
        if(flightCarrierflag ==='F') {
            flightCarrierFilter.destination = containerFilterValues && containerFilterValues.destination ? containerFilterValues.destination:null;
            flightCarrierFilter.pou = containerFilterValues && containerFilterValues.pou ? containerFilterValues.pou :null;     
        }
        containerDisplayPage=1;
    }
    else if(args.action ==='CLEAR_FILTER'){
        mailAcceptance.containerNumber=null;
        mailAcceptance.rdtDate=null;
        containerDisplayPage=state.containerReducer.containerDisplayPage;
         containerFilterValues= {};
         dispatch(reset('containerFilter'));
    }
    if (flightnotAlready) {
        const data = { mailAcceptance:{...mailAcceptance, containerPageInfo:null}, flightCarrierflag, containerDisplayPage,flightCarrierFilter}
        const url = 'rest/mail/operations/outbound/listContainers';
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            handleResponseForContainers(dispatch, response, flightsArray, flightIndex,containerDisplayPage,containerFilterValues);
            return response
        })
            .catch(error => {
                return error;
            });
    }else {
        let containersArray='';
        let containerInfo='';
        mailAcceptance.containerPageInfo=containerPageInfo
        dispatch({ type: constant.LIST_CONTAINER, mailAcceptance, flightsArray, mode: 'multi', flightIndex  });
        dispatch( { type: constant.LIST_MAILBAGS, containerInfo,containersArray,mode:'multi' });
        if (!flightnotAlready) {
          return Promise.resolve({})
        }
      }
}

                                                             

    export function onClearContainerFilter(values){
     const { dispatch, getState } = values;
     const state = getState();
     //const formValues= state.form.containerFilter.values;  
     let containerData= state.containerReducer && state.containerReducer.containers ? 
                state.containerReducer.containers : [];
    let pagedContainerData= state.containerReducer && state.containerReducer.flightContainers ? 
                state.containerReducer.flightContainers : []; 
        pagedContainerData={...pagedContainerData,results:containerData};
    dispatch(reset('containerFilter'));
    dispatch({type: 'CLEAR_CONTAINER_FILTER',containerDataAfterFilter:pagedContainerData});
    dispatch(asyncDispatch(listmailbagsinContainers)());
    //onContainerRowSelection(values);
}

//Added as part of reassign to flight CR
//To validate the filter values entered in reassign pop up
export function validateForm(data) {
    let isValid = true;
    let error = ""
    if (!data) {
        Promise.reject("Please enter filter values");
    } else {
            if (!data.flightnumber.flightDate || !data.flightnumber.flightNumber) {
                if(!data.flightnumber.flightDate && !data.flightnumber.flightNumber) {
                   if (!(data.fromDate && data.toDate)) {
                    Promise.reject("Please enter Flight date and Flight number or Flight date range");
                   }
                }
                else if(!data.flightnumber.flightDate){
                    Promise.reject("Please enter Flight Date");
                }
                else if (!data.flightnumber.flightNumber){
                    Promise.reject("Please enter Flight Number");
                }
            }
            
        }
    }

//The main outbound list
export const listFlightDetailsForReassign = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let recordsPerPage = state.commonReducer.defaultPageSize;
    if(args && args.recordsPerPage) {
        recordsPerPage = args.recordsPerPage;
    }
    let displayPage='';
   // const mode = state.commonReducer.displayMode;
    let mode=state.commonReducer.displayMode;
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    const assignTo = state.commonReducer.flightCarrierflag;
    const flightCarrierFilter = (state.form.reassignContainerToFlight&&state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values :
    (state.form.transferContainerToFlight&&state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values:{};
    if (!flightCarrierFilter.flightnumber.flightDate || !flightCarrierFilter.flightnumber.flightNumber) {
        if(!flightCarrierFilter.flightnumber.flightDate && !flightCarrierFilter.flightnumber.flightNumber) {
           if (!(flightCarrierFilter.fromDate && flightCarrierFilter.toDate)) {
            return Promise.reject(new Error("Please enter Flight date or Flight number or Flight date range"));
           }
        }
         // Commented as part of IASCB-47000
       /* else if(!flightCarrierFilter.flightnumber.flightDate){
            return Promise.reject(new Error("Please enter Flight Date"));
        }
        else if (!flightCarrierFilter.flightnumber.flightNumber){
            return Promise.reject(new Error("Please enter Flight Number"));
        }*/
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
    const flightNumber = (state.form.reassignContainerToFlight&&state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values.flightnumber :
    (state.form.transferContainerToFlight&&state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values.flightnumber:{};
    if(flightCarrierFilter.airportCode ===loggedAirport ){
        flightActionsEnabled='true';
    }
        if (!isEmpty(flightNumber)) {
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
    flightCarrierFilter.flightStatus = ['ACT','TBA']
    flightCarrierFilter.flightOperationalStatus = 'O,N'
    //added as part of IASCB-36551
    if(state.filterReducer.filterValues.filterType ==='F') {
        flightCarrierFilter.operatingReference = state.filterReducer.filterValues.operatingReference
    } else {
        flightCarrierFilter.operatingReference = state.commonReducer.defaultOperatingReference
    }
    flightCarrierFilter.recordsPerPage = recordsPerPage;
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
    const flightCarrierFilter = (state.form.reassignContainerToFlight&&state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values :
    (state.form.transferContainerToFlight&&state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values:{};
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    if(flightCarrierFilter.airportCode === loggedAirport ){
        flightActionsEnabled='true';
    }
    let List=state.containerReducer.flightDetails?state.containerReducer.flightDetails.results:[];
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
        
        handleResponseForReassignToFlight(dispatch, response, data, constant.FETCH_CAPACITY,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

export function printuldtag(values){
    
    const { args, getState, dispatch } = values;
    const state = getState();
    let indexes = [];
    const containerDetailsCollection = [];
    let selectedContainerData = [];
    if (args.index) {
        indexes.push(args.index);
    }
    else {
        indexes = state.containerReducer.selectedContainerIndex;
    }
    for (var i = 0; i < indexes.length; i++) {
        containerDetailsCollection.push(state.containerReducer.flightContainers.results[indexes[i]]);
        selectedContainerData.push(state.containerReducer.flightContainers.results[indexes[i]]);
    }

    const validateData = { selectedContainerData };
    const url = 'rest/xaddons/mail/operations/printuldtag';
    // const actionType=constant.ATTACH_ROUTING_LIST;
    return makeRequest({
        url, data: { ...validateData }
    }).then(function (response) {
        openULDTagPopup(response);
        return response;
    })
        .catch(error => {
            return error;
        }); 

}
export function openULDTagPopup(response){
    var uldlabeldetails=response.results[0];
    var redirecturl=response.results[1];
    
    var url = redirecturl +"?"+ uldlabeldetails;
    var windowFeatures = 'width=1150,height=560'; 
  
    window.open(url, '_blank', windowFeatures);
}
function handleResponseForReassignToFlight(dispatch, response, data, mode,flightActionsEnabled) {
    
    if (!isEmpty(response.results)) {
        if(mode === constant.FETCH_CAPACITY) {
            const { flightCapacityDetails } = response.results[0];
             dispatch({ type: constant.LIST_CAPACITY_REASSIGN_FLIGHT,flightCapacityDetails,mode});
       }else{
            const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
            dispatch({ type: constant.LIST_DETAILS_REASSIGN_FLIGHT, mailflightspage, wareHouses, data, mode,flightActionsEnabled });
       }
    }
}

export const clearReassignFlightPanel = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: constant.CLEAR_REASSIGN_FLIGHT_PANEL });
    dispatch(reset('reassignContainerToFlight'));

}

export function toggleFilter(screenMode) {
    return { type: constant.TOGGLE_FILTER, screenMode };
}

export function toggleCarditView() {
    return { type: constant.TOGGLE_CARDIT_VIEW };
}



export const clearFilter = (values) => {
    const { dispatch } = values;
    dispatch({ type: constant.CLEAR_FILTER });
    dispatch(reset(constant.OUTBOUND_FILTER));


}

export function reassignContainerToFlightAction(values){
    const {args,dispatch, getState, } = values;
    const state = getState(); 
    let selectedContainerData=[];
    selectedContainerData=state.containerReducer?[...state.containerReducer.containerDetailsToBeReused]:[];
    selectedContainerData = selectedContainerData.map((item)=>{
        return Object.assign({},{...item})
    })
    const containerDetails = state.form.containerDetails?(state.form.containerDetails.values.containerDetails):[];
    let loggedAirport= state.commonReducer.airportCode;
   let reassignFilter=state.containerReducer.reassignFilter;
   let  reassignContainerToFlightOrCarrier={};
   let pou = '';
   let pous = [];
   let countOfStations = 1;
   let isRoundRobin = false;
   let isMultiSector = false;
   
   let selectedFlight={};
   if(reassignFilter==='F'){
    const selectedFlightIndex=state.containerReducer?state.containerReducer.selectedFlightIndexReassign:[];

    if(selectedFlightIndex.length!==1){
        return Promise.reject(new Error("Please select one of the flight details"));
    }

    selectedFlight=state.containerReducer?state.containerReducer.flightDetails.results[selectedFlightIndex[0]]:{};

   
    // const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    //const data = {selectedContainerData};
     reassignContainerToFlightOrCarrier  = (state.form.reassignContainerToFlight.values)?state.form.reassignContainerToFlight.values:{}
    const flightNumber=(state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values.flightnumber:{};
    


    const flightRoute = selectedFlight.flightRoute;
    pous = flightRoute.split("-");
    
    for(let k=0; k<pous.length; k++){
        if(pous[k] === loggedAirport){
            countOfStations = (pous.length-1) -k;
            pou = pous[k+1];
            break;
        }
    }

    if(countOfStations > 1){
        isMultiSector = true;
        if(pous[0] === pous[length-1]){
            isRoundRobin = true;
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
    reassignContainer.remarks=reassignContainerToFlightOrCarrier.remarks;

    

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
                            let autoPOU='';
                            for(let k=0; k<pous.length; k++){
                                if(pous[k] === containerDetails[j].destination){
                                    autoPOU = pous[k];
                                    break;
                                }
                            }
                            if(autoPOU !== ''){
                                selectedContainerData[i].pou = autoPOU;
                            }else{
                                if(isMultiSector && !isRoundRobin){
                                    return Promise.reject(new Error("Please enter POU"));  
                                }else{
                                    selectedContainerData[i].pou = pou;
                                }
                                
                            }
                            selectedContainerData[i].destination =selectedContainerData[j].destination;
                            //selectedContainerData[i].finalDestination = selectedContainerData[j].destination;   
                        }else{
                            let autoPOU='';
                            for(let k=0; k<pous.length; k++){
                                if(pous[k] === containerDetails[j].destination){
                                    autoPOU = pous[k];
                                    break;
                                }
                            }
                            if(autoPOU !== ''){
                                selectedContainerData[i].pou = autoPOU;
                            }else{
                                if(isMultiSector && !isRoundRobin){
                                    return Promise.reject(new Error("Please enter POU"));  
                                }else{
                                    selectedContainerData[i].pou = pou;
                                }
                            }
                            
                            selectedContainerData[i].destination = containerDetails[j].destination;
                            selectedContainerData[i].finalDestination = containerDetails[j].destination;
                        }
                    }else{
                        if(selectedContainerData[i].type ==='B'){
                            if(containerDetails[j].pou && containerDetails[j].pou!=null && containerDetails[j].pou.trim().length ) {
                                selectedContainerData[i].pou = containerDetails[j].pou;
                            }else {
                                selectedContainerData[i].pou = pou;
                            }
                            selectedContainerData[i].destination =selectedContainerData[j].destination;
                            //selectedContainerData[i].finalDestination = selectedContainerData[j].destination;   
                        }else{
                        selectedContainerData[i].pou = containerDetails[j].pou;
                        selectedContainerData[i].destination = containerDetails[j].destination;
                        selectedContainerData[i].finalDestination = containerDetails[j].destination;
                        }
                    }
                }
                else{
                    if(selectedContainerData[i].type==='B'&&
                    selectedContainerData[i]&&selectedContainerData[i].destination&&
                    containerDetails[j].destination&&selectedContainerData[i].destination!=containerDetails[j].destination){
                    selectedContainerData[i].containerDestChanged=true;
                  }
                    selectedContainerData[i].destination = containerDetails[j].destination;
                    selectedContainerData[i].finalDestination = containerDetails[j].destination;
                }
                    selectedContainerData[i].actualWeight = containerDetails[j].actualWeight;
                    selectedContainerData[i].contentId = containerDetails[j].contentId;
                    selectedContainerData[i].remarks = containerDetails[j].remarks;
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
    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
       if(isValidation||!uldBarrowCheck){
       if (reassignFilter==='C'|| args.warningFlag||!state.commonReducer.weightScaleAvailable ||actualWeight!=0){
            const data = { selectedContainerData, reassignContainer, onwardRouting,warningMessagesStatus };
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
export function revertUldConversion(values){
    const { dispatch } = values;
    dispatch(reset('addcontainerForm'));
}
export function convertUldToBarrow(values){
    const {args,dispatch, getState, } = values;
    const state = getState(); 
    let selectedContainerData=[];
    let onwardRouting=[];
    let item=(state.containerReducer.selectedContainerforModify) ? state.containerReducer.selectedContainerforModify : [];
let barrowFlag=state.form.addcontainerForm.values&&state.form.addcontainerForm.values.barrowFlag;
let uldToBarrowCheck=false;
let barrowToUldCheck=false;
if(item.barrowFlag!=barrowFlag && barrowFlag&&/[A-Z]{1,3}[0-9]{1,5}[A-Z]{2}/.test(item.containerNumber))
{ 
    uldToBarrowCheck=true;
}
else if(item.barrowFlag!=barrowFlag && !barrowFlag&&/[A-Z]{1,3}[0-9]{1,5}[A-Z]{2}/.test(item.containerNumber)&&item.type==='B'){
    barrowToUldCheck=true;
} 
   if(args.uldToBarrow||args.barrowToUld||(!uldToBarrowCheck&&!barrowToUldCheck)){
        //  item.type='B';
        item.uldTobarrow=args.uldToBarrow;
        item.barrowToUld=args.barrowToUld;
        item.finalDestination=state.form.addcontainerForm.values&&state.form.addcontainerForm.values.destination;
        if(item.type==='B'&&
        state.containerReducer.selectedContainer&&state.containerReducer.selectedContainer.destination&&
        item.finalDestination&&item.finalDestination!=state.containerReducer.selectedContainer.destination){
        item.containerDestChanged=true;
        }
        item.destination=  item.finalDestination;
    
        if(state.commonReducer.flightCarrierflag==='F'){
            
       
        if( (item.uldTobarrow||item.type==='B')&& item.finalDestination!= item.pou&& !item.barrowToUld){
            dispatch( { type: constant.CONTAINER_MODIFY, item});
            return Promise.reject(new Error("POU and destination should be same"));    
        }
    }
        selectedContainerData.push(item);
       
        let reassignContainer={};
        reassignContainer.flightNumber=state.containerReducer.mailAcceptance.flightNumber?state.containerReducer.mailAcceptance.flightNumber:null;
        reassignContainer.flightDate=state.containerReducer.mailAcceptance.flightDate?state.containerReducer.mailAcceptance.flightDate:null;
        reassignContainer.carrierCode=state.containerReducer.mailAcceptance.carrierCode?state.containerReducer.mailAcceptance.carrierCode: null;
        reassignContainer.reassignFilterType=state.commonReducer.flightCarrierflag;
        reassignContainer.scanDate= state.containerReducer.scanDate;
        reassignContainer.mailScanTime=state.containerReducer.scanTime;
       /* if( reassignContainer.reassignFilterType==='F'){
        reassignContainer.destination= state.containerReducer.mailAcceptance.flightDestination?state.containerReducer.mailAcceptance.flightDestination:null;
         } else{
            reassignContainer.destination= state.containerReducer.mailAcceptance.destination?state.containerReducer.mailAcceptance.destination:null;
        }*/
        reassignContainer.destination=state.form.addcontainerForm.values&&state.form.addcontainerForm.values.destination;
        reassignContainer.flightCarrierCode=  state.containerReducer.mailAcceptance.carrierCode?state.containerReducer.mailAcceptance.carrierCode:null;
        reassignContainer.carrierCode=reassignContainer.flightCarrierCode;
        reassignContainer.assignedto=reassignContainer.reassignFilterType;
        let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
        let containerModify=true;
        const data = { selectedContainerData, reassignContainer, onwardRouting, containerModify, warningMessagesStatus };
        const url='rest/mail/operations/containerenquiry/reassignContainer';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponseonBulkConversion(state,dispatch, response);
        return response
    })
    .catch(error => {
        return error;
    });
}else if(uldToBarrowCheck){
    dispatch(requestWarning([{code:"mail.operations.container.uldwarning", description:"The selected ULD container will be converted as Bulk. Do you want to continue?"}],{functionRecord:convertUldToBarrow, args:{warningFlag:true}}));
   
}
else if(barrowToUldCheck){
    dispatch(requestWarning([{code:"mail.operations.container.barrowwarning", description:"The selected bulk container will be converted as ULD. Do you want to continue? "}],{functionRecord:convertUldToBarrow, args:{warningFlag:true}}));
   
}
}
function handleResponseonBulkConversion(state,dispatch,response){
    if (isEmpty(response.errors)) {
    let item=(state.containerReducer.selectedContainerforModify) ? state.containerReducer.selectedContainerforModify : [];
    item.uldTobarrow=false;
    dispatch( { type: constant.ADD_CONTAINER_POPUPCLOSE,item}); 
        const warningMapValue = { ['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
    }
}


function handleResponseForContainers(dispatch, response, flightsArray, flightIndex,containerDisplayPage,containerFilterValues) {
    
    if (!isEmpty(response.results)) {
        const { mailAcceptance } = response.results[0];
        flightsArray.push(mailAcceptance);
        dispatch({ type: constant.LIST_CONTAINER, mailAcceptance, flightsArray, mode: 'multi', flightIndex,containerDisplayPage,containerFilterValues});
        // dispatch({type: 'CHANGE_DETAILPANEL_MODE',mode:'multi'});


    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
        }
    }
}
export function paBuiltUpdate(values) {
    const { args, dispatch ,getState } = values;
    const state = getState(); 
    let isBarrow=args.barrowFlag;
    if(isBarrow){
    dispatch(change(constant.ADD_CONTAINER_FORM,'paBuiltFlagValue', false));
    dispatch(change(constant.ADD_CONTAINER_FORM,'paCode', null));
    
    dispatch({ type: constant.PABUILT_FLAG, data: false });
    }
    dispatch({ type: constant.BARROW_CHECK,isBarrow})
}
export function transferContainerAction(values){
    const {dispatch,getState} =values;
    const state =getState();
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    let selectedContainerData=state.containerReducer?[...state.containerReducer.containerDetailsToBeReused]:[];
    selectedContainerData = selectedContainerData.map((item)=>{
        return Object.assign({},{...item})
    })
    
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

    transferForm.flightPou = transferForm.destination;
    transferForm.fromScreen='MTK060';
    const data = {selectedContainerData,transferForm, onwardRouting};
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
export function handlevalidateContainerTransferSaveResponse(dispatch,response){
    if (response.status==='transfersave_success') {
         dispatch(transferContainerSuccess(response.status));       
        const warningMapValue = { ['mail.operations.securityscreeningwarning']:'N' };
        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
     } 
 }
 export function transferContainerSuccess(data){
    return{ type : constant.TRANSFERSAVE_SUCCESS }
}
export const clearTransferFlightPanel = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: constant.CLEAR_REASSIGN_FLIGHT_PANEL });
    dispatch(reset('transferContainerToFlight'));

}
export function transferContainerToFlightAction(values){
    const {dispatch, getState ,args} = values;
    const state = getState(); 
        let selectedContainerData=[];
        selectedContainerData=state.containerReducer?[...state.containerReducer.containerDetailsToBeReused]:[];
        selectedContainerData = selectedContainerData.map((item)=>{
            return Object.assign({},{...item})
        })

    const selectedFlightIndex=state.containerReducer?state.containerReducer.selectedFlightIndexReassign:[];

    if(selectedFlightIndex.length!==1){
        return Promise.reject(new Error("Please select one of the flight details"));
    }

    let selectedFlight={};
    selectedFlight=state.containerReducer?state.containerReducer.flightDetails.results[selectedFlightIndex[0]]:{};

   
    const transferContainerToFlight  = (state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values:{}
    const containerDetails = state.form.containerDetails?(state.form.containerDetails.values.containerDetails):[];
    let onwardRouting=(state.form.onwardRouting && state.form.onwardRouting.values) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }


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
     dispatch(requestWarning([{code:"mail.operations.container.uldwarning", description:"The selected ULD container("+containerNumbers+") will be converted as Bulk. Do you want to continue?"}],{functionRecord:transferContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
    }
    else if(bulkToUldValidaion&&!barrowToUld){
         dispatch(requestWarning([{code:"mail.operations.container.barrowwarning", description:"The selected bulk container will be converted as ULD. Do you want to continue?"}],{functionRecord:transferContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
    }
    else{
     isValidation=true;
    }
 }
else if(bulkToUldValidaion&&uldBarrowCheck){
 if(!barrowToUld){
     dispatch(requestWarning([{code:"mail.operations.container.barrowwarning", description:"The selected bulk container will be converted as ULD. Do you want to continue?"}],{functionRecord:transferContainerToFlightAction, args:{warningFlag:args.warningFlag}}));
 }
 else{
     isValidation=true;
 } }
    else{
     isValidation=true;
    }
    for(let i=0; i<selectedContainerData.length; i++){
        for(let j=0; j<containerDetails.length; j++){
            if(selectedContainerData[i].containerNumber === containerDetails[j].containerNumber){
                if(!containerDetails[j].pou){
                    if(selectedContainerData[i].type ==='B'){
                        selectedContainerData[i].pou = pou;
                        selectedContainerData[i].destination =selectedContainerData[j].destination;
                        //selectedContainerData[i].finalDestination = selectedContainerData[j].destination;   
                    }else{
                        selectedContainerData[i].pou = pou;
                        selectedContainerData[i].destination = containerDetails[j].destination;
                        selectedContainerData[i].finalDestination = containerDetails[j].destination;
                    }
                }else{
                    if(selectedContainerData[i].type ==='B'){
                        selectedContainerData[i].pou = pou;
                        selectedContainerData[i].destination =selectedContainerData[j].destination;
                        //selectedContainerData[i].finalDestination = selectedContainerData[j].destination;   
                    }else{
                    selectedContainerData[i].pou = containerDetails[j].pou;
                    selectedContainerData[i].destination = containerDetails[j].destination;
                    selectedContainerData[i].finalDestination = containerDetails[j].destination;
                    }
                }
                selectedContainerData[i].actualWeight = containerDetails[j].actualWeight;
                selectedContainerData[i].contentId = containerDetails[j].contentId;
                selectedContainerData[i].remarks = containerDetails[j].remarks;
            }
        }
    }

    let warningMessagesStatus = state.commonReducer.warningMessagesStatus;
    transferForm.fromScreen='MTK060';
    if(isValidation||!uldBarrowCheck){
        const data = { selectedContainerData, transferForm, onwardRouting,warningMessagesStatus };
    //const data = {selectedContainerData,reassignContainer,onwardRouting};
    const url='rest/mail/operations/containerenquiry/transferContainerSave';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handlevalidateContainerTransferSaveResponse(dispatch, response);
        return response;
    })
    }  
  }
  export function handleResponseForModify(dispatch,response,mode) {
    
    if(isEmpty(response.errors) ){
            let {disableForModify} = response.results[0];
            dispatch( { type:mode,disableForModify});
        
    }
}