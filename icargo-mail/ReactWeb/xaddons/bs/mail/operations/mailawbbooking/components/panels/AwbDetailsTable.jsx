import React, { Component,Fragment } from 'react';
import { IColumn, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Constants,Key,ComponentId } from '../../constants/constants.js'
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip';


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
        }

        static getDerivedStateFromProps(props, state){
            let shipmentMap = new Map();
            if (!isEmpty(props.oneTimeValues)) {
                if (!isEmpty(props.oneTimeValues[Key.SHIPMENT_STATUS_LIST])){
                    props.oneTimeValues[Key.SHIPMENT_STATUS_LIST].forEach(element => {
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

        attachAwb= (event) =>{
            const index=event.target.dataset.index;
            this.props.attachAwb(index);
            console.log(event);
        }

        render() {
            
            const results=(this.props.awbDetails&&this.props.awbDetails.results)?
                                this.props.awbDetails.results:[];

        return (
            <Fragment>
                <IMultiGrid
                    rowCount={results.length}
                    headerHeight={45}
                    className="table-list"
                    rowHeight={38}
                    rowClassName="table-row"
                    tableId="mailAwbBookingTable"
                    sortEnabled={false}
                    form={true}
                    onRowSelection={this.onRowSelection}
                    customHeader={{
                                headerClass: '',
                                customPanel: this.displayHeader(),
                                "pagination":{"page":this.props.awbDetails,getPage:this.props.getNewPage},
                
                    }}
                    additionalData={{}}
                    data={results}
                    enableFixedRowScroll
                    width={'100%'}
                    hideTopRightGridScrollbar
                    hideBottomLeftGridScrollbar
                    fixedRowCount={1}
                    fixedColumnCount={2}

                >
                    <Columns>

                        <IColumn
                            dataKey="masterDocumentNumber"
                            label="AWB No."
                            width={100}
                            flexGrow={1}
                            hideOnExport
                            id="masterDocumentNumber">
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
                            dataKey="flightDetails"
                            label="Flight Details"
                            flexGrow={1}
                            id="flightDetails"
                            width={180}>
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
                                                {cellProps.cellData != null && cellProps.cellData.length > 1 ?
                                                    <div>
                                                        {cellProps.cellData[0].split(' ')[2] + ' ' + cellProps.cellData[0].split(' ')[0] + ' '} <span class="text-light-grey"> {cellProps.cellData[0].split(' ')[1]} </span>
                                                        <span id={'flightAdd' + cellProps.rowIndex} onClick={this.toggleFlightPop} class="badge badge-pill light badge-info mar-l-3xs">+{cellProps.cellData.length - 1}</span>
                                                        {this.state.isFlightOpen && this.state.currentFlightTarget === 'flightAdd' + cellProps.rowIndex &&
                                                            <div>
                                                                <IPopover isOpen={this.state.isFlightOpen} target={'flightAdd'+cellProps.rowIndex}> >
                                                                    <IPopoverBody>
                                                                        <div className="pad-sm">
                                                                            {cellProps.cellData.map((element, index) => {
                                                                                if (index != 0) {
                                                                                    return <div>
                                                                                        {element.split(' ')[2] + ' ' + element.split(' ')[0] + ' '} <span class="text-light-grey"> {element.split(' ')[1]} </span>
                                                                                    </div>
                                                                                }
                                                                            })
                                                                            }
                                                                        </div>
                                                                    </IPopoverBody>
                                                                </IPopover>
                                                            </div>
                                                        }
                                                </div>:
                                                <div> {cellProps.cellData[0].split(' ')[2]+' '+cellProps.cellData[0].split(' ')[0]+' '} <span class="text-light-grey"> {cellProps.cellData[0].split(' ')[1]} </span> 
                                                </div>
                                            }
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
                            width={100}>
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
                            width={80}>
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
                            label="Ship. Date"
                            flexGrow={1}
                            id="shipmentDate"
                            width={100}>
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
                            label="Org"
                            width={50}
                            flexGrow={1}
                            id="awbOrgin">
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
                            label="Dest"
                            width={50}
                            flexGrow={1}
                            id="awbDestination">
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
                            label="Booked"
                            width={180}
                            flexGrow={1}
                            id="bookedPieces">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                        {cellProps.cellData}Pcs | {cellProps.rowData.bookedWeight}Kg | 
                                        {cellProps.rowData.bookedVolume}CBM
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="bookingStation"
                            label="Bkg. Stn"
                            width={80}
                            flexGrow={1}
                            id="bookingStation">
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
                            label="Bkg. Date"
                            width={100}
                            flexGrow={1}
                            hideOnExport
                            id="bookingDate">
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
                            width={110}
                            flexGrow={1}
                            id="remarks">
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
                            label="Bkg Status"
                            width={100}
                            flexGrow={1}
                            id="bookingStatus">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>{this.getBookingStatus(cellProps.cellData)}</Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        
                        <IColumn
                            dataKey="shipmentStatus"
                            label="Shp. Status"
                            width={100}
                            flexGrow={1}
                            id="shipmentStatus">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content><div>{this.getShipmentStatus(cellProps.cellData)}</div></Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        
                        <IColumn
                            dataKey=""
                            label=""
                            width={100}
                            flexGrow={1}
                            id="attachButton">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content><IButton category="default" className="btn-sm" privilegeCheck={false} id={ComponentId.ATTACH_AWB+cellProps.rowIndex} data-index={cellProps.rowIndex} onClick={this.attachAwb}>Attach</IButton>
                                        <IToolTip target={ComponentId.ATTACH_AWB+cellProps.rowIndex} placement="left" value={` Attach awb ${cellProps.rowData.shipmentPrefix}- ${cellProps.rowData.masterDocumentNumber}`} />
                                        </Content>)
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
