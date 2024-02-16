
const intialState = {

  relisted: false,
  displayMode: 'initial',
  screenMode: 'initial',
  activeGraph: 'ServiceFailuresGraph',
  activeTab: 'MISSING_ORIGIN_SCAN',
  oneTimeValues: {},
  station:'',
  fromDate:'',
  toDate:'',
  tableFilterMissOrgScan: {},
  tableFilterMissDestScan: {},
  tableFilterMissBothScan: {},
  tableFilterOnTime: {},
  tableFilterNotOnTime: {},
  tableFilterRaised: {},
  tableFilterAccepted: {},
  tableFilterDenied: {},
  tableFilter: {},
  currentTableFilter: {},

  filterValues: {},

  sort: {},
  sortMissOrgScan: {},
  sortFilterMissDestScan: {},
  sortFilterMissBothScan: {},
  sortFilterOnTime: {},
  sortFilterNotOnTime: {},
  sortFilterRaised: {},
  sortFilterAccepted: {},
  sortFilterDenied: {},

  paginatedMailbags: {},
   paginatedMailbagsMissOrgScan: {},
    paginatedMailbagsMissDestScan: {},
     paginatedMailbagsMissBothScan: {},
      paginatedMailbagsOnTime: {},
       paginatedMailbagsNotOnTime: {},
        paginatedMailbagsRaised: {},
         paginatedMailbagsAccepted: {},
    paginatedMailbagsDenied: {}

}

const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return { ...state, screenMode: action.screenMode };
    case 'SCREENLOAD_SUCCESS':
      return { ...state, relisted: true, oneTimeValues: action.data.oneTimeValues,station:action.data.mailMonitorFilter.station,fromDate:action.data.mailMonitorFilter.fromDate,toDate:action.data.mailMonitorFilter.toDate };
    case 'CHANGE_TAB':
      return { ...state, activeTab: action.currentTab, currentTableFilter: action.currentTableFilter, sort: action.sort, paginatedMailbags:action.paginatedMailbags};
    case 'CHANGE_GRAPH':
      return { ...state, activeGraph: action.currentGraph };
    case 'APPLY_TABLE_FILTER':
      return { ...state, tableFilter: action.tableFilter };
    case 'CLEAR_TABLE_FILTER':
      return { ...state, currentTableFilter: {} };
    case 'UPDATE_SORT_VARIABLE':
      return { ...state, sort: action.data };
      
    case 'NO_DATA_LISTONTABCHANGE':
      return { ...state, screenMode: 'display', noData: true, filterValues: action.data.mailPerformanceMonitorFilter, displayPage: action.data.mailPerformanceMonitorFilter.displayPage, tableFilter: {} };
    case 'RETAIN_VALUES_LISTONTABCHANGE':
      return { ...state, };
    case 'LIST_ON_TAB_CHANGE_SUCCESS':
      return {
        ...state, mailbagsdetails: action.existingMailBagDetails, mailMonitorSummary: action.mailMonitorSummary,
        screenMode: 'display', filterValues: action.data.mailMonitorFilter, displayPage: action.data.mailMonitorFilter.displayPage, tableFilter: {}, noData: false,
        paginatedMailbags : action.paginatedMailbags
      }


    default:
      return state;
  }
}

export default commonReducer;


