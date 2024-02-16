import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { listMailbagsEnquiry, clearFilter, toggleFilter } from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

// let filterValues ={
//   'flightnumber':{
//       'flightDate':'',
//       'carrierCode':'',
//       'flightNumber':''
//   }
// }

const mapStateToProps = (state) => {
  return {
    filterValues: state.filterReducer.filterValues,
    airportCode: state.commonReducer.airportCode,
    oneTimeValues: state.commonReducer.oneTimeValues,
    screenMode: state.filterReducer.screenMode,
    initialValues:state.filterReducer.fromInbound?{...state.filterReducer.filterValues}:
				(state.filterReducer.screenMode!=='initial' ||state.filterReducer.noData?{...state.filterReducer.filterValues}:{scanPort: state.commonReducer.airportCode, fromDate: getCurrentDate(), toDate: getCurrentDate()}),
    filter: state.filterReducer.summaryFilter.filter,
    popOver: state.filterReducer.summaryFilter.popOver,
    popoverCount: state.filterReducer.summaryFilter.popoverCount,
    formValues: state.form && state.form.mailbagFilter ? state.form.mailbagFilter.values : {},
    showPopOverFlag: state.filterReducer.showPopOverFlag
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    onlistMailbagEnquiry: () => {
      dispatch(asyncDispatch(listMailbagsEnquiry)({ 'displayPage': '1', action: 'LIST','orgDesChangeFlag':false }))
    },
    onclearMailbagsEnquiry: () => {
      dispatch(dispatchAction(clearFilter)());
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