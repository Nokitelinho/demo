import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import MailbagTableFilter from './MailbagTableFilter.jsx';
import ServiceFailuresTabPanel from './tabpanels/ServiceFailuresTabPanel.jsx';
import StpTabPanel from './tabpanels/StpTabPanel.jsx';
import ForceMajeureTabPanel from './tabpanels/ForceMajeureTabPanel.jsx';

export default class MailbagDetailsTable extends Component {
    constructor(props) {
        super(props);
     
    }

    openHistoryPopup= (id) => {
        let index = id;
        let data ={index:index}
        this.props.openHistoryPopup(data);
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }

    render() {

        const results = this.props.mailbagsInTable ? this.props.mailbagsInTable : '';
        const rowCount = results ? results.length : '';

        return (
            
                <ITable
                    rowCount={rowCount}
                    headerHeight={45}
                    className="table-list"
                    rowHeight={50}
                    rowClassName="table-row"
                    tableId="mailbagtable"
                    sortEnabled={false}         
                    key={this.props.activeTab}        
                    name='newMailbagsTable'
                 // onRowSelection={this.onRowSelection}
                    data={results}
                    customHeader={{

                        headerClass: '',

                        customPanel:
                        <div>
                            {
                                this.props.activeGraph === 'ServiceFailuresGraph' &&
                                <ServiceFailuresTabPanel activeTab={this.props.activeTab} changeTab={this.props.changeTab} currentTableFilter={this.props.currentTableFilter} />
                            }
                            {
                                this.props.activeGraph === 'StpGraph' &&
                                <StpTabPanel activeTab={this.props.activeTab} changeTab={this.props.changeTab} currentTableFilter={this.props.currentTableFilter} />
                            }
                            {
                                this.props.activeGraph === 'ForceMajeureGraph' &&
                                <ForceMajeureTabPanel activeTab={this.props.activeTab} changeTab={this.props.changeTab} currentTableFilter={this.props.currentTableFilter} />
                            }
                        </div>
                        ,

                        placement: 'dynamic',

                        dataConfig: {
                            screenId: '',
                            tableId: 'mailbagtable'
                        },

                         "pagination":{"page":this.props.paginatedMailbags,getPage:this.props.getNewPage},

                        filterConfig: {
                            panel: <MailbagTableFilter oneTimeValues={this.props.oneTimeValues} tableFilter={this.props.tableFilter} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyTableFilter,
                            onClearFilter: this.props.onClearTableFilter
                        },

                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            name: 'Mailbag List'
                        },

                        sortBy: {
                            onSort: this.sortList,
                           selectedSort:this.props.sort.sortBy,
                           sortOrder: this.props.sort.sortByItem
                        }

                    }}
                    
                >
                    <Columns>
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
                            dataKey="mailbagId"
                            label="Mailbag ID"
                            flexGrow={3}
                            id="mailbagId"
                            width={110}
                             selectColumn={true}
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
                            dataKey="servicelevel"
                            label="Service Level"
                            flexGrow={1}
                            id="servicelevel"
                            width={60}
                             selectColumn={true}
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
                                        <Content>{
                                            
                                               this.props.oneTimeValues['mail.operations.mailservicelevels'].find((element) => {
                                                   return element.fieldValue==cellProps.rowData.servicelevel
                                                   })? this.props.oneTimeValues['mail.operations.mailservicelevels'].find((element) => {
                                                   return element.fieldValue==cellProps.rowData.servicelevel
                                                   }).fieldDescription:''
                                            
                                            
                                            }</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailorigin"
                            label="Org Airport"
                            width={20}
                            flexGrow={1}
                            id="mailorigin"
                             selectColumn={true}
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
                            dataKey="mailDestination"
                            label="Dest Airport"
                            width={35}
                            flexGrow={1}
                            id="mailDestination"
                             selectColumn={true}
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
                            dataKey="reqDeliveryDateAndTime"
                            label="RDT"
                            width={60}
                            flexGrow={1}
                            id="reqDeliveryDateAndTime" 
                             selectColumn={true}
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
                            dataKey="scannedDate"
                            label="Actual Time of Delivery"
                            width={60}
                            flexGrow={1}
                            id="actDeliveryDateAndTime"
                             selectColumn={true}
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
                                       <Content>{cellProps.rowData.scannedDate}{' '}{cellProps.rowData.scannedTime}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="scannedPort"
                            label="Current Airport"
                            width={20}
                            flexGrow={1}
                            id="scannedPort"
                             selectColumn={true}
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
                            dataKey="latestStatus"
                            label="Latest Transaction"
                            width={40}
                            flexGrow={1}
                            id="latestStatus"
                             selectColumn={true}
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
                                        <Content>{
                                            this.props.oneTimeValues['mailtracking.defaults.mailstatus'].find((element) => {
                                                return element.fieldValue==cellProps.rowData.latestStatus
                                                })?this.props.oneTimeValues['mailtracking.defaults.mailstatus'].find((element) => {
                                                return element.fieldValue==cellProps.rowData.latestStatus
                                                }).fieldDescription:''
                                          
                                            
                                            }</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="onTimeDelivery"
                            label="On time Delivery"
                            width={40}
                            flexGrow={1}
                            id="onTimeDelivery"
                             selectColumn={true}
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
                            dataKey="consignmentNumber"
                            label="Consignment Number"
                            width={100}
                            flexGrow={1}
                            id="consignmentNumber"
                             selectColumn={true}
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
                                        <Content>{cellProps.cellData}
                                             <a href="#" className="pcp-icon"  onClick={() => this.openHistoryPopup(cellProps.rowIndex)}><span className="badge badge-pill light badge-info mar-x-2xs" > 
                                            
                                                History
                                              
                                                </span></a> 
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
