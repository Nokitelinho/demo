import React from 'react';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';

class ContainerCustome extends React.PureComponent {
    constructor(props) {
        super(props);
    }
   
    render() {
            return (
            <div className="card-header">
                <Row className="align-items-center">
                    <Col>
                        <h4>Containers</h4>
                    </Col>
                  
                    <Col xs="auto">
                        <div className="pad-r-sm">
                            <a href="#" onClick={this.props.allContainerIconAction}><i className="icon ico-maximize"></i></a>
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}
ContainerCustome.propTypes = {
    allContainerIconAction: PropTypes.func,
}
export default ContainerCustome