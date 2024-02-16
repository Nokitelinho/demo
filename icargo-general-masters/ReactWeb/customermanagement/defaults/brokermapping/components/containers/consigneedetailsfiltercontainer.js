import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import { showCustomerLovPopup } from "../../actions/commonaction";

import ConsigneeDetailsFilterPanel from "../panels/ConsigneeDetailsFilterPanel.jsx";

const mapStateToProps = state => {
  return {
    showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
    initialValues: {
      ...state.commonReducer.consigneeFilter
      }
  };
};

const mapDispatchToProps = dispatch => {
  return {
    showCustomerPopup: () => {
        dispatch(dispatchAction(showCustomerLovPopup)());
      }
  };
};

const ConsigneeDetailsFilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ConsigneeDetailsFilterPanel);

export default ConsigneeDetailsFilterContainer;
