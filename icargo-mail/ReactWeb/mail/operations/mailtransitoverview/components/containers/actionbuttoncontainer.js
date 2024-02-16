import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import ActionButtonPanel from "../panels/ActionButtonPanel.jsx";
import { asyncDispatch } from "icoreact/lib/ico/framework/component/common/actions/index.js";
import { onCloseFunction } from "../../actions/commonaction.js";
import { navigateToMailInbound } from "../../actions/commonaction.js";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions/dispatchAction.js";

const mapDispatchToProps = (dispatch) => {
    return {
      onCloseFunction: () => {
          dispatch(asyncDispatch(onCloseFunction)());
        },
          navigateToMailInbound:() => {
            dispatch(dispatchAction(navigateToMailInbound)());
          }
      }
       
    } 
  
const ActionButtonContainer= connectContainer(
    null,
    mapDispatchToProps
)(ActionButtonPanel)
export default ActionButtonContainer;