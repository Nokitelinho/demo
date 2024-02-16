import { ActionType} from '../constants/constants';

const intialState = {
  relisted : false,  
  oneTimeValues:{},
  displayMode:'initial',
  screenMode:'initial',
  screenFilterMode:'edit',
  maxFetchCount:null
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {  
    case ActionType.SCREENLOAD_SUCCESS:
      return {...state,relisted:true,oneTimeValues:action.data.oneTimeValues,screenMode:'initial',displayMode:'initial',screenFilterMode:'edit',maxFetchCount:action.data.maxPageCount};
case ActionType.SAVE_SELECTED_INDEX:
            return {...state,selectedIndex: action.indexes};
    default:
      return state;
  }
}
 
export default commonReducer;


