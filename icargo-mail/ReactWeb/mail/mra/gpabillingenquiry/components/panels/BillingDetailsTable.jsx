import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import TableHeaderPanel from './TableHeaderPanel.jsx';
import MailbagTableCustomRowPanel from './MailbagTableCustomRowPanel.jsx';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge';
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid';
import { Row, Col } from "reactstrap";
import MailbagNoDataPanel from './MailbagNoDataPanel.jsx';

let Aux = (props)=>  props.children
export default class BillingDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = []
        this.state = {
            openPopover: false
        }

    } 

    onRowSelection = (data) => {
         if (data.index > -1) {
             if (data.isRowSelected) {             
                 this.selectMailbag(data.index);
             } else {
                   this.unSelectMailbag(data.index);
             }
         }
        else {
              if ((data.event) && (data.event.target.checked)) {
                 this.selectAllMailbags(-1);
             } else {
                  this.unSelectAllMailbags();
              }
          }
     }

     selectMailbag = (mailbag) => {
         this.selectedMailbags.push(mailbag);
         this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
     }
     unSelectMailbag = (mailbag) => {
         let index = -1;
         for (let i = 0; i < this.selectedMailbags.length; i++) {
             var element = this.selectedMailbags[i];
             if (element === mailbag) {
                 index = i;
                 break;
             }
         }
         if (index > -1) {
            this.selectedMailbags.splice(index, 1);
        }
         this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
     }   
    selectAllMailbags = () => {
        const results = this.props.mailbagsdetails ? this.props.mailbagsdetails.results : '';
        const length = results?results.length:'';

        for (let i = 0; i < length; i++) {
            this.selectedMailbags.push(i);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }

    unSelectAllMailbags = () => {
        this.selectedMailbags = []
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }    
    allMailbagIconAction=(values)=>{
        this.props.allMailbagIconAction(values)
    }       
     sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
    openCompareRow =() =>{
        this.props.openCompareRow();
    }
    getExpandIcon=() => (
    <div className="table-header__item">
              <i class="icon ico-maximize" onClick={this.openCompareRow}></i>
            
    </div>
    )   
    render() {

        const results = this.props.mailbagList ? this.props.mailbagList : '';
        const rowCount = results.length;   
        const containerRatingPAList = this.props.containerRatingPAList?this.props.containerRatingPAList:'';

        return (
		   results.length > 0 ?
           <Aux>
               <div className="card-body p-0 flex-column-custom">
                <div className="flex-column-custom billing-table">
                <ITable
                    rowCount={rowCount}
                    headerHeight={45}
                    className="table-list"
                    rowHeight={40}
                    rowClassName="table-row"
                    tableId="mailbagtable"
                    sortEnabled={false}
                    form={true}
                    name='newMailbagsTable'
                    onRowSelection={this.onRowSelection}
                    resetSelectionOnDataChange={true}
                    customHeader={{
                        headerClass: '',
                        placement: 'nondynamic',
                    customPanel: <TableHeaderPanel selectedMailbagIndex={this.props.selectedMailbagIndex}
                    allMailbagIconAction={this.allMailbagIconAction}/>,
                    "pagination": { "page": this.props.mailbagsdetails, getPage: this.props.getNewPage,
                    options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ]  },
                    pageable: true,
                    customIcons:this.getExpandIcon(),
                    exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            addlColumns:[],
                            name: 'Billing List'
                        },
                    sortBy: {
                            onSort: this.sortList}                                            
                    }}
                    data={results}
                >
                    <Columns customRow={MailbagTableCustomRowPanel} customRowDataKey='mailbagID' >
                        <IColumn
                            width={15}
                            dataKey=""
                            flexGrow={0.2}
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
                            width={5}
                            flexGrow={.5}
                            hideOnExport>
                            <Cell>
                                <HeadCell disableSort rowToggler>
                                </HeadCell>
                                <RowCell rowToggler>
                                </RowCell>
                            </Cell>
                        </IColumn>                                      
                        <IColumn
                            dataKey="mailbagID"
                            label="Mailbag ID"
                            flexGrow={3}
                            id="mailbagID"
                            width={170}
                            sortByItem={true}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => {
                                    if (cellProps.rowData.mailType === 'I') {
                                        return <Content>
                                        {cellProps.rowData.orgOfficeOfExchange} {cellProps.rowData.destOfficeOfExchange} {cellProps.rowData.category} {cellProps.rowData.subClass} {cellProps.rowData.year}
                                        <mark>{cellProps.rowData.dsn} {cellProps.rowData.rsn} {cellProps.rowData.hni}</mark> {cellProps.rowData.regInd} {cellProps.rowData.mailWeight}
                                        </Content>
                                    }
                                    else
                                        return <Content>{cellProps.cellData}</Content>

                                }}                                
                            </RowCell>
                        </Cell>
                        </IColumn>    
                    <IColumn
                        dataKey="mcaIndicator"
                        label="MCA Ind"
                        width={30}
                        flexGrow={.5}
                        id="mcaIndicator"
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
                                    <div className="d-flex justify-content-end">
                                        {
                                            cellProps.rowData.mcaIndicator === 'MCA' &&
                                            <span className="badge light badge-info pad-x-3xs mar-l-3xs">{cellProps.cellData}</span>
                                        }
                                    </div>
                                </Content>
                            )
                            }                          
                        </RowCell>
                    </Cell>
                    </IColumn>                                            
                     <IColumn
                            dataKey="ratedSector"
                            label="Rated Sector"
                            flexGrow={1}
                            id="ratedSector"
                            width={40}
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
                            dataKey="gpaCode"
                            label="GPA Code"
                            width={30}
                            flexGrow={1}
                            id="gpaCode"
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
                            dataKey="wgt"
                            label="Weight"
                            width={35}
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
                                    <Content>
                                        <span className={cellProps.rowData.paBuilt!=null && containerRatingPAList!=null && cellProps.rowData.paBuilt=='Yes'
                                        && (containerRatingPAList.lastIndexOf(cellProps.rowData.gpaCode)!=-1 ||  containerRatingPAList=='ALL') ?'text-red':'text-black'}>
                                        {cellProps.rowData.weight} {cellProps.rowData.wgtunitcod}
                                        </span>
                                        </Content>)
                                }
                            </RowCell>
                        </Cell>
                        </IColumn> 
                        <IColumn
                            dataKey="currency"
                            label="Curr"
                            width={30}
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
                            dataKey="applicableRate"
                            label="Rate"
                            width={50}
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
                                    <Content>
                                        <span className={cellProps.rowData.paBuilt!=null && containerRatingPAList!=null  && cellProps.rowData.paBuilt=='Yes'
                                        && (containerRatingPAList.lastIndexOf(cellProps.rowData.gpaCode)!=-1 ||  containerRatingPAList=='ALL')?'text-red':'text-black'}>
                                       {cellProps.cellData}
                                        </span>
                                        </Content>)
                                }
                            </RowCell>
                        </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="grossAmount"
                            label="Amt"
                            width={50}
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
                            dataKey="serviceTax"
                            label="S.Tax"
                            width={45}
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
                            dataKey="netAmount"
                            label="Net Amount"
                            width={40}
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
                            dataKey="billingStatus"
                            label="Billing Status"
                            width={50}
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
                                                <span className="badge light badge-active pad-x-3xs spacer pre-wrap">{cellProps.cellData}</span>
                                            }
                                            {
                                                (cellProps.cellData === 'WITHDRAWN' || cellProps.cellData === 'ON HOLD' || cellProps.cellData === 'TO BE RE-RATED') &&
                                                <span class="badge light badge-info pad-x-3xs pre-wrap">{cellProps.cellData}</span>
                                            }
                                            {
                                                (cellProps.cellData === 'BILLABLE' || cellProps.cellData === 'TO BE REPORTED') &&
                                                <span class="badge light badge-alert pad-x-3xs pre-wrap">{cellProps.cellData}</span>
                                            }                                           
                                        </div>
                                    </Content>
                                )
                                }                                
                            </RowCell>
                        </Cell>
                        </IColumn>                                                                     
                    </Columns>
                </ITable>
                 </div> 
            </div> 
            </Aux>  
			:
             <div className="card-body p-0 flex-column-custom">
                 <div className="flex-column-custom billing-table">
                     <div>
                         <Row>
                             <Col><h4>Billing Entries</h4></Col>
                         </Row>
                     </div>
                     < MailbagNoDataPanel />
                 </div>
             </div> 			
        );   

    }
}
