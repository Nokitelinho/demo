
const intialState = {
  relisted : false,
  screenMode: 'initial',
  displayMode:'initial',
  airportCode:'',
  oneTimeValues:{},
  oneTimeType:[],
  oneTimeSubType:[],
  oneTimeCat:[],
  oneTimeMailClass:[],
  oneTimeFlightType: [],
  oneTimeRSN:[],
  oneTimeHNI:[],
  oneTimeMailServiceLevel:[],
  showPrintMailTagFlag:false,
  selectedMailBagData:[],
  mailBagsSelected:false,
  showAddConsignment:'show',
  showAddMultipleFlag:false,
  receptacles:0,
  defWeightUnit:'',
  selectedMailbagIndex:[],
  style: { maxHeight: '125px' },
  count : 0,
  oneTimeTransportStage:[]
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    
    case 'SCREENLOAD_SUCCESS':
      return {...state,relisted: true,oneTimeValues: action.data.oneTimeType,oneTimeType:action.data.oneTimeType,oneTimeSubType:action.data.oneTimeSubType,oneTimeCat:action.data.oneTimeCat,oneTimeMailServiceLevel:action.data.oneTimeMailServiceLevel,oneTimeMailClass:action.data.oneTimeMailClass,oneTimeFlightType:action.data.oneTimeFlightType,
        oneTimeRSN:action.data.oneTimeRSN, oneTimeHNI:action.data.oneTimeHNI,defWeightUnit:action.data.defWeightUnit , oneTimeTransportStage:action.data.oneTimeTransportStageQualifier};
      // return {...state,oneTimeType:action.data.oneTimeType,oneTimeSubType:action.data.oneTimeSubType,oneTimeCat:action.data.oneTimeCat}   
     case 'SHOW_PRINT_MAIL_TAG':
      return {...state,showPrintMailTagFlag:true, mailBagsSelected:action.mailBagsSelected};
     case 'SELECT_MAIL_BAG':
      return {...state,selectedMailBagData:action.mailBagDetails} ; 
    case 'TOGGLE_FILTER':
      return {...state,showAddConsignment: action.showAddConsignment };
    case 'CLOSE':
      return {...state,showPrintMailTagFlag:false, showAddMultipleFlag:false };
    case 'ADD_MULTIPLE':
      return {...state,showAddMultipleFlag:true };
    case 'RECEPTACLES':
      return {...state,receptacles:action.reseptacles };
    case 'NEW_RSN':
      return {...state,receptacles:0 };
    case 'ADD_MULTIPLE_MAILBAG':
      return {...state,showAddMultipleFlag:false,receptacles:0}
    case 'SCREENLOAD_SUCCESS_NAVIGATION':
      return {...state,relisted: true,oneTimeValues: action.data.oneTimeType,oneTimeType:action.data.oneTimeType,oneTimeSubType:action.data.oneTimeSubType,oneTimeCat:action.data.oneTimeCat,oneTimeMailServiceLevel:action.data.oneTimeMailServiceLevel,oneTimeMailClass:action.data.oneTimeMailClass,oneTimeFlightType:action.data.oneTimeFlightType,
        oneTimeRSN:action.data.oneTimeRSN, oneTimeHNI:action.data.oneTimeHNI,defWeightUnit:action.data.defWeightUnit, oneTimeTransportStage:action.data.oneTimeTransportStage};
    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailbagIndex:action.indexes}
    case 'CHANGE_STYLE' :
      return {...state, style:action.style}
    case 'LIST_SUCCESS' :
      return {...state, style:action.style, selectedMailbagIndex:[],oneTimeTransportStage:action.oneTimeTransportStage}
    case 'COUNT' :
      return {...state, selectedMailbagIndex:action.selectedMailbagsIndex}      
    default:
      return state;
  }
}
 
export default commonReducer;


