import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Row, Input, Label } from 'reactstrap'
import { ITextField, ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class ConsignorDetailsPopup extends React.PureComponent {

    constructor(props) {
        super(props);
        this.onsaveNewConsignorDetails = this.onsaveNewConsignorDetails.bind(this);
           }
    onsaveNewConsignorDetails() {
            this.props.onsaveNewConsignorDetails();
    }
    render() {

        let agentType = []
        if(!isEmpty(this.props.oneTimeValues)){
            agentType = this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        return (

            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="6">
                                <div class="form-group">
                                    <Label className="form-control-label">Agent Type</Label>
                                    <ISelect name='agenttype' options={agentType} />
                                </div>
                            </Col>

                            <Col xs="6">
                                <div class="form-group">
                                    <Label className="form-control-label">Agent Id</Label>
                                    <ITextField mode="edit" name="agentId" type="text" componentId="TXT_MAIL_OPERATIONS_MAILBAG_SECURITY_AGENT_ID" class="form-control" uppercase={true} maxlength="16"></ITextField>
                                </div>
                            </Col>
                               <Col xs="6">
                                <div class="form-group">
                                    <Label className="form-control-label">ISO Country Code</Label>
                                    <ITextField mode="edit" name="isoCountryCode" type="text" maxlength="2" componentId="TXT_MAIL_OPERATIONS_MAILBAG_SECURITY_ISO_COUNTRY_CODE" class="form-control" uppercase={true} ></ITextField>
                                </div>
                            </Col>
                            <Col xs="6">
                                <div class="form-group">
                                    <Label className="form-control-label">Expiry</Label>
                                    <ITextField mode="edit" name="expiryDate" type="text" maxlength="4" componentId="TXT_MAIL_OPERATIONS_MAILBAG_SECURITY_EXPIRY_DATE"  class="form-control" uppercase={true} ></ITextField>
                                </div>
                            </Col>


                        </Row>

                    </div>
                </PopupBody>
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onsaveNewConsignorDetails}>Save</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>
            </Fragment>


        )
    }
}

export default icpopup(wrapForm('consignorDetailsPopupForm')(ConsignorDetailsPopup), { title: 'Add New Consigner Details', className: 'modal_700px' })