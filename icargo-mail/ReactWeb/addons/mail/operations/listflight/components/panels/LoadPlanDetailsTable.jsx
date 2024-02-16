import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'


export default class LoadPlanDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedFlights = []
    }


    onSelectmultiple = (data) => {
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                this.selectFlight(data.index);
            } else {
                this.unSelectFlight(data.index);
            }
        } else {
            if (data.selectedIndexes.length) {
                this.selectAllFlight()
            } else {
                this.unSelectAllFlight();

            }
        }  
    }

    displayHeader = () => {
        return <h4 class="fs16 font-weight-bold">Flight Details</h4>
    }

    selectFlight = (flight) => {
        this.selectedFlights.push(flight);
        this.props.saveSelectedFlightIndex(this.selectedFlights);
    }

    unSelectFlight = (flight) => {
        let index = -1;
        for (let i = 0; i < this.selectedFlights.length; i++) {
            var element = flight;
            if (element === flight) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedFlights.splice(index, 1);
        }
        this.props.saveSelectedFlightIndex(this.selectedFlights);
    }

    selectAllFlight = () => {
        for (let i = 0; i < this.props.flightDetails.length; i++) {
            this.selectedFlights.push(i);
        }
        this.props.saveSelectedFlightIndex(this.selectedFlights);
    }

    unSelectAllFlight = () => {
        this.selectedFlights = []
        this.props.saveSelectedFlightIndex(this.selectedFlights);
    }

    render() {
       if (this.props.flightDetails.length) {
            for (var i = 0; i < this.props.flightDetails.length; i++) {
                this.props.flightDetails[i].flightNumber = this.props.flightDetails[i].bookingFlightNumber?this.props.flightDetails[i].bookingCarrierCode + "-" +
                    this.props.flightDetails[i].bookingFlightNumber:this.props.flightDetails[i].bookingCarrierCode;
                this.props.flightDetails[i].bookingFlightDate = this.props.flightDetails[i].bookingFlightDate.toString().substring(0, 11);
            }
        }
        const results = (this.props.flightDetails && this.props.flightDetails.length) ?
            this.props.flightDetails : [];

        return (
            <div className="inner-panel">
                <div class="d-flex" style={{ height: "35vh" }}>
                    <ITable
                        rowCount={results.length}
                        headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName="table-head"
                        rowHeight={35}
                        rowClassName='table-row'
                        data={results}
                        tableId="awbFlightDetailsTable"
                        name="awbFlightDetailsTable"
                        form={true}
                        enableFixedRowScroll
                        hideTopRightGridScrollbar
                        hideBottomLeftGridScrollbar
                        sortEnabled={false}
                        onRowSelection={this.onSelectmultiple}
                        customHeader={{
                            headerClass: '',
                            customPanel: this.displayHeader(),
                            // "pagination": { "page": this.props.flightDetails, getPage: this.props.getNewPage },

                        }}
                        additionalData={{}}
                        width={'100%'}
                    >
                        <Columns>
                            <IColumn
                                width={100}
                                dataKey=""
                                flexGrow={0}
                                className="align-items-center first-column" hideOnExport>
                                <Cell>
                                    <HeadCell disableSort selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>

                            <IColumn
                                label='flightNumber'
                                dataKey='flightNumber'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>Flight No</Content>)
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
                                label='bookingFlightDate'
                                dataKey='bookingFlightDate'
                                width={150} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>Flight Date</Content>)
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
                                label='origin'
                                dataKey='origin'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>POL</Content>)
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
                                label='destination'
                                dataKey='destination'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>POU</Content>)
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
                                label='bookedPieces'
                                dataKey='bookedPieces'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>Pcs.</Content>)
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
                                label='bookedWeight'
                                dataKey='bookedWeight'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>Wgt.(Kg.)</Content>)
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
                                label='attachedMailBagCount'
                                dataKey='attachedMailBagCount'
                                width={100} flexGrow={0} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>Attached Pcs.</Content>)
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
        );

    }
}


