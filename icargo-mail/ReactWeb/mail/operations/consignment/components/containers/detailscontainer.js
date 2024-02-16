import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { maintainconsignment, save, validateMailBagForm, getFormData, validateMailBagRowDomestic } from '../../actions/filteraction';
import {
  deleteConsignmentAction, screenLoad, printConsignment, ShowPrintMailTag, addMultiple,
  getAddMultipleData, validateRSN, calculateReseptacles, selectMailBags, printMailTag, toggleFilter,
  newRowCheck, getLastRowData, validateForm, validateAddMultiple, populateMailbagId, onCloseFunction, getTotalCount, 
  deleteRows, newRSN, populateFlightNumber, saveSelectedMailbagsIndex, saveUnselectedMailbagsIndex,saveSelectedMultipleMailbagIndex,transportStageQualifierDefaulting} from '../../actions/commonaction';
import { asyncDispatch, dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { addRow, deleteRow, resetForm } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


const mapStateToProps = (state) => {
  return {
    screenMode: state.listconsignmentreducer.screenMode,
    //displayMode:state.commonReducer.displayMode,
    //filterValues: (state.filterReducer && state.filterReducer.values.flightnumber) ? state.filterReducer.values : filterValues,
    initialValuesinConsignment: {
      diableMailbagLevel:state.listconsignmentreducer ?state.listconsignmentreducer.diableMailbagLevel:null,
      remarks: state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.remarks : null,
      consignmentdate: state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.consignmentDate : null,
      consignmenttype: state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.type : null,
      consignmentsubtype: state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.subType : null,
      awbnumber:{
        masterDocumentNumber:state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.masterDocumentNumber : null,
        shipmentPrefix:state.listconsignmentreducer ? state.listconsignmentreducer.consignmentmodel.shipmentPrefix : null},
      //routingdetails:state.listconsignmentreducer ? state.listconsignmentreducer.routingdetails : null,
      flighttype: "O",
      'routingdetails': state.listconsignmentreducer.routingdetails ? state.listconsignmentreducer.routingdetails : [],
      printMailTagType:'ALLMAILBAGS',
      mailBagsTable:state.listconsignmentreducer.mailbagsdetails?state.listconsignmentreducer.mailbagsdetails:null,


    },
    initialValues: state.listconsignmentreducer && state.listconsignmentreducer.mailbagsdetails ? {'mailBagsTable':state.listconsignmentreducer.mailbagsdetails}:{},
    
    mailBags: state.listconsignmentreducer.mailbagsdetails,
    routingdetails: state.listconsignmentreducer.routingdetails,
    consignmentmodel: state.listconsignmentreducer.consignmentmodel,
    consignmentdate: state.listconsignmentreducer.consignmentmodel.consignmentDate,
    oneTimeType: state.commonReducer.oneTimeType,
    oneTimeMailClass: state.listconsignmentreducer.oneTimeMailClass,
    oneTimeSubType: state.commonReducer.oneTimeSubType,
    oneTimeCat: state.commonReducer.oneTimeCat,
    oneTimeMailServiceLevel:state.commonReducer.oneTimeMailServiceLevel,
    oneTimeFlightType: state.commonReducer.oneTimeFlightType,
    oneTimeTransportStage: state.commonReducer.oneTimeTransportStage,
    dataList: (state.form.mailBagsTable && state.form.mailBagsTable.values) ?
      state.form.mailBagsTable.values.mailBagsTable : state.listconsignmentreducer.mailbagsdetails,
    activeMailbagAddTab: state.listconsignmentreducer.activeMailbagAddTab,
    mailBagsPage: state.listconsignmentreducer.dataList,
    oneTimeRSN: state.commonReducer.oneTimeRSN,
    oneTimeHNI: state.commonReducer.oneTimeHNI,
    showPrintMailTagFlag: state.commonReducer.showPrintMailTagFlag,
    showAddMultipleFlag: state.commonReducer.showAddMultipleFlag,
    mailBagsSelected: state.commonReducer.mailBagsSelected,
    showAddConsignment: state.commonReducer.showAddConsignment,
    fromScreen: state.listconsignmentreducer.fromScreen,
    excelMailbags: state.listconsignmentreducer.excelMailbags,
    lastRowData: state.listconsignmentreducer.lastRowData,
    receptacles: state.commonReducer.receptacles,
    addMultipleData: state.listconsignmentreducer.addMultipleData,
    rsnData: state.listconsignmentreducer.rsnData,
    formData: state.listconsignmentreducer.formData,
    isDomestic: state.listconsignmentreducer.isDomestic,
    routingList: state.listconsignmentreducer.routingList,
    typeFlag: state.listconsignmentreducer.typeDisableFlag,
    selectedMailbagIndex: state.commonReducer.selectedMailbagIndex,
    style: state.commonReducer.style,
    count: state.commonReducer.count,
    diableMailbagLevel:state.listconsignmentreducer.diableMailbagLevel,
    newRowDataDomestic : {existingMailbag : false,statedBags:'1', mailbagWeight: { displayValue: 0, roundedDisplayValue: '0', displayUnit: state.commonReducer.defWeightUnit, unitSelect: true ,disabled:true  }},
    newRowData: state.listconsignmentreducer.lastRowData ?
      {
        destinationExchangeOffice: state.listconsignmentreducer.lastRowData.destinationExchangeOffice?state.listconsignmentreducer.lastRowData.destinationExchangeOffice:'',
        dsn: state.listconsignmentreducer.lastRowData.dsn?state.listconsignmentreducer.lastRowData.dsn:'',
        mailCategoryCode: state.listconsignmentreducer.lastRowData.mailCategoryCode?state.listconsignmentreducer.lastRowData.mailCategoryCode:'',
        mailClass: state.listconsignmentreducer.lastRowData.mailClass?state.listconsignmentreducer.lastRowData.mailClass:'',
        mailSubclass: state.listconsignmentreducer.lastRowData.mailSubclass?state.listconsignmentreducer.lastRowData.mailSubclass:'',
        originExchangeOffice: state.listconsignmentreducer.lastRowData.originExchangeOffice?state.listconsignmentreducer.lastRowData.originExchangeOffice:'',
        year: state.listconsignmentreducer.lastRowData.year?state.listconsignmentreducer.lastRowData.year:'',
        existingMailbag : false,statedBags:state.listconsignmentreducer.lastRowData.statedBags?state.listconsignmentreducer.lastRowData.statedBags:'1',
        registeredOrInsuredIndicator: state.listconsignmentreducer.lastRowData.registeredOrInsuredIndicator?state.listconsignmentreducer.lastRowData.registeredOrInsuredIndicator:'',
        highestNumberedReceptacle: state.listconsignmentreducer.lastRowData.highestNumberedReceptacle?state.listconsignmentreducer.lastRowData.highestNumberedReceptacle:'',
        mailOrigin:state.listconsignmentreducer.lastRowData.mailOrigin?state.listconsignmentreducer.lastRowData.mailOrigin:'',
        mailDestination:state.listconsignmentreducer.lastRowData.mailDestination?state.listconsignmentreducer.lastRowData.mailDestination:'',
        mailbagWeight: { displayValue: 0, roundedDisplayValue: '0', displayUnit: state.listconsignmentreducer.lastRowData.mailbagWeight?state.listconsignmentreducer.lastRowData.mailbagWeight.displayUnit:state.commonReducer.defWeightUnit, unitSelect: true ,disabled:false  }
      } : {statedBags:'1', mailCategoryCode: 'A', existingMailbag : false, mailbagWeight: { displayValue: 0, roundedDisplayValue: '0', displayUnit: state.commonReducer.defWeightUnit, unitSelect: true ,disabled:false  } },
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onlistMaintainConsignment: (displayPage, pageSize) => {
      dispatch(asyncDispatch(maintainconsignment)({ displayPage, pageSize, action: 'LIST' }))
    },
    onSave: (mailBags, routingDetails, isDomestic) => {
      let validObject = validateMailBagForm(mailBags, routingDetails, isDomestic);
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else {
        dispatch(asyncDispatch(save)()).then(() => {
          dispatch(asyncDispatch(screenLoad)());
          dispatch(reset('consignmentFilter'));
        })
      }
    },
    addRow: (newRowData, curRowData) => {
      let newRow = newRowCheck(curRowData);
      if (newRow) {
        dispatch(addRow("mailBagsTable", newRowData))
      }
      else {
        let validObject = validateForm(curRowData);
        if (!validObject.valid) {
          dispatch(requestValidationError(validObject.msg, ''));
        }
        else {
          dispatch(addRow("mailBagsTable", newRowData))
        }
      }
    },
    addRowDomestic: (curRowData, newRowDataDomestic) => {
      let newRow = newRowCheck(curRowData);
      if (newRow) {
        dispatch(addRow("mailBagsTable", newRowDataDomestic))
      }
      else {
        let validObject = validateMailBagRowDomestic(curRowData);
        if (!validObject.valid) {
          dispatch(requestValidationError(validObject.msg, ''));
        }
        else {
          dispatch(addRow("mailBagsTable", newRowDataDomestic));
        }
      }
    },
    deleteRow: () => {
      dispatch(dispatchAction(deleteRows)());
    },
    resetRow: () => dispatch(resetForm("mailBagsTable")),
    changeAddMailbagTab: (currentTab) => {
      dispatch({ type: 'CHANGE_ADD_MAILBAG_TAB', currentTab: currentTab });
    },
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(maintainconsignment)({ mode: "EXPORT", displayPage, pageSize }))
    },
    deleteConsignmentAction: () => {
      dispatch(requestWarning([{ code: "mail.operations.consignment.delete", description: "Do you wish to delete the consignment?" }], { functionRecord: deleteConsignmentAction })).then(() => {
        dispatch(asyncDispatch(screenLoad()))
      })
    },
    printConsignment: () => {
      dispatch(dispatchPrint(printConsignment)());
    },
    ShowPrintMailTag: () => {
      dispatch(dispatchAction(ShowPrintMailTag)());
    },
    calculateReseptacles: (rsnData, index) => {
      let validObject = validateRSN(rsnData);
      if (validObject && !validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else {
        dispatch(dispatchAction(calculateReseptacles)(index));
      }
    },
    showAddMultiplePanel: () => {
      dispatch({ type: 'ADD_MULTIPLE' })
    },
    selectMailBags: (data) => {
      dispatch(dispatchAction(selectMailBags)(data))
    },
    printMailTag: () => {
      dispatch(dispatchPrint(printMailTag)());
    },
    onToggleFilter: (showAddConsignment) => {
      dispatch(toggleFilter(showAddConsignment));
    },
    onClose: () => {
      dispatch({ type: 'CLOSE' })
    },
    getLastRowData: () => {
      dispatch(dispatchAction(getLastRowData)());
    },
    newRSN: (addMultipleData, rsnData) => {
      let validObject = validateAddMultiple(addMultipleData);
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else {
        dispatch(dispatchAction(newRSN)());
      }
    },
    getAddMultipleData: () => {
      dispatch(dispatchAction(getAddMultipleData)());
    },
    addMultiple: (addMultipleData, receptacles) => {
      let validObject = validateAddMultiple(addMultipleData);
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
      }
      else {
        dispatch(dispatchAction(addMultiple)(receptacles));
      }
    },
    populateMailbagId: (rowIndex) => {
      dispatch(dispatchAction(populateMailbagId)(rowIndex));
    },
    populateMailbagIdTextField: (event) => {
      //event.preventDefault();
      dispatch(asyncDispatch(populateMailbagId)(event.target.getAttribute("data-rowIndex")));
    },
    storeFormToReducer: () => {
      dispatch(dispatchAction(storeFormToReducer)());
    },
    getFormData: () => {
      dispatch(dispatchAction(getFormData)());
    },
    onCloseFunction: () => {
      dispatch(dispatchAction(onCloseFunction)());
    },
    changeSubTypeFlag: (data) => {
      dispatch({ type: 'TYPE_FLAG', data });
    },
    saveSelectedMailbagsIndex: (index, action) => {
      if(action==='SELECT'){
        dispatch(dispatchAction(saveSelectedMailbagsIndex)(index, action));
      }else{
        dispatch(dispatchAction(saveUnselectedMailbagsIndex)(index, action));
      }
    },
    saveSelectedMultipleMailbagIndex: (index) => {
        dispatch(dispatchAction(saveSelectedMultipleMailbagIndex)(index));
      
    },
    changeStyle: (numRows) => {
      if (numRows > 1) {
        dispatch({ type: 'CHANGE_STYLE', style: { maxHeight: '125px', overflowY: 'auto' } })
      } else {
        dispatch({ type: 'CHANGE_STYLE', style: { maxHeight: '125px' } })
      }

    },
    getTotalCount: () => {
      return dispatch(dispatchAction(getTotalCount)());
    },
    populateFlightNumber: (index) => {
        dispatch(dispatchAction(populateFlightNumber)(index));
    },
    transportStageQualifierDefaulting:(name)=>{
      dispatch(dispatchAction(transportStageQualifierDefaulting)(name));
    }
  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer