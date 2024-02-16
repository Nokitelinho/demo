import React from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';


export default class MailNoDataPanel extends React.Component {
    render() {
        return (
           
                            <ITable
                                rowCount={0}
                                headerHeight={45}
                                className="table-list"
                                rowHeight={45}
                                rowClassName=""
                                sortEnabled={false}
                                data={[]}
                                tableId='maillisttable'
                                noRowsRenderer={() => (<div></div>)}  >
                                
                <Columns>

                   <IColumn
                        dataKey=""
                        label="Mailbag Id"
                        width={140} 
                        flexGrow={1}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content><h5>{cellProps.cellData}</h5></Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                        <IColumn
                        dataKey=""
                        label="Cont. No"
                        width={80} 
                        flexGrow={1}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content><h5>{cellProps.cellData}</h5></Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey=""
                        label="POU"
                        width={30} 
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
                        label="Dest"
                        width={30} 
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
                        label="Offload Reason"
                        width={50} 
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
                        label="Remarks"
                        width={30} 
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
                     
                     
                   
                </Columns>
            </ITable>
                  
          
        );
    }
}
