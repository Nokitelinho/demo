import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ISelect, ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class Routing extends React.Component{

    componentDidMount(){
        this.props.transportStageQualifierDefaulting(this.props.name);
    }
    render(){
    return (
        <ISelect disabled={this.props.disabled} name={this.props.name} options={this.props.options} />
    )
}
}

class RoutingTable extends React.Component {

    render() {

        let transportStageQualifier = [];

        if (!isEmpty(this.props.oneTimeTransportStage)) {
            transportStageQualifier = this.props.oneTimeTransportStage.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }

        try {
            const { row, index, addRow, deleteRow, numRows, form } = this.props;
            return (
                <Row>
                    <Col xs="5" md="3">
                        <div className="form-group">
                            <label className="form-control-label">Flight Number</label>
                            <Row className="form-row">
                                <Col xs="6">
                                    <ITextField disabled={this.props.diableMailbagLevel} name={`${form}.${index}.onwardCarrierCode`}  componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_CARRIERCODE" uppercase="true" />
                                </Col>
                                <Col>
                                    <ITextField disabled={this.props.diableMailbagLevel} name={`${form}.${index}.onwardFlightNumber`}  componentId="TXT_MAIL_OPERATIONS_CONSIGNMENT_FLIGHTNUMBER" onBlur={this.props.populateFlightNumber(index)} uppercase="true"/>
                                </Col>
                            </Row>
                        </div>
                    </Col>
                    <Col xs="4" md='3'>
                        <div className="form-group">
                            <label className="form-control-label">Dep Date</label>
                            
                        
                            {this.props.diableMailbagLevel? <ITextField className="form-control" name="onwardFlightDate" disabled={true}/>:
                             <DatePicker name={`${form}.${index}.onwardFlightDate`} componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_DEPDATE" />}
                        </div>
                    </Col>
                    <Col xs="3" md='2'>
                        <div className="form-group">
                            <label className="form-control-label">POL</label>
                            <Lov disabled={this.props.diableMailbagLevel} componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_POL" uppercase={true} name= {`${form}.${index}.pol`} lovTitle= "Select Airport"  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />
                        </div>
                    </Col>
                    <Col xs="3" md='2'>
                        <div className="form-group">
                            <label className="form-control-label">POU</label>
                            <Lov disabled={this.props.diableMailbagLevel} componentId="CMP_MAIL_OPERATIONS_CONSIGNMENT_POU" uppercase={true} name= {`${form}.${index}.pou`} lovTitle= "Select Airport"  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />
                        </div>
                    </Col>
                    <Col xs="3">
                        <div className="form-group">
                            <label className="form-control-label ">Transport Stage</label>
                                <Routing disabled={this.props.diableMailbagLevel || this.props.isDomestic} name={`${form}.${index}.transportStageQualifier`} options={transportStageQualifier} transportStageQualifierDefaulting={this.props.transportStageQualifierDefaulting}/>
                        </div>
                    </Col>
                    <Col xs='auto'>
                        {(index < numRows - 1) ?
                            <a onClick={() => {deleteRow(index); this.props.changeStyle(numRows-1);}} className="mar-t-2lg d-inline-block"><i className="icon ico-minus-round"></i></a>
                            :
                            <a onClick={() => {addRow(); this.props.changeStyle(numRows);} } className="mar-t-2lg d-inline-block"><i className="icon ico-plus-round"></i></a>
                        }
                    </Col>
                </Row>
            )
        }
        catch (e) {
            console.log(e);
        }
    }
}
export default icFieldArray('routingdetails')(RoutingTable)
