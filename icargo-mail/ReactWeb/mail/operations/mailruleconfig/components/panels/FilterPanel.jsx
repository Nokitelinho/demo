import React, { PureComponent, Fragment } from 'react';
import { Field } from 'redux-form';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, ISelect, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
class FilterPanel extends PureComponent {

    constructor(props) {
        super(props);
    }
    changeScreenMode = (event) => {
        this.props.onChangeScreenMode((this.props.screenMode === SCREEN_MODE.EDIT) ?
            SCREEN_MODE.DISPLAY : SCREEN_MODE.EDIT);
    }

    submitFilter = () => {
        this.props.submitFilter();
    }

    clearFilter = () => {
        this.props.reset();
        this.props.onClearFilter();
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

        let status = [{ label: 'Select', value: '' },
        { label: 'Active', value: 'Active' },
        { label: 'Inactive', value: 'Inactive' }
        ];

        const mainFilter =
            (this.props.screenMode === SCREEN_MODE.EDIT || this.props.screenMode === SCREEN_MODE.INITIAL) ?
                (<Fragment>

                    <div className="header-filter-panel flippane">
                            <div className="pad-md pad-b-3xs">

                                <Row>

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
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

                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label ">
                                                <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.catgeory" />
                                            </label>
                                            <ISelect name="categoryCode"
                                                defaultOption={true} options={mailCategory}
                                                componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                                        </div>
                                    </Col>

                                    <Col xs="4">
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

                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label ">
                                                <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.status" />
                                            </label>
                                            <ISelect name="status"
                                                defaultOption={true} options={status}
                                                componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                                        </div>
                                    </Col>
                                     <Col xs="4">
                                    <div className="form-group">
                                        <label className="form-control-label">
                                            <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.fromdate" /></label>
                                        
                                                <DatePicker value={new Date()} name="fromDate"  type="from" toDateName="toDate" id="fromDate" />
                                            
                                    </div>
                                </Col>
                                <Col xs="4" >
                                    <div className="form-group">
                                        <label className="form-control-label">
                                            <IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.todate" /></label>
                                        
                                                <DatePicker name="toDate"  type="to" fromDateName="fromDate" id="toDate" />
                                            
                                    </div>
                                </Col>
                                </Row>

                            </div>
                            <div className="btn-row">
                                <IButton category="primary" componentId='OPERATIONS_SHIPMENT_EDIAGREEMENT299_LIST' onClick={this.submitFilter}>
                                    <IMessage
                                        msgkey="operations.shipment.ediagreement299.label.list"
                                        defaultMessage="List" />
                                </IButton>
                                <IButton category="default" componentId='OPERATIONS_SHIPMENT_EDIAGREEMENT299_CLEAR' onClick={this.clearFilter}>

                                    <IMessage
                                        msgkey="operations.shipment.ediagreement299.label.clear"
                                        defaultMessage="Clear" />
                                </IButton>

                            </div>
                            {(this.props.screenMode === SCREEN_MODE.EDIT) &&
                                <i className="icon ico-close-fade flipper flipper-ico" onClick={this.changeScreenMode}></i>}
                        </div>
                        </Fragment>
               )
                :

                (<Fragment>
                        <div className="header-summary-panel flippane">
                            <div className="pad-md">

                                { /* Place for adding filter summary --start */}
                                <Row>

                                    {this.props.screenFilter.ooe && this.props.screenFilter.ooe.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.ooe" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.ooe}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.originAirport && this.props.screenFilter.originAirport.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.origin" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.originAirport}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.subclass && this.props.screenFilter.subclass.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.subclass" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.subclass}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.contractCarrier && this.props.screenFilter.contractCarrier.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.carriercode" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.contractCarrier}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.xxresdit && this.props.screenFilter.xxresdit.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.xxresdit" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.xxresdit}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.doe && this.props.screenFilter.doe.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.doe" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.doe}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.destinationAirport && this.props.screenFilter.destinationAirport.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.destn" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.destinationAirport}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.categoryCode && this.props.screenFilter.categoryCode.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.catgeory" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.categoryCode}</div>
                                        </Col> : ""
                                    }

                                    {this.props.screenFilter.mailboxId && this.props.screenFilter.mailboxId.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.mailboxid" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.mailboxId}</div>
                                        </Col> : ""
                                    }

                                     {this.props.screenFilter.status && this.props.screenFilter.status.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.status" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.status}</div>
                                        </Col> : ""
                                    }
                                     {this.props.screenFilter.fromDate && this.props.screenFilter.fromDate.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.fromdate" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.fromDate}</div>
                                        </Col> : ""
                                    }
                                     {this.props.screenFilter.toDate && this.props.screenFilter.toDate.length > 0 ?
                                        <Col xs="4">
                                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.messageruleconfig.lbl.todate" /></label>
                                            <div className="form-control-data"> {this.props.screenFilter.toDate}</div>
                                        </Col> : ""
                                    }
                                </Row>
                                { /* Place for adding filter summary --start */}

                            </div>
                            <i className="icon ico-pencil-rounded-orange flipper flipper-ico"
                                onClick={this.changeScreenMode}></i>
                        </div>
                        </Fragment>)

        return [mainFilter]
    }

}

export default wrapForm('filterPanelForm')(FilterPanel);

FilterPanel.propTypes = {
    onChangeScreenMode: PropTypes.func,
    submitFilter: PropTypes.func,
    clearFilter: PropTypes.func,
    screenMode: PropTypes.string,
}


