import React from 'react';
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
const Aux =(props) =>props.children;

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.dateRange='ListBillingGPADateRange'
        this.onclearBillingDetails = this.onclearBillingDetails.bind(this);
        this.onlistBillingDetails = this.onlistBillingDetails.bind(this);  
        this.toggleFilter = this.toggleFilter.bind(this);  
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);    
      
    }
    onlistBillingDetails() {
        let isValid = true;
        let error = "";
        const fromDate = this.props.fromDate;
        const toDate = this.props.toDate;
        if (fromDate == null || toDate == null) {
             isValid = false;
            this.props.displayError('From Date and To Date Mandatory')
         }
        if (!isValid) {
            dispatch(requestValidationError(error, ''));
        }
        else{
            this.props.onlistBillingDetails();
        }
    }
    onclearBillingDetails() {
        this.props.onclearBillingDetails();
    }
    toggleFilter() {
        this.props.onToggleFilter((this.props.screenFilterMode === 'edit') ? 'display' : 'edit');
    }
    showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }

    render() {

        let category=[];
        let status=[];
        let uspsPerformance=[];
        let rateBasis=[];
        let selectedStatus="";
        let selectedStatusLabel="";

        if (!isEmpty(this.props.oneTimeValues)) {
            category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            category.push({value:'ALL',label:'All'});
            status =this.props.oneTimeValues['mailtracking.mra.gpabilling.gpabillingstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            uspsPerformance =this.props.oneTimeValues['mailtracking.mra.gpabilling.uspsperformed'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            uspsPerformance.push({value:'ALL',label:'All'});
            rateBasis =this.props.oneTimeValues['mailtracking.mra.gpabilling.ratebasis'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            rateBasis.push({value:'ALL',label:'All'});
        }

        if (this.props.filterValues.billingStatus && this.props.filterValues.billingStatus.length > 0 ) {
            selectedStatus = status.find((element) => {return  element.value === this.props.filterValues.billingStatus});
            selectedStatusLabel = selectedStatus.label;  
        }
        

        return ( <Aux>  
         {(this.props.screenFilterMode === 'edit' || this.props.screenMode === 'initial') ? (
        <div className="header-panel">         
        <div className="flippane animated fadeInDown" id="headerForm">
             <div className="pad-md pad-b-3xs"> 
                <Row>
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group mandatory">
                        <label className="form-control-label mandatory_label">From Date</label>
                        <DatePicker name="fromDate"  componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_DATEFROM" dateFieldId={this.dateRange} type="from" toDateName="toDate"/>
                    </div>
                </div>
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group mandatory">
                        <label className="form-control-label mandatory_label">To Date</label>
                        <DatePicker name="toDate" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_DATETO" dateFieldId={this.dateRange} type="to" fromDateName="fromDate" />
                    </div>
                </div> 
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">Con Doc No:</label>
                        <ITextField name="consignmentNumber" uppercase={true} type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_CONDOCNO"></ITextField>
                    </div>
                </div>
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label ">Billing Status</label>
                        <ISelect  defaultOption={true} name="billingStatus" options={status} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_BILLINGSTATUS"/>
                    </div>
                </div>
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label " >GPA Code</label>
                        <Lov name="gpaCode" lovTitle="GPA Code" uppercase={true} maxlength="5" dialogWidth="600"  dialogHeight="453" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_PACODE" />
                    </div>
                </div>  
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">Org. OE</label>
                        <Lov name= "originOE" uppercase={true} lovTitle= "Origin OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_OOE"/>
                    </div>                                
                </div>  
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">Dest. OE</label>
                        <Lov name= "destinationOE" uppercase={true} lovTitle= "Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_DOE"/>
                    </div>                                
                </div>  
                <div className="col-3 col-sm-2 col-md-2">   
                    <div className="form-group">
                        <label className="form-control-label ">Origin</label>
                        <Lov name= "origin" uppercase={true} lovTitle= "Origin" dialogWidth="600" dialogHeight="425"  maxlength="3" actionUrl="ux.showCity.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_ORIGIN"/>
                    </div>                                
                </div> 
                <div className="col-3 col-sm-2 col-md-2">   
                    <div className="form-group">
                        <label className="form-control-label ">Destination</label>
                        <Lov name= "destination" uppercase={true} lovTitle= "Destination" dialogWidth="600" dialogHeight="425"  maxlength="3" actionUrl="ux.showCity.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_DESTINATION"/>
                    </div>                                
                </div>                                                     
                </Row>
                <Row>
                <div className="col-3 col-sm-2 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">Category</label>
                        <ISelect  name="category" options={category} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_CATEGORY"/>
                    </div>                                
                </div> 
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">Sub-Class</label>
                        <Lov name= "subClass" uppercase={true} lovTitle= "Subclass" dialogWidth="600" dialogHeight="425"  maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_SUBCLASS"/>
                    </div>                                
                </div>                     
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label ">Year</label>
                        <ITextField name="year" type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_YEAR" maxlength="1" ></ITextField>
                    </div>                                
                </div>
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label ">DSN No:</label>
                        <ITextField name="dsn" type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_DSN" maxlength="4"></ITextField>
                    </div>                                
                </div>
                <div className="col-3 col-sm-2 col-md-1">   
                    <div className="form-group">
                        <label className="form-control-label ">RSN</label>
                        <ITextField name="rsn" type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_RSN" maxlength="3" ></ITextField>
                    </div>                                
                </div> 
                <div className="col-3 col-sm-2 col-md-1">   
                    <div className="form-group">
                        <label className="form-control-label ">HNI</label>
                        <ITextField name="hni" type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_HNI" maxlength="3" ></ITextField>
                    </div>                                
                </div>
                <div className="col-3 col-sm-2 col-md-1">   
                    <div className="form-group">
                        <label className="form-control-label ">RI</label>
                        <ITextField name="ri" type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_RI" maxlength="3" ></ITextField>
                    </div>                                
                </div> 
                <div className="col-3 col-sm-4 col-md-5">   
                    <div className="form-group">
                        <label className="form-control-label ">Mailbag ID</label>
                        <ITextField name="mailbag" uppercase={true} type="text" componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_MAILBAGID"></ITextField>
                    </div>                                
                </div>                                 
                <div className="col-3 col-sm-3 col-md-3">
                    <div className="form-group">
                        <label className="form-control-label ">USPS Perf. met.</label>
                        <ISelect name="uspsMailPerformance" options={uspsPerformance} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_USPSPERFORMANCE"/>
                    </div>                                 
                </div>   
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label ">Rate Basis</label>
                        <ISelect name="rateBasis" options={rateBasis} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_RATEBASIS"/>
                    </div>                                 
                </div>                     
                </Row>
                <Row>
                <div className="col-3 col-sm-2 col-md-2">
                    <div className="form-group">
                        <label className="form-control-label ">PA Built</label>
                        <ISelect  defaultOption={true}
    name="paBuilt" options={[{ label: "Yes", value: "Y" }, { label: "No", value: "N" }]} componentId="CMP_MAIL_MRA_GPABILLINGENQUIRY_RATEBASIS"/>
                    </div>                                 
                </div>   
                    </Row>
            </div>
            <div className="btn-row">
                <IButton category="primary" bType="LIST" accesskey="L" componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_LIST' onClick={this.onlistBillingDetails}>List</IButton>
                <IButton category="default" bType="CLEAR" accesskey="C" componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_CLEAR' onClick={this.onclearBillingDetails}>Clear</IButton>
            </div> 
            {(this.props.screenFilterMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}                   
        </div>
         
        </div>
         ) : (
        <div className="header-panel">  
        <div className="flippane animated fadeInDown" id="headerForm">
             <div className="pad-md">
            <Row>
                {this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">From Date*</label>
                        <div className="form-control-data">{this.props.filter.fromDate}</div>
                    </Col> : ""
                }  
                {this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">To Date*</label>
                        <div className="form-control-data">{this.props.filter.toDate}</div>
                    </Col> : ""
                }    
                {this.props.filter.consignmentNumber && this.props.filter.consignmentNumber.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Con Doc No:</label>
                        <div className="form-control-data">{this.props.filter.consignmentNumber}</div>
                    </Col> : ""
                }    
                {this.props.filter.billingStatus && this.props.filter.billingStatus.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Billing Status</label>
                        <div className="form-control-data">{selectedStatusLabel}</div>
                    </Col> : ""
                } 
               {this.props.filter.gpaCode && this.props.filter.gpaCode.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">GPA Code</label>
                        <div className="form-control-data">{this.props.filter.gpaCode}</div>
                    </Col> : ""
                }       
               {this.props.filter.originOE && this.props.filter.originOE.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Org. OE</label>
                        <div className="form-control-data">{this.props.filter.originOE}</div>
                    </Col> : ""
                }     
               {this.props.filter.destinationOE && this.props.filter.destinationOE.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Dest. OE</label>
                        <div className="form-control-data">{this.props.filter.destinationOE}</div>
                    </Col> : ""
                }    
               {this.props.filter.origin && this.props.filter.origin.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Origin</label>
                        <div className="form-control-data">{this.props.filter.origin}</div>
                    </Col> : ""
                }    
                {this.props.filter.destination && this.props.filter.destination.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Destination</label>
                        <div className="form-control-data">{this.props.filter.destination}</div>
                    </Col> : ""
                } 
             {this.props.filter.category && this.props.filter.category.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Category</label>
                        <div className="form-control-data">{this.props.filter.category}</div>
                    </Col> : ""
                }   
             {this.props.filter.subClass && this.props.filter.subClass.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Sub-Class</label>
                        <div className="form-control-data">{this.props.filter.subClass}</div>
                    </Col> : ""
                }   
              {this.props.filter.year && this.props.filter.year.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Year</label>
                        <div className="form-control-data">{this.props.filter.year}</div>
                    </Col> : ""
                }
                 {this.props.filter.dsn && this.props.filter.dsn.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">DSN</label>
                        <div className="form-control-data">{this.props.filter.dsn}</div>
                    </Col> : ""
                }
                {this.props.filter.rsn && this.props.filter.rsn.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">RSN</label>
                        <div className="form-control-data">{this.props.filter.rsn}</div>
                    </Col> : ""
                }
                {this.props.filter.hni && this.props.filter.hni.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">HNI</label>
                        <div className="form-control-data">{this.props.filter.hni}</div>
                    </Col> : ""
                }
                {this.props.filter.ri && this.props.filter.ri.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">RI</label>
                        <div className="form-control-data">{this.props.filter.ri}</div>
                    </Col> : ""
                }
                {this.props.filter.mailbag && this.props.filter.mailbag.length > 0 ?
                    <Col xs="5">
                        <label className="form-control-label">Mailbag ID</label>
                        <div className="form-control-data">{this.props.filter.mailbag}</div>
                    </Col> : ""
                }
                {this.props.filter.uspsMailPerformance && this.props.filter.uspsMailPerformance.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">USPS Perf. met.</label>
                        <div className="form-control-data">{this.props.filter.uspsMailPerformance}</div>
                    </Col> : ""
                }
                {this.props.filter.rateBasis && this.props.filter.rateBasis.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">Rate Basis</label>
                        <div className="form-control-data">{this.props.filter.rateBasis}</div>
                    </Col> : ""
                }
                {this.props.filter.paBuilt && this.props.filter.paBuilt.length > 0 ?
                    <Col xs="4">
                        <label className="form-control-label">PABuilt</label>
                        <div className="form-control-data">{this.props.filter.paBuilt=="Y"?"Yes":"No"}</div>
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
                                            {this.props.popOver.destinationOE && this.props.popOver.destinationOE.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Dest. OE:</label>
                                                    <div className="form-control-data">{this.props.popOver.destinationOE}</div>
                                                </div> : null
											}
                                            {this.props.popOver.origin && this.props.popOver.origin.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Origin: </label>
                                                    <div className="form-control-data">{this.props.popOver.origin}</div>
                                                </div> : null
											}
                                            {this.props.popOver.destination && this.props.popOver.destination.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Destination: </label>
                                                    <div className="form-control-data">{this.props.popOver.destination}</div>
                                                </div> : null
											}
											{this.props.popOver.category && this.props.popOver.category.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Category: </label>
                                                    <div className="form-control-data">{this.props.popOver.category}</div>
                                                </div> : null
											}
											{this.props.popOver.subClass && this.props.popOver.subClass.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Sub Class: </label>
                                                    <div className="form-control-data">{this.props.popOver.subClass}</div>
                                                </div> : null
											}
											{this.props.popOver.year && this.props.popOver.year.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Year: </label>
                                                    <div className="form-control-data">{this.props.popOver.year}</div>
                                                </div> : null
											}
											{this.props.popOver.dsn && this.props.popOver.dsn.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">DSN: </label>
                                                    <div className="form-control-data">{this.props.popOver.dsn}</div>
                                                </div> : null
											}
											{this.props.popOver.rsn && this.props.popOver.rsn.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">RSN: </label>
                                                    <div className="form-control-data">{this.props.popOver.rsn}</div>
                                                </div> : null
											}
											{this.props.popOver.hni && this.props.popOver.hni.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">HNI: </label>
                                                    <div className="form-control-data">{this.props.popOver.hni}</div>
                                                </div> : null
											}
											{this.props.popOver.ri && this.props.popOver.ri.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">RI: </label>
                                                    <div className="form-control-data">{this.props.popOver.ri}</div>
                                                </div> : null
											}
											{this.props.popOver.mailbag && this.props.popOver.mailbag.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mailbag Id: </label>
                                                    <div className="form-control-data">{this.props.popOver.mailbag}</div>
                                                </div> : null
											}
											{this.props.popOver.uspsMailPerformance && this.props.popOver.uspsMailPerformance.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label ">USPS Perf. met.: </label>
													<div className="form-control-data"> {this.props.popOver.uspsMailPerformance}</div>
                                                </div> : null
											}
											{this.props.popOver.rateBasis && this.props.popOver.rateBasis.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label ">Rate Basis: </label>
													<div className="form-control-data"> {this.props.popOver.rateBasis}</div>
                                                </div> : null
											}
											 {this.props.popOver.paBuilt && this.props.popOver.paBuilt.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label ">PA Built: </label>
													<div className="form-control-data"> {this.props.popOver.paBuilt=='Y'?"Yes":"No"}</div>
                                                </div> : null
											} 
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                            </div>
                            : ""}

        </div> 
         </div>
          )}
        </Aux> )
    }
}


export default wrapForm('gpaBillingEntryFilter')(FilterPanel);