import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { toggleFilter,clearFilter,onListDetails,onListDSNDetails,validateTab, changeTab, changeOffloadTab} from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import FilterPanel from '../panels/FilterPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';




const mapStateToProps = (state) => {
  return {
   
    screenMode: state.filterReducer.screenMode,
    filterValues: state.filterReducer.filterValues,
    activeOffloadTab:state.commonReducer.activeOffloadTab,
    oneTimeValues: state.commonReducer.oneTimeValues,
    offloadfilterform:state.form.offloadFilter,
    offloadMailFilterform:state.form.offloadMailFilter,
    offloadDSNFilterform:state.form.offloadDSNFilter,
    mailbagsdetails:state.filterReducer.mailbagsdetails,
    //initialValues:{containerType:'ALL',upliftAirport: state.commonReducer.upliftAirport},
    initialValues:{containerType:'ALL',upliftAirport: state.commonReducer.upliftAirport,...state.filterReducer.filterValues},
    defaultPageSize:state.filterReducer.defaultPageSize,
    containerdetails: state.filterReducer.containerdetails,
    filter: state.filterReducer.summaryFilter.filter,
    popOver: state.filterReducer.summaryFilter.popOver,
    popoverCount: state.filterReducer.summaryFilter.popoverCount,
    showPopOverFlag: state.commonReducer.showPopOverFlag,
   
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    toggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    toggleMailFilter:(screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    toggleDSNFilter :(screenMode) =>{
      dispatch(toggleFilter(screenMode));
    },
    onClearDetails: () => {
      dispatch(dispatchAction(clearFilter)());
    },
    onClearMailDetails :() =>{
      dispatch(dispatchAction(clearFilter)());
    },
    onClearDSNDetails :() =>{
      dispatch(dispatchAction(clearFilter)());
    },
    onListDetails: (data) => {
      dispatch(asyncDispatch(onListDetails)({ 'displayPage': '1', mode: 'LIST' ,data}))
    },
    onListMailDetails:(data)=>{
      dispatch(asyncDispatch(onListDetails)({ 'displayPage': '1', mode: 'LIST' ,data}))
    },
    onListDSNDetails:(data) => {
      dispatch(asyncDispatch(onListDSNDetails)({ 'displayPage': '1', mode: 'LIST' ,data}))
    },
    changeOffloadTab: (currentTab,containerdetails) => {
      dispatch(dispatchAction(changeOffloadTab)(currentTab));
    },
    showPopover: () => {
      dispatch({type : 'SHOW_POPOVER'});
     },
   closePopover: () => {
      dispatch({type : 'CLOSE_POPOVER'});
    },
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
    },


  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer