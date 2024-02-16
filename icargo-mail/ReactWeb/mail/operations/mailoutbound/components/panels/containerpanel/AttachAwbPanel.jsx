
import React, { Fragment } from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types'

class AttachAwbPanel extends React.PureComponent {
    constructor(props) {
        super(props);


    }
    onSaveAttachAwb = () => {
        this.props.onSaveAttachAwb();
    }
    render() {
       let weightUnit =[];
       weightUnit = this.props.attachAwbOneTimeValues['shared.defaults.weightUnitCodes'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
       
        return (
            <Fragment >
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="11">
                                <div className="form-group mb-0">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.awbno" />
                                    </Label>
                                    <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                                </div>
                            </Col>
                            <Col>
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.agentcode" />
                                    </Label> 
                                    <ITextField disabled="true" name="agentCode" />
                                </div>
                            </Col>
                            <Col xs="auto">
                                <div className="mar-t-md">
                                    <IButton category="primary" bType="LIST"  accesskey="L" disabled = {this.props.attachawbListSuccess === true} onClick={this.props.listAwbDetails}>List</IButton>
                                    <IButton category="default" bType="CLEAR" accesskey="C" onClick={this.props.clearAttachAwbPanel}>Clear</IButton>
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.origin" />
                                    </Label> 
                                    <ITextField name="origin" disabled="true"/>
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.destination" />
                                    </Label> 
                                    <ITextField name="destination" disabled="true" />
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.shipmentpieces" />
                                    </Label> 
                                    <ITextField name="standardPieces" disabled = {this.props.attachawbListSuccess === false}/>
                                </div>
                            </Col>
                            <Col xs="12">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.shipmentweight" />
                                    </Label> 
                                    <Row>
                                        <Col xs="12">
                                            <ITextField name="standardWeight" disabled = {this.props.attachawbListSuccess === false}/>
                                        </Col>
                                        <Col xs="12">
                                            <ISelect name='stdWeightMeasure.displayUnit' options={weightUnit} disabled = {this.props.attachawbListSuccess === false} />
                                        </Col>
                                    </Row>
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <div className="form-group mb-0">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.shipmentdescription" />
                                    </Label> 
                                    <ITextField name="shipmentDescription" disabled = {this.props.attachawbListSuccess === false}/>
                                </div>
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" disabled = {this.props.attachawbListSuccess === false} onClick={this.onSaveAttachAwb}>Save</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeAttachAwb}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

AttachAwbPanel.propTypes = {
    onSaveAttachAwb: PropTypes.func,
    attachAwbOneTimeValues: PropTypes.object,
    attachawbListSuccess: PropTypes.bool,
    listAwbDetails: PropTypes.func,
    clearAttachAwbPanel: PropTypes.func,
    closeAttachAwb: PropTypes.func
}

export default icpopup(wrapForm('attachAwbDetails')(AttachAwbPanel), { title: 'Attach Awb' })



