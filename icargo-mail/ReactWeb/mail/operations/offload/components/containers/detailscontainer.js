import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { selectContainers, updateSortVariables, applyContainerFilter, onClearContainerFilter, applyMailFilter, onClearMailFilter ,onClearDSNFilter,applyDSNFilter} from '../../actions/commonaction';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { onListDetails, onListMailDetails ,onListDSNDetails} from '../../actions/filteraction';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
//import moment from 'moment';


const getTableResults = (state) => state.filterReducer.containerdetails && state.filterReducer.containerdetails.results ? state.filterReducer.containerdetails.results : [];
const getTableFilter = (state) => state.filterReducer.tableFilter ? state.filterReducer.tableFilter : {}
const getSortDetails = (state) => state.commonReducer.sort;


const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
  if (!isEmpty(filterValues)) {
        
    return results.filter((obj) => {  
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
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;
      containers.sort((record1, record2) => {
          let sortVal = 0;
          let data1;
          let data2;
          data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
          data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
       
        /*  if(sortBy==='containerNo'){
            if((moment.utc(data1).diff(moment.utc(data2)))>0){
                sortVal=1;
            }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
                sortVal=-1;
            }
        }else{*/

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
       // }
          if (sortorder === 'DSC') {
              sortVal = sortVal * -1;
          }
          return sortVal;
      });
  }
  return containers;
});
const mapStateToProps = (state) => {
  return {

    screenMode: state.filterReducer.screenMode,
    activeOffloadTab:state.commonReducer.activeOffloadTab,
    oneTimeValues: state.commonReducer.oneTimeValues,
    noData:state.filterReducer.noData,
    filterValues:state.filterReducer.filterValues,
    containerdetails: state.filterReducer.containerdetails,
    containers: state.filterReducer.containerdetails,
    tableFilter:state.filterReducer.tableFilter,
    containerslist: getSortedDetails(state),
    initialValues: { ...state.filterReducer.tableFilter },
    displayPage:state.filterReducer.displayPage,
    selectedMailbagIndex:state.filterReducer.selectedMailbagIndex,
    defaultPageSize:state.filterReducer.defaultPageSize

  }
}

const mapDispatchToProps = (dispatch, ownProps) => {
  return {
   
    onListDetails: (displayPage, defaultPageSize) => {
      dispatch(asyncDispatch(onListDetails)({ displayPage, defaultPageSize, mode: 'LIST' }))
    },
    onListMailDetails: (displayPage, defaultPageSize) => {
      dispatch(asyncDispatch(onListDetails)({ displayPage, defaultPageSize, mode: 'LIST' }))
    },
    onListDSNDetails:(displayPage, defaultPageSize) => {
      dispatch(asyncDispatch(onListDSNDetails)({displayPage, defaultPageSize,mode: 'LIST' }))
    },
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
  } ,
    exportToExcel: (displayPage, pageSize) => {
    return dispatch(asyncDispatch(onListDetails)({ mode: "EXPORT", displayPage, pageSize }))
     },
    exportToExcelMailBags: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(onListDetails)({ mode: "EXPORT", displayPage, pageSize }))
    },
    exportToExcelDSN:(displayPage, pageSize) =>{
      return dispatch(asyncDispatch(onListDSNDetails)({ mode: "EXPORT", displayPage, pageSize }))
    },
     updateSortVariables: (sortBy, sortByItem) => {
      dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
   },
   
  onApplyContainerFilter:()=>{
    dispatch(dispatchAction(applyContainerFilter)());
 },
 onClearContainerFilter:()=>{
    dispatch(dispatchAction(onClearContainerFilter)());
    },
    onApplyMailFilter: () => {
      dispatch(dispatchAction(applyMailFilter)());
    },
    onClearMailFilter: () => {
      dispatch(dispatchAction(onClearMailFilter)());
    },
    onApplyDSNFilter : () =>{
      dispatch(dispatchAction(applyDSNFilter)());
    },
    onClearDSNFilter: () =>{
      dispatch(dispatchAction(onClearDSNFilter)());
    },
    saveSelectedMailbagsIndex:(indexes)=> {
      dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
   },

  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer