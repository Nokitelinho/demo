import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { onAccept, validateInvoiceAmount } from '../../actions/commonaction.js';
import PropTypes from 'prop-types';
const Aux =(props) =>props.children;


class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
        this.onAccept = this.onAccept.bind(this);

    }

    onAccept = (event) => {
        this.props.onAccept(this.props.tableValues,this.props.selectedMailbagIndex);
    }
    onReprocess = () => {
        this.props.onReprocess();
    }
    onReject = () => {
        this.props.onReject();
    }



    render() {


        return (
            <Aux>



                    <IButton category="primary" bType="REPROCESS" accesskey="P" disabled={this.props.screenMode === 'initial' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ? true:false} onClick={this.onReprocess}>Reprocess</IButton>
                    <IButton category="primary" bType="ACCEPT" accesskey="A" disabled={this.props.screenMode === 'initial' || this.props.disableAcceptButton === 'Y' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ?true:false} onClick={this.onAccept}>Accept</IButton>
                    <IButton category="primary" bType="REJECT" accesskey="R"disabled={this.props.screenMode === 'initial' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ?true:false} onClick={this.onReject}>Reject</IButton>
                    <IButton category="primary" bType="RAISECLAIM" accesskey="C" disabled={this.props.screenMode === 'initial' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ?true:false} onClick={this.props.onRaiseClaim}>Raise Claim</IButton>
                    <IButton category="primary" bType="SAVE" accesskey="S" disabled={this.props.screenMode === 'initial' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ?true:false} onClick={this.props.onSaveFunction} >Save</IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.onCloseFunction} >Close</IButton>

                </Aux>

        );
    }
}
export default ActionButtonPanel;