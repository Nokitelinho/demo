import React, { Component, Fragment } from 'react';
import { Row, Col } from 'reactstrap';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import icpoup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';


export class ListCCAPopup extends Component {

    constructor(props) {
        super(props);

    }
    render() {

        const rowCount = this.props.ccaDetails ? this.props.ccaDetails.length : 0
        return (
            <div className="popup-area">
                <PopupBody>
                            
                                {rowCount > 0 ?
                                    <ITable
                                        rowCount={rowCount}
                                        headerHeight={35}
                                        className="table-list"
                                        gridClassName="table_grid"
                                        rowHeight={100}
                                        sortEnabled={true}
                                        data={this.props.ccaDetails}
                                        tableId='invoicelisttable'
                                        rowClassName='table-row'
                                        customHeader={
                                            {

                                                exportData: {
                                                    exportAction: this.props.exportToExcel,
                                                    pageable: false,
                                                    name: 'CCA_List'
                                                }

                                            }

                                        }
                                        noRowsRenderer={() => (<div><IMessage msgkey="customermanagement.defaults.customerconsole.noresult"/></div>)}>
                                        <Columns>

                                            <IColumn
                                                label="AWB"
                                                dataKey="awb"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="AWB Date"
                                                dataKey="awbDate"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA No."
                                                dataKey="ccaNumber"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
                                                        {(cellProps) => (
                                                            <Content>
                                                                <div className="d-inline-block">
                                                                    {cellProps.label}
                                                                </div>
                                                            </Content>
                                                        )
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA Date"
                                                dataKey="ccaDate"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
                                                        {(cellProps) => (
                                                            <Content>
                                                                <div className="d-inline-block">
                                                                    {cellProps.label}
                                                                </div>
                                                            </Content>
                                                        )
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA Amount"
                                                dataKey="ccaAmount"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Type"
                                                dataKey="ccaType"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Status"
                                                dataKey="ccaStatus"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
                                                        {(cellProps) => (
                                                            <Content>
                                                                <div className="d-inline-block">
                                                                    {cellProps.label}
                                                                </div>
                                                            </Content>
                                                        )
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Reason"
                                                dataKey="ccaReason"
                                                width={10}
                                                flexGrow={1.4}
												className="wrap-view">
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="Billing Status"
                                                dataKey="billingStatus"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                        </Columns >
                                    </ITable > :
                                    <ITable
                                        rowCount={rowCount}
                                        headerHeight={35}
                                        className="table-list"
                                        gridClassName="table_grid"
                                        rowHeight={60}
                                        sortEnabled={true}
                                        data={this.props.ccaDetails}
                                        tableId='invoicelisttable'
                                        rowClassName='table-row'

                                        noRowsRenderer={() => (<div><IMessage msgkey="customermanagement.defaults.customerconsole.noresult"/></div>)}>
                                        <Columns>

                                            <IColumn
                                                label="AWB"
                                                dataKey="awb"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="AWB Date"
                                                dataKey="awbDate"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA No."
                                                dataKey="ccaNumber"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
                                                        {(cellProps) => (
                                                            <Content>
                                                                <div className="d-inline-block">
                                                                    {cellProps.label}
                                                                </div>
                                                            </Content>
                                                        )
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA Date"
                                                dataKey="ccaDate"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
                                                        {(cellProps) => (
                                                            <Content>
                                                                <div className="d-inline-block">
                                                                    {cellProps.label}
                                                                </div>
                                                            </Content>
                                                        )
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                            <IColumn
                                                label="CCA Amount"
                                                dataKey="ccaAmount"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Type"
                                                dataKey="ccaType"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Status"
                                                dataKey="ccaStatus"
                                                width={10}
                                                flexGrow={1}>
                                                <Cell>
                                                    <HeadCell >
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="CCA Reason"
                                                dataKey="ccaReason"
                                                width={10}
                                                flexGrow={1.4}>
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                label="Billing Status"
                                                dataKey="billingStatus"
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
                                                                {cellProps.cellData}
                                                            </Content>
                                                        )
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                        </Columns >
                                    </ITable >}
                            
                </PopupBody>
                <PopupFooter>
                    <IButton category="default" onClick={this.props.onClose}><IMessage msgkey="customermanagement.defaults.customerconsole.close"/></IButton>
                </PopupFooter>
            </div>

        )
    }
}
ListCCAPopup.propTypes = {
    ccaDetails: PropTypes.array,
    exportToExcel: PropTypes.func,
    onClose: PropTypes.func
};
export default icpoup((ListCCAPopup), { title: 'CCA List', className: 'modal_90w' });