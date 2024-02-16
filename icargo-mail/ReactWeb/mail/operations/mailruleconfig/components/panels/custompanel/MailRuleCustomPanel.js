import React from 'react';
import { Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';

class MailRuleCustomPanel extends React.PureComponent {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="mar-r-xs table-header__icons">
                <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD' privilegeCheck={false} onClick={this.props.onAddMailRule}>Add</IButton>
                <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD' privilegeCheck={false} onClick={this.props.onmodiftMailRule}>Modify</IButton>
                <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD' privilegeCheck={false} onClick={this.props.onDeleteMailRule}>Delete</IButton>
                <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD' privilegeCheck={false}>Activate/Deactivate</IButton>
            </div>


        )
    }
}
export default MailRuleCustomPanel



