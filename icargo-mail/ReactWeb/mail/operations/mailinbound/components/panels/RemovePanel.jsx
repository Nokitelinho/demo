import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Label, Row } from 'reactstrap'
import { IButton, ITextArea, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
//import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
/**
 *  The same panel will be used to remove mailbag/container
 */
class RemovePanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {

        }
        this.offloadReason = [];
        this.init();
    }
    save = () => {
        this.props.onSave();
    }
    init() {
        if (!isEmpty(this.props.oneTimeValues) && this.props.oneTimeValues['mailtracking.defaults.offload.reasoncode']) {
            this.offloadReason = this.props.oneTimeValues['mailtracking.defaults.offload.reasoncode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    }
    render() {
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col>
                                <div className="form-group pt-1">
                                    <Label className="form-control-label">
                                        Offload Reason
                                    </Label>
                                    <ISelect name={`removeReason`} options={this.offloadReason} />
                                </div>
                            </Col>
                            <Col>
                                <div className="form-group pt-1">
                                    <Label className="form-control-label">
                                        Remarks
                                    </Label>
                                    <ITextArea name="removeRemarks" uppercase={true} />
                                </div>
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.save} >Save</IButton>
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.props.toggle}>Close</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

RemovePanel.propTypes = {
    toggle: PropTypes.func,
    saveRemoveMailAction: PropTypes.func
}
export default icpopup(wrapForm('removeDetails')(RemovePanel), { title: 'Remove ULD/Mailbag' })


