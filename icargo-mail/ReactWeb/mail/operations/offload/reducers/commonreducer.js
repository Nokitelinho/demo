
const intialState = {
  relisted : false,  
  displayMode:'initial',
  screenMode: 'initial',
  oneTimeValues:{},
  activeOffloadTab:'CONTAINER_VIEW',
  sort:{},
  upliftAirport:'',
  fromScreen:'OFFLOAD',
  showPopOverFlag: false,
  

}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {    
    case 'SCREENLOAD_SUCCESS':
      return {
        ...state,
        relisted:true,
        oneTimeValues:action.data.oneTimeValues,
        upliftAirport: action.data.offloadFilter.upliftAirport
      };
      case 'CHANGE_OFFLOAD_TAB':
      return {
        ...state,
        activeOffloadTab: action.currentTab
      }
   case 'UPDATE_SORT_VARIABLE':
          return {
              ...state,
              sort: action.data
          }    
    case 'OFFLOAD_SUCCESS':
      return {
        ...state
          }  
case 'LIST_SUCCESS':
          return {
              ...state,
              fromScreen: action.fromScreen
          } 
       case 'LIST_SUCCESS_PAGINATION':
          return {
              ...state,
              fromScreen: action.fromScreen
          }
      case 'SHOW_OFFLOAD_FROM_MAILBAGENQUIRY':
        return {
           ...state,
              activeOffloadTab:'MAIL_VIEW' 
        }
      case 'OFFLOAD_MAILBAG_FROM_OUTBOUND':
        return {
           ...state,
              activeOffloadTab:'MAIL_VIEW' 
        }  
      case 'SHOW_OFFLOAD_FROM_LISTCONTAINER':
        return {
           ...state,
              activeOffloadTab:'CONTAINER_VIEW' 
        }   
       case 'CHANGE_OFFLOAD_OK_TAB':
      return {
        ...state,
        activeOffloadTab: action.currentTab
      }    

    case 'SHOW_POPOVER':
      return {
        ...state,
        showPopOverFlag: true
      };
    case 'CLOSE_POPOVER':
      return {
        ...state,
        showPopOverFlag: false
      };
    default:
      return state;
  }
}
 
export default commonReducer;


