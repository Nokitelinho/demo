import React, { PureComponent, Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, CustomRow } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'

export default class SecurityExemptionPanel extends React.PureComponent{
    constructor(props) {
        super(props);
        // this.editsecurityExemption = this.editsecurityExemption.bind(this);
        // this.deletesecurityExemption = this.deletesecurityExemption.bind(this);
    }
    rowAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        let securityExemption= this.props.securityExemption;
        let EditsecurityExemption  = securityExemption[index];
        if (actionName === 'DELETE') {
             this.props.rowActionDeleteExemption({index: index ,securityExemption:securityExemption });
        }
        if (actionName === 'EDIT'){
            this.props.rowActionEditExemption(EditsecurityExemption, index);
        }
    }

    editExemptionDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.editExemptionDetails({ actionName: actionName, index: rowIndex });
    }

    deleteExemptionDetails(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.deleteExemptionDetails({ actionName: actionName, index: rowIndex });
    }

    render(){

        const results = (this.props.securityExemption ? this.props.securityExemption : '');
        const rowCount = results.length;

        return(
            <div className="p-0 table-height-section-1 grid-container">
			{ this.props.showITable &&
            <ITable
                gridClassName="table_grid"
        headerClassName="table-head"
        className="table-list"
        rowHeight={45}
        rowClassName="table-row"
               tableId="securityexemptiontable"
               data ={results}
                >

                <Columns>
                    <IColumn
                    dataKey="seScreeningReasonCode"
                    label="Reason for Exemption"
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
                    dataKey="seScreeningAuthority"
                    label="SE Applicable Authority"
                    width={250}
                    flexGrow={1}>
                         <Cell>
                         <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {/* {(cellProps) => (
                                        <Content>{cellProps.cellData}</Content>)
                                    } */}
                                     {(cellProps) => (
                                  <Content><Label id={'SEAuthority' +cellProps.rowIndex} >

                                  {cellProps.cellData && cellProps.cellData.length > 10 && cellProps.cellData!=null  ?

                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>

                                  {cellProps.cellData && cellProps.cellData.length > 10 ? <IToolTip value={cellProps.cellData} target={'SEAuthority' +cellProps.rowIndex} placement='bottom' /> : ""}

                                </Content>
                                )}
                                </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                    dataKey="seScreeningRegulation"
                    label="SE Applicable Regulation"
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
                                  <Content><Label id={'SERegulation' +cellProps.rowIndex} >

                                  {((cellProps.cellData) && (cellProps.cellData.length > 10) &&( cellProps.cellData!=null)) ?

                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>

                                  {cellProps.cellData && cellProps.cellData.length > 10 ? <IToolTip value={cellProps.cellData} target={'SERegulation' +cellProps.rowIndex} placement='bottom' /> : ""}

                                </Content>
                                )}
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

