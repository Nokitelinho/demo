
import { SCREEN_MODE } from '../constants';

const initialState = {
};

const detailspanelreducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SCREENLOAD_SUCCESS':
            return {
                ...state
            }
        case 'GENERATE_SUCCESS':
        return {
            ...state
        }
        case 'NAVIGATION_FILTER':
        return {
            ...state
        }
        default:
            return state;
    }
}
export default detailspanelreducer;
