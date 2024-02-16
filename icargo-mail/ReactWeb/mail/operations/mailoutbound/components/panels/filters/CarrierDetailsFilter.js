import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';

class CarrierDetailsFilter extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <Row>
           {/* <Col xs="6">
                    <div className="form-group">
                       <Label className="form-control-label">
                            <IMessage msgkey="mail.operations.mailoutbound.upliftairport" />
                            </Label> 
                        <ITextField name="upliftAirport" uppercase={true} />
                    </div>
                </Col> */}
             <Col xs="5">
               <div className="form-group">
                   <Label className="form-control-label">
                       <IMessage msgkey="mail.operations.mailoutbound.carriercode" /></Label>
                        <Lov name= "carrierCode" uppercase={true}  lovTitle= "CarrierCode" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT"/>
               </div>
           </Col> 
          <Col xs="5">
               <div className="form-group">
                   <Label className="form-control-label">
                       <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                       <ITextField name="destination" uppercase={true}  componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
               </div>
           </Col> 
        </Row>
       )
    }  
}

export default wrapForm('carrierDetailsFilter')(CarrierDetailsFilter)