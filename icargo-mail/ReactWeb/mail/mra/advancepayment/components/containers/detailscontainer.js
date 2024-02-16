import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';



const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    paymentdetails : state.filterReducer.paymentdetails,
    screenMode: state.filterReducer.screenMode,
    screenFilterMode:state.filterReducer.screenFilterMode,
    filterValues:state.filterReducer.filterValues,

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