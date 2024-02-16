import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { onCloseFunction, onOffloadFunction } from '../../actions/commonaction';
import { onListDetails } from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {  
  return {
    activeOffloadTab: state.commonReducer.activeOffloadTab,
    screenMode: state.filterReducer.screenMode,
    filterValues: state.filterReducer.filterValues,
    activeOffloadTab:state.commonReducer.activeOffloadTab,
    oneTimeValues: state.commonReducer.oneTimeValues,
    displayPage:state.filterReducer.displayPage,
    defaultPageSize:state.filterReducer.defaultPageSize
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onCloseFunction: () => {
        dispatch(dispatchAction(onCloseFunction)());
    },
    onOffloadFunction:() =>{
        dispatch(asyncDispatch(onOffloadFunction)()).then(() => {
          dispatch(asyncDispatch(onListDetails)({ 'displayPage': '1', mode: 'LIST' }))
        });
    },
     
  } 
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer