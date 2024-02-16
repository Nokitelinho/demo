import React, { Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { Input } from 'reactstrap';
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import DiscrepancyPanel from './DiscrepancyPanel.jsx'
import FlightDetailsFilter from './filters/FlightDetailsFilter.jsx'
import FlightDetails from './custompanels/FlightDetails.jsx'
import PropTypes from 'prop-types'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';

const Aux = (props) => props.children;

export default class FlightDetailsTable extends React.PureComponent {
    constructor(props) {
        super(props);
         this.selectedFlights = []
        this.state = {
            rowClkCount: 0,
            indexArray:[],
            latestIndex:0,
            showDiscrepancy:false,
            showTransfer:false,
            checkBoxValue:{},
            selectFlag:false,
            checked:[],
            flightAllSelectFlag:false,
        }
    }

//starts here 
/*onSelectFlight = (event) => {
         let inLoc = event.target.dataset.index;
        this.setState({checkBoxValue:{inLoc:!(event.target.checked)}});
        let count = this.state.rowClkCount;
        (event.target.checked === true) ? count = ++count : count = --count
        
        let indexArray=[];
        indexArray=this.state.indexArray;
        (event.target.checked === true) ?
            indexArray.push(event.target.dataset.index):indexArray.splice(indexArray.indexOf(event.target.dataset.index), 1);
        this.setState({
            rowClkCount: count,
            indexArray:indexArray
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    }*/
//*******************  ends here 


//modified  STARTS HERE 
    onSelectFlight = (event) => {
        let count = this.state.rowClkCount;
        let index = event.target.dataset.index;
        
        let checked = this.state.checked;
        checked[index] = event.target.checked;
        this.setState({
        checked: checked,
        selectFlag:true,
        })
        let indexArray=[];
        indexArray=this.state.indexArray;
        if (event.target.checked == true) {
            indexArray.push(event.target.dataset.index);
            count = ++count
            
        } else {
            indexArray.splice(indexArray.indexOf(parseInt(event.target.dataset.index)), 1);
            count = --count
          
        }
        this.setState({
            rowClkCount: count,
             indexArray:indexArray
        })
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

//modified  ENDS HERE


    flightAction =(data) =>{
        this.props.commonFlightAction(data);
    }

    highlightRowOnSelection=(data)=>{
        if(this.props.displayMode == 'multi' && this.props.flightDetails && data.rowData){
                if(this.props.flightDetails.flightNo===data.rowData.flightNo && 
                    this.props.flightDetails.flightSeqNumber===data.rowData.flightSeqNumber &&
                    this.props.flightDetails.carrierId === data.rowData.carrierId &&
                    this.props.flightDetails.flightRoute === data.rowData.flightRoute){
                    return 'table-row table-row__bg-red'
                }
        }
        return 'table-row' 

    }

    indFlightAction = (event) => {
        let flightData=this.props.flightData.results;
        if (flightData[event.target.dataset.index].onlineAirportParam === 'N'&&this.props.valildationforimporthandling==='Y') {
            this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')
        } else {
        let actionName = event.target.dataset.mode;
        let indexArray =[];
        indexArray.push(event.target.dataset.index);
        // let dataToPass = event.target.dataset.data;
        let data={indexArray:indexArray,actionName:actionName,displayMode:this.props.displayMode,flightDetails:this.props.flightDetails};
        if(actionName==='CLOSE_FLIGHT'){
            this.props.commonFlightAction(data);
        }
        if(actionName==='OPEN_FLIGHT'){
            this.props.commonFlightAction(data);
        }
        if(actionName==='AUTO_ATTACH_AWB'){
            this.props.indFlightAction(data);
        }
        if(actionName==='DISCREPANCY'){
            this.props.indFlightAction(data);
        }
        if(actionName==='GENERATE_MANIFEST'){
            this.props.generateManifest(data);
        }
        if(actionName==='PRINT_CN46'){
            this.props.printCN46(data);
        }
        // let source = event.currentTarget.textContent.trim();
        // let actionName = source.split(/[ ,]+/).filter(function (v) { return v !== '' }).join('_').toUpperCase();
       
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    }
    allContainerAction = () => {
        this.props.openAllContainersPopup()
    }
    allMailbagsAction = () => {
        this.props.openAllMailbagsPopup()
    }
    onRowSelection = (data) => {
         if(data.index!==-1){
        //if (this.props.displayMode == 'display'|| this.props.displayMode === 'multi')
            this.props.onRowSelection({...data,fromTableRowClick:true});
             this.setState({latestIndex:data.index});
         }
    }
    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }


    toggleDiscrepancy=()=>{
       this.setState({showDiscrepancy:!this.state.showDiscrepancy});
    } 

    getFlightNumber=(rowData)=>{
        return rowData.carrierCode+rowData.flightNo;
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }
    clearAllSelect=()=>{
        this.props.clearAllSelect();
    }

   /* flightAllSelect=(event)=>{
           this.props.flightAllSelect(event.target.checked);
       
    }*/

//modified starts here  


selectAll = (event) => {
        // Set all checked states to true/false
        let selectArray = [];
        let flightsSelected = [];
        const count = this.props.flightData&&this.props.flightData.results?
            this.props.flightData.results.length : 0;
        for (let i = 0; i < count; i++) {
            selectArray.push(event.target.checked);
            flightsSelected.push(i);
        }
        if(!event.target.checked){
            flightsSelected=[];
        }
        this.setState({
            checked: selectArray,
            selectFlag: true,
            rowClkCount:event.target.checked?count:0,
            flightAllSelectFlag:event.target.checked,
            indexArray:flightsSelected,
        })

    }

    
    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }

    

//modified ENDS HERE
    setIndex=()=>{
           const indexDetails=this.props.indexDetails;
           if(indexDetails.checked){
               const indexArray=indexDetails.indexArray;
               if(indexArray!=null){
                   this.setState({
                    rowClkCount: indexDetails.indexArray.length,
                    indexArray:indexArray
                    })
               }
           }
            else{
                const indexArray=[];
                this.setState({
                    rowClkCount: 0,
                    indexArray:indexArray
                    })
            }
       this.clearAllSelect();
    }


    render() {
       /* const rowCount = (this.props.flightData)&&this.props.flightData.results ? this.props.flightData.results.length : 0;
        if(!isEmpty(this.props.indexDetails)){
                this.setIndex();
        }*/




// STARTS HERE

const results = this.props.flightData?this.props.flightData.results:[];
        // const flights=this.props.flights;      
        const rowCount = results?results.length:0;
        //  const rowCount = results.length;
        // let status = [];
        let clickCount=0;
        if(!this.state.selectFlag&&this.state.checked.length==0){ 
            let selectArray=[];
            for(let i=0;i<rowCount;i++){
                selectArray.push(false);
            }
            this.setState({
                checked:selectArray,
                selectFlag: true,
                })
        }
        
        for(let i=0;i<rowCount;i++){
            if(this.state.checked[i]==true){
                clickCount++;
            }
        }
        if(rowCount>0&&clickCount==rowCount){
            this.setState({
                flightAllSelectFlag:true
                })
        }
        else{
            this.setState({
                flightAllSelectFlag:false
                })
        }
        


//ENDS HERE

        return (
            (this.props.displayMode === 'display' || this.props.displayMode === 'multi') ? (
                <Fragment>
                                  <DiscrepancyPanel show={this.props.showDiscrepancy} onClose={this.props.closeDiscrepancy}
                                                        discrepancyData={this.props.discrepancyData} 
                        toggle={this.toggleDiscrepancy} />

                    <ITable
                        customHeader={{
                            headerClass: '',
                            //customPanel: customPanel,
                            customPanel: <FlightDetails rowClkCount={this.state.rowClkCount} flightAction={this.flightAction} flightActionsEnabled={this.props.flightActionsEnabled}
                                allMailbagsAction={this.allMailbagsAction} indexArray={this.state.indexArray} displayError={this.props.displayError}
                                allContainerAction={this.allContainerAction} displayMode={this.props.displayMode}   valildationforimporthandling={this.props. valildationforimporthandling}
                                flightDetails={this.props.flightDetails} flightData={this.props.flightData}/>,
                            "pagination": { "page": this.props.flightData ? this.props.flightData : null, getPage: this.props.listMailInbound,defaultPageSize:this.props.defaultPageSize, mode: this.props.displayMode === 'multi' ? 'subminimal' : null,
                            options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ] 
                         },
                            filterConfig: {
                                panel: <FlightDetailsFilter initialValues={this.props.initialValues}  />,
                                title: 'Filter',
                                onApplyFilter: this.props.onApplyFlightFilter,
                                onClearFilter: this.props.onClearFlightFilter
                            },
                            exportData: this.props.displayMode === 'display'?{
                                exportAction: this.props.exportToExcel,
                                pageable: true,
                                addlColumns:[{label:'Flight',excelData:this.getFlightNumber}],
                                name: 'Flight Details'
                             }:null,
                            sortBy: {
                                    onSort: this.sortList
                                        },
                            pageable: true,
                            dataConfig: {
                                    screenId: 'MTK064',
                                    tableId: 'flightlisttable'
                                },
                            
                        }}
                        rowCount={rowCount}
                            headerHeight={35}
                        className="table-list"
                        gridClassName="table_grid"
                        tableId='flightlisttable'
                        sortEnabled={true}
                        onRowSelection={this.onRowSelection}
                        rowClassName={this.highlightRowOnSelection}
                        rowHeight={50}
                        data={this.props.flightData?this.props.flightData.results:[]}>
                        <Columns >
                            <IColumn
                                    width={32}
                                dataKey="checkbox"
                                className={this.props.displayMode == 'multi' ? 'align-self-center' : ''}
                                flexGrow={0}>
                                

                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>
                                                <Input type="checkbox" checked={this.state.flightAllSelectFlag} onClick={this.selectAll}/>
                                            </Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                    <Input type="checkbox" data-index={cellProps.rowIndex}  
                                                        //checked={cellProps.rowData.checkBoxSelect||(this.state.checkBoxValue&&this.state.checkBoxValue.indexLoc)} 
                                                        checked={this.state.checked[cellProps.rowIndex]}
                                                       
                                                        onClick={this.onSelectFlight}  />
                                                <label ></label>
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>



                            </IColumn>
                            {(this.props.displayMode == 'multi') ? (
                                [
                                    <IColumn key={12} dataKey="flightNo" id="flightNo" label="Flight No" hideOnExport width={120} flexGrow={1} selectColumn={true} sortByItem={true} >
                                        <Cell>
                                            <HeadCell disableSort >
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (
                                                    <Content>
                                                        <div className="mar-b-2xs"><IFlightNumber 
                                            {...{carrierCode:cellProps.rowData.carrierCode,flightNumber:cellProps.rowData.flightNo,flightCarrierId:Number(cellProps.rowData.carrierId),flightSequenceNumber:Number(cellProps.rowData.flightSeqNumber),legSerialNumber:Number(cellProps.rowData.legSerialNumber),flightDate:cellProps.rowData.sta}}
                                            transactionCode="MALINB"
                                            showDate={false}
                                            mode='display'  ></IFlightNumber></div>
                                                        <div className="text-light-grey">{cellProps.rowData.flightDate}</div>
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>,
                                    <IColumn key={1} dataKey="flightRoute" id="flightRoute" label="Flight Route" width={180} flexGrow={1} selectColumn={true} sortByItem={true} >
                                        <Cell>
                                            <HeadCell disableSort>
                                                {() => (
                                                    <Content></Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (
                                                    <Content>
                                                        <div className="mar-b-3xs">
                                                            {cellProps.rowData.flightOperationStatus === 'Open' &&
                                                                <span className="badge badge-pill light badge-active">Open</span>
                                                            }
                                                            {cellProps.rowData.flightOperationStatus === 'Closed' &&
                                                                <span className="badge badge-pill light badge-error">Closed</span>
                                                            }
                                                            {cellProps.rowData.flightOperationStatus === 'New' &&
                                                                <span className="badge badge-pill light badge-info">New</span>
                                                            }
                                                        </div>
                                                        <div>
                                                            <span className="pl-0 separation">
                                                                {
                                                                    (cellProps.cellData.split('-')).map((route, index) => {
                                                                        if (index === (cellProps.cellData.split('-')).length - 1) {
                                                                            return <span className="text-light-grey">{route}</span>;
                                                                        } else {
                                                                            return <Aux key={index}><span className="text-light-grey">{route}</span>
                                                                                <i className="icon ico-air-sm mar-x-2xs"></i></Aux>
                                                                        }
                                                                    })
                                                                }
                                                            </span>
                                                        </div>
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                ]
                            ) : null}
                            {(this.props.displayMode == 'display') ? (
                                [
                                    <IColumn key={3} dataKey="flightNo" id="flightNo" label="Flight No" hideOnExport width={110} flexGrow={0} selectColumn={true} sortByItem={true} >
                                <Cell>
                                    <HeadCell disableSort >
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content><span><IFlightNumber 
                                            {...{carrierCode:cellProps.rowData.carrierCode,flightNumber:cellProps.rowData.flightNo,flightCarrierId:Number(cellProps.rowData.carrierId),flightSequenceNumber:Number(cellProps.rowData.flightSeqNumber),legSerialNumber:Number(cellProps.rowData.legSerialNumber),flightDate:cellProps.rowData.sta}}
                                            transactionCode="MALINB"
                                            showDate={false}
                                            mode='display'></IFlightNumber></span></Content>)
                                        }
                                    </RowCell>
                                </Cell>
                                    </IColumn>,
                                    <IColumn key={4} dataKey="flightRoute" id="flightRoute" label="Flight Route" width={100} flexGrow={0} selectColumn={true} sortByItem={true} >
                                <Cell>
                                    <HeadCell disableSort>
                                        {(cellProps) => (
                                            <Content>{cellProps.label}</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                             
                                              <span className="pl-0 separation">
                                                  
                                                  {
                                                     
                                                      (cellProps.cellData.split('-')).map((route, index) => {
                                                          if (index === (cellProps.cellData.split('-')).length - 1) {
                                                              return route;
                                                          } else {
                                                              return <Aux key={index}>{route}<i className="icon ico-air-sm mar-x-2xs"></i> </Aux>
                                                          }
                                                      })
                                                  }
                                              </span>
                                                  
                                            </Content>)
                                        }
                                    </RowCell>

                                </Cell>
                                    </IColumn>,
                                    <IColumn  key={2} dataKey="flightDate" id="flightDate" label="Flight Date" width={110} flexGrow={0} selectColumn={true} sortByItem={true} >
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

                                        <IColumn key={5} dataKey="status" id="status" label="Status" width={80} flexGrow={0} selectColumn={true} sortByItem={true} >
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                                    <Content>{(cellProps.cellData==='Active')?
                                                        <span className="badge badge-pill high-light badge-active">Active</span> : <span className="badge badge-pill high-light badge-alert">{cellProps.cellData}</span>}
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>,
                                        <IColumn key={6} dataKey="aircraftType" id="aircraftType" label="Aircraft Type" width={100} flexGrow={0} selectColumn={true} sortByItem={true}>
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
                                        <IColumn key={7} dataKey="gateInfo" id="gateInfo"  label="Gate info" width={80} flexGrow={0}  selectColumn={true}>
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
                                    </IColumn>,
                                        <IColumn key={8} dataKey="recievedInfo" id="recievedInfo" label="Recieved info" width={100} flexGrow={1}  selectColumn={true}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                                    <Content>
                                                            {(cellProps.cellData) ? (<div>
                                                                <div className="pad-b-3xs">({cellProps.cellData.split("-")[0]}) Manifested</div>
                                                                <div className="pad-b-3xs">({cellProps.cellData.split("-")[1]}) Recieved</div>
                                                            </div>) : '--'}
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>,
                                        <IColumn key={9} dataKey="port" id="port" label="Port" width={50} flexGrow={0}  selectColumn={true}>
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
                                        <IColumn key={10} dataKey="manifestInfo" id="manifestInfo" label="Manifest Info" width={100} flexGrow={1}  selectColumn={true}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (
                                                    <Content>
                                                        
                                                        
                                                       {/* cellProps.cellData*/}

                                                      <span className="pl-0 separation">
                                                  
                                                        {
                                                     
                                                      (cellProps.cellData.split('|')).map((data, index) => {
                                                          if (index === (cellProps.cellData.split('|')).length - 1) {
                                                              return data;
                                                          } else {
                                                              return <Aux key={index}>{data}<br></br> </Aux>
                                                          }
                                                      })
                                                        }
                                                    </span>
                                                        
                                                        
                                                        </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>,
                                    <IColumn key={11} dataKey="flightOperationStatus" id="flightOperationStatus" label="Flight Op status" width={100} flexGrow={0} selectColumn={true}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (
                                                    <Content>
                                                        {cellProps.cellData === 'Open' &&
                                                            <span className="badge badge-pill light badge-active">Open</span>
                                                        }
                                                        {cellProps.cellData === 'Closed' &&
                                                            <span className="badge badge-pill light badge-error">Closed</span>
                                                        }
                                                        {cellProps.cellData === 'New' &&
                                                            <span className="badge badge-pill light badge-info">New</span>
                                                        }
                                                    </Content>)
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                   
                                ]
                            ) : null
                            
                            }


                            <IColumn width={40} flexGrow={0}
                            className={this.props.displayMode == 'multi' ? 'align-self-center' : ''}>
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                                <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {(cellProps.rowData.flightOperationStatus!='New') ?
                                                    <span onClick={this.gridUtitity}>
                                                        <IDropdown portal={true}>
                                                            <IDropdownToggle className="dropdown-toggle more-toggle">
                                                                <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                            </IDropdownToggle>
                                                            <IDropdownMenu portal={true} right={true} >
                                                            { (cellProps.rowData.flightOperationStatus==='Open')?  <IDropdownItem data-mode="CLOSE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CLOSE_FLIGHT" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} disabled={!this.props.flightActionsEnabled} > Close Flight  </IDropdownItem>:[]}
                                                            { (cellProps.rowData.flightOperationStatus==='Closed')?   <IDropdownItem data-mode="OPEN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_OPEN_FLIGHT" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} disabled={!this.props.flightActionsEnabled} > Open Flight </IDropdownItem>:[]}
                                                           { (cellProps.rowData.flightOperationStatus==='Open')?    <IDropdownItem data-mode="AUTO_ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_AUTO_ATTACH_AWB" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} disabled={!this.props.flightActionsEnabled} > Auto Attach AWB</IDropdownItem>:[]}
                                                                <IDropdownItem data-mode="GENERATE_MANIFEST" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_GENERATE_MANIFEST" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} disabled={!this.props.flightActionsEnabled} > Generate Manifest</IDropdownItem>
                                                                <IDropdownItem data-mode="DISCREPANCY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_DISCREPANCY" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} > Discrepancy</IDropdownItem>
                                                                <IDropdownItem data-mode="PRINT_CN46" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_PRINT_CN46" data-index={cellProps.rowIndex} data-containerdata={"cellProps"} onClick={this.indFlightAction} disabled={!this.props.flightActionsEnabled} > Print CN46</IDropdownItem>
                                                            </IDropdownMenu>
                                                        </IDropdown>
                                                    </span>
                                                    : null}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>
                    </ITable>
                </Fragment>
            ) : null
        );
    }
}
FlightDetailsTable.propTypes = {
    displayMode: PropTypes.string,
    onRowSelection: PropTypes.func,
    flightData: PropTypes.object,
    onApplyFlightFilter: PropTypes.func,
    onClearFlightFilter: PropTypes.func,
    flightAction: PropTypes.func,
    allContainerAction: PropTypes.func,
    allMailbagsAction: PropTypes.func,
    commonFlightAction:PropTypes.func,
    indFlightAction:PropTypes.func,
    generateManifest:PropTypes.func,
    openAllContainersPopup:PropTypes.func,
    openAllMailbagsPopup:PropTypes.func,
    updateSortVariables:PropTypes.func,
    clearAllSelect:PropTypes.func,
    indexDetails:PropTypes.object,
    showDiscrepancy:PropTypes.string,
    closeDiscrepancy:PropTypes.func,
    discrepancyData:PropTypes.array,
    listMailInbound:PropTypes.func,
    initialValues:PropTypes.object,
    defaultPageSize:PropTypes.string,
    exportToExcel:PropTypes.func,
    displayError:PropTypes.func,
    valildationforimporthandling:PropTypes.string
}

