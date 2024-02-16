import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { listSettlementBatch,loadBatchDetail,toggleFilter,clearFilter, validateForm} from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError, clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

const mapStateToProps = (state) => {
  return {
     screenMode:state.filterReducer.screenMode,
     filterValues:  state.filterReducer.filterValues,    
     noRecords: state.filterReducer.noRecords,
     listSettlementBatchFilter: state.form.listSettlementBatchFilter,
     oneTimeValues: state.commonReducer.oneTimeValues,
     initialValues:{ 
       fromDate: state.commonReducer.defaultFromDate,
       toDate: state.commonReducer.defaultToDate,
       ...state.filterReducer.filterValues
    }
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },

   onlistSettlementBatch: (values) => {
     let validObject = validateForm(values)
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
        dispatch({ type: 'NO_FILTER_SELECTED' });
      } else {
        dispatch(asyncDispatch(listSettlementBatch)())
          .then((response) => {
            if (response.results[0].batchLists.length > 0) {
              dispatch(asyncDispatch(loadBatchDetail)({ selectedBatchId: response.results[0].batchLists[0].batchId + '~' +response.results[0].batchLists[0].gpaCode+ '~'+ response.results[0].batchLists[0].batchSequenceNum, pageNumber: '1', batchListMode: 'onList' }))
            }
          }
          ).then(() => { dispatch(clearError()) });
      }
    },
    onclearSettlementBatch: () => {
      dispatch(dispatchAction(clearFilter)());
    },
  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer