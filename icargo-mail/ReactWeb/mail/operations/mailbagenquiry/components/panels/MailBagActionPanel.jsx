import React from 'react';
import { Col } from "reactstrap";
import { IButtonDropdown, IDropdownToggle, IDropdownMenu, IDropdownItem } from 'icoreact/lib/ico/framework/component/common/dropdown';
class MailBagActionPanel extends React.PureComponent {
    constructor(props) {
        super(props);
    }

    onMailBagActionClick = (event) => {
        let functionName = event.target.dataset.mode?event.target.dataset.mode:"REASSIGN";
        this.props.onAllMailBagActionClick(functionName);
    }

    render() {
        return (
            <div className="row align-items-center">
                <Col>
                    <h4>Mail Bag Details</h4>
                </Col>
                {(this.props.selectedMailbags.length > 1) ?
                    <Col xs="auto">
                        <IButtonDropdown split={false} text="Select" category="secondary" className="more-button">
                            <IDropdownMenu right={true} >
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="REASSIGN" componentId="CMP_Mail_Operations_MailBagEnquiry_Reassign" >Reassign</IDropdownItem>
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="RETURN" componentId="CMP_Mail_Operations_MailBagEnquiry_Return" >Return</IDropdownItem>
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="VIEW_MAIL_HISTORY" componentId="CMP_Mail_Operations_MailBagEnquiry_View_History" >View Mail History</IDropdownItem>
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="DELIVER_MAIL" componentId="CMP_Mail_Operations_MailBagEnquiry_Deliver" >Deliver Mail</IDropdownItem>
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="TRANSFER" componentId="CMP_Mail_Operations_MailBagEnquiry_Transfer">Transfer</IDropdownItem>
                                <IDropdownItem onClick={this.onMailBagActionClick} data-mode="OFFLOAD" componentId="CMP_Mail_Operations_MailBagEnquiry_Offload">Offload</IDropdownItem>
                                <IDropdownItem  onClick={this.onMailBagActionClick} data-mode="MODIFY_ORG_DST" componentId="CMP_Mail_Operations_MailBagEnquiry_Modify_Origin_Dest">Modify Origin Destination</IDropdownItem>
                            </IDropdownMenu>
                        </IButtonDropdown>
                    </Col>
                    : null}
            </div>
        )
    }
}
export default MailBagActionPanel