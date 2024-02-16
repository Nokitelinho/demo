import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { change} from 'icoreact/lib/ico/framework/component/common/form';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import * as constant from '../constants/constants';
import { listContainersinFlight,listmailbagsinContainers} from './flightlistactions.js';
import {assignToContainers ,revertUldConversion,convertUldToBarrow} from './containeractions.js';
import {listDetails} from './filteraction.js'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {onMailBagAction} from './mailbagactions.js'
import {onSavemailbagDetailsforExcel} from './mailbagactions.js'
export function screenLoad(args) {
    const {dispatch,getState} =args;
    const state = getState();
    const url = 'rest/mail/operations/outbound/screenload';
    return makeRequest({
        url, data: {}
    }).then(function (response) {
        state.commonReducer.isNavigation? response.results[0].isCarrierDefault = 'N' : '';
        state.commonReducer.isNavigation = false;
        handleScreenloadResponse(dispatch, response,state);
        return response;
    })

}

export const chooseFilter = (values) => {
    const { args, dispatch} = values;
    dispatch({ type: constant.CHOOSE_FILTER, data: args });
}




export function handleScreenloadResponse(dispatch, response,state) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0],state));
        dispatch(change(constant.OUTBOUND_FILTER, 'flightnumber.carrierCode', response.results[0].carrierCode))
      

    }
}
export function screenLoadSuccess(data,state) {
    return { type: constant.SCREENLOAD_SUCCESS, data };
}

export function toggleFlightPanel(values) {
    const {dispatch} = values;
    dispatch( { type: constant.TOGGLE_FLIGHT_PANEL });
}

export function openAllMailbagsPopup(values){
    
    const {dispatch,getState} =values;
    const state = getState();
    const filterValues=state.filterReducer.filterValues
    const fromDate=filterValues.fromDate?filterValues.fromDate:''
    const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.airportCode?filterValues.airportCode:'';
    const carrierCode=filterValues.carrierCode ? filterValues.carrierCode:'';
    const flightNumber=filterValues.flightNumber ?filterValues.flightNumber:'';
    const flightDate = filterValues.flightDate ? filterValues.flightDate:'';
    const destination = filterValues.destination ? filterValues.destination:'';
    const assignTo = filterValues.assignTo ? filterValues.assignTo:'';
    var url = "mail.operations.ux.mailbagenquiry.defaultscreenload.do?fromScreen=mail.operations.mailoutbound&isPopup=true&assignedTo="+assignTo+'&fromDate='+fromDate+'&todate='+toDate+'&airport='+airportCode+'&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate+'&destination='+destination;
    var closeButtonIds = ['CMP_Operations_Shipment_UX_AwbEnquiry_close'];
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: 'All Mailbags'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function openAllContainersPopup(values){
    
    const {dispatch,getState} =values;
    const state = getState();
    const filterValues=state.filterReducer.filterValues;
    const fromDate=filterValues.fromDate?filterValues.fromDate:''
    const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.airportCode?filterValues.airportCode:'';
    const carrierCode=filterValues.carrierCode ? filterValues.carrierCode:'';
    const flightNumber=filterValues.flightNumber ?filterValues.flightNumber:'';
    const flightDate = filterValues.flightDate ? filterValues.flightDate:'';
    const destination = filterValues.destination ? filterValues.destination:'';
    const assignTo = filterValues.assignTo ? filterValues.assignTo:'';

    var url = "mail.operations.ux.containerenquiry.defaultscreenload.do?fromScreen=mail.operations.ux.mailoutbound&isPopup=true&assignedTo="+assignTo+'&fromDate='+fromDate+'&todate='+toDate+'&airport='+airportCode+'&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate+'&destination='+destination;
    var closeButtonIds = ['CMP_Mail_Operations_ContainerEnquiry_List'];
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: 'All Containers'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function openViewConsignmentPopup(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let index=args.index;
    let consignmentNumber =state.carditReducer.carditMailbags.results[index].consignmentNumber;
    let paCode =state.carditReducer.carditMailbags.results[index].paCode;
    var url = "mail.operations.ux.consignment.screenload.do?fromScreen=mail.operations.ux.mailoutbound&isPopup=true&conDocNo="+consignmentNumber+'&paCode='+paCode;
    var closeButtonIds = ['CMP_Mail_Operations_ListConsignment_Close'];
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: 'List Consignment'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function onExpandClick(values) { 
    const { dispatch, getState } = values;
    const state = getState();
    var url='';
    let filter={};
    let popupTitle=''
    var closeButtonIds='';
    const activeTab = state.commonReducer.activeMainTab;
    if(activeTab===constant.CARDIT_LIST) {
       filter = state.carditReducer.carditfilterValues;
       url = "mail.operations.ux.carditenquiry.defaultscreenload.do?fromScreen=mail.operations.ux.mailoutbound&isPopup=true&filterValues="+filter;
       popupTitle='Cardit Enquiry'
       closeButtonIds = ['CMP_Mail_Operations_CarditEnquiry_Close'];
    } else {
        filter = state.lyingListReducer.filterValues;
        url = "mail.operations.ux.mailbagenquiry.defaultscreenload.do?fromScreen=mail.operations.ux.mailoutbound&isPopup=true&filterValues="+filter;
        popupTitle='Mailbag Enquiry'
        closeButtonIds = ['CMP_Mail_Operations_MailEnquiry_Close'];
    }
   
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
        closeButtonIds: closeButtonIds,
        popupTitle: popupTitle
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

//Added by A-7929 as part of ICRD-327611
export function onCloseFunction() {
    // const { args, dispatch, getState } = values;
    // let state = getState();
    navigateToScreen('home.jsp', {});
    
}


export const warningHandler = (action, dispatch) => {
    switch (action.type) {
        case constant.DELEGATE_WARNING_ONOK:
            if (action.executionContext) {
                const warningCode = action.warningCode[0];
                const rowIndex=action.executionContext&&action.executionContext.args&&action.executionContext.args.rowIndex?
                action.executionContext.args.rowIndex:0;
                const currentDate=action.executionContext&&action.executionContext.args&&action.executionContext.args.currentDate?
                action.executionContext.args.currentDate:null;
                if (warningCode == "mailtracking.defaults.war.coterminus" ) {
                    const warningMapValue = { [warningCode]: 'Y' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }
                
 if (warningCode == "mail.operations.container.detachAWBwarning" ) {
dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }


if (warningCode ==  "mail.operations.securityscreeningwarning") {
                    const warningMapValue = { [warningCode]: 'Y' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                        dispatch(asyncDispatch(listDetails)()).then(() => {
                        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
                        });
                     })
                    });
                }
                if (warningCode == "mailtracking.defaults.war.latvalidation" ) {
                    dispatch(asyncDispatch(action.executionContext.functionRecord)({rowIndex:rowIndex,currentDate:currentDate,showWarning:'N',data: action.executionContext.args ? action.executionContext.args.data:null}))
                }
               if (warningCode == "mailtracking.defaults.warn.ulddoesnot.exists" ||
                    warningCode == "mailtracking.defaults.warn.uldisnotinairport" ||
                 warningCode == "mailtracking.defaults.warn.uldisnotinthesystem" ||
                 warningCode == "mailtracking.defaults.warn.uldisnotoperational" ||
                 warningCode == "mail.defaults.warning.uldisnotinthesystem" ||
                 warningCode == "mail.defaults.error.uldisnotinairport" ||
                 warningCode == "mail.defaults.warning.uldisnotinairport" ||
                 warningCode == "mail.defaults.error.uldislost" ||
                 warningCode == "mail.defaults.warning.uldislost" ||
                 warningCode == "mail.defaults.error.uldisnotoperational" ||
                 warningCode == "mail.defaults.warning.uldisnotoperational" ||
                 warningCode == "mail.defaults.error.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.error.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.warning.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.error.uldisnotinthesystem" ||
                 warningCode == "mailtracking.defaults.err.uldalreadyinuseatanotherport"||
                 warningCode == "mailtracking.defaults.uldalreadyassignedtoanotherClosedflight"

                ) {
                    if(action.executionContext&&action.executionContext.args){
                    action.executionContext.args.showWarning='N';
                    }
                    const warningMapValue = { [warningCode]: 'Y' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                    dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                }
                if(warningCode == "mail.operations.container.actualweight"){
                     const warningCode = action.warningCode;
                    if (warningCode[0] === "mail.operations.container.actualweight") {
                        dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                            dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                              dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
                            });
                    });
                  }
                }
                if(warningCode == "mail.operations.container.uldwarning"){
                    dispatch({ type: constant.ULD_CONVERSION, uldToBarrow:true });
                    dispatch(asyncDispatch(action.executionContext.functionRecord)({uldToBarrow:true,warningFlag:action.executionContext.args.warningFlag})).then(()=>{
                        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
                        });
                    });
                   
                     }
                     if(warningCode=="mail.operations.container.barrowwarning"){
                    dispatch({ type: constant.BULK_CONVERSION , barrowToUld:true });
                        dispatch(asyncDispatch(action.executionContext.functionRecord)({barrowToUld:true,warningFlag:action.executionContext.args.warningFlag})).then(()=>{
                             dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                              dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
                            });
                        });
                     }
                     if(warningCode == "mail.operations.mailbags.deletewarn"){
                        const warningCode = action.warningCode;
                       if (warningCode[0] === "mail.operations.mailbags.deletewarn") {
                           dispatch(dispatchAction(action.executionContext.functionRecord)(action.executionContext.args))
                        }
                     }
					 if(warningCode == "mail.operation.domesticmaildoesnotexistwarning"){
                        const warningMapValue = { [warningCode]: 'Y' };
                        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue })
                        dispatch(dispatchAction(action.executionContext.functionRecord)({rowIndex:rowIndex,active:action.executionContext&&action.executionContext.args&&action.executionContext.args.active?
                            action.executionContext.args.active:null}))
                        
                     }
                     if(warningCode == "mail.operation.domesticmaildoesnotexistwarningonsave"){
                        const warningMapValue = { [warningCode]: 'Y' };
                        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue })
                        dispatch(asyncDispatch(action.executionContext.functionRecord)()).then(() => {
                            dispatch(asyncDispatch(onSavemailbagDetailsforExcel)()).then((response) => {  
                              if(response.status === "success") {
                                 dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                                   dispatch(asyncDispatch(listmailbagsinContainers)());
                                })
                              }
                         })
                          })
                     }
                     if(action.warningCode == "mailtracking.defaults.reassignmail.pabuiltToNonPabuilt"){
                        const warningCode = action.warningCode;
                        const warningMapValue = { [warningCode]: 'Y' };
                        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                       if (warningCode[0] === "mailtracking.defaults.reassignmail.pabuiltToNonPabuilt") {
                           dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args,retainFlag:true})).then(()=>{
                           dispatch(asyncDispatch(listContainersinFlight)()).then((response)=>{
                            if(!isEmpty(response)) {
                              dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
                            }
                            })
                         })
                        }
                     }

                     if(action.warningCode == "mailtracking.defaults.reassignmail.pabuiltToPabuilt"){
                        const warningCode = action.warningCode;
                        const warningMapValue = { [warningCode]: 'Y' };
                        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                       if (warningCode[0] === "mailtracking.defaults.reassignmail.pabuiltToPabuilt") {
                           dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args,retainFlag:true})).then(()=>{
                            dispatch(asyncDispatch(listContainersinFlight)()).then((response)=>{
                             if(!isEmpty(response)) {
                               dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
                             }
                             })
                          })
                        }
                     }
                     if(action.warningCode == "mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt"){
                        const warningCode = action.warningCode;
                        const warningMapValue = { [warningCode]: 'Y' };
                        dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                       if (warningCode[0] === "mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt") {
                           dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args})).then(()=> {
                            dispatch(asyncDispatch(listDetails)()).then(() => {
                                dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                                dispatch(asyncDispatch(listmailbagsinContainers)());
                                })
                             })
                           })
                      }
                   }
                }
            break;
        case constant.DELEGATE_WARNING_ONCANCEL:
            if (action.executionContext) {
                const warningCode = action.warningCode[0];
                if (warningCode == "mailtracking.defaults.war.coterminus" ) {
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                }
                  if (warningCode == "mailtracking.defaults.warn.ulddoesnot.exists" ||
                    warningCode == "mailtracking.defaults.warn.uldisnotinairport" ||
                 warningCode == "mailtracking.defaults.warn.uldisnotinthesystem" ||
                 warningCode == "mailtracking.defaults.warn.uldisnotoperational" ||
                 warningCode == "mail.defaults.warning.uldisnotinthesystem" ||
                 warningCode == "mail.defaults.error.uldisnotinairport" ||
                 warningCode == "mail.defaults.warning.uldisnotinairport" ||
                 warningCode == "mail.defaults.error.uldislost" ||
                 warningCode == "mail.defaults.warning.uldislost" ||
                 warningCode == "mail.defaults.error.uldisnotoperational" ||
                 warningCode == "mail.defaults.warning.uldisnotoperational" ||
                 warningCode == "mail.defaults.error.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.error.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.warning.uldnotinairlinestock" ||
                 warningCode == "mail.defaults.error.uldisnotinthesystem"||
				 warningCode == "mailtracking.defaults.err.uldalreadyinuseatanotherport"				 
                ) {
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
                }
                  if(warningCode == "mail.operations.container.uldwarning" ||
                          warningCode == "mailtracking.defaults.uldalreadyassignedtoanotherClosedflight"){
                    dispatch(dispatchAction(revertUldConversion)());
                     }
			    if(warningCode == "mail.operation.domesticmaildoesnotexistwarning"){
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue })
                }
                if(warningCode == "mail.operation.domesticmaildoesnotexistwarningonsave"||warningCode =="mail.operations.securityscreeningwarning" ){
                    const warningMapValue = { [warningCode]: 'N' };
                    dispatch({ type: constant.SET_WARNING_MAP, warningMapValue })
                }
            }
            break;

            case "__DELEGATE_WARNING_ONCUSTOMBUTTON":
            if(action.warningCode == "mailtracking.defaults.reassignmail.pabuiltToNonPabuilt"){
                const warningCode = action.warningCode;
                const warningMapValue = { [warningCode]: 'Y' };
                dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
               if (warningCode[0] === "mailtracking.defaults.reassignmail.pabuiltToNonPabuilt") {
                   dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args,paContainerNumberUpdate:true})).then(()=> {
                    dispatch(asyncDispatch(listContainersinFlight)()).then((response)=>{
                        if(!isEmpty(response)) {
                          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
                        }
                    })
                   })
                }
             }

             if(action.warningCode == "mailtracking.defaults.reassignmail.pabuiltToPabuilt"){
                const warningCode = action.warningCode;
                const warningMapValue = { [warningCode]: 'Y' };
                dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
               if (warningCode[0] === "mailtracking.defaults.reassignmail.pabuiltToPabuilt") {
                   dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args,paBuiltFlagUpdate:true})).then(()=>{
                    dispatch(asyncDispatch(listContainersinFlight)()).then((response)=>{
                        if(!isEmpty(response)) {
                          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
                        }
                    })
                   })
                }
             }

             if(action.warningCode == "mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt"){
                const warningCode = action.warningCode;
                const warningMapValue = { [warningCode]: 'Y' };
                dispatch({ type: constant.SET_WARNING_MAP, warningMapValue });
               if (warningCode[0] === "mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt") {
                dispatch(asyncDispatch(action.executionContext.functionRecord)({...action.executionContext.args,paContainerNumberUpdate:true})).then(()=>{
                    dispatch(asyncDispatch(listDetails)()).then(() => {
                        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                        dispatch(asyncDispatch(listmailbagsinContainers)());
                        })
                     })
                })
               
             }
            }
           
          break;
        default:
            break;
    }
}
