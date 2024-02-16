const intialState = {
  displayPage: '',
  screenMode: 'initial',
  filterValues: {
  },
  noData: true,
  tableFilter: {},
  mailbagsdetails: [],
  containerdetails: [],
  selectedMailbagIndex: [],
  noData:true,
  defaultPageSize: 10,
  containers:null,
  mailbags: null,
  summaryFilter: {},

}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return { ...state, screenMode: action.screenMode }
    case 'CLEAR_FILTER':
      return intialState;
      case 'LIST_SUCCESS':
      return {
        ...state,
        containerdetails: action.offloadDetailsPageResult,
         displayPage:action.displayPage,
        defaultPageSize: action.defaultPageSize,
         screenMode:action.displayPage === '1'? 'display':action.screenMode,
         filterValues:action.data.offloadFilter,
         displayPage:action.data.offloadFilter.displayPage,
         tableFilter:{},
        noData: false,
        summaryFilter: action.summaryFilter

        }
        case 'LIST_SUCCESS_PAGINATION':
      return {
        ...state,
        containerdetails: action.offloadDetailsPageResult,
          displayPage:action.displayPage,screenMode:action.displayPage === '1'? 'display':action.screenMode,
          filterValues:action.data.offloadFilter,
          displayPage:action.data.offloadFilter.displayPage,
          tableFilter:{},
        noData: false,
        summaryFilter: action.summaryFilter
        }  
        case 'NO_DATA':
      return {
        ...state,
          screenMode:'display',
        containerdetails: [],
          noData:true,
          filterValues:action.data.offloadFilter,
          displayPage:action.data.offloadFilter.displayPage,
          tableFilter:{}
        } 
        case 'CLEAR_TABLE_FILTER':
      return {
        ...state,
        tableFilter: {}
      }
        case 'LIST_FILTER':
      return {
        ...state,
        tableFilter: action.ContainerTableFilter
      }
    case 'LIST_MAILFILTER':
      return {
        ...state,
        tableFilter: action.MailTableFilter
      }
case 'SHOW_OFFLOAD_FROM_LISTCONTAINER':
      let containers=null;
      if(action.data.containerNumber&&action.data.containerNumber.split(',').length>1){
          containers=action.data.containerNumber.split(',');
      }
       return {
        ...state, filterValues: {
          'containerNo': containers==null?action.data.containerNumber:null,
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber,
          'flightDate': action.data.flightDate,
          flightnumber:{
              'carrierCode': action.data.carrierCode,
              'flightNumber': action.data.flightNumber,
              'flightDate': action.data.flightDate,
          }
        },containers:containers,containerType:'U'}
       case 'SHOW_OFFLOAD_FROM_MAILBAGENQUIRY':
        let mailbags=null;
        if(action.data.mailbags&&action.data.mailbags.split(',').length>1){
          mailbags=action.data.mailbags.split(',');
        }
        return {
        ...state, filterValues: {
          'mailbagId': mailbags==null?action.data.mailbags:null,
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber,
          'flightDate': action.data.flightDate,
          flightnumber:{
              'carrierCode': action.data.carrierCode,
              'flightNumber': action.data.flightNumber,
              'flightDate': action.data.flightDate,
          }
        },mailbags:mailbags}
        case 'OFFLOAD_MAILBAG_FROM_OUTBOUND':
        mailbags=null;
        if(action.data.mailbags&&action.data.mailbags.split(',').length>1){
          mailbags=action.data.mailbags.split(',');
        }
        return {
        ...state, filterValues: {
          'mailbagId': mailbags==null?action.data.mailbags:null,
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber,
          'containerNo': action.data.containerNumber,
          'flightDate': action.data.flightDate,
          flightnumber:{
              'carrierCode': action.data.carrierCode,
              'flightNumber': action.data.flightNumber,
              'flightDate': action.data.flightDate,
          }
        },mailbags:mailbags}
        case 'OFFLOAD_CONTAINER_FROM_OUTBOUND':
        let containerLists=null;
        if(action.data.containerNumber&&action.data.containerNumber.split(',').length>1){
          containerLists=action.data.containerNumber.split(',');
        }
        return {
        ...state, filterValues: {
          'containerNo': containerLists==null?action.data.containerNumber:null,
          'carrierCode': action.data.carrierCode,
          'flightNumber': action.data.flightNumber,
          'flightDate': action.data.flightDate,
          flightnumber:{
              'carrierCode': action.data.carrierCode,
              'flightNumber': action.data.flightNumber,
              'flightDate': action.data.flightDate,
          }
        },containers:containerLists,containerType:'U'}
    case 'LIST_DSNFILTER':
      return {
        ...state,
        tableFilter: action.DSNTableFilter
      } 
    case 'SAVE_SELECTED_INDEX':
      return {
        ...state,
        selectedMailbagIndex: action.indexes
      }

      case 'CHANGE_OFFLOAD_OK_TAB':
      return intialState;

      case 'RETAIN_VALUES':
          return {...state,
            screenMode:'initial', 
            filterValues:action.data.offloadFilter,
            noData:true};  		
            case 'CLEAR_TABLE':
            return {
              ...state,
              containerdetails: [],
              noData: true      
              }











    default:
      return state;
  }
}


export default filterReducer;