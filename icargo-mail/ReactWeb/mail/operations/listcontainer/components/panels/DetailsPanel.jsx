import React, { Component } from 'react';
import ContainerDetailsTable from './ContainerDetailsTable.jsx';
import ReassignPanel from './ReassignPanel.jsx';
import TransferPanel from './TransferPanel.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import ReassignFlightPanel from '../../../mailoutbound/components/panels/containerpanel/ReassignFlightPanel.jsx';
import TransferFlightPanel from '../../../mailinbound/components/panels/TransferPanel.jsx';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';


 class DetailsPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showReassign: false,
            showTransfer: false
        }
        //this.transferContainerAction = this.transferContainerAction.bind(this)   

    }
    toggleTransfer = () => {
        this.setState({ showTransfer: !this.state.showTransfer });
    }
    toggleReassign = () => {
        this.setState({ showReassign: !this.state.showReassign });
    }



    docontainerAction = (event) => {
        if (this.props.flightDetailsFromInbound.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode?event.target.dataset.mode:'TRANSFERCON';
        let selectedIndex = event.target.dataset.index
        
        if (actionName === 'VIEWMAILBAG') {
            let data = {index:selectedIndex}
            this.props.viewMailbag(data);

        }
        if (actionName === 'TRANSFERCON') {
            this.props.validateContainerForTransferToCarrier(selectedIndex, 'CARRIER');
        }
        if (actionName === 'TRANSFERCONFLIGHT') {
            this.props.validateContainerForTransfer(selectedIndex, 'FLIGHT');
        }
        if (actionName === 'OFFLOADCON') {
            if(event.target&&event.target.dataset&&event.target.dataset.index){
                let selectedIndex = event.target.dataset.index;
                let selectedContainer=null;
                selectedContainer=this.props.containerDetails.results[selectedIndex];
                this.props.selectContainersForOffload(selectedContainer);
            }
            this.props.offloadContainer();
        }
        if (actionName === 'UNASSIGNCON') {
            this.props.unassignContainer(event);
        }
        if (actionName === 'REASSIGNCON') {
            if(event.target&&event.target.dataset&&event.target.dataset.index){
                let selectedIndex = event.target.dataset.index;
                let selectedContainer=null;
                selectedContainer=this.props.containerDetails.results[selectedIndex];
                this.props.validateContainerForReassign(selectedContainer,'REASSIGN_IND', 'CARRIER');
            }
        }
        if (actionName === 'MARK_AS_HBA'||actionName === 'UNMARK_HBA') {
            let selectedIndex = event.target.dataset.index;
            let selectedContainer=null;
            selectedContainer=this.props.containerDetails.results[selectedIndex];
            this.props.markAsHBAFlag(selectedContainer, actionName);
        }
        if (actionName === 'REASSIGNCONFLIGHT') {
            if(event.target&&event.target.dataset&&event.target.dataset.index){
                let selectedIndex = event.target.dataset.index;
                let selectedContainer=null;
                selectedContainer=this.props.containerDetails.results[selectedIndex];
                this.props.validateContainerForReassign(selectedContainer,'REASSIGN_IND', 'FLIGHT');
            }
        }
        if (actionName === 'REASSIGNCON_MULTI') 
        {
            const selectedContainer = null;
            this.props.validateContainerForReassign(selectedContainer,'REASSIGN_MULTI', 'CARRIER');
        }
        if (actionName === 'REASSIGNCONFLIGHT_MULTI') 
        {
            const selectedContainer = null;
            this.props.validateContainerForReassign(selectedContainer,'REASSIGN_MULTI', 'FLIGHT');
        }
        if (actionName === 'RELEASECONTAINER') {
            if(event.target&&event.target.dataset&&event.target.dataset.index){
                let selectedIndex = event.target.dataset.index;
                let selectedContainer=[];
                selectedContainer.push(this.props.containerDetails.results[selectedIndex]);
                this.props.selectContainersForRelease(selectedContainer);
            }
            this.props.releaseContainer();
        }
		 if (actionName === 'ULD_MARK_FULL' || actionName === 'ULD_UNMARK_FULL') {
                this.props.markUnmarkULDFullIndicator(selectedIndex, actionName);
            }
            if (actionName === 'PRINT_ULD_TAG' ) {
                selectedIndex= event.target.dataset.index;
                this.props.printuldtag(selectedIndex, actionName);
            }
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();

    }
    }


    render() {
        return (
            (this.props.screenMode === 'initial' || this.props.noData == true) ?
                <div className="card">
                    <div className="card-header">
                        <h4 className="col">Container Enquiry</h4>
                    </div>
                    <div className="card-body p-0">
                        <NoDataPanel />
                    </div>
                </div>
                :
            <div className="card container-details-list">
                    <ContainerDetailsTable containerDetails={this.props.containerDetailsList} getNewPage={this.props.onlistContainerDetails} exportToExcel={this.props.exportToExcel} docontainerAction={this.docontainerAction}
                            selectContainers={this.props.selectContainers} updateSortVariables={this.props.updateSortVariables} oneTimeValues={this.props.oneTimeValues} onInputChangeSearchmode={this.props.onInputChangeSearchmode} onApplyContainerFilter={this.props.onApplyContainerFilter} onClearContainerFilter={this.props.onClearContainerFilter}
                        assignedTo={this.props.assignedTo} containerDetailsList={this.props.containerDetails} displayError={this.props.displayError}
                            initialValues={this.props.initialValues} saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                            contentId={this.props.contentId} saveActualWeight={this.props.saveActualWeight} tableValues={this.props.tableValues}
                        containerdetailForm={this.props.containerdetailForm} flightDetailsFromInbound={this.props.flightDetailsFromInbound}
							 tableFilterInitialValues={this.props.tableFilterInitialValues} estimatedChargePrivilage={this.props.estimatedChargePrivilage}
                            />
                    <ReassignPanel show={this.props.showReassignFlag}  containerDetailsForPopUp = {this.props.containerDetailsForReassignPopUp} oneTimeValues={this.props.oneTimeValues} reassignContainer={this.props.reassignContainerToFlightAction} toggle={this.toggleReassign} initialValues={{ actualWeight:this.props.actualWeight,reassignFilterType: "C", scanDate: this.props.scanDate, mailScanTime:this.props.scanTime, destination:this.props.finalDestination, flightCarrierCode:this.props.carrierCode}} onClose={this.props.onClose} />
                    <TransferPanel show={this.props.showTransferFlag} onClose={this.props.onClose} transferContainer={this.props.transferContainerAction} toggle={this.toggleTransfer} initialValues={{transferFilterType: "C", scanDate: this.props.currentDate, mailScanTime:this.props.scanTime}} />
                    <ReassignFlightPanel 
                   show={this.props.showReassignFlightFlag} 
                   reassignContainer={this.props.reassignContainerAction} 
                   toggle={this.toggleReassign} 
                   initialValues={{ 
                   scanDate: this.props.scanDate, 
                   mailScanTime:this.props.scanTime,
                   dest:this.props.destination,
                   fromDate: this.props.reassignFromDate,
                   toDate:this.props.reassignToDate,
                   actualWeight:this.props.actualWeight,
                   contentId:this.props.contentID,
                   oneTimeValues:this.props.oneTimeValues,
                    flightnumber:{
                        carrierCode : this.props.carrierCode
                            }
                        }}
                  uldToBarrowAllow={this.props.uldToBarrowAllow}
                   onClose={this.props.onClose}
                   listFlightDetailsForReassign={this.props.listFlightDetailsForReassign}
                   flightDetails={this.props.flightDetails}
                   oneTimeValues={this.props.oneTimeValues}
                   flightDetailsPage={this.props.flightDetailsPage}
                   getNewPage={this.props.getNewPage}
                   saveSelectedFlightsIndex={this.props.saveSelectedFlightsIndex}
                   reassignContainerToFlightAction={this.props.reassignContainerToFlightAction}
                   toggleFn={this.props.onClose}
                   clearReassignFlightPanel={this.props.clearReassignFlightPanel}
                   multipleFlag={this.props.multipleFlag}
                   containerDetailsForPopUp = {this.props.containerDetailsForReassignPopUp} />
                   <TransferFlightPanel 
                   show={this.props.showTransferFlightFlag} 
                   reassignContainer={this.props.reassignContainerAction} 
                   toggle={this.toggleReassign} 
                   uldTobarrow={this.props.uldTobarrow} 
                   initialValues={{ 
                   scanDate: this.props.scanDate, 
                   mailScanTime:this.props.scanTime,
                   dest:this.props.destinationForTransferPopup,
                   fromDate: this.props.reassignFromDate,
                   toDate:this.props.reassignToDate,
                   actualWeight:this.props.actualWeight,
                   contentId:this.props.contentID,
                   oneTimeValues:this.props.oneTimeValues,
                    flightnumber:{
                        carrierCode : this.props.carrierCode
                            }
                        }}
                   onClose={this.props.onClose}
                   listFlightDetailsForTransfer={this.props.listFlightDetailsForTransfer}
                   flightDetails={this.props.flightDetails}
                   oneTimeValues={this.props.oneTimeValues}
                   flightDetailsPage={this.props.flightDetailsPage}
                   getNewPage={this.props.getNewPageTransferPanel}
                   saveSelectedFlightsIndex={this.props.saveSelectedFlightsIndex}
                   transferContainerToFlightAction={this.props.transferContainerToFlightAction}
                   toggleFn={this.props.onClose}
                   clearTransferFlightPanel={this.props.clearTransferFlightPanel}
                   multipleFlag={this.props.multipleFlag}
                   containerDetailsForPopUp={this.props.containerDetailsForPopUp} />
            </div>
        );
    }
}

export default wrapForm('containerdetail')(DetailsPanel)