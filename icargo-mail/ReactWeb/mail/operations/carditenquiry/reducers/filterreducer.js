const intialState ={
   screenMode:'initial',
   displayPage:'',
   pageSize:100,
   mailbagsdetails:[],
   totalWeight:'',
   totalPieces:'',
   filterValues :{flightType:'C'
   },
   selectedMailbagIndex:[],
   selectedMailbagIds:[],
   selectedResditIndex:[],
   noData:true,
   displayPage:'',
   tableFilter:{},
   sort: {
   },
    summaryFilter:{},
    selectedResditMailbagData:{}
}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return {...state,screenMode: action.screenMode }
    case 'LIST_SUCCESS':
      return {...state,mailbagsdetails:action.mailbags, totalWeight:action.totalWeight?action.totalWeight:'', totalPieces:action.totalPieces ?action.totalPieces:'' ,displayPage:action.displayPage,pageSize:action.pageSize,screenMode:action.displayPage === '1'? 'display':action.screenMode,filterValues:action.data.carditFilter,displayPage:action.data.carditFilter.displayPage,tableFilter:{},noData:false,summaryFilter:action.summaryFilter}   
      case 'LIST_SUCCESS_PAGINATION':
    return {...state,mailbagsdetails:action.mailbags,displayPage:action.displayPage,pageSize:action.pageSize,screenMode:action.displayPage === '1'? 'display':action.screenMode,filterValues:action.data.carditFilter,displayPage:action.data.carditFilter.displayPage,tableFilter:{},noData:false,summaryFilter:action.summaryFilter}   
    case 'NO_DATA':
      return {...state,screenMode:'display',mailbagsdetails:[],noData:true,filterValues:action.data.carditFilter,pageSize:action.data.pageSize,displayPage:action.data.carditFilter.displayPage,tableFilter:{}}
      case 'LIST_FILTER':
      return {...state,tableFilter:action.carditTableFilter} 
    case 'CLEAR_FILTER':
      return intialState;
    case 'CLEAR_TABLE_FILTER':
      return {...state,tableFilter:{}};
    case 'CLEAR_TABLE':
      return intialState;   
    case 'UPDATE_SORT_VARIABLE':
      return {
          ...state,
          sort: action.data
      }   
    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedMailbagIndex:action.indexes,selectedMailbagIds:action.mailbagId}
      case 'SAVE_SELECTED_RESDIT_MAILBAG_DATA':
      return {...state,  mailbagsdetails:action.mailDetails}
    case 'RETAIN_VALUES':
      return {...state,}
    default:
      return state
  }
}


export default filterReducer;