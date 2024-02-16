import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {performRowSelection} from '../../actions/flightaction';
import { changeDetailPanelMode } from '../../actions/commonaction';

const mapStateToProps = (state) => { 
  return {
     // screenMode:state.filterReducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      containerData:state.containerReducer.containerData

  }
}

const mapDispatchToProps = (dispatch) => {
  return {
      onFlightRowSelection:(data)=>{
         dispatch(dispatchAction(performRowSelection)(data))
      },
      onChangeDetailPanelMode:(event)=>{
        dispatch(dispatchAction(changeDetailPanelMode)(event))
      }
    }
  }

let DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer