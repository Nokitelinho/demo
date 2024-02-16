import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FlightListPanel from '../panels/FlightListPanel.jsx';
import {listDetails,fetchFlightCapacityDetails,fetchFlightPreAdviceDetails ,fetchFlightVolumeDetails} from '../../actions/filteraction.js'
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {openAllContainersPopup,openAllMailbagsPopup} from '../../actions/commonaction.js'
import {applyFlightFilter,onClearFlightFilter,applyCarrierFilter,onClearCarrierFilter,listContainersinFlight,listmailbagsinContainers} from '../../actions/flightlistactions.js'
import { toggleFlightPanel } from '../../actions/commonaction.js';
import * as constant from '../../constants/constants';
import {applyCarditFilter} from '../../actions/carditaction';
import { asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';


const mapStateToProps = (state) => {
  return {
     displayMode:state.commonReducer.displayMode,   
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    
    //On clickin x in flight details tabel
    toggleFlightPanel: () => {
     dispatch(dispatchAction(toggleFlightPanel)());
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
    },
    //On clicking flight table filter popup
     onApplyFlightFilter:(displayMode)=>{
       dispatch(asyncDispatch(applyFlightFilter)({displayPage:'1'})).then((response) => {
        if(!isEmpty(response.results[0]) ){
          if(response.results[0].flightCarrierFilter.assignTo === 'F')
            dispatch(asyncDispatch(fetchFlightCapacityDetails)()).then(()=>{
              if(displayMode==='multi') {
                dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then((response)=>{
                  if(!isEmpty(response)) {
                    dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
                  }
                 })
              }
              })
              
            }
      })
    },
    //On clicking carrier table filter popup
    onApplyCarrierFilter:(displayMode)=>{
      dispatch(asyncDispatch(applyCarrierFilter)({displayPage:'1'})).then((response)=> {
        if(response && !isEmpty(response.results[0]) ){
          if(displayMode==='multi') {
            dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then((response)=>{
              if(!isEmpty(response)) {
                dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
              }
             })
          }
        }
      });
   },
    //On clearing flight table filter popup
    onClearFlightFilter:()=>{
      // dispatch(asyncDispatch(listDetails)()).then((response) => {
      //   if(!isEmpty(response.results[0]) ){
      //     if(response.results[0].flightCarrierFilter.assignTo === 'F')
      //       dispatch(asyncDispatch(fetchFlightCapacityDetails)());
      //   }
      // })
      dispatch(dispatchAction(onClearFlightFilter)());
    },

    onClearCarrierFilter:()=>{
      dispatch(dispatchAction(onClearCarrierFilter)());
      dispatch(asyncDispatch(applyCarrierFilter)({displayPage:'1'})).then((response)=> {
        if(response && !isEmpty(response.results[0]) ){
          if(displayMode==='multi') {
            dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then((response)=>{
              if(!isEmpty(response)) {
                dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
              }
             })
          }
        }
      });
    },
   
    toggleCarditDisplay:()=>{
      dispatch({ type: constant.TOGGLE_CARDIT_VIEW });
      dispatch(asyncDispatch(applyCarditFilter)({displayPage:"1"}));
    },
    openAllContainersPopup: () => {
      dispatch(dispatchAction(openAllContainersPopup)());
    },
    openAllMailbagsPopup: () => {
      dispatch(dispatchAction(openAllMailbagsPopup)());
    },
    
  }
}


const FlightListContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FlightListPanel)

export default FlightListContainer