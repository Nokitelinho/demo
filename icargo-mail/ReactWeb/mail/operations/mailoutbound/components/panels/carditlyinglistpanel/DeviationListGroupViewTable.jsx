import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { Label } from "reactstrap";
import DeviationlistFilterPanel from './DeviationlistFilterPanel.jsx'
import PropTypes from 'prop-types';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

export default class DeviationListGroupViewTable extends Component {

    rowClassNameFn({rowData}){
        return !isEmpty(rowData.deviationErrors) && (rowData.deviationErrors.includes('OBMISSED'))
            ? 'table-row notify-border-left red red-warning no-mar' : 'table-row  notify-border-left blue red-warning no-mar'
    } 
    render() {
        const rowCount = (this.props.deviationGroupMailbags.results) ? this.props.deviationGroupMailbags.results.length : 0
        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={70}
                    tableId="deviationlistgrouptable"
                    rowClassName={this.rowClassNameFn}
                    customHeader={{
                        placement: 'dynamic',
                        "pagination": { "page": this.props.deviationGroupMailbags, getPage: this.props.getNewPage, "mode": 'subminimal' },
                        filterConfig: {
                            panel: <DeviationlistFilterPanel oneTimeValues={this.props.oneTimeValues} initialValues={this.props.initialValues} deviationListFilter={this.props.deviationListFilter}/>,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyFilter,
                            onClearFilter: this.props.onClearFilter
                        },

                    }}
                    data={this.props.deviationGroupMailbags.results}>
                    <Columns>
                        <IColumn
                            dataKey=""
                            label=""
                            width={45}
                            flexGrow={1}>
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
                            width={70}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>Total Pcs</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                             {cellProps.rowData.count}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey=""
                            label=""
                            width={65}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>Total Wt</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                             {cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : ''}
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
                                        <Content></Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {
                                                cellProps.rowData.deviationErrors && cellProps.rowData.deviationErrors.includes('OBMISSED') ?
                                                <Label className="badge badge-pill light badge-error">
                                                    O/B Missed
                                                </Label> :
                                                <Label className="badge badge-pill light badge-info">
                                                    I/B Unplanned  
                                                </Label> 
                                            }                                          
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

DeviationListGroupViewTable.propTypes = {
    deviationGroupMailbags: PropTypes.object,
    getNewPage: PropTypes.func,
    oneTimeValues: PropTypes.object,
    tablefilterValues: PropTypes.object,
    initialValues: PropTypes.object,
    onApplyFilter: PropTypes.func,
    onClearFlightFilter: PropTypes.func,
    onClearFilter: PropTypes.func,
}