import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FlightCarrierPanel from '../panels/FlightCarrierPanel.jsx';
import { chooseFilter } from '../../actions/commonaction';
import {toggleFilter} from '../../actions/filteraction';
import {dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { listDetails, validateForm, fetchFlightCapacityDetails, fetchFlightPreAdviceDetails ,fetchFlightVolumeDetails} from '../../actions/filteraction';
import {clearDetails} from '../../actions/filteraction';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';
const mapStateToProps = (state) => {
  return {
    screenMode: state.filterReducer.screenMode,
    filterValues:  state.filterReducer.filterValues,
    outboundFilter: state.form.outboundFilter,
    flightCarrierflag:state.commonReducer.flightCarrierflag,
    displayMode:state.filterReducer.displayMode,
    airportCode:state.commonReducer.airportCode,
    carrierCode:state.commonReducer.flightCarrierCode,
    isCarrierDefault:state.commonReducer.isCarrierDefault,
    //initialValues:{airportCode:state.commonReducer.airportCode,carrierCode:state.commonReducer.flightCarrierCode,filterType:state.commonReducer.isCarrierDefault==='Y'?'C':'F'},
    initialValues: {
      airportCode: state.commonReducer.airportCode,
      fromDate:state.commonReducer.fromDate,
      toDate:state.commonReducer.toDate,
      fromTime:'00:00',
      toTime:'23:59',
      operatingReference: state.commonReducer.defaultOperatingReference,
      carrierCode:state.commonReducer.flightCarrierCode,
      filterType:state.commonReducer.isCarrierDefault==='Y'?'C':'F',
      ...state.filterReducer.filterValues
    },
    oneTimeValues:state.commonReducer.oneTimeValues,
    filter: state.filterReducer.summaryFilter.filter,
    popOver: state.filterReducer.summaryFilter.popOver,
    popoverCount: state.filterReducer.summaryFilter.popoverCount,
    showPopOverFlag: state.filterReducer.showPopOverFlag
  }
}


const mapDispatchToProps = (dispatch) => {
  return {

    onchangeFilter: (event) => {
      dispatch(dispatchAction(chooseFilter)(event.target.value));
    },
    onToggleFilter: (screenMode) => {
      dispatch(dispatchAction(toggleFilter)(screenMode));
    },
    // The main outbound list
    onlistDetails: (values) => {
        /*  let validObject = validateForm(values)
      if (!validObject.valid) {
        dispatch(requestValidationError(validObject.msg, ''));
       
      }
      else {*/
      dispatch(asyncDispatch(listDetails)({'displayPage':'1',mode:'display',fromMainList:true})) .then((response) => {
        if(!isEmpty(response.results[0]) ){
          if(response.results[0].flightCarrierFilter.assignTo === 'F'){
            dispatch(asyncDispatch(fetchFlightCapacityDetails)());
			dispatch(asyncDispatch(fetchFlightPreAdviceDetails)());
            if(isSubGroupEnabled('LUFTHANSA_SPECIFIC') === true){
            dispatch(asyncDispatch(fetchFlightVolumeDetails)());}
		  }
        }
      });
     // }
    },
     onclearDetails: () => {
      dispatch(dispatchAction(clearDetails)());
    },

    showPopover: () => {
      dispatch({type : 'SHOW_POPOVER'});
     },
   closePopover: () => {
      dispatch({type : 'CLOSE_POPOVER'});
     },  

  }
}
const FlightCarrierContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FlightCarrierPanel)


export default FlightCarrierContainer;