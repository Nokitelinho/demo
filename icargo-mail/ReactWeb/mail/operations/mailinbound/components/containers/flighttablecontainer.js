import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FlightDetailTable from '../panels/FlightDetailstable.jsx';
import { asyncDispatch,dispatchAction,dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';

import {
  performFlightScreenCommonAction, performFlightScreenAction,
  onApplyFlightFilter, onClearFlightFilter, onRowSelection, generateManifest ,printCN46
} from '../../actions/flightaction';
import {listMailbagAndDsnsOnContainerList,onContainerRowSelection} from '../../actions/containeraction';
import { listMailInbound } from '../../actions/filteraction';
import { openAllContainersPopup,openAllMailbagsPopup,flightAllSelect,onApplyFlightSort } from '../../actions/flightaction';
import * as constant from '../../constants/constants';
import {displayError} from  '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {    
    displayMode:state.commonReducer.displayMode,
    //flightData:getDetails(state),
    flightData: state.listFlightreducer.flightData ? state.listFlightreducer.flightData : [],
    discrepancyData:state.listFlightreducer?state.listFlightreducer.discrepancyData:{},
    showDiscrepancy:state.listFlightreducer?state.listFlightreducer.showDiscrepancy:false,
    initialValues:state.listFlightreducer.filterValues,
    indexDetails:state.listFlightreducer.indexDetails?state.listFlightreducer.indexDetails:{},
    defaultPageSize:state.listFlightreducer.flightData?state.listFlightreducer.flightData.defaultPageSize:'10',
    flightActionsEnabled: state.commonReducer.loggedAirport === state.filterReducer.filterVlues.port,
    flightDetails: state.listFlightreducer.flightDetails,
    valildationforimporthandling:state.commonReducer.valildationforimporthandling?state.commonReducer.valildationforimporthandling:'N'
  }
}

const mapDispatchToProps = (dispatch) => {  
  return {        
    displayError:(message, target)=>{
      dispatch(dispatchAction(displayError)({message:message,target: target}))
    },
      onApplyFlightFilter:(filter)=>{
         dispatch(dispatchAction(onApplyFlightFilter)(filter))
              //dispatch(dispatchAction(onContainerRowSelection)())
      },
      onClearFlightFilter:(filter)=>{
         dispatch(dispatchAction(onClearFlightFilter)(filter))
      },  
      flightCommonAction:(data)=>{
        dispatch(dispatchAction(performFlightScreenCommonAction)(data))
      },  
      indFlightAction:(data)=>{
        
         dispatch(asyncDispatch(performFlightScreenAction)(data)).then(()=>{
        if (data.displayMode === 'multi') {
          dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST_MULTI } })).then(() => {
            dispatch(asyncDispatch(onRowSelection)({rowData:data.flightDetails, fromTableRowClick: true })).then((response) => {
              dispatch(dispatchAction(listMailbagAndDsnsOnContainerList)(response))
            })
          })
        }
        else {
          dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST } })).then(() => {
          })
        }
      })
          dispatch(dispatchAction(onClearFlightFilter)(data))
      }, 
      commonFlightAction:(data)=>{
        dispatch(asyncDispatch(performFlightScreenAction)(data)).then(()=>{
        if (data.displayMode === 'multi') {
          dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST_MULTI } })).then(() => {
            dispatch(asyncDispatch(onRowSelection)({rowData:data.flightDetails, fromTableRowClick: true })).then((response) => {
              dispatch(dispatchAction(listMailbagAndDsnsOnContainerList)(response))
            })
          })
        }
        else {
          dispatch(asyncDispatch(listMailInbound)({ 'action': { type: constant.LIST } })).then(() => {
          })
        }
      })
         dispatch(dispatchAction(onClearFlightFilter)(data))
      },        
      onRowSelection:(data)=>{
        dispatch(asyncDispatch(onRowSelection)(data)).then((response)=>{
        dispatch(dispatchAction(listMailbagAndDsnsOnContainerList)(response))
      })
      }, 
      listMailInbound:(data,pageSize)=>{
          dispatch(asyncDispatch(listMailInbound)({'action':{type:constant.LIST,displayPage:data,pageSize:pageSize}}))
      },
      generateManifest:(data)=>{
          dispatch(dispatchPrint(generateManifest)(data));
      },
      closeDiscrepancy:()=>{
        dispatch({type:constant.CLOSE_DISCREPANCY})
      },
      openAllContainersPopup: () => {
      dispatch(dispatchAction(openAllContainersPopup)());
      },
      openAllMailbagsPopup: () => {
      dispatch(dispatchAction(openAllMailbagsPopup)());
      },
      exportToExcel: (displayPage, pageSize) => {
        return dispatch(asyncDispatch(listMailInbound)({'action':{type:'EXPORT',displayPage,pageSize}}))
       },
      clearAllSelect:()=>{
         dispatch({type:constant.CLEAR_FLIGHT_SELECT_ALL});
      },
      flightAllSelect:(data)=>{
         dispatch(dispatchAction(flightAllSelect)({'checked':data}))
      },
      updateSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(onApplyFlightSort)({ sortBy, sortByItem }))
          dispatch(dispatchAction(onContainerRowSelection)())
      },
      printCN46:(data)=>{
        dispatch(dispatchPrint(printCN46)(data));
      },
    
  }
}

const FlightTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FlightDetailTable)

export default FlightTableContainer

