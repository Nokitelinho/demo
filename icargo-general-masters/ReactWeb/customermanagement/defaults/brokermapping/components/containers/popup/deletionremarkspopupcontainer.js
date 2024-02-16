import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import DeletionRemarksPopUpPanel from "../../panels/popup/DeletionRemarksPopUpPanel.jsx";
import { updateRemarksForDeletedPOA } from "../../../actions/commonaction";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util.js";
import { requestValidationError } from "icoreact/lib/ico/framework/component/common/store/commonactions.js";

const mapStateToProps = (state) => {
  return {};
};
const mapDispatchToProps = (dispatch) => {
  return {
    onClose: () => {
      dispatch({ type: "CLOSE_DELETE_REMARK_POPUP" });
      dispatch({ type: "CLOSE_REMARK_POPUP_FOR_CONSIGNEE" });
      dispatch({ type: "CLOSE_DELETE_REMARK_SINGLEPOA" });
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
    },
    onOk: (args) => {
      if (!isEmpty(args.newRemark)) {
        dispatch(dispatchAction(updateRemarksForDeletedPOA)(args));
        dispatch({ type: "CLOSE_DELETE_REMARK_POPUP" });
        dispatch({ type: "CLOSE_REMARK_POPUP_FOR_CONSIGNEE" });
        dispatch({ type: "CLOSE_DELETE_REMARK_SINGLEPOA" });
      } else {
        dispatch(requestValidationError("Please enter deletion remarks", ""));
      }
    },
  };
};

const DeletionRemarksPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DeletionRemarksPopUpPanel);

export default DeletionRemarksPopUpContainer;
