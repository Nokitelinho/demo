import React, { Component } from 'react';
import AddMessageRulePopup from '../popup/AddMessageRulePopup.jsx';
import PropTypes from 'prop-types';
export class PopupPanel extends React.Component {
    constructor(props) {
        super(props)
    }

    
    render() {
        return (
            <div>

             <AddMessageRulePopup show={this.props.showAddPopup} 
                toggleFn={this.props.onCloseMailRule}
                saveMailRuleConfig={this.props.saveMailRuleConfig} 
                oneTimeMap={this.props.oneTimeMap}initialValues={this.props.initialValues} />

            </div>
        );
    }
}
PopupPanel.propTypes = {
    saveMailRuleConfig:PropTypes.func,
    onCloseMailRule:PropTypes.func,
    showAddPopup:PropTypes.bool,
    oneTimeMap:PropTypes.object
}