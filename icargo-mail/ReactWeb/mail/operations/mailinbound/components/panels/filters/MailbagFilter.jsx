import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';

class MailbagFilter extends React.PureComponent {
    constructor(props) {
        super(props); 
        this.statusList = []; 
        this.category = [];
        this.init();
    } 
    init() {
		if (!isEmpty(this.props.oneTimeValues)) {
			this.statusList = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {
				if (value.fieldValue == 'ACP' ||  value.fieldValue == 'ARR'
					|| value.fieldValue == 'ASG'
                    || value.fieldValue == 'DLV' || value.fieldValue == 'TRA'
                    ) 
                return value;
            });
            this.statusList = this.statusList.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    }   
    render() {
        return (
                    <Row>
                <Col xs="12">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.discrepancy.lbl.mailbagid" />
                     </Label>
                       <ITextField name="mailbagId" uppercase={true} />
                    </div>
                        </Col>                            
                <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          Org.
                     </Label>
                        <Lov name="mailOrigin" lovTitle="Origin" maxlength="6" uppercase={true} dialogWidth="600" dialogHeight="425"  actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_mailorigin"/>      
                          
                    </div>
                        </Col>
                <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                         Dest.
                     </Label>
                             <Lov name= "mailDestination" lovTitle="Destination" maxlength="6" uppercase={true} dialogWidth="600" dialogHeight="425"  actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_maildestination" />
                    </div>
                        </Col>   
                <Col xs="12">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.cat" />
                     </Label>
                            <ISelect defaultOption={true} name="mailCategoryCode" options={this.category} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_category" />
                   
                    
                    </div>
                        </Col>   
                <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.subclass" />
                     </Label> 
                               <Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600"  dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_Sc"/>
                    </div>
                        </Col>                          
                {/* <Col xs="4">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.yr" />
                     </Label> 
                            <ITextField name="year" />
                    </div>
                        </Col>                             */}
                <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.dsn" />
                     </Label>
                            <ITextField name="despatchSerialNumber" />
                    </div>
                        </Col>
                <Col xs="4">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.mailrsn" />
                     </Label>
                        <ITextField name="receptacleSerialNumber" />
                    </div>
                        </Col>   
                {/* <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.hni" />
                     </Label>
                         <ITextField name="hni" />
                    </div>
                                </Col>                                 */}
                {/* <Col xs="4">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.ri" />
                     </Label>
                            <ITextField name="ri" />
                    </div>
                        </Col>                           */}
                {/* <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.mailwt" />
                     </Label>
                        <ITextField name="weight" />
                    </div>
                                </Col> */}
                {/* <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.mailVol" />
                     </Label>
                         <ITextField name="volume" />
                    </div>
                                </Col> */}
                {/* <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.scandate" />
                     </Label>
                                <DatePicker name="scanDate" dateFieldId="scanDate"  id="scanDate" />
                    </div>
                        </Col>    */}
                {/* <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mail.operations.mailinbound.awbnumber" />
                     </Label>
                        <ITextField name="awb" />
                    </div>
                        </Col>    */}
                 <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mail.operations.mailinbound.consignmentnumber" />
                     </Label>
                        <ITextField name="consigmentNumber" uppercase={true} />
                    </div>
                 </Col>   
                 <Col xs="12">
                        <div className="form-group">
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailinbound.awbnumber" />
                            </Label>
                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" /> 
                        </div>
                    </Col>
                {/* <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.tooltip.transfercarrier" />
                     </Label>
                        <ITextField name="transfferCarrier" />
                    </div>
                        </Col>                           */}
                {/* <Col xs="4">
                    <div className="form-group">
                        <div className="mar-t-md">
                                    <ICheckbox name="transit"  label="Transit"/>
                        </div>
                    </div>
                               </Col> */}
                {/* <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.arrivalsealno" />
                     </Label>
                         <ITextField name="arriveSealNo" />
                    </div>
                        </Col> */}
                {/* <Col xs="6">
                                <div className="form-group">
                         <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.sealno" />
                     </Label>
                         <ITextField name="sealNo" />
                        </div>
                        </Col>    */}
                 <Col xs="12">
						<div className="form-group">
							<label className="form-control-label ">Mailbag Status</label>
							<ISelect defaultOption={true} name="mailStatus" options={this.statusList}  /> 
						</div>
				</Col>  
                <Col xs="12">
                    <div className="form-group">
                    <Label className="form-control-label">
                          RDT
                     </Label>
                        <DatePicker name="rdtDate"  />
                    </div>
                </Col>                    
                <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pacode"/>
                     </Label>
                         <Lov name="paCode" uppercase={true} lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                    </div>
                        </Col>                            
                <Col xs="auto">
                    <div className="form-group">
                        <div className="mar-t-md">
                                    <ICheckbox name="carditAvailable"  label="Cardit Avl"/>
                        </div>
                    </div>
                                </Col> 
                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="routingAvlFlag"  label="Routing Avl"/>
                                    </div>
                        </div>
                               </Col>    */}
                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="pltEnableFlag"  label="PLT"/>
                        </div>
                        </div>
                               </Col>  */}
                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="transitFlag"  label="Transit"/>
                        </div>
                        </div>
                               </Col>   */}

                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="arriveFlag"  label="Arrive"/>
                        </div>
                        </div>
                               </Col>  */}
                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="deliverFlag"  label="Deliver"/>
                        </div>
                        </div>
                               </Col>  */}
                {/* <Col xs="auto">
                    <div className="form-group">
                    <div className="mar-t-md">
                                    <ICheckbox name="unarrived"  label="Unarrived"/>
                                    </div>
                    </div>
                                </Col>                                  */}
                            </Row>
        )
    }
}

export default wrapForm('mailbagFilter')(MailbagFilter)
