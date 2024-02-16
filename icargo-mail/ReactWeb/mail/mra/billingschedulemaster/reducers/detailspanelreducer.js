
import { SCREEN_MODE } from '../constants';
const initialState = {
  showAddPopup: false,
  selectedMailRule: {},
  lastRowData: {},
  selectedBillingDataIndex: [],
  parameterExists: false,
  parameterIndex: '',
  parameterMap:new Map()
};


const detailspanelreducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SCREENLOAD_SUCCESS':
      return {
        ...state
      }
    case 'LOAD_ADD_POPUP':
      return {
        ...state,
        showAddPopup: true,
        parameterIndex: action.index

      }
    case 'CLOSE_ADD_POPUP':
      return {
        ...state,
        showAddPopup: false,
      }
    case 'PARAMETER_EXISTS':
      return {
        ...state,
        parameterExists: true,
      }
    case 'PARAMETER_OK':
      return {
        ...state,
        parameterData: action.parameter,
        parameterMap:action.parameterMap,
        showAddPopup: false

      }

    case 'LAST_ROW':
      return { ...state, lastRowData: action.curBillingDetails }
    case 'SAVE_SELECTED_INDEX':
      return { ...state, selectedBillingDataIndex: action.indexes }
    case 'CHANGE_STYLE':
      return { ...state, style: action.style }
    case 'LIST_SUCCESS':
      return { ...state, style: action.style, selectedBillingDataIndex: [] }
    case 'COUNT':
      return { ...state, selectedBillingDataIndex: action.selectedBillingDataIndex }
    default:
      return state;
  }
}
export default detailspanelreducer;
