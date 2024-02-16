import React, { Component, Fragment } from 'react';
import { ITextField, ISelect,ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';

class LyinglistFilterPanel extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let category = [];
        let status = [];
        let carditStat = [];
        let onTimeDeliveryStat = [];
        let serviceLevels = [];	
        if (!isEmpty(this.props.oneTimeValues)) {
            status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {
				if (value.fieldValue !== 'ASG' && value.fieldValue !== 'DMG'
					&& value.fieldValue !== 'CDT' && value.fieldValue !== 'MFT'
					&& (!value.fieldDescription.includes("Resdit")))
					return value
			});

            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            serviceLevels = this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        carditStat = [{ "label": "Available", "value": "Y" },
        { "label": "Not Available", "value": "N" }]
        
        onTimeDeliveryStat = [{ "label": "Yes", "value": "Y" },
		{ "label": "No", "value": "N" }]

        return (
            <Fragment>
               <Row>

              <Col xs="12">
              <div className="form-group">													
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.mailbagid" />
                            </Label>
                    <ITextField mode="edit" uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_MailbagId" name="mailbagId" type="text"></ITextField>
                </div>
              </Col>
              {/* <Col xs="6">
              <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.originoe" />
                            </Label>
                    <Lov name="ooe" lovTitle="Origin OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/>
												
              </div>
              </Col>
              <Col xs="6">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.destinationoe" />
                            </Label>
                            <Lov name="doe" lovTitle="Destination OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/>										
                </div>
              </Col> */}
              <Col xs="6">
              <div className="form-group">	
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.originairport" />
                            </Label>
                    <Lov name="mailOrigin" lovTitle="Origin airport" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1"/>
												
              </div>
              </Col>
              <Col xs="6">
                <div className="form-group">	
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.destairport" />
                            </Label>
                            <Lov name="mailDestination" lovTitle="Dest uplift airport" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1"/>										
                </div>
              </Col>
               <Col xs="6">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.catgeory" />
                            </Label>
                            <ISelect name="mailCategoryCode" defaultOption={true} options={category} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_category"  />
                </div>
               </Col>
               <Col xs="4">
                <div className="form-group">												
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.subclass" />
                            </Label>
                <Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" />
               </div>
              </Col>
             {/* <Col xs="2">
                <div className="form-group">											
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.year" />
                            </Label>
                <ITextField mode="edit" name="year" type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Year" ></ITextField>
                </div>
             </Col> */}
             <Col xs="4">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.dsn" />
                            </Label>
                <ITextField mode="edit" name="despatchSerialNumber" uppercase={true} type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Dsn"></ITextField>
                </div>
             </Col>
             <Col xs="4">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.rsn" />
                            </Label>											
                <ITextField mode="edit" name="receptacleSerialNumber" type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Rsn"></ITextField>
                </div>
             </Col>
             <Col xs="4">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.status" />
                            </Label>											
                <ISelect name="operationalStatus" defaultOption={true} options={status} componentId="CMP_Mail_Operations_MailBagEnquiry_currentStatus" />													
                </div>
              </Col>
              <Col xs="4">
                 <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.airport" />
                            </Label>	
                 <Lov name="scanPort" uppercase={true} componentId='CMP_Mail_Operations_MailBagEnquiry_port' lovTitle="Airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                 </div>
              </Col>
             <Col xs="8">
                <div className="form-group mandatory">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.fromdate" />
                            </Label>
                            <DatePicker name="fromDate" componentId='CMP_Mail_Operations_MailBagEnquiry_FromDate' />
                </div>
             </Col>
            <Col xs="8">
               <div className="form-group mandatory">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.todate" />
                            </Label>
                            <DatePicker name="toDate" componentId='CMP_Mail_Operations_MailBagEnquiry_ToDate'/>
               </div>
            </Col>
            {/* <Col xs="4">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.userid" />
                            </Label>
                <ITextField mode="edit" name="userID" type="text" uppercase={true} componentId='CMP_Mail_Operations_MailBagEnquiry_Userid'></ITextField>
                </div>
            </Col> */}
            <Col xs="4">
                <div className="mar-t-md">
                <ICheckbox name="damageFlag" label="Damaged" />											
                </div>
            </Col>
            <Col xs="4">
                <div className="mar-t-md">
                <ICheckbox name="transitFlag" label="Transit" />
                </div>
            </Col>
            <Col xs="8">
            <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.requireddeliverydate" />
                            </Label>
                <DatePicker name="rdtDate" dateFieldId="DUMMY" type="from" toDateName="rdtDate"  componentId='CMP_Mail_Operations_CarditEnquiry_ReqDlvDate' />
            </div>
            </Col>
            <Col xs="8">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.containernumber" />
                            </Label>
                <ITextField mode="edit" name="containerNo" type="text" uppercase={true} componentId= 'CMP_Mail_Operations_MailBagEnquiry_ContNo'></ITextField>
                </div>
            </Col>
            <Col xs="12">
                <div className="form-group">	
                <IFlightNumber mode="edit" ></IFlightNumber>
                </div>
            </Col>
            <Col xs="6">
               <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.consignmentnumber" />
                            </Label>
               <ITextField mode="edit" name="consignmentNo" uppercase={true} type="text" componentId = 'CMP_Mail_Operations_MailBagEnquiry_ConsigmentNumber'></ITextField>
               </div>
            </Col>
            <Col xs="6">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.upucode" />
                            </Label>
                <ITextField mode="edit" name="upuCode" uppercase={true} type="text"></ITextField>
                </div>
            </Col>
            <Col xs="6">
                <div className="form-group">	
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.cardittype" />
                            </Label>											
                <ISelect name="carditStatus" defaultOption={true} options={carditStat} />
                </div>
            </Col>
            {isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="6">
									<div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.servicelevel" />
                            </Label>	
										<ISelect defaultOption={true} name="serviceLevel" options={serviceLevels} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_servicelevel" />
									</div>
								</Col>
								}	
                                {isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="6">
									<div className="form-group">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.ontimedelivery" />
                            </Label>	
										<ISelect defaultOption={true} name="onTimeDelivery" options={onTimeDeliveryStat} componentId="CMP_Mail_Operations_MailBagEnquiry_ontimedelivery"/>
									</div>
								</Col>
								}
            </Row>
            </Fragment>
        )
    }
}
LyinglistFilterPanel.propTypes = {
    oneTimeValues: PropTypes.object
}
export default wrapForm('MailbagFilter')(LyinglistFilterPanel)
