import React from 'react';
import { Row, Col } from "reactstrap";
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import AddMailBag from '../AddMailbag.jsx';
import PropTypes from 'prop-types';

class MailbagTable extends React.PureComponent {
    constructor(props) {
        super(props);         
    } 
    
    changeTab=(data)=>{
        this.props.changeMailTab(data);
    }

    commMailBagAction = (event) => {
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode;
        let indexArray=this.props.indexArray; 
        let data={indexArray:indexArray,actionName:actionName}
        this.props.mailBagAction(data);
        
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
	}

    allPopUpOperations=(event)=>{
        if (this.props.flightDetails.onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode;
        let indexArray=this.props.indexArray; 
        let data={indexArray:indexArray,actionName:actionName}
         this.props.allPopUpOperations(data);
        
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
        }
    }

    render() {
        let active=0;
        if (this.props.active === 'MAIL_VIEW') {
            active = 0;
        } else  {
            active = 1;
        }
      
          
      return (          
            (this.props.active == 'MAIL_VIEW') ?
                <div className="mailbag-tab-view">
            <Row className="align-items-center">
                        <Col className="pr-0">
                            <div className="tab d-inline-flex">
                                <IButtonbar active={active}>
                                    <IButtonbarItem>
                                        <div className="btnbar" data-tab="MAIL_VIEW" onClick={() => this.changeTab('MAIL_VIEW')}>Mailbags</div>
                                    </IButtonbarItem>
                                    <IButtonbarItem>
                                        <div className="btnbar" data-tab="DSN_VIEW" onClick={() => this.changeTab('DSN_VIEW')}>DSN</div>
                                    </IButtonbarItem>
                                </IButtonbar>
                            </div>
                        </Col>
                        {(this.props.rowClkCount > 1) ?
                            <Col xs="auto" className="p-0">
                                <IDropdown>
                                    <IDropdownToggle className="dropdown-toggle more-toggle">
                                        <a href="#"><i className="icon ico-v-ellipsis align-middle"></i></a>
                                    </IDropdownToggle>
                                    <IDropdownMenu right={true} >
                                    {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_FLIGHT_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem>: []}
                                        {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_UNDO_ARRIVAL_MAILBAG" onClick={this.commMailBagAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem>: []}
                                        <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Attach Routing </IDropdownItem>

                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ARRIVE_MAIL_MAILBAG" onClick={this.commMailBagAction} disabled={(this.props.validateDeliveryMailBagFlag) || (!this.props.flightActionsEnabled)}> Arrive Mail </IDropdownItem> : []}

                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DELIVER_MAIL_MAILBAG" onClick={this.allPopUpOperations} disabled={(this.props.validateDeliveryMailBagFlag) || (!this.props.flightActionsEnabled)}> Deliver Mail </IDropdownItem> : []}
										
                                        {(this.props.flightOperationStatus==='Open') ?     <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_MAIL_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer</IDropdownItem>: []}
                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_AWB_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}>Attach AWB</IDropdownItem> : []}
                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DETACH_AWB_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled||(this.props.masterDocumentFlag=="N")}>Detach AWB</IDropdownItem> : []}
                                        {(this.props.readyforDeliveryRequired === 'Y' && this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Ready for delivery </IDropdownItem> : []}
                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="DAMAGE_CAPTURE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DAMAGE_CAPTURE_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}>Damage Capture</IDropdownItem> : []}
                                        {(this.props.flightOperationStatus === 'Open') ? <IDropdownItem data-mode="CHANGE_SCAN_TIME" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_SCAN_TIME_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Change scan time</IDropdownItem> : []}
                                        <IDropdownItem data-mode="VIEW_MAIL_HISTORY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_VIEW_HISTORY_MAILBAG" onClick={this.commMailBagAction}> View Mail History</IDropdownItem>
                                        <IDropdownItem data-mode="REMOVE_MAILBAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_REMOVE_MAILBAG" onClick={this.allPopUpOperations}>Remove Mailbag</IDropdownItem>
                                    </IDropdownMenu>
                                </IDropdown>
                            </Col>
                            : null}
                        <Col xs="auto" className="p-0">
                            <IButton  style={this.props.flightOperationStatus!='Closed' ? {} : { display: 'none' }} onClick={this.props.addMailbagClk} disabled={!this.props.flightActionsEnabled} componentId="CMP_MAIL_OPERATIONS_INBOUND_ADD_MAILBAG"><i className="icon ico-plus m-0 valign-middle"></i></IButton>
                            <AddMailBag show={this.props.show} toggleFn={this.props.toggle} onSavemailbagDetails={this.props.onSavemailbagDetails} populateMailbagId={this.props.populateMailbagId}  displayError={this.props.displayError}
                               valildationforimporthandling={this.props.valildationforimporthandling} onDeleteRow={this.props.deleteRow} resetRow={this.props.resetRow} initialValues={this.props.initialValues} mailbagOneTimeValues={this.props.oneTimeValuesFromScreenLoad}
                                activeMailbagAddTab={this.props.activeMailbagAddTab} addRow={this.props.addRow} newMailbags={this.props.newMailbags} updatedMailbags={this.props.updatedMailbags}
                                importedMailbagDetails={this.props.importedMailbagDetails} changeAddMailbagTab={this.props.changeAddMailbagTab}  defaultWeightUnit={this.props.defaultWeightUnit} previousWeightUnit={this.props.previousWeightUnit} />
                        </Col>
                      {/*  <Col xs="auto" className="p-0">
                            <ICustomHeaderHolder tableId="mailbaglisttable" />
                        </Col>
                        <Col xs="auto" className="pl-0">
                            <div className="pad-r-md">
                                <i class="icon ico-maximize" onClick={this.props.allMailbagIconAction}></i>
                            </div>
                        </Col>*/ }
                    </Row>
                </div> :
                <div className="dsn-tab-view">
                    <Row className="align-items-center">
                <Col xs="auto mr-auto">
                    <div className="tab">
                        <IButtonbar active={active}>
                            <IButtonbarItem>
                                        <div className="btnbar" data-tab="MAIL_VIEW" onClick={() => this.changeTab('MAIL_VIEW')}>Mailbags</div>
                            </IButtonbarItem>
                            <IButtonbarItem>
                                <div className="btnbar" data-tab="DSN_VIEW" onClick={() => this.changeTab('DSN_VIEW')}>DSN</div>
                            </IButtonbarItem>
                        </IButtonbar>
                    </div>
                        </Col>
                        {(this.props.rowClkCount > 1) ?
                <Col xs="auto">
                                <IDropdown>
                                    <IDropdownToggle className="dropdown-toggle more-toggle">
                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                    </IDropdownToggle>
                                    <IDropdownMenu right={true} >
                                        <IDropdownItem data-mode="CHANGE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CHANGE_FLIGHT_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Change Flight</IDropdownItem>
                                        <IDropdownItem data-mode="UNDO_ARRIVAL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_UNDO_ARRIVAL_MAILBAG" onClick={this.commMailBagAction} disabled={!this.props.flightActionsEnabled}> Undo Arrival</IDropdownItem>
                                        <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_ROUTING_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Attach Routing </IDropdownItem>
                                        <IDropdownItem data-mode="ARRIVE_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ARRIVE_MAIL_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Arrive Mail </IDropdownItem>
                                        <IDropdownItem data-mode="DELIVER_MAIL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DELIVER_MAIL_MAILBAG" onClick={this.allPopUpOperations}> Deliver Mail </IDropdownItem>
                                        <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_TRANSFER_MAIL_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Transfer</IDropdownItem>
                                        {(this.props.readyforDeliveryRequired === 'Y') ? <IDropdownItem data-mode="READY_FOR_DELIVERY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_READY_FOR_DELIVERY_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Ready for delivery </IDropdownItem> : []}
                                        <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_ATTACH_AWB_MAILBAG" onClick={this.allPopUpOperations} disabled={!this.props.flightActionsEnabled}> Attach AWB</IDropdownItem>
                                    </IDropdownMenu>
                                </IDropdown>
                            </Col>
                            : null}
                    </Row>
                </div>
            /*  <div className="border-bottom">
                  <Row className="align-items-center">
                <Col xs="auto mr-auto">
                    <div className="tab">
                        <IButtonbar active={active}>
                            <IButtonbarItem>
                                <div className="btnbar" data-tab="MAIL_VIEW" onClick={() => this.changeTab('MAIL_VIEW')}>Mailbags</div>
                            </IButtonbarItem>
                            <IButtonbarItem>
                                <div className="btnbar" data-tab="DSN_VIEW" onClick={() => this.changeTab('DSN_VIEW')}>DSN</div>
                            </IButtonbarItem>
                        </IButtonbar>
                    </div>
                </Col>
                    { (this.props.active=='MAIL_VIEW')?
                    <Col xs="auto">
                        <IButton class="btn btn-default" onClick={this.props.addMailbagClk}><i class="icon ico-plus m-0 valign-middle"></i></IButton>
                            <AddMailBag show={this.props.show} toggleFn={this.props.toggle} onSavemailbagDetails={this.props.onSavemailbagDetails} populateMailbagId={this.props.populateMailbagId}
                                        onDeleteRow={this.props.deleteRow} resetRow={this.props.resetRow}   initialValues={this.props.initialValues} mailbagOneTimeValues={this.props.oneTimeValuesFromScreenLoad}
                                        activeMailbagAddTab= {this.props.activeMailbagAddTab} addRow={this.props.addRow}newMailbags={this.props.newMailbags} updatedMailbags={this.props.updatedMailbags} 
                                        importedMailbagDetails={this.props.importedMailbagDetails} changeAddMailbagTab={this.props.changeAddMailbagTab} />
                    </Col>
                    : null
                    }

                        {(this.props.rowClkCount > 1) ?
                        (this.props.active=='MAIL_VIEW')?[
                    <Col xs="auto">
                                  <IDropdown>
                                      <IDropdownToggle className="dropdown-toggle more-toggle">
                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                    </IDropdownToggle>
                                    <IDropdownMenu right={true} >
                                        <IDropdownItem data-mode="CHANGE_FLIGHT" onClick={this.allPopUpOperations}> Change Flight</IDropdownItem>
                                        <IDropdownItem data-mode="UNDO_ARRIVAL" onClick={this.commMailBagAction}> Undo Arrival</IDropdownItem>
                                        <IDropdownItem data-mode="ATTACH_ROUTING" onClick={this.allPopUpOperations}> Attach Routing </IDropdownItem>
                                        <IDropdownItem data-mode="ARRIVE_MAIL" onClick={this.commMailBagAction}> Arrive Mail </IDropdownItem>
                                        <IDropdownItem data-mode="DELIVER_MAIL" onClick={this.allPopUpOperations}> Deliver Mail </IDropdownItem>
                                        <IDropdownItem data-mode="TRANSFER" onClick={this.allPopUpOperations}> Transfer</IDropdownItem>
                                        <IDropdownItem data-mode="ATTACH_AWB" onClick={this.allPopUpOperations}>Attach AWB</IDropdownItem>
                                       {(this.props.readyforDeliveryRequired==='Y') ? <IDropdownItem data-mode="READY_FOR_DELIVERY" onClick={this.allPopUpOperations}> Ready for delivery </IDropdownItem>:[]}
                                        <IDropdownItem data-mode="DAMAGE_CAPTURE" onClick={this.allPopUpOperations}>Damage Capture</IDropdownItem>
                                        <IDropdownItem data-mode="CHANGE_SCAN_TIME" onClick={this.allPopUpOperations}> Change scan time</IDropdownItem>
                                        <IDropdownItem data-mode="VIEW_MAIL_HISTORY" onClick={this.commMailBagAction}> View Mail History</IDropdownItem>
                                    </IDropdownMenu>
                                </IDropdown>

                            </Col>]:
                              [ <Col xs="auto">
                                  <IDropdown>
                                      <IDropdownToggle className="dropdown-toggle more-toggle">
                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                    </IDropdownToggle>
                                    <IDropdownMenu right={true} >
                                        <IDropdownItem data-mode="CHANGE_FLIGHT" onClick={this.allPopUpOperations}> Change Flight</IDropdownItem>
                                        <IDropdownItem data-mode="UNDO_ARRIVAL" onClick={this.commMailBagAction}> Undo Arrival</IDropdownItem>
                                        <IDropdownItem data-mode="ATTACH_ROUTING" onClick={this.allPopUpOperations}> Attach Routing </IDropdownItem>
                                        <IDropdownItem data-mode="ARRIVE_MAIL" onClick={this.allPopUpOperations}> Arrive Mail </IDropdownItem>
                                        <IDropdownItem data-mode="DELIVER_MAIL" onClick={this.allPopUpOperations}> Deliver Mail </IDropdownItem>
                                        <IDropdownItem data-mode="TRANSFER" onClick={this.allPopUpOperations}> Transfer</IDropdownItem>
                                       {(this.props.readyforDeliveryRequired==='Y') ?  <IDropdownItem data-mode="READY_FOR_DELIVERY" onClick={this.allPopUpOperations}> Ready for delivery </IDropdownItem>:[]}
                                        <IDropdownItem data-mode="ATTACH_AWB" onClick={this.allPopUpOperations}> Attach AWB</IDropdownItem>
                                    </IDropdownMenu>
                                </IDropdown>

                            </Col>]:null}
                      {(this.props.active == 'MAIL_VIEW') ?
                <Col xs="auto">
                              <ICustomHeaderHolder tableId="mailbaglisttable" />
                        </Col>:
                          <Col xs="auto">
                              <ICustomHeaderHolder tableId="dsnlisttable" />
                        </Col>}
                <Col xs="auto">
                {this.props.active == 'MAIL_VIEW'&&
                    <i class="icon ico-maximize" onClick={this.props.allMailbagIconAction}></i>
                }
                            </Col>
                    </Row>
                      </div>*/
        )
    }
}
export default MailbagTable

MailbagTable.propTypes = {
    rowClkCount:PropTypes.number,
    addMailbagClk:PropTypes.func,
    show:PropTypes.bool,
    toggle:PropTypes.func,
    onSavemailbagDetails:PropTypes.func,
    mailBagAction:PropTypes.func,
    allMailbagIconAction:PropTypes.func,
    changeMailTab:PropTypes.func,
    indexArray:PropTypes.array,
    allPopUpOperations:PropTypes.func,
    active:PropTypes.string,
    flightOperationStatus:PropTypes.string,
    readyforDeliveryRequired:PropTypes.string,
    populateMailbagId:PropTypes.func,
    deleteRow:PropTypes.func,
    resetRow:PropTypes.func,
    initialValues:PropTypes.object,
    oneTimeValuesFromScreenLoad:PropTypes.array,
    activeMailbagAddTab:PropTypes.string,
    addRow:PropTypes.func,
    newMailbags:PropTypes.object,
    updatedMailbags:PropTypes.func,
    importedMailbagDetails:PropTypes.func,
    changeAddMailbagTab:PropTypes.func,
    defaultWeightUnit:PropTypes.string,
    previousWeightUnit:PropTypes.string,
    flightDetails: PropTypes.object,
    displayError:PropTypes.string,
    valildationforimporthandling:PropTypes.string,
    masterDocumentFlag:PropTypes.string
}