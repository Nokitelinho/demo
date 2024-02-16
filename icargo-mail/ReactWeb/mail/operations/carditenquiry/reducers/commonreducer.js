
const intialState = {
  relisted : false,
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  systemParameters: null,
 showPopOverFlag:false,
  defaultFromDate:'',
  defaultToDate:'',
  selectedMailbags:[]
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case 'SCREENLOAD_SUCCESS':
      return {...state,
        airportCode:action.data.airportCode,
        defaultFromDate: action.data.fromDate,
        defaultToDate: action.data.toDate,
        relisted: true,
        oneTimeValues:action.data.oneTimeValues, 
        systemParameters: action.data.systemParameters};
    case 'SHOW_POPOVER':
      return {...state,showPopOverFlag:true} ;
    case 'CLOSE_POPOVER':
      return {...state,showPopOverFlag:false} ;   
    case 'SELECTED_MAILBAGS_FOR_LISTAWB' :
        return{...state, selectedMailbags:action.selectedMailbags}
    default:
      return state;
  }
}
 
export default commonReducer;


