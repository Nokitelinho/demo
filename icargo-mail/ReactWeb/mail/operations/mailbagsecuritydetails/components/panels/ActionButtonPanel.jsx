import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';


class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
        this.onCloseFunction = this.onCloseFunction.bind(this);
    }

    onCloseFunction() {
        this.props.onCloseFunction();
    }
    print =() =>{
		this.props.print();
	}


    render() {


        return (
            <Fragment>

                <IButton category="btn btn-primary" bType="PRINT" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_PRINT" onClick={this.print} disabled={!this.props.filterList}>Print</IButton>
                <IButton category="btn btn-default" bType="CLOSE" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton>
            </Fragment>
        );
    }
}


export default ActionButtonPanel;