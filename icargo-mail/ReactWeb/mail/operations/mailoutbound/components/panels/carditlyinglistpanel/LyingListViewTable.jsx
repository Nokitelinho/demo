import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import LyinglistFilterPanel from './LyinglistFilterPanel.jsx'
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
import PropTypes from 'prop-types';

export default class LyingListViewTable extends Component {
    constructor(props) {
        super(props);
        this.selectedLyingMailbags = [];
        this.state = {

            rowClkCount: 0,

        }
    }

    onRowSelection = (data) => {
        if(data.selectedIndexes) {
          this.props.selectLyingMailbags(data.selectedIndexes);
        }
  
    }
    

    render() {
        const rowCount = (this.props.lyinglistMailbags.results) ? this.props.lyinglistMailbags.results.length : 0
        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={70}
                    rowClassName="table-row"
                    tableId="lyinglisttable"
                    customHeader={{
                        placement:'dynamic',
                        "pagination": { "page": this.props.lyinglistMailbags, getPage: this.props.getNewPage, "mode": 'subminimal' },
                        filterConfig: {
                            panel: <LyinglistFilterPanel oneTimeValues={this.props.oneTimeValues} tablefilterValues={this.props.tablefilterValues} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyFilter,
                            onClearFilter: this.props.onClearFilter
                        },
                        // customPanel:<CarditSummary carditSummary={this.props.carditSummary}/> 

                    }}
                    data={this.props.lyinglistMailbags.results}
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
                            width={100}
                            selectColumn={false}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (<Content>
                                        {<div className="pad-b-3xs">
                                            <DataDisplay index={cellProps.rowIndex} id='mailbagId' >
                                                {cellProps.rowData.mailbagId}
                                            </DataDisplay>
                                        </div>}
                                        {<div className="row">
                                            <div className="col">
                                                <div className="text-light-grey pad-b-3xs">{cellProps.rowData.scannedDate}</div>
                                                <div>{cellProps.rowData.mailorigin ? cellProps.rowData.mailorigin:''} - {cellProps.rowData.mailDestination ? cellProps.rowData.mailDestination:''}</div>
                                            </div>
                                            <div className="col">
                                                <div className="pad-b-3xs">
                                                    <DataDisplay index={cellProps.rowIndex} id='weight' >
                                                    {cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue:''} {''} {cellProps.rowData.weight ? cellProps.rowData.weight.unitCodeForDisplay:''}
                                                    </DataDisplay>
                                                </div>
                                                <div>{cellProps.rowData.containerNumber}</div>
                                            </div>
                                            <div className="col-auto">
                                                {cellProps.rowData.accepted === 'Y' ? <i className="icon ico-ok-green"></i> : <i className="icon ico-ok-dark"></i>}
                                            </div>
                                        </div>}
                                    </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        {/* <IColumn
                            dataKey="accepted"
                            label=""
                            width={20}
                            flexGrow={0}
                            id="accepted">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {cellProps.rowData.accepted === 'Y' ? <i className="icon ico-ok-green"></i> : <i className="icon ico-ok-dark"></i>}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn> */}
                    </Columns>
                </ITable>
            </Fragment>
        );
    }
}
LyingListViewTable.propTypes = {
    selectLyingMailbags: PropTypes.func,
    lyinglistMailbags:PropTypes.object,
    getNewPage:PropTypes.func,
    onApplyFilter:PropTypes.func,
    onClearFilter:PropTypes.func,
    initialValues:PropTypes.object,
    tablefilterValues:PropTypes.object,
    oneTimeValues:PropTypes.object,
}