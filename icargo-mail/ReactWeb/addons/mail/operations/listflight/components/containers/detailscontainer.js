import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { listCarditEnquiry, applyCarditFilter, onClearFlightFilter, updateSortVariables, performCarditAction } from '../../actions/filteraction';
import { screenLoad } from '../../actions/commonaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';



const mapStateToProps = (state) => {
  return {
    screenMode: state.filterReducer.screenMode,
    noData: state.filterReducer.noData, 
   activeTab:state.commonReducer.activeTab,
    //selectedMailbagIndex: state.filterReducer.selectedMailbagIndex, 
    flightDetails:state.commonReducer.flightDetails
  }
}

const mapDispatchToProps = (dispatch) => {
  return { 
    saveSelectedFlightIndex: (indexes) => {
      dispatch({ type: 'SAVE_SELECTED_INDEX', indexes })
    },
    listFlightDetails: (displayPage,pageSize) => {
      dispatch(asyncDispatch(screenLoad)({'displayPage':displayPage,'pageSize':pageSize}))
  }
    
  }

}
const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer