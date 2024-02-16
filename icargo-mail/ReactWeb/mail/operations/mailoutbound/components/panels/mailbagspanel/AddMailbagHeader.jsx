import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, IButton} from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col, Label} from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';

class AddMailbagHeader extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    onImportMailbags = () => {
        this.props.onImportMailbags();

    }
    Close=()=>{
        this.props.onCloseImport();
    }
    render() {
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            
                            <Col xs="10">
                                <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.containerjourneyid" />
                                    </Label>
                                <ITextField name="containerJnyID" />
                            </Col>
                            <Col xs="10">
                                <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.bellycartid" />
                                    </Label>
                                <ITextField name="bellyCarditId" />
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.onImportMailbags}>Add</IButton>{' '}
                    <IButton color="default" onClick={this.Close}>Close</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}
AddMailbagHeader.propTypes = {
    onImportMailbags:PropTypes.func,
    onCloseImport:PropTypes.func,
}
export default icpopup(wrapForm('addMailbagImport')(AddMailbagHeader), { title: 'Import Mailbags' })
