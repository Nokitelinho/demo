import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Col, Label, Row } from 'reactstrap'
import { ITextField, IButton, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import OnwardRouting from './OnwardRouting.jsx';
import ContainerDetails from './ContainerDetails.jsx';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IAccordion, IAccordionItem, IAccordionItemTitle, IAccordionItemBody } from 'icoreact/lib/ico/framework/component/common/accordion';
const Aux = (props) => props.children;

/*Transfer to Flight Panel used in container enquiry and Mail Inbound*/

class TransferPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filterType: "F",
            showContainer: "N",
            containerIndex: "",
            disablePou: false,
            isCapacityPopoverOpen: false,
            capacityPopoverId: '',
        }
    }

    onFilterChange = (event) => {
        //this.setState({ filterType: event.target.value })
        if (event.target.value === 'C') {
            this.setState({ disablePou: true, filterType: event.target.value })
        } else {
            this.setState({ disablePou: false, filterType: event.target.value })
        }
    }
    showContainer = () => {
        this.setState({ showContainer: 'Y' })
    }

    onlistContainer = () => {
        this.props.listContainer(this.props.selectedMail, this.state.filterType, 'REASSIGN');
    }
    transferSave = () => {
        this.props.transferContainerToFlightAction();
    }
    selectContainer = (event) => {
        let selectedIndex = event.target.dataset.key;
        this.setState({ containerIndex: selectedIndex })
    }
    onClear = () => {
        this.props.clearReassignForm(this.state.filterType);
    }

    onlistDetails = () => {
        this.props.listFlightDetailsForTransfer();
    }

    toggleCapacityPopover = () => {
        this.setState({ isCapacityPopoverOpen: !this.state.isCapacityPopoverOpen })
    }

    savePopoverId = (index) => {
        this.setState({ capacityPopoverId: index });
        this.toggleCapacityPopover();
    }

    onRowSelection = (data) => {
        if (data.selectedIndexes) {
            this.props.saveSelectedFlightsIndex(data.selectedIndexes);
        }

    }


    render() {
        //const rowCount = (this.props.flightDetails) ? this.props.flightDetails.results.length : 0;
        const results = this.props.flightDetails ? this.props.flightDetails : [];
        const style = results.length > 0 ? 'd-flex flex-grow-1' : 'flex-grow-1';
        // const flights=this.props.flights;      
        const rowCount = results.length;
        //  const rowCount = results.length;
        let keyValue = 110;
        let status = [];
        let contentId = [];
        status = this.props.oneTimeValues ? this.props.oneTimeValues['flight.operation.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })) : '';
        contentId = this.props.oneTimeValues ? this.props.oneTimeValues['mail.operations.contentid'].map((value) => ({ value: value.fieldValue, label: value.fieldValue })) : '';

        return (

            <Fragment>
                <PopupBody>
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="8">
                                <div className="form-group">
                                    <IFlightNumber mode="edit" uppercase={true}></IFlightNumber>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">From Date</label>
                                    <DatePicker name="fromDate" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_FROMDATE' />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">To Date</label>
                                    <DatePicker name="toDate" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_TODATE' />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <label className="form-control-label ">Destination</label>
                                    <Lov name="dest" lovTitle="Destination" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_DESTINATION' dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                </div>
                            </Col>
                            <Col>
                                <div className="mar-t-md">
                                    <IButton category="primary" bType="LIST" accesskey="L" onClick={this.onlistDetails} class="btn btn-primary">List</IButton>
                                    <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.props.clearTransferFlightPanel} class="btn btn-default">Clear</IButton>{' '}
                                </div>
                            </Col>
                        </Row>
                    </div>
                    <Fragment>
                        <IAccordion>
                            <IAccordionItem expanded>
                                <IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i>Flight List</div></IAccordionItemTitle>
                                <IAccordionItemBody>
                                    <div className={style} style={{ height: "207px" }}>
                                        <ITable
                                            customHeader={{
                                                "pagination": { "page": this.props.flightDetails && this.props.flightDetails.length > 0 ? this.props.flightDetailsPage : null, getPage: this.props.getNewPage, mode: 'minimal' },
                                            }}
                                            rowCount={rowCount}
                                            headerHeight={35}
                                            className="table-list"
                                            gridClassName="table_grid"
                                            headerClassName="table-head"
                                            rowHeight={35}
                                            rowClassName='table-row'
                                            data={results}
                                            onRowSelection={this.onRowSelection}>

                                            <Columns>
                                                <IColumn
                                                    //width={32}
                                                    flexGrow={0}
                                                    dataKey="">
                                                    <Cell>
                                                        <RowCell selectOption>
                                                        </RowCell>
                                                    </Cell>
                                                </IColumn>
                                                <IColumn
                                                    dataKey="flightDetails"
                                                    label="Flight Details"
                                                    width={270}
                                                    id="flightDetails"
                                                    flexGrow={this.props.displayMode === 'display' ? 0 : 1}
                                                    selectColumn={true}
                                                    hideOnExport>
                                                    <Cell>
                                                        <HeadCell disableSort>
                                                            {() => (
                                                                <Content>Flight Details</Content>)
                                                            }
                                                        </HeadCell>
                                                        <RowCell>
                                                            {(cellProps) => {
                                                                let selectedStatus = '';
                                                                selectedStatus = status.find((element) => { return element.value === cellProps.rowData.flightStatus });
                                                                let flightRouteArr = cellProps.rowData.flightRoute.split("-");
                                                                return <Content>
                                                                    <DataDisplay index={cellProps.rowIndex} id='flightNumber' label='Flight Number' className="align-items-center d-flex mar-b-2xs" sortByItem={true}>
                                                                        <span className="fs16 pad-r-md"> {cellProps.rowData.carrierCode}{cellProps.rowData.flightNumber}</span>
                                                                        {cellProps.rowData.dcsinfo != null && <span className={cellProps.rowData.dcsregectionReason === null ? "badge badge-pill light badge-active" : "badge badge-pill light badge-error"}>{cellProps.rowData.dcsinfo}</span>}
                                                                    </DataDisplay>
                                                                    <div className="mar-b-2xs">
                                                                        <span className="text-light-grey mar-r-xs">
                                                                            {
                                                                                flightRouteArr.map((route, index) => {
                                                                                    if (index === flightRouteArr.length - 1) {
                                                                                        return route;
                                                                                    } else {
                                                                                        return <Aux key={keyValue++}>{route}<i className="icon ico-air-sm mar-x-2xs"></i></Aux>
                                                                                    }
                                                                                })
                                                                            }
                                                                        </span>
                                                                        <DataDisplay index={cellProps.rowIndex} id='flightDepartureDate' label='Flight Date' sortByItem={true}>
                                                                            {cellProps.rowData.flightDepartureDate} : {cellProps.rowData.flightTime} ({cellProps.rowData.flightDateDesc})
                                                    </DataDisplay>
                                                                    </div>
                                                                    <div>
                                                                        <span>{cellProps.rowData.aircraftType}</span>
                                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                                        <span>{selectedStatus ? selectedStatus.label : ''}</span>
                                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                                        {cellProps.rowData.flightOperationalStatus === 'O' &&
                                                                            <span className="text-green">Open</span>
                                                                        }
                                                                        {cellProps.rowData.flightOperationalStatus === 'C' &&
                                                                            <span>Closed</span>
                                                                        }
                                                                        {cellProps.rowData.flightOperationalStatus === 'N' &&
                                                                            <span className="text-blue">New</span>
                                                                        }
                                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                                        <span><span className="text-light-grey">Gate :</span> <span className="text-green">{cellProps.rowData.departureGate ? cellProps.rowData.departureGate : ''}</span></span>
                                                                    </div>
                                                                </Content>
                                                            }
                                                            }
                                                        </RowCell>
                                                    </Cell>
                                                </IColumn>
                                                <IColumn
                                                    key={keyValue++}
                                                    dataKey=""
                                                    label="Container Info"
                                                    width={100}
                                                    id="manifestInfo"
                                                    flexGrow={1}
                                                    selectColumn={true}
                                                    hideOnExport>
                                                    <Cell>
                                                        <HeadCell disableSort>
                                                            {() => (
                                                                <Content>Container Info</Content>)
                                                            }
                                                        </HeadCell>
                                                        <RowCell>
                                                            {(cellProps) => {

                                                                let container = [];
                                                                container = cellProps.rowData.containerDetails ? cellProps.rowData.containerDetails.map((value) => (<div className="mar-r-2xs mar-b-3xs" key={keyValue++}>{value.containercount ? value.containercount : ''} - {value.containergroup ? value.containergroup : ''} ({value.mailbagcount ? value.mailbagcount : ''}{value.mailbagcount ? '/' : ''}{value.mailbagweight != null && value.mailbagweight != undefined ? value.mailbagweight.roundedDisplayValue : ''})</div>)) : container

                                                                if (cellProps.rowData.flightOperationalStatus != 'N') {
                                                                    return <Content>
                                                                        <div className="d-flex flex-wrap">
                                                                            <div className="mar-r-2xs d-inline-flex flex-wrap">
                                                                                <div className="text-grey mar-r-2xs mar-b-3xs">Manifested :</div>{container}
                                                                            </div>
                                                                            <div className="mar-b-3xs mar-r-xs">
                                                                                <span className="text-grey mar-r-2xs">Total :</span><span>{cellProps.rowData.totalContainerCount ? cellProps.rowData.totalContainerCount : ''}{cellProps.rowData.totalContainerCount ? '/' : ''}{cellProps.rowData.totalContainerWeight ? cellProps.rowData.totalContainerWeight.roundedDisplayValue : ''}</span>
                                                                            </div>
                                                                            <div className="d-inline-flex flex-wrap">
                                                                                <div className="text-grey mar-r-2xs">Pre-advice :</div>{cellProps.rowData.preadvice ? cellProps.rowData.preadvice.totalBags : 0} / {cellProps.rowData.preadvice.totalWeight ? cellProps.rowData.preadvice.totalWeight.roundedDisplayValue : 0}
                                                                            </div>
                                                                        </div>
                                                                    </Content>
                                                                }
                                                                else {
                                                                    return <Content><span className="text-light-grey">No Container Info</span></Content>
                                                                }
                                                            }}
                                                        </RowCell>
                                                    </Cell>
                                                </IColumn>
                                                <IColumn dataKey=""
                                                    label="Capacity Details"
                                                    width={250}
                                                    flexGrow={0}
                                                    id="capacityDetails"
                                                    selectColumn={true}
                                                    hideOnExport
                                                    key={keyValue++}>
                                                    <Cell>
                                                        <HeadCell disableSort>
                                                            {() => (
                                                                <Content>Capacity Details</Content>)
                                                            }
                                                        </HeadCell>
                                                        <RowCell>
                                                            {(cellProps) => (
                                                                <Content>
                                                                    <div className="d-flex">
                                                                        <div>
                                                                            <div className="mar-b-2xs">
                                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Mail : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].mailUtised.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].mailCapacity.roundedDisplayValue : ''}
                                                                            </div>
                                                                            <div className="mar-b-2xs">
                                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Cargo : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].cargoUtilised.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].cargoCapacity.roundedDisplayValue : ''}
                                                                            </div>
                                                                            <div>
                                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Total : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].totalUtilised.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].segmentTotalWeight.roundedDisplayValue : ''}

                                                                            </div>
                                                                        </div>
                                                                        <div className="align-self-end">
                                                                            {cellProps.rowData.capacityDetails &&
                                                                                cellProps.rowData.capacityDetails.length > 1 ?
                                                                                <div onClick={this.gridUtitity}>
                                                                                    <div className="mar-l-xs badge badge-pill light badge-info" id={'capacityextra' + cellProps.rowIndex} onClick={() => this.savePopoverId(cellProps.rowIndex)} >+{cellProps.rowData.capacityDetails.length - 1}</div>
                                                                                </div>
                                                                                : null}
                                                                        </div>
                                                                    </div>

                                                                    {this.state.isCapacityPopoverOpen && 'capacityextra' + cellProps.rowIndex === 'capacityextra' + this.state.capacityPopoverId &&

                                                                        <div>
                                                                            <IPopover placement="right" isOpen={this.state.isCapacityPopoverOpen} target={'capacityextra' + this.state.capacityPopoverId} toggle={this.toggleCapacityPopover} className="icpopover">
                                                                                <IPopoverBody>
                                                                                    <div className="pad-md pad-b-sm">
                                                                                        {
                                                                                            cellProps.rowData.capacityDetails.map((value) =>
                                                                                                <div className="mar-b-2xs" key={keyValue++}>
                                                                                                    <span className="text-light-grey">{value.segmentOrigin + ' - ' + value.segmentDestination}.Mail : </span>{value.mailUtised.roundedDisplayValue + ' /' + value.mailCapacity.roundedDisplayValue}
                                                                                                    <span className="text-light-grey"> Cargo : </span>{value.cargoUtilised.roundedDisplayValue + ' /' + value.cargoCapacity.roundedDisplayValue}
                                                                                                    <span className="text-light-grey"> Total : </span>{value.totalUtilised.roundedDisplayValue + ' /' + value.segmentTotalWeight.roundedDisplayValue}
                                                                                                </div>
                                                                                            )}
                                                                                    </div>
                                                                                </IPopoverBody>
                                                                            </IPopover>
                                                                        </div>
                                                                    }
                                                                </Content>)
                                                            }
                                                        </RowCell>
                                                    </Cell>
                                                </IColumn>
                                            </Columns>
                                        </ITable>
                                    </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                            <IAccordionItem>
                                <IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i>Container Details</div></IAccordionItemTitle>
                                <IAccordionItemBody>
                                    <div className="pad-md">
                                        <Row>
                                        <Col xs="2">
                                                {/* <Label className="form-control-label">Container</Label> */}
                                            </Col>
                                            <Col xs="4">
                                                <Label className="form-control-label">Container</Label>
                                            </Col>
                                            <Col xs="2">
                                                {/* <Label className="form-control-label">Container</Label> */}
                                            </Col>
                                            <Col xs="3">
                                                <Label className="form-control-label">POU</Label>
                                            </Col>
                                            <Col xs="3">
                                                <Label className="form-control-label">Destination</Label>
                                            </Col>
                                            <Col xs="3">
                                                <Label className="form-control-label">Actual Weight</Label>
                                            </Col>
                                            <Col xs="3">
                                                <Label className="form-control-label">Content ID</Label>
                                            </Col>
                                            <Col xs="3">
                                                <Label className="form-control-label">Remarks</Label>
                                            </Col>
                                        </Row>
                                        <ContainerDetails oneTimeValues={this.props.oneTimeValues} initialValues={this.props.containerDetailsForPopUp} />
                                    </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                            <IAccordionItem>
                                <IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i>Other Details</div></IAccordionItemTitle>
                                <IAccordionItemBody>
                                    <div className="pad-md">
                                        <Row>
                                            <Col md="4">
                                                <div className="form-group">
                                                    <Label className="form-control-label">Scan Date</Label>
                                                    {isSubGroupEnabled('AA_SPECIFIC') ? <ITextField className="form-control" name="scanDate" disabled={true} /> : <DatePicker name="scanDate" />}

                                                </div>
                                            </Col>

                                            <Col md="2">
                                                <div className="form-group">
                                                    <Label className="form-control-label">Time</Label>
                                                    <TimePicker name="mailScanTime" disabled={isSubGroupEnabled('AA_SPECIFIC') ? true : false} />
                                                </div>
                                            </Col>
                                        </Row>
                                    </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                            <IAccordionItem>
                                <IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i> Onward Routing</div></IAccordionItemTitle>
                                <IAccordionItemBody>
                                    <div className="pad-md">
                                        <Row>
                                            <Col xs="8">
                                            <Row>
                                            <Col xs="12">
                                                <Label className="form-control-label">Flight Number</Label>
                                            </Col>
                                            <Col xs="12">
                                                <Label className="form-control-label">Flight Date</Label>
                                            </Col>
                                            </Row>
                                            </Col>
                                            <Col xs="4">
                                                <Label className="form-control-label">POL</Label>
                                            </Col>
                                            <Col xs="4">
                                                <Label className="form-control-label">POU</Label>
                                            </Col>
                                        </Row>
                                        <OnwardRouting />
                                    </div>
                                </IAccordionItemBody>
                            </IAccordionItem>
                        </IAccordion>
                    </Fragment>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.transferSave} className="btn btn-primary">Save</IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.onClose} className="btn btn-default">Cancel</IButton>
                </PopupFooter>
            </Fragment>

        )
    }
}


export default icpopup(wrapForm('transferContainerToFlight')(TransferPanel), { title: 'Transfer to Flight', className: 'modal_990px' })
