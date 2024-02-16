import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
import { Row, Col,Label} from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
class FlightCarrierFilterPanel extends Component {

    constructor(props) {
        super(props);

    }
    onchangeFilter=()=> {
        this.props.onchangeFilter();
    }
    onlistDetails=()=> {
        this.props.onlistDetails(this.props.outboundFilter.values);
    }
    onclearDetails=()=> {
        this.props.onclearDetails();
    }
   

    render() {

        return (
            <Fragment>
                {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
                     <div className="formview">
                       {(this.props.flightCarrierflag === 'F') ?
                          <div className="pad-t-md border-top">
                                <Row >
                                    <Col xs="7" md="6">
                                        <IFlightNumber {...this.props.filterValues.flightnumber} mode="edit" ></IFlightNumber>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>
                                             <ITextField name="airportCode" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' uppercase={true}  />
                                        </div>
                                    </Col>

                                    <Col xs="3">
                                      <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.fromdate" /></Label>
                                                <DatePicker name="fromDate" />
                                    </div>
                                    </Col>
                                   <Col xs="3">
                                    <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.todate" /></Label>
                                                 <DatePicker name="toDate" />
                                        </div>
                                    </Col>
                                     <Col xs="4" md="3">
                                        <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.flightoperationalstatus" /></Label>
                                                 <ITextField name="flightOperationalStatus" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                        </div>
                                    </Col>
                                   <Col>
                                        <div className="mar-t-md">
                                            <IButton category="primary" bType="LIST"  accesskey="L"  onClick={this.onlistDetails}>List</IButton>
                                            <IButton category="secondary" bType="CLEAR" accesskey="C"  onClick={this.onclearDetails}>Clear</IButton>{' '}
                                        </div>
                                    </Col>
                                  </Row>
                                  
                               </div>
                                :<div></div>}
                            {(this.props.flightCarrierflag === 'C') ?
                               <div className="pad-t-md border-top">
                                <Row>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>
                                                     <ITextField name="airportCode" componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' uppercase={true}  />
                                        </div>
                                    </Col>
                                    <Col xs="3" md="2">
                                        <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.carriercode" /></Label>
                                                 <Lov name= "carrierCode" uppercase={true}  lovTitle= "CarrierCode" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT"/>
                                        </div>
                                    </Col>
                                   <Col xs="3" md="2">
                                        <div className="form-group">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                                                <ITextField name="destination" uppercase={true}  componentId='CMP_Operations_FltHandling_ExportManifest_Reassign_Barrow' />
                                        </div>
                                    </Col>
                                    <Col>
                                        <div className="mar-t-md">
                                            <IButton category="primary" bType="LIST" onClick={this.onlistDetails}>List</IButton>
                                            <IButton category="secondary" bType="CLEAR" onClick={this.onclearDetails}>Clear</IButton>{' '}
                                        </div>
                                    </Col>
                                </Row>
                             </div>
                                :<div></div>}

                        </div>
                ) : (

                        <div className="displayview">
                            {(this.props.flightCarrierflag === 'F') ?
                                <div className="pad-t-md pad-b-2md border-top">
                                    <Row>
                                         <Col xs="4"> 
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.flightnumber" /></Label>
                                                    <IFlightNumber {...this.props.filterValues.flightnumber} mode="display" ></IFlightNumber>
                                        </Col>
                                        <Col xs="4">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.upliftairport" /></Label>\
                                                    <div className="form-control-data">{this.props.filterValues.airportCode}</div>
                                        </Col>
                                        <Col xs="4"> 
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.fromdate" /></Label>
                                            <div className="form-control-data">{this.props.filterValues.fromDate}</div>
                                           
                                        </Col>
                                         <Col xs="4"> 
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.todate" /></Label>
                                                     <div className="form-control-data">{this.props.filterValues.toDate}</div>
                                         </Col>
                                        <Col xs="4">
                                            <Label className="form-control-label">
                                                <IMessage msgkey="mail.operations.mailoutbound.flightoperationalstatus" /></Label>
                                           <div className="form-control-data">{this.props.filterValues.flightOperationalStatus}</div>
                                        </Col>
                                  </Row>
                                      
                                </div>
                                
                                : (this.props.flightCarrierflag === 'C') ?
                                    <div className="pad-t-md pad-b-2md border-top">
                                        <Row>
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.airport" /></Label>
                                                <div className="form-control-data">{this.props.filterValues.airportCode}</div>
                                            </Col>
                                        
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.carriercode" /></Label>
                                                <div className="form-control-data">{this.props.filterValues.carrierCode}</div>
                                            </Col>
                                            <Col xs="4">
                                                <Label className="form-control-label">
                                                    <IMessage msgkey="mail.operations.mailoutbound.destination" /></Label>
                                                <div className="form-control-data">{this.props.filterValues.destination}</div>
                                            </Col>
                                       </Row>

                                      
                                    </div>
                                    : <div></div>

                            }

                        </div>

                    )}

            </Fragment>



        )
    }
}

FlightCarrierFilterPanel.propTypes = {
    screenMode: PropTypes.string,
    togglePanel: PropTypes.func,
    onchangeFilter:PropTypes.func,
    onlistDetails:PropTypes.func,
    outboundFilter:PropTypes.object,
    onclearDetails:PropTypes.func,
    flightCarrierflag:PropTypes.string,
    filterValues:PropTypes.object,
    
}
export default wrapForm('outboundFilter')(FlightCarrierFilterPanel);