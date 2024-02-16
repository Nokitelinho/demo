
import { ActionType } from '../constants/constants.js'

const intialState = {
  activeTab: 'BookingView',
  relisted : false,
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  systemParameters: null,
  selectedMailbagVos: [],
  carditFilter:{}
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case ActionType.SCREENLOAD_SUCCESS :
      return {...state,airportCode:action.data.airportCode,
               oneTimeValues:action.data.oneTimeValues, 
                systemParameters: action.data.systemParameters,relisted:true};
                
    case ActionType.LIST_AWB_SUCCESS:
      return {...state,oneTimeValues: action.data.oneTimeValues,relisted: true,}
    
    case ActionType.FILTER_FROM_CARDIT_ENQUIRY:
      return{...state, carditFilter:action.data.carditFilter,selectedMailbagVos:action.data.selectedMailbags}  
      
    case 'CHANGE_TAB':
      return { ...state,activeTab: action.currentTab}
      
    default:
      return state;
  }
}
 
export default commonReducer;


