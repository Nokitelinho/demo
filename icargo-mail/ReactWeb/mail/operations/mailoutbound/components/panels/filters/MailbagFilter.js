import React, { Fragment } from 'react';
import { Row, Col,Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import PropTypes from 'prop-types';
class MailbagFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
        this.status = [];
        this.init();
    } 
    init() {
		if (!isEmpty(this.props.oneTimeValues)) {
			this.status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {
				if (value.fieldValue == 'ACP' ||  value.fieldValue == 'ARR'
					|| value.fieldValue == 'ASG' || value.fieldValue == 'DMG'
                    || value.fieldValue == 'DLV' || value.fieldValue == 'OFL'
                    || value.fieldValue == 'RTN' || value.fieldValue == 'TRA'
                    )
					return value
            });
            this.status = this.status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    }  
    render() {
        let category = [];
        let registeredorinsuredcode = [];
        let highestnumbermail = []
        // let mailclass = []
        if (!isEmpty(this.props.oneTimeValues)) {
            registeredorinsuredcode = this.props.oneTimeValues['mailtracking.defaults.registeredorinsuredcode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            highestnumbermail = this.props.oneTimeValues['mailtracking.defaults.highestnumbermail'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            // mailclass = this.props.mailbagOneTimeValues['mailtracking.defaults.mailclass'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));


        }
        return (
            <Fragment>
                <Row>
                    <Col xs="12">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.mailbagid" />
                            </Label> 
                            <ITextField mode="edit" name="mailbagId" uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                        </div>
                    </Col>
                    {/* <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originoe" />
                            </Label>
                            <Lov name="ooe" lovTitle="Origin OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destinationoe" />
                            </Label> 
                            <Lov name="doe" uppercase={true} lovTitle="Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col> */}
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.originairport" />
                            </Label>
                            <Lov name="mailOrigin" lovTitle="Origin airport" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.destairport" />
                            </Label> 
                            <Lov name="mailDestination" uppercase={true} lovTitle="Dest uplift airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.catgeory" />
                            </Label> 
                            <ISelect name="mailCategoryCode" defaultOption={true} options={category} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.subclass" />
                            </Label> 
                            <Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    {/* <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.year" />
                            </Label> 
                            <ITextField mode="edit" name="year" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR"></ITextField>
                        </div>
                    </Col> */}
                    <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.dsn" />
                            </Label> 
                            <ITextField mode="edit" name="despatchSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DSN"></ITextField>
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.rsn" />
                            </Label> 
                            <ITextField mode="edit" name="receptacleSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RSN"></ITextField>
                        </div>
                    </Col>
                    {/* <Col xs="5">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.hni" />
                            </Label> 
                            <ISelect name="highestNumberedReceptacle"  defaultOption={true} mode="edit" options={highestnumbermail} />
                        </div>
                    </Col>
                    <Col xs="5">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.ri" />
                            </Label> 
                            <ISelect name="registeredOrInsuredIndicator" defaultOption={true}  mode="edit" options={registeredorinsuredcode} />
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.weight" />
                            </Label> 
                            <ITextField name="mailbagWeight" mode="edit" />
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.volume" />
                            </Label> 
                            <ITextField name="volume" mode="edit" />
                        </div>
                    </Col> */}
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.transfercarrier" />
                            </Label> 
                            <Lov name="transferFromCarrier" lovTitle="TransferCarrier" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                        </div>
                    </Col>
                    {/* <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.sealnumber" />
                            </Label> 
                            <ITextField name="sealNumber" mode="edit" />
                        </div>
                    </Col> */}
                    <Col xs="10">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.awbno" />
                            </Label>
                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.pacode" />
                            </Label>
                            <Lov name="paCode" lovTitle="PA Code" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                        </div>
                    </Col>
                    <Col xs="8">
						<div className="form-group">
							<label className="form-control-label ">Mailbag Status</label>
							<ISelect defaultOption={true} name="mailStatus" options={this.status} componentId="CMP_Mail_Operations_MailBagEnquiry_currentStatus" />
						</div>
					</Col>
                    {/* <Col xs="auto">
                        <div className="mar-b-xs">
                            <ICheckbox label="Routing Info available" name="routingInfo" />
                        </div>
                    </Col> */}
                    {/* <Col xs="auto">
                        <div className="mar-b-xs">
                            <ICheckbox label="PLT" name="plt" />
                        </div>
                    </Col> */}     
                      
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.consignmentnumber" />
                            </Label>
                            <ITextField mode="edit" uppercase={true} name="consigmentNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO" maxlength="35"></ITextField>
                        </div>
                    </Col> 
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                              Consignment Date
                            </Label>
                            <DatePicker name="consignmentDate"   componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATETO" />
                             </div>
                    </Col> 
                    <Col xs="8">
                        <div className="form-group">
                            <Label className="form-control-label">
                            RDT
                            </Label>
                            <DatePicker name="rdtDate"   componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                             </div>
                    </Col>
                    <Col xs="auto">
                        <div className="mar-b-xs">
                            <ICheckbox label="Cardit Available" name="carditAvailable" />
                        </div>
                    </Col>
                    <Col xs="auto">
                        <div className="mar-b-xs">
                            <ICheckbox label="Damaged" name="damaged" />
                        </div>
                    </Col>
                </Row>
            </Fragment>
        )
    }
}
MailbagFilter.propTypes = {
    mailbagOneTimeValues:PropTypes.object
}

export default wrapForm('mailbagFilter')(MailbagFilter)
