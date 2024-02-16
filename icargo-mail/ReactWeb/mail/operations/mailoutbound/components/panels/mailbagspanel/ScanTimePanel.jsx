import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
//import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import {Label} from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
class MailScanTime extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    onScanTimeSave = () => {
        this.props.onSavemailbagActions('CHANGE SCAN TIME');
    }
    onCancel = () => {
        this.props.toggleMailPopup();
    }
    render() {
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <div className="row">
                            <div className="col-12">
                                <div className="form-group m-0">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.scandate" />
                                    </Label>
                                    <DatePicker name="scanDate" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                </div>
                            </div>
                            <div className="col-12">
                                <div className="form-group m-0">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.scantime" />
                                    </Label>
                                    <TimePicker name="scanTime" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                </div>
                            </div>
                        </div>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onScanTimeSave}>Save</IButton>
                    <IButton category="default" VbType="CANCEL" accesskey="N" onClick={this.onCancel}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

MailScanTime.propTypes = {
    onClickScanTime: PropTypes.func,
    onSavemailbagActions:PropTypes.func,
    toggleMailPopup:PropTypes.func,
}

export default icpopup(wrapForm('scanTimeForm')(MailScanTime), { title: 'Scan Time' })
