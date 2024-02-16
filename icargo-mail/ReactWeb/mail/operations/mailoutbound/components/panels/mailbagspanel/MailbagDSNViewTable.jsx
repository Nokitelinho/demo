import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import DSNFilter from '../filters/DSNFilter.js'
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
import MailbagCustome from '../custompanels/MailbagTable.js'
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import PropTypes from 'prop-types';

export default class MailbagDSNViewTable extends Component {
      constructor(props) {
        super(props);   

       this.state = {
            rowClkCount: 0,
            show:false
        }
      }
     addMailbagClk=()=> {
        this.setState({show:true});
     }
     toggle=()=> {
       this.setState({show:false});
     }

     togglePanel = () => {
        this.setState({ isOpen: !this.state.isOpen })
    }

    savePopoverId = (index, rowData) => {
        this.setState({ popoverId: index + rowData.dsn });
        this.setState({ rowData: rowData });
        this.togglePanel();
    }

     //on row specific dsn level actions
     dsnActions = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        
        if (actionName === 'DSN_ATTACH_AWB') {
            this.props.dsnAction({ actionName: actionName, index: index });
        }
      
        else if (actionName === 'DSN_DETACH_AWB') {
          this.props.dsnAction({ actionName: actionName, index: index });
        }
        else if (actionName === 'DSNN_ATTACH_ROUTING') {
         this.props.dsnAction({ actionName: actionName, index: index });
        }

        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
        render() {
           // const rowCount=(this.props.mailbaglist)?this.props.mailbaglist.results.length:0;
            const rowCount=(this.props.mailbaglistdsnview&&this.props.mailbaglistdsnview.results)?this.props.mailbaglistdsnview.results.length:0;
        try{
        return (
              <div className="flex-grow-1 d-flex">
            <ITable
                    customHeader={{
                         //  placement:'dynamic',

                            headerClass: '',
                             "pagination":{page:this.props.mailbaglistdsnview,getPage:this.props.listMailbagsInDSNView,"mode":'subminimal'},
                          filterConfig: {
                              panel: <DSNFilter mailbagOneTimeValues={this.props.mailbagOneTimeValues} oneTimeValues={this.props.oneTimeValues} initialValues={this.props.dsnFilterValues}/>,
                               title: 'Filter',
                               onApplyFilter: this.props.onApplyMailbagDSNFilter,
                               onClearFilter: this.props.onClearMailbagDSNFilter
                           },
                          sortBy: {
                                onSort:this.sortList
                            },

                        headerClass: '',
                            "pagination":{"page":this.props.listMailbagsInDSNView,"mode":'subminimal'},
                        filterConfig: {
                            panel: <DSNFilter mailbagOneTimeValues={this.props.mailbagOneTimeValues} 
                                initialValues={this.props.initialDsnValues?this.props.initialDsnValues:{}}/>,
                            title: 'Filter',
                            onApplyFilter: this.props.onApplyDsnFilter,
                            onClearFilter: this.props.onClearDsnFilter
                        },
                        sortBy: {
                            onSort:this.sortList
                        },

                        customPanel:<MailbagCustome activeMailbagTab={this.props.activeMailbagTab} show={this.props.addMailbagShow} onLoadAddMailbagPopup={this.props.onLoadAddMailbagPopup} closeAddMailbagPopup={this.props.closeAddMailbagPopup} onSavemailbagDetails={this.props.onSavemailbagDetails}  changeAddMailbagTab={this.props.changeAddMailbagTab} activeMailbagAddTab= {this.props.activeMailbagAddTab}  addRow={this.props.addRow} onDeleteRow={this.props.onDeleteRow} resetRow={this.props.resetRow}  newMailbags={this.props.newMailbags} updatedMailbags={this.props.updatedMailbags} importedMailbagDetails={this.props.importedMailbagDetails}  selectedMailbags={this.props.selectedMailbags} onMailBagActionClick={this.props.onMailBagActionClick} toggleFlightPanel={this.props.toggleFlightPanel} populateMailbagId={this.props.populateMailbagId} mailbagOneTimeValues={this.props.mailbagOneTimeValues} defaultWeightUnit={this.props.defaultWeightUnit} previousRowWeight={this.props.previousRowWeight} currentDate={this.props.currentDate} currentTime={this.props.currentTime}/>
                       
                        }}

                        rowCount={rowCount}
                        headerHeight={35}
                        className="table-list"
                        gridClassName=""
                        headerClassName=""
                        rowHeight={40}
                        tableId='mailbagsdsntable'
                        rowClassName="table-row"
                        data={this.props.mailbaglistdsnview&&this.props.mailbaglistdsnview.results?
                            this.props.mailbaglistdsnview.results:[]}
                    >
                        <Columns>
                            <IColumn
                                width={40}
                                dataKey=""
                                flexGrow={0}
                                className="" >
                                <Cell>
                                    <HeadCell disableSort selectOption>
                                    </HeadCell>
                                    <RowCell selectOption>
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn key={2} width={16} className="p-0">
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell >
                                        {(cellProps) => (
                                            <Content>
                                                <a id={'mailbagdsnextra' + cellProps.rowIndex + cellProps.rowData.dsn} onClick={() => { this.savePopoverId(cellProps.rowIndex, cellProps.rowData) }}>
                                                    {(this.state.isOpen && (this.state.popoverId == cellProps.rowIndex + cellProps.rowData.dsn)) ?
                                                        <i className="icon ico-minus-round align-middle"></i> :
                                                        <i className="icon ico-plus-round align-middle"></i>}
                                                </a>
                                                {this.state.isOpen && this.state.popoverId != '' && 'mailbagdsnextra' + cellProps.rowIndex + cellProps.rowData.dsn === 'mailbagdsnextra' + this.state.popoverId &&
                                                    <div>
                                                        <IPopover isOpen={this.state.isOpen} target={'mailbagdsnextra' + this.state.popoverId} toggle={this.togglePanel} className="icpopover table-filter"> 
                                                    <IPopoverBody>
                                                                <Row>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Cat</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailCategoryCode}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Subclass</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.mailSubclass}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Year</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.year}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Consignment Number</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.consignmentNumber}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">AWB No</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.documentOwnerCode}-{cellProps.rowData.masterDocumentNumber}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Pa Code</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.paCode}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">Routing Available</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.routingAvl}</div>
                                                                    </Col>
                                                                    <Col xs="auto">
                                                                        <div className="text-light-grey">PLT</div>
                                                                        <div className="mar-b-xs">{cellProps.rowData.pltEnableFlag}</div>
                                                                    </Col>
                                                                </Row>
                                                            </IPopoverBody>
                                                        </IPopover>
                                                    </div>}
                                                {/* <PopoverDetails width={500}>
                                                    <div className="card pad-md">
                                                        <Row>
                                                            <Col xs="8">
                                                                <div className="text-light-grey">Vol</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.vol}</div>
                                                            </Col>
                                                            <Col xs="8">
                                                                <div className="text-light-grey">Carrier</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.carrier}</div>
                                                            </Col>
                                                            <Col xs="8">
                                                                <div className="text-light-grey">Seal No</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.sealno}</div>
                                                            </Col>
                                                            <Col xs="8">
                                                                <div className="text-light-grey">Damage Deatails</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.damagedetails}</div>
                                                            </Col>
                                                            <Col xs="8">
                                                                <div className="text-light-grey">Belly Cart ID</div>
                                                                <div className="mar-b-xs">{cellProps.rowData.bellycartid}</div>
                                                            </Col>
                                                        </Row>
                                                    </div>
                                                </PopoverDetails> */}
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>

                            <IColumn
                                dataKey=""
                                label=""
                                width={100}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content>DSN Details</Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{
                                                <div className="pad-b-3xs">
                                                    <DataDisplay index={cellProps.rowIndex}>{cellProps.rowData.dsn}</DataDisplay>
                                                </div>
                                            }
                                                {
                                                    <div>
                                                        <DataDisplay index={cellProps.rowIndex}>
                                                            {cellProps.rowData.bags} <span className="text-light-grey">Bag</span>  {cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue : ''} 
                                                            {/* <span className="text-light-grey">Kg</span> */}
                                                        </DataDisplay>
                                                    </div>
                                                }
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn
                                dataKey=""
                                label=""
                                width={100}
                                flexGrow={1}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>{

                                                <div>
                                                    <DataDisplay index={cellProps.rowIndex}>
                                                        {cellProps.rowData.originExchangeOffice}
                                                    </DataDisplay>-
                                                 <DataDisplay index={cellProps.rowIndex}>
                                                        {cellProps.rowData.destinationExchangeOffice}
                                                    </DataDisplay>
                                                </div>
                                            }

                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn>
                            <IColumn width={30} className="align-self-center" flexGrow={0}>
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
                                                    <IDropdown>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                            <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                        </IDropdownToggle>
                                                        <IDropdownMenu right={true} >
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DSN_ATTACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DSN_ATTACH_AWB" onClick={this.dsnActions} disabled={this.props.flightActionsEnabled === 'false'}>Attach AWB</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DSN_DETACH_AWB" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DSN_DETACH_AWB" onClick={this.dsnActions} disabled={cellProps.rowData.masterDocumentNumber!=null?this.props.flightActionsEnabled === 'false':this.props.flightActionsEnabled === 'true'}>Detach AWB</IDropdownItem>
                                                            <IDropdownItem data-index={cellProps.rowIndex} data-mode="DSN_ATTACH_ROUTING" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_DSN_ATTACH_ROUTING" onClick={this.dsnActions} disabled={this.props.flightActionsEnabled === 'false'}> Attch Routing</IDropdownItem>
                                                           
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
MailbagDSNViewTable.propTypes = {
    dsnAction:PropTypes.func,
    mailbaglistdsnview:PropTypes.object,
    listMailbagsInDSNView:PropTypes.object,
    mailbagOneTimeValues:PropTypes.object,
    onApplyMailbagFilter:PropTypes.func,
    onClearMailbagFilter:PropTypes.func,
    activeMailbagTab:PropTypes.string,
    addMailbagShow:PropTypes.func,
    onLoadAddMailbagPopup:PropTypes.func,
    closeAddMailbagPopup:PropTypes.func,
    onSavemailbagDetails:PropTypes.func,
    changeAddMailbagTab:PropTypes.func,
    activeMailbagAddTab:PropTypes.func,
    addRow:PropTypes.func,
    onDeleteRow:PropTypes.func,
    resetRow:PropTypes.func,
    newMailbags:PropTypes.object,
    updatedMailbags:PropTypes.func,
    importedMailbagDetails:PropTypes.func,
    selectedMailbags:PropTypes.object,
    onMailBagActionClick:PropTypes.func,
    toggleFlightPanel:PropTypes.func,
    populateMailbagId:PropTypes.func,
    onApplyDsnFilter:PropTypes.func,
    onClearDsnFilter:PropTypes.func,
    initialDsnValues:PropTypes.object,
}