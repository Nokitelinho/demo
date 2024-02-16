import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { ITextField, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';


class OtherDetails extends Component {

    constructor(props) {
        super(props);
        this.typeChange = this.typeChange.bind(this);
    }
    
    typeChange = (event) => {
        console.log(event.value);
        let select  = event.value;
        // let select = '';
        // event[3]?select = event[0]+event[1]+event[2]+event[3]:select = event[0]+event[1]+event[2]
        // console.log(select);
         if(select==='CN46'){
             this.props.changeSubTypeFlag(false);
         }else{
            this.props.changeSubTypeFlag(true);
         } 
    }

    render() {

        let type = [];
        let subtype = [];
        let flighttype = [];
        //onwardCarrierCode = this.props.routingdetails?this.props.routingdetails[0].onwardCarrierCode:"";
        if (!isEmpty(this.props.oneTimeType)) {
            type = this.props.oneTimeType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));

        }

        if (!isEmpty(this.props.oneTimeSubType)) {
            subtype = this.props.oneTimeSubType.map((value) => ({ value: value.fieldValue, label: value.fieldValue }));

        }

        if (!isEmpty(this.props.oneTimeFlightType)) {
            flighttype = this.props.oneTimeFlightType.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));

        }

        return (
            <Fragment>
                <Row>
                    <Col xs="4" md="3">
                        <div className="form-group">
                            <label className="form-control-label">Consignment Date</label>
                           
                            {this.props.diableMailbagLevel? <ITextField className="form-control" name="consignmentdate" disabled={true}/>:
                              <DatePicker componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_CONDATE" name="consignmentdate"/>}
                        
                        </div>
                    </Col>
                    <Col xs="3" md="3">
                        <div className="form-group">
                            <label className="form-control-label">Consignment Type</label>
                            <ISelect disabled={this.props.diableMailbagLevel} defaultOption={true} componentId="CMB_MAIL_OPERATIONS_CONSIGNMENT_TYPE" name="consignmenttype" options={type} onOptionChange={this.typeChange}/>
                        </div>
                    </Col>
                    <Col xs="3" md="2">
                        <div className="form-group">
                            <label className="form-control-label">Sub Type</label>
                            <ISelect  defaultOption={true} componentId="CMB_MAIL_OPERATIONS_CONSIGNMENT_SUBTYPE" name="consignmentsubtype" options={subtype} disabled={this.props.typeFlag}/>
                        </div>
                    </Col>
                    <Col xs="3" md="2">
                        <div className="form-group">
                            <label className="form-control-label">Type</label>
                            <ISelect disabled={this.props.diableMailbagLevel} componentId="CMB_MAIL_OPERATIONS_CONSIGNMENT_IBOBTYPE" name="flighttype" options={flighttype} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label" >AWB No</label>
                            <IAwbNumber reducerName="awbReducer"  uppercase={true} hideLabel={true} form="true" disabled={true} name ="awbnumber"/>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label">Remarks</label>
                            <ITextField disabled={this.props.diableMailbagLevel} componentId="TXT_MAIL_OPERATIONS_UX_CONSIGNMENT_REMARKS" uppercase={true} name='remarks' type="text" value="abc" />
                        </div>
                    </Col>
                </Row>
            </Fragment>
        )
    }
}
export default wrapForm('OtherDetails')(OtherDetails);