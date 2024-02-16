import { ActionType} from '../constants/constants';

const intialState = {
  relisted : false,  
  oneTimeValues:{},
  displayMode:'initial',
  screenMode:'initial',
  screenFilterMode:'edit',
  maxFetchCount:null,
  filterValuesFromAdvpay:{}
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {  
    case ActionType.SCREENLOAD_SUCCESS:
      return {...state,relisted:true,screenMode:'initial',displayMode:'initial',screenFilterMode:'edit',maxFetchCount:action.data.maxPageCount};
    case ActionType.FILTER_FRM_ADVPAY:
      return {...state,filterValuesFromAdvpay:action.data}
      case ActionType.EXCEL_UPLOAD_SUCCESS:
        return { ...state};
    default:
      return state;
  }
}
 
export default commonReducer;


