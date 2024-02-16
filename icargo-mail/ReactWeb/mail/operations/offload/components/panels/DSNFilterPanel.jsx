import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { ITextField,ISelect,IMessage,IButton } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';

const Aux =(props) =>props.children;
 class DSNFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.toggleDSNFilter = this.toggleDSNFilter.bind(this);
        this.onClearDSNDetails = this.onClearDSNDetails.bind(this);
        this.onListDSNDetails = this.onListDSNDetails.bind(this);
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);


    }
    onListDSNDetails() {
        let isValid = true;
        let error = "" ;
        let form = this.props.offloadDSNFilterform;
       const carriercode= form.values && form.values.flightnumber? form.values.flightnumber.carrierCode:null;
       const flightNumber= form.values && form.values.flightnumber? form.values.flightnumber.flightNumber:null;
       const flightDate= form.values && form.values.flightnumber? form.values.flightnumber.flightDate:null;
        if (carriercode == null || flightNumber == null || flightDate == null) {
            isValid = false;
                this.props.displayError(' Flight details cannot be blank')
        }

        if (!isValid) {
            dispatch(requestValidationError(error, ''));
            }
        this.props.onListDSNDetails();
    }
    onClearDSNDetails () {
        this.props.onClearDSNDetails();
    }
    toggleDSNFilter(){
        this.props.toggleDSNFilter((this.props.screenMode === 'edit')?'display':'edit');
    }
    showPopover() {
        this.props.showPopover();
    }
    closePopover() {
        this.props.closePopover();
    }
    render() {

        let containerType = [];
        let mailClass =[] ;
        let selectedContainerType='';
        let selectedContainerTypeLabel ='';
        let selectedMailClass='';
        let selectedMailClassLabel='';
        if (!isEmpty(this.props.oneTimeValues)) {
            containerType = this.props.oneTimeValues['mailtracking.defaults.containertype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            containerType.push({value:'ALL',label:'All'});
            mailClass = this.props.oneTimeValues['mailtracking.defaults.mailclass'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
       // if (this.props.screenMode === 'display') {
            if (this.props.filterValues.containerType && this.props.filterValues.containerType.length > 0 && this.props.filterValues.containerType!== 'ALL' ) {
                selectedContainerType = containerType.find((element) => {return  element.value === this.props.filterValues.containerType});
                selectedContainerTypeLabel = selectedContainerType.label;  
            }
            else
            selectedContainerTypeLabel='All';
            if (this.props.filterValues.mailClass && this.props.filterValues.mailClass.length > 0) {
                selectedMailClass = mailClass.find((element) => {return  element.value === this.props.filterValues.mailClass});
                selectedMailClassLabel = selectedMailClass.label; 
           // } 
    
        }
        return (<Aux>
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
            
                   <div className="header-filter-panel flippane">
                     <div className="pad-md pad-b-3xs">
                        <Row>
                            <div className="col-9 col-md-6">
                                <div className="form-group ">
                                   <IFlightNumber mode="edit" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_FLIGHTNO" uppercase={true}></IFlightNumber>
                                </div>
                            </div>
                            <div className="col-3 col-md-2">
                                <div className="form-group ">
                                    <label className="form-control-label ">Uplift Airport</label>
                                    <ITextField mode="edit" maxlength="3" disabled={true} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_UPLIFTAIRPORT" name="upliftAirport" type="text" class="form-control"></ITextField>
                                </div>
                            </div>

                             <div className="col-4 col-md-3">
                                <div className="form-group ">
                                    <label className="form-control-label ">Container Type</label>
                                    <ISelect name="containerType" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CONTAINERTYPE"  options={containerType}  />
                                </div>
                            </div>
                           
                            <div className="col-4 col-md-3">
                                <div className="form-group ">
                                    <label className="form-control-label ">Container No</label>
                                    <ITextField mode="edit" name="containerNo" uppercase={true} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CONTAINERNO" type="text" class="form-control"></ITextField>
                                </div>
                                </div>
                             <div className="col-4 col-md-3">
                                <div className="form-group ">
                                    <label className="form-control-label ">Mail Class</label>
                                    <ISelect defaultOption={true} name="mailClass" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_MAILCLASS" options={mailClass}  />
                                </div>
                            </div>
                            <div className="col-2 col-md-1">
                                <div className="form-group">
                                    <label className="form-control-label ">Year</label>
                                    <ITextField mode="edit" maxlength="1" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_YEAR"  name="year" type="text"  ></ITextField>
                                </div>
                            </div>
            
                            <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">DSN</label>
                                    <ITextField mode="edit" maxlength="4" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_DSN" name="dsn" type="text" ></ITextField>
                                </div>
                            </div>
                            <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">OOE</label>
                                    <Lov name="ooe" lovTitle="Origin OE" uppercase={true} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_OOE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"  />
                                </div>
                             </div>

                             <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">DOE</label>
                                    <Lov name="doe" lovTitle="Destination OE" uppercase={true} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_DOE" dialogWidth="600" dialogHeight="473" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" />
                                </div>
                            </div>
                        </Row>   
                        </div>
                       
                  
                    <div className="btn-row">
                                <IButton category="primary" className="btn btn-primary flipper" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_LIST" onClick={this.onListDSNDetails} >List</IButton>
                                <IButton category="default" className="btn btn-default" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CLEAR"  onClick={this.onClearDSNDetails}>Clear</IButton>
                    </div>

                   {/* {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleDSNFilter}></i>}*/}
                </div>
            ) : (
                    <div className="header-summary-panel flippane" >

                        <div className="pad-md">
                            <Row>

                                {this.props.filterValues.flightnumber ?
                                   	<div className="col-4">
                                       <label className="form-control-label">Flight Number</label>
                                        <div className="form-control-data">{this.props.filterValues.flightnumber.carrierCode}{this.props.filterValues.flightnumber.flightNumber}</div>
                                    </div> : ""
                                    
                                }
                                 {this.props.filterValues.flightnumber ?
                                   	<div className="col-4">
                                       <label className="form-control-label">Flight Date</label>
                                        <div className="form-control-data">{this.props.filterValues.flightnumber.flightDate}</div>
                                    </div> : ""
                                    
                                } 
                               
                                {this.props.filter.upliftAirport && this.props.filter.upliftAirport.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Uplift Airport</label>
                                        <div className="form-control-data">{this.props.filter.upliftAirport}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.containerType && this.props.filter.containerType.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.offload.lbl.containertype" /></label>
                                        <div className="form-control-data" >{selectedContainerTypeLabel}</div>
                                    </div> : ""
                                }
                                 {this.props.filter.containerNo && this.props.filter.containerNo.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.offload.lbl.containerno" /></label>
                                        <div className="form-control-data">{this.props.filter.containerNo}</div>
                                    </div> : ""
                                }
                                {this.props.filter.mailClass && this.props.filter.mailClass.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label">Mail Class</label>
                                        <div className="form-control-data" >{selectedMailClassLabel}</div>
                                    </div> : ""
                                }
                                {this.props.filter.year && this.props.filter.year.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Year</label>
                                        <div className="form-control-data">{this.props.filter.year}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.ooe && this.props.filter.ooe.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">OOE</label>
                                        <div className="form-control-data">{this.props.filter.ooe}</div>
                                    </Col> : ""
                                }
                                 
                                 {this.props.filter.dsn && this.props.filter.dsn.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">DSN</label>
                                        <div className="form-control-data">{this.props.filter.dsn}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.doe && this.props.filter.doe.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">DOE</label>
                                        <div className="form-control-data">{this.props.filter.doe}</div>
                                    </Col> : ""
                                }
                                
                                 

                            </Row>
                        </div>

                      {/*   <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleDSNFilter}></i>*/}
                        {this.props.popoverCount > 0 ?
                            <div className="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> >
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.mailClass && this.props.popOver.destmailClassination.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mail Class :</label>
                                                    <div className="form-control-data">{this.props.popOver.mailClass}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.year && this.props.popOver.year.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Year :</label>
                                                    <div className="form-control-data">{this.props.popOver.year}</div>
                                                </div> : null
                                            }
                                             {this.props.popOver.dsn && this.props.popOver.dsn.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">DSN :</label>
                                                    <div className="form-control-data">{this.props.popOver.dsn}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.ooe && this.props.popOver.ooe.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">OOE :</label>
                                                    <div className="form-control-data">{this.props.popOver.ooe}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.doe && this.props.popOver.doe.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">DOE :</label>
                                                    <div className="form-control-data">{this.props.popOver.doe}</div>
                                                </div> : null
                                            }
                                           
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                            </div>
                            : ""}
                        


                        {/* <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleDSNFilter}></i>*/}
                    </div>
                )}
        </Aux>)
    }

} 
export default wrapForm('offloadDSNFilter')(DSNFilterPanel);