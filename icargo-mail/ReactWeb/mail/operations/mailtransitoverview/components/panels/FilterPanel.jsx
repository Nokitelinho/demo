import React, { Component, Fragment } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import Lov from 'icoreact/lib/ico/framework/component/common/lov/Lov';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Col, Row, Input, Label } from 'reactstrap';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
class FilterPanel extends React.Component {

    constructor(props) {
		super(props);
		this.onlistMailTransitOverview = this.onlistMailTransitOverview.bind(this);
        this.onclearMailTransitOverview=this.onclearMailTransitOverview.bind(this);   
        this.toggleFilter = this.toggleFilter.bind(this); 
        this.dateRange='MailTransitDateRange';
    }

    toggleFilter() {
		this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
	}
    onlistMailTransitOverview() {
		this.props.onlistMailTransitOverview();
	}
    onclearMailTransitOverview() {
		this.props.onclearMailTransitOverview();
	}


    render() {

        return (
            <Fragment>
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (

                <div className="header-filter-panel flippane">
						<div className="pad-md pad-b-3xs">
						<Row>
                                <Col xs="4" md="2">
									<div className="form-group">
										<label className="form-control-label custom-mandatory-label">Port</label>
                                        {/* <Lov name="port" uppercase={true} lovTitle="Port" dialogWidth="600"  dialogHeight="425" maxlength="2" /> */}
                                        <Lov name="airportCode"  uppercase={true} lovTitle="Port" dialogWidth="400" dialogHeight="325" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" mandatory={true} />
									</div>
                                </Col>
                               
                                    {/* rom mailinboundfilterpanel */}
                                    <Col xs="4" >
                                        
                                        <div className="form-group">

                                            <Label className="form-control-label custom-mandatory-label">From Date & Time</Label>
                                          
                                                <div className="form-row">
                                                    <Col xs="16">
                                                <DatePicker  name="fromDate"  dateFieldId={this.dateRange} type="from" toDateName="toDate" />
                                                    </Col>
                                            <Col xs="8"><TimePicker name="fromTime" /></Col>
                                                </div>
                                        </div>
                                    </Col>        
                                        
                                     

                                    <Col xs="4" >

                                        <div className="form-group">

                                            <Label className="form-control-label custom-mandatory-label">To Date & Time</Label>

                                                <div className="form-row">
                                                    <Col xs="16">
                                                        <DatePicker name="toDate" dateFieldId={this.dateRange} type="to" fromDateName="fromDate" />
                                                    </Col>
                                            <Col xs="8"><TimePicker name="toTime" /></Col>
                                                </div>

                                        </div>
                                    </Col> 


                                <Col xs="9" md="8">
                                    {/* <col xs='4'md='3' sm='5'> */}
                                <IFlightNumber  mode="edit" uppercase={true} ></IFlightNumber>
                                </Col>
                                   
                        </Row>
                                    </div>
                                    


                                    

<div className="btn-row">
                                    <IButton category="btn btn-primary flipper" onClick={this.onlistMailTransitOverview} >List</IButton>

                                    <IButton category="defualt" className="btn btn-default"s onClick={this.onclearMailTransitOverview}>Clear</IButton>
                                 
                                </div>
                                {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                                </div>
                               
                ):(
                    <div className="header-summary-panel flippane">
							<div className="pad-md">
								<Row>
                                {this.props.mailTransitFilter.flightNumber && this.props.mailTransitFilter.flightNumber.length > 0 ?
										<Col xs="2">
                                            
											<label className="form-control-label ">FlightNumber</label>
											<div className="form-control-data">{this.props.mailTransitFilter.flightnumber.carrierCode}-{this.props.mailTransitFilter.flightNumber}</div>
                                            {/* <Row>
                                            <div className="form-control-data"> {this.props.mailTransitFilter.flightnumber.carrierCode}</div>
                                            <div className="form-control-data"> {this.props.mailTransitFilter.flightnumber.flightDate}</div>
                                            </Row>                                           */}
                                            </Col> : ""
									}

                                {(this.props.mailTransitFilter.flightnumber && this.props.mailTransitFilter.flightnumber.flightDate) ?
										<Col xs="2">
                                            
											<label className="form-control-label ">FlightDate</label>
											<div className="form-control-data">{this.props.mailTransitFilter.flightnumber.flightDate}</div>
                                                                                      
                                            </Col> : ""
									}
									{this.props.mailTransitFilter.airportCode && this.props.mailTransitFilter.airportCode.length > 0 ?
										<Col xs="2">
											<label className="form-control-label ">PORT</label>
											<div className="form-control-data"> {this.props.mailTransitFilter.airportCode}</div>
                                            </Col> : ""
									}
                                    {this.props.mailTransitFilter.fromDate && this.props.mailTransitFilter.fromDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">From Date & Time</label>
											<div className="form-control-data"> {this.props.mailTransitFilter.fromDate},{this.props.mailTransitFilter.fromTime}</div>
                                            </Col> : ""
									}
                                    {this.props.mailTransitFilter.toDate && this.props.mailTransitFilter.toDate.length > 0 ?
										<Col xs="4">
											<label className="form-control-label ">To Date & Time</label>
											<div className="form-control-data"> {this.props.mailTransitFilter.toDate},{this.props.mailTransitFilter.toTime}</div>
                                            </Col> : ""
									}
                                    

                                </Row>
                                <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
                                </div>
                                </div>         
                )
                }
                            
            </Fragment>
        );
    }
}

{/* <div className="form-group">
										<label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailbagenquiry.lbl.dsn" /></label>
										<ITextField mode="edit" name="despatchSerialNumber" uppercase={true} type="text" componentId="CMP_Mail_Operations_MailBagEnquiry_Dsn"></ITextField>
									</div> */}






                                    export default wrapForm('mailTransitFilterForm')(FilterPanel)