import React from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import ServiceFailuresTabPanel from './tabpanels/ServiceFailuresTabPanel.jsx'

export default class NoDataPanel extends React.Component {
    render() {
        return (
            <div className="card mar-t-md">
                <div className="tabs ui-tabs">

                    <div className="card-header p-0 card-header-action ">

                        <ServiceFailuresTabPanel />

                    </div>
                    
                    <div className="card-body p-0">
                        <ITable
                            rowCount={0}
                            headerHeight={45}
                            className="table-list"
                            rowHeight={45}
                            rowClassName=""
                            sortEnabled={false}
                            data={[]}
                            tableId='mailbaglisttable'
                            noRowsRenderer={() => (<div></div>)}  >

                            <Columns>
                                <IColumn
                                    width={32}
                                    dataKey=""
                                    flexGrow={0}
                                    className="" hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                        </HeadCell>
                                        <RowCell selectOption>
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    dataKey=""
                                    label="Mail Bag Id"
                                    width={243}
                                    flexGrow={3}
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
                                    label="Service Level"
                                    width={115}
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
                                    label="Org Airport"
                                    width={88}
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
                                    label="Dest Airport"
                                    width={93}
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
                                    label="RDT"
                                    width={136}
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
                                    label="Actual Time of Delivery"
                                    width={155}
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
                                    label="Current Airport"
                                    width={110}
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
                                    label="Latest Transaction"
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
                                    label="On time Delivery"
                                    width={118}
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
                                    label="Consignment Number"
                                    width={161}
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
                    </div>
                </div>
            </div>

        );
    }
}
