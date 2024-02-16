import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';
import * as constant from '../constants/constants';
import {onSaveContainer,transferContainerToFlightAction,onContainerRowSelection,listMailbagAndDsnsOnContainerList} from './containeraction';
import {onGenManifestPrint,onSaveExcelmailbagDetails,performMailbagScreenAction,getFlightDetailsForChangeFlight,updateFlightDetails,
listChangeFlightMailPanel,onSavemailbagDetails,onPopulatemailbagDetails,validateNewMailbagAdded,screenloadTransferMailAction} from './mailbagaction';
import {listContainersAlone,onRowSelection,listContainers} from './flightaction';
import { asyncDispatch , dispatchPrint,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { listMailInbound } from './filteraction';

    export function changeDetailPanelMode(values){
    const { args, dispatch } = values;
    const mode = args.currentTarget.dataset.mode;   
    dispatch({type:constant.CHANGE_TABLE_DETAILPANEL_MODE,mode});
    dispatch({ type: constant.CHANGE_DETAILPANEL_MODE, mode });
}

export function screenLoad(values) {
   
    const { dispatch, getState } = values;
     const state = getState();
    const mailinboundFilter=(state.form.mailinboundFilter.values)?state.form.mailinboundFilter.values:{};
    const data={mailinboundFilter};
    const url = 'rest/mail/operations/mailinbound/screenload';    
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleScreenloadResponse(dispatch, response,state);
        return response;
    })
    .catch(error => {
        return error;
    });

}

export function onClose(values) { 
    const { dispatch, getState } = values;
        let state = getState();
        let fromScreen = state.filterReducer.fromScreen;
        if (fromScreen === 'mail.operations.ux.mailtransitoverview') {
            const params = {
                 airportCode:state.filterReducer.navigationFilter.airportCode,
                 fromDate:state.filterReducer.navigationFilter.fromDate,
                 toDate:state.filterReducer.navigationFilter.toDate,
                 flightNumber:state.filterReducer.navigationFilter.flightnumber.flightNumber,
                 carrierCode:state.filterReducer.navigationFilter.flightnumber.carrierCode,
                 flightDate:state.filterReducer.navigationFilter.flightnumber.flightDate,
                // conDocNo: state.filterpanelreducer.navigationFilter,
                // paCode: state.filterpanelreducer.paCode,
                fromScreen:'mail.operations.ux.mailinbound'
            }
            navigateToScreen('mail.operations.ux.mailtransitoverview.defaultscreenload.do',params);
        }
        else {
            navigateToScreen('home.jsp', {});
    
        }
               
            }

export function clearPopUp(values){
    const { args, dispatch } = values;
    const action=args.action;
    if(action==constant.CHANGE_FLIGHT){
        dispatch(reset(constant.CHANGE_FLIGHT_MAIL_DETAILS)); 
        dispatch({type: constant.LIST_CONTAINERS_POPUP,  
            containerVosToBeReused:{},flightDetailsToBeReused:{},addContainerButtonShow:true});
			  dispatch({type: constant.CLEAR_CHANGE_FLIGHT, flightno:{}}); 
    }
    if(action==constant.ATTACH_ROUTING){
        dispatch(reset(constant.ATTACH_ROUTING_FORM)); 
    }
    if(action==constant.TRANSFER){
        dispatch(reset(constant.TRANSFER_MAIL_FORM)); 
        dispatch({type: constant.LIST_CONTAINERS_POPUP,  
                         containerVosToBeReused:{},flightDetailsToBeReused:{},addContainerButtonShow:true});
    }
    if(action==constant.ATTACH_AWB){
        dispatch(reset(constant.ATTACH_AWB_DETAILS)); 
    }
    if(action==constant.DAMAGE_CAPTURE){
        dispatch(reset(constant.DAMAGE_CAPTURE_RESET)); 
    }
}
export function displayError(values){
    const { dispatch,args } = values;
    dispatch(requestValidationError(args.message,args.target));
}

function handleScreenloadResponse(dispatch, response,state) {
    if(response.errors==null){
    if (response.results) {
        dispatch({ type: constant.SCREEN_LOAD_SUCCESS, oneTimeValues:response.results[0].oneTimeValues,
                    filterVlues:response.results[0],density:response.results[0].density,ReadyforDeliveryRequired:response.results[0].readyforDeliveryRequired,stationVolUnt:response.results[0].stationVolUnt,port:response.results[0].port,
                    valildationforimporthandling:response.results[0].valildationforimporthandling,validationforTBA:response.results[0].validationforTBA });
                    if(state.filterReducer.fromScreen === 'mail.operations.ux.mailtransitoverview'){
                        dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST, displayPage: '1',pageSize:'100' } ,fromMainList:true}))
                    } 
                }
    }
}

export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case "__DELEGATE_WARNING_ONOK":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] ===  "mailtracking.defaults.warn.ulddoesnot.exists" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotinairport" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotinthesystem" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.warning.uldisnotinthesystem" ||
                warningCode[0] == "mail.defaults.error.uldisnotinairport" ||
                warningCode[0] == "mail.defaults.warning.uldisnotinairport" ||
                warningCode[0] == "mail.defaults.error.uldislost" ||
                warningCode[0] == "mail.defaults.warning.uldislost" ||
                warningCode[0] == "mail.defaults.error.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.warning.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.warning.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.error.uldisnotinthesystem" ) {
                    action.executionContext.args.showWarning='N';
                    dispatch(asyncDispatch(action.executionContext.functionRecord)( action.executionContext.args));
                } 
                if (warningCode == "mail.operations.container.uldwarning") {
                    dispatch(asyncDispatch(transferContainerToFlightAction)({ uldToBarrow: true }));
                }
                if (warningCode == "mail.operations.container.detachAWBwarning") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)( action.executionContext.args));
                }
                if (warningCode == "mail.operations.container.detachAWBwarningContainer") {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)( {...action.executionContext.args,detachAwb:true}));
                }
                if (warningCode == "mail.operations.mailbag.transfermanifestwarning") {
                    dispatch(dispatchPrint(onGenManifestPrint)({ transferManifest: true }));
                }
                if(warningCode[0] === "mail.operations.container.retainwarning"){
                    dispatch(asyncDispatch(action.executionContext.functionRecord)( action.executionContext.args));

                } 
                if(warningCode == "mail.operation.domesticmaildoesnotexistwarning"){
                    action.executionContext.args.showWarning='N';
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args))
                    
                 }                    
                if(warningCode == "mail.operation.domesticmaildoesnotexistwarningonsave"){
                    action.executionContext.args.showWarning='N';
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then((response)=>{
                        dispatch(asyncDispatch(onSaveExcelmailbagDetails)(response)).then(()=>{
                        dispatch(asyncDispatch(onContainerRowSelection)()).then(()=>{
                            dispatch(dispatchAction(listContainersAlone)())
                        })})} )
                    
                 }                    

                
                
                if(warningCode[0] === "mail.operations.container.releaseWarning"){
                    dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args, arriveMail:true})).then((response)=>{
                            if( response &&response.status!=='security'){
                                let deliveryFlag=false;
                                let data=response.results;
                                for(let i=0;i<data.length;i++){
                                if(data[i].deliverMailDetails.date!=null && data[i].deliverMailDetails.time!=null){
                                    deliveryFlag=true;  
                                    break; 
                                }
                                }
                               dispatch(asyncDispatch(onRowSelection)({fromAction:'Container',stopPropgn:response.stopPropgn?response.stopPropgn:false,deliveryFlag}))}})
                }
                if (warningCode ==  "mail.operations.securityscreeningwarning") {
                    action.executionContext.args.screenWarning=true;
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                        dispatch(asyncDispatch(onContainerRowSelection)()).then(()=>{
                            dispatch(dispatchAction(listContainersAlone)())
                        });
                    });
                }                     

            }
            break;
        case "__DELEGATE_WARNING_ONCANCEL":
            if (action.executionContext) {
                const warningCode = action.warningCode;
                if (warningCode[0] ===  "mailtracking.defaults.warn.ulddoesnot.exists" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotinairport" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotinthesystem" ||
                warningCode[0] == "mailtracking.defaults.warn.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.warning.uldisnotinthesystem" ||
                warningCode[0] == "mail.defaults.error.uldisnotinairport" ||
                warningCode[0] == "mail.defaults.warning.uldisnotinairport" ||
                warningCode[0] == "mail.defaults.error.uldislost" ||
                warningCode[0] == "mail.defaults.warning.uldislost" ||
                warningCode[0] == "mail.defaults.error.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.warning.uldisnotoperational" ||
                warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.warning.uldnotinairlinestock" ||
                warningCode[0] == "mail.defaults.error.uldisnotinthesystem"  ||
                warningCode[0] == "mail.operations.container.retainwarning"  ||
                warningCode[0] == "mail.operation.domesticmaildoesnotexistwarning"||
                warningCode[0] == "mail.operation.domesticmaildoesnotexistwarningonsave") {
                    const warningMapValue = { [warningCode]: 'N' };
                }
                if(warningCode == "mail.operations.mailbag.transfermanifestwarning"){
                    
                    dispatch({type: constant.TRANSFER_SAVE,showTransferClose:false});
                
                   
                     }
            }
            break;
        default:
            break;
    }
}

export function handlePopupActions(values){
    const {dispatch,getState} =values;
    const state = getState();
    
    let popupAction = state.commonReducer.popupAction;
    let actionType= state.commonReducer.actionType;
    let indexArray = state.commonReducer.indexArray;
    var newMailbags=state.form.newMailbagsTable && state.form.newMailbagsTable.values && state.form.newMailbagsTable.values.newMailbagsTable ?
    state.form.newMailbagsTable.values.newMailbagsTable : [];
    var activeMailbagAddTab = state.mailbagReducer?state.mailbagReducer.activeMailbagAddTab:'NORMAL_VIEW';
    
    if(popupAction=='embargo'){
        if(actionType===constant.ARRIVE_MAIL){
            let data={indexArray:indexArray,actionName:actionType}
            dispatch(asyncDispatch(performMailbagScreenAction)(data)).then((response)=>{
                if(response.results && response.results[0]!=null&&response.results[0].transferManifestVO!=null){
                  dispatch(asyncDispatch(onGenManifestPrint)(values)).then((response)=>{
                    dispatch(asyncDispatch(onContainerRowSelection)()).then((response)=>{
                    dispatch(asyncDispatch(onRowSelection)(response))
                  })
                    if(actionType === constant.ARRIVE_MAIL) {
                      dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({'action':constant.ARRIVE_MAIL, response: response}))
                      .then((response)=>{
                          dispatch(dispatchAction(updateFlightDetails)({response}))
                          dispatch(asyncDispatch(listChangeFlightMailPanel)({...response,'action':constant.ARRIVE_MAIL}))
                        })
                    }
                  })
                  
                    dispatch(asyncDispatch(listContainers)()).then((response) =>{
                  dispatch(asyncDispatch(listMailbagAndDsnsOnContainerList)(response))
                })
                 
                  }else {
                    dispatch(asyncDispatch(onContainerRowSelection)()).then((response)=>{
                  dispatch(asyncDispatch(onRowSelection)(response))
                })
                    if(actionType === constant.ARRIVE_MAIL) {
                      dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({'action':constant.ARRIVE_MAIL, response: response}))
                      .then((response)=>{
                          dispatch(dispatchAction(updateFlightDetails)({response}))
                          dispatch(asyncDispatch(listChangeFlightMailPanel)({...response,'action':constant.ARRIVE_MAIL}))
                        })
                    }
                    dispatch(asyncDispatch(listContainers)()).then((response) =>{
                  dispatch(asyncDispatch(listMailbagAndDsnsOnContainerList)(response))
                })
                  }
                    }
                  );
            }else if(actionType===constant.TRANSFER){
                let data={indexArray:indexArray,actionName:actionType}
                dispatch(asyncDispatch(screenloadTransferMailAction)(data))
            }
            else{
                let validObject = validateNewMailbagAdded(newMailbags);
                if (!validObject.valid) {
                  dispatch(requestValidationError(validObject.msg, ''));
                }else{
                    if(activeMailbagAddTab==='NORMAL_VIEW') {
                    dispatch(asyncDispatch(onSavemailbagDetails)()).then(()=>{
                          dispatch(asyncDispatch(onContainerRowSelection)()).then(()=>{
                              dispatch(dispatchAction(listContainersAlone)())
                        })
                      });
                      } else {
                        dispatch(asyncDispatch(onPopulatemailbagDetails)()).then((response)=>{
                          if(response.status==='success'){
                        dispatch(asyncDispatch(onSaveExcelmailbagDetails)(response)).then(()=>{
                          dispatch(asyncDispatch(onContainerRowSelection)()).then(()=>{
                              dispatch(dispatchAction(listContainersAlone)())
                            })
                          })
                        }
                      });
                      }
                    }
                }
    }
}

