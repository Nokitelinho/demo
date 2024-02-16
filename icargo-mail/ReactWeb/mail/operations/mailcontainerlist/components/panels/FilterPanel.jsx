import React, { Fragment } from 'react';
import {ISelect, ICheckbox,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';



class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistContainerDetails = this.onlistContainerDetails.bind(this);
        this.onclearContainerDetails = this.onclearContainerDetails.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);
      
    }
    onlistContainerDetails() {
        this.props.onlistContainerDetails();
    }
    
    onclearContainerDetails() {
        this.props.onclearContainerDetails();
    }
    
    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

render() {  

    let searchmode = [];
    if (!isEmpty(this.props.oneTimeValues)) {
        searchmode = this.props.oneTimeValues['mailtracking.defaults.containersearchmode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
    }

   


    return ( 
        <Fragment>
 {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
            <div className="header-filter-panel flippane">
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.ux.mailcontainerlist.lbl.destination" /></label>
                                     <Lov name="destination" lovTitle="Destination airport" uppercase={true} dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_MAILCONTAINERLIST_DESTINATION" />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group mar-t-md">   
                                    <ICheckbox name="subclassGroup"  label="EMS Container" />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group mar-t-md">
                                    <ICheckbox name="uldFulIndFlag"  label="Containers marked as full" />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group mar-t-md">
                                    <ICheckbox name="unplannedContainers"  label=" Show Unplanned Container" />
                                </div>
                            </Col>
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <label className="form-control-label ">Assigned</label>
                                    <ISelect name="assignedTo" options={searchmode}  />
                                </div>
                            </Col>
                                   
                        </Row>
                    </div>
                    <div className="btn-row">
                        <IButton category="primary" bType="LIST" componentId="CMP_MAIL_OPERATIONS_MAILCONTAINERLIST_LIST "accesskey="L" onClick={this.onlistContainerDetails}>List</IButton>
                        <IButton category="default" bType="CLEAR" componentId="CMP_MAIL_OPERATIONS_MAILCONTAINERLIST_CLEAR"accesskey="C" onClick={this.onclearContainerDetails}>Clear</IButton>
                        
                    </div>
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
            </div>
): (

            <div className="header-summary-panel flippane">
                    <div className="pad-md">
                        <Row>
                        {this.props.filter.destination && this.props.filter.destination.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.ux.mailcontainerlist.lbl.destination" /></label>
                                        <div className="form-control-data">{this.props.filter.destination}</div>
                                    </Col> : ""
                                }
                                 {this.props.filter.subclassGroup && this.props.filter.subclassGroup.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">EMS Container</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {this.props.filter.uldFulIndFlag && this.props.filter.uldFulIndFlag.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Containers marked as full</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                           {this.props.filter.unplannedContainers && this.props.filter.unplannedContainers.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Show Unplanned Container</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                         
                         {this.props.filter.assignedTo && this.props.filter.assignedTo.length > 0 ?
                                    <Col xs="4">
                                        {(this.props.filter.assignedTo === 'DESTN' || this.props.filter.assignedTo === 'FLT') ? (
                                            <div>
                                                <label className="form-control-label">Assigned</label>
                                                {(this.props.filter.assignedTo === 'DESTN') ?
                                                    (<div className="form-control-data">Carrier</div>)
                                                    :
                                                    (<div className="form-control-data">Flight</div>)}
                                            </div>)
                                            : <div>
                                                <label className="form-control-label">Assigned</label>
                                                <div className="form-control-data">All</div>
                                            </div>}
                                    </Col> : ""
                                }
                        </Row>
                    </div>

                    <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
            </div>
             
)}
        </Fragment>
            )
        }
}



export default wrapForm('containerFilter')(FilterPanel);


