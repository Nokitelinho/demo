import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import PropTypes from 'prop-types';





class OnwardRouting extends React.Component {

    render() {
        try {
            const { index, addRow, deleteRow, numRows, form } = this.props;
            return (
                <div className="mar-b-2md">
                    <Row>
                        <Col xs="6">
                            <Row>
                                <Col>
                                    <Row className="form-row">
                                        <Col xs="8">
                                            <ITextField name={`${form}.${index}.carrierCode`} uppercase="true" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_CARRIERCODE' />
                                        </Col>
                                        <Col xs="16">
                                            <ITextField name={`${form}.${index}.flightNumber`} uppercase="true" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_FLIGHTNUM' />
                                        </Col>
                                    </Row>
                                </Col>
                               
                            </Row>
                        </Col>
                        <Col xs="6">
                        <DatePicker name={`${form}.${index}.flightDate`} componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_FLIGHTDATE' />
                               
                        </Col>
                        <Col xs="5">
                            <Lov name={`${form}.${index}.pol`} uppercase="true" lovTitle="Select Airport" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_POL' dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />
                        </Col>
                        <Col xs="5">
                            <Lov name={`${form}.${index}.pou`} uppercase="true" lovTitle="Select Airport" componentId='CMP_MAIL_OPERATIONS_MAILOUTBOUND_POU' dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />
                        </Col>
                        <Col className="align-self-center">
                            <div>
                                {(index < numRows - 1) ?
                                    <div className="d-inline-block">
                                        <a onClick={() => deleteRow(index)}>
                                            <i className="icon ico-minus-round align-middle"></i>
                                        </a>
                                    </div>
                                    :
                                    <div className="d-inline-block">
                                        <a onClick={() => addRow()}>
                                            <i className="icon ico-plus-round align-middle"></i>
                                        </a>
                                    </div>
                                }
                            </div>
                        </Col>
                    </Row>

                </div >
            )
        }
        catch (e) {
        }
    }

}
OnwardRouting.propTypes = {
    form: PropTypes.object,
    numRows: PropTypes.number,
    deleteRow: PropTypes.func,
    addRow: PropTypes.func,
    index: PropTypes.number,
}
export default icFieldArray('onwardRouting')(OnwardRouting)




