const intialState ={
   containerDetails:[],
   selectContainers:[],
   screenMode:'initial',
   filterDetails:{
        },
    sort: {
        },
    tableFilter:{},
    assignedTo:'',
    summaryFilter:{},     
    pageSize:100,
    filterInitialValues:{},
    companyCode:'',   
    estimatedChargePrivilage:false   
  }



const listcontainerreducer = (state = intialState, action) => {
  switch (action.type) {
    case 'LIST_SUCCESS':
      return {...state,pageSize:action.pageSize,screenMode:'display',filterDetails:action.data,containerDetails:action.containerDetails, tableFilter:{}, sort: {}, summaryFilter:action.summaryFilter, companyCode:action.companyCode,estimatedChargePrivilage:action.estimatedChargePrivilage}   
    case 'CLEAR_FILTER':
      return intialState;
    case 'CLEAR_TABLE':
      return intialState;  
    case 'TOGGLE_FILTER':
      return {...state,screenMode: action.screenMode }
    case 'LIST_FILTER':
      return {...state,tableFilter:action.ContainerTableFilter, filterInitialValues:action.tableFilter}
    case 'CHANGESEARCH_FILTER_TABLE':
      return {...state,assignedTo:action.data.assignedTo} ;
    case 'CLEAR_TABLE_FILTER':
      return {...state,tableFilter:{},filterInitialValues:{}};
    case 'UPDATE_SORT_VARIABLE':
      return {
          ...state,
          sort: action.data
      }     
    default:
      return state
  }
}


export default listcontainerreducer;