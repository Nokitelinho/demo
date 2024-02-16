import React, { Component } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton, IMessage, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import PropTypes from 'prop-types';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';

export class BillingDetailsTablePanel extends Component {
    constructor(props) {
        super(props)
        this.selectedMailbags = []
        this.state = {
            openPopover: false
        }        

    }
 
    render() {
        const results = this.props.mailbagsdetailslist ? this.props.mailbagsdetailslist.results : '';
        const rowCount = results?results.length:'';   

        return (
            <div> 
                <PopupBody >
                    <div className="border-top scroll-y scroll-x d-flex" style={{"height":"400px"}}>
                        <IMultiGrid
                            rowCount={rowCount}
                            headerHeight={45}
                            className="table-list"
                            rowHeight={40}
                            rowClassName="table-row"
                            tableId="mailbagtable"
                            sortEnabled={false}
                            form={true}
                            name='newMailbagsTable'
                            customHeader={{
                            "pagination": { "page": this.props.mailbagsdetailslist, getPage: this.props.listmailbagdetails }                                            
                            }}
                            data={results}
                        >  
                                <Columns >
                      
                                    <IColumn
                                        dataKey="gpaCode"
                                        label="GPA Code"
                                        flexGrow={3}
                                        id="gpaCode"
                                        width={70}
                                    
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
                                        dataKey="mailbagID"
                                        label="Mailbag ID"
                                        flexGrow={3}
                                        id="mailbagID"
                                        width={250}
                                        sortByItem={true}
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
                                        dataKey="csgDocumentNumber"
                                        label="Cons.Doc.No"
                                        flexGrow={3}
                                        id="csgDocumentNumber"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="invoiceNumber"
                                        label="Inv No"
                                        flexGrow={3}
                                        id="invoiceNumber"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="ccaRefNumber"
                                        label="MCA No"
                                        flexGrow={3}
                                        id="ccaRefNumber"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="orgOfficeOfExchange"
                                        label="Org OE:"
                                        flexGrow={3}
                                        id="orgOfficeOfExchange"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="destOfficeOfExchange"
                                        label="Dst OE:"
                                        flexGrow={3}
                                        id="destOfficeOfExchange"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="category"
                                        label="Category"
                                        flexGrow={3}
                                        id="category"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="subClass"
                                        label="Class"
                                        flexGrow={3}
                                        id="subClass"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="year"
                                        label="Year"
                                        flexGrow={3}
                                        id="year"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="dsn"
                                        label="DSN"
                                        flexGrow={3}
                                        id="dsn"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="rsn"
                                        label="RSN"
                                        flexGrow={3}
                                        id="rsn"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="hni"
                                        label="HNI"
                                        flexGrow={3}
                                        id="hni"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="regInd"
                                        label="RI"
                                        flexGrow={3}
                                        id="regInd"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="weight"
                                        label="Weight"
                                        width={100}
                                        flexGrow={1}
                                        id="weight"
                                        sortByItem={true}
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
                                        label="Curr"
                                        width={100}
                                        flexGrow={1}
                                        id="currency"
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
                                        dataKey="declaredValue"
                                        label="Declared Value"
                                        width={100}
                                        flexGrow={1}
                                        id="declaredValue"
                                        sortByItem={true}
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
                                        dataKey="ratedSector"
                                        label="Rated Sector"
                                        flexGrow={1}
                                        id="ratedSector"
                                        width={100}
                                        sortByItem={true}
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
                                        dataKey="applicableRate"
                                        label="Rate"
                                        width={100}
                                        flexGrow={1}
                                        id="applicableRate"
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
                                        dataKey="grossAmount"
                                        label="Amt"
                                        width={100}
                                        flexGrow={1}
                                        id="grossAmount"
                                        sortByItem={true}
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
                                        dataKey="surChg"
                                        label="Surcharge"
                                        width={100}
                                        flexGrow={1}
                                        id="surChg"
                                        sortByItem={true}
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
                                        dataKey="serviceTax"
                                        label="S.Tax"
                                        width={100}
                                        flexGrow={1}
                                        id="serviceTax"
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
                                        dataKey="valCharges"
                                        label="Val Chgs"
                                        width={100}
                                        flexGrow={1}
                                        id="valCharges"
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
                                        dataKey="amount"
                                        label="Gross Amt"
                                        width={100}
                                        flexGrow={1}
                                        id="amount"
                                        sortByItem={true}
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
                                        dataKey="netAmount"
                                        label="Net Amt"
                                        width={100}
                                        flexGrow={1}
                                        id="netAmount"
                                        sortByItem={true}
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
                                        dataKey="rateType"
                                        label="UPU/Contract"
                                        width={100}
                                        flexGrow={1}
                                        id="rateType"
                                        sortByItem={true}
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
                                        dataKey="rateType"
                                        label="RateCard/RateLine"
                                        width={150}
                                        flexGrow={1}
                                        id="rateType"
                                        sortByItem={true}
                                    >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>

                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.rateIdentifier}/{cellProps.rowData.rateLineIdentifier}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                    </IColumn>      
                                    <IColumn
                                        dataKey="isUSPSPerformed"
                                        label="USPS Perf Met"
                                        width={100}
                                        flexGrow={1}
                                        id="isUSPSPerformed"
                                        sortByItem={true}
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
                                        dataKey="billingStatus"
                                        label=""
                                        width={100}
                                        flexGrow={1}
                                        id="billingStatus"
                                        sortByItem={true}
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
                                                    <div className="d-inline">
                                                        {
                                                            (cellProps.cellData === 'BILLED' || cellProps.cellData === 'REPORTED' || cellProps.cellData === 'PROFORMA BILLED' ) &&
                                                            <span className="badge light badge-active pad-y-3xs mar-l-xs">{cellProps.cellData}</span>
                                                        }
                                                        {
                                                            (cellProps.cellData === 'WITHDRAWN' || cellProps.cellData === 'ON HOLD' || cellProps.cellData === 'TO BE RE-RATED') &&
                                                            <span class="badge light badge-info pad-y-3xs mar-l-xs">{cellProps.cellData}</span>
                                                        }
                                                        {
                                                            (cellProps.cellData === 'BILLABLE' || cellProps.cellData === 'TO BE REPORTED') &&
                                                            <span class="badge light badge-alert pad-y-3xs mar-l-xs">{cellProps.cellData}</span>
                                                        }                                           
                                                    </div>
                                                </Content>
                                            )
                                            }                                
                                 </RowCell>
                                </Cell>
                                </IColumn>                                                                     
                            </Columns>
                        </IMultiGrid>                                              
                    </div>
                </PopupBody >
            </div>
        );
    }
}

 BillingDetailsTablePanel.propTypes = {
   closeBillingPopup: PropTypes.func
 }

export default icpopup(wrapForm('billingEnteriesForm')(BillingDetailsTablePanel), { title: 'Billing Entries', className:'modal_90w'});