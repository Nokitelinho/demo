const intialState = {
  screenMode: 'initial',
  displayPage: null,
  pageSize: 100,
  noData: true,
  selectedFlightIndex:[]
}


const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return { ...state, screenMode: action.screenMode }
    case 'SAVE_SELECTED_INDEX':
      return { ...state, selectedFlightIndex: action.indexes }
    case 'RETAIN_VALUES':
      return { ...state, }
    default:
      return state
  }
}


export default filterReducer;