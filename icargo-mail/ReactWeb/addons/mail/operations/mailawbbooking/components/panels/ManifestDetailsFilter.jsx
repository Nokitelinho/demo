import React from 'react';
import { Row, Col } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip';

class ManifestDetailsFilter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            status: [],
            showPopover: false,
        }
        this.dateRangeIdr = 'MailAwbBookingDateRange'
    }

    componentDidMount() {
        //this.initializeOneTime();
    }


    render() {
        return (

            <div>
                <Row>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label" >AWB Number</label>
                            <IAwbNumber mode="edit" shipmentPrefix={this.props.initialValues.shipmentPrefix} masterDocumentNumber={this.props.initialValues.masterDocumentNumber} reducerName="awbReducer" hideLabel={true} form="true" />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group mandatory">
                            <label className="form-control-label mandatory_label">Manifest From</label>
                            <DatePicker name="manifestDateFrom" id="manifestDateFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="manifestDateTo" />
                            <IToolTip value={'Manifest From'} target={'manifestDateFrom'} placement='bottom' />
                        </div>
                    </Col>
                    <Col xs="8">
                        <div className="form-group mandatory">
                            <label className="form-control-label mandatory_label">Manifest To</label>
                            <DatePicker name="manifestDateTo" id="manifestDateTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="manifestDateFrom" />
                            <IToolTip value={'Manifest To'} target={'manifestDateTo'} placement='bottom' />
                        </div>
                    </Col>
                </Row>
                <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">SCC</label>
                            <Lov name="mailScc" id="mailScc" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
                                closeButtonIds={['btnOk', 'btnClose']} uppercase={true}
                            />
                            <IToolTip value={'SCC'} target={'mailScc'} placement='bottom' />
                        </div>
                    </Col>

                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Orgin</label>
                            <Lov name="origin" id="origin" lovTitle="Origin" isReactContext={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                            <IToolTip value={'Orgin'} target={'origin'} placement='bottom' />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Destination</label>
                            <Lov name="destination" id="destination" lovTitle="Destination" isReactContext={true} dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                            <IToolTip value={'AWB Destination'} target={'destination'} placement='bottom' />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group mandatory">
                            <label className="form-control-label  mandatory_label">POU</label>
                            <Lov name="pou" lovTitle="POU" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                            <IToolTip value={'POU'} target={'pou'} placement='bottom' />
                        </div>
                    </Col>

                </Row>
                <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label">POL</label>
                            <Lov name="pol" lovTitle="POL" dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                            <IToolTip value={'POL'} target={'pol'} placement='bottom' />
                        </div>
                    </Col>

                    <Col xs="6">
                        <div className="form-group">
                            <IFlightNumber flightnumber={this.props.initialValues} mode="edit" showDate={false}></IFlightNumber>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Flight Date</label>
                            <DatePicker name="flightDate" id="flightDate" dateFieldId={this.dateRangeIdr} />
                            <IToolTip value={'Flight Date'} target={'flightDate'} placement='bottom' />
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}

export default wrapForm('manifestViewFilterForm')(ManifestDetailsFilter);