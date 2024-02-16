import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util.js";
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';

import ConsigneeDetailsPanel from "../../panels/accordion/ConsigneeDetailsPanel.jsx";
import {deleteConsigneePOA,applyConsigneeDetailsFilter,sortedConsigneeList,onClearConsigneeFilter} from "../../../actions/commonaction";
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import {TOGGLE_CONSIGNEE_POP_UP,SAVE_SELECTED_CONSIGNEE_INDEX} from "../../../constants/constants";
import { filterConsigneeDetails } from "../../../utils/utils.js";

const getSortDetails=(state)=>state.commonReducer.sortConsignee;
const getConsigneeDetails=(state)=> state.commonReducer.consigneeDetails;
const getConsigneeFilter=(state)=>state.commonReducer.consigneeFilter;
const getFilteredDetails=createSelector([getConsigneeDetails,getConsigneeFilter],(consigneeDetails,consigneefilters)=>{
  let consigneeDetailsCopy=[];
  consigneeDetailsCopy=consigneeDetails;
  return filterConsigneeDetails(consigneeDetailsCopy,consigneefilters)
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
      consigneeDetails:getSortedAndFilteredBooking(state),
      showConsigneePopUp:state.commonReducer.showConsigneePopUp,
      selectedConsigneeIndex:state.commonReducer.selectedConsigneeIndex,
      showRemarksForConsignee:state.commonReducer.showRemarksForConsignee,
      disableDeleteButton:state.commonReducer.selectedConsigneeIndex.length?false:true
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    applyConsigneeDetailsFilter: () => {
      dispatch(dispatchAction(applyConsigneeDetailsFilter)());
    },
    onAdd: () => {
      dispatch({ type: TOGGLE_CONSIGNEE_POP_UP });
    },
    onDelete:()=>{
      dispatch(dispatchAction(deleteConsigneePOA)())
    },
    saveSelectedConsigneeIndex: (values)=>{
      dispatch({type:SAVE_SELECTED_CONSIGNEE_INDEX,data:values})
    },
    sortedList: (sortBy, sortOrder) => {
      dispatch(dispatchAction(sortedConsigneeList)({sortBy, sortOrder}));
    },
    onClearFilter: () => {
      dispatch(dispatchAction(onClearConsigneeFilter)())
    },
    openRemarkPopup:()=>{
      dispatch({ type: "OPEN_REMARK_POPUP_FOR_CONSIGNEE" });
    }
  };
};

const ConsigneeDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ConsigneeDetailsPanel)

export default ConsigneeDetailsContainer;