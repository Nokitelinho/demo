import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { toggleFilter,listMailboxDetails,clearPanelFilter} from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
  return {
    screenMode:state.filterReducer.screenMode,
    filterValues:  state.filterReducer.filterValues,
    listfilter: state.filterReducer.listfilter,
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    onclearMailboxIdDetails: () => {
      dispatch(dispatchAction(clearPanelFilter)());
       },
    
    onlistMailboxDetails: () => {
      dispatch(asyncDispatch(listMailboxDetails)({'displayPage':'1',mode:'LIST'}))
     },
  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer