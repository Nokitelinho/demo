import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';

class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);  
        this.markAsHba = this.markAsHba.bind(this);
    }
  
    onClose=()=> {
        this.props.onClose();
    }	 

    markAsHba() {
        this.props.markAsHba();
    }

    render() {
        let position = this.props.position !==null;    
        let hbaType = this.props.hbaType !==null;

        return (
            <div>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.markAsHba} disabled={!(position && hbaType)} componentId ='CMP_HBA_MARKING_SAVE'>Save</IButton>
                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.onClose}>Close</IButton>  
            </div>
        );
    }
}
ActionButtonPanel.propTypes = {
    onClose: PropTypes.func
}
export default ActionButtonPanel;