import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ContainerTable from '../panels/ContainerTable.jsx';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {displayError} from  '../../actions/commonaction';
import {onRowSelection,listContainers} from '../../actions/flightaction';
import { setDeliveryPopuFieldStatus } from '../../actions/containeraction';
import {performContainerScreenAction,onContainerRowSelection,onApplyContainerFilter,saveAwbDetailsAction,
       onClearContainerFilter,onSaveContainer,allContainerIconAction,transferContainerAction,listAwbDetailsAction,containerAllSelect,
      screenloadAttachRoutingAction,listAttachRoutingAction,saveAttachRoutingAction,clearAttachRoutingAction,listMailbagAndDsnsOnContainerList,saveChangeFlight,
      onApplyContainerSort, clearFilter,populateULD, validateContainerForTransfer, listFlightDetailsForReassignAndTransfer, fetchFlightCapacityDetails, transferContainerToFlightAction, clearTransferFlightPanel, retainContainer} from '../../actions/containeraction';
import * as constant from '../../constants/constants';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

const mapStateToProps = (state) => {
  
  return {
    screenMode:state.filterReducer.screenMode,
    displayMode:state.commonReducer.displayMode,   
    containerDetail:state.containerReducer.containerDetail,   
    containerData:state.containerReducer.filterFlag?
            state.containerReducer.containerDataAfterFilter:state.containerReducer.containerData,
    flightDetails:(state.listFlightreducer)?state.listFlightreducer.flightDetails:null,
    flightOperationDetails:(state.listFlightreducer)?state.listFlightreducer.flightOperationDetails:null,
    containerDetailsInVo:(state.containerReducer)?state.containerReducer.containerDetailsInVo:{} , 
    arrivalDetailsInVo:(state.containerReducer)?state.containerReducer.arrivalDetailsInVo:{},
    saveClose:(state.containerReducer.saveClose)?state.containerReducer.saveClose:null,
    activeMailbagTab:state.containerReducer?state.containerReducer.activeMailbagTab:null,
    attachClose:(state.containerReducer.attachClose)?state.containerReducer.attachClose:false,
    attachAwbDetails:(state.containerReducer.attachAwbDetails)?
              {...state.containerReducer.attachAwbDetails,
                    awbNumber:{shipmentPrefix:state.containerReducer.attachAwbDetails.shipmentPrefix?
                                    state.containerReducer.attachAwbDetails.shipmentPrefix:'',
                            masterDocumentNumber:state.containerReducer.attachAwbDetails.documentNumber?
                                    state.containerReducer.attachAwbDetails.documentNumber:''}}:{},
    initialValues:{date:state.containerReducer.date?state.containerReducer.date:'',uldTobarrow:state.containerReducer.uldTobarrow,
                      time:state.containerReducer.time?state.containerReducer.time:'',
                      'onwardRouting':state.containerReducer.attachRoutingDetails.onwardRouting?state.containerReducer.attachRoutingDetails.onwardRouting:[],
                      consignemntNumber:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.consignemntNumber:null,
                      paCode:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails.paCode:null,
                      consignmentDate:(state.containerReducer.consignmentDocumentVO)?state.containerReducer.consignmentDocumentVO.consignmentDate:null,
                      consignmentType:(state.containerReducer.consignmentDocumentVO)?state.containerReducer.consignmentDocumentVO.type:null,
                      containerNo:state.containerReducer.isDisabled === true?'BULK-' + state.filterReducer.filterVlues.port:'',
                      barrow:state.containerReducer.isDisabled === true?true:false,
                      pol:state.listFlightreducer && state.listFlightreducer.flightDetails &&state.listFlightreducer.flightDetails.prevPort?state.listFlightreducer.flightDetails.prevPort:'',
                      desination:state.filterReducer.filterVlues.port?state.filterReducer.filterVlues.port:''
                    },
    containerDetailsToBeReused:(state.containerReducer.containerDetailsToBeReused)?state.containerReducer.containerDetailsToBeReused:{},
    changeContainerClose:(state.containerReducer.changeContainerClose)?state.containerReducer.changeContainerClose:false,
    containerVosToBeReused:(state.containerReducer.containerVosToBeReused)?state.containerReducer.containerVosToBeReused:{},
    transferContainerClose:(state.containerReducer.transferContainerClose)?state.containerReducer.transferContainerClose:false,
    attachRoutingClose:(state.containerReducer.attachRoutingClose)?state.containerReducer.attachRoutingClose:null,
    initialValuesForFilter:state.containerReducer.filterValues.length?null:{...state.containerReducer.filterValues},
    attachRoutingDetails:(state.containerReducer.attachRoutingDetails)?state.containerReducer.attachRoutingDetails:{},
    oneTimeValues:(state.containerReducer.oneTimeValues)?state.containerReducer.oneTimeValues:{},
    time:state.containerReducer.time?state.containerReducer.time:'',
    date:state.containerReducer.date?state.containerReducer.date:'',
    addContainerForm:state.form.addcontainerForm&&
                            state.form.addcontainerForm.values?(state.form.addcontainerForm.values):{},
    indexDetails:state.containerReducer.indexDetails?state.containerReducer.indexDetails:{},
    readyforDeliveryRequired:state.commonReducer.ReadyforDeliveryRequired?state.commonReducer.ReadyforDeliveryRequired:'N',
    addContainerShow:state.containerReducer.addContainerClose?state.containerReducer.addContainerClose:false,
    showReadyForDel:(state.containerReducer.showContReadyForDel)?state.containerReducer.showContReadyForDel:false,
    listEnable: (state.containerReducer.listEnable)?  state.containerReducer.listEnable:false,
    destToPopulate:state.containerReducer.destToPopulate,
    awbListDisable: (state.containerReducer.awbListDisable)?  state.containerReducer.awbListDisable:false,
    showDeliverMail:state.containerReducer.showDeliverMail?state.containerReducer.showDeliverMail:false,
    isDisabled:state.containerReducer.isDisabled,
    flightActionsEnabled: state.commonReducer.loggedAirport ===state.filterReducer.filterVlues.port,
    showTransferFlag:state.containerReducer.showTransferFlag,
    scanTime:state.containerReducer.scanTime,
    uldTobarrow:state.containerReducer.uldTobarrow,
    showTransferFlightFlag:state.containerReducer.showTransferFlightFlag,
    scanDate:state.containerReducer.scanDate,
    reassignFromDate:state.containerReducer.reassignFromDate,
    reassignToDate:state.containerReducer.reassignToDate,
    containerDetailsForPopUp:{'containerDetails': state.containerReducer.containerDetails ? state.containerReducer.containerDetails : []},
    oneTimeValuesFromScreenLoad:state.commonReducer.oneTimeValues,
    flightDetailsTransfer:state.containerReducer.flightDetails ?state.containerReducer.flightDetails.results:[],
    flightDetailsPage:state.containerReducer.flightDetails ?state.containerReducer.flightDetails:{},
    selectedContainerIndex:state.containerReducer.selectedContainerIndex ?state.containerReducer.selectedContainerIndex:[],
    destinationForTransferPopUp:state.containerReducer.containerDetails && state.containerReducer.containerDetails.length > 0? state.containerReducer.containerDetails[0].destination : '',
   // uldNumber:'BULK-' + state.filterReducer.filterVlues.port
    enableDeliveryPopup: state.containerReducer.enableDeliveryPopup,
    companyCode: state.__commonReducer ? state.__commonReducer.logonAttributes.ownAirlineCode  :'',
    loggedAirport:   state.commonReducer.loggedAirport,
    showRemoveContainerPanel: state.containerReducer.showRemoveContainerPanel,
    retainValidation: state.containerReducer.retainValidation ,
    valildationforimporthandling:state.commonReducer.valildationforimporthandling?state.commonReducer.valildationforimporthandling:'N'   ,
    validationforTBA:state.commonReducer.validationforTBA,
    mailbagData:state.mailbagReducer.mailbagData,
	carrierCode:state.filterReducer.filterVlues.flightnumber && state.filterReducer.filterVlues.flightnumber.carrierCode?state.filterReducer.filterVlues.flightnumber.carrierCode:''
  }
}
const mapDispatchToProps = (dispatch) => {
      
  return {  
      containerAction:(data)=>{
        dispatch(asyncDispatch(performContainerScreenAction)(data)).then((response)=>{
          if( response &&response.status!=='security'){
              dispatch(asyncDispatch(onRowSelection)({fromAction:'Container',stopPropgn:response.stopPropgn?response.stopPropgn:false}))}})
      },
      onApplyContainerFilter:(filter)=>{
         dispatch(dispatchAction(onApplyContainerFilter)(filter))
      },
      onClearContainerFilter:(filter)=>{
         dispatch(dispatchAction(onClearContainerFilter)(filter))
      },
      onSaveContainer:(values)=>{
        dispatch(asyncDispatch(onSaveContainer)(values))
        
},
      onClearContainer: () => {
        dispatch(dispatchAction(clearFilter)());
      },
      onRowSelection:(data)=>{
        dispatch(asyncDispatch(onRowSelection)({mode:data}))
      },
      onContainerRowSelection:(data)=>{
        dispatch(dispatchAction(onContainerRowSelection)(data))
      },
      allContainerIconAction:(data)=>{
        dispatch(dispatchAction(allContainerIconAction)({'indexArray':data}))
      },
    /*  allSelectAction:(values)=>{
        dispatch(dispatchAction(allSelectAction)(values))
      }
      transferContainerAction:(data)=>{
        dispatch(asyncDispatch(transferContainerAction)(data)).then(()=>{
              dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}))})
      },*/

      transferContainerAction:(data)=>{
        dispatch(asyncDispatch(transferContainerAction)({data})).then(()=>{
          if( response &&response.status!=='security'){
          dispatch(asyncDispatch(listContainers)()).then(() => {
            dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}));
          })
        }
        })
        },

      listContainers:(data)=>{
         dispatch(asyncDispatch(listContainers)({'action':{type:constant.LIST,displayPage:data}})).then((response)=>{
              dispatch(asyncDispatch(listMailbagAndDsnsOnContainerList)(response))})
      },
      detachAWB:(data) => {
        dispatch(dispatchAction(performContainerScreenAction)(data))
      },

      closeAttachAwb:()=>{
        dispatch({type: constant.ATTACH_AWB_CLOSE});
      },

      listAwbDetails:()=>{
        dispatch(asyncDispatch(listAwbDetailsAction)({dispatch,'from':'CONTAINER'}))
      },
      onSaveAttachAwb:()=>{
        dispatch(asyncDispatch(saveAwbDetailsAction)(dispatch)).then(()=>{
              dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}))})
      },
      screenloadAttachRouting:(data)=>{
        dispatch(dispatchAction(screenloadAttachRoutingAction)(data))
      },
      listAttachRouting:(data)=>{
        dispatch(asyncDispatch(listAttachRoutingAction)(data))
      },
      saveAttachRouting:(data)=>{
        dispatch(asyncDispatch(saveAttachRoutingAction)(data)).then(()=>{
              dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}))})
      },
      clearAttachRoutingPanel:(data)=>{
        dispatch(dispatchAction(clearAttachRoutingAction)(data))
      },
      saveChangeFlight:(data)=>{
        dispatch(asyncDispatch(saveChangeFlight)(data)).then(()=>{
              dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}))})
      },
      closePopUp:(data)=>{
        if(data===constant.ATTACH_ROUTING)
            dispatch({type: constant.ATTACH_ROUT_CLOSE});
        else if(data===constant.ATTACH_AWB)
             dispatch({type: constant.ATTACH_AWB_CLOSE});
        else if(data==constant.CHANGE_FLIGHT)
           dispatch({type: constant.CHANGE_FLIGHT_SAVE});
        else if(data==constant.TRANSFER)
           dispatch({type: constant.TRANSFER_CLOSE});
       else if(data===constant.READY_FOR_DELIVERY)
          dispatch({type:constant.READY_FOR_CONT_DEL_CLOSE,showContReadyForDel:false });
        else if(data===constant.REMOVE_CONTAINER)
          dispatch({type:constant.REMOVE_CONTAINER, showRemoveContainerPanel:false });
        else
          dispatch({type: constant.ATTACH_ROUT_CLOSE});
      },
      containerAllSelect:(data)=>{
         dispatch(dispatchAction(containerAllSelect)({'checked':data}))
      },
      displayError:(message, target)=>{
        dispatch(dispatchAction(displayError)({message:message,target: target}))
      },
      clearAllSelect:()=>{
         dispatch({type:constant.CLEAR_SELECT_ALL});
      },
      updateSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(onApplyContainerSort)({ sortBy, sortByItem }))
      },
      addContainerClose:(data) => {
        dispatch({type: constant.ADD_CONTAINER_SHOW,data:data});
      },
       popUpContScreenload:(data)=>{
        if(data.actionName===constant.READY_FOR_DELIVERY)
          dispatch({type:constant.READY_FOR_CONT_DEL_CLOSE,showContReadyForDel:true });
        else if(data.actionName===constant.DELIVER_MAIL) {
          if(data.showValue===true) {
            dispatch(dispatchAction(setDeliveryPopuFieldStatus)(data))
          }
          dispatch({type:constant.DELIVER_CONTAINER,showDeliverMail:data.showValue });
        } 
        else if(data.actionName===constant.REMOVE_CONTAINER) {
          dispatch({type:constant.REMOVE_CONTAINER,showRemoveContainerPanel: true });
        }
      },
      populateULD:(data) => {
        dispatch(dispatchAction(populateULD)(data))
      },
      validateContainerForTransferToCarrier:(index, mode)=>{
        dispatch(asyncDispatch(validateContainerForTransfer)({index, mode}))
     },
      validateContainerForTransfer:(index, mode)=>{
        dispatch(asyncDispatch(validateContainerForTransfer)({index, mode})).then(()=>{
          dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'transfer'})) .then((response) => {
            if(!isEmpty(response.results[0]) ){
              if(response.results[0].flightCarrierFilter.assignTo === 'F')
                dispatch(asyncDispatch(fetchFlightCapacityDetails)());
            }
          });
        })
     },
     listFlightDetailsForTransfer: (values) => {
      dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'transfer'})) .then((response) => {
        if(!isEmpty(response.results[0]) ){
          if(response.results[0].flightCarrierFilter.assignTo === 'F')
            dispatch(asyncDispatch(fetchFlightCapacityDetails)());
        }
      });
      },
      getNewPageTransferPanel: (displayPage) => {
        dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':displayPage, panel:'transfer'})) .then((response) => {
          if(!isEmpty(response.results[0]) ){
            if(response.results[0].flightCarrierFilter.assignTo === 'F')
              dispatch(asyncDispatch(fetchFlightCapacityDetails)());
          }
        });
        },
        saveSelectedFlightsIndex:(indexes)=> {
          dispatch({ type: constant.SAVE_SELECTED_FLIGHT_INDEX,indexes})
        },
        /*transferContainerToFlightAction:(data)=>{
          dispatch(asyncDispatch(transferContainerToFlightAction)({data,warningFlag:false}))
          },*/

          transferContainerToFlightAction:(data)=>{
            dispatch(asyncDispatch(transferContainerToFlightAction)({data,warningFlag:false})).then(()=>{
              dispatch(asyncDispatch(listContainers)()).then(() => {
                dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}));
              })
            
            })
            },
        onClose:() => {
            dispatch({type : 'CLOSE'})
         },
         clearTransferFlightPanel:()=>{
          dispatch(dispatchAction(clearTransferFlightPanel)())
        },
        retainContainer:(index)=>{
          dispatch(dispatchAction(retainContainer)({'index':index, 'warningFlag':false}))
       },
  }
}



const ContainerTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ContainerTable)

export default ContainerTableContainer
