import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {clearFilter,listBillingDetails,toggleFilter, listConsignmentDetails} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import FilterPanel from '../panels/FilterPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {isEmpty} from 'icoreact/lib/ico/framework/component/util'
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';



const mapStateToProps = (state) => {
  let currentDate = getCurrentDate();
  return {
     oneTimeValues:state.commonReducer.oneTimeValues,
     fromDate:(state.form.gpaBillingEntryFilter&&state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values.fromDate:null,
     toDate:(state.form.gpaBillingEntryFilter&&state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values.toDate:null,
     filterValues: state.filterReducer.filterValues,
     screenMode:state.filterReducer.screenMode,
     screenFilterMode:state.filterReducer.screenFilterMode,
     changeStatus:state.filterReducer.changeStatus,
     initialValues:!isEmpty(state.filterReducer.filterValues) ? {...state.filterReducer.filterValues} : {rateBasis:'ALL',uspsMailPerformance:'ALL'},
     popOver: state.filterReducer.summaryFilter.popOver,
     popoverCount: state.filterReducer.summaryFilter.popoverCount,
     showPopOverFlag: state.filterReducer.showPopOverFlag,
     filter: state.filterReducer.summaryFilter.filter,

    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    onToggleFilter: (screenFilterMode) => {
      dispatch(toggleFilter(screenFilterMode));
    },
    onclearBillingDetails: () => {
      dispatch(dispatchAction(clearFilter)());
    }	,
    onlistBillingDetails: () => {
    // dispatch(asyncDispatch(listBillingDetails)({'displayPage':'1',mode:'LIST'}))
    dispatch(asyncDispatch(listBillingDetails)({'displayPage':'1',mode:'LIST'})).then((response) => {
      //if(data.actionName==='MODIFY' || data.actionName==='ADD_CONTAINER') {
        if (isEmpty(response.errors)) {
          dispatch(asyncDispatch(listConsignmentDetails)({'displayPage':'1',mode:'CONSIGNMENT_LIST'}))
        }})
    },
    displayError: (message, target) => {
        dispatch(requestValidationError(message, target))
    },
      showPopover: () => {
      dispatch({type : 'SHOW_POPOVER'});
     },
   closePopover: () => {
      dispatch({type : 'CLOSE_POPOVER'});
     },
  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer