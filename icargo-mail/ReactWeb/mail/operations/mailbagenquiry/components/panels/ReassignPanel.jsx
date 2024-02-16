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
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import PopoverDetails from './Popoverdetails.jsx'

class ReassignPanel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filterType: "F",
            showContainer: "N",
            containerIndex: "",
            disablePou:false
        }
    }

    onFilterChange = (event) => {
        //this.setState({ filterType: event.target.value })
        if(event.target.value==='C'){
            this.setState({ disablePou: true, filterType: event.target.value })
        }else{
            this.setState({ disablePou: false, filterType: event.target.value })
        }
    }
    showContainer = () => {
        this.setState({ showContainer: 'Y' })
    }
    
    onlistContainer = () => {
        this.props.listContainer(this.props.selectedMail,this.state.filterType, 'REASSIGN');
    }
    reassignSave = () => {
        this.props.reassignSave(this.state.containerIndex);
    }
    selectContainer = (event) => {
        let selectedIndex = event.target.dataset.key;
        this.setState({ containerIndex: selectedIndex })
    }
    onClear = () => {
        this.props.clearReassignForm(this.state.filterType);
    }
    onRowSelection = (data) => {
        if (data.selectedIndexes) {
            this.setState({ containerIndex: data.selectedIndexes })
            this.props.saveSelectedContainersIndex(data.selectedIndexes);
        }
    }
    render() {
        const rowCount = (this.props.containerDetails) ? this.props.containerDetails.length : 0;
        return (

            <Fragment>
                <PopupBody>
                    <div className="pad-md pad-t-xs pad-b-3xs">
                        <div className="pad-b-2xs">
                            <IRadio name="reassignFilterType" options={[{ 'label': 'Carrier', 'value': 'C' }, { 'label': 'Flight', 'value': 'F' }]} value={this.state.filterType} onChange={this.onFilterChange} />
                        </div>
                        <Row>
                            {(this.state.filterType === 'C') ?
                                <Fragment>
                                <div className="col-5">
                                    <div className="form-group">
                                        <label className="form-control-label">Carrier Code</label>
                                        <Lov name="carrierCode" uppercase={true} lovTitle="Carrier Code" componentId="CMP_Mail_Operations_MailBagEnquiry_FromCarrier" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" />
                                    </div>
                                </div>
                                <div className="form-group">
                                    <label className="form-control-label ">Destination</label>
                                    <Lov name="destination" uppercase={true} lovTitle="Destination airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                                </div></Fragment> : null}
                                {(this.state.filterType === 'F') ?
                                    <div className="col">
                                        <div className="form-group">
                                            <IFlightNumber mode="edit" ></IFlightNumber>
                                        </div>
                                    </div> : null}
                            <div className="col-5">
                                <div className="form-group">
                                    <label className="form-control-label ">Uplift</label>
                                    <ITextField name="uplift" uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_UpliftAirport" lovTitle="Uplif airport" />
                                </div>
                            </div>
                            <div className="col-auto">
                                <IButton category="primary" bType="LIST" accesskey="L" className="mar-t-md" onClick={this.onlistContainer}>List</IButton>
                                <IButton category="secondary" bType="CLEAR" accesskey="C" className="mar-t-md" onClick={this.onClear}>Clear</IButton>
                            </div>
                        </Row>
                    </div>

                    <div className="card">
                        <div className="card-header card-header-action">
                            <div className="col"><h4>Container Details</h4></div>
                            <div className="col-auto">
                                {!this.props.flightValidation === null}{
                                    <PopoverDetails width={600} isValidFlight={this.props.isValidFlight} pous={this.props.pous} pabuiltUpdate={this.props.pabuiltUpdate}   saveNewContainer={this.props.saveNewContainer} disablePou={this.state.disablePou} clearAddContainerPopover={this.props.clearAddContainerPopover} showReassign={this.props.showReassign}>
                                    </PopoverDetails>
                                }
                            </div>
                        </div>
                        <div className="d-flex" style={{ height: "200px" }}>
                            <ITable
                                rowCount={rowCount}
                                headerHeight={35}
                                className="table-list"
                                gridClassName="table_grid"
                                headerClassName="table-head"
                                rowHeight={35}
                                rowClassName='table-row'
                                data={this.props.containerDetails}
                                onRowSelection={this.onRowSelection}>

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
                                            <RowCell selectOption>
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
                                                    <Content>{cellProps.rowData.containerNumber}
                                                    {cellProps.rowData.paBuiltFlag === 'Y'&&'(SB)'}
                                                    </Content>)
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
                    <div className="pad-md">
                        <Row>
                                <Col xs="5" className="form-group mandatory">
                                    <Label className="form-control-label">Scan Date</Label>
                                    {isSubGroupEnabled('AA_SPECIFIC')? <ITextField className="form-control" name="scanDate" disabled={true}/>:<DatePicker name="scanDate" />}
                                    
                                </Col>
                                <Col xs="3" className="form-group mandatory">
                                    <Label className="form-control-label">Scan Time</Label>
                                    <TimePicker name="scanTime" disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false}/>
                                </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" disabled={!this.props.containerDetails} onClick={this.reassignSave} >Save</IButton>{' '}
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.close}>Close</IButton>{' '}
                </PopupFooter>
            </Fragment>

        )
    }
}


export default icpopup(wrapForm('reassignForm')(ReassignPanel), { title: 'Reassign Mail', className: 'modal_700px' })
