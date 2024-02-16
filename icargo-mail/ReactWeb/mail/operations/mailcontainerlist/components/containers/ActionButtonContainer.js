import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { onCloseFunction } from '../../actions/commonaction';
import { onSelectFunction } from '../../actions/commonaction';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';



const mapDispatchToProps = (dispatch) => {
  return {
    onCloseFunction: () => {
      dispatch(dispatchAction(onCloseFunction)());
    },
    onSelectFunction: () => {
      dispatch(dispatchAction(onSelectFunction)());
    }

  }
}

const ActionButtonContainer = connectContainer(
  null,mapDispatchToProps
)(ActionButtonPanel)


export default ActionButtonContainer