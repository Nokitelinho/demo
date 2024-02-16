import React, { PureComponent, Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, ISelect, IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';


  class AddConsignerButtonPanel extends React.PureComponent{
    constructor(props) {
        super(props);
       
    }

    okConsignerButton = () => {
        this.props.okConsignerButton(this.props.newConsignerDetails, this.props.ConsignerDetails)
    }

    render(){

        let consignorStatusCode = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            consignorStatusCode = this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return(
        <Fragment>
             <PopupBody>
              <div className="pad-md">
                <Row>
                <Col xs="2" md="8">
                    <div className="form-group">
                        <label className="form-control-label">Consigner Status Code</label>
                        {/* <ITextField  name="securityStatusCode"  id="securityStatusCode" type="text"></ITextField> */}
                        <ISelect name="screeningMethodCode" id ="screeningMethodCode" options={consignorStatusCode}/>

                    </div>
                </Col>

                 <Col xs="2" md="8">
                    <div className="form-group">
                        <label className="form-control-label">Consigner Name/Id</label>
                        <ITextField  name="screeningAuthority"  id="screeningAuthority" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>

                 <Col xs="2" md="8">
                    <div className="form-group">
                        <label className="form-control-label">Consigner Country Code</label>
                        <ITextField  name="consCountryCode"  id="consCountryCode" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>
                </Row>
              </div>  
              </PopupBody>
              
                <PopupFooter>
                    <IButton category="primary" onClick={this.okConsignerButton}>OK</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeButton}>Cancel</IButton>
                </PopupFooter>
        </Fragment>
        )
    }
}

export default icpopup(wrapForm('addConsignerButtonPanelForm')(AddConsignerButtonPanel), { title:'Add New Consigner Details' })