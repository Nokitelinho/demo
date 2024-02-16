import React, { Fragment } from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Label } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';

class LoadPlanFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            status: [],
            showPopover:false,
        }
        this.onlistLoadPlanDetails = this.onlistLoadPlanDetails.bind(this);
        this.dateRangeIdr = 'MailAwbBookingDateRange'
    }

    componentDidMount(){
        //this.initializeOneTime();
    }

    toggleFilter = () => {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    togglePopover =()=>{
        this.setState({ showPopover:!this.state.showPopover });
    }

    onlistLoadPlanDetails = () => {
        this.props.onlistLoadPlanDetails();
    }

    onclearLoadPlanDetails = () => {
        this.props.onclearLoadPlanDetails();
    }

    render() {
        return (

            <Fragment>
                   {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? 
                        <div className="header-filter-panel flippane">
                            <div className="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label" >AWB Number</label>
                                            <IAwbNumber mode="edit" shipmentPrefix={this.props.initialValues.shipmentPrefix} masterDocumentNumber={this.props.initialValues.masterDocumentNumber} reducerName="awbReducer" hideLabel={true} form="true" />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Planned Flight Date From</label>
                                            <DatePicker name="plannedFlightDateFrom" id ="plannedFlightDateFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="plannedFlightDateTo" />
                                            <IToolTip value={'Planned Flight From'} target={'plannedFlightDateFrom'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                    <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Planned Flight Date To</label>
                                            <DatePicker name="plannedFlightDateTo" id ="plannedFlightDateTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="plannedFlightDateFrom" />
                                            <IToolTip value={'Planned Flight To'} target={'plannedFlightDateTo'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <IFlightNumber flightnumber={this.props.initialValues} mode="edit" showDate={false}></IFlightNumber>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Flight Date</label>
                                            <DatePicker name="flightDate" id="flightDate" dateFieldId={this.dateRangeIdr} />
                                            <IToolTip value={'Flight Date'} target={'flightDate'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">AWB Orgin</label>
                                            <Lov name="origin" id="origin" lovTitle="AWB Origin" isReactContext={true}  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                            <IToolTip value={' AWB Orgin'} target={'origin'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">AWB Destination</label>
                                            <Lov name="destination" id="destination" lovTitle=" AWB Destination" isReactContext={true}  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                            <IToolTip value={'AWB Destination'} target={'destination'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="2">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">POL</label> 
                                            <Lov name="pol" lovTitle="POL" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1"  uppercase={true}/>
                                            <IToolTip value={'POL'} target={'pol'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="2">
                                        <div className="form-group">
                                            <label className="form-control-label">POU</label> 
                                            <Lov name="pou" lovTitle="POU" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1"  uppercase={true}/>
                                            <IToolTip value={'POU'} target={'pou'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="2">
                                        <div className="form-group">
                                            <label className="form-control-label ">SCC</label>
                                            <Lov name="mailScc" id="mailScc" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
											closeButtonIds={['btnOk', 'btnClose']} uppercase={true}
											/>
                                            <IToolTip value={'SCC'} target={'mailScc'} placement='bottom'/>
                                        </div>
                                    </Col>
                                </Row>

                            </div>
                            <div className="btn-row">
                                <IButton id="list" category="primary" privilegeCheck={false}  onClick={this.onlistLoadPlanDetails}>List</IButton>
                                <IToolTip value={'List'} target={'list'} placement='bottom'/>
                                <IButton id="Clear" category="default" privilegeCheck={false}  onClick={this.onclearLoadPlanDetails}>Clear</IButton>
                                <IToolTip value={'Clear'} target={'Clear'} placement='bottom'/>
                            </div>
                            {(this.props.screenMode == 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                        </div>

                   :(
                    <div className="header-summary-panel flippane animated fadeInDown">
                    <div className="pad-md">
                    {this.props.loadPlanFilter?
                        <Row>
                            {this.props.loadPlanFilter.shipmentPrefix && this.props.loadPlanFilter.shipmentPrefix.length > 0 &&
                                this.props.loadPlanFilter.masterDocumentNumber && this.props.loadPlanFilter.masterDocumentNumber.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">AWB</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.shipmentPrefix} - {this.props.loadPlanFilter.masterDocumentNumber}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.plannedFlightDateFrom && this.props.loadPlanFilter.plannedFlightDateFrom.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label ">Planned Flight Date From</Label>
                                    <div className="form-control-data">  {this.props.loadPlanFilter.plannedFlightDateFrom}</div>
                                </Col> : ""

                            }
                            {this.props.loadPlanFilter.plannedFlightDateTo && this.props.loadPlanFilter.plannedFlightDateTo.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label ">Planned Flight Date To</Label>
                                    <div className="form-control-data">  {this.props.loadPlanFilter.plannedFlightDateTo}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.flightNumber ?
                                <Col xs="3">
                                    <Label className="form-control-label">Flight Number</Label>
                                    {/* <IFlightNumber {...this.props.loadPlanFilter.flightNumber} showDate={false} mode="display"></IFlightNumber> */}
                                    <div className="form-control-data">{this.props.loadPlanFilter.flightNumber}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.flightDate && this.props.loadPlanFilter.flightDate.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Flight Date</Label>
                                    <div className="form-control-data"> {this.props.loadPlanFilter.flightDate}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.origin && this.props.loadPlanFilter.origin.length > 0 ?
                                <Col xs="2">
                                    <Label className="form-control-label">AWB Origin</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.origin}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.destination && this.props.loadPlanFilter.destination.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">AWB Destination</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.destination}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.pol && this.props.loadPlanFilter.pol.length > 0 ?
                                <Col xs="2">
                                    <Label className="form-control-label">POL</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.pol}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.pou && this.props.loadPlanFilter.pou.length > 0 ?
                                <Col xs="2">
                                    <Label className="form-control-label">POU</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.pou}</div>
                                </Col> : ""
                            }
                            {this.props.loadPlanFilter.mailScc && this.props.loadPlanFilter.mailScc.length > 0 ?
                                <Col xs="2">
                                    <Label className="form-control-label">SCC</Label>
                                    <div className="form-control-data">{this.props.loadPlanFilter.mailScc}</div>
                                </Col> : ""
                            }
                        </Row>:null}
                    
                    </div>
                    <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>

                         <div className="header-extra-data" style={{display:this.props.loadPlanPopoverCount > 0 ? null:'none'}}>
                            <div className="badge" id="filterPopover" onMouseEnter={this.togglePopover}>+{this.props.loadPlanPopoverCount}</div>
                            <IPopover placement="auto-start" isOpen={this.state.showPopover} target={'filterPopover'} toggle={this.togglePopover} className="icpopover"> >
                                <IPopoverBody>
                                    <div className="header-extra-data-panel">
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.shipmentPrefix && this.props.loadPlanPopOver.shipmentPrefix.length > 0 &&
                                            this.props.loadPlanFilter.masterDocumentNumber && this.props.loadPlanFilter.masterDocumentNumber.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">AWB Number</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.shipmentPrefix} - {this.props.loadPlanPopOver.masterDocumentNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.plannedFlightDateFrom && this.props.loadPlanPopOver.plannedFlightDateFrom.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Planned Flight Date From :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.plannedFlightDateFrom}</div>
                                            </div> : null
                                        }

                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.plannedFlightDateTo && this.props.loadPlanPopOver.plannedFlightDateTo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Planned Flight Date To :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.plannedFlightDateTo}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.flightnumber ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Flight Number:</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.flightnumber.carrierCode+' '+this.props.popOver.flightnumber.flightNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.flightDate && this.props.loadPlanPopOver.flightDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Flight Date :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.flightDate}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.origin && this.props.loadPlanPopOver.origin.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">AWB Origin :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.origin}</div>
                                            </div> : null
                                        }

                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.destination && this.props.loadPlanPopOver.destination.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">AWB Destination :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.destination}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.pol && this.props.loadPlanPopOver.pol.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">POL :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.pol}</div>
                                            </div> : null
                                        }
                                        {this.props.loadPlanPopOver && this.props.loadPlanPopOver.pou && this.props.loadPlanPopOver.pou.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">POU :</Label>
                                                <div className="form-control-data">{this.props.loadPlanPopOver.pou}</div>
                                            </div> : null
                                        }
                                        {
                                            this.props.loadPlanPopOver && this.props.loadPlanPopOver.mailScc && this.props.loadPlanPopOver.mailScc.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">SCC :</Label>
                                                    <div className="form-control-data">{this.props.loadPlanPopOver.mailScc}</div>
                                                </div> : null
                                        }
                                    </div>
                                </IPopoverBody>
                            </IPopover>
                            
                        </div>
                   </div>)}
            </Fragment>
        )
    }
}


export default wrapForm('loadPlanViewForm')(LoadPlanFilterPanel);