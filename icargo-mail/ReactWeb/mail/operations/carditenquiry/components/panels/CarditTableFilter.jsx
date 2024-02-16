import React, { Fragment } from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { Row, Col, Container } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';

class CarditTableFilter extends React.PureComponent { 
    constructor(props) {
        super(props);
    }

    render() {
        let category = [];
        let status = [];

        if (!isEmpty(this.props.oneTimeValues)) {
            status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => { if (value.fieldValue === 'ACP' || value.fieldValue === 'CAP') return value });
            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }


        return (
            <Fragment>
                <Row>
                    <Col xs="12">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.mailbagId" /></label>
                            <ITextField mode="edit" name="mailbagId"  uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.ooe" /></label>
                            <Lov name="ooe" lovTitle="Origin OE"  uppercase={true}  dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.doe" /></label>
                            <Lov name="doe" lovTitle="Destination OE"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.category" /></label>
                            <ISelect name="mailCategoryCode" uppercase={true} options={category} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.subclass" /></label>
                            <Lov name="mailSubclass" lovTitle="Subclass"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS" />
                        </div>
                    </Col>
                    <Col xs="2">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.year" /></label>
                            <ITextField mode="edit" name="year" uppercase={true}  type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR"></ITextField>
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.dsn" /></label>
                            <ITextField mode="edit" name="despatchSerialNumber" uppercase={true}  type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DSN"></ITextField>
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.rsn" /></label>
                            <ITextField mode="edit" name="receptacleSerialNumber"  uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RSN"></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.consignmentNo" /></label>
                            <ITextField mode="edit" name="consignmentNumber"  uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO"></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">RDT</label>
                            <DatePicker name="reqDeliveryDate"  uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group mar-t-md">
                            <TimePicker name="reqDeliveryTime"  uppercase={true} className="mar-t-md" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDTTIME"/>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.pacode" /></label>
                            <Lov name="paCode" lovTitle="PA Code"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_PACODE" />
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label ">HNI</label>
                            <ITextField mode="edit"  uppercase={true} name="highestNumberedReceptacle" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_HNI" ></ITextField>
                            </div>
                    </Col>
                   <Col xs="12">
                        <div className="form-group">
                            <label className="form-control-label" >AWB No</label>
                            <IAwbNumber reducerName="awbReducer"  uppercase={true} hideLabel={true} form="true" />
                        </div>
                    </Col>
                    
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label ">Consignment Date</label>
                            <DatePicker name="consignmentDate"  uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTDATE" />
                        </div>
                    </Col>
                    <Col xs="12">
                        <div className="form-group">
                            <IFlightNumber {...this.props.tableFilter.flightnumber}  uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTNO" mode="edit" ></IFlightNumber>
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.upliftAirport" /></label>
                            <Lov name="upliftAirport" lovTitle="uplift airport"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                             <label className="form-control-label" >ULD No</label>
                            <ITextField mode="edit" name="uldNumber"  uppercase={true} type="text" ></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.originAirport" /></label>
                            <Lov name="mailorigin" lovTitle="Origin airport"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.destAirport" /></label>
                            <Lov name="mailDestination" lovTitle="Dest uplift airport"  uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILDESTINATION" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.mailStatus" /></label>
                            <ISelect name="accepted" options={status}  uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILSTATUS" />
                        </div>
                    </Col>
                </Row>
            </Fragment>
        )
    }
}
export default wrapForm('carditTableFilter')(CarditTableFilter)
