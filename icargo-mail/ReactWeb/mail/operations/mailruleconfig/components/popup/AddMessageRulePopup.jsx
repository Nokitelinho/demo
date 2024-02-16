import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Col, Row, Input, Label } from 'reactstrap'
import { IButton, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import PropTypes from 'prop-types';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'

class AddMessageRulePopup extends React.PureComponent {

    constructor(props) {
        super(props);
    }

    render() {
        let mailCategory = [];
        if (!isEmpty(this.props.oneTimeMap)) {
            mailCategory = this.props.oneTimeMap['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        let xxresdit = [{ label: 'Select', value: '' },
        { label: 'Yes', value: 'Yes' },
        { label: 'No', value: 'No' }
        ];
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.ooe" />
                                    </label>
                                    <Lov name="ooe" lovTitle="Origin OE"
                                        uppercase={true} dialogWidth="600" dialogHeight="473"
                                        actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.origin" />
                                    </label>
                                    <Lov name="originAirport" lovTitle="Origin Airport"
                                        maxlength="3" uppercase={true} dialogWidth="600"
                                        dialogHeight="520"
                                        actionUrl="ux.showAirport.do?formCount=1"
                                        componentId="CMP_Mail_Operations_MailBagEnquiry_mailorigin" />
                                </div>

                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.subclass" />
                                    </label>
                                    <Lov name="subclass" lovTitle="Subclass"
                                        uppercase={true} dialogWidth="600" dialogHeight="425"
                                        maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS" />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.carriercode" />
                                    </label>
                                    <Lov name="contractCarrier" lovTitle="CarrierCode"
                                        dialogWidth="600" dialogHeight="425"
                                        actionUrl="ux.showAirline.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT"
                                        uppercase={true} />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.xxresdit" />
                                    </label>
                                    <ISelect name="xxresdit"
                                        options={xxresdit}
                                        defaultOption={true}
                                        componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.doe" />
                                    </label>
                                    <Lov name="doe" lovTitle="Destination OE"
                                        uppercase={true} dialogWidth="600" dialogHeight="473"
                                        actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.destn" />
                                    </label>
                                    <Lov name="destinationAirport" lovTitle="Destination Airport"
                                        maxlength="3" uppercase={true} dialogWidth="600" dialogHeight="520"
                                        actionUrl="ux.showAirport.do?formCount=1"
                                        componentId="CMP_Mail_Operations_MailBagEnquiry_maildestination" />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.catgeory" />
                                    </label>
                                    <ISelect name="categoryCode"
                                        defaultOption={true} options={mailCategory}
                                        componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                                </div>
                            </Col>

                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">
                                        <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.mailboxid" />
                                    </label>
                                    <Lov name="mailboxId" lovTitle="MailboxID" dialogWidth="600"
                                        dialogHeight="520" actionUrl="mailtracking.defaults.ux.mailidlov.list.do?formCount=1"
                                        componentId="CMP_Mail_Operations_MailBagEnquiry_port"
                                        uppercase={true} />
                                </div>
                            </Col>
                            <Col xs="8">
                                    <div className="form-group">
                                        <label className="form-control-label">
                                            <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.fromdate" /></label>
                                        
                                                <DatePicker value={new Date()} name="fromDate"  type="from" toDateName="toDate" id="fromDate" />
                                            
                                    </div>
                                </Col>
                                <Col xs="8" >
                                    <div className="form-group">
                                        <label className="form-control-label">
                                            <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.todate" /></label>
                                        
                                                <DatePicker name="toDate"  type="to" fromDateName="fromDate" id="toDate" />
                                            
                                    </div>
                                </Col>
                        </Row>
                    </div>
                </PopupBody>
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.props.saveMailRuleConfig}>Save</IButton>{' '}
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

AddMessageRulePopup.propTypes = {
    saveMailRuleConfig: PropTypes.func,
    toggleFn: PropTypes.func,
    oneTimeMap:PropTypes.object
}

export default icpopup(wrapForm('addMessageRuleForm')(AddMessageRulePopup), { title: 'Add Mail Rules' })
