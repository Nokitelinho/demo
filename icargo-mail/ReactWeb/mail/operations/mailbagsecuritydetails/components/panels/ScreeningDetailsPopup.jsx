import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Row, Input, Label } from 'reactstrap'
import { ITextField, ISelect, IButton, ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import listmailbagReducer from '../../reducers/listmailbagreducer';
import filterReducer from '../../reducers/filterreducer';



class ScreeningDetailsPopup extends React.PureComponent {

    constructor(props) {
        super(props);

        this.onsaveNewScreeningDetails = this.onsaveNewScreeningDetails.bind(this);
    }
    onsaveNewScreeningDetails() {
        this.props.onsaveNewScreeningDetails();
    }

    render() {
        let status = []
            if(!isEmpty(this.props.oneTimeValues)){
            status = this.props.oneTimeValues['mail.operations.results'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        let method = []
            if(!isEmpty(this.props.oneTimeValues)){
            method = this.props.oneTimeValues['mail.operations.screeningmethod'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="8">
                                <div class="form-group">
                                    <Label className="form-control-label">Screened Location</Label>

                                 <ITextField mode="edit" name="screeningLocation" type="text"   componentId="TXT_MAIL_OPERATIONS_MAILBAG_SECURITY_SCREENED_LOCATION" class="form-control" uppercase={true} disabled={true}></ITextField>
                                </div>
                            </Col>
                            <Col xs="8">
                                <div class="form-group">
                                    <Label className="form-control-label">Method</Label>
                                    <ISelect name='screeningMethodCode' options={method} />
                                </div>
                            </Col>
                            <Col xs="8">
                                <div class="form-group">
                                    <Label className="form-control-label">Security Status Issued by</Label>
                                    <ITextField mode="edit" name="securityStatusParty" type="text" componentId="TXT_MAIL_OPERATIONS_MAILBAG_SECURITY_ISSUED_BY" class="form-control" uppercase={true} maxlength="20"></ITextField>
                                </div>
                            </Col>

                        </Row>
                        <Row>
                            <Col xs="8">
                                <Label className="form-control-label">Date</Label>
                                <DatePicker name="securityStatusDate" />
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">Time</Label>
                                <TimePicker name="time" />
                            </Col>

                            <Col xs="8">

                                <Label className="form-control-label">Status</Label>
                                <ISelect name="result" options={status} />
                            </Col>
                        </Row>
                    </div>

                </PopupBody>
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onsaveNewScreeningDetails} >Save</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>
            </Fragment>

        )
    }

}


export default icpopup(wrapForm('screeningDetailsPopupForm')(ScreeningDetailsPopup), { title: 'Add New Screening Details', className: 'modal_550px' })