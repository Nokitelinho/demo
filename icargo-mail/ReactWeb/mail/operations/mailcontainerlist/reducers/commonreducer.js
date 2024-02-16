


const intialState = {
    oneTimeValues:{},
    relisted : false, 
    displayMode:'initial',
     filterIntialValues:{},
     selectedContainerIndex:[],
     
}

const commonreducer = (state = intialState, action) => {
    switch (action.type) {    
        case 'SCREENLOAD_SUCCESS':
          return {...state,filterIntialValues:{assignedTo:"ALL"}, relisted : true,oneTimeValues:action.data.oneTimeValues};
        case 'LIST_SUCCESS':
        return {...state, filterIntialValues:action.containerFilter} 
        case 'CLEAR_FILTER':
        return {...state,filterIntialValues:{assignedTo:"ALL"}};
      case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedContainerIndex:action.indexes};  
        default:
          return state;
        }
}


export default commonreducer;
