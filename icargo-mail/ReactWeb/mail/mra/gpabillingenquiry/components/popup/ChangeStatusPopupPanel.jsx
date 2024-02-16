import React, { Component } from 'react';
import icpopup, { PopupFooter, PopupBody,PopupHeader } from 'icoreact/lib/ico/framework/component/common/modal';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { ITextArea } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Row, Col} from "reactstrap";
import PropTypes from 'prop-types';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
export class ChangeStatusPopupPanel extends Component {
    constructor(props) {
        super(props)

    }
    saveChangeStatusPopup = () =>{
        this.props.saveChangeStatusPopup();
    }
    closeChangeStatusPopup = () =>{ 
        this.props.closeChangeStatusPopup(); 
    }
    render() {
             let status=[];
             let dsn = this.props.dsn;
             if (!isEmpty(this.props.oneTimeValuesStatus)) {
                 status =this.props.oneTimeValuesStatus['mailtracking.mra.gpabilling.gpabillingstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
             }
        return (
            <div> 
                <PopupBody >
                    <div className="card mar-sm">
                        <div className="card-body">
                             <Row>
                                <Col xs="4">
                                    <label className="form-control-label">DSN</label>
                                    <div className="form-control-data" name="dsnNumber">{dsn}</div>
                                </Col>                                 
                             </Row>
                        </div>
                    </div>
                    <div className="card mar-sm">
                        <div className="card-body">
                            <Row>
                               <Col xs="8"> 
                               <label className="form-control-label">Billing Status</label>
                               <ISelect  name="billingStatus" options={status} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_BILLINGSTATUS"/>
                               </Col> 
                            </Row>
                        </div>
                    </div>
                    <div className="card mar-sm">
                        <div className="card-body">
                            <Row>
                               <Col xs="4"> 
                               <label className="form-control-label">Remarks</label>
                                <ITextArea className="textarea" name="remarks" id="groupRemarks" style={{ "width":"400px" }}></ITextArea>
                               </Col> 
                            </Row>
                        </div>
                    </div>
                </PopupBody >
                <PopupFooter >
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.saveChangeStatusPopup}>Save</IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.closeChangeStatusPopup}>Close</IButton>
                </PopupFooter >
            </div>
        );
    }
}
ChangeStatusPopupPanel.propTypes = {
   closeChangeStatusPopup: PropTypes.func,
   saveChangeStatusPopup: PropTypes.func
}
export default icpopup(wrapForm('statusMailForm')(ChangeStatusPopupPanel), { title: 'Change Status'});