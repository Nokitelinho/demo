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
import { Forms } from '../../constants/constants.js';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            status: [],
            showPopover:false,
        }
        this.dateRangeIdr = 'MailAwbBookingDateRange'
    }

    componentDidMount(){
        this.initializeOneTime();
    }

    initializeOneTime = () => {

        let status = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if (!isEmpty(this.props.oneTimeValues['operations.shipment.shipmentstatus']))
            status = this.props.oneTimeValues['operations.shipment.shipmentstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.setState({status:status})
        }
    }

    onlistAwbDetails = () => {
        this.props.onlistAwbDetails();
    }
    onclearAwbDetails = () => {
        this.props.onclearAwbDetails();
    }
    toggleFilter = () => {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    togglePopover =()=>{
        this.setState({ showPopover:!this.state.showPopover });
    }

    populateDescription=()=>{
        if (this.props.screenMode === 'display') {
            if(this.props.filter){
                if (this.props.filter.bookingStatus && this.props.filter.bookingStatus.length > 0) {
                    let selectedStatus = this.state.status.find((element) => {return  element.value === this.props.filter.bookingStatus});
                    this.selectedStatusLabel = selectedStatus.label;
                }
            }
            if(this.props.popOver){
                if (this.props.popOver.bookingStatus && this.props.popOver.bookingStatus.length > 0) {
                    let selectedStatus = this.state.status.find((element) => {return  element.value === this.props.popOver.bookingStatus});
                    this.selectedStatusLabel = selectedStatus.label;
                }
            }
        }
    }

    render() {

        this.populateDescription();
        return (

            <Fragment>
                   {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? 
                        <div className="header-filter-panel flippane">
                            <div className="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label" >AWB</label>
                                            <IAwbNumber mode="edit" shipmentPrefix={this.props.initialValues.shipmentPrefix} masterDocumentNumber={this.props.initialValues.masterDocumentNumber} reducerName="awbReducer" hideLabel={true} form="true" />
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Booking From</label>
                                            <DatePicker name="bookingFrom" id ="bookingFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="bookingTo" />
                                            <IToolTip value={'Booking From'} target={'bookingFrom'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Booking To</label>
                                            <DatePicker name="bookingTo" id ="bookingTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="bookingFrom" />
                                            <IToolTip value={'Booking To'} target={'bookingTo'} placement='bottom'/>
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
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Product</label>
                                            <Lov name="mailProduct" id="mailProduct" lovTitle="Product" closeButtonIds={['btok', 'BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLOSE']} uppercase={true} dialogWidth="600" dialogHeight="520" actionUrl="products.defaults.ux.screenloadProductLov.do?productObject=productName&formNumber=1&activeProduct=Y&rowIndex=0" uppercase={true} />
                                            <IToolTip value={'Product'} target={'mailProduct'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Orgin</label>
                                            <Lov name="orginOfBooking" id="orginOfBooking" lovTitle="Origin" isReactContext={true}  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                            <IToolTip value={'Orgin'} target={'orginOfBooking'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Destination</label>
                                            <Lov name="destinationOfBooking" id="destinationOfBooking" lovTitle="Destination" isReactContext={true}  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                            <IToolTip value={'Destination'} target={'destinationOfBooking'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="2">
                                        <div className="form-group">
                                            <label className="form-control-label ">Via Point</label>
                                            <Lov name="viaPointOfBooking" id="viaPointOfBooking" lovTitle="Via Point" isReactContext={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true}/>
                                            <IToolTip value={'Via Point'} target={'viaPointOfBooking'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    </Row>
                                    <Row>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Shipping Date</label>
                                            <DatePicker name="shipmentDate" id="shipmentDate" />
                                            <IToolTip value={'Shipping Date'} target={'shipmentDate'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <IFlightNumber flightnumber={this.props.initialValues} mode="edit" showDate={false}></IFlightNumber>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Flight From</label>
                                            <DatePicker name="bookingFlightFrom" id="bookingFlightFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" />
                                            <IToolTip value={'Flight From'} target={'bookingFlightFrom'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Flight To</label>
                                            <DatePicker name="bookingFlightTo" id="bookingFlightTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="fromDate" />
                                            <IToolTip value={'Flight To'} target={'bookingFlightTo'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Agent Code</label>
                                            <Lov name="agentCode" id="agentCode" isReactContext={true} lovTitle="Agent Code" dialogWidth="600" dialogHeight="520" actionUrl='shared.defaults.ux.agent.screenloadagentlov.do?multiselect=N&pagination=Y&textfiledObj=agentCode&formNumber=1&textfiledDesc=&rowCount=0&agentCode="' 
											formParameters={{ 0 : "agentCode", 2:'customerCode'} }	uppercase={true}		
											/>
                                            <IToolTip value={'Agent Code'} target={'agentCode'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Customer Code</label>
                                            <Lov name="customerCode" id="customerCode" closeButtonIds={['CMP_Shared_Customer_CustomerLov_Close', 'btnOk']} isReactContext={true} lovTitle="Customer Code" dialogWidth="600" dialogHeight="520" actionUrl="ux.showCustomer.do?formCount=1&mode=Y" 
											formParameters={{ 2:'customerCode'} } uppercase={true}
											/>
                                            <IToolTip value={'Customer Code'} target={'customerCode'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">User ID</label>
                                            <ITextField mode="edit" name="bookingUserId" id="bookingUserId" type="text" uppercase={true} ></ITextField>
                                            <IToolTip value={'User ID'} target={'bookingUserId'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label className="form-control-label ">Shipment Status</label>
                                            <ISelect defaultOption={true} name="bookingStatus" id="bookingStatus" options={this.state.status} uppercase={true}/>
                                            <IToolTip value={'Shipment Status'} target={'bookingStatus'} placement='bottom'/>
                                        </div>
                                    </Col>
                                    {/* <Col xs="2">
                                        <div className="form-group">
                                            <label className="form-control-label ">SCC Group</label>
                                            <Lov name="mailSccGroup" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
											closeButtonIds={['btnOk', 'btnClose']} 
											/>
                                        </div>
                                    </Col> */}
                                </Row>

                            </div>
                            <div className="btn-row">
                                <IButton id="list" category="primary" privilegeCheck={false}  onClick={this.onlistAwbDetails}>List</IButton>
                                <IToolTip value={'List'} target={'list'} placement='bottom'/>
                                <IButton id="Clear" category="default" privilegeCheck={false}  onClick={this.onclearAwbDetails}>Clear</IButton>
                                <IToolTip value={'Clear'} target={'Clear'} placement='bottom'/>
                            </div>
                            {(this.props.screenMode == 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                        </div>

                   :(
                    <div className="header-summary-panel flippane animated fadeInDown">
                    <div className="pad-md">
                    {this.props.filter?
                        <Row>
                            {this.props.filter.shipmentPrefix && this.props.filter.shipmentPrefix.length > 0 &&
                                this.props.filter.masterDocumentNumber && this.props.filter.masterDocumentNumber.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label">AWB</Label>
                                    <div className="form-control-data">{this.props.filter.shipmentPrefix} - {this.props.filter.masterDocumentNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFrom && this.props.filter.bookingFrom.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label ">Booking From</Label>
                                    <div className="form-control-data">  {this.props.filter.bookingFrom}</div>
                                </Col> : ""

                            }
                            {this.props.filter.bookingTo && this.props.filter.bookingTo.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label ">Booking To</Label>
                                    <div className="form-control-data">  {this.props.filter.bookingTo}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailScc && this.props.filter.mailScc.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">SCC</Label>
                                    <div className="form-control-data"> {this.props.filter.mailScc}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailProduct && this.props.filter.mailProduct.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Product</Label>
                                    <div className="form-control-data">{this.props.filter.mailProduct}</div>
                                </Col> : ""
                            }
                            {this.props.filter.orginOfBooking && this.props.filter.orginOfBooking.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Origin</Label>
                                    <div className="form-control-data">{this.props.filter.orginOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.destinationOfBooking && this.props.filter.destinationOfBooking.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Destination</Label>
                                    <div className="form-control-data">{this.props.filter.destinationOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.viaPointOfBooking && this.props.filter.viaPointOfBooking.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Via Point</Label>
                                    <div className="form-control-data">{this.props.filter.viaPointOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.shipmentDate && this.props.filter.shipmentDate.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Shipment Date</Label>
                                    <div className="form-control-data">{this.props.filter.shipmentDate}</div>
                                </Col> : ""
                            }
                            {this.props.filter.flightnumber ?
                                <Col xs="3">
                                    <Label className="form-control-label">Flight Number</Label>
                                    <IFlightNumber {...this.props.filter.flightnumber} showDate={false} mode="display"></IFlightNumber>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFlightFrom && this.props.filter.bookingFlightFrom.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Flight From</Label>
                                    <div className="form-control-data">{this.props.filter.bookingFlightFrom}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFlightTo && this.props.filter.bookingFlightTo.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Flight To</Label>
                                    <div className="form-control-data">{this.props.filter.bookingFlightTo}</div>
                                </Col> : ""
                            }
                            {this.props.filter.agentCode && this.props.filter.agentCode.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Agent Code</Label>
                                    <div className="form-control-data">{this.props.filter.agentCode}</div>
                                </Col> : ""
                            }
                            {
                                this.props.filter.customerCode && this.props.filter.customerCode.length > 0 ?
                                    <Col xs="3">
                                        <Label className="form-control-label">Customer Code</Label>
                                        <div className="form-control-data">{this.props.filter.customerCode}</div>
                                    </Col> : ""
                            }
                            {this.props.filter.bookingUserId && this.props.filter.bookingUserId.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">User Id</Label>
                                    <div className="form-control-data">{this.props.filter.bookingUserId}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingStatus && this.props.filter.bookingStatus.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">Shipment Status</Label>
                                    <div className="form-control-data">{this.selectedStatusLabel}</div>
                                </Col> : ""
                            }
                            {/* {this.props.filter.mailSccGroup && this.props.filter.mailSccGroup.length > 0 ?
                                <Col xs="3">
                                    <Label className="form-control-label">SCC Group</Label>
                                    <div className="form-control-data"> {this.props.filter.mailSccGroup}</div>
                                </Col> : ""
                            } */}
                        </Row>:null}
                    
                    </div>
                    <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>

                         <div className="header-extra-data" style={{display:this.props.popoverCount > 0 ? null:'none'}}>
                            <div className="badge" id="filterPopover" onMouseEnter={this.togglePopover}>+{this.props.popoverCount}</div>
                            <IPopover placement="auto-start" isOpen={this.state.showPopover} target={'filterPopover'} toggle={this.togglePopover} className="icpopover"> >
                                <IPopoverBody>
                                    <div className="header-extra-data-panel">
                                        {this.props.popOver && this.props.popOver.shipmentPrefix && this.props.popOver.shipmentPrefix.length > 0 &&
                                            this.props.filter.masterDocumentNumber && this.props.filter.masterDocumentNumber.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">AWB</Label>
                                                <div className="form-control-data">{this.props.popOver.shipmentPrefix} - {this.props.popOver.masterDocumentNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFrom && this.props.popOver.bookingFrom.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Booking From :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFrom}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.bookingTo && this.props.popOver.bookingTo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Booking To :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingTo}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.mailScc && this.props.popOver.mailScc.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">SCC :</Label>
                                                <div className="form-control-data">{this.props.popOver.mailScc}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.mailProduct && this.props.popOver.mailProduct.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Product :</Label>
                                                <div className="form-control-data">{this.props.popOver.mailProduct}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.orginOfBooking && this.props.popOver.orginOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Origin :</Label>
                                                <div className="form-control-data">{this.props.popOver.orginOfBooking}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.destinationOfBooking && this.props.popOver.destinationOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Destination :</Label>
                                                <div className="form-control-data">{this.props.popOver.destinationOfBooking}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.viaPointOfBooking && this.props.popOver.viaPointOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Via Point :</Label>
                                                <div className="form-control-data">{this.props.popOver.viaPointOfBooking}</div>
                                            </div> : null
                                        }
                                        {
                                            this.props.popOver && this.props.popOver.stationOfBooking && this.props.popOver.stationOfBooking.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">Station :</Label>
                                                    <div className="form-control-data">{this.props.popOver.stationOfBooking}</div>
                                                </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.shipmentDate && this.props.popOver.shipmentDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Shipping Date:</Label>
                                                <div className="form-control-data">{this.props.popOver.shipmentDate}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.flightnumber ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Flight Number:</Label>
                                                <div className="form-control-data">{this.props.popOver.flightnumber.carrierCode+' '+this.props.popOver.flightnumber.flightNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFlightFrom && this.props.popOver.bookingFlightFrom.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Flight From :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFlightFrom}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFlightTo && this.props.popOver.bookingFlightTo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Flight To :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFlightTo}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingStatus && this.props.popOver.bookingStatus.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Shipment Status :</Label>
                                                <div className="form-control-data">{this.selectedStatusLabel}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.agentCode && this.props.popOver.agentCode.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Agent Code:</Label>
                                                <div className="form-control-data">{this.props.popOver.agentCode}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.customerCode && this.props.popOver.customerCode.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">Customer Code :</Label>
                                                <div className="form-control-data">{this.props.popOver.customerCode}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingUserId && this.props.popOver.bookingUserId.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">User Id :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingUserId}</div>
                                            </div> : null
                                        }
                                        {/* {this.props.popOver && this.props.popOver.mailSccGroup && this.props.popOver.mailSccGroup.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">SCC Group:</Label>
                                                <div className="form-control-data">{this.props.popOver.mailSccGroup}</div>
                                            </div> : null
                                        } */} 
                                      
                                    </div>
                                </IPopoverBody>
                            </IPopover>
                            
                        </div>
                   </div>)}
            </Fragment>
        )
    }
}


export default wrapForm(Forms.MAIL_AWBBOOKING_FILTER)(FilterPanel);