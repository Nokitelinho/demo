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
            const { row, index, addRow, deleteRow, numRows } = this.props;
            return (
                <div className="mar-b-2md">
                    {this.props.fromPopup === "Transfer" ?
                        <Row>
                            <Col xs="8">
                                <Row>
                                    <Col xs="4">
                                        <ITextField name={`${row}.carrierCode`} />
                                    </Col>
                                    <Col xs="8">
                                        <ITextField name={`${row}.flightNumber`} />
                                    </Col>
                                    <Col xs="12">
                                        <DatePicker name={`${row}.flightDate`} />
                                    </Col>
                                </Row>
                            </Col >
                            <Col xs="6">
                                <Lov name={`${row}.destination`} componentId='CMP_Operations_FltHandling_ExportManifest_ThruRouting_Destination'
                                    lovTitle="Airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                            </Col>
                            <Col xs="align-self-center">
                                <div>
                                    {(index < numRows - 1) ?
                                        <div className="d-inline-block">
                                            <a onClick={() => deleteRow(index)}>
                                                <i className="icon ico-minus-round align-middle" ></i>
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
                        :
                        <Row>
                            <Col xs="8">
                                <Row>
                                    <Col xs="12">
                                        <Row className="form-row">
                                            <Col xs="8">
                                                <ITextField name={`${row}.carrierCode`} uppercase={true} />
                                            </Col>
                                            <Col xs="16">
                                                <ITextField name={`${row}.flightNumber`} />
                                            </Col>
                                        </Row>
                                    </Col>
                                    <Col xs="12">
                                        <DatePicker name={`${row}.flightDate`} />
                                    </Col>
                                </Row>
                            </Col>
                            <Col xs="4">
                                <ITextField name={`${row}.pol`} uppercase={true} />
                            </Col>
                            <Col xs="4">
                                <ITextField name={`${row}.pou`} uppercase={true} />
                            </Col>
                            <Col className="align-self-center">
                                <div>
                                    {(index < numRows - 1) ?
                                        <div className="d-inline-block">
                                            <a onClick={() => deleteRow(index)}>
                                                <i className="icon ico-minus-round align-middle" ></i>
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
                    }

                </div>

            )
        }
        catch (e) {
        }
    }

}
OnwardRouting.propTypes = {
    fromPopup: PropTypes.string,
    row: PropTypes.object,
    index: PropTypes.number,
    addRow: PropTypes.func,
    deleteRow: PropTypes.func,
    numRows: PropTypes.number
}
export default icFieldArray('onwardRouting')(OnwardRouting)




