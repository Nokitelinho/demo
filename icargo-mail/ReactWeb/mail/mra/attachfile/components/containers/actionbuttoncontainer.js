import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction,asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose,onAddFile} from '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {
    displayMode:state.filterReducer.displayMode,
    filterFromAdvpay:state.commonReducer.filterValuesFromAdvpay
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {     
          onCloseFunction:()=>{
          dispatch(dispatchAction(onClose)())
          },
          onAddFile:()=>{
            dispatch(asyncDispatch(onAddFile)())
          }
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer