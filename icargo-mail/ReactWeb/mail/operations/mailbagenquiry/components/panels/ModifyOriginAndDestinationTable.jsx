import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import PropTypes from 'prop-types';
import { ITextField, ISelect,ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IMeasure } from 'icoreact/lib/ico/framework/component/business/measure';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
export default class ModifyOriginAndDestinationTable extends Component {

    constructor(props) {
        super(props);
        this.selectedMails = []
        this.rowindex = '';
        this.state = {
            rowIndex: '',
        }
    }
   
     isDisableRequired=(cellProps,fromOrigin)=>{
        if(!this.props.dummyAirportForDomMail||this.props.dummyAirportForDomMail===null) {
           return true;
        }
        else if(fromOrigin&&cellProps&&cellProps.rowData&&cellProps.rowData.mailorigin&&cellProps.rowData.mailorigin===this.props.dummyAirportForDomMail){
             return false;
         }
         else if(!fromOrigin&&cellProps&&cellProps.rowData&&cellProps.rowData.mailDestination&&cellProps.rowData.mailDestination===this.props.dummyAirportForDomMail){
            return false;
        }
       else{
       return true;
       } 
    }

    checkValidOrgAndDest=(rowIndex,isOrgUpd)=>{
     if(isOrgUpd){
            const malOrg=this.props.newMailbagsTable[rowIndex].mailorigin;   
            this.props.checkValidOrgAndDest(malOrg,isOrgUpd);
        }
        else{
            const malDest=this.props.newMailbagsTable[rowIndex].mailDestination; 
            this.props.checkValidOrgAndDest(malDest,isOrgUpd); 
        }
   
    }

    render() {

        let  selectedMailbagsDetails= [];
        let results=[];
        selectedMailbagsDetails=this.props.selectedMailbags ? this.props.selectedMailbags : '';
        let mailData=[];
        selectedMailbagsDetails.forEach(function(element) { 
            let mail={...element,
            mailbagWeight:{ displayValue:element.weight.displayValue,roundedDisplayValue: element.weight.roundedDisplayValue,displayUnit:element.weight.displayUnit,unitSelect:"true"}
            }
            mailData.push(mail);
            }, this);
         results=mailData;
        
        const rowCount = results ? results.length : 0
        return (
                 
            <Fragment>
                <div className="d-flex" style={{height:380}}>
                    <IMultiGrid
                        customHeader={{
                            placement:'dynamic',
                            headerClass: '',
                            "pagination": { "page": this.props.mailbags, getPage: this.props.getMailbagsNewPage, "mode": 'minimal' },
                           
                        }}
                        rowCount={rowCount}
                        headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        headerClassName=""
                        rowHeight={45}
                        data={results}
                        form={true}
                        name='newMailbagsTable'
                        tableId='addmailbagstable'
                        onRowSelection={this.onRowSelection}
                        rowClassName="table-row"
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
                            className="align-items-center"
                            selectColumn={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content></Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                     {(cellProps) => (
                                        <Content><input type="checkbox" data-index={cellProps.rowIndex}  /></Content>)
                                    } 
                                </RowCell>
                            </Cell>
                        </IColumn>
                        
                            <IColumn
                                label="Mailbag ID" dataKey="mailbagId"
                                width={260} id="mailbagId">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.mailbagId`} maxlength="29" uppercase={true} value={cellProps.cellData} disabled={true}  /></Content>)
                                        }  
                                    </RowCell>
 
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailorigin"
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
                                                    <Lov name={`${cellProps.rowIndex}.mailorigin`}  value={cellProps.cellData} id={`mailorigin${cellProps.rowIndex}`} maxlength="3"lovTitle="Origin" dialogWidth="600" dialogHeight="473" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_ORIGIN" actionUrl="ux.showAirport.do?formCount=1"  onBlur={() => this.checkValidOrgAndDest(cellProps.rowIndex,true)}  uppercase={true} />
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
                                                    <Lov name={`${cellProps.rowIndex}.mailDestination`}  value={cellProps.cellData} maxlength="3" lovTitle="Destination" dialogWidth="600" dialogHeight="473" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_DESTINATION" actionUrl="ux.showAirport.do?formCount=1" onBlur={() => this.checkValidOrgAndDest(cellProps.rowIndex,false)} uppercase={true} />
                                                </Content>)
                                        
                                        } 
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Origin OE" dataKey="ooe"
                                width={120} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><Lov name={`${cellProps.rowIndex}.ooe`} uppercase={true} value={cellProps.cellData}  disabled={true} lovTitle="Origin OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/></Content>)
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
                                            <Content><Lov disabled={true} name={`${cellProps.rowIndex}.doe`} uppercase={true} value={cellProps.cellData}  lovTitle="Destination OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/></Content>)
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
                                            <Content> <ITextField className=" w-100" name={`${cellProps.rowIndex}.mailCategoryCode`} 
                                                value={cellProps.cellData} disabled={true}/></Content>)

                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Sub class"
                                width={120} dataKey="mailSubclass">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><Lov disabled={true} name={`${cellProps.rowIndex}.mailSubclass`} uppercase={true} value={cellProps.cellData} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1"/></Content>)
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
                                            <Content><ITextField maxlength="1"  componentId="CMP_MAIL_OPERATIONS_OUTBOUND_UX__YEAR"  disabled={true} name={`${cellProps.rowIndex}.year`} value={cellProps.cellData} /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="DSN"
                                width={70} dataKey="despatchSerialNumber">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField maxlength="4" componentId="CMP_MAIL_OPERATIONS_ADD_DSN" disabled={true} name={`${cellProps.rowIndex}.despatchSerialNumber`} value={cellProps.cellData}  /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="RSN"
                                width={70} dataKey="receptacleSerialNumber">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField maxlength="3" componentId="CMP_MAIL_OPERATIONS_ADD_RSN" disabled={true} name={`${cellProps.rowIndex}.receptacleSerialNumber`} value={cellProps.cellData}/></Content>)
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
                                            <Content><ITextField disabled={true} name={`${cellProps.rowIndex}.highestNumberedReceptacle`} value={cellProps.cellData}/></Content>)



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
                                            <Content> <ITextField disabled={true} name={`${cellProps.rowIndex}.registeredOrInsuredIndicator`} value={cellProps.cellData} /></Content>)

                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Weight"
                                width={120} dataKey="mailbagWeight">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                       {(cellProps) => (
                                             <Content>
                                                <div className=" w-100" style={{height:'32px'}}>
                                                <IMeasure className=" w-100" disabled={true} name={`${cellProps.rowIndex}.mailbagWeight`} mode="edit" unitType="MWT" unitSelect={true}
                                                  /> </div></Content> )
                                       }
                                        
                                    </RowCell> 
                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Volume"
                                width={70} dataKey="mailbagVolume">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField disabled={true} name={`${cellProps.rowIndex}.mailbagVolume`} value={cellProps.cellData} /></Content>)
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
                                                   <ITextField className="form-control" name={`${cellProps.rowIndex}.scannedDate`} disabled={true}/>
                                 
                                                </Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Scan Time"
                                width={120} dataKey="scannedTime">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField  disabled={true} name={`${cellProps.rowIndex}.scannedTime`} value={cellProps.cellData} /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Carrier"
                                width={120} dataKey="Carrier">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><Lov  disabled={true}  name={`${cellProps.rowIndex}.carrier`} value={cellProps.cellData} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" uppercase={true}/></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Seal No"
                                width={100} dataKey="arrivalSealNumber">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField  disabled={true}  name={`${cellProps.rowIndex}.arrivalSealNumber`} value={cellProps.cellData} /></Content>)
                                        }
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                            dataKey=""
                            label="Dmgd"
                            width={60}
                          //  flexGrow={0}
                            id="damage"
                            className="justify-content-center"
                            selectColumn={true}
                           >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.rowData.damageFlag === 'Y'?  <ICheckbox  name={`${cellProps.rowIndex}.damageFlag`} disabled="true"/> :
                                    <ICheckbox  name={`${cellProps.rowIndex}.damage`} disabled="true"/> }</Content>)
                                    } 
                                </RowCell>
                            </Cell>
                        </IColumn>
                            <IColumn
                                label="Remarks"
                                width={80} dataKey="mailRemarks">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                       {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.mailRemarks`} value={cellProps.cellData}  disabled={true}  /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Belly Cart ID"
                                width={100} dataKey="bellyCartId">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.bellyCartId`} value={cellProps.cellData}  disabled={true} /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="PA Built ULD"
                                width={100} dataKey="acceptancePostalContainerNumber">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.acceptancePostalContainerNumber`} value={cellProps.cellData}  disabled={true}  /></Content>)
                                        }
                                    </RowCell>

                                </Cell>
                            </IColumn>
                            <IColumn
                                label="Reassign"
                                width={80}
                                dataKey =""
                                className="justify-content-center"
                                selectColumn={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ICheckbox data-index={cellProps.rowIndex} name={`${cellProps.rowIndex}.reassign`}  disabled={true} /></Content>)
                                        } 
                                    </RowCell>

                                </Cell>
                            </IColumn>
                        </Columns>
                    </IMultiGrid>
                </div>
               
            </Fragment>

        );

    }
}
ModifyOriginAndDestinationTable.propTypes = {
    populateMailbagId:PropTypes.func,
    setRowIndex:PropTypes.func,
    
    existingMailbags:PropTypes.array,
    newMailbags:PropTypes.array,
    mailbagOneTimeValues:PropTypes.object,
    mailbags:PropTypes.object,
    getMailbagsNewPage:PropTypes.func,
    previousRowWeight:PropTypes.string,
}