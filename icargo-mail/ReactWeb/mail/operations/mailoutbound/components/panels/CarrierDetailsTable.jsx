import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content }
    from 'icoreact/lib/ico/framework/component/common/grid';
    import FlightDetailsFilter from './filters/FlightDetailsFilter.js';
    import CarrierDetailsFilter from './filters/CarrierDetailsFilter.js';
import FlightDetails from './custompanels/FlightDetails.js';
import PropTypes from 'prop-types';
//import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
export default class CarrierDetailsTable extends Component {

    constructor(props) {
        super(props);
        this.selectedCarriers = []
        this.state = {
            showGenManifestPopup: false
        }

    }

    onRowSelection = (data) => {
   
        if (data.index > -1) {
            if (data.isRowSelected) {
               
                this.props.selectFlights({flightIndex:data.index});
            } else {
               
            }
        }
        
    }

    getRowClassName=(row)=> {
        const data=row.rowData;
        
        const selectedCarrier=this.props.selectedMailflight?this.props.selectedMailflight:'';
        const destination = selectedCarrier.destination;

        if (destination === data.destination && this.props.displayMode === 'multi') {
            return 'table-row table-row__bg-red'
        }
        else {
            return 'table-row'
        }

    }
    
    getManifestInfo =(rowData) =>{

let manifestInfo=""
 manifestInfo=manifestInfo +("Manifested :");
    for(var i=0;i<rowData.containerDetails.length;i++) {
   
 manifestInfo=manifestInfo+(rowData.containerDetails[i].containercount + " - " + rowData.containerDetails[i].containergroup + "(" + rowData.containerDetails[i].mailbagcount +"/"+ rowData.containerDetails[i].mailbagweight.formattedDisplayValue+")");
  
    }
return manifestInfo;
}

    

    onSelect = (event) => {
        let count = this.state.rowClkCount;
        let index = event.target.dataset.index;
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
        for (let i = 0; i < this.selectedFlights.length; i++) {
            var element = this.selectedFlights[i];
            if (element === flight) {
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

    flightAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        if (actionName === 'GENERATE_MANIFEST') {
            this.genManifestClk();
        }
        // let source = event.currentTarget.textContent.trim();
        // let actionName = source.split(/[ ,]+/).filter(function (v) { return v !== '' }).join('_').toUpperCase();
        else {
            this.props.flightAction({ type: actionName, index });
        }
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    flightCloseAction = (data) => {
        // let actionName =event.target.dataset.mode;
        let status = 'C';
        if (status === data.flightOperationalStatus) {
            this.props.displayError('Flight is already Closed', '')
        } else {
            //  this.props.flightAction({ type: actionName,data });
            this.props.flightCloseAction(data);
        }
        //  let actionName =event.target.dataset.mode
        // let source = event.currentTarget.textContent.trim();
        // let actionName = source.split(/[ ,]+/).filter(function (v) { return v !== '' }).join('_').toUpperCase();

        //  event.stopPropagation();
        //   event.nativeEvent.stopImmediatePropagation();
    }


    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    genManifestClk = () => {
        this.setState({ showGenManifestPopup: true });
    }

    onToggle = () => {
        this.setState({ showGenManifestPopup: false });
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateCarrierSortVariables(sortBy, sortByItem,this.props.displayMode);
    }

    onApplyCarrierFilter = () => {
        this.props.onApplyCarrierFilter(this.props.displayMode);
    }
    
    onSelect = (event) => {
        let count = this.state.rowClkCount;
        let index = event.target.dataset.index;
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

    render() {
        const results = this.props.carrierDetails ? this.props.carrierDetails:'';
        // const carriers=this.props.carriers;      
        const rowCount=results.length;
        return (

            <Fragment>
                <ITable
                    customHeader={{
                        headerClass: '',
                        "pagination": { "page": this.props.carriers, getPage: this.props.getNewPage, mode: this.props.displayMode === 'multi' ? 'subminimal' : '',
                        options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ]  },
                        filterConfig: {
                            panel: <CarrierDetailsFilter initialValues= {this.props.initialCarrierValues}/>,
                            title: 'Filter',
                            onApplyFilter: this.onApplyCarrierFilter,
                            onClearFilter: this.props.onClearCarrierFilter
                        },
                        customPanel: this.props.displayMode === 'display' ? <FlightDetails rowClkCount={this.state.rowClkCount} displayMode={this.props.displayMode}
                        openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} carditView={this.props.carditView}
                        toggleCarditDisplay={this.props.toggleCarditDisplay} />:null,
                       // exportData: this.props.displayMode === 'display' ? {
                        //exportAction: this.props.exportToExcel,
                       //         pageable: true,
                       //         name: 'Flight List'
                       // } : null,

                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            addlColumns: [
							  {label:'Container Info',excelData:this.getManifestInfo}
							],
                           // addlColumns:[{label:'Weight',excelData:this.getWeight},{label:'Consignment Number',dataKey :'consignmentNumber'},{label:'Consignment Date',dataKey :'consignmentDate'},{label:'AWB Number',excelData :this.getAWBNumber},{label:'RDT',excelData :this.getRDTValue},{label:'ULD Number',dataKey :'uldNumber'},{label:'RI',dataKey :'registeredOrInsuredIndicator'}],
                            name: 'Carrier List'},
                        sortBy:{
                            onSort:this.sortList
                        },
                        pageable: true,
                        dataConfig:this.props.displayMode === 'display' ?{
                            screenId:'MTK060',
                            tableId:'carrierlisttable'
                        }:null
                            
                       
                    }}
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName="table_grid"
                    tableId='carrierlisttable'
                    onRowSelection={this.onRowSelection}
                    data={results}
                    rowClassName={this.getRowClassName}
                    rowHeight={45}
                >
                    <Columns>
                    {(this.props.displayMode === 'display') ? (
                            <IColumn
                                width={32}
                                dataKey=""
                                className="">
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.onSelect} /></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content><input type="checkbox" data-index={cellProps.rowIndex} onClick={this.onSelect} /></Content>)

                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>):null}
                        <IColumn
                            dataKey="carrierCode"
                            label="Carrier Code"
                            flexGrow={0}
                            id="carrierCode"
                            width={90}
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
                            dataKey="pol"
                            label="Uplift Airport"
                            flexGrow={0}
                            id="pol"
                            width={110}
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
                            dataKey="destination"
                            label="Destination"
                            flexGrow={0}
                            id="destination"
                            width={90}
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
                        {(this.props.displayMode === 'display') ? (
                            <IColumn
                                dataKey=""
                                label="Container Info"
                                id="manifestInfo"
								hideOnExport
                                width={150}
                                flexGrow={1}
                                selectColumn={true}>
                                
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>Container Info</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => {

                                            let container = [];
                                            container = cellProps.rowData.containerDetails ? cellProps.rowData.containerDetails.map((value) => (<div className="mar-r-2xs" key={1}>{value.containercount ? value.containercount : ''} - {value.containergroup ? value.containergroup : ''} ({value.mailbagcount ? value.mailbagcount : ''}/{value.mailbagweight != null && value.mailbagweight != undefined ? value.mailbagweight.formattedDisplayValue : ''})</div>)):'';

                                            return <Content>
                                                <div className="d-inline-flex flex-wrap">
                                                    <div className="text-grey mar-r-2xs">Manifested :</div>{container}
                                                </div>
                                            </Content>
                                        }}
                                    </RowCell>
                                </Cell>
                            </IColumn>) : null}
                    </Columns>
                </ITable>
            </Fragment>
        )
    }
}
CarrierDetailsTable.propTypes = {
    selectFlights:PropTypes.func,
    selectedMailflight:PropTypes.object,
    saveSelectedFlights:PropTypes.func,
    displayMode:PropTypes.string,
    flightAction:PropTypes.func,
    displayError:PropTypes.func,
    flightCloseAction:PropTypes.func,
    carrierDetails:PropTypes.array,
    carriers:PropTypes.object,
    getNewPage:PropTypes.func,
    initialValues:PropTypes.object,
    onApplyFlightFilter:PropTypes.func,
    onClearFlightFilter:PropTypes.func,
    openAllMailbagsPopup:PropTypes.func,
    toggleCarditDisplay:PropTypes.func,
    exportToExcel:PropTypes.func,
    openAllContainersPopup:PropTypes.func,
}
//const flightDetailsTable = wrapForm('FlightDetailsForm')(FlightDetailsTable);
//export default flightDetailsTable;