import React from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements'

class RSNPanel extends React.Component {

    constructor(props) {
        super(props);
        this.calculateReseptacles = this.calculateReseptacles.bind(this);
    }
    calculateReseptacles(index) {
        this.props.getAddMultipleData();
        this.props.calculateReseptacles(this.props.rsnData, index);
    }

    render() {
        const { index, addRow, deleteRow, numRows, form } = this.props;

        return (
            <Row>
                <Col xs="8">
                    <div className="form-group">
                        <label className="form-control-label">From</label>
                        <ITextField maxlength="3" mode="edit" name={`${form}.${index}.from`} type="text" onBlur={this.calculateReseptacles(index)}></ITextField>
                    </div>
                </Col>
                <Col xs="8">
                    <div className="form-group">
                        <label className="form-control-label">To</label>
                        <ITextField maxlength="3" mode="edit" name={`${form}.${index}.to`} type="text" onBlur={this.calculateReseptacles(index)}></ITextField>
                    </div>
                </Col>
                <Col>
                    <div className="form-group">
                        <label className="form-control-label">Receptacles</label>
                        <ITextField mode="edit" name={`${form}.${index}.receptacles`} type="text" disabled={true}></ITextField>
                    </div>
                </Col>
                <Col xs='auto'>
                     <div className="form-group">
                        {(index < numRows - 1) ?
                            <a onClick={() => deleteRow(index)} className="mar-t-2lg d-inline-block"><i className="icon ico-minus-round"></i></a>
                            :
                            <a onClick={() => addRow()} className="mar-t-2lg d-inline-block"><i className="icon ico-plus-round"></i></a>
                        }
                    </div>
                    </Col>
            </Row>
        )
    }
}
export default icFieldArray('RSNPanelForm')(RSNPanel)