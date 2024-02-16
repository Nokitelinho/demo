import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import {maintainconsignment,clearPanelFilter,onChangeScreenMode} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {resetTable} from 'icoreact/lib/ico/framework/component/common/store/commonactions';




/*const mapStateToProps = (state) => {
  return {
   
     airportCode:state.commonReducer.airportCode,
     oneTimeValues: state.commonReducer.oneTimeValues,
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
   onlistCarditEnquiry: () => {
     dispatch(asyncDispatch(listCarditEnquiry)({'displayPage':'1',action:'LIST'}))
    },
    onClearFilter: () => {
      
      dispatch(dispatchAction(clearFilter)());
    }

  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)
*/

const mapStateToProps = (state) => {
  return {
    
     airportCode:state.commonReducer.airportCode,
     oneTimeValues: state.commonReducer.oneTimeValues,
     initialValues:state.listconsignmentreducer.navigationFilter,
     editMode:state.listconsignmentreducer.editMode,
     screenMode:state.listconsignmentreducer.screenMode,
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
  onlistMaintainConsignment: () => {
     dispatch(asyncDispatch(maintainconsignment)({'displayPage':'1',action:'LIST'}))
    },
  onclearConsignmentDetails: () => {
    dispatch(dispatchAction(clearPanelFilter)());
    const tableId = 'mailBagsTable';
    dispatch(resetTable(tableId));
    /*dispatch(reset('consignmentFilter'));
    dispatch({type:'CLEAR_FILTER' });*/
     },
     onChangeScreenMode:(mode)=>{
      dispatch(dispatchAction(onChangeScreenMode)(mode));
     }
  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)

export default FilterContainer