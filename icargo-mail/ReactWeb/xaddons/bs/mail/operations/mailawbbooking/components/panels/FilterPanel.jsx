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
import { Forms,Key,Constants,Urls,ComponentId } from '../../constants/constants.js'

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            status: [],
            showPopover:false,
        }
        this.dateRangeIdr = 'CarditDsnEnquiryDateRange'
    }

    componentDidMount(){
        this.initializeOneTime();
    }

    initializeOneTime = () => {

        let status = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if (!isEmpty(this.props.oneTimeValues[Key.SHIPMENT_STATUS_LIST]))
            status = this.props.oneTimeValues[Key.SHIPMENT_STATUS_LIST].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
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
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'view' : 'edit');
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

                    <div style={{display:(this.props.screenMode === 'edit' || this.props.screenMode === 'initial')?null:'none'}} >
                        <div class="header-filter-panel flippane animated fadeInDown">
                            <div class="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="7">
                                        <div className="form-group">
                                            <label className="form-control-label" >{Constants.AWB}</label>
                                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.BOOKING_FROM} /></label>
                                            <DatePicker name="bookingFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="bookingTo" componentId={ComponentId.BOOKING_FROM} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.BOOKING_TO} /></label>
                                            <DatePicker name="bookingTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="bookingFrom" componentId={ComponentId.BOOKING_TO} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.SCC} /></label>
                                            <Lov name="mailScc" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="425" actionUrl={Urls.SCCLOV} componentId={ComponentId.SCC}
											closeButtonIds={['btnOk', 'btnClose']}
											/>
                                        </div>
                                    </Col>
                                    <Col xs="5">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.PRODUCT} /></label>
                                            <Lov name="mailProduct" lovTitle="Product" closeButtonIds={['btok', 'BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLOSE']} uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl={Urls.PRODUCTLOV} componentId={ComponentId.PRODUCT} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.ORIGIN} /></label>
                                            <Lov name="orginOfBooking" lovTitle="Origin" isReactContext={true}  dialogWidth="600" dialogHeight="425" actionUrl={Urls.AIRPORTLOV} componentId={ComponentId.ORIGIN} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.VIA_POINT} /></label>
                                            <Lov name="viaPointOfBooking" lovTitle="Via Point" isReactContext={true} dialogWidth="600" dialogHeight="425" actionUrl={Urls.AIRPORTLOV} /*componentId={ComponentId.VIA_POINT}*/ />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.DESTINATION} /></label>
                                            <Lov name="destinationOfBooking" isReactContext={true} lovTitle="Destination" dialogWidth="600" dialogHeight="425" actionUrl={Urls.AIRPORTLOV} componentId={ComponentId.DESTINATION} />
                                        </div>
                                    </Col>
                                    <Col xs="3">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.STATION} /></label>
                                            <Lov name="stationOfBooking" isReactContext={true} lovTitle="Station" dialogWidth="600" dialogHeight="425" actionUrl={Urls.STATIONLOV} componentId={ComponentId.STATION} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.SHIPPING_DATE} /></label>
                                            <DatePicker name="shipmentDate" componentId={ComponentId.SHIPPING_DATE} />
                                        </div>
                                    </Col>
                                    <Col xs="5">
                                        <div className="form-group">
                                            <IFlightNumber mode="edit" showDate={false}></IFlightNumber>
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.FLIGHT_FROM} /></label>
                                            <DatePicker name="bookingFlightFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" componentId={ComponentId.FLIGHT_FROM} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.FLIGHT_TO} /></label>
                                            <DatePicker name="bookingFlightTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="fromDate" componentId={ComponentId.FLIGHT_TO} />
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.AGENT_CODE} /></label>
                                            <Lov name="agentCode" isReactContext={true} lovTitle="Agent Code" dialogWidth="600" dialogHeight="425" actionUrl={Urls.AGENTLOV} componentId={ComponentId.AGENT_CODE}
											formParameters={{ 0 : "agentCode", 2:'customerCode'} }			
											/>
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.CUSTOMER_CODE} /></label>
                                            <Lov name="customerCode" closeButtonIds={['CMP_Shared_Customer_CustomerLov_Close', 'btnOk']} isReactContext={true} lovTitle="Customer Code" dialogWidth="600" dialogHeight="425" actionUrl={Urls.CUSTOMERLOV} componentId={ComponentId.CUSTOMER_CODE} 
											formParameters={{ 2:'customerCode'} }
											/>
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.USER_ID} /></label>
                                            <ITextField mode="edit" name="bookingUserId" type="text" componentId={ComponentId.USER_ID}  ></ITextField>
                                        </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.SHIPMENT_STATUS_LBL} /></label>
                                            <ISelect defaultOption={true} name="bookingStatus" options={this.state.status} componentId={ComponentId.SHIPMENT_STATUS_LBL} />
                                        </div>
                                    </Col>
                                    
                                </Row>

                            </div>
                            <div class="btn-row">
                                <IButton category="primary" privilegeCheck={false}  componentId={ComponentId.LIST_BTN} onClick={this.onlistAwbDetails}><IMessage msgkey={Key.LIST_LBL}/></IButton>
                                <IButton category="default" privilegeCheck={false}  componentId={ComponentId.CLEAR_BTN} onClick={this.onclearAwbDetails}><IMessage msgkey={Key.CLEAR_LBL}/></IButton>
                            </div>
                            {(this.props.screenMode == 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                        </div>
                    </div>


                    <div className="header-summary-panel flippane animated fadeInDown"  style={{display:(this.props.screenMode === 'edit' || this.props.screenMode === 'initial')?'none':null}}>
                    <div className="pad-md">
                    {this.props.filter?
                        <Row>
                            {this.props.filter.shipmentPrefix && this.props.filter.shipmentPrefix.length > 0 &&
                                this.props.filter.masterDocumentNumber && this.props.filter.masterDocumentNumber.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label">{Constants.AWB}</Label>
                                    <div className="form-control-data">{this.props.filter.shipmentPrefix} - {this.props.filter.masterDocumentNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFrom && this.props.filter.bookingFrom.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label "><IMessage msgkey={Key.BOOKING_FROM} /></Label>
                                    <div className="form-control-data">  {this.props.filter.bookingFrom}</div>
                                </Col> : ""

                            }
                            {this.props.filter.bookingTo && this.props.filter.bookingTo.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label "><IMessage msgkey={Key.BOOKING_TO} /></Label>
                                    <div className="form-control-data">  {this.props.filter.bookingTo}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailScc && this.props.filter.mailScc.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.SCC} /></Label>
                                    <div className="form-control-data"> {this.props.filter.mailScc}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailProduct && this.props.filter.mailProduct.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.PRODUCT} /></Label>
                                    <div className="form-control-data">{this.props.filter.mailProduct}</div>
                                </Col> : ""
                            }
                            {this.props.filter.orginOfBooking && this.props.filter.orginOfBooking.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.ORIGIN} /></Label>
                                    <div className="form-control-data">{this.props.filter.orginOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.viaPointOfBooking && this.props.filter.viaPointOfBooking.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.VIA_POINT} /></Label>
                                    <div className="form-control-data">{this.props.filter.viaPointOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.destinationOfBooking && this.props.filter.destinationOfBooking.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.DESTINATION} /></Label>
                                    <div className="form-control-data">{this.props.filter.destinationOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.stationOfBooking && this.props.filter.stationOfBooking.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.STATION} /></Label>
                                    <div className="form-control-data">{this.props.filter.stationOfBooking}</div>
                                </Col> : ""
                            }
                            {this.props.filter.shipmentDate && this.props.filter.shipmentDate.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.SHIPPING_DATE} /></Label>
                                    <div className="form-control-data">{this.props.filter.shipmentDate}</div>
                                </Col> : ""
                            }
                            {this.props.filter.flightnumber ?
                                <Col xs="4">
                                    <IFlightNumber {...this.props.filter.flightnumber} mode="display" showDate={false} ></IFlightNumber>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFlightFrom && this.props.filter.bookingFlightFrom.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_FROM} /></Label>
                                    <div className="form-control-data">{this.props.filter.bookingFlightFrom}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingFlightTo && this.props.filter.bookingFlightTo.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_TO} /></Label>
                                    <div className="form-control-data">{this.props.filter.bookingFlightTo}</div>
                                </Col> : ""
                            }
                            {this.props.filter.agentCode && this.props.filter.agentCode.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.AGENT_CODE} /></Label>
                                    <div className="form-control-data">{this.props.filter.agentCode}</div>
                                </Col> : ""
                            }
                            {
                                this.props.filter.customerCode && this.props.filter.customerCode.length > 0 ?
                                    <Col xs="4">
                                        <Label className="form-control-label"><IMessage msgkey={Key.CUSTOMER_CODE} /></Label>
                                        <div className="form-control-data">{this.props.filter.customerCode}</div>
                                    </Col> : ""
                            }
                            {this.props.filter.bookingUserId && this.props.filter.bookingUserId.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.USER_ID} /></Label>
                                    <div className="form-control-data">{this.props.filter.bookingUserId}</div>
                                </Col> : ""
                            }
                            {this.props.filter.bookingStatus && this.props.filter.bookingStatus.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.SHIPMENT_STATUS_LBL} /></Label>
                                    <div className="form-control-data">{this.selectedStatusLabel}</div>
                                </Col> : ""
                            }
                        
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
                                                <Label className="form-control-label">{Constants.AWB} :</Label>
                                                <div className="form-control-data">{this.props.popOver.shipmentPrefix} - {this.props.popOver.masterDocumentNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFrom && this.props.popOver.bookingFrom.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.BOOKING_FROM} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFrom}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.bookingTo && this.props.popOver.bookingTo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.BOOKING_TO} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingTo}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.mailScc && this.props.popOver.mailScc.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.SCC} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.mailScc}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.mailProduct && this.props.popOver.mailProduct.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.PRODUCT} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.mailProduct}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.orginOfBooking && this.props.popOver.orginOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.ORIGIN} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.orginOfBooking}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.viaPointOfBooking && this.props.popOver.viaPointOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.VIA_POINT} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.viaPointOfBooking}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.destinationOfBooking && this.props.popOver.destinationOfBooking.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.DESTINATION} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.destinationOfBooking}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.flightnumber ?
                                            <div className="header-extra-data-detail">
                                                <br></br>
                                                <IFlightNumber {...this.props.popOver.flightnumber} mode="display" showDate={false} ></IFlightNumber>
                                                <br></br>
                                                <br></br>
                                            </div> : null
                                        }
                                        {
                                            this.props.popOver && this.props.popOver.stationOfBooking && this.props.popOver.stationOfBooking.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label"><IMessage msgkey={Key.STATION} />:</Label>
                                                    <div className="form-control-data">{this.props.popOver.stationOfBooking}</div>
                                                </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.shipmentDate && this.props.popOver.shipmentDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.SHIPPING_DATE} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.shipmentDate}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFlightFrom && this.props.popOver.bookingFlightFrom.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_FROM} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFlightFrom}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingFlightTo && this.props.popOver.bookingFlightTo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_TO} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingFlightTo}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingStatus && this.props.popOver.bookingStatus.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.SHIPMENT_STATUS_LBL} /> :</Label>
                                                <div className="form-control-data">{this.selectedStatusLabel}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.agentCode && this.props.popOver.agentCode.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.AGENT_CODE} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.agentCode}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.customerCode && this.props.popOver.customerCode.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.CUSTOMER_CODE} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.customerCode}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.bookingUserId && this.props.popOver.bookingUserId.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.USER_ID} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.bookingUserId}</div>
                                            </div> : null
                                        }
                                        
                                      
                                    </div>
                                </IPopoverBody>
                            </IPopover>
                        </div>

                </div>
            </Fragment>


        )
    }
}


export default wrapForm(Forms.MAIL_AWBBOOKING_FILTER)(FilterPanel);