import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';


class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
        this.onCloseFunction = this.onCloseFunction.bind(this);
        this.onSelectFunction = this.onSelectFunction.bind(this);
    }

    onCloseFunction() {
        this.props.onCloseFunction();
    }
    onSelectFunction() {
        this.props.onSelectFunction();
    }


    render() {


        return (
            <Fragment>

                <IButton category="btn btn-primary" bType="OK" componentId="CMP_MAIL_OPERATIONS_MAILCONTAINERLIST_SELECT" accesskey="S" onClick={this.onSelectFunction} >Select</IButton>
                <IButton category="btn btn-default" bType="CLOSE" componentId="CMP_MAIL_OPERATIONS_MAILCONTAINERLIST_CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton>
            </Fragment>
        );
    }
}


export default ActionButtonPanel;