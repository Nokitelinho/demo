import React, { PureComponent, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content }
    from 'icoreact/lib/ico/framework/component/common/grid';
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import FlightDetailsFilter from './filters/FlightDetailsFilter.js';
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
import FlightDetails from './custompanels/FlightDetails.js';
import GenerateManifestPopup from './GenerateManifestPopup.jsx'
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import PreAdvice from './PreadvicePopup.jsx';
import PropTypes from 'prop-types';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
const Aux = (props) => props.children;
export default class FlightDetailsTable extends PureComponent {

    constructor(props) {
        super(props);
        this.selectedFlights = [];
        this.state = {
            showGenManifestPopup: false,
            showPreadvicePopup: false,
            showDescrepencyPopup: false,
            checked:[],
            selectFlag:false,
            rowClkCount: 0,
            isCapacityPopoverOpen: false,
            capacityPopoverId: '',
            dcsPopoverId:'',
            isDcsPopoverOpen:false,
            isContainerPopoverOpen: false,
            containerPopoverId: '',           
            isVolPostnPopoverOpen: false,
            posPopoverId: '',
            isVolPopoverOpen: false,
            volPopoverId: '',
        }


    }

    onRowSelection = (data) => {
        this.setState({ dcsPopoverId: '', isCapacityPopoverOpen: false,isVolPostnPopoverOpen:false, isVolPopoverOpen: false })
        if (data.index > -1) {
            //if (data.isRowSelected) {

                this.props.selectFlights({ flightIndex: data.index });
           // } else {
                //  this.unSelectFlight(data.rowData);
           // }
        }
        // else {
        //     if (data.event.target.checked) {
        //         this.selectAllFlights(this.flights);
        //     } else {
        //         this.unSelectAllFlights();
        //     }
        // }
    }

    getRowClassName=(row)=> {
        const data = row.rowData;

        const selectedFlight = this.props.selectedMailflight ? this.props.selectedMailflight : '';
        const selectedFlightpk = selectedFlight.flightpk;
        const rowFlightpk = data.flightpk;

        if (JSON.stringify(selectedFlightpk) === JSON.stringify(rowFlightpk) && this.props.displayMode === 'multi') {
            return 'table-row table-row__bg-red'
        }
        else {
            return 'table-row'
        }

    }
    getNewPage=(displayPage, selectedPageSize)=>{
        this.props.onlistDetails(displayPage, selectedPageSize,this.props.displayMode)
    }

    flightAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        if (actionName === 'GENERATE_MANIFEST') {
            this.genManifestClk();
            this.props.saveIndexForMailManifes({ type: actionName, index: index });
        }
        else if (actionName === 'PRINT_CN46') {
            this.props.printCN({ type: actionName, index: index });
        }
        else {
            if (actionName === 'PRE-ADVICE') {
                this.preadvicePopupClk();
            }

            else if (actionName === 'DISCREPANCY') {
                this.discrepencyPopupClk();
            }
            // let source = event.currentTarget.textContent.trim();
            // let actionName = source.split(/[ ,]+/).filter(function (v) { return v !== '' }).join('_').toUpperCase();

            this.props.performFlightAction({ type: actionName, index: index, displayMode:this.props.displayMode });
        }
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    genManifestClk = () => {
        this.setState({ showGenManifestPopup: true });
    }
    preadvicePopupClk = () => {
        this.setState({ showPreadvicePopup: true });
    }

    discrepencyPopupClk = () => {
        this.setState({ showDescrepencyPopup: true });
    }

    onToggle = () => {
        this.setState({ showGenManifestPopup: false,showPreadvicePopup:false });
        this.props.onPreadviceOK();
    }
    onSelect = (event) => {
        let count = this.state.rowClkCount;
        let index = event.target.dataset.index;
        //Added by A-8164 for ICRD-319232
        let checked = this.state.checked;
        checked[index] = event.target.checked;
        this.setState({
        checked: checked,
        selectFlag:true,
        })
        if (event.target.checked == true) {
            count = ++count
            this.selectFlight(index);
        } else {
            count = --count
            this.unSelectFlight(index);
        }
        this.setState({
            rowClkCount: count
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    selectFlight = (flight) => {
        this.selectedFlights.push(flight);
        this.props.saveSelectedFlights(this.selectedFlights);
    }
    unSelectFlight = (flight) => {
        let index = -1;
        let flightSelected=flight;
        for (let i = 0; i < this.selectedFlights.length; i++) {
            let element = this.selectedFlights[i];
            if (element === flightSelected) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedFlights.splice(index, 1);
        }
        this.props.saveSelectedFlights(this.selectedFlights);
    }
    selectAllFlights = (flights) => {
        this.selectedFlights = flights
        this.props.saveSelectedFlights(this.selectedFlights);
    }

    unSelectAllFlights = () => {
        this.selectedFlights = []
        this.props.saveSelectedFlights(this.selectedFlights);
    }
    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem,this.props.displayMode);
    }
    onApplyFlightFilter = () => {
        this.props.onApplyFlightFilter(this.props.displayMode);
    }

    //Added by A-8164 for ICRD-319232
    selectAll = (event) => {
        // Set all checked states to true/false
        let selectArray = [];
        let flightsSelected = [];
        const count = this.props.flightDetails ? this.props.flightDetails.length : 0;
        for (let i = 0; i < count; i++) {
            selectArray.push(event.target.checked);
            flightsSelected.push(i);
        }
        this.setState({
            checked: selectArray,
            selectFlag: true,
            rowClkCount:event.target.checked?count:0
        })
        event.target.checked?
            this.selectAllFlights(flightsSelected):
            this.unSelectAllFlights();
    }
    savePopoverId = (index) => {
        this.setState({ capacityPopoverId: index});
        this.toggleCapacityPopover();
    }
    savePosPopoverId = (index) => {
        this.setState({ posPopoverId: index });
        this.togglePosPopover();
    }
    saveVolPopoverId = (index) => {
        this.setState({ volPopoverId: index });
        this.toggleVolPopover();
    }
    savedcsErrorPopover=(index)=>{
        this.setState({dcsPopoverId:index,isDcsPopoverOpen: !this.state.isDcsPopoverOpen})
    }
    toggleCapacityPopover = () => {
        this.setState({ isCapacityPopoverOpen: !this.state.isCapacityPopoverOpen })
    }
    togglePosPopover = () => {
        this.setState({ isVolPostnPopoverOpen: !this.state.isVolPostnPopoverOpen })
}
    toggleVolPopover = () => {
            this.setState({ isVolPopoverOpen: !this.state.isVolPopoverOpen })
    }
   getCapacityInfo = (rowData) => {
          let capacityInfo = "";
        let Info = "";
        let Detalis = "";
        // let flightRoute = rowData.flightRoute;
        const capacityDetails = this.props.flightCapacityDetails.results
        if (capacityDetails !== undefined || capacityDetails !== null) {
            for (let i = 0; i < capacityDetails.length; i++) {
                Info = capacityDetails[i].flightpk;
                Detalis = rowData.flightpk;
                if (Info.companyCode == Detalis.companyCode && Info.flightCarrierId == Detalis.flightCarrierId && Info.flightSeqNum == Detalis.flightSeqNum &&  Info.flightNumber==Detalis.flightNumber) {
                    const value = capacityDetails[i].capacityDetails[0];
                    capacityInfo = value.segmentOrigin + ' - ' + value.segmentDestination + '.Mail :' + value.mailUtised.roundedDisplayValue + ' /' + value.mailCapacity.roundedDisplayValue + ',' +
                        'Cargo : ' + value.cargoUtilised.roundedDisplayValue + '/' + value.cargoCapacity.roundedDisplayValue + ',' +
                        'Total : ' + value.totalUtilised.roundedDisplayValue + ' /' + value.segmentTotalWeight.roundedDisplayValue
                    return capacityInfo;
                }
            }

        }
    }
    getManifestInfo =(rowData) =>{
        return rowData.manifestInfo;
    }
    getPreadviceInfo =(rowData) =>{
        let preadviceInfo="";
        let Info = "";
        let Details = "";
        const preadviceDetails = this.props.flightPreAdviceDetails.results
        if (preadviceDetails !== undefined || preadviceDetails !== null) {
            for (let i = 0; i < preadviceDetails.length; i++) {
                Info = preadviceDetails[i].flightpk;
                Details = rowData.flightpk;
                if (Info.companyCode == Details.companyCode && Info.flightCarrierId == Details.flightCarrierId && Info.flightSeqNum == Details.flightSeqNum &&  Info.flightNumber==Details.flightNumber) {
                    const value = preadviceDetails[i].preadvice;
                    if(value.totalWeight!=null){
        preadviceInfo=value.totalBags+" Bags "+value.totalWeight.displayValue+" "+value.totalWeight.unitCodeForDisplay;
        return preadviceInfo;
                    }
                }
            }
        }
    }
    getFlightDetails = (rowData) => {
        let flightDetail = "";
        let flightRoute = rowData.flightRoute;
        let flightRouteArr = flightRoute.split("-");
        flightDetail = flightDetail + ' ' + rowData.flightNumber;
        for (let i = 0; i < flightRouteArr.length; i++) {
            const value = flightRouteArr[i];
            flightDetail = flightDetail+ ' -' +value;
        }
        flightDetail = flightDetail + ' |' + rowData.flightDepartureDate + ' ' +":"+ ' ' + rowData.flightTime+ ' ' + "("+ ' ' +rowData.flightDateDesc +")";
        flightDetail = flightDetail+ ' |' + rowData.aircraftType;
        //flightDetail = flightDetail+ ' |' +rowData.flightStatus;

        let flightStatus = '';

        if(rowData.flightOperationalStatus === 'O'){
            flightStatus = 'Open'
        }else if(rowData.flightOperationalStatus === 'C'){
            flightStatus = 'Closed'
        }else if(rowData.flightOperationalStatus === 'N'){
            flightStatus = 'New'
        }
        let statusOneTime = this.props.oneTimeValues['flight.operation.flightstatus'];
        let statusMap = [];
        for(let i=0; i<statusOneTime.length; i++){
            let status = {'value' : '', 'label' : ''};
            status.value = statusOneTime[i].fieldValue;
            status.label = statusOneTime[i].fieldDescription;
            statusMap.push(status);
        }
        let selectedStatus = '';
        for(let i=0; i<statusMap.length; i++){
            if(rowData.flightStatus === statusMap[i].value){
                selectedStatus = statusMap[i].label;
                break;
            }
        }
        flightDetail = flightDetail+ ' |' +selectedStatus;
        flightDetail = flightDetail+ ' |' +flightStatus;
        flightDetail = flightDetail+ ' |' + 'Gate:' ;   
        let gate = rowData.departureGate ? rowData.departureGate : '';
        flightDetail = flightDetail+ ' ' + gate;                                               
        return flightDetail;
    }

    getUpliftAirport =(rowData) =>{
        return rowData.upliftAirport;
    }

    saveContainerPopoverId = (index) => {
        this.setState({ containerPopoverId: index});
        this.toggleContainerPopover();
    }


    toggleContainerPopover = () => {
        this.setState({ isContainerPopoverOpen: !this.state.isContainerPopoverOpen })
    }

    render() {
        const results = this.props.flightDetails ? this.props.flightDetails : '';
        // const flights=this.props.flights;      
        const rowCount = results.length;
        //  const rowCount = results.length;
        let keyValue=110;
        let status = [];
        let dcsStatus =[];
        if(!this.state.selectFlag&&this.state.checked.length==0){ //Added by A-8164 for ICRD-319232
            let selectArray=[];
            for(let i=0;i<rowCount;i++){
                selectArray.push(false);
            }
            this.setState({
                checked:selectArray,
                selectFlag: true,
                })
        }
        status = this.props.oneTimeValues['flight.operation.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        dcsStatus = this.props.oneTimeValues['operations.flthandling.dws.dcsreportingstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        return (
            /*  <ITable rowCount={rowCount}
           headerHeight={40} 
           rowHeight={35} 
           className="" 
           gridClassName="" 
           headerClassName=""
           sortEnabled={false}
           data={this.props.flightDetails}>
           <Columns>
               <IColumn dataKey="companyCode" label="Name" width={160} >
                   <Cell>
                       <HeadCell>
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
       </ITable>*/
            //,"mode":'minimal'
            (this.props.displayMode === 'display' || this.props.displayMode === 'multi') ? (
                <Fragment>
                    <ITable
                        customHeader={{
                
                            headerClass: '',
                            "pagination": {
                                "page": this.props.flights, getPage: this.getNewPage, mode: this.props.displayMode === 'multi' ? 'subminimal' : '',
                           options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ] 
                         },
                            filterConfig: {
                                panel: <FlightDetailsFilter initialValues={this.props.initialValues} oneTimeValues={this.props.oneTimeValues}/>,
                                title: 'Filter',
                                onApplyFilter: this.onApplyFlightFilter,
                                onClearFilter: this.props.onClearFlightFilter
                            },
                            customPanel: this.props.displayMode === 'display' ? <FlightDetails flightCarrierView={this.props.flightCarrierView} rowClkCount={this.state.rowClkCount} performFlightAction={this.props.performFlightAction} displayMode={this.props.displayMode}
                                openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup}
                                toggleCarditDisplay={this.props.toggleCarditDisplay} carditView={this.props.carditView} selectedFlights={this.props.selectedFlights}/>:null,
                         

                            exportData:
                            this.props.displayMode === 'display' ? {
                                exportAction: this.props.exportToExcel,
                                pageable: true,
                                addlColumns:[{label:'Flight Details',excelData:this.getFlightDetails},
                                {label:'Uplift Airport',excelData:this.getUpliftAirport},
                                {label:'Container Info',excelData:this.getManifestInfo}, 
                                {label:'Pre-advice Info',excelData:this.getPreadviceInfo},
                                {label:'Capacity Info',excelData:this.getCapacityInfo}],
                                name: 'Flight List'
                            } : null,
                            sortBy: {
                                onSort: this.sortList
                            },
                            dataConfig: this.props.displayMode === 'display' ? {
                                screenId: 'MTK060',
                                tableId: 'flightlisttable'
                            } : null


                        }}
                        rowCount={rowCount}
                        headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        tableId='flightlisttable'
                        onRowSelection={this.onRowSelection}
                        rowClassName={this.getRowClassName}
                        rowHeight={45}

                        //customHeader={{"pagination":{"page":this.props.mailbagDetails,getPage:this.props.getNewPage}}}-->
                        data={results}
                    >
                        <Columns>
                            {(this.props.displayMode === 'display') ? (
                                <IColumn
                                    width={32}
                                    dataKey=""
                                    className=""
                                    selectColumn={false}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.selectAll} /></Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content><input type="checkbox" data-index={cellProps.rowIndex} checked={this.state.checked[cellProps.rowIndex]} onClick={this.onSelect} /></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>) : null}
                            {(this.props.displayMode === 'display') ? (
                                <IColumn
                                    dataKey="flightDetails"
                                    label="Flight Details"
                                    width={270}
                                    id="flightDetails"
                                    flexGrow={this.props.displayMode === 'display' ? 0 : 1}
                                    selectColumn={true}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Flight Details</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => {
                                                let selectedStatus = '';
                                                selectedStatus = status.find((element) => { return element.value === cellProps.rowData.flightStatus });
                                                let dcsStat = '';
                                                
                                                dcsStat = (cellProps.rowData.dcsinfo== null) ? dcsStatus.find((element) => { return element.value === 'AFMNOTSNT' }) : dcsStatus.find((element) => { return element.value === cellProps.rowData.dcsinfo }) ;
                                                
                                                let flightRouteArr = cellProps.rowData.flightRoute.split("-");
                                                return <Content>
                                                    <DataDisplay index={cellProps.rowIndex} id='flightNumber' label='Flight Number' className="align-items-center d-flex mar-b-2xs" sortByItem={true}>
                                                        {/* <span className="fs16 pad-r-xs">{cellProps.rowData.carrierCode}{cellProps.rowData.flightNumber} </span>   */}
                                                        <span> <IFlightNumber 
                                            {...{carrierCode:cellProps.rowData.carrierCode,flightNumber:cellProps.rowData.flightNumber,flightCarrierId:cellProps.rowData.carrierId,flightSequenceNumber:cellProps.rowData.flightSequenceNumber,flightDate:cellProps.rowData.flightDate,legSerialNumber:cellProps.rowData.legSerialNumber}}
                                            transactionCode="MALOUT"
                                            showDate={false}
                                            mode='display'  ></IFlightNumber></span>
                                            {cellProps.rowData.dcsinfo === null && <span className= {"badge badge-pill light badge-info"}>{dcsStat.label}</span>}
                                            {(cellProps.rowData.dcsinfo ==='AFMPROREJ'  ||  cellProps.rowData.dcsinfo ==='AFMFINREJ' || cellProps.rowData.dcsinfo ==='AFMAMDFINREJ' ) ?
                                                  <div className="d-inline-flex pointer" onClick={this.gridUtitity}>
                                               <span id={'dcsReason' + cellProps.rowIndex} className="badge badge-pill light badge-error" onClick={() => this.savedcsErrorPopover(cellProps.rowIndex)}>{dcsStat.label}</span>
                                                            </div>
                                            :''}
                                            {(cellProps.rowData.dcsinfo ==='AFMFINACK' || cellProps.rowData.dcsinfo ==='AFMAMDFINACK') ? <span className= {"badge badge-pill light badge-active"}>{dcsStat.label}</span>:''}
                                            
                                            {cellProps.rowData.dcsinfo !=null && cellProps.rowData.dcsinfo !='AFMPROREJ' && cellProps.rowData.dcsinfo !='AFMFINREJ' && cellProps.rowData.dcsinfo !='AFMAMDFINREJ' && cellProps.rowData.dcsinfo !='AFMFINACK' && cellProps.rowData.dcsinfo !='AFMAMDFINACK' && <span className= {"badge badge-pill light badge-info"}>{dcsStat ? dcsStat.label:''}</span>}
                                            
                                                    {this.state.isDcsPopoverOpen && 'dcsReason' + cellProps.rowIndex === 'dcsReason' + this.state.dcsPopoverId &&
                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isDcsPopoverOpen} target={'dcsReason' + this.state.dcsPopoverId}  className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                    {cellProps.rowData.dcsregectionReason}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                    </DataDisplay>
                                                    <div className="mar-b-2xs">
                                                        <span className="text-light-grey mar-r-xs">
                                                            {
                                                                flightRouteArr.map((route, index) => {
                                                                    if (index === flightRouteArr.length - 1) {
                                                                        return route;
                                                                    } else {
                                                                        return <Aux key={keyValue++}>{route}<i className="icon ico-air-sm mar-x-2xs"></i></Aux>
                                                                    }
                                                                })
                                                            }
                                                        </span>
                                                        <DataDisplay index={cellProps.rowIndex} id='flightDepartureDate' label='Flight Date' sortByItem={true}>
                                                    {cellProps.rowData.flightDepartureDate} : {cellProps.rowData.flightTime} ({cellProps.rowData.flightDateDesc})
                                                    </DataDisplay>
                                                        </div>
                                                    <div>
                                                        <span>{cellProps.rowData.aircraftType}</span>
                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                        <span>{selectedStatus ? selectedStatus.label : ''}</span>
                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                        {cellProps.rowData.flightOperationalStatus === 'O' &&
                                                            <span className="text-green">Open</span>
                                                        }
                                                        {cellProps.rowData.flightOperationalStatus === 'C' &&
                                                            <span>Closed</span>
                                                        }
                                                        {cellProps.rowData.flightOperationalStatus === 'N' &&
                                                            <span className="text-blue">New</span>
                                                        }
                                                        <span className="pad-x-xs text-light-grey">|</span>
                                                        <span><span className="text-light-grey">Gate :</span> <span className="text-green">{cellProps.rowData.departureGate ? cellProps.rowData.departureGate : ''}</span></span>
                                                    </div>
                                                </Content>
                                            }
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>) : ([<IColumn
                                    dataKey="flightDetails"
                                    label="Flight Details"
                                    id="flightDetails"
                                    width={240}
                                    /*flexGrow={this.props.displayMode === 'display' ? 0 : 0}*/
                                    selectColumn={true}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Flight Details</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => {
                                                // let selectedStatus = '';
                                                // selectedStatus = status.find((element) => { return element.value === cellProps.rowData.flightStatus });
                                                let dcsStat = '';
                                                dcsStat = (cellProps.rowData.dcsinfo== null) ? dcsStatus.find((element) => { return element.value === 'AFMNOTSNT' }) : dcsStatus.find((element) => { return element.value === cellProps.rowData.dcsinfo }) ;
                                                let flightRouteArr = cellProps.rowData.flightRoute.split("-");
                                                return <Content>
                                                    <DataDisplay index={cellProps.rowIndex} id='flightNumber' label='Flight Number' className="align-items-center d-flex mar-b-2xs flex-wrap" sortByItem={true}>
                                                    <span> <IFlightNumber 
                                            {...{carrierCode:cellProps.rowData.carrierCode,flightNumber:cellProps.rowData.flightNumber,flightCarrierId:cellProps.rowData.carrierId,flightSequenceNumber:cellProps.rowData.flightSequenceNumber,flightDate:cellProps.rowData.flightDate,legSerialNumber:cellProps.rowData.legSerialNumber}}
                                            transactionCode="MALOUT"
                                            showDate={false}
                                            mode='display'></IFlightNumber></span>
                                                    {cellProps.rowData.dcsinfo === null && <span className= {"badge badge-pill light badge-info"}>{dcsStat.label}</span>}
                                            {(cellProps.rowData.dcsinfo ==='AFMPROREJ' || cellProps.rowData.dcsinfo ==='AFMFINREJ' || cellProps.rowData.dcsinfo ==='AFMAMDFINREJ') ?
                                                <div className="d-inline-flex pointer" onClick={this.gridUtitity}>
                                                 <span id={'dcsReason' + cellProps.rowIndex} className="badge badge-pill light badge-error" onClick={() => this.savedcsErrorPopover(cellProps.rowIndex)}>{dcsStat.label}</span>
                                                 </div> : ''
                                            }
                                            {(cellProps.rowData.dcsinfo ==='AFMFINACK' || cellProps.rowData.dcsinfo ==='AFMAMDFINACK' ) ? <span className= {"badge badge-pill light badge-active"}>{dcsStat.label}</span>:''}
                                            
                                            {cellProps.rowData.dcsinfo !=null && cellProps.rowData.dcsinfo !='AFMPROREJ' && cellProps.rowData.dcsinfo !='AFMFINREJ' && cellProps.rowData.dcsinfo !='AFMAMDFINREJ' && cellProps.rowData.dcsinfo !='AFMFINACK' && cellProps.rowData.dcsinfo !='AFMAMDFINACK' && <span className= {"badge badge-pill light badge-info"}>{dcsStat ? dcsStat.label:''}</span>}
                                            
                                                        {this.state.isDcsPopoverOpen && 'dcsReason' + cellProps.rowIndex === 'dcsReason' + this.state.dcsPopoverId &&
                                                            <div>
                                                                <IPopover placement="right" isOpen={this.state.isDcsPopoverOpen} target={'dcsReason' + this.state.dcsPopoverId} className="icpopover">
                                                                    <IPopoverBody>
                                                                        <div className="pad-md pad-b-sm">
                                                                            {cellProps.rowData.dcsregectionReason}
                                                                        </div>
                                                                    </IPopoverBody>
                                                                </IPopover>
                                                            </div>
                                                        }
                                                    </DataDisplay>
                                                    <div className="mar-b-2xs">
                                                        <span className="text-light-grey">
                                                            {
                                                                flightRouteArr.map((route, index) => {
                                                                    if (index === flightRouteArr.length - 1) {
                                                                        return route;
                                                                    } else {
                                                                        return <Aux key={keyValue++}>{route}<i className="icon ico-air-sm mar-x-2xs"></i></Aux>
                                                                    }
                                                                })
                                                            }
                                                        </span>
                                                    </div>
                                                    <DataDisplay index={cellProps.rowIndex} id='flightDepartureDate' label='Flight Date' sortByItem={true}>
                                                    {cellProps.rowData.flightDepartureDate} : {cellProps.rowData.flightTime} ({cellProps.rowData.flightDateDesc})
                                                    </DataDisplay>
                                                    {/* <span></span> */}
                                                </Content>
                                            }
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                key={keyValue++}
                                    dataKey="departureGate"
                                    id="departureGate"
                                    label="Gate info"
                                    width={90} flexGrow={1}
                                    className="d-flex"
                                    hideOnExport
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData ? cellProps.cellData : '--'}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                dataKey=""
                                label="Container Info"
                                width={180}
                                id="manifestInfo"
                                flexGrow={1}
                                selectColumn={true}
                                className="d-flex"
                                hideOnExport>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>Container Info</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let container = [];
                                            let extraContainers = [];
                                            container = cellProps.rowData.containerDetails ? cellProps.rowData.containerDetails.map((value) => (<div className="mar-b-3xs d-flex line-15" key={keyValue++}>
                                            <div className="pad-r-xs">{value.containercount ? value.containercount : ''} <span className="text-light-grey">{value.containergroup ? value.containergroup : ''}</span></div>
                                            <div className="pad-r-xs">{value.mailbagcount ? value.mailbagcount : ''}<span className="text-light-grey"> Bags </span></div>
                                            <div>{value.mailbagweight != null && value.mailbagweight != undefined ? value.mailbagweight.roundedDisplayValue : ''}</div>
                                             </div>)) : container
                                           if(cellProps.rowData.containerDetails){
                                                for(let i=2;i<container.length;i++){
                                                    extraContainers.push(container[i]);
                                                }
                                            }

                                            if (cellProps.rowData.flightOperationalStatus != 'N') {
                                                return <Content>
                                                    <div className="d-inline-flex flex-column">
                                                    {container[0]}
                                                    {container[1]}
                                                    </div>
                                                    {container &&
                                                        container.length > 2 ?
                                                        <div className="align-self-end d-inline-flex mar-l-2sm" onClick={this.gridUtitity} >
                                                            <span className="badge badge-pill light badge-info pointer" id={'containerextra' + cellProps.rowIndex} onClick={() => this.saveContainerPopoverId(cellProps.rowIndex)} >+{container.length - 2}</span>
                                                        </div>
                                                        : null}

                                                    {this.state.isContainerPopoverOpen && 'containerextra' + cellProps.rowIndex === 'containerextra' + this.state.containerPopoverId &&

                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isContainerPopoverOpen} target={'containerextra' + this.state.containerPopoverId} toggle={this.toggleContainerPopover} className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                        {extraContainers}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </Content>
                                            }
                                            else {
                                                return <Content><span className="text-black">No Container Info</span></Content>
                                            }
                                        }}
                                    </RowCell>
                                </Cell>
                            </IColumn>])}
                            {(this.props.displayMode === 'display' && isSubGroupEnabled('LUFTHANSA_SPECIFIC') === false) ? (
                                [<IColumn
                                    dataKey="upliftAirport"
                                    label="Uplift Airport"
                                    id="upliftAirport"
                                    width={90}
                                    flexGrow={0}
                                    key={keyValue++}
                                    selectColumn={true}
                                    hideOnExport>
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
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey="departureGate"
                                    id="departureGate"
                                    label="Gate info"
                                    width={90} flexGrow={1}
                                    className="d-flex"
                                    hideOnExport
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>{cellProps.cellData ? cellProps.cellData : '--'}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey=""
label="Container Info"
                                    width={100}
                                    id="manifestInfo"
                                    flexGrow={1}
                                    selectColumn={true}
className="d-flex"
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
            <Content>Container Info</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => {

                                                let container = [];
            let extraContainers = [];
            container = cellProps.rowData.containerDetails ? cellProps.rowData.containerDetails.map((value) => (<div className="mar-b-3xs d-flex line-15" key={keyValue++}>
            <div className="pad-r-sm">{value.containercount ? value.containercount : ''} <span className="text-light-grey">{value.containergroup ? value.containergroup : ''}</span></div>
            <div className="pad-r-sm">{value.mailbagcount ? value.mailbagcount : ''}<span className="text-light-grey"> Bags </span></div>
            <div>{value.mailbagweight != null && value.mailbagweight != undefined ? value.mailbagweight.roundedDisplayValue : ''}</div>
            </div>)) : container
            if(cellProps.rowData.containerDetails){
                for(let i=3;i<container.length;i++){
                    extraContainers.push(container[i]);
                }
            }

            if (cellProps.rowData.flightOperationalStatus != 'N' && cellProps.rowData.manifestInfo!="Manifested :Total :0Pre-advice :") {
                return <Content>
                    <div className="d-inline-flex flex-column">
                    {container[0]}
                    {container[1]}
                    {container[2]}
                    </div>
                    {container &&
                        container.length > 3 ?
                        <div className="align-self-end d-inline-flex mar-l-2sm" onClick={this.gridUtitity} >
                            <span className="badge badge-pill light badge-info pointer" id={'containerextra' + cellProps.rowIndex} onClick={() => this.saveContainerPopoverId(cellProps.rowIndex)} >+{container.length - 3}</span>
                        </div>
                        : null}

                    {this.state.isContainerPopoverOpen && 'containerextra' + cellProps.rowIndex === 'containerextra' + this.state.containerPopoverId &&

                        <div>
                            <IPopover placement="right" isOpen={this.state.isContainerPopoverOpen} target={'containerextra' + this.state.containerPopoverId} toggle={this.toggleContainerPopover} className="icpopover">
                                <IPopoverBody>
                                    <div className="pad-md pad-b-sm">
                                        {extraContainers}
                                    </div>
                                </IPopoverBody>
                            </IPopover>
                        </div>
                    }
                </Content>
            }
            else {
                return <Content><span className="text-black">No Container Info</span></Content>
            }
        }}
    </RowCell>
</Cell>
</IColumn>,
<IColumn
key={keyValue++}
dataKey=""
label="Pre-advice Info"
width={100}
id="preAdviceInfo"
flexGrow={1}
selectColumn={true}
hideOnExport>
<Cell>
<HeadCell disableSort>
{() => (
    <Content>Pre-advice Info</Content>)
}
</HeadCell>
<RowCell>
{(cellProps) => {
      //if (cellProps.rowData.flightOperationalStatus != 'N') {
        return <Content>
            {cellProps.rowData.preadvice ? cellProps.rowData.preadvice.totalBags :0} Bags {cellProps.rowData.preadvice.totalWeight ? cellProps.rowData.preadvice.totalWeight.roundedDisplayValue :0 } {this.props.stationWeightUnit}
        </Content>
    //}
    //else {
    //    return <Content><span className="text-black">No Container Info</span></Content>
    //}
                                            }}
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn dataKey=""
                                    label="Capacity Details"
                                    width={250}
                                    flexGrow={0}
                                    id="capacityDetails"
                                    selectColumn={true}
                                    hideOnExport
                                    key={keyValue++}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Capacity Details</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="d-flex">
                                                        <div>                                                       
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Mail : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].mailCapacity.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].mailUtised.roundedDisplayValue  : ''}
                                                            </div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Cargo : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].cargoCapacity.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].cargoUtilised.roundedDisplayValue  : ''}
                                                            </div>
                                                            <div>
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Total : </span>{cellProps.rowData.capacityDetails ?  cellProps.rowData.capacityDetails[0].segmentTotalWeight.roundedDisplayValue  + ' /' + cellProps.rowData.capacityDetails[0].totalUtilised.roundedDisplayValue  : ''}
                                                         
                                                        </div>
                                                        </div>
                                                        <div className="align-self-end">
                                                            {cellProps.rowData.capacityDetails &&
                                                                cellProps.rowData.capacityDetails.length > 1 ?
                                                                <div onClick={this.gridUtitity}>
                                                                    <div className="mar-l-xs badge badge-pill light badge-info" id={'capacityextra' + cellProps.rowIndex} onClick={() => this.savePopoverId(cellProps.rowIndex)} >+{cellProps.rowData.capacityDetails.length - 1}</div>
                                                                </div>
                                                                : null}
                                                        </div>
                                                    </div>

                                                    {this.state.isCapacityPopoverOpen && 'capacityextra' + cellProps.rowIndex === 'capacityextra' + this.state.capacityPopoverId &&

                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isCapacityPopoverOpen} target={'capacityextra' + this.state.capacityPopoverId} toggle={this.toggleCapacityPopover} className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                        {
                                                                            cellProps.rowData.capacityDetails.map((value) =>
                                                                                <div className="mar-b-2xs" key={keyValue++}>
                                                                                    <span className="text-light-grey">{value.segmentOrigin + ' - ' + value.segmentDestination}.Mail : </span>{  value.mailUtised.roundedDisplayValue + ' /' + value.mailCapacity.roundedDisplayValue}
                                                                                    <span className="text-light-grey"> Cargo : </span>{ value.cargoUtilised.roundedDisplayValue + ' /' + value.cargoCapacity.roundedDisplayValue}
                                                                                    <span className="text-light-grey"> Total : </span>{ value.totalUtilised.roundedDisplayValue + ' /' + value.segmentTotalWeight.roundedDisplayValue }
                                                                                </div>
                                                                            )}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                ]
                            ) : null
                            }
                            {(this.props.displayMode === 'display' && isSubGroupEnabled('LUFTHANSA_SPECIFIC') === true) ? (
                                [<IColumn
                                    dataKey="upliftAirport"
                                    label="Uplift Airport"
                                    id="upliftAirport"
                                    width={90}
                                    flexGrow={0}
                                    key={keyValue++}
                                    selectColumn={true}
                                    hideOnExport>
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
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey="departureGate"
                                    id="departureGate"
                                    label="Gate info"
                                    width={90} flexGrow={1}
                                    className="d-flex"
                                    hideOnExport
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData ? cellProps.cellData : '--'}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey=""
                                    label="Container Info"
                                    width={100}
                                    id="manifestInfo"
                                    flexGrow={1}
                                    selectColumn={true}
                                    className="d-flex"
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Container Info</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => {
                                                let container = [];
                                                let extraContainers = [];
                                                container = cellProps.rowData.containerDetails ? cellProps.rowData.containerDetails.map((value) => (<div className="mar-b-3xs d-flex line-15" key={keyValue++}>
                                                    <div className="pad-r-sm">{value.containercount ? value.containercount : ''} <span className="text-light-grey">{value.containergroup ? value.containergroup : ''}</span></div>
                                                    <div className="pad-r-sm">{value.mailbagcount ? value.mailbagcount : ''}<span className="text-light-grey"> Bags </span></div>
                                                    <div>{value.mailbagweight != null && value.mailbagweight != undefined ? value.mailbagweight.roundedDisplayValue : ''}</div>
                                                </div>)) : container
                                                if (cellProps.rowData.containerDetails) {
                                                    for (let i = 3; i < container.length; i++) {
                                                        extraContainers.push(container[i]);
                                                    }
                                                }
                                                if (cellProps.rowData.flightOperationalStatus != 'N' && cellProps.rowData.manifestInfo != "Manifested :Total :0Pre-advice :") {
                                                    return <Content>
                                                        <div className="d-inline-flex flex-column">
                                                            {container[0]}
                                                            {container[1]}
                                                            {container[2]}
                                                        </div>
                                                        {container &&
                                                            container.length > 3 ?
                                                            <div className="align-self-end d-inline-flex mar-l-2sm" onClick={this.gridUtitity} >
                                                                <span className="badge badge-pill light badge-info pointer" id={'containerextra' + cellProps.rowIndex} onClick={() => this.saveContainerPopoverId(cellProps.rowIndex)} >+{container.length - 3}</span>
                                                            </div>
                                                            : null}
                                                        {this.state.isContainerPopoverOpen && 'containerextra' + cellProps.rowIndex === 'containerextra' + this.state.containerPopoverId &&
                                                            <div>
                                                                <IPopover placement="right" isOpen={this.state.isContainerPopoverOpen} target={'containerextra' + this.state.containerPopoverId} toggle={this.toggleContainerPopover} className="icpopover">
                                                                    <IPopoverBody>
                                                                        <div className="pad-md pad-b-sm">
                                                                            {extraContainers}
                                                                        </div>
                                                                    </IPopoverBody>
                                                                </IPopover>
                                                            </div>
                                                        }
                                                    </Content>
                                                }
                                                else {
                                                    return <Content><span className="text-black">No Container Info</span></Content>
                                                }
                                            }}
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey=""
                                    label="Pre-advice Info"
                                    width={100}
                                    id="preAdviceInfo"
                                    flexGrow={1}
                                    selectColumn={true}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Pre-advice Info</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => {
                                                //if (cellProps.rowData.flightOperationalStatus != 'N') {
                                                return <Content>
                                                    {cellProps.rowData.preadvice ? cellProps.rowData.preadvice.totalBags : 0} Bags {cellProps.rowData.preadvice.totalWeight ? cellProps.rowData.preadvice.totalWeight.roundedDisplayValue : 0} {this.props.stationWeightUnit}
                                                </Content>
                                                //}
                                                //else {
                                                //    return <Content><span className="text-black">No Container Info</span></Content>
                                                //}
                                            }}
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn dataKey=""
                                    label="Capacity Details"
                                    width={250}
                                    flexGrow={0}
                                    id="capacityDetails"
                                    selectColumn={true}
                                    hideOnExport
                                    key={keyValue++}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Capacity Details</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                    <div className="d-flex">
                                                        <div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Mail : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].mailCapacity.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].mailUtised.roundedDisplayValue : ''}
                                                            </div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Cargo : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].cargoCapacity.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].cargoUtilised.roundedDisplayValue : ''}
                                                            </div>
                                                            <div>
                                                                <span className="text-light-grey">{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentOrigin + ' - ' + cellProps.rowData.capacityDetails[0].segmentDestination : ''}.Total : </span>{cellProps.rowData.capacityDetails ? cellProps.rowData.capacityDetails[0].segmentTotalWeight.roundedDisplayValue + ' /' + cellProps.rowData.capacityDetails[0].totalUtilised.roundedDisplayValue : ''}
                                                            </div>
                                                        </div>
                                                        <div className="align-self-end">
                                                            {cellProps.rowData.capacityDetails &&
                                                                cellProps.rowData.capacityDetails.length > 1 ?
                                                                <div onClick={this.gridUtitity}>
                                                                    <div className="mar-l-xs badge badge-pill light badge-info" id={'capacityextra' + cellProps.rowIndex} onClick={() => this.savePopoverId(cellProps.rowIndex)} >+{cellProps.rowData.capacityDetails.length - 1}</div>
                                                                </div>
                                                                : null}
                                                        </div>
                                                    </div>
                                                    {this.state.isCapacityPopoverOpen && 'capacityextra' + cellProps.rowIndex === 'capacityextra' + this.state.capacityPopoverId &&
                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isCapacityPopoverOpen} target={'capacityextra' + this.state.capacityPopoverId} toggle={this.toggleCapacityPopover} className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                        {
                                                                            cellProps.rowData.capacityDetails.map((value) =>
                                                                                <div className="mar-b-2xs" key={keyValue++}>
                                                                                    <span className="text-light-grey">{value.segmentOrigin + ' - ' + value.segmentDestination}.Mail : </span>{value.mailUtised.roundedDisplayValue + ' /' + value.mailCapacity.roundedDisplayValue}
                                                                                    <span className="text-light-grey"> Cargo : </span>{value.cargoUtilised.roundedDisplayValue + ' /' + value.cargoCapacity.roundedDisplayValue}
                                                                                    <span className="text-light-grey"> Total : </span>{value.totalUtilised.roundedDisplayValue + ' /' + value.segmentTotalWeight.roundedDisplayValue}
                                                                                </div>
                                                                            )}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn dataKey=""
                                    label="Positions"
                                    width={150}
                                    flexGrow={0}
                                    id="positions"
                                    selectColumn={true}
                                    hideOnExport
                                    key={keyValue++}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Positions</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                     {cellProps.rowData.volumeDetails &&
                                                                cellProps.rowData.volumeDetails.length > 0?
                                                    <div className="d-flex">
                                                        <div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].segmentOrigin + ' - ' + cellProps.rowData.volumeDetails[0].segmentDestination : ''}.LDC : </span>{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].altLowerDeckOne + ' /' + cellProps.rowData.volumeDetails[0].totUtlLowerDeckOne : ''}
                                                            </div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].segmentOrigin + ' - ' + cellProps.rowData.volumeDetails[0].segmentDestination : ''}.LDP : </span>{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].altLowerDeckTwo + ' /' + cellProps.rowData.volumeDetails[0].totUtlLowerDeckTwo : ''}
                                                            </div>
                                                            <div>
                                                                <span className="text-light-grey">{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].segmentOrigin + ' - ' + cellProps.rowData.volumeDetails[0].segmentDestination : ''}.MDP : </span>{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].altUpperDeckOne + ' /' + cellProps.rowData.volumeDetails[0].totUtlUpperDeckOne : ''}
                                                            </div>
                                                        </div>
                                                        <div className="align-self-end">
                                                            {cellProps.rowData.volumeDetails &&
                                                                cellProps.rowData.volumeDetails.length > 1 ?
                                                                <div onClick={this.gridUtitity}>
                                                                    <div className="mar-l-xs badge badge-pill light badge-info" id={'volextra' + cellProps.rowIndex} onClick={() => this.savePosPopoverId (cellProps.rowIndex)} >+{cellProps.rowData.volumeDetails.length - 1}</div>
                                                                </div>
                                                                : null}
                                                        </div>
                                                    </div>
                                                    :null}
                                                    {this.state.isVolPostnPopoverOpen && 'volextra' + cellProps.rowIndex === 'volextra' + this.state.posPopoverId &&
                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isVolPostnPopoverOpen} target={'volextra' + this.state.posPopoverId} toggle={this.togglePosPopover} className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                        {
                                                                            cellProps.rowData.volumeDetails.map((value) =>
                                                                                <div className="mar-b-2xs" key={keyValue++}>
                                                                                    <span className="text-light-grey">{value.segmentOrigin + ' - ' + value.segmentDestination}.LDC : </span>{value.altLowerDeckOne + ' /' + value.totUtlLowerDeckOne}
                                                                                    <span className="text-light-grey"> LDP : </span>{value.altLowerDeckTwo + ' /' + value.totUtlLowerDeckTwo}
                                                                                    <span className="text-light-grey"> MDP : </span>{value.altUpperDeckOne + ' /' + value.totUtlUpperDeckOne}
                                                                                </div>
                                                                            )}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>,
                                <IColumn
                                    key={keyValue++}
                                    dataKey=""
                                    label="Volume(cbm)"
                                    width={100}
                                    id="volume"
                                    flexGrow={1}
                                    selectColumn={true}
                                    hideOnExport>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content>Volume(cbm)</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                     {cellProps.rowData.volumeDetails &&
                                                                cellProps.rowData.volumeDetails.length >0 ?
                                                    <div className="d-flex">
                                                        <div>
                                                            <div className="mar-b-2xs">
                                                                <span className="text-light-grey">{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].segmentOrigin + ' - ' + cellProps.rowData.volumeDetails[0].segmentDestination : ''} : </span>{cellProps.rowData.volumeDetails ? cellProps.rowData.volumeDetails[0].altVolume.roundedDisplayValue + ' /' + cellProps.rowData.volumeDetails[0].totUtlVolume.roundedDisplayValue : ''}
                                                            </div>
                                                        </div>
                                                        <div className="align-self-end">
                                                            {cellProps.rowData.volumeDetails &&
                                                                cellProps.rowData.volumeDetails.length > 1 ?
                                                                <div onClick={this.gridUtitity}>
                                                                    <div className="mar-l-xs badge badge-pill light badge-info" id={'volextra' + cellProps.rowIndex} onClick={() => this.saveVolPopoverId(cellProps.rowIndex)} >+{cellProps.rowData.volumeDetails.length - 1}</div>
                                                                </div>
                                                                : null}
                                                        </div>
                                                    </div>
                                                    :null}
                                                    {this.state.isVolPopoverOpen && 'volextra' + cellProps.rowIndex === 'volextra' + this.state.volPopoverId &&
                                                        <div>
                                                            <IPopover placement="right" isOpen={this.state.isVolPopoverOpen} target={'volextra' + this.state.volPopoverId} toggle={this.toggleVolPopover} className="icpopover">
                                                                <IPopoverBody>
                                                                    <div className="pad-md pad-b-sm">
                                                                        {
                                                                            cellProps.rowData.volumeDetails.map((value) =>
                                                                                <div className="mar-b-2xs" key={keyValue++}>
                                                                                    <span className="text-light-grey">{value.segmentOrigin + ' - ' + value.segmentDestination} : </span>{value.altVolume.roundedDisplayValue + ' /' + value.totUtlVolume.roundedDisplayValue}
                                                                                </div>
                                                                            )}
                                                                    </div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                ]
                            ) : null
                            }
                            <IColumn
                                width={40}
                                flexGrow={0}
                                hideOnExport
                                selectColumn={false}>
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {cellProps.rowData.flightOperationalStatus === 'O' &&
                                                    <span onClick={this.gridUtitity}>

                                                        <IDropdown className="drop-icon-setwidth" portal={true}>
                                                            <IDropdownToggle className="dropdown-toggle btn btn-link no-pad more-toggle">
                                                                <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                            </IDropdownToggle>

                                                            <IDropdownMenu right={true} portal={true}>
                                                                <IDropdownItem data-mode="CLOSE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_CLOSE_FLIGHT" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Close Flight  </IDropdownItem>
                                                                <IDropdownItem data-mode="AUTO_ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_AUTO_ATTACH_AWB" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Auto Attach AWB</IDropdownItem>
                                                                <IDropdownItem data-mode="GENERATE_MANIFEST" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_GENERATE_MANIFEST" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Generate Manifest</IDropdownItem>
                                                                <IDropdownItem data-mode="PRE-ADVICE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRE_ADVICE" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Pre-Advice</IDropdownItem>
                                                                {isSubGroupEnabled('TURKISH_SPECIFIC') && <IDropdownItem data-mode="ULD_ANNOUNCE" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> ULD Announce</IDropdownItem>}
                                                                <IDropdownItem data-mode="PRINT_CN46" privilegeCheck={true} 
                                                                componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRINT_CN46" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Print CN46</IDropdownItem>
                                                            </IDropdownMenu>
                                                        </IDropdown>
                                                    </span>}

                                                {cellProps.rowData.flightOperationalStatus === 'C' &&
                                                    <span onClick={this.gridUtitity}>

                                                        <IDropdown className="drop-icon-setwidth" portal={true}>
                                                            <IDropdownToggle className="dropdown-toggle btn btn-link no-pad more-toggle">
                                                                <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                            </IDropdownToggle>

                                                            <IDropdownMenu right={true} portal={true}>
                                                                <IDropdownItem data-mode="REOPEN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REOPEN_FLIGHT" data-index={cellProps.rowIndex}  disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Re-Open Flight </IDropdownItem>

                                                                <IDropdownItem data-mode="AUTO_ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_AUTO_ATTACH_AWB" data-index={cellProps.rowIndex}  disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Auto Attach AWB</IDropdownItem>
                                                                <IDropdownItem data-mode="GENERATE_MANIFEST" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_GENERATE_MANIFEST" data-index={cellProps.rowIndex}  disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Generate Manifest</IDropdownItem>
                                                                <IDropdownItem data-mode="PRE-ADVICE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRE_ADVICE" data-index={cellProps.rowIndex}  disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Pre-Advice</IDropdownItem>
                                                                <IDropdownItem data-mode="PRINT_CN46" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRINT_CN46" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Print CN46</IDropdownItem>
                                                            </IDropdownMenu>
                                                        </IDropdown>
                                                    </span>}

                                                     {cellProps.rowData.flightOperationalStatus === 'N' &&
                                                    <span onClick={this.gridUtitity}>

                                                        <IDropdown className="drop-icon-setwidth">
                                                            <IDropdownToggle className="dropdown-toggle btn btn-link no-pad more-toggle">
                                                                <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                            </IDropdownToggle>

                                                            <IDropdownMenu right={true} >

                                                                <IDropdownItem data-mode="PRE-ADVICE" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRE_ADVICE" data-index={cellProps.rowIndex}  disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Pre-Advice</IDropdownItem>
                                                                {isSubGroupEnabled('TURKISH_SPECIFIC') && <IDropdownItem data-mode="ULD_ANNOUNCE" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> ULD Announce</IDropdownItem>}
                                                                <IDropdownItem data-mode="PRINT_CN46" privilegeCheck={true}
                                                                componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRINT_CN46" data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} onClick={this.flightAction}> Print CN46</IDropdownItem>
                                                            </IDropdownMenu>
                                                        </IDropdown>
                                                    </span>}

                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </ITable>
                    <GenerateManifestPopup onClickPrint={this.props.onClickPrint} show={this.state.showGenManifestPopup} toggle={this.onToggle} />
                    <PreAdvice show={this.state.showPreadvicePopup} flightValidation={this.props.flightValidation} selectedFlight={this.props.selectedFlight} onClickOK={this.onToggle} />
                </Fragment>
            ) : null

        )


    }
}
FlightDetailsTable.propTypes = {
    selectFlights:PropTypes.func,
    selectedMailflight:PropTypes.object,
    displayMode:PropTypes.string,
    saveIndexForMailManifes:PropTypes.func,
    performFlightAction:PropTypes.func,
    onPreadviceOK:PropTypes.func,
    saveSelectedFlights:PropTypes.func,
    updateSortVariables:PropTypes.func,
    flightDetails:PropTypes.array,
    flightCapacityDetails:PropTypes.object,
    oneTimeValues:PropTypes.object,
    flights:PropTypes.object,
    getNewPage:PropTypes.func,
    initialValues:PropTypes.object,
    flightCarrierView:PropTypes.string,
    onApplyFlightFilter:PropTypes.func,
    onClearFlightFilter:PropTypes.func,
    openAllMailbagsPopup:PropTypes.func,
    toggleCarditDisplay:PropTypes.func,
    openAllContainersPopup:PropTypes.func,
    carditView:PropTypes.string,
    selectedFlights:PropTypes.array,
    exportToExcel:PropTypes.func,
    flightActionsEnabled:PropTypes.string,
    onClickPrint:PropTypes.func,
    show:PropTypes.string,
    flightValidation:PropTypes.bool,
    selectedFlight:PropTypes.array,
    flightPreAdviceDetails:PropTypes.object,
    
}

//const flightDetailsTable = wrapForm('FlightDetailsForm')(FlightDetailsTable);
//export default flightDetailsTable;