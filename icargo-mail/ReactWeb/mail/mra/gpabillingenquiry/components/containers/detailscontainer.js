import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
//import { selectContainers,onSaveFunction,onOKRemarks } from '../../actions/commonaction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
//import { listInvoicDetails, clearFilter } from '../../actions/filteraction';



const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    noData: state.filterReducer.noData,
    mailbagsdetails : state.filterReducer.mailbagsdetails,
    screenMode: state.filterReducer.screenMode,
    tableFilter: state.filterReducer.tableFilter,
    selectedMailbagIndex:state.filterReducer.selectedMailbagIndex
  }
}

const mapDispatchToProps = (dispatch, ownProps) => {
  return {
  

  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer