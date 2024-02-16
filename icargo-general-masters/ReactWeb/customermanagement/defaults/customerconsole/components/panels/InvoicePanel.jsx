import React, { Component } from 'react';
import { Row, Col, } from "reactstrap";
import InvoiceTablePanel from './InvoiceTablePanel.jsx'
import PropTypes from 'prop-types';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class InvoicePanel extends Component {
    constructor(props) {
        super(props);

    }
    onChangeInvoiceDisplayMode=()=> {
        this.props.onChangeInvoiceDisplayMode((this.props.invoiceDisplayMode === 'min') ? 'max' : 'min');

    }
   


    render() {

        return (

            <div className={this.props.invoiceDisplayMode === 'min'?"mar-y-2sm inner-panel default-view-set":"inner-panel"}>
                    <div className="card inner-panel tab-line-set">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.invoice"/></h4>
                            </Col>
                              <div className="card-header-btns">
                                    <IButton className="btn btn-icon" onClick={this.onChangeInvoiceDisplayMode} >
                                        <i className="icon ico-maximise-blue"></i>
                                    </IButton>
                                </div>
                        </div>
                        <div className="card-body p-0 inner-panel">
                            <InvoiceTablePanel  getNewPage={this.props.getNextInvoiceDetail} currency={this.props.currency} selectInvoice={this.props.selectInvoice} selectedInvoice={this.props.selectedInvoice} invoiceDisplayMode={this.props.invoiceDisplayMode} filterInvoiceDetails={this.props.filterInvoiceDetails} statusCount={this.props.statusCount} invoiceFilter={this.props.invoiceFilter} invoiceDetails={this.props.invoiceDetails} invoiceDetailsPage={this.props.invoiceDetailsPage} exportToExcel={this.props.exportToExcel}></InvoiceTablePanel>
                        </div>
                    </div>
            </div>

        )
    }
}
InvoicePanel.propTypes = {
  //  selectedInvoice: PropTypes.array,
    invoiceDetails: PropTypes.array,
    currency: PropTypes.string,
    selectInvoice: PropTypes.func,
    statusCount: PropTypes.object,
    invoiceFilter: PropTypes.string,
    invoiceDetailsPage:PropTypes.object,
    filterInvoiceDetails: PropTypes.func,
    onChangeInvoiceDisplayMode: PropTypes.func,
    exportToExcel:PropTypes.func,
    invoiceDisplayMode: PropTypes.string
};

export default InvoicePanel;