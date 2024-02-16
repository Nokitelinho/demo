import { SURCHARGE_DETAILS,OPEN_COMPAREROW_POPUP,CLOSE_BILLING_POPUP } from '../constants/constants';

const intialState = {
    showChangeStatusPopup: false,
    showSurchargePopup: false,
    surchargeDetails:[],
    comparerow:false
   }
const screenPopupReducer = (state = intialState, action) => {
    switch (action.type) {
      
      case OPEN_COMPAREROW_POPUP:
      return {...state,comparerow:true };
      case CLOSE_BILLING_POPUP:
      return {...state,comparerow: false};     

                   
        default:
            return state
    }
}
export default screenPopupReducer;