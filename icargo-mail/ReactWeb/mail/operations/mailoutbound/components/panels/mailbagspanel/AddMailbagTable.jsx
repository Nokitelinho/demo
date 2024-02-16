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
export default class AddMailbagTable extends Component {

    constructor(props) {
        super(props);
        this.selectedMails = []
        this.rowindex = '';
        this.state = {
            rowIndex: '',
        }
    }
   
    populateMailbagId = (rowIndex) => {
        this.props.populateMailbagId(rowIndex)
    }

    onSelect = (event) => {
        let index = event.target.dataset.index;
        if (event.target.checked == true) {
            // count = ++count
            this.selectedMails.push(index);
            this.rowindex = index;
            this.props.setRowIndex(index);
        }
    }


    render() {

        let results = []
     
        if(this.props.existingMailbagPresent) {
            results = this.props.existingMailbags ? this.props.existingMailbags : '';
        }
        else {
            results=this.props.newMailbags ? this.props.newMailbags : '';
        }
        let registeredorinsuredcode = [];
        let mailcategory = []
        let highestnumbermail = []
        // let mailclass = []
        let companyCode=[];
        if (!isEmpty(this.props.mailbagOneTimeValues)) {

            var ob = [...this.props.mailbagOneTimeValues['mailtracking.defaults.registeredorinsuredcode']];
            ob.sort((a,b) => a.fieldValue - b.fieldValue);
            registeredorinsuredcode = ob.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            mailcategory = this.props.mailbagOneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            var obj = [...this.props.mailbagOneTimeValues['mailtracking.defaults.highestnumbermail']]
            obj.sort((a,b) => a.fieldValue - b.fieldValue);
            highestnumbermail = obj.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            // mailclass = this.props.mailbagOneTimeValues['mailtracking.defaults.mailclass'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            if(isSubGroupEnabled('TURKISH_SPECIFIC')){  //Added by A-7929 as part of ICRD-347376
            companyCode=this.props.mailbagOneTimeValues['mailtracking.defaults.companycode'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
            }
        }

        const rowCount = results ? results.length : 0
        return (
                 
            <Fragment>
                 {this.props.existingMailbagPresent &&
                 <div>Below mailbags are already exists. Do you want to reassign?</div>
                 }
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
                        fixedColumnCount={this.props.existingMailbagPresent?1:2}
                        enableFixedRowScroll
                        hideTopRightGridScrollbar
                        hideBottomLeftGridScrollbar>
                        {/* colHeaders={['Mail bag ID', 'Origin OE', 'Dest OE','Cat','SC','Yr','DSN','RSN','HNI','RI',
                            'Wt','Vol','Scan Date/Time','Rcvd','Dlvd','Dmgd','Seal No','Arival Seal No']}                          
                          */}
                        <Columns >
                        {!this.props.existingMailbagPresent &&
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
                                            <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.onSelect} /></Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        }
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
                                            <Content><ITextField name={`${cellProps.rowIndex}.mailbagId`} maxlength="29" uppercase={true} value={cellProps.cellData} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={this.props.disableForModify?true:false}/></Content>)
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
                                                    <Lov name={`${cellProps.rowIndex}.mailorigin`}  value={cellProps.cellData} id={`mailorigin${cellProps.rowIndex}`} lovTitle="Origin" dialogWidth="600" dialogHeight="473" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_ORIGIN" actionUrl="ux.showAirport.do?formCount=1" disabled={this.props.disableForModify?true:false}  uppercase={true} />
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
                                            <Content><Lov name={`${cellProps.rowIndex}.ooe`} uppercase={true} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} lovTitle="Origin OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/></Content>)
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
                                            <Content><Lov disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.doe`} uppercase={true} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)} lovTitle="Destination OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/></Content>)
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
                                            // <Content> <ISelect name={`${cellProps.rowIndex}.mailCategoryCode`} value={cellProps.cellData}  options={mailcategory} onBlur={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
                                            <Content> <ISelect className=" w-100" name={`${cellProps.rowIndex}.mailCategoryCode`} showTextField={true} searchable={true} onOptionChange={() => this.populateMailbagId(cellProps.rowIndex)}
                                                value={cellProps.cellData} options={mailcategory} disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)}/></Content>)

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
                                            <Content><Lov disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.mailSubclass`} uppercase={true} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1"/></Content>)
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
                                            <Content><ITextField maxlength="1"  componentId="CMP_MAIL_OPERATIONS_OUTBOUND_UX__YEAR"  disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.year`} value={cellProps.cellData} onChange={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
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
                                            <Content><ITextField maxlength="4" componentId="CMP_MAIL_OPERATIONS_ADD_DSN" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.despatchSerialNumber`} value={cellProps.cellData} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} /></Content>)
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
                                            <Content><ITextField maxlength="3" componentId="CMP_MAIL_OPERATIONS_ADD_RSN" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.receptacleSerialNumber`} value={cellProps.cellData} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} /></Content>)
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
                                            <Content><ISelect disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.highestNumberedReceptacle`} value={cellProps.cellData} options={highestnumbermail} onOptionChange={() => this.populateMailbagId(cellProps.rowIndex)} portal = {true}/></Content>)
                                        



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
                                            <Content> <ISelect disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.registeredOrInsuredIndicator`} value={cellProps.cellData} options={registeredorinsuredcode} onOptionChange={() => this.populateMailbagId(cellProps.rowIndex)}  portal = {true}/></Content>)

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
                                                <IMeasure className=" w-100" disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.mailbagWeight`} mode="edit" unitType="MWT" unitSelect={true}
                                                onBlur={() => this.populateMailbagId(cellProps.rowIndex)} onValueUpdate={()=>this.populateMailbagId(cellProps.rowIndex)} displayUnit={this.props.previousRowWeight?this.props.previousRowWeight:null} /> </div>
                                                {/*<ITextField name={`${cellProps.rowIndex}.mailbagWeight`} value={cellProps.cellData} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} /> */}</Content>)
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
                                            <Content><ITextField disabled={cellProps.rowData.disabled?cellProps.rowData.disabled: (cellProps.rowData.mailbagId && cellProps.rowData.mailbagId.length==12? true:this.props.disableForModify?true:false)} name={`${cellProps.rowIndex}.mailbagVolume`} value={cellProps.cellData} /></Content>)
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
                                width={120} dataKey="scannedTime">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField  disabled={ isSubGroupEnabled('AA_SPECIFIC') ?true:false} name={`${cellProps.rowIndex}.scannedTime`} value={cellProps.cellData} /></Content>)
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
                                            <Content><Lov name={`${cellProps.rowIndex}.carrier`} value={cellProps.cellData} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" uppercase={true}/></Content>)
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
                                width={100} dataKey="arrivalSealNumber">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><ITextField name={`${cellProps.rowIndex}.arrivalSealNumber`} value={cellProps.cellData} disabled={this.props.disableForModify?true:false}/></Content>)
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
                                            <Content><ITextField name={`${cellProps.rowIndex}.mailRemarks`} value={cellProps.cellData} disabled={this.props.disableForModify?true:false} /></Content>)
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
                                            <Content><ITextField name={`${cellProps.rowIndex}.bellyCartId`} value={cellProps.cellData} disabled={this.props.disableForModify?true:false}/></Content>)
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
                                            <Content><ITextField name={`${cellProps.rowIndex}.acceptancePostalContainerNumber`} value={cellProps.cellData} /></Content>)
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
                                            <Content><ICheckbox data-index={cellProps.rowIndex} name={`${cellProps.rowIndex}.reassign`} disabled={!this.props.existingMailbagPresent}/></Content>)
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
AddMailbagTable.propTypes = {
    populateMailbagId:PropTypes.func,
    setRowIndex:PropTypes.func,
    existingMailbagPresent:PropTypes.bool,
    existingMailbags:PropTypes.array,
    newMailbags:PropTypes.array,
    mailbagOneTimeValues:PropTypes.object,
    mailbags:PropTypes.object,
    getMailbagsNewPage:PropTypes.func,
    previousRowWeight:PropTypes.string,
}