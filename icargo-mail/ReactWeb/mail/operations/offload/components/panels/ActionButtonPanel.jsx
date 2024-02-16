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
               
                <IButton category="primary" className="btn btn-primary" onClick={this.props.onOffloadFunction} disabled={this.props.activeOffloadTab=='DSN_VIEW'?true:false}>Offload</IButton>
                <IButton className="btn btn-default" onClick={this.props.onCloseFunction} >Close</IButton>
                          
           </Aux>
                   
        );
    }
}
export default ActionButtonPanel;