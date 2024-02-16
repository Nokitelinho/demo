import React, { Component } from 'react';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import AddMultiplePanel from './AddMultiplePanel.jsx';
import MailBagCustomPanel from './custompanel/MailBagCustomPanel.jsx';
import { IMeasure } from 'icoreact/lib/ico/framework/component/business/measure';
import { DatePicker }  from 'icoreact/lib/ico/framework/component/common/date';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';



import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

export default class MailbagDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowClkCount: 0
        }
        this.selectedMailBags = [];
        this.selectedMailbagsIndex = [];
    }

    onSelectmultiple = (data) => {
        let count = this.state.rowClkCount;
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                count = ++count
                this.selectMailBags(data.rowData);
                this.props.saveSelectedMailbagsIndex(data.index, 'SELECT');
            } else {
                count = --count
                this.unSelectMailBags(data.rowData);
                this.props.saveSelectedMailbagsIndex(data.index, 'UNSELECT');
            }
        } else {
            if (data.isRowSelected === true) {
                this.props.getTotalCount();
                this.selectedMailbagsIndex = this.props.selectedMailbagsIndex;
                //this.selectAllMailbagsIndex();
                this.selectAllMailBags(this.props.mailbagDetails);
                this.props.saveSelectedMultipleMailbagIndex(data.selectedIndexes);

            } else {
                count = 0
                this.unSelectAllMailBags();
                this.props.saveSelectedMailbagsIndex(data.index, 'UNSELECT');

            }
        }
        this.setState({
            rowClkCount: count
        })

    }

    selectMailBags = (mailBag) => {
        this.selectedMailBags.push(mailBag);
        this.props.selectMailBags(this.selectedMailBags);
    }
    unSelectMailBags = (mailBag) => {
        let index = -1;
        for (let i = 0; i < this.selectedMailBags.length; i++) {
            var element = this.selectedMailBags[i];
            if (element.id === mailBag.id) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedMailBags.splice(index, 1);
        }
        this.props.selectMailBags(this.selectedMailBags);
    }
    selectAllMailBags = (data) => {
        this.selectedMailBags = data
        this.props.selectMailBags(this.selectedMailBags);
    }

    unSelectAllMailBags = () => {
        this.selectedMailBags = []
        this.props.selectMailBags(this.selectedMailBags);
    }

    populateMailbagId = (rowIndex) => {
        this.props.populateMailbagId(rowIndex);
    }

    populateMailbagIdTextField = (event) => {
        this.props.populateMailbagIdTextField(event);
    }

    deleteRow = () => {
        this.props.deleteRow(this.props.selectedMailbagIndex)
    }

    selectMailbagIndex = (mailbag) => {
        this.selectedMailbagsIndex.push(mailbag);
        this.props.saveSelectedMailbagsIndex(this.selectedMailbagsIndex);
    }
    unSelectMailbagIndex = (mailbag) => {
        let index = -1;
        for (let i = 0; i < this.selectedMailbagsIndex.length; i++) {
            var element = this.selectedMailbagsIndex[i];
            if (element === mailbag) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedMailbagsIndex.splice(index, 1);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbagsIndex);
    }
    selectAllMailbagsIndex = () => {

        for (let i = 0; i < this.props.count; i++) {
            this.selectedMailbagsIndex.push(i);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbagsIndex);
    }

    unSelectAllMailbagsIndex = () => {
        this.selectedMailbagsIndex = []
        this.props.saveSelectedMailbagsIndex(this.selectedMailbagsIndex);
    }


    render() {

        let category = [];
        let rsn = [];
        let hni = [];
        let mailClassValue = [];
        let mailServiceLevels = [];
        if (!isEmpty(this.props.oneTimeCat)) {
            category = this.props.oneTimeCat.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeType)) {
            type = this.props.oneTimeType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeSubType)) {
            subType = this.props.oneTimeSubType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeRSN)) {
            rsn = this.props.oneTimeRSN.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeHNI)) {
            hni = this.props.oneTimeHNI.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeMailClass)) {
            mailClassValue = this.props.oneTimeMailClass.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        if (!isEmpty(this.props.oneTimeMailServiceLevel)) {
            mailServiceLevels = this.props.oneTimeMailServiceLevel.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        return (
            <div className="card-body p-0 d-flex">
                {this.props.mailbagDetails.length > 0 ?
                    <IMultiGrid
                    form={true}
                        destroyFormOnUnmount={true}
                    name="mailBagsTable"
                    rowCount={this.props.mailbagDetails.length}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={45}
                    rowClassName="table-row"
                    sortEnabled={false}
                    resetSelectionOnDataChange={true}
                    tableId="mailBagsTable"
                        fixedRowCount={1}
                        fixedColumnCount={2}
                        enableFixedRowScroll
                        hideTopRightGridScrollbar
                        hideBottomLeftGridScrollbar
                    customHeader={
                        {
                            headerClass: '',
                            placement: 'dynamic',
                           
                            "pagination": { "page": this.props.mailBagsList, getPage: this.props.getNewPage },
                            customPanel: <MailBagCustomPanel addRow={this.props.addRow} deleteRow={this.deleteRow} newRowData={this.props.newRowData} lastRowData={this.props.lastRowData}
                                getLastRowData={this.props.getLastRowData} showAddMultiplePanel={this.props.showAddMultiplePanel} showAddMultipleFlag={this.props.showAddMultipleFlag}
                                storeFormToReducer={this.props.storeFormToReducer} isDomestic={this.props.isDomestic} addRowDomestic={this.props.addRowDomestic}
                                    getFormData={this.props.getFormData} selectedMailbagIndex={this.props.selectedMailbagIndex} mailBags={this.props.mailBags} newRowDataDomestic={this.props.newRowDataDomestic} />,
                            exportData: {
                                exportAction: this.props.exportToExcel,
                                pageable: true,
                                    addlColumns: [],
                                name: 'Mail Bags Details'
                            },
                        }}
                    onRowSelection={this.onSelectmultiple}
                    data={this.props.mailbagDetails ? this.props.mailbagDetails : []}>
                    <Columns>
                        <IColumn
                            width={32}
                            dataKey=""
                            flexGrow={0}
                                className="align-items-center first-column" hideOnExport>
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
                                width={260}
                                id="mailId"
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                    let isDisabled =  cellProps.rowData.existingMailbag; 
                                    return (
                                                <Content><ITextField componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_MAILBAGID" uppercase={true} name={`${cellProps.rowIndex}.mailId`} id={`mailId${cellProps.rowIndex}`} type="text" value="abc" maxlength="29" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} /></Content>)
                                        //<Content><ITextField componentId="CMP_Operations_FltHandling_ExportManifest_List" uppercase={true} name={`${cellProps.rowIndex}.statedWeight`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={this.props.isDomestic || `${cellProps.rowIndex}.existingMailbag`} /></Content>)
                                    } 
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="originExchangeOffice"
                            label="Origin OE"
                                width={120}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><Lov maxlength="6" name={`${cellProps.rowIndex}.originExchangeOffice`} id={`originExchangeOffice${cellProps.rowIndex}`} uppercase={true} lovTitle="Origin OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_OOE" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} />
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="destinationExchangeOffice"
                            label="Dest OE"
                                width={120}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                        {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                    return (
                                        <Content>
                                                    <Lov maxlength="6" name={`${cellProps.rowIndex}.destinationExchangeOffice`} id={`destinationExchangeOffice${cellProps.rowIndex}`} uppercase={true} lovTitle="Destination OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_DOE" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} />
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailCategoryCode"
                            label="Cat"
                                width={65}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect portal={true} name={`${cellProps.rowIndex}.mailCategoryCode`} id={`mailCategoryCode${cellProps.rowIndex}`} uppercase={true} options={category} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_CAT" searchable /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailClass"
                            label="Class"
                                width={65}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_CLASS" portal={true} name={`${cellProps.rowIndex}.mailClass`} id={`mailClass${cellProps.rowIndex}`} options={mailClassValue} uppercase={true} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailSubclass"
                            label="SC"
                                width={90}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content>
                                            <Lov maxlength="2" name={`${cellProps.rowIndex}.mailSubclass`} id={`mailSubclass${cellProps.rowIndex}`} uppercase={true} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2"  actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_SC" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} />
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="year"
                            label="Yr"
                                width={50}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content><ITextField maxlength="1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_YR" uppercase={true} name={`${cellProps.rowIndex}.year`} id={`year${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="dsn"
                            label="DSN"
                            width={60}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content><ITextField maxlength="4" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_DSN" uppercase={true} name={`${cellProps.rowIndex}.dsn`} id={`dsn${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="receptacleSerialNumber"
                            label="RSN"
                            width={60}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content><ITextField maxlength="3" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_RSN" uppercase={true} name={`${cellProps.rowIndex}.receptacleSerialNumber`} id={`receptacleSerialNumber${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="statedBags"
                            label="Std Bags"
                            width={70}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content><ITextField componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_NUMBAG" uppercase={true} name={`${cellProps.rowIndex}.statedBags`} id={`statedBags${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex}  disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="highestNumberedReceptacle"
                            label="HNI"
                                width={65}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_HNI" portal={true} name={`${cellProps.rowIndex}.highestNumberedReceptacle`} id={`highestNumberedReceptacle${cellProps.rowIndex}`} options={hni} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="registeredOrInsuredIndicator"
                            label="RI"
                                width={65}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_RI" portal={true} name={`${cellProps.rowIndex}.registeredOrInsuredIndicator`} id={`registeredOrInsuredIndicator${cellProps.rowIndex}`} options={rsn} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailbagWeight"
                            label="Wt"
                            width={150}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : ((cellProps.rowData && cellProps.rowData.mailbagWeight) ? cellProps.rowData.mailbagWeight.disabled : false);
                                    return (
                                                <Content> <div style={{height:'32px'}} className="w-100"><IMeasure className="w-100" componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_WEIGHT" name={`${cellProps.rowIndex}.mailbagWeight`} id={`mailbagWeight${cellProps.rowIndex}`} mode="edit" unitType="MWT" unitSelect={true}
                                        onBlur={() => this.populateMailbagId(cellProps.rowIndex)} 
                                        onValueUpdate={() => this.populateMailbagId(cellProps.rowIndex)} 
                                        disabled={isDisabled} 
                                       // roundedDisplayValue={cellProps.cellData.roundedDisplayValue}
                                                    displayUnit={cellProps.cellData ? cellProps.cellData.displayUnit : ''} 
                                                /></div></Content>)
                                        //<Content><ITextField componentId="CMP_Operations_FltHandling_ExportManifest_List" uppercase={true} name={`${cellProps.rowIndex}.statedWeight`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={this.props.isDomestic || `${cellProps.rowIndex}.existingMailbag`} /></Content>)
                                    }
                                }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        {isSubGroupEnabled('TURKISH_SPECIFIC') &&
                        <IColumn
                            dataKey="declaredValue"
                            label="Declared Value"
                            width={110}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                    let isDisabled =  this.props.isDomestic?this.props.isDomestic:cellProps.rowData.existingMailbag;
                                    return(
                                        <Content><ITextField maxlength="3" uppercase={true} name={`${cellProps.rowIndex}.declaredValue`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>}
                        {isSubGroupEnabled('TURKISH_SPECIFIC') &&<IColumn
                            dataKey="currencyCode"
                            label="Currency"
                            width={80}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                    let isDisabled =  this.props.isDomestic?this.props.isDomestic:cellProps.rowData.existingMailbag;
                                    return(
                                        <Content>
                                            <Lov name={`${cellProps.rowIndex}.currencyCode`} uppercase={true} lovTitle="Currency code" dialogWidth="600" dialogHeight="425" maxlength="3"  actionUrl="ux.showCurrency.do?formCount=1" data-rowIndex={cellProps.rowIndex}/>
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>}
                        <IColumn
                            dataKey="uldNumber"
                            label="ULD No"
                            width={150}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_ULDNUM" uppercase={true} name={`${cellProps.rowIndex}.uldNumber`} id={`uldNumber${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)}/></Content>)
                                        }
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
                                        {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailOrigin`} id={`mailOrigin${cellProps.rowIndex}`} lovTitle="Origin" dialogWidth="600" dialogHeight="520" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_ORIGIN" actionUrl="ux.showAirport.do?formCount=1"  data-rowIndex={cellProps.rowIndex} uppercase={true} />

                                                </Content>)
                                        }
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
                                        {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailDestination`} id={`mailDestination${cellProps.rowIndex}`} lovTitle="Destination" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_DESTINATION" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1"  data-rowIndex={cellProps.rowIndex} uppercase={true} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="reqDeliveryTime"
                                label="RDT"
                                width={150}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>
                                                    <div className="w-100"><DatePicker uppercase={true} name={`${cellProps.rowIndex}.requiredDlvDate`} id={`requiredDlvDate${cellProps.rowIndex}`} componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_RDT" type="text" value="abc" data-rowIndex={cellProps.rowIndex} /></div>
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey=""
                                label=""
                                width={90}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>
                                                    <TimePicker uppercase={true} name={`${cellProps.rowIndex}.requiredDlvTime`} id={`requiredDlvTime${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="transWindowEndTime"
                                label="TSW"
                                width={150}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {


                                            return (
                                                <Content><ITextField uppercase={true} name={`${cellProps.rowIndex}.transWindowEndTime`} id={`transWindowEndTime${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} disabled={true} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>


                            <IColumn
                                dataKey="mailServiceLevel"
                                label="Service level"
                                width={150}
                                className="last-column"
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <ISelect className=" w-100" portal={true} name={`${cellProps.rowIndex}.mailServiceLevel`} id={`mailServiceLevel${cellProps.rowIndex}`} componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_SRVLEVEL" uppercase={true} options={mailServiceLevels}  searchable />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </IMultiGrid>
                    :
                    <IMultiGrid
                        form={true}
                        destroyFormOnUnmount={true}
                        name="mailBagsTable"
                        rowCount={this.props.mailbagDetails.length}
                        headerHeight={35}
                        className="table-list"
                        gridClassName=""
                        headerClassName=""
                        rowHeight={45}
                        rowClassName="table-row"
                        sortEnabled={false}
                        tableId="mailBagsTable"
                        fixedRowCount={1}
                        fixedColumnCount={2}
                        enableFixedRowScroll
                        hideTopRightGridScrollbar
                        hideBottomLeftGridScrollbar
                        customHeader={
                            {
                                headerClass: '',
                                placement: 'dynamic',

                                customPanel: <MailBagCustomPanel addRow={this.props.addRow} deleteRow={this.deleteRow} newRowData={this.props.newRowData} lastRowData={this.props.lastRowData}
                                    getLastRowData={this.props.getLastRowData} showAddMultiplePanel={this.props.showAddMultiplePanel} showAddMultipleFlag={this.props.showAddMultipleFlag}
                                    storeFormToReducer={this.props.storeFormToReducer} isDomestic={this.props.isDomestic} addRowDomestic={this.props.addRowDomestic}
                                    getFormData={this.props.getFormData} selectedMailbagIndex={this.props.selectedMailbagIndex} mailBags={this.props.mailBags} newRowDataDomestic={this.props.newRowDataDomestic} />,
                            }}
                        onRowSelection={this.onSelectmultiple}
                        data={this.props.mailbagDetails ? this.props.mailbagDetails : []}>
                        <Columns>
                            <IColumn
                                width={32}
                                dataKey=""
                                flexGrow={0}
                                className="align-items-center first-column" hideOnExport>
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
                                width={260}
                                id="mailId"
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_MAILBAGID" uppercase={true} name={`${cellProps.rowIndex}.mailId`} id={`mailId${cellProps.rowIndex}`} type="text" value="abc" maxlength="29" data-rowIndex={cellProps.rowIndex} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} /></Content>)
                                            //<Content><ITextField componentId="CMP_Operations_FltHandling_ExportManifest_List" uppercase={true} name={`${cellProps.rowIndex}.statedWeight`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={this.props.isDomestic || `${cellProps.rowIndex}.existingMailbag`} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="originExchangeOffice"
                                label="Origin OE"
                                width={120}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><Lov maxlength="6" name={`${cellProps.rowIndex}.originExchangeOffice`} id={`originExchangeOffice${cellProps.rowIndex}`} uppercase={true} lovTitle="Origin OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_OOE" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="destinationExchangeOffice"
                                label="Dest OE"
                                width={120}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov maxlength="6" name={`${cellProps.rowIndex}.destinationExchangeOffice`} id={`destinationExchangeOffice${cellProps.rowIndex}`} uppercase={true} lovTitle="Destination OE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_DOE" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailCategoryCode"
                                label="Cat"
                                width={65}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect portal={true} name={`${cellProps.rowIndex}.mailCategoryCode`} id={`mailCategoryCode${cellProps.rowIndex}`} uppercase={true} options={category} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_CAT" searchable /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailClass"
                                label="Class"
                                width={65}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_CLASS" portal={true} name={`${cellProps.rowIndex}.mailClass`} id={`mailClass${cellProps.rowIndex}`} options={mailClassValue} uppercase={true} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailSubclass"
                                label="SC"
                                width={90}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov maxlength="2" name={`${cellProps.rowIndex}.mailSubclass`} id={`mailSubclass${cellProps.rowIndex}`} uppercase={true} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_SC" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="year"
                                label="Yr"
                                width={50}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField maxlength="1" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_YR" uppercase={true} name={`${cellProps.rowIndex}.year`} id={`year${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="dsn"
                                label="DSN"
                                width={60}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField maxlength="4" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_DSN" uppercase={true} name={`${cellProps.rowIndex}.dsn`} id={`dsn${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="receptacleSerialNumber"
                                label="RSN"
                                width={60}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField maxlength="3" componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_RSN" uppercase={true} name={`${cellProps.rowIndex}.receptacleSerialNumber`} id={`receptacleSerialNumber${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="statedBags"
                                label="Std Bags"
                                width={70}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_NUMBAG" uppercase={true} name={`${cellProps.rowIndex}.statedBags`} id={`statedBags${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} disabled={isDisabled} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="highestNumberedReceptacle"
                                label="HNI"
                                width={65}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_HNI" portal={true} name={`${cellProps.rowIndex}.highestNumberedReceptacle`} id={`highestNumberedReceptacle${cellProps.rowIndex}`} options={hni} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="registeredOrInsuredIndicator"
                                label="RI"
                                width={65}
                                flexGrow={0}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content> <ISelect componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_RI" portal={true} name={`${cellProps.rowIndex}.registeredOrInsuredIndicator`} id={`registeredOrInsuredIndicator${cellProps.rowIndex}`} options={rsn} onBlur={() => this.populateMailbagId(cellProps.rowIndex)} disabled={isDisabled} searchable /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="mailbagWeight"
                                label="Wt"
                                width={130}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : ((cellProps.rowData && cellProps.rowData.mailbagWeight) ? cellProps.rowData.mailbagWeight.disabled : false);
                                            return (
                                                <Content><div style={{height:'32px'}} className="w-100"><IMeasure className="w-100" componentId="CMB_MAIL_OPERATIONS_UX_CONSIGNMENT_WEIGHT" name={`${cellProps.rowIndex}.mailbagWeight`} id={`mailbagWeight${cellProps.rowIndex}`} mode="edit" unitType="MWT" unitSelect={true}
                                                    onBlur={() => this.populateMailbagId(cellProps.rowIndex)}
                                                    onValueUpdate={() => this.populateMailbagId(cellProps.rowIndex)}
                                                    disabled={isDisabled}
                                                    // roundedDisplayValue={cellProps.cellData.roundedDisplayValue}
                                                    displayUnit={cellProps.cellData ? cellProps.cellData.displayUnit : ''}
                                                /></div></Content>)
                                            //<Content><ITextField componentId="CMP_Operations_FltHandling_ExportManifest_List" uppercase={true} name={`${cellProps.rowIndex}.statedWeight`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={this.props.isDomestic || `${cellProps.rowIndex}.existingMailbag`} /></Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="uldNumber"
                                label="ULD No"
                                width={150}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content><ITextField componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_ULDNUM" uppercase={true} name={`${cellProps.rowIndex}.uldNumber`} id={`uldNumber${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} onBlur={this.populateMailbagIdTextField} disabled={isDisabled} /></Content>)
                                        }
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
                                        {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailOrigin`} id={`mailOrigin${cellProps.rowIndex}`} lovTitle="Origin" dialogWidth="600" dialogHeight="520" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_ORIGIN" actionUrl="ux.showAirport.do?formCount=1" disabled={isDisabled} data-rowIndex={cellProps.rowIndex} uppercase={true} />

                                                </Content>)
                                        }
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
                                        {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                                <Content>
                                                    <Lov name={`${cellProps.rowIndex}.mailDestination`} id={`mailDestination${cellProps.rowIndex}`} lovTitle="Destination" componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_DESTINATION" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" disabled={isDisabled} data-rowIndex={cellProps.rowIndex} uppercase={true} />
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey="reqDeliveryTime"
                                label="RDT"
                                width={150}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                                <Content>
                                                    <div className="w-100"><DatePicker uppercase={true} name={`${cellProps.rowIndex}.requiredDlvDate`} id={`requiredDlvDate${cellProps.rowIndex}`} componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_RDT" type="text" value="abc" data-rowIndex={cellProps.rowIndex} /></div>
                                                </Content>)
                                        }
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey=""
                                label=""
                                width={75}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            return (
                                        <Content>
                                            <TimePicker  uppercase={true} name={`${cellProps.rowIndex}.requiredDlvTime`} id={`requiredDlvTime${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex}  />
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="transWindowEndTime"
                            label="TSW"
                            width={150}
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                   
                                            return (
                                        <Content><ITextField  uppercase={true} name={`${cellProps.rowIndex}.transWindowEndTime`} id={`transWindowEndTime${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex}  disabled={true} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                       
                        
                        <IColumn
                            dataKey="mailServiceLevel"
                            label="Service level"
                            width={150}
                                className="last-column"
                            flexGrow={1}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                            let isDisabled = this.props.isDomestic ? this.props.isDomestic : cellProps.rowData.existingMailbag;
                                            return (
                                        <Content>
                                                    <ISelect className=" w-100" portal={true} name={`${cellProps.rowIndex}.mailServiceLevel`} id={`mailServiceLevel${cellProps.rowIndex}`} componentId="CMP_MAIL_OPERATIONS_UX_CONSIGMENT_DOMESTIC_SRVLEVEL" uppercase={true} options={mailServiceLevels} disabled={isDisabled} searchable />
                                            </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailStatus"
                            label=""
                            width={0}
                            flexGrow={0}
                            >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                             return (
                                        <Content>
                                                     <Content><ITextField  uppercase={true} name={`${cellProps.rowIndex}.mailStatus`} id={`mailStatus${cellProps.rowIndex}`} type="text"  data-rowIndex={cellProps.rowIndex} /></Content>
                                            </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                    </Columns>
                    </IMultiGrid>}
                        {isSubGroupEnabled('TURKISH_SPECIFIC') &&
                        <IColumn
                            dataKey="declaredValue"
                            label="Declared Value"
                            width={110}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                    let isDisabled =  this.props.isDomestic?this.props.isDomestic:cellProps.rowData.existingMailbag;
                                    return(
                                        <Content><ITextField maxlength="3" uppercase={true} name={`${cellProps.rowIndex}.declaredValue`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} disabled={isDisabled} /></Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>}
                        {isSubGroupEnabled('TURKISH_SPECIFIC') &&<IColumn
                            dataKey="currencyCode"
                            label="Currency"
                            width={80}
                            flexGrow={0}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => {
                                    
                                    let isDisabled =  this.props.isDomestic?this.props.isDomestic:cellProps.rowData.existingMailbag;
                                    return(
                                        <Content>
                                            <Lov name={`${cellProps.rowIndex}.currencyCode`} uppercase={true} lovTitle="Currency code" dialogWidth="600" dialogHeight="425" maxlength="3"  actionUrl="ux.showCurrency.do?formCount=1" data-rowIndex={cellProps.rowIndex}/>
                                        </Content>)
                                    }
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>}

                <AddMultiplePanel show={this.props.showAddMultipleFlag} onClose={this.props.onClose} oneTimeCat={this.props.oneTimeCat} oneTimeRSN={this.props.oneTimeRSN} oneTimeHNI={this.props.oneTimeHNI}
                    receptacles={this.props.receptacles} calculateReseptacles={this.props.calculateReseptacles} newRSN={this.props.newRSN}
                    getAddMultipleData={this.props.getAddMultipleData} addMultipleData={this.props.addMultipleData} rsnData={this.props.rsnData}
                    addMultiple={this.props.addMultiple} oneTimeMailClass={this.props.oneTimeMailClass} oneTimeMailServiceLevel={this.props.oneTimeMailServiceLevel} />

            </div>
        );
    }
}
