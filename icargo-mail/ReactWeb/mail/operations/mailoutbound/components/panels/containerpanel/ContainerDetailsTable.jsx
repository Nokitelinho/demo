import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import ContainerPopoverDetails from './ContainerPopoverDetails.jsx'
import PopoverDetails from '../Popoverdetails.jsx'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import ContainerFilter from '../filters/ContainerFilter.js';
import ContainerTableCustom from '../custompanels/ContainerTableCustom.js'
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import PropTypes from 'prop-types';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
export default class ContainerDetailsTable extends Component {

    constructor(props) {
        super(props);
        this.selectedContainers = []
        this.state = {
            isOpen: false,
            rowClkCount: 0,
            popoverId: '',
            isOffloadPopoverOpen: false,
            offloadPopoverId: ''
        }
    }
   
   
  //on row selection and checkbox click
    onRowSelection = (data) => {
    if(data.selectedIndexes) {
        this.props.saveSelectedContainersIndex(data.selectedIndexes);
        if (data.index > -1) {
                this.props.selectContainers({ containerIndex: data.index });
                
        }
    }

            }


    

   

    getRowClassName=(row)=> {
        const data = row.rowData;
        const selectedContainer = this.props.selectedContainer ? this.props.selectedContainer : '';
      
        if (JSON.stringify(selectedContainer.containerNumber) === JSON.stringify(data.containerNumber) && this.props.displayMode === 'multi') {
            return 'table-row table-row__bg-red'
        }
        else {
            return 'table-row'
        }

    }
    
    
    //on row specific container action
    containerAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        this.props.containerAction({ actionName: actionName, index: index });
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    offload = (event) => {
        //let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        this.props.navigateToOffload(this.props.containerlist[index]);
    }

    sortList = (sortBy, sortByItem) => {
        this.props.onApplyContainerSort(sortBy, sortByItem);
    }
    
    assignToContainer = (rowIndex) => {
        this.props.assignToContainer(rowIndex,this.props.activeMainTab, this.props.carditMailbags,this.props.lyinglistMailbags,this.props.selectedCarditIndex,this.props.selectedLyinglistIndex,this.props.currentDate)
    }
    
    togglePanel = () => {
        this.setState({ isOpen: !this.state.isOpen })
    }

    savePopoverId = (index, rowData) => {
        this.setState({ popoverId: index + rowData.containerNumber });
        this.setState({ rowData: rowData });
        this.togglePanel();
    }
    saveOffloadPopoverId = (index) => {
        this.setState({ offloadPopoverId: index});
        this.toggleOffloadPopover();
    }

    toggleOffloadPopover = () => {
        this.setState({ isOffloadPopoverOpen: !this.state.isOffloadPopoverOpen })
    }
    render() {
        let length=0;
         length = this.props.allMailBags ? this.props.allMailBags.length : 0
        let masterDocumentNumberFlag ="N";
        let allMailBags =this.props.allMailBags;
        if(length!=0){
            for(let i=0;i<allMailBags.length;i++){
                 if(allMailBags[i].masterDocumentNumber!=null){
                    masterDocumentNumberFlag ="Y";  
                    break;
                       }
              }
            }
        const results = this.props.containerlist ? this.props.containerlist:'';
       // const containers=this.props.containers;      
        const rowCount=results.length;
        try {
            return (
                  <div className="flex-grow-1 d-flex">
                    <ITable
                        customHeader={{
                            headerClass: '',
                           "pagination": { "page": this.props.containers, 
                           getPage: this.props.flightCarrierflag==='F'?this.props.getContainerNewPage:this.props.getContainerNewPageCarrier, "mode": 'subminimal' },
                            sortBy:{
                                onSort:this.sortList,
                                customSortByItems:[
                                    {
                                                    id:'pou',
                                                    label:'POU'
                                                    },
                                                    {
                                                        id:'destination',
                                                        label:'Destination'
                                                    },
                                                    {
                                                        id:'totalWeight',
                                                        label:'Weight'
                                                    },
                                                    {
                                                        id:'totalBags',
                                                        label:'No. Of Mailbags'
                                                    }
                                    ]
                            },
                            filterConfig: {
                                panel: <ContainerFilter initialValues={this.props.tableFilter} flightCarrierflag={this.props.flightCarrierflag}/>,
                                title: 'Filter',
                                 onApplyFilter: this.props.onApplyContainerFilter,
                                 onClearFilter: this.props.onClearContainerFilter
                            },
                            customPanel:<ContainerTableCustom selectedContainerIndex={this.props.selectedContainerIndex} show={this.props.addContainerShow} addContainerClk={this.props.addContainerClk} toggle={this.props.toggleAddContainerPopup}
                            onListToModifyContainer ={this.props.onListToModifyContainer} onSaveContainer={this.props.onSaveContainer} rowClkCount={this.state.rowClkCount} mailAcceptance={this.props.mailAcceptance} containerMultipleSelectionAction={this.props.containerMultipleSelectionAction}
                             onUnMountAddContPopup={this.props.onUnMountAddContPopup} containerAction={this.props.containerAction} onclickPABuilt={this.props.onclickPABuilt} PABuiltChecked={this.props.PABuiltChecked}{...this.props} wareHouses={this.props.wareHouses} initialValues={this.props.initialValues} onLoadAddContPopup={this.props.onLoadAddContPopup} containerActionMode={this.props.containerActionMode} 
                             enableContainerSave={this.props.enableContainerSave} flightActionsEnabled={this.props.flightActionsEnabled} populateDestForBarrow={this.props.populateDestForBarrow} isBarrow={this.props.isBarrow}/>
                           
                        }}                   
                        rowCount={rowCount}
                        tableId="containerDetailsTable"
                        headerHeight={35}
                        className="table-list"
                        gridClassName=""
                        headerClassName=""
                        rowHeight={40}
                        rowClassName={this.getRowClassName}
                        additionalData={this.props.mailAcceptance}
                        //customHeader={{"pagination":{"page":this.props.mailbagDetails,getPage:this.props.getNewPage}}}-->
                        data={results}
                        onRowSelection={this.onRowSelection}
                    >
                        <Columns >
                            <IColumn
                                //width={40}
                                dataKey=""
                                flexGrow={0}
                                className="align-self-center" >
                                <Cell>
                                    <HeadCell disableSort selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn key={2} flexGrow={0}
                             //width={16}
                             className="align-self-center pr-0 width24">
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (<Content><div className="width16"></div></Content>)}
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>

                                                <a id={'containerextra' + cellProps.rowIndex + cellProps.rowData.containerNumber} onClick={() => { this.savePopoverId(cellProps.rowIndex, cellProps.rowData) }}>
                                                    {(this.state.isOpen && (this.state.popoverId == cellProps.rowIndex + cellProps.rowData.containerNumber)) ?
                                                        <i class="icon ico-minus-round align-middle"></i> :
                                                        <i class="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isOpen && this.state.popoverId != '' && 'containerextra' + cellProps.rowIndex + cellProps.rowData.containerNumber==='containerextra' + this.state.popoverId&&
                                                    <div>
                                                        <IPopover isOpen={this.state.isOpen} target={'containerextra' + this.state.popoverId} toggle={this.togglePanel} className="icpopover table-filter"> 
                                                    <IPopoverBody>
                                                                <Row>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Container</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.containerNumber ? cellProps.rowData.containerNumber : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">POU</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.pou ? cellProps.rowData.pou : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Dest</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.destination ? cellProps.rowData.destination : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">PA</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.paCode ? cellProps.rowData.paCode : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Wt</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.totalWeight ? cellProps.rowData.totalWeight.roundedDisplayValue : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Assigned on date</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.assignedOn ? cellProps.rowData.assignedOn : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Onward Flight</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.onwardFlights ? cellProps.rowData.onwardFlights : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Barrow/ULD info</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.type === 'U' ? 'ULD' : 'Barrow'}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">PA Build Info</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.paBuiltFlag==='Y' ? 'Yes' : 'No'}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Transfer Carrier</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.transferFromCarrier? cellProps.rowData.transferFromCarrier : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Warehouse</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.warehouse ? cellProps.rowData.warehouse : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Loc</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.location ? cellProps.rowData.location : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Remarks</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.remarks ? cellProps.rowData.remarks : ''}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Container Journey Id</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.containerJnyId ? cellProps.rowData.containerJnyId : ''}</div>
                                                                    </Col>
                                                                </Row>
                                                            </IPopoverBody>
                                                        </IPopover>
                                                    </div>}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey=""
                                label="Container Number"
                                id="containerNumber"
                                width={160}
                                flexGrow={1}
                                selectColumn={true}
                                sortByItem={true}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>Containers</Content>)
                                        }
                                    </HeadCell>
                                     <RowCell>
                    {(cellProps) => (<Content>
                      <div className="row">
                        <div className="col">
                          <div className="pad-b-2xs"><span>{cellProps.rowData.containerNumber}{cellProps.rowData.paBuiltFlag === 'Y' && '(SB)'}</span>
                             {
                                    cellProps.rowData.offloadedInfo && cellProps.rowData.offloadedInfo.length > 0 ? 
                                   
                                        <a href="#"><i id={"offloadInfo" + cellProps.rowIndex} className="icon ico-ex-blue" onClick={() => this.saveOffloadPopoverId(cellProps.rowIndex)}></i>
                                        {
                                        this.state.isOffloadPopoverOpen && 'offloadInfo' + cellProps.rowIndex === 'offloadInfo' + this.state.offloadPopoverId &&
                                            <div>
                                                <IPopover placement="bottom" isOpen={this.state.isOffloadPopoverOpen} target={'offloadInfo' + this.state.offloadPopoverId} toggle={this.toggleOffloadPopover} className="icpopover">
                                                    <IPopoverHeader>Offloaded Information</IPopoverHeader>
                                                    <IPopoverBody>
                                                        {
                                                            <div className="pad-sm">
                                                                <div><span className="text-light-grey">No.of times offloaded :</span> {cellProps.rowData.offloadCount}</div>
                                                                <div><span className="text-light-grey">Offloaded flight information</span> : {cellProps.rowData.offloadedInfo.map((value) => <span>{value},</span>)}</div>
                                                            </div>
                                                        }
                                                    </IPopoverBody>
                                                </IPopover>
                                            </div>
                                        }
                                        </a> 
                                    
                                        :"" 
							 }
                          </div>
                          <div className="text-light-grey mar-b-2xs">
                            {cellProps.rowData.pol}
                            <i class="icon ico-air-sm mar-x-2xs"></i>
                            {cellProps.additionalData.carrierpk === null ? cellProps.rowData.pou : cellProps.rowData.finalDestination}
                          </div>
                        </div>
                       
                           
                       
                        <div className="col">
                          <div className="pad-b-3xs"><span>{cellProps.rowData.totalBags}</span> <span className="text-light-grey">Bags</span><span className="pad-x-xs">|</span>
                          {cellProps.rowData.totalWeight != null && cellProps.rowData.totalWeight != undefined ? cellProps.rowData.totalWeight.roundedDisplayValue : ''} 
                          </div>
						  {cellProps.additionalData.estimatedChargePrivilage?
                          <div>
                          <span className="text-light-grey">Est chg: </span>
                          <span>{cellProps.rowData.rateAvailforallMailbags!=null&&cellProps.rowData.rateAvailforallMailbags!=undefined&&cellProps.rowData.rateAvailforallMailbags=='N'?<span className="text-red">{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined ? cellProps.rowData.provosionalCharge :''}</span>:<span>{cellProps.rowData.provosionalCharge!='0' && cellProps.rowData.provosionalCharge!=undefined ? cellProps.rowData.provosionalCharge :''}</span>}</span>
                        <span className="text-light-grey"> {cellProps.rowData.baseCurrency}</span>
                          </div>
						  :''}
                          {/* <span className="text-light-grey">KG</span> */}
                          {/* <div>{cellProps.rowData.totalWeight != null && cellProps.rowData.totalWeight != undefined ? cellProps.rowData.totalWeight.roundedDisplayValue : ''} </div> */}
                        </div>
							<div className="col">
                         {(cellProps.rowData.uldFulIndFlag === 'Y') ? <span className={"badge badge-pill light badge-active"}>{'ULD FULL'}</span> : ''}
							</div>
                        
                        <div className="col-auto">
                          {(this.props.carditmailbagcount > 0 || this.props.lyingmailbagcount > 0 || (this.props.selectedDeviationList.length > 0 && this.props.flightCarrierflag==='F')) &&
                            <IButton onClick={() => this.assignToContainer(cellProps.rowIndex)} disabled={this.props.flightActionsEnabled === 'false'} className="container-assign-btn">Assign</IButton>
                          }
                        </div>
                      </div>
                      <div className="pad-t-3xs">{cellProps.rowData.minReqDelveryTime ? 'Earliest RDT :' : ''} {cellProps.rowData.minReqDelveryTime ? cellProps.rowData.minReqDelveryTime : ''}</div>
                      
                    </Content>)}
                  </RowCell>
                                </Cell>
                            </IColumn>
                         {/*   <IColumn
                                dataKey=""
                                label=" "
                                width={50}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>Mailbags</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            
                                            <Content>
                                                <div className="row">
                                                <Col xs="auto">
                                                <div className="pad-b-3xs"><span>{cellProps.rowData.totalBags}</span> <span className="text-light-grey">Bags</span></div>
                                                
                                                {/* <span className="text-light-grey">KG</span> 
                                                
                                                <div>{cellProps.rowData.totalWeight != null && cellProps.rowData.totalWeight != undefined ? cellProps.rowData.totalWeight.roundedDisplayValue : ''} </div>
                                                 </Col>
                                                 <Col className="p-0">
                                                {(this.props.carditmailbagcount > 0 || this.props.lyingmailbagcount > 0 || this.props.selectedDeviationList.length > 0) && 
                                                <IButton onClick={() => this.assignToContainer(cellProps.rowIndex)} disabled={this.props.flightActionsEnabled === 'false'} className="container-assign-btn">Assign</IButton>
                                                }
                                                </Col>
                                                
                                                </div>
                                            </Content>
                                        )
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>*/}
                            
                            {/* {this.props.carditmailbagcount > 0 ? (
                                <IColumn
                                    dataKey=""
                                    label=" "
                                    width={50}
                                    flexGrow={1}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {() => (
                                                <Content></Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>
                                                     <IButton onClick={() => this.assignToContainer(cellProps.rowIndex)} className="container-assign-btn">Assign</IButton>
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>) : null} */}
                            <IColumn
                              width={30}
                             flexGrow={0} className="align-self-center" selectColumn={false}>
                                < Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                                {(!cellProps.rowData.uld) ?
                                                    // <span onClick={this.gridUtitity}>
                                                    <IDropdown portal={true}   right={true}>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                            <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                        </IDropdownToggle>
                                                        <IDropdownMenu portal={true} right={true} flip={cellProps.scrollbarData && cellProps.scrollbarData.vertical?false:true}>
                                                        <IDropdownItem  data-mode="DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DETACH_AWB" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={masterDocumentNumberFlag=="Y"?this.props.flightActionsEnabled === 'false':this.props.flightActionsEnabled === 'true'}>Detach AWB</IDropdownItem>
                                                            <IDropdownItem data-mode="REASSIGN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_CARRIER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} >Reassign to Carrier</IDropdownItem>
                                                            <IDropdownItem data-mode="REASSIGN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_FLIGHT" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Reassign to Flight</IDropdownItem>
                                                            {this.props.flightCarrierflag === 'F' && <IDropdownItem data-mode="ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_ATTACH_ROUTING" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} >Attach Routing</IDropdownItem>}
                                                            {this.props.flightCarrierflag === 'F' && <IDropdownItem data-mode="ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_ATTACH_AWB" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Attach AWB</IDropdownItem>}
                                                            <IDropdownItem data-mode="MODIFY" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_MODIFY_CONTAINER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}> Modify</IDropdownItem>
                                                            <IDropdownItem data-mode="UNASSIGN" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_UNASSIGN_CONTAINER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Unassign</IDropdownItem>
                                                            <IDropdownItem data-mode="OFFLOAD" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_OFFLOAD_CONTAINER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Offload</IDropdownItem>
                                                            <IDropdownItem data-mode="DELETE_CONTAINER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DELETE_CONTAINER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Delete</IDropdownItem>
                                                            <IDropdownItem data-mode="TRANSFER" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_CARRIER" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} >Transfer to Carrier</IDropdownItem>
                                                            <IDropdownItem data-mode="TRANSFER_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REASSIGN_TO_FLIGHT" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}>Transfer to Flight</IDropdownItem>
															<IDropdownItem data-mode="ULD_MARK_FULL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_ULD_MARK_FULL" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} disabled={cellProps.rowData.uldFulIndFlag==='Y'}>Mark as ULD Full</IDropdownItem>
                                                            <IDropdownItem data-mode="ULD_UNMARK_FULL" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_ULD_UNMARK_FULL" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'}  disabled={cellProps.rowData.uldFulIndFlag==='N'}>Mark ULD as not Full</IDropdownItem>
                                                            {isSubGroupEnabled('QF_SPECIFIC')&& this.props.flightCarrierflag === 'F'&&
                                                            <IDropdownItem data-mode="PRINT_ULD_TAG" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_PRINT_ULD_TAG" onClick={this.containerAction} data-index={cellProps.rowIndex} disabled={this.props.flightActionsEnabled === 'false'} >Print ULD Tag</IDropdownItem>}
                                                        </IDropdownMenu>
                                                    </IDropdown>
                                                    //  </span>
                                                    : ''}

                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                        </Columns>

                    </ITable>
                   
                </div>
            );
        }
        catch (e) {
        }
    }
}
ContainerDetailsTable.propTypes = {
    saveSelectedContainersIndex: PropTypes.func,
    selectContainers: PropTypes.func,
    containerAction: PropTypes.func,
    navigateToOffload: PropTypes.func,
    addContainerShow: PropTypes.bool,
    toggleAddContainerPopup:PropTypes.func,
    addContainerClk:PropTypes.func,
    onApplyContainerSort: PropTypes.func,
    assignToContainer: PropTypes.func,
    activeMainTab: PropTypes.string,
    carditMailbags: PropTypes.object,
    flightCarrierflag: PropTypes.bool,
    lyinglistMailbags:PropTypes.object,
    selectedCarditIndex: PropTypes.func,
    getContainerNewPage:PropTypes.func,
    currentDate: PropTypes.currentDate,
    containerlist: PropTypes.array,
    onApplyContainerFilter: PropTypes.func,
    onClearContainerFilter:PropTypes.func,
    getContainerNewPageCarrier: PropTypes.func,
    selectedLyinglistIndex: PropTypes.func,
    selectedContainerIndex: PropTypes.func,
    onListToModifyContainer: PropTypes.func,
    onSaveContainer: PropTypes.func,
    mailAcceptance: PropTypes.object,
    containerMultipleSelectionAction: PropTypes.func,
    onUnMountAddContPopup: PropTypes.func,
    onclickPABuilt: PropTypes.func,
    PABuiltChecked: PropTypes.bool,
    wareHouses: PropTypes.func,
    initialValues: PropTypes.object,
    onLoadAddContPopup: PropTypes.func,
    containerActionMode: PropTypes.string,
    enableContainerSave: PropTypes.bool,
    flightActionsEnabled: PropTypes.bool,
    populateDestForBarrow: PropTypes.func,
    selectedContainer: PropTypes.object,
    displayMode: PropTypes.string,
    containers: PropTypes.object,
    lyingmailbagcount: PropTypes.number,
    carditmailbagcount: PropTypes.number,
  
}
//const flightDetailsTable = wrapForm('FlightDetailsForm')(FlightDetailsTable);
//export default flightDetailsTable;
// // {cellProps.rowData.totalWeight.roundedDisplayValue?cellProps.rowData.totalWeight.roundedDisplayValue:null}