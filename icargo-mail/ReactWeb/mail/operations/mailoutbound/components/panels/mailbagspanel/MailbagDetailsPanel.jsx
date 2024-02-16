import React, { Component } from 'react';
import MailbagDetailsTable from './MailbagDetailsTable.jsx';
import { MailbagDetailsTabPanel } from './MailbagDetailsTabPanel.jsx'
import MailbagDSNViewTable from './MailbagDSNViewTable.jsx';
import PropTypes from 'prop-types';


export default class MailbagDetailsPanel extends Component {

    constructor(props) {
        super(props);
        this.selectedMail = []
        this.selectedMailDetails = []
        
       //this.toggleMailPopup = this.toggleMailPopup.bind(this);
      //  this.onMailBagActionClick = this.onMailBagActionClick.bind(this);
    }

 
   

    onMailbagRowSelection = (data) => {
        // let count = this.state.mailbagrowClkCount;
        if (data.index > -1) {
            if (data.isRowSelected) {
                // count = ++count
                if (!this.selectedMail.includes(data.index)) {

                    this.selectedMail.push(data.index);
                    //this.selectedMailDetails.push(this.props.mailbagslist[data.index]);
                    this.props.onMailRowSelect(data.isRowSelected,data.index);
                }

            } else {
                // count = --count
                let positionNum = this.selectedMail.indexOf(data.index);
                if (positionNum > -1) {
                    this.selectedMail.splice(positionNum, 1);
                    this.selectedMailDetails.splice(positionNum, 1);
                }
            }
        }
        else {
            if ((data.isRowSelected !== undefined && data.isRowSelected) || (data.event !== undefined && data.event.target.checked)) {
                this.selectedMail = [-1];
                this.selectedMailDetails = this.props.mailbagslist;
            } else {
                this.selectedMail = [];
                this.selectedMailDetails = [];
            }
        }

    }
    

 
  


    render() {
        let damageCodes = [];
       // let mailStatus = new Map();
        //let operationType = new Map();

        return (
            <div className="mail-outbound-mailbag-details-list-small">
                <div className="detailpanel d-flex flex-column flex-grow-1">

                    <MailbagDetailsTabPanel  allMailbagIconAction = {this.props.allMailbagIconAction} activeMainTab={this.props.activeMailbagTab} changeDetailsTab={this.props.changeMailTab}   />
                   
                            
                                <div className="card border-0" style={{ display: this.props.activeMailbagTab === 'MAIL_VIEW' ? null : 'none' }}>
                                <MailbagDetailsTable
                                    selectedContainerIndex={this.props.selectedContainerIndex}
                                    selectedMailbagsIndex={this.props.selectedMailbagsIndex}
                                    damageCodes={damageCodes}
                                    mailbagslist={this.props.mailbagslist}
                                    mailbags={this.props.mailbags}
                                    onMailBagAction={this.props.onMailBagAction}
                                    mailbagAction={this.props.mailbagAction}
                                    containerDetails={this.props.containerDetails}
                                    selectedMailbags={this.props.selectedMailbags}
                                    toggleMailPopup={this.toggleMailPopup}
                                    mailcontainerDetails={this.props.mailcontainerDetails}
                                    flightValidation={this.props.flightValidation}
                                    saveNewContainer={this.props.saveNewContainer}
                                    reassignSave={this.props.reassignSave}
                                    onMailbagRowSelection={this.onMailbagRowSelection}
                                    selectedContainer={this.props.selectedContainer}
                                    listContainer={this.props.listContainer}
                                    mailbagOneTimeValues={this.props.mailbagOneTimeValues}
                                    oneTimeValues={this.props.oneTimeValues}
                                    reassignContainers={this.props.reassignContainers}
                                    flightActionsEnabled={this.props.flightActionsEnabled}
                                    showImportPopup={this.props.showImportPopup}
                                    containerJnyID={this.props.containerJnyID}
                                    importedMailbagDetails={this.props.importedMailbagDetails}
                                    onClickImportPopup={this.props.onClickImportPopup}
                                    onCloseImport={this.props.onCloseImport}
                                    onImportMailbags={this.props.onImportMailbags}
                                    newMailbags={this.props.newMailbags}
                                    activeMailbagAddTab={this.props.activeMailbagAddTab}
                                    addModifyFlag={this.props.addModifyFlag}
                                    onDeleteRow={this.props.onDeleteRow}
                                    remarkspopup={this.props.remarkspopup}
                                    scanTimePopup={this.props.scanTimePopup}
                                    initialValues={this.props.initialValues}
                                    onSavemailbagActions={this.props.onSavemailbagActions}
                                    currentDate={this.props.currentDate}
                                    currentTime={this.props.currentTime}
                                    defaultWeightUnit={this.props.defaultWeightUnit}
                                    previousRowWeight={this.props.previousRowWeight}
                                    postalCodes={this.props.postalCodes}
                                    mailbagReturnPopup={this.props.mailbagReturnPopup}
                                    closeReturnPopup={this.props.closeReturnPopup}
                                    mailbagReturnDamageFlag={this.props.mailbagReturnDamageFlag}
                                    doReturnMail={this.props.doReturnMail}
                                    showReassign={this.props.showReassign}
                                    carrierCode = {this.props.carrierCode}
                                    closeReassign={this.props.closeReassign}
                                    onCloseRemarksPopup={this.props.onCloseRemarksPopup}
                                    oncloseScanTimePopup={this.props.oncloseScanTimePopup}
                                    onApplyMailbagFilter={this.props.onApplyMailbagFilter} 
                                    onClearMailbagFilter={this.props.onClearMailbagFilter} 
                                    flightCarrierflag={this.props.flightCarrierflag}
                                    mailBagTableFilterInitialValues={this.props.mailBagTableFilterInitialValues}
									openHistoryPopup={this.props.openHistoryPopup}
                                    navigateToOffload={this.props.navigateToOffload}
                                    existingMailbags={this.props.existingMailbags}
                                    existingMailbagPresent={this.props.existingMailbagPresent}
                                    onCloseExistingMailbag={this.props.onCloseExistingMailbag}
                                    reassignExistingMailbags={this.props.reassignExistingMailbags}
                                    reassignMailbagsCancel={this.props.reassignMailbagsCancel}
                                    closeAddMailbagPopup={this.props.closeAddMailbagPopup}
                                    onApplyMailbagSort = {this.props.onApplyMailbagSort}
                                    containersPresent={this.props.containersPresent} 
                                    reassignFilterType={this.props.reassignFilterType}
                                    pous={this.props.pous}
                                    saveSelectedMailbagIndex={this.props.saveSelectedMailbagIndex}
                                    pabuiltUpdate={this.props.pabuiltUpdate}
                                    isValidFlight={this.props.isValidFlight}
                                    destination={this.props.destination}
                                    uldDestination={this.props.uldDestination}
                                    reassignDefaultValues={this.props.reassignDefaultValues}
                                    showTransfer={this.props.showTransfer}
                                    transferDefaultValues={this.props.transferDefaultValues}
                                    clearTransferPanel={this.props.clearTransferPanel}
                                    listTransfer={this.props.listTransfer}
                                    transferContainers={this.props.transferContainers}
                                    addContainerButtonShow={this.props.addContainerButtonShow}
                                    pousForTransfer={this.props.pousForTransfer}
                                    clearAddContainerPopoverForTransfer={this.props.clearAddContainerPopoverForTransfer}
                                    closeTranferPopup={this.props.closeTranferPopup}
                                    transferSave={this.props.transferSave}
                                    ownAirlineCode={this.props.ownAirlineCode}
                                    partnerCarriers={this.props.ownAirlineCode}
                                    getNextPageContainerForTransfer={this.props.getNextPageContainerForTransfer} 
                                    disableForModify={this.props.disableForModify} 
                                    damageDetails={this.props.damageDetails}
                                    damageDetail = {this.props.damageDetail} 
                                    saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                                   {...this.props} />
                                    </div>
                                    <div className="card border-0" style={{ display:  this.props.activeMailbagTab === 'DSN_VIEW' ? null : 'none' }}>
                              
                                <MailbagDSNViewTable 
                                    listMailbagsInDSNView={this.props.listMailbagsInDSNView} 
                                    mailbaglistdsnview={this.props.mailbaglistdsnview}  
                                    dsnAction={this.props.dsnAction}  
                                    currentDate={this.props.currentDate}
                                    currentTime={this.props.currentTime}
                                    flightActionsEnabled={this.props.flightActionsEnabled}
                                    onApplyMailbagDSNFilter={this.props.onApplyMailbagDSNFilter}
                                    onClearMailbagDSNFilter={this.props.onClearMailbagDSNFilter}
                                    dsnFilterValues={this.props.dsnFilterValues}
                                    oneTimeValues={this.props.oneTimeValues}
                                    flightActionsEnabled={this.props.flightActionsEnabled} 
                                    onApplyDsnFilter={this.props.onApplyDsnFilter} 
                                    onClearDsnFilter={this.props.onClearDsnFilter}
                                    initialDsnValues={this.props.initialDsnValues?this.props.initialDsnValues:{}}
									
                                    {...this.props} />

</div>

                </div>
            </div>
        );
    }
}
MailbagDetailsPanel.propTypes = {
    onMailRowSelect:PropTypes.func,
    mailbagslist:PropTypes.array,
    activeMailbagTab:PropTypes.string,
    selectedContainerIndex:PropTypes.number,
    selectedMailbagsIndex:PropTypes.number,
    allMailbagIconAction:PropTypes.func,
    mailbags:PropTypes.object,
    onMailBagAction:PropTypes.func,
    containerDetails:PropTypes.object,
    selectedMailbags:PropTypes.object,
    mailcontainerDetails:PropTypes.object,
    flightValidation:PropTypes.object,
    saveNewContainer:PropTypes.func,
    reassignSave:PropTypes.func,
    selectedContainer:PropTypes.object,
    listContainer:PropTypes.array,
    mailbagOneTimeValues:PropTypes.object,
    oneTimeValues:PropTypes.object,
    reassignContainers:PropTypes.func,
    flightActionsEnabled:PropTypes.bool,
    showImportPopup:PropTypes.bool,
    containerJnyID:PropTypes.string,
    onClickImportPopup:PropTypes.func,
    onCloseImport:PropTypes.func,
    onImportMailbags:PropTypes.func,
    newMailbags:PropTypes.array,
    activeMailbagAddTab:PropTypes.func,
    addModifyFlag:PropTypes.bool,
    onDeleteRow:PropTypes.func,
    remarkspopup:PropTypes.func,
    scanTimePopup:PropTypes.bool,
    initialValues:PropTypes.object,
    onSavemailbagActions:PropTypes.func,
    currentDate:PropTypes.string,
    currentTime:PropTypes.string,
    defaultWeightUnit:PropTypes.string,
    previousRowWeight:PropTypes.string,
    postalCodes:PropTypes.object,
    mailbagReturnPopup:PropTypes.bool,
    closeReturnPopup:PropTypes.func,
    mailbagReturnDamageFlag:PropTypes.bool,
    doReturnMail:PropTypes.func,
    showReassign:PropTypes.func,
    closeReassign:PropTypes.func,
    onCloseRemarksPopup:PropTypes.func,
    oncloseScanTimePopup:PropTypes.func,
    onApplyMailBagFilter:PropTypes.func,
    onClearMailbagFilter:PropTypes.func,
    flightCarrierflag:PropTypes.bool,
    mailBagTableFilterInitialValues:PropTypes.object,
    openHistoryPopup:PropTypes.func,
    navigateToOffload:PropTypes.func,
    existingMailbags:PropTypes.object,
    existingMailbagPresent:PropTypes.object,
    onCloseExistingMailbag:PropTypes.func,
    reassignExistingMailbags:PropTypes.func,
    reassignMailbagsCancel:PropTypes.func,
    closeAddMailbagPopup:PropTypes.func,
    listMailbagsInDSNView:PropTypes.object,
    mailbaglistdsnview:PropTypes.object,
    dsnAction:PropTypes.func,
    changeMailTab:PropTypes.func,
    onApplyMailbagFilter:PropTypes.func,
    onApplyMailbagSort:PropTypes.func,
    containersPresent:PropTypes.string,
    onApplyDsnFilter:PropTypes.func,
    onClearDsnFilter:PropTypes.func,
    initialDsnValues:PropTypes.object
}