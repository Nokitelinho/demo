import React, { Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import AddContainer from '../AddContainer.jsx';
import PropTypes from 'prop-types'

class ContainerCustome extends React.PureComponent {
    constructor(props) {
        super(props);         
    }  

    commContainerAction = (event) => {
        if (this.props.flightDetails.onlineAirportParam === 'N' && this.props.valildationforimporthandling === 'Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')

        } else {
        let actionName = event.target.dataset.mode;
        let indexArray=this.props.indexArray;
        let data={indexArray:indexArray,actionName:actionName}
        this.props.containerAction(data);
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    }

    allPopOperations =(event)=>{
        if (this.props.flightDetails.onlineAirportParam === 'N' && this.props.valildationforimporthandling === 'Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode;
        let indexArray=this.props.indexArray;
        let data={indexArray:indexArray,actionName:actionName}
        this.props.allPopOperations(data);
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
        }
    }


    render() {
      return ( 
            <Fragment>
            <Row className="align-items-center">
                    <Col>
                    <h4>Containers</h4>
                        </Col>
                       
                         {(this.props.selectedContainerIndex && this.props.selectedContainerIndex.length>1)?
                        <Col xs="auto" className="p-0">
                            <IDropdown>
                                <IDropdownToggle className="dropdown-toggle more-toggle">
                                    <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                </IDropdownToggle>
                                <IDropdownMenu right={true} >
                                {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem  data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_FLIGHT" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem>:[]}
                                {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem  data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_UNDO_ARRIVAL" onClick={this.commContainerAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem>:[]}
                                    {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="ACQUIT_ULD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ACQUIT_ULD" onClick={this.commContainerAction} disabled={!this.props.flightActionsEnabled}> Acquit ULD </IDropdownItem> : []}
                                    {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ARRIVE_MAIL" onClick={this.commContainerAction} disabled={!this.props.flightActionsEnabled}> Arrive Mail </IDropdownItem> : []}
                                    {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DELIVER_MAIL" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}> Deliver Mail </IDropdownItem> : []}
                                    <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}> Attach Routing</IDropdownItem>
                                    {/*Removing as part of IASCB-117536 {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_AWB" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}> Attach AWB</IDropdownItem> : []} */}
                                {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem  data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_CONTAINER" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}>Transfer To Carrier</IDropdownItem>:[]}
                                {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem  data-mode="TRANSFER_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_CONTAINER" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}>Transfer To Flight</IDropdownItem>:[]}
                                    {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="RETAIN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_RETAIN_CONTAINER" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled || this.props.retainValidation}> Retain Container</IDropdownItem> : []}
                                    {(this.props.flightOperationStatus === 'Open' && this.props.readyforDeliveryRequired === 'Y') ? <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY" onClick={this.allPopOperations} disabled={!this.props.flightActionsEnabled}> Ready for delivery </IDropdownItem> : []}
                                </IDropdownMenu>
                            </IDropdown>                           
                        </Col>    :null}
                    <Col xs="auto" className="p-0">
                        <IButton class="btn btn-default" style={this.props.flightOperationStatus!='Closed' ? {} : { display: 'none' }}  disabled={!this.props.flightActionsEnabled} onClick={this.props.addContainerClk}><i className="icon ico-plus valign-middle"></i></IButton> 
                        <AddContainer dest={this.props.dest} pol={this.props.pol} show={this.props.show} toggleFn={this.props.toggle}
                            addContainerForm={this.props.addContainerForm} onSaveContainer={this.props.onSaveContainer} displayError={this.props.displayError} onClearContainer = {this.props.onClearContainer}
                            isDisabled={this.props.isDisabled} populateULD={this.props.populateULD} initialValues={this.props.initialValues} valildationforimporthandling={this.props.valildationforimporthandling} />
                </Col>
                    </Row>               
            </Fragment>
        )
    }
}
ContainerCustome.propTypes={
    containerAction:PropTypes.func,
    show:PropTypes.bool,
    toggle:PropTypes.func,
    rowClkCount:PropTypes.number,
    allContainerIconAction:PropTypes.func,
    addContainerClk:PropTypes.func,
    onSaveContainer:PropTypes.func,
    indexArray:PropTypes.array,
    allPopOperations:PropTypes.func,
    flightOperationStatus:PropTypes.string,
    readyforDeliveryRequired:PropTypes.string,
    dest:PropTypes.string,
    addContainerForm:PropTypes.string,
    pol:PropTypes.array,
    displayError:PropTypes.string,
    onClearContainer: PropTypes.func,
    flightDetails: PropTypes.object,
    valildationforimporthandling: PropTypes.string

}
export default ContainerCustome