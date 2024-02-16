const intialState ={
  containerFilter:{},
  searchMode:'',
  screenMode:'initial'
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'CLEAR_FILTER':
      return intialState;  
    case 'LIST_SUCCESS':
      return {...state,screenMode:'display'}; 	  
    default:
      return {...state,screenMode:'display'};
  }
}



export default filterReducer;