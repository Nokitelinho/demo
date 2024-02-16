const intialState = {
  displayPage: '',
  screenMode: 'initial',
  filterValues: {
  },
  noData: true,
  tableFilter: {},
  mailbagsdetails: [],
  mailMonitorSummary: [],
  selectedMailbagIndex: [],
  saved: false,
  fromScreen: '',
  initialValues: {}

}

const filterReducer = (state = intialState, action) => {
  switch (action.type) {

    case 'TOGGLE_FILTER':
      return { ...state, screenMode: action.screenMode }

    case 'LIST_SUCCESS':
      return {
        ...state, mailbagsdetails: action.mailbagsdetails, mailMonitorSummary: action.mailMonitorSummary,
        screenMode: 'display', filterValues: action.data.mailMonitorFilter, displayPage: action.data.mailMonitorFilter.displayPage, tableFilter: {}, noData: false
      }

    case 'NO_DATA':
      return { ...state, screenMode: 'display', noData: true, filterValues: action.data.mailPerformanceMonitorFilter, displayPage: action.data.mailPerformanceMonitorFilter.displayPage, tableFilter: {} }
    
    case 'CLEAR_FILTER':
      return intialState;
    
    case 'CLEAR_TABLE':
      return intialState;

    // case 'LIST_SUCCESS_PAGINATION':
    //   return {
    //     ...state, mailbagsdetails: action.mailbagDetails,
    //     displayPage: action.displayPage, screenMode: action.displayPage === '1' ? 'display' : action.screenMode, filterValues: action.data.mailPerformanceMonitorFilter,
    //     displayPage: action.data.mailPerformanceMonitorFilter.displayPage, tableFilter: {}, noData: false
    //   };

    case 'SAVE_SELECTED_INDEX':
      return { ...state, selectedMailbagIndex: action.indexes }

    case 'RETAIN_VALUES':
      return { ...state, }
      
    default:
      return state;
  }
}


export default filterReducer;