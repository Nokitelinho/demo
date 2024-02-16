const intialState = {
    oneTimeMap: {},
    screenLoadComplete: false,
    systemParameters: null,
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
        default:
            return state;
    }
}
export default commonReducer;