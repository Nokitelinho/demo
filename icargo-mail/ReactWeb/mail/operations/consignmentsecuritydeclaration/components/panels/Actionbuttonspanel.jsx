import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
export default class ActionButtonsPanel extends Component {
	constructor(props) {
		super(props);
		this.newdetailspanelcontent = this.newdetailspanelcontent;
	}
	close = () => {
		this.props.close();
	}

	print =() =>{
		this.props.print();
	}

	save = () => {
		this.props.save(this.newdetailspanelcontent);
	}
	render() {
		return (
			<Fragment>
					<IButton category="btn btn-primary" bType="PRINT" onClick={this.print} disabled={!this.props.filterList}>Print</IButton>
					<IButton category="btn btn-primary" bType="SAVE" onClick={this.save}  disabled={!this.props.filterList} newdetailspanelcontent={this.props.newdetailspanelcontent.values}>Save</IButton>
                    <IButton category="btn btn-default" bType="CLOSE" accesskey="O" onClick={this.close}>Close</IButton>
				
			</Fragment>

		)
	}
}

ActionButtonsPanel.propTypes = {
	close: PropTypes.func,
	screenMode: PropTypes.string,

}