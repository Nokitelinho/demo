import React, { Component,Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content,DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
import CarditDsnTableFilter from './CarditDsnTableFilter.jsx'
import { Row, Col } from "reactstrap";
import { Constants } from '../../constants/constants.js'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { isSubGroupEnabled } from 'icoreact/lib/ico/framework/component/common/orchestration';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'


export default class DsnDetailsTable extends Component {
          
        constructor(props){
            super(props);
            this.state={
                
            }
        }

        getFlightNumber=(rowData)=>{
            let flight='';
            if(rowData.carrierCode&&rowData.flightNumber&&rowData.flightDate)
                flight=rowData.carrierCode + rowData.flightNumber + ' ' + rowData.flightDate;
            return flight;
        }

        getAwbNumber=(rowData)=>{
            let awb= '';
            if(rowData.shipmentPrefix && rowData.masterDocNumber)
                awb= rowData.shipmentPrefix + '-' + rowData.masterDocNumber;
            return awb;
        }

        getAcceptanceStatus=(rowData)=>{
            return rowData.acceptanceStatus===Constants.ACP?
                    Constants.ACCEPTED:Constants.CAP_NOT_ACP;
        }

        sortList = (sortBy, sortByItem) => {
            this.props.updateSortVariables(sortBy, sortByItem);
        }

        onRowSelection = (data) =>{
            if(data&&data.index!='-1'){
                const selectedIndexes=data.selectedIndexes;
                this.props.saveSelectedIndexes(selectedIndexes);
            }
        }

        onRowOperation = (event) =>{
            const index=event.target.dataset.index;
            const operation=event.target.dataset.mode;
            if(operation!=null){
                this.props.onRowOperation({index,operation})
            }
        }

        displayHeader= () =>{
            return <Row className="align-items-center">
                <Col xs="auto">
                    <h4>{Constants.TABLE_HEADER}</h4>
                </Col>
                {this.props.selectedIndexes&&this.props.selectedIndexes.length>1&&
                    <Col className="text-right">
                        <IButton category="default" onClick={this.props.attachAll} >{Constants.ATTACH_AWB}</IButton>
                        <IButton category="default" onClick={this.props.detachAll}>{Constants.DETACH_AWB}</IButton>
                    </Col>
                }
            </Row> 
        }

        render() {
            
            const results=(this.props.dsnDetails&&this.props.dsnDetails.results)?
                                this.props.dsnDetails.results:[];

        return (
            <Fragment>
                <IMultiGrid
                    rowCount={results.length}
                    headerHeight={45}
                    className="table-list"
                    rowHeight={38}
                    rowClassName="table-row"
                    tableId="dsnTable"
                    sortEnabled={false}
                    form={true}
                    onRowSelection={this.onRowSelection}
                    customHeader={{
                                headerClass: '',
                                customPanel: this.displayHeader(),
                                dataConfig: {
                                        screenId: '',
                                        tableId: 'dsnTable'
                                },
                                "pagination":{"page":this.props.dsnDetails,getPage:this.props.getNewPage},
                                filterConfig: {
                                    panel: <CarditDsnTableFilter  oneTimeValues={this.props.oneTimeValues} 
                                            initialValues={{...this.props.dsnFilterValues,flightnumber:this.props.dsnFilterValues?
                                                {carrierCode:this.props.dsnFilterValues.carrierCode,flightNumber:this.props.dsnFilterValues.flightNumber,
                                                    flightDate:this.props.dsnFilterValues.flightDate}:null}} />,
                                    title: 'Filter',
                                    onApplyFilter: this.props.onApplyDsnFilter,
                                    onClearFilter: this.props.onClearDsnFilter
                                },
                                exportData: {
                                    exportAction: this.props.exportToExcel,
                                    pageable: true,
                                    addlColumns:[{label:'Flight',excelData:this.getFlightNumber},
                                                    {label:'AWB Number',excelData:this.getAwbNumber},
                                                    {label:'Acceptance Status',excelData:this.getAcceptanceStatus}],
                                    name: 'Cardit DSN List'
                                },
                                sortBy: {
                                    onSort: this.sortList
                                },
                
                    }}
                    additionalData={{}}
                    data={results}
                >
                    <Columns>
                        <IColumn
                            width={32}
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
                            dataKey="dsn"
                            label="DSN"
                            flexGrow={0}
                            id="dsn"
                            width={80}
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
                            dataKey="paCode"
                            label="PA Code"
                            flexGrow={0}
                            id="paCode"
                            width={70}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="bags"
                            label="No. of Mailbags"
                            flexGrow={0}
                            id="bags"
                            width={110}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="originExchangeOffice"
                            label="Origin OE"
                            flexGrow={0}
                            id="originExchangeOffice"
                            width={80}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="destinationExchangeOffice"
                            label="Dest DE"
                            width={80}
                            flexGrow={0}
                            selectColumn={true}
                            sortByItem={true}
                            id="destinationExchangeOffice">
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
                            dataKey="mailCategoryCode"
                            label="Category"
                            width={70}
                            flexGrow={0}
                            id="mailCategoryCode"
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="mailSubclass"
                            label="Sub Class"
                            width={80}
                            flexGrow={0}
                            id="mailSubclass"
                            selectColumn={true}
                            sortByItem={true}>
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
                            label="Yr"
                            width={50}
                            flexGrow={0}
                            id="year"
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="flightNumber"
                            label="Flight Number"
                            width={120}
                            flexGrow={0}
                            hideOnExport
                            id="flightNumber"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>Flight Number</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                        <Col>
                                            <div> {cellProps.rowData.carrierCode}{cellProps.rowData.carrierCode ? '-' :''}
                                                {cellProps.cellData}
                                            </div>
                                            <div><DataDisplay index={cellProps.rowIndex} id='flightDate' label='Flight Date' sortByItem={true}>
                                                <span class="text-light-grey">{cellProps.rowData.flightDate} </span>
                                            </DataDisplay></div>
                                               
                                        </Col>
                                    </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="weight"
                            label="Weight"
                            width={100}
                            flexGrow={0}
                            id="weight"
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="masterDocumentNumber"
                            label="AWB Number"
                            width={120}
                            flexGrow={0}
                            hideOnExport
                            id="masterDocumentNumber"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.rowData.shipmentPrefix}{cellProps.rowData.shipmentPrefix?'-':''}
                                        { cellProps.cellData?cellProps.cellData:'--' }
                                </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="csgDocNum"
                            label="Consignment Number"
                            width={160}
                            flexGrow={0}
                            id="csgDocNum"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData?cellProps.cellData:'--'}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        
                        <IColumn
                            dataKey="consignmentDate"
                            label="Consignment Date"
                            width={130}
                            flexGrow={0}
                            id="consignmentDate"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData?cellProps.cellData:'--'}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="requiredDeliveryTime"
                            label="RDT"
                            width={120}
                            flexGrow={0}
                            id="requiredDeliveryTime"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                        <Col>
                                            <div>{cellProps.rowData.requiredDeliveryTime?cellProps.rowData.requiredDeliveryTime:'--'} </div>
                                        </Col>
                                    </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="uldNumber"
                            label="Container"
                            width={110}
                            flexGrow={0}
                            id="uldNumber"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData?cellProps.cellData:'--'}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="acceptanceStatus"
                            label="Status"
                            width={150}
                            flexGrow={1}
                            hideOnExport
                            id="acceptanceStatus"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            <DataDisplay index={cellProps.rowIndex} id='acceptanceStatus' label='Acceptance status' sortByItem={true}>
                                            {cellProps.cellData === Constants.ACP &&
                                                <span className="badge badge-pill light badge-active">{Constants.ACCPTD}</span>
                                            }
                                            {cellProps.cellData === Constants.CAP &&
                                                <span className="badge badge-pill light badge-alert">{Constants.NOT_ACPTD}</span>
                                            }
                                            </DataDisplay>        
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn width={38} flexGrow={1} className="float-right">
                                    < Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content> </Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    { 
                                                    // <span onClick={this.gridUtitity}>
                                                    <IDropdown portal={true} >
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                            <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                        </IDropdownToggle>
                                                        <IDropdownMenu portal={true} right={true} >
                                                            {isSubGroupEnabled(Constants.KE_SPECIFIC) &&<IDropdownItem data-index={cellProps.rowIndex} data-mode={Constants.ATTACH} onClick={this.onRowOperation} >{Constants.ATTACH}</IDropdownItem>}
                                                            {isSubGroupEnabled(Constants.KE_SPECIFIC) &&<IDropdownItem data-index={cellProps.rowIndex} data-mode={Constants.DETACH} onClick={this.onRowOperation}>{Constants.DETACH}</IDropdownItem>}
                                                        </IDropdownMenu>
                                                    </IDropdown>
                                                
                                                    }
                                                
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                        
                    
                    </Columns>
                </IMultiGrid>
            </Fragment>
        );
    
    }
}
