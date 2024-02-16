import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util.js";
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';

import BrokerDetailsPanel from "../../panels/accordion/BrokerDetailsPanel.jsx";
import { deleteBrokerPOA,applyBrokerDetailsFilter,sortedList,onClearFilter} from "../../../actions/commonaction";
import {TOGGLE_BROKER_POP_UP,SAVE_SELECTED_INDEX} from "../../../constants/constants";
import { filterBrokerDetails } from "../../../utils/utils.js";

const getSortDetails=(state)=>state.commonReducer.sort;
const getBrokerDetails=(state)=> state.commonReducer.brokerDetails;
const getBrokerFilter=(state)=>state.commonReducer.brokerFilter;

const getFilteredDetails=createSelector([getBrokerDetails,getBrokerFilter],(brokerDetails,brokerfilters)=>{
  let datas=[];
  datas=brokerDetails;
  return filterBrokerDetails(datas,brokerfilters)
});

const getSortedAndFilteredBooking= createSelector([getSortDetails,getFilteredDetails],(sortDetails,filteredData)=>
  {
    if (!isEmpty(sortDetails)) {
      const sortBy = sortDetails.sortBy;
      const sortorder = sortDetails.sortOrder;
      return [...filteredData].sort((record1, record2) => {
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
  else{return filteredData;}
  });
  
const mapStateToProps = (state) => {
  return {
      brokerDetails: getSortedAndFilteredBooking(state),
      showBroker:state.commonReducer.showBroker,
      selectedIndex:state.commonReducer.selectedIndex,
      showDeleteRemarks:state.commonReducer.showDeleteRemarks,
      disableDeleteButton:state.commonReducer.selectedIndex.length?false:true
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    applyBrokerDetailsFilter: () => {
      dispatch(dispatchAction(applyBrokerDetailsFilter)());
    },
    onAdd: () => {
      dispatch({ type: TOGGLE_BROKER_POP_UP });
    },
    saveSelectedIndex: (values) => {
      dispatch({type:SAVE_SELECTED_INDEX,data:values})
    },
    onDelete:()=>{
      dispatch(dispatchAction(deleteBrokerPOA)())
    },
    sortedList: (sortBy, sortOrder) => {
      dispatch(dispatchAction(sortedList)({sortBy, sortOrder}));
    },
    onClearFilter: () => {
      dispatch(dispatchAction(onClearFilter)())
    },
    openRemarkPopup:()=>{
      dispatch({ type: "OPEN_DELETE_REMARK_POPUP" });
    }
  };
};

const BrokerDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(BrokerDetailsPanel)

export default BrokerDetailsContainer;