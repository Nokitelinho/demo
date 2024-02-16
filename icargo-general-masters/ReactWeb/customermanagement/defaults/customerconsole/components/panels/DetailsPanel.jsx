import React, { Component, Fragment } from 'react';
import ContactDetailsPanel from './ContactDetailsPanel.jsx'
import BillingPanel from './BillingPanel.jsx'
import InvoiceContainer from '../containers/invoicecontainer.js'
import ChartPanel from './ChartPanel.jsx'
import PropTypes from 'prop-types';

import {CommonUtil} from 'icoreact/lib/ico/framework/config/app/commonutil';
import IScribblePad from 'icoreact/lib/ico/framework/component/business/scribblepad/';

class DetailsPanel extends Component {
    constructor(props) {
        super(props);

    }


    render() {
		const {customerDetails} = this.props;
		const userId = CommonUtil.logonAttributes.userId;
		const filtervalues = {customerDetails,userId};
        return (
            <section className="section-panel scroll-y" style={{height:'78vh'}}>
                {this.props.invoiceDisplayMode === 'min' ?
                    <Fragment>
						{/* Kiran SP */}  
                        <IScribblePad 
                            title="Scratch Pad"
                            iconclass="ico-pencil-rounded-orange"
							filtervalues={filtervalues}
							scribbleType="CUSTOMER_SCRIBBLE"
                            reducername="customerscribblereducer"
                            displayMode="side-bar"
							/>
                        {/* Kiran SP */}					
                        <ChartPanel ageingReceivables={this.props.ageingReceivables} statusView={this.props.statusView}></ChartPanel>
                        <InvoiceContainer></InvoiceContainer>
                        <BillingPanel ageingReceivables={this.props.ageingReceivables} statusView={this.props.statusView} receivablesCreditInfo={this.props.receivablesCreditInfo}></BillingPanel>
                        <ContactDetailsPanel customerDetails={this.props.customerDetails}></ContactDetailsPanel>
                    </Fragment> :
                    <InvoiceContainer></InvoiceContainer>}
            </section>

        )
    }
}

DetailsPanel.propTypes = {
    ageingReceivables: PropTypes.array,
    statusView: PropTypes.array,
    customerDetails: PropTypes.object,
    receivablesCreditInfo: PropTypes.object,
    invoiceDisplayMode: PropTypes.string
};

export default DetailsPanel;