import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { clearFilter, onList, changeFilterMode, getBillingInvoiceDetails } from '../../actions/filteraction'
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
const mapStateToProps = (state) => {
  return {
    filterDisplayMode: state.filterReducer.filterDisplayMode,
    customerDetails: state.filterReducer.customerDetails,
    noRecordFound: state.filterReducer.noRecordFound,
    initialValues: {
      inboundAccNo: state.filterReducer.inboundAccNo
    }

  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onClearFilter: () => {
      dispatch(clearError());
      dispatch(dispatchAction(clearFilter)());
    },

    onList: () => {
      dispatch(asyncDispatch(onList)())
        .then((response) => {
          if (isEmpty(response.errors)) {
            return dispatch(dispatchAction(getBillingInvoiceDetails)())
          } else {
            return response
          }
        });
    },
    onChangeFilterMode: (mode) => {
      dispatch(changeFilterMode(mode));
    },
  }
}

const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)

export default FilterContainer