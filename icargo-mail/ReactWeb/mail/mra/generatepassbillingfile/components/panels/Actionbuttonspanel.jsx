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

	generate =() =>{
		this.props.generate();
	}

	generatelog =() =>{
		this.props.generatelog();
	}

	render() {
		return (
			<Fragment>
				<IButton category="btn btn-primary" bType="PRINT" onClick={this.generate}>Generate</IButton>
                <IButton category="btn btn-primary" bType="SAVE" onClick={this.generatelog}>Generation Log</IButton>
				<IButton category="default" onClick={this.close}>Close</IButton>
			</Fragment>

		);
	}
}

ActionButtonsPanel.propTypes = {
	close: PropTypes.func,
	screenMode: PropTypes.string,

}