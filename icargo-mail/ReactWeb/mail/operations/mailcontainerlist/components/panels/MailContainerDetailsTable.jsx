import React from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'

export default class MailContainerDetailsTable extends React.Component {
    
    constructor(props) {
        super(props);
        this.selectedContainersIndex = [];
        this.onlistContainerDetails = this.onlistContainerDetails.bind(this);
        this.state = {
            rowClkCount: 0
        }
    }
  
    onlistContainerDetails() {
        this.props.onlistContainerDetails();
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
    onSelectmultiple = (data) => {
        let count = this.state.rowClkCount;
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                count = ++count
                this.selectContainerIndex(data.index);
            } else {
                count = --count
                this.unSelectContainerIndex(data.index);
            }
        } else {
            if (data.index==-1 && data.selectedIndexes!=0 && data.selectedIndexes!=undefined) {
                this.selectedContainersIndex = [];
                count = this.props.containerDetails.length;
                this.selectAllContainersIndex(-1);
            } else {
                count = 0
                this.unSelectAllContainersIndex();
            }
        }
        this.setState({
            rowClkCount: count
        })
    }
    selectContainerIndex = (Container) => {
        this.selectedContainersIndex.push(Container);
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    unSelectContainerIndex = (Container) => {
        let index = -1;
        for (let i = 0; i < this.selectedContainersIndex.length; i++) {
            var element = this.selectedContainersIndex[i];
            if (element === Container) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedContainersIndex.splice(index, 1);
        }
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    selectAllContainersIndex = () => {
        for (let i = 0; i < this.props.containerDetails.results.length; i++) {
            this.selectedContainersIndex.push(i);
        }
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    unSelectAllContainersIndex = () => {
        this.selectedContainersIndex = []
        this.props.saveSelectedContainersIndex(this.selectedContainersIndex);
    }
    render() {

      const results = (this.props.containerDetails.results) ? this.props.containerDetails.results: '';
      const rowCount = (this.props.containerDetails.results.length) ? this.props.containerDetails.results.length : 0;
     
      
        return (

           
            <div className="d-flex" style={{"height":"400px" }}>
            <>
        <ITable

            rowCount={rowCount}
            headerHeight={35}
            gridClassName="table_grid"
            headerClassName="table-head"
            className="table-list"
            rowHeight={45}
            rowClassName="table-row"
            tableId="mailbagDetailsTable2"
            
            sortEnabled={false}
            customHeader={
            {
                headerClass: '',
                placement: '',
                dataConfig: {
                    screenId: '',
                    tableId: 'mailcontainertable'
                },
                "pagination": { "page": this.props.containerDetails, getPage: this.props.getNewPage,
                options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' }] },
              
                sortBy: {
                    onSort: this.sortList
                }
            }
        }


            data={results}
            onRowSelection={this.onSelectmultiple}
        >
        
                    <Columns>
                    
                    <IColumn
                        width={40}
                        dataKey=""
                        flexGrow={0}
                        className="first-column align-items-center"
                        hideOnExport>
                        <Cell>
                            <HeadCell disableSort selectOption>
                            </HeadCell>
                            <RowCell selectOption>
                            </RowCell>
                        </Cell>
                    </IColumn>
                    
                    
                        <IColumn
                            label='Container ID'
                            dataKey='containerNumber'
                            width={100} flexGrow={1}
                            id="containerNumber"
                            sortByItem={true} >
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
                            label="Destination"
                            dataKey="destination"
                            id="destination"
                            width={80} flexGrow={1}
                            sortByItem={true} >
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
                            label="SubClass Group"
                            dataKey="subclassGroup"
                            id="subclassGroup"
                            width={80} flexGrow={1} 
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
                            label="No of MailBags"
                            dataKey="bags"
                            id="bags"
                            width={80} flexGrow={1}
                            sortByItem={true} >
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
                            label="Total Weight"
                            dataKey="weight"
                            id="weight"
                            width={80} flexGrow={1} 
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
                            label="No of Days"
                            dataKey="noOfDaysInCurrentLoc"
                            id="noOfDaysInCurrentLoc"
                            width={80} flexGrow={1} 
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
                            label="Expected/Provisional Charge"
                            dataKey="charge"
                            id="charge"
                            width={80} flexGrow={1}
                            sortByItem={true} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                             <span>{cellProps.rowData.rateAvailforallMailbags!=null&&cellProps.rowData.rateAvailforallMailbags!=undefined&&cellProps.rowData.rateAvailforallMailbags=='N'?<span className="text-red">{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined ? cellProps.rowData.provosionalCharge :''}</span>:<span>{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined && cellProps.rowData.provosionalCharge!='0' ? cellProps.rowData.provosionalCharge :''}</span>}</span>
                                             <span className="pad-l-2xs">{cellProps.rowData.baseCurrency}</span> 
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            label="ULD Full Indicator"
                            dataKey="uldFulIndFlag"
                            id="uldFulIndFlag"
                            width={80} flexGrow={1}
                            sortByItem={true} >
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
                            label="Planned Flight  and date"
                            dataKey="plannedFlightAndDate"
                            id="plannedFlightAndDate"
                            width={80} flexGrow={1} 
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
                        
                    </Columns>
                        
        </ITable>
        </>
          </div>
      )
      }
    }