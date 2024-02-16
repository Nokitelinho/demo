import React from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Col, Label, Row } from 'reactstrap'
import { IButton, IRadio, ITextArea, ITextField } from 'icoreact/lib/ico/framework/html/elements';
import OnwardRouting from './OnwardRouting.jsx';
import ContainerDetails from './ContainerDetails.jsx';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { IAccordion, IAccordionItem, IAccordionItemTitle, IAccordionItemBody } from 'icoreact/lib/ico/framework/component/common/accordion';
class ReassignPanel extends React.Component {

    constructor(props) {
        super(props);
        this.reassignContainer = this.reassignContainer.bind(this);
        this.state = {
            reassignFilterType: "C"
        }
    }

    onFilterChange = (event) => {
        this.setState({ reassignFilterType: event.target.value })
    }

    reassignContainer() {
        this.props.reassignContainer();
    }

    onClose = () => {
        this.props.onClose();
    }

    render() {
        return (

            <div>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="6">
                                <div className="form-group">
                                    <label className="form-control-label ">Carrier Code</label>
                                    <Lov name="flightCarrierCode" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_CARRIERLOV" lovTitle="Carrier Code" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" />

                                </div>
                            </Col>
                            <Col xs="5">
                                <div className="form-group">
                                    <Label className="form-control-label">Scan Date</Label>
                                    {isSubGroupEnabled('AA_SPECIFIC')?  <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                                  
                                    
                                </div>
                            </Col>
                            <Col xs="5">
                                <div className="form-group">
                                    <Label className="form-control-label">Scan Time</Label>
                                    <TimePicker name="mailScanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                               
                                </div>
                            </Col>
                        </Row>
                        <Row>
                           
                          
                            <Col xs="24">
                                <Label>Remarks</Label>
                                <ITextField name="remarks" uppercase={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_REMARKS"/>
                            </Col>
                        </Row>
                    </div>
                    <IAccordion>
                    <IAccordionItem expanded>
                                <IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i>Container Details</div></IAccordionItemTitle>
                                <IAccordionItemBody>
                                    <div className="pad-md">
                                        <Row>
                                        <Col xs="3">
                                            </Col>
                                            <Col xs="5">
                                                <Label className="form-control-label">Container</Label>
                                            </Col>
                                            <Col xs="3">
                                            </Col>
                                           
                                            <Col xs="4">
                                                <Label className="form-control-label">Destination</Label>
                                            </Col>
                                            <Col xs="4">
                                                <Label className="form-control-label">Actual Weight</Label>
                                            </Col>
                                            <Col xs="5">
                                                <Label className="form-control-label">Content ID</Label>
                                            </Col>
                                        </Row>
                                        <ContainerDetails oneTimeValues={this.props.oneTimeValues} uldToBarrowAllow={this.props.uldToBarrowAllow} initialValues={this.props.containerDetailsForPopUp} />
                                    
                                        </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                            </IAccordion>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.reassignContainer}>Save</IButton>
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.onClose}>Close</IButton>
                </PopupFooter>
            </div>
        )
    }
}


export default icpopup(wrapForm('reassignContainer')(ReassignPanel), { title: 'Reassign To Carrier', className: 'modal_700px' })
