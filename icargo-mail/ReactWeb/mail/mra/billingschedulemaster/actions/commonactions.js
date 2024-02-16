import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { SCREENLOAD_ACTION_URL, SCREENLOAD_SUCCESS, SAVE_ACTION_URL, VALIDATE_ACTION_URL, Errors} from '../constants';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { getMockedScreenLoadResponse } from '../temp/sample-data/screenload_response';
import * as constant from '../constants/index'
import { deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { closePopupWindow } from 'icoreact/lib/ico/framework/component/common/modal/popuputils';
import { change } from 'icoreact/lib/ico/framework/component/common/form';
import moment from 'moment';
import { billingschedulemasterScreenList} from './filterpanelactions.js'
const mockResponse = (request) => {
  return getMockedScreenLoadResponse();
}

export function screenLoad(args) {
  const { dispatch, getState } = args;
  const state = getState();
  const url = 'rest/mail/mra/defaults/billingschedulemaster/screenload';
  return makeRequest({
    url, data: {}
  }).then(function (response) {
    handletScreenloadResponse(dispatch, response);
    return response;
  })
    .catch(error => {
      return error;
    });
}

function handletScreenloadResponse(dispatch, response) {

  if (response.results) {
    dispatch(constructScreenLoadSuccess(response.results[0]));
  }
}

export function constructScreenLoadSuccess(data, state) {
  return { type: constant.SCREENLOAD_SUCCESS, data };
}
export function filterDisable(values) {

}
export function onClose(values) {
  const { getState } = values;
  const state = getState();
  navigateToScreen('home.jsp', {});
}
export function onCloseFunction() {
  navigateToScreen('home.jsp', {});
}
export const getLastRowData = (values) => {
  const { dispatch, getState } = values;
  const state = getState();
  const curBillingDetails = state.form.BillingDetailsTable ?
    state.form.BillingDetailsTable.values.BillingDetailsTable[(state.form.BillingDetailsTable.values.BillingDetailsTable.length) - 1] : null;
  dispatch({ type: 'LAST_ROW', curBillingDetails });
}
export function newRowCheck(curRowData) {

  let operationFlag = "";
  let newRow = true;

  return newRow;
}
export function deleteRows(values) {
  const { dispatch, getState } = values;
  const state = getState();
  const rowIndex = state.commonReducer.selectedBillingDataIndex ? state.commonReducer.selectedBillingDataIndex : [];
  const billingDetailslist = state.form.BillingDetailsTable ?
    state.form.BillingDetailsTable.values.BillingDetailsTable : null
  if (rowIndex.length > 0) {
    for (let i = 0; i < rowIndex.length; i++) {
      const index = rowIndex[(rowIndex.length) - (i + 1)].toString(); 
      dispatch({ type: 'CLEAR_TABLE' });
        dispatch(deleteRow("BillingDetailsTable", index));
    }
  } else {
    dispatch(requestValidationError('Please select atleast one row to delete', ''));
  }
}
export const warningHandler = (action, dispatch, state) => {
  
  switch (action.type) {
    case "__DELEGATE_WARNING_ONOK":
      if (action.executionContext) {
        const warningCode = action.warningCode;
        dispatch(dispatchAction(action.executionContext.functionRecord)({ warningOKFlag: true, data: action.executionContext.args }))


      }
      break;
    case "__DELEGATE_WARNING_ONCANCEL":
      if (action.executionContext) {
        const warningCode = action.warningCode;
        if (warningCode == 'mail.mra.billingschedulemaster.oncancel') {
        }
      }
      break;
    default:
      break;
  }
}
export function saveSelectedBillingIndex(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  const index = args;
  let indexes = state.commonReducer.selectedBillingDataIndex ? state.commonReducer.selectedBillingDataIndex : [];
  indexes.push(index);
  dispatch({ type: 'SAVE_SELECTED_INDEX', indexes });
}

export function saveUnselectedBillingIndex(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  const index = args;
  let indexes = state.commonReducer.selectedBillingDataIndex ? state.commonReducer.selectedBillingDataIndex : [];
  if (index > -1) {
    let ind = -1;
    for (let i = 0; i < indexes.length; i++) {
      var element = indexes[i];
      if (element === index) {
        ind = i;
        break;
      }
    }
    if (ind > -1) {
      indexes.splice(ind, 1);
    }
  } else {
    indexes = [];
  }
  dispatch({ type: 'SAVE_SELECTED_INDEX', indexes });
}

export const selectBillingDetails = (values) => {
  const { args, dispatch } = values;
  const billDetails = args;
  dispatch({ type: 'SELECT_BILL_DETAILS', billDetails });
}
const getBillingDetails = (data, deleted, parameterMap) => {
  let billingDetails = [];
  let deletedDetails = [];
  data.map((item, index) => {
    billingDetails.push({ ...item, parametersList: parameterMap.get(index) });
  });
  deleted.map((item, index) => {
    deletedDetails.push({ ...item, __opFlag: 'D',parametersList: parameterMap.get(index) });
  });
  const clubbed = [...deletedDetails, ...billingDetails];
  return clubbed
}
export const validateBillingDates = (values) => {
  const { args, dispatch, getState } = values;
  const state = getState();
  const data = state.form.BillingDetailsTable.values.BillingDetailsTable;
  let flag = true;
  for (let i = 0; i < data.length; i++) {
    if (data[i].billingPeriodFromDate == "") {
      dispatch(requestValidationError('Please select Billing Dates', ''));
      flag = false;
    }
    if (data[i].billingPeriodToDate == "") {
      dispatch(requestValidationError('Please select Billing Dates', ''));
      flag = false;
    }
    if (data[i].gpaInvoiceGenarateDate && data[i].passFileGenerateDate && (moment.utc(data[i].gpaInvoiceGenarateDate).diff(moment.utc(data[i].passFileGenerateDate))) > 0) {
      dispatch(requestValidationError('PASS File generation date cannot be less than GPA Invoice generation date for Period number ' + data[i].periodNumber, ''));
      flag = false;
    }
  }
  return flag;
}
export function saveSelectedMultipleBillingIndex(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  let index = args;
  let indexes =state.commonReducer.selectedBillingDataIndex ? state.commonReducer.selectedBillingDataIndex : [];
  indexes.push(...index);
  dispatch({ type: 'SAVE_SELECTED_MULTIPLE_INDEX', indexes});
}
export function validateBillingDetails(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  if (validateBillingDates(values)) {
  const url = VALIDATE_ACTION_URL;
  let billingScheduleDetailList = getBillingDetails(state.form.BillingDetailsTable.values.BillingDetailsTable, state.form.BillingDetailsTable.values.deleted, state.detailspanelreducer.parameterMap);
  const request = { billingScheduleDetailList };
  return makeRequest({
    url,
    data: { ...request }
  }).then(function (response) {
    return response;
  })
    .catch(error => {
      return error;
    });
}
  else
  return Promise.resolve({errors:"",staus:"Fail"});
}
export function onSave(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  const url = SAVE_ACTION_URL;
  const data = state.form.BillingDetailsTable.values.BillingDetailsTable
  if (validateBillingDates(values)) {
  let billingScheduleDetailList = getBillingDetails(state.form.BillingDetailsTable.values.BillingDetailsTable, state.form.BillingDetailsTable.values.deleted, state.detailspanelreducer.parameterMap);
  const request = { billingScheduleDetailList };
  return makeRequest({
    url,
    data: { ...request }
  }).then(function (response) {
    dispatch(asyncDispatch(billingschedulemasterScreenList)({ displayPage:'1', mode: 'LIST' ,'pageSize':'30' }))
    return response;
  })
    .catch(error => {
      return error;
    });
  }
  else
  return Promise.resolve({errors:"",staus:"Fail"});
}
function getFirstday(i, filtervalues) {
  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  var date = new Date(filtervalues.year, i - 1, 1);
  date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear()
  return date;
}
function getLastday(i, filtervalues) {
  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  var date1 = new Date(filtervalues.year, i, 1)
  var date = new Date(date1)
  date.setDate(date.getDate() - 1)
  date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear()
  return date;
}
function getPeriodNumber(i, filtervalues) {
  let date = moment(getCurrentDate(), 'DD-MMM-YYYY').format('DD-MM-YYYY');
  let year = filtervalues.year;
  let month = 1;
  let lastDigit = 1;
  let zero = '';
  let appendtoLastdigit = 0
  if (i < 10)
    zero = 0;
  else zero = '';
  month = i;
  lastDigit = '1';
  let day = moment().format('DD');
  let periodNumber = year + zero + month + appendtoLastdigit + lastDigit;
  return periodNumber;
}
function getFirstday_bimonthly(i, filtervalues) {
  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  if (i % 2 == 0) {
    var date = new Date(filtervalues.year, (i / 2) - 1, 1);
    date.setDate(date.getDate() + 15);
    date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear();
  }
  else {
    var date = new Date(filtervalues.year, i - (i / 2), 1);
    date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear()
  }
  return date;
}
function getLastday_bimonthly(i, filtervalues) {
  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];
  if (i % 2 == 1) {
    var date = new Date(filtervalues.year, i - (i / 2), 1);
    date.setDate(date.getDate() + 14);
    date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear()
    return date;
  }
  else {
    var date = new Date(filtervalues.year, i / 2, 1)
    date.setDate(date.getDate() - 1)
    date = date.getDate() + "-" + monthNames[date.getMonth()] + "-" + date.getFullYear()
  }
  return date;
}
function getPeriodNumber_bimonthly(i, filtervalues) {
  let date = moment(getCurrentDate(), 'DD-MMM-YYYY').format('DD-MM-YYYY');
  let year = filtervalues.year;
  let month = 1;
  let lastDigit = 1;
  let zero = '';
  let odd = 1;
  if (i % 2 == 0) {
    lastDigit = '2';
  }
  else {
    lastDigit = '1';
  }
  let appendtoLastdigit = 0
  if (i < 19) {
    zero = 0;
  }
  else {
    zero = '';
  }
  if (i % 2 == 1)
    month = Math.ceil(i / 2);
  else
    month = i / 2;
  let day = moment().format('DD');
  let periodNumber = year + zero + month + appendtoLastdigit + lastDigit;
  return periodNumber;
}
export const generatePeriodNumber = (values) => {
  const { dispatch, getState, args } = values;
  const state = getState();
  const index = args.rowIndex;
  let filterValues = '';
  let billingPeriod = '';
  let lastDigit = '01';
  if (state.form.billingFilter) {
    filterValues = state.form.billingFilter.values;
  }
  let billingList = state.form.BillingDetailsTable ? state.form.BillingDetailsTable.values.BillingDetailsTable : null
  let selectedRow = billingList[index];
  if(selectedRow.billingPeriodFromDate){
   let day=moment(selectedRow.billingPeriodFromDate).format('DD');
   if(day==16){
    lastDigit='02';
   }
  }
  if (selectedRow.billingPeriodToDate) {
    let month = moment(selectedRow.billingPeriodToDate).format('MM');
    let year = moment(selectedRow.billingPeriodToDate).format('YYYY');
    if (filterValues.billingPeriod) {
      billingPeriod = filterValues.billingPeriod;
    }
    let periodNumber = year + month + lastDigit;
    dispatch(change("BillingDetailsTable", `BillingDetailsTable.${index}.periodNumber`, periodNumber));   
  }
}
