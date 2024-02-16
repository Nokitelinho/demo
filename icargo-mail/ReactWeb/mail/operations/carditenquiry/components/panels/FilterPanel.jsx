import React, { Fragment } from 'react';

import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col } from "reactstrap";
import { IButton, ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistCarditEnquiry = this.onlistCarditEnquiry.bind(this);
        this.onclearCarditEnquiry = this.onclearCarditEnquiry.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
        this.dateRangeIdr = 'CarditEnquiryDateRange'
   }
    onlistCarditEnquiry() {
        this.props.onlistCarditEnquiry();
    }
     onclearCarditEnquiry() {
        this.props.onclearCarditEnquiry();
    }
    toggleFilter(){
        this.props.onToggleFilter((this.props.screenMode === 'edit')?'display':'edit');
    }

    showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }
    computeDateRange (formValues) {
        
        if(formValues.mailbagId && formValues.mailbagId.length >0) {
            this.dateRangeIdr = 'DateRange-If-MailId-Present'
        }  else if((formValues.paCode && formValues.paCode.length >0)) {
            this.dateRangeIdr = 'DateRange-If-PACode-Present'
        } 
        else if((formValues.mailOrigin && formValues.mailOrigin.length >0) ||
                (formValues.mailDestination && formValues.mailDestination.length >0) ||
                (formValues.airportCode && formValues.airportCode.length >0)) {
                this.dateRangeIdr = 'DateRange-If-Origin-Dest-Uplift-Present'
        } else {
            this.dateRangeIdr = 'CarditEnquiryDateRange'
        }
    }
    render() {
        this.computeDateRange(this.props.formValues)
        let category=[];
        let flightType=[];
        let status=[];
        let selectedCategory="";
        let selectedCategoryLabel="";
         let selectedStatus="";
        let selectedStatusLabel="";
        let selectedFlightType="";
        let selectedFlightTypeLabel="";
		let awbAttached=[];
		let selectedawbAttached="";
        let selectedawbAttachedLabel="";
         if (!isEmpty(this.props.oneTimeValues)) {
               status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {if(value.fieldValue=== 'ACP' || value.fieldValue=== 'CAP') return value});
               category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
               flightType =this.props.oneTimeValues['mailtracking.defaults.carditenquiry.flighttype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
			     awbAttached=this.props.oneTimeValues['mailtracking.defaults.carditenquiry.awbattached'].filter((value) => {if(value.fieldValue=== 'Y' || value.fieldValue=== 'N') return value});
               status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
			  awbAttached = awbAttached.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            
            }

        if (this.props.screenMode === 'display') {
            if (this.props.filterValues.mailCategoryCode && this.props.filterValues.mailCategoryCode.length > 0) {
                selectedCategory = category.find((element) => {return  element.value === this.props.filterValues.mailCategoryCode});
                selectedCategoryLabel = selectedCategory.label;
            }
             if (this.props.filterValues.mailStatus && this.props.filterValues.mailStatus.length > 0) {
                selectedStatus = status.find((element) => {return  element.value === this.props.filterValues.mailStatus});
                selectedStatusLabel = selectedStatus.label;
            }
            if (this.props.filterValues.flightType && this.props.filterValues.flightType.length > 0) {
                selectedFlightType = flightType.find((element) => {return  element.value === this.props.filterValues.flightType});
                selectedFlightTypeLabel = selectedFlightType.label;
            }
			 if (this.props.filterValues.awbAttached && this.props.filterValues.awbAttached.length > 0) {
                selectedawbAttached = awbAttached.find((element) => {return  element.value === this.props.filterValues.awbAttached});
                selectedawbAttachedLabel = selectedawbAttached.label;
            }
            
        }

        return (<Fragment>

            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                <div className="header-filter-panel flippane">
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="7" md="4">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.mailbagId" /></label>
                                    <ITextField mode="edit" name="mailbagId" uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILBAGID"></ITextField>
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.ooe" /></label>
                                    <Lov name="ooe" lovTitle="Origin OE" uppercase={true} dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.doe" /></label>
                                    <Lov name="doe" lovTitle="Destination OE" uppercase={true} dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.category" /></label>
                                    <ISelect defaultOption={true} name="mailCategoryCode" options={category} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CATEGORY" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.subclass" /></label>
                                    <Lov name="mailSubclass" lovTitle="Subclass" uppercase={true} dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_SUBCLASS" />
                                </div>
                            </Col>
                            <Col xs="2" md="1">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.year" /></label>
                                    <ITextField mode="edit" name="year" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_YEAR" maxlength="1" ></ITextField>
                                </div>
                            </Col>
                            <Col xs="3" md="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.dsn" /></label>
                                    <ITextField mode="edit" name="despatchSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DSN" maxlength="4"></ITextField>
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.rsn" /></label>
                                    <ITextField mode="edit" name="receptacleSerialNumber" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RSN" maxlength="3" ></ITextField>
                                </div>
                            </Col>
                            <Col xs="4" md="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.consignmentNo" /></label>
                                    <ITextField mode="edit" name="conDocNo" type="text" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTNO" maxlength="35" uppercase={true}></ITextField>
                                </div>
                            </Col>
                            <Col xs="4" md="3">
                                <div className="form-group">
                                    <label className="form-control-label ">Consignment Date</label>
                                    <DatePicker name="consignmentDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_CONSIGNMENTDATE" />
                                </div>
                            </Col>
                            <Col xs="4" md="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.fromDate" /></label>
                                    <DatePicker name="fromDate"  dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATEFROM" />
                                </div>
                            </Col>
                            <Col xs="4" md="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.toDate" /></label>
                                    <DatePicker name="toDate"  dateFieldId={this.dateRangeIdr} type="to"  fromDateName="fromDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DATETO" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.pacode" /></label>
                                    <Lov name="paCode" lovTitle="PA Code" uppercase={true} maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1&mode=Y" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_PACODE" />
                                </div>
                            </Col>
                                    
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label ">Flight Type</label>
                                    <ISelect name="flightType" options={flightType} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTTYPE" />
                                </div>
                            </Col>

                            <Col xs="8" md="6">
                                <div className="form-group">
                                    <IFlightNumber mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTNO" uppercase={true}></IFlightNumber>
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.upliftAirport" /></label>
                                    <Lov name="airportCode" lovTitle="uplift airport" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.originAirport" /></label>
                                    <Lov name="mailOrigin" lovTitle="Origin airport" uppercase={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILORIGIN" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.destAirport" /></label>
                                    <Lov name="mailDestination" uppercase={true} lovTitle="Destination airport " dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILDESTINATION" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.mailStatus" /></label>
                                    <ISelect defaultOption={true} name="mailStatus" options={status} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_MAILSTATUS" />
                                </div>
                            </Col>
                            <Col xs="5" md="3">
                                <div className="form-group">
                                    <IUldNumber name="uldNumber" mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_ULDNO" />
                                </div>
                            </Col>
                            
                            <Col xs="5" md="3">
                                <div className="form-group">
                                    <label className="form-control-label ">RDT</label>
                                    <DatePicker name="reqDeliveryDate" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDT" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group mar-t-md">
                                    <TimePicker name="reqDeliveryTime" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_RDTTIME"/>
                                </div>
                            </Col>
                            <Col xs="6" md="4">
                                <div className="form-group">
                                    <label className="form-control-label" >AWB No</label>
                                    <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                                </div>
                            </Col>
							 <Col xs="3" md="2">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.awbattached" /></label>
                                    <ISelect defaultOption={true} name="awbAttached" options={awbAttached} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AWB_ATTACHED" />
                                </div>
                            </Col>

                            <Col xs="5" md="3">
                                <div className="form-group">
                                    <label className="form-control-label ">TSW</label>
                                    <DatePicker name="transportServWindowDate" componentId="CMP_Mail_Operations_CarditEnquiry_TSW" />
                                </div>
                            </Col>
                            <Col xs="3" md="2">
                                <div className="form-group mar-t-md">
                                    <TimePicker name="transportServWindowTime" componentId="CMP_Mail_Operations_CarditEnquiry_TSWTime"/>
                                </div>
                            </Col>
                            <Col xs="4" md="3">
                                <div className="mar-t-md">
                                    <ICheckbox name="pendingResditChecked" label="Pending Resdit Events" />
                                </div>
                            </Col>


                        </Row>
                    </div>
                    <div className="btn-row">
                        <IButton category="primary" bType="LIST" accesskey="L" componentId='CMP_Mail_Operations_CarditEnquiry_List' onClick={this.onlistCarditEnquiry}>List</IButton>
                        <IButton category="default" bType="CLEAR" accesskey="C" componentId='CMP_Mail_Operations_CarditEnquiry_Clear' onClick={this.onclearCarditEnquiry}>Clear</IButton>
                    </div>
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                </div>
            ) : (
                    <div className="header-summary-panel flippane">
                        <div className="pad-md">
                            <Row>
                                {this.props.filter.mailbagId && this.props.filter.mailbagId.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.mailbagId" /></label>
                                        <div className="form-control-data"> {this.props.filter.mailbagId}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.ooe && this.props.filter.ooe.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.ooe" /></label>
                                        <div className="form-control-data">  {this.props.filter.ooe}</div>
                                    </Col> : ""

                                }
                                {this.props.filter.doe && this.props.filter.doe.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label "><IMessage msgkey="mail.operations.carditenquiry.lbl.doe" /></label>
                                        <div className="form-control-data">  {this.props.filter.doe}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailCategoryCode && this.props.filter.mailCategoryCode.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.category" /></label>
                                        <div className="form-control-data"> {selectedCategoryLabel}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailSubclass && this.props.filter.mailSubclass.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.subclass" /></label>
                                        <div className="form-control-data">{this.props.filter.mailSubclass}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.year && this.props.filter.year.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.year" /></label>
                                        <div className="form-control-data">{this.props.filter.year}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.despatchSerialNumber && this.props.filter.despatchSerialNumber.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.dsn" /></label>
                                        <div className="form-control-data">{this.props.filter.despatchSerialNumber}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.receptacleSerialNumber && this.props.filter.receptacleSerialNumber.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.rsn" /></label>
                                        <div className="form-control-data">{this.props.filter.receptacleSerialNumber}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.conDocNo && this.props.filter.conDocNo.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.consignmentNo" /></label>
                                        <div className="form-control-data">{this.props.filter.conDocNo}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.consignmentDate && this.props.filter.consignmentDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">Consignment Date</label>
                                        <div className="form-control-data">{this.props.filter.consignmentDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.consignmentDate && this.props.filter.consignmentDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">Consignment Date</label>
                                        <div className="form-control-data">{this.props.filter.consignmentDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.fromDate" /></label>
                                        <div className="form-control-data">{this.props.filter.fromDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.toDate" /></label>
                                        <div className="form-control-data">{this.props.filter.toDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.paCode && this.props.filter.paCode.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.pacode" /></label>
                                        <div className="form-control-data">{this.props.filter.paCode}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.pendingResditChecked ?
                                    <Col xs="auto">
                                        <label className="form-control-label">Pending Resdit Events</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {this.props.filter.flightnumber ?
                                    <Col xs="auto">
                                        <IFlightNumber {...this.props.filter.flightnumber} mode="display" ></IFlightNumber>
                                    </Col> : ""
                                }
                                {
                                    this.props.filter.airportCode && this.props.filter.airportCode.length > 0 ?
                                        <Col xs="auto">
                                            <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.upliftAirport" /></label>
                                            <div className="form-control-data">{this.props.filter.airportCode}</div>
                                        </Col> : ""
                                }
                                {this.props.filter.uldNumber && this.props.filter.uldNumber.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.uldNo" /></label>
                                        <div className="form-control-data">{this.props.filter.uldNumber}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailOrigin && this.props.filter.mailOrigin.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.originAirport" /></label>
                                        <div className="form-control-data">{this.props.filter.mailOrigin}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailDestination && this.props.filter.mailDestination.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.destAirport" /></label>
                                        <div className="form-control-data">{this.props.filter.mailDestination}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailStatus && this.props.filter.mailStatus.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.mailStatus" /></label>
                                        <div className="form-control-data">{selectedStatusLabel}</div>
                                    </Col> : ""
                                }
								{this.props.filter.awbAttached && this.props.filter.awbAttached.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.awbattached" /></label>
                                        <div className="form-control-data">{selectedawbAttachedLabel}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.flightType && this.props.filter.flightType.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">Flight Type</label>
                                        <div className="form-control-data">{selectedFlightTypeLabel}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.reqDeliveryDate && this.props.filter.reqDeliveryDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">RDT</label>
                                        <div className="form-control-data">{this.props.filter.reqDeliveryDate} {this.props.filter.reqDeliveryTime ? this.props.filter.reqDeliveryTime : ''}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.shipmentPrefix && this.props.filter.shipmentPrefix.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">AWB No.</label>
                                        <div className="form-control-data">{this.props.filter.shipmentPrefix} - {this.props.filter.masterDocumentNumber}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.transportServWindowDate && this.props.filter.transportServWindowDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">TSW</label>
                                        <div className="form-control-data">{this.props.filter.transportServWindowDate} {this.props.filter.transportServWindowTime ? this.props.filter.transportServWindowTime : ''}</div>
                                    </Col> : ""
                                }
                            </Row>
                        </div>

                        <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>

                {this.props.popoverCount > 0 ?
                            <div className="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> >
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.despatchSerialNumber && this.props.popOver.despatchSerialNumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.dsn" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.despatchSerialNumber}</div>
                                                </div> : null
                                            }

                                            {this.props.popOver.receptacleSerialNumber && this.props.popOver.receptacleSerialNumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.rsn" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.receptacleSerialNumber}</div>
                                                </div> : null
                                            }

                                            {this.props.popOver.conDocNo && this.props.popOver.conDocNo.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.consignmentNo" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.conDocNo}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.consignmentDate && this.props.popOver.consignmentDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Consignment Date :</label>
                                                    <div className="form-control-data">{this.props.popOver.consignmentDate}</div>
                                                </div> : null
                                            }
  
                                            {this.props.popOver.fromDate && this.props.popOver.fromDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.fromDate" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.fromDate}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.toDate && this.props.popOver.toDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.toDate" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.toDate}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.paCode && this.props.popOver.paCode.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.pacode" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.paCode}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.pendingResditChecked ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Pending Resdit Events :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.flightnumber ?
                                                <div className="header-extra-data-detail">
                                                    <br></br>
                                                    <IFlightNumber {...this.props.popOver.flightnumber} mode="display" ></IFlightNumber>
                                                    <br></br>
                                                    <br></br>
                                                </div> : null
                                            }
                                            {
                                                this.props.popOver.airportCode && this.props.popOver.airportCode.length > 0 ?
                                                    <div className="header-extra-data-detail">
                                                        <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.upliftAirport" /> :</label>
                                                        <div className="form-control-data">{this.props.popOver.airportCode}</div>
                                                    </div> : null
                                            }
                                            {this.props.popOver.uldNumber && this.props.popOver.uldNumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.uldNo" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.uldNumber}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.mailOrigin && this.props.popOver.mailOrigin.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.originAirport" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.mailOrigin}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.mailDestination && this.props.popOver.mailDestination.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.destAirport" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.mailDestination}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.mailStatus && this.props.popOver.mailStatus.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.mailStatus" /> :</label>
                                                    <div className="form-control-data">{selectedStatusLabel}</div>
                                                </div> : null
                                            }
											{this.props.popOver.awbAttached && this.props.popOver.awbAttached.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.carditenquiry.lbl.awbattached" /> :</label>
                                                    <div className="form-control-data">{selectedawbAttachedLabel}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.flightType && this.props.popOver.flightType.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Flight Type :</label>
                                                    <div className="form-control-data">{selectedFlightTypeLabel}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.reqDeliveryDate && this.props.popOver.reqDeliveryDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">RDT :</label>
                                                    <div className="form-control-data">{this.props.popOver.reqDeliveryDate} {this.props.popOver.reqDeliveryTime ? this.props.popOver.reqDeliveryTime : ''}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.shipmentPrefix && this.props.popOver.shipmentPrefix.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">AWB No. :</label>
                                                    <div className="form-control-data">{this.props.popOver.shipmentPrefix} - {this.props.popOver.masterDocumentNumber}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.transportServWindowDate && this.props.popOver.transportServWindowDate.length > 0 ?
                                    <Col xs="auto">
                                        <label className="form-control-label">TSW</label>
                                        <div className="form-control-data">{this.props.popOver.transportServWindowDate} {this.props.popOver.transportServWindowTime ? this.props.popOver.transportServWindowTime : ''}</div>
                                    </Col> : ""
                                }
                                          
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                            </div>
                            : ""}

                    </div>
                )}
        </Fragment>)
    }
}


export default wrapForm('carditFilter')(FilterPanel);