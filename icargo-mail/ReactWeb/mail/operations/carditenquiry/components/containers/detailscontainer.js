import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {listCarditEnquiry,applyCarditFilter,onClearFlightFilter,updateSortVariables,performCarditAction,saveMaibagResdit,performCarditResditSendAction} from '../../actions/filteraction';
import {navigateActions} from '../../actions/commonaction';
import { asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import moment from 'moment';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';


const getTableResults = (state) => state.filterReducer.mailbagsdetails && state.filterReducer.mailbagsdetails.results ? state.filterReducer.mailbagsdetails.results : [];

const getTableFilter = (state) => state.filterReducer.tableFilter ? state.filterReducer.tableFilter : {}

const getSortDetails = (state) => state.filterReducer.sort;

const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
if (!isEmpty(filterValues)) {
    let {accepted} = filterValues;

      
      if(accepted) {
     let status = (accepted =='ACP')?'Y':'N';
     const mailStatus={};
     mailStatus.accepted=status;
     filterValues={...filterValues,...mailStatus}
      }
      
    
  return results.filter((obj) => { 
     obj.year=obj.year.toString(); 
     if(obj.reqDeliveryTime!=null)
     obj.reqDeliveryTime=obj.reqDeliveryTime.substring(0, 5);
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

const getDefaultValues = createSelector([getTableFilter], (filterValues) =>
  {
    let  flightnumber={};
   if(filterValues.carrierCode||filterValues.flightNumber||filterValues.flightDate){ flightnumber={
        carrierCode: filterValues.carrierCode ? filterValues.carrierCode : null,
        flightNumber: filterValues.flightNumber ? filterValues.flightNumber : null,
        flightDate: filterValues.flightDate ? filterValues.flightDate : null
      }
    }
   delete filterValues.carrierCode
   delete filterValues.flightNumber
   delete filterValues.flightDate
  return filterValues={flightnumber,...filterValues}
  });
  
const getSortedDetails = createSelector([getSortDetails, getDetails], (sortDetails, mailbags) => {

  if (sortDetails) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;
      mailbags.sort((record1, record2) => {
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
          }
        }
          if (sortorder === 'DSC') {
              sortVal = sortVal * -1;
          }
          return sortVal;
      });
  }
  return mailbags;
});
const mapStateToProps= (state) =>{
  return {
      screenMode:state.filterReducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      noData:state.filterReducer.noData,
      mailbags:state.filterReducer.mailbagsdetails,
      mailbagslist:getSortedDetails(state),
      totalWeight:state.filterReducer.totalWeight,
      totalPieces:state.filterReducer.totalPieces,
      selectedMailbagIndex:state.filterReducer.selectedMailbagIndex,
      totalPieces:state.filterReducer.totalPieces,
      totalWeight:state.filterReducer.totalWeight,
      systemParameters:state.commonReducer.systemParameters,
      tableFilter:state.filterReducer.tableFilter,
      oneTimeValues: state.commonReducer.oneTimeValues,
      selectedMailbagsId:state.filterReducer.selectedMailbagIds,
      // initialValues:{...state.filterReducer.tableFilter}
      initialValues:getDefaultValues(state)
      
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
     onlistCarditEnquiry: (displayPage,pageSize) => {
     dispatch(asyncDispatch(listCarditEnquiry)({displayPage,pageSize,mode:'LIST'}))
    },
    onApplyFlightFilter:()=>{
       dispatch(dispatchAction(applyCarditFilter)());
    },
    onClearFlightFilter:()=>{
       dispatch(dispatchAction(onClearFlightFilter)());
    },
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(listCarditEnquiry)({ mode: "EXPORT", displayPage, pageSize }))
    },
    updateSortVariables: (sortBy, sortByItem) => {
      dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
   },
   
   saveSelectedMailbagsIndex:(indexes,mailbagId)=> {
     dispatch({ type: 'SAVE_SELECTED_INDEX',indexes,mailbagId});
   },
   carditAction:(data)=>{
    dispatch(asyncDispatch(performCarditAction)({index:data.index,mode:data.actionName})).then((response) => {
      dispatch(dispatchAction(performCarditResditSendAction)({mode:data.actionName}));
       })
  },
  carditMultipleSelectionAction:(data)=>{
    dispatch(asyncDispatch(performCarditAction)({mode:data.actionName})).then((response) => {
      dispatch(dispatchAction(performCarditResditSendAction)({mode:data.actionName}));
       })
   },
  bulkResditSend:(data)=>{
    dispatch(asyncDispatch(performCarditAction)({mode:data.actionName,selectedMailbags:data.selectedMailbags,selectedResdits:data.selectedResdits,selectedResditVersion:data.selectedResditVersion})).then((response) => {
    dispatch(dispatchAction(performCarditResditSendAction)({mode:data.actionName}));
     })
     },
  displayError: (message, target) => {
    dispatch(requestValidationError(message, target))
  },
  saveResditMailbagData:(mailbagData,maildetails,selectedMailbag)=>{
    dispatch(dispatchAction(saveMaibagResdit)({maildata:mailbagData,mailDetails:maildetails,selectedMailbag:selectedMailbag}));
  },
  navigatetoViewCondoc:(data) => {
    dispatch(dispatchAction(navigateActions)(data))
 },
 }
  
}
  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer