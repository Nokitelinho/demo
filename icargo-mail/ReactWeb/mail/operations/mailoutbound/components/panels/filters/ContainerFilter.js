import React, { Fragment } from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class ContainerFilter extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <Fragment>
                <Row>
                  <Col xs="8">
                        <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.containerno" />
                        </Label>
                        <ITextField name="containerNumber" uppercase={true} />
                  </Col>                
                  <Col xs="8">
                       <div className="form-group">
                        <label className="form-control-label ">RDT</label>
                            <DatePicker name="rdtDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                        </div>
                   </Col>
                   {
                       this.props.flightCarrierflag ==='F' && 
                       <Col xs="8">
                            <Label className="form-control-label">
                                    POU
                                </Label>
                                <Lov name="pou" uppercase={true} lovTitle="POU" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />                 
                        </Col> 
                   }
                   {
                       this.props.flightCarrierflag ==='F' && 
                       <Col xs="8">
                            <Label className="form-control-label">
                                    Destination
                                </Label>
                                <Lov name="destination" uppercase={true} lovTitle="Destination" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />                 
                        </Col>  
                   }  
                </Row>
            </Fragment>
        )
    }
}

export default wrapForm('containerFilter')(ContainerFilter)
