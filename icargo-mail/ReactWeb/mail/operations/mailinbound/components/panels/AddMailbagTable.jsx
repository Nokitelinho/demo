import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time'
import { ITextField,ISelect} from 'icoreact/lib/ico/framework/html/elements'
import {Lov} from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker }  from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IMeasure } from 'icoreact/lib/ico/framework/component/business/measure';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
export default class AddMailbagTable extends Component {

   constructor(props) {
        super(props);
        this.selectedMails = []
        // this.rowindex = '';
        this.state = {
            rowIndex: '',
        }
    }
    /*addRow = () => {
        this.props.addRow()
    }
    onDeleteRow=()=> {
        this.props.onDeleteRow(this.props.rowindex)
    }*/
    populateMailbagId=(rowIndex)=>{
        this.props.populateMailbagId(rowIndex)
    }
    
    onSelect = (event) => {
        let index = event.target.dataset.index;
        if (event.target.checked == true) {
           // count = ++count
            this.selectedMails.push(index);
            this.props.setRowIndex(index);
        }
    }
   
 
    render() {

        const results = this.props.newMailbags ? this.props.newMailbags : '';
      let registeredorinsuredcode=[];
      let mailcategory=[]
      let highestnumbermail=[]
      let companyCode=[]
       if (!isEmpty(this.props.mailbagOneTimeValues)) {
              
        registeredorinsuredcode = this.props.mailbagOneTimeValues['mailtracking.defaults.registeredorinsuredcode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        mailcategory = this.props.mailbagOneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        highestnumbermail = this.props.mailbagOneTimeValues['mailtracking.defaults.highestnumbermail'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
       //Added by A-8893 as part of IASCB-18938
        if(isSubGroupEnabled('TURKISH_SPECIFIC')){ companyCode = this.props.mailbagOneTimeValues['mailtracking.defaults.companycode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
       }
    }

        const rowCount = results ? results.length : 0
        return (
                <div className="d-flex" style={{ height: '380px' }}>
               
                         <IMultiGrid
                            rowCount={rowCount}
                            headerHeight={35}
                            className="table-list"
                            gridClassName="table_grid"
                            headerClassName="table-head"
                        rowClassName="table-row"
                            rowHeight={50}
                            data={this.props.newMailbags?this.props.newMailbags:[]}
                            additionalData={this.props.previousWeightUnit?this.props.previousWeightUnit:null}
                            form={true}
                            name='newMailbagsTable'
                            onRowSelection={this.onRowSelection}
                            destroyFormOnUnmount={true}
                            fixedRowCount={1}
                            fixedColumnCount={2}
                            enableFixedRowScroll
                            hideTopRightGridScrollbar
                            hideBottomLeftGridScrollbar>
                            {/* colHeaders={['Mail bag ID', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI',
                            'Wt','Vol','Scan Date/Time','Rcvd','Dlvd','Dmgd','Seal No','Arival Seal No']}                          
                          */}
                            <Columns >
                            <IColumn
                                width={40}
                                dataKey=""
                                className="first-column align-items-center"
                                selectColumn={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.onSelect} /></Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                                <IColumn
                                    label="Mailbag ID" dataKey="mailbagId" 
                                        width={330} >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.mailbagId`} uppercase={true} maxlength="29" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled:false} value={cellProps.cellData}  onBlur={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                <IColumn
                                dataKey="mailOrigin"
                                label="Origin"
                                width={100}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => 
                                               (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailOrigin`}  value={cellProps.cellData} id={`mailOrigin${cellProps.rowIndex}`} lovTitle="Origin" dialogWidth="600" dialogHeight="473" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_ORIGIN" actionUrl="ux.showAirport.do?formCount=1" disabled={this.props.disableForModify?true:false}  uppercase={true} />
                                                </Content>)
                                        
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailDestination"
                                label="Destination"
                                width={100}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => 
                                               (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailDestination`}  value={cellProps.cellData}  lovTitle="Destination" dialogWidth="600" dialogHeight="473" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_DESTINATION" actionUrl="ux.showAirport.do?formCount=1" disabled={this.props.disableForModify?true:false}  uppercase={true} />
                                                </Content>)
                                        
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                                 <IColumn
                                    label="Origin OE"  dataKey="ooe" 
                                        width={120} >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>
												<Lov maxlength="6" name={`${cellProps.rowIndex}.ooe`} uppercase={true} value={cellProps.cellData} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} lovTitle="Origin OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" onChange={() => this.populateMailbagId(cellProps.rowIndex)}/>
												</Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Dest. OE" dataKey="doe" 
                                        width={120} >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>
												<Lov maxlength="6" name={`${cellProps.rowIndex}.doe`} uppercase={true} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData} lovTitle="Destination OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" onChange={() => this.populateMailbagId(cellProps.rowIndex)} />
												</Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Category Code"
                                        width={120} dataKey="mailCategoryCode" >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                               <Content> <ISelect className=" w-100" portal={true} name={`${cellProps.rowIndex}.mailCategoryCode`} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)}  value={cellProps.cellData}  options={mailcategory} onChange={() => this.populateMailbagId(cellProps.rowIndex)} searchable= {true}/></Content>)
                                           
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Sub class"
                                        width={100} dataKey="mailSubclass">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>
												<Lov maxlength="2" name={`${cellProps.rowIndex}.mailSubclass`} uppercase={true} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)}  value={cellProps.cellData} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1"  onChange={() => this.populateMailbagId(cellProps.rowIndex)} />
												</Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Year"
                                        width={50} dataKey="year">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField componentId="CMP_MAIL_OPERATIONS_INBOUND_UX_YEAR" name={`${cellProps.rowIndex}.year`}  maxlength="1" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)} /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="DSN"
                                        width={60} dataKey="despatchSerialNumber">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.despatchSerialNumber`} componentId="CMP_MAIL_OPERATIONS_INBOUND_UX_DSN" maxlength="4" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData}  onChange={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="RSN"
                                        width={60} dataKey="receptacleSerialNumber">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.receptacleSerialNumber`} componentId="CMP_MAIL_OPERATIONS_INBOUND_UX_RSN" maxlength="3" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)} /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="HNI"
                                        width={60} dataKey="highestNumberedReceptacle">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content> <ISelect name={`${cellProps.rowIndex}.highestNumberedReceptacle`} portal={true} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)}  value={cellProps.cellData}  options={highestnumbermail} onChange={() => this.populateMailbagId(cellProps.rowIndex)} searchable= {true}/></Content>)
                                               
                    
                                            
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="RI"
                                        width={60} dataKey="registeredOrInsuredIndicator">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content> <ISelect name={`${cellProps.rowIndex}.registeredOrInsuredIndicator`} portal={true} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData}  options={registeredorinsuredcode} onChange={() => this.populateMailbagId(cellProps.rowIndex)} searchable= {true}/></Content>)
                                           
                                           }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Weight"
                                        width={120} dataKey="weight">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                         {(cellProps) => {
                                             let isDisabled =  cellProps.rowData.disabled ? cellProps.rowData.disabled : cellProps.rowData.weight.disabled;                                             
                                             return (
                                                <Content> 
                                                    <div className=" w-100" style={{height:'32px'}}>   
                                                    <IMeasure className=" w-100" portal={true} name={`${cellProps.rowIndex}.weight`} mode="edit" displayUnit={cellProps.additionalData} unitType="MWT" unitSelect={true} disabled={isDisabled}
                                                    onBlur={() => this.populateMailbagId(cellProps.rowIndex)} onValueUpdate={() => this.populateMailbagId(cellProps.rowIndex)}/> 
                                               </div> </Content>);
                                         }
                                        }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Volume"
                                        width={70} dataKey="volume">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.volume`}disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} value={cellProps.cellData} /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                 <IColumn
                                    label="Scanned Date"
                                        width={130} dataKey="scannedDate">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    {isSubGroupEnabled('AA_SPECIFIC')?  <ITextField className="form-control" name={`${cellProps.rowIndex}.scannedDate`} disabled={true}/>: <DatePicker name={`${cellProps.rowIndex}.scannedDate`} value={cellProps.cellData}/>}
                                           </Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Scan Time"
                                        width={80} dataKey="scannedTime">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                <TimePicker disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false} name={`${cellProps.rowIndex}.scannedTime`}  value={cellProps.cellData}/></Content>)

                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                {isSubGroupEnabled('TURKISH_SPECIFIC') &&<IColumn
                                label="Company Code"
                                width={100} dataKey="mailCompanyCode">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content> <ISelect portal={true} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:false)} name={`${cellProps.rowIndex}.mailCompanyCode`} value={cellProps.cellData} options={companyCode} onOptionChange={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>}
                                <IColumn
                                    label="Seal No"
                                        width={100} dataKey="sealNumber">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.sealNumber`} value={cellProps.cellData}  /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Remarks"
                                        width={120} dataKey="remarks">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.remarks`} value={cellProps.cellData}  /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    label="Arrival SealNumber"
                                    className="last-column"
                                        width={145} dataKey="arrivalSealNumber">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                         <RowCell>
                                            {(cellProps) => (
                                                <Content><ITextField name={`${cellProps.rowIndex}.arrivalSealNumber`} value={cellProps.cellData}  /></Content>)
                                            }
                                        </RowCell>
                                       
                                    </Cell>
                                </IColumn>
                            </Columns>
                        </IMultiGrid>
                  


                  
                       
                    </div>

                           
                         
                    );

    }
}
AddMailbagTable.propTypes = {
    populateMailbagId:PropTypes.func,
    setRowIndex:PropTypes.func,
    newMailbags:PropTypes.array,
    mailbagOneTimeValues:PropTypes.array,
    previousWeightUnit:PropTypes.string,
    
}
