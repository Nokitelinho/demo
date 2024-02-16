import React from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Col, Label, Row } from 'reactstrap'
import { IButton,ITextField } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class ReadyForDeliveryPanel extends React.Component {

    constructor(props) {
        super(props);
 
        this.state = {
            
        }
    }

   
    saveReadyFordeliver=()=>{
        this.props.saveReadyFordeliver();
    }
   
    render() {
        return (

            <div>
                <PopupBody>
                <div className="pad-md"> 

                    <Row>
                            <Col xs="8">
                                <div className="form-group m-0">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                                    </Label>
                            {/* <DatePicker name="date" /> */}
                            {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="date" disabled={true}/>:<DatePicker name="date" />}
                           
                                </div>
                        </Col>
                            <Col xs="8">
                                <div className="form-group m-0">
                                <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scantime" />
                                    </Label>
                            <TimePicker name="time" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false} />
                                </div>
                            </Col>
                        </Row>
                </div>

            </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.saveReadyFordeliver} >Save</IButton>
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.props.toggle}>Close</IButton>
                </PopupFooter>
            </div>

        )
    }
}

ReadyForDeliveryPanel.propTypes = {
    displayMode: PropTypes.string,
    toggle: PropTypes.func,
    saveReadyFordeliver:PropTypes.func,
}
export default icpopup(wrapForm('ReadyForDeliveryForm')(ReadyForDeliveryPanel), { title: 'Ready For Delivery' })


