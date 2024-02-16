import { ActionType } from '../constants/constants.js'

const intialState = {
  sort : {},
  dsnFilter : {},
  selectedIndexes : [],

}

 const detailsReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case ActionType.UPDATE_SORT_VARIABLE :
      return {...state,sort:action.data};
    
    case ActionType.SAVE_FILTER :
      return {...state,dsnFilter:action.data};
      
    case ActionType.CLEAR_TABLE_FILTER :
      return {...state,dsnFilter:{}};

    case ActionType.SAVE_SELECTED_INDEXES :
      return {...state,selectedIndexes:action.data?action.data:[]};

    case ActionType.LIST_DSN_SUCCESS :
      return {...state,selectedIndexes:[]};

    default:
      return state;
  }
}
 
export default detailsReducer;