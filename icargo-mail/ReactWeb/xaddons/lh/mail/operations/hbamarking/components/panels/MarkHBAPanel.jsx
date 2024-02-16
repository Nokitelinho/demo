import React from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Label, Row } from 'reactstrap'
import { IRadio } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class MarkHBAPanel extends React.Component {

    constructor(props) {
        super(props);
       
    }

    onPositionChange = (event) => {
        this.props.changePosition(event.target.value)

    }
    onHbaTypeChange = (event) => {
        this.props.changeHbaType(event.target.value)
    }

    render() {
        let hbaTypes = [];
        let hbaPositions = [];
      
        if (!isEmpty(this.props.oneTimeValues)) {
            hbaTypes = this.props.oneTimeValues['mail.operations.hbatype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription} ));
            hbaPositions = this.props.oneTimeValues['mail.operations.hbaposition'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription}));
        }
        return (

            <div>
                
                    <div className="pad-md">
                        <Row>
                            <Col xs="10">
                            <Label>Position:</Label>
                            <IRadio name="position" options={hbaPositions}  onChange={this.onPositionChange}  />
  
                            </Col>
                         
                        </Row>
                        <Row>
                        <Col xs="10">
                            <Label>HBA Type:</Label>
                            <IRadio name="hbaType" options={hbaTypes}  onChange={this.onHbaTypeChange}  />
                            </Col>
                        </Row>
                    </div>
                    
                    
            </div>
        )
    }
}


export default wrapForm('markHbaForm')(MarkHBAPanel)
