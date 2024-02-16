import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FlightPanel from '../panels/FlightPanel.jsx';

import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { asyncDispatch,dispatchAction,dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import {listContainersinFlight,listmailbagsinContainers,performFlightScreenAction,clickPreadviceOK,onGenManifestPrint,saveIndexforGenerateManifest,printCNForFlight,onApplyFlightSort,onApplyCarrierSort, updateCarrierSortVariables} from '../../actions/flightlistactions.js'
import {listContainersinCarrier} from '../../actions/carrierlistaction.js'
import {listDetails,fetchFlightCapacityDetails,fetchFlightPreAdviceDetails,fetchFlightVolumeDetails} from '../../actions/filteraction.js'
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {openAllContainersPopup,openAllMailbagsPopup} from '../../actions/commonaction.js'
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import moment from 'moment';
import * as constant from '../../constants/constants';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';
//
const getFlightTableResults = (state) => state.filterReducer.flightDetails && state.filterReducer.flightDetails.results ? state.filterReducer.flightDetails.results : [];
const getCarrierTableResults = (state) => state.filterReducer.carrierDetails && state.filterReducer.carrierDetails.results ? state.filterReducer.carrierDetails.results : [];

const getFlightTableFilter = (state) => state.listFlightReducer.tableFilter ? state.listFlightReducer.tableFilter : {}
const getCarrierTableFilter = (state) => state.listFlightReducer.carrierFilter ? state.listFlightReducer.carrierFilter : {}

const getFlightSortDetails = (state) => state.listFlightReducer.sort;
const getCarrierSortDetails = (state) => state.listFlightReducer.carrierSort;
const getFilterValues = (state) => state.listFlightReducer.tableFilter;
const getDefaultValues = createSelector([getFilterValues], (filterValues) =>
  (
    {
      flightnumber:
      {
        carrierCode: filterValues.carrierCode ? filterValues.carrierCode : null,
        flightNumber: filterValues.flightNumber ? filterValues.flightNumber : null,
        flightDate: filterValues.flightDate ? filterValues.flightDate : null
      },
      upliftAirport: filterValues.upliftAirport ? filterValues.upliftAirport : null
    }
  ));
 
const getFlightDetails = createSelector([getFlightTableResults, getFlightTableFilter], (results, filter) => results.filter((obj) => {
  const anotherObj = { ...obj, ...filter };
  return JSON.stringify(obj) === JSON.stringify(anotherObj)
}))

const getCarrierDetails = createSelector([getCarrierTableResults, getCarrierTableFilter], (results, filter) => results.filter((obj) => {
  const anotherObj = { ...obj};
  return JSON.stringify(obj) === JSON.stringify(anotherObj)
}))

const getFlightSortedDetails = createSelector([getFlightSortDetails, getFlightDetails], (sortDetails, flights) => {

  if (!isEmpty(sortDetails)) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;
      flights = flights.sort((record1, record2) => {
          let sortVal = 0;
          let data1;
          let data2;
          data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
          data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
          if(sortBy==='flightDate'){
            if((moment.utc(data1).diff(moment.utc(data2)))>0){
                sortVal=1;
            }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
                sortVal=-1;
            }
        }else{
          if(data1===null){
             data1='';
          }    
          if(data2===null){
             data2='';
          }
          if (data1 > data2) {
              sortVal = 1;
          }
          if (data1 < data2) {
              sortVal = -1;
          }}
          if (sortorder === 'DSC') {
              sortVal = sortVal * -1;
          } 
          return sortVal;
       
      });
  }
  return [...flights];
});

const getCarrierSortedDetails = createSelector([getCarrierSortDetails, getCarrierDetails], (sortDetails, carriers) => {

  if (sortDetails) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;
      carriers= carriers.sort((record1, record2) => {
          let sortVal = 0;
          let data1;
          let data2;
          data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
          data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
       
          if(data1===null){
             data1='';
          }    
          if(data2===null){
             data2='';
          }
          if (data1 > data2) {
              sortVal = 1;
          }
          if (data1 < data2) {
              sortVal = -1;
          }
          if (sortorder === 'DSC') {
              sortVal = sortVal * -1;
          }
          return sortVal;
      });
  }
  return [...carriers];
});

const mapStateToProps = (state) => {
  return {
     
     displayMode:state.commonReducer.displayMode,   
     flightCarrierView:state.commonReducer.flightCarrierView,  
     flightlist:state.filterReducer.flightDetails? state.filterReducer.flightDetails.results :[],
     flights:state.filterReducer.flightDetails,//for pagination
     
	 
	 carrierlist:state.filterReducer.carrierDetails? state.filterReducer.carrierDetails.results :[],
     carriers:state.filterReducer.carrierDetails,//for pagination
     flightValidation: state.listFlightReducer.flightValidation,
     selectedFlight:state.listFlightReducer.selectedFlight,
     preadviceshow:state.listFlightReducer.preadviceshow,
     oneTimeValues: state.commonReducer.oneTimeValues,
     selectedMailflight: state.containerReducer.mailAcceptance,
     flightActionsEnabled: state.filterReducer.flightActionsEnabled,
     selectedFlights:state.listFlightReducer.selectedFlights,
     flightCapacityDetails:state.filterReducer.flightDetails,
     flightPreAdviceDetails:state.filterReducer.flightDetails,
     flightCarrierflag: state.commonReducer.flightCarrierflag,
     initialValues:state.listFlightReducer.tableFilter,
     initialCarrierValues:state.listFlightReducer.carrierFilter,
     stationWeightUnit:state.commonReducer.stationWeightUnit,
  }
}

const mapDispatchToProps = (dispatch,ownProps) => {
  return {
    //on pagination of flights
    onlistDetails: (displayPage, selectedPageSize,displayMode) => {
      console.log(",displayMode",displayMode);
      dispatch(asyncDispatch(listDetails)({'displayPage':displayPage, 'recordsPerPage': selectedPageSize})).then((response) => {
        if(!isEmpty(response.results[0]) ){
          if(response.results[0].flightCarrierFilter.assignTo === 'F'){
            dispatch(asyncDispatch(fetchFlightCapacityDetails)());
            dispatch(asyncDispatch(fetchFlightPreAdviceDetails)());
            if(isSubGroupEnabled('LUFTHANSA_SPECIFIC') === true){
              dispatch(asyncDispatch(fetchFlightVolumeDetails)());}
            }
		  }
        if( !(ownProps && ownProps.displayMode==='display')) {
        if(data.displayMode === 'multi'){
        dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then(() => {
          dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}));
        
      })    
     }
    }
      })
    },
  
    //export to excel
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(listDetails)({ mode: "EXPORT", displayPage, pageSize }))
    },
    //hovering over the flights
    selectFlights: (data) => {
        dispatch(asyncDispatch(listContainersinFlight)({flightIndex:data.flightIndex, containerDisplayPage:'1'})).then((response)=>{
        if(!isEmpty(response)) {
          if(response.results[0]&&response.results[0].mailAcceptance&&
              response.results[0].mailAcceptance.containerPageInfo&&response.results[0].mailAcceptance.containerPageInfo.results&&
                response.results[0].mailAcceptance.containerPageInfo.results.length>0){
                  dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:'0',mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
              }
            else{
              dispatch({type: constant.MAIL_CLEAR})
            }
        }
       })
    },
   saveSelectedFlights:(data)=> {
            dispatch({ type: constant.SAVE_SELECTED,data})
    },
 
   displayError: (message, target) => {
            dispatch(requestValidationError(message, target))
   },
  
   // ondoing flightactions
   performFlightAction:(data)=>{

    dispatch(asyncDispatch(performFlightScreenAction)(data)).then(()=>{
     if(data.type === constant.CLOSE_FLIGHT || data.type === constant.REOPEN_FLIGHT){
       dispatch(asyncDispatch(listDetails)()).then(() => {
         

         
        //IASCB-66602: need to relist flight after fligh closure or reopen
        dispatch(asyncDispatch(listContainersinFlight)({flightIndex:data.index, containerDisplayPage:'1'})).then((response)=>{
        if(!isEmpty(response)) {
          if(response.results[0]&&response.results[0].mailAcceptance&&
              response.results[0].mailAcceptance.containerPageInfo&&response.results[0].mailAcceptance.containerPageInfo.results&&
                response.results[0].mailAcceptance.containerPageInfo.results.length>0){
                  dispatch(asyncDispatch(listmailbagsinContainers)({containerIndex:'0',mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
              }
            else{
              dispatch({type: constant.MAIL_CLEAR})
            }
        }
      })

      })
    //IASCB-66602 ends
     }
    })
    

 },

   printCN:(data)=>{
    dispatch(dispatchPrint(printCNForFlight)(data));
   },
   
   saveIndexForMailManifes:(data)=>{
    dispatch(dispatchAction(saveIndexforGenerateManifest)(data));
   },

  onPreadviceOK:()=> {
        dispatch(dispatchAction(clickPreadviceOK)())
    },
  
  onClickPrint :() => {
    dispatch(dispatchPrint(onGenManifestPrint)());
   
  },

  updateSortVariables: (sortBy, sortByItem,displayMode) => {
    dispatch(asyncDispatch(onApplyFlightSort)({ sortBy, sortByItem })).then((response)=>{
      dispatch(asyncDispatch(fetchFlightCapacityDetails)()).then(()=>{
        if(displayMode==='multi') {
          dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then((response)=>{
            if(!isEmpty(response)) {
              dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
            }
           })
        }
        })
       
       })
  },

  openAllContainersPopup: () => {
    dispatch(dispatchAction(openAllContainersPopup)());
  },
  openAllMailbagsPopup: () => {
    dispatch(dispatchAction(openAllMailbagsPopup)());
  },
  
updateCarrierSortVariables: (sortBy, sortByItem,displayMode) => {
    dispatch(dispatchAction(onApplyCarrierSort)({ sortBy, sortByItem }));
        if(displayMode==='multi') {
          dispatch(asyncDispatch(listContainersinFlight)({flightIndex:'0', containerDisplayPage:'1'})).then((response)=>{
            if(!isEmpty(response)) {
              dispatch(asyncDispatch(listmailbagsinContainers)({mailbagsDisplayPage:'1',mailbagsDSNDisplayPage:'1'}))
            }
           })
        }
      //  }
       
       },

  // updateCarrierSortVariables: (sortBy, sortByItem) => {
  //   dispatch(dispatchAction(updateCarrierSortVariables)({ sortBy, sortByItem }))
  // },

  
  //Carrier actions

  // selectCarriers: (data) => {
  //        dispatch(asyncDispatch(listContainersinCarrier)(data)).then((response)=>{
  //      dispatch(asyncDispatch(listmailbagsinContainers)(response))
        
  //       })
  //    }
  }
}


const FlightTableContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FlightPanel)

export default FlightTableContainer