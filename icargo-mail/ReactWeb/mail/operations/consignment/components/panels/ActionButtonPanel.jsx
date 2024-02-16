import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { IButtonDropdown, IDropdownMenu, IDropdownItem } from 'icoreact/lib/ico/framework/component/common/dropdown';

class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);
        this.showPrintMailTag = this.showPrintMailTag.bind(this);
        this.onCloseFunction = this.onCloseFunction.bind(this);
        this.save = this.save.bind(this);
    }

    showPrintMailTag() {
        this.props.ShowPrintMailTag();
    }

    onCloseFunction() {
        this.props.onCloseFunction();
    }

    save() {
        if (this.props.isDomestic) {
            this.props.getLastRowData();
            this.props.getFormData();
            this.props.onSave(this.props.formData, this.props.routingList, this.props.isDomestic, this.props.activeMailbagAddTab,this.props.deletedMailbagslist);
        } else {
            this.props.getFormData();
            this.props.onSave(this.props.formData, this.props.routingList, this.props.isDomestic, this.props.activeMailbagAddTab,this.props.deletedMailbagslist);
        }
    }

    printConsignment = (event) => {
        let printType = event.target.dataset.mode?event.target.dataset.mode:'';
        this.props.printConsignment(printType);
    }

    navigateToConsignmentSecurity = () => {
        this.props.navigateToConsignmentSecurity();
    }

    printConsignmentSummary = (event) => {
        let printType = event.target.dataset.mode?event.target.dataset.mode:'';
        this.props.printConsignmentSummary(printType);
    }

    render() {


        return (
            <div>
                <Fragment>
                    {this.props.screenMode === 'initial' ? <Fragment>
                        <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_SAVE' privilegeCheck={false} disabled={true}>Save</IButton>
                        <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_PRINTMAILTAG' privilegeCheck={false} disabled={true}>Print Mailtag</IButton>
                        <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_PRINTBUTTON' privilegeCheck={false} disabled={true}>Print</IButton>
                        <IButton category="primary" privilegeCheck={false} disabled={true}>Print CN Summary </IButton>
                        <IButton category="primary" privilegeCheck={false} disabled={true}>Consignment Security Declaration</IButton>
                        <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_DELETE' privilegeCheck={false} disabled={true}>Delete</IButton>
                        <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_CLOSE' privilegeCheck={false} bType="CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton>
                    </Fragment>

                        :
                        <div><IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_SAVE' privilegeCheck={false} bType="SAVE" accesskey="S" onClick={this.save}>Save</IButton>
                            <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_PRINTMAILTAG' privilegeCheck={false} onClick={this.showPrintMailTag}>Print Mailtag</IButton>
                            <IButtonDropdown split={false}
                                text="Print"
                                category="primary"
                                className="more-button">
                                <IDropdownMenu className="more-button-list">
                                    <IDropdownItem data-mode="AV7" onClick={this.printConsignment}> Print AV-7 </IDropdownItem>
                                    <IDropdownItem data-mode="CN38" onClick={this.printConsignment}> Print CN 38 </IDropdownItem>
                                    <IDropdownItem data-mode="CN37" onClick={this.printConsignment}> Print CN 37 </IDropdownItem>
                                    <IDropdownItem data-mode="CN41" onClick={this.printConsignment}> Print CN 41 </IDropdownItem>
                                    <IDropdownItem data-mode="CN46" onClick={this.printConsignment}> Print CN 46 </IDropdownItem>
                                    <IDropdownItem data-mode="CN47" onClick={this.printConsignment}> Print CN 47 </IDropdownItem>


                                </IDropdownMenu>
                            </IButtonDropdown>
                            <IButtonDropdown split={false}
                                text="Print CN Summary"
                                category="primary"
                                className="more-button">
                                <IDropdownMenu className="more-button-list">
                                    <IDropdownItem data-mode="CNSummary38" onClick={this.printConsignmentSummary}> Print CN 38 </IDropdownItem>
                                    <IDropdownItem data-mode="CNSummary37" onClick={this.printConsignmentSummary}> Print CN 37 </IDropdownItem>
                                    <IDropdownItem data-mode="CNSummary41" onClick={this.printConsignmentSummary}> Print CN 41 </IDropdownItem>
                                    <IDropdownItem data-mode="CNSummary46" onClick={this.printConsignmentSummary}> Print CN 46 </IDropdownItem>
                                    <IDropdownItem data-mode="CNSummary47" onClick={this.printConsignmentSummary}> Print CN 47 </IDropdownItem>
                                </IDropdownMenu>
                            </IButtonDropdown>
                            <IButton category="primary" privilegeCheck={false} onClick={this.props.navigateToConsignmentSecurity}>Consignment Security Declaration</IButton>
                            <IButton category="primary" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_DELETE' privilegeCheck={false} onClick={this.props.deleteConsignmentAction}>Delete</IButton>
                            <IButton category="default" componentId='BTN_MAIL_OPERATIONS_UX_CONSIGNMENT_CLOSE' privilegeCheck={false} bType="CLOSE" accesskey="O" onClick={this.onCloseFunction}>Close</IButton></div>
                    }
                </Fragment>
            </div>
        );
    }
}
export default ActionButtonPanel;