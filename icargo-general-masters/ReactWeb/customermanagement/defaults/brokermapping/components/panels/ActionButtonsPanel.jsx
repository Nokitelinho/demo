import React, { Component } from 'react';
import { IButton} from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

import DeleteCustomerPopUpContainer from '../containers/popup/deletecustomerpopupcontainer';
import SinglePoaPopUpContainer from '../containers/popup/singlepoapopupcontainer';
import ViewHistoryPopUpContainer from '../containers/popup/viewhistorypopupcontainer';
/**
 * This component is used for the handling Footer Section Buttons
 * All Buttons except delete, will be enabled only when creating a customer or modifying an existing customer
 * Delete button is only enabled if the customer is a temporary customer 
 */
class ActionButtonsPanel extends Component {
    constructor(props) {
        super(props);
        this.state={
        }
    }
    
    render() {
        return (
            <div className="footer-panel">
                <IButton componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_SINGLEPOA" category="default"
                    accesskey="" disabled={this.props.showActionButtons} onClick={() => this.props.onClickSinglePoa()}>
                    <IMessage msgkey="customermanagement.defaults.brokermapping.singlepoa"
                        defaultMessage="Single POAs" />
                    <SinglePoaPopUpContainer show={this.props.showSinglePoa} />
                </IButton>
                <IButton componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_VIEW_HISTORY" category="default"
                    accesskey="" disabled={this.props.showActionButtons} onClick={()=>this.props.findPoaHistory()}>
                    <IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory"
                        defaultMessage="View History" />
                    <ViewHistoryPopUpContainer show={this.props.showViewHistory} />
                </IButton>
                <IButton componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_SAVE" category="primary"
                    accesskey="" disabled={this.props.showActionButtons} onClick={() => this.props.onSave()}>
                    <IMessage msgkey="customermanagement.defaults.brokermapping.save"
                        defaultMessage="Save" />
                </IButton>
                <IButton componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_DELETE" category="default"
                    accesskey="" disabled={this.props.disableCustomerFields} onClick={() => this.props.toogleDeletePopUp()}>
                    <IMessage msgkey="customermanagement.defaults.brokermapping.delete"
                        defaultMessage="Delete" />
                    <DeleteCustomerPopUpContainer show={this.props.showDeletePopUp} />
                </IButton>
            </div>
        );
    }
}


export default ActionButtonsPanel;


