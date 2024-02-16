
 const intialState = {

   displayMode:'initial',
   oneTimeValues:{},
   selectedMailboxIndex:[],

 }

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case 'SCREENLOAD_SUCCESS':
      return {...state,oneTimeValues:action.data.oneTimeValues};
      case 'SELECTED_COUNT':
      return {...state,selectedMailEventIndex:action.selectedMailEventIndex};
      case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailEventIndex:action.indexes};
      case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailboxIndex:action.indexes}
    default:
      return state;
  }
}
 
export default commonReducer;


