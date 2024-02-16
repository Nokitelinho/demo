import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {clearFilter,listPaymentDetails,toggleFilter,addPayment} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import FilterPanel from '../panels/FilterPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';



const mapStateToProps = (state) => {
  let currentDate = getCurrentDate();
  return {
     oneTimeValues:state.commonReducer.oneTimeValues,
     screenMode:state.filterReducer.screenMode,
     screenFilterMode:state.filterReducer.screenFilterMode,
     filterValues:state.filterReducer.filterValues,
	 initialValues:{ 
       fromDate:state.filterReducer.fromDate,
       toDate: state.filterReducer.toDate,
       batchStatus: state.filterReducer.batchStatus,
	   paCode: state.filterReducer.paCode,
       ...state.filterReducer.filterValues
      }
    }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenFilterMode) => {
      dispatch(toggleFilter(screenFilterMode));
    },
    onListPayment: () => {
      dispatch(asyncDispatch(listPaymentDetails)({'displayPage':'1',mode:'LIST'}))
    },
    onclearPayment: () => {
      dispatch(dispatchAction(clearFilter)());
    }	,
    onAddPayment : () =>{
      dispatch(dispatchAction(addPayment)());
    }	,
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