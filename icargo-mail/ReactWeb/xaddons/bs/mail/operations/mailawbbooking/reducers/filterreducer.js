import { ActionType } from '../constants/constants.js'

const intialState = {
  screenMode: 'initial',
  awbDetails: null,
  totalWeight: '',
  totalPieces: '',
  filterValues: {
  },
  displayPage: null,
  pageSize: null,
  summaryFliter:{},
  tableFilter: {},
  awbFilter: {},
}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case ActionType.TOGGLE_FILTER :
      return { ...state, screenMode: action.screenMode }
    case ActionType.LIST_AWB_SUCCESS :
      return {
        ...state, awbDetails: action.data.mailBookingDetailsCollectionPage, screenMode: 'display',filterValues: {...action.data.awbFilter,
            flightNumber:{carrierCode:action.data.awbFilter.carrierCode,flightNumber:action.data.awbFilter.flightNumber,
              flightDate:action.data.awbFilter.flightDate}},displayPage:action.data.displayPage,pageSize: action.data.pageSize,
                summaryFilter:action.summaryFilter, awbFilter:action.data.awbFilter,
      }
    case ActionType.CLEAR_FILTER :
      return intialState;
    default:
      return state
  }
}


export default filterReducer;