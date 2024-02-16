import React, { Component, Fragment } from 'react';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
export default class ActionButtonsPanel extends Component {
  constructor(props) {
    super(props)
  }
  onCloseFunction = () => {
    this.props.onCloseFunction();
  }
  save =() => {
   this.props.onSave();
  }

  render() {
    return (<Fragment>

      <Fragment>
        <IButton componentId="sample" onClick={this.save} category="primary" >
          <IMessage msgkey="sample"
            defaultMessage="Save " />
        </IButton>

      </Fragment>

      <IButton componentId="OPERATIONS_SHIPMENT_EDIAGREEMENT299_CLOSE" category="default"
        onClick={this.onCloseFunction}>
        <IMessage msgkey="operations.shipment.ediagreement299.label.close"
          defaultMessage="Close" />
      </IButton>
    </Fragment>
    );
  }
}

ActionButtonsPanel.propTypes = {
  close: PropTypes.func,
  screenMode: PropTypes.string,

}