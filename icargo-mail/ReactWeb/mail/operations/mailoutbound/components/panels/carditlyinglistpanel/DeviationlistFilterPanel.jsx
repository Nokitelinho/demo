import React, { Component, Fragment } from 'react';
import { ITextField, ISelect, ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
class DeviationlistFilterPanel extends Component {
    constructor(props) {
        super(props);
        this.dateRange='MailOutboundDeviationDateRange'
    }
    computeDateRange (formValues) {     
        if(formValues.mailbagId && formValues.mailbagId.length >0) {
            this.dateRange = 'MailOutboundDeviationDateRange'
        }
    }

    render() {
        let status = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if(this.props.oneTimeValues['mail.operations.deviationlist.status']) {
                status = this.props.oneTimeValues['mail.operations.deviationlist.status'];
            }
            status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        if(this.props.deviationListFilter && this.props.deviationListFilter.values) {
            this.computeDateRange(this.props.deviationListFilter.values)
        }
        return (
            <Fragment>
               <div className="mb-3 row">
                    <Col xs="16">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.mailbagid" />
                            </Label>
                            <ITextField mode="edit" name="mailbagId" type="text" uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.consignmentnumber" />
                            </Label>
                            <ITextField mode="edit" uppercase={true} name="consignmentNo" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO" maxlength="35"></ITextField>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group mandatory">
                            <Label className="form-control-label mandatory_label">
                            <IMessage msgkey="mail.operations.mailoutbound.fromdate" />
                            </Label>
                            <DatePicker name="fromDate" dateFieldId={this.dateRange} type="from" toDateName="toDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATEFROM" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group mandatory">
                            <Label className="form-control-label mandatory_label">
                            <IMessage msgkey="mail.operations.mailoutbound.todate" />
                            </Label>
                            <DatePicker name="toDate" dateFieldId={this.dateRange} type="to" fromDateName="fromDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATETO" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                Deviation Type
                            </Label>
                            <ISelect name="mailStatus"  options={status} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILSTATUS" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originairport" />
                            </Label>
                            <Lov name="mailOrigin" uppercase={true} lovTitle="Origin airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destairport" />
                            </Label>
                            <Lov name="mailDestination" uppercase={true} lovTitle="Dest uplift airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILDESTINATION" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.pacode" />
                            </Label>
                            <Lov name="paCode" uppercase={true} lovTitle="PA Code" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_PACODE" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group mandatory">
                            <Label className="form-control-label mandatory_label">
                                <IMessage msgkey="mail.operations.mailoutbound.upliftairport" />
                            </Label>
                            <Lov name="scanPort" uppercase={true} lovTitle="uplift airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label ">RDT</label>
                            <DatePicker name="rdtDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                        </div>
                    </Col>
                </div>
            </Fragment>
        )
    }
}

DeviationlistFilterPanel.propTypes = {
    oneTimeValues: PropTypes.object,
    screenMode: PropTypes.string,
    filterValues: PropTypes.object
}
export default wrapForm('deviationListFilter')(DeviationlistFilterPanel)
