const intialState = {
  relisted : false,
  screenMode:'initial',
  batchDisplay: 'hide',
  oneTimeValues:{},
  systemParameters: null,
  defaultFromDate:'',
  defaultToDate:''
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case 'SCREENLOAD_SUCCESS':
      return {...state,
        defaultFromDate: action.data.fromDate,
        defaultToDate: action.data.toDate,
        relisted: true,
        oneTimeValues:action.data.oneTimeValues
      }; 
      case 'SAVE_SELECTED_INDEX':
            return {...state,selectedIndex: action.indexes};
      
    default:
      return state;
  }
}
 
export default commonReducer;


