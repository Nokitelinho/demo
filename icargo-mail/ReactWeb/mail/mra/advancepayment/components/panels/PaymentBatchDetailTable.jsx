import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import HeaderPanel from './HeaderPanel.jsx';
import PropTypes from 'prop-types';
import { Key,ComponentId } from '../../constants/constants.js';
import {IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'

let Aux = (props)=>  props.children
export default class PaymentBatchDetailTable extends Component {
    constructor(props) {
        super(props);

    }

    addAttachment= (data) => {
        this.props.addAttachment(data)
    }
    
    editBatch= (data) => {
        this.props.editBatch(data);
    }
    deleteBatch= (data) => {
        this.props.deleteBatch(data);
    }

    deleteAttachment= (data) => {
        this.props.deleteAttachment(data);
    }
	saveSelectedBatchIndexes = (data) => {               
       this.props.saveSelectedBatchIndexes(data.selectedIndexes);                  
    }
        render() {
        const results = this.props.paymentdetails.results?this.props.paymentdetails.results:'';
        const rowCount = results?results.length:'';

        return (
            <Aux>
            <ITable
                rowCount={rowCount}
                headerHeight={45}
                className="table-list"
                rowHeight={50}
                rowClassName="table-row"
                tableId="paymenttable"
                sortEnabled={false}
                form={true}
                resetSelectionOnDataChange={true}
                name='newPaymentTable'
                onRowSelection={this.saveSelectedBatchIndexes}
                customHeader={{
                            headerClass: '',
                            placement: 'dynamic',
                customPanel: <HeaderPanel filterValues={this.props.filterValues}  onAddPayment={this.props.onAddPayment} />,
                "pagination": { "page": this.props.paymentdetails, getPage: this.props.getNewPage,
                options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ]  },
                pageable: true,
                exportData: {
                    exportAction: this.props.exportToExcel,
                    pageable: true,
                    name: 'Payment Batch Details'
                },
                  }}
                data={results}
               >
                    <Columns >
                    <IColumn
                            width={32}
                            dataKey=""
                            flexGrow={0}
                            className="" 
                            hideOnExport>
                            <Cell>
                                <HeadCell disableSort selectOption>
                                </HeadCell>
                                <RowCell selectOption>
                                </RowCell>
                            </Cell>
                    </IColumn>                       
                    <IColumn
                        dataKey="batchID"
                        label="Batch ID"
                        flexGrow={3}
                        id="batchID"
                        width={30}
                        >
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
                        dataKey="paCode"
                        label="PA Code"
                        flexGrow={3}
                        id="paCode"
                        width={30}
                        >
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
                        dataKey="batchStatus"
                        label="Batch Status"
                        flexGrow={3}
                        id="batchStatus"
                        width={30}
                        >
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
                        dataKey="currency"
                        label="Currency"
                        flexGrow={3}
                        id="currency"
                        width={30}
                        >
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
                        dataKey="batchAmt"
                        label="Batch Amount"
                        flexGrow={3}
                        id="batchAmt"
                        width={30}
                        >
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
                        dataKey="appliedAmt"
                        label="Applied Amount"
                        flexGrow={3}
                        id="appliedAmt"
                        width={30}
                        >
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
                        dataKey="unappliedAmt"
                        label="Unapplied Amount"
                        flexGrow={3}
                        id="unappliedAmt"
                        width={30}
                        >
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
                        dataKey="date"
                        label="Date"
                        flexGrow={3}
                        id="date"
                        width={30}
                        >
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
                        dataKey="remark"
                        label="Remark"
                        flexGrow={3}
                        id="remark"
                        width={30}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                            {(cellProps) => (
                                <Content><Label id={'remark' +cellProps.rowIndex} >
                                {cellProps.cellData && cellProps.cellData.length > 13 ? 
                                    cellProps.cellData.substring(0, 10) + "..." : cellProps.cellData}</Label>
                                    {cellProps.cellData && cellProps.cellData.length > 13 ? <IToolTip value={cellProps.cellData} target={'remark' +cellProps.rowIndex} placement='bottom' /> : ""}
                                </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>  
                    <IColumn
                        dataKey=""
                        label="Attachment"
                        hideOnExport
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
                                    <Content>
                                        {cellProps.rowData.processID  ?
                                        <span className="align-center d-inline-block align-middle">
                                                    <i className="icon ico-clip mar-r-2xs"></i>(1)   
                                        </span> : 
                                            <span className="align-center d-inline-block align-middle">
                                                <a href="#" className="d-flex" onClick={() => this.addAttachment(cellProps.rowData)} >
                                                    <i className="icon ico-clip mar-r-2xs"></i> 
                                                </a>
                                        </span>
                                        }
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>                                     
                    <IColumn
                            dataKey=""
                            label=""
                            flexGrow={0}
                            id="menu"
                            width={40}
                            hideOnExport
                            className="last-column justify-content-end"
                            >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            { 
                                                <IDropdown className="d-flex justify-content-end">
                                                    <IDropdownToggle className="dropdown-toggle no-border">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu right={true} >
                                                        <IDropdownItem data-mode="EDIT_BATCH"  componentId={ComponentId.CMP_EDT} data-index={cellProps.rowIndex} onClick={() => this.editBatch(cellProps.rowData)} ><IMessage msgkey={Key.EDT_BTH}  /></IDropdownItem>
                                                        <IDropdownItem data-mode="DEL_BATCH"  componentId={ComponentId.CMP_DEL} data-index={cellProps.rowIndex} onClick={() => this.deleteBatch(cellProps.rowData)} ><IMessage msgkey={Key.DLT_BTH}  /></IDropdownItem>
                                                        <IDropdownItem data-mode="DEL_ATCH"  componentId={ComponentId.CMP_DEL_ATH} data-index={cellProps.rowIndex} onClick={() => this.deleteAttachment(cellProps.rowData)} ><IMessage msgkey={Key.DLT_ATH}  /></IDropdownItem>
                                                    </IDropdownMenu>
                                                </IDropdown>
                                            }

                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>                                                                                                                                
                </Columns>
            </ITable>
            </Aux>

        );

    }
}

PaymentBatchDetailTable.propTypes = {
    addAttachment: PropTypes.func,
    deleteBatch: PropTypes.func,
    deleteAttachment : PropTypes.func
};