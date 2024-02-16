import React, { Component,Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import LyinglistFilterPanel from './LyinglistFilterPanel.jsx'
import PropTypes from 'prop-types';

export default class LyingListGroupViewTable extends Component {
   
    render() {
       const rowCount = (this.props.lyingGroupMailbags.results) ? this.props.lyingGroupMailbags.results.length : 0
         return (
            <Fragment>
            <ITable
                rowCount={rowCount}
                headerHeight={35}
                className="table-list"
                gridClassName=""
                headerClassName=""
                rowHeight={70}
                tableId="lyinglistgrouptable"
                rowClassName="table-row"
                customHeader={{
                    placement:'dynamic',
                           "pagination":{"page":this.props.lyingGroupMailbags,getPage:this.props.getNewPage,"mode":'subminimal'},
                            filterConfig: {
                                panel: <LyinglistFilterPanel  oneTimeValues={this.props.oneTimeValues} tablefilterValues={this.props.tablefilterValues} initialValues={this.props.initialValues}/>,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyFilter,
                                onClearFilter: this.props.onClearFilter
                            },
                           // customPanel:<CarditSummary carditSummary={this.props.carditSummary}/> 
            
                  }}
                data={this.props.lyingGroupMailbags.results}
                >
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
                                            {cellProps.rowData.acceptedWeight ? cellProps.rowData.acceptedWeight.roundedDisplayValue : ''} / {cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : ''}
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
LyingListGroupViewTable.propTypes = {
    lyingGroupMailbags: PropTypes.object,
    getNewPage:PropTypes.func,
    oneTimeValues:PropTypes.object,
    tablefilterValues:PropTypes.object,
    initialValues:PropTypes.object,
    onApplyFilter:PropTypes.func,
    onClearFlightFilter:PropTypes.func,
    onClearFilter:PropTypes.func,
}