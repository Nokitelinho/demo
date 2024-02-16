import React, { Component } from 'react';
import { Row, Col } from 'reactstrap';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import PropTypes from 'prop-types';
import {IMoney} from 'icoreact/lib/ico/framework/component/business/money';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import InvoiceTabPanel from './InvoiceTabPanel.jsx'
export class InvoiceTablePanel extends Component {

    constructor(props) {
        super(props);
        this.invoiceDetails = [];
        this.selectedInvoice = this.props.selectedInvoice;
    }
    
    onRowSelection = (data) => {
        if (data.index > -1) {
            if (data.isRowSelected) {
                this.selectInvoice(data.rowData);
            } else {
                this.unSelectInvoice(data.rowData);
            }
        } else {
            if (data.event) {
                if (data.event && data.event.target.checked) {
                    this.selectAllInvoices(this.props.invoiceDetails);
                } else {
                    this.unSelectAllInvoices();
                }
            }
        }
    }
    selectInvoice=(invoice)=> {
        let index = -1;
        for (let i = 0; i < this.selectedInvoice.length; i++) {
            var element = this.selectedInvoice[i];
            if (element.invoiceNumber === invoice.invoiceNumber) {
                index = i;
                break;
            }
        }
          if (index === -1) {
           this.selectedInvoice.push(invoice);

        }
        this.props.selectInvoice(this.selectedInvoice);
        
    }
    unSelectInvoice=(invoice)=> {
        let index = -1;
        for (let i = 0; i < this.selectedInvoice.length; i++) {
            var element = this.selectedInvoice[i];
            if (element.invoiceNumber === invoice.invoiceNumber) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedInvoice.splice(index, 1);
        }
        this.props.selectInvoice(this.selectedInvoice);
    }
    selectAllInvoices=(invoice)=> {
        this.selectedInvoice = invoice
        this.props.selectInvoice(this.selectedInvoice);
    }

    unSelectAllInvoices=()=> {
        this.selectedInvoice = []
        this.props.selectInvoice(this.selectedInvoice);
    }
    getInvoiceDate=(rowData)=>{
        return rowData.invoiceDate;
    }
    getBillingPeriod=(rowData)=>{
        return rowData.periodFromDate+"-"+rowData.periodToDate;
    }
    getStatus=(rowData)=>{
        let status;
        (rowData.status==='D')? status= 'Difference':status='Finalized';
        return status;
    }
    getCurrency=(rowData)=>{
        return this.props.currency;
    }
    render() {
        const invoiceDetails = this.props.invoiceDetails?this.props.invoiceDetails:[];
        const rowcount= invoiceDetails.length;
        const invoiceDetailsPage=this.props.invoiceDetailsPage;
        const currency=this.props.currency;
        this.selectedInvoice=this.props.selectedInvoice;
            return (
            <div className="animated fadeInDown inner-panel">
                <div className="d-flex inner-panel" >
                        <ITable
                            rowCount={rowcount}
                            headerHeight={35}
                            className="table-list"
                            gridClassName="table_grid"
                            rowHeight={60}
                            sortEnabled={false}
                            data={invoiceDetails}
                            tableId='invoicelisttable'
                            additionalData={this.props.currency}
                            onRowSelection={this.onRowSelection}
                            rowClassName= 'table-row'
                            customHeader={{
                                customPanel: <CustomPanel statusCount={this.props.statusCount} invoiceFilter={this.props.invoiceFilter} filterInvoiceDetails={this.props.filterInvoiceDetails} />,
                                pagination: {
                                    page: invoiceDetailsPage,
                                    getPage: this.props.getNewPage,
                                    mode: 'multi' //'subminimal
                                },
                                exportData: {
                                    exportAction: this.props.exportToExcel,
                                    pageable: true,
                                    name: 'Invoice_Details',
                                    addlColumns:[{label:'Invoice Date',excelData:this.getInvoiceDate},
                                                  {label:'Billing Period', excelData:this.getBillingPeriod},
                                                  {label:'Status',excelData:this.getStatus},
                                                  {label:'Currency',excelData:this.getCurrency}]
                                }   
                            }}
                            noRowsRenderer={() => (<div><IMessage msgkey="customermanagement.defaults.customerconsole.noresult"/></div>)}>
                            <Columns>
                                <IColumn
                                    width={15}
                                    hideOnExport
                                    dataKey=""
                                    flexGrow={0.2}
                                    className="" >
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                        </HeadCell>
                                        <RowCell selectOption>
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Invoice Details"
                                    dataKey="invoiceNumber"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="mon-flight-detals">
                                                        <label className="block">
                                                            <strong>{cellProps.rowData.invoiceNumber}</strong>
                                                        </label>
                                                        <span className="pl-0">{cellProps.rowData.invoiceDate}</span>
                                                        <div className="dot-indicators d-flex">
                                                            <span className="pl-0">{cellProps.rowData.status === 'F' ? <i className="red m-0"></i> : <i className="orange m-0"></i>}</span>
                                                        </div>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Period"
                                    dataKey=""
                                    hideOnExport
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="text-center">
                                                        {cellProps.label}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="text-center">
                                                        <div className="date-range-display">{cellProps.rowData.periodFromDate}</div>
                                                        <div className="mar-t-2md">{cellProps.rowData.periodToDate}</div>
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Original Billed"
                                    dataKey="orgBilledAmount"
                                    width={10}
                                    flexGrow={1}>
                                   
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label + "(" + currency + ")"}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="money-reset">
                                                        <IMoney value={cellProps.rowData.orgBilledAmount} mode="display" placement="left"/>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Adj. Billed"
                                    dataKey="adjBilledAmount"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label + "(" + currency + ")"}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="money-reset">
                                                        <IMoney value={cellProps.rowData.adjBilledAmount} mode="display" placement="left"/>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Net Billed"
                                    dataKey="netBilledAmount"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label + "(" + currency + ")"}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="money-reset">
                                                        <IMoney value={cellProps.rowData.netBilledAmount} mode="display" placement="left"/>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Paid"
                                    dataKey="paidAmount"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                         {cellProps.label + "(" + currency + ")"}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="money-reset">
                                                        <IMoney value={cellProps.rowData.paidAmount} mode="display" placement="left"/>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Outstanding"
                                    dataKey="outstandingAmount"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label + "(" + currency + ")"}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="money-reset">
                                                        <IMoney value={cellProps.rowData.outstandingAmount} mode="display" placement="left"/>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Age/ Last Act."
                                    dataKey="age"
                                    width={10}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="">
                                                        {cellProps.label}
                                                    </div>
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div>
                                                        <div className="">
                                                            {cellProps.rowData.age < 31 ?
                                                                <span className="badge badge-pill high-light badge-active">{cellProps.rowData.age}</span> :
                                                                cellProps.rowData.age < 61 ?
                                                                    <span className="badge badge-pill high-light badge-alert">{cellProps.rowData.age}</span> :
                                                                    <span className="badge badge-pill high-light badge-error">{cellProps.rowData.age}</span>}
                                                        </div>
                                                        <span className="d-flex pad-t-2xs">{cellProps.rowData.lastActedDate}</span>
                                                    </div></Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                            </Columns>
                        </ITable>
                   
                </div>
            </div>
        )
    }
}
InvoiceTablePanel.propTypes = {
 //   selectedInvoice: PropTypes.array,
    invoiceDetails: PropTypes.array,
    currency: PropTypes.string,
    selectInvoice: PropTypes.func,
    statusCount: PropTypes.object,
    invoiceFilter: PropTypes.string,
    filterInvoiceDetails: PropTypes.func,
    invoiceDetailsPage:PropTypes.object,
    exportToExcel:PropTypes.func,
    invoiceDisplayMode: PropTypes.string
};
export default InvoiceTablePanel;
class CustomPanel extends Component{
 constructor(props) {
        super(props);
 }
    render(){
        return (
                <div className="tabs ui-tabs" style={{ display: "block" }}>
                    <InvoiceTabPanel statusCount={this.props.statusCount} invoiceFilter={this.props.invoiceFilter} filterInvoiceDetails={this.props.filterInvoiceDetails} />
                </div>
            )
    }
} 