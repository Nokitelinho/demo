import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Input } from 'reactstrap';
import ReturnMail from './ReturnMail.jsx';
import ReassignPanel from './ReassignPanel.jsx';
import TransferPanel from './TransferPanel.jsx';
import ViewDamage from './ViewDamage.jsx';
import MailbagTableFilter from './MailbagTableFilter.jsx';
import MailBagActionPanel from './MailBagActionPanel.jsx';
import ModifyOriginAndDestination from './ModifyOriginAndDestination.jsx';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

export default class MailbagDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.saveActualWeight = this.saveActualWeight.bind(this);
    }

   /* saveActualWeight = (rowIndex) => {
        this.props.saveActualWeight(rowIndex, this.props.tableValues[rowIndex].actualWeight);
    }*/

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
        this.saveActualWeight = this.saveActualWeight.bind(this);

    }

    saveActualWeight = (rowIndex) => {
        if (this.props.filterValues.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        const actualWeight=this.props.tableValues[rowIndex].actualWeight;
        this.props.saveActualWeight(rowIndex,actualWeight);        
    }
    }
    updateOrgAndDest = (rowIndex,isOrgUpd) => {
        if(isOrgUpd){
            const malOrg=this.props.mailbagtable[rowIndex].mailorigin;   
            this.props.updateOrgAndDest(rowIndex,malOrg,isOrgUpd);
        }
        else{
            const malDest=this.props.mailbagtable[rowIndex].mailDestination; 
            this.props.updateOrgAndDest(rowIndex,malDest,isOrgUpd); 
        }
      
    }
    isDisableRequired=(cellProps,fromOrigin)=>{
        if(this.props.capFlag){
            return true;
        }
        else if(!this.props.dummyAirportForDomMail||this.props.dummyAirportForDomMail===null){
            return true;
        }
        else if(!fromOrigin&&cellProps&&cellProps.rowData&&cellProps.rowData.mailDestination&&cellProps.rowData.mailDestination===this.props.dummyAirportForDomMail){
          return false;
        }
        else if(fromOrigin&&cellProps&&cellProps.rowData&&cellProps.rowData.mailorigin&&cellProps.rowData.mailorigin===this.props.dummyAirportForDomMail){
            return false;
        }
    
    else{
    return true;
    }
    }
     
    

    render() {
      //  const tableFilterobj = this.props.tableFilter;
        const results = this.props.mailbagslist;
        const rowCount = (results) ? results.length : 0;

        let status = new Map;
        let operationTyp = new Map;
        let actualUnit='Actual Weight';
        if (this.props.mailStats != null) {
            status = this.props.mailStats;
        }
        if (this.props.operationType != null) {
            operationTyp = this.props.operationType;
        }
        if(results.length>0){
            actualUnit=actualUnit+'('+results[0].actualWeightUnit+')';
        }

        try {
            return (
                <Fragment>
                    <ReturnMail
                        show={this.props.showReturn}
                        mailbagDetails={this.props.mailbagslist}
                        damageCodes={this.props.damageCodes}
                        toggleFn={this.props.toggleMailPopup}
                        doReturnMail={this.props.doReturnMail}
                        selectedMail={this.props.mailndex}
                        close={this.props.close}
                        postalCodes={this.props.postalCodes}
                        damageDetail={this.props.damageDetail}
                        selectedMailbag = {this.props.selectedMailbags} />
                    <ViewDamage
                        show={(this.props.showDamage)&&this.props.damageDetails.length>0}
                        mailbagDetails={this.props.mailbagslist}
                        damageDetails={this.props.damageDetails}
                        toggleFn={this.props.toggleDamageMailPopup}
                        close={this.props.close}
                        imagepopupclose={this.props.imagepopupclose}
                        selectedMailbag = {this.props.selectedMailbags} />
                    <TransferPanel
                        show={this.props.showTransfer}
                        mailbagDetails={this.props.mailbagslist}
                        toggleFn={this.props.toggleMailPopup}
                        listContainer={this.props.listContainer}
                        selectedMail={this.props.mailndex}
                        containerDetails={this.props.containerDetails}
                        transferSave={this.props.transferSave}
                        saveNewContainer={this.props.saveNewContainer}
                        pabuiltUpdate={this.props.pabuiltUpdate}
                        flightValidation={this.props.flightValidation}
                        close={this.props.close}
                        isValidFlight={this.props.isValidFlight}
                        initialValues = {this.props.defaultValues}
                        showTransfer={this.props.showTransfer}
                        pous={this.props.pous}
                        clearTransferForm={this.props.clearTransferForm} 
                        clearContainerDetails={this.props.clearContainerDetails}
                        ownAirlineCode={this.props.ownAirlineCode}
                        partnerCarriers={this.props.partnerCarriers}  
                        clearAddContainerPopover={this.props.clearAddContainerPopover}  
                        />
                    <ReassignPanel
                        show={this.props.showReassign}
                        mailbagDetails={this.props.mailbagslist}
                        toggleFn={this.props.toggleMailPopup}
                        listContainer={this.props.listContainer}
                        selectedMail={this.props.mailndex}
                        containerDetails={this.props.containerDetails}
                        reassignSave={this.props.reassignSave}
                        saveNewContainer={this.props.saveNewContainer}
                        flightValidation={this.props.flightValidation}
                        close={this.props.close}
                        initialValues = {this.props.defaultValues}
                        isValidFlight={this.props.isValidFlight}
                        showTransfer={this.props.showTransfer}
                        clearReassignForm={this.props.clearReassignForm}
                        pous={this.props.pous}
                        destination={this.props.destination}
                        pabuiltUpdate={this.props.pabuiltUpdate} 
                        clearAddContainerPopover={this.props.clearAddContainerPopover} 
                        showReassign={this.props.showReassign}
                        saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                          />
                     <ModifyOriginAndDestination
                        show={this.props.showModifyPopup}
                        selectedMailbags={this.props.selectedMailbags}
                        checkValidOrgAndDest={this.props.checkValidOrgAndDest}
                        newMailbagsTable={this.props.newMailbagsTable}
                        onSaveOrgAndDest={this.props.onSaveOrgAndDest}
                        onCloseModifyPopup={this.props.onCloseModifyPopup}
                        dummyAirportForDomMail={this.props.dummyAirportForDomMail}
                    /> 

                    <IMultiGrid
                         form={true}
                        name="mailbagtable"
                        rowCount={rowCount}
                        headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName=""
                        rowHeight={45}
                        rowClassName="table-row"
                        tableId="mailbagtable"
                        sortEnabled={false}
                        resetSelectionOnDataChange={true}
                        onRowSelection={this.props.onMailbagRowSelection}
                      
                        fixedRowCount={1}
                        fixedColumnCount={4}
                        enableFixedRowScroll
                        hideTopRightGridScrollbar
                        hideBottomLeftGridScrollbar
                        
                        customHeader={{
                            headerClass: '',
                            dataConfig: {
                                screenId: 'MTK057',
                                tableId: 'mailbagtable'
                            },
                            "pagination": { "page": this.props.mailbagsdetails, getPage: this.props.getNewPage ,
                            options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' }] },
                            customPanel: <MailBagActionPanel onMailBagAction={this.props.onMailBagAction} selectedMailbags={this.props.selectedMailbags}
                            openHistoryPopup={this.props.openHistoryPopup} onAllMailBagActionClick={this.props.onAllMailBagActionClick} dummyAirportForDomMail={this.props.dummyAirportForDomMail} />,
                            filterConfig: {
                                panel: <MailbagTableFilter oneTimeValues={this.props.oneTimeValues} initialValues={this.props.tableFilterInitialValues}/>,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyMailbagFilter,
                                onClearFilter: this.props.onClearMailbagFilter
                            },
                            exportData: {
                                exportAction: this.props.exportToExcel,
                                pageable: true,
                                addlColumns:[],
                                name: 'Mailbag List'
                            },
                            sortBy: {
                                onSort: this.sortList
                            }
                        }}
                        data={results} 
                        >

                        <Columns>
                            <IColumn
                                width={32}
                                dataKey=""
                                flexGrow={0}
                                className="first-column"
                                hideOnExport >
                                <Cell>
                                    <HeadCell disableSort selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailbagId"
                                label="Mailbag Id"
                                id="mailbagId"
                                width={240}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="latestStatus"
                                label="Latest Operation"
                                id="latestStatus"
                                width={120}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{status.get(cellProps.cellData)}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="scannedDate"
                                label="Date of Operation"
                                id="scannedDate"
                                width={120}
                                flexGrow={0}
                                sortByItem={true}
                                selectColumn={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailorigin"
                                label="Origin Airport"
                                id="mailorigin"
                                width={100}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                       {/* (cellProps) =>(
                                           <Content>
                                          <Lov name={`${cellProps.rowIndex}.mailorigin`}  value={cellProps.cellData} id={`mailorigin${cellProps.rowIndex}`} lovTitle="Origin" dialogWidth="600" dialogHeight="473" maxlength="3"  actionUrl="ux.showAirport.do?formCount=1" 
                                            disabled={this.isDisableRequired(cellProps,true)}   uppercase={true}   onBlur={() => this.updateOrgAndDest(cellProps.rowIndex,true)}  /></Content>)
                                       */
                                            (cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailDestination"
                                label="Dest. Airport"
                                id="mailDestination"
                                width={90}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                    {/* (cellProps) =>(
                                           <Content> <Lov name={`${cellProps.rowIndex}.mailDestination`}  value={cellProps.cellData} id={`mailDestination${cellProps.rowIndex}`} lovTitle="Destination" dialogWidth="600" dialogHeight="473"  actionUrl="ux.showAirport.do?formCount=1" 
                                           disabled={this.isDisableRequired(cellProps,false)}  uppercase={true} maxlength="3" onBlur={() => this.updateOrgAndDest(cellProps.rowIndex,false)}  /></Content>)
                                       */
                                      (cellProps) => (
                                        <Content>{cellProps.cellData}</Content>)
                                    }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="scannedPort"
                                label="Airport"
                                id="scannedPort"
                                width={60}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="reqDeliveryDate"
                                label="RDT"
                                id="reqDeliveryDate"
                                width={140}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData?cellProps.cellData + "-"+ cellProps.rowData.reqDeliveryTime:"--"}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="operationalStatus"
                                label="IN/OUT"
                                id="operationalStatus"
                                width={80}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{operationTyp.get(cellProps.cellData)}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="flightNumber"
                                label="Flight/Dest"
                                id="flightNumber"
                                width={80}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.rowData.carrierCode}{cellProps.rowData.flightNumber}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="flightDate"
                                label="Dep/Arr Date"
                                id="flightDate"
                                width={90}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="uldNumber"
                                label="Container No"
                                id="uldNumber"
                                width={100}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}
                                            {
                                                cellProps.rowData.paBuiltFlag === 'Y' && "(SB)"
                                               
                                            }
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="consignmentNumber"
                                label="Cons. No"
                                id="consignmentNumber"
                                width={260}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData?cellProps.cellData: "--"}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="scannedUser"
                                label="User ID"
                                id="scannedUser"
                                width={100}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="displayWeight"
                                label="Wt"
                                id="displayWeight"
                                width={80}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}
                                >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                           <IColumn
                                dataKey="actualWeight"
                                label="Actual Weight"
                                width={120}
                                flexGrow={0}
                                id="actualWeight"
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                      
                                        {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.actualWeight`} type="text" value="abc" onBlur={() => this.saveActualWeight(cellProps.rowIndex)} disabled={this.props.capFlag} /></Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailbagVolume"
                                label="Vol."
                                id="mailbagVolume"
                                width={70}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                       {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="awb"
                                label="AWB No"
                                id="awb"
                                width={80}
                                flexGrow={1}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.rowData.shipmentPrefix}{"-"}{cellProps.rowData.masterDocumentNumber}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                          
                            <IColumn
                                dataKey="servicelevel"
                                label="Service Level"
                                id="servicelevel"
                                width={100}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                             <Content>{cellProps.rowData.servicelevel ? this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => {return  element.value === cellProps.rowData.servicelevel}).label :' '}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                             
                            {isSubGroupEnabled('AA_SPECIFIC') &&
                            <IColumn
                                dataKey="onTimeDelivery"
                                label="On-time Delivery"
                                id="onTimeDelivery"
                                width={120}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            } 
                             <IColumn
                                dataKey="transportSrvWindow"
                                label="TSW"
                                id="transportSrvWindow"
                                width={90}
                                flexGrow={1}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{cellProps.cellData}</Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            
                            <IColumn width={40} 
                             flexGrow={0}
                             className="last-column justify-content-end">
                                < Cell>
                                <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {<IDropdown  portal={true}>
                                                    <IDropdownToggle className="dropdown-toggle more-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu right={true} portal={true} >
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="REASSIGN" privilegeCheck={true} componentId="CMP_Mail_Operations_MailBagEnquiry_Reassign" onClick={this.props.onMailBagActionClick}>Reassign</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="RETURN" componentId="CMP_Mail_Operations_MailBagEnquiry_Return" onClick={this.props.onMailBagActionClick}>Return</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="VIEW_DAMAGE" componentId="CMP_Mail_Operations_MailBagEnquiry_View_Damage" onClick={this.props.onMailBagActionClick}>View Damage</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="VIEW_MAIL_HISTORY" componentId="CMP_Mail_Operations_MailBagEnquiry_View_History" onClick={this.props.onMailBagActionClick}>View Mail History</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="DELIVER_MAIL" componentId="CMP_Mail_Operations_MailBagEnquiry_Deliver" onClick={this.props.onMailBagActionClick}>Deliver Mail</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="TRANSFER" componentId="CMP_Mail_Operations_MailBagEnquiry_Transfer" onClick={this.props.onMailBagActionClick}>Transfer</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex} data-mode="OFFLOAD" componentId="CMP_Mail_Operations_MailBagEnquiry_Offload" onClick={this.props.onMailBagActionClick}>Offload</IDropdownItem>
                                                        <IDropdownItem data-key={cellProps.rowIndex}  data-mode="MODIFY_ORG_DST" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_MAILBAGENQUIRY_MODIFY_ORIGIN_DEST" onClick={this.props.onMailBagActionClick}>Modify Origin Destination</IDropdownItem> 
                                                    </IDropdownMenu>
                                                </IDropdown>}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </IMultiGrid>
                </Fragment>
            );
        }
        catch (e) {
            console.log(e)
        }
    }
}