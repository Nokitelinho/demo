
import { SCREEN_MODE } from '../constants';

const initialState = {
    screenMode: SCREEN_MODE.INITIAL,
    screenFilter: {},
    mailRuleConfigList:[],
    newMailRuleList:[]
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
                screenMode: SCREEN_MODE.DISPLAY,
                screenFilter: action.data.mailRuleConfigFilter,
                mailRuleConfigList:action.listValues,

            };

            case 'ADD_NEW_MAILRULE':
            return {
                ...state,
                
                mailRuleConfigList:action.listValues,
                screenMode: action.screenMode,
                newMailRuleList:action.newMailRuleList

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
        default:
            return state;
    }
}

export default filterpanelreducer;