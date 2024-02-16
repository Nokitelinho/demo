import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {validateDeleteCustomer } from "../../../actions/commonaction";
import {
  dispatchAction
} from "icoreact/lib/ico/framework/component/common/actions";

import DeleteCustomerPopUpPanel from "../../panels/popup/DeleteCustomerPopUpPanel.jsx";

const mapStateToProps = (state) => {
  return {
    showPopUp:state.commonReducer.showPopUp,
    
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onClose: ()=>{
        dispatch({ type: "CLOSE_DELETE_POPUP" });

    },
    onYes: ()=>{
      dispatch(dispatchAction(validateDeleteCustomer)());
      dispatch({ type: "CLOSE_DELETE_POPUP" });
    }
  };
};

const DeleteCustomerPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DeleteCustomerPopUpPanel)

export default DeleteCustomerPopUpContainer;