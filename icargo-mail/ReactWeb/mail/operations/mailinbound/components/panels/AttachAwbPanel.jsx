
import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import {  IButton } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import PropTypes from 'prop-types'
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class AttachAwbPanel extends React.PureComponent {
    constructor(props) {
        super(props);
      
        
    }
    onSaveAttachAwb=()=>{
        this.props.onSaveAttachAwb();
    }

    closeAttachAwb=()=>{

        this.props.from==='MAIL'?
            this.props.closeMailPopUp('ATTACH_ROUTING'):this.props.closeContainerPopUp('ATTACH_ROUTING');
    }
    render() {
        
        return (
            <div style={{ maxHeight: '50vh' }}>
                <PopupBody>
                    <div className="pad-md pad-b-xs">
                  
                    <Row>
                            <Col xs="11">
                              <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.awbno" />
                              </Label>
                             <IAwbNumber reducerName="awbReducer"  hideLabel={true} form="true" />
                                        </Col>
                            <Col xs="6">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.agentcode" />
                                    </Label> 
                                    <div className="form-control">{this.props.initialValues.agentCode}</div>
                                </div>
                                        </Col>
                            <Col>
                                <div className="mar-t-md">
                                                    <IButton category="primary" className="m-r-10" bType="LIST" accesskey="L" onClick={this.props.listAwbDetails}>List</IButton>
                                               
                                                    <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.props.clearAttachAwbPanel}>Clear</IButton>
                                </div>
                            </Col>
                                            </Row>
                                            
                        <Row>
                            <Col xs="4">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.origin" />
                                    </Label> 
                                <div className="mar-b-xs">{this.props.initialValues.origin}</div>
                                        </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.destination" />
                                    </Label> 
                                <div className="mar-b-xs">{this.props.initialValues.destination}</div>
                                </Col>
                            <Col xs="8">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.shipmentpieces" />
                                    </Label> 
                                <div className="mar-b-xs">{this.props.initialValues.standardPieces}</div>
                            </Col>
                            <Col xs="8">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.shipmentweight" />
                                    </Label> 
                                <div className="mar-b-xs">{this.props.initialValues.standardWeight}</div>
                            </Col>
                            <Col>
                                <Label className="form-control-label">
                                         <IMessage msgkey="mail.operations.mailinbound.shipmentdescription" />
                                    </Label> 
                                <div className="mar-b-xs">{this.props.initialValues.shipmentDescription}</div>
                            </Col>
                            </Row>
                            </div>
                   
                            
                               
                 
                        
                  
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" disabled={!this.props.awbListDisable} onClick={this.onSaveAttachAwb}>Save</IButton>
                    <IButton color="secondary" onClick={this.closeAttachAwb}>Cancel</IButton>
                </PopupFooter>  
            </div>
        )
    }
}

AttachAwbPanel.propTypes = {
    from: PropTypes.string,
    onSaveAttachAwb: PropTypes.func,
    closeMailPopUp: PropTypes.func,
    closeContainerPopUp: PropTypes.func,
    initialValues:PropTypes.object,
    listAwbDetails:PropTypes.func,
    clearAttachAwbPanel:PropTypes.func,
    awbListDisable:PropTypes.bool
}

export default icpopup(wrapForm('attachAwbDetails')(AttachAwbPanel), { title: 'Attach Awb', maxWidth: '1200px !important', width: '1200px !important' })



