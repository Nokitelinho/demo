import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import MailTableHeaderPanel from './ContainerTableHeaderPanel.jsx'
import { ITextField,ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import MailTableFilter from './MailTableFilter.jsx';


export default class MailDetailsTable extends Component {
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
                    tableId="mailtable"
                    name="mailtable"
                    sortEnabled={false}
                    onRowSelection={this.onRowSelection}
                    form={true}
                    resetSelectionOnDataChange={true}
                    customHeader={{
                        headerClass: '',
                        placement: 'dynamic',
                        dataConfig: {
                            screenId: '',
                            tableId: 'mailtable'
                        },
                        customPanel: <MailTableHeaderPanel  />,
                        "pagination": { "page": this.props.containers, getPage: this.props.getNewPage },
                        filterConfig: {
                            panel: <MailTableFilter  tableFilter={this.props.tableFilter} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyMailFilter,
                            onClearFilter: this.props.onClearMailFilter
                        },
                        exportData: {
                            exportAction: this.props.exportToExcelMailBags,
                            pageable: true,
                            name: 'Offload MailBags List'
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
                            dataKey="mailbagId"
                            label='Mailbag Id'
                            flexGrow={0}
                            id="mailbagId"
                            width={250}
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
                            label='Cont. No'
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
                        dataKey="offloadReason"
                        label="Offload Reason"
                            width={250}
                        flexGrow={1}>
                    
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
                            width={250}
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
