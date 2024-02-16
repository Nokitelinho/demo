import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import {IMessage} from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
const Aux =(props) =>props.children;
class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.dateRange='InvoiceEnquiryDateRange'
         this.onclearInvoicDetails = this.onclearInvoicDetails.bind(this);
         this.onlistInvoicDetails = this.onlistInvoicDetails.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);

    }
    
    onlistInvoicDetails() {
        let isValid = true;
        let error = "" ;
       const gpaCode=this.props.gpaCode;
       const fromDate=this.props.fromDate;
       const toDate = this.props.toDate;
        const invoicId = this.props.invoicId;
        if (invoicId == null) {
        if (gpaCode == null || fromDate == null || toDate == null ) {
            isValid = false;
                this.props.displayError('Mandatory fields cannot be blank')
        }
        /*  if (fromDate == null) {
            isValid = false;
            this.props.displayError(' Please enter From Date ') 
        }
          if (toDate == null) {
            isValid = false;
            this.props.displayError(' Please enter To Date ') 
        }
            if (gpaCode == null) {
               isValid = false;
               this.props.displayError(' Please enter Gpa Code ') 
           }*/
        }
        if (!isValid) {
        dispatch(requestValidationError(error, ''));
        }
        else
        this.props.onlistInvoicDetails();
    } 
    onclearInvoicDetails() {
        this.props.onclearInvoicDetails();
    }
    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }
    
    render() {

        return (<Aux>
       
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                <div className="header-panel">
                    <div className="flippane animated fadeInDown">
                    <div className="pad-x-md pad-t-md">
                        <Row>
                            <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label " ><IMessage msgkey="mail.mra.invoicenquiry.lbl.gpacode" /></label>
                                <Lov name="gpaCode" uppercase={true} lovTitle="GPA Code" maxlength="5" dialogWidth="600" dialogHeight="453" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_MRA_INVOICENQUIRY_PACODE" />
                              </div>
                        </div>

                            <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.mra.invoicenquiry.lbl.invoicId" /></label>
                                <Lov name="invoicId" uppercase={true} lovTitle="Invoic ID" dialogWidth="600" dialogHeight="500" actionUrl="showNewInvoicLOV.do?formCount=1"  componentId="CMP_MAIL_MRA_INVOICENQUIRY_INVOICID" />
                            </div>
                          </div>

                            <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.fromDate" /></label>
                                <DatePicker name="fromDate" componentId="CMP_MAIL_MRA_INVOICENQUIRY_DATEFROM" dateFieldId={this.dateRange} type="from" toDateName="toDate"/>
                            </div>
                       </div>


                            <div className="col-3 col-sm-4 col-md-3">
                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.toDate" /></label>
                                <DatePicker name="toDate" componentId="CMP_MAIL_MRA_INVOICENQUIRY_DATETO" dateFieldId={this.dateRange} type="to" fromDateName="fromDate"/>
                            </div>
                       </div>


                            <div className="col-5 col-sm-6 col-md-5">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.mra.invoicenquiry.lbl.mailbagId" /></label>
                                <Lov name="mailbagId"  uppercase={true} lovTitle="Mail Bags" dialogWidth="750" dialogHeight="600" actionUrl="showNewDespatchLOV.do?formCount=1"  componentId="CMP_MAIL_MRA_INVOICENQUIRY_MAILBAGID"/>
                            </div>
                       </div> 
                       <div class="col pad-t-md">
                        <IButton category="primary" bType="LIST" accesskey="L"  componentId='CMP_MAIL_MRA_INVOICENQUIRY_LIST' onClick={this.onlistInvoicDetails}><IMessage msgkey="mail.mra.invoicenquiry.button.list" /></IButton>
                        <IButton category="default" bType="CLEAR" accesskey="C" componentId='CMP_MAIL_MRA_INVOICENQUIRY_CLEAR' onClick={this.onclearInvoicDetails}><IMessage msgkey="mail.mra.invoicenquiry.button.clear" /></IButton>
                </div>
                        </Row>
                </div>
            
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                    </div>
                </div>
            ) : (
                    <div className="header-panel" id="headerData" >
                        <div className="flippane animated fadeInDown">
                        <div className="pad-md">
                            <Row>

                                {this.props.filterValues.gpaCode && this.props.filterValues.gpaCode.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.gpacode" /></label>
                                        <div className="form-control-data">{this.props.filterValues.gpaCode}</div>
                                    </Col> : ""
                                }
                                {this.props.filterValues.invoicId && this.props.filterValues.invoicId.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.invoicId" /></label>
                                        <div className="form-control-data">{this.props.filterValues.invoicId}</div>
                                    </Col> : ""
                                }
                                {this.props.filterValues.fromDate && this.props.filterValues.fromDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.fromDate" /></label>
                                        <div className="form-control-data">{this.props.filterValues.fromDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filterValues.toDate && this.props.filterValues.toDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.mra.invoicenquiry.lbl.toDate" /></label>
                                        <div className="form-control-data">{this.props.filterValues.toDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filterValues.mailbagId && this.props.filterValues.mailbagId.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label "><IMessage msgkey="mail.mra.invoicenquiry.lbl.mailbagId" /></label>
                                        <div className="form-control-data"> {this.props.filterValues.mailbagId}</div>
                                    </Col> : ""
                                }


                            </Row>
                        </div>

                        <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
                        </div>
                    </div>
                )}

        </Aux>)
    }
}


export default wrapForm('invoicFilter')(FilterPanel);