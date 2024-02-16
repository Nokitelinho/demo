import React, { Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import NoDataExcelPanel from './NoDataExcelPanel.jsx';
import AddMailbagTabPanel from './AddMailbagTabPanel.jsx'
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
export default class NoDataPanel extends React.Component {
    render() {
        return (
            <Fragment>
                <AddMailbagTabPanel activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} />
                {
                    this.props.activeMailbagAddTab === 'EXCEL_VIEW' &&
                    <NoDataExcelPanel />
                }
                {
                    this.props.activeMailbagAddTab === 'NORMAL_VIEW' &&
                        <ITable
                            name="mailBagsTable"
                            rowCount={0}
                            headerHeight={35}
                            className="table-list"
                            rowHeight={45}
                            tableId="mailBagsTable"
                            sortEnabled={false}
                            rowClassName=""
                            data={[]}
                            //onRowSelection={this.onSelectmultiple}
                            noRowsRenderer={() => (null)}>

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
                                    dataKey="mailId"
                                    label="Maibag Id"
                                    // width={140}
                                    flexGrow={0}
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
                                    dataKey="originExchangeOffice"
                                    label="Origin OE"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="destinationExchangeOffice"
                                    label="Dest OE"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="mailCategoryCode"
                                    label="Cat"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="mailClass"
                                    label="Class"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="mailSubclass"
                                    label="SC"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="year"
                                    label="Yr"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="dsn"
                                    label="DSN"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="receptacleSerialNumber"
                                    label="RSN"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="statedBags"
                                    label="Std Bags"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="highestNumberedReceptacle"
                                    label="HNI"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="registeredOrInsuredIndicator"
                                    label="RI"
                                    // width={50}
                                    flexGrow={1}>
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
                                    dataKey="weight"
                                    label="Wt"
                                    // width={50}
                                    flexGrow={1}>
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
                                {isSubGroupEnabled('TURKISH_SPECIFIC') &&
                                <IColumn
                                    dataKey="declaredValue"
                                    label="Declared Value"
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
                                                <Content><h5>{cellProps.cellData}</h5></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>}
                                {isSubGroupEnabled('TURKISH_SPECIFIC') &&
                                <IColumn
                                    dataKey="currencyCode"
                                    label="Currency"
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
                                                <Content><h5>{cellProps.cellData}</h5></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>}
                                <IColumn
                                    dataKey="uldNumber"
                                    label="ULD No"
                                    // width={50}
                                    flexGrow={1}>
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
                            dataKey="mailOrigin"
                            label="Origin"
                            // width={50}
                            flexGrow={1}>
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
                            dataKey="mailDestination"
                            label="Destination"
                            // width={65}
                            flexGrow={1}>
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
                            dataKey="reqDeliveryTime"
                            label="RDT"
                            // width={40}
                            flexGrow={1}>
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
                            dataKey="transWindowEndTime"
                            label="TSW"
                            // width={40}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                   
                                    return(
                                        <Content><ITextField  uppercase={true} name={`${cellProps.rowIndex}.transWindowEndTime`} id={`transWindowEndTime${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex}  disabled={true} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                       
                        
                        <IColumn
                            dataKey="mailServiceLevel"
                            label="Service level"
                            // width={65}
                            flexGrow={1}>
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
                            </Columns>
                        </ITable>
                }
            </Fragment>
        );
    }
}