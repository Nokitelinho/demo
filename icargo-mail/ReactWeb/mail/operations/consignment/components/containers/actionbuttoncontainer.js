import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { ShowPrintMailTag, printConsignment, deleteConsignmentAction, screenLoad, getLastRowData, onCloseFunction, navigateToConsignmentSecurity,printConsignmentSummary,validateMailbagsPresentandPrint } from '../../actions/commonaction';
import { asyncDispatch,dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {reset} from 'redux-form';
import { save, validateMailBagForm, getFormData, validateMailBagFormDomestic } from '../../actions/filteraction';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

const mapStateToProps = (state) => {
  return {
    formData:state.listconsignmentreducer.formData,
    deletedMailbagslist:state.listconsignmentreducer.deletedMailbagslist,
    isDomestic:state.listconsignmentreducer.isDomestic,
    routingList:state.listconsignmentreducer.routingList,
    screenMode:state.listconsignmentreducer.screenMode,
    lastRowData:state.listconsignmentreducer.lastRowData,
    activeMailbagAddTab:state.listconsignmentreducer.activeMailbagAddTab,
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    ShowPrintMailTag:() => {
        dispatch(dispatchAction(ShowPrintMailTag)());
        },
    printConsignment:(printType) => {
        dispatch(dispatchPrint(printConsignment)(printType));
        },
    deleteConsignmentAction: () => {
        dispatch(requestWarning([{code:"mail.operations.consignment.delete", description:"Do you wish to delete the consignment?"}],{functionRecord:deleteConsignmentAction}))
        }, 
    onCloseFunction:() => {
        dispatch(dispatchAction(onCloseFunction)());
    },
    onSave: (mailBags, routingDetails, isDomestic, activeTab,deletedMailbagslist) => {
        let validObject = {};
        validObject.valid= true;
          if(isDomestic){
            validObject = validateMailBagFormDomestic(mailBags, routingDetails, activeTab);
          }else{
            validObject = validateMailBagForm(mailBags, routingDetails, isDomestic, activeTab,deletedMailbagslist);
          }      
        if(!validObject.valid){
          dispatch(requestValidationError(validObject.msg, ''));
          }
        else if(mailBags && mailBags.length==0 && deletedMailbagslist && deletedMailbagslist.length>0) {
        dispatch(requestWarning([{code:"mail.operations.consignment.delete", description:"Do you wish to delete the consignment?"}],{functionRecord:deleteConsignmentAction}))
       }
        else {
       dispatch(asyncDispatch(save)()).then((response) =>{
        if(!isEmpty(response)) {
        dispatch(asyncDispatch(screenLoad)());
        dispatch(reset('consignmentFilter'));
       }})}
      },
    getFormData:() => {
        dispatch(dispatchAction(getFormData)());
      },
    getLastRowData:() => {
        dispatch(dispatchAction(getLastRowData)());
      },
    navigateToConsignmentSecurity:() => {
      dispatch(dispatchAction(navigateToConsignmentSecurity)());
    },
    printConsignmentSummary:(printType) => {
  dispatch(dispatchAction(validateMailbagsPresentandPrint)({'printType':printType}));
    }
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer