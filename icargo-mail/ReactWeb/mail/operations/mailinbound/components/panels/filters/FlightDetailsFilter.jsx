import React , { Fragment } from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class FlightDetailsFilter extends React.PureComponent {
    constructor(props) {
        super(props);   
    }   
    render() {
        let mailStatus=[{value:'C',label:'Close'},{value:'O',label:'Open'},{value:'N',label:'New'}];
        return (
            <Fragment>
                    <Row>
                <Col xs="18" md="18" lg="18">
                            <IFlightNumber  mode="edit" ></IFlightNumber>
                        </Col>                            
                <Col xs="6" md="6" lg="6">
                    <div className="form-group">
                    <Label className="form-control-label">
                          <IMessage msgkey="mail.operations.mailinbound.previousairport" />
                     </Label>
                     <Lov name="pol" lovTitle="POL" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                         {/* <ITextField name="pol" uppercase={true} /> */}
                    </div>
                        </Col>  
                        </Row>
                        <Row>  
                            <Col xs="10" md="10" lg="10">
                            <div className="form-group">
                                <Label className="form-control-label">
                                    Mail Operational Status
                                </Label>
                                <ISelect defaultOption={true} name="mailstatus" options={mailStatus} />
                            </div>
                            </Col>                             
                      </Row>
           </Fragment>        
        )
    }
}
export default wrapForm('flightDetailsFilter')(FlightDetailsFilter)
