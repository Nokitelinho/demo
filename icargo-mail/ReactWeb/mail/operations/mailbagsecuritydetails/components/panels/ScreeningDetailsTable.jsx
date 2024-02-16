import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, CustomRow } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';


export default class ScreeningDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.editScreeningDetails = this.editScreeningDetails.bind(this);
        this.deleteScreeningDetails = this.deleteScreeningDetails.bind(this);

    }

    editScreeningDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.editScreeningDetails({ actionName: actionName, index: rowIndex });
    }
    deleteScreeningDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.deleteScreeningDetails({ actionName: actionName, index: rowIndex });
    }


    render() {

        const results = this.props.mailbagSecurityDetails ? this.props.mailbagSecurityDetails : '';
        const rowCount = results.length;
        return (

            <>
                <ITable

                    headerHeight={35}
                    gridClassName="table_grid"
                    headerClassName="table-head"
                    className="table-list"
                    rowHeight={45}
                    rowClassName="table-row"
                    tableId="mailbagDetailsTable2"
                    name="mailbagDetailsTable2"
                    sortEnabled={false}

                    form={true}
                    data={results}
                >

                    <Columns>
                        <IColumn
                            label='Screened Location'
                            dataKey='screeningLocation'
                            width={100} flexGrow={1} >
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
                            label='Method'
                            dataKey='screeningMethodCode'
                            width={80} flexGrow={1} >
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
                            label='Security Status Issued by'
                            dataKey='securityStatusParty'
                            width={180} flexGrow={1} >
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
                            label='Date and Time'
                            dataKey='securityStatusDate'
                            width={150} flexGrow={1} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <CustomRow>
                                            <div className="d-flex flex-column">
                                                <span className="mb-1"> {cellProps.cellData} </span>
                                                <span> {cellProps.rowData.time}</span>
                                            </div>
                                        </CustomRow>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            label='Source'
                            dataKey='sourceIndicator'
                            width={100} flexGrow={1} >
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
                            label='Result'
                            dataKey='result'
                            width={100} flexGrow={1} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {(cellProps.rowData.result === 'F') ?
                                                <span className="badge badge-pill light badge-error">Fail</span> :
                                                <span className="badge badge-pill light badge-active">Pass</span>}
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
                                                    <IDropdownToggle className="dropdown-toggle more-toggle border-0">
                                                        <a href="#"><i className="icon ico-v-ellipsis align-middle"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu portal={true} className="mailbag-dropdownmenu" right={true} >
                                                        <IDropdownItem data-index={cellProps.rowIndex} data-mode="Edit"  onClick={this.editScreeningDetails} privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_EDIT_SCREENING" > Edit</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="Delete" onClick={this.deleteScreeningDetails} privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_MAILBAG_SECURITY_DELETE_SCREENING" > Delete</IDropdownItem>
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
