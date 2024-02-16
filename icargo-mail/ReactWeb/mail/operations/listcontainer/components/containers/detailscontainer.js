import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {contentId,selectContainers,selectContainersForOffload,validateContainerForReassign,unassignContainer,reassignContainerAction,validateContainerForTransfer,transferContainerAction,updateSortVariables,applyContainerFilter, onClearContainerFilter,selectContainersForRelease,checkCloseFlight,markUnmarkULDFullIndicator, printuldtag} from '../../actions/commonaction';
import {onInputChangeSearchmode, saveActualWeight, validateActualWeight, listFlightDetailsForReassignAndTransfer, validateForm, fetchFlightCapacityDetails, reassignContainerToFlightAction, clearReassignFlightPanel, transferContainerToFlightAction, clearTransferFlightPanel,releaseContainer } from '../../actions/commonaction';
import {onlistContainerDetails, navigateActions,validateContainerOffload,markAsHBAFlag} from '../../actions/filteraction';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import moment from 'moment';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import {displayError} from  '../../actions/commonaction';
const getTableResults = (state) => state.listcontainerreducer.containerDetails && state.listcontainerreducer.containerDetails.results ? state.listcontainerreducer.containerDetails.results : [];

const getTableFilter = (state) => state.listcontainerreducer.tableFilter ? state.listcontainerreducer.tableFilter : {}

const getSortDetails = (state) => state.listcontainerreducer.sort;

const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
if (!isEmpty(filterValues)) {
      
  return results.filter((obj) => {  
    const bags = JSON.stringify(obj.bags);
    const weight = JSON.stringify(obj.weight);
  // const actualWeight = JSON.stringify(obj.actualWeight);
   const actualWeight = obj.actualWeightMeasure?obj.actualWeightMeasure.formattedDisplayValue:'';
   const assignedOn=obj.assignedOn.substring(0,11);
    obj = {...obj,bags,weight,actualWeight,assignedOn};
     const anotherObj = { ...obj, ...filterValues}; 
     if(JSON.stringify(obj)===JSON.stringify(anotherObj))
          return true;
     else 
        return false 
    } )
	

  } else {
      return results;
  }

});



const getSortedDetails = createSelector([getSortDetails, getDetails], (sortDetails, containers) => {

  if (sortDetails) {
      let sortBy = sortDetails.sortBy;
      if(sortBy ==='assignedOn') {
        sortBy = 'assignedOnInMilliSec';
      }
      const sortorder = sortDetails.sortByItem;
    containers = containers.sort((record1, record2) => {
        let sortVal = 0;
        let data1;
        let data2;
        data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
        data2 = record2[sortBy] && typeof record2[sortBy] === "object"?record2[sortBy].systemValue:record2[sortBy];
        
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
  return [...containers];
});


const mapStateToProps= (state) =>{
  return {
      screenMode:state.listcontainerreducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      containerDetails:state.listcontainerreducer.containerDetails,
      estimatedChargePrivilage:state.listcontainerreducer.estimatedChargePrivilage,
      containerDetailsList:getSortedDetails(state),
      selectedContainers:state.listcontainerreducer.selectedContainers,
      showTransferFlag:state.containeractionreducer.showTransferFlag,
      oneTimeValues: state.commonReducer.oneTimeValues,
      assignedTo:state.listcontainerreducer.assignedTo,
      isValidationSuccess:state.commonReducer.isValidationSuccess,
      showReassignFlag:state.containeractionreducer.showReassignFlag,
      currentDate: getCurrentDate(),
      initialValues:{...getSortedDetails(state)},
      tableValues: state.form.containertable?state.form.containertable.values.containertable:[],
	  tableFilterInitialValues: state.listcontainerreducer.tableFilter ? state.listcontainerreducer.tableFilter : {},
      containerdetailForm:(state.form&&state.form.containerdetail&&state.form.containerdetail.values)?state.form.containerdetail.values:null,
      scanDate:state.containeractionreducer.scanDate,
      scanTime:state.containeractionreducer.scanTime, 
      uldTobarrow:state.containeractionreducer.uldTobarrow,
      uldToBarrowAllow:state.commonReducer.uldToBarrowAllow,
      reassignFromDate:state.containeractionreducer.reassignFromDate,
      reassignToDate:state.containeractionreducer.reassignToDate,
      destination:state.containeractionreducer.selectedContainer&&state.containeractionreducer.selectedContainer.length>0?state.containeractionreducer.selectedContainer[0].destination:'',
      finalDestination:state.containeractionreducer.selectedContainer&&state.containeractionreducer.selectedContainer.length>0?state.containeractionreducer.selectedContainer[0].finalDestination:'',
      flightDetails:state.containeractionreducer.flightDetails ?state.containeractionreducer.flightDetails.results:[],
      flightDetailsPage:state.containeractionreducer.flightDetails ?state.containeractionreducer.flightDetails:{},
      flightCarrierCode: state.containeractionreducer.flightCarrierCode,
      showReassignFlightFlag: state.containeractionreducer.showReassignFlightFlag,
      showTransferFlightFlag: state.containeractionreducer.showTransferFlightFlag,
      companyCode:state.listcontainerreducer.companyCode,
      actualWeight:state.containeractionreducer.selectedContainer&&state.containeractionreducer.selectedContainer.length>0&&state.containeractionreducer.multipleFlag==false?state.containeractionreducer.selectedContainer[0].actualWeight:'',
      contentID:state.containeractionreducer.selectedContainer&&state.containeractionreducer.selectedContainer.length>0&&state.containeractionreducer.multipleFlag==false?state.containeractionreducer.selectedContainer[0].contentId:'',
      multipleFlag:state.containeractionreducer.multipleFlag,
      carrierCode: state.commonReducer.carrierCode,
      containerDetailsForPopUp:{'containerDetails': state.containeractionreducer.containerDetails ? state.containeractionreducer.containerDetails : [],},
      containerDetailsForReassignPopUp:{'containerDetails': state.containeractionreducer.selectedContainer ? state.containeractionreducer.selectedContainer : [],},
      destinationForTransferPopup:state.containeractionreducer.containerDetails&&state.containeractionreducer.containerDetails.length>0?state.containeractionreducer.containerDetails[0].destination:'',
      flightDetailsFromInbound:state.commonReducer.flightDetailsFromInbound?state.commonReducer.flightDetailsFromInbound:{}
     // toFlightDetails:{toFlightDetails}
  }
}

const mapDispatchToProps = (dispatch) => {
  return {	
    displayError:(message, target)=>{
      dispatch(requestValidationError(message, ''));
      //dispatch(dispatchAction(displayError)({message:message,target: target}))
    },
   selectContainers: (data) => {
           dispatch(dispatchAction(selectContainers)(data))
      },
    validateContainerForReassign:(data,flag, mode)=>{
      dispatch(asyncDispatch(validateContainerForReassign)({data,flag, mode}))
      .then(()=>{
        if(mode!=='CARRIER'){
        dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'reassign'})) .then((response) => {
          if(!isEmpty(response.results[0]) ){
            if(response.results[0].flightCarrierFilter.assignTo === 'F')
              dispatch(asyncDispatch(fetchFlightCapacityDetails)());
          }
        });
      }
      }
    )
    },
    markAsHBAFlag:(data,mode) => {
     dispatch(asyncDispatch(markAsHBAFlag)({data,mode}))
},
      reassignContainerAction:()=>{
      dispatch(asyncDispatch(reassignContainerAction)()).then(() =>{
        if( response &&response.status!=='security'){
        dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1','pageSize':'10',action:'LIST'}))
        }} );
      },   
      unassignContainer:(data)=>{
          dispatch(asyncDispatch(unassignContainer)(data)).then(() =>{
          dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1','pageSize':'10',action:'LIST'}))
         } )
       },
      validateContainerForTransfer:(index, mode)=>{
          dispatch(asyncDispatch(validateContainerForTransfer)({index, mode})).then(()=>{
            dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'transfer'})) .then((response) => {
              if(!isEmpty(response.results[0]) ){
                if(response.results[0].flightCarrierFilter.assignTo === 'F')
                  dispatch(asyncDispatch(fetchFlightCapacityDetails)());
              }
            });
          })
       },
       validateContainerForTransferToCarrier:(index, mode)=>{
        dispatch(asyncDispatch(validateContainerForTransfer)({index, mode}))
     },
      transferContainerAction:() => {
          dispatch(asyncDispatch(transferContainerAction)()).then(() =>{
          if( response &&response.status!=='security'){
          dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1','pageSize':'10',action:'LIST'}))
         } }) 
       },
	   markUnmarkULDFullIndicator:(index, mode) => {
	dispatch(asyncDispatch(checkCloseFlight)({index:index,mode:mode})).then(() => {
   dispatch(asyncDispatch(markUnmarkULDFullIndicator)({index:index,mode:mode})).then(() =>{
       dispatch(asyncDispatch(onlistContainerDetails)({action:'LIST'}))
	})
   })
}, printuldtag:(index, mode)=>{  
    dispatch(dispatchAction(printuldtag)({index:index,mode:mode}))
    
},
      viewMailbag:(data) => {
          dispatch(dispatchAction(navigateActions)(data))
       },
       onApplyContainerFilter:()=>{
        dispatch(dispatchAction(applyContainerFilter)());
     },
     onClearContainerFilter:()=>{
        dispatch(dispatchAction(onClearContainerFilter)());
     },
       onInputChangeSearchmode:() => {
        dispatch(dispatchAction(onInputChangeSearchmode)());
      },
      exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(onlistContainerDetails)({ mode: "EXPORT", displayPage, pageSize }))
       },
    updateSortVariables: (sortBy, sortByItem) => {
      dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
   },
      onClose:() => {
          dispatch({type : 'CLOSE'})
        dispatch({type :'CONTAINER_CONVERSION',uldToBarrow:false,barrowToUld:false})
      //navigateToScreen('home.jsp',{});
       },
      onlistContainerDetails: (displayPage,pageSize) => {
          dispatch(asyncDispatch(onlistContainerDetails)({displayPage,pageSize,action:'LIST'}))
       },
      offloadContainer:() => {
        dispatch(asyncDispatch(validateContainerOffload)()).then((response) =>{
            dispatch(asyncDispatch(navigateActions)({fromButton:'FROM_OFFLOAD'}))
        })},
        selectContainersForOffload:(data)=>{
            dispatch(dispatchAction(selectContainersForOffload)(data))
        },  
        selectContainersForRelease:(data)=>{
          dispatch(dispatchAction(selectContainersForRelease)(data))
        },
       contentId:(data)=>{
        dispatch(asyncDispatch(contentId)(data)).then(() =>{
        dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1',action:'LIST'}))
       } )
     },
     saveActualWeight:(rowIndex, actualWeight) => {    
        let validObject = validateActualWeight(actualWeight);
            if(!validObject.valid){
                dispatch(requestWarning([{code:"mail.operations.container.actualweight", description:"Do you wish to delete the actual weight of the ULD?"}],{functionRecord:saveActualWeight, args:{rowIndex,actualWeight:'0'}}));
              }
              else if(!validObject.numeric){
                dispatch(requestValidationError('Please enter Numerical Values for Actual Weight', ''));   
                return{};
              }
              else {
                dispatch(asyncDispatch(saveActualWeight)({rowIndex,actualWeight})).then(() =>{
                  dispatch(asyncDispatch(onlistContainerDetails)({action:'LIST'}))
                     } )
              }
        
      },
      saveSelectedContainersIndex:(indexes)=> {
        dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
      },  
      listFlightDetailsForReassign: (values) => {
        dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'reassign'})) .then((response) => {
          if(!isEmpty(response.results[0]) ){
            if(response.results[0].flightCarrierFilter.assignTo === 'F')
              dispatch(asyncDispatch(fetchFlightCapacityDetails)());
          }
        });
        },
        getNewPage: (displayPage) => {
          dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':displayPage, panel:'reassign'})) .then((response) => {
            if(!isEmpty(response.results[0]) ){
              if(response.results[0].flightCarrierFilter.assignTo === 'F')
                dispatch(asyncDispatch(fetchFlightCapacityDetails)());
            }
          });
          },
          listFlightDetailsForTransfer: (values) => {
            dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':'1',mode:'display', panel:'transfer'})) .then((response) => {
              if(!isEmpty(response.results[0]) ){
                if(response.results[0].flightCarrierFilter.assignTo === 'F')
                  dispatch(asyncDispatch(fetchFlightCapacityDetails)());
              }
            });
            },
            getNewPageTransferPanel: (displayPage) => {
              dispatch(asyncDispatch(listFlightDetailsForReassignAndTransfer)({'displayPage':displayPage, panel:'transfer'})) .then((response) => {
                if(!isEmpty(response.results[0]) ){
                  if(response.results[0].flightCarrierFilter.assignTo === 'F')
                    dispatch(asyncDispatch(fetchFlightCapacityDetails)());
                }
              });
              },
          saveSelectedFlightsIndex:(indexes)=> {
            dispatch({ type: 'SAVE_SELECTED_FLIGHT_INDEX',indexes})
          },
          reassignContainerToFlightAction:(data)=>{
            dispatch(asyncDispatch(reassignContainerToFlightAction)({data,warningFlag:false})).then(()=>{
              if( response &&response.status !=null &&response.status!=='security'){
              dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':1,'pageSize':10,action:'LIST'}))            
            }})
            },
            transferContainerToFlightAction:(data)=>{
              dispatch(asyncDispatch(transferContainerToFlightAction)({data,warningFlag:false})).then(()=>{
                dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':1,'pageSize':10,action:'LIST'}))            
              })
              },
            clearReassignFlightPanel:()=>{
              dispatch(dispatchAction(clearReassignFlightPanel)())
            },
            clearTransferFlightPanel:()=>{
              dispatch(dispatchAction(clearTransferFlightPanel)())
            }, 
            releaseContainer:(data)=>{
              dispatch(asyncDispatch(releaseContainer)(data)).then(() =>{
              dispatch(asyncDispatch(onlistContainerDetails)({'displayPage':'1','pageSize':'10',action:'LIST'}))
             } )
           },
   }
}
  
  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)


export default DetailsContainer