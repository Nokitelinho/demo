import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Constants,Key,ComponentId} from '../../constants/constants.js'


export default class FooterPanel extends Component {

    attachAwb= (event) =>{
        this.props.attachAwb();
        console.log(event);
    }

    render(){
        return (
        <div>
            <IButton className="btn btn-default" category="primary" privilegeCheck={false} onClick={this.props.attachAwb} >Attach</IButton>
            <IButton className="btn btn-default" privilegeCheck={false} onClick={this.props.onClose} >Close</IButton>
        </div>
        )
    }

}