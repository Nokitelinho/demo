import { ActionType } from '../constants/constants.js'

const intialState = {
  screenMode: 'initial',
  dsnDetails: null,
  totalWeight: '',
  totalPieces: '',
  filterValues: {
  },
  displayPage: null,
  pageSize: null,
  summaryFliter:{},
  tableFilter: {},
  carditFilter: {},
  mailCountFromSyspar:'',

}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case ActionType.TOGGLE_FILTER :
      return { ...state, screenMode: action.screenMode }
    case ActionType.LIST_DSN_SUCCESS :
      return {
        ...state, dsnDetails: action.data.dsnDetailsCollectionPage, screenMode: 'display',filterValues: {...action.data.carditFilter,
            flightNumber:{carrierCode:action.data.carditFilter.carrierCode,flightNumber:action.data.carditFilter.flightNumber,
              flightDate:action.data.carditFilter.flightDate}},displayPage:action.data.displayPage,pageSize: action.data.pageSize,
                totalPieces:action.data.totalCount,totalWeight:action.data.totalWeight,summaryFilter:action.summaryFilter,
                  carditFilter:action.data.carditFilter,mailCountFromSyspar:action.data.mailCountFromSyspar
      }
    case ActionType.CLEAR_FILTER :
      return intialState;
    case ActionType.CLEAR_DSN_TABLE:
      return {...state,dsnDetails: null, screenMode: 'initial'};
    case ActionType.ERROR_SHOW:
      return {...state,dsnDetails: null, screenMode: 'initial'};
    default:
      return state
  }
}


export default filterReducer;