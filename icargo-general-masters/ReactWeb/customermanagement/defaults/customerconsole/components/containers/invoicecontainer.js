import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import InvoicePanel from '../panels/InvoicePanel.jsx';
import { changeInvoiceDisplayMode, filterInvoiceDetails,selectInvoice } from '../../actions/invoiceaction'
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { getBillingInvoiceDetails } from '../../actions/filteraction';

const getInvoices = (state) => state.invoiceReducer.invoiceDetails
const getInvoiceFilter = (state) => state.invoiceReducer.invoiceFilter
const getDisplayMode = (state) => state.invoiceReducer.invoiceDisplayMode
const getInvoiceDetails = createSelector([getInvoices, getInvoiceFilter, getDisplayMode], (invoiceDetails, invoiceFilter, displayMode) => {
  const filteredInvoicedetails = invoiceDetails.filter((invoice) => invoiceFilter === 'ALL' || invoiceFilter === invoice.status)
  return displayMode === 'min' ? filteredInvoicedetails.slice(0, 5) : filteredInvoicedetails
}
)


const mapStateToProps = (state) => {
  return {
    invoiceDisplayMode: state.invoiceReducer.invoiceDisplayMode,
    invoiceDetails: getInvoiceDetails(state),
    invoiceFilter: state.invoiceReducer.invoiceFilter,
    statusCount: state.invoiceReducer.statusCount,
    currency: state.filterReducer.customerDetails.billingCurrency,
    selectedInvoice: state.invoiceReducer.selectedInvoice,
    invoiceDetailsPage:state.invoiceReducer.invoiceDetailsPage

  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onChangeInvoiceDisplayMode: (mode) => {
      dispatch(changeInvoiceDisplayMode(mode));
    },
    filterInvoiceDetails: (filter) => {
      dispatch(dispatchAction(filterInvoiceDetails)(filter))
    },
    selectInvoice:(invoiceNumber)=>{
       dispatch(dispatchAction(selectInvoice)(invoiceNumber))
    },
    getNextInvoiceDetail:(pageNumber, pageSize)=>{
      dispatch(asyncDispatch(getBillingInvoiceDetails)({ mode: "NEXT_PAGE", pageNumber, pageSize }))
    },
    exportToExcel:(pageNumber, pageSize)=>{
      return dispatch(asyncDispatch(getBillingInvoiceDetails)({ mode: "EXPORT", pageNumber, pageSize }))
    }
  }
}

const InvoiceContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(InvoicePanel)

export default InvoiceContainer