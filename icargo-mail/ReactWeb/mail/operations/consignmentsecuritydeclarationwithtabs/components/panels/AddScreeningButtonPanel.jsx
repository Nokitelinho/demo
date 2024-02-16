import React, { PureComponent, Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, ISelect, ICheckbox, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

  class AddScreeningButtonPanel extends React.PureComponent{
    constructor(props) {
        super(props);
       
    }

    okButton = () => {
        {this.props.EditScreeningDetails?this.props.okEditButton(this.props.editedDetail, this.props.rowIndex, this.props.ScreeningDetails)
        :
        this.props.okButton(this.props.newScreeningDetails, this.props.ScreeningDetails)}
    }

    // onclickPieces = (event) => {
    //     this.props.onclickPieces(event);
    // }

    render(){

        let screeningmethod = [];
        let results=[];
        if (!isEmpty(this.props.oneTimeValues)) {
            screeningmethod = this.props.oneTimeValues['mail.operations.screeningmethod'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeValues)) {
            results = this.props.oneTimeValues['mail.operations.results'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return(
        <Fragment>
            <PopupBody>
                <div className="px-3 pt-3">
                <Row>
                    <Col xs="2" md="4">
                    <div class="form-group">
                        <label className="form-control-label mandatory_label">Screened Location</label>
                        {/* <ISelect name="screeningLocation" id ="screeningLocation" /> */}
                        <ITextField  name="screeningLocation" id= "screeningLocation" type="text" uppercase={true} disabled={true}></ITextField>
                    </div>
                </Col>

                <Col xs="2" md="2">
                    <div class="form-group">
                        <label className="form-control-label mandatory_label">Method</label>
                        <ISelect name="screeningMethodCode" id ="screeningMethodCode" options={screeningmethod} />
                    </div>
                </Col>

                <Col xs="2" md="3">
                    <div className="form-group">
                        <label className="form-control-label">No. of Bags</label>
                        <ITextField  name="statedBags" id= "statedBags" type="text"></ITextField>
                    </div>
                </Col>

                <Col xs="2" md="2">
                    <div class="form-check mar-t-md">
                        <ICheckbox name="pieces" id= "pieces" className="form-check-input" onClick={this.onclickPieces} value ={this.props.FullPiecesFlag}/>
                        <label className="form-check-label">Full Pcs</label>
                    </div>
                </Col>

                 <Col xs="2" md="2">
                    <div className="form-group">
                        <label className="form-control-label">Weight</label>
                        <ITextField  name="statedWeight"  id="statedWeight" type="text"></ITextField>
                    </div>
                </Col>

                 <Col xs="2" md="5">
                    <div className="form-group">
                        <label className="form-control-label">Security Status Issued by</label>
                        <ITextField  name="securityStatusParty" id="securityStatusParty" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>

                <Col md="4">
                    <div className="form-group">
                        <label className="form-control-label">Date & Time</label>
                        <DatePicker name="securityStatusDate" id="securityStatusDate" />
                        
                    </div>     
                </Col>

                 <Col md="2">
                    <div className="form-group mb-0 mt-3">
                    <TimePicker  name="time" id="time"></TimePicker> 
                    </div>     
                </Col>

                </Row>
                <Row>

                    <Col xs="2" md="5">
                    <div className="form-group">
                        <label className="form-control-label">SM Applicable Authority</label>
                        <ITextField  name="screeningAuthority" id="screeningAuthority" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>

                <Col xs="2" md="5">
                    <div className="form-group">
                        <label className="form-control-label">SM Applicable Regulation</label>
                        <ITextField  name="screeningRegulation" id="screeningRegulation" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>

                <Col xs="2" md="5">
                    <div className="form-group">
                        <label className="form-control-label">Additional Security Info</label>
                        <ITextField  name="additionalSecurityInfo" id="additionalSecurityInfo" type="text" uppercase={true}></ITextField>
                    </div>
                </Col>

                <Col xs="2" md="4">
                    <div class="form-group">
                        <label className="form-control-label mandatory_label">Result</label>
                        <ISelect name="result" id ="result" options={results}/>
                    </div>
                </Col>

                <Col xs="2" md="5">
                    <div className="form-group">
                        <label className="form-control-label">Remarks</label>
                        <ITextField name="remarks" id="remarks" type="text"></ITextField>
                    </div>
                </Col>
            </Row>
                </div>
                </PopupBody>
            
                <PopupFooter>
                    <IButton category="primary" onClick={this.okButton} >OK</IButton>
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.closeButton}>Cancel</IButton>
                </PopupFooter>
        </Fragment>
        )
    }
}

export default icpopup(wrapForm('addScreeingButtonPanelform')(AddScreeningButtonPanel), { title:'Add New Screening Details' , className: 'modal_800px' })