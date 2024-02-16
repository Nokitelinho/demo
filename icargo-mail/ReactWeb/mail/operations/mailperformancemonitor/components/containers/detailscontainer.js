import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { selectContainers } from '../../actions/commonaction';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { onApplyTableFilter, onClearTableFilter, updateSortVariables, onChangeTab, openHistoryPopup } from '../../actions/commonaction';
import {listMailbagDetails} from '../../actions/filteraction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';


const getTableResults = (state) => {
  if (!isEmpty(state.filterReducer.mailbagsdetails)) {
    switch (state.commonReducer.activeTab) {
      case 'MISSING_ORIGIN_SCAN': {
          if(state.filterReducer.mailbagsdetails.missingOriginScanMailbags){
             state.commonReducer.paginatedMailbagsMissOrgScan=state.filterReducer.mailbagsdetails.missingOriginScanMailbags;
             return state.filterReducer.mailbagsdetails.missingOriginScanMailbags.results;
         }
            else{
               state.commonReducer.paginatedMailbagsMissOrgScan={};
             return [];
            }
      }
      case 'MISSING_DESTINATION_SCAN':{ 
        if(state.filterReducer.mailbagsdetails.missingArrivalScanMailbags){
           state.commonReducer.paginatedMailbagsMissDestScan=state.filterReducer.mailbagsdetails.missingArrivalScanMailbags;
        return state.filterReducer.mailbagsdetails.missingArrivalScanMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsMissDestScan={};
             return [];
            }
      }
      case 'MISSING_BOTH_SCAN': {
        if(state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags){
           state.commonReducer.paginatedMailbagsMissBothScan=state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags;
        return state.filterReducer.mailbagsdetails.missingOriginAndArrivalScanMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsMissBothScan={};
             return [];
            }
      }
      case 'ON_TIME_MAILBAGS': {
        if(state.filterReducer.mailbagsdetails.onTimeMailbags){
           state.commonReducer.paginatedMailbagsOnTime=state.filterReducer.mailbagsdetails.onTimeMailbags;
        return state.filterReducer.mailbagsdetails.onTimeMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsOnTime={};
             return [];
            }
      }
      case 'DELAYED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.delayedMailbags){
             state.commonReducer.paginatedMailbagsNotOnTime=state.filterReducer.mailbagsdetails.delayedMailbags;
        return state.filterReducer.mailbagsdetails.delayedMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsNotOnTime={};
             return [];
            }
    }
      case 'RAISED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.raisedMailbags){
             state.commonReducer.paginatedMailbagsRaised=state.filterReducer.mailbagsdetails.raisedMailbags;
        return state.filterReducer.mailbagsdetails.raisedMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsRaised={};
             return [];
            }
    }
      case 'APPROVED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.approvedMailbags){
             state.commonReducer.paginatedMailbagsAccepted=state.filterReducer.mailbagsdetails.approvedMailbags;
        return state.filterReducer.mailbagsdetails.approvedMailbags.results;
        }
       else{
               state.commonReducer.paginatedMailbagsAccepted={};
             return [];
            }
    }
      case 'REJECTED_MAILBAGS': {
          if(state.filterReducer.mailbagsdetails.deniedMailbags){
             state.commonReducer.paginatedMailbagsDenied=state.filterReducer.mailbagsdetails.deniedMailbags;
        return state.filterReducer.mailbagsdetails.deniedMailbags.results;
    }
       else{
               state.commonReducer.paginatedMailbagsDenied={};
             return [];
    }
    }

  }
}
}

const getTableFilter = (state) => state.commonReducer.currentTableFilter ? state.commonReducer.currentTableFilter : {}

const getSortDetails = (state) => state.commonReducer.sort ? state.commonReducer.sort:{};

const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
  if (!isEmpty(filterValues)) {

    return results.filter((obj) => {
      const anotherObj = { ...obj, ...filterValues };
      if (JSON.stringify(obj) === JSON.stringify(anotherObj))
        return true;
      else
        return false
    })

  } else {
    return results;
  }

});


const getSortedDetails = createSelector([getSortDetails, getDetails], (sortDetails, mailbags) => {

  if (!isEmpty(sortDetails)) {
    const sortBy = sortDetails.sortBy;
    const sortorder = sortDetails.sortByItem;
    mailbags.sort((record1, record2) => {
      let sortVal = 0;
      let data1;
      let data2;
      data1 = record1[sortBy] && typeof record1[sortBy] === "object" ? record1[sortBy].systemValue : record1[sortBy];
      data2 = record2[sortBy] && typeof record2[sortBy] === "object" ? record2[sortBy].systemValue : record2[sortBy];

      if (data1 === null) {
        data1 = '';
      }
      if (data2 === null) {
        data2 = '';
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
    displayMode: state.filterReducer.displayMode,
    noData: state.filterReducer.noData,
    mailbagsdetails: getSortedDetails(state),
    selectedMailbagIndex: state.filterReducer.missingOriginScanMailbagsList,
    tableFilter: state.commonReducer.tableFilter,
    oneTimeValues: state.commonReducer.oneTimeValues,
    fromScreen: state.filterReducer.fromScreen,
    filterValues: state.filterReducer.filterValues,
    screenMode: state.filterReducer.screenMode,
    mailMonitorSummary: state.filterReducer.mailMonitorSummary,
    activeTab: state.commonReducer.activeTab,
    activeGraph: state.commonReducer.activeGraph,
    initialValues: { ...state.commonReducer.currentTableFilter },
    sort: getSortDetails(state),
    currentTableFilter: state.commonReducer.currentTableFilter,
    paginatedMailbags: state.commonReducer.paginatedMailbags
  }
}


const mapDispatchToProps = (dispatch) => {
  return {
    selectContainers: (data) => {
      dispatch(dispatchAction(selectContainers)(data))
    },
     changeTab: (currentTab) => {
       dispatch(dispatchAction(onChangeTab)(currentTab));
     },

    changeGraph: (currentGraph) => {
      dispatch({ type: 'CHANGE_GRAPH', currentGraph: currentGraph });
    },
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
    },
    onApplyTableFilter: () => {
      dispatch(dispatchAction(onApplyTableFilter)());
    },
    onClearTableFilter: () => {
      dispatch(dispatchAction(onClearTableFilter)());
    },
    exportToExcel: (displayPage, pageSize) => {
      return dispatch(asyncDispatch(listMailbagDetails)({ mode: "EXPORT", displayPage, pageSize }))
    },
    updateSortVariables: (sortBy, sortByItem) => {
      dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
    },
    openHistoryPopup: (data) => {
      dispatch(dispatchAction(openHistoryPopup)(data))
    },
    onlistMailbagDetails: (displayPage, pageSize) => {
      dispatch(asyncDispatch(listMailbagDetails)({ 'displayPage': displayPage, mode: 'LIST', 'pageSize': pageSize }))
    }
  }
}

const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer