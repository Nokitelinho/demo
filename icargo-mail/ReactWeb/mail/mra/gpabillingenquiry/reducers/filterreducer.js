import { CLEAR_FILTER, LIST_SUCCESS,RETAIN_VALUES,NO_DATA,UPDATE_BILLING,SAVE_SELECTED_INDEXS,LIST_BLGSUCCESS, LIST_CONSSUCCESS,UPDATE_INDEX} from '../constants/constants';
import { RERATE_MAILS, SURCHARGE_DETAILS,SAVE_SELECTED_INDEX,TOGGLE_FILTER,CHANGE_DETAILS,CLOSE_BILLING_POPUP} from '../constants/constants';
import {  CLOSE_POPOVER,CLOSE_SURCHARGE_POPUP,CLOSE_STATUS_POPUP,MAIL_ICON,CHANGE_STATUS_SAVE,UPDATE_SORT_VARIABLE,SHOW_POPOVER,CLEAR_INDEXES } from '../constants/constants';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
let currentDate = getCurrentDate();



const intialState ={
  displayPage:'',
  filterValues :{
    port:'',
    fromDate:currentDate,
    toDate:currentDate
  },
  noData:true,
  tableFilter:{},
  oneTimeValuesStatus:{},
  screenMode:'initial',
  displayMode:'initial',
  changeStatus:'no',
  screenFilterMode:'edit',
  mailbagsdetails:[],
  mailbagsdetailslist:[],
  consignmentdetails:[],
  surchargeDetails:[],
  selectedMailbagIndex:[],
  selectedConsignmentIndex:[],
  showChangeStatusPopup: false,
  showSurchargePopup: false,
  showMailbagIcon:false,
  dsn:[],
  pageSize:100,
  sort:{},
  billingStatus:'',
  remarks:'',
  summaryFilter:{},
  showPopOverFlag:false,
  totalRecordCount:null,
  selectedConsignments:[],
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case TOGGLE_FILTER:
      return {...state,screenFilterMode: action.screenFilterMode }
    case CLEAR_FILTER:
      return intialState;  
    case LIST_SUCCESS:
      return {...state,pageSize:action.data.gpaBillingEntryFilter.defaultPageSize,mailbagsdetails:action.gpaBillingEntryDetails,mailbagsdetailslist:action.gpaBillingEntryDetails,displayMode:'list',screenFilterMode:'display',screenMode:'display',noData:false,filterValues:action.data.gpaBillingEntryFilter,displayPage:action.data.gpaBillingEntryFilter.displayPage,tableFilter:{},showChangeStatusPopup: false,showMailbagIcon:false,summaryFilter:action.summaryFilter,totalRecordCount:action.gpaBillingEntryDetails.totalRecordCount,selectedConsignments:[]}; 	
      //return {...state,pageSize:action.gpaBillingEntryDetails&&action.gpaBillingEntryDetails.defaultPageSize,mailbagsdetails:action.gpaBillingEntryDetails,mailbagsdetailslist:action.gpaBillingEntryDetails,consignmentdetails:action.consignmentDetails,displayMode:'list',screenFilterMode:'display',screenMode:'display',noData:false,filterValues:action.data.gpaBillingEntryFilter,displayPage:action.data.gpaBillingEntryFilter.displayPage,tableFilter:{},showChangeStatusPopup: false,showMailbagIcon:false,summaryFilter:action.summaryFilter}; 	
      //return {...state,pageSize:action.gpaBillingEntryDetails&&action.gpaBillingEntryDetails.defaultPageSize,mailbagsdetails:action.gpaBillingEntryDetails,mailbagsdetailslist:action.gpaBillingEntryDetails,displayMode:'list',screenFilterMode:'display',screenMode:'display',noData:false,filterValues:action.data.gpaBillingEntryFilter,displayPage:action.data.gpaBillingEntryFilter.displayPage,tableFilter:{},showChangeStatusPopup: false,showMailbagIcon:false,summaryFilter:action.summaryFilter}; 	
    case NO_DATA:
      return {...state,displayMode:'display',noData:true,filterValues:action.data.gpaBillingEntryFilter,tableFilter:{}};
    case RETAIN_VALUES:
      return {...state};
    case RERATE_MAILS:
      return {...state, selectedMailbagIndex:action.indexes};     
    case SAVE_SELECTED_INDEX:
      return {...state, selectedMailbagIndex:action.indexes};
    case SAVE_SELECTED_INDEXS: 
      return {...state, selectedConsignmentIndex:action.indexes};
    case SURCHARGE_DETAILS:
      return {...state,showSurchargePopup:true,surchargeDetails:action.data.surchargeDetails};
    case CHANGE_DETAILS:
      return {...state,showChangeStatusPopup:true,oneTimeValuesStatus:action.data.oneTimeValuesStatus,dsn:action.data.dsn,billingStatus:action.data.billingStatus,remarks:action.data.remarks};      
    case UPDATE_BILLING:
      return {...state,mailbagsdetails:action.data.gpaBillingEntryDetails};
    case CLOSE_SURCHARGE_POPUP:
      return {...state,showSurchargePopup: false};
    case CLOSE_STATUS_POPUP:
      return {...state,showChangeStatusPopup: false};   
    case CHANGE_STATUS_SAVE:
      return {...state,showChangeStatusPopup: false,changeStatus:'yes'}; 
    case LIST_BLGSUCCESS : 
    return {...state,pageSize:action.pageSize,mailbagsdetailslist:action.gpaBillingEntryDetails,totalRecordCount:action.gpaBillingEntryDetails.totalRecordCount};    
      case LIST_CONSSUCCESS :
    return {...state,consignmentdetails:action.consignmentDetails}; 
    case MAIL_ICON:
      return {...state,showMailbagIcon:true }; 
    case SHOW_POPOVER:
        return {...state,showPopOverFlag:true} ;
    case CLOSE_POPOVER:
        return {...state,showPopOverFlag:false} ;    
    case UPDATE_SORT_VARIABLE:
      return {...state, sort: action.data}   ;
    case CLEAR_INDEXES :
    return {...state,selectedMailbagIndex:[]}  ;   
	case UPDATE_INDEX:
    return {...state,selectedConsignments:action.data}  ;  	
    default:
      return state;
  }
}


export default filterReducer;