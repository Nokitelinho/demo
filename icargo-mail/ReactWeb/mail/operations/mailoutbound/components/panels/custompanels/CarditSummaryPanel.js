import React  from 'react';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';

class CarditSummary extends React.PureComponent {
    constructor(props) {
        super(props);         
    }  
    render() {
        return (
            <div className="pad-2xs border-bottom">
                <Row>
                    <Col>
                        <div className="row m-0 bg-light" style={{ height: '40px' }}>
                            <div className="col-8 d-flex align-items-center justify-content-center" style={{ background: '#56bdde' }}><i className="icon ico-mailbag16x16-alt"></i></div>
                            <div className="col-auto align-self-center fs20">
                                {this.props.summary &&this.props.summary.count? this.props.summary.count:0}
                            </div>
                        </div>
                    </Col>
                    <Col>
                        <div className="row m-0 bg-light" style={{ height: '40px' }}>
                            <div className="col-8 d-flex align-items-center justify-content-center" style={{ background: '#b198dc' }}><i className="icon ico-weight-generic-alt-16x16"></i></div>
                            <div className="col-auto align-self-center fs20">
                                {this.props.summary ? this.props.summary.weight ? this.props.summary.weight.roundedDisplayValue:0:0}
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}
CarditSummary.propTypes = {
    summary: PropTypes.object,
}
export default CarditSummary
// {this.props.carditSummary.weight.formattedDisplayValue} {this.props.carditSummary.weight.systemUnit}

