import { ActionType } from '../constants/constants.js';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();

const intialState = {
  screenMode: 'initial',
  awbDetails: null,
  totalWeight: '',
  totalPieces: '',
  filterValues: {
  },
  displayPage: null,
  pageSize: '20',
  summaryFliter:{},
  tableFilter: {},
  awbFilter: {},
  flagForMandatoryFieldsError:0,
  sort: {},
  clearFlag: false,
  loadPlanBookingDetails:{},
  loadPlanSummaryFilter:{},
  loadPlanFilter:{},
  manifestBookingDetails:{},
  manifestSummaryFilter:{},
  manifestFilter:{},
  onListButtonClick: false,
}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case ActionType.TOGGLE_FILTER :
      return { ...state, screenMode: action.screenMode }
    case ActionType.LIST_AWB_SUCCESS :
      return {
        ...state, awbDetails:action.data.mailBookingDetailsCollectionPage? action.data.mailBookingDetailsCollectionPage:null, screenMode: 'display',filterValues: {...action.data.awbFilter,
            flightNumber:{carrierCode:action.data.awbFilter.carrierCode,flightNumber:action.data.awbFilter.flightNumber,
              flightDate:action.data.awbFilter.flightDate}},displayPage:action.data.displayPage,pageSize: action.data.pageSize,
                summaryFilter:action.summaryFilter, awbFilter:action.data.awbFilter,flagForMandatoryFieldsError:1,clearFlag: false,loadPlanSummaryFilter:{}
      }
    case ActionType.CLEAR_FILTER :
      return intialState;

    case 'GET_DISPLAY_PAGE':
    return {...state,displayPage:action.displayPage, clearFlag:action.clearFlag}  

    case ActionType.ERROR_SHOW  :
      return{
        ...state
      }
    case 'ERROR_NO_RECORDS':
    return{...state,awbDetails:null,manifestBookingDetails:null,loadPlanBookingDetails:null }
    
    case 'UPDATE_SORT_VARIABLE':
    return {
      ...state, sort: action.data
    }
    case 'FILTER_DETAILS':
    return {...state, summaryFilter:action.summaryFilter,screenMode: 'display'}

    case 'LIST_LOAD_PLAN_SUCCESS':
      return {...state, loadPlanBookingDetails:action.data.loadPlanDetailsCollectionPage? action.data.loadPlanDetailsCollectionPage:null, screenMode: 'display',
      displayPage:action.data.displayPage,pageSize: action.data.pageSize,loadPlanSummaryFilter:action.loadPlanSummaryFilter,
      loadPlanFilter:action.data.loadPlanFilter,clearFlag: false,summaryFliter:{}}

    case 'CLEAR_LOAD_PLAN_FILTER':
    return intialState;

    case 'LOAD_PLAN_FILTER_DETAILS':
    return {...state, loadPlanSummaryFilter:action.summaryFilter,screenMode: 'display'}
    case 'LIST_MANIFEST_SUCCESS':
      return {...state, manifestBookingDetails:action.data.manifestDetailsCollectionPage? action.data.manifestDetailsCollectionPage:null, screenMode: 'display',
      displayPage:action.data.displayPage,pageSize: action.data.pageSize,manifestSummaryFilter:action.manifestSummaryFilter,
      manifestFilter:action.data.manifestFilter,clearFlag: false,summaryFliter:{}}
    case 'CLEAR_MANIFEST_FILTER':
    return intialState;
    case 'MANIFEST_FILTER_DETAILS':
    return {...state, manifestSummaryFilter:action.summaryFilter,screenMode: 'display'}

    default:
      return state
  }
}


export default filterReducer;