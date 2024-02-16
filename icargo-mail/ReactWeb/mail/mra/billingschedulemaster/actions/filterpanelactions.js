import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { LIST_ACTION_URL, CLEAR_FILTER, LIST_SUCCESS, TOGGLE_SCREEN_MODE, SHOULD_SERVE_MOCKED_RESPONSE } from '../constants';
import { getMockedListResponse } from '../temp/sample-data/list_response';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import { addRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
import moment from 'moment';


export function clearFilter(values) {
  const { dispatch,getState } = values;
  const state = getState();
  state.form.BillingDetailsTable.values.BillingDetailsTable=[];
  dispatch({ type: 'CLEAR_FILTER' });
  dispatch(reset('billingScheduleFilter'));

 }
export function filterDisable(values) {
  const { dispatch } = values;
  dispatch({ type: 'DISABLE_FILTER' });
}
export function changeScreenMode(mode) {
  return { type: TOGGLE_SCREEN_MODE, data: mode };
}

export function updateSortVariables(data) {
  const { dispatch } = data;
  dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })

}

export function billingschedulemasterScreenList(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  let warningOK = args.warningOKFlag;
  // dispatch(reset('BillingDetailsTable'));
  // dispatch({ type: 'CLEAR_TABLE' });
  const displayPage = args && args.displayPage ? args.displayPage : state.filterpanelreducer.displayPage
  const pageSize = args && args.pageSize ? args.pageSize : state.filterpanelreducer.pageSize;
  const billingScheduleFilter = (state.form.billingFilter.values) ? state.form.billingFilter.values : {}
  billingScheduleFilter.displayPage = displayPage?displayPage:args.data.displayPage;
  billingScheduleFilter.pageSize = pageSize;
  const data = { billingScheduleFilter }
  if (!billingScheduleFilter.billingType) {
    return Promise.reject(new Error("Please Select Billing Type"));
    dispatch({ type: 'NO_DATA', data });
  }
  if (!billingScheduleFilter.year) {
    return Promise.reject(new Error("Please Enter Year"));
  }
  if (warningOK) {
    dispatch(reset('BillingDetailsTable'));
    dispatch({ type: 'CLEAR_TABLE' });
    state.form.BillingDetailsTable.values.BillingDetailsTable = [];
    state.form.BillingDetailsTable.initial.BillingDetailsTable = [];
    state.detailspanelreducer.parameterMap=new Map();
    if (billingScheduleFilter.billingPeriod == '' || billingScheduleFilter.billingPeriod == "M") {

      for (let i = 1; i <= 12; i++) {
        const newRowData = {
          periodNumber: getPeriodNumber(i, billingScheduleFilter), billingPeriodFromDate: getFirstday(i, billingScheduleFilter), billingPeriodToDate: getLastday(i, billingScheduleFilter), gpaInvoiceGenarateDate: '',
          passFileGenerateDate: '', masterCutDataDate: '', airLineUploadCutDate: '', invoiceAvailableDate: '', postalOperatorUploadDate: ''
        }
        dispatch(addRow("BillingDetailsTable", newRowData));
      }
    }
    else {
      let i = 1;
      while (i <= 24) {
        const newRowData = {
          periodNumber: getPeriodNumber_bimonthly(i, billingScheduleFilter), billingPeriodFromDate: getFirstday_bimonthly(i, billingScheduleFilter), billingPeriodToDate: getLastday_bimonthly(i, billingScheduleFilter), gpaInvoiceGenarateDate: '',
          passFileGenerateDate: '', masterCutDataDate: '', airLineUploadCutDate: '', invoiceAvailableDate: '', postalOperatorUploadDate: ''
        }
        i++;
        dispatch(addRow("BillingDetailsTable", newRowData));
      }
    }
  }
  else {
    const url = LIST_ACTION_URL;
    return makeRequest({
      url,
      data: { ...data }
    }).then(function (response) {
      if (!isEmpty(response.results)) {
        dispatch(reset('BillingDetailsTable'));
        dispatch({ type: 'CLEAR_TABLE' })
        state.form.BillingDetailsTable.values.BillingDetailsTable = [];
        state.form.BillingDetailsTable.initial.BillingDetailsTable = [];
      }
      handleScreenListResponse(dispatch, response, args.mode, billingScheduleFilter);
      return response;
    }).catch(error => {
      return error;
    });
  }
}
function handleScreenListResponse(dispatch, response, action, filtervalues) {
  if (!isEmpty(response.results)) {
    let filterData = response.results
    const { billingScheduleMasterPage } = response.results[0]
    if (action === 'LIST') {
      const filterdata = filtervalues;
      let pageSize = filtervalues.pageSize;
      const summaryFilter = makeSummaryFilter(filtervalues);
      if (billingScheduleMasterPage.results && billingScheduleMasterPage.results.length > 0) {
        dispatch({ type: 'LIST_SUCCESS', billingScheduleMasterPage, summaryFilter, pageSize });
      }
    }
  }
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
  var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
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
function constructScreenListSuccess(data) {
  return { type: LIST_SUCCESS, data };
}
function makeSummaryFilter(data) {
  let filter = {};
  let popOver = {};
  let count = 0;
  let popoverCount = 0;
  if (data.billingType && data.billingType.length > 0) {
    if (count < 6) {
      filter = { ...filter, billingType: data.billingType };
      count++;
    } else {
      popOver = { ...popOver, billingType: data.billingType };
      popoverCount++;
    }
  }
  if (data.billingPeriod && data.billingPeriod.length > 0) {
    if (count < 6) {
      filter = { ...filter, billingPeriod: data.billingPeriod };
      count++;
    } else {
      popOver = { ...popOver, billingPeriod: data.billingPeriod };
      popoverCount++;
    }
  }
  if (data.year && data.year.length > 0) {
    if (count < 6) {
      filter = { ...filter, year: data.year };
      count++;
    } else {
      popOver = { ...popOver, year: data.year };
      popoverCount++;
    }
  }
  const summaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
  return summaryFilter;
}


