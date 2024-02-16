import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ConsignmentPanel from '../panels/ConsignmentPanel.jsx';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {updateBillingDetails,listConsignmentDetails} from '../../actions/filteraction';


const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    noData: state.filterReducer.noData,
    screenMode: state.filterReducer.screenMode,
    tableFilter: state.filterReducer.tableFilter,
    consignmentdetails : state.filterReducer.consignmentdetails,
    selectedConsignmentIndex:state.filterReducer.selectedConsignmentIndex,
	selectedConsignments:state.filterReducer.selectedConsignments,
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    saveSelectedConsignmentIndex:(indexes)=> {
	dispatch({type : 'UPDATE_INDEX',data:indexes});
    dispatch(asyncDispatch(updateBillingDetails)({indexes}))
    },
    onlistBillingDetails: (displayPage,fromConsignmentList) => {
    // dispatch(asyncDispatch(listBillingDetails)({displayPage,mode:'LIST',fromConsignmentList:'Y'}))
         dispatch(asyncDispatch(listConsignmentDetails)({displayPage, mode:'CONSIGNMENT_LIST'}))
    }
  }
}
const ConsignmentContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ConsignmentPanel)


export default ConsignmentContainer 