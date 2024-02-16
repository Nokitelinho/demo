import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import PaymentBatchPanel from '../panels/PaymentBatchPanel.jsx';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {  addAttachment,deleteBatch,deleteAttachment,saveSelectedBatchIndexes,editBatch  } from '../../actions/commonaction';
import {addPayment,listPaymentDetails} from '../../actions/filteraction';
import { requestSuccess } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { Msgs } from '../../constants/constants.js';
 
const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    paymentdetails : state.filterReducer.paymentdetails,
    screenMode: state.filterReducer.screenMode,
    screenFilterMode:state.filterReducer.screenFilterMode,
    filterValues:state.filterReducer.filterValues,
  }
}
const mapDispatchToProps = (dispatch) => {
 return {
    
  addAttachment: (selectedBatchRow) => {
    dispatch(dispatchAction(addAttachment)({selectedBatchRow}))
  },
  onAddPayment : () =>{
    dispatch(dispatchAction(addPayment)());
  },
  editBatch : (selectedBatchRow) =>{
    dispatch(asyncDispatch(editBatch)({selectedBatchRow}))
  },  
  deleteBatch : (selectedBatchRow) =>{
    dispatch(asyncDispatch(deleteBatch)({selectedBatchRow})).then(() => {
      let message={'msgkey':Msgs.CNFRM_MSG_DELBATCH,'defaultMessage':'Batch Deleted Successfully'};
      dispatch(requestSuccess(message));
      setTimeout(() => {
          dispatch({type,data:response.results[0]})
        }, 2000)
      dispatch(asyncDispatch(listPaymentDetails)({mode:'LIST'}));
   })
  },
  deleteAttachment : (selectedBatchRow) =>{
    dispatch(asyncDispatch(deleteAttachment)({selectedBatchRow})).then(() => {
      let message={'msgkey':Msgs.CNFRM_MSG_DELATH,'defaultMessage':'Batch attachment Deleted Successfullly'};
      dispatch(requestSuccess(message));
      setTimeout(() => {
          dispatch({type,data:response.results[0]})
        }, 2000)
      dispatch(asyncDispatch(listPaymentDetails)({mode:'LIST'}));
   })
  },
  exportToExcel: (displayPage, pageSize) => {
    return dispatch(asyncDispatch(listPaymentDetails)({ mode: "EXPORT", displayPage, pageSize }))
  },
  getNewPage : (displayPage, pageSize) => {
    dispatch(asyncDispatch(listPaymentDetails)({displayPage,pageSize,mode:'LIST'}))
  },
  saveSelectedBatchIndexes: (index) => {           
        dispatch(dispatchAction(saveSelectedBatchIndexes)(index));
  }


    }
}

const PaymentBatchContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(PaymentBatchPanel)


export default PaymentBatchContainer