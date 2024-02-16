import React, { Component, Fragment } from 'react';
import { ITextField, ISelect,ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col,Label} from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
class CarditFilterPanel extends Component {
    constructor(props) {
        super(props);
        this.dateRange='MailOutboundCarditDateRange-MailbagIdOrConsignmentNotPresent'
    }
    computeDateRange (formValues) {     
        if(formValues.mailbagId && formValues.mailbagId.length >0) {
            this.dateRange = 'MailOutboundCarditDateRange-MailbagIdOrConsignmentPresent'
        } else if(formValues.conDocNo && formValues.conDocNo.length> 0){
            this.dateRange = 'MailOutboundCarditDateRange-MailbagIdOrConsignmentPresent'
        }
    }

    render() {
        let category = [];
        let status = [];
        let flightType = [];
        // let selectedStatus = "";
        if (!isEmpty(this.props.oneTimeValues)) {
            status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => { if (value.fieldValue === 'ACP' || value.fieldValue === 'CAP') return value });
            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            flightType = this.props.oneTimeValues['mailtracking.defaults.carditenquiry.flighttype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        if (this.props.screenMode === 'display') {
            if (this.props.filterValues.mailCategoryCode && this.props.filterValues.mailCategoryCode.length > 0) {
            //    let selectedCategory = category.find((element) => { return element.value === this.props.filterValues.mailCategoryCode });

            }
            if (this.props.filterValues.mailStatus && this.props.filterValues.mailStatus.length > 0) {
                // selectedStatus = status.find((element) => { return element.value === this.props.filterValues.mailStatus });
            //   let  selectedStatusLabel = selectedStatus.label;
            }

        }
        if(this.props.carditFilterFormValues && this.props.carditFilterFormValues.values) {
            this.computeDateRange(this.props.carditFilterFormValues.values)
        }
        
        return (
            <Fragment>
                 <Row>
                            <Col xs="12">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.mailbagid" />
                            </Label>
                         <ITextField mode="edit" name="mailbagId" type="text" uppercase={true}  componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                                </div>
                            </Col>

                            {/* commented by A-7815 as part of IASCB-37980
                             <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originoe" />
                            </Label>
                                    <Lov name="ooe" uppercase={true}  lovTitle="Origin OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                                </div>
                            </Col> */}
                            {/* <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destinationoe" />
                            </Label>
                                    <Lov name="doe" uppercase={true}  lovTitle="Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE" />
                                </div>
                            </Col> */}
                            {/* <Col xs="8">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.catgeory" />
                            </Label>
                                    <ISelect name="mailCategoryCode" defaultOption={true} options={category} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                                </div>
                            </Col> */}
                            {/* <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.subclass" />
                            </Label>
                                    <Lov name="mailSubclass" uppercase={true}  lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS" />
                                </div>
                            </Col> */}
                            {/* <Col xs="2">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.year" />
                            </Label>
                                    <ITextField mode="edit" name="year" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR" maxlength="1" ></ITextField>
                                </div>
                            </Col> */}
                            {/* <Col xs="4">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.dsn" />
                            </Label>
                                    <ITextField mode="edit" name="despatchSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DSN" maxlength="4"></ITextField>
                                </div>
                            </Col> */}
                            {/* <Col xs="4">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.rsn" />
                            </Label>
                                    <ITextField mode="edit" name="receptacleSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RSN" maxlength="3" ></ITextField>
                                </div>
                            </Col> */}
                            <Col xs="8">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.consignmentnumber" />
                            </Label>
                                    <ITextField mode="edit" uppercase={true}  name="conDocNo" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO" maxlength="35"></ITextField>
                                </div>
                            </Col>
                            {/* <Col xs="8">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.consignmentdate" />
                            </Label>
                                    <DatePicker name="consignmentDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTDATE" />
                                </div>
                            </Col> */}
                            <Col xs="8">
                                <div className="form-group mandatory">
                            <Label className="form-control-label">
                            Consignment <IMessage msgkey="mail.operations.mailoutbound.fromdate" />
                            </Label>
                            <DatePicker name="fromDate" dateFieldId={this.dateRange} type="from" toDateName="toDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATEFROM" />
                                </div>
                            </Col>
                            <Col xs="8">
                                <div className="form-group mandatory">
                            <Label className="form-control-label">
                            Consignment <IMessage msgkey="mail.operations.mailoutbound.todate" />
                            </Label>
                                    <DatePicker name="toDate" dateFieldId={this.dateRange} type="to" fromDateName="fromDate"  componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATETO" />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.pacode" />
                            </Label>
                                    <Lov name="paCode" uppercase={true} lovTitle="PA Code" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_PACODE" />
                                </div>
                            </Col>
                            {/* <Col xs="auto">
                                <div className="mar-t-md">
                                    <ICheckbox name="pendingResditChecked" label="Pending Resdit Events" />
                                </div>
                            </Col> */}
                            {/* <Col xs="12">
                                <div className="form-group">
                                    <IFlightNumber mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTNO"></IFlightNumber>
                                </div>
                            </Col> */}
                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.upliftairport" />
                            </Label>
                                    <Lov name="airportCode" uppercase={true}  lovTitle="uplift airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                                    <IUldNumber name="uldNumber" mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_ULDNO" />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originairport" />
                            </Label>
                                    <Lov name="mailOrigin" uppercase={true} lovTitle="Origin airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destairport" />
                            </Label>
                                    <Lov name="mailDestination" uppercase={true} lovTitle="Dest uplift airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILDESTINATION" />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.status" />
                            </Label>
                                    <ISelect name="mailStatus" defaultOption={true} options={status} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILSTATUS" />
                                </div>
                            </Col>

                            <Col xs="6">
                                <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.flighttype" />
                            </Label>
                                    <ISelect name="flightType" defaultOption={true} options={flightType} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTTYPE" />
                                </div>
                            </Col>

                            {/* <Col xs="6">
                                <div className="form-group">
                                    <label className="form-control-label ">RDT</label>
                                    <DatePicker name="reqDeliveryDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group mar-t-md">
                                    <TimePicker name="reqDeliveryTime" />
                                </div>
                            </Col> */}
                            <Col xs="12" md="12">
                                <div className="form-group">
                                    <IFlightNumber mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTNO"></IFlightNumber>
                                </div>
                            </Col>
                            {/* <Col xs="10">
                                <div className="form-group">
                                    <label className="form-control-label" >AWB No</label>
                                    <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                                </div>
                            </Col> */}


                        </Row>
            </Fragment>
        )
    }
}
CarditFilterPanel.propTypes={
    oneTimeValues: PropTypes.object,
    screenMode:PropTypes.string,
    filterValues:PropTypes.object
}
export default wrapForm('carditFilter')(CarditFilterPanel)
