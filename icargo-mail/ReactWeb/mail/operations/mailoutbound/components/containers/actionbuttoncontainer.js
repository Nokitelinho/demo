import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {onCloseFunction} from '../../actions/commonaction';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';


const mapStateToProps = (state) => {  
  return {
    screenMode: state.filterReducer.screenMode,
    displayMode:state.filterReducer.displayMode,
    airportCode:state.commonReducer.airportCode,
    currentDate: getCurrentDate(), 
   
  }
}
const mapDispatchToProps = (dispatch) => {
  return {


    onCloseFunction: () => {
        dispatch(dispatchAction(onCloseFunction)());
    },
     
   
    
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer