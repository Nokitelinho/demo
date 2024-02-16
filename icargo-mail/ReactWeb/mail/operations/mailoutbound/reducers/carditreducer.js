
import * as constant from '../constants/constants';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();
const intialState = {

  activeCarditTab:'GROUP_VIEW',
  listDisplayPage:'1',
  groupDisplayPage:'1',
  carditMailbags:'',
  carditGroupMailbags:'',
  carditSummary:'',
  carditFilterApplied:false,
  carditfilterValues :{
  },
  carditmailbagcount:0,
  selectedCarditIndex:[],
  carditView:'initial',
  openScanTimePopup: false,
  assignData: {}
}

 const carditReducer = (state = intialState, action) => {
  switch (action.type) {
    case constant.CHANGE_CARDITS_TAB:
      return { ...state,activeCarditTab: action.data
      }
    case constant.CARDIT_LIST_VIEW:
      return { ...state,selectedCarditIndex:[],carditmailbagcount:0,carditMailbags:action.carditMailbags,carditSummary:action.carditSummary,carditfilterValues:action.data.carditFilter,listDisplayPage:action.data.carditFilter.displayPage
      }

    case constant.CARDIT_GROUP_VIEW:
      return { ...state,selectedCarditIndex:[],carditmailbagcount:0,carditGroupMailbags:action.carditGroupMailbags,carditSummary:action.carditSummary,carditfilterValues:action.data.carditFilter,groupDisplayPage:action.data.carditFilter.displayPage
      }
    case constant.SAVE_CARDIT_LIST_FILTER:
      return {...state,carditMailbags:action.carditMailbags?action.carditMailbags:'',carditSummary:action.carditSummary?action.carditSummary:'',carditfilterValues:action.data.carditFilter,listDisplayPage:action.data.carditFilter.displayPage}
    case constant.SAVE_CARDIT_GROUP_FILTER:
      return {...state,carditGroupMailbags:action.carditGroupMailbags?action.carditGroupMailbags:'',carditSummary:action.carditSummary?action.carditSummary:'',carditfilterValues:action.data.carditFilter,groupDisplayPage:action.data.carditFilter.displayPage}
    case constant.CARDIT_FILTER_APPLIED:
      return {...state,carditFilterApplied:true}
    case constant.CLEAR_FILTER_APPLIED:
       return {...state,carditMailbags:'',carditGroupMailbags:'',carditfilterValues :{},carditSummary:{},carditFilterApplied:false,carditView:'initial'}
    case constant.CARDIT_MAIL_SELECTED:
      return {...state,selectedCarditIndex:action.indexes,carditmailbagcount:action.count}    
    case constant.CHANGE_CARDITLYINGLIST_TAB:
        return {
          ...state,
          selectedCarditIndex:[],
          carditmailbagcount:0
        }
    case constant.CLEAR_FILTER:
      return { ...state,carditMailbags:'',carditGroupMailbags:'',carditfilterValues :{}, carditSummary:{},carditFilterApplied:false,carditView:'initial'} 
      case constant.TOGGLE_CARDIT_VIEW:
      return {...state,carditfilterValues:{fromDate:currentDate,toDate:currentDate},carditFilterApplied:(state.carditView=='initial')?true:false,carditView: (state.carditView=='initial') ? 'expanded':(state.carditView=='expanded')?'initial':'expanded'
       }
    case constant.CLEAR_FLIGHT_TABLE_DATA: 
       return  {
        ...state
       }
     case constant.ASSIGN_SCAN_TIME_POPUP_OPEN: 
       return {
        ...state,
        assignData: action.data,
        openScanTimePopup: true
       }
      case constant.ASSIGN_SCAN_TIME_CLOSE:
        return {
          ...state,
        assignData: {},
        openScanTimePopup: false
        }
    default:
      return state;
  }
}
 
export default carditReducer;


