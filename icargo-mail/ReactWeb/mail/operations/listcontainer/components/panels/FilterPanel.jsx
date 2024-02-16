import React, { Fragment } from 'react';
import { ITextField, ISelect, ICheckbox, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistContainerDetails = this.onlistContainerDetails.bind(this);
        this.onclearContainerDetails = this.onclearContainerDetails.bind(this);
        this.onInputChangeSearchmode = this.onInputChangeSearchmode.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
        this.dateRange='ContainerDetailsDateRange'
    }
    onlistContainerDetails() {
        this.props.onlistContainerDetails();
    }
    onclearContainerDetails() {
        this.props.onclearContainerDetails();
    }

    onInputChangeSearchmode() {
        this.props.onInputChangeSearchmode();
    }

    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }

    render() {

        let searchmode = [];
        let flighttype = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            flighttype = this.props.oneTimeValues['mailtracking.defaults.operationtype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            searchmode = this.props.oneTimeValues['mailtracking.defaults.containersearchmode'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));

        }
        let operationType=(this.props.containerFilterForm && this.props.containerFilterForm.values && this.props.containerFilterForm.values.operationType)?this.props.containerFilterForm.values.operationType:null;


        return (<Fragment>

            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                <div className="header-filter-panel flippane">
                    <div className="pad-md pad-b-3xs">
                        <Row>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.containerNo" /></label>
                                    <ITextField mode="edit" name="containerNo" type="text" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_CONTAINERNUMBER" uppercase={true}></ITextField>
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.fromDate" /></label>
                                    <DatePicker name="fromDate" dateFieldId={this.dateRange} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_FROMDATE" type="from" toDateName="toDate"/>
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.toDate" /></label>
                                    <DatePicker name="toDate" dateFieldId={this.dateRange} componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_TODATE" type="to" fromDateName="fromDate"/>
                                </div>
                            </Col>
                            <Col xs="2" md="2" sm="34">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.airport" /></label>
                                    <Lov name="airport" lovTitle="Airport" uppercase={true} dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_AIRPORT" />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.assignedBy" /></label>
                                    <ITextField mode="edit" name="assignedBy" uppercase={true} type="text" componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_ASSIGNEDBY"></ITextField>
                                </div>
                            </Col>
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.assignedTo" /></label>
                                    <ISelect name="assignedTo" options={searchmode} onOptionChange={this.onInputChangeSearchmode} />
                                </div>
                            </Col>
                            {(this.props.assignedTo === 'DESTN') ?
                                <Col xs="2" md="2" sm="3">
                                    <div className="form-group">
                                        <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.carrierCode" /></label>
                                        <Lov name="carrierCode" lovTitle="Carrier Code" uppercase={true} dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirline.do?formCount=1"
                                            componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_CARRIERCODE" />
                                    </div>
                                </Col> : null}
                            {(this.props.assignedTo === 'FLT') ?
                                <Fragment>
                                    <Col xs="6" md="6" sm="8">
                                        <div className="form-group">
                                            <IFlightNumber mode="edit" ></IFlightNumber>
                                        </div>
                                    </Col>
                                   
                                </Fragment>
                                : null}
                                  {(this.props.assignedTo !== 'DESTN') ?
                                <Fragment>
                                    <Col xs="3" md="3" sm="4">
                                        <div className="form-group">
                                            <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.type" /></label>
                                            <ISelect name="operationType" options={flighttype} />
                                        </div>
                                    </Col>
                                </Fragment>
                                : null}
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.destination" /></label>
                                    <Lov name="destination" lovTitle="Destination airport" uppercase={true} dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1"
                                        componentId="CMP_MAIL_OPERATIONS_CONTAINERENQUIRY_DESTINATION" />
                                </div>
                            </Col>
                            {isSubGroupEnabled('LUFTHANSA_SPECIFIC') &&
                            <Col xs="3" md="3" sm="4">
                                        <div className="form-group">
                                            <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.hbamarked"/></label>
                                            <ISelect name="hbaMarked" options={[{ 'label': 'Yes', 'value': 'Y' }, 
                            { 'label': 'No', 'value': 'N' }]} />
                                        </div>
                                    </Col>
                            }
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group mar-t-md">
                                    <ICheckbox name="toBeTransfered" disabled={operationType==='O'?true:false} label="To Be transfered" />
                                </div>
                            </Col>
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <ICheckbox name="notClosedFlag" label="Not Closed" />
                                </div>
                            </Col>
                            <Col xs="2" md="2" sm="3">
                                <div className="form-group">
                                    <ICheckbox name="mailAcceptedFlag" label="Mail accepted" />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <ICheckbox name="showEmpty" label="Show Empty" checked />
                                </div>
                            </Col>
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <ICheckbox name="showUnreleased" label="Unreleased ULD/Barrow"  />
                                </div>
                            </Col>
                            {isSubGroupEnabled('SINGAPORE_SPECIFIC') &&
                            <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <ICheckbox name="subclassGroup" label="EMS"  />
                                </div>
                            </Col>}
                            {isSubGroupEnabled('SINGAPORE_SPECIFIC') &&
                                <Col xs="3" md="3" sm="4">
                                <div className="form-group">
                                    <ICheckbox name="showUnplanedContainer" label="Show Unplanned Container"  />
                                </div>
                            </Col>
                             } 
                        </Row>
                    </div>
                    <div className="btn-row">
                        <IButton category="primary" bType="LIST" accesskey="L" onClick={this.onlistContainerDetails}>List</IButton>
                        <IButton category="default" bType="CLEAR" accesskey="C" onClick={this.onclearContainerDetails}>Clear</IButton>
                    </div>
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                </div>
            ) : (
                    <div className="header-summary-panel flippane">
                        <div className="pad-md">
                            <Row>
                                {this.props.filter.containerNo && this.props.filter.containerNo.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label "><IMessage msgkey="mail.operations.containerenquiry.lbl.containerNo" /></label>
                                        <div className="form-control-data"> {this.props.filter.containerNo}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.fromDate" /></label>
                                        <div className="form-control-data">{this.props.filter.fromDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.toDate" /></label>
                                        <div className="form-control-data">{this.props.filter.toDate}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.airport && this.props.filter.airport.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.airport" /></label>
                                        <div className="form-control-data">{this.props.filter.airport}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.assignedBy && this.props.filter.assignedBy.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.assignedBy" /></label>
                                        <div className="form-control-data">{this.props.filter.assignedBy}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.assignedTo && this.props.filter.assignedTo.length > 0 ?
                                    <Col xs="4">
                                        {(this.props.filter.assignedTo === 'DESTN' || this.props.filter.assignedTo === 'FLT') ? (
                                            <div>
                                                <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.assignedTo" /></label>
                                                {(this.props.filter.assignedTo === 'DESTN') ?
                                                    (<div className="form-control-data">Carrier</div>)
                                                    :
                                                    (<div className="form-control-data">Flight</div>)}
                                            </div>)
                                            : <div>
                                                <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.assignedTo" /></label>
                                                <div className="form-control-data">All</div>
                                            </div>}
                                    </Col> : ""
                                }
                                {this.props.filter.destination && this.props.filter.destination.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.destination" /></label>
                                        <div className="form-control-data">{this.props.filter.destination}</div>
                                    </Col> : ""
                                }
                                {this.props.filter.toBeTransfered && this.props.filter.toBeTransfered.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">To Be transfered</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {this.props.filter.notClosedFlag && this.props.filter.notClosedFlag.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Not Closed</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {this.props.filter.mailAcceptedFlag && this.props.filter.mailAcceptedFlag.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Mail accepted</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {this.props.filter.showEmpty && this.props.filter.showEmpty.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Show Empty</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                               {this.props.filter.showUnreleased && this.props.filter.showUnreleased.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Unreleased ULD/Barrow</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                {isSubGroupEnabled('LUFTHANSA_SPECIFIC') &&this.props.filter.hbaMarked && this.props.filter.hbaMarked.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Hba Marked</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                 { isSubGroupEnabled('SINGAPORE_SPECIFIC') &&this.props.filter.subclassGroup && this.props.filter.subclassGroup.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">EMS</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                }
                                  { isSubGroupEnabled('SINGAPORE_SPECIFIC') &&
                                  this.props.filter.unplannedContainers && this.props.filter.unplannedContainers.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">Show Unplaned Container</label>
                                        <div className="form-control-data">Yes</div>
                                    </Col> : ""
                                 } 
                            </Row>
                        </div>
                        <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>
                        {this.props.popoverCount > 0 ?
                            <div class="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.showPopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.props.showPopOverFlag} target={'filterPopover'} toggle={this.closePopover} className="icpopover"> >
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.destination && this.props.popOver.destination.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mail.operations.containerenquiry.lbl.destination" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.destination}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.toBeTransfered && this.props.popOver.toBeTransfered.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">To Be transfered :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.notClosedFlag && this.props.popOver.notClosedFlag.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Not Closed :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.mailAcceptedFlag && this.props.popOver.mailAcceptedFlag.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Mail accepted :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.showEmpty && this.props.popOver.showEmpty.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Show Empty :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.showUnreleased && this.props.popOver.showUnreleased.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Unreleased ULD/Barrow :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                             { isSubGroupEnabled('SINGAPORE_SPECIFIC') &&this.props.popOver.subclassGroup && this.props.popOver.subclassGroup.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">EMS :</label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            { isSubGroupEnabled('SINGAPORE_SPECIFIC')&&
                                            this.props.popOver.unplannedContainers && this.props.popOver.unplannedContainers.length > 0 ? 
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label">Show Unplaned Container :</label>
                                                    <div className="form-control-data">Yes</div>
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
        </Fragment>)
    }
}


export default wrapForm('containerFilter')(FilterPanel);