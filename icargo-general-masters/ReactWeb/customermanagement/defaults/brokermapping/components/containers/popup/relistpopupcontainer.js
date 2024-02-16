import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { validateFilter } from "../../../actions/commonaction";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import { change } from "icoreact/lib/ico/framework/component/common/form";

import RelistPopUpPanel from "../../panels/popup/RelistPopUpPanel.jsx";

const mapStateToProps = (state) => {
  return {
    //   screenMode: state.commonReducer.screenMode,
    showRelist:state.commonReducer.showRelist,
    customerDetails:state.form.brokermappingform.values?state.form.brokermappingform.values.customerCode:"",
      initialValues: {
        
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    onClose: ()=>{
        dispatch({ type: "CLOSE_RELIST_POPUP" });
        dispatch(change('brokermappingform','customerCode',''));
    },
    onYes: ()=>{
      dispatch({type:"__POPUP_OPEN_STATE",popupMode: false}),
      dispatch(dispatchAction(validateFilter)()),
      dispatch({
        type:"CLEAR",
        data: "initial"
      })
    }
  };
};

const RelistPopUpContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(RelistPopUpPanel)

export default RelistPopUpContainer;