import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';

const mapStateToProps = (state) => {
  return {
    //screenMode:state.filterReducer.screenMode,
    noRecords: state.filterReducer.noRecords,
    //batchDisplay: state.filterReducer.batchDisplay,    
    batchDetailStatus: state.filterReducer.batchDetailStatus
    //batchDetailsList: state.batchDetailReducer.batchDetailsList,
      
  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  null
)(DetailsPanel)

export default DetailsContainer