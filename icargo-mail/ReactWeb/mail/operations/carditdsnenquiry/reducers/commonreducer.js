
import { ActionType } from '../constants/constants.js'

const intialState = {
  relisted : false,
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  systemParameters: null,
  selectedMailbagVos: [],

}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case ActionType.SCREENLOAD_SUCCESS :
      return {...state,airportCode:action.data.airportCode,
                relisted: true,oneTimeValues:action.data.oneTimeValues, 
                systemParameters: action.data.systemParameters};

    case ActionType.SAVE_SELECTED_MAILBAGVOS:
      return {...state,selectedMailbagVos: action.data};
    
    default:
      return state;
  }
}
 
export default commonReducer;


