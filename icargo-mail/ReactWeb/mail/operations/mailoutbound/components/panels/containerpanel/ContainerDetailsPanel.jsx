import React, { Component } from 'react';
import ContainerDetailsTable from './ContainerDetailsTable.jsx';
import PropTypes from 'prop-types';
import ReassignPanel from '../../../../listcontainer/components/panels/ReassignPanel.jsx'
import ReassignFlightPanel from './ReassignFlightPanel.jsx'
import AttachAwbPanel from './AttachAwbPanel.jsx'
import AttachRoutingPanel from './AttachRoutingPanel.jsx'
import ContainerCustome from '../custompanels/ContainerTable.js'
import  ScanTimePanel  from '../mailbagspanel/ScanTimePanel.jsx'
import TransferPanel from '../../../../listcontainer/components/panels/TransferPanel.jsx';
import TransferFlightPanel from '../../../../mailinbound/components/panels/TransferPanel.jsx';
export default class ContainerDetailsPanel extends Component {
     constructor(props) {
        super(props);        
             this.state = {
        showReassign: false,
        showAttachRouting:false
       
      }        
    }
    toggleAttachRouting=()=>{
        this.setState({showAttachRouting:!this.state.showAttachRouting});
    }
    toggleReassign=()=>{
        this.setState({showReassign:!this.state.showReassign});
    } 
    closePopUp=()=>{
        this.props.closePopUp('ATTACH_ROUT');
    }
    addContainerClk = () => {
        if (this.props.selectedContCount > 1) {
            this.props.displayError('More than one container is selected');
            // this.setState({show:false});
        }
        else
        this.props.containerAction({ actionName: 'ADD_CONTAINER'});
      

    }
    toggleAddContainerPopup = () => {
        this.props.closeAddContainerPopup();
    }

   
   
    render() {
        return (
         <div className="mail-outbound-container-details-list-small">
                      <ContainerCustome allContainerIconAction={this.props.allContainerIconAction} />               
                <ContainerDetailsTable containers={this.props.containers} containerlist={this.props.containerlist} displayMailbags={this.props.displayMailbags} saveSelectedContainersIndex={this.props.saveSelectedContainersIndex} selectedContainerIndex={this.props.selectedContainerIndex} containerMultipleSelectionAction={this.props.containerMultipleSelectionAction} mailAcceptance={this.props.mailAcceptance} containerAction={this.props.containerAction} onListToModifyContainer={this.props.onListToModifyContainer} onSaveContainer={this.props.onSaveContainer} onUnMountAddContPopup={this.props.onUnMountAddContPopup}  addContainerShow= {this.props.addContainerShow} onclickPABuilt={this.props.onclickPABuilt}{...this.props} PABuiltChecked={this.props.PABuiltChecked} wareHouses={this.props.wareHouses} selectedContainer={this.props.selectedContainer} displayMode={this.props.displayMode} initialValues={this.props.initialValues} onLoadAddContPopup={this.props.onLoadAddContPopup} onApplyContainerFilter={this.props.onApplyContainerFilter}  show={this.props.addContainerShow} addContainerClk={this.addContainerClk} 
                  toggle={this.toggleAddContainerPopup}   containerActionMode={this.props.containerActionMode} enableContainerSave={this.props.enableContainerSave}  flightActionsEnabled={this.props.flightActionsEnabled} assignToContainer={this.props.assignToContainer} carditMailbags={this.props.carditMailbags} activeMainTab={this.props.activeMainTab} selectedCarditIndex={this.props.selectedCarditIndex} lyingmailbagcount={this.props.lyingmailbagcount} selectedLyinglistIndex={this.props.selectedLyinglistIndex} selectedDeviationList={this.props.selectedDeviationList} currentDate={this.props.currentDate} navigateToOffload={this.props.navigateToOffload} populateDestForBarrow={this.props.populateDestForBarrow} carditmailbagcount={this.props.carditmailbagcount} onApplyContainerSort={this.props.onApplyContainerSort} flightCarrierflag={this.props.flightCarrierflag} getContainerNewPageCarrier={this.props.getContainerNewPageCarrier} tableFilter={this.props.tableFilter} isBarrow={this.props.isBarrow} /> 
                  <AttachAwbPanel show={this.props.attachClose} 
                   listAwbDetails={this.props.listAwbDetails} 
                   clearAttachAwbPanel={this.props.clearAttachAwbPanel}
                   toggle={this.toggleAttachAwb} onSaveAttachAwb={this.props.onSaveAttachAwb}
                   closeAttachAwb= {this.props.closeAttachAwb}
                   initialValues={this.props.attachAwbDetails} attachawbListSuccess={this.props.attachawbListSuccess} attachAwbOneTimeValues={this.props.attachAwbOneTimeValues}/> 
                 <AttachRoutingPanel show={this.props.attachRoutingClose} listAttachRouting={this.props.listAttachRouting} clearAttachRoutingPanel={this.props.clearAttachRoutingPanel}
                    toggle={this.toggleAttachRouting} saveAttachRouting={this.props.saveAttachRouting} closeAttachRouting={this.closePopUp}
                    initialValues={this.props.attachRoutingInitialValues} attachroutingListSuccess={this.props.attachroutingListSuccess} attachRoutingFilter={this.props.attachRoutingFilter} attachRoutingOneTimeValues={this.props.attachRoutingOneTimeValues}/>
                   <ReassignPanel reassignContainer={this.props.reassignContainerToFlightAction} oneTimeValues={this.props.oneTimeValues} containerDetailsForPopUp={this.props.containerDetailsForPopUp} show={this.props.showReassignFlag}  toggle={this.toggleReassign} initialValues={{actualWeight:this.props.actualWeight, reassignFilterType: "C", scanDate: this.props.scanDate, mailScanTime:this.props.scanTime, destination:this.props.finalDestination, flightCarrierCode: this.props.flightCarrierCode }} onClose={this.props.onClose} />
                   <ReassignFlightPanel 
                   show={this.props.showReassignFlightFlag} 
                   reassignContainer={this.props.reassignContainerAction} 
                   toggle={this.toggleReassign} 
                   initialValues={{ 
                   scanDate: this.props.scanDate, 
                   fromDate: this.props.reassignFromDate,
                   toDate:this.props.scanDate,
                   fromDate: this.props.reassignFromDate,
                   toDate:this.props.reassignToDate,
                   mailScanTime:this.props.scanTime,
                   dest:this.props.destination,
                   actualWeight:this.props.actualWeight,
                   oneTimeValues:this.props.oneTimeValues,
                   contentId:this.props.contentId,
                    flightnumber:{
                        carrierCode : this.props.flightCarrierCode
                    } }} 
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
                   containerDetailsForPopUp={this.props.containerDetailsForPopUp}
                   flightDisplayPage={this.props.flightDisplayPage}
                   defaultPageSize={this.props.defaultPageSize}
                       />
            <ScanTimePanel   
                show={this.props.openScanTimePopup} 
                toggleMailPopup = {this.props.closeAssignScanTimePopup } 
                initialValues={this.props.scanTimeDefaultValue} 
                onSavemailbagActions={this.props.continueAssignToContainer}/>
         <TransferPanel show={this.props.showTransferFlag} onClose={this.props.onCloseTransfer} 
               transferContainer={this.props.transferContainerAction}  
              toggle={this.onCloseTransfer} 
              initialValues={{transferFilterType: "C", scanDate: this.props.scanDate, mailScanTime:this.props.scanTime}} 
            /> 
           < TransferFlightPanel
                 show={this.props.showTransferFlightFlag}
                 initialValues={{ 
                    scanDate: this.props.scanDate,
                    fromDate: this.props.reassignFromDate,
                    toDate:this.props.reassignToDate,
                    mailScanTime:this.props.scanTime,
                    dest:this.props.destination,
                    actualWeight:this.props.actualWeight,
                    oneTimeValues:this.props.oneTimeValues,
                    contentId:this.props.contentId,
                     flightnumber:{
                         carrierCode : this.props.flightCarrierCode
                     } }} 
                listFlightDetailsForTransfer={this.props.listFlightDetailsForReassign}
                flightDetails={this.props.flightDetails}
                oneTimeValues={this.props.oneTimeValues}
                flightDetailsPage={this.props.flightDetailsPage}
                getNewPage={this.props.getNewPage}
                saveSelectedFlightsIndex={this.props.saveSelectedFlightsIndex}
                clearTransferFlightPanel={this.props.clearTransferFlightPanel}
                containerDetailsForPopUp={this.props.containerDetailsForPopUp}
                uldToBarrow={this.props.uldToBarrowAllow}
                onClose={this.props.onCloseTransfer}
                toggleFn={this.props.onCloseTransfer}
                reassignContainer={this.props.transferContainerAction} 
                transferContainerToFlightAction={this.props.transferContainerToFlightAction}
           />
            </div>
        );
    }
}
ContainerDetailsPanel.propTypes = {
    closePopUp: PropTypes.func,
    selectedContCount: PropTypes.string,
    displayError: PropTypes.func,
    containerAction: PropTypes.func,
    closeAddContainerPopup: PropTypes.func,
    allContainerIconAction: PropTypes.func,
    containers: PropTypes.object,
    containerlist: PropTypes.array,
    initialValues: PropTypes.object,
    displayMailbags: PropTypes.object,
    containerActionMode: PropTypes.string,
    enableContainerSave: PropTypes.bool,
    saveSelectedContainersIndex: PropTypes.func,
    selectedContainerIndex: PropTypes.array,
    containerMultipleSelectionAction: PropTypes.func,
    mailAcceptance: PropTypes.object,
    onListToModifyContainer: PropTypes.func,
    onSaveContainer: PropTypes.func,
    onUnMountAddContPopup: PropTypes.func,
    addContainerShow: PropTypes.bool,
    onclickPABuilt: PropTypes.func,
    PABuiltChecked: PropTypes.bool,
    wareHouses: PropTypes.array,
    selectedContainer: PropTypes.string,
    displayMode: PropTypes.string,
    onLoadAddContPopup: PropTypes.func,
    onApplyContainerFilter: PropTypes.func,
    flightActionsEnabled: PropTypes.string,
    assignToContainer: PropTypes.func,
    carditMailbags: PropTypes.object,
    activeMainTab: PropTypes.string,
    selectedCarditIndex: PropTypes.array,
    lyingmailbagcount: PropTypes.string,
    selectedLyinglistIndex: PropTypes.array,
    selectedDeviationList: PropTypes.array,
    currentDate: PropTypes.func,
    navigateToOffload: PropTypes.func,
    populateDestForBarrow: PropTypes.func,
    carditmailbagcount: PropTypes.number,
    onApplyContainerSort: PropTypes.func,
    flightCarrierflag: PropTypes.string,
    getContainerNewPageCarrier: PropTypes.func,
    attachClose: PropTypes.bool,
    listAwbDetails: PropTypes.func,
    clearAttachAwbPanel: PropTypes.func,
    onSaveAttachAwb: PropTypes.func,
    closeAttachAwb: PropTypes.func,
    attachAwbDetails: PropTypes.object,
    attachawbListSuccess: PropTypes.bool,
    attachAwbOneTimeValues: PropTypes.array,
    attachRoutingClose: PropTypes.bool,
    listAttachRouting: PropTypes.func,
    clearAttachRoutingPanel: PropTypes.func,
    saveAttachRouting: PropTypes.func,
    attachRoutingInitialValues: PropTypes.object,
    attachroutingListSuccess: PropTypes.bool,
    attachRoutingFilter: PropTypes.object,
    attachRoutingOneTimeValues: PropTypes.array,
    showReassignFlag: PropTypes.bool,
    reassignContainerAction: PropTypes.func,
    onClose: PropTypes.func,
}