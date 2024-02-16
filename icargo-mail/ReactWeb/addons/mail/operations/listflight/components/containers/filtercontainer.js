import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { toggleFilter, listFlightDetails, clearFilter,changeTab  } from '../../actions/filteraction';
import TabPanel from '../panels/TabPanel.jsx';
import { screenLoad } from '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {
    screenMode: state.filterReducer.screenMode,
	activeTab:state.commonReducer.activeTab,
    initialValues: { awbNumber: {'shipmentPrefix':state.commonReducer.shipmentPrefix,
    'masterDocumentNumber':state.commonReducer.documentNumber }}
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    // onlistFlightDetails: () => {
    //   dispatch(asyncDispatch(listFlightDetails)({ 'displayPage': '1', mode: 'LIST' }))
    // },
    // onclearFlightDetails: () => {
    //   dispatch(dispatchAction(clearFilter)());
    // },
    showPopover: () => {
      dispatch({ type: 'SHOW_POPOVER' });
    },
    closePopover: () => {
      dispatch({ type: 'CLOSE_POPOVER' });
    },
	    changeTab: (currentTab) => {
        dispatch(dispatchAction(changeTab)({currentTab}));
        if(currentTab==='LoadPlanView'){
            dispatch(asyncDispatch(screenLoad)())
        }else if(currentTab==='BookingView'){
    
             dispatch(asyncDispatch(screenLoad)())
        }
      }
  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(TabPanel)


export default FilterContainer