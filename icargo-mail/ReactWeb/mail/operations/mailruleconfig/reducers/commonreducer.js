const intialState = {
    oneTimeMap: {},
    screenLoadComplete: false,
    systemParameters: null,
    selectedMailRleConfigIndex: [],
}

const commonReducer = (state = intialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state,
                oneTimeMap: action.data.oneTimeValues,
                screenLoadComplete: true,
                systemParameters: action.data.systemParameters,

            };

        case 'SAVE_SELECTED_INDEX':
            return {
                ...state,
                selectedMailRleConfigIndex: action.indexes
            };

        default:
            return state;
    }
}
export default commonReducer;