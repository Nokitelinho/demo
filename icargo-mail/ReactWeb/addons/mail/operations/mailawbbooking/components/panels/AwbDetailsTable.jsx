import React, { Component,Fragment } from 'react';
import { IColumn, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Constants } from '../../constants/constants.js'
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import AwbDetailsFilter from './AwbDetailsFilter.jsx';


export default class AwbDetailsTable extends Component {
          
        constructor(props){
            super(props);
            this.state={
                shipmentMap : null,
                isMailSccOpen: false,
                isFlightOpen: false,
                currentSccTarget:null,
                currentFlightTarget:null,
            }
            this.selectedAwbs = []
        }

        static getDerivedStateFromProps(props, state){
            let shipmentMap = new Map();
            if (!isEmpty(props.oneTimeValues)) {
                if (!isEmpty(props.oneTimeValues['operations.shipment.shipmentstatus'])){
                    props.oneTimeValues['operations.shipment.shipmentstatus'].forEach(element => {
                        shipmentMap.set(element.fieldValue,element.fieldDescription)
                    });
                }
            }
            return {
                shipmentMap: shipmentMap,
              };
        }


        getShipmentStatus=(cellData)=>{
            let shipmentStatus=null;
            if(cellData!=null)
                if (!isEmpty(this.state.shipmentMap)){ 
                    shipmentStatus=this.state.shipmentMap.get(cellData);
                    if(shipmentStatus==='Executed'){
                        shipmentStatus=<span class="badge badge-pill light badge-active">{shipmentStatus}</span>
                    }
                    else if(shipmentStatus==='Reopened'){
                        shipmentStatus=<span class="badge badge-pill light badge-error">{shipmentStatus}</span>
                    }
                    else if(shipmentStatus==='New'){
                        shipmentStatus=<span class="badge badge-pill light badge-info">{shipmentStatus}</span>
                    }
                    else{
                        shipmentStatus=<span class="badge badge-pill light badge-alert">{shipmentStatus}</span>
                    }
                }
            return shipmentStatus;        
        }

        getBookingStatus = (cellData) =>{
            if(cellData!=null){
                return cellData===Constants.CONFIRM_CODE?
                    Constants.CONFIRM:Constants.QUEUED;
            }
        }

        toggleMailSccPop = (event) =>{
            const currentTarget=event.currentTarget.id;
            this.setState({ isMailSccOpen: !this.state.isMailSccOpen, currentSccTarget: currentTarget })
        }

        toggleFlightPop = (event) =>{
            const currentTarget=event.currentTarget.id;
            this.setState({ isFlightOpen: !this.state.isFlightOpen, currentFlightTarget: currentTarget })
        }

        displayHeader= () =>{
            return <h4 class="fs16 font-weight-bold">{Constants.TABLE_HEADER}</h4>
        }

        onRowSelection = (data) => {
            if (data.index > -1) {
                if (data.isRowSelected) {
                    this.selectAwb(data.index);
            }else {
                this.unSelectAwb(data.index);
            }
        }else {
            if((data) && (data.checked)) {
                this.selectAllAwbs(-1);
            }
             else {
                if(this.selectedAwbs.length>0){
                    this.unSelectAllAwbs();
                }
            }
        }
    }    

    selectAwb = (awb) => {
        this.selectedAwbs.push(awb);
        this.props.saveSelectedAwbsIndex(this.selectedAwbs);
    }

    unSelectAwb = (awb) => {
        let index = -1;
        for (let i = 0; i < this.selectedAwbs.length; i++) {
            var element = this.selectedAwbs[i];
            if (element === awb) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedAwbs.splice(index, 1);
        }
        this.props.saveSelectedAwbsIndex(this.selectedAwbs);
    }

    selectAllAwbs = () => {
        for (let i = 0; i < this.props.awbDetails.results.length; i++) {
            this.selectedAwbs.push(i);
        }
        this.props.saveSelectedAwbsIndex(this.selectedAwbs);
    }

    unSelectAllAwbs = () => {
        this.selectedAwbs = []
        this.props.saveSelectedAwbsIndex(this.selectedAwbs);
    }

    sortList = (sortBy, sortByItem) => {
        this.props.updateSortVariables(sortBy, sortByItem);
    }

    onApplyAwbFilter=()=>{
        this.props.onApplyAwbFilter(this.props.displayPage,this.props.pageSize);
    }

    onClearAwbFilter=()=>{
        this.props.onClearAwbFilter();
    }

    render() {
            
            const results=(this.props.awbDetails&&this.props.awbDetails.results)?
                                this.props.awbDetails.results:[];

        return (
            <Fragment>
                <IMultiGrid
                    rowCount={results.length}
                    headerHeight={100}
                    className="table-list"
                    rowHeight={140}
                    rowClassName="table-row"
                    tableId="mailAwbBookingTable"
                    sortEnabled={false}
                    form={true}
                    onRowSelection={this.onRowSelection}
                    customHeader={{
                        headerClass: '',
                        customPanel: this.displayHeader(),
                        dataConfig: {
                            screenId: 'MTK071',
                            tableId: 'mailAwbBookingTable'
                        },
                        "pagination":{"page":this.props.awbDetails,getPage:this.props.getNewPage},
                        filterConfig: {
                            panel: <AwbDetailsFilter oneTimeValues={this.props.oneTimeValues} initialValues={this.props.filter}/>,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyAwbFilter,
                            onClearFilter: this.props.onClearAwbFilter
                        },
                        exportData: {
                            exportAction: this.props.exportToExcel,
                            pageable: true,
                            addlColumns:[],
                            name: 'AWB Details',
                        },
                        sortBy: {
                            onSort: this.sortList
                        }
                    }}
                    additionalData={{}}
                    data={results}
                    enableFixedRowScroll
                    width={'100%'}
                    hideTopRightGridScrollbar
                    hideBottomLeftGridScrollbar
                    fixedRowCount={0}
                    fixedColumnCount={0}

                >
                    <Columns>

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
                            dataKey="masterDocumentNumber"
                            label="AWB No."
                            width={100}
                            flexGrow={1}
                            id="masterDocumentNumber"
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
                                            <span>
                                                {cellProps.rowData.shipmentPrefix}{cellProps.rowData.shipmentPrefix ? '-' : ''}
                                                {cellProps.cellData}
                                            </span>
                                </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>                    
                        <IColumn
                            //dataKey="flightDetails"
                            dataKey ="bookingFlightNumber"
                            label="Flight Number"
                            flexGrow={1}
                            //id="flightDetails"
                            id="bookingFlightNumber"
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
                                        <Content>
                                            <div> 
                                                <div> 
                                                    {cellProps.rowData.bookingCarrierCode}{' '}{cellProps.cellData}
                                                    {/* {cellProps.cellData[0].split(' ')[2]+' '+cellProps.cellData[0].split(' ')[0]+' '} */}
                                                </div>
                                            </div>
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="agentCode"
                            label="Agent Code"
                            flexGrow={1}
                            id="agentCode"
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
                                        <Content>{cellProps.cellData}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="mailScc"
                            label="SCC"
                            flexGrow={1}
                            id="mailScc"
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
                                        <Content>
                                            {cellProps.cellData&&cellProps.cellData.length>1&&cellProps.cellData.split(',').length>1 ?
                                                <div>
                                                    {cellProps.cellData.split(',')[0] + ' '} <span id={'mailSccAdd' + cellProps.rowIndex} onClick={this.toggleMailSccPop} class="badge badge-pill light badge-info mar-l-3xs">+{cellProps.cellData.split(',').length - 1}</span>
                                                    {this.state.isMailSccOpen && this.state.currentSccTarget === 'mailSccAdd' + cellProps.rowIndex &&
                                                        <div>
                                                            <IPopover isOpen={this.state.isMailSccOpen} target={'mailSccAdd'+cellProps.rowIndex}> >
                                                                <IPopoverBody>
                                                                        <div className='pad-sm'>{cellProps.cellData.split(',').splice(1).toString()}</div>
                                                                </IPopoverBody>
                                                            </IPopover>
                                                        </div>
                                                    }
                                                </div>:
                                                <div>
                                                    {cellProps.cellData}
                                                </div>

                                            }
                                            
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="shipmentDate"
                            label="Shipping Date"
                            flexGrow={1}
                            id="shipmentDate"
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
                                        <Content>{cellProps.cellData}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="awbOrgin"
                            label="AWB Org"
                            width={70}
                            flexGrow={1}
                            id="awbOrgin"
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
                            dataKey="awbDestination"
                            label="AWB Des"
                            width={70}
                            flexGrow={1}
                            id="awbDestination"
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
                            dataKey="bookedPieces"
                            label="Booked Pcs"
                            width={90}
                            flexGrow={1}
                            id="bookedPieces"
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
                                        {cellProps.cellData}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="bookedWeight"
                            label="Booked Wgt"
                            width={100}
                            flexGrow={1}
                            id="bookedWeight"
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
                            dataKey="bookedVolume"
                            label="Booked Vol"
                            width={90}
                            flexGrow={1}
                            id="bookedVolume"
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
                            dataKey="bookingStatus"
                            label="Status"
                            width={60}
                            flexGrow={1}
                            id="bookingStatus"
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
                            dataKey="shipmentStatus"
                            label="Shipment Status"
                            width={120}
                            flexGrow={1}
                            id="shipmentStatus"
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
                                        <Content>{this.getShipmentStatus(cellProps.cellData)}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="bookingStation"
                            label="Booking Stn"
                            width={100}
                            flexGrow={1}
                            id="bookingStation"
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
                            dataKey="bookingDate"
                            label="Booking Date"
                            width={100}
                            flexGrow={1}
                            id="bookingDate"
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
                            dataKey="remarks"
                            label="Remarks"
                            width={100}
                            flexGrow={1}
                            id="remarks"
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
                    </Columns>
                </IMultiGrid>
            </Fragment>
        );
    
    }
}
