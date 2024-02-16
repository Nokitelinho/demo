import React from 'react';
import { Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';

class MailBagCustomPanel extends React.PureComponent {
    constructor(props) {
        super(props);
        this.showAddMultiplePanel = this.showAddMultiplePanel.bind(this);
    }

    addRow = () => {
        if(this.props.isDomestic){
            this.props.getLastRowData();
            this.props.getFormData;
            this.props.addRowDomestic(this.props.lastRowData, this.props.newRowDataDomestic);
        }else{
            this.props.getLastRowData();
            this.props.getFormData;
            this.props.addRow(this.props.newRowData, this.props.lastRowData);
        }
    }

    showAddMultiplePanel() {
        this.props.showAddMultiplePanel();
    }

    onDeleteRow = () => {
        this.props.deleteRow();
    }

    render() {
        return (
            <div className="mar-r-xs table-header__icons">
                <IButton category="primary" onClick={this.addRow} componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD' privilegeCheck={false}>Add</IButton>
                <IButton category="primary" onClick={this.showAddMultiplePanel} disabled={this.props.isDomestic} componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_ADD_MULTIPLE' privilegeCheck={false}>Add Multiple</IButton>
                <IButton category="default" onClick={this.onDeleteRow} componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_DELETEMAILBAG' privilegeCheck={false}>Delete</IButton>
            </div>
        )
    }
}
export default MailBagCustomPanel