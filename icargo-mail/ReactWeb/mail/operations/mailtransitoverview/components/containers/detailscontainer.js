import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';

import DetailsPanel from '../panels/DetailsPanel.jsx';
import { listMailTransitOverview } from "../../actions/filteraction.js";
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { updateSortVariables } from '../../actions/commonaction.js';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import { onClearMailTransitFilter } from '../../actions/commonaction.js';
import { onApplyMailTransitFilter } from '../../actions/commonaction.js';
 
const getSortDetails = (state) => state.commonReducer.sort;
const getTableResults = (state) => state.filterReducer.mailTransitDetails && state.filterReducer.mailTransitDetails.results ? state.filterReducer.mailTransitDetails.results : [];
//const mailTransitDetails=(state)=> state.filterReducer.mailTransitDetails
const getTableFilter = (state) => state.commonReducer.tableFilter ? state.commonReducer.tableFilter : {}

const getMailbags = createSelector([getTableResults, getTableFilter], (results, filter) => results.filter((obj) => {
  
     const object={...obj};

  const anotherObj = { ...object, ...filter };
  return JSON.stringify(obj) === JSON.stringify(anotherObj)
}))

const getSortedDetails = createSelector([getSortDetails,getMailbags], (sortDetails, mailbags) => {

  if (sortDetails) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortByItem;

      // if(sortBy === 'mailBagDestination'){ //mailbagVolume is string, so comparing strings won't give correct result
      //   for(let i=0; i<mailbags.length; i++){
      //     mailbags[i] = {...mailbags[i], 'mailbagVolume':mailbags[i].volume.displayValue} //float data so correct sorting
      // }
      // }
      if(sortBy==='totalNoImportBags' ){
      for(let i=0; i<mailbags.length; i++){
        mailbags[i].totalNoImportBags=parseInt(mailbags[i].totalNoImportBags);
  }
      }
      if( sortBy==='totalWeightImportBags'){
        for(let i=0; i<mailbags.length; i++){
          mailbags[i].totalWeightImportBags=parseFloat(mailbags[i].totalWeightImportBags);
    }
        }
      
      
      //let mailbags =  mailbags&& mailbags.results? mailbags.results:[] ;
       mailbags.sort((record1, record2) => {
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
  return mailbags;
});
const mapStateToProps = (state) => {
    return {
      tableFilter:state.commonReducer.tableFilter,
      oneTimeValues:state.commonReducer.oneTimeValues,
      screenMode:state.filterReducer.screenMode,
       mailTransitDetails: getSortedDetails(state),
      mailTransitDetailslist: state.filterReducer.mailTransitDetails,
      sort: state.commonReducer.sort,
      initialValues: state.commonReducer.tableFilter ? state.commonReducer.tableFilter : {},
     //tableFilterInitialValues: state.commonReducer.tableFilter ? state.commonReducer.tableFilter : {},
      // initialValues:getDefaultValues(state)
      //  defaultValues:{mailBagDestination:state.commonReducer.tableFilter.mailBagDestination}
      // initialValues: {...getSortedDetails(state)}
      // initialValues: {...tablefilter}
    }}

    const mapDispatchToProps = (dispatch) => {
        return {
         
          getNewPage: (displayPage,pageSize) => {// for pagination,each time when we click next(getPage) control comes here
            dispatch(asyncDispatch(listMailTransitOverview)({ displayPage,pageSize, action: 'LIST' })).then(()=>  dispatch({ type: 'UPDATE_NEXT_PAGE_VALUE'}));
            
          },
            exportToExcel: (displayPage, pageSize) => {
              return dispatch(asyncDispatch(listMailTransitOverview)({ mode: "EXPORT", displayPage, pageSize }))
            },
            updateSortVariables: (sortBy, sortByItem) => {
              dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
            },
            onApplyMailTransitFilter:()=>{
              dispatch(dispatchAction(onApplyMailTransitFilter)())  
            },
            onClearMailTransitFilter:()=>{
              dispatch(dispatchAction(onClearMailTransitFilter)());
      
          }}}
          const DetailsContainer = connectContainer(
            mapStateToProps,
            mapDispatchToProps
         ) (DetailsPanel)
          
          export default DetailsContainer