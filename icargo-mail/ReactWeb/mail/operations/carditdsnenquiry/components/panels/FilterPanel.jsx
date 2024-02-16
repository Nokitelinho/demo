import React, { Fragment } from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Label } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { TimePicker } from 'icoreact/lib/ico/framework/component/common/time';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { Forms,Key,Constants,Urls,ComponentId } from '../../constants/constants.js'

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            category: [],
            status: [],
            flightType: [],
            awbAttached: [{label:'Yes',value:'Y'},{label:'No',value:'N'}],
            showPopover:false,
        }
        this.dateRangeIdr = 'CarditDsnEnquiryDateRange'
    }

    componentDidMount(){
        this.initializeOneTime();
    }

    initializeOneTime = () => {

        let category = [];
        let status = [];
        let flightType = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if (!isEmpty(this.props.oneTimeValues[Key.MAIL_STATUS]))
                status = this.props.oneTimeValues[Key.MAIL_STATUS].filter((value) => { if (value.fieldValue === Constants.ACP || value.fieldValue === Constants.CAP) return value });
            if (!isEmpty(this.props.oneTimeValues[Key.MAIL_CATEGORY]))
                category = this.props.oneTimeValues[Key.MAIL_CATEGORY].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
                if (!isEmpty(this.props.oneTimeValues[Key.FLIGHT_TYPE]))
                flightType = this.props.oneTimeValues[Key.FLIGHT_TYPE].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            if (!isEmpty(status))
                status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.setState({status:status,category:category,flightType:flightType})
        }
    }

    onlistCarditDsnEnquiry = () => {
        this.props.onlistCarditDsnEnquiry();
    }
    onclearCarditDsnEnquiry = () => {
        this.props.onclearCarditDsnEnquiry();
    }
    toggleFilter = () => {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'view' : 'edit');
    }
    togglePopover =()=>{
        this.setState({ showPopover:!this.state.showPopover });
    }

    populateDescription=()=>{
        if (this.props.screenMode === 'display') {
            if(this.props.filter){
                if (this.props.filter.mailCategoryCode && this.props.filter.mailCategoryCode.length > 0) {
                    let selectedCategory = this.state.category.find((element) => {return  element.value === this.props.filter.mailCategoryCode});
                    this.selectedCategoryLabel = selectedCategory.label;
                }
                if (this.props.filter.mailStatus && this.props.filter.mailStatus.length > 0) {
                    let selectedStatus = this.state.status.find((element) => {return  element.value === this.props.filter.mailStatus});
                    this.selectedStatusLabel = selectedStatus.label;
                }
                if (this.props.filter.flightType && this.props.filter.flightType.length > 0) {
                    let selectedFlightType = this.state.flightType.find((element) => {return  element.value === this.props.filter.flightType});
                    this.selectedFlightTypeLabel = selectedFlightType.label;
                }
            }
            if(this.props.popOver){
                if (this.props.popOver.mailCategoryCode && this.props.popOver.mailCategoryCode.length > 0) {
                    let selectedCategory = this.state.category.find((element) => {return  element.value === this.props.filter.mailCategoryCode});
                    this.selectedCategoryLabel = selectedCategory.label;
                }
                if (this.props.popOver.mailStatus && this.props.popOver.mailStatus.length > 0) {
                    let selectedStatus = this.state.status.find((element) => {return  element.value === this.props.popOver.mailStatus});
                    this.selectedStatusLabel = selectedStatus.label;
                }
                if (this.props.popOver.flightType && this.props.popOver.flightType.length > 0) {
                    let selectedFlightType = this.state.flightType.find((element) => {return  element.value === this.props.popOver.flightType});
                    this.selectedFlightTypeLabel = selectedFlightType.label;
                }
            }
        }
    }

    render() {

        this.populateDescription();
        return (

            <Fragment>

                    <div style={{display:(this.props.screenMode === 'edit' || this.props.screenMode === 'initial')?null:'none'}} >
                        <div class="header-filter-panel flippane animated fadeInDown">
                            <div class="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.OOE_LBL} /></label>
                                            <Lov name="ooe" lovTitle="Origin OE" codeFldNameInScrn="ooe" dialogWidth="600" dialogHeight="425" actionUrl={Urls.OOELOV} componentId={ComponentId.OOE_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.DOE_LBL} /></label>
                                            <Lov name="doe" lovTitle="Destination OE" codeFldNameInScrn="doe" dialogWidth="600" dialogHeight="425" actionUrl={Urls.OOELOV} componentId={ComponentId.DOE_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.CATEGORY_LBL} /></label>
                                            <ISelect defaultOption={true} name="mailCategoryCode" options={this.state.category} componentId={ComponentId.CATEGORY_SELECT} />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.SUBCLASS_LBL} /></label>
                                            <Lov name="mailSubclass" codeFldNameInScrn="mailSubclass" lovTitle="Subclass" dialogWidth="600" dialogHeight="425" maxlength="2" actionUrl={Urls.SUBCLASSLOV} componentId={ComponentId.SUBCLASS_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="2" md="1">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.YEAR_LBL} /></label>
                                            <ITextField mode="edit" name="year" type="text" componentId={ComponentId.YEAR_TEXT} maxlength="1" ></ITextField>
                                        </div>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.DSN_LBL} /></label>
                                            <ITextField mode="edit" name="despatchSerialNumber" type="text" componentId={ComponentId.DSN_TEXT} maxlength="4"></ITextField>
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label class="form-control-label "><IMessage msgkey={Key.CONSIGNMENT_NUMBER_LBL} /></label>
                                            <ITextField mode="edit" name="conDocNo" type="text" componentId={ComponentId.CONSIGNMENT_NO_TEXT} maxlength="35"></ITextField>
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.CONSIGNMENT_DATE_LBL} /></label>
                                            <DatePicker name="consignmentDate" componentId={ComponentId.CONSIGNMENTDATE_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.RDT_LBL} /></label>
                                            <DatePicker name="rdtDate" componentId={ComponentId.RDT_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="2" md="2">
                                        <div className="form-group mar-t-md">
                                            <TimePicker name="rdtTime" componentId={ComponentId.RDTTIME_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="8" md="6">
                                        <div className="form-group">
                                            <IFlightNumber mode="edit" componentId={ComponentId.RDTTIME_TEXT}></IFlightNumber>
                                        </div>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.AIRPORT_LBL} /></label>
                                            <Lov name="airportCode" lovTitle="uplift airport" dialogWidth="600" dialogHeight="520" actionUrl={Urls.AIRPORTLOV} componentId={ComponentId.AIRPORT_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="3">
                                        <div className="form-group">
                                            <IUldNumber name="uldNumber"  defaultLabel="Container" mode="edit" componentId={ComponentId.ULDNO_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="6" md="4">
                                        <div className="form-group">
                                            <label className="form-control-label" >{Constants.AWB}</label>
                                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.FROMDATE_LBL} /></label>
                                            <DatePicker name="fromDate" dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" componentId={ComponentId.DATEFROM_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.TODATE_LBL} /></label>
                                            <DatePicker name="toDate" dateFieldId={this.dateRangeIdr} type="to" fromDateName="fromDate" componentId={ComponentId.DATETO_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.MAILSTATUS_LBL} /></label>
                                            <ISelect defaultOption={true} name="mailStatus" options={this.state.status} componentId={ComponentId.MAILSTATUS_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.PACODE_LBL} /></label>
                                            <Lov name="paCode" lovTitle="PA Code" codeFldNameInScrn="paCode"  maxlength="5" dialogWidth="600" dialogHeight="425" actionUrl={Urls.PACODELOV} componentId={ComponentId.PACODE_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.AWB_ATTACHED_LBL}/></label>
                                            <ISelect defaultOption={true} name="awbAttached" options={this.state.awbAttached} componentId={ComponentId.AWBSELECT_TEXT} />
                                        </div>
                                    </Col>
                                    <Col xs="4" md="3">
                                        <div className="form-group">
                                            <label className="form-control-label "><IMessage msgkey={Key.FLIGHT_TYPE_LBL}/></label>
                                            <ISelect defaultOption={true} name="flightType" options={this.state.flightType} componentId={ComponentId.FLIGHTTYPE_TEXT} />
                                        </div>
                                    </Col>


                                </Row>

                            </div>
                            <div class="btn-row">
                                <IButton category="primary" componentId={ComponentId.LIST_BTN} onClick={this.onlistCarditDsnEnquiry}><IMessage msgkey={Key.LIST_LBL}/></IButton>
                                <IButton category="default" componentId={ComponentId.CLEAR_BTN} onClick={this.onclearCarditDsnEnquiry}><IMessage msgkey={Key.CLEAR_LBL}/></IButton>
                            </div>
                            {(this.props.screenMode == 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                        </div>
                    </div>


                    <div className="header-summary-panel flippane animated fadeInDown"  style={{display:(this.props.screenMode === 'edit' || this.props.screenMode === 'initial')?'none':null}}>
                    <div className="pad-md">
                    {this.props.filter?
                        <Row>
                            {this.props.filter.ooe && this.props.filter.ooe.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label "><IMessage msgkey={Key.OOE_LBL} /></Label>
                                    <div className="form-control-data">  {this.props.filter.ooe}</div>
                                </Col> : ""

                            }
                            {this.props.filter.doe && this.props.filter.doe.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label "><IMessage msgkey={Key.DOE_LBL} /></Label>
                                    <div className="form-control-data">  {this.props.filter.doe}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailCategoryCode && this.props.filter.mailCategoryCode.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.CATEGORY_LBL} /></Label>
                                    <div className="form-control-data"> {this.selectedCategoryLabel}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailSubclass && this.props.filter.mailSubclass.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.SUBCLASS_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.mailSubclass}</div>
                                </Col> : ""
                            }
                            {this.props.filter.year && this.props.filter.year.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.YEAR_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.year}</div>
                                </Col> : ""
                            }
                            {this.props.filter.despatchSerialNumber && this.props.filter.despatchSerialNumber.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.DSN_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.despatchSerialNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.receptacleSerialNumber && this.props.filter.receptacleSerialNumber.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.RSN_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.receptacleSerialNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.conDocNo && this.props.filter.conDocNo.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.CONSIGNMENT_NUMBER_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.conDocNo}</div>
                                </Col> : ""
                            }
                            {this.props.filter.consignmentDate && this.props.filter.consignmentDate.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.CONSIGNMENT_DATE_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.consignmentDate}</div>
                                </Col> : ""
                            }
                            {this.props.filter.fromDate && this.props.filter.fromDate.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.FROMDATE_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.fromDate}</div>
                                </Col> : ""
                            }
                            {this.props.filter.toDate && this.props.filter.toDate.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.TODATE_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.toDate}</div>
                                </Col> : ""
                            }
                            {this.props.filter.paCode && this.props.filter.paCode.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.PACODE_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.paCode}</div>
                                </Col> : ""
                            }
                            {this.props.filter.flightnumber ?
                                <Col xs="4">
                                    <IFlightNumber {...this.props.filter.flightnumber} mode="display" ></IFlightNumber>
                                </Col> : ""
                            }
                            {
                                this.props.filter.airportCode && this.props.filter.airportCode.length > 0 ?
                                    <Col xs="4">
                                        <Label className="form-control-label"><IMessage msgkey={Key.AIRPORT_LBL} /></Label>
                                        <div className="form-control-data">{this.props.filter.airportCode}</div>
                                    </Col> : ""
                            }
                            {this.props.filter.uldNumber && this.props.filter.uldNumber.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.ULD_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.uldNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.mailStatus && this.props.filter.mailStatus.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.MAILSTATUS_LBL} /></Label>
                                    <div className="form-control-data">{this.selectedStatusLabel}</div>
                                </Col> : ""
                            }
                            {this.props.filter.flightType && this.props.filter.flightType.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_TYPE_LBL} /></Label>
                                    <div className="form-control-data">{this.selectedFlightTypeLabel}</div>
                                </Col> : ""
                            }
                            {this.props.filter.reqDeliveryDate && this.props.filter.reqDeliveryDate.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.RDT_LBL} /></Label>
                                    <div className="form-control-data">{this.props.filter.reqDeliveryDate} {this.props.filter.reqDeliveryTime ? this.props.filter.reqDeliveryTime : ''}</div>
                                </Col> : ""
                            }
                            {this.props.filter.shipmentPrefix && this.props.filter.shipmentPrefix.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label">{Constants.AWB}</Label>
                                    <div className="form-control-data">{this.props.filter.shipmentPrefix} - {this.props.filter.documentNumber}</div>
                                </Col> : ""
                            }
                            {this.props.filter.awbAttached && this.props.filter.awbAttached.length > 0 ?
                                <Col xs="4">
                                    <Label className="form-control-label"><IMessage msgkey={Key.AWB_ATTACHED_LBL} /> :</Label>
                                    <div className="form-control-data">{this.props.filter.awbAttached=='Y' ? 'Yes' : 'No'}</div>
                                </Col> : ""
                            }
                        </Row>:null}
                    </div>

                    <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>

                        <div className="header-extra-data" style={{display:this.props.popoverCount > 0 ? null:'none'}}>
                            <div className="badge" id="filterPopover" onMouseEnter={this.togglePopover}>+{this.props.popoverCount}</div>
                            <IPopover placement="auto-start" isOpen={this.state.showPopover} target={'filterPopover'} toggle={this.togglePopover} className="icpopover"> >
                                <IPopoverBody>
                                    <div className="header-extra-data-panel">
                                        {this.props.popOver && this.props.popOver.despatchSerialNumber && this.props.popOver.despatchSerialNumber.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.DSN_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.despatchSerialNumber}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.receptacleSerialNumber && this.props.popOver.receptacleSerialNumber.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.RSN_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.receptacleSerialNumber}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.conDocNo && this.props.popOver.conDocNo.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.CONSIGNMENT_NUMBER_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.conDocNo}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.consignmentDate && this.props.popOver.consignmentDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.CONSIGNMENT_DATE_LBL} />:</Label>
                                                <div className="form-control-data">{this.props.popOver.consignmentDate}</div>
                                            </div> : null
                                        }

                                        {this.props.popOver && this.props.popOver.fromDate && this.props.popOver.fromDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.FROMDATE_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.fromDate}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.toDate && this.props.popOver.toDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.TODATE_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.toDate}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.paCode && this.props.popOver.paCode.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.PACODE_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.paCode}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.flightnumber ?
                                            <div className="header-extra-data-detail">
                                                <br></br>
                                                <IFlightNumber {...this.props.popOver.flightnumber} mode="display" ></IFlightNumber>
                                                <br></br>
                                                <br></br>
                                            </div> : null
                                        }
                                        {
                                            this.props.popOver && this.props.popOver.airportCode && this.props.popOver.airportCode.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label"><IMessage msgkey={Key.AIRPORT_LBL} /> :</Label>
                                                    <div className="form-control-data">{this.props.popOver.airportCode}</div>
                                                </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.uldNumber && this.props.popOver.uldNumber.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.ULD_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.uldNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.mailStatus && this.props.popOver.mailStatus.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.MAILSTATUS_LBL} /> :</Label>
                                                <div className="form-control-data">{this.selectedStatusLabel}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.flightType && this.props.popOver.flightType.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.FLIGHT_TYPE_LBL} /> :</Label>
                                                <div className="form-control-data">{this.selectedFlightTypeLabel}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.reqDeliveryDate && this.props.popOver.reqDeliveryDate.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.RDT_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.reqDeliveryDate} {this.props.popOver.reqDeliveryTime ? this.props.popOver.reqDeliveryTime : ''}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.shipmentPrefix && this.props.popOver.shipmentPrefix.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label">{Constants.AWB} :</Label>
                                                <div className="form-control-data">{this.props.popOver.shipmentPrefix} - {this.props.popOver.documentNumber}</div>
                                            </div> : null
                                        }
                                        {this.props.popOver && this.props.popOver.awbAttached && this.props.popOver.awbAttached.length > 0 ?
                                            <div className="header-extra-data-detail">
                                                <Label className="form-control-label"><IMessage msgkey={Key.AWB_ATTACHED_LBL} /> :</Label>
                                                <div className="form-control-data">{this.props.popOver.awbAttached} {this.props.popOver.awbAttached ? this.props.popOver.awbAttached : ''}</div>
                                            </div> : null
                                        }
                                      
                                    </div>
                                </IPopoverBody>
                            </IPopover>
                        </div>

                </div>
            </Fragment>


        )
    }
}


export default wrapForm(Forms.CARDIT_DSN_FILTER)(FilterPanel);