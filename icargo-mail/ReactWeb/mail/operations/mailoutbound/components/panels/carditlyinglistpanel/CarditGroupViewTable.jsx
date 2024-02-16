import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import CarditFilterPanel from './CarditFilterPanel.jsx'
import PropTypes from 'prop-types';
export default class CarditGroupViewTable extends Component {
    constructor(props) {
        super(props);
    }
    onApplyCarditFilter=()=> {
        this.props.onApplyCarditFilter(this.props.carditFilter.values);
    }
    render() {
        const rowCount = (this.props.carditGroupMailbags) ? this.props.carditGroupMailbags.results.length : 0
        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={45}
                    rowClassName="table-row"
                    tableId="carditgrouptable"
                    customHeader={{
                        placement:'dynamic',
                        "pagination": { "page": this.props.carditGroupMailbags, getPage: this.props.getNewPage, "mode": 'subminimal' },
                        filterConfig: {
                            panel: <CarditFilterPanel carditFilterFormValues={this.props.carditFilter} oneTimeValues={this.props.oneTimeValues} tablefilterValues={this.props.tablefilterValues} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.onApplyCarditFilter,
                            onClearFilter: this.props.onClearCarditFilter
                        },
                       // customPanel: <CarditSummary carditSummary={this.props.carditSummary} />

                    }}
                    data={this.props.carditGroupMailbags ? this.props.carditGroupMailbags.results:[]}>
                    <Columns>
                      
                        <IColumn
                            dataKey=""
                            label=""
                            width={50}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>Dest</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.rowData.destinationCode}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey=""
                            label=""
                            width={100}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>ACP/Total Pcs</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {cellProps.rowData.acceptedBagCount} / {cellProps.rowData.count}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey=""
                            label=""
                            width={100}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>ACP/Total Wt</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            <div>{cellProps.rowData.acceptedWeight ? cellProps.rowData.acceptedWeight.roundedDisplayValue : ''}/{cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : ''}</div>

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
CarditGroupViewTable.propTypes={
    onApplyCarditFilter: PropTypes.func,
    carditFilter:PropTypes.object,
    carditGroupMailbags:PropTypes.array,
    getNewPage: PropTypes.func,
    oneTimeValues:PropTypes.object,
    onClearCarditFilter: PropTypes.func,
    tablefilterValues:PropTypes.object,
    initialValues:PropTypes.object,
}