import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { ISelect, IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class FilterPanel extends Component {
    constructor(props) {
        super(props);
        this.onclearMailbagDetails = this.onclearMailbagDetails.bind(this);
        this.onlistMailbagDetails = this.onlistMailbagDetails.bind(this);
        this.onToggleFilter = this.onToggleFilter.bind(this);
        this.dateRangeIdr = 'mailPerformanceDateRange'
    }

    onlistMailbagDetails() {
       
        this.props.onlistMailbagDetails();
    }

    onclearMailbagDetails() {
        this.props.onclearMailbagDetails();
    }
    onToggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }
    

    render() {

        let mailservicelevels = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            mailservicelevels = this.props.oneTimeValues['mail.operations.mailservicelevels'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        return (<div>
       
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
              

            <div className="header-filter-panel flippane animated fadeInDown position-relative" id="headerForm">
                <div className="pad-md pad-b-xs">

                        <Row>
                            

                        <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label"><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.fromDate" /></label>
                                <DatePicker name="fromDate" dateFieldId={this.dateRange} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_DATEFROM" dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate"/>
                            </div>
                       </div>


                            <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label"><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.toDate" /></label>
                                <DatePicker name="toDate" dateFieldId={this.dateRange} componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_DATETO" dateFieldId={this.dateRangeIdr} type="to" fromDateName="fromDate"/>
                            </div>
                       </div>

                       <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label" ><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.gpacode" /></label>
                                <Lov name="paCode" lovTitle="GPA Code" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_PACODE" uppercase={true}/>
                              </div>
                        </div>
                        <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label" ><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.station" /></label>
                                <Lov name="station" lovTitle="Station" maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl="ux.showStation.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_STATION" uppercase={true}/>
                              </div>
                        </div>
                        <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group">
                                    <label className="form-control-label " ><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.servicelevel" /></label>
                                 <ISelect defaultOption={true} name="serviceLevel"  options={mailservicelevels} />
                              </div>
                        </div>

                        </Row>
                </div>
                <div className="btn-row">
                        <IButton category="primary" onClick={this.onlistMailbagDetails}><IMessage msgkey="mail.operations.ux.mailperformancemonitor.button.list" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_LIST"/></IButton>
                        <IButton category="default" onClick={this.onclearMailbagDetails}><IMessage msgkey="mail.operations.ux.mailperformancemonitor.button.clear" componentId="CMP_MAIL_OPERATIONS_MAILPERFORMANCEMONITOR_CLEAR"/></IButton>
                </div>
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.onToggleFilter}></i>}
            </div>
            ) : (
            <div className="flippane position-relative" id="headerForm">
                <div className="pad-md pad-b-xs">
                            <Row>


                                {this.props.filterValues.fromDate && this.props.filterValues.fromDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.fromDate" /></label>
                                        <div className="form-control-data">{this.props.filterValues.fromDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filterValues.toDate && this.props.filterValues.toDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.toDate" /></label>
                                        <div className="form-control-data">{this.props.filterValues.toDate}</div>
                                    </Col> : ""
                                } 
                                
                                {this.props.filterValues.paCode && this.props.filterValues.paCode.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.mailperformancemonitor.lbl.gpacode" /></label>
                                        <div className="form-control-data">{this.props.filterValues.paCode}</div>
                                    </Col> : ""
                                }

                                {this.props.filterValues.station && this.props.filterValues.station.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.station" /></label>
                                        <div className="form-control-data">{this.props.filterValues.station}</div>
                                    </Col> : ""
                                } 
                                {this.props.filterValues.serviceLevel && this.props.filterValues.serviceLevel.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailperformancemonitor.lbl.servicelevel" /></label>
                                        <div className="form-control-data">{
                                            
                                            
                                                 this.props.oneTimeValues['mail.operations.mailservicelevels'].find((element) => {
                                                   return element.fieldValue==this.props.filterValues.serviceLevel
                                                   })? this.props.oneTimeValues['mail.operations.mailservicelevels'].find((element) => {
                                                   return element.fieldValue==this.props.filterValues.serviceLevel
                                                   }).fieldDescription:''
                                            
                                            }</div>
                                    </Col> : ""
                                } 

                            </Row>
                        </div>

                        <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.onToggleFilter}></i>
                    </div>
                )}

        </div>)
    }
}


export default wrapForm('mailPerformanceMonitorFilter')(FilterPanel);