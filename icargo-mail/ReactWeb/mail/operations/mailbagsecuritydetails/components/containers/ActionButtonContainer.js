import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { onCloseFunction, onPrint } from '../../actions/commonaction';
import { asyncDispatch, dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';


const mapStateToProps = (state) => {
  return {
    filterList: state.filterReducer.filterList
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onCloseFunction: () => {
      dispatch(dispatchAction(onCloseFunction)());
    },
    print: () => {
      dispatch(dispatchPrint(onPrint)())
    },

  }
}

const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)


export default ActionButtonContainer