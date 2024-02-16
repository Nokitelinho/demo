import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';

const Aux =(props) =>props.children;


class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
      

    }

   
    render() {


        return (
            <Aux>
            
                <IButton className="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.props.onCloseFunction} >Close</IButton>
                          
           </Aux>
                   
        );
    }
}
export default ActionButtonPanel;