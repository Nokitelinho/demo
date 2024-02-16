import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import BillingEntriesPanel from '../panels/BillingEntriesPanel.jsx';
import {onExpandClick} from '../../actions/commonaction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {listBillingDetails,updateSortVariables,allMailbagIconAction} from '../../actions/filteraction';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';

 const getTableResults = (state) => state.filterReducer.mailbagsdetails && state.filterReducer.mailbagsdetails.results ? state.filterReducer.mailbagsdetails.results : [];

 const getTableFilter = (state) => state.filterReducer.tableFilter ? state.filterReducer.tableFilter : {}

 const getSortDetails = (state) => state.filterReducer.sort;

 const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues)=> results.filter((obj) => {
  const weight = obj.weight.formattedDisplayValue;
  const actualWeight = JSON.stringify(obj.actualWeight);
  const objDup={...obj};
  const carrier=objDup.carrierCode;
  const flightNum=objDup.flightNumber;
  const carrierCode=carrier +flightNum;
  obj = {...objDup,carrierCode,weight,actualWeight};

  const anotherObj = { ...obj, ...filterValues };
  return JSON.stringify(obj) === JSON.stringify(anotherObj)
}))


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
   return [...mailbags];
 });

const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    noData: state.filterReducer.noData,
    mailbagsdetails : state.filterReducer.mailbagsdetails,
    mailbagList: getSortedDetails(state),
    screenMode: state.filterReducer.screenMode,
    tableFilter: state.filterReducer.tableFilter,
    selectedMailbagIndex:state.filterReducer.selectedMailbagIndex,
    containerRatingPAList:state.commonReducer.containerRatingPAList
  }
}
const mapDispatchToProps = (dispatch) => {
 return {
    saveSelectedMailbagsIndex:(indexes)=> {
     dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})  
    },
    onlistBillingDetails: (displayPage,pageSize) => {
    // dispatch(asyncDispatch(listBillingDetails)({displayPage,pageSize,mode:'LIST',fromBillingList:'Y'}))
     dispatch(asyncDispatch(listBillingDetails)({displayPage,pageSize,mode:'LIST'}))
    },
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(listBillingDetails)({ mode: "EXPORT", displayPage, pageSize }))
    },
    updateSortVariables: (sortBy, sortByItem) => {
       dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
     },
    allMailbagIconAction:()=>{
      dispatch(asyncDispatch(allMailbagIconAction)())
     },
    openCompareRow:() =>{
      dispatch({type:'OPEN_COMPAREROW_POPUP'})
     }
    }
}

const BillingEntriesContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(BillingEntriesPanel)


export default BillingEntriesContainer