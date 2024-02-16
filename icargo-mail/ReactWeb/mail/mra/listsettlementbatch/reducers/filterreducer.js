import { ActionType } from '../constants/constants.js';

const intialState ={
   screenMode:'initial',
   batchDisplay: 'hide',
   displayPage:'',
   pageSize:100,
   filterValues :{
    'fromDate': '',
    'toDate': '',
    'batchId':'',
	  'paCode':'',
	  'batchSequenceNum':''
   },
   fromScreen:'',
   noRecords:true,
   displayPage:'',
   batchLists: {},
   batchListMode: 'onList',
   selectedBatch:[]
   
}

const filterReducer = (state = intialState, action) => {
  switch (action.type) {
    case 'TOGGLE_FILTER':
      return {...state,screenMode: action.screenMode }
       case 'CLEAR_TABLE':
      return { ...state, noRecords: true, batchDisplay: 'show', filterDisplayMode: 'list' ,filterValues: action.data.listSettlementBatchFilter}
      case 'LISTBATCH_SUCCESS':
      return { ...state, screenMode:'display',	filterDisplayMode: 'list', batchDisplay: 'show', 	noRecords: false,filterValues: action.data.listSettlementBatchFilter,batchLists: action.data.batchLists }
       case 'CLEAR_FILTER':
      return intialState;
      case 'LISTBATCHDETAIL_SUCCESS':
      return { ...state, batchListMode: action.batchListMode ,batchDetailsList: action.batchDetails, screenMode:'display', batchDetailStatus: "show",selectedBatchId: action.selectedBatchId,pageNumber: action.pageNumber, pageSize: action.pageSize,selectedBatch:action.selectedBatch }
      case 'LISTBATCHDETAIL_ERROR':
      return { ...state, batchListMode: action.batchListMode,batchDetailStatus: "hide",selectedBatchId: action.selectedBatchId, pageNumber: action.pageNumber, pageSize: action.pageSize,selectedBatch:action.selectedBatch };
      case 'STORE_ADVANCE_PAYMENT_FILTER':
      return {
        ...state, filterValues: {
          'fromDate': action.data.batchDate,
          'toDate': action.data.batchDate,
          'batchId': action.data.batchId,
          'paCode':action.data.paCode,
		      'batchSequenceNum' : action.data.batchSequenceNum
        },
        fromScreen:action.data.fromScreen
      }
    default:
      return state
  }
}
export default filterReducer;