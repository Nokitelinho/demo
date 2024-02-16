import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

export default class ConsignorDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.editConsignorDetails = this.editConsignorDetails.bind(this);
        this.deleteConsignorDetails = this.deleteConsignorDetails.bind(this);


    }

    editConsignorDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.editConsignorDetails({ actionName: actionName, index: rowIndex });
    }

    deleteConsignorDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.deleteConsignorDetails({ actionName: actionName, index: rowIndex });
    }

    render() {
        let agentType = []
            if(!isEmpty(this.props.oneTimeValues)){
            agentType = this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        const results = this.props.mailbagSecurityDetails ? this.props.mailbagSecurityDetails : '';
        const rowCount = results.length;
        return (

            <>
                <ITable


                    className="table-list"
                    gridClassName="table_grid"
                    headerClassName="table-head"
                    headerHeight={45}
                    rowHeight={45}
                    rowClassName="table-row"
                    tableId="mailbagDetailsTable"
                    name="mailbagDetailsTable"
                    sortEnabled={false}

                    form={true}
                    data={results}>
                    <Columns>
                        <IColumn
                            label='Agent Type'
                            dataKey='agenttype'
                            width={120} flexGrow={1} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                         {(cellProps.rowData.agenttype) != null && (cellProps.rowData.agenttype).length > 0? 
                                                this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.agenttype }).label : ' '
                                            } 
									</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>

                        <IColumn
                            label='ISO Country Code'
                            dataKey='isoCountryCode'
                            width={160} flexGrow={1} >
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
                            label='Agent ID'
                            dataKey='agentId'
                            width={200} flexGrow={1} >
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
                            label='Expiry'
                            dataKey='expiryDate'
                            width={170} flexGrow={1} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                         <Content>
                                            <Label id={'expire-'+ cellProps.cellData + cellProps.rowData.serialNumber} >{cellProps.cellData}</Label>
                                          <IToolTip value={"MMYY"} target={'expire-' + cellProps.cellData + cellProps.rowData.serialNumber} placement='bottom' />  
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            width={44}
                            className="align-self-center"
                            flexGrow={0}
                            hideOnExport>

                            < Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content></Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>                                   
                                                <IDropdown portal={true}>
                                                    <IDropdownToggle className="dropdown-toggle more-toggle border-0" >
                                                        <a href="#"><i className="icon ico-v-ellipsis align-middle"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu portal={true} className="mailbag-dropdownmenu" right={true} >
                                                        <IDropdownItem data-index={cellProps.rowIndex} data-mode="Edit" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_EDIT_CONSIGNOR" privilegeCheck={true} onClick={this.editConsignorDetails} > Edit</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="Delete" componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_DELETE_CONSIGNOR" privilegeCheck={true} onClick={this.deleteConsignorDetails} > Delete</IDropdownItem>
                                                    </IDropdownMenu>
                                                </IDropdown>
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                    </Columns>
                </ITable>
            </>

        );
    }

}
