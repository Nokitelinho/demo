import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import ListCCAPopup from './ListCCAPopup.jsx'
import PaymentDetailsPopup from './PaymentDetailsPopup.jsx'
import PropTypes from 'prop-types';
import { IButtonDropdown, IDropdownMenu, IDropdownItem } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class ActionButtonsPanel extends Component {
    constructor(props) {
        super(props);
    }
    onClose=()=>{
        this.props.onClose();
    }
    onSelectListCCA=()=> {
        this.props.listCCADetails();
    }
    onCloseListCCA=()=>{
        this.props.closeCCADetails();
    }
    onSelectPaymentDetails=()=>{
        this.props.listPaymentDetails();
    }
    onClosePaymentDetails=()=> {
        this.props.closePaymentDetails();
    }
    navigateActions=(event)=> {
        this.props.navigateActions(event.target.getAttribute('data-screenkey'));
    }
	getAccountStatement=(event)=>{
        this.props.getAccountStatement(event.target.getAttribute('data-batchkey'));
    }
	onEmail = () => {
		this.props.emailAccountStatement();
	}
  
    render() {

        const disabled = this.props.noRecordFound
         
        return (

            <footer className="footer-panel">
                <ListCCAPopup toggleFn={this.onCloseListCCA} show={this.props.showListCCA} onClose={this.onCloseListCCA} exportToExcel={this.props.exportToExcel} ccaDetails={this.props.ccaDetails} />
                <PaymentDetailsPopup toggleFn={this.onClosePaymentDetails} currency={this.props.currency} show={this.props.showPaymentDetails} onClose={this.onClosePaymentDetails} paymentDetails={this.props.paymentDetails} />
                <IButtonDropdown split={false}
                    text="..."
                    category="default">
                    <IDropdownMenu>
                        <IDropdownItem disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_OUTREC' data-screenkey="OR" accesskey="R" className="btn btn-nav" onClick={this.navigateActions}><i className="icon ico-link-blue"></i> <IMessage msgkey="customermanagement.defaults.customerconsole.outstandrec"/>
                        </IDropdownItem>
                    </IDropdownMenu>
                </IButtonDropdown>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_CUSDET' data-screenkey="CD" accesskey="S" className="btn btn-nav" onClick={this.navigateActions} ><i className="icon ico-link-blue"></i> <IMessage msgkey="customermanagement.defaults.customerconsole.customdetails"/></IButton>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_UNAPPL' data-screenkey="UP" accesskey="U" className="btn btn-nav" onClick={this.navigateActions} ><i className="icon ico-link-blue"></i> <IMessage msgkey="customermanagement.defaults.customerconsole.unappliedpay"/></IButton>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_INVDET' data-screenkey="ID" accesskey="I" className="btn btn-nav" onClick={this.navigateActions} ><i className="icon ico-link-blue"></i> <IMessage msgkey="customermanagement.defaults.customerconsole.invoicedet"/></IButton>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_CCA' popup="cca_list" accesskey="T" className="btn btn-default poptrigger" onClick={this.onSelectListCCA} ><IMessage msgkey="customermanagement.defaults.customerconsole.ccas"/></IButton>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_PAY' popup="payment_details" accesskey="P" className="btn btn-default poptrigger" onClick={this.onSelectPaymentDetails} ><IMessage msgkey="customermanagement.defaults.customerconsole.paydet"/></IButton>
                <IButton disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_EMAIL' accesskey="E" className="btn btn-default" onClick={this.onEmail} ><IMessage msgkey="customermanagement.defaults.customerconsole.email"/></IButton>
                <IButtonDropdown disabled={disabled} componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_ACC' category="primary" text="Account Statement" dropup className="bottom">
					<IDropdownMenu>
						<IDropdownItem className="fs12" onClick={this.getAccountStatement}data-batchkey="E">Excel</IDropdownItem>
						<IDropdownItem className="fs12" onClick={this.getAccountStatement}data-batchkey="P" >PDF</IDropdownItem>
					</IDropdownMenu>
                </IButtonDropdown>
                <IButton componentId='CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_CLOSE'  className="btn btn-default" btype="CLOSE" accesskey="O"  onClick={this.onClose} ><IMessage msgkey="customermanagement.defaults.customerconsole.close"/></IButton>
            </footer>


        )
    }
}
ActionButtonsPanel.propTypes = {
    getAccountStatement: PropTypes.func,
    onClose: PropTypes.func,
    showListCCA: PropTypes.bool,
    currency: PropTypes.string,
    showPaymentDetails: PropTypes.bool,
    noRecordFound: PropTypes.bool,
    exportToExcel: PropTypes.func,
    ccaDetails: PropTypes.array,
    paymentDetails: PropTypes.array,
    navigateActions: PropTypes.func,
    listCCADetails: PropTypes.func,
    closeCCADetails: PropTypes.func,
    listPaymentDetails: PropTypes.func,
    closePaymentDetails: PropTypes.func,
	emailAccountStatement: PropTypes.func
};


export default ActionButtonsPanel;