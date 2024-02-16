import React from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Col, Label, Row } from 'reactstrap'
import { IButton, IRadio, ITextArea,ITextField } from 'icoreact/lib/ico/framework/html/elements';
import OnwardRouting from './OnwardRouting.jsx';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

/*Transfer to Carrier Panel used in container enquiry and Mail Inbound*/

class TransferPanel extends React.Component {

    constructor(props) {
        super(props);
        this.transferContainer = this.transferContainer.bind(this);
    }

    onFilterChange = (event) => {
        this.setState({ transferFilterType: event.target.value })
    }

    onClose = () => {
        this.props.onClose();
    }

    transferContainer() {
        this.props.transferContainer();
    }

    /*onClose = () => {
        closePopupWindow();
    }*/
    render() {
        return (

            <div>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="8">
                                    <div className="form-group">
                                        <label className="form-control-label">Carrier Code</label>
                                        <Lov name="carrier" lovTitle="Carrier Code" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" uppercase={true}/>
                                    </div>
                                </Col>
                            <Col xs="7">
                                <div className="form-group">
                                    <label className="form-control-label">Destination</label>
                                    <Lov name="destination" lovTitle="Destination airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" uppercase={true}/>
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">Scan Date</Label>
                                    {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                          
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">Scan Time</Label>
                                    <TimePicker name="mailScanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false} />
                                </div>
                            </Col>
                            <Col xs="24">
                                <Label>Remarks</Label>
                                <ITextArea name="remarks" />
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.transferContainer} >Save</IButton>{' '}
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.onClose}>Close</IButton>{' '}
                </PopupFooter>
            </div>

        )
    }
}


export default icpopup(wrapForm('transferForm')(TransferPanel), { title: 'Transfer Container' })
