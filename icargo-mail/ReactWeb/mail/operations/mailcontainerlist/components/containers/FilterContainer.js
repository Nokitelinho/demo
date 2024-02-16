import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import {onlistContainerDetails,clearFilter,toggleFilter} from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';


const mapStateToProps = (state) => {
    return {
        
        oneTimeValues: state.commonreducer.oneTimeValues,	 
        filter: state.mailcontainerreducer.summaryFilter.filter,
        assignedTo:state.commonreducer.assignedTo,
        screenMode:state.mailcontainerreducer.screenMode,
        initialValues:state.commonreducer.filterIntialValues,
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
     onlistContainerDetails: () => {
       dispatch(asyncDispatch(onlistContainerDetails)({displayPage:'1',action:'LIST'}))
      },
    onclearContainerDetails: () => {
        dispatch(dispatchAction(clearFilter)());
      },	
      onToggleFilter: (screenMode) => {
          dispatch(toggleFilter(screenMode));
        }
  }
}


const FilterContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
  )(FilterPanel)
  

export default FilterContainer