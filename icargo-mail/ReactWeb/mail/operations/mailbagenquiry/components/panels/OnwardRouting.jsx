import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'

class OnwardRouting extends React.Component {

    render() {

        try {
            const { row, index, addRow, deleteRow, numRows } = this.props;
            return (
                <Row>
                    <Col>
                        <Row>
                        <Col xs="9">
                        <div className="form-group">
                            <label className="form-control-label">Flight Number</label>
                            <Row className="form-row">
                                <Col>
                                    <ITextField name={`${row}.onwardCarrierCode`}  uppercase="true" maxlength="3"/>
                                </Col>
                                <Col>
                                    <ITextField name={`${row}.onwardFlightNumber`}  maxlength="5" />
                                </Col>
                            </Row>
                        </div>
                    </Col>
                    <Col xs="9">
                        <div className="form-group">
                            <label className="form-control-label">Dep Date</label>
                            <DatePicker name={`${row}.onwardFlightDate`} />
                        </div>
                    </Col>
                            <Col xs="6">
                                <div className="form-group">
                                    <label className="form-control-label">POU</label>
                                    <ITextField uppercase={true} name={`${row}.pou`} />
                                </div>
                            </Col>

                        </Row>
                    </Col >
                    <Col xs="auto" className="align-self-center">
                        <div className="">
                            {(index < numRows - 1) ?
                                <div className="d-inline-block align-top mar-l-2xs">
                                    <a onClick={() => deleteRow(index)} className="text-link">
                                        <i className="icon ico-minus-round align-middle" ></i>
                                    </a>
                                </div>
                                :
                                <div className="d-inline-block align-top">
                                    <a onClick={() => addRow()} className="text-link">
                                        <i className="icon ico-plus-round align-middle"></i>
                                    </a>
                                </div>
                            }
                        </div>
                    </Col>
                </Row >
            )
        }
        catch (e) {
            console.log(e);
        }
    }

}
export default icFieldArray('onwardRouting')(OnwardRouting)




