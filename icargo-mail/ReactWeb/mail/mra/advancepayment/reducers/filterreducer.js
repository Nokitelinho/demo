import { ActionType} from '../constants/constants';





const intialState ={
  relisted : false,  
  oneTimeValues:{},
  displayMode:'initial',
  screenMode:'initial',
  screenFilterMode:'edit',
  maxFetchCount:null,
  pageSize:100,
  displayPage:'',
  totalRecordCount:null,
  summaryFliter:{},
  filterValues: {},
  paymentdetails:{},
  showAddPaymentPopup:false,
  fromEditBatch:'N',
  paymentbatchdetail:{},
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case ActionType.TOGGLE_FILTER:
      return {...state,screenFilterMode: action.screenFilterMode }
    case ActionType.CLEAR_FILTER :
        return intialState;  
    case ActionType.LIST_SUCCESS:
      return {...state,pageSize:action.data.paymentBatchFilter.defaultPageSize,paymentdetails:action.data.paymentBatchDetails,displayMode:'list',screenFilterMode:'display',filterValues:action.data.paymentBatchFilter,displayPage:action.data.paymentBatchFilter.displayPage,totalRecordCount:action.data.paymentBatchDetails.totalRecordCount}; 	
    case ActionType.ADD_PAYMENT:
      return {...state,showAddPaymentPopup:true}
    case ActionType.ADD_PAY_CLS:
      return {...state,showAddPaymentPopup:false}
    case ActionType.CREATE_SUCCESS:
        return {...state,showAddPaymentPopup:false}      
    case ActionType.DEL_BATCH:
          return {...state,filterValues:action.data.paymentBatchFilter}
    case ActionType.DEL_ATCH:
        return {...state,filterValues:action.data.paymentBatchFilter}        
    case ActionType.EDT_BTH:
        return {...state,showAddPaymentPopup:true,fromEditBatch:'Y',paymentbatchdetail:action.data.paymentBatchDetail,filterValues:action.data.paymentBatchFilter}       
    case ActionType.OK_SUCCESS:
        return {...state,showAddPaymentPopup:false,fromEditBatch:'N'}   
    default:
      return state;
  }
}


export default filterReducer;