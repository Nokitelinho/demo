import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {UpdateAdditionalDetails } from "../../../actions/commonaction";
import {
  dispatchAction
} from "icoreact/lib/ico/framework/component/common/actions";

import AdditionalDetailsPopUpPanel from "../../panels/popup/AdditionalDetailsPopUpPanel.jsx";
import {TOGGLE_POP_UP_CLOSE} from "../../../constants/constants";

const mapStateToProps = (state) => {
  return {
      showAdditionalPopUp:state.commonReducer.showAdditionalPopUp,
      customerDetails:state.commonReducer.customerDetails,
      initialValues: {
        // ...state.commonReducer.customerDetails
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    onClose: ()=>{
        dispatch({ type: TOGGLE_POP_UP_CLOSE });
    },
    onOk :args=>{
      dispatch(dispatchAction(UpdateAdditionalDetails)(args))
      dispatch({ type: TOGGLE_POP_UP_CLOSE });
    }
  };
};

const AdditionalDetailsPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(AdditionalDetailsPopUpPanel)

export default AdditionalDetailsPopUpContainer;