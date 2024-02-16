import React, { PureComponent, Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, CustomRow } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'

export default class ConsignerDetailsPanel extends React.PureComponent{
    constructor(props) {
        super(props);
        this.editConsignorDetails = this.editConsignorDetails.bind(this);
        this.deleteConsignorDetails = this.deleteConsignorDetails.bind(this);
    }
    rowAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        let ConsignerDetails= this.props.ConsignerDetails;
        let EditConsignorDetails = ConsignerDetails[index]
        if (actionName === 'DELETE') {
             this.props.rowActionDeletecons({index: index ,ConsignerDetails:ConsignerDetails });
        }
        if (actionName === 'EDIT'){
            this.props.rowActionEditcons(EditConsignorDetails, index);
        }
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

    render(){

        const results = (this.props.ConsignerDetails ? this.props.ConsignerDetails : '');
        const rowCount = results.length;

        return(
            <div class="p-0 table-height-section-1 grid-container">
			{ this.props.showITable &&
            <ITable
                gridClassName="table_grid"
        headerClassName="table-head"
        className="table-list"
        rowHeight={45}
        rowClassName="table-row"
               tableId="consignerDetailstable"
               data ={results}
                >

                <Columns>
                <IColumn
                    dataKey="agenttype"
                    label="Agent Type"
                     width={200} flexGrow={1}>
                        <Cell>
                        <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                {(cellProps) => (
                                        <Content>
										{(cellProps.rowData.agenttype) != null ?
                                                this.props.oneTimeValues['mail.operations.consignorstatuscode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.agenttype }).label : ' '
                                            } 
									</Content>)
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey="isoCountryCode"
                    label="ISO Country Code"
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
                                        <Content>{cellProps.cellData}</Content>)
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey="agentId"
                    label="Agent ID"
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
                                        <Content><Label id={'agentid' +cellProps.rowIndex} >
                                  {cellProps.cellData && cellProps.cellData.length > 10 && cellProps.cellData!=undefined && cellProps.cellData!=null ?
                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>
                                  {cellProps.cellData && cellProps.cellData.length > 10 ? <IToolTip value={cellProps.cellData} target={'agentid' +cellProps.rowIndex} placement='bottom' /> : ""}
                                </Content>
                                    )
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                    dataKey="expiryDate"
                    label="Expiry"
                    width={200}
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
                                                    <IDropdownToggle className="dropdown-toggle more-toggle border-0">
                                                        <a href="#"><i className="icon ico-v-ellipsis align-middle"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu portal={true} className="mailbag-dropdownmenu" right={true} >
                                                        <IDropdownItem data-mode="EDIT" data-index={cellProps.rowIndex} onClick={this.rowAction}>Edit</IDropdownItem>
                                                        <IDropdownItem data-mode="DELETE" data-index={cellProps.rowIndex} onClick={this.rowAction}>Delete</IDropdownItem>
                                                    </IDropdownMenu>
                                                </IDropdown>
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>

                </Columns>        
                </ITable>
			}	
               </div>
        )
        
    }
}

