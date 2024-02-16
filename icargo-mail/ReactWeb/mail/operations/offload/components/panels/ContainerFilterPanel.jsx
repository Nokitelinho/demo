import React from 'react';
import { Row, Col } from "reactstrap";
import { ITextField,ISelect,IMessage ,IButton} from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';


const Aux = (props) => props.children;
 class ContainerFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.toggleFilter = this.toggleFilter.bind(this);
        this.onClearDetails = this.onClearDetails.bind(this);
        this.onListDetails = this.onListDetails.bind(this);
    }
    onListDetails(){

        let isValid = true;
        let error = "" ;
        let form = this.props.offloadfilterform;
       const carriercode= form.values && form.values.flightnumber? form.values.flightnumber.carrierCode:null;
       const flightNumber= form.values && form.values.flightnumber? form.values.flightnumber.flightNumber:null;
       const flightDate= form.values && form.values.flightnumber? form.values.flightnumber.flightDate:null;
        let type = 'U';
        let data ={type:type};
        if (carriercode == null || flightNumber == null || flightDate == null) {
            isValid = false;
                this.props.displayError(' Flight details cannot be blank')
        }

        if (!isValid) {
            dispatch(requestValidationError(error, ''));
            }
        this.props.onListDetails(data);
    }
    onClearDetails () {
        this.props.onClearDetails();
    }
    toggleFilter(){
        this.props.toggleFilter((this.props.screenMode === 'edit')?'display':'edit');
    }

    render() {


        let containerType = [];
        let selectedContainerType="";
        let selectedContainerTypeLabel="";
        if (!isEmpty(this.props.oneTimeValues)) {
            containerType = this.props.oneTimeValues['mailtracking.defaults.containertype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            containerType.push({value:'ALL',label:'All'});
        }

      //  if (this.props.screenMode === 'display') {
            if (this.props.filterValues.containerType && this.props.filterValues.containerType.length > 0) {
                selectedContainerType = containerType.find((element) => {return  element.value === this.props.filterValues.containerType});
                selectedContainerTypeLabel = selectedContainerType.label;
            }
            else
            selectedContainerTypeLabel='All';
       // }
        return (<Aux>
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
              
                     <div className="header-filter-panel flippane">
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <div className="col-9 col-md-6">
                                <div className="form-group  ">
                                   <IFlightNumber  flightnumber={this.props.filterValues ? this.props.filterValues.flightNumber : ''}  mode="edit" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_FLIGHTNO" uppercase={true}></IFlightNumber>
                                </div>
                             </div>


                         	<div className="col-3 col-md-2">
                                <div className="form-group ">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.offload.lbl.upliftAirport" /></label>
                                    <ITextField mode="edit" name="upliftAirport" disabled={true} maxlength="3" componentId="CMP_MAIL_OPERATIONS_OFFLOAD_UPLIFTAIRPORT" type="text" class="form-control"></ITextField>
                                </div>
                            </div>

                            <div className="col-4 col-md-3">
                                <div className="form-group ">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.offload.lbl.containertype" /></label>
                                    <ISelect name="containerType"  options={containerType}  componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CONTAINERTYPE"/>
                                </div>
                            </div>

                            <div className="col-4 col-md-3">
                                <div className="form-group ">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.offload.lbl.containerno" /></label>
                                    <ITextField mode="edit" name="containerNo" uppercase={true} type="text"  componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CONTAINERNO" class="form-control"></ITextField>
                                </div>
                            </div>

                        </Row>  
                    </div>
                             <div className="btn-row">
                                <IButton category="primary" className="btn btn-primary flipper" onClick={this.onListDetails} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_LIST"><IMessage msgkey="mail.operations.offload.lbl.list" /></IButton>
                                <IButton className="btn btn-default" onClick={this.onClearDetails} componentId="CMP_MAIL_OPERATIONS_OFFLOAD_CLEAR" ><IMessage msgkey="mail.operations.offload.lbl.clear" /></IButton>
                            </div>

                   {/* {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}*/}
                </div>
            ) : (
                    <div className ="header-summary-panel flippane">

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
                                
                                {this.props.filterValues.upliftAirport && this.props.filterValues.upliftAirport.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.offload.lbl.upliftAirport" /></label>
                                        <div className="form-control-data">{this.props.filterValues.upliftAirport}</div>
                                    </div> : ""
                                }
                                   {this.props.filterValues.containerType && this.props.filterValues.containerType.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.offload.lbl.containertype" /></label>
                                        <div className="form-control-data">{selectedContainerTypeLabel}</div>
                                    </div> : ""
                                }
                                  {this.props.filterValues.containerNo && this.props.filterValues.containerNo.length > 0 ?
                                   	<div className="col-4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.offload.lbl.containerno" /></label>
                                        <div className="form-control-data">{this.props.filterValues.containerNo}</div>
                                    </div> : ""
                                }
                                

                            </Row>
                        </div>


                     {/*  <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleFilter}></i>*/}
                    </div>
                )}
        </Aux>)
    }

} 
export default wrapForm('offloadFilter')(ContainerFilterPanel);