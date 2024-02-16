import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Label, Row } from 'reactstrap'
import { IButton, IRadio, ITextArea } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { ISelect, ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import RSNPanel from './RSNPanel.jsx';

class AddMultiplePanel extends React.Component {

    constructor(props) {
        super(props);
        this.newRSN = this.newRSN.bind(this);
    }

    newRSN() {
        this.props.getAddMultipleData();
        this.props.newRSN(this.props.addMultipleData, this.props.rsnData);
    }

    onClose = () => {
        this.props.onClose();
    }

    addMultiple = () => {
        this.props.addMultiple(this.props.addMultipleData, this.props.receptacles);
    }

    render() {
        let category = [];
        let rsn = [];
        let hni = [];
        let mailClassValue = [];

        if (!isEmpty(this.props.oneTimeCat)) {
            category = this.props.oneTimeCat.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeType)) {
            type = this.props.oneTimeType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeSubType)) {
            subType = this.props.oneTimeSubType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeRSN)) {
            rsn = this.props.oneTimeRSN.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeHNI)) {
            hni = this.props.oneTimeHNI.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeMailClass)) {
            mailClassValue = this.props.oneTimeMailClass.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }

        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">Origin OE</label>
                                    <Lov maxlength="6" name="originExchangeOffice" lovTitle="Origin OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" uppercase={true}/>
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group">
                                    <label className="form-control-label ">Dest OE</label>
                                    <Lov maxlength="6" name="destinationExchangeOffice" lovTitle="Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE" uppercase={true}/>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">Cat</label>
                                    <ISelect name="mailCategoryCode" options={category} searchable />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">Class</label>
                                    <ISelect name="class" options={mailClassValue} searchable/>
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                                    <label className="form-control-label ">SC</label>
                                    <Lov maxlength="2" name="mailSubclass" lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS" uppercase={true}/>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">Yr</label>
                                    <ITextField maxlength="1" mode="edit" name="year" type="text" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_CONTAINERNUMBER"></ITextField>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">DSN</label>
                                    <ITextField maxlength="4" mode="edit" name="dsn" type="text" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_CONTAINERNUMBER"></ITextField>
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                                    <label className="form-control-label ">HNI</label>
                                    <ISelect name="hni" options={hni} searchable/>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">RI</label>
                                    <ISelect name="ri" options={rsn} searchable/>
                                </div>
                            </Col>
                        </Row>
                        <div className="pad-b-2xs border-bottom mar-b-2sm font-weight-bold">RSN Range</div>
                        <RSNPanel receptacles={this.props.receptacles} calculateReseptacles={this.props.calculateReseptacles}
                            newRSN={this.props.newRSN} getAddMultipleData={this.props.getAddMultipleData} addMultipleData={this.props.addMultipleData} rsnData={this.props.rsnData} />
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.addMultiple} componentId="BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD" privilegeCheck={false}>Add</IButton>{' '}
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.onClose} componentId="BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_CLOSE" privilegeCheck={false}>Close</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}


export default icpopup(wrapForm('addMultiple')(AddMultiplePanel), { title: 'New Mail Details' })
