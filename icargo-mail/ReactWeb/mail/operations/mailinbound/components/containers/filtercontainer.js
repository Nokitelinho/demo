import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { listMailInbound ,changeScreenMode,clearFilterPanel} from '../../actions/filteraction';
import {screenLoad} from '../../actions/commonaction.js';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util'
import * as constant from '../../constants/constants';

const mapStateToProps = (state) => {
  let currentDate = getCurrentDate();
  return {
       screenMode:state.filterReducer.screenMode,
       //store filterValues in one single object and pass it to FilterPanel
       filterVlues:state.filterReducer.filterVlues,
  //  popoverCount: getFilterValuesCount(state),
       oneTimeValues:state.commonReducer.oneTimeValues,
       initialValues:{port:state.filterReducer.navigationFilter.airportCode?state.filterReducer.navigationFilter.airportCode:state.filterReducer.filterVlues.port,
        fromDate:state.filterReducer.navigationFilter.fromDate?state.filterReducer.navigationFilter.fromDate:state.filterReducer.filterVlues.fromDate,
        toDate:state.filterReducer.navigationFilter.toDate?state.filterReducer.navigationFilter.toDate:state.filterReducer.filterVlues.toDate,
       // flightnumber:state.filterReducer.navigationFilter.flightNumber&& state.filterReducer.navigationFilter.carrierCode && state.filterReducer.navigationFilter.flightDate?{'flightNumber':state.filterReducer.navigationFilter.flightNumber,'carrierCode':state.filterReducer.navigationFilter.carrierCode,'flightDate':state.filterReducer.navigationFilter.flightDate}:state.filterReducer.filterVlues.flightnumber, 
        flightnumber:state.filterReducer.navigationFilter.flightnumber?state.filterReducer.navigationFilter.flightnumber:state.filterReducer.filterVlues.flightnumber,
        //flightnumber:state.filterReducer.navigationFilter.flightNumber?state.filterReducer.navigationFilter.flightNumber:state.filterReducer.filterVlues.flightnumber,
        //:state.filterReducer.navigationFilter.flightNumber?{'flightNumber':state.filterReducer.navigationFilter.flightNumber}:state.filterReducer.filterVlues.flightnumber
         fromTime:state.filterReducer.filterVlues.fromTime, toTime:state.filterReducer.filterVlues.toTime,
        operatingReference: state.filterReducer.filterVlues.operatingReference, mailFlightChecked: state.filterReducer.filterVlues.mailFlightChecked,
        mailstatus: state.filterReducer.filterVlues.mailstatus,pol: state.filterReducer.filterVlues.pol  },
            filter: state.filterReducer.summaryFilter?state.filterReducer.summaryFilter.filter:{},
     popOver: state.filterReducer.summaryFilter.popOver,
     popoverCount: state.filterReducer.summaryFilter.popoverCount,
     showPopOverFlag: state.commonReducer.showPopOverFlag,
     flightDate:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&&
     state.filterReducer.summaryFilter.filter.flightDate?state.filterReducer.summaryFilter.filter.flightDate:'',
  }
}

/*const getFilterValuesCount = (state)=>{
  let filterVlues=state.filterReducer.filterValues;
  let count=0;
  if(filterVlues!=null){
    for(let key in filterVlues) {
      let value = filterVlues[key];
      if(value!=null)
        count++;
    }
    //count=Object.keys(filterVlues).length;
    if(count>5)
      count=count-5;
    else
      count=0;
  }
  return count;
}*/
const mapDispatchToProps = (dispatch) => {  
  return {        
      listMail:()=>{
      dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST, displayPage: '1',pageSize:'100' } ,fromMainList:true}))

      },
      onChangeScreenMode:(mode)=>{
        dispatch(dispatchAction(changeScreenMode)(mode))
      },
      onclearFilterPanel:()=>{
        dispatch(dispatchAction(clearFilterPanel)(dispatch))
        dispatch(dispatchAction(screenLoad)())
      }     ,
            showPopover: () => {
     dispatch({type :constant.SHOW_POPOVER});
    },
  closePopover: () => {
     dispatch({type : constant.CLOSE_POPOVER});
      }     
  }
}



const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)

export default FilterContainer
