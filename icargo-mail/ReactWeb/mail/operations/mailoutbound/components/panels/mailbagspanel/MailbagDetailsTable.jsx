import React, { Component } from 'react';
import { Row, Col} from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import PopoverDetails from '../Popoverdetails.jsx'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import MailBagFilter from '../filters/MailbagFilter.js'
import ReassignPanel from '../../../../mailbagenquiry/components/panels/ReassignPanel.jsx';
import ReturnMail from '../../../../mailbagenquiry/components/panels/ReturnMail.jsx';
import RemarksPanel from './RemarksPanel.jsx';
import ScanTimePanel from './ScanTimePanel.jsx';
import MailbagCustome from '../custompanels/MailbagTable.js'
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import PropTypes from 'prop-types';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import TransferMailPanel from '../../../../mailinbound/components/panels/TransferMailPanel.jsx';

export default class MailbagDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = []
        
      this.state = {
        mailbagrowClkCount: 0,
        showReturn: false,
        showDamage: false,
        showTransfer: false,
        showReassign: false,
        showScanTime: false,
        mailndex: ''
    }        
    }
    
    addMailbagClk = () => {
        this.setState({ show: true });
    }
    toggle = () => {
        this.setState({ show: false });
    }
    toggleMailPopup=()=> {
        this.setState({
            showReturn: false,
            showCaptureDamage: false,
            showTransfer: false,
            showReassign: false,
            showRemarks: false,
            showScanTime: false,
            mailndex: 0
        });
    }

     //on row selection and checkbox click
    onMailbagRowSelection = (data) => {
        if(data.selectedIndexes) {
        this.props.saveSelectedMailbagIndex(data.selectedIndexes);
        }
    }
    getMailbagClassName = (row) => {
        const data = row.rowData;
        const importedMailbagDetails = this.props.importedMailbagDetails ? this.props.importedMailbagDetails : '';
        if (!isEmpty(importedMailbagDetails)) {
            if (importedMailbagDetails.some(mails => JSON.stringify(mails.mailbagId) === JSON.stringify(data.mailbagId))
                && importedMailbagDetails.some(mails => JSON.stringify(mails.containerNumber) === JSON.stringify(data.containerNumber))) {
                return 'font-weight-bold pad-r-xs pad-b-3xs';
            }
            else {
                return 'pad-r-xs pad-b-3xs';
            }
        }
        else {
            return 'pad-r-xs pad-b-3xs';
        }
    }
    

    //on row specific mailbag action
     mailbagAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        
         if (actionName === 'RETURN') {
            this.props.mailbagAction({ actionName: actionName, index: index });
          
        } 
        if (actionName === 'DAMAGE_CAPTURE') {
            this.props.mailbagAction({ actionName: actionName, index: index });
          
        } 
        else if (actionName === 'REMARKS') {
           // this.setState({
               // showRemarks: true
          //  });
          this.props.mailbagAction({ actionName: actionName, index: index });
            
        }
        else if (actionName === 'CHANGE SCAN TIME') {
           // this.setState({
            //    showScanTime: true
            //});
            this.props.mailbagAction({ actionName: actionName, index: index });
            
        }
        else if (actionName === 'MODIFY_MAILBAG') { 
          this.props.mailbagAction({ actionName: actionName, index: index });
           
        }
        else if (actionName === 'REASSIGN') {
           this.props.mailbagAction({ actionName: actionName, index: index });
           
           // this.props.mailbagAction({ actionName: actionName, index: index });
        } else if (actionName === 'TRANSFER') {
            this.setState({
                showTransfer: true,
                // mailndex: selectedIndex
            });
            this.props.mailbagAction({ actionName: actionName, index: index });
        } else if (actionName === 'VIEW_DAMAGE') {
            this.setState({
                showDamage: true
            });
            this.props.mailbagAction({ actionName: actionName, index: index });
        } 
        else if (actionName === 'DELETE') {
            
            this.props.mailbagAction({ actionName: actionName, index: index });
        }
        else if (actionName === 'VIEW MAIL HISTORY') {
            
            this.props.openHistoryPopup(this.props.mailbagslist[index]);
        }
        else if (actionName === 'OFFLOAD') {
            
            this.props.navigateToOffload([this.props.mailbagslist[index]]);
        }
         else if (actionName === 'ATTACH_AWB') {
            
            this.props.mailbagAction({ actionName: actionName, index: index });
        }
        else if (actionName === 'DETACH_AWB') {
            
            this.props.detachAWB({ actionName: actionName, index: index });
        }
        

        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    togglePanel = () => {
        this.setState({ isOpen: !this.state.isOpen })
    }

    savePopoverId = (index, rowData) => {
        this.setState({ popoverId: index + rowData.mailSequenceNumber.toString() });
        this.setState({ rowData: rowData });
        this.togglePanel();
    }

    sortList = (sortBy, sortByItem) => {
        this.props.onApplyMailbagSort(sortBy, sortByItem);
    }   

    render() {
        // let containers=[];
        let damageCodes=[]
        if (!isEmpty(this.props.oneTimeValues)) {
            damageCodes = this.props.oneTimeValues['mailtracking.defaults.return.reasoncode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        // const rowCount=(this.props.mailbaglist)?this.props.mailbaglist.results.length:0;
        const rowCount = (this.props.mailbagslist) ? this.props.mailbagslist.length : 0;
        try {
            return (
                <div className="flex-grow-1 d-flex">
                    <ReassignPanel
                        show={this.props.showReassign}
                        mailbagDetails={this.props.mailbagslist}
                        close={this.props.closeReassign}
                        listContainer={this.props.listContainer}
                        containerDetails={this.props.reassignContainers}
                        reassignSave={this.props.reassignSave}
                        pabuiltUpdate={this.props.pabuiltUpdate}
                        pous={this.props.pous}
                        isValidFlight={this.props.isValidFlight}
                        destination={this.props.destination}
                        saveNewContainer={this.props.saveNewContainer}
                        clearReassignForm={this.props.clearReassignForm}
                        initialValues = {this.props.reassignDefaultValues}
                        clearAddContainerPopover={this.props.clearAddContainerPopover}
                        saveSelectedContainersIndex={this.props.saveSelectedContainersIndex}
                        //initialValues={{  scanDate: this.props.currentDate, scanTime:this.props.currentTime, carrierCode :this.props.carrierCode, reassignFilterType:this.props.reassignFilterType,
                         //uldDestination: this.props.uldDestination 
                        // }}
                          />

                    <RemarksPanel   show={this.props.remarkspopup} toggleMailPopup={this.props.onCloseRemarksPopup} onSavemailbagActions={this.props.onSavemailbagActions}/>
                    <ScanTimePanel   show={this.props.scanTimePopup} toggleMailPopup = {this.props.oncloseScanTimePopup } initialValues={this.props.initialValues} onSavemailbagActions={this.props.onSavemailbagActions}/>
                     <ReturnMail
                        damageCodes={damageCodes}
                        show={this.props.mailbagReturnPopup}
                        mailbagDetails={this.props.mailbagslist}
                        selectedMail={this.props.selectedMail}
                        oneTimeValues={this.props.oneTimeValues}
                        close={this.props.closeReturnPopup}
                        selectedMailbag={this.props.selectedMailbags}
                        damageDetail = {this.props.damageDetail}
                        doReturnMail={this.props.doReturnMail}
                        postalCodes={this.props.postalCodes}
                        mailbagReturnDamageFlag={this.props.mailbagReturnDamageFlag}/>
                     <TransferMailPanel                                                                                                                                                       
                        show={this.props.showTransfer}
                        initialValues = {this.props. transferDefaultValues}   
                        clearTransferPanel={this.props.clearTransferPanel}
                        listTransfer={this.props.listTransfer}
                        containerData={this.props.transferContainers}
                        addContainerButtonShow={this.props.addContainerButtonShow}
                        pabuiltUpdate={this.props.pabuiltUpdate}
                        pous={this.props.pousForTransfer}
                        destination={this.props.destination}
                        saveNewContainer={this.props.saveNewContainer}
                        showTransfer={this.props.showTransfer}
                        clearAddContainerPopover={this.props.clearAddContainerPopoverForTransfer}
                        closeTransferMail={this.props.closeTranferPopup}
                        saveTransferMail={this.props.transferSave}
                        ownAirlineCode={this.props.ownAirlineCode}
                        partnerCarriers={this.props.partnerCarriers}
                        listChangeFlightMailPanel={this.props.getNextPageContainerForTransfer}
                    />
                    
                    <ITable
                        customHeader={{
                            headerClass: '',
                          "pagination": { "page": this.props.mailbags, getPage: this.props.getMailbagsNewPage, "mode": 'subminimal' },
                            filterConfig: {
                             panel: <MailBagFilter mailbagOneTimeValues={this.props.mailbagOneTimeValues}   initialValues={this.props.mailBagTableFilterInitialValues} oneTimeValues={this.props.oneTimeValues} />,
                             title: 'Filter',
                             onApplyFilter: this.props.onApplyMailbagFilter,
                             onClearFilter: this.props.onClearMailbagFilter
                            },
                            sortBy:{
                               onSort:this.sortList,
                               customSortByItems:[
                                {
                                                id:'mailstatus',
                                                label:'Status'
                                                },
                                                {
                                                    id:'scanDate',
                                                    label:'Scan Date and Time'
                                                },
                                                {
                                                    id:'mailorigin',
                                                    label:'Origin'
                                                }
                                ]
                            },
                            customPanel:<MailbagCustome mailbagAction={this.props.mailbagAction} selectedMailbagsIndex={this.props.selectedMailbagsIndex} currentTime={this.props.currentTime} currentDate={this.props.currentDate} defaultWeightUnit={this.props.defaultWeightUnit} previousRowWeight={this.props.previousRowWeight}activeMailbagTab={this.props.activeMailbagTab} show={this.props.addMailbagShow} addModifyFlag={this.props.addModifyFlag} onLoadAddMailbagPopup={this.props.onLoadAddMailbagPopup} closeAddMailbagPopup={this.props.closeAddMailbagPopup} onSavemailbagDetails={this.props.onSavemailbagDetails}  changeAddMailbagTab={this.props.changeAddMailbagTab} activeMailbagAddTab= {this.props.activeMailbagAddTab}  addRow={this.props.addRow} onDeleteRow={this.props.onDeleteRow} resetRow={this.props.resetRow}  newMailbags={this.props.newMailbags} updatedMailbags={this.props.updatedMailbags} importedMailbagDetails={this.props.importedMailbagDetails}  selectedMailbags={this.props.selectedMailbags} onMailBagActionClick={this.props.mailbagAction} toggleFlightPanel={this.props.toggleFlightPanel} populateMailbagId={this.props.populateMailbagId} mailbagOneTimeValues={this.props.mailbagOneTimeValues} flightActionsEnabled={this.props.flightActionsEnabled} showImportPopup={this.props.showImportPopup} containerJnyID={this.props.containerJnyID} onCloseImport={this.props.onCloseImport} onClickImportPopup={this.props.onClickImportPopup} onImportMailbags={this.props.onImportMailbags} flightCarrierflag={this.props.flightCarrierflag} mailbagMultipleSelectionAction={this.props.mailbagMultipleSelectionAction} existingMailbags={this.props.existingMailbags} existingMailbagPresent={this.props.existingMailbagPresent} onCloseExistingMailbag={this.props.onCloseExistingMailbag} reassignExistingMailbags={this.props.reassignExistingMailbags} reassignMailbagsCancel={this.props.reassignMailbagsCancel} containersPresent={this.props.containersPresent} navigateToOffload={this.props.navigateToOffload} mailbagslist={this.props.mailbagslist} detachAWB={this.props.detachAWB} disableForModify={this.props.disableForModify}/>
                        }}

                        rowCount={rowCount}
                        headerHeight={35}
                        className="table-list"
                        gridClassName=""
                        headerClassName=""
                        rowHeight={40}
                        rowClassName="table-row"
                        tableId='mailbagstable'
                        onRowSelection={this.onMailbagRowSelection}
                        data={this.props.mailbagslist}
                    >
                        <Columns>
                            <IColumn
                               // width={40}
                                dataKey=""
                                flexGrow={0}
                                className="align-self-center" >
                                <Cell>
                                    <HeadCell disableSort selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn key={2}
                            //width={16}
                             className="align-self-center pr-0 width24">
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content><div className="width16"></div></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                                <a id={'mailbagextra' + cellProps.rowIndex + cellProps.rowData.mailSequenceNumber.toString()} onClick={() => { this.savePopoverId(cellProps.rowIndex, cellProps.rowData) }}>
                                                    {(this.state.isOpen && (this.state.popoverId == cellProps.rowIndex + cellProps.rowData.mailSequenceNumber.toString())) ?
                                                        <i className="icon ico-minus-round align-middle"></i> :
                                                        <i className="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isOpen && this.state.popoverId != '' && 'mailbagextra' + cellProps.rowIndex + cellProps.rowData.mailSequenceNumber.toString() === 'mailbagextra' + this.state.popoverId &&
                                                    <div>
                                                        <IPopover isOpen={this.state.isOpen} target={'mailbagextra' + this.state.popoverId} toggle={this.togglePanel} className="icpopover table-filter"> 
                                                    <IPopoverBody>
                                                                <Row>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Origin Office of exchange</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.ooe ? cellProps.rowData.ooe : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Destination Office of exchange</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.doe ? cellProps.rowData.doe : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Category</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailCategoryCode ? cellProps.rowData.mailCategoryCode : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Sub Class</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailSubclass ? cellProps.rowData.mailSubclass : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Year</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.year }</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">DSN</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.despatchSerialNumber ? cellProps.rowData.despatchSerialNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">RSN</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.receptacleSerialNumber ? cellProps.rowData.receptacleSerialNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Volume</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.volume ? cellProps.rowData.volume.roundedDisplayValue : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Transfer Carrier</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.transferFromCarrier ? cellProps.rowData.transferFromCarrier : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Consignment Number</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.consignmentNumber ? cellProps.rowData.consignmentNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Seal No</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.sealNumber ? cellProps.rowData.sealNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Damage Info</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.damageFlag ? cellProps.rowData.damageFlag : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Belly cart Id</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.bellyCartId ? cellProps.rowData.bellyCartId : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">AWB No</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.shipmentPrefix}-{cellProps.rowData.masterDocumentNumber}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">PA Code</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.paCode ? cellProps.rowData.paCode : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Routing Available Info</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.routingAvlFlag && cellProps.rowData.routingAvlFlag==='Y'? 'Yes':'No'}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">PLT Checkbox box</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.sealNumber ? cellProps.rowData.sealNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Remarks</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailRemarks ? cellProps.rowData.mailRemarks : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">CARDIT Present</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.carditPresent&&cellProps.rowData.carditPresent==='Y' ? 'Yes' : 'No'}</div>
                                                                    </Col>
                                                                     {isSubGroupEnabled('TURKISH_SPECIFIC') &&<Col xs="auto">
                                                                        <div className="text-light-grey">Mail Company Code</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailCompanyCode ? cellProps.rowData.mailCompanyCode : ''}</div>
																	 </Col>}
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">TSW</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.transportSrvWindow ? cellProps.rowData.transportSrvWindow :''}</div>
                                                                    </Col>
																	<Col xs="auto">
                                                                        <div className="text-light-grey">PA ULD</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.acceptancePostalContainerNumber ? cellProps.rowData.acceptancePostalContainerNumber :''}</div>
                                                                    </Col>
                                                                </Row>
                                                            </IPopoverBody>
                                                        </IPopover>
                                                    </div>}
                                                {/* <PopoverDetails width={500}>
                                                    <div className="card pad-md">
                                                        <Row>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Origin Office of exchange</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.ooe ? cellProps.rowData.ooe :''}</div>
                                                            </Col>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Destination Office of exchange</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.doe?cellProps.rowData.doe:''}</div>
                                                            </Col>
                                                           
                                                        </Row>
                                                        <Row>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Category</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.mailCategoryCode?cellProps.rowData.mailCategoryCode:''}</div>
                                                            </Col>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Sub Class</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.mailSubclass?cellProps.rowData.mailSubclass:''}</div>
                                                            </Col>
                                                           
                                                        </Row>
                                                        <Row>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Year</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.year?cellProps.rowData.year:''}</div>
                                                            </Col>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">DSN</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.despatchSerialNumber?cellProps.rowData.despatchSerialNumber:''}</div>
                                                            </Col>
                                                           
                                                        </Row>
                                                        <Row>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">RSN</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.receptacleSerialNumber?cellProps.rowData.receptacleSerialNumber:''}</div>
                                                            </Col>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">HI</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.highestNumberedReceptacle?cellProps.rowData.highestNumberedReceptacle:''}</div>
                                                            </Col>
                                                           
                                                        </Row>
                                                        <Row>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">RI</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.registeredOrInsuredIndicator?cellProps.rowData.registeredOrInsuredIndicator:''}</div>
                                                            </Col>
                                                            <Col xs="12">
                                                                <div className="text-light-grey">Weight</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.weight ? cellProps.rowData.weight.displayValue :''}</div>
                                                            </Col>
                                                           
                                                        </Row>
                                                    </div>
                                                </PopoverDetails> */}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                            dataKey=""
                            label="mailbagId"
                            id="mailbagId"
                            sortByItem={true}
                            width={140}
                            flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>Mailbag Details</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                      <Content>
                        <div className="align-items-center d-flex mar-b-2xs flex-wrap">
                          <span className={this.getMailbagClassName(cellProps)}>{cellProps.rowData.mailbagId}</span>
                          <span className="badge badge-pill light badge-active"> {this.props.oneTimeValues['mailtracking.defaults.mailstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.latestStatus }).label} </span>
                        </div>
                        <div className="text-light-grey mar-b-2xs">
                          {cellProps.rowData.mailorigin ? cellProps.rowData.mailorigin : ''}
                          <i class="icon ico-air-sm mar-x-2xs"></i>
                          {cellProps.rowData.mailDestination ? cellProps.rowData.mailDestination : ''}
                        </div>
                        <div>
                          <span className="pad-r-sm">{cellProps.rowData.reqDeliveryDateAndTime ? 'RDT : ' : ''}{cellProps.rowData.reqDeliveryDateAndTime ? cellProps.rowData.reqDeliveryDateAndTime : ''}</span>
                          <span className="text-light-grey">{cellProps.rowData.servicelevel ? this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.servicelevel }).label : ''}</span>
                        </div>
                      </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>

                            <IColumn 
                            //width={40}
                            className="align-self-center"
                            flexGrow={0}>
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {(!cellProps.rowData.uld) ?
                                                    // <span onClick={this.gridUtitity}>
                                                    <IDropdown portal={true}>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                            <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                        </IDropdownToggle>
                                                        <IDropdownMenu portal={true} className="mailbag-dropdownmenu" right={true} >
                                                            {this.props.flightCarrierflag === 'F' &&<IDropdownItem data-index={cellProps.rowIndex} data-mode="ATTACH_AWB" privilegeCheck={true}  onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Attach AWB </IDropdownItem>}
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="REASSIGN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Reassign</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DETACH_AWB" onClick={this.mailbagAction} disabled={cellProps.rowData.masterDocumentNumber!=null?this.props.flightActionsEnabled === 'false':this.props.flightActionsEnabled === 'true'}>Detach AWB</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DELETE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DELETE_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Delete</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="MODIFY_MAILBAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_MODIFY_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Modify</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="RETURN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_RETURN_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Return</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DAMAGE_CAPTURE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_CAPTURE_DAMAGE" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Damage Capture </IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="VIEW MAIL HISTORY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_VIEW_MAIL_HISTORY" onClick={this.mailbagAction}>View Mail History</IDropdownItem>
                                                            {isSubGroupEnabled('AA_SPECIFIC')===false &&<IDropdownItem data-index={cellProps.rowIndex} data-mode="CHANGE SCAN TIME" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_CHANGE_SCAN_TIME" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Change scan time</IDropdownItem>}
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="REMARKS" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_MAIL_REMARKS" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Remark</IDropdownItem>
                                                            {this.props.flightCarrierflag === 'F' &&<IDropdownItem data-index={cellProps.rowIndex} data-mode="OFFLOAD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_OFFLOAD_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}>Offload</IDropdownItem>}
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_MAILBAG" onClick={this.mailbagAction} disabled={this.props.flightActionsEnabled === 'false'}> Transfer</IDropdownItem>

                                                        </IDropdownMenu>
                                                    </IDropdown>
                                                    //  </span>
                                                    : ''}

                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>

                        </Columns>
                    </ITable>
                </div>
            );
        }
        catch (e) {
        }
    }
}
MailbagDetailsTable.propTypes = {
    saveSelectedMailbagIndex:PropTypes.func,
    mailbagAction:PropTypes.func,
    navigateToOffload:PropTypes.func,
    mailbagslist:PropTypes.array,
    oneTimeValues:PropTypes.object,
    showReassign:PropTypes.func,
    closeReassign:PropTypes.func,
    listContainer:PropTypes.object,
    reassignContainers:PropTypes.func,
    reassignSave:PropTypes.func,
    saveNewContainer:PropTypes.func,
    remarkspopup:PropTypes.func,
    onCloseRemarksPopup:PropTypes.func,
    onSavemailbagActions:PropTypes.func,
    initialValues:PropTypes.object,
    oncloseScanTimePopup:PropTypes.func,
    scanTimePopup:PropTypes.func,
    mailbagReturnPopup:PropTypes.func,
    closeReturnPopup:PropTypes.func,
    selectedMailbags:PropTypes.array,
    doReturnMail:PropTypes.func,
    postalCodes:PropTypes.object,
    mailbagReturnDamageFlag:PropTypes.bool,
    mailbags:PropTypes.object,
    getMailbagsNewPage:PropTypes.func,
    mailbagOneTimeValues:PropTypes.array,
    mailBagTableFilterInitialValues:PropTypes.object,
    onApplyMailBagFilter:PropTypes.func,
    onClearMailbagFilter:PropTypes.func,
    selectedMailbagsIndex:PropTypes.number,
    currentTime:PropTypes.string,
    currentDate:PropTypes.string,
    defaultWeightUnit:PropTypes.string,
    previousRowWeight:PropTypes.string,
    activeMailbagTab:PropTypes.string,
    addMailbagShow:PropTypes.func,
    addModifyFlag:PropTypes.bool,
    onLoadAddMailbagPopup:PropTypes.func,
    closeAddMailbagPopup:PropTypes.func,
    onSavemailbagDetails:PropTypes.func,
    changeAddMailbagTab:PropTypes.func,
    activeMailbagAddTab:PropTypes.string,
    addRow:PropTypes.func,
    onDeleteRow:PropTypes.func,
    resetRow:PropTypes.func,
    newMailbags:PropTypes.array,
    updatedMailbags:PropTypes.array,
    importedMailbagDetails:PropTypes.object,
    toggleFlightPanel:PropTypes.func,
    populateMailbagId:PropTypes.func,
    flightActionsEnabled:PropTypes.bool,
    showImportPopup:PropTypes.func,
    containerJnyID:PropTypes.string,
    onCloseImport:PropTypes.func,
    onClickImportPopup:PropTypes.func,
    onImportMailbags:PropTypes.func,
    flightCarrierflag:PropTypes.bool,
    mailbagMultipleSelectionAction:PropTypes.func,
    existingMailbags:PropTypes.array,
    existingMailbagPresent:PropTypes.bool,
    onCloseExistingMailbag:PropTypes.func,
    reassignExistingMailbags:PropTypes.func,
    reassignMailbagsCancel:PropTypes.func,
    openHistoryPopup:PropTypes.func,
    onApplyMailbagSort:PropTypes.func,
    onApplyMailbagFilter:PropTypes.func,
    containersPresent:PropTypes.string,
    clearReassignForm:PropTypes.func,
    saveSelectedContainersIndex:PropTypes.func,
}