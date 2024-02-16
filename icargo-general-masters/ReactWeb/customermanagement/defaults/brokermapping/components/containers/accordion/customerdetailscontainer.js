import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";

import CustomerDetailsPanel from "../../panels/accordion/CustomerDetailsPanel.jsx";
import { removeSpace} from "../../../actions/commonaction";
import {TOGGLE_ADDITIONAL_POP_UP} from "../../../constants/constants";

const getadditionalNames=state=>{
  const customerDetails=state.commonReducer.customerDetails;
  let additionalArray=customerDetails.additionalNames;
 
  return {...additionalArray.slice(0,5)}
}

const mapStateToProps = (state) => {
  return {
      customerDetails:state.commonReducer.customerDetails,
      showAdditionalPopUp:state.commonReducer.showAdditionalPopUp,
      disableCustomerFields:state.commonReducer.disableCustomerFields,
      showAddNamPopUp:state.commonReducer.showAddNamPopUp,
      initialValues: {
        ...state.commonReducer.customerDetails,
        adittionalName:state.commonReducer.additionalNames.length>0?state.commonReducer.additionalNames[0].adlNam:""
      }
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onShowAdditionalPopUp:()=>
    {
      dispatch({
        type: TOGGLE_ADDITIONAL_POP_UP
      })
    },
    onShowAdditionalName:()=>{
      dispatch({type:"SHOW_ADDITIONAL_NAME_POPUP"})
    },
    removeSpace:(args)=>{
      dispatch(dispatchAction(removeSpace)(args))
    }
  };
};

const CustomerDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(CustomerDetailsPanel)

export default CustomerDetailsContainer;