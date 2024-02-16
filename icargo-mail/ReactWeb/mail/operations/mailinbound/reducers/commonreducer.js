import * as constant from '../constants/constants'
const intialState = {
  displayMode: 'initial', //multi initial display
  oneTimeValues: {},
  density:'',
  ReadyforDeliveryRequired:'',
  defaultWeightUnit:'',
  previousWeightUnit:'',
  stationVolUnt:'',
  loggedAirport:'',
  valildationforimporthandling:'',
  popupAction:null,
  actionType:'',
  indexArray:[],
  isContinued:false,
  embargoInfo: false,
  validationforTBA:''
}

const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    // case constant.LIST_SUCCESS:
    //    return {...state,displayMode:action.mode};
      //return { ...state, displayMode: 'display' };
    case constant.LIST_CONTAINERS:
       //return { ...state, displayMode: 'display' };
      return { ...state, displayMode:action.mode };
    // case constant.CHANGE_DETAILPANEL_MODE:
    //   return {
    //     ...state,
    //     displayMode: action.mode
    //   };
      case constant.MAL_DSN_LIST_ON_CON_CLK:
      return {
        ...state,
        displayMode: action.mode
      }
    case constant.CLOSE_FLIGHT:
      return {
        ...state,
        displayMode: action.mode
      };
    case constant.OPEN_FLIGHT:
      return {
        ...state,
        displayMode: action.mode
      };
    case constant.SCREEN_LOAD_SUCCESS:
      return {
        ...state,
        oneTimeValues: action.oneTimeValues,
        density:action.density,ReadyforDeliveryRequired:action.ReadyforDeliveryRequired,
        defaultWeightUnit:action.filterVlues.defWeightUnit,previousWeightUnit:action.filterVlues.defWeightUnit,
        stationVolUnt:action.stationVolUnt,
        loggedAirport: action.port,
        valildationforimporthandling:action.valildationforimporthandling,
        validationforTBA:action.validationforTBA
      };
      case constant.CLEAR_FILTER:
      return {
        ...state,
        displayMode: 'initial'
      };
      case constant.UPDATE_MAILBAGS_IN_ADD_POPUP:
      return{
        ...state,
        previousWeightUnit:action.updatedMailbags[action.updatedMailbags.length-1].weight.displayUnit
      }
      case constant.MAIL_DSN_LIST:
      return{
        ...state,
        previousWeightUnit:action.mailbagData&&action.mailbagData[action.mailbagData.length-1]
          &&action.mailbagData[action.mailbagData.length-1].weightUnit?action.mailbagData[action.mailbagData.length-1].weightUnit:
            state.defaultWeightUnit
      }
      case constant.LIST_SUCCESS:
      return{
        ...state,
        previousWeightUnit:action.mailbagData&&action.mailbagData[action.mailbagData.length-1]
          &&action.mailbagData[action.mailbagData.length-1].weightUnit?action.mailbagData[action.mailbagData.length-1].weightUnit:
            state.defaultWeightUnit,displayMode:action.mode
      }
      case constant.CHANGE_TABLE_DETAILPANEL_MODE:
      return{
        ...state,
        previousWeightUnit:action.mailbagData&&action.mailbagData[action.mailbagData.length-1]
          &&action.mailbagData[action.mailbagData.length-1].weightUnit?action.mailbagData[action.mailbagData.length-1].weightUnit:
            state.defaultWeightUnit,displayMode:action.mode
      }
      case constant.ON_CONTAINER_CLICK:
      return{
        ...state,
        previousWeightUnit:action.mailbagData&&action.mailbagData[action.mailbagData.length-1]
          &&action.mailbagData[action.mailbagData.length-1].weightUnit?action.mailbagData[action.mailbagData.length-1].weightUnit:
            state.defaultWeightUnit
      }
      case constant.UPDATE_POPUP_HANDLER:
        return {...state,popupAction:action.fromPopup?"embargo":null, fromPopup:action.fromPopup , isContinued:action.fromPopup?true:false}

      case constant.EMBARGO_WARNING_POPUP_HANDLING:
      return {...state,popupAction:null, fromPopup:null}  

      case constant.EMBARGO_CONTINUE_ACTION:
        return{...state, actionType:action.actionType, indexArray:action.indexArray}

      case constant.EMBARGO_CONTINUE_ACTION_FOR_MAKING_ACTION_NULL_AFTER_ARRIVING:
      return{...state, actionType:null, indexArray:null}  
        
      case constant.CONTINUE_ACTION_AFTER_SAVE_FOR_EMBARGO:
      return{...state, isContinued:null, embargoInfo : action.embargoInfo}

    default:
      return state
  }
}
export default commonReducer;