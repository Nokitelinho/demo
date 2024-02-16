import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import CarditTableFilter from './CarditTableFilter.jsx'
import MailbagTableCustomRowPanel from './MailbagTableCustomRowPanel.jsx';
import TableHeaderPanel from './TableHeaderPanel.jsx'
import { IButton,ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
export default class MailbagDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = [],
        this.selectedMailbagsId = [],
        this.selectedResdit=[],
        this.state = {
            showResditPopup: false,
            showResditIndex: '',
            flag:false,
            selectedResditIndex:[],
            selectedMailbag:''
        }

    }
    onRowSelection = (data) => {

        if (data.index > -1) {
            if (data.isRowSelected) {

                this.selectMailbag(data.index,data.rowData.mailbagId);
            } else {
                this.unSelectMailbag(data.index);
            }
        }
        else {
            if  (data.checked) {
                this.selectAllMailbags(-1);
            } else {
                this.unSelectAllMailbags();
            }
        }
    }
    onRowsSelection = (data) => {

        this.setState({selectedResditIndex:data.selectedIndexes});
        if (data.index > -1) {
            if (data.isRowSelected && data.rowData.additionalInfo!=true || (!data.isRowSelected && data.rowData.additionalInfo)) {
               
                this.selectResdit(data);
            } 
            else{
                this.unSelectResdit(data);
            }
        }
        
    }
    carditAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        this.props.carditAction({ actionName: actionName, index: index });
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    navigateAction= (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        this.props.navigatetoViewCondoc({index: index });
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    selectMailbag = (mailbag,mailbagId) => {
        this.selectedMailbags.push(mailbag);
        this.selectedMailbagsId.push(mailbagId)
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags, this.selectedMailbagsId);
    }
    selectResdit = (data) => {
        let index = -1;
        let indexFlag=false;
        if( this.selectedResdit.length==0){
       this.selectedResdit.push({mailbag:data.rowData.mailbagId,resdit:data.rowData.eventCode,selected:true,airportCode:data.rowData.airportCode});
    }
       else{
        for (let i = 0; i < this.selectedResdit.length; i++) {
            var element = this.selectedResdit[i];
            if (element.mailbag === data.rowData.mailbagId && element.resdit==data.rowData.eventCode) {
              index = i;
              indexFlag=true;
                break;
            }
            
        }
        if (indexFlag) {
            this.selectedResdit.splice(index,1,{mailbag:data.rowData.mailbagId,resdit:data.rowData.eventCode,selected:true,airportCode:data.rowData.airportCode});
        }
        else{
            this.selectedResdit.push({mailbag:data.rowData.mailbagId,resdit:data.rowData.eventCode,selected:true,airportCode:data.rowData.airportCode});
        }

       }
      
    }
    unSelectMailbag = (mailbag) => {
        let index = -1;
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            var element = this.selectedMailbags[i];
            if (element === mailbag) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedMailbagsId.splice(index, 1);
            this.selectedMailbags.splice(index, 1);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags,this.selectedMailbagsId);
    }
    unSelectResdit = (data) => {
        let index = -1;
        for (let i = 0; i < this.selectedResdit.length; i++) {
            var element = this.selectedResdit[i];
            if (element.mailbag === data.rowData.mailbagId && element.resdit==data.rowData.eventCode) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedResdit.splice(index,1,{mailbag:data.rowData.mailbagId,resdit:data.rowData.eventCode,selected:false,airportCode:data.rowData.airportCode});
        }
       
    }
    selectAllMailbags = () => {

        for (let i = 0; i < this.props.mailbagDetails.length; i++) {
            this.selectedMailbags.push(i);
            this.selectedMailbagsId.push(this.props.mailbagDetails[i].mailbagId)
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags,this.selectedMailbagsId);
    }
   
    unSelectAllMailbags = () => {
        this.selectedMailbags = [];
        this.selectedMailbagsId=[];
      this.props.saveSelectedMailbagsIndex(this.selectedMailbags,this.selectedMailbagsId);
    }
    


    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
    getWeight = (rowData) => {
        return rowData.weight.displayValue;
    }
    getAWBNumber = (rowData) => {
        if (rowData.shipmentPrefix) {
            return rowData.shipmentPrefix + '-' + rowData.masterDocumentNumber;
        } else {
            return ''
        }
    }
    getFlightNumber = (rowData) => {
        if (rowData.carrierCode) {
            return rowData.carrierCode + '-' + rowData.flightNumber;
        } else {
            return ''
        }
    }
    getRDTValue = (rowData) => {
        if (rowData.reqDeliveryDate) {
            return rowData.reqDeliveryDate + '' + rowData.reqDeliveryTime
        } else {
            return ''
        }
    }
    onSelect = (value) => {
        this.setState({ showResditPopup: true, showResditIndex: value.rowindex,selectedMailbag:value.mailbag});
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();

    }
    onOKClick = () => {
        let index= -1;
        let mailDetails=this.props.mailbagDetails;
        let selectedMailbag=this.state.selectedMailbag;
        let selectedResditOfSelectedMailbag=this.selectedResdit;
        let selectedMailbagFromMailDetails= mailDetails.filter((value)=> {if(value.mailbagId==selectedMailbag)return value });
     
  for(let i=0;i<selectedResditOfSelectedMailbag.length;i++){
      for(let j=0;j< selectedMailbagFromMailDetails[0].mailbagHistories.length;j++){
          index=i;
   if(selectedResditOfSelectedMailbag[i].resdit==selectedMailbagFromMailDetails[0].mailbagHistories[j].eventCode&& selectedMailbagFromMailDetails[0].mailbagHistories[j].additionalInfo){
    this.selectedResdit.splice(index,1,{mailbag: selectedResditOfSelectedMailbag[i].mailbag,resdit:selectedResditOfSelectedMailbag[i].resdit,selected:true,airportCode:selectedResditOfSelectedMailbag[i].airportCode});
    }
    if(selectedResditOfSelectedMailbag[i].resdit==selectedMailbagFromMailDetails[0].mailbagHistories[j].eventCode&&((!selectedMailbagFromMailDetails[0].mailbagHistories[j].additionalInfo)|| selectedMailbagFromMailDetails[0].mailbagHistories[j].additionalInfo==null)){
        this.selectedResdit.splice(index,1,{mailbag: selectedResditOfSelectedMailbag[i].mailbag,resdit:selectedResditOfSelectedMailbag[i].resdit,selected: false,airportCode:selectedResditOfSelectedMailbag[i].airportCode});
        }
    
   
  
   }
  }
        this.setState({ showResditPopup: false, showResditIndex: '' });

    }
    onDoneClick = ()=> {
       this.props.saveResditMailbagData(this.selectedResdit,this.props.mailbagDetails,this.state.selectedMailbag);
        this.setState({ showResditPopup: false, showResditIndex: '', flag:true}); 
    }
    onApplyFlightFilter=()=>{
        this.props.onApplyFlightFilter();
    }
    render() {



        const results = this.props.mailbagDetails ? this.props.mailbagDetails : '';
        const rowCount = results.length;
        const flag=this.state.flag;
        const selectedResditIndex=this.state.selectedResditIndex;

        return (
            <Fragment>
              
                    <IMultiGrid
                    form={true}
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    rowHeight={35}
                    rowClassName="table-row"
                    tableId="mailbagtable"
                    sortEnabled={false}
                    fixedRowCount={1}
                    fixedColumnCount={6}
                    enableFixedRowScroll
                    width={1000}
                    hideTopRightGridScrollbar
                    hideBottomLeftGridScrollbar

                    onRowSelection={this.onRowSelection}
                    customHeader={{
                        headerClass: '',
                        dataConfig: {
                            screenId: '',
                            tableId: 'mailbagtable'
                        },
                        customPanel: <TableHeaderPanel selectedMailbagIndex={this.props.selectedMailbagIndex} carditMultipleSelectionAction={this.props.carditMultipleSelectionAction} oneTimeValues={this.props.oneTimeValues} displayError={this.props.displayError}mailbagDetails={this.props.mailbagDetails}form={this.props.form} bulkResditSend={this.props.bulkResditSend}initialValues={this.props.initialValues}selectedMailbagsId={this.props.selectedMailbagsId}/>,
                        "pagination": { "page": this.props.mailbags, getPage: this.props.getNewPage,
                        options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' }]  },
                        filterConfig: {
                            panel: <CarditTableFilter oneTimeValues={this.props.oneTimeValues} tableFilter={this.props.tableFilter} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.onApplyFlightFilter,
                            onClearFilter: this.props.onClearFlightFilter
                        },
                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            // addlColumns: [{ label: 'Weight', excelData: this.getWeight }, { label: 'Consignment Number', dataKey: 'consignmentNumber' }, { label: 'Consignment Date', dataKey: 'consignmentDate' }, { label: 'AWB Number', excelData: this.getAWBNumber }, { label: 'RDT', excelData: this.getRDTValue }, { label: 'ULD Number', dataKey: 'uldNumber' }, { label: 'RI', dataKey: 'registeredOrInsuredIndicator' }],
                           addlColumns: [],
                            name: 'Cardit List'
                        },
                        sortBy: {
                            onSort: this.sortList
                        }

                    }}
                    data={results}>
                    <Columns customRow={MailbagTableCustomRowPanel} customRowDataKey='mailbagId'>
                        <IColumn
                            width={40}
                            dataKey=""
                            className="first-column"
                            hideOnExport>
                            <Cell>
                                <HeadCell disableSort selectOption>
                                </HeadCell>
                                <RowCell selectOption>
                                </RowCell>
                            </Cell>
                        </IColumn>

                        <IColumn
                      

                      

                            dataKey="mailbagId"
                            label="Mail Bag ID"
                            flexGrow={3}
                            id="mailbagId"
                            width={230}
                            selectColumn={true}
                            sortByItem={true}
                        >
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
                            width={30}
                            dataKey=""
                            flexGrow={0}
                            selectColumn={false}
                            hideOnExport>
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content></Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content><a href="#"><i id={"resditimage_" + cellProps.rowIndex} onClick={() => this.onSelect({rowindex:cellProps.rowIndex,mailbag:cellProps.rowData.mailbagId})} className="icon ico-ex-blue"></i></a></Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            flexGrow={3}
                            id="mailbagId"
                            width={30}
                            selectColumn={true}
                            sortByItem={true}
                        >
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>{}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                   {(cellProps) => {
                                        let events = cellProps.rowData.mailbagHistories;
                           let selected = events.filter((value) => { if(value.additionalInfo===true) return value });
                           return <Content>{flag==true&&selected.length!=0?"("+selected.length+")":""}</Content>

                                  }}
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="flightNumber"
                            label="Flight Number"
                            width={100}
                            flexGrow={1}
                            id="carrierCode"
                            selectColumn={true}
                            sortByItem={true}
                        >
                            <Cell>
                                <HeadCell disableSort>
                                    {() => (
                                        <Content>Flight Number</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.rowData.carrierCode}{cellProps.rowData.carrierCode ? '-' : ''}{cellProps.rowData.flightNumber}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="flightDate"
                            label="Flight Date"
                            width={95}
                            flexGrow={1}
                            id="flightDate"
                            selectColumn={true}
                            sortByItem={true}
                        >
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
                            dataKey="accepted"
                            label="Accepted"
                            width={70}
                            flexGrow={1}
                            id="accepted"
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>  
                                            {/* commented by A-7815 as part of IASCB-37980 */}
                                            {/* <input type="checkbox" checked={cellProps.rowData.accepted === 'Y' ? true : false} /> */}
                                            {((cellProps.rowData.latestStatus!='NEW' && cellProps.rowData.latestStatus!='BKD' && cellProps.rowData.latestStatus!='CAN') && cellProps.rowData.accepted!='E') || cellProps.rowData.accepted === 'Y' ? <i className="icon ico-ok-green"></i> : (cellProps.rowData.accepted === 'E' ? <i className="icon ico-error-sm"></i> : <i className="icon ico-ok-dark"></i>)}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="ooe"
                            label="Origin OE"
                            flexGrow={1}
                            id="ooe"
                            width={80}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="doe"
                            label="Dest OE"
                            width={80}
                            flexGrow={1}
                            selectColumn={true}
                            sortByItem={true}
                            id="doe">
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
                            dataKey="mailCategoryCode"
                            label="Category"
                            width={70}
                            flexGrow={1}
                            selectColumn={true}
                            id="mailCategoryCode"
                            sortByItem={true}>
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
                            dataKey="mailSubclass"
                            label="Subclass"
                            width={70}
                            flexGrow={1}
                            id="mailSubclass"
                            selectColumn={true}
                            sortByItem={true}
                        >
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
                            dataKey="year"
                            label="Yr"
                            width={30}
                            flexGrow={1}
                            id="year"
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="despatchSerialNumber"
                            label="DSN"
                            width={50}
                            flexGrow={1}
                            selectColumn={true}
                            id="despatchSerialNumber"
                            sortByItem={true}
                        >
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
                            dataKey="receptacleSerialNumber"
                            label="RSN"
                            width={50}
                            flexGrow={1}
                            selectColumn={true}
                            id="receptacleSerialNumber"
                            sortByItem={true}
                        >
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
                            dataKey="highestNumberedReceptacle"
                            label="HNI"
                            width={50}
                            flexGrow={1}
                            selectColumn={true}
                            id="highestNumberedReceptacle"
                            sortByItem={true}
                        >
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
                            dataKey="registeredOrInsuredIndicator"
                            label="RI"
                            flexGrow={1}
                            id="registeredOrInsuredIndicator"
                            width={80}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="mailbagWeight"
                            label="Weight"
                            flexGrow={1}
                            id="mailbagWeight"
                            width={80}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="consignmentNumber"
                            label="Consignment No"
                            flexGrow={1}
                            id="consignmentNumber"
                            width={120}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="consignmentDate"
                            label="Consignment Date"
                            flexGrow={1}
                            id="consignmentDate"
                            width={130}
                            selectColumn={true}
                            sortByItem={true}>
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
                            dataKey="shipmentPrefix"
                            label="AWB No."
                            flexGrow={1}
                            id="shipmentPrefix"
                            width={100}
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData?cellProps.cellData + "-"+ cellProps.rowData.masterDocumentNumber:"--"}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                       
                        <IColumn
                            dataKey="reqDeliveryDate"
                            label="RDT"
                            flexGrow={1}
                            id="reqDeliveryDate"
                            width={180}
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData} {"  "}
                                        {cellProps.rowData.reqDeliveryTime}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                       
                        
					  <IColumn
                            dataKey="uldNumber"
                            label="ULD Number"
                            flexGrow={1}
                            id="uldNumber"
                            width={130}
                            selectColumn={true}
                            sortByItem={true}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{cellProps.cellData?cellProps.cellData:'--'}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailorigin"
                             label="Origin Airport"
                              width={100}
                              flexGrow={1}
                              selectColumn={true}
                              id="mailorigin"
                              sortByItem={true}>
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
                                                dataKey="mailDestination"
                                                label="Dest Airport"
                                                width={100}
                                                flexGrow={1}
                                                selectColumn={true}
                                                id="mailDestination"
                                                sortByItem={true}>
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
                                                dataKey="transportSrvWindow"
                                                label="TSW"
                                                width={100}
                                                flexGrow={1}
                                                selectColumn={true}
                                                id="transportSrvWindow"
                                                sortByItem={true}>
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
                                                dataKey="servicelevel"
                                                label="Service Level"
                                                width={100}
                                                flexGrow={1}
                                                selectColumn={true}
                                                id="servicelevel"
                                                sortByItem={true}>
                                                <Cell>
                                                    <HeadCell disableSort>
                                                        {(cellProps) => (
                                                            <Content>{cellProps.label}</Content>)
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>{cellProps.rowData.servicelevel ? this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription })).find((element) => {return  element.value === cellProps.rowData.servicelevel}).label :' '}</Content>)
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
					  <IColumn
                            dataKey=""
                            label=""
                            flexGrow={1}
                            id="menu"
                            width={40}
                            className="last-column justify-content-end"
                            >
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {
                                               
                                                <IDropdown>
                                                    <IDropdownToggle className="dropdown-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu right={true} >
                                                        <IDropdownItem data-mode="VIEW_CONDOC" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_VIEW_CONDOC" data-index={cellProps.rowIndex} onClick={this.navigateAction}>View ConDoc</IDropdownItem>
                                                        <IDropdownItem data-mode="SEND_RESDIT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SEND_RESDIT" data-index={cellProps.rowIndex} onClick={this.carditAction}>Send Resdit</IDropdownItem>
                                                    </IDropdownMenu>
                                                </IDropdown>

                                            }

                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
					 

                        

						 
					 
					 
					 
                       

                    </Columns>
            
                </IMultiGrid>
               
                {
                    this.state.showResditPopup &&
                    <IPopover placement="right" isOpen={this.state.showResditPopup} target={'resditimage_' + this.state.showResditIndex} toggle={this.onOKClick} className="icpopover"> >
                    <IPopoverHeader>Mailbag Event History</IPopoverHeader>
                        <IPopoverBody>
                            <div style={{ "width": "750px" }}>
                                <div className="pad-sm border-bottom">
                                    <div className="mar-b-3xs"><span className="text-light-grey">MailbagId :</span> <span>{this.props.mailbagDetails[this.state.showResditIndex].mailbagId}</span></div>
                                    <div><span className="text-light-grey">Consignment No :</span> <span>{this.props.mailbagDetails[this.state.showResditIndex].consignmentNumber}</span></div>
                                </div>
                                <div className="d-flex" style={{"height":"350px" }}>
                                    <ITable
                                        rowCount={this.props.mailbagDetails[this.state.showResditIndex].mailbagHistories.length}
                                        data={this.props.mailbagDetails[this.state.showResditIndex].mailbagHistories}
                                        onRowSelection={this.onRowsSelection}
                                        form="true"
                                        name="resditSelection"
                                        headerHeight={35}
                                        rowHeight={35}
                                        rowClassName="table-row"
                                        tableId="resditTable">
                                        <Columns>
                                            <IColumn
                                             width={40}
                                             dataKey=""
                                             className="first-column"
                                             hideOnExport>
                                            <Cell>
                                              <HeadCell disableSort >
                                              {(cellProps) => (
                                              <Content></Content>)
                                            }
                                              </HeadCell>
                                              <RowCell >
                                              {(cellProps) => (
                                              <Content><ICheckbox   name={`${cellProps.rowIndex}.additionalInfo`}/></Content>)
                                            }
                                             </RowCell>
                                            </Cell>
                                           </IColumn>
                                            <IColumn
                                                dataKey="messageTime"
                                                label=" Date & Time"
                                                width={100}
                                                flexGrow={1}
                                                selectColumn={true}>
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
                                                label="Resdit Event"
                                                width={75}
                                                flexGrow={1}
                                                id="Resdit Event"
                                                selectColumn={false}>
                                                <Cell>
                                                    <HeadCell disableSort>
                                                        {(cellProps) => (
                                                            <Content>{cellProps.label}</Content>)
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => {

                                                            let events = this.props.oneTimeValues['mailtracking.defaults.resditevent'];
                                                            let selectedEvent = events.find((element) => { return element.fieldValue === cellProps.rowData.eventCode });

                                                            return <Content>{selectedEvent.fieldDescription}</Content>

                                                        }}
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>

                                            <IColumn
                                                dataKey="processedStatus"
                                                label="Status"
                                                width={100}
                                                flexGrow={1}
                                                selectColumn={false}
                                                id="processedStatus">
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
                                                dataKey="airportCode"
                                                label="Airport Code"
                                                width={75}
                                                flexGrow={1}
                                                selectColumn={false}
                                                id="airportCode">
                                                <Cell>
                                                    <HeadCell disableSort>
                                                        {(cellProps) => (
                                                            <Content>{cellProps.label}</Content>)
                                                        }
                                                    </HeadCell>
                                                    <RowCell>
                                                        {(cellProps) => (
                                                            <Content>{cellProps.rowData.airportCode}</Content>)
                                                        }
                                                    </RowCell>
                                                </Cell>
                                            </IColumn>
                                        </Columns>
                                    </ITable>
                                
                                </div>
                                <div  class="button_popup"> <IButton category="primary" className="btn btn-primary"accesskey="S" onClick={this.onOKClick} >Close</IButton></div>
                               <div  class="button_popup"> <IButton category="primary" className="btn btn-primary"accesskey="S" onClick={this.onDoneClick} >Done </IButton></div>
                            </div>
                        </IPopoverBody>
                    </IPopover>
                }
              
            </Fragment>
        );
    }
}
