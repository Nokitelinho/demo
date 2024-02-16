import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonsPanel from '../panels/ActionButtonsPanel.jsx';
import { onClose, listCCADetails, closeCCADetails, listPaymentDetails, closePaymentDetails, navigateActions, getAccountStatement, emailAccountStatement } from '../../actions/commonaction'
import { dispatchAction, asyncDispatch, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
  return {
    noRecordFound: state.filterReducer.noRecordFound,
    ccaDetails: state.commonReducer.ccaDetails,
    showListCCA: state.commonReducer.showListCCA,
    paymentDetails: state.commonReducer.paymentDetails,
    showPaymentDetails: state.commonReducer.showPaymentDetails,
    currency: state.filterReducer.customerDetails.billingCurrency
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onClose: () => {
      dispatch(dispatchAction(onClose)());
    },
    listCCADetails: () => {
      dispatch(dispatchAction(listCCADetails)({ mode: "LIST", pageNumber: '1' }))
    },
    exportToExcel: () => {
      return dispatch(asyncDispatch(listCCADetails)({ mode: "EXPORT" }))
    },
    closeCCADetails: () => {
      dispatch(dispatchAction(closeCCADetails)())
    },
    listPaymentDetails: () => {
      dispatch(dispatchAction(listPaymentDetails)())
    },
    closePaymentDetails: () => {
      dispatch(dispatchAction(closePaymentDetails)())
    },
    getAccountStatement: (exportMode) => {
      dispatch(dispatchAction(getAccountStatement)(exportMode))
      
    },
    navigateActions: (screen) => {
      dispatch(dispatchAction(navigateActions)(screen))
    },
	emailAccountStatement: () => {
      dispatch(dispatchAction(emailAccountStatement)());
    }
  }
}

const ActionButtonsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonsPanel)

export default ActionButtonsContainer