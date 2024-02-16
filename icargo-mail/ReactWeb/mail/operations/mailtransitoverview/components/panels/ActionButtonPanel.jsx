import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';

const Aux =(props) =>props.children;

class ActionButtonPanel extends Component {

    navigateToMailInbound = () => {
        this.props.navigateToMailInbound();
    }


    render() {


        return (
            <Aux>
                <IButton category="primary"  onClick={this.props.navigateToMailInbound}>Mail Inbound</IButton>
                <IButton className="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.props.onCloseFunction} >Close</IButton>
                          
           </Aux>
                   
        );
    }

}
export default ActionButtonPanel;