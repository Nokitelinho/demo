import React from 'react';
import { ITextField, ISelect,IMessage} from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col,Container} from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker }  from 'icoreact/lib/ico/framework/component/common/date';
import {Lov} from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


class MailbagTableFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
    }   
    
    render() {
      
        let mailservicelevels = [];
        let mailstatuss = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            mailservicelevels = this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            mailstatuss = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
       
        let ontimdelivs = [{
            value:'Y',
            label:'Yes'
        },
        {
            value:'N',
            label:'No'
        }
    ]
       // var ontimeDelivMap = new Map();
       // ontimeDelivMap.set('Y','Yes');
      //   ontimeDelivMap.set('N','No');

        return (
            <Container fluid className="">
                <Row>
                    <Col xs="12">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.mailbagId"/></label>
                            <ITextField mode="edit" name="mailbagId" type="text" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_MAILBAGID"></ITextField>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label " ><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.servicelevel" /></label>
                            <ISelect name="servicelevel" options={mailservicelevels} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_SERVICELEVEL"/>
                        </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.originairport"/></label>
                            <Lov name= "mailorigin" lovTitle= "Origin airport" dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_ORIGINAIRPORT"/>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.destinationairport"/></label>
                            <Lov name= "mailDestination" lovTitle="Destination airport" dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_DESTINATIONAIRPORT"/>
                        </div>
                    </Col>
                    <Col xs="10">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.rdt"/></label>
                            <ITextField mode="edit" name="reqDeliveryTime" type="text" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_RDT"></ITextField>
                        </div>
                    </Col>
                    <Col xs="10">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.actualdlvtim"/></label>
                             <DatePicker name="scannedDate" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_ACTUALDLVTIM"/>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.currentairport"/></label>
                            <Lov name= "scannedPort" lovTitle="Current airport" dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_CURRENTAIRPORT"/>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.latesttransaction"/></label>
                             <ISelect name="latestStatus" options={mailstatuss} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_LATESTTRANSACTION"/>
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.ontimedelivery"/></label>
                              <ISelect name="onTimeDelivery" options={ontimdelivs} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_ONTIMEDELIVERY"/>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col xs="12">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.conno"/></label>
                            <ITextField mode="edit" name="consignmentNumber" type="text" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_CONSIGNMENTNO"></ITextField>
                        </div>
                    </Col>

                </Row>

            </Container>        
        )
    }
}
export default wrapForm('mailbagTableFilter')(MailbagTableFilter)
