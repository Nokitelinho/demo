import React from 'react';
import { Row, Col } from "reactstrap";
import { ITextField,ISelect,IMessage,IButton } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';

const Aux =(props) =>props.children;
class MailFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.toggleMailFilter = this.toggleMailFilter.bind(this);
        this.onClearMailDetails = this.onClearMailDetails.bind(this);
        this.onListMailDetails = this.onListMailDetails.bind(this);
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
        

    }
    onClearMailDetails () {
        this.props.onClearMailDetails();
    }
    toggleMailFilter(){
        this.props.toggleMailFilter((this.props.screenMode === 'edit')?'display':'edit');
    }
    onListMailDetails() {
        let isValid = true;
        let error = "" ;
        let form = this.props.offloadMailFilterform;
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
        this.props.onListMailDetails();
    }
    showPopover() {
        this.props.showPopover();
    }
    closePopover() {
        this.props.closePopover();
    }
    render() {


        let containerType = [];
        let mailCategoryCode = [];
        let selectedContainerType="";
        let selectedContainerTypeLabel="";
        let selectedMailCategoryCode ='';
        let selectedMailCategoryCodeLabel ='';
        if (!isEmpty(this.props.oneTimeValues)) {
            containerType = this.props.oneTimeValues['mailtracking.defaults.containertype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            containerType.push({value:'ALL',label:'All'});
            mailCategoryCode = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
       // if (this.props.screenMode === 'display') {
            if (this.props.filterValues.containerType && this.props.filterValues.containerType.length > 0 && this.props.filterValues.containerType!== 'ALL' ) {
                selectedContainerType = containerType.find((element) => {return  element.value === this.props.filterValues.containerType});
                selectedContainerTypeLabel = selectedContainerType.label;  
            }
            else
            selectedContainerTypeLabel='All';
           
            if (this.props.filterValues.mailCategoryCode && this.props.filterValues.mailCategoryCode.length > 0) {
                selectedMailCategoryCode = mailCategoryCode.find((element) => {return  element.value === this.props.filterValues.mailCategoryCode});
                selectedMailCategoryCodeLabel = selectedMailCategoryCode.label;
            }
           
      // }
        return (<Aux>
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                
                <div className="header-filter-panel flippane">
                <div className="pad-md pad-b-3xs">
                        <Row>
                            <div className="col-9 col-md-6">
                                <div className="form-group ">
                                   <IFlightNumber mode="edit" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_FLIGHTNO" uppercase={true}></IFlightNumber>
                                </div>
                            </div>


                         	<div className="col-3 col-md-2">
                                <div className="form-group ">
                                    <label className="form-control-label ">Uplift Airport</label>
                                    <ITextField mode="edit" maxlength="3" disabled={true}  componentId="CMP_MAIL_OPERATIONS_OFFLOAD_UPLIFTAIRPORT" name="upliftAirport" type="text" class="form-control"></ITextField>
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
                                    <label className="form-control-label ">Category</label>
                                    <ISelect defaultOption={true} name="mailCategoryCode" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CATEGORY" options={mailCategoryCode}  />
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

                         <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">Sub Class</label>
                                    <Lov name="mailSubclass" lovTitle="Subclass" uppercase={true} dialogWidth="600" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_SUBCLASS" dialogHeight="425" maxlength="2" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1"/>
                                </div>
                         </div>

                         <div className="col-2 col-md-1">
                                <div className="form-group">
                                    <label className="form-control-label ">Year</label>
                                    <ITextField mode="edit" maxlength="1" name="year" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_YEAR" type="text"  ></ITextField>
                                </div>
                         </div>

                           <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">DSN</label>
                                    <ITextField mode="edit" maxlength="4" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_DSN" name="dsn" type="text" ></ITextField>
                                </div>
                         </div>
                         <div className="col-7 col-md-5">
                                <div className="form-group">
                                    <label className="form-control-label ">Mailbag Id</label>
                                    <ITextField mode="edit" name="mailbagId" uppercase={true} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_MAILBAGID" type="text" ></ITextField>
                                </div>
                         </div>

                           <div className="col-3 col-md-2">
                                <div className="form-group">
                                    <label className="form-control-label ">RSN</label>
                                    <ITextField mode="edit" maxlength="3" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_RSN" name="rsn" type="text"   ></ITextField>
                                 </div>
                            </div>

                         </Row>      
                     </div>
             

                     <div className="btn-row">
                                <IButton category="primary" className="btn btn-primary flipper"  componentId="CMP_MAIL_OPERATIONS_OFFLOAD_LIST" onClick={this.onListMailDetails}>List</IButton>
                                <IButton category="default" className="btn btn-default" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CLEAR" onClick={this.onClearMailDetails}>Clear</IButton>
                    </div>

                  {/*  {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleMailFilter}></i>}*/}
                </div>
            ) : (
                    <div className="header-summary-panel flippane"  >

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
                                 {this.props.filter.mailCategoryCode && this.props.filter.mailCategoryCode.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label">Category</label>
                                        <div className="form-control-data">{selectedMailCategoryCodeLabel}</div>
                                    </div> : ""
                                }
                                 {this.props.filter.ooe && this.props.filter.ooe.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">OOE</label>
                                        <div className="form-control-data">{this.props.filter.ooe}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.doe && this.props.filter.doe.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">DOE</label>
                                        <div className="form-control-data">{this.props.filter.doe}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.mailSubclass && this.props.filter.mailSubclass.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Sub Class</label>
                                        <div className="form-control-data">{this.props.filter.mailSubclass}</div>
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
                                
                                 {this.props.filter.mailbagId && this.props.filter.mailbagId.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Mailbag Id</label>
                                        <div className="form-control-data">{this.props.filter.mailbagId}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.rsn && this.props.filter.rsn.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">RSN</label>
                                        <div className="form-control-data">{this.props.filter.rsn}</div>
                                    </Col> : ""
                                }
                                
                                

                            </Row>
                        </div>

                        {/* <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleMailFilter}></i>*/}
                         

                         {this.props.popoverCount > 0 ?
                            <div className="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> >
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
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
                                            {this.props.popOver.mailSubclass && this.props.popOver.mailSubclass.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Sub Class :</label>
                                                    <div className="form-control-data">{this.props.popOver.mailSubclass}</div>
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
                                             {this.props.popOver.mailbagId && this.props.popOver.mailbagId.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mailbag Id  :</label>
                                                    <div className="form-control-data">{this.props.popOver.mailbagId}</div>
                                                </div> : null
                                            }
                                             {this.props.popOver.rsn && this.props.popOver.rsn.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">RSN :</label>
                                                    <div className="form-control-data">{this.props.popOver.rsn}</div>
                                                </div> : null
                                            }
                                          
                                           
                                        </div>
                                    </IPopoverBody>
                                </IPopover>
                            </div>
                            : ""}
                        

    


                    </div>
                )}
        </Aux>)
    }

} 
export default wrapForm('offloadMailFilter')(MailFilterPanel);