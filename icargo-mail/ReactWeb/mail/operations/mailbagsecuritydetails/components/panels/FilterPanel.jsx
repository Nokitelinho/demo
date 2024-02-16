import React, { Fragment } from 'react';
import { Col, Row, Input, Label } from 'reactstrap'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import SecurityPopOver from './SecurityPopOver.jsx';


class FilterPanel extends React.Component {


    constructor(props) {
        super(props);
        this.onlistMailbagSecurityDetails = this.onlistMailbagSecurityDetails.bind(this);
        this.onclearMailbagsecurityDetails = this.onclearMailbagsecurityDetails.bind(this);

    }

    onlistMailbagSecurityDetails() {
        this.props.onlistMailbagSecurityDetails();
    }

    changeScreenMode = (event) => {
        this.props.onChangeScreenMode(event.target.dataset.mode);
    }
    onclearMailbagsecurityDetails() {
        this.props.onclearMailbagsecurityDetails();
    }
	onSelect = () => {
        this.props.onSelect();

    }



    render() {

        return (
            <Fragment>

                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (

                    <div class="header-filter-panel flippane" id="headerForm">
                        <div class="pad-md pad-b-3xs">
                            <div class="row">
                                <Col xs="5">
                                    <div class="form-group">
                                        <label class="form-control-label">Mail Bag ID</label>
                                        <ITextField mode="edit" name="mailbagId" type="text" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_MAILBAGID"  class="form-control" uppercase={true} maxlength="29"></ITextField>

                                    </div>
                                </Col>
                                <div class="col mar-t-md">
                                    <IButton id="list" category="btn btn-primary flipper" bType="LIST" accesskey="L" flipper="headerData" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_LIST" onClick={this.onlistMailbagSecurityDetails} >List</IButton>

                                    <IButton category="default" bType="CLEAR" accesskey="C" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_CLEAR" privilegeCheck={false} onClick={this.onclearMailbagsecurityDetails}>Clear</IButton>
                                </div>
                            </div>
                        </div>

                        {(this.props.screenMode === 'edit') ? (<i onClick={this.changeScreenMode} data-mode="display" className="icon ico-close-fade flipper flipper-ico"></i>) : null}
                    </div>) : <div>


                    <div><div className="header-summary-panel flippane">
                        <div className="pad-md">
                            <Row>
                            <Col xs="7" md="6" lg="5">
                                        <Label className="form-control-label">Mail Bag ID</Label>
                                        <div className="form-control-data">{this.props.mailbagId}</div>
                                    </Col>
                                    <Col xs="4" >
                                        <Label className="form-control-label">Origin</Label>
                                        <div className="form-control-data">{this.props.origin}</div>
                                    </Col>
                                    <Col xs="4">
                                        <Label className="form-control-label ">Destination</Label>
                                        <div className="form-control-data">{this.props.destination}</div>
                                    </Col>
								 <Col xs="4">
                                    <Label className="form-control-label ">Security Status Code:</Label>
                                    <div className="form-control-data d-flex align-items-center">
                                       <div>{this.props.securityStatusCode}</div>
                                        <i onClick={this.onSelect} id={"resditimage_"} class="icon ico-pencil open_code mar-l-sm" data-target="webuiPopover47" ></i>
                                        {this.props.showPopover && <SecurityPopOver showPopover={this.props.showPopover} oneTimeValues={this.props.oneTimeValues} onClose={this.props.onClose} onOKClick={this.props.onOKClick} onSaveSecurityStatus={this.props.onSaveSecurityStatus} />
                                        }
                                    </div>
                                </Col>
                            </Row>
                        </div>
                        <i onClick={this.changeScreenMode} data-mode="edit" className="icon ico-pencil-rounded-orange flipper flipper-ico"></i></div></div>
                </div>
                }


            </Fragment>
        );
    }
}






export default wrapForm('mailbagSecurityFilter')(FilterPanel);