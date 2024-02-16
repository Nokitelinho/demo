
import moment from 'moment';
const intialState = {
  relisted : false,  
  displayMode:'initial',
  screenMode: 'initial',
  oneTimeValues:{},
  origin:[],
  displayOrigin:[],
  displayDestination:[],
  mailSubClassCodes:[],
  filterInitialValues:{}

}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {    
    case 'SCREENLOAD_SUCCESS':
    if (action.mode === 'List_INVOIC_SCREEN') {
      return {...state,relisted:true,oneTimeValues:action.data.oneTimeValues,origin:action.data.airportCodes,mailSubClassCodes:action.data.mailSubClassCodes};
    } else {
      return {...state,relisted:true,oneTimeValues:action.data.oneTimeValues,origin:action.data.airportCodes,mailSubClassCodes:action.data.mailSubClassCodes,filterInitialValues:getInitialDateRange() };
    }
    case 'AIRPORT_FILTER_SUCCESS':
      return { ...state,displayOrigin:action.origintodisplay,displayDestination:action.displayDestination };
	  case 'SET_WARNING_VALUE':
      return {...state };
    default:
      return state;
  }
}
 
export default commonReducer;
function getInitialDateRange() {
  var toDate=(moment()).format('DD-MMM-YYYY');
  let fromDate =(moment().subtract(30, 'days')).format('DD-MMM-YYYY')
  let filterInitialValues={fromDate,toDate}
  return filterInitialValues;
}


