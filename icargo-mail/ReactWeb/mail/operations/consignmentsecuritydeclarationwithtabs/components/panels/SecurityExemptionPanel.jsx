import React, { PureComponent, Fragment } from 'react';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, ISelect, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';



export  class SecurityExemptionPanel extends React.PureComponent{
    constructor(props) {
        super(props);
    }


    render(){

        // let securityStatusCodes = [];
        // if (!isEmpty(this.props.oneTimeValues)) {
        //     securityStatusCodes = this.props.oneTimeValues['mail.operations.securitystatuscodes'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        // }
        let reasonArray = [{ "label": "Mail", "value": "Mail" }]
       
        return(<Fragment>
            <div className="container-fluid px-3 pt-3">
                <Row>
                    <Col xs="3" md="4">
                        <div class="form-group">
                            <label className="form-control-label mandatory_label">Reason For Exemption</label>
                                <ISelect name="defaultReason" id ="defaultReason" options={reasonArray}  disabled={(this.props.initialValues&&this.props.securityExemption)?true:false}/>
                                {/* <ITextField  type="text" name="defaultReason" id ="defaultReason" disabled={(this.props.initialValues&&this.props.securityExemption)?true:false}></ITextField> */}
                        </div>
                    </Col>
                    <Col xs="3" md="4">
                        <div className="form-group">
                            <label className="form-control-label">SE Applicable Authority</label>
                                <ITextField  type="text" name="screeningAuthority" id ="screeningAuthority" disabled={(this.props.initialValues&&this.props.securityExemption)?true:false} uppercase={true}></ITextField>
                        </div>
                    </Col>
                    <Col xs="3" md="5">
                        <div className="form-group">
                            <label className="form-control-label">SE Applicable Regulation</label>
                                <ITextField  name="screeningRegulation" id ="screeningRegulation"  type="text" disabled={(this.props.initialValues&&this.props.securityExemption)?true:false} uppercase={true}></ITextField>
                        </div>
                    </Col>
                </Row>
            </div>
        </Fragment>)
        
    }
}
export default wrapForm('securityexemptionform')(SecurityExemptionPanel);
