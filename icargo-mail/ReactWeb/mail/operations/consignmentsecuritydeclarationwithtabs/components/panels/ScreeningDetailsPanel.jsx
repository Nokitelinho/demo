import React, { Component, Fragment } from 'react';
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'


export default class ScreeningDetailsPanel extends React.Component{
    constructor(props) {
        super(props);
    }

    rowAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        let ScreeningDetails= (this.props.concatenatedScreeningDetails.length===0?this.props.ScreeningDetails:this.props.concatenatedScreeningDetails)
        let EditScreeningDetails = ScreeningDetails[index]
        if (actionName === 'DELETE') {
             this.props.rowActionDelete({index: index ,ScreeningDetails:ScreeningDetails });
        }
        if (actionName === 'EDIT'){
            this.props.rowActionEdit( EditScreeningDetails, index);
        }
    }

    render(){
       // const results = (this.props.ScreeningDetails ? this.props.ScreeningDetails : '')||(this.props.newScreeningDetails?this.props.newScreeningDetails:'');
        //const results= (this.props.newScreeningDetails)? this.props.ScreeningDetails.concat(this.props.newScreeningDetails):this.props.ScreeningDetails;
        
        const results = (this.props.concatenatedScreeningDetails.length===0?this.props.ScreeningDetails:this.props.concatenatedScreeningDetails)
        const rowCount = results.length;

        return(
             <div className="inner-panel">
       <ITable
               form={true}
               rowCount ={rowCount}
               headerHeight={35}
               className="table-list"
               headerClassName=""
               rowHeight={70}
               rowClassName="table-row"
               tableId="screeningDetailsTable"
               name="screeningDetailsTable"
               data ={results}
                >

                <Columns>
                
                <IColumn
                    dataKey="screeningLocation"
                    label="Screened Location"
                    width={140}
                    flexGrow={1}
                    id="screeningLocation">
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
                    dataKey="screeningMethodCode"
                    label="Method"
                    width={80}
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
                    dataKey="statedBags"
                    label="No. of Bags"
                    width={90}
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
                    dataKey="statedWeight"
                    label="Weight"
                    width={90}
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
                    dataKey="securityStatusParty"
                    label="Security Status Issued by"
                    width={160}
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
                    dataKey="securityStatusDate"
                    label="Date and Time"
                    width={180}
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
                    dataKey="screeningAuthority"
                    label="SM Applicable Authority"
                    width={120}
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
                    dataKey="screeningRegulation"
                    label="SM Applicable Regulation"
                    width={300}
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
                    dataKey="additionalSecurityInfo"
                    label="Additional Security Information"
                    width={170}
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
                    dataKey="remarks"
                    label="Remarks"
                    width={130}
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
                    dataKey=""
                    label=""
                    width={100}
                    flexGrow={1}>
                        <Cell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>
                                        {(cellProps.rowData.result === 'F')?
                                        <span className="badge badge-pill light badge-error">Fail</span>:
                                        <span className="badge badge-pill light badge-active">Pass</span>}
                                        </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                    dataKey=""
                    label=""
                    width={null}
                    flexGrow={null}>
                        <Cell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>
                                        <IDropdown className="drop-down-button">
                                        <IDropdownToggle>
                                            <i className="icon ico-v-ellipsis"></i>
                                        </IDropdownToggle>
                                        <IDropdownMenu >
                                            <IDropdownItem data-mode="EDIT" data-index={cellProps.rowIndex} onClick={this.rowAction}>Edit</IDropdownItem>
                                            <IDropdownItem data-mode="DELETE" data-index={cellProps.rowIndex} onClick={this.rowAction}>Delete</IDropdownItem>
                                        </IDropdownMenu>
                                        </IDropdown >
                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                </Columns>        
                </ITable>
             
             </div>);
        
    }
}
