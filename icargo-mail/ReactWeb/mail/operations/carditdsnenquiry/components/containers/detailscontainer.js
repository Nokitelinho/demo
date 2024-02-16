import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {listCarditDsnEnquiry} from '../../actions/filteraction';
import {updateSortVariables,onApplyDsnFilter,onClearDsnFilter,saveSelectedIndexes} from '../../actions/detailsaction';
import {attachAll,fetchMailbagsForDsns,detachAll,attachAwbWhenNotSelected} from '../../actions/commonaction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {Constants,Errors,Key} from '../../constants/constants.js'
import moment from 'moment';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



const mapStateToProps= (state) =>{
  return {
      screenMode:state.filterReducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      dsnDetails:{...state.filterReducer.dsnDetails,results:getSortedDetails(state)},
      totalPieces:state.filterReducer.totalPieces,
      totalWeight:state.filterReducer.totalWeight,
      systemParameters:state.commonReducer.systemParameters,
      dsnFilterValues:state.detailsReducer.dsnFilter,
      oneTimeValues: state.commonReducer.oneTimeValues,
      initialValues:{},
      selectedIndexes: state.detailsReducer.selectedIndexes,
      mailCountFromSyspar:state.filterReducer.mailCountFromSyspar,
  }
}

const mapDispatchToProps = (dispatch,ownProps) => {
  return {
    onlistCarditDsnEnquiry: (displayPage,pageSize) => {
        dispatch(asyncDispatch(listCarditDsnEnquiry)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST}))
    },
    onApplyDsnFilter:(value)=>{
        dispatch(dispatchAction(onApplyDsnFilter)());
    },
    onClearDsnFilter:()=>{
        dispatch(dispatchAction(onClearDsnFilter)());
    },
    exportToExcel: (displayPage, pageSize) => {
        return dispatch(asyncDispatch(listCarditDsnEnquiry)({ mode: Constants.EXPORT, displayPage, pageSize }))
    },
    updateSortVariables: (sortBy, sortByItem) => {
        dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
    },
    saveSelectedIndexes: (selectedIndexes) => {
        dispatch(dispatchAction(saveSelectedIndexes)(selectedIndexes))
    },
    onRowOperation: (rowDataPassed) => {
        if(rowDataPassed.operation===Constants.ATTACH){
            dispatch(asyncDispatch(fetchMailbagsForDsns)({index:rowDataPassed.index}))
                    .then((response)=>dispatch(asyncDispatch(attachAll)(response)))
                        .then(()=> dispatch(requestWarning([{code:Key.RELIST_WARN,description:Errors.RELIST_WARN}],
                            {functionRecord:listCarditDsnEnquiry})));
        }
        if(rowDataPassed.operation===Constants.DETACH){
            dispatch(asyncDispatch(fetchMailbagsForDsns)({index:rowDataPassed.index}))
                    .then((response)=>dispatch(asyncDispatch(detachAll)(response)))
                        .then(()=>dispatch(asyncDispatch(listCarditDsnEnquiry)({mode:Constants.LIST})));
        }
    },
    attachAll: () => {
        dispatch(asyncDispatch(fetchMailbagsForDsns)())
                .then((response)=>dispatch(asyncDispatch(attachAll)(response)))
                    .then(()=> dispatch(requestWarning([{code:Key.RELIST_WARN,description:Errors.RELIST_WARN}],
                        {functionRecord:listCarditDsnEnquiry})));
    },
    detachAll: () => {
        dispatch(asyncDispatch(fetchMailbagsForDsns)())
                .then((response)=>dispatch(asyncDispatch(detachAll)(response)))
                    .then(()=>dispatch(asyncDispatch(listCarditDsnEnquiry)({mode:Constants.LIST})))
    },

 
}
  
}


/**
 * Methods for filtering and sorting.
 * PLEASE NOTE: Any changes made here
 * should be done inside the similar 
 * methods present in commonaction also.
 */
const getTableResults = (state) => state.filterReducer.dsnDetails 
                            && state.filterReducer.dsnDetails.results ? state.filterReducer.dsnDetails.results : [];
const getTableFilter = (state) => state.detailsReducer.dsnFilter ? state.detailsReducer.dsnFilter : {}
const getSortDetails = (state) => state.detailsReducer.sort;

const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
              if (!isEmpty(filterValues)) {  
                return results.filter((obj) => { 
                  obj.year=obj.year.toString();
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
        }
        else{
            if(sortBy==='weight'){
                data1=parseFloat(data1);
                data2=parseFloat(data2);
            }
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



  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer