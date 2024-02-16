import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { listMailbagsEnquiry } from '../../actions/filteraction';
import { saveActualWeight, validateActualWeight, openHistoryPopup, validateMailbags, 
  onApplyMailbagFilter, onClearMailbagFilter, updateSortVariables } from '../../actions/commonaction';
import {
  onMailBagAction, doReturnMail, listContainers, validateFlight, navigateToOffload,
  onreassignMailSave, onTransferMailSave, saveNewContainer, onMailRowSelect,pabuiltUpdate,updateOrgAndDest,validateOrgDest,validateOrgDestForMultiple,onSaveOrgAndDest
} from '../../actions/detailsaction';
import {reset} from 'redux-form';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

import { change } from 'icoreact/lib/ico/framework/component/common/form'; 
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {displayError} from  '../../actions/commonaction';

const getTableResults = (state) => state.listmailbagsreducer.mailbagsdetails && state.listmailbagsreducer.mailbagsdetails.results ? state.listmailbagsreducer.mailbagsdetails.results : [];

const getTableFilter = (state) => state.filterReducer.tableFilter ? state.filterReducer.tableFilter : {}

const getSortDetails = (state) => state.commonReducer.sort;

const getMailbags = createSelector([getTableResults, getTableFilter], (results, filter) => results.filter((obj) => {
  const weight = obj.weight.formattedDisplayValue;
  //const actualWeight = JSON.stringify(obj.actualWeight);
  const actualWeight = obj.actualWeightMeasure?obj.actualWeightMeasure.formattedDisplayValue:'';
  const awb = obj.shipmentPrefix+"-"+obj.masterDocumentNumber;
  const volume = obj.volume?obj.volume.formattedDisplayValue:'';
  const objDup={...obj};
  const carrier=objDup.carrierCode;
  const flightNum=objDup.flightNumber;
  const carrierCode=carrier +flightNum;
  obj = {...objDup,carrierCode,weight,actualWeight,volume, awb};

  const anotherObj = { ...obj, ...filter };
  return JSON.stringify(obj) === JSON.stringify(anotherObj)
}))

const getSortedDetails = createSelector([getSortDetails, getMailbags], (sortDetails, mailbags) => {

  if (sortDetails) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;
      if(sortBy === 'awb'){
        for(let i=0; i<mailbags.length; i++){
          const awb = mailbags[i].shipmentPrefix+"-"+mailbags[i].masterDocumentNumber;
          mailbags[i] = {...mailbags[i], 'awb':awb}
        }
      }else if(sortBy === 'mailbagVolume'){ //mailbagVolume is string, so comparing strings won't give correct result
        for(let i=0; i<mailbags.length; i++){
          mailbags[i] = {...mailbags[i], 'mailbagVolume':mailbags[i].volume.displayValue} //float data so correct sorting
      }
      }

      mailbags = mailbags.sort((record1, record2) => {
        let sortVal = 0;
        let data1;
        let data2;
        data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
        data2 = record2[sortBy] && typeof record2[sortBy] === "object"?record2[sortBy].systemValue:record2[sortBy];
        
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
  return [...mailbags];
});

const mapStateToProps = (state) => {
  return {
    screenMode: state.filterReducer.screenMode,
    displayMode: state.commonReducer.displayMode,
    mailbagsdetails: state.listmailbagsreducer.mailbagsdetails,
    selectedMailbags: state.listmailbagsreducer.selectedMailbags,
    selectedMailbag: state.commonReducer.selectedMailbag,
    oneTimeValues: state.commonReducer.oneTimeValues,
    filterValues: state.filterReducer.filterValues,
    popupMode: state.listmailbagsreducer.popupMode,
    damageDetails: state.listmailbagsreducer.damageDetails,
    damageDetail:state.commonReducer.damageDetail,
    selectedIndex: state.listmailbagsreducer.selectedIndex,
    tableFilter: state.filterReducer.tableFilter,
    onwardRouting: state.listmailbagsreducer.onwardRouting,
    containerDetails: state.listmailbagsreducer.containerDetails,
    flightValidation: state.listmailbagsreducer.flightValidation,
    tableValues: state.form.mailbagtable?state.form.mailbagtable.values.mailbagtable:[],
    showReturn: state.commonReducer.showReturn,
    showReassign: state.commonReducer.showReassign,
    showTransfer: state.commonReducer.showTransfer,
    showViewDamage:state.commonReducer.showViewDamage,
    mailbagslist : getSortedDetails(state),
    destination : state.listmailbagsreducer.destination,
    initialValues: {...getSortedDetails(state)},
    defaultValues:{uplift: state.commonReducer.airportCode, 
      reassignFilterType:state.listmailbagsreducer.reassignFilterType, 
      transferFilterType:state.listmailbagsreducer.transferFilterType,
     scanDate:getCurrentDate(), scanTime:state.commonReducer.scanTime,pou:state.listmailbagsreducer.pou,
     carrierCode:state.listmailbagsreducer.carrierCode, 
     destination:state.listmailbagsreducer.destination, 
     uldDestination: state.listmailbagsreducer.destination,
     flightnumber:state.listmailbagsreducer.flightnumber
    },
    postalCodes : state.commonReducer.postalCodes,
    isValidFlight:state.listmailbagsreducer.isValidFlight,
    tableFilterInitialValues: state.filterReducer.tableFilter ? state.filterReducer.tableFilter : {},
    capFlag:state.filterReducer.capFlag,
    scanDate:getCurrentDate(),
    scanTime:state.commonReducer.scanTime
    ,pous:state.listmailbagsreducer.pous,
    mailbagdetailForm:(state.form&&state.form.mailbagdetail&&state.form.mailbagdetail.values)?state.form.mailbagdetail.values:null,
    ownAirlineCode:state.commonReducer.ownAirlineCode,
    partnerCarriers:state.commonReducer.partnerCarriers,
    mailbagtable:(state.form&&state.form.mailbagtable&&state.form.mailbagtable.values.mailbagtable)?state.form.mailbagtable.values.mailbagtable:null,
    showModifyPopup:state.listmailbagsreducer.showModifyPopup,
    newMailbagsTable:(state.form&&state.form.newMailbagsTable&&state.form.newMailbagsTable.values.newMailbagsTable)?state.form.newMailbagsTable.values.newMailbagsTable:null,
   dummyAirportForDomMail:state.commonReducer.dummyAirportForDomMail?state.commonReducer.dummyAirportForDomMail:null
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    displayError:(message, target)=>{
     // dispatch(requestValidationError(message, ''));
     dispatch(dispatchAction(displayError)({message:message,target: target}))
    },
    onlistMailbagsEnquiry: (displayPage,pageSize) => {
      dispatch(asyncDispatch(listMailbagsEnquiry)({ displayPage,pageSize, action: 'LIST' }))

    },
    onMailBagAction: (type, selectedMail) => {
      console.log(type);
      if (type === 'VIEW_DAMAGE') {
        dispatch(asyncDispatch(onMailBagAction)({ selectedMail, action: 'VIEW_DAMAGE' }))
      } else if (type === 'DELIVER_MAIL') {
        dispatch(asyncDispatch(onMailBagAction)({ selectedMail, action: 'DELIVER_MAIL' })).then(() =>{
            dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST'}))
        } )
      } else if (type === 'OFFLOAD') {
        dispatch(asyncDispatch(onMailBagAction)({ selectedMail, action: type })).then((response) =>{
           dispatch(asyncDispatch(navigateToOffload)({ selectedMail, action: type }))
        })
      }
      else {
        dispatch(dispatchAction(onMailBagAction)({ selectedMail, action: type }))
      }

    },

    onApplyMailFilter: () => {
      dispatch(dispatchAction(applyMailFilter)());
    },
    onClearMailFilter: () => {
      dispatch(dispatchAction(onClearMailFilter)());
    },
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(listMailbagsEnquiry)({ mode: "EXPORT", displayPage, pageSize }))
    },

    doReturnMail: (selectedMail) => {

      dispatch(asyncDispatch(doReturnMail)({ selectedMail, action: "DO_RETURN" })).then(() =>{
        dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST'}))
           } )

    },
    reassignSave: (containerIndex) => {

      dispatch(asyncDispatch(onreassignMailSave)({ containerIndex, action: "DO_REASSIGN" })).then(() =>{
        if( response &&response.status !=null &&response.status!=='security'){
        dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST'}))
      }} )
    },
    transferSave: (containerIndex) => {

      dispatch(asyncDispatch(onTransferMailSave)({ containerIndex, action: "DO_TRANSFER" })).then(() =>{
        if( response &&response.status !=null &&response.status!=='security'){
        dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST'}))
      }} )

    },
    saveNewContainer: (fromScreen) => {

      dispatch(asyncDispatch(saveNewContainer)({ action: "SAVE_CONTAINER" ,fromScreen}))

    },
    onMailRowSelect: (isRowSelected, rowIdx) => {
      dispatch(dispatchAction(onMailRowSelect)({isRowSelected, rowIdx}));
    },
    listContainer: (selectedMail,filterType,fromScreen) => {
     //Added by A-8176 as part of ICRD-335975
     if(filterType === 'F')   {
      dispatch(asyncDispatch(validateFlight)({ selectedMail, action: 'VALIDATE_FLIGHT', fromScreen: fromScreen })).then((response) => {
        dispatch(asyncDispatch(listContainers)({action: 'LIST_CONTAINER', fromScreen: fromScreen }))
      })
    }
     else {
      dispatch(asyncDispatch(listContainers)({ action: 'LIST_CONTAINER', fromScreen: fromScreen }))
    }
  },

    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
    },
    saveActualWeight:(rowIndex, actualWeight) => {    
      let validObject = validateActualWeight(actualWeight);
          if(!validObject.valid){
            dispatch(requestWarning([{code:"mail.operations.mailbagenquiry.actualweight", description:"Do you wish to delete the actual weight of the mailbag?"}],{functionRecord:saveActualWeight, args:{rowIndex,actualWeight:'0'}}));
            }
            else if(!validObject.numeric){
              dispatch(requestValidationError('Please enter Numerical Values for Actual Weight', ''));   
              return{};
            }
            else {
            	dispatch(asyncDispatch(saveActualWeight)({'rowIndex': rowIndex, 'actualWeight':actualWeight}))
            }
      
    },
    openHistoryPopup: (rowIdx) => {
      dispatch(dispatchAction(openHistoryPopup)({rowIdx}));
    },
    clearReassignForm:(reassignFilterType)=>{
      dispatch(reset('reassignForm'));
      dispatch(change( 'reassignForm','reassignFilterType', reassignFilterType));
      dispatch({type:'CLEAR_REASSIGN_FORM', reassignFilterType: reassignFilterType });
    },
    clearTransferForm:(transferFilterType)=>{
      dispatch(reset('transferForm'));
      dispatch({type:'CLEAR_REASSIGN_FORM', transferFilterType:transferFilterType});
    },
    validateMailbags: (functionName, rowIdx) => {
      dispatch(asyncDispatch(validateMailbags)({functionName, rowIdx})).then(() =>{
        dispatch({type:functionName });
           } )
    },
    close: () => {
      dispatch({type:'CLEAR_REASSIGN_FORM'});
      dispatch({type:'CLOSE_POP_UP' });
    },
    imagepopupclose:()=>{
      dispatch({type:'CLOSE_IMAGE_POP_UP' });
    },

    clearContainerDetails :() =>{
      dispatch({type:'CLEAR_CONTAINERDETAILS_TRANSFER'});
    },
    checkDamage: (damageDetails) => {
      if(damageDetails.length===0){
        dispatch(requestValidationError('No damage details found for the mailbag',''))
      }
    },
    onApplyMailbagFilter:()=>{
      dispatch(dispatchAction(onApplyMailbagFilter)());
   },
   onClearMailbagFilter:()=>{
    dispatch(dispatchAction(onClearMailbagFilter)());
 },
 updateSortVariables: (sortBy, sortByItem) => {
  dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
},
saveSelectedMailbagsIndex:(indexes)=> {
  dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
},
pabuiltUpdate:(barrowCheck)=>{
  dispatch(dispatchAction(pabuiltUpdate)({barrowCheck:barrowCheck}))    
},
clearAddContainerPopover:()=>{
  dispatch(reset('transferForm'));
  dispatch(reset('reassignForm'));
},
updateOrgAndDest:(rowIndex, malOrgAndDest,isOrgUpd)=>{
  let validObject = validateOrgDest(malOrgAndDest,isOrgUpd)
  if (!validObject.valid) {
    dispatch(requestValidationError(validObject.msg, ''));
  }
  dispatch(asyncDispatch(updateOrgAndDest)({'rowIndex': rowIndex, 'malOrgAndDest':malOrgAndDest,'isOrgUpd':isOrgUpd}))      
},
checkValidOrgAndDest:(malOrgAndDest,isOrgUpd)=>{
  let validObject = validateOrgDest(malOrgAndDest,isOrgUpd)
  if (!validObject.valid) {
    dispatch(requestValidationError(validObject.msg, ''));
  }
},
onSaveOrgAndDest :(values)=>{
  let validObject= validateOrgDestForMultiple(values)
  if (!validObject.valid) {
    dispatch(requestValidationError(validObject.msg, ''));
  }
  else{
    dispatch(asyncDispatch(onSaveOrgAndDest)()).then((response) =>{
      dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST','orgDesChangeFlag':true}))
    } )
  }
},
onCloseModifyPopup:()=>{
  dispatch(reset('newMailbagsTable'));
  dispatch({type:'CLOSE_MODIFY_POPUP'})
},
saveSelectedContainersIndex:(indexes)=> {
  dispatch({ type: 'SAVE_SELECTED_CONTAINER_INDEX',indexes})
}
  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer