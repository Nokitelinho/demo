import React, { Component } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col, Label, FormGroup } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import PropTypes from 'prop-types';
export class SurchargePopupPanel extends React.Component {
    constructor(props) {
        super(props)
    }
    closeSurchargePopup = () =>{
        this.props.closeSurchargePopup()
    }    
    render() {

        let surchargeDetails = this.props.surchargeDetails;
 
        return (
            <div>
                <PopupBody >
                    <div class="card">
                        <div class="card-body p-0">
                            <div class="d-flex" style={{height:"35vh"}}>
                        <ITable
                            rowCount={surchargeDetails.length}
                            headerHeight={35}
                            className="table-list"
                            gridClassName="table_grid"
                            headerClassName=""
                            rowHeight={35}
                            rowClassName="table-row"
                            sortEnabled={false}
                            data={surchargeDetails}
                            noRowsRenderer={() => (<div>No Records found</div>)}>
                            <Columns>
                                <IColumn
                                    label="Charge Head:"
                                    dataKey=""
                                    width={20}
                                    flexGrow={2}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>
                                                    {cellProps.label}
                                                </Content>
                                            )
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    {
                                                        cellProps.rowData.chargeHeadDesc
                                                    }
                                                </Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Rate"
                                    width={10}
                                    flexGrow={1}
                                    dataKey="">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {
                                                (cellProps) => (
                                                    <Content>
                                                        {cellProps.label}
                                                    </Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    {
                                                        cellProps.rowData.applicableRate
                                                    }
                                                </Content>
                                            )
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Amount"
                                    dataKey=""
                                    width={20}
                                    flexGrow={2} >
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
                                                    {
                                                        cellProps.rowData.chargeAmt
                                                    }
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
                    </div>
                </PopupBody >
                <PopupFooter >
                    <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.props.closeSurchargePopup}>Close</IButton>
                </PopupFooter >
            </div>
        );
    }
}
SurchargePopupPanel.propTypes = {
surchargeDetails: PropTypes.array,
closeSurchargePopup: PropTypes.func

}
export default icpopup(wrapForm('surchargeMailForm')(SurchargePopupPanel), { title: 'Surcharge Details' });