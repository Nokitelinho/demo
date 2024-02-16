import React, { Component, Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { FormGroup, Col, Label, Row, Container, Input } from 'reactstrap'
import { ITextField, ISelect, IButton, IRadio, ITextArea, ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import OnwardRouting from './OnwardRouting.jsx';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge'
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content }
    from 'icoreact/lib/ico/framework/component/common/grid';
import { addRow, deleteRow, resetForm }
    from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import PopoverDetails from './Popoverdetails.jsx';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class TransferPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filterType: "C",
            showContainer: "N",
            containerIndex: "",
            isOwnCarrierCode:'Y'
        }
    }

    onFilterChange = (event) => {
        this.setState({ filterType: event.target.value })
        this.props.clearContainerDetails('TRANSFER');
    }
    showContainer = () => {
        this.setState({ showContainer: 'Y' })
    }
    onlistContainer = () => {
        this.props.listContainer(this.props.selectedMail, this.state.filterType, 'TRANSFER');
    }
    transferSave = () => {
        this.props.transferSave(this.state.containerIndex);
    }
    selectContainer = (event) => {
        let selectedIndex = event.target.dataset.key;
        this.setState({ containerIndex: selectedIndex })
    }
    saveContainer = () => {
        this.props.saveNewContainer('TRANSFER');
    }
    onClear = () => {
        //this.setState({ filterType: "C" });
        this.props.clearTransferForm(this.state.filterType);
        this.setState({isOwnCarrierCode:'Y'});
    }
    validateCarrier =(event) =>{
       
        let partnerCarriers = this.props.partnerCarriers;
        if(event.target.defaultValue !== this.props.ownAirlineCode ){
             this.setState({isOwnCarrierCode:'N'});
        }
        if(event.target.defaultValue === undefined || event.target.defaultValue==='' || event.target.defaultValue=== null || partnerCarriers && partnerCarriers.includes(event.target.defaultValue)){
            this.setState({isOwnCarrierCode:'Y'});
        }
      
      

    }

    render() {
        const rowCount = (this.props.containerDetails) ? this.props.containerDetails.length : 0;

        return (

            <Fragment>
                <PopupBody>
                    <div>
                        <div className="pad-md pad-b-2xs">
                            <Label className="form-control-label">Transfer To</Label>
                            <div>
                                <IRadio name="transferFilterType" options={[{ 'label': 'Carrier', 'value': 'C' }, { 'label': 'Flight', 'value': 'F' }]} value={this.state.filterType} onChange={this.onFilterChange} />
                            </div>
                        </div>
                        <div>
                            {(this.state.filterType === 'C') ?
                                <div className="pad-x-md">
                                    <Row>
                                        <Col xs="6">
                                            <div className="form-group">
                                                <label className="form-control-label ">Carrier Code</label>
                                                <Lov name="carrierCode" uppercase={true} lovTitle="Carrier Code" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" onBlur={this.validateCarrier}/>
                                            </div>
                                        </Col>
                                        <Col xs="6">
                                            <div className="form-group">
                                                <label className="form-control-label ">Destination</label>
                                                <Lov name="destination" disabled={this.state.isOwnCarrierCode ==='N'?true:false} uppercase={true} lovTitle="Destination airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                                            </div>
                                        </Col>
                    <Col xs="5">
                                            <div className="form-group">
                                                <label className="form-control-label ">Uplift</label>
                                                <ITextField name="uplift" disabled={this.state.isOwnCarrierCode ==='N'?true:false} uppercase={true} />
                                            </div>
                                        </Col>
                    <Col xs="7" className="mar-t-md">
                      <IButton category="primary" disabled={this.state.isOwnCarrierCode === 'N' ? true : false} onClick={this.onlistContainer}>List</IButton>
                      <IButton category="secondary"  onClick={this.onClear}>Clear</IButton>
                    </Col>
                                    </Row>
                                </div> : null}
                          {/*  {(this.state.filterType === 'F') ? ( */}
                                <Fragment>
                                       {(this.state.filterType === 'F') ?  
                                    <div className="pad-x-md">
                                        <Row>
                                            <div className="col">
                                                <div className="form-group">
                                                    <IFlightNumber mode="edit" ></IFlightNumber>
                                                </div>
                                            </div>
                                            <div className="col-auto mar-t-md">
                                                <IButton category="primary" onClick={this.onlistContainer}>Verify</IButton>
                                                <IButton category="secondary" onClick={this.onClear}>Clear</IButton>
                                            </div>
                                        </Row>
                  </div> : null
                                  }
                                    <div className="card border-bottom-0">
                                        <div className="card-header card-header-action">
                                            <div className="col"><h4>Container Details</h4></div>
                                            
                                            <div className="col-auto">
                                                {this.props.flightValidation === null}{

                                                    <PopoverDetails width={600} initialValues={this.props.initialValues} filterType={this.state.filterType} flightValidation={this.props.flightValidation} pous={this.props.pous}  isValidFlight={this.props.isValidFlight} saveNewContainer={this.props.saveNewContainer} pabuiltUpdate={this.props.pabuiltUpdate} showTransfer={this.props.showTransfer} clearAddContainerPopover={this.props.clearAddContainerPopover} >


                 

                                                        <div className="card">
                                                            <div className="card-body p-0">
                                                                <div className="pad-md pad-b-3xs border-bottom">
                                                                    <Row>
                                                                        <Col xs="auto">
                                                                            <div className="form-group mar-t-md">
                                                                                <ICheckbox name="barrowFlag" label="Barrow" />
                                                                            </div>
                                                                        </Col>
                                                                        <Col xs="6">
                                                                            <div className="form-group">
                                                                                <label className="form-control-label ">ULD/Barrow</label>
                                                                                <ITextField mode="edit" name="uldNumber" uppercase={true} type="text"></ITextField>
                                                                            </div>
                                                                        </Col>
                                                                        <Col xs="5">
                                                                            <div className="form-group">
                                                                                <label className="form-control-label ">POU</label>
                                                                                <ITextField mode="edit" name="pou" uppercase={true} type="text"></ITextField>
                                                                            </div>
                                                                        </Col>
                                                                        <Col xs="5">
                                                                            <div className="form-group">
                                                                                <label className="form-control-label ">Destination</label>
                                                                                <ITextField mode="edit" name="uldDestination" uppercase={true} type="text"></ITextField>
                                                                            </div>
                                                                        </Col>
                                                                        <Col xs="auto">
                                                                            <div className="form-group mar-t-md">
                                                                                <ICheckbox name="paBuilt" label="PA Build" />
                                                                            </div>
                                                                        </Col>
                                                                        <Col xs="24">
                                                                            <div className="form-group">
                                                                                <label className="form-control-label ">Remarks</label>
                                                                                <ITextField mode="edit" name="uldRemarks" uppercase={true} type="text"></ITextField>
                                                                            </div>
                                                                        </Col>
                                                                    </Row>
                                                                </div>
                                                                <div className="border-bottom pad-x-2xs">
                                                                    <h4>Onward Routing</h4>
                                                                </div>
                                                                <div className="pad-md pad-b-3xs scroll-y" style={{ maxHeight: "100px" }}>
                                                                    <OnwardRouting />
                                                                </div>
                                                                <div className="btn-row">
                                                                    <IButton category="primary" bType="SAVE" accesskey="S"  onClick={this.saveContainer}>Save Container</IButton>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </PopoverDetails>
                                                }
                                            </div> 
                                        </div>
                                        <div class="d-flex" style={{ height: "200px" }}>
                                            <ITable
                                                rowCount={rowCount}
                                                headerHeight={35}
                                                className="table-list"
                                                gridClassName="table_grid"
                                                headerClassName="table-head"
                                                rowHeight={35}
                                                rowClassName='table-row'
                                                data={this.props.containerDetails}>
                                                <Columns>
                                                    <IColumn
                                                        dataKey=""
                                                        width={32}
                                                        flexGrow={0}>
                                                        <Cell>
                                                            <HeadCell disableSort>
                                                                {(cellProps) => (
                                                                    <Content>
                                                                        <label ></label>
                                                                    </Content>)
                                                                }
                                                            </HeadCell>
                                                            <RowCell>
                                                                {(cellProps) => (
                                                                    <Content>
                                                                        <Input type="checkbox" name="contanerFlag" value="Y" key={rowCount + cellProps.rowIndex} className="m-r-5 m-l-5" data-key={cellProps.rowIndex} onClick={this.selectContainer} />
                                                                        <label ></label>
                                                                    </Content>)
                                                                }
                                                            </RowCell>
                                                        </Cell>
                                                    </IColumn>
                                                    <IColumn
                                                        dataKey="containerNumber"
                                                        label="Container No"
                                                        width={100}
                                                        flexGrow={1}                                        >
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
                                                        width={80}
                                                        flexGrow={0}>
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
                                                        dataKey="finalDestination"
                                                        label="Destination"
                                                        width={80}
                                                        flexGrow={0}>
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
                                                        width={80}
                                                        flexGrow={0}>
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
                                                        width={100}
                                                        flexGrow={0}>
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
                                                </Columns>
                                            </ITable>
                                        </div>
                                    </div>
                                </Fragment>
                          {/*  ) : (*/}
                              { (this.state.filterType === 'C') ?
                                <div className="pad-x-md pad-b-md">
                                <Row>
                                    <Col xs="10">
                                        <Label>Scan Date</Label>
                                        {/* <DatePicker name="scanDate" /> */}
                                        {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                         
                                    </Col>
                                    <Col xs="6">
                                        <Label>Scan Time</Label>
                                        <TimePicker name="scanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false} />
                                    </Col>
                                </Row>
                            </div> :null
                              }
                            {/*)}  */}
                        </div>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.transferSave}>Save</IButton>{' '}
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.close}>Close</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}


export default icpopup(wrapForm('transferForm')(TransferPanel), { title: 'Transfer Mailbag' })