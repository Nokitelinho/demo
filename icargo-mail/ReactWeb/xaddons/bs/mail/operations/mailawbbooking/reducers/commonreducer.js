
import { ActionType } from '../constants/constants.js'

const intialState = {
  relisted : false,
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  systemParameters: null,
  selectedMailbagVos: [],
  carditFilter:{},

}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case ActionType.SCREENLOAD_SUCCESS :
      return {...state,airportCode:action.data.airportCode,
               oneTimeValues:action.data.oneTimeValues, 
                systemParameters: action.data.systemParameters};

    case ActionType.SAVE_SELECTED_MAILBAGVOS:
    return {...state,carditFilter:action.data.carditFilter,selectedMailbagVos:action.data.mailbagVos}

    case ActionType.LIST_AWB_SUCCESS:
      return {...state,oneTimeValues: action.data.oneTimeValues,relisted: true,}
    
    default:
      return state;
  }
}
 
export default commonReducer;


