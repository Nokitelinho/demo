import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
class DsnFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
        this.category = [];
        this.onInit();
    }   
    onInit() {
        if (!isEmpty(this.props.oneTimeValues)) {
            this.category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    }
    render() {
    //      let category = [];
     
    //    if (!isEmpty(this.props.oneTimeValues)) {
    //        category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            
           

    //     }
        return (
                    <Row>
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.dsn" />
                     </Label>
                                <ITextField name="despatchSerialNumber"  uppercase={true}/>
                    </div>
                        </Col>                           
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.mailorigin" />
                     </Label>
                               
                    
                   <Lov name="ooe" lovTitle="ooe" maxlength="6" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_mailorigin"/>
                    </div>
                        </Col>
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.destination" />
                     </Label>
                              <Lov name= "doe" lovTitle="doe" maxlength="6" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_maildestination" />
                                 
                    </div>
                        </Col>   
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.cat" />
                     </Label>
                                
                              <ISelect defaultOption={true} name="mailCategoryCode" options={this.category} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_category" />
                    </div>
                        </Col>   
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.subclass" />
                     </Label> 
                               
                              <Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600"  dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_Sc"/>
                    </div>
                        </Col> 
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pacode" />
                     </Label> 
                       <Lov name="paCode" uppercase={true} lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                    </div>
                </Col>                         
                {/* <Col xs="3">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.lbl.yr" />
                     </Label> 
                               <ITextField name="year" />
                    </div>
                        </Col>    */}
                        {/* <Col xs="4">
                    <div className="form-group">
                        <div className="mar-t-md">
                                    <ICheckbox name="routingAvlFlag"  label="Routing Avl"/>
                        </div>
                    </div>
                               </Col>    */}
                {/* <Col xs="3">
                    <div className="form-group">
                        <div className="mar-t-md">
                                    <ICheckbox name="pltEnableFlag"  label="PLT"/>
                        </div>
                    </div>
                               </Col>                           */}
                {/* <Col xs="7">
                    <div className="form-group">
                        <Label className="form-control-label">
                          <IMessage msgkey="mail.operations.mailinbound.awbnumber" />
                     </Label> 
                          <ITextField name="awb" />
                    </div>
                        </Col>    */}
                {/* <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mail.operations.mailinbound.consignmentnumber" />
                     </Label> 
                         <ITextField name="consignmentNumber"  uppercase={true} />
                    </div>
                </Col>    */}
                <Col xs="16">
                        <div className="form-group">
                            <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailinbound.awbnumber" />
                            </Label>
                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" /> 
                        </div>
                </Col>                       
                {/* <Col xs="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pacode" />
                     </Label> 
                             
                               <Lov name="paCode" uppercase={true} lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                    </div>
                        </Col>                             */}
                            </Row>
        )
    }
}

export default wrapForm('dsnFilter')(DsnFilter)