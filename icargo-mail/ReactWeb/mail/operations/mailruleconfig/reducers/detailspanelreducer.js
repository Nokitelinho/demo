
import { SCREEN_MODE } from '../constants';

const initialState = {
    showAddPopup: false,
    selectedMailRule:{}
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
                selectedMailRule:{}
            }

        case 'CLOSE_ADD_POPUP':
            return {
                ...state,
                showAddPopup: false,
            }
        case 'LOAD_MODIFY_POPUP':
        return {
            ...state,
            showAddPopup: true,
            selectedMailRule:action.selectedMailRule
        }    
        default:
            return state;
    }
}
export default detailspanelreducer;
