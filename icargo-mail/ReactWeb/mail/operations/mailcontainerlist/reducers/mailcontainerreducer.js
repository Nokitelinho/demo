const intialState ={
    containerDetails:[],
    screenMode:'initial',
    assignedTo:'',
    pageSize:100,
    summaryFilter:{},
	sort: {}

}



const mailcontainerreducer = (state = intialState, action) => {
  switch (action.type) {
    case 'LIST_SUCCESS':
      return {...state,screenMode:'display',summaryFilter:action.summaryFilter,containerDetails:action.containerDetails}   
      case 'CLEAR_FILTER':
        return intialState;
      case 'TOGGLE_FILTER':
        return {...state,screenMode: action.screenMode }
	  case 'UPDATE_SORT_VARIABLE':
        return {
            ...state,
            sort: action.data
        }    
        case 'CLEAR_TABLE':
        return intialState;  
      default:
      return state
  }
}


export default mailcontainerreducer;