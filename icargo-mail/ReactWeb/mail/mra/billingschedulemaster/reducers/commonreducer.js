const intialState = {
  oneTimeMap: {},
  screenLoadComplete: false,
  systemParameters: null,
  selectedBilllingData:[],
  selectedBillingDataIndex: []
}

const commonReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'SCREENLOAD_SUCCESS':
      return {
        ...state,
        oneTimeValues: action.data.oneTimeValues,
        screenLoadComplete: true,
        systemParameters: action.data.systemParameters,

      };
    case 'SAVE_SELECTED_INDEX':
      return { ...state, selectedBillingDataIndex: action.indexes };
      case 'SAVE_SELECTED_MULTIPLE_INDEX':
      return { ...state, selectedBillingDataIndex: action.indexes };
      case 'SELECT_BILL_DETAILS':
      return {...state,selectedBilllingData:action.billDetails} ; 
    default:
      return state;
  }
}
export default commonReducer;