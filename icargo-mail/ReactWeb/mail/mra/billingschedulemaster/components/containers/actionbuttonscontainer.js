import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import ActionButtonsPanel from '../panels/Actionbuttonspanel.jsx'
import { onCloseFunction, onSave,validateBillingDetails} from '../../actions/commonactions'

const mapStateToProps = (state) => {
  return {
    screenMode: state.filterpanelreducer.screenMode,
    parameterMap: state.detailspanelreducer.parameterMap
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    onCloseFunction: () => {
      dispatch(dispatchAction(onCloseFunction)());
    },
    onSave: () => {
      dispatch(asyncDispatch(validateBillingDetails)()).then((response) => {
        if (response.status=="success") {
          dispatch(asyncDispatch(onSave)());
        }
      })
    }
  }
}


const ActionButtonsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonsPanel)
export default ActionButtonsContainer

