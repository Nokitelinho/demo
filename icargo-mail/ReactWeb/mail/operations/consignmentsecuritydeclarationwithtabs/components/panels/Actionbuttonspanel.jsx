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

	saveDetails =() =>{
		this.props.saveDetails();
	}

	print =() =>{
		this.props.print();
	}

	render() {
		return (
			<Fragment>
					<IButton category="btn btn-primary" bType="PRINT" onClick={this.print} disabled={!this.props.filterList}>Print</IButton>
                    <IButton category="btn btn-primary" bType="SAVE" onClick={this.saveDetails} disabled={!this.props.filterList}>Save</IButton>
                    <IButton category="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.close}>Close</IButton>
				
			</Fragment>

		)
	}
}

ActionButtonsPanel.propTypes = {
	close: PropTypes.func,
	screenMode: PropTypes.string,

}