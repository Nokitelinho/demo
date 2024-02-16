import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class OnwardRouting extends React.Component {

    render() {
        try {
            const { row, index, addRow, deleteRow, numRows } = this.props;
            return (
                <div className="mar-b-2md">
                    <Row>
                        <Col xs="16">
                            <Row>
                                <Col xs="4">
                                    <ITextField name={`${row}.onwardCarrierCode`} uppercase={true} />
                                </Col>
                                <Col xs="8">
                                    <ITextField name={`${row}.flightNumber`} />
                                </Col>
                                <Col xs="12">
                                    <DatePicker name={`${row}.onwardFlightDate`} />
                                </Col>
                            </Row>
                        </Col>
                        <Col xs="6">
                            <Lov name={`${row}.pou`} componentId='CMP_Operations_FltHandling_ExportManifest_ThruRouting_Destination' uppercase={true} lovTitle="Airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
                        </Col>
                        <Col xs="col align-self-center">
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
                    </Row >
                </div>
            )
        }
        catch (e) {
            console.log(e);
        }
    }

}
export default icFieldArray('onwardRouting')(OnwardRouting)




