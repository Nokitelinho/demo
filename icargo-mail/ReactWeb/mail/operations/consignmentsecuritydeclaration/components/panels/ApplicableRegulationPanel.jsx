import React, { PureComponent, Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, CustomRow } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'

export default class ApplicableRegulationPanel extends React.PureComponent{
    constructor(props) {
        super(props);
    }
    rowAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        let applicableRegulation= this.props.applicableRegulation;
        let EditRegulationDetails  = applicableRegulation[index];
        if (actionName === 'DELETE') {
             this.props.rowActionDeleteRegulation({index: index ,applicableRegulation:applicableRegulation });
        }
        if (actionName === 'EDIT'){
            this.props.rowActionEditRegulation(EditRegulationDetails, index);
        }
    }

    editApplicableRegulation(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.editExemptionDetails({ actionName: actionName, index: rowIndex });
    }

    deleteApplicableRegulation(event) {
        let actionName = event.target.dataset.mode
        let rowIndex = event.target.dataset.index
        this.props.deleteExemptionDetails({ actionName: actionName, index: rowIndex });
    }

    render(){

        const results = (this.props.applicableRegulation ? this.props.applicableRegulation : '');
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
               tableId="applicableregulationtable"
               data ={results}
                >
                <Columns>
                    <IColumn
                    dataKey="applicableRegTransportDirection"
                    label="AR Transport Direction"
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
                                       {((cellProps.rowData.applicableRegTransportDirection) != null || (cellProps.rowData.applicableRegTransportDirection)!=undefined)?
                                           this.props.oneTimeValues['mail.operations.applicableregulationtransport'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.applicableRegTransportDirection }).label : ' '
                                       }</Content>
                                    
                                )
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey="applicableRegBorderAgencyAuthority"
                    label="AR Border Agency Authority"
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
                                        <Content> {(cellProps.rowData.applicableRegBorderAgencyAuthority) != null ||(cellProps.rowData.applicableRegBorderAgencyAuthority) != undefined ?
                                            this.props.oneTimeValues['mail.operations.applicableRegulationBorderAgencyAuthority'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.applicableRegBorderAgencyAuthority }).label : ' '
                                        }</Content>)
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                    dataKey="applicableRegReferenceID"
                    label="AR Reference ID"
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
                                        <Content><Label id={'refid' +cellProps.rowIndex} >

                                        {cellProps.cellData && cellProps.cellData.length > 10 && cellProps.cellData!=null ?
                                        cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>
                                        {cellProps.cellData && cellProps.cellData.length > 10? <IToolTip value={cellProps.cellData} target={'refid' +cellProps.rowIndex} placement='bottom' /> : ""}
                                     </Content>)
                                    }
                                </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                    dataKey="applicableRegFlag"
                    label="AR Flag"
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
                                         <Content>
                                          {(cellProps.rowData.applicableRegFlag) != null ?
                                            this.props.oneTimeValues['mail.operations.applicableRegulationFlag'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.applicableRegFlag }).label : ' '
                                        }
                                        </Content>)} */}
                                       

                                {(cellProps) => {  let data = (cellProps.rowData.applicableRegFlag!=undefined||cellProps.rowData.applicableRegFlag!=""||cellProps.rowData.applicableRegFlag!=null)? this.props.oneTimeValues['mail.operations.applicableRegulationFlag'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.applicableRegFlag }):""

                                 return <Content><Label id={'appRegulation' +cellProps.rowIndex} >


                                 { (data!="" && data!=undefined) ?((data.label) && (this.props.oneTimeValues['mail.operations.applicableRegulationFlag'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => { return element.value === cellProps.rowData.applicableRegFlag }).label).length > 10 ?

                                    (data.label).substring(0,10) + "..." : data.label ):""}</Label>

                                  {data!="" && data!=undefined && data.label
                                   && (data.label).length > 10 ? <IToolTip value={data.label } target={'appRegulation' +cellProps.rowIndex} placement='bottom' /> : ""}

                                </Content>
                                }}
                                       
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

