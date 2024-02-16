import React from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdownMenu, IDropdownToggle, IDropdownItem, IDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown'
import ContainerActionPanel from './custompanel/ContainerActionPanel.jsx'
import ContainerTableFilter from './ContainerTableFilter.jsx'
import { ISelect,ITextField  } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { Row, Col } from "reactstrap";
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
export default class ContainerDetailsTable extends React.Component {

    constructor(props) {
        super(props);
        this.selectedContainers = [];
        this.selectedContainersIndex = [];
        this.state = {
            isOffloadPopoverOpen: false,
            offloadPopoverId: '',
            rowClkCount: 0
           
        }
    }

    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    onSelectmultiple = (data) => {
        let count = this.state.rowClkCount;
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                count = ++count
                this.selectContainers(data.rowData);
                this.selectContainerIndex(data.index);
            } else {
                count = --count
                this.unSelectContainer(data.rowData);
                this.unSelectContainerIndex(data.index);
            }
        } else {
            if (data.checked) {
                count = this.props.containerDetails.length;
                this.selectAllContainers(this.props.containerDetailsList);
                this.selectAllContainersIndex(-1);
                
            } else {
                count = 0
                this.unSelectAllContainers();
                this.unSelectAllContainersIndex();

            }
        }
        this.setState({
            rowClkCount: count
        })
    
    }

    selectContainers = (containerDetail) => {
        this.selectedContainers.push(containerDetail);
        this.props.selectContainers(this.selectedContainers);
    }
    unSelectContainer = (containerDetail) => {
        let index = -1;
        for (let i = 0; i < this.selectedContainers.length; i++) {
            var element = this.selectedContainers[i];
            if (element.id === containerDetail.id) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedContainers.splice(index, 1);
        }
        this.props.selectContainers(this.selectedContainers);
    }
    selectAllContainers = (data) => {
        this.selectedContainers = data
        this.props.selectContainers(this.selectedContainers);
    }

    unSelectAllContainers = () => {
        this.selectedContainers = []
        this.props.selectContainers(this.selectedContainers);
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
   
    contentId =(rowIndex,selected) => {
        if (this.props.flightDetailsFromInbound.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        if(this.props.tableValues[rowIndex] && this.props.tableValues[rowIndex].contentId && selected && selected.value) {
            if(this.props.tableValues[rowIndex].contentId===selected.value) {
                return;
            }
        }
        this.props.contentId(rowIndex);
    }
    }

    saveActualWeight = (rowIndex) => {
        if (this.props.flightDetailsFromInbound.valildationforimporthandling === 'N') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        if(this.props.tableValues[rowIndex]) {
            if(this.props.tableValues[rowIndex] && this.props.tableValues[rowIndex].actualWeightMeasure) {
               if(this.props.tableValues[rowIndex].actualWeight==this.props.tableValues[rowIndex].actualWeightMeasure.displayValue) {
                return;
               } 
            }
            this.props.saveActualWeight(rowIndex, this.props.tableValues[rowIndex].actualWeight);
        } else {
            const actualWeight = this.props.containerdetailForm[rowIndex].actualWeight;       
            this.props.saveActualWeight(rowIndex, actualWeight);
         }
    }

    }
    selectContainerIndex = (Container) => {
        this.selectedContainersIndex.push(Container);
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    unSelectContainerIndex = (Container) => {
        let index = -1;
        for (let i = 0; i < this.selectedContainersIndex.length; i++) {
            var element = this.selectedContainersIndex[i];
            if (element === Container) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedContainersIndex.splice(index, 1);
        }
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    selectAllContainersIndex = () => {

        for (let i = 0; i < this.props.containerDetailsList.results.length; i++) {
            this.selectedContainersIndex.push(i);
        }
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }

    unSelectAllContainersIndex = () => {
        this.selectedContainersIndex = []
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }

    savePopoverId = (index) => {
        this.setState({ offloadPopoverId: index});
        this.toggleOffloadPopover();
    }


    toggleOffloadPopover = () => {
        this.setState({ isOffloadPopoverOpen: !this.state.isOffloadPopoverOpen })
    }

    render() {
        const results = (this.props.containerDetails) ? this.props.containerDetails : '';
                    
        const rowCount = (this.props.containerDetails) ? this.props.containerDetails.length : 0;
        let contentId= [];

        let actualUnit='Actual Weight';
        const estimatedChargePrivilage=this.props.estimatedChargePrivilage?this.props.estimatedChargePrivilage:false;
        //if(results.length>0){
           // if(results[0].actualWeightUnit!=null)
            //actualUnit=actualUnit+'('+results[0].actualWeightUnit+')';
        //}

        if (!isEmpty(this.props.oneTimeValues)) {
            contentId = this.props.oneTimeValues['mail.operations.contentid'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        return (

            <IMultiGrid

                rowCount={rowCount}
                headerHeight={35}
                className="table-list"
                gridClassName="table_grid"
                headerClassName=""
                rowHeight={35}
                rowClassName="table-row"
                tableId="containertable"
                name="containertable"
                sortEnabled={false}
                form={true}
                resetSelectionOnDataChange={true}
                rowClassName="table-row"
                fixedRowCount={1}
                fixedColumnCount={5}
                enableFixedRowScroll
                hideTopRightGridScrollbar
                hideBottomLeftGridScrollbar
                customHeader={
                    {
                        headerClass: '',
                        placement: '',
                        dataConfig: {
                            screenId: '',
                            tableId: 'containertable'
                        },
                        "pagination": { "page": this.props.containerDetailsList, getPage: this.props.getNewPage,
                        options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' }] },
                        customPanel: <ContainerActionPanel rowClkCount={this.state.rowClkCount} docontainerAction={this.props.docontainerAction} />,
                        filterConfig: {
                            panel: <ContainerTableFilter oneTimeValues={this.props.oneTimeValues} tableFilter={this.props.tableFilter} onInputChangeSearchmode={this.props.onInputChangeSearchmode} initialValues={this.props.tableFilterInitialValues} assignedTo={this.props.assignedTo} />,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyContainerFilter,
                            onClearFilter: this.props.onClearContainerFilter
                        },
                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            addlColumns: [],
                            name: 'Container List'
                        },
                        sortBy: {
                            onSort: this.sortList
                        }

                    }
                }
                data={results}
                onRowSelection={this.onSelectmultiple}
            >

                <Columns>
                    <IColumn
                        width={40}
                        dataKey=""
                        flexGrow={0}
                        className="first-column align-items-center"
                        hideOnExport>
                        <Cell>
                            <HeadCell disableSort selectOption>
                            </HeadCell>
                            <RowCell selectOption>
                            </RowCell>
                        </Cell>
                    </IColumn>


                    {/* <IColumn
                        width={30}
                        dataKey=""
                        flexGrow={0}
                        selectColumn={false}
                        hideOnExport>
                        <Cell>
                            <HeadCell disableSort>
                                {() => (
                                    <Content></Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>{cellProps.rowData.offloadedInfo ? (cellProps.rowData.offloadedInfo.length > 0 ? <a href="#"><i id={"offloadInfo" + cellProps.rowIndex} className="icon ico-ex-blue" onClick={() => this.savePopoverId(cellProps.rowIndex)}></i>
                                        {this.state.isOffloadPopoverOpen && 'offloadInfo' + cellProps.rowIndex === 'offloadInfo' + this.state.offloadPopoverId &&
                                            <div>

                                                <IPopover placement="right" isOpen={this.state.isOffloadPopoverOpen} target={'offloadInfo' + this.state.offloadPopoverId} toggle={this.toggleOffloadPopover} className="icpopover">
                                                    <IPopoverHeader>Offloaded Information</IPopoverHeader>
                                                    <IPopoverBody>
                                                        {
                                                            <div className="pad-sm">
                                                                <div><span className="text-light-grey">No.of times offloaded :</span> {cellProps.rowData.offloadCount}</div>
                                                                <div><span className="text-light-grey">Offloaded flight information</span> : {cellProps.rowData.offloadedInfo.map((value) => <span>{value},</span>)}</div>
                                                            </div>
                                                        }
                                                    </IPopoverBody>
                                                </IPopover>
                                            </div>}</a> : "") : ""}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn> */}
                    <IColumn
                        dataKey="containerNumber"
                        label="Container Number"
                        className="align-items-center"
                        width={130}
                        flexGrow={0}
                        id="containerNumber"
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
										  <div className="col">
                                       { isSubGroupEnabled('SINGAPORE_SPECIFIC') &&cellProps.rowData.subclassGroup==='EMS'&& <span className= {"badge badge-pill light badge-active"}>EMS</span>}
                                      </div>
								  </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        width={30}
                        dataKey=""
                        flexGrow={0}
                        className="align-items-center"
                        selectColumn={false}
                        hideOnExport>
                        <Cell>
                            <HeadCell disableSort>
                                {() => (
                                    <Content></Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>{cellProps.rowData.offloadedInfo ? (cellProps.rowData.offloadedInfo.length > 0 ? <a href="#"><i id={"offloadInfo" + cellProps.rowIndex} className="icon ico-ex-blue" onClick={() => this.savePopoverId(cellProps.rowIndex)}></i>
                                        {this.state.isOffloadPopoverOpen && 'offloadInfo' + cellProps.rowIndex === 'offloadInfo' + this.state.offloadPopoverId &&
                                            <div>

                                                <IPopover placement="right" isOpen={this.state.isOffloadPopoverOpen} target={'offloadInfo' + this.state.offloadPopoverId} toggle={this.toggleOffloadPopover} className="icpopover">
                                                    <IPopoverHeader>Offloaded Information</IPopoverHeader>
                                                    <IPopoverBody>
                                                        {
                                                            <div className="pad-sm">
                                                                <div><span className="text-light-grey">No.of times offloaded :</span> {cellProps.rowData.offloadCount}</div>
                                                                <div><span className="text-light-grey">Offloaded flight information</span> : {cellProps.rowData.offloadedInfo.map((value) => <span>{value},</span>)}</div>
                                                            </div>
                                                        }
                                                    </IPopoverBody>
                                                </IPopover>
                                            </div>}</a> : "") : ""}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="flightDetail"
                        label="Carrier/ Flight No"
                        className="align-items-center"
                        width={120}
                        flexGrow={0}
                        id="flightNumber"
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
                                    (cellProps.rowData.flightNumber === '-1') ?
                                        <Content>{cellProps.rowData.carrierCode}</Content> :
                                        <Content>{cellProps.rowData.carrierCode}-{cellProps.rowData.flightNumber}</Content>
                                )
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="flightDate"
                        label="Flight Date"
                        className="align-items-center"
                        width={90}
                        flexGrow={0}
                        id="flightDate"
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
                        dataKey="assignedPort"
                        label="Current Port"
                        className="align-items-center"
                        width={90}
                        flexGrow={0}
                        id="currentPort"
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
                        dataKey="pol"
                        label="POL"
                        className="align-items-center"
                        width={50}
                        flexGrow={0}
                        id="pol"
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
                        dataKey="pou"
                        label="POU"
                        className="align-items-center"
                        width={50}
                        flexGrow={0}
                        id="pou"
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
                        dataKey="destination"
                        label="Dest"
                        className="align-items-center"
                        width={50}
                        flexGrow={0}
                        id="destination"
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
                        dataKey="assignedOn"
                        label="Assigned On"
                        className="align-items-center"
                        width={120}
                        flexGrow={0}
                        id="assignedOn"
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
                        dataKey="assignedBy"
                        label="Assigned By"
                        className="align-items-center"
                        width={100}
                        flexGrow={0}
                        id="assignedBy"
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
                        dataKey="onwadRoute"
                        label="Onward Route"
                        className="align-items-center"
                        width={150}
                        flexGrow={0}
                        id="onwardRoute"
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
                        dataKey="bags"
                        label="No of Bags"
                        className="align-items-center"
                        width={80}
                        flexGrow={0}
                        id="bags"
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
                        dataKey="weight"
                        label="Weight"
                        className="align-items-center"
                        width={100}
                        flexGrow={0}
                        id="weight"
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
                        dataKey="actualWeight"
                        className="align-items-center"
						label={actualUnit}
                        width={120}
                        flexGrow={0}
                        id="actualWeight"
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
                                    <Content><ITextField name={`${cellProps.rowIndex}.actualWeight`} type="text" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_ACTUAL_WEIGHT" onBlur={() => this.saveActualWeight(cellProps.rowIndex)} /></Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                        dataKey="contentId"
                        label="Content ID"
                        className="align-items-center"
                        width={80}
                        flexGrow={0}
                        id="contentId"
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
                                    <Content>
                                        <ISelect className="w-100" name={`${cellProps.rowIndex}.contentId`} options={contentId} privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_ACTUAL_WEIGHT" onOptionChange={(e) => this.contentId(cellProps.rowIndex,e)} />
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    {isSubGroupEnabled('SINGAPORE_SPECIFIC') &&
                    <IColumn
                        dataKey="noOfDaysInCurrentLoc"
                        label="No. of Days"
                        className="align-items-center"
                        width={80}
                        flexGrow={0}
                        id="noOfDaysInCurrentLoc"
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
                    </IColumn>}
					   <IColumn
                        dataKey="uldFullStatus"
                        label="ULD Full Status"
                        className="align-items-center"
                        width={120}
                        flexGrow={0}
                        id="uldFullStatus"
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
                                    <Content>
                                        {(cellProps.rowData.uldFulIndFlag === 'Y') ? <i className="ico-ok-green-md"></i> : <i className="ico-close-red"></i>}

                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    {isSubGroupEnabled('LUFTHANSA_SPECIFIC') &&
                    <IColumn
                        dataKey="hbaMarked"
                        label="HBA Marked"
                        className="align-items-center"
                        width={120}
                        flexGrow={0}
                        id="hbaMarked"
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
                                    <Content>
                                        {(cellProps.rowData.hbaMarked === 'Y') ? 'Yes' : 'No'}
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    }
                    {isSubGroupEnabled('SINGAPORE_SPECIFIC') &&
                    <IColumn
                        dataKey="plannedFlightAndDate"
                        label="Planned Flight Details"
                        className="align-items-center"
                        width={150}
                        flexGrow={0}
                        id="plannedFlightAndDate"
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
                {estimatedChargePrivilage?
                    <IColumn
                        dataKey="estimatedCharge"
                        label="Estimated Charge"
                        className="align-items-center"
                        width={150}
                        flexGrow={0}
                        id="estimatedCharge"
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
                                    <Content>
                                       <span>{cellProps.rowData.rateAvailforallMailbags!=null&&cellProps.rowData.rateAvailforallMailbags!=undefined&&cellProps.rowData.rateAvailforallMailbags=='N'?<span className="text-red">{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined ? cellProps.rowData.provosionalCharge :''}</span>:<span>{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined ? cellProps.rowData.provosionalCharge :''}</span>}</span>
                                       <span className="pad-l-2xs">{<span className="text-light-grey"> {cellProps.rowData.baseCurrency}</span>}</span>
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
					:''}
                    <IColumn
                        dataKey="remarks"
                        label="Remarks"
                        className="align-items-center"
                        width={150}
                        flexGrow={1}
                        id="remarks"
                        selectColumn={true}
                    >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content><div className="text-truncate">{cellProps.cellData}</div></Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        label=""
                        width={40}
                        dataKey=""
                        className="last-column justify-content-end align-items-center"
                        flexGrow={0}>
                        <Cell>
                            <HeadCell disableSort>
                                {() => (
                                    <Content></Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>

                                        <IDropdown portal={true}>
                                            <IDropdownToggle className="dropdown-toggle">
                                                <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                </IDropdownToggle>
                                                <IDropdownMenu right={true} portal={true} >
                                                    <IDropdownItem data-mode="VIEWMAILBAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_VIEWMAILBAG" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>View Mailbag</IDropdownItem>
                                                    <IDropdownItem data-mode="TRANSFERCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_TRANSFER_CARRIER" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Transfer To Carrier</IDropdownItem>
                                                    <IDropdownItem data-mode="TRANSFERCONFLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_TRANSFER_FLIGHT" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Transfer To Flight</IDropdownItem>
                                                   <IDropdownItem data-mode="REASSIGNCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_REASSIGN_TO_CARRIER" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Reassign To Carrier</IDropdownItem>
                                                   <IDropdownItem data-mode="REASSIGNCONFLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_REASSIGN_TO_FLIGHT" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Reassign To Flight</IDropdownItem>
                                                    <IDropdownItem data-mode="OFFLOADCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_OFFLOAD" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Offload Container</IDropdownItem>
                                                     {isSubGroupEnabled('LUFTHANSA_SPECIFIC') &&
                                                    <IDropdownItem data-mode="MARK_AS_HBA" privilegeCheck={true}  data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>{cellProps.rowData.hbaMarked === 'Y'?'Edit HBA':'Mark as HBA'}</IDropdownItem>
                                                     }
                                                    {isSubGroupEnabled('LUFTHANSA_SPECIFIC')&&cellProps.rowData.hbaMarked === 'Y' ? 
														<IDropdownItem data-mode="UNMARK_HBA" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_UNMARK_HBA" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Unmark HBA</IDropdownItem>:[]}
                                                    <IDropdownItem data-mode="UNASSIGNCON" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_UNASSIGN" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Unassign Container</IDropdownItem>
                                                    <IDropdownItem data-mode="RELEASECONTAINER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_RELEASE" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction}>Release Container</IDropdownItem>
													  <IDropdownItem data-mode="ULD_MARK_FULL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_ULD_MARK_FULL" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction} disabled={cellProps.rowData.uldFulIndFlag === 'Y'}>Mark as ULD Full</IDropdownItem>
                                                <IDropdownItem data-mode="ULD_UNMARK_FULL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_ULD_UNMARK_FULL" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction} disabled={cellProps.rowData.uldFulIndFlag === 'N'}>Mark ULD as not Full</IDropdownItem>
                                                {isSubGroupEnabled('QF_SPECIFIC')&&
                                                    <IDropdownItem data-mode="PRINT_ULD_TAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_PRINT_ULD_TAG" data-index={cellProps.rowIndex} onClick={this.props.docontainerAction} >Print ULD Tag</IDropdownItem>}

                                              </IDropdownMenu>
                                            </IDropdown>
                                        </Content>
                                    )
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        </Columns>
            </IMultiGrid>
            )

    }
}