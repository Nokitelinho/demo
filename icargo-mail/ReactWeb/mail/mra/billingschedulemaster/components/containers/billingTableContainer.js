import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { asyncDispatch, dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import moment from 'moment';
import BillingDetailsTable from '../panels/BillingDetailsTable.jsx';
import { initialValues } from '../../selectors/filterpanelselector';
import { billingschedulemasterScreenList, changeScreenMode, clearFilter,filterDisable } from '../../actions/filterpanelactions';
import { onAddParameter, onParamterOK } from '../../actions/detailspanelactions';
import { addRow, deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {
  getLastRowData, newRowCheck, deleteRows, selectBillingDetails,
  saveSelectedBillingIndex, saveUnselectedBillingIndex,generatePeriodNumber,saveSelectedMultipleBillingIndex
} from '../../actions/commonactions';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
import { onCloseParameter, saveParameter} from '../../actions/popupactions';
const getNewBillingDetails = (state) => (state.billingschedulemasterReducer ?
  state.billingschedulemasterReducer.selectedbillingDetails : []);
const getPeriodNumber_Monthly = (data) => {
  let date = moment(getCurrentDate(), 'DD-MMM-YYYY').format('DD-MM-YYYY');
  let year = data.year;
  let billingPeriod = data.billingPeriod;
  let lastDigit = '01';
  let periodNumber = year + lastDigit;
  return periodNumber;

}


const mapStateToProps = (state) => {
  return {
    screenMode: state.filterpanelreducer.screenMode,
    lastRowData: state.detailspanelreducer.lastRowData,
    billingSchedulePage: state.filterpanelreducer.billingSchedulePage,
    filterData: state.filterpanelreducer.summaryFilter?state.filterpanelreducer.summaryFilter.filter:{},
    billingScheduleData: (state.form.BillingDetailsTable  && state.form.BillingDetailsTable.values && state.form.BillingDetailsTable.values.BillingDetailsTable.length > 0) ? state.form.BillingDetailsTable.values.BillingDetailsTable : state.filterpanelreducer.billingScheduleData,
    parameterData: state.detailspanelreducer.parameterData,
    filterValues:state.form.billingFilter.values,
    newRowData: {
      periodNumber: '', billingPeriodFromDate: '', billingPeriodToDate: '', gpaInvoiceGenarateDate: '',
      passFileGenerateDate: '', masterCutDataDate: '', airLineUploadCutDate: '', invoiceAvailableDate: '', postalOperatorUploadDate: ''
    },
    selectedBillingDataIndex: state.detailspanelreducer.selectedBillingDataIndex,
    parameterExists: state.detailspanelreducer.parameterExists,
    parameterMap: state.detailspanelreducer.parameterMap,
    showAddPopup: state.detailspanelreducer.showAddPopup,
    filterEnabled: state.filterpanelreducer.filterEnabled,
    noData:state.filterpanelreducer.noData,
    oneTimeMap: state.commonReducer.oneTimeMap
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    onAddParameter: (value) => {
      dispatch(dispatchAction(onAddParameter)(value));
    },
    onParamterOK: (index) => {
      dispatch(dispatchAction(onParamterOK(index)));
    },
    onChangeScreenMode: (newMode) => {
      dispatch(changeScreenMode(newMode));
    },

    onClearFilter: () => {
      dispatch(dispatchAction(clearFilter)());
    },

    submitFilter: () => {
      dispatch(asyncDispatch(billingschedulemasterScreenList)({}));
    },
    addRow: (newRowData, curRowData) => {
      dispatch(dispatchAction(filterDisable)());
      let newRow = newRowCheck(curRowData);
      if (newRow) {
        dispatch(addRow("BillingDetailsTable", newRowData));
      }

    },
    onDeleteRow: (rowIndex) => {
      dispatch(deleteRow("BillingDetailsTable", rowIndex))
    },
    deleteRow: () => {
      dispatch(dispatchAction(deleteRows)());
    },
    getLastRowData: () => {
      dispatch(dispatchAction(getLastRowData)());
    },
    saveSelectedBillingIndex: (index, action) => {
      if (action === 'SELECT') {
        dispatch(dispatchAction(saveSelectedBillingIndex)(index, action));
      } else {
        dispatch(dispatchAction(saveUnselectedBillingIndex)(index, action));
      }
    },
    saveSelectedMultipleBillingIndex: (index,action) => {
      if (action === 'SELECT') {
        dispatch(dispatchAction(saveSelectedMultipleBillingIndex)(index,action));
      } else {
        dispatch(dispatchAction(saveUnselectedBillingIndex)(index, action));
      }
        
    },
    selectBillingDetails: (data) => {
      dispatch(dispatchAction(selectBillingDetails)(data))
    },
    onlistdetails: (displayPage, pageSize) => {
      dispatch(asyncDispatch(billingschedulemasterScreenList)({ displayPage, pageSize, mode: 'LIST' }))
    },
    generatePeriodNumber:(index) => {
      dispatch(dispatchAction(generatePeriodNumber)({rowIndex:index}));
    },
    onCloseParameter: () => {
      dispatch(dispatchAction(onCloseParameter)());
  },
  saveParameter: () => {
       dispatch(dispatchAction(saveParameter)());
   }
  }
}



export default connectContainer(mapStateToProps, mapDispatchToProps)(BillingDetailsTable);