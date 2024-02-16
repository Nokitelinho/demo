import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import FlightListContainer from '../containers/flightlistcontainer';
import CarditLyinglistContainer from '../containers/carditlyinglistcontainer.js';
import PropTypes from 'prop-types';




export default class DetailsPanel extends Component {

    constructor(props) {
        super(props);
    }

    toggle=()=> {
        this.props.onExpandPanel();
    }

   
    render() {
        return (
            <div className="section-panel animated fadeInUp">
                {(this.props.carditView === 'initial') ? (
                    <Fragment>
                        <FlightListContainer toggleCarditDisplay={this.toggle} carditView={this.props.carditView} />
                    </Fragment>
                ) : (
                        <Row className="flex-grow-1">
                            <Col xs="6" className="d-flex">
                                <CarditLyinglistContainer></CarditLyinglistContainer>
                            </Col>
                            <Col xs="18" className="d-flex">
                                <FlightListContainer toggleCarditDisplay={this.toggle} carditView={this.props.carditView} />
                            </Col>
                        </Row>
                    )}
            </div>
        );
    }
}
DetailsPanel.propTypes = {
    onExpandPanel:PropTypes.func,
    carditView:PropTypes.string,
}