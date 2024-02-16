import {
    LIST_DETAILS,
    CLEAR_FILTER,
    OPEN_CUSTOMERPOPUP,
    CLOSE_CUSTOMERPOPUP,
    UPDATE_CUSTOMER_SEARCH_DETAILS,
    TOOGLE_SCREEN_MODE,
    UPDATE_BROKER_MAPPING_DETAILS,
    TOGGLE_BROKER_POP_UP,
    TOGGLE_POP_UP_CLOSE,
    TOGGLE_ADDITIONAL_POP_UP,
    SAVE_SELECTED_INDEX,
    ADD_NEW_BROKER_POA,
    TOGGLE_CONSIGNEE_POP_UP,
    SAVE_SELECTED_CONSIGNEE_INDEX,
    UPDATE_BROKER_DETAILS,
    UPDATE_CONSIGNEE_DETAILS,
    APPLY_BROKER_FILTERS,
    UPDATE_SORT_VARIABLE,
    CLEAR_SORT_VARIABLE,
    DISPLAY_SELECTED_INDEX,
    DISPLAY_SELECTED_CUSTOMER,
    APPLY_CONSIGNEE_FILTERS,
    UPDATE_CONSIGNEE_SORT_VARIABLE,
    CLEAR_CONSIGNEE_SORT_VARIABLE,
    CLEAR_CONSIGNEE_FILTER
  } from "../constants/constants";
const initialState = {
    screenMode: 'initial',
    showCustomerLovPopupFlag:false,
    customerListDetails:[],
    oneTimeValues: [],
    disableCustomerFields:true,
    showSavedAlert:false,
    showBroker:false,
    showConsigneePopUp:false,
    showCreateCustomerPopUp:false,
    showDeletePopUp:false,
    showRelist:false,
    showActionButtons:true,
    showAdditionalPopUp:false,
    filterDetails:{},
    customerDetails:{},
    brokerDetails:[],
    consigneeDetails:[],
    saveConsigneeDetails:[],
    selectedIndex:[],
    selectedConsigneeIndex:[],
    intialBrokerDetails:[],
    initialConsigneeDetails:[],
	selectedCustomerIndex:[],
    selectedCustomerCode:[],
     brokerFilter:{},
    sort: {},
    customerCodeValue:'',
    consigneeFilter:{},
    sortConsignee:{},
    showSinglePoa:false,
    awbDetails:[],
    initialAwbDetails:[],
    search:"",
    brokerCodeValue:'',
    brokerNameValue:'',
    consigneeCodeValue:'',
    consigneeNameValue:'',
    station:'',
    selectedSinglePoaIndex:[],
    invalidCus:"",
    showAddNamPopUp:false,
    addNamSearch:"",
    initialAdditionalNames:[],
    additionalNames:[],
    selectedAddNamIndex:[],
    selectedCustomertype:"",
    showDeleteRemarks:false,
    showRemarksForConsignee:false,
    showRemarksForSinglePoa:false,
    showViewHistory:false,
    ViewHistoryDetails:[],
    additionalDetails:[],
    showLoader:false
}

const commonReducer = (state = initialState, action) => {
    switch(action.type) {  
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state,
                oneTimeValues: action.oneTimeValues,
                station:action.data.station
            };
        case OPEN_CUSTOMERPOPUP:
                return {
                  ...state,
                  showCustomerLovPopupFlag: true,
                  customerListDetails:[]
                };
        case CLOSE_CUSTOMERPOPUP:
              return {
                ...state,
                showCustomerLovPopupFlag: false
              }; 
        case UPDATE_CUSTOMER_SEARCH_DETAILS:
            return{
                ...state,
                customerListDetails: action.data
            };
        case CLEAR_FILTER:
            return{
                ...state,
                customerListDetails:[],
                screenMode: "initial"
            }
        case TOOGLE_SCREEN_MODE:
            return {
                ...state,
                screenMode: action.data.screenMode,
                filterDetails:action.data.filterDetails,
                showActionButtons:action.data.showActionButtons

            };
        case "TOOGLE_DELETE_POP_UP":
            return{...state,showDeletePopUp:true}
        case "CLOSE_DELETE_POPUP":
            return{...state,showDeletePopUp:false}
        case "SHOW_CUSTOMER_POPUP":
            return{...state,showCreateCustomerPopUp: action.data.showCreateCustomerPopUp,invalidCus:action.data.invalidCus}
        case "CLOSE_CUSTOMER_POPUP":
            return{...state,showCreateCustomerPopUp:false}
        case "SHOW_RELIST_POPUP":
            return{...state,showRelist:action.data.showRelist}
        case "CLOSE_RELIST_POPUP":
                return{...state,showRelist:false}
        
        case UPDATE_BROKER_MAPPING_DETAILS:
            let customerType=action.data.customerDetails.customerType
            let disableCustomerFields=true
            if(customerType!==null && customerType==="TMP")
            {
                disableCustomerFields=false
            }
            return{
                ...state,
                customerDetails:{...action.data.customerDetails,
                    adittionalName:action.data.customerDetails.additionalNames.length>0?action.data.customerDetails.additionalNames[0].adlNam:""},
                disableCustomerFields: disableCustomerFields,
                brokerDetails: action.data.brokerDetails,
                consigneeDetails: action.data.consigneeDetails,
                intialBrokerDetails: action.data.intialBrokerDetails,
                initialConsigneeDetails: action.data.initialConsigneeDetails,
                awbDetails: action.data.awbDetails,
                initialAwbDetails:action.data.initialAwbDetails,
                showActionButtons: action.data.showActionButtons,
                initialAdditionalNames:action.data.customerDetails.additionalNames,
                additionalNames:action.data.customerDetails.additionalNames,
                selectedCustomertype:customerType
            }
        case UPDATE_BROKER_DETAILS:
            return{
                ...state,
                brokerDetails:action.data.updatedBrokerDetails
            }
        case UPDATE_CONSIGNEE_DETAILS:
            return{
                ...state,
                consigneeDetails:action.data.updateConsigneeDetails
            }
        case ADD_NEW_BROKER_POA:
            let brokerDetails=[...state.brokerDetails];
            let intialBrokerDetails=[...state.intialBrokerDetails];
            brokerDetails.push(action.data.brokerDetails)
            intialBrokerDetails.push(action.data.intialBrokerDetails)
            return{
                ...state,
                brokerDetails: brokerDetails,
                intialBrokerDetails: intialBrokerDetails 
            }
        case TOGGLE_BROKER_POP_UP:
            return{...state,showBroker:true}
        case TOGGLE_CONSIGNEE_POP_UP:
            return{...state,showConsigneePopUp:true}
        case TOGGLE_POP_UP_CLOSE:
            return{
                ...state,
                showAdditionalPopUp:false,
                showConsigneePopUp:false,
                showBroker:false
            }

        case TOGGLE_ADDITIONAL_POP_UP:
            return{...state,showAdditionalPopUp:true}
        case SAVE_SELECTED_INDEX: 
            return {
                ...state,
                selectedIndex: action.data
            }
        case SAVE_SELECTED_CONSIGNEE_INDEX:
            return{
                ...state,
                selectedConsigneeIndex:action.data
            }
        case "CLEAR":
            return{
                ...state,
                screenMode: action.data,
                disableCustomerFields:true,
                showCustomerLovPopupFlag:false,
                showSavedAlert:false,
                customerListFilter:[],
                showBroker:false,
                showConsigneePopUp:false,
                showCreateCustomerPopUp:false,
                showDeletePopUp:false,
                showRelist:false,
                showActionButtons:true,
                showAdditionalPopUp:false,
                filterDetails:{},
                customerDetails:{},
                brokerDetails:[],
                consigneeDetails:[],
                saveConsigneeDetails:[],
                selectedIndex:[],
                selectedConsigneeIndex:[],
                intialBrokerDetails:[],
                initialConsigneeDetails:[],
                selectedCustomerIndex:[],
                selectedCustomerCode:[],
                brokerFilter:{},
                sort: {},
                customerCodeValue:'',
                consigneeFilter:{},
                sortConsignee:{},
                showSinglePoa:false,
                awbDetails:[],
                initialAwbDetails:[],
                search:"",
                brokerCodeValue:'',
                brokerNameValue:'',
                consigneeCodeValue:'',
                consigneeNameValue:'',
                selectedSinglePoaIndex:[],
                invalidCus:"",
                showAddNamPopUp:false,
                addNamSearch:"",
                initialAdditionalNames:[],
                additionalNames:[],
                selectedAddNamIndex:[],
                selectedCustomertype:"",
                showDeleteRemarks:false,
                showRemarksForConsignee:false,
                showRemarksForSinglePoa:false,
                showViewHistory:false,
                ViewHistoryDetails:[],
                additionalDetails:[],
                showLoader:false

            }
        case "disableCustomerFields":
            return{
                ...state,
                disableCustomerFields:action.data.disableFields
            }
            case DISPLAY_SELECTED_INDEX: 
            return {
                ...state,
                selectedCustomerIndex: action.data
            }
            case DISPLAY_SELECTED_CUSTOMER:
                return {
                    ...state,
                    customerCodeValue: action.data,
                    showCustomerLovPopupFlag:false
                }  
         case APPLY_BROKER_FILTERS:
            return{...state,brokerFilter:action.data}
        case UPDATE_SORT_VARIABLE:
            return {
                ...state,
                sort: action.data
            } 
        case CLEAR_SORT_VARIABLE:
            return {
                ...state,
                sort: {}
            }
        case "CLEAR_BROKER_FILTER":
            return{
                ...state,
                brokerFilter:{}
            }
        case APPLY_CONSIGNEE_FILTERS:
            return{...state,consigneeFilter:action.data}
        case CLEAR_CONSIGNEE_FILTER:
            return{...state,consigneeFilter:{}}
        case UPDATE_CONSIGNEE_SORT_VARIABLE:
            return {...state,sortConsignee: action.data}
        case CLEAR_CONSIGNEE_SORT_VARIABLE:
            return {...state,sortConsignee: {}}
        case "SHOW_SINGLEPOA_POPUP":
            return{...state,showSinglePoa:true}
        case "CLOSE_SINGLEPOA_POPUP":
            return{...state,showSinglePoa:false}
        case "ADD_NEW_CONSIGNEE_POA":
            let consigneeDetails=[...state.consigneeDetails];
            let initialConsigneeDetails=[...state.initialConsigneeDetails];
            consigneeDetails.push(action.data.consigneeDetails)
            initialConsigneeDetails.push(action.data.initialConsigneeDetails)
            return{
                ...state,
                consigneeDetails: consigneeDetails,
                initialConsigneeDetails: initialConsigneeDetails 
            }
        case "ADD_SINGLE_POA":
            let awbDetails=[...state.awbDetails];
            let initialAwbDetails=[...state.initialAwbDetails];
            awbDetails.push(action.data.newSinglePOA)
            initialAwbDetails.push(action.data.newSinglePOA)
            return{
                ...state,
                awbDetails: awbDetails,
                initialAwbDetails: initialAwbDetails 
            }
        case "UPDATE_SEARCH":
            return{
                ...state,
                search:action.data
            }
        case "DISPLAY_SELECTED_CUSTOMERDETAILS":
            return{
                ...state,
                brokerCodeValue: action.data.brokerCode,
                brokerNameValue:action.data.brokerName,
                consigneeCodeValue:action.data.consigneeCode,
                consigneeNameValue:action.data.consigneeName,
                showCustomerLovPopupFlag:false
            }
        case "SAVE_SELECTED_SINGLEPOA_INDEX":
            return{
                ...state,
                selectedSinglePoaIndex:action.data
            }
        case "UPDATE_SINGLE_POA_DETAILS":
            return{
                ...state,
                awbDetails:action.data.updateSinglePoaDetails
            }
        case "CLEAR_BROKER/CONSIGNEE_FORM_VALUES":
            return{...state,
                brokerCodeValue: "",
                brokerNameValue:"",
                consigneeCodeValue:"",
                consigneeNameValue:"",
            }
        case "SHOW_ADDITIONAL_NAME_POPUP":
            return{...state,showAddNamPopUp:true}
        case "CLOSE_ADDITIONAL_NAME_POPUP":
            return{...state,showAddNamPopUp:false}
        case "UPDATE_ADDNAM_SEARCH":
            return{
                ...state,
                addNamSearch:action.data
            }
        case "SAVE_SELECTED_ADLNAM_INDEX":
            return{...state,selectedAddNamIndex:action.data}
        case "UPDATE_ADDNAME_DETAILS":
            return{
                ...state,additionalNames: action.data.updateAdditionalDetails
            }
        case "ADD_ADDITIONAL_NAMES":
            let additionalNames=[...state.additionalNames];
            additionalNames.push(action.data);
            return{...state,additionalNames:additionalNames}
        case "UPDATE_CUS_STATE":
            return{...state,customerDetails:{...state.customerDetails,station:action.data.station,country:action.data.country}};
        case "_ON_CLEAR_BROKER_CONSIGNEE_CODE":
		  return {
			...state,
			brokerCodeValue: "",
			brokerNameValue: "",
			consigneeCodeValue: "",
			consigneeNameValue: "",
		  };
        case "CLOSE_DELETE_REMARK_POPUP":
            return{...state,showDeleteRemarks:false}
        case "OPEN_DELETE_REMARK_POPUP":
            return{...state,showDeleteRemarks:true}
        case "OPEN_REMARK_POPUP_FOR_CONSIGNEE":
            return{...state,showRemarksForConsignee:true}
        case "CLOSE_REMARK_POPUP_FOR_CONSIGNEE":
            return{...state,showRemarksForConsignee:false}
        case "OPEN_DELETE_REMARK_SINGLEPOA":
            return{...state,showRemarksForSinglePoa:true}
        case "CLOSE_DELETE_REMARK_SINGLEPOA":
            return{...state,showRemarksForSinglePoa:false}
        case "OPEN_VIEWHISTORY_POPUP":
            return{...state,showViewHistory:true}
        case "CLOSE_VIEWHISTORY_POPUP":
            return{...state,showViewHistory:false}
        case "ADD_VIEWHISTORY_DETAILS":
            return{...state,ViewHistoryDetails:action.data.viewHistoryDetails}
        case "FETCH_ADDITIONAL_DETAILS":
            return{...state,additionalDetails:[...state.additionalDetails,action.data.additionalDetails]}
        case "SHOW_LOADER":
            return{...state,showLoader:!state.showLoader}
		default:
            return state;
    }
}

export default commonReducer;

