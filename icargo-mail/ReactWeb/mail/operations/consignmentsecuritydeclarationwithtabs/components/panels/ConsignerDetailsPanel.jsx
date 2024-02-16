import React, { PureComponent, Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'


export default class ConsignerDetailsPanel extends React.PureComponent{
    constructor(props) {
        super(props);
    }

    render(){

        const results = (this.props.concatenatedConsignerDetails.length===0?this.props.ConsignerDetails:this.props.concatenatedConsignerDetails)
        const rowCount = results.length;

        return(
            <div className="inner-panel">
            <ITable
               rowCount ={rowCount}
               headerHeight={35}
               className="table-list"
               gridClassName=""
               headerClassName=""
               rowHeight={70}
               rowClassName="table-row"
               tableId="consignerDetailstable"
               data ={results}
                >

                <Columns>
                <IColumn
                    dataKey="screeningMethodCode"
                    label="Cons. Status Code"
                    width={250}
                    flexGrow={0}>
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
                    label="Cons. Name/ID"
                    width={300}
                    flexGrow={0}>
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
                    dataKey="countryCode"
                    label="Cons. Country Code"
                    width={250}
                    flexGrow={0}>
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
        )
        
    }
}

