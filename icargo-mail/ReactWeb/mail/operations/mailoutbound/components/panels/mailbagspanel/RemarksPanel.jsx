import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import {Label} from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
class MailRemarks extends React.PureComponent {
    constructor(props) {
        super(props);


    }
    onRemarksSave = () => {
        this.props.onSavemailbagActions('REMARKS');
    }
    onCancelRemarks = () => {
        this.props.toggleMailPopup();
    }
    render() {
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <div className="form-group m-0">
                            <Label className="form-control-label">
                                <IMessage msgkey="mail.operations.mailoutbound.remarks" />
                            </Label>
                            <ITextField name="remarks" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                        </div>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onRemarksSave}>Save</IButton>
                    <IButton category="default" bType="CANCEL" accesskey="N" onClick={this.onCancelRemarks}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

MailRemarks.propTypes = {
    onClickemarksOK: PropTypes.func,
    onSavemailbagActions:PropTypes.func,
    toggleMailPopup:PropTypes.func,
}

export default icpopup(wrapForm('mailRemarksForm')(MailRemarks), { title: 'Remarks' })
