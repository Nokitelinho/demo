import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {reset} from 'redux-form';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {screenLoad,onCloseFunction,onSaveFunction,onRaiseClaim,onAccept,validateInvoiceAmount,onReprocess,onReject} from '../../actions/commonaction';
import {listInvoicDetails,listInvoicEnquiryOnNavigation} from '../../actions/filteraction';

const mapStateToProps = (state) => {
  return {
    displayMode: state.filterReducer.displayMode,
    screenMode: state.filterReducer.screenMode,
    disableAcceptButton: state.filterReducer.disableAcceptButton,
    // eligibleBagFlag:state.commonReducer.eligibleBagFlag,
    tableValues:state.form.newMailbagsTable?state.form.newMailbagsTable.values:[],
    selectedMailbagIndex: state.filterReducer.selectedMailbagIndex,
    filterValues: state.filterReducer.filterValues
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {


    onCloseFunction: () => {
        dispatch(dispatchAction(onCloseFunction)());
    },
     onSaveFunction: () => {
        dispatch(asyncDispatch(onSaveFunction)()).then((response) => {
            dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
          });

    },
    onRaiseClaim :() => {
      dispatch(asyncDispatch(onRaiseClaim)()).then((response) => {
        dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
      });
    },
    onAccept :(tableValues,indexes) => {



          let validObject = validateInvoiceAmount(tableValues,indexes);
          if(!validObject.valid){
            dispatch(requestWarning([{ code: "mail.mra.invoicenquiry.warning", description: "There are bags eligible for Claim. Do you still want to proceed?" }], { functionRecord: onAccept }))
            }
            else {
              dispatch(asyncDispatch(onAccept)()).then((response) => {
                dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
                   } );
            }

    },
    onReprocess :() =>{
      dispatch(asyncDispatch(onReprocess)()).then((response) => {
        dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
      });
    } ,
    onReject :() =>{
      dispatch(asyncDispatch(onReject)()).then((response) => {
        dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
      });
    }

  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer