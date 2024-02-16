import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import { showCustomerLovPopup } from "../../actions/commonaction";

import BrokerDetailsFilterPanel from "../panels/BrokerDetailsFilterPanel.jsx";

const mapStateToProps = state => {
  return {
    showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
    initialValues: {
      ...state.commonReducer.brokerFilter
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

const BrokerDetailsFilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(BrokerDetailsFilterPanel);

export default BrokerDetailsFilterContainer;
