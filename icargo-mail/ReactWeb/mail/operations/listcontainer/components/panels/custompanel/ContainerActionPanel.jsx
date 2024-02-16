import React from 'react';
import { Col } from "reactstrap";
import { IButtonDropdown, IDropdownToggle, IDropdownMenu, IDropdownItem } from 'icoreact/lib/ico/framework/component/common/dropdown';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
class ContainerActionPanel extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="row align-items-center">
                <Col><h4>Container Enquiry</h4></Col>
                {(this.props.rowClkCount > 1) ?
                    <Col xs="auto">
                        <IButtonDropdown split={false} text="Select" category="secondary" className="more-button">
                            <IDropdownMenu right={true} >
                                <IDropdownItem data-mode="TRANSFERCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_TRANSFER_CARRIER" onClick={this.props.docontainerAction}>Transfer To Carrier</IDropdownItem>
                                <IDropdownItem data-mode="TRANSFERCONFLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_TRANSFER_FLIGHT" onClick={this.props.docontainerAction}>Transfer To Flight</IDropdownItem>
                                <IDropdownItem data-mode="REASSIGNCON_MULTI"  privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_REASSIGN_TO_CARRIER" onClick={this.props.docontainerAction}>Reassign To Carrier</IDropdownItem>
                                <IDropdownItem data-mode="REASSIGNCONFLIGHT_MULTI" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_REASSIGN_TO_FLIGHT" onClick={this.props.docontainerAction}>Reassign To Flight</IDropdownItem>
                                <IDropdownItem data-mode="OFFLOADCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_OFFLOAD" onClick={this.props.docontainerAction}>Offload Container</IDropdownItem>
                                <IDropdownItem data-mode="UNASSIGNCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_UNASSIGN" onClick={this.props.docontainerAction}>Unassign Container</IDropdownItem>
                                <IDropdownItem data-mode="RELEASECONTAINER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_RELEASE" onClick={this.props.docontainerAction}>Release Container</IDropdownItem>
                           </IDropdownMenu>
                        </IButtonDropdown>
                    </Col> : null}
            </div>
        )
    }
}
export default ContainerActionPanel