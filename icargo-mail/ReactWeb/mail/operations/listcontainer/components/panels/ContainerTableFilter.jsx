import React, { Fragment } from 'react';
import { ITextField, ISelect, ICheckbox, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Container } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
//import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class ContainerTableFilter extends React.PureComponent {
    constructor(props) {
        super(props);
        this.onlistContainerDetails = this.onlistContainerDetails.bind(this);
        this.onclearContainerDetails = this.onclearContainerDetails.bind(this);
        this.onInputChangeSearchmode = this.onInputChangeSearchmode.bind(this);
    }

    onlistContainerDetails() {
        this.props.onlistContainerDetails();
    }
    onclearContainerDetails() {
        this.props.onclearContainerDetails();
    }

    onInputChangeSearchmode() {
        this.props.onInputChangeSearchmode();
    }

    render() {

        let searchmode = [];
        let flighttype = [];
        let airportCode = this.props.airportCode;
        if (!isEmpty(this.props.oneTimeValues)) {
            flighttype = this.props.oneTimeValues['mailtracking.defaults.operationtype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            searchmode = this.props.oneTimeValues['mailtracking.defaults.containersearchmode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));

        }

        let flightDetails =this.props.tableFilter && this.props.tableFilter.flightnumber ? this.props.tableFilter.flightnumber:null;
        return (
            <div>
                <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.containerNo" /></label>
                            <ITextField mode="edit" name="containerNumber" type="text" uppercase={true}></ITextField>
                        </div>
                    </Col>
                   <Col xs="12">
                        <div className="form-group">
                            <IFlightNumber {...flightDetails}  mode="edit" ></IFlightNumber>
                        </div>
                    </Col>
                
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Current Port</label>
                            <ITextField mode="edit" name="assignedPort" type="text" uppercase={true}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">POL</label>
                            <ITextField mode="edit" name="pol" type="text" uppercase={true}></ITextField>
                        </div>
                   </Col>
                   <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">POU</label>
                            <ITextField mode="edit" name="pou" type="text" uppercase={true}></ITextField>
                        </div>
                   </Col>
                   <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Dest</label>
                            <ITextField mode="edit" name="destination" type="text" uppercase={true}></ITextField>
                        </div>
                   </Col>
                   <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Assigned On</label>
                            <DatePicker name="assignedOn"  />
                        </div>
                   </Col>
                   <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Assigned By</label>
                            <ITextField mode="edit" name="assignedBy" type="text" uppercase={true}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Outward Route</label>
                            <ITextField mode="edit" name="onwardRoute" type="text" uppercase={true}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">No of Bags</label>
                            <ITextField mode="edit" name="bags" type="text"></ITextField>
                        </div>
                    </Col>
                   <Col xs="6">
                                <div className="form-group">
                            <label className="form-control-label ">Weight</label>
                            <ITextField mode="edit" name="weight" type="text"></ITextField>
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                            <label className="form-control-label ">Actual Weight</label>
                            <ITextField mode="edit" name="actualWeight" type="text"></ITextField>
                                </div>
                            </Col>
                        <Col xs="6">
                            <div className="form-group">
                            <label className="form-control-label ">Content ID</label>
                            <ITextField mode="edit" name="contentId" type="text" uppercase={true}></ITextField>
                            </div>
                    </Col>
               
                </Row>
            </div>
        )
    }
}
export default wrapForm('ContainerTableFilter')(ContainerTableFilter)