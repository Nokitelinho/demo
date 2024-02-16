import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Row, Col, FormGroup, Label } from "reactstrap";
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov'
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';


class FilterPanel extends Component {
    constructor(props) {
        super(props);

    }

    clearFilter=()=> {
        this.props.reset();
        this.props.onClearFilter();
    }

    changeFilterMode=()=> {
        this.props.onChangeFilterMode((this.props.filterDisplayMode === 'edit') ? 'view' : 'edit');
    }


    onList=()=> {
        this.props.onList();
    }
    getStatusClass=(status)=>{
        switch (status) {
            case "Active": return "badge badge-pill high-light badge-active pad-x-xs pad-y-2xs mar-r-xs"
            case "Draft": return "badge badge-pill high-light badge-info pad-x-xs pad-y-2xs mar-r-xs"
            case "New": return "badge badge-pill high-light badge-info pad-x-xs pad-y-2xs mar-r-xs"
            case "Inactive": return "badge badge-pill high-light badge-alert pad-x-xs pad-y-2xs mar-r-xs"
            case "Rejected": return "badge badge-pill high-light badge-error pad-x-xs pad-y-2xs mar-r-xs"
            case "Blacklisted": return "badge badge-pill high-light badge-error pad-x-xs pad-y-2xs mar-r-xs"
        }
    }



    render() {
        const formParams = { "code": "inboundAccNo" }
        const url = "ux.showCustomer.do?formCount=1&mode=Y"
        const customerDetails = this.props.customerDetails
        return (
            <header>
                {(this.props.filterDisplayMode === 'edit' || this.props.filterDisplayMode === 'initial' || (this.props.filterDisplayMode === 'list' && this.props.noRecordFound)) ? (
                    <div className="pad-md pad-b-3xs position-relative">
                        <Row>
                            <div className="col-3 col-sm-4 col-md-3">
                                <FormGroup className="mandatory">
                                    <Label className="form-control-label"><IMessage msgkey="customermanagement.defaults.customerconsole.custcode"/></Label>
                                    {<Lov componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_CUSCODE" formParameters={formParams} closeButtonIds={['btnOk', 'btnClose']} uppercase={true} name="inboundAccNo" lovTitle="Customer"
                                        dialogWidth="600" dialogHeight="500"  actionUrl={url}
                                    />
                                    }
                                </FormGroup>
                            </div>
                            <Col>
                                <div className="mar-t-md">
                                    <IButton category="primary" componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_LIST" btype="LIST" accesskey="L" onClick={this.onList}><IMessage msgkey="customermanagement.defaults.customerconsole.list"/></IButton>
                                    <IButton category="default" componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTOMER_CONSOLE_CLEAR" btype="CLEAR" accesskey="C" onClick={this.clearFilter}><IMessage msgkey="customermanagement.defaults.customerconsole.clear"/></IButton>
                                </div>
                            </Col>
                        </Row>
                        {(this.props.filterDisplayMode === 'edit') ?
                                    <i className="icon ico-close-fade flipper flipper-ico" onClick={this.changeFilterMode}></i> :
                                    ""}
                    </div>
                ) : <div className="flippane col animated fadeInDown" id="headerData" >

                        <Row>
                            <Col xs="5" sm="6" className="pad-y-2sm border-right">
                                <Col xs="24">
                                    <div className="no-gutters row">
                                        <Col xs="18">
                                            <Row className="no-mar flight-number-data form-group">
                                               
                                                <div className="no-pad d-flex col-auto col-sm-auto col-md-auto col-lg-auto rest-fliper">
                                                    <h4 className="text-link strong">{customerDetails.customerCode}<i className="icon btn-scribble" id="flightnumber_scribbleimg"></i></h4>
                                                    <a href="#" className="d-flex-inline pad-3xs pad-l-2sm flipper" flipper="headerForm"><i className="icon ico-pencil-rounded-orange" onClick={this.changeFilterMode}></i></a>
                                                </div>
                                            </Row>
                                        </Col>
                                        <Col xs="6" className="text-right">
                                            <label className={this.getStatusClass(customerDetails.status)}>{customerDetails.status}</label>
                                        </Col>
                                    </div>
                                </Col>
                                <Col xs="24" className="pad-t-2sm">
                                    <span>{customerDetails.customerName}</span>
                                </Col>
                            </Col>
                            <Col>
                                <Row className="pad-md">
                                    <Col xs="6">
                                        <Label className="form-control-label"><IMessage msgkey="customermanagement.defaults.customerconsole.exprtagntcde"/></Label>
                                        <div className="form-control-data">{customerDetails.iataCode}</div>
                                    </Col>
                                    <Col xs="6">
                                        <Label className="form-control-label"><IMessage msgkey="customermanagement.defaults.customerconsole.accno"/></Label>
                                        <div className="form-control-data">{customerDetails.accountNumber}</div>
                                    </Col>
                                    <Col xs="6">
                                        <Label className="form-control-label"><IMessage msgkey="customermanagement.defaults.customerconsole.validfrm"/></Label>
                                        <div className="form-control-data">{customerDetails.validFrom}</div>
                                    </Col>
                                    <Col xs="6">
                                        <Label className="form-control-label"><IMessage msgkey="customermanagement.defaults.customerconsole.validto"/></Label>
                                        <div className="form-control-data">{customerDetails.validTo}</div>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>
                    </div>
                }
            </header>


        )
    }
}

FilterPanel.propTypes = {
    reset: PropTypes.func,
    onClearFilter: PropTypes.func,
    customerDetails: PropTypes.object,
    onChangeFilterMode: PropTypes.func,
    noRecordFound: PropTypes.bool,
    filterDisplayMode: PropTypes.string,
    onList: PropTypes.func
};
export default wrapForm('customerConsoleFilter')(FilterPanel);