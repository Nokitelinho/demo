import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';


class ContactDetailsPanel extends Component {
    constructor(props) {
        super(props);
    }


    render() {

        const customerDetails = this.props.customerDetails

        return (
            <Row className="mar-t-md">
                <Col xs="16" md="16" sm="24">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.contctdet"/></h4>
                            </Col>
                        </div>
                        <Col className="card-body p-0">
                            <Row>
                                <Col className="bg-primary" xs="6">
                                    <Col className="text-white pad-md">
                                        <Col>
                                            <h3>{customerDetails.contactcustomerName}</h3>
                                        </Col>
										<Col>
                                            <h4>{customerDetails.contactcustomerTitle}</h4>
                                        </Col>
                                        <Col>
                                            <div className="mar-t-md d-flex align-items-center">
                                                <i className="icon ico-mobile-white"></i>
                                                <span className="pad-l-2md">{customerDetails.contactcustomerPhone}</span>
                                            </div>
                                            <div className="mar-t-2sm">
                                                <i className="icon ico-email-white"></i>
                                                <span className="pad-l-2xs d-flext-inline align-text-bottom">
                                                    <a className="text-white" href={"mailTo:" + customerDetails.contactcustomerEmail}>{customerDetails.contactcustomerEmail}</a>
                                                </span>
                                            </div>
                                            <div className="mar-t-2sm">
                                                <i className="icon ico-print-white"></i>
                                                <span className="pad-l-2xs d-flext-inline align-text-bottom">{customerDetails.contactcustomerFax}</span>
                                            </div>
                                        </Col>
                                    </Col>
                                </Col>
                                <Col>
                                    <Col className="pad-md">
                                        <Row>
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.street"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.street}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.city"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.city}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.state"/></label>
                                            </Col>
                                            <Col xs="6">
                                                <span className="text-black">{customerDetails.state}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.country"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.country}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.zip"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.zipCode}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            <Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.accspec"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.accountSpecialist}</span>
                                            </Col>
                                        </Row>
                                        <Row className="row mar-t-xs">
                                            < Col xs="6">
                                                <label className="text-grey strong"><IMessage msgkey="customermanagement.defaults.customerconsole.instructn"/></label>
                                            </Col>
                                            <Col>
                                                <span className="text-black">{customerDetails.instruction}</span>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Col>
                            </Row>
                        </Col>
                    </div>

                </Col>
            </Row>

        )
    }
}

ContactDetailsPanel.propTypes = {
    customerDetails: PropTypes.object
};
export default ContactDetailsPanel;