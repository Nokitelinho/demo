import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { addRow, deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { stat } from 'fs';
import RouteDetailsPanel from '../panels/RouteDetailsPanel.jsx';
import {deleteRows,saveSelectedFlightDetailIndex,saveUnselectedFlightDetailIndex,getTotalCount,populateFlightNumber} from '../../actions/commonaction';
import {Constants} from '../../constants/constants.js'

const mapStateToProps = (state) => {
  return {
    routeDetails:(state.form.routeDetailsTable && state.form.routeDetailsTable.values &&state.form.routeDetailsTable.values.routeDetailsTable&&state.form.routeDetailsTable.values.routeDetailsTable.length>0) ?
    state.form.routeDetailsTable.values.routeDetailsTable:[],
    newRowData:  {carrierCode:'',flightNumber:'',flightDate:'',truck:'',pol:'',pou:'',blockSpace:'',agreementType:''},
    oneTimeValues:state.commonReducer.oneTimeValues,
  }
}
const mapDispatchToProps = (dispatch) => {

  return {
    addRow: (newRowData) => {
        dispatch(addRow(Constants.ROUTEDETAIL_TABLE, newRowData))
    },
    onDeleteRow: () => {
      dispatch(dispatchAction(deleteRows)());
    },
     getTotalCount: () => {
      return dispatch(dispatchAction(getTotalCount)());
    },
     populateFlightNumber: (index) => {
       
        dispatch(dispatchAction(populateFlightNumber)(index));
    },
    selectedFlightDetailIndex: (index, action) => {
      if(action===Constants.SELECT){
        dispatch(dispatchAction(saveSelectedFlightDetailIndex)(index, action));
      }else{
        dispatch(dispatchAction(saveUnselectedFlightDetailIndex)(index, action));
      }

    }
  }
}
const RouteDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(RouteDetailsPanel)


export default RouteDetailsContainer