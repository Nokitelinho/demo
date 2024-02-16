import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ISelect} from 'icoreact/lib/ico/framework/html/elements'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class FlightDetailsFilter extends React.PureComponent {
    constructor(props) {
        super(props);
        this.flightOperationalstatus = [];
        this.flightStatus = [];
        this.init();
    }
    init() {
        if (!isEmpty(this.props.oneTimeValues)) { 
            this.flightOperationalstatus = this.props.oneTimeValues['mailtracking.defaults.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.flightOperationalstatus.push({ value: 'N', label: 'New' });
            this.flightStatus = this.props.oneTimeValues['flight.operation.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    }
    render() {
       return (
            <Row>
                <Col xs="14">
                    <IFlightNumber flightnumber={this.props.initalValues} mode="edit"></IFlightNumber>
                </Col>
                {/* <Col xs="6">
                    <div className="form-group">
                       <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.upliftairport" />
                            </Label> 
                        <ITextField name="upliftAirport" uppercase={true} />
                    </div>
                </Col> */}
                <Col xs="8">
                    <div className="form-group">
                        <Label className="form-control-label">
                            Destination
                        </Label>
                        <Lov name="destination" uppercase={true} lovTitle="Destination" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />
                    </div>
                </Col>
                <Col xs="8">
                    <div className="form-group">
                        <Label className="form-control-label">
                             Mail Operational Status
                        </Label> 
                        <ISelect name="flightOperationalStatus" options={this.flightOperationalstatus} defaultOption={true} componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                    </div>
                </Col>
                <Col xs="8">
                    <div className="form-group">
                        <Label className="form-control-label">
                        <IMessage msgkey="mail.operations.mailoutbound.flightoperationalstatus" />
                        </Label> 
                        <ISelect name="flightStatus" multi={true} options={this.flightStatus} componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                    </div>
                </Col>
            </Row>
        )
    }
}
FlightDetailsFilter.propTypes = {
    initalValues:PropTypes.object
}
export default wrapForm('flightDetailsFilter')(FlightDetailsFilter)