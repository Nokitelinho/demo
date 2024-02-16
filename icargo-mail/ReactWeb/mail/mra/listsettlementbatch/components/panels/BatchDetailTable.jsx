import React, { Component } from 'react';
import { IColumn, ITable, Cell, HeadCell, RowCell, Content, Columns } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown'
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'
import { Label } from "reactstrap";
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Key} from '../../constants/constants.js';

export default class BatchDetailTable extends Component {
    constructor(props) {
        super(props);
    
    }
    selectExcelUpload=(data)=> {
        this.props.onSelectExcelUpload(data)
    }
    saveSelectedBatchIndexes = (data) => {               
        this.props.saveSelectedBatchIndexes(data.selectedIndexes);                  
     }
    render() {
        const rowCount = (this.props.batchDetailsList.results) ? this.props.batchDetailsList.results.length : 0;
        const batchdetailsPage = this.props.batchDetailsList
        const batchdetails = this.props.batchDetailsList.results
        const tableSource=this.props.source
        const tableBatchDetail=this.props.batchDetailsList
      
        return (
            <ITable
                rowCount={rowCount}
                headerHeight={40}
                className="table-list"
                gridClassName="table_grid"
                headerClassName="table-head"
                rowHeight={45}
                rowClassName="table-row"
                onRowSelection={this.saveSelectedBatchIndexes}
                customHeader={
                    {
                        customPanel: <HeaderPanel />,
                        pagination: {
                            page: batchdetailsPage,
                            getPage: this.props.getNewPage
                        },
                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            name: 'Batch_Details'
                        }

                    }

                }
                data={batchdetails}
            >
             <Columns >
                    <IColumn
                            width={40}
                            dataKey=""
                            className="first-column"
                            hideOnExport>
                            <Cell>
                                <HeadCell disableSort selectOption>
                                </HeadCell>
                                <RowCell selectOption>
                                </RowCell>
                            </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="gpaCode"
                        label="PA Code"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="accountNumber"
                        label="Acc No:"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="invoiceNumber"
                        label="Invoice No:"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="mailIdr"
                        label="Mailbag ID"
                        width={110}
                        flexGrow={6} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="batchamount"
                        label="Batch Amount"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="appliedAmount"
                        label="Applied Amt."
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
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
                        dataKey="unappliedAmount"
                        label="Unapplied Amt."
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content><span className="align-center d-inline-block align-middle">{cellProps.cellData}</span>
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                        dataKey="reasonCode"
                        label="Reason"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                     <Content><Label id={'reasonCode-' +cellProps.rowIndex} >{cellProps.cellData && cellProps.cellData.length > 13 ? cellProps.cellData.substring(0, 10) + "..." : cellProps.cellData}</Label>
                                        {cellProps.cellData && cellProps.cellData.length > 13 ? <IToolTip value={cellProps.cellData} target={'reasonCode-' + cellProps.rowIndex} placement='bottom' /> : ""}
                                    </Content>)                                   
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="remarks"
                        label="Remarks"
                        width={70}
                        flexGrow={1} >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label} </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) =>  (
                                     <Content><Label id={'remarks-' + cellProps.rowIndex} >{cellProps.cellData && cellProps.cellData.length > 13 ? cellProps.cellData.substring(0, 10) + "..." : cellProps.cellData}</Label>
                                        {cellProps.cellData && cellProps.cellData.length > 13 ? <IToolTip value={cellProps.cellData} target={'remarks-' + cellProps.rowIndex} placement='bottom' /> : ""}
                                    </Content>)   
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>                   
                </Columns>
            </ITable>
        );
    }
}
class HeaderPanel extends Component {

    render() {
        return <div className="d-flex align-items-left">
            <h4><IMessage msgkey={Key.BATCH_DTL_LBL} /></h4>
        </div>
    }
}

BatchDetailTable.propTypes = {   
    onClearSplit: PropTypes.func,
    getNewPage: PropTypes.func,
    exportToExcel: PropTypes.func,    
    onCloseSplitPopup: PropTypes.func,    
    batchDetailsList: PropTypes.object,
    source: PropTypes.string
};