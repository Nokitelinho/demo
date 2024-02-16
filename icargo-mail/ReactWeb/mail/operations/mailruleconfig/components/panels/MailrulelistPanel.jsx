import React, { Component, Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import MailRuleCustomPanel from './custompanel/MailRuleCustomPanel.js';

class MailrulelistPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    onRowSelection = (data) => {
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                this.props.saveSelectedMailRleConfigIndex(data.index, 'SELECT');
            } else {
                this.props.saveSelectedMailRleConfigIndex(data.index, 'UNSELECT');
            }
        }else {
            if (data.selectedIndexes&&data.selectedIndexes.length>0) {
                this.props.saveMultipleMailRleConfigIndex(data.selectedIndexes);
            } else {
                this.props.saveSelectedMailRleConfigIndex(data.index, 'UNSELECT');
            }
        }
    }

    render() {
        const results=this.props.mailRuleConfigList;
        return (
            <Fragment>
                    <ITable
                        name="mailrulelist"
                        headerHeight={35}
                        resetFormOnDataChange={false}
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName="table-head"
                        rowHeight={45}
                        rowClassName="table-row"
                        sortEnabled={false}
                        resetSelectionOnDataChange={true}
                        tableId="mailrulelist"
                        onRowSelection={this.onRowSelection}
                        data={results}
                        customHeader={
                            {
                                headerClass: '',
                                placement: 'dynamic',

                                customPanel: <MailRuleCustomPanel onmodiftMailRule={this.props.onmodiftMailRule} onAddMailRule={this.props.onAddMailRule} onDeleteMailRule={this.props.onDeleteMailRule}/>,

                            }}
                    >
                        <Columns>
                            <IColumn
                                width={40}
                                dataKey=""
                                flexGrow={0}
                                className="">
                                <Cell>
                                    <HeadCell selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="ooe"
                                label="Origin Office Of Exchange"
                                id="ooe"
                                width={200}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="doe"
                                label="Destination Office Of Exchange"
                                width={250}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>

                            <IColumn
                                dataKey="contractCarrier"
                                label="Contract Carrier"
                                width={130}
                                id="contractCarrier"
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="originAirport"
                                label="Origin"
                                id="originAirport"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="destinationAirport"
                                label="Destination"
                                id="destinationAirport"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="categoryCode"
                                label="Category"
                                id="categoryCode"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="subclass"
                                label="Subclass"
                                id="subclass"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="xxresdit"
                                label="XX Resdit"
                                id="xxresdit"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailboxId"
                                label="Mailbox ID"
                                id="mailboxId"
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>{cellProps.cellData}</Content>
                                            )

                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="status"
                                label="Status"
                                id="status"
                                width={120}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {
                                            return (
                                                <Content>
                                               {(cellProps.cellData==='Active')?<span className= {"badge high-light badge-active"}>ACTIVE</span>: <span className= {"badge high-light badge-inactive"}>INACTIVE</span>}
                                                </Content>
                                            )
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </ITable>
                    </Fragment>
        )
    }
}

//export default wrapForm('mailrulelist')(MailrulelistPanel);
export default MailrulelistPanel;