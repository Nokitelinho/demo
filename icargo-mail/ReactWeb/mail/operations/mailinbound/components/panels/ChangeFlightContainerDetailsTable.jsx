import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import PropTypes from 'prop-types';

export default class ChangeFlightContainerDetailsTable extends Component{

    constructor(props) {
        super(props);
         this.state = {
             latestIndex:-1
         }
      
    }
    addRow=()=> {
        this.props.addRow()
    }
    onContainerRowSelection=(data)=>{
        if(data.index!==-1)
             this.setState({latestIndex:data.index});
        this.props.setLatestIndex(data);
    }
    highlightRowOnSelection=(data)=>{
        if(data.index!==-1){
                if(this.state.latestIndex!==-1){
                    if(this.state.latestIndex===data.index){
                        return 'table-row table-row__bg-red text-primary' 
                    }
                }
        }
        return 'table-row' 

    }
      render() {

        const results = this.props.containerData ? this.props.containerData : '';

        const rowCount = results ? results.length : 0
        return (
      <div className="d-flex" style={{ height: '200px' }}>
               

      
                       <ITable
                         customHeader={{
                             "pagination": {
                                    "page": this.props.containerData ? this.props.containerData : {},
                                    getPage: this.props.listChangeFlightMailPanel, mode: 'subminimal'
                                },
                            pageable: true, 
                             }}
                            rowCount={rowCount}
                            headerHeight={35}
                            className="table-list"
                            gridClassName="table_grid"
                            headerClassName="table-head"
                            noRowsRenderer={() => { }} 
                    rowHeight={35}
                            rowClassName={this.highlightRowOnSelection}
                            onRowSelection={this.onContainerRowSelection}
                            //data={(this.props.containerData&&this.props.containerData.results)?this.props.containerData.results:null}
                            data={(this.props.containerData&&this.props.containerData.results)?this.props.containerData.results:null}
                            tableId='containerDetailslisttable'
                            name='ContainerDetailsOnPopupTable'>

                            <Columns >

                                <IColumn
                                    label="Container No"
                            width={110}
                            flexGrow={0}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.containerNumber?cellProps.rowData.containerNumber:cellProps.rowData.containerno} </Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    label="POU"
                                        width={70} >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.pou}   </Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    label="Destination"
                            width={90}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.destination}  </Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    label="No of bags"
                                        width={70} >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.bags?cellProps.rowData.bags
                                                        :cellProps.rowData.mailBagCount?cellProps.rowData.mailBagCount.split('-')[1]:'0'}</Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    label="Weight"
                            width={70}
                            flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.totalWeight?cellProps.rowData.totalWeight.roundedDisplayValue
                                                    :cellProps.rowData.mailBagWeight ? cellProps.rowData.mailBagWeight.split('-')[1] : '0'} </Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>


                            </Columns>

                        </ITable> 
                        </div>
        );
}
}
ChangeFlightContainerDetailsTable.propTypes = {
    addRow:PropTypes.func,
    setLatestIndex:PropTypes.func,
    containerData:PropTypes.array,
    listChangeFlightMailPanel:PropTypes.func
}