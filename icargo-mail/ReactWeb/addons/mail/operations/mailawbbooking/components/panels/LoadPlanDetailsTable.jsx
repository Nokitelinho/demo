import React, { Component,Fragment } from 'react';
import { IColumn, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { Constants } from '../../constants/constants.js'
import IMultiGrid from 'icoreact/lib/ico/framework/component/common/grid/multigrid/IMultiGrid';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import LoadPlanDetailsFilter from './LoadPlanDetailsFilter.jsx';


export default class LoadPlanDetailsTable extends Component {
          
        constructor(props){
            super(props);
            this.state={
                isMailSccOpen: false,
                currentSccTarget:null
            }
            this.selectedLoadPlanAwbs = []
        }

        displayHeader= () =>{
            return <h4 class="fs16 font-weight-bold">{Constants.TABLE_HEADER}</h4>
        }  
    
        toggleMailSccPop = (event) =>{
            const currentTarget=event.currentTarget.id;
            this.setState({ isMailSccOpen: !this.state.isMailSccOpen, currentSccTarget: currentTarget })
        }

        sortList = (sortBy, sortByItem) => {
            this.props.updateSortVariables(sortBy, sortByItem);
        }
    
        onApplyLoadPlanFilter=()=>{
            this.props.onApplyLoadPlanFilter(this.props.displayPage,this.props.pageSize);
        }
    
        onClearLoadPlanFilter=()=>{
            this.props.onClearLoadPlanFilter();
        }

        onRowSelection = (data) => {
            if (data.index > -1) {
                if (data.isRowSelected) {
                    this.selectLoadPlanAwb(data.index);
            }else {
                this.unSelectLoadPlanAwb(data.index);
            }
        }
        else {
            if((data) && (data.checked)) {
                this.selectAllLoadPlanAwbs(-1);
            }
             else {
                if(this.selectedLoadPlanAwbs.length>0){
                    this.unSelectAllLoadPlanAwbs();
                }
            }
        }
    }  

        selectLoadPlanAwb = (awb) => {
            this.selectedLoadPlanAwbs.push(awb);
            this.props.saveSelectedLoadPlanAwbsIndex(this.selectedLoadPlanAwbs);
        }
    
        unSelectLoadPlanAwb = (awb) => {
            let index = -1;
            for (let i = 0; i < this.selectedLoadPlanAwbs.length; i++) {
                var element = this.selectedLoadPlanAwbs[i];
                if (element === awb) {
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                this.selectedLoadPlanAwbs.splice(index, 1);
            }
            this.props.saveSelectedLoadPlanAwbsIndex(this.selectedLoadPlanAwbs);
        }
    
        selectAllLoadPlanAwbs = () => {
            for (let i = 0; i < this.props.loadPlanBookingDetails.results.length; i++) {
                this.selectedLoadPlanAwbs.push(i);
            }
            this.props.saveSelectedLoadPlanAwbsIndex(this.selectedLoadPlanAwbs);
        }
    
        unSelectAllLoadPlanAwbs = () => {
            this.selectedLoadPlanAwbs = []
            this.props.saveSelectedLoadPlanAwbsIndex(this.selectedLoadPlanAwbs);
        }
    
    render() {
            
            const results=(this.props.loadPlanBookingDetails&&this.props.loadPlanBookingDetails.results)?
                                this.props.loadPlanBookingDetails.results:[];

        return (
            <Fragment>
                <IMultiGrid
                    rowCount={results.length}
                    headerHeight={100}
                    className="table-list"
                    rowHeight={200}
                    rowClassName="table-row"
                    tableId="loadPlanDetailsTable"
                    sortEnabled={false}
                    form={true}
                    onRowSelection={this.onRowSelection}
                    customHeader={{
                        headerClass: '',
                        customPanel: this.displayHeader(),
                        dataConfig: {
                            screenId: 'MTK071',
                            tableId: 'loadPlanDetailsTable'
                        },
                        "pagination":{"page":this.props.loadPlanBookingDetails,getPage:this.props.getNewPage},
                        filterConfig: {
                            panel: <LoadPlanDetailsFilter initialValues={this.props.loadPlanFilter}/>,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyLoadPlanFilter,
                            onClearFilter: this.props.onClearLoadPlanFilter
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
                            dataKey="awbNumber"
                            label="AWB Number"
                            width={100}
                            flexGrow={1}
                            id="awbNumber"
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
                            dataKey="standardPieces"
                            label="Std Pcs"
                            width={70}
                            flexGrow={1}
                            id="standardPieces"
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
                            dataKey="standardWeight"
                            label="Std Wgt"
                            width={80}
                            flexGrow={1}
                            id="standardWeight"
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
                            dataKey="volume"
                            label="Vol.(CBM)"
                            width={80}
                            flexGrow={1}
                            id="volume"
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
                            dataKey="origin"
                            label="AWB Org."
                            width={80}
                            flexGrow={1}
                            id="origin"
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
                            dataKey="destination"
                            label="AWB Des."
                            width={80}
                            flexGrow={1}
                            id="destination"
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
                            dataKey="pol"
                            label="POL"
                            width={60}
                            flexGrow={1}
                            id="pol"
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
                            dataKey="pou"
                            label="POU"
                            width={60}
                            flexGrow={1}
                            id="pou"
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
                            dataKey="scc"
                            label="SCC"
                            flexGrow={1}
                            id="scc"
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
                            dataKey="plannedWeight"
                            label="Planned Wgt"
                            width={90}
                            flexGrow={1}
                            id="plannedWeight"
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
                            dataKey="plannedPieces"
                            label="Planned Pcs"
                            width={90}
                            flexGrow={1}
                            id="plannedPieces"
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
                            dataKey ="plannedFlight"
                            label="Planned Flt."
                            flexGrow={1}
                            id="plannedFlight"
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
                                                    {/* {cellProps.rowData.bookingCarrierCode}{' '} */}
                                                    {cellProps.cellData}
                                                </div>
                                            </div>
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>   
                        <IColumn
                            dataKey="flightDate"
                            label="Flight Date & Time"
                            width={140}
                            flexGrow={1}
                            id="flightDate"
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
                                            {cellProps.cellData}{' '}{cellProps.rowData.flightTime}
                                         </span>
                                         </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey="shipmentDescription"
                            label="Shipment Desc"
                            width={120}
                            flexGrow={1}
                            id="shipmentDescription"
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
                    </Columns>
                </IMultiGrid>
            </Fragment>
        );
    
    }
}
