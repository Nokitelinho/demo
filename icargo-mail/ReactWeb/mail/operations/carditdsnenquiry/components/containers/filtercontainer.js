import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { toggleFilter,listCarditDsnEnquiry,clearFilter} from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {Constants} from '../../constants/constants.js'

const mapStateToProps = (state) => {
  return {
     screenMode:state.filterReducer.screenMode,
     filterValues:  state.filterReducer.filterValues,
     airportCode:state.commonReducer.airportCode,
     oneTimeValues: state.commonReducer.oneTimeValues,
     initialValues:{airportCode:state.commonReducer.airportCode},
     filter:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter?
              state.filterReducer.summaryFilter.filter:null,
     popOver:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver?
              state.filterReducer.summaryFilter.popOver:null,
     popoverCount:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popoverCount?
              state.filterReducer.summaryFilter.popoverCount:null,
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    onToggleFilter: (screenMode) => {
          dispatch(toggleFilter(screenMode));
    },
   onlistCarditDsnEnquiry: () => {
          dispatch(asyncDispatch(listCarditDsnEnquiry)({'displayPage':'1','pageSize':10,mode:Constants.LIST}))
    },
    onclearCarditDsnEnquiry: () => {
          dispatch(dispatchAction(clearFilter)());
    }

  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer