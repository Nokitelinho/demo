import { ActionType } from '../constants/constants.js'

const intialState = {
  sort : {},
  dsnFilter : {},
  selectedAwbIndex : [],
  selectedLoadPlanAwbIndex: [],
  selectedManifestAwbIndex: []
}

 const detailsReducer = (state = intialState, action) => {
  switch (action.type) {
      
    case ActionType.SAVE_FILTER :
      return {...state,dsnFilter:action.data};
      
    case ActionType.CLEAR_TABLE_FILTER :
      return {...state,dsnFilter:{}};

    case 'SAVE_SELECTED_INDEX' :
      return {...state, selectedAwbIndex:action&&action.indexes?action.indexes:[]}  

    case 'SAVE_SELECTED_LOAD_PLAN_AWB_INDEX':
      return {...state, selectedLoadPlanAwbIndex:action&&action.indexes?action.indexes:[]}  
    case 'SAVE_SELECTED_MANIFEST_AWB_INDEX':
      return { ...state, selectedManifestAwbIndex: action && action.indexes ? action.indexes : [] }

    default:
      return state;
  }
}
 
export default detailsReducer;