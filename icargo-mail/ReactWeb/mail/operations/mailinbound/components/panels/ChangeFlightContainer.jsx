import React from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import {  Col, Label, Row } from 'reactstrap'
import {  IButton, ITextArea,ITextField } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';


class ChangeFlightContainer extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

        }


    }



    render() {
        return (
            <div>
                <PopupBody>
                <div className="pad-md"> 
                    <Row>
                            <Col xs="20">
                                <IFlightNumber mode="edit"></IFlightNumber>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                                    </Label>
                       
                            {/* <DatePicker name="date" /> */}
                            {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="date" disabled={true}/>:<DatePicker name="date" />}
                          
                                </div>
                        </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scantime" />
                                    </Label>
                            <TimePicker name="time"  disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                                </div>
                            </Col>
                            <Col xs="24">
                                <Label className="form-control-label">
                                <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.remarks" />
                                    </Label>
                            <ITextArea name="remarks" />
                            </Col>
                        </Row>
                </div>

            </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.props.saveChangeFlight} >Save</IButton>{' '}
                    <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.clearChangeFlight} >Clear</IButton>{' '}
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.props.closeChangeFlight}>Close</IButton>{' '}
                </PopupFooter>
            </div>
        )
    }
}
ChangeFlightContainer.propTypes = {
    saveChangeFlight: PropTypes.func,
    closeChangeFlight: PropTypes.func
}
//export default icpopup(PopUpComponent, { title: 'Discrepancy' })
export default icpopup(wrapForm('ChangeFlightContainerDetails')(ChangeFlightContainer), { title: 'Change Flight'})



