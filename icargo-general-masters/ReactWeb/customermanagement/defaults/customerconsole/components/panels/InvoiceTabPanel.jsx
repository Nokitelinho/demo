import React, { Component } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';


class InvoiceTabPanel extends Component {
    constructor(props) {
        super(props);

    }
    filter = (key) => {
        this.props.filterInvoiceDetails(key);
    }
    render() {
        let active = 0;
        if (this.props.invoiceFilter === 'F') {
            active = 0;
        } else if (this.props.invoiceFilter === 'D') {
            active = 1;
        } else {
            active = 2
        }
        return (
            <IButtonbar size="md" active={active}>
                        <IButtonbarItem >
                            <a className="nav-link ui-tabs-anchor" onClick={() => this.filter('F')}>
                                <div className="fs12"> <div className="dot-indicators">
                                    <span className="pad-r-3xs"><i className="red m-0"></i></span>
                                </div>
                                    <IMessage msgkey="customermanagement.defaults.customerconsole.finalized"/></div>
                            </a>
                        </IButtonbarItem>
                        <IButtonbarItem >
                            <a className="nav-link ui-tabs-anchor" onClick={() => this.filter('D')}>
                                <div className="fs12"> <div className="dot-indicators">
                                    <span className="pl-0"><i className="orange m-0"></i></span>
                                </div> <IMessage msgkey="customermanagement.defaults.customerconsole.diff"/> </div>
                            </a>
                        </IButtonbarItem>
                        <IButtonbarItem >
                            <a className="nav-link ui-tabs-anchor" onClick={() => this.filter('ALL')}>
                                <div className="fs12"><IMessage msgkey="customermanagement.defaults.customerconsole.all"/> </div>
                            </a>
                        </IButtonbarItem>
            </IButtonbar>



        )
    }
}

InvoiceTabPanel.propTypes = {
    statusCount: PropTypes.object,
    invoiceFilter: PropTypes.string,
    filterInvoiceDetails: PropTypes.func
};
export default InvoiceTabPanel;