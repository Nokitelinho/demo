import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import MailBagtable from '../panels/MailBagtable.jsx';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { addRow,deleteRow} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {
  screenLoadAttachAwb, changeMailTab, performMailbagScreenAction, onApplyMailbagFilter, onClearMailbagFilter, onSavemailbagDetails, onPopulatemailbagDetails, onSaveExcelmailbagDetails, allMailbagIconAction, mailAllSelect,
  detachAwb, screenloadAttachRoutingAction,listChangeFlightMailPanel,screenLoadChangeFlightAction,screenloadTransferMailAction,screenLoadDamageCapture,populateMailbagId,getFlightDetailsForChangeFlight,
  onApplyDsnFilter, pabuiltUpdate, onClearDsnFilter, onApplyMailSort, onApplyDsnSort, saveNewContainer, addRowInMailbagTable, onLoadAddMailbagPopup, isdomestic, validateNewMailbagAdded, clearAddContainerPopover, onGenManifestPrint, validateMailbagDelivery
} from '../../actions/mailbagaction'
import {listAwbDetailsAction,saveAwbDetailsAction,listAttachRoutingAction,saveAttachRoutingAction,onContainerRowSelection,listMailbagAndDsns,listMailbagAndDsnsOnContainerList} from '../../actions/containeraction'
import {clearPopUp} from '../../actions/commonaction'
import { updateFlightDetails, setDeliveryPopuFieldStatus } from '../../actions/mailbagaction'
// import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import * as constant from '../../constants/constants';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {listContainersAlone,listContainers,onRowSelection} from '../../actions/flightaction';
import {reset} from 'redux-form';
import {displayError} from  '../../actions/commonaction';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



// const getNewMailbags= () =>([]);
// const getNewMailbagsDetails=createSelector([getNewMailbags], (results) =>{
//     let mailData=[];
//     results.forEach(function(element) {
//       let mail={...element,mailbagId:element.mailBagId,ooe:element.mailBagId.substring(0, 6),doe:element.mailBagId.substring(6, 12),
//                 mailCategoryCode:element.mailBagId.substring(12, 13),mailSubclass:element.mailBagId.substring(13, 15),
//                 year:element.mailBagId.substring(15, 16),despatchSerialNumber:element.mailBagId.substring(16, 20),
//                 receptacleSerialNumber:element.mailBagId.substring(20, 23),
//                 highestNumberedReceptacle:element.mailBagId.substring(23, 24),registeredOrInsuredIndicator:element.mailBagId.substring(24, 25),
//                 weight:{displayValue :element.weight, roundedDisplayValue:element.weight,displayUnit:element.weightUnit,unitSelect:"true"},scannedDate:element.scanDate.split(" ")[0],scannedTime:element.scanDate.split(" ")[1],
//                 dlvd:element.deliverFlag,dmgd:element.damageInfo?'Y':'N',rcvd:element.arriveFlag,disabled:true
//                 }
//       mailData.push(mail);
//     }, this);
//     return mailData;
// });

const mapStateToProps = (state) => {
 // let currentDate = getCurrentDate();
  return {
    masterDocumentFlag:state.mailbagReducer.masterDocumentFlag,
    screenMode:state.filterReducer?state.filterReducer.screenMode:null,
    containerData:state.containerReducer.containerData,
    mailbagData:state.mailbagReducer.filterFlag?(state.mailbagReducer && state.mailbagReducer.mailDataAfterFilter ? 
          state.mailbagReducer.mailDataAfterFilter : []):(state.mailbagReducer?state.mailbagReducer.mailbagData:[]),
    dsnData:(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter:[]):
                            state.mailbagReducer?(state.mailbagReducer.dsnData):[],
    activeMailbagTab:state.containerReducer?state.containerReducer.activeMailbagTab:'MAIL_VIEW', 
    activeMailbagAddTab:state.mailbagReducer?state.mailbagReducer.activeMailbagAddTab:'NORMAL_VIEW',
    initialValuesTime: {
      time: state.containerReducer.time ? state.containerReducer.time : '',
      date: state.containerReducer.date ? state.containerReducer.date : ''
    },
    attachRoutingClose:(state.mailbagReducer.attachRoutingClose)?state.mailbagReducer.attachRoutingClose:false,
    containerDetailsForPopUp:(state.containerReducer)?state.containerReducer.containerVosToBeReused:{} ,
    changeFlightClose:(state.mailbagReducer.changeFlightClose)?state.mailbagReducer.changeFlightClose:false,
    showTransferClose:(state.mailbagReducer.showTransferClose)?state.mailbagReducer.showTransferClose:false,
    attachRoutingDetails: (state.containerReducer) ? {
      'onwardRouting': state.containerReducer.attachRoutingDetails.onwardRouting ? state.containerReducer.attachRoutingDetails.onwardRouting : [],
    consignemntNumber:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.consignemntNumber:null,
    paCode:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.paCode:null,
    consignmentDate:(state.containerReducer.consignmentDocumentVO)?state.containerReducer.consignmentDocumentVO.consignmentDate:null,
    consignmentType:(state.containerReducer.consignmentDocumentVO)?state.containerReducer.consignmentDocumentVO.type:null,
    }:{},
    attachClose:(state.mailbagReducer.attachClose)?state.mailbagReducer.attachClose:false,
    showDamageClose:(state.mailbagReducer.showDamageClose)?state.mailbagReducer.showDamageClose:false,
    attachAwbDetails:(state.containerReducer.attachAwbDetails)?
      {
        ...state.containerReducer.attachAwbDetails,
        awbNumber: {
          shipmentPrefix: state.containerReducer.attachAwbDetails.shipmentPrefix ?
                                    state.containerReducer.attachAwbDetails.shipmentPrefix:'',
                            masterDocumentNumber:state.containerReducer.attachAwbDetails.documentNumber?
            state.containerReducer.attachAwbDetails.documentNumber : ''
        }
      } : {},
    initialValues:state.mailbagReducer.filterValues.length?null :{...state.mailbagReducer.filterValues},
    oneTimeValues:(state.mailbagReducer.oneTimeValues)?state.mailbagReducer.oneTimeValues:{},
    newMailbags:state.form.newMailbagsTable && state.form.newMailbagsTable.values && state.form.newMailbagsTable.values.newMailbagsTable ?
    state.form.newMailbagsTable.values.newMailbagsTable : [],
    oneTimeValuesFromScreenLoad:state.commonReducer.oneTimeValues,  
    updatedMailbags:state.mailbagReducer.updatedMailbags,
    flightDetails:(state.listFlightreducer)?state.listFlightreducer.flightDetails:null,
    oneTimeValuesForAttachRouting:(state.containerReducer.oneTimeValues)?state.containerReducer.oneTimeValues:{},
    time:state.containerReducer.time?state.containerReducer.time:'',
    date:state.containerReducer.date?state.containerReducer.date:'',
    transferFilterType:state.mailbagReducer.transferFilterType,
    indexDetails:state.mailbagReducer.indexDetails?state.mailbagReducer.indexDetails:{},
    readyforDeliveryRequired:state.commonReducer.ReadyforDeliveryRequired?state.commonReducer.ReadyforDeliveryRequired:'N',
    showDeliverMail:(state.mailbagReducer.showDeliverMail)?state.mailbagReducer.showDeliverMail:false,
    showRemoveMailPanel: state.mailbagReducer.showRemoveMailPanel ? state.mailbagReducer.showRemoveMailPanel : false,
    showReadyForDel:(state.mailbagReducer.showReadyForDel)?state.mailbagReducer.showReadyForDel:false,
    showScanTimePanel:(state.mailbagReducer.showScanTimePanel)?state.mailbagReducer.showScanTimePanel:false,
    initialDsnValues:state.mailbagReducer.dsnFilterValues,
    previousWeightUnit:state.commonReducer.previousWeightUnit?state.commonReducer.previousWeightUnit:'' ,
	addContainerButtonShow:state.mailbagReducer.addContainerButtonShow,
    addMailPopUpShow:state.mailbagReducer.addMailPopUpShow,
    pous: state.mailbagReducer.flightDetailsToBeReused?buildPous(state.mailbagReducer.flightDetailsToBeReused.flightRoute,state.mailbagReducer.flightDetailsToBeReused.pol):[],
    currentDate:state.containerReducer.date,
    currentTime:state.containerReducer.time,
    awbListDisable: (state.containerReducer.awbListDisable)?  state.containerReducer.awbListDisable:false,
    flightActionsEnabled: state.commonReducer.loggedAirport ===state.filterReducer.filterVlues.port,
    ownAirlineCode:state.mailbagReducer.ownAirlineCode,
    partnerCarriers:state.mailbagReducer.partnerCarriers,
    uplift:state.commonReducer.loggedAirport,
    enableDeliveryPopup: state.mailbagReducer.enableDeliveryPopup,
    destination:state.mailbagReducer.destination,
    flightnumberTransferForm:state.mailbagReducer.flightnumber,
    carrierCode:state.mailbagReducer.carrierCode?state.mailbagReducer.carrierCode:null,
    flightnoChangeFlightForm:state.mailbagReducer.flightno?state.mailbagReducer.flightno:'',
    damageDetail:state.mailbagReducer.damageDetail,
    validateDeliveryMailBagFlag: state.mailbagReducer.validateDeliveryMailBagFlag,
    valildationforimporthandling:state.commonReducer.valildationforimporthandling?state.commonReducer.valildationforimporthandling:'N',
    validationforTBA:state.commonReducer.validationforTBA
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

const mapDispatchToProps = (dispatch) => {
  // split this container for  connect container 
  return {        
    displayError:(message, target)=>{
      dispatch(dispatchAction(displayError)({message:message,target: target}))
    },
        //mailbag table
      mailBagAction:(values)=>{
        dispatch(asyncDispatch(performMailbagScreenAction)(values)).then((response)=>{
          if(response.results && response.results[0]!=null&&response.results[0].transferManifestVO!=null){
            dispatch(asyncDispatch(onGenManifestPrint)(values)).then((response)=>{
              dispatch(asyncDispatch(onContainerRowSelection)()).then((response)=>{
              dispatch(asyncDispatch(onRowSelection)(response))
            })
              if(values.actionName === constant.ARRIVE_MAIL) {
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
              if(response.status==='success'){
              dispatch(asyncDispatch(onContainerRowSelection)()).then((response)=>{
            dispatch(asyncDispatch(onRowSelection)(response))
            
          })
              if(values.actionName === constant.ARRIVE_MAIL) {
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
              }
            )
        
      },
	  
	    validateMailbagDelivery:(data)=>{
         dispatch(dispatchAction(validateMailbagDelivery)(data))
      }, 
      listMailbagAndDsns:(data)=>{
          dispatch(dispatchAction(listMailbagAndDsns)({'action':{type:constant.LIST,displayPage:data}}))
      },      
      
      onApplyMailbagFilter:(filter)=>{
         dispatch(dispatchAction(onApplyMailbagFilter)(filter))
      },
      onClearMailbagFilter:(filter)=>{
         dispatch(dispatchAction(onClearMailbagFilter)(filter))
      },
      // onSavemailbagDetails:(values)=>{
      //   dispatch(dispatchAction(onSavemailbagDetails)(values))
      // },
      allMailbagIconAction:(data)=>{
        dispatch(dispatchAction(allMailbagIconAction)({'indexArray':data}))
      },
      changeMailTab:(data)=>{
        dispatch(dispatchAction(changeMailTab)(data))
      },
      changeAddMailbagTab:(currentTab) => {
        dispatch({type:constant.CHANGE_ADD_MAILBAG_TAB,currentTab:currentTab });
      },
      popUpScreenload:(data)=>{
        if(data.actionName===constant.ATTACH_AWB)
          dispatch(asyncDispatch(screenLoadAttachAwb)(data))
        if(data.actionName===constant.CHANGE_FLIGHT)
          dispatch(asyncDispatch(screenLoadChangeFlightAction)(data))
        if(data.actionName===constant.ATTACH_ROUTING)
          dispatch(asyncDispatch(screenloadAttachRoutingAction)(data))
        if(data.actionName===constant.TRANSFER)
          dispatch(asyncDispatch(screenloadTransferMailAction)(data))
        if(data.actionName===constant.DAMAGE_CAPTURE)
          dispatch(asyncDispatch(screenLoadDamageCapture)(data))
        if(data.actionName===constant.DELIVER_MAIL){
          // dispatch(asyncDispatch(listContainers)()).then(()=>
          dispatch(dispatchAction(setDeliveryPopuFieldStatus)(data))
          dispatch({type:constant.DELIVER_CLOSE,showDeliverMail:true })
        // );
        }
        if(data.actionName===constant.READY_FOR_DELIVERY)
          dispatch({type:constant.READY_FOR_DEL_CLOSE,showReadyForDel:true });
        if(data.actionName===constant.CHANGE_SCAN_TIME)
          dispatch({type:constant.CHANGE_SCAN_TIME_CLOSE,showScanTimePanel:true });
        if(data.actionName===constant.REMOVE_MAILBAG) {
          dispatch({type:constant.REMOVE_MAILBAG,showRemoveMailPanel:true });
        }

      },
      detachAWB:(data) => {
        dispatch(requestWarning([{code:"mail.operations.container.detachAWBwarning", description:"Are you sure you want Detach AWB?"}],
        {functionRecord:detachAwb,args:{data}}));
      },
      listAwbDetails:()=>{
        dispatch(asyncDispatch(listAwbDetailsAction)({dispatch,'from':'MAIL'}))
      },
      listTransfer:(data)=>{
        dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({data,'action':constant.LIST_TRANSFER})).then((response)=>{
        dispatch(asyncDispatch(listChangeFlightMailPanel)({ ...response, 'action': constant.LIST_TRANSFER }))
      })
      },
      onSaveAttachAwb:()=>{
        dispatch(asyncDispatch(saveAwbDetailsAction)(dispatch)).then(()=>{
        dispatch(asyncDispatch(onContainerRowSelection)())
      })
      },
      listAttachRouting:(data)=>{
        dispatch(dispatchAction(listAttachRoutingAction)(data))
      },
      saveAttachRouting:(data)=>{
        dispatch(asyncDispatch(saveAttachRoutingAction)(data)).then(()=>{
        dispatch(asyncDispatch(onContainerRowSelection)())
      })
      },
      listChangeFlightMailPanel:(data)=>{
        dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({data,'action':constant.LIST_CHANGEFLIGHT})).then((response)=>{
        dispatch(asyncDispatch(listChangeFlightMailPanel)({ ...response, 'action': constant.LIST_CHANGEFLIGHT }))
      })
      },
      closePopUp:(data)=>{
        if(data===constant.ATTACH_ROUTING)
          dispatch({type: constant.ATTACH_ROUT_CLOSE});
        if(data===constant.CHANGE_FLIGHT){
          dispatch(reset(constant.CHANGE_FLIGHT_MAIL_DETAILS));
          dispatch({type: constant.CHANGE_FLIGHT_SAVE});
        dispatch({
          type: constant.LIST_CONTAINERS_POPUP,
          containerVosToBeReused: {}, flightDetailsToBeReused: {}, addContainerButtonShow: true
        });
          }
        if(data===constant.TRANSFER){
          dispatch({type: constant.TRANSFER_SAVE});
        dispatch({
          type: constant.LIST_CONTAINERS_POPUP,
          containerVosToBeReused: {}, flightDetailsToBeReused: {}, addContainerButtonShow: true
        });
        dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING})
        }
        if(data===constant.ATTACH_AWB)
          dispatch({type: constant.ATTACH_AWB_MAL_CLOSE});
        if(data===constant.DAMAGE_CAPTURE)
          dispatch({type: constant.DAMAGE_CAPTURE_CLOSE});
        if(data===constant.DELIVER_MAIL)
          dispatch({type:constant.DELIVER_CLOSE,showDeliverMail:false });
        if(data===constant.READY_FOR_DELIVERY)
          dispatch({type:constant.READY_FOR_DEL_CLOSE,showReadyForDel:false });
        if(data===constant.CHANGE_SCAN_TIME)
          dispatch({type:constant.CHANGE_SCAN_TIME_CLOSE,showScanTimePanel:false });
        if(data===constant.REMOVE_MAILBAG)
          dispatch({type:constant.REMOVE_MAILBAG,showRemoveMailPanel:false });
        
      },
      clearTransferPanel:(transferFilterType)=>{
        dispatch(reset(constant.TRANSFER_MAIL_FORM)); 
      dispatch({
        type: constant.TRANSFER_POPUP_CLEAR, transferFilterType: transferFilterType,
        containerVosToBeReused: {}, flightDetailsToBeReused: {}, addContainerButtonShow: true
      });
      },

      addRow: (data) => {
        dispatch(dispatchAction(addRowInMailbagTable)({...data}));
      },

      onDeleteRow: (rowIndex) => 
        dispatch(deleteRow(constant.NEW_MAILBAG_TABLE, rowIndex)), 


    onSavemailbagDetails: (values,mode) => {
      let validObject = validateNewMailbagAdded(values);
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else{
        if(mode==='NORMAL_VIEW') {
        dispatch(asyncDispatch(onSavemailbagDetails)()).then((response)=>{
          if(response.status==='success'){
              dispatch(asyncDispatch(onContainerRowSelection)()).then(()=>{
                  dispatch(dispatchAction(listContainersAlone)())
            })
          }
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
    },

      populateMailbagId:(rowIndex) =>{
        dispatch(asyncDispatch(isdomestic)({rowIndex:rowIndex})).then((response)=>{
          if(response.isDomesticFlag === 'Y'){
            dispatch(asyncDispatch(populateMailbagId)({rowIndex:rowIndex , active:response.active}))
          }
          else if (response.isDomesticFlag === 'N' &&response.active==='mailbagId'){
            dispatch(asyncDispatch(populateMailbagId)({rowIndex:rowIndex , active:response.active}))
          }
          else{
            dispatch(dispatchAction(populateMailbagId)({rowIndex:rowIndex , active:response.active}))
          }
        })
    },
    mailAllSelect:(data)=>{
         dispatch(dispatchAction(mailAllSelect)({'checked':data}))
      },
    clearAllSelect:()=>{
         dispatch({type:constant.CLEAR_MAL_SELECT_ALL});
      },
    clearPopUp:(data)=>{
      dispatch(dispatchAction(clearPopUp)({'action':data}));
    },
    onApplyDsnFilter:(filter)=>{
         dispatch(dispatchAction(onApplyDsnFilter)(filter))
    },
    onClearDsnFilter:(filter)=>{
         dispatch(dispatchAction(onClearDsnFilter)(filter))
      },
    updateMailSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(onApplyMailSort)({ sortBy, sortByItem }))
      },
    updateDsnSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(onApplyDsnSort)({ sortBy, sortByItem }))
      },
    saveNewContainer: (fromScreen) => {
       dispatch(asyncDispatch(saveNewContainer)({ action: constant.SAVE_CONTAINER ,fromScreen}))    
    },
    pabuiltUpdate:(barrowCheck)=>{
      dispatch(dispatchAction(pabuiltUpdate)({barrowCheck:barrowCheck}))    
   
    },
    addMailPopUp:(data) =>{
      if(data==false){
        dispatch(dispatchAction((values)=>{
          const {getState}=values;
          const state=getState();
            state.form.newMailbagsTable&&state.form.newMailbagsTable.values
              &&state.form.newMailbagsTable.values.newMailbagsTable?
                state.form.newMailbagsTable.values.newMailbagsTable=[]:null;
        })());
      }
      dispatch(dispatchAction(onLoadAddMailbagPopup)(data));
      },
      clearContainerDetails :() =>{
        dispatch({type:constant.TRANSFER_POPUP_CLEAR});
      },
      clearAddContainerPopover:(values)=>{
        dispatch(dispatchAction(clearAddContainerPopover)(values));
      },
  }
}



const MailbagTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(MailBagtable)

export default MailbagTableContainer
