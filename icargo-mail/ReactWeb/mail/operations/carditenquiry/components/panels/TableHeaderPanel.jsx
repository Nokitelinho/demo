import React from 'react';
import { Row, Col } from "reactstrap";
import BulkResditPopup from './BulkResditPopup.jsx'
import { IDropdown, IButtonDropdown, IDropdownToggle, IDropdownMenu, IDropdownItem } from 'icoreact/lib/ico/framework/component/common/dropdown';
export default class TableHeaderPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            show:false
        }

    }
    carditMultipleAction = (event) => {
        let actionName = event.target.dataset.mode
        this.props.carditMultipleSelectionAction({ actionName: actionName });

    }
    togglePopup = () => {
        this.setState(
             {show: true }
        )
    }
    closePopup = () => {
        this.setState(
            { show: false });
    }
    render() {
        let length = 0
        length = this.props.selectedMailbagIndex ? this.props.selectedMailbagIndex.length : 0
        return (
            <div className="row align-items-center">
               <BulkResditPopup  show={this.state.show} oneTimeValues={this.props.oneTimeValues} bulkResditSend={this.props.bulkResditSend} displayError={this.props.displayError} mailbagDetails={this.props.mailbagDetails} selectedMailbagIndex={this.props.selectedMailbagIndex}closePopup={this.closePopup}/>
                <Col>
                    <h4>Mail Bag Details</h4>
                </Col>
                <Col xs="auto">
                    {(length > 1) ?
                        <IButtonDropdown split={false}
                            text="Action"
                            category="default"
                         >
                            <IDropdownMenu>
                                  <IDropdownItem data-mode="SEND_RESDIT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SEND_RESDIT" onClick={this.carditMultipleAction}>Send Resdit</IDropdownItem>
                                  <IDropdownItem data-mode="BULK_SEND_RESDIT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SEND_RESDIT" onClick={this.togglePopup}>Bulk Resdit</IDropdownItem>
                            </IDropdownMenu>
                        </IButtonDropdown> : null}
                </Col>
            </div>
        );
    }
}
