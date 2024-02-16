import FilterPanel  from "../panels/FilterPanel.jsx";
import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';
import { listMailTransitOverview } from "../../actions/filteraction.js";
import { fetchFlightCapacityDetails } from "../../actions/filteraction.js";
import { clearFilter } from "../../actions/filteraction.js";
import { toggleFilter } from "../../actions/filteraction.js";
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';



import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';


const mapStateToProps = (state) => {
    return {
    mailTransitFilter: state.filterReducer.mailTransitFilter,
   screenMode:state.filterReducer.screenMode,
   
  initialValues:{airportCode:state.filterReducer.navigationFilter.airportCode?state.filterReducer.navigationFilter.airportCode :state.commonReducer.airportCode,
    fromDate:state.filterReducer.navigationFilter.fromDate?state.filterReducer.navigationFilter.fromDate:(state.filterReducer.fromDate?state.filterReducer.fromDate:getCurrentDate()),
      toDate:state.filterReducer.navigationFilter.toDate?state.filterReducer.navigationFilter.toDate:(state.filterReducer.toDate?state.filterReducer.toDate:getCurrentDate()),
      fromTime:'00:00',
  toTime:'23:59',
  flightnumber:state.filterReducer.navigationFilter.flightnumber?state.filterReducer.navigationFilter.flightnumber:''
  
}
            }}

const mapDispatchToProps=(dispatch)=>{

    return{
      onToggleFilter: (screenMode) => {
        dispatch(toggleFilter(screenMode));
      },
    onlistMailTransitOverview: () => {
        dispatch(asyncDispatch(listMailTransitOverview)({ 'displayPage': '1', action: 'LIST' }))
      },
    
      onclearMailTransitOverview: () => {
        dispatch(dispatchAction(clearFilter)());
      }
    }
}
  const FilterContainer = connectContainer(
         mapStateToProps,
         mapDispatchToProps
)(FilterPanel)


export default FilterContainer;