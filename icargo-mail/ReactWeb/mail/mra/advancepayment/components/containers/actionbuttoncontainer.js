import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction,asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose,navigateToListSettlementBatch} from '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {
    displayMode:state.filterReducer.displayMode
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {     
          onCloseFunction:()=>{
          dispatch(dispatchAction(onClose)())
          },
		  navigateToListSettlementBatch:(mailbag)=>{
          dispatch(asyncDispatch(navigateToListSettlementBatch)())  
          },
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer