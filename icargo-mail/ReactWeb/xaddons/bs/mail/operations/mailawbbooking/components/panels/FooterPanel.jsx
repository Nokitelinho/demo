import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Constants,Key,ComponentId} from '../../constants/constants.js'


export default class FooterPanel extends Component {
    render(){
        return (
        <div>
            <IButton className="btn btn-default" privilegeCheck={false} onClick={this.props.onClose} componentId={ComponentId.CLOSE_BTN}><IMessage msgkey={Key.CLOSE_LBL}/></IButton>
        </div>
        )
    }

}