import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { asyncDispatch,dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {execute} from '../../actions/commonaction';


import { reset } from 'redux-form';

const mapStateToProps = (state) => {
  return {
  }
}
const mapDispatchToProps = (dispatch) => {
return {
execute:()=>{
 dispatch(asyncDispatch(execute)());     
  },
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer