import React, { Component, Fragment } from 'react';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { Col, Row } from 'reactstrap';
import { ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';

import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class SecurityPopOver extends Component {
    constructor(props) {
        super(props);

        this.onClose = this.onClose.bind(this);
        this.onSaveSecurityStatus = this.onSaveSecurityStatus.bind(this);
    }
    onClose() {
        this.props.onClose();
    }
    onSaveSecurityStatus() {
        this.props.onSaveSecurityStatus();
    }
    onOKClick = () => {
        this.props.onOKClick();
    }


    render() {

        let securityStatusCode = []
        if(!isEmpty(this.props.oneTimeValues)){
            securityStatusCode = this.props.oneTimeValues['mail.operations.securitystatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));

        }
        return (

            <Fragment>

                <IPopover placement="bottom" isOpen={this.props.showPopover} toggle={this.onOKClick} target={"resditimage_"} className="icpopover">
                    <IPopoverHeader>Security Status Code</IPopoverHeader>
                    <IPopoverBody >
                        <div style={{ "width": "250px" }}>
                            <div className="pad-md">
                                <Col xs="16">
                                    <ISelect name='securityStatusCode' options={securityStatusCode} />
                                </Col>
                            </div>
                            <div className="pad-md">
                                <Row>
                                    <Col xs="10">
                                    </Col>
                                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.props.onSaveSecurityStatus} >Save</IButton>
                                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.onClose}>Cancel</IButton>
                                </Row>
                            </div>
                        </div>
                    </IPopoverBody>
                </IPopover>


            </Fragment>

        )
    }

}

export default wrapForm('securityPopOverForm')(SecurityPopOver)

