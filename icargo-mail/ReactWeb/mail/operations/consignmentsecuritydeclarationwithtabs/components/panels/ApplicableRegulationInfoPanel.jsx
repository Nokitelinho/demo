import React, { PureComponent, Fragment } from 'react';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, ISelect, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


export class ApplicableRegulationInfoPanel extends React.PureComponent{
    constructor(props) {
        super(props);
    }

    
    render(){

        let applicableregulationtransport = [];
        let applicableRegulationBorder = [];
        let applicableRegulationFlag = [];

        if (!isEmpty(this.props.oneTimeValues)) {
            applicableregulationtransport = this.props.oneTimeValues['mail.operations.applicableregulationtransport'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }

        if (!isEmpty(this.props.oneTimeValues)) {
            applicableRegulationBorder = this.props.oneTimeValues['mail.operations.applicableRegulationBorderAgencyAuthority'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }

        if (!isEmpty(this.props.oneTimeValues)) {
            applicableRegulationFlag = this.props.oneTimeValues['mail.operations.applicableRegulationFlag'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
             
        return(<Fragment>
       <div className="container-fluid px-3 pt-3">
            <Row>
                <Col xs="3" md="4">
                    <div class="form-group">
                        <label className="form-control-label mandatory_label">AR Transport Direction</label>
                        <ISelect name="applicableRegTransportDirection" id ="applicableRegTransportDirection" options={applicableregulationtransport} 
                        disabled={this.props.applicableRegTransportDirection&&this.props.applicableRegTransportDirection.length!==0}/>
                    </div>
                </Col>
                <Col xs="3" md="4">
                    <div className="form-group">
                        <label className="form-control-label">AR Border Agency Authority</label>
                        {/* <ITextField  name="applicableRegBorderAgencyAuthority" id ="applicableRegBorderAgencyAuthority" type="text" disabled={disabledValue}></ITextField> */}
                        <ISelect name="applicableRegBorderAgencyAuthority" id ="applicableRegBorderAgencyAuthority" options={applicableRegulationBorder}
                        disabled={this.props.applicableRegBorderAgencyAuthority&&this.props.applicableRegBorderAgencyAuthority.length!==0}/>
                    </div>
                </Col>
                <Col xs="3" md="4">
                    <div className="form-group">
                        <label className="form-control-label">AR Reference ID</label>
                        <ITextField  name="applicableRegReferenceID" id ="applicableRegReferenceID" type="text" 
                         disabled={this.props.applicableRegReferenceID&&this.props.applicableRegReferenceID.length!==0 } uppercase={true}>
                        </ITextField>
                    </div>
                </Col>
                <Col xs="3" md="4">
                    <div className="form-group">
                        <label className="form-control-label">AR Flag</label>
                        {/* <ITextField  name="applicableRegFlag" id ="applicableRegFlag" value="" type="text" disabled={disabledValue}></ITextField> */}
                        <ISelect name="applicableRegFlag" id ="applicableRegFlag" options={applicableRegulationFlag} 
                        disabled={this.props.applicableRegFlag&&this.props.applicableRegFlag.length!==0}/>
                     </div>
                </Col>
            </Row>
        </div>
    </Fragment>)
        
    }
}

export default wrapForm('applicableregulationinfo')(ApplicableRegulationInfoPanel);