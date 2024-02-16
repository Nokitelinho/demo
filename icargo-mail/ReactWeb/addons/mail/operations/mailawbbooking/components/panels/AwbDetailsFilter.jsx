import React from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { Row, Col } from "reactstrap";
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class AwbDetailsFilter extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            status: [],
            showPopover:false,
        }
        this.dateRangeIdr = 'MailAwbBookingDateRange'
    }

    componentDidMount(){
        this.initializeOneTime();
    }

    initializeOneTime = () => {

        let status = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if (!isEmpty(this.props.oneTimeValues['operations.shipment.shipmentstatus']))
            status = this.props.oneTimeValues['operations.shipment.shipmentstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.setState({status:status})
        }
    }

    render(){
        return(
            <div>
                 <Row>
                    <Col xs="8">
                        <div className="form-group">
                            <label className="form-control-label" >AWB</label>
                                <IAwbNumber mode="edit" shipmentPrefix={this.props.initialValues.shipmentPrefix} masterDocumentNumber={this.props.initialValues.masterDocumentNumber} reducerName="awbReducer" hideLabel={true} form="true" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Booking From</label>
                                <DatePicker name="bookingFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="bookingTo" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Booking To</label>
                                <DatePicker name="bookingTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="bookingFrom" />
                        </div>
                    </Col>                
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label ">SCC</label>
                                <Lov name="mailScc" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
								closeButtonIds={['btnOk', 'btnClose']} uppercase={true}
								/>
                        </div>
                    </Col>
                    </Row>
                    <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Product</label>
                                <Lov name="mailProduct" lovTitle="Product" closeButtonIds={['btok', 'BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLOSE']} uppercase={true} dialogWidth="600" dialogHeight="425" actionUrl="products.defaults.ux.screenloadProductLov.do?productObject=productName&formNumber=1&activeProduct=Y&rowIndex=0" uppercase={true} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Orgin</label>
                                <Lov name="orginOfBooking" lovTitle="Origin" isReactContext={true}  dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Destination</label>
                                <Lov name="destinationOfBooking" lovTitle="Destination" isReactContext={true}  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" uppercase={true} />
                         </div>
                    </Col>
                    <Col xs="4">
                        <div className="form-group">
                            <label className="form-control-label ">Via Point</label>
                                <Lov name="viaPointOfBooking" lovTitle="Via Point" isReactContext={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" uppercase={true}/>
                        </div>
                    </Col>
                    </Row>
                    <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Shipping Date</label>
                                <DatePicker name="shipmentDate" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <IFlightNumber flightnumber={this.props.initialValues} mode="edit" showDate={false}></IFlightNumber>
                        </div>
                    </Col>
                    <Col xs="6">
                    <div className="form-group">
                        <label className="form-control-label ">Flight From</label>
                            <DatePicker name="bookingFlightFrom" dateFieldId={this.dateRangeIdr} type="from" toDateName="toDate" />
                    </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Flight To</label>
                                <DatePicker name="bookingFlightTo" dateFieldId={this.dateRangeIdr} type="to" fromDateName="fromDate" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Agent Code</label>
                                <Lov name="agentCode" isReactContext={true} lovTitle="Agent Code" dialogWidth="600" dialogHeight="425" actionUrl='shared.defaults.ux.agent.screenloadagentlov.do?multiselect=N&pagination=Y&textfiledObj=agentCode&formNumber=1&textfiledDesc=&rowCount=0&agentCode="' 
								formParameters={{ 0 : "agentCode", 2:'customerCode'} }	uppercase={true}		
							    />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Customer Code</label>
                                <Lov name="customerCode" closeButtonIds={['CMP_Shared_Customer_CustomerLov_Close', 'btnOk']} isReactContext={true} lovTitle="Customer Code" dialogWidth="600" dialogHeight="425" actionUrl="ux.showCustomer.do?formCount=1&mode=Y" 
								formParameters={{ 2:'customerCode'} } uppercase={true}
								/>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">User ID</label>
                                <ITextField mode="edit" name="bookingUserId" type="text" uppercase={true} ></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">Shipment Status</label>
                                <ISelect defaultOption={true} name="bookingStatus" options={this.state.status} uppercase={true}/>
                        </div>
                    </Col>
                    {/* <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label ">SCC Group</label>
                                <Lov name="" lovTitle="Scc" isMultiselect={true} dialogWidth="600" dialogHeight="425" actionUrl="ux.showScc.do?formCount=1&multiselect=Y"
								closeButtonIds={['btnOk', 'btnClose']} 
								/>
                        </div>
                    </Col> */}
                    </Row>              
            </div>
        )
    }
}

export default wrapForm('awbDetailsFilterForm')(AwbDetailsFilter)