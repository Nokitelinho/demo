import React, { Component, Fragment } from 'react';
import { Row, Col } from 'reactstrap';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import icpoup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import PropTypes from 'prop-types';
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'
import { Label } from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

export class PaymentDetailsPopup extends Component {

    constructor(props) {
        super(props);
        this.paymentDetails = []
    }

    render() {
        this.paymentDetails = this.props.paymentDetails;
        const currency=this.props.currency;


        return (
             <div className="popup-area">
                <PopupBody>
                                <ITable
                                    rowCount={this.paymentDetails.length}
                                    headerHeight={45}
                                    className="table-list"
                                    gridClassName="table_grid"
                                    rowHeight={60}
                                    sortEnabled={false}
                                    additionalData={this.props.currency}
                                    data={this.paymentDetails}
                                    tableId='paymentdetailstable'
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
                                            label="Billing Indicator"
                                            dataKey="billingIndicator"
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
                                            label="Total Billed"
                                            dataKey="totalBilledAmount"
                                            width={10}
                                            flexGrow={1}>
                                            <Cell>
                                                <HeadCell disableSort>
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="">
                                                                {cellProps.label + "(" + currency + ")"}
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
                                            label="Total Settled"
                                            dataKey="totalSettledAmount"
                                            width={10}
                                            flexGrow={1}>
                                            <Cell>
                                                <HeadCell disableSort>
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="">
                                                                {cellProps.label + "(" + currency + ")"}
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
                                            label="Internal CCA Amt"
                                            dataKey="internalCCAAmount"
                                            width={10}
                                            flexGrow={1}>
                                            <Cell>
                                                <HeadCell disableSort>
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="">
                                                                {cellProps.label + "(" + currency + ")"}
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
                                            label="Due Amount"
                                            dataKey="dueAmount"
                                            width={10}
                                            flexGrow={1}>
                                            <Cell>
                                                <HeadCell disableSort>
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="">
                                                                {cellProps.label + "(" + currency + ")"}
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
                                            label="Payment Status"
                                            dataKey="paymentStatus"
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
                                            label="Remarks"
                                            dataKey="remarks"
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
                                                            <Label id={'pdrmk-' +cellProps.rowIndex} >{cellProps.cellData && cellProps.cellData.length > 13 ? cellProps.cellData.substring(0, 10) + "..." : cellProps.cellData}</Label>
                                                             {cellProps.cellData && cellProps.cellData.length > 13 ?
                                                              <IToolTip value={cellProps.cellData} target={'pdrmk-' + cellProps.rowIndex} placement='bottom' /> 
                                                              : ""}                                   
                                                        </Content>
                                                    )
                                                    }
                                                </RowCell>
                                            </Cell>
                                        </IColumn>
                                        <IColumn
                                            label="Case Closed"
                                            dataKey="caseClosed"
                                            width={10}
                                            flexGrow={1}
                                            className="" >
                                            <Cell>
                                                <HeadCell disableSort >
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="text-center">
                                                                {cellProps.label}
                                                            </div>
                                                        </Content>
                                                    )
                                                    }
                                                </HeadCell>
                                                <RowCell >
                                                    {(cellProps) => (
                                                        <Content>
                                                            <div className="text-center">
                                                             {(cellProps.cellData == 'Y' ||cellProps.cellData == 'A') ? "Y" : "N"}
                                                            </div>
                                                        </Content>
                                                    )
                                                    }
                                                </RowCell>
                                            </Cell>
                                        </IColumn>


                                    </Columns >
                                </ITable >
                            
                </PopupBody>
                <PopupFooter>
                    <IButton category="default" onClick={this.props.onClose}><IMessage msgkey="customermanagement.defaults.customerconsole.close"/></IButton>
                </PopupFooter>
            </div>

        )
    }
}

PaymentDetailsPopup.propTypes = {
    paymentDetails: PropTypes.array,
    currency: PropTypes.string,
    onClose: PropTypes.func
};

export default (icpoup((PaymentDetailsPopup), { title: 'Payment Details', className: 'modal_70w' }));