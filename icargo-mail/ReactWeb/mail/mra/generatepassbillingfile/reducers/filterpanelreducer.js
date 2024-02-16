
import { SCREEN_MODE } from '../constants';

const initialState = {
    screenMode: SCREEN_MODE.INITIAL,
    screenFilter: {},
    isValidPeriodNumber:false,
    isValidBillingPeriod:false
};

const filterpanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state
            };

        case 'LIST_SUCCESS':
            const formData = action.filterData;
            return {
                ...state,
                screenMode: SCREEN_MODE.DISPLAY

            };

        case 'TOGGLE_SCREEN_MODE':
            return {
                ...state,
                screenMode: action.data
            };

        case 'CLEAR_FILTER':
            return {
                ...state,
                screenMode: SCREEN_MODE.INITIAL,
                screenFilter: {}
            };
        case 'POPULATE_PERIOD':
                return {
                    ...state,
                    screenMode: SCREEN_MODE.DISPLAY,
                    isValidPeriodNumber:action.data.validPeriodNumber,
                    isValidBillingPeriod:action.data.validBillingPeriod
                   
        };
        default:
            return state;
    }
}

export default filterpanelreducer;