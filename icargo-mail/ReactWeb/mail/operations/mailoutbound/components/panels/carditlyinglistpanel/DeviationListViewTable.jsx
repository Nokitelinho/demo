import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import DeviationlistFilterPanel from './DeviationlistFilterPanel.jsx'
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import PropTypes from 'prop-types';

export default class DeviationListViewTable extends Component {
    constructor(props) {
        super(props);
        this.selectedLyingMailbags = [];
        this.state = {
            rowClkCount: 0,
        }
    }

    onRowSelection = (data) => {
        let  deviationlistMailbags = (this.props.deviationlistMailbags.results) ? this.props.deviationlistMailbags.results : []
        //currently, there is no action defined for unplanned shipment
        let isUnplanned = false;
        if(deviationlistMailbags.length === 0) {
            return;
        } else {
            for(var i=0;i<deviationlistMailbags.length;i++) {
                if(deviationlistMailbags[i].deviationErrors.includes('IBUNPLANED')) {
                    isUnplanned = true;
                }
            }
        }
        if (data.index > -1 && !isUnplanned) {
            this.props.onClickDeviationLlist({ id: data.rowData.mailSequenceNumber, isRowSelected: data.isRowSelected })
        } else if (data.event && data.event.target && data.event.target && !isUnplanned) {
            if (data.event.target && data.event.target.checked === true) {
                this.props.onClickDeviationLlist({ selectAll: true })
            } else {
                this.props.onClickDeviationLlist({ unSelectAll: true })
            }
        } else if (data.index == -1 && data.isRowSelected === false) {
            this.props.onClickDeviationLlist({ unSelectAll: true })
        }
    }

    rowClassNameFn({ rowData }) {
        return !isEmpty(rowData.deviationErrors) && (rowData.deviationErrors.includes('OBMISSED'))
            ? 'table-row notify-border-left red red-warning no-mar' : 'table-row  notify-border-left blue red-warning no-mar'
    }

    render() {
        const rowCount = (this.props.deviationlistMailbags.results) ? this.props.deviationlistMailbags.results.length : 0
        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={70}
                    rowClassName={this.rowClassNameFn}
                    tableId="deviationlisttable"
                    customHeader={{
                        searchConfig: true,
                        placement: 'dynamic',
                        "pagination": { "page": this.props.deviationlistMailbags, getPage: this.props.getNewPage, "mode": 'subminimal' },
                        filterConfig: {
                            panel: <DeviationlistFilterPanel oneTimeValues={this.props.oneTimeValues} tablefilterValues={this.props.tablefilterValues} initialValues={this.props.initialValues} deviationListFilter={this.props.deviationListFilter}/>,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyFilter,
                            onClearFilter: this.props.onClearFilter
                        },
                    }}
                    data={this.props.deviationlistMailbags.results}
                    onRowSelection={this.onRowSelection}>
                    <Columns>
                        <IColumn
                            //width={32}
                            dataKey=""
                            flexGrow={0}
                            className="" >
                            <Cell>
                                <HeadCell disableSort selectOption>
                                </HeadCell>
                                <RowCell selectOption>
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey=""
                            label=""
                            flexGrow={1}
                            id="ooe"
                            width={120}
                            selectColumn={false}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            <div className="pad-b-3xs">{cellProps.rowData.mailbagId}</div>
                                            <div className="row">
                                                <div className="col">
                                                    <div className="text-light-grey pad-b-3xs">{cellProps.rowData.consignmentDate}</div>
                                                    <div>{cellProps.rowData.mailorigin ? cellProps.rowData.mailorigin : ''} - {cellProps.rowData.mailDestination ? cellProps.rowData.mailDestination : ''}</div>
                                                </div>
                                                <div className="col">
                                                    <div className="pad-b-3xs">{cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : ''} {''} {cellProps.rowData.weight ? cellProps.rowData.weight.unitCodeForDisplay : ''}</div>
                                                    <div>{cellProps.rowData.consignmentNumber}</div>
                                                    {
                                                        cellProps.rowData.reqDeliveryDateAndTime && 
                                                        <div>RDT: {cellProps.rowData.reqDeliveryDateAndTime}</div>
                                                    }
                                                    
                                                </div> 
                                                <div className="col-auto">
                                                {cellProps.rowData.accepted === 'Y' ? <i className="icon ico-ok-green"></i> : (cellProps.rowData.accepted === 'E' ? <i className="icon ico-error-sm"></i> : <i className="icon ico-ok-dark"></i>)}
                                            </div>
                                            </div>
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                    </Columns>
                </ITable>
            </Fragment>
        );
    }
}

DeviationListViewTable.propTypes = {
    selectLyingMailbags: PropTypes.func,
    deviationlistMailbags: PropTypes.object,
    getNewPage: PropTypes.func,
    onApplyFilter: PropTypes.func,
    onClearFilter: PropTypes.func,
    initialValues: PropTypes.object,
    tablefilterValues: PropTypes.object,
    oneTimeValues: PropTypes.object,
}