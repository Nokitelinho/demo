import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Col, Label, Row } from 'reactstrap'
import { ITextField, ISelect, IButton, ITextArea } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import OnwardRouting from './OnwardRouting.jsx';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
class AttachRoutingPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            transferFilterType: "C"
        }
    }

    onFilterChange = (event) => {
        this.setState({ transferFilterType: event.target.value })
    }

    transferContainer=()=> {
        this.props.transferContainer();
    }

    closeAttachRouting = () => {
        this.props.closeAttachRouting('ATTACH_ROUT');
    }
    listAttachRouting=()=>{
       this.props.listAttachRouting(this.props.attachRoutingFilter.values);
    }


    render() {
        let type = [];
        if (!isEmpty(this.props.attachRoutingOneTimeValues)) {
             type = this.props.attachRoutingOneTimeValues['mailtracking.defaults.consignmentdocument.type'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label mandatory_label">
                                        <IMessage msgkey="mail.operations.mailoutbound.condocno" />
                                    </Label>
                                    <ITextField name="consignemntNumber" />
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label mandatory_label">
                                        <IMessage msgkey="mail.operations.mailoutbound.pa" />
                                    </Label>
                                    <ITextField name="paCode" />
                                </div>
                            </Col>
                            <Col>
                                <div className="mar-t-md">
                                    <IButton category="primary" className="m-r-10" bType="LIST" accesskey="L" disabled={this.props.attachroutingListSuccess===true} onClick={this.listAttachRouting}>List</IButton>
                                    <IButton category="secondary" bType="CLEAR" accesskey="C"  onClick={this.props.clearAttachRoutingPanel}>Clear</IButton>
                                </div>
                            </Col>
                        </Row>
                        <div className="strong pad-b-2xs border-bottom mar-b-2sm">Add Routing</div>
                        <Row>
                            <Col xs="6">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.flightnumber" />
                                </Label>
                            </Col>
                            <Col xs="6">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.flightdate" />
                                </Label>
                            </Col>
                            <Col xs="5">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.pol" />
                                </Label>
                            </Col>
                            <Col xs="5">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.pou" />
                                </Label>
                            </Col>
                        </Row>
                        {(this.props.show) ? <OnwardRouting fromPopup="AttachRouting" initialValues={this.props.initialValues}/> : null}
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.consignmentdate" />
                                </Label>
                                    <DatePicker name="consignmentDate" />
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.consignmenttype" />
                                    </Label>
                                    <ISelect name="consignmentType"  options={type} defaultOption={true} />
                                </div>
                            </Col>
                            
                            <Col>
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.remarks" />
                                </Label>
                                <ITextArea name="remarks" />
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.props.saveAttachRouting} disabled={this.props.attachroutingListSuccess===false}>Save</IButton>{' '}
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.closeAttachRouting}>Close</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}
AttachRoutingPanel.propTypes = {
    transferContainer: PropTypes.func,
    closeAttachRouting: PropTypes.func,
    listAttachRouting: PropTypes.func,
    attachRoutingOneTimeValues: PropTypes.object,
    attachRoutingFilter: PropTypes.object,
    attachroutingListSuccess: PropTypes.bool,
    clearAttachRoutingPanel: PropTypes.func,
    show: PropTypes.bool,
    initialValues: PropTypes.object,
    saveAttachRouting: PropTypes.func,
}

export default icpopup(wrapForm('attachRoutingForm')(AttachRoutingPanel), { title: 'Attach Routing',className: 'modal_550px'  })


