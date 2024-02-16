import { ActionType } from '../constants/constants.js'

 const intialState = {

   displayMode:'initial',
   oneTimeValues:{},
   routeDetails:[],
   parentFilter:{},
 
 }

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    case ActionType.SCREENLOAD_SUCCESS:
      return {...state,oneTimeValues:action.data.oneTimeValues};
    case ActionType.SELECTED_COUNT:
      return {...state,selectedFlightDetailIndex:action.selectedFlightDetailIndex};
    case ActionType.SAVE_SELECTED_INDEX :
      return {...state, selectedFlightDetailIndex:action.indexes}; 
    case ActionType.SAVE_FILTERVALUES :
      return {...state, parentFilter:action.data.parentFilter}; 
    default:
      return state;
  }
}
 
export default commonReducer;


