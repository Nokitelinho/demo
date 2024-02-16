import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import {onlistContainerDetails,clearFilter,onInputChangeSearchmode, toggleFilter} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

let filterValues ={
  'flightnumber':{
      'flightDate':'',
      'carrierCode':'',
      'flightNumber':''
  }
}


const mapStateToProps = (state) => {
  return {
  screenMode:state.listcontainerreducer.screenMode,
	filterValues: (state.filterReducer && state.filterReducer.values.flightnumber) ? state.filterReducer.values : filterValues,   
  airportCode:state.commonReducer.airportCode,
  oneTimeValues: state.commonReducer.oneTimeValues,	 
  assignedTo:state.commonReducer.assignedTo,
  filterDetails: state.listcontainerreducer.filterDetails,
  initialValues:state.commonReducer.filterIntialValues,
  filter: state.listcontainerreducer.summaryFilter.filter,
  popOver: state.listcontainerreducer.summaryFilter.popOver,
  popoverCount: state.listcontainerreducer.summaryFilter.popoverCount,
  showPopOverFlag: state.commonReducer.showPopOverFlag,
  containerFilterForm:state.form.containerFilter
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
   onlistContainerDetails: () => {
     dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1',action:'LIST'}))
    },
    onclearContainerDetails: () => {
      dispatch(dispatchAction(clearFilter)());
    },	
	onInputChangeSearchmode:() => {
      dispatch(dispatchAction(onInputChangeSearchmode)());
    },
  onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
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