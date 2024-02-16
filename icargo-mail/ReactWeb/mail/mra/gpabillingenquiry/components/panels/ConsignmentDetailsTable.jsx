import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import ConsignmentTableHeaderPanel from './ConsignmentTableHeaderPanel.jsx';
import MailbagTableCustomRowPanel from './MailbagTableCustomRowPanel.jsx';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { IBadge } from 'icoreact/lib/ico/framework/component/common/badge';
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid';

export default class ConsignmentDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            openPopover: false
        }

    }

   onRowSelection = (data) => {
         if (data.index > -1) {
             if (data.isRowSelected) {             
                 this.selectedConsignment(data.index);
             } else {
                   this.unSelectConsignment(data.index);
             }
         }
        else if ((data.event) && (data.event.target.checked)) {
                 this.selectAllConsignment(data.selectedIndexes);
             } else if ((data.event) && (!data.event.target.checked)) {
                  this.unSelectAllConsignment();
              }
          
     }

     selectedConsignment = (mailbag) => {
		  let selectedConsignments = this.props.selectedConsignments;
		  selectedConsignments.push(mailbag);
         this.props.saveSelectedConsignmentIndex(selectedConsignments);
     }

     unSelectConsignment = (mailbag) => {
		  let selectedConsignments = this.props.selectedConsignments;
         let index = -1;
         for (let i = 0; i < selectedConsignments.length; i++) {
             var element = selectedConsignments[i];
             if (element === mailbag) {
                 index = i;
                 break;
             }
         }
         if (index > -1) {
            selectedConsignments.splice(index, 1);
        }
         this.props.saveSelectedConsignmentIndex(selectedConsignments);
     } 
    selectAllConsignment = (indexes) => {
       
        this.props.saveSelectedConsignmentIndex(indexes);
    }

    unSelectAllConsignment = () => {
        this.selectedConsignments = []
        this.props.saveSelectedConsignmentIndex(this.selectedConsignments);
    }        

    render() {

        const results = this.props.consignmentdetails ? this.props.consignmentdetails.results : '';
        const rowCount = results?results.length:'';

        return (
            <div className="card-body p-0 flex-column-custom">
                <div className="flex-column-custom">
                        <ITable
                            rowCount={rowCount}
                            headerHeight={35}
                            className="table-list"
                            gridClassName=""
                            headerClassName=""
                            rowHeight={80}
                            rowClassName="table-row"
                            tableId="consignmenttable"
                            customHeader={{
                                customPanel: <ConsignmentTableHeaderPanel />,
                                "pagination": {"page": this.props.consignmentdetails,
                                    getPage: this.props.getNewPage, mode: 'subminimal'
                                },
                            pageable: true,                                 
                            }}

                            data={results}
                            onRowSelection={this.onRowSelection}
                        >
                            <Columns>
                                <IColumn
                                    width={40}
                                    dataKey=""
                                    flexGrow={0}
                                    className="" 
                                    selectColumn={false}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                        </HeadCell>
                                        <RowCell selectOption>
                                            {(cellProps) => (
                                                <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.onSelect} /></Content>)
                                            }                                            
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    label=""
                                    flexGrow={1}
                                    id="ooe"
                                    width={100}
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (<Content>
                                                {<div className="row">
                                                    <div className="col">
                                                        <div className="d-block"><strong>{cellProps.rowData.consignmentNumber}</strong> </div>
                                                        <div className="border-right  pad-r-xs">{cellProps.rowData.origin}-{cellProps.rowData.destination}</div>
                                                        <div className="text-grey">Sub Class:{cellProps.rowData.mailSubClass}</div>
                                                        <div className="text-grey d-block">Cat:{cellProps.rowData.mailCategory}</div>
                                                    </div>
                                                    <div className="col">
                                                        <div className="text-grey d-block">DSN:{cellProps.rowData.dsnNumber}</div>
                                                        <div className="text-grey d-block">Curr:{cellProps.rowData.currency}</div>
                                                        <div className="text-grey d-block">Rate:{cellProps.rowData.rate}</div>
                                                    </div>
                                                </div>
                                                }
                                            </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                            </Columns>
                        </ITable>
                </div>
            </div>
        );

    }
}
