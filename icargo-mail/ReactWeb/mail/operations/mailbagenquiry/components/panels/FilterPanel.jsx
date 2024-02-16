import React, { Fragment } from 'react';
import PropTypes from 'prop-types'
import { ITextField, ISelect, ICheckbox, IRadio, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { FormGroup, Label, Row, Col, Collapse, Container } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';



class FilterPanel extends React.Component {
	constructor(props) {
		super(props);
		this.onlistMailbagsEnquiry = this.onlistMailbagsEnquiry.bind(this);
		this.onclearMailbagsEnquiry = this.onclearMailbagsEnquiry.bind(this);
		this.toggleFilter = this.toggleFilter.bind(this);
		this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
		this.dateRange='MailbagDetailsDateRange'
	}
	onlistMailbagsEnquiry() {
		this.props.onlistMailbagEnquiry();
	}
	onclearMailbagsEnquiry() {
		this.props.onclearMailbagsEnquiry();
	}
	toggleFilter() {
		this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'view' : 'edit');
	}
	showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }

	computeDateRange (formValues) {
        if(formValues.mailbagId && formValues.mailbagId.length >0) {
            this.dateRange = 'DateRange-If-MailId-Present'
        } else if((formValues.mailOrigin && formValues.mailOrigin.length >0) ||
                (formValues.mailDestination && formValues.mailDestination.length >0)) {
                this.dateRange = 'DateRange-If-Origin-Dest-Present'
        } else {
            this.dateRange = 'MailbagDetailsDateRange'
        }
    }
	render() {

		this.computeDateRange(this.props.formValues)

		let category = [];
		let status = [];
		let carditStat = [];
		let serviceLevels = [];	
        let onTimeDeliveryStat = [];
		if (!isEmpty(this.props.oneTimeValues)) {
			status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {
				if (value.fieldValue !== 'ASG' && value.fieldValue !== 'DMG'
					&& value.fieldValue !== 'CDT' && value.fieldValue !== 'MFT'
					&& value.fieldValue !== 'DEP' && value.fieldValue !== 'ARV'
					&& (!value.fieldDescription.toLowerCase().includes("resdit")))
					return value
			});

			category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
			status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
			serviceLevels = this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
		}

		carditStat = [{ "label": "Available", "value": "Y" },
		{ "label": "Not Available", "value": "N" }]

		onTimeDeliveryStat = [{ "label": "Yes", "value": "Y" },
		{ "label": "No", "value": "N" }]

		return (
			<Fragment>
				{(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (

					<div className="header-filter-panel flippane">
						<div className="pad-md pad-b-3xs">
							<Row>
								<Col xs="7" md="5">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.mailbagId" /></label>
										<ITextField mode="edit" uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_MailbagId" name="mailbagId" type="text"></ITextField>
									</div>
								</Col>
								 <Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.originoe" /></label>
										<Lov name="ooe" lovTitle="Origin OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_originOE"/>
									</div>
								</Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.destnoe" /></label>
										<Lov name= "doe" lovTitle="Destination OE" uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_destnOE" />
									</div>
								</Col> 
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.mailorigin" /></label> 
										<Lov name="mailOrigin" lovTitle="Origin Airport" maxlength="3" uppercase={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_mailorigin"/>
									</div>
								</Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.maildestn" /></label>
										<Lov name= "mailDestination" lovTitle="Destination Airport" maxlength="3" uppercase={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_maildestination" />
									</div>
								</Col> 
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.cat" /></label>
										<ISelect name="mailCategoryCode" options={category} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_category" />
									</div>
								</Col>
								<Col xs="2" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.sc" /></label>
										<Lov name="mailSubclass" uppercase={true} lovTitle="Subclass" dialogWidth="600"  dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_Sc"/>
									</div>
								</Col>
								<Col xs="3" md="1">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.yr" /></label>
										<ITextField mode="edit" name="year" type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Year" ></ITextField>
									</div>
								</Col>
								<Col xs="2" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.dsn" /></label>
										<ITextField mode="edit" name="despatchSerialNumber" uppercase={true} type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Dsn"></ITextField>
									</div>
								</Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.rsn" /></label>
										<ITextField mode="edit" name="receptacleSerialNumber" type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Rsn"></ITextField>
									</div>
								</Col>
								<Col xs="3" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.operationDetails" /></label>
										<ISelect name="operationalStatus" options={status} componentId="CMP_Mail_Operations_MailBagEnquiry_currentStatus" />
									</div>
								</Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.port" /></label>
										<Lov name="scanPort" lovTitle="Airport" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_Mail_Operations_MailBagEnquiry_port" uppercase={true}/>
									</div>
								</Col>
								<Col xs="3" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.tooltip.carditType" /></label>
										<ISelect name="carditStatus" options={carditStat} />
									</div>
								</Col>
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.formDate" /></label>
										<DatePicker name="fromDate" dateFieldId={this.dateRange} componentId='CMP_Mail_Operations_MailBagEnquiry_FromDate'type="from" toDateName="toDate" />
									</div>
								</Col>
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.toDate" /></label>
										<DatePicker name="toDate" dateFieldId={this.dateRange}  componentId='CMP_Mail_Operations_MailBagEnquiry_ToDate' type="to" fromDateName="fromDate"/>
									</div>
								</Col>

								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ReqDlvDate" /></label>
										<DatePicker name="rdtDate" dateFieldId="DUMMY" type="from" toDateName="rdtDate" componentId='CMP_Mail_Operations_CarditEnquiry_ReqDlvDate' />
									</div>
								</Col>
								<Col xs="3" className="form-group mar-t-md">
                                    <TimePicker name="reqDeliveryTime" componentId="CMP_Mail_Operations_MailBagEnquiry_ReqDlvTime" />
									
                                </Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.userid" /></label>
										<ITextField mode="edit" name="userID" type="text" uppercase={true} componentId='CMP_Mail_Operations_MailBagEnquiry_Userid'></ITextField>
									</div>
                                </Col>
								<Col xs="3" md="2">
									<div className="form-check mar-t-md">
										<ICheckbox name="damageFlag" label="Damaged" componentId="CMP_Mail_Operations_MailBagEnquiry_damaged" />
									</div>
								</Col>
								<Col xs="2" md="2">
									<div className="form-check mar-t-md">
										<ICheckbox name="transitFlag" label="Transit" componentId='CMP_Mail_Operations_MailBagEnquiry_transit' />
									</div>
								</Col>
								<Col xs="6" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.contNo" /></label>
										<ITextField mode="edit" name="containerNo" type="text" uppercase={true} componentId='CMP_Mail_Operations_MailBagEnquiry_ContNo'></ITextField>
									</div>
								</Col>
								{isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label ">Service Level</label>
										<ISelect defaultOption={true} name="serviceLevel" options={serviceLevels} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_servicelevel" />
									</div>
								</Col>
								}	
								<Col xs="9" md="6">
									<div className="form-group">
										<IFlightNumber  flightnumber={this.props.filterValues ? this.props.filterValues.flightnumber : ''}mode="edit" uppercase={true}></IFlightNumber>
									</div>
								</Col>
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.consigmentnumber" /></label>
										<ITextField mode="edit" name="consignmentNo" uppercase={true} type="text" componentId='CMP_Mail_Operations_MailBagEnquiry_ConsigmentNumber'></ITextField>
									</div>
								</Col>
								<Col xs="3" md="2">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.upucode" /></label>
										<ITextField mode="edit" name="upuCode" uppercase={true} type="text"></ITextField>
									</div>
								</Col>

								{isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="4" md="3">
									<div className="form-group">
										<label className="form-control-label ">Service Level</label>
										<ISelect name="serviceLevel" options={serviceLevels} uppercase={true} componentId="CMP_Mail_Operations_MailBagEnquiry_category" />
									</div>
								</Col> }
								
                                {isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="3" md="3">
									<div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ontimedelivery" /></label>
										<ISelect name="onTimeDelivery" options={onTimeDeliveryStat} />
									</div>
								</Col>
								}
								{isSubGroupEnabled('AA_SPECIFIC') &&
								<Col xs="5" md="3">
                                <div className="form-group">
                                    <label className="form-control-label ">TSW</label>
                                    <DatePicker name="transportServWindowDate" componentId="CMP_Mail_Operations_MailBagEnquiry_TSW" />
                                </div>
                                </Col>
								}
								{isSubGroupEnabled('AA_SPECIFIC') &&
                                <Col xs="3" md="2">
                                 <div className="form-group mar-t-md">
                                    <TimePicker name="transportServWindowTime" componentId="CMP_Mail_Operations_MailBagEnquiry_TSWTime"/>
                                 </div>
                                </Col>
								}
							</Row>
						</div>
						<div className="btn-row">
							<IButton category="primary" className="btn btn-primary" componentId='CMP_Mail_Operations_MailbagEnquiry_List' onClick={this.onlistMailbagsEnquiry}><IMessage msgkey="mail.operations.ux.mailbagenquiry.btn.list" /></IButton>
							<IButton category="default" className="btn btn-default" componentId='CMP_Mail_Operations_MailbagEnquiry_Clear' onClick={this.onclearMailbagsEnquiry}><IMessage msgkey="mail.operations.ux.mailbagenquiry.btn.clear" /></IButton>{' '}
						</div>
						{(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
					</div>
				) : (
						<div className="header-summary-panel flippane">
							<div className="pad-md">
								<Row>
									{this.props.filter.mailbagId && this.props.filter.mailbagId.length > 0 ?
										<Col xs="5">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.mailbagid" /></label>
											<div className="form-control-data"> {this.props.filter.mailbagId}</div>
										</Col> : ""
									}

									{this.props.filter.ooe && this.props.filter.ooe.length > 0 ?
										<Col xs="4">

											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.originoe" /></label>
											<div className="form-control-data"> {this.props.filter.ooe}</div>
										</Col> : ""
									}

									{this.props.filter.doe && this.props.filter.doe.length > 0 ?
										<Col xs="4">

											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.destnoe" /></label>
											<div className="form-control-data"> {this.props.filter.doe}</div>
										</Col> : ""
									}
									{this.props.filter.mailOrigin && this.props.filter.mailOrigin.length > 0 ?
										<Col xs="4">

											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.mailorigin" /></label>
											<div className="form-control-data"> {this.props.filter.mailOrigin}</div>
										</Col> : ""
									}
									{this.props.filter.mailDestination && this.props.filter.mailDestination.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.maildestn" /></label>
											<div className="form-control-data"> {this.props.filter.mailDestination}</div>
										</Col> : ""
									}
									{this.props.filter.mailCategoryCode && this.props.filter.mailCategoryCode.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.cat" /></label>
											<div className="form-control-data"> {this.props.filter.mailCategoryCode}</div>
										</Col> : ""
									}
									{this.props.filter.mailSubclass && this.props.filter.mailSubclass.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.sc" /></label>
											<div className="form-control-data"> {this.props.filter.mailSubclass}</div>
										</Col> : ""
									}
									{this.props.filter.year && this.props.filter.year.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.yr" /></label>
											<div className="form-control-data"> {this.props.filter.year}</div>
										</Col> : ""
									}
									{this.props.filter.despatchSerialNumber && this.props.filter.despatchSerialNumber.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.dsn" /></label>
											<div className="form-control-data"> {this.props.filter.despatchSerialNumber}</div>
										</Col> : ""
									}
									{this.props.filter.receptacleSerialNumber && this.props.filter.receptacleSerialNumber.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.rsn" /></label>
											<div className="form-control-data"> {this.props.filter.receptacleSerialNumber}</div>
										</Col> : ""
									}
									{this.props.filter.operationalStatus && this.props.filter.operationalStatus.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.operationDetails" /></label>
											<div className="form-control-data"> {this.props.filter.operationalStatus}</div>
										</Col> : ""
									}
									{this.props.filter.scanPort && this.props.filter.scanPort.length > 0 ?
										<Col xs="3">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.port" /></label>
											<div className="form-control-data"> {this.props.filter.scanPort}</div>
										</Col> : ""
									}
									{this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.formDate" /></label>
											<div className="form-control-data"> {this.props.filter.fromDate}</div>
										</Col> : ""
									}
									{this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.toDate" /></label>
											<div className="form-control-data"> {this.props.filter.toDate}</div>
										</Col> : ""
									}
									{this.props.filter.userID && this.props.filter.userID.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.userid" /></label>
											<div className="form-control-data"> {this.props.filter.userID}</div>
										</Col> : ""
									}
									{this.props.filter.damageFlag && this.props.filter.damageFlag.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.damaged" /></label>
											<div className="form-control-data"> {this.props.filter.damageFlag}</div>
										</Col> : ""
									}
									{this.props.filter.transitFlag && this.props.filter.transitFlag.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">Transit</label>
											<div className="form-control-data"> {this.props.filter.transitFlag}</div>
										</Col> : ""
									}
									{this.props.filter.rdtDate && this.props.filter.rdtDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ReqDlvDate" /></label>
											<div className="form-control-data"> {this.props.filter.rdtDate}</div>
										</Col> : ""
									}
									{this.props.filter.containerNo && this.props.filter.containerNo.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.contNo" /></label>
											<div className="form-control-data"> {this.props.filter.containerNo}</div>
										</Col> : ""
									}
									{this.props.filter.flightnumber && this.props.filter.flightnumber.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.fltNo" /></label>
											<div className="form-control-data"> {this.props.filter.flightnumber}</div>
										</Col> : ""
									}
									{this.props.filter.consignmentNo && this.props.filter.consignmentNo.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">Consignment No</label>
											<div className="form-control-data"> {this.props.filter.consignmentNo}</div>
										</Col> : ""
									}
									{this.props.filter.upuCode && this.props.filter.upuCode.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.upucode" /></label>
											<div className="form-control-data"> {this.props.filter.upuCode}</div>
										</Col> : ""
									}
									{this.props.filter.carditStatus && this.props.filter.carditStatus.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.tooltip.carditType" /></label>
											<div className="form-control-data"> {this.props.filter.carditStatus}</div>
										</Col> : ""
									}
									{this.props.filter.serviceLevel && this.props.filter.serviceLevel.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">Service Level</label>
											<div className="form-control-data"> {this.props.filter.serviceLevel}</div>
										</Col> : ""
									}  
                                    {this.props.filter.onTimeDelivery && this.props.filter.onTimeDelivery.length > 0 ?
										<Col xs="4">
											<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ontimedelivery" /></label>
											<div className="form-control-data"> {this.props.filter.onTimeDelivery}</div>
										</Col> : ""
									}
									 {this.props.filter.transportServWindowDate && this.props.filter.transportServWindowDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">TSW</label>
											<div className="form-control-data">{this.props.filter.transportServWindowDate} {this.props.filter.transportServWindowTime ? this.props.filter.transportServWindowTime : ''}</div>
										</Col> : ""
									}

									
								</Row>
							</div>
							<i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
							{this.props.popoverCount > 0 ?
                            <div className="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> 
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.operationalStatus && this.props.popOver.operationalStatus.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.operationDetails" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.operationalStatus}</div>
                                                </div> : null
											}
											{this.props.popOver.scanPort && this.props.popOver.scanPort.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.port" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.scanPort}</div>
                                                </div> : null
											}
											{this.props.popOver.carditStatus && this.props.popOver.carditStatus.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.tooltip.carditType" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.carditStatus}</div>
                                                </div> : null
											}
											{this.props.popOver.fromDate && this.props.popOver.fromDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.formDate" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.fromDate}</div>
                                                </div> : null
											}
											{this.props.popOver.toDate && this.props.popOver.toDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.toDate" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.toDate}</div>
                                                </div> : null
											}
											{this.props.popOver.rdtDate && this.props.popOver.rdtDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ReqDlvDate" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.rdtDate}</div>
                                                </div> : null
											}
											{this.props.popOver.containerNo && this.props.popOver.containerNo.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.contNo" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.containerNo}</div>
                                                </div> : null
											}
											{this.props.popOver.flightnumber && this.props.popOver.flightnumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.fltNo" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.flightnumber}</div>
                                                </div> : null
											}
											{this.props.popOver.consignmentNo && this.props.popOver.consignmentNo.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.consigmentnumber" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.consignmentNo}</div>
                                                </div> : null
											}
											{this.props.popOver.transitFlag && this.props.popOver.transitFlag.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label ">Transit</label>
													<div className="form-control-data"> {this.props.popOver.transitFlag}</div>
                                                </div> : null
											}
											{this.props.popOver.year && this.props.popOver.year.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.yr" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.year}</div>
                                                </div> : null
											}
											{this.props.popOver.receptacleSerialNumber && this.props.popOver.receptacleSerialNumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.rsn" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.receptacleSerialNumber}</div>
                                                </div> : null
											}
											{this.props.popOver.userID && this.props.popOver.userID.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.userid" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.userID}</div>
                                                </div> : null
											}
											{this.props.popOver.upuCode && this.props.popOver.upuCode.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.upucode" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.upuCode}</div>
                                                </div> : null
											}
											{this.props.popOver.damageFlag && this.props.popOver.damageFlag.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.damaged" /></label>
													<div className="form-control-data"> {this.props.popOver.damageFlag}</div>
                                                </div> : null
											}
											{this.props.popOver.serviceLevel && this.props.popOver.serviceLevel.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label ">Service Level</label>
													<div className="form-control-data"> {this.props.popOver.serviceLevel}</div>
                                                </div> : null
											}   
                                            {this.props.popOver.onTimeDelivery && this.props.popOver.onTimeDelivery.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.ontimedelivery" /></label>
													<div className="form-control-data"> {this.props.popOver.onTimeDelivery}</div>
                                                </div> : null
											}
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                            </div>
                            : ""}
						</div>
					)
				}
			</Fragment>
		)
	}
}


export default wrapForm('mailbagFilter')(FilterPanel);