import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';
import { IPrintMultiButton } from 'icoreact/lib/ico/framework/component/common/printmultibutton';




class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
    }
  
    onClose = () => {
        this.props.onClose();
    }

    onOkButton = () => {
        this.props.onOkButton();
    }

    render() {
        return (
            <div>
                <IButton category="primary" className="btn btn-primary" onClick={this.onOkButton} >Ok</IButton>
                <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.onClose} >Close</IButton>
            </div>
        );
    }
}
ActionButtonPanel.propTypes = {
    onClose: PropTypes.func
}
export default ActionButtonPanel;