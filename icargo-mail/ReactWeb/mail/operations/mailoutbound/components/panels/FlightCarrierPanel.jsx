import React, { Component, Fragment } from 'react';
import { IRadio } from 'icoreact/lib/ico/framework/html/elements';
import { FormGroup, Label } from "reactstrap";
import { ITextField, ISelect,ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';


class FilterCarrierPanel extends Component {
    constructor(props) {
        super(props);
        this.dateRange='MailOutboundDateRange'
        this.flightOperationalstatus = [];
        this.flightStatus = [];
        this.flightStatusMap = new Map();
        this.init();
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
    }

    init() {
        if (!isEmpty(this.props.oneTimeValues)) { 
            this.flightOperationalstatus = this.props.oneTimeValues['mailtracking.defaults.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.flightOperationalstatus.push({ value: 'N', label: 'New' });
            this.flightStatus = this.props.oneTimeValues['flight.operation.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            
            this.props.oneTimeValues['flight.operation.flightstatus'].forEach(status => {
                this.flightStatusMap.set(status.fieldValue, status.fieldDescription);
            });
        }
    }
   toggleFilter=()=> {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }
    onchangeFilter=()=> {
        this.props.onchangeFilter();
    }
    onlistDetails=()=> {
        this.props.onlistDetails(this.props.outboundFilter.values);
    }
    onclearDetails=()=> {
        this.props.onclearDetails();
    }
    showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }

    render() {
        
        let operationalStatusLabel = '';
        let selectedStatus = '';
        let selectedFlightStatus = '';
        let operationalFlightStatusLabel = '';
        /*
        if (!isEmpty(this.props.oneTimeValues)) { 
            flightOperationalstatus = this.props.oneTimeValues['mailtracking.defaults.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            flightOperationalstatus.push({ value: 'N', label: 'New' });
            flightStatus = this.props.oneTimeValues['flight.operation.flightstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        } */
        if (this.props.filterValues.flightOperationalStatus && this.props.filterValues.flightOperationalStatus.length > 0) {
            selectedStatus = this.flightOperationalstatus.find((element) => { return element.value === this.props.filterValues.flightOperationalStatus });
            operationalStatusLabel = selectedStatus.label;
        }

        if (this.props.filterValues.flightStatus && this.props.filterValues.flightStatus.length > 0) {
            selectedFlightStatus = this.flightStatus.find((element) => { return element.value === this.props.filterValues.flightStatus[0] });
            operationalFlightStatusLabel = selectedFlightStatus.label;
        }
        return (
            <div>
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ?
                    <IRadio name="filterType" options={[{ 'label': 'Flight', 'value': 'F' }, { 'label': 'Carrier', 'value': 'C' }]} onChange={this.props.onchangeFilter.bind(this)} /> : null}
                {(this.props.screenMode === 'edit') ? (<i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>) : null}
                {(this.props.screenMode === 'display') ? (<i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>) : null}
                <Fragment>
                    {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                        <div className="formview">
                            {(this.props.flightCarrierflag === 'F') ?
                                 <Fragment>
                                <div className="pad-t-md border-top">
                                    <Row >
                                        <Col xs="7" md="6">
                                            <IFlightNumber {...this.props.filterValues.flightnumber} mode="edit" uppercase={true}></IFlightNumber>
                                        </Col>
                                        <Col xs="3" md="2">
                                            <div className="form-group mandatory">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>
                                                    <Lov name="airportCode" lovTitle="uplift airport" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                                    
                                            </div>
                                        </Col>
                                        <Col xs="3" md="2">
                                            <div className="form-group">
                                                <Label className="form-control-label">Destination</Label>
                                                <Lov name="destination" lovTitle="Destination" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_DESTINATION' dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                            </div>
                                        </Col>
                                        <Col xs="4">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.fromdate" /></Label>
                                                <div className="form-row">
                                                    <Col xs="18">
                                                        <DatePicker name="fromDate" dateFieldId={this.dateRange} type="from" toDateName="toDate" /></Col>
                                                    <Col xs="6"><TimePicker name="fromTime" /></Col>
                                                </div>

                                            </div>
                                        </Col>
                                        <Col xs="4">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.todate" /></Label>
                                                <div className="form-row">
                                                    <Col xs="18">
                                                        <DatePicker name="toDate" dateFieldId={this.dateRange} type="to" fromDateName="fromDate" />
                                                    </Col>
                                                    <Col xs="6">
                                                        <TimePicker name="toTime"/>
                                                    </Col>
                                                </div>
                                            </div>
                                        </Col>
                                        
                                        <Col xs="4" md="3">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                    Mail Operational Status</Label>
                                                <ISelect name="flightOperationalStatus" options={this.flightOperationalstatus} defaultOption={true} componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                            </div>
                                        </Col>
                                        <Col xs="3" md="3">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                Operating Carrier
                                                </Label>
                                                <ITextField  name="operatingReference" uppercase={true} />
                                            </div>
                                        </Col>
                                        <Col xs="4" md="3">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.flightoperationalstatus" /></Label>
                                                <ISelect name="flightStatus" multi={true} options={this.flightStatus} componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                            </div>
                                        </Col>
                                        <Col xs="4" md="3">
                                            <div className="mar-t-md">
                                                <ICheckbox name="mailFlightOnly" label="Mail Flight" />
                                            </div>
                                        </Col>
                                        {/* <Col>
                                            <div className="mar-t-md">
                                                <IButton category="primary" bType="LIST" accesskey="L" onClick={this.onlistDetails}>List</IButton>
                                                <IButton category="secondary" bType="CLEAR" accesskey="C"  onClick={this.onclearDetails}>Clear</IButton>{' '}
                                            </div>
                                        </Col> */}
                                    </Row>

                                </div>
                                <div className="btn-row">
                                    <IButton category="primary" bType="LIST" accesskey="L" onClick={this.onlistDetails}>List</IButton>
                                    <IButton category="secondary" bType="CLEAR" accesskey="C"  onClick={this.onclearDetails}>Clear</IButton>{' '}
                                </div>
                                </Fragment>
                                : null}
                            {(this.props.flightCarrierflag === 'C') ?
                                <div className="pad-t-md border-top">
                                    <Row>
                                        <Col xs="3" md="2">
                                            <FormGroup className="mandatory">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>
                                                    <Lov name="airportCode" lovTitle="uplift airport" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                                   
                                            </FormGroup>
                                        </Col>
                                        <Col xs="3" md="2">
                                            <FormGroup className="mandatory">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.carriercode" /></Label>
                                                <Lov name="carrierCode" lovTitle="CarrierCode" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                            </FormGroup>
                                        </Col>
                                        <Col xs="3" md="2">
                                            <div className="form-group">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                                                <Lov name="destination" lovTitle="Destination" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                            </div>
                                        </Col>
                                        <Col>
                                            <div className="mar-t-md">
                                                <IButton category="primary" bType="LIST" accesskey="L" onClick={this.onlistDetails}>List</IButton>
                                                <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.onclearDetails}>Clear</IButton>{' '}
                                            </div>
                                        </Col>
                                    </Row>
                                </div>
                                : null}

                        </div>
                    ) : (
                            <div className="displayview">
                                {(this.props.filterValues.filterType === 'F') ?
                                    <div className="pad-t-xs pad-b-2md ">
                                        <Row>
                                            <Col xs="3">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.type" /></Label>
                                                <div className="form-control-data">
                                                    <IMessage msgkey="mail.operations.mailoutbound.flight" /></div>
                                            </Col>
                                            {this.props.filter.flightNumber && this.props.filter.flightNumber.length > 0 ?
                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.flightnumber" /></Label>
                                                    <div className="form-control-data">{this.props.filterValues.carrierCode}-{this.props.filterValues.flightNumber} </div>
                                                </Col> : ""
                                            }
                                            {this.props.filter.flightDate && this.props.filter.flightDate.length > 0 ?
                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.flightdate" /></Label>
                                                    <div className="form-control-data">{this.props.filterValues.flightDate}</div>
                                                </Col> : ""
                                            }
                                            {this.props.filter.airportCode && this.props.filter.airportCode.length > 0 ?
                                                <Col xs="3">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>
                                                    <div className="form-control-data">{this.props.filterValues.airportCode}</div>
                                                </Col> : ""
                                            }
                                            {this.props.filter.destination && this.props.filter.destination.length > 0 ?

                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                        <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                                                    <div className="form-control-data">{this.props.filterValues.destination}</div>
                                                </Col> : ""
                                            }
                                            {this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?

                                                <Col xs="3">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.fromdate" /></Label>
                                                    <div className="form-control-data">{this.props.filter.fromDate} {this.props.filter.fromTime}</div>

                                                </Col> : ""
                                            }
                                            {this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.todate" /></Label>
                                                    <div className="form-control-data">{this.props.filter.toDate} {this.props.filter.toTime}</div>
                                                </Col> : ""
                                            }
                                            {this.props.filter.flightOperationalStatus && this.props.filter.flightOperationalStatus.length > 0 ?
                                                <Col xs="3">
                                                    <Label className="form-control-label">
                                                    Mail Operational Status
                                                    </Label>
                                                    <div className="form-control-data"> {operationalStatusLabel}</div>
                                                </Col> : ""
                                            }
                                            {
                                            this.props.filter.operatingReference && this.props.filter.operatingReference.length > 0 ?
                                                <Col xs="3">
                                                    <Label className="form-control-label">
                                                    Operating Carrier
                                                    </Label>
                                                    <div className="form-control-data">{this.props.filter.operatingReference}</div>
                                                </Col> : ""
                                            }
                                            {
                                                this.props.filter.flightStatus && this.props.filter.flightStatus.length > 0 ?
                                                <Col xs="3">
                                                <FormGroup>
                                                    <Label className="form-control-label">Flight Operational Status</Label>
                                                    <div className="form-control-data">
                                                    {
                                                    this.props.filter.flightStatus
                                                    .map((item, index) => {
                                                        if (index < 2)
                                                            return this.flightStatusMap.get(item) + (index !== 1 && index !== this.props.filter.flightStatus.length - 1 ? "," : "")
                                                        else if (index == this.props.filter.flightStatus.length - 1) return ` ( + ${this.props.filter.flightStatus.length - 2} )`;
                                                        else return null;
                                                    })
                                                    }
                                                    </div>
                                                </FormGroup>
                                                </Col> :""
                                            }   
                                            {
                                            this.props.filter.mailFlightOnly && this.props.filter.mailFlightOnly===true?
                                                <Col xs="3">
                                                    <Label className="form-control-label">
                                                    Mail Flights
                                                    </Label>
                                                    <div className="form-control-data">Yes</div>
                                                </Col> : ""
                                            }   
                                        </Row>
                                         {this.props.popoverCount > 0 ?
                            <div class="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> >
                                <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.toDate && this.props.popOver.toDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"> To Date :</label>
                                                    <div className="form-control-data">{this.props.popOver.toDate}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.flightOperationalStatus && this.props.popOver.flightOperationalStatus.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mail Operational Status :</label>
                                                    <div className="form-control-data">{operationalStatusLabel}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.operatingReference && this.props.popOver.operatingReference.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Operating Reference :</label>
                                                    <div className="form-control-data">{this.props.popOver.operatingReference}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.flightStatus && this.props.popOver.flightStatus.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Flight Status :</label>
                                                    <div className="form-control-data">{operationalFlightStatusLabel}</div>
                                                </div> : null
                                            }
                                            {
                                            this.props.popOver.mailFlightOnly && this.props.popOver.mailFlightOnly === true ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mail Flights</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                                </div>: ""} 
                                
                                    </div>

                                    : (this.props.filterValues.filterType === 'C') ?
                                        <div className="pad-t-xs pad-b-2md">
                                            <Row>
                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                        <IMessage msgkey="mail.operations.mailoutbound.type" /></Label>
                                                    <div className="form-control-data">
                                                        <IMessage msgkey="mail.operations.mailoutbound.carrier" /></div>
                                                </Col>
                                                {
                                                    this.props.filterValues.airportCode && this.props.filterValues.airportCode.length > 0 ?
                                                        <Col xs="4">
                                                            <Label className="form-control-label">
                                                                <IMessage msgkey="mail.operations.mailoutbound.airport" /></Label>
                                                            <div className="form-control-data">{this.props.filterValues.airportCode}</div>
                                                        </Col> : ""
                                                }
                                                {
                                                    this.props.filterValues.carrierCode && this.props.filterValues.carrierCode.length > 0 ?

                                                        <Col xs="4">
                                                            <Label className="form-control-label">
                                                                <IMessage msgkey="mail.operations.mailoutbound.carriercode" /></Label>
                                                            <div className="form-control-data">{this.props.filterValues.carrierCode}</div>
                                                        </Col> : ""
                                                }
                                                {this.props.filterValues.destination && this.props.filterValues.destination.length > 0 ?

                                                    <Col xs="4">
                                                        <Label className="form-control-label">
                                                            <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                                                        <div className="form-control-data">{this.props.filterValues.destination}</div>
                                                    </Col> : ""
                                                }
                                            </Row>
                                        </div>
                                        : null
                                }
                            </div>
                        )}
                </Fragment>
                {/*
                {(this.props.screenMode === 'display') ?
                    <div class="header-extra-data">
                        <div className="badge">+1</div>
                        <div className="header-extra-data-panel">
                            <div className="header-extra-data-detail">
                                <label className="form-control-label">Flight Operational Status :</label>
                                <div className="form-control-data">Data</div>
                            </div>
                        </div>
                    </div> : null}
                */}
            </div>
        )
    }
}

FilterCarrierPanel.propTypes = {
    onToggleFilter:PropTypes.func,
    screenMode:PropTypes.string,
    onchangeFilter:PropTypes.func,
    onlistDetails:PropTypes.func,
    onclearDetails:PropTypes.func,
    outboundFilter:PropTypes.object,
    oneTimeValues:PropTypes.object,
    filterValues:PropTypes.object,
    flightCarrierflag:PropTypes.string,
}
export default wrapForm('outboundFilter')(FilterCarrierPanel);