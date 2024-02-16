import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { onClose } from '../../actions/commonaction';
import { okButtonClick } from '../../actions/filteraction';

const mapDispatchToProps = (dispatch) => {
  return {
    onClose: () => {
      dispatch(dispatchAction(onClose)());
    },

    onOkButton: () => {
      dispatch(asyncDispatch(okButtonClick)());

    }
  }
}
const ActionButtonContainer = connectContainer(
  null,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer
