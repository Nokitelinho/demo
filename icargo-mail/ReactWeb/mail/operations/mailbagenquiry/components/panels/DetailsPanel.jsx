import React, { Component } from 'react';
import MailbagDetailsTable from './MailbagDetailsTable.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DataDisplay, ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'

class DetailsPanel extends Component {
    constructor(props) {
        super(props);

        this.selectedMail = []
        this.selectedMailDetails = []
        this.selectedMailbags = []
        this.state = {
            showReturn: false,
            showDamage: false,
            showTransfer: false,
            showReassign: false,
            mailndex: ''

        }
        this.toggleMailPopup = this.toggleMailPopup.bind(this);

    }
    toggleMailPopup() {

        this.setState({
            showReturn: false,
            showDamage: false,
            showTransfer: false,
            showReassign: false,
            mailndex: 0
        });
        this.props.close();
    }

    onMailbagRowSelection = (data) => {

        if (data.index > -1) {
            if (data.isRowSelected) {

                this.selectMailbag(data.index);
            } else {
                this.unSelectMailbag(data.index);
            }
        }
        else {
            if ((data) && (data.checked)) {
                this.selectAllMailbags(-1);
            } else {
                this.unSelectAllMailbags();
            }
        }

        if (data.index > -1) {
            if (data.isRowSelected) {
                if (!this.selectedMail.includes(data.index)) {

                    this.selectedMail.push(data.index);
                    //this.selectedMailDetails.push(this.props.mailbagslist[data.index]);
                    this.props.onMailRowSelect(data.isRowSelected, data.index);
                }

            } else {
                let positionNum = this.selectedMail.indexOf(data.index);
                if (positionNum > -1) {
                    this.selectedMail.splice(positionNum, 1);
                    // this.selectedMailDetails.splice(positionNum, 1);
                    this.props.onMailRowSelect(data.isRowSelected, positionNum);
                }
            }


        }
        else {
            if ((data.isRowSelected !== undefined && data.isRowSelected) || (data.event !== undefined && data.event.target.checked)) {
                this.selectedMail = [-1];
                //this.selectedMailDetails = this.props.mailbagslist;
                this.props.onMailRowSelect(true, -1);
            } else {
                this.selectedMail = [];
                //this.selectedMailDetails = [];
                this.props.onMailRowSelect(false, -1);
            }

        }

        console.log(this.selectedMail);

    }

    onAllMailBagActionClick = (functionName) => {
        if (this.props.filterValues.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        if (functionName === 'VIEW_MAIL_HISTORY') {
            this.props.openHistoryPopup();
        } else {

        if (functionName === 'RETURN') {
            this.props.validateMailbags(functionName);
            this.setState({
                showReturn: this.props.showReturn,
                mailndex: selectedIndex
            });
        } else if (functionName === 'REASSIGN') {
            this.props.validateMailbags(functionName);
            this.setState({
                showReassign: this.props.showReassign,
                mailndex: selectedIndex
            });
        } else if (functionName === 'TRANSFER') {
            this.props.validateMailbags(functionName);
            this.setState({
                showTransfer: this.props.showTransfer,
                //mailndex: selectedIndex
            });
        } else if (functionName === 'VIEW_DAMAGE') {
            //this.props.checkDamage(this.props.damageDetails);
            this.setState({
                showDamage: this.props.showViewDamage
            });

        } else if(functionName === 'OFFLOAD'){
            const selectedIndex=this.selectedMail;
            this.props.onMailBagAction(functionName, selectedIndex);
        } else if(functionName === 'DELIVER_MAIL'){
            const selectedIndex=this.selectedMail;
            this.props.onMailBagAction(functionName, selectedIndex);
        } else if(functionName === 'MODIFY_ORG_DST'){
            const selectedIndex=this.selectedMail;
            this.props.onMailBagAction(functionName, selectedIndex);
        }

        //this.props.onMailBagAction(functionName, selectedIndex);
        }
    }
    }

    onMailBagActionClick = (event) => {
        if (this.props.filterValues.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let functionName = event.target.dataset.mode
        let selectedIndex = event.target.dataset.key

        if (functionName === 'VIEW_MAIL_HISTORY') {
            let data = {index:selectedIndex}
            this.props.openHistoryPopup(data);
        } else {

        if (functionName === 'RETURN') {
            this.props.onMailRowSelect(true, selectedIndex);
            this.props.validateMailbags(functionName, selectedIndex);
            this.setState({
                showReturn: this.props.showReturn,
                mailndex: selectedIndex
            });
        } else if (functionName === 'REASSIGN') {
            this.props.validateMailbags(functionName, selectedIndex);
            this.setState({
                showReassign: this.props.showReassign,
                mailndex: selectedIndex
            });
        } else if (functionName === 'TRANSFER') {
            this.props.validateMailbags(functionName, selectedIndex);
            this.setState({
                showTransfer: this.props.showTransfer,
                mailndex: selectedIndex
            });
        } else if (functionName === 'VIEW_DAMAGE') {
            //this.props.checkDamage(this.props.damageDetails);
            this.props.onMailRowSelect(true, selectedIndex);
            
        this.props.onMailBagAction(functionName, selectedIndex);
            this.setState({
                showDamage: this.props.showViewDamage
            });

        } else {

            //this.damagePopup();
        }

        this.props.onMailBagAction(functionName, selectedIndex);
        }
    }
    }

    selectMailbag = (mailbag) => {
        this.selectedMailbags.push(mailbag);
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }
    unSelectMailbag = (mailbag) => {
        let index = -1;
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            var element = this.selectedMailbags[i];
            if (element === mailbag) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedMailbags.splice(index, 1);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }
    selectAllMailbags = () => {

        this.selectedMailbags = [];
        for (let i = 0; i < this.props.mailbagsdetails.results.length; i++) {
            this.selectedMailbags.push(i);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }

    unSelectAllMailbags = () => {
        this.selectedMailbags = []
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }

    render() {

        let damageCodes = [];
        let mailStatus = new Map();
        let operationType = new Map();

        if (!isEmpty(this.props.oneTimeValues)) {
            damageCodes = this.props.oneTimeValues['mailtracking.defaults.return.reasoncode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));

            this.props.oneTimeValues['mailtracking.defaults.mailstatus'].forEach(status => {
                mailStatus.set(status.fieldValue, status.fieldDescription);
            });
            this.props.oneTimeValues['mailtracking.defaults.operationtype'].forEach(status => {
                operationType.set(status.fieldValue, status.fieldDescription);
            });
        }


        return (
            (this.props.screenMode === 'initial') ?
                <div className="card">
                    <div className="card-header">
                        <div className="col p-0">
                            <h4>Mail Bag Details</h4>
                        </div>
                    </div>
                    <div className="card-body p-0" style={{display: "flex",flexDirection: "column", flexGrow: 1 }}>
                        <NoDataPanel />
                    </div>
                </div> :
                <div className="card mailbag-details-list">
                    <ICustomHeaderHolder tableId='detailsPanel' />
                    <MailbagDetailsTable mailbagslist={this.props.mailbagslist}
                        mailbagsdetails={this.props.mailbagsdetails}
                        getNewPage={this.props.onlistMailbagsEnquiry}
                        onMailBagAction={this.props.onMailBagAction}
                        damageCodes={damageCodes} mailStats={mailStatus}
                        operationType={operationType}
                        popupMode={this.props.popupMode}
                        damageDetails={this.props.damageDetails}
                        damageDetail = {this.props.damageDetail}
                        doReturnMail={this.props.doReturnMail}
                        exportToExcel={this.props.exportToExcel}
                        listContainer={this.props.listContainer}
                        containerDetails={this.props.containerDetails}
                        reassignSave={this.props.reassignSave}
                        transferSave={this.props.transferSave}
                        saveNewContainer={this.props.saveNewContainer}
                        pabuiltUpdate={this.props.pabuiltUpdate}
                        flightValidation={this.props.flightValidation}
                        selectedMailbags={this.props.selectedMailbags}
                        selectedMailbag={this.props.selectedMailbag}
                        onMailRowSelect={this.props.onMailRowSelect}
                        toggleMailPopup={this.props.close}
                        onMailBagActionClick={this.onMailBagActionClick}
                        onMailbagRowSelection={this.onMailbagRowSelection}
                        showReturn={this.props.showReturn}
                        showDamage={this.props.showViewDamage}
                        showReassign={this.props.showReassign}
                        showTransfer={this.props.showTransfer}
                        mailndex={this.state.mailndex}
                        saveActualWeight={this.props.saveActualWeight}
                        tableValues={this.props.tableValues}
                        openHistoryPopup={this.props.openHistoryPopup}
                        close={this.props.close}
                        imagepopupclose={this.props.imagepopupclose}
                        onApplyMailbagFilter={this.props.onApplyMailbagFilter}
                        onClearMailbagFilter={this.props.onClearMailbagFilter}
                        oneTimeValues={this.props.oneTimeValues}
                        updateSortVariables={this.props.updateSortVariables}
                        initialValues={this.props.initialValues}
                        postalCodes={this.props.postalCodes}
                        isValidFlight={this.props.isValidFlight}
                        onAllMailBagActionClick={this.onAllMailBagActionClick}
                        tableFilterInitialValues={this.props.tableFilterInitialValues}
                        capFlag={this.props.capFlag}
                        clearReassignForm={this.props.clearReassignForm}
                        toggleDamageMailPopup={this.toggleMailPopup}
                        pous={this.props.pous}
                        clearTransferForm={this.props.clearTransferForm}
                        destination={this.props.destination}  
                        mailbagdetailForm={this.props.mailbagdetailForm}
                        defaultValues={this.props.defaultValues}
                        clearContainerDetails={this.props.clearContainerDetails}
                        ownAirlineCode={this.props.ownAirlineCode}
                        partnerCarriers={this.props.partnerCarriers}
                        mailbagtable={this.props.mailbagtable}      
                        clearAddContainerPopover={this.props.clearAddContainerPopover}    
                        updateOrgAndDest={this.props.updateOrgAndDest} 
                        showModifyPopup={this.props.showModifyPopup}    
                        checkValidOrgAndDest={this.props.checkValidOrgAndDest}
                        newMailbagsTable={this.props.newMailbagsTable}
                        onSaveOrgAndDest={this.props.onSaveOrgAndDest}
                        onCloseModifyPopup={this.props.onCloseModifyPopup}
                        dummyAirportForDomMail={this.props.dummyAirportForDomMail}
                        filterValues={this.props.filterValues}
                        displayError={this.props.displayError}
                        saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                               />
                </div>
        );
    }
}
export default wrapForm('mailbagdetail')(DetailsPanel)