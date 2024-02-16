import React, { Fragment } from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Label } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IPopover, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip';

class ManifestFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            status: [],
            showPopover: false,
        }
        this.onlistManifestDetails = this.onlistManifestDetails.bind(this);
        this.dateRangeIdr = 'MailAwbBookingDateRange'
    }

    componentDidMount() {
        //this.initializeOneTime();
    }

    toggleFilter = () => {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    togglePopover = () => {
        this.setState({ showPopover: !this.state.showPopover });
    }

    onlistManifestDetails = () => {
        this.props.onlistManifestDetails();
    }

    onclearManifestDetails = () => {
        this.props.onclearManifestDetails();
    }

    render() {
        return (

            <Fragment>
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ?
                    <div className="header-filter-panel flippane">
                        <div className="pad-md pad-b-3xs">
                            <Row>
                                <Col xs="4">
                                    <div className="form-group">
                                        <label className="form-control-label" >AWB Number</label>
                                        <IAwbNumber mode="edit" shipmentPrefix={this.props.initialValues.shipmentPrefix} masterDocumentNumber={this.props.initialValues.masterDocumentNumber} reducerName="awbReducer" hideLabel={true} form="true" />
                                    </div>
                                </Col>
                                <Col xs="4">
                                    <div className="form-group mandatory">
                                        <label className="form-control-label mandatory_label">Manifest From</label>
                                        <DatePicker name="manifestDateFrom" id="manifestDateFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="manifestDateTo" />
                                        <IToolTip value={'Manifest From'} target={'manifestDateFrom'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="4">
                                    <div className="form-group mandatory">
                                        <label className="form-control-label mandatory_label">Manifest To</label>
                                        <DatePicker name="manifestDateTo" id="manifestDateTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="manifestDateFrom" />
                                        <IToolTip value={'Manifest To'} target={'manifestDateTo'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="2">
                                    <div className="form-group">
                                        <label className="form-control-label ">SCC</label>
                                        <Lov name="mailScc" id="mailScc" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
                                            closeButtonIds={['btnOk', 'btnClose']} uppercase={true}
                                        />
                                        <IToolTip value={'SCC'} target={'mailScc'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="3">
                                    <div className="form-group">
                                        <label className="form-control-label ">Orgin</label>
                                        <Lov name="origin" id="origin" lovTitle="AWB Origin" isReactContext={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                        <IToolTip value={'Orgin'} target={'origin'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="3">
                                    <div className="form-group">
                                        <label className="form-control-label ">Destination</label>
                                        <Lov name="destination" id="destination" lovTitle=" AWB Destination" isReactContext={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                        <IToolTip value={'Destination'} target={'destination'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="2">
                                    <div className="form-group mandatory">
                                        <label className="form-control-label mandatory_label">POU</label>
                                        <Lov name="pou" lovTitle="POU" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                        <IToolTip value={'POU'} target={'pou'} placement='bottom' />
                                    </div>
                                </Col>
                                <Col xs="2">
                                    <div className="form-group">
                                        <label className="form-control-label">POL</label>
                                        <Lov name="pol" lovTitle="POL" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                                        <IToolTip value={'POL'} target={'pol'} placement='bottom' />
                                    </div>
                                </Col>
                                {<Col xs="3">
                                    <div className="form-group">
                                        <IFlightNumber flightnumber={this.props.initialValues} mode="edit" showDate={false}></IFlightNumber>
                                    </div>
                                </Col>}
                                {<Col xs="3">
                                    <div className="form-group">
                                        <label className="form-control-label ">Flight Date</label>
                                        <DatePicker name="flightDate" id="flightDate" dateFieldId={this.dateRangeIdr} />
                                        <IToolTip value={'Flight Date'} target={'flightDate'} placement='bottom' />
                                    </div>
                                </Col>}

                            </Row>

                        </div>
                        <div className="btn-row">
                            <IButton id="list" category="primary" privilegeCheck={false} onClick={this.onlistManifestDetails}>List</IButton>
                            <IToolTip value={'List'} target={'list'} placement='bottom' />
                            <IButton id="Clear" category="default" privilegeCheck={false} onClick={this.onclearManifestDetails}>Clear</IButton>
                            <IToolTip value={'Clear'} target={'Clear'} placement='bottom' />
                        </div>
                        {(this.props.screenMode == 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                    </div>

                    : (
                        <div className="header-summary-panel flippane animated fadeInDown">
                            <div className="pad-md">
                                {this.props.manifestFilter ?
                                    <Row>
                                        {this.props.manifestFilter.shipmentPrefix && this.props.manifestFilter.shipmentPrefix.length > 0 &&
                                            this.props.manifestFilter.masterDocumentNumber && this.props.manifestFilter.masterDocumentNumber.length > 0 ?
                                            <Col xs="3">
                                                <Label className="form-control-label">AWB</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.shipmentPrefix} - {this.props.manifestFilter.masterDocumentNumber}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.manifestDateFrom && this.props.manifestFilter.manifestDateFrom.length > 0 ?
                                            <Col xs="4">
                                                <Label className="form-control-label ">Manifest From</Label>
                                                <div className="form-control-data">  {this.props.manifestFilter.manifestDateFrom}</div>
                                            </Col> : ""

                                        }
                                        {this.props.manifestFilter.manifestDateTo && this.props.manifestFilter.manifestDateTo.length > 0 ?
                                            <Col xs="3">
                                                <Label className="form-control-label ">Manifest To</Label>
                                                <div className="form-control-data">  {this.props.manifestFilter.manifestDateTo}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.mailScc && this.props.manifestFilter.mailScc.length > 0 ?
                                            <Col xs="2">
                                                <Label className="form-control-label">SCC</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.mailScc}</div>
                                            </Col> : ""
                                        }

                                        {this.props.manifestFilter.origin && this.props.manifestFilter.origin.length > 0 ?
                                            <Col xs="2">
                                                <Label className="form-control-label">AWB Origin</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.origin}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.destination && this.props.manifestFilter.destination.length > 0 ?
                                            <Col xs="3">
                                                <Label className="form-control-label">AWB Destination</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.destination}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.pou && this.props.manifestFilter.pou.length > 0 ?
                                            <Col xs="2">
                                                <Label className="form-control-label">POU</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.pou}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.pol && this.props.manifestFilter.pol.length > 0 ?
                                            <Col xs="2">
                                                <Label className="form-control-label">POL</Label>
                                                <div className="form-control-data">{this.props.manifestFilter.pol}</div>
                                            </Col> : ""
                                        }

                                        {this.props.manifestFilter.flightNumber ?
                                            <Col xs="3">
                                                <Label className="form-control-label">Flight Number</Label>
                                                {/* <IFlightNumber {...this.props.manifestFilter.flightNumber} showDate={false} mode="display"></IFlightNumber> */}
                                                <div className="form-control-data">{this.props.manifestFilter.flightNumber}</div>
                                            </Col> : ""
                                        }
                                        {this.props.manifestFilter.flightDate && this.props.manifestFilter.flightDate.length > 0 ?
                                            <Col xs="3">
                                                <Label className="form-control-label">Flight Date</Label>
                                                <div className="form-control-data"> {this.props.manifestFilter.flightDate}</div>
                                            </Col> : ""
                                        }
                                    </Row> : null}

                            </div>
                            <i className="icon ico-pencil-rounded-orange flipper flipper-ico" onClick={this.toggleFilter}></i>

                            <div className="header-extra-data" style={{ display: this.props.manifestPopoverCount > 0 ? null : 'none' }}>
                                <div className="badge" id="filterPopover" onMouseEnter={this.togglePopover}>+{this.props.manifestPopoverCount}</div>
                                <IPopover placement="auto-start" isOpen={this.state.showPopover} target={'filterPopover'} toggle={this.togglePopover} className="icpopover">
                                    <IPopoverBody>
                                        <div className="header-extra-data-panel">
                                            {this.props.manifestPopOver && this.props.manifestPopOver.shipmentPrefix && this.props.manifestPopOver.shipmentPrefix.length > 0 &&
                                                this.props.manifestFilter.masterDocumentNumber && this.props.manifestFilter.masterDocumentNumber.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">AWB Number</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.shipmentPrefix} - {this.props.manifestPopOver.masterDocumentNumber}</div>
                                                </div> : null
                                            }
                                            {this.props.manifestPopOver && this.props.manifestPopOver.manifestDateFrom && this.props.manifestPopOver.manifestDateFrom.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">Manifest From :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.manifestDateFrom}</div>
                                                </div> : null
                                            }

                                            {this.props.manifestPopOver && this.props.manifestPopOver.manifestDateTo && this.props.manifestPopOver.manifestDateTo.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">Manifest To :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.manifestDateTo}</div>
                                                </div> : null
                                            }
                                            {
                                                this.props.manifestPopOver && this.props.manifestPopOver.mailScc && this.props.manifestPopOver.mailScc.length > 0 ?
                                                    <div className="header-extra-data-detail">
                                                        <Label className="form-control-label">SCC :</Label>
                                                        <div className="form-control-data">{this.props.manifestPopOver.mailScc}</div>
                                                    </div> : null
                                            }

                                            {this.props.manifestPopOver && this.props.manifestPopOver.origin && this.props.manifestPopOver.origin.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">AWB Origin :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.origin}</div>
                                                </div> : null
                                            }

                                            {this.props.manifestPopOver && this.props.manifestPopOver.destination && this.props.manifestPopOver.destination.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">AWB Destination :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.destination}</div>
                                                </div> : null
                                            }
                                            {this.props.manifestPopOver && this.props.manifestPopOver.pou && this.props.manifestPopOver.pou.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">POU :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.pou}</div>
                                                </div> : null
                                            }
                                            {this.props.manifestPopOver && this.props.manifestPopOver.pol && this.props.manifestPopOver.pol.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">POL :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.pol}</div>
                                                </div> : null
                                            }
                                            {this.props.manifestPopOver && this.props.manifestPopOver.flightnumber ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">Flight Number:</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.flightnumber.carrierCode + ' ' + this.props.popOver.flightnumber.flightNumber}</div>
                                                </div> : null
                                            }
                                            {this.props.manifestPopOver && this.props.manifestPopOver.flightDate && this.props.manifestPopOver.flightDate.length > 0 ?
                                                <div className="header-extra-data-detail">
                                                    <Label className="form-control-label">Flight Date :</Label>
                                                    <div className="form-control-data">{this.props.manifestPopOver.flightDate}</div>
                                                </div> : null
                                            }

                                        </div>
                                    </IPopoverBody>
                                </IPopover>

                            </div>
                        </div>)}
            </Fragment>
        )
    }
}


export default wrapForm('manifestViewForm')(ManifestFilterPanel);