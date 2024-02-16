import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ContainerDetailsPanel from '../panels/containerpanel/ContainerDetailsPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { convertUldToBarrow,checkCloseFlight,onListContainer, onSaveContainer,clickPABuilt,performContainerAction,listAwbDetailsAction,saveAwbDetailsAction,clearAWB,clearAttachRouting,onApplyContainerFilter,onClearContainerFilter,listAttachRoutingAction,saveAttachRoutingAction,reassignContainerAction,validateContainerForReassign,unassignContainer,updateContainerSortVariables,assignToContainers,validateAttachRoutingForm, navigateToOffload,validateContainerOffload,navigateActions, allContainerIconAction,populateDestForBULKContainer,populateDestForBarrow, onApplyContainerSort, listFlightDetailsForReassign, validateForm, fetchFlightCapacityDetails, reassignContainerToFlightAction, clearReassignFlightPanel,paBuiltUpdate,markUnmarkULDFullIndicator, printuldtag} from '../../actions/containeractions'
import { listContainersinFlight,listmailbagsinContainers} from '../../actions/flightlistactions.js'
import {listDetails} from '../../actions/filteraction.js'
import { updateScanTime, assignCarditMails, closeAssignScanTimePopup, continueAssignToContainer,onClearContainerFilterPanel,transferContainerAction,clearTransferFlightPanel,transferContainerToFlightAction } from '../../actions/containeractions'
 //import {listContainersinFlight,listmailbagsinContainers,flightCloseAction,performFlightScreenAction,getContainerDetails,clickPreadviceOK,clickgenManifestPrint,onchangePrintType} from '../../actions/flightlistactions.js'
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import { applyCarditFilter } from '../../actions/carditaction.js';
import {applyLyingListFilter} from '../../actions/lyinglistaction.js'
import * as constant from '../../constants/constants';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



const getSelectedCarditMails = (state) => state.carditReducer.selectedCarditIndex;
const getCarditMails = (state) => state.carditReducer.carditMailbags;
const getSelectedLyinglistMails = (state) => state.lyingListReducer.selectedLyinglistIndex;
const getLyinglistMails = (state) => state.lyingListReducer.lyinglistMailbags;
const getSelectedDeviationMails = (state) => state.deviationListReducer.selectedDeviationList;
const getDeviationMails = (state) => state.deviationListReducer.deviationlistMailbagsArray;
const getScanTimeDefaultValue = createSelector([getSelectedCarditMails, getCarditMails, 
  getSelectedLyinglistMails, getLyinglistMails,
  getSelectedDeviationMails, getDeviationMails],
    (selectedCarditMails, carditMailbags,selectedLyinglistMails, lyinglistMails, selectedDeviationMails, deviationMails) => {  
        let scanDate = '';
        let scanTime = '';
        if(selectedCarditMails.length===1){
          scanDate = carditMailbags.results[selectedCarditMails[0]].scannedDate;
          scanTime = carditMailbags.results[selectedCarditMails[0]].scannedTime;
        }else  if(selectedLyinglistMails.length===1){
          scanDate = lyinglistMails.results[selectedLyinglistMails[0]].scannedDate;
          scanTime = lyinglistMails.results[selectedLyinglistMails[0]].scannedTime;
        }
        else if(selectedDeviationMails.length ===1) {
          const found = deviationMails.find(element => element.mailSequenceNumber == selectedDeviationMails[0]);
          if(found) {
            scanDate = found.scannedDate;
            scanTime = found.scannedTime;
          }       
        } 
        return {
          scanDate:scanDate,
          scanTime:scanTime
        };     
    });
const mapStateToProps = (state) => {

  return {
    containerlist: state.containerReducer.flightContainers?state.containerReducer.flightContainers.results:[],
    containers:state.containerReducer.flightContainers,
    carditmailbagcount: state.carditReducer.carditmailbagcount,
    lyingmailbagcount:state.lyingListReducer.lyingmailbagcount,
    selectedContCount: state.containerReducer.selectedContCount,
    mailAcceptance: state.containerReducer.mailAcceptance,
    selectedContainerIndex:state.containerReducer.selectedContainerIndex,
    selectedContainer:state.mailbagReducer.selectedContainer,
    containerinerOneTimeValues: state.containerReducer.containerinerOneTimeValues,
    carditMailbags:state.carditReducer.carditMailbags,
    lyinglistMailbags:state.lyingListReducer.lyinglistMailbags,
    activeMainTab:state.commonReducer.activeMainTab,
    selectedCarditIndex:state.carditReducer.selectedCarditIndex,
    selectedLyinglistIndex:state.lyingListReducer.selectedLyinglistIndex,
    selectedDeviationList: state.deviationListReducer.selectedDeviationList,
    //to open add containers
    containerActionMode:state.containerReducer.containerActionMode,
    enableContainerSave:state.containerReducer.enableContainerSave,
    addContainerShow:state.containerReducer.addContainerShow,
    wareHouses:state.filterReducer.wareHouses,
    PABuiltChecked:state.containerReducer.PABuiltCheckFlag,
    displayMode:state.commonReducer.displayMode, 
    allMailBags:state.mailbagReducer.allMailBags,
    initialValues:{...state.containerReducer.selectedContainerforModify,airportCode:state.commonReducer.airportCode,
     'containerNumber':state.containerReducer.selectedContainerforModify&&state.containerReducer.selectedContainerforModify.containerNumber?state.containerReducer.selectedContainerforModify.containerNumber:'',
    },
    flightActionsEnabled: state.filterReducer.flightActionsEnabled,
    tableFilter:state.containerReducer.tableFilter,
    //integrate
    isValidationSuccess:state.containerReducer.isValidationSuccess,
    attachClose:(state.containerReducer.attachClose)?state.containerReducer.attachClose:false,
    attachawbListSuccess:state.containerReducer.attachawbListSuccess,
    attachAwbOneTimeValues:state.containerReducer.attachAwbOneTimeValues,
    attachAwbDetails:(state.containerReducer.attachAwbDetails)?
    {...state.containerReducer.attachAwbDetails,weightUnit:'K',
          awbNumber:{shipmentPrefix:state.containerReducer.attachAwbDetails.shipmentPrefix?
                          state.containerReducer.attachAwbDetails.shipmentPrefix:'',
                  masterDocumentNumber:state.containerReducer.attachAwbDetails.documentNumber?
                          state.containerReducer.attachAwbDetails.documentNumber:''}}:{},
attachRoutingInitialValues:{
                      'onwardRouting':state.containerReducer.attachRoutingDetails.onwardRouting?state.containerReducer.attachRoutingDetails.onwardRouting:[],
                      consignemntNumber:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.consignemntNumber:null,
                      paCode:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.paCode:null,
                      consignmentDate:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.consignmentDate:null,
                      consignmentType:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.consignmentType:null,
                      remarks:(state.containerReducer.attachRoutingDetails.remarks)?state.containerReducer.attachRoutingDetails.remarks:null,
                    },
containerDetailsToBeReused:(state.containerReducer.containerDetailsToBeReused)?state.containerReducer.containerDetailsToBeReused:{},
attachRoutingFilter: state.form.attachRoutingForm,
attachRoutingClose:(state.containerReducer.attachRoutingClose)?state.containerReducer.attachRoutingClose:null,
attachRoutingDetails:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails:{},
attachroutingListSuccess:state.containerReducer.attachroutingListSuccess,
attachRoutingOneTimeValues:state.containerReducer.attachRoutingOneTimeValues,
showReassignFlag:state.containerReducer.showReassignFlag,
showReassignFlightFlag:state.containerReducer.showReassignFlightFlag,
currentDate: getCurrentDate(),
flightCarrierflag: state.filterReducer.filterValues && 
                    state.filterReducer.filterValues.filterType ? state.filterReducer.filterValues.filterType:state.commonReducer.flightCarrierflag,
scanDate:state.containerReducer.scanDate,
uldToBarrowAllow:state.commonReducer.uldToBarrowAllow,
scanTime:state.containerReducer.scanTime,
flightDetails:state.containerReducer.flightDetails ?state.containerReducer.flightDetails.results:[],
flightDetailsPage:state.containerReducer.flightDetails ?state.containerReducer.flightDetails:{},
oneTimeValues: state.commonReducer.oneTimeValues,
flightCarrierCode: state.commonReducer.flightCarrierCode,
pou:state.containerReducer&&state.containerReducer.containers.length>0&&state.containerReducer.containers[0]&&state.containerReducer.containers[0].pou?state.containerReducer.containers[0].pou:'',
destination:state.containerReducer.selectedContainer?state.containerReducer.selectedContainer.destination:(state.containerReducer&&state.containerReducer.containers.length>0&&state.containerReducer.containers[0]&&state.containerReducer.containers[0].pou?state.containerReducer.containers[0].pou:(state.containerReducer&&state.containerReducer.containers.length>0&&state.containerReducer.containers[0]&&state.containerReducer.containers[0].destination?state.containerReducer.containers[0].destination:'')),
finalDestination:state.containerReducer.selectedContainer?state.containerReducer.selectedContainer.finalDestination:(state.containerReducer&&state.containerReducer.containers.length>0&&state.containerReducer.containers[0]&&state.containerReducer.containers[0].pou?state.containerReducer.containers[0].pou:''),
reassignFromDate:state.containerReducer.reassignFromDate,
reassignToDate:state.containerReducer.reassignToDate,
actualWeight:state.containerReducer.selectedContainer?state.containerReducer.selectedContainer.actualWeight:'',
contentId:state.containerReducer.selectedContainer?state.containerReducer.selectedContainer.contentId:'',
multipleFlag:state.containerReducer.multipleFlag,
openScanTimePopup: state.carditReducer.openScanTimePopup,
scanTimeDefaultValue: getScanTimeDefaultValue(state),
containerDetailsForPopUp:{'containerDetails': state.containerReducer.containerDetailsToBeReused ? state.containerReducer.containerDetailsToBeReused : []},
isBarrow : state.containerReducer.isBarrow,
showTransferFlag:state.containerReducer.showTransferFlag,
showTransferFlightFlag:state.containerReducer.showTransferFlightFlag,
flightDisplayPage:state.filterReducer.flightDisplayPage,
defaultPageSize:state.filterReducer.flightDetails?state.filterReducer.flightDetails.defaultPageSize:100

  }
}


const mapDispatchToProps = (dispatch) => {
  return {
   // displayMailbags:(data)=>{
    //  dispatch(asyncDispatch(displayMailbagsinSelectedContainers)(data));
   // },

  //on pagination of containers
   getContainerNewPage: (displayPage) => {
    dispatch(asyncDispatch(listContainersinFlight)({'containerDisplayPage':displayPage})).then((response)=>{
    if(!isEmpty(response)) {
      dispatch(asyncDispatch(listmailbagsinContainers)())
    }
   })
  },

    //display mailbags in selected or clicked container
   selectContainers: (data) => {
    dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:data.containerIndex,mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'},));
   },
  
    saveSelectedContainersIndex:(indexes)=> {
      dispatch({ type: constant.SAVE_SELECTED_CONTAINER_INDEX,indexes})
    },
    //for row specific container action
    containerAction:(data)=>{
      if(data.actionName === constant.OFFLOAD) {
        dispatch(asyncDispatch(validateContainerOffload)({index:data.index,mode:data.actionName})).then(() =>{
          dispatch(asyncDispatch(navigateActions)({index:data.index,mode:data.actionName}))
        })
      }
 else {
      dispatch(asyncDispatch(checkCloseFlight)({index:data.index,mode:data.actionName,actions:'CONTAINER_LEVEL'})).then((response) => {
        //if(data.actionName==='MODIFY' || data.actionName==='ADD_CONTAINER') {
          if (isEmpty(response.errors)) {
            if(data.actionName===constant.UNASSIGN) {
              dispatch(asyncDispatch(unassignContainer)({index:data.index,mode:data.actionName})).then(()=>{
                dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                  dispatch(asyncDispatch(listmailbagsinContainers)());
                })
              
              })
            } 
            else if(data.actionName===constant.DETACH_AWB){
              dispatch(requestWarning([{code:"mail.operations.container.detachAWBwarning", description:"Are you sure you want Detach AWB?"}],
    {functionRecord:performContainerAction, args:{index:data.index,mode:data.actionName}}));
      }
            else if(data.actionName===constant.PRINT_ULD_TAG){
                dispatch(asyncDispatch(printuldtag)({index:data.index,mode:data.actionName}))
        }
            else if(data.actionName === constant.ULD_MARK_FULL || data.actionName === constant.ULD_UNMARK_FULL ){
          dispatch(asyncDispatch(markUnmarkULDFullIndicator)({index:data.index,mode:data.actionName})).then(()=>{
dispatch(asyncDispatch(listContainersinFlight)())
          })
    }
            else {
                dispatch(asyncDispatch(performContainerAction)({index:data.index,mode:data.actionName})).then((response)=>{ 
              if(data.actionName===constant.ATTACH_ROUTING && response.results[0].attachRoutingDetails.consignemntNumber !="") { 
                dispatch(asyncDispatch(listAttachRoutingAction)())
                }
                if(data.actionName===constant.REASSIGN_FLIGHT||data.actionName===constant.TRANSFER_FLIGHT){
                  dispatch(asyncDispatch(listFlightDetailsForReassign)({ 'displayPage':'1',mode:'display'})) .then((response) => {
                    if(!isEmpty(response.results[0]) ){
                      if(response.results[0].flightCarrierFilter.assignTo === 'F')
                        dispatch(asyncDispatch(fetchFlightCapacityDetails)());
                    }
                  });
                }
                if(data.actionName===constant.DELETE_CONTAINER){
                  dispatch(asyncDispatch(listDetails)()).then(() => {
                    dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
                      dispatch(asyncDispatch(listmailbagsinContainers)());
                    })
                  })
                }
              
              })
            }
          }
      })
    }
   },
    containerMultipleSelectionAction:(data)=>{
      if(data.actionName === constant.OFFLOAD) {
          dispatch(asyncDispatch(validateContainerOffload)({mode:data.actionName})).then(() =>{
            dispatch(asyncDispatch(navigateActions)({fromButton:'FROM_OFFLOAD'}))
         })
      }
      else if((data.actionName===constant.REASSIGN_FLIGHT||data.actionName===constant.TRANSFER_FLIGHT) &&data.index!=undefined){
        dispatch(asyncDispatch(checkCloseFlight)({index:data.index,mode:data.actionName,actions:'CONTAINER_LEVEL'})).then(() => {
			dispatch(asyncDispatch(listmailbagsinContainers)({ containerIndex:data.index,mailbagsDisplayPage:'1','containerTransferCheck':'true'})).then(()=>{
           dispatch(asyncDispatch(performContainerAction)({mode:data.actionName,multipleFlag:true})).then((response) =>{
              dispatch(asyncDispatch(listFlightDetailsForReassign)({'displayPage':'1',mode:'display'})) .then((response) => {
                if(!isEmpty(response.results[0]) ){
                  if(response.results[0].flightCarrierFilter.assignTo === 'F')
                    dispatch(asyncDispatch(fetchFlightCapacityDetails)());
                }
              });
			  dispatch(asyncDispatch(listDetails)()).then(() => {
				  dispatch(asyncDispatch(listContainersinFlight)());
             })
           }) 
		})
        })
      }	 
      else if(data.actionName===constant.PRINT_ULD_TAG){
          dispatch(asyncDispatch(printuldtag)({index:data.index,mode:data.actionName}))
  }
      else {
        dispatch(asyncDispatch(checkCloseFlight)({index:data.index,mode:data.actionName,actions:'CONTAINER_LEVEL'})).then(() => {
           dispatch(asyncDispatch(performContainerAction)({mode:data.actionName,multipleFlag:true})).then((response) =>{
			if(data.actionName===constant.ATTACH_ROUTING && response.results[0].attachRoutingDetails.consignemntNumber !="") { 
              dispatch(asyncDispatch(listAttachRoutingAction)())
            }
            if(data.actionName===constant.REASSIGN_FLIGHT||data.actionName===constant.TRANSFER_FLIGHT){
              dispatch(asyncDispatch(listFlightDetailsForReassign)({'displayPage':'1',mode:'display'})) .then((response) => {
                if(!isEmpty(response.results[0]) ){
                  if(response.results[0].flightCarrierFilter.assignTo === 'F')
                    dispatch(asyncDispatch(fetchFlightCapacityDetails)());
                }
              });
            }
              dispatch(asyncDispatch(listDetails)()).then(() => {
                dispatch(asyncDispatch(listContainersinFlight)());
             })
           }) 
        })
      } 
    },
    
    
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
    },
     closeAddContainerPopup:()=>{
       dispatch({ type: constant.ADD_CONTAINER_POPUPCLOSE})
    },
    onListToModifyContainer:()=>{
      dispatch(asyncDispatch(onListContainer)());
    },
   
    /*onSaveContainer: () => {    
	 let containers = 0;
	 dispatch(asyncDispatch(onSaveContainer)()).then(() => {
	     dispatch(asyncDispatch(listDetails)()).then(() => {
	        dispatch(asyncDispatch(listContainersinFlight)()).then((responseContainer) => {         
	          if (!isEmpty(responseContainer.results)) {
	            const containerInfo  = responseContainer.results[0];
	            const containerPageInfo = containerInfo.mailAcceptance.containerPageInfo;
	            containers = containerPageInfo.results.length;
	          }
	          dispatch(asyncDispatch(listmailbagsinContainers)()).then((response)=>{
	            if (containers === 1) {               
	               dispatch({type: constant.MAIL_CLEAR});
	             }
	          });
	        })
	      })
	    })*/
	    
   
   onSaveContainer: (warningForULDConversion) => {
    let customButtons = [
      {
      className: 'Primary',
      text: 'No',
      callBackFun:onSaveContainer
      }
    ]
     if(warningForULDConversion){
      dispatch(asyncDispatch(convertUldToBarrow)({uldToBarrow:false})).then((response)=> {
        if(response.status=="reassign_success"){
      dispatch(asyncDispatch(listDetails)()).then(() => {
      dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
      dispatch(asyncDispatch(listmailbagsinContainers)());
      })
      })
      }})
    }
     else{
      dispatch(asyncDispatch(onSaveContainer)({customButtonsForWarning:customButtons })).then((response) => {
        if (isEmpty(response.errors)) {
        dispatch(asyncDispatch(listDetails)()).then(() => {
        dispatch(asyncDispatch(listContainersinFlight)({containerDisplayPage:'1'})).then(() => {
          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1'}));
        })
      })
     }
    })
  }
    },
    onclickPABuilt: (event)=> {
      dispatch(dispatchAction(clickPABuilt)(event.target.checked));
    },

    populateDestForBarrow:(event)=>{
      dispatch(dispatchAction(populateDestForBarrow)({dest:event.value}));
    },
    populateDestForBULKContainer:(destination,barrowCheck)=>{
      dispatch(dispatchAction(populateDestForBULKContainer)({dest:destination,barrowCheck:barrowCheck}));
    },
   
    //attach
    closeAttachAwb:()=>{
      dispatch({type: constant.ATTACH_AWB_CLOSE});
    },

    listAwbDetails:()=>{
      dispatch(asyncDispatch(listAwbDetailsAction)())
    },
    onSaveAttachAwb:()=>{
      dispatch(asyncDispatch(saveAwbDetailsAction)()).then(() => {
        dispatch(asyncDispatch(listDetails)()).then(() => {
        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
          dispatch(asyncDispatch(listmailbagsinContainers)());
        })
      })
    })
    },
    clearAttachAwbPanel:()=>{
      dispatch(dispatchAction(clearAWB)());
    },
    clearAttachRoutingPanel:()=>{
      dispatch(dispatchAction(clearAttachRouting)());
    },

    //attach routing

    listAttachRouting:(filter)=>{
      let validObject = validateAttachRoutingForm(filter)
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
       
      }
      else {
        dispatch(dispatchAction(listAttachRoutingAction)());
          dispatch({type:constant.LIST_DISABLE});
       
      }
      
    },

    saveAttachRouting:(data)=>{
      dispatch(dispatchAction(saveAttachRoutingAction)(data))
    },

    onApplyContainerSort: (sortBy, sortByItem) => {
      dispatch(dispatchAction(onApplyContainerSort)({ sortBy, sortByItem }))
    },
   
    closePopUp:(data)=>{
      if(data===constant.ATTACH_ROUT)
      dispatch({type: constant.ATTACH_ROUTING_CLOSE});
    },
     //On clicking container table filter popup
     onApplyContainerFilter:()=>{
      dispatch(asyncDispatch(onApplyContainerFilter)({action:'APPLY_FILTER'})).then((response)=>{
        if(!isEmpty(response)) {
          dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:'0',mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
        }
       })
   },
   //On clearing container table filter popup
   onClearContainerFilter:()=>{
      //dispatch(asyncDispatch(onApplyContainerFilter)({action:'CLEAR_FILTER'}));
      dispatch(dispatchAction(onClearContainerFilterPanel)());
   },
   //Reassign
   validateContainerForReassign:(data)=>{
    dispatch(asyncDispatch(validateContainerForReassign)(data))
  },
   reassignContainerAction:(data)=>{
    dispatch(asyncDispatch(reassignContainerAction)(data)).then(()=>{
      dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
        dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
      })
    
    })
    },  
    onClose:() => {
      dispatch({type : constant.REASSIGN_CLOSE});
        dispatch({type :constant.CONTAINER_CONVERSION,uldToBarrow:false,barrowToUld:false})

   }, 
  
  unassignContainer:(data)=>{
    dispatch(asyncDispatch(reassignContainerAction)(data)).then(()=>{
      dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
        dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
      })
    
    })
    },  
    updateContainerSortVariables: (sortBy, sortByItem) => {
      dispatch(dispatchAction(updateContainerSortVariables)({ sortBy, sortByItem }))
    },
   //cardit/lying list assign
    assignToContainer:(rowIndex,activeTab,cardits,lyinglists,carditIndexes,lyinglistindexes,currentDate) =>{
     // let validObject = validateToAssign(rowIndex,cardits,activeTab,lyinglists,carditIndexes,lyinglistindexes);
     // if (!validObject) {
      // dispatch(requestValidationError('Accepted mailbags cannot be assigned', ''));
     // }
     // else {
       dispatch(dispatchAction(assignCarditMails)({rowIndex:rowIndex,currentDate:currentDate}));
          //dispatch(asyncDispatch(assignToContainers)({rowIndex:rowIndex,currentDate:currentDate}));
     // }

       
     
    },
    continueAssignToContainer:()=> {
      dispatch(dispatchAction(continueAssignToContainer)());
    },
    navigateToOffload: (container) => {
      dispatch(dispatchAction(navigateToOffload)({ container}))
    },
    allContainerIconAction:()=>{
      dispatch(dispatchAction(allContainerIconAction)())
    },
    //on pagination of containers in carrier
    getContainerNewPageCarrier: (displayPage) => {
    dispatch(asyncDispatch(listContainersinFlight)({'containerDisplayPage':displayPage})).then((response)=>{
    if(!isEmpty(response)) {
      dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
    }
   })
  },
  listFlightDetailsForReassign: (values) => {
dispatch(asyncDispatch(listFlightDetailsForReassign)({'displayPage':'1',mode:'display'})) .then((response) => {
  if(!isEmpty(response.results[0]) ){
    if(response.results[0].flightCarrierFilter.assignTo === 'F')
      dispatch(asyncDispatch(fetchFlightCapacityDetails)());
  }
});
},
getNewPage: (displayPage) => {
  dispatch(asyncDispatch(listFlightDetailsForReassign)({'displayPage':displayPage})) .then((response) => {
    if(!isEmpty(response.results[0]) ){
      if(response.results[0].flightCarrierFilter.assignTo === 'F')
        dispatch(asyncDispatch(fetchFlightCapacityDetails)());
    }
  });
  },
  saveSelectedFlightsIndex:(indexes)=> {
    dispatch({ type: constant.SAVE_SELECTED_FLIGHT_INDEX,indexes})
  },
  reassignContainerToFlightAction:(flightDisplayPage,defaultPageSize)=>{
    dispatch(asyncDispatch(reassignContainerToFlightAction)({warningFlag:false})).then((response)=>{
      if(response&&response.status=="reassign_success" ){
    dispatch(asyncDispatch(listDetails)({'displayPage':flightDisplayPage, 'recordsPerPage': defaultPageSize})).then(() => {
      dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
        dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
      })
    
    })
    }})
  },
    clearReassignFlightPanel:()=>{
      dispatch(dispatchAction(clearReassignFlightPanel)())
    }, 
    closeAssignScanTimePopup:()=> {
      dispatch(dispatchAction(closeAssignScanTimePopup)())
    },
    barrowCheck:(isBarrow)=> {
      dispatch(dispatchAction(paBuiltUpdate)({barrowFlag:isBarrow}));
    },
    onCloseTransfer:() =>{
    dispatch({type :constant.TRANSFER_CLOSE})
    dispatch({type :constant.CONTAINER_CONVERSION,uldToBarrow:false,barrowToUld:false})
   },
   transferContainerAction:()=>{
    dispatch(asyncDispatch(transferContainerAction)()).then(()=>{
      dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
        dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
      })
    
    })
    },
    clearTransferFlightPanel:()=>{
      dispatch(dispatchAction(clearTransferFlightPanel)())
    },
    transferContainerToFlightAction:(data)=>{
      dispatch(asyncDispatch(transferContainerToFlightAction)({data,warningFlag:false})).then((response)=>{
        if(response&&response.status=="transfersave_success" ){
          dispatch(asyncDispatch(listDetails)()).then(() => {
        dispatch(asyncDispatch(listContainersinFlight)()).then(() => {
          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
        })
        } ) 
      }})
      }

  }
}


const ContainerTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ContainerDetailsPanel)

export default ContainerTableContainer