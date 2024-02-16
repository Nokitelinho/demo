import React from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content, CustomRow } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'

export default class ScreeningDetailsPanel extends React.Component{
    constructor(props) {
        super(props);
        this.editScreeningDetails = this.editScreeningDetails.bind(this);
        this.deleteScreeningDetails = this.deleteScreeningDetails.bind(this);

    }

    rowAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        let ScreeningDetails= this.props.ScreeningDetails;
        let EditScreeningDetails = ScreeningDetails[index]
        if (actionName === 'DELETE') {
            
             this.props.rowActionDelete({index: index ,ScreeningDetails:ScreeningDetails });
        }
        if (actionName === 'EDIT'){
            this.props.rowActionEdit( EditScreeningDetails, index);
        }
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
    render(){
  
    const results = (this.props.ScreeningDetails ? this.props.ScreeningDetails : '');
    const rowCount = results.length;

        return(
            <>
       <ITable
        gridClassName="table_grid"
        headerClassName="table-head"
        className="table-list"
        rowHeight={45}
        rowClassName="table-row"
        tableId="screeningDetailsTable"
        name="screeningDetailsTable"
        sortEnabled={false}
        form={true}
        data={results}
               
                >

                <Columns>
                
                <IColumn
                            label='Screened Location'
                            dataKey='screeningLocation'
                            width={110} flexGrow={1} >
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
                            width={95} flexGrow={1} >
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
                            width={140} flexGrow={1} >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content><Label id={'issued' +cellProps.rowIndex} >

                                        {cellProps.cellData && cellProps.cellData.length > 10 && cellProps.cellData!=null ?
                                        cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>
                                        {cellProps.cellData && cellProps.cellData.length > 10? <IToolTip value={cellProps.cellData} target={'issued' +cellProps.rowIndex} placement='bottom' /> : ""}
      </Content>)
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
                                                <span className="mb-1"> {cellProps.cellData.substring(0,17)} </span>
                                            </div>
                                        </CustomRow>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>

                    <IColumn
                    dataKey="screeningAuthority"
                    label="Applicable Authority"
                    width={120} flexGrow={1} >
                        <Cell>
                        <HeadCell disableSort>
                            {(cellProps) => (
                                <Content>{cellProps.label}</Content>)
                            }
                        </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                  <Content><Label id={'Authority' +cellProps.rowIndex} >

                                  {cellProps.cellData && cellProps.cellData.length > 10 && cellProps.cellData!=null ?
                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>
                                  {cellProps.cellData && cellProps.cellData.length > 10 ? <IToolTip value={cellProps.cellData} target={'Authority' +cellProps.rowIndex} placement='bottom' /> : ""}
                                </Content>
                                )}
                            </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey="screeningRegulation"
                    label="Applicable Regulation"
                    width={110} flexGrow={1} >
                        <Cell>
                        <HeadCell disableSort>
                            {(cellProps) => (
                                <Content>{cellProps.label}</Content>)
                            }
                        </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                  <Content><Label id={'Regulation' +cellProps.rowIndex} >

                                  {cellProps.cellData && cellProps.cellData.length > 10 ?

                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>

                                  {cellProps.cellData && cellProps.cellData.length > 10 ? <IToolTip value={cellProps.cellData} target={'Regulation' +cellProps.rowIndex} placement='bottom' /> : ""}

                                </Content>
                                )}
                            </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey="remarks"
                    label="Remarks"
                    width={100} flexGrow={1} >
                        <Cell>
                        <HeadCell disableSort>
                            {(cellProps) => (
                                <Content>{cellProps.label}</Content>)
                            }
                        </HeadCell>
                            <RowCell>
                            {(cellProps) => (
                                  <Content><Label id={'remark' +cellProps.rowIndex} >

                                  {cellProps.cellData && cellProps.cellData.length > 10 ?

                                  cellProps.cellData.substring(0,10) + "..." : cellProps.cellData}</Label>

                                  {cellProps.cellData && cellProps.cellData.length > 10? <IToolTip value={cellProps.cellData} target={'remark' +cellProps.rowIndex} placement='bottom' /> : ""}

                                </Content>
                                )}
                            </RowCell>
                        </Cell>
                    </IColumn>

                   <IColumn
                            label='Result'
                            dataKey='result'
                            width={80} flexGrow={1} >
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
                </>
             );
        
    }
}
