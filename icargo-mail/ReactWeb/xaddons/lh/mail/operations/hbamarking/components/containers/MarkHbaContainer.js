import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose, changePosition, changeHbaType} from '../../actions/commonaction';
import MarkHBAPanel from '../panels/MarkHBAPanel.jsx';

const mapStateToProps = (state) => {
  return {
  
     screenMode:state.commonReducer.screenMode,
     oneTimeValues:state.commonReducer.oneTimeValues,
     operationFlag:state.commonReducer.operationFlag,
     initialValues:{position:state.commonReducer.position,
      hbaType:state.commonReducer.hbaType}
  }
}
const mapDispatchToProps = (dispatch) => {
  return {

    onClose: () => {
      dispatch(dispatchAction(onClose)());
  },
  changePosition: (position) => {
    dispatch(dispatchAction(changePosition)({position}));
  },
  changeHbaType: (hbaType) => {
    dispatch(dispatchAction(changeHbaType)({hbaType}));
  }

  }
}
const MarkHBAContainer = connectContainer(
   mapStateToProps,
  mapDispatchToProps
)(MarkHBAPanel)


export default MarkHBAContainer