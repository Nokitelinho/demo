import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { clearFilter, listMailbagDetails, onToggleFilter } from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import FilterPanel from '../panels/FilterPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


const mapStateToProps = (state) => {
  return {
    screenMode: state.filterReducer.screenMode,
    filterValues: state.filterReducer.filterValues,
    oneTimeValues: state.commonReducer.oneTimeValues,
    initialValues: {station:state.commonReducer.station,fromDate:state.commonReducer.fromDate,toDate:state.commonReducer.toDate}
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(onToggleFilter(screenMode));
    },
    onclearMailbagDetails: () => {
      dispatch(dispatchAction(clearFilter)());
    },
    onlistMailbagDetails: () => {
      dispatch(asyncDispatch(listMailbagDetails)({ 'displayPage': '1', mode: 'LIST' }))
    },
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
    }


  }
}

const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer