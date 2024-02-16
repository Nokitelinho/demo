import React, { Component, Fragment } from 'react';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
export default class ActionButtonsPanel extends Component {
	constructor(props) {
		super(props)
	}
	close = () => {
		this.props.close();
	}

	render() {
		return (
			<Fragment>

				{/* {(this.props.screenMode === SCREEN_MODE.EDIT || this.props.screenMode === SCREEN_MODE.DISPLAY) && */}
				<Fragment>

					<IButton componentId="sample" onClick={this.props.saveMailRuleConfig} category="primary" >
						<IMessage msgkey="sample"
							defaultMessage="Save " />
					</IButton>

				</Fragment>
				{/* } */}

				<IButton componentId="OPERATIONS_SHIPMENT_EDIAGREEMENT299_CLOSE" category="default"
					onClick={this.close}>
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