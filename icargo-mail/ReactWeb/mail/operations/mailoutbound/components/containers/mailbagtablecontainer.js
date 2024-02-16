import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import MailbagDetailsPanel from '../panels/mailbagspanel/MailbagDetailsPanel.jsx'
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { checkCloseFlight} from '../../actions/containeractions'
import { onMailBagAction,listmailbagsindsnview ,onSavemailbagDetails,validateNewMailbagAdded,
  populateMailbagId,doReturnMail,onImportMailbags,updateAddMailbagForm,closeMailbagPopup,
  onSaveRemarks,onSaveScanTime,deleteMailBags,validateFlight,listContainers,onreassignMailSave, 
  openHistoryPopup, navigateToOffload, allMailbagIconAction,onReassignExistingmailbags,performDSNAction, onApplyMailbagSort,

  onClearMailbagFilter, onApplyMailbagFilter,saveNewContainer,isdomestic,pabuiltUpdate,onApplyDsnFilter,onClearDsnFilter,populateMailBagIdForExcelView,onSavemailbagDetailsforExcel,validateFlightDetailsForTransfer,listContainerForTransferPanel,clearAddContainerPopoverForTransfer,onTransferMailSave } from '../../actions/mailbagactions.js'

  


import { addRow,deleteRow} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
//import {onMailBagAction,listContainers, validateFlight,doReturnMail,onMailRowSelect,saveNewContainer,onreassignMailSave} from '../../../mailbagenquiry/actions/detailsaction.js'
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util'
import { listContainersinFlight,listmailbagsinContainers} from '../../actions/flightlistactions.js'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {listDetails} from '../../actions/filteraction.js'
import * as constant from '../../constants/constants';
import { reset } from 'redux-form';
import { applyCarditFilter} from '../../actions/carditaction.js';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { onApplyMailbagDSNFilter, onClearMailbagDSNFilter,onClickImportPopup} from '../../actions/mailbagactions.js'

const getNewMailbags= (state) =>(state.mailbagReducer && state.mailbagReducer.selectedMailbags ?
  state.mailbagReducer.selectedMailbags:[]);
const getNewMailbagsDetails=createSelector([getNewMailbags], (results) =>{
let mailData=[];
results.forEach(function(element) {
let mail={...element,mailbagId:element.mailbagId,ooe:element.mailbagId===29?element.mailbagId.substring(0, 6):element.ooe?element.ooe:"",doe:element.mailbagId===29?element.mailbagId.substring(6, 12):element.doe?element.doe:"",
mailCategoryCode:element.mailbagId===29?element.mailbagId.substring(12, 13):element.mailCategoryCode?element.mailCategoryCode:"",mailSubclass:element.mailbagId===29?element.mailbagId.substring(13, 15):element.mailSubclass?element.mailSubclass:"",
year:element.mailbagId===29?element.mailbagId.substring(15, 16):element.year?element.year:"",despatchSerialNumber:element.mailbagId===29?element.mailbagId.substring(16, 20):element.despatchSerialNumber?element.despatchSerialNumber:"",
receptacleSerialNumber:element.mailbagId===29?element.mailbagId.substring(20, 23):element.receptacleSerialNumber?element.receptacleSerialNumber:"",
highestNumberedReceptacle:element.mailbagId===29?element.mailbagId.substring(23, 24):"9",registeredOrInsuredIndicator:element.mailbagId===29?element.mailbagId.substring(24, 25):"9",
mailbagWeight:{ displayValue:element.weight.displayValue,roundedDisplayValue: element.weight.roundedDisplayValue,displayUnit:element.weight.displayUnit,unitSelect:"true"}
}
mailData.push(mail);
}, this);
return mailData;
});
const mapStateToProps = (state) => {
  return {
    mailbagslist: state.mailbagReducer.mailBags?state.mailbagReducer.mailBags.results:[],
    mailbags:state.mailbagReducer.mailBags,
    mailbaglistdsnview: state.mailbagReducer.mailBagsdsnView,
    activeMailbagTab: state.mailbagReducer.activeMailbagTab,
    selectedContainer:state.mailbagReducer.selectedContainer,
    flightActionsEnabled: state.filterReducer.flightActionsEnabled,
    //to open add mailbag
    selectedMailbagsIndex:state.mailbagReducer.selectedMailbagsIndex,
    addMailbagShow: state.mailbagReducer.addMailbagShow,
    addModifyFlag:state.mailbagReducer.addModifyFlag,
    containerDetails: state.containerReducer.flightContainers,
    oneTimeValues:state.commonReducer.oneTimeValues,
    mailbagOneTimeValues: state.mailbagReducer.mailbagOneTimeValues,
    selectedMailbags: state.mailbagReducer.selectedMailbags,
    damageDetails: state.listmailbagsreducer.damageDetails,
    
    selectedMail:state.mailbagReducer.selectedMail,
    selectedMailbag:state.mailbagReducer.selectedMailbag,
    damageDetail:state.mailbagReducer.damageDetail,
    
    activeMailbagAddTab:state.mailbagReducer.activeMailbagAddTab,
    showImportPopup:state.mailbagReducer.showImportPopup,
    containerJnyID:state.mailbagReducer.containerJnyID,
    importedMailbagDetails:state.mailbagReducer.importedMailbagDetails,
    showReassign: state.mailbagReducer.showReassign,
    carrierCode:state.commonReducer.flightCarrierCode,
    newMailbags:state.form.newMailbagsTable && state.form.newMailbagsTable.values && 
    state.form.newMailbagsTable.values.newMailbagsTable && state.form.newMailbagsTable.values.newMailbagsTable.length>0 ?
    state.form.newMailbagsTable.values.newMailbagsTable : getNewMailbagsDetails(state),
    remarkspopup:state.mailbagReducer.remarkspopup,
    scanTimePopup:state.mailbagReducer.scanTimePopup,
    currentTime:state.mailbagReducer.currentTime,
    postalCodes:state.mailbagReducer.postalCodes,
    mailbagReturnPopup:state.mailbagReducer.mailbagReturnPopup,
    mailbagReturnDamageFlag:state.mailbagReducer.mailbagReturnDamageFlag,
    initialValues:{scanDate:state.mailbagReducer.currentDate,scanTime:state.mailbagReducer.currentTime},
    //mailbag actions integrate
    mailcontainerDetails:state.listmailbagsreducer.containerDetails,
    reassignContainers:state.mailbagReducer.reassignContainers,
    flightValidation: state.listmailbagsreducer.flightValidation,
    currentDate: state.mailbagReducer.currentDate?state.mailbagReducer.currentDate:getCurrentDate(),
    defaultWeightUnit:state.mailbagReducer.defaultWeightUnit,
    previousRowWeight:state.mailbagReducer.previousRowWeight,
    updatedMailbags:state.mailbagReducer.updatedMailbags,
    mailBagTableFilterInitialValues:state.mailbagReducer.tableFilter?state.mailbagReducer.tableFilter :{},
    dsnFilterValues: state.mailbagReducer.dsnFilterValues?state.mailbagReducer.dsnFilterValues :{},
    flightCarrierflag:state.commonReducer.flightCarrierflag,
    existingMailbags:state.mailbagReducer.existingMailbags,
    existingMailbagPresent:state.mailbagReducer.existingMailbagPresent,
    containersPresent: state.containerReducer.flightContainers&&state.containerReducer.flightContainers.results&&state.containerReducer.flightContainers.results.length>0?false:true,
    reassignFilterType:state.mailbagReducer.reassignFilterType,
    isValidFlight:state.mailbagReducer.isValidFlight,
    destination:state.mailbagReducer.destination,
    uldDestination: state.mailbagReducer.destination,
    pou:state.mailbagReducer.pou,
    pous:state.mailbagReducer.pous,
    showTransfer: state.mailbagReducer.showTransfer,
    reassignDefaultValues: {
      uplift: state.commonReducer.airportCode, 
      reassignFilterType:state.mailbagReducer.reassignFilterType, 
      scanDate:state.mailbagReducer.currentDate?state.mailbagReducer.currentDate:getCurrentDate(), 
      scanTime:state.mailbagReducer.currentTime,
      pou:state.mailbagReducer.pou,
      carrierCode:state.mailbagReducer.carrierCode&&state.mailbagReducer.carrierCode.length>0?state.mailbagReducer.carrierCode:state.commonReducer.flightCarrierCode, 
      destination:state.mailbagReducer.destination, 
      uldDestination: state.mailbagReducer.destination,
      flightnumber:state.mailbagReducer.flightnumber
    },

    transferDefaultValues:{
      uplift: state.commonReducer.airportCode,
      carrier:state.commonReducer.flightCarrierCode,
      transferFilterType :state.mailbagReducer.transferFilterType,
      scanDate:state.mailbagReducer.currentDate?state.mailbagReducer.currentDate:getCurrentDate(), 
      mailScanTime:state.mailbagReducer.currentTime,
      uldDestination :state.mailbagReducer.destination,
      destination: state.mailbagReducer.destination,
      flightnumber:state.mailbagReducer.flightnumber
    },
    transferContainers:state.mailbagReducer.transferContainers,
    addContainerButtonShow:state.mailbagReducer.addContainerButtonShow,
    pousForTransfer: state.mailbagReducer.flightDetailsToBeReused?buildPous(state.mailbagReducer.flightDetailsToBeReused.flightRoute,state.mailbagReducer.flightDetailsToBeReused.pol):[],
    ownAirlineCode:state.mailbagReducer.ownAirlineCode,
    partnerCarriers:state.mailbagReducer.partnerCarriers,
    disableForModify:state.mailbagReducer.disableForModify,
    initialDsnValues:state.mailbagReducer.dsnFilterValues.length?null :{...state.mailbagReducer.dsnFilterValues}

  }
}





const mapDispatchToProps = (dispatch) => {
  return {

    changeMailTab: (currentTab) => {
      dispatch(asyncDispatch(listmailbagsindsnview)({ currentTab: currentTab }));
    },
    listMailbagsInDSNView:(displayPage) =>{
      dispatch(asyncDispatch(listmailbagsindsnview)({ displayPage: displayPage }));
    },

    saveSelectedMailbagIndex:(indexes)=> {
      dispatch({ type: constant.SAVE_SELECTED_MAILBAGS_INDEX,indexes})
    },
    
    //for row specific mailbag action
    mailbagAction:(data)=>{
      dispatch(asyncDispatch(checkCloseFlight)({index:data.index,mode:data.actionName,actions:'MAILBAG_LEVEL'})).then((response) => {
          if (isEmpty(response.errors)) {
             dispatch(asyncDispatch(onMailBagAction)({index:data.index,mode:data.actionName})).then(() => {
              if(data.actionName===constant.DELETE) {
                  dispatch(dispatchAction(deleteMailBags)())
              }
              if(data.actionName===constant.MODIFY_MAILBAG){
                dispatch(asyncDispatch(isdomestic)({rowIndex:'0'})).then((response)=>{
                  if(response.isDomesticFlag === 'Y'){
                    dispatch(asyncDispatch(populateMailbagId)({rowIndex:'0',active:"newMailbagsTable.0.mailbagId"}))
                  }
                })
              }
         })
        }
     })
    },
    detachAWB:(data) => {
    dispatch(requestWarning([{code:"mail.operations.container.detachAWBwarning",description:"Are you sure you want Detach AWB?"}],
    {functionRecord:onMailBagAction, args:{index:data.index,mode:data.actionName}}));
    },
     
    mailbagMultipleSelectionAction:(data)=>{
        if(data.actionName===constant.REASSIGN) {
          dispatch(asyncDispatch(checkCloseFlight)({actions:'MAILBAG_LEVEL'})).then((response) => {
          if (response.status==="success") {
        dispatch(asyncDispatch(onMailBagAction)({mode:data.actionName}));
          }
          })
        }else{
         dispatch(asyncDispatch(onMailBagAction)({mode:data.actionName})); 
        }
    },
    
    onLoadAddMailbagPopup: (functionName) => {
     // if(functionName === 'MODIFY_MAILBAG') {
      //  dispatchdispatch(dispatchAction(updateSelectedMailbags)());
     // }
     dispatch(asyncDispatch(checkCloseFlight)({actions:'MAILBAG_LEVEL'})).then((response) => {
      if (response.status==="success") {
          dispatch(dispatchAction(onMailBagAction)({mode:functionName}));
      }})
    //dispatch({ type: 'SCREENLOAD_ADD_MAILBAG' });
    },
    //on pagination of mailbags
    getMailbagsNewPage :(displayPage) =>{
      dispatch(asyncDispatch(listmailbagsinContainers)({'displayPage':displayPage,'isPagination':true}))
    },
    closeAddMailbagPopup: () => {
      dispatch(dispatchAction(closeMailbagPopup)());
      //dispatch({ type: 'ADD_MAILBAG_POPUPCLOSE' })
    },
    onClickImportPopup:() =>{
      dispatch(asyncDispatch(onClickImportPopup)())
    },
    onCloseImport:() =>{
      dispatch({ type: constant.CLOSE_IMPORT_POPUP })
    },
    onCloseRemarksPopup:()=> {
      dispatch({type:constant.REMARKS_CLOSE})
    },
    oncloseScanTimePopup:()=>{
      dispatch({type:constant.SCAN_TIME_CLOSE})
    },
    onImportMailbags:() =>{
      dispatch(asyncDispatch(onImportMailbags)());
    },
    closeReturnPopup: () => {
      dispatch({type:constant.RETURN_POPUP_CLOSE });
    },
   

    onMailBagAction: (type, selectedMail) => {
      
      if (type === constant.VIEW_DAMAGE) {
        dispatch(asyncDispatch(onMailBagAction)({ selectedMail, action: 'VIEW_DAMAGE' }))
      } else if (type === constant.DELIVER_MAIL) {
        dispatch(asyncDispatch(onMailBagAction)({ selectedMail, action: 'DELIVER_MAIL' }))
      } else if (type === constant.MODIFY_MAILBAG){
        dispatch(dispatchAction(onMailBagAction)({ selectedMail, action: type }))
      }

    },

   
    changeAddMailbagTab: (currentTab) => {
      dispatch({type:constant.CHANGE_ADD_MAILBAG_TAB,currentTab: currentTab });
    },
    addRow: (values,currentDate,currentTime,defaultWeightUnit,previousRowWeight) =>{
    
    let validObject = validateNewMailbagAdded(values)
    if (!validObject.valid) {
      dispatch(requestValidationError(validObject.msg, ''));
    }
    else {
       dispatch(addRow(constant.NEW_MAILBAGS_TABLE,{mailCategoryCode:'A',registeredOrInsuredIndicator:0,highestNumberedReceptacle:0,mailbagVolume:0,scannedDate:currentDate,scannedTime:currentTime,mailbagWeight:{displayValue : 0, roundedDisplayValue: '0',displayUnit:previousRowWeight,unitSelect:"true"},ooe:values.length!==0?values[values.length-1].ooe:null,doe:values.length!==0?values[values.length-1].doe:null,despatchSerialNumber:values.length!==0?values[values.length-1].despatchSerialNumber:null,mailSubclass:values.length!==0?values[values.length-1].mailSubclass:null,year:values.length!==0?values[values.length-1].year:null,mailCategoryCode:values.length!==0?values[values.length-1].mailCategoryCode:null}
      ))
    }
  },
    onDeleteRow: (rowIndex) => {
      dispatch(deleteRow(constant.NEW_MAILBAGS_TABLE, rowIndex))
    },

    populateMailbagId:(rowIndex) =>{
      dispatch(asyncDispatch(isdomestic)({rowIndex:rowIndex})).then((response)=>{
        if(response.isDomesticFlag === 'Y'){
          dispatch(asyncDispatch(populateMailbagId)({rowIndex:rowIndex,active:response.active}))
        }
        else if (response.isDomesticFlag === 'N'&&response.active==='mailbagId'){
          dispatch(asyncDispatch(populateMailbagId)({rowIndex:rowIndex,active:response.active}))
        }
        else{
          dispatch(dispatchAction(populateMailbagId)({rowIndex:rowIndex,active:response.active}))
        }
        
      })
     
    },
    saveNewContainer: (fromScreen) => {
      dispatch(asyncDispatch(saveNewContainer)({showWarning:'Y',fromScreen}));
    },
    onSavemailbagDetails: (values,activeTab) => {
    
      let validObject = validateNewMailbagAdded(values)
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else {
        if( activeTab==='NORMAL_VIEW') {
          dispatch(asyncDispatch(onSavemailbagDetails)()).then((response) => {
            
            if(response.status === "success") {
              dispatch(asyncDispatch(listDetails)()).then(() => {
               dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                 dispatch(asyncDispatch(listmailbagsinContainers)());
             })
            })
            }
            if(response.status === "success_embargo") {
              dispatch(asyncDispatch(listDetails)()).then(() => {
              dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                dispatch(asyncDispatch(listmailbagsinContainers)({embargoInfo:true,mailbagsDSNDisplayPage:'1',mailbagsDisplayPage:'1'}));
             })
             })
           }
       })
     }
     else{
      dispatch(asyncDispatch(populateMailBagIdForExcelView)()).then((response) => {
        if(response.status === "success") {
        dispatch(asyncDispatch(onSavemailbagDetailsforExcel)()).then((response) => {
            
          if(response.status === "success") {
            dispatch(asyncDispatch(listDetails)()).then(() => {
             dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
               dispatch(asyncDispatch(listmailbagsinContainers)());
            })
          })
          }
          if(response.status === "success_embargo") {
            dispatch(asyncDispatch(listDetails)()).then(() => {
            dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
              dispatch(asyncDispatch(listmailbagsinContainers)({embargoInfo:true,mailbagsDSNDisplayPage:'1',mailbagsDisplayPage:'1'}));
           })
           })
         }
     })
     }
      })
     }
    }
    
      
    },
    onSavemailbagActions: (actionName) => {
      if(actionName===constant.REMARKS) {
       dispatch(asyncDispatch(onSaveRemarks)()).then(() => {
             dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
               dispatch(asyncDispatch(listmailbagsinContainers)());
           })
        })
      }
      if(actionName==='CHANGE SCAN TIME') {
        dispatch(asyncDispatch(onSaveScanTime)()).then(() => {
              dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                dispatch(asyncDispatch(listmailbagsinContainers)());
            })
         })
       }
     },
     doReturnMail: (selectedMail) => {

      dispatch(asyncDispatch(doReturnMail)({ selectedMail, action: "DO_RETURN" })).then(() => {
        dispatch(asyncDispatch(listDetails)()).then(() => {
        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
          dispatch(asyncDispatch(listmailbagsinContainers)());
        })
      })
    })
  },
  // mailbag actions integration
  listContainer: (selectmails,filterType,fromScreen) => {
    //Added by A-8176 as part of ICRD-335975
     if(filterType === 'F')   {
      dispatch(asyncDispatch(validateFlight)({action: constant.VALIDATE_FLIGHT, fromScreen: fromScreen })).then((response) => {
        dispatch(asyncDispatch(listContainers)({ response, action: constant.LIST_REASSIGN_CONTAINER, fromScreen: fromScreen }))
     })
    }
     else {
      dispatch(asyncDispatch(listContainers)({ action: constant.LIST_REASSIGN_CONTAINER, fromScreen: fromScreen }))
    }
  },
  reassignSave: (containerIndex) => {
    let customButtons = [
      {
      className: 'Primary',
      text: 'No',
      callBackFun:onreassignMailSave
      }
    ]
      dispatch(asyncDispatch(onreassignMailSave)({ containerIndex, action: "DO_REASSIGN",customButtonsForWarning:customButtons }))
     .then((response)=>{
      if (isEmpty(response.errors)) {
          dispatch(asyncDispatch(listDetails)()).then(() => {
        dispatch(asyncDispatch(listContainersinFlight)({ containerDisplayPage:'1'})).then((response)=>{
         if(!isEmpty(response)) {
           dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
         }
         })
         })
        }

      })
    },
    

    closeReassign: () => {
      dispatch({type:constant.CLEAR_REASSIGN_FORM});
      dispatch({type:constant.CLOSE_POP_UP });
    },
    openHistoryPopup: (mailbag) => {
      dispatch(dispatchAction(openHistoryPopup)({mailbag}));
    },
    navigateToOffload: (mailbag) => {
      dispatch(dispatchAction(navigateToOffload)( mailbag))
    },
    allMailbagIconAction:()=>{
      dispatch(dispatchAction(allMailbagIconAction)())
    },
    //for row specific dsn action
     dsnAction:(data)=>{
       dispatch(asyncDispatch(checkCloseFlight)({index:data.index,mode:data.actionName,actions:'MAILBAG_LEVEL'})).then((response) => {
         if (isEmpty(response.errors)) {
           if(data.actionName=="DSN_DETACH_AWB")
         {
          dispatch(requestWarning([{code:"mail.operations.container.detachAWBwarning", description:"Are you sure you want Detach AWB?"}],
          {functionRecord:performDSNAction, args:{index:data.index,mode:data.actionName}}));
         }
         else{
          dispatch(asyncDispatch(performDSNAction)({index:data.index,mode:data.actionName}));
         }
         }
      })
   
    },
    dsnMultipleSelectionAction:(data)=>{
      
     dispatch(asyncDispatch(performDSNAction)({mode:data.actionName}));
     
    },
    onCloseExistingMailbag:() =>{
      dispatch({type:constant.EXISTING_MAILBAG_POPUP_CLOSE });
    },
    reassignExistingMailbags:()=>{
      dispatch(asyncDispatch(onReassignExistingmailbags)({action:constant.SAVE_REASSIGN})).then((response) => {
        if(response.status === "success") {
           dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
             dispatch(asyncDispatch(listmailbagsinContainers)());
          })
        }
   })
  },
  reassignMailbagsCancel:()=>{
    dispatch(asyncDispatch(onReassignExistingmailbags)({action:constant.CANCEL_REASSIGN})).then((response) => {
      if(response.status === "success") {
         dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
           dispatch(asyncDispatch(listmailbagsinContainers)());
        })
      }
 })
 },
onApplyMailbagSort: (sortBy, sortByItem) => {
  dispatch(dispatchAction(onApplyMailbagSort)({ sortBy, sortByItem }))
},
onApplyMailbagFilter:()=>{
  dispatch(asyncDispatch(onApplyMailbagFilter)());
},
onClearMailbagFilter:()=>{
  dispatch(dispatchAction(onClearMailbagFilter)());
},
onApplyMailbagDSNFilter:()=>{
  dispatch(asyncDispatch(listmailbagsindsnview)({ currentTab: constant.DSN_VIEW, displayPage:1 , applyFilter:true}));
},
onClearMailbagDSNFilter:()=>{
  dispatch(dispatchAction(onClearMailbagDSNFilter)());
},
clearReassignForm:(reassignFilterType)=>{
  dispatch(reset('reassignForm'));
  dispatch({type:constant.CLEAR_REASSIGN_FORM, reassignFilterType: reassignFilterType });
},
pabuiltUpdate:(barrowCheck)=>{
  dispatch(dispatchAction(pabuiltUpdate)({barrowCheck:barrowCheck}))    
},

clearAddContainerPopover:()=>{
  dispatch(reset('reassignForm'));
},

clearTransferPanel:(transferFilterType)=>{
  dispatch(reset('transferMailForm'));
  dispatch({type: constant.TRANSFER_POPUP_CLEAR, transferFilterType:transferFilterType,
    transferContainers:{},flightDetailsToBeReused:{},addContainerButtonShow:true});
},
listTransfer:(data)=>{
  dispatch(asyncDispatch(validateFlightDetailsForTransfer)({data,'action':constant.LIST_TRANSFER})).then((response)=>{
        dispatch(asyncDispatch(listContainerForTransferPanel)({...response,'action':constant.LIST_TRANSFER}))})
},
clearAddContainerPopoverForTransfer:()=>{
  dispatch(dispatchAction(clearAddContainerPopoverForTransfer)());
},
closeTranferPopup:()=>{
    dispatch({type: constant.TRANSFER_SAVE});
    dispatch({type: constant.LIST_CONTAINERS_POPUP,  
                   containerVosToBeReused:{},flightDetailsToBeReused:{},addContainerButtonShow:true});
 },
 transferSave: (containerIndex) => {
  dispatch(asyncDispatch(onTransferMailSave)({ containerIndex})).then(()=>{
    dispatch(asyncDispatch(listContainersinFlight)({ containerDisplayPage:'1'})).then((response)=>{
      if(!isEmpty(response)) {
        dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
      }
     })
  })

},
getNextPageContainerForTransfer:(displayPage)=>{
  dispatch(asyncDispatch(validateFlightDetailsForTransfer)({'action':constant.LIST_TRANSFER,'displayPage':displayPage})).then((response)=>{
    dispatch(asyncDispatch(listContainerForTransferPanel)({...response,'action':constant.LIST_TRANSFER}))})
  },

onApplyDsnFilter:(filter)=>{
    dispatch(dispatchAction(onApplyDsnFilter)(filter))
},
onClearDsnFilter:(filter)=>{
    dispatch(dispatchAction(onClearDsnFilter)(filter))


},
saveSelectedContainersIndex:(indexes)=> {
  dispatch({ type: constant.SAVE_SELECTED_CONTAINER_INDEX,indexes})
},
  
 }
}

const buildPous=(data,port)=>{
  if(data!=undefined){
  let destArray=data.split('-');
        destArray.reverse();
        const index = destArray.indexOf(port);
        if(index===0){
          destArray.splice(index, 1);
        }else{
          destArray.splice(index);
        }
        let dest=[];
        destArray.forEach(function(element) {
            dest.push({label:element,value:element});
        }, this);
        return dest.reverse();
      }
  };


const MailBagTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(MailbagDetailsPanel)

export default MailBagTableContainer