import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class ContainerFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
    }   
    render() {
        return (
                 <Row>
                <Col xs="8">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mailtracking.defaults.arrivemail.tooltip.contno" />
                     </Label>
                               <ITextField name="containerNumber" uppercase={true}/>
                    </div>
                    </Col>  
                    <Col xs="8">
                            <Label className="form-control-label">
                                    POL
                            </Label>
                            <Lov name="pol" uppercase={true} lovTitle="POL" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />                 
                    </Col> 
                    <Col xs="8">
                            <Label className="form-control-label">
                                    Destination
                            </Label>
                            <Lov name="destination" uppercase={true} lovTitle="Destination" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />                 
                    </Col> 

                {/* <Col> 
                    <div className="form-group">
                        <label className="form-control-label">Remarks</label>
                                <ITextField name="remarks" />
                    </div>
                        </Col>  commented for ICRD-330846 */}                      
                    </Row>
        )
    }
}

export default wrapForm('containerFilter')(ContainerFilter)
