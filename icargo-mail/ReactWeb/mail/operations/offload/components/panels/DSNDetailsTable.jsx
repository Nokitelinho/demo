import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import DSNTableHeaderPanel from './ContainerTableHeaderPanel.jsx'
import { ISelect ,ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import DSNTableFilter from './DSNTableFilter.jsx';


export default class DSNDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = []
        this.state = {
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

        for (let i = 0; i < this.props.containerdetails.length; i++) {
            this.selectedMailbags.push(i);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }

    unSelectAllMailbags = () => {
        this.selectedMailbags = []
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
    }
    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
    
    render() {

 

        const results = this.props.containerdetails ? this.props.containerdetails : '';
        const rowCount = results.length;

        let offloadReason= [];

        if (!isEmpty(this.props.oneTimeValues)) {
            offloadReason = this.props.oneTimeValues['mailtracking.defaults.offload.reasoncode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    rowHeight={45}
                    rowClassName="table-row"
                    tableId="dsntable"
                    name="dsntable"
                    sortEnabled={false}
                    onRowSelection={this.onRowSelection}
                    form={true}
                    resetSelectionOnDataChange={true}
                    customHeader={{
                        headerClass: '',
                        placement: 'dynamic',
                        dataConfig: {
                            screenId: '',
                            tableId: 'dsntable'
                        },
                        customPanel: <DSNTableHeaderPanel  />,
                        "pagination": { "page": this.props.containers, getPage: this.props.getNewPage },
                        filterConfig: {
                            panel: <DSNTableFilter  tableFilter={this.props.tableFilter} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyDSNFilter,
                            onClearFilter: this.props.onClearDSNFilter
                        },
                        exportData: {
                            exportAction: this.props.exportToExcelDSN,
                            pageable: true,
                            name: 'Offload DSN List'
                            }, 
                        sortBy: {
                                onSort: this.sortList
                            }
                    }}
                    data={results}>
                    
                    <Columns>
                        <IColumn
                            width={42}
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
                            dataKey="dsn"
                            label='DSN'
                            flexGrow={0}
                            id="dsn"
                            width={45}
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
                            dataKey="ooe"
                            label='OOE'
                            flexGrow={0}
                            id="ooe"
                            width={65}
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
                            dataKey="doe"
                            label='DOE'
                            flexGrow={0}
                            id="doe"
                            width={65}
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
                            dataKey="mailClass"
                            label='Mail Class'
                            flexGrow={0}
                            id="mailClass"
                            width={55}
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
                            dataKey="subClass"
                            label='Sub Class'
                            flexGrow={0}
                            id="subClass"
                            width={55}
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
                            dataKey="containerNo"
                            label='Cont. No.'
                            flexGrow={0}
                            id="containerNo"
                            width={100}
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
                                        {
                                            cellProps.rowData.paBuildFlag === 'Y' && "(SB)"
                                           
                                        }</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="consignmentNo"
                            label='Cons No.'
                            flexGrow={1}
                            id="consignmentNo"
                            width={100}
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
                            dataKey="pou"
                            label="POU"
                            flexGrow={0}
                            id="pou"
                            width={50}
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
                            dataKey="destination"
                            label="Dest"
                            width={50}
                            flexGrow={0}
                            selectColumn={true}
                            sortByItem={true}
                            id="destination">
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
                            dataKey="acceptedBags"
                            label="Acc. Bags"
                            width={70}
                            flexGrow={0}
                            selectColumn={true}
                            id="acceptedBags"
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
                            dataKey="acceptedWeight"
                            label="Acc. Wt"
                            width={70}
                            flexGrow={0}
                            id="acceptedWeight"
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
                        dataKey="offloadReason"
                        label="Offload Reason"
                            width={120}
                            flexGrow={1}
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
                                          <ISelect name={`${cellProps.rowIndex}.offloadReason`}  options={offloadReason}   />
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                        <IColumn
                            dataKey="remarks"
                            label="Remarks"
                            width={180}
                            flexGrow={1}
                            id="remarks"
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
                                            <ITextField  name={`${cellProps.rowIndex}.remarks`} value={cellProps.cellData} />
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
