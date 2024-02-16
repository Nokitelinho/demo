import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { toggleFilter,listCarditEnquiry,clearFilter} from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
  return {
     screenMode:state.filterReducer.screenMode,
     filterValues:  state.filterReducer.filterValues,
     airportCode:state.commonReducer.airportCode,
     oneTimeValues: state.commonReducer.oneTimeValues,
     initialValues:{ 
       airportCode:state.commonReducer.airportCode,
       fromDate: state.commonReducer.defaultFromDate,
       toDate: state.commonReducer.defaultToDate,
       ...state.filterReducer.filterValues
      },
     formValues: state.form && state.form.carditFilter ? state.form.carditFilter.values : {},
     filter: state.filterReducer.summaryFilter.filter,
     popOver: state.filterReducer.summaryFilter.popOver,
     popoverCount: state.filterReducer.summaryFilter.popoverCount,
     showPopOverFlag: state.commonReducer.showPopOverFlag,
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
   onlistCarditEnquiry: () => {
     dispatch(asyncDispatch(listCarditEnquiry)({'displayPage':'1',mode:'LIST'}))
    },
    onclearCarditEnquiry: () => {
      dispatch(dispatchAction(clearFilter)());
    },
      showPopover: () => {
     dispatch({type : 'SHOW_POPOVER'});
    },
  closePopover: () => {
     dispatch({type : 'CLOSE_POPOVER'});
    }

  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer