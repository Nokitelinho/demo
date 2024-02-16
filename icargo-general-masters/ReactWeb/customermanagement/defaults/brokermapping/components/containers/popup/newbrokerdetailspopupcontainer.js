import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {showCustomerLovPopup,validateCustomerCode,validateBrokerPoa,updateAddDetailsForm } from "../../../actions/commonaction";
import {
  dispatchAction,asyncDispatch
} from "icoreact/lib/ico/framework/component/common/actions";
import { isEmpty } from "icoreact/lib/ico/framework/component/util";

import NewBrokerDetailsPopUpPanel from "../../panels/popup/NewBrokerDetailsPopUpPanel.jsx";
import {TOGGLE_POP_UP_CLOSE} from "../../../constants/constants";

const mapStateToProps = (state) => {
  return {
      showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
      showBroker:state.commonReducer.showBroker,
	  brokerFileIndex: "MNGPOA" + state.commonReducer.brokerCodeValue,
      brokerFileEnable: isEmpty(state.commonReducer.brokerCodeValue)
      ? false
      : true,
    brokerCodeValue:state.commonReducer.brokerCodeValue,
	  initialValues: {
        brokerCode:state.commonReducer.brokerCodeValue ? state.commonReducer.brokerCodeValue : "",
        brokerName:state.commonReducer.brokerNameValue? state.commonReducer.brokerNameValue:"",
        station:state.commonReducer.station?state.commonReducer.station:"",
        orginRadio: state.form.adddetailsform
        ? state.form.adddetailsform.values
          ? state.form.adddetailsform.values.orginRadio
            ? state.form.adddetailsform.values.orginRadio
            : "I"
          : "I"
        : "I",
        sccCodeI:state.form.adddetailsform?state.form.adddetailsform.values?state.form.adddetailsform.values.sccCodeI?state.form.adddetailsform.values.sccCodeI:"":"":"",
        sccCodeE:state.form.adddetailsform?state.form.adddetailsform.values?state.form.adddetailsform.values.sccCodeE?state.form.adddetailsform.values.sccCodeE:"":"":"",
        orgin:state.form.adddetailsform?state.form.adddetailsform.values?state.form.adddetailsform.values.orgin?state.form.adddetailsform.values.orgin:"":"":"",
        destination:state.form.adddetailsform?state.form.adddetailsform.values?state.form.adddetailsform.values.destination?state.form.adddetailsform.values.destination:"":"":"",
		document: state.form.adddetailsform
        ? state.form.adddetailsform.values
          ? state.form.adddetailsform.values.document &&
            state.commonReducer.brokerCodeValue
            ? state.form.adddetailsform.values.document
            : []
          : []
        : [],
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    onClose: ()=>{
        dispatch({ type: TOGGLE_POP_UP_CLOSE });
        dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
        dispatch({type:"CLEAR_BROKER/CONSIGNEE_FORM_VALUES"})
    },
    onOk :(args)=>{
      dispatch(dispatchAction(updateAddDetailsForm)())
    },
    showCustomerPopup: () => {
      dispatch(dispatchAction(showCustomerLovPopup)());
    },
    validateCustomer:(args)=>{
      dispatch(asyncDispatch(validateCustomerCode)(args))
    },
    updateOnEmptyBroker: () => {
      dispatch({ type: "_ON_CLEAR_BROKER_CONSIGNEE_CODE" });
    },
  };
};

const NewBrokerDetailsPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(NewBrokerDetailsPopUpPanel)

export default NewBrokerDetailsPopUpContainer;