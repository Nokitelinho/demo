import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Label, Row } from 'reactstrap'
import { IButton, IRadio, ITextArea } from 'icoreact/lib/ico/framework/html/elements';

class PrintMailTag extends React.Component {

    constructor(props) {
        super(props);
        this.transferContainer = this.transferContainer.bind(this);
        this.state = {
            transferFilterType: "C"
        }
    }

    onFilterChange = (event) => {
        this.setState({ transferFilterType: event.target.value })
    }

    onClose = () => {
        this.props.onClose();
    }

    transferContainer() {
        this.props.transferContainer();
    }

    /*onClose = () => {
        closePopupWindow();
    }*/
    render() {
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        {(this.props.mailBagsSelected === true) ? (
                            <IRadio name="printMailTagType" options={[{ 'label': 'Print Selected Mail bags ', 'value': 'SELECTED' }, { 'label': 'Print All Mail bags', 'value': 'ALLMAILBAGS' }]} value={this.state.printMailTagType} onChange={this.onFilterChange} />
                        ) : (
                                <IRadio name="printMailTagType" options={[{ 'label': 'Print All Mail bags', 'value': 'ALLMAILBAGS' }]} value={this.state.printMailTagType} onChange={this.onFilterChange} />
                            )}
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.props.printMailTag} componentId='BTN_MAIL_OPERATIONS_UX_PRINTBUTTON' privilegeCheck={false}>Print</IButton>{' '}
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.onClose} componentId="BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_CLOSE" privilegeCheck={false}>Close</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}


export default icpopup(wrapForm('printMailTagForm')(PrintMailTag), { title: 'Mail Tag Print' })