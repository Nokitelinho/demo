import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { onCloseFunction } from '../../actions/commonaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {  
  return {
   
    screenMode: state.filterReducer.screenMode,
    filterValues: state.filterReducer.filterValues,
    oneTimeValues: state.commonReducer.oneTimeValues,
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onCloseFunction: () => {
        dispatch(asyncDispatch(onCloseFunction)());
    }
     
  } 
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer