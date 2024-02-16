import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Row, Col, Label} from "reactstrap";
import { ISelect, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';

class GenerateManifestPopup extends React.PureComponent {

    onClickPrint = () => {
        this.props.onClickPrint();
    }

    toggle = () => {
        this.props.toggle();
    }
    render() {
        let printoptions = ['Mailbag level', 'AWB level', 'Destn Category level', 'DSN/Mailbag level'];
        printoptions = printoptions.map((value) => ({ value: value, label: value }));
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="10">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.printtype" />
                                    </Label>
                                    <ISelect name="printTypes" options={printoptions} />
                                </div>
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.onClickPrint.bind(this)}>Print</IButton>{' '}
                    <IButton category="default" onClick={this.toggle.bind(this)}>Cancel</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}
GenerateManifestPopup.propTypes = {
    onClickPrint:PropTypes.func,
    toggle:PropTypes.func

}

export default icpopup(wrapForm('generateManifestForm')(GenerateManifestPopup), { title: 'Manifest Print' })
