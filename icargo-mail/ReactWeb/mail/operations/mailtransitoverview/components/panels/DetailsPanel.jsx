import React, { Component, Fragment } from 'react';

import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'

import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import DetailsTableTableCustomRowContainer from '../containers/DetailsTableTableCustomRowContainer.js'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import MailTransitTableFilter from './MailTransitTableFilter.jsx';

class DetailsPanel extends Component {
    constructor(props) {
        super(props);
    }

    displayHeader = () => {

        return <h4 class="fs16 font-weight-bold">Mail Bag List</h4>
        
        }
        sortList = (sortBy, sortByItem) => {
            this.props.updateSortVariables(sortBy, sortByItem);
          //  this.saveActualWeight = this.saveActualWeight.bind(this);
    
        }
    render() {
        //  const tableFilterobj = this.props.tableFilter;
          const results = this.props.mailTransitDetails; 
        const rowCount = (results) ? results.length : 0;
        if (rowCount != 0) {
            for (let i = 0; i < rowCount; i++) {
                this.props.mailTransitDetails[i].usedCapacityofMailbag = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.usedCapacityofMailbag : 0;
                this.props.mailTransitDetails[i].allotedULDPostnLDC = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.allotedULDPostnLDC : 0;
                this.props.mailTransitDetails[i].allotedULDPostnLDP = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.allotedULDPostnLDP : 0;
                this.props.mailTransitDetails[i].allotedULDPostnMDP = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.allotedULDPostnMDP : 0;
                this.props.mailTransitDetails[i].availableFreeSaleCapacity = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.availableFreeSaleCapacity : 0;
                this.props.mailTransitDetails[i].freeSaleULDPostnLDP = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.freeSaleULDPostnLDP : 0;
                this.props.mailTransitDetails[i].freeSaleULDPostnLDC = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.freeSaleULDPostnLDC : 0;
                this.props.mailTransitDetails[i].freeSaleULDPostnMDP = this.props.mailTransitDetails[i].capacityDetails ? this.props.mailTransitDetails[i].capacityDetails.freeSaleULDPostnMDP : 0;
            }
        }
       // const rowCount=5;
      
        return (<Fragment>
         {(this.props.screenMode === 'display')  || (this.props.screenMode === 'edit') ? (
        <div class="card">
					{/* <div class="card-header card-header-action"> */}
                    
<div className="d-flex flex-column flex-grow-1">
    
<ITable    //IMultiGrid
                        //form={true}
                       // name="mailtransit"
                        rowCount={rowCount}
                        //headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName=""
                        //rowHeight={45}
                        rowClassName="table-row"
                        tableId="mailtransittable"
                        //sortEnabled={true}
                       // resetSelectionOnDataChange={true}

                        //showFixedWidthColumns={true}
                        columnHeaderRowHeight={80}
                        rowCount={rowCount}
                        headerHeight={35}
                        customHeader={{
                            headerClass: '',
                            customPanel: this.displayHeader(),
                            dataConfig: {
                                        screenId: 'MTK076',
                                        tableId: 'mailtransittable'
                                    },
                           // placement: 'dynamic',
                          
               // customPanel: <HeaderPanel filterValues={this.props.filterValues}  onAddPayment={this.props.onAddPayment} />,
                                "pagination": {
                                    "page": this.props.mailTransitDetailslist, getPage: this.props.getNewPage,
                                    options: [{ value: '10', label: '10' }, { value: '20', label: '20' }, { value: '30', label: '30' }, { value: '40', label: '40' }, { value: '100', label: '100' }]
                                },
                pageable: true,

                filterConfig: {
                    panel: <MailTransitTableFilter initialValues={this.props.initialValues}/>, 
                    title: 'Filter',
                    onApplyFilter: this.props.onApplyMailTransitFilter,
                    onClearFilter: this.props.onClearMailTransitFilter
                },
                sortBy: {
                    onSort: this.sortList
                },
                exportData: {
                    exportAction: this.props.exportToExcel,
                    pageable: true,
                    name: 'Mail Transit Details'
                }

                
                  }}
                 

                        data={results} 
                        >

       <Columns customRow={DetailsTableTableCustomRowContainer} customRowDataKey='carrierCode'  >
       <IColumn
                                    width={30}
                                    dataKey=""
                                    flexGrow={0}
                                    className="" >
                                    <Cell>
                                        <HeadCell rowToggler>
                                        </HeadCell>
                                        <RowCell rowToggler>
                                        </RowCell>
                                    </Cell>     
                                    </IColumn>
                            <IColumn
                                dataKey="carrierCode"
                                label="Carrier Code"
                                id="carrierCode"
                                width={120}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={false}>
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
                                dataKey="mailBagDestination"
                                label="Mailbag Destination"
                                id="mailBagDestination"
                                width={150}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={false}>
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
                                dataKey="totalNoImportBags"
                                label="Total Number of Import Mailbags"
                                id="totalNoImportBags"
                                width={200}
                                flexGrow={0}
                                sortByItem={true}
                                selectColumn={true}>
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
                                dataKey="totalWeightImportBags"
                                label="Total Weight of Import Mailbags"
                                id="totalWeightImportBags"
                                width={200}
                                flexGrow={0}
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
                                dataKey="countOfExportNonAssigned"
                                label="Count of Export-Not Assigned Mailbags"
                                id="countOfExportNonAssigned"
                                width={200}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={false}>
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
                                dataKey="totalWeightOfExportNotAssigned"
                                label="Total weight of Export-Not Assigned Mailbags"
                                id="totalWeightOfExportNotAssigned"
                                width={300}
                                flexGrow={0}
                                selectColumn={true}
                                sortByItem={false}>
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

                                {/* <IColumn
                                    dataKey="usedCapacityofMailbag"
                                    label="Used capacity of mail"
                                    id="usedCapacityofMailbag"
                                    width={300}
                             flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
                                < Cell>
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
                                    dataKey="allotedULDPostnLDC"
                                    label="Allotted Mail ULD position(LDC)"
                                    id="allotedULDPostnLDC"
                                    width={300}
                                flexGrow={0}
                                selectColumn={true}
                                    sortByItem={false}>
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
                                    dataKey="allotedULDPostnLDP"
                                    label="Allotted Mail ULD position(LDP)"
                                    id="allotedULDPostnLDP"
                                    width={300}
                             flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
                                < Cell>
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
                                    dataKey="allotedULDPostnMDP"
                                    label="Allotted Mail ULD position(MDP)"
                                    id="allotedULDPostnMDP"
                                    width={300}
                                    flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
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
                                    dataKey="availableFreeSaleCapacity"
                                    label="Available Free Sale capacity"
                                    id="availableFreeSaleCapacity"
                                    width={300}
                                    flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
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
                                    dataKey="freeSaleULDPostnLDC"
                                    label="Free sale ULD position(LDC)"
                                    id="freeSaleULDPostnLDC"
                                    width={300}
                                    flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
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
                                    dataKey="freeSaleULDPostnLDP"
                                    label="Free sale ULD position(LDP)"
                                    id="freeSaleULDPostnLDP"
                                    width={300}
                                    flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
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
                                    dataKey="freeSaleULDPostnMDP"
                                    label="Free sale ULD position(MDP)"
                                    id="freeSaleULDPostnMDP"
                                    width={300}
                                    flexGrow={0}
                                    selectColumn={true}
                                    sortByItem={false}>
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
                                </IColumn> */}
                        </Columns>
                    </ITable>  
                    </div>
                    </div>
                ): ("")} 

                    </Fragment>
);
    }
}

                    export default wrapForm('mailTransitDetail')(DetailsPanel)