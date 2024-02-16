import React, { Fragment } from 'react';
import { Row, Col, Label } from "reactstrap";
import { IButton,ICheckbox } from 'icoreact/lib/ico/framework/html/elements'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { ISelect } from 'icoreact/lib/ico/framework/html/elements'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import PropTypes from 'prop-types'
import {Lov} from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';

class FilterPanel extends React.PureComponent {
    constructor(props) {   
         super(props);
         this.dateRange='MailInboundDateRange'         
        this.state = {
            showPopover:false,
        }
    }
    changeScreenMode=(event)=>{       
        this.props.onChangeScreenMode(event.target.dataset.mode);
    }
    clearFilterPanel=()=>{
        this.props.reset();        
        this.props.onclearFilterPanel();
    }    
    togglePopover =()=>{
        this.setState({ showPopover:!this.state.showPopover });
    }
    render(){
        let mailStatus=[];
        
        // let paValue=this.props.filterVlues?this.props.filterVlues.pa:''; 
        // let transfercarrierValue=this.props.filterVlues?this.props.filterVlues.transfercarrier:'';
        if (!isEmpty(this.props.oneTimeValues)) {
            mailStatus=this.props.oneTimeValues['mailtracking.defaults.mailarrivalstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        mailStatus=[{value:'C',label:'Close'},{value:'O',label:'Open'},{value:'N',label:'New'}];
        return(
            <Fragment>
                 {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (                    
                    <div className="header-filter-panel flippane">
                        <div className="pad-md pad-b-3xs">
                            <Row>
                                <Col xs="8" md="5">
                                <IFlightNumber flightnumber={this.props.filterVlues?this.props.filterVlues.flightNumber:''} mode="edit" uppercase={true} ></IFlightNumber>
                            </Col>
                                <Col xs="3" md="2">
                                    <div className="form-group">
                                        <Label className="form-control-label">
                                         <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.port" />
                                         </Label>
                                        <ITextField name="port" uppercase={true} />
                                    </div>
                                    </Col>
                                <Col xs="4" md="4">
                                    <div className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="mail.operations.mailinbound.fromdate" /></Label>
                                        <div className="form-row">
                                            <Col xs="18">
                                                <DatePicker value={new Date()} name="fromDate" dateFieldId={this.dateRange} type="from" toDateName="toDate" id="fromdatename" />
                                            </Col>
                                            <Col xs="6"><TimePicker name="fromTime" /></Col>
                                        </div>
                                    </div>
                                </Col>
                                <Col xs="4" md="4">
                                    <div className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="mail.operations.mailinbound.todate" /></Label>
                                        <div className="form-row">
                                            <Col xs="18">
                                                <DatePicker name="toDate" dateFieldId={this.dateRange} type="to" fromDateName="fromDate" id="toDateName" />
                                            </Col>
                                            <Col xs="6">
                                                <TimePicker name="toTime" />
                                            </Col>
                                        </div>
                                    </div>
                                </Col>
                                <Col xs="5" md="3">
                                    <div className="form-group">
                                        <Label className="form-control-label">
                                        {/* <IMessage msgkey="mail.operations.mailinbound.flightoperationalstatus"/> */}
                                        Mail Operational Status
                                        </Label>
                                        <ISelect defaultOption={true} name="mailstatus" options={mailStatus} />
                                    </div>
                                    </Col>
                                <Col xs="3" md="2">
                                       <div className="form-group">
                                        <Label className="form-control-label">
                                        <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.transfercarrier"/></Label>
                                        <Lov name="transfercarrier" uppercase={true} lovTitle="Transfer Carrier" dialogWidth="600" dialogHeight="500" actionUrl="ux.showAirport.do?formCount=1" />
                                                        </div>
                                    </Col>
                                <Col xs="3" md="2">
                                    <div className="form-group">
                                        <Label className="form-control-label">
                                        <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pa"/></Label> 
                                                 <Lov name= "pa" uppercase={true} lovTitle= "GPA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" />
                                                        </div>
                                    </Col>

                                <Col xs="auto">
                                    <div className="mar-t-md">
                                        <ICheckbox name="mailFlightChecked" label="Mail Flight" />
                                    </div>
                                </Col>
                                <Col xs="3" md="2">
                                <div className="form-group">
                                        <Label className="form-control-label">
                                       POL</Label> 
                                    <Lov name="pol" lovTitle="POL" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1"  uppercase={true}/>
                                    </div>
                                </Col>
                                <Col xs="3" md="3">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                    Operating Carrier
                                    </Label> 
                                    <ITextField  name="operatingReference" uppercase={true} />
                                    </div>
                                </Col>

                            </Row>
                            </div>
                            <div class="btn-row">
                            <IButton category="primary" bType="LIST" accesskey="L" onClick={this.props.listMail} componentId="BUT_MAIL_OPERATIONS_MAILINBOUND_LIST">List</IButton>
                            <IButton category="secondary" bType="CLEAR" accesskey="C" onClick={this.clearFilterPanel} componentId="BUT_MAIL_OPERATIONS_MAILINBOUND_CLEAR">Clear</IButton>
                            </div>
                        {(this.props.screenMode === 'edit') ? (<i onClick={this.changeScreenMode} data-mode="display" className="icon ico-close-fade flipper flipper-ico"></i>) : null}
                        </div>
                        
                           
                       
                        

                        
                 ):(
                        <div className="header-summary-panel flippane">
                            <div className="pad-md">
                                <Row>
                        {
                              (this.props.filterVlues && this.props.filterVlues.flightnumber && this.props.filterVlues.flightnumber.flightNumber) ?
                                            <Fragment>
                                            <Col xs="4">
                                                     <Label className="form-control-label">
                                                    <IMessage msgkey="mailtracking.defaults.mailarrival.tooltip.flightnumber"/></Label> 
                                                    <div className="form-control-data">{this.props.filterVlues.flightnumber.carrierCode}-{this.props.filterVlues.flightnumber.flightNumber} </div>
                                                </Col>
                                                </Fragment> : '' 
                        }
                        {    (this.props.filterVlues && this.props.filterVlues.flightnumber && this.props.filterVlues.flightnumber.flightDate ) ?                     
                                                <Fragment>
                                                <Col xs="4">
                                                    <Label className="form-control-label">
                                                    <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.flightdate"/></Label> 
                                                    <div className="form-control-data">{this.props.filterVlues.flightnumber.flightDate} </div>
                                                </Col>
                                            </Fragment> : ''
                        }
                        {
                             (this.props.filter.port) ?
                                            <Col xs="4">
                                                 <Label className="form-control-label">
                                                <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.port" /></Label> 
                                                <div className="form-control-data">{this.props.filter.port} </div>
                            </Col>: ''
                        }
                        {
                            (this.props.filter.fromDate) ?
                                            <Col xs="4">
                                                <Label className="form-control-label"  >
                                                <IMessage msgkey="mail.operations.mailinbound.fromdate"/></Label> 
                                                <div className="form-control-data">{this.props.filter.fromDate} </div>
                            </Col>: ''
                        }
                        {
                            (this.props.filter.toDate) ?
                                            <Col xs="4">
                                                <Label className="form-control-label" for="toDateName">
                                                <IMessage msgkey="mail.operations.mailinbound.todate"/></Label> 
                                                <div className="form-control-data">{this.props.filter.toDate} </div>
                            </Col>: ''
                        }
                        {
                            (this.props.filter.mailstatus) ?
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                               Mail Operational Status</Label> 
                                        
                                                <div className="form-control-data">
                                                    {(this.props.filter.mailstatus=='C')?
                                                        'Close':
                                                    ((this.props.filter.mailstatus=='O')?
                                                        'Open'
                                                    :
                                                        'New')}
                                                    </div>
                            </Col>: '' 
                        }     
                        {       
                                        (this.props.filter.transfercarrier) ?
                                            <Col xs="4">
                                         <Label className="form-control-label">
                                        <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.transfercarrier"/></Label> 
                                                <div className="form-control-data">{this.props.filter.transfercarrier}</div>
                            </Col>: ''
                        }
                        {
                                        (this.props.filter.pa&&this.props.popoverCount<1) ?
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pa"/></Label> 
                                                <div className="form-control-data">{this.props.filter.pa}</div>
                            </Col>: ''
                        } 
                        {
                            (this.props.filter.mailFlightChecked) ?
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailinbound.mailflight"/></Label> 
                                                <div className="form-control-data">Yes</div>
                            </Col>: ''
                        }
                         {
                            (this.props.filter.pol) ?
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                POL</Label> 
                                                <div className="form-control-data">{this.props.filter.pol}</div>
                            </Col>: ''
                        }
                        {
                            (this.props.filter.operatingReference) ?
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                Operating Carrier
                                            </Label> 
                                <div className="form-control-data">{this.props.filter.operatingReference}</div>
                            </Col>: ''
                        }


                            </Row>
                         
                          
                        </div>
                            <i onClick={this.changeScreenMode} data-mode="edit" className="icon ico-pencil-rounded-orange flipper flipper-ico"></i>
                            {this.props.popoverCount > 0 &&
                            <div className="header-extra-data">
                                <div className="badge" id="filterPopover" onMouseEnter={this.togglePopover}>+{this.props.popoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.state.showPopover} target={'filterPopover'} toggle={this.togglePopover} className="icpopover"> 
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.popOver.transfercarrier && this.props.popOver.transfercarrier.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mailtracking.defaults.mailarrival.lbl.transfercarrier" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.transfercarrier}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.pa && this.props.popOver.pa.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <label className="form-control-label"><IMessage msgkey="mailtracking.defaults.mailarrival.lbl.pa" /> :</label>
                                                    <div className="form-control-data">{this.props.popOver.pa}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.mailFlightChecked ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">
                                                        <IMessage msgkey="mail.operations.mailinbound.mailflight" /></Label>
                                                    <div className="form-control-data">Yes</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.pol && this.props.popOver.pol.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">
                                                        POL</Label>
                                                    <div className="form-control-data">{this.props.popOver.pol}</div>
                                                </div> : null
                                            }
                                            {this.props.popOver.operatingReference && this.props.popOver.operatingReference.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">
                                                        Operating Carrier
                                            </Label>
                                                    <div className="form-control-data">{this.props.popOver.operatingReference}</div>
                                                </div> : null
                                            }
                                        </div> 
                                    </IPopoverBody>
                                </IPopover>
                            </div> }
                        </div>
                 )}
            </Fragment>
        )
    }    
}

FilterPanel.propTypes = {
    onChangeScreenMode: PropTypes.func,
    reset: PropTypes.func,
    onclearFilterPanel:PropTypes.func,
    screenMode: PropTypes.object,
    filterVlues: PropTypes.object,
    listMail:PropTypes.func,
    oneTimeValues:PropTypes.array,
    filter:PropTypes.object,
    popoverCount:PropTypes.number,
    popOver:PropTypes.object,
    
}
export default wrapForm('mailinboundFilter')(FilterPanel);