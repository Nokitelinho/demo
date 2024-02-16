import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {ValidateAddConsigneePoa,showCustomerLovPopup,validateCustomerCode,updateAddConsigneeDetailsForm } from "../../../actions/commonaction";
import {
  dispatchAction,asyncDispatch
} from "icoreact/lib/ico/framework/component/common/actions";
import { isEmpty } from "icoreact/lib/ico/framework/component/util";

import NewConsigneeDetailsPopUpPanel from "../../panels/popup/NewConsigneeDetailsPopUpPanel.jsx";
import {TOGGLE_POP_UP_CLOSE} from "../../../constants/constants";


const mapStateToProps = (state) => {
  return {
      screenMode: state.commonReducer.screenMode,
      showConsigneePopUp:state.commonReducer.showConsigneePopUp,
      showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
	  consigneeFileIndex: "MNGPOA" + state.commonReducer.consigneeCodeValue,
      consigneeFileEnable: isEmpty(state.commonReducer.consigneeCodeValue)
      ? false
      : true,
      consigneeCodeValue:state.commonReducer.consigneeCodeValue,
    initialValues: {
        consigneeCode:state.commonReducer.consigneeCodeValue ? state.commonReducer.consigneeCodeValue : "",
        consigneeName:state.commonReducer.consigneeNameValue? state.commonReducer.consigneeNameValue:"",
        station:state.commonReducer.station?state.commonReducer.station:"",
        orginRadio: state.form.addconsigneedetailsform
        ? state.form.addconsigneedetailsform.values
          ? state.form.addconsigneedetailsform.values.orginRadio
            ? state.form.addconsigneedetailsform.values.orginRadio
            : "I"
          : "I"
        : "I",
        sccCodeI:state.form.addconsigneedetailsform?state.form.addconsigneedetailsform.values?state.form.addconsigneedetailsform.values.sccCodeI?state.form.addconsigneedetailsform.values.sccCodeI:"":"":"",
        sccCodeE:state.form.addconsigneedetailsform?state.form.addconsigneedetailsform.values?state.form.addconsigneedetailsform.values.sccCodeE?state.form.addconsigneedetailsform.values.sccCodeE:"":"":"",
        orgin:state.form.addconsigneedetailsform?state.form.addconsigneedetailsform.values?state.form.addconsigneedetailsform.values.orgin?state.form.addconsigneedetailsform.values.orgin:"":"":"",
        destination:state.form.addconsigneedetailsform?state.form.addconsigneedetailsform.values?state.form.addconsigneedetailsform.values.destination?state.form.addconsigneedetailsform.values.destination:"":"":"",
		document: state.form.addconsigneedetailsform
        ? state.form.addconsigneedetailsform.values
          ? state.form.addconsigneedetailsform.values.document &&
            state.commonReducer.consigneeCodeValue
            ? state.form.addconsigneedetailsform.values.document
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
        dispatch({type:"CLEAR_BROKER/CONSIGNEE_FORM_VALUES"})
        dispatch({type:"__POPUP_OPEN_STATE",popupMode: false})
    },
    onOk :()=>{
      dispatch(dispatchAction(updateAddConsigneeDetailsForm)())
    },
    showCustomerPopup: () => {
      dispatch(dispatchAction(showCustomerLovPopup)());
    },
    validateCustomer:(args)=>{
      dispatch(asyncDispatch(validateCustomerCode)(args))
    },
    updateOnEmptyConsignee: () => {
      dispatch({ type: "_ON_CLEAR_BROKER_CONSIGNEE_CODE" });
    },
  };
};

const NewConsigneeDetailsPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(NewConsigneeDetailsPopUpPanel)

export default NewConsigneeDetailsPopUpContainer;