const initialState ={
    relisted:false,
    airportCode:'',
   mailTransitDetails:[],
    pageSize:20,
    sort:{},
    navigationFilter:{},
    tableFilter:{},
    
    
}
const commonReducer=(state=initialState,action)=>{
    switch (action.type){

        case 'SCREENLOAD_SUCCESS':
            return {...state,airportCode:action.data.mailTransitFilter.airportCode,relisted: true};//dbt
              
            // case 'LIST_SUCCESS':
            //      return {...state,mailTransitDetails:action.mailTransits};
             

            //     case 'CLEAR_FILTER':
            //         return {...state,relisted:true,mailTransitDetails:[]};  

            case 'UPDATE_SORT_VARIABLE':
             return {...state, sort: action.data};
            //  case 'CLEAR_TABLE_FILTER':
            //     return {...state,tableFilter:{}};
            //   case 'CLEAR_FILTER':
            //       return {...state,relisted:true,mailTransitDetails:[]}; 
            case 'LIST_FILTER':
                return {...state,tableFilter:action.mailTransitTableFilter}; 
              case 'CLEAR_TABLE_FILTER':
                return {...state,tableFilter:{}};
                    default:
                        return state
                
    }
}
export default commonReducer;