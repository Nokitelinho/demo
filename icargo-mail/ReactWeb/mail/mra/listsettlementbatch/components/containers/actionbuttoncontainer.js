import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose} from '../../actions/commonaction';
import {clearBatch} from '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {
    screenMode:state.filterReducer.screenMode
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {     
          onCloseFunction:()=>{
          dispatch(dispatchAction(onClose)())
          },
          clearBatchFunction:()=>{
          dispatch(dispatchAction(clearBatch)())
          }
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer