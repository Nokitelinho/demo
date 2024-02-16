import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import MailboxDetailsPanel from '../panels/MailboxDetailsPanel.jsx';
import {listMailboxDetails,messagingOptionChange} from '../../actions/filteraction';
import {deleteRows,selectedMailEvents,saveSelectedMailEventIndex,saveUnselectedMailEventIndex} from '../../actions/commonaction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { stat } from 'fs';
import { addRow, deleteRow, resetForm } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

const mapStateToProps = (state) => {
  return {
    initialValues:{mailboxName:state.filterReducer.mailboxName?state.filterReducer.mailboxName :'' ,
    ownerCode:state.filterReducer.ownerCode?state.filterReducer.ownerCode :'',
    partialResdit:state.filterReducer.partialResdit?state.filterReducer.partialResdit :'',
    msgEventLocationNeeded:state.filterReducer.msgEventLocationNeeded?state.filterReducer.msgEventLocationNeeded :'',
    resditTriggerPeriod:state.filterReducer.resditTriggerPeriod?state.filterReducer.resditTriggerPeriod :'',
    messagingEnabled:state.filterReducer.messagingEnabled?state.filterReducer.messagingEnabled :'',
    resditversion:state.filterReducer.resditversion?state.filterReducer.resditversion :'',
    mailboxOwner:state.filterReducer.mailboxOwner?state.filterReducer.mailboxOwner :'',
    mailboxStatus:state.filterReducer.mailboxStatus?state.filterReducer.mailboxStatus :'',
    remarks:state.filterReducer.remarks?state.filterReducer.remarks :'',
    },
    messagingEnabledFlag:state.filterReducer.messagingEnabledFlag?state.filterReducer.messagingEnabledFlag :'',
    mailEvents:(state.form.mailBoxIdTable && state.form.mailBoxIdTable.values &&state.form.mailBoxIdTable.values.mailBoxIdTable&&state.form.mailBoxIdTable.values.mailBoxIdTable.length>0) ?
    state.form.mailBoxIdTable.values.mailBoxIdTable : state.filterReducer.mailEvents,
    oneTimeValues:state.commonReducer.oneTimeValues,
    newRowData:  {mailCategory:'', mailClass: '', received : false, handedOver:false,uplifted:false },
    mailboxowner:state.form.mailboxidPanel&&state.form.mailboxidPanel.values&&state.form.mailboxidPanel.values.mailboxOwner?state.form.mailboxidPanel.values.mailboxOwner:''
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onlistMailboxDetails: (displayPage,pageSize) => {
      dispatch(asyncDispatch(listMailboxDetails)({displayPage,pageSize,mode:'LIST'}))
     },
     onMessagingOptionChange: (values) => {
      dispatch(dispatchAction(messagingOptionChange)(values));
    },
    
    addRow: (newRowData) => {
        dispatch(addRow("mailBoxIdTable", newRowData))
    },
    onDeleteRow: () => {
      dispatch(dispatchAction(deleteRows)());
    },
    getTotalCount: () => {
      return dispatch(dispatchAction(getTotalCount)());
    },
    saveSelectedMailEventIndex: (index, action) => {
      if(action==='SELECT'){
        dispatch(dispatchAction(saveSelectedMailEventIndex)(index, action));
      }else{
        dispatch(dispatchAction(saveUnselectedMailEventIndex)(index, action));
      }
      
    },

  }
}
const MailBoxDetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(MailboxDetailsPanel)


export default MailBoxDetailsContainer