import * as constant from '../constants/constants';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();
const intialState = {

  activeLyingListTab:'GROUP_VIEW',
  listDisplayPage:'',
  groupDisplayPage:'',
  lyinglistMailbags:'',
  lyingGroupMailbags:'',
  lyingSummary:'',
  lyingFilterApplied:false,
  filterValues :{
  },
  lyingmailbagcount:'',
  selectedLyinglistIndex:[]

}

 const lyingListReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.SCREENLOAD_SUCCESS: {
        return {
            ...state,
            filterValues: {
                fromDate:currentDate,
                toDate: currentDate,
                lyingFilterApplied:true
            }
        }
    }
  case constant.CHANGE_LYING_TAB:
      return { ...state,activeLyingListTab: action.data
      }
  case constant.LYING_LIST_VIEW:
      return { ...state,lyinglistMailbags:action.lyinglistMailbags,filterValues:action.data.mailbagFilter,listDisplayPage:action.data.mailbagFilter.displayPage,lyingSummary:action.carditSummary
      }

   case constant.LYING_GROUP_VIEW:
      return { ...state,lyingGroupMailbags:action.lyinglistGroupedMailbags,filterValues:action.data.mailbagFilter,groupDisplayPage:action.data.mailbagFilter.displayPage,lyingSummary:action.carditSummary
      }
  case constant.LYING_FILTER_APPLIED:
      return {...state,lyingFilterApplied:true}
  case constant.LYING_MAIL_SELECTED:
      return {...state,selectedLyinglistIndex:action.indexes,lyingmailbagcount:action.count}  
    case constant.CLEAR_LYINGLIST_FILTER:
      return { ...state,lyinglistMailbags:'', lyingGroupMailbags:'', filterValues:{},lyingSummary:{},lyingFilterApplied:false}  
   
    default:
      return state;
  }
}
 
export default lyingListReducer;


