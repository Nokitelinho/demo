const intialState ={
    containerFilter:{},
    searchMode:'',
    screenMode:'initial'
  }
  
  
  
  const filterReducer = (state = intialState, action) => {
    switch (action.type) {
      case 'LIST_SUCCESS':
        return {...state,screenMode:'display'}; 	   
      case 'CLEAR_FILTER':
        return intialState;
        case 'UPDATE_FILTER_DETAILS':
          return {
              ...state,
              containerFilter: {
                destination:action.data.destination
              }
            };
        default:
        return state
    }
  }
  
  
  export default filterReducer;