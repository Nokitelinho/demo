import React from 'react';
import { ITable } from 'icoreact/lib/ico/framework/component/common/grid';
import { IColumn, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';

export default class ConsignmentNoDataPanel extends React.Component {
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
                tableId='consignmenttable'
                noRowsRenderer={() => (<div></div>)}  >
                 <Columns> 
                    <IColumn
                        dataKey=""
                        label="Consignment"
                        width={140} 
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
                 </Columns>              
            </ITable>
                  
          
        );
    }
}
