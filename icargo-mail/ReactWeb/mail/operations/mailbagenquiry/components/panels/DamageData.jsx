import React, { Fragment } from 'react';
import { Row, Col } from 'reactstrap'
import { icFieldArray } from 'icoreact/lib/ico/framework/component/common/form';
import { ITextField ,ISelect, IFile} from 'icoreact/lib/ico/framework/html/elements';

class DamageData extends React.Component {

    render() {
       
        try {
            const {row,index,addRow,deleteRow,numRows } = this.props;
            return(
                <Fragment>
                    <Row>
                        <Col>
                            <Row>
                            <Col xs="10" >
                                    <div className="form-group">
                                        <label className="form-control-label">Damage Code</label>
                            <ISelect portal={true} name={`${row}.damageCode`} options={this.props.damageCodes} />
                                    </div>
                            </Col>
                                <Col>
                                    <div className="form-group">
                                        <label className="form-control-label">Remarks</label>
                                <ITextField uppercase={true} name={`${row}.remarks`} />
                                    </div>
                            </Col>
                            <Col>
                                    <div className="form-group">
                                        <label className="form-control-label">Upload File</label>
										<IFile name={`${row}.fileData`} fileIndex={index} fileNameMaxLength={25} children="Choose File"/>
                                        
                                    </div>
                            </Col>		 
                        </Row>
                    </Col >                    
                        <Col xs="auto" className="align-self-center">
                            {(index < numRows - 1) ?
                                <a onClick={() => deleteRow(index)}>
                                        <i className="icon ico-minus-round align-middle" ></i>
                                    </a>
                                :
                                <a onClick={() => addRow()}>
                                        <i className="icon ico-plus-round align-middle"></i>
                                    </a>
                            }
                    </Col>
                    </Row >
                </Fragment>
            )
        }
        catch (e) {
            console.log(e);
        }
    }

}
export default icFieldArray('damageData')(DamageData)




