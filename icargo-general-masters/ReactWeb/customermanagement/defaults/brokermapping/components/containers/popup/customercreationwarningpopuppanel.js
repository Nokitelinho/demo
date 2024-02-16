import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { change,focus } from "icoreact/lib/ico/framework/component/common/form";

import CustomerCreationWarningPopUpPanel from "../../panels/popup/CustomerCreationWarningPopUpPanel.jsx";
import {TOOGLE_SCREEN_MODE} from "../../../constants/constants";

const mapStateToProps = (state) => {
  return {
    showCreateCustomerPopUp:state.commonReducer.showCreateCustomerPopUp,
    invalidCus:state.commonReducer.invalidCus,
    initialValues: {
        
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    onClose: ()=>{
        dispatch({ type:"CLOSE_CUSTOMER_POPUP"});
        dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
        dispatch(change('brokermappingform','customerCode',''));
        dispatch({type:"CLEAR", data: "initial"});
        dispatch(focus('brokermappingform','customerCode'))
    },
    onYes: ()=>{
      dispatch({ type:"CLOSE_CUSTOMER_POPUP"});
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
      dispatch(change('brokermappingform','customerCode',''));
      dispatch({type:"CLEAR", data: "initial"}),
      dispatch({type:"disableCustomerFields",data:{disableFields: false}}),
      dispatch({type:TOOGLE_SCREEN_MODE,data:{screenMode: "newCustomer",showActionButtons:false}})
    }
  };
};

const CustomerCreationWarningPopUpConatiner = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(CustomerCreationWarningPopUpPanel)

export default CustomerCreationWarningPopUpConatiner;