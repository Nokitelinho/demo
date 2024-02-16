import React, { Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'


class ActionButtonPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {


        }
        this.onCloseFunction = this.onCloseFunction.bind(this);
        this.onSaveMailboxData = this.onSaveMailboxData.bind(this);
        this.onActive = this.onActive.bind(this);
    }

    onSaveMailboxData() {
        this.props.onSaveMailboxData();
    }
    onCloseFunction() {
        this.props.onCloseFunction();
    }
    onActive(){
        this.props.onActive();
    }

    render() {



        return (
            <Fragment>
                {this.props.mailboxStatus === 'ACTIVE' ?
                    <Fragment>

                        <IButton category="btn btn-primary" bType="INACTIVE" onClick={this.onActive} disabled={this.props.isbuttondisabled}>Inactivate</IButton>
                        <IButton category="btn btn-primary" bType="SAVE" onClick={this.onSaveMailboxData} disabled={this.props.isbuttondisabled}>Save</IButton>
                        <IButton category="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton>


                    </Fragment>
                    : <div>
                      <IButton category="btn btn-primary" bType="ACTIVE" onClick={this.onActive} disabled={this.props.isbuttondisabled}>Activate</IButton>
                        <IButton category="btn btn-primary" bType="SAVE" onClick={this.onSaveMailboxData} disabled={this.props.isbuttondisabled}>Save</IButton>
                        <IButton category="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton>
                    </div>
                }
            </Fragment>
        )
    }
}


export default ActionButtonPanel;