
import { SCREEN_MODE } from '../constants';

const initialState = {
  screenMode: SCREEN_MODE.INITIAL,
  screenFilter: {},
  billingDetails: [],
  
  noData: true,
  displayPage: '',
  pageSize: 30,
  summaryFilter: {},
  sort: {},
  tableFilter: {},
  billingSchedulePage: {},
  billingScheduleData: [],
  filterEnabled: false,
  //nodata:false,
  clearFilter:false


};

const filterpanelreducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SCREENLOAD_SUCCESS':
      return {
        ...state
      };

    case 'LIST_SUCCESS':
      return {
        ...state,
        screenMode: SCREEN_MODE.DISPLAY,
        billingSchedulePage: action.billingScheduleMasterPage,
        billingScheduleData: action.billingScheduleMasterPage.results,
        noData: false,
        summaryFilter: action.summaryFilter,
        filterPanelValues: action.summaryFilter.filter,
        displayPage: action.displayPage,
        pageSize: action.pageSize,
        filterEnabled: false

      };

    case 'TOGGLE_SCREEN_MODE':
      return {
        ...state,
        screenMode: action.data
      };

    case 'CLEAR_FILTER':
      return {
        clearFilter:true,
        screenMode: SCREEN_MODE.INITIAL,
        screenFilter: {},
        initialState,
        billingSchedulePage: {},
        billingScheduleData: []
      };
      case 'CLEAR_TABLE':
      return {
        ...state,
        billingSchedulePage: {},
        billingScheduleData: []
      };
    case 'DISABLE_FILTER':
      return {
        ...state,
        filterEnabled: true
      };
    case 'LIST_SUCCESS_PAGINATION':
      return {
        ...state,
        screenMode: SCREEN_MODE.DISPLAY,
        filterValues: action.filterData,
        noData: false,
        summaryFilter: action.summaryFilter,
      }
    case 'NO_DATA':
      return {
        ...state,
        screenMode: SCREEN_MODE.DISPLAY,
        mailbagsdetails: [],
        noData: true,
      }
      case 'NO_DATA_3':
      return {
        ...state,
        screenMode: SCREEN_MODE.DISPLAY,
        nodata: true,
      }
    case 'UPDATE_SORT_VARIABLE':
      return {
        ...state,
        sort: action.data
      }
    case 'LIST_FILTER':
      return { ...state, tableFilter: action.filterData }
    case 'CLEAR_TABLE_FILTER':
      return { ...state, tableFilter: {} };

    default:
      return state;
  }
}

export default filterpanelreducer;