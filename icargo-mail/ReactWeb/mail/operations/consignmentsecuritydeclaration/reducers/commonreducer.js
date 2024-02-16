import * as constant from '../constants/constants';

const intialState = {
    screenLoadComplete: false,
    systemParameters: null,
    oneTimeValues:{}
}

const commonReducer = (state = intialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state,
                oneTimeMap: action.data.oneTimeValues,
                screenLoadComplete: true,
                systemParameters: action.data.systemParameters,
                oneTimeValues:action.data.oneTimeValues
            };

        case constant.SCREENLOAD_SUCCESS_NAVIGATION:
            return{...state,
                oneTimeMap: action.data.oneTimeValues,
                screenLoadComplete: true,
                systemParameters: action.data.systemParameters,
                oneTimeValues:action.data.oneTimeValues};    

        default:
            return state;
    }
}
export default commonReducer;