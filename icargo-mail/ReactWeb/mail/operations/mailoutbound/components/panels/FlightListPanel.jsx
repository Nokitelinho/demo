import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';
import FlightTableContainer from '../containers/flighttablecontainer.js'
import ContainerTableContainer from '../containers/containertablecontainer.js';
import MailBagTableContainer from '../containers/mailbagtablecontainer.js'
class FlightListPanel extends Component {
    constructor(props) {
        super(props);

    }
    

    render() {
        return (
            <Fragment>
                { this.props.displayMode === 'display' ? (
                <div className="flex-grow-1 flex-only">
                    <FlightTableContainer displayMode ={this.props.displayMode} openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} toggleCarditDisplay={this.props.toggleCarditDisplay} selectFlights={this.props.selectFlights} onApplyFlightFilter={this.props.onApplyFlightFilter} onApplyCarrierFilter =  {this.props.onApplyCarrierFilter} onClearFlightFilter={this.props.onClearFlightFilter} onClearCarrierFilter={this.props.onClearCarrierFilter} carditView={this.props.carditView} />
                </div>)
                :''}
                { this.props.displayMode === 'multi' ? (
                <div className="card">
                    <Row className="flex-grow-1">
                        <Col className="pr-0 d-flex for-border-set">
                            <FlightTableContainer displayMode ={this.props.displayMode} openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} toggleCarditDisplay={this.props.toggleCarditDisplay} selectFlights={this.props.selectFlights} onApplyFlightFilter={this.props.onApplyFlightFilter}  onApplyCarrierFilter =  {this.props.onApplyCarrierFilter} onClearFlightFilter={this.props.onClearFlightFilter} onClearCarrierFilter={this.props.onClearCarrierFilter} carditView={this.props.carditView} />
                        </Col>
                        <Col className="p-0 border-left border-right d-flex">
                            <ContainerTableContainer selectFlights={this.props.selectFlights} />
                        </Col>
                        <Col className="pl-0 d-flex">
                            <MailBagTableContainer toggleFlightPanel={this.props.toggleFlightPanel} displayMode={this.props.displayMode} selectFlights={this.props.selectFlights} />
                        </Col>
                    </Row>
                    <a className="flight-mulit-panel-close"><i data-mode="display" className="icon ico-close-panel" onClick={this.props.toggleFlightPanel}></i></a>
                </div>
                ): ''}
            </Fragment >
        );
    }
}
FlightListPanel.propTypes = {
    displayMode:PropTypes.string,
    openAllMailbagsPopup:PropTypes.func,
    openAllContainersPopup:PropTypes.func,
    toggleCarditDisplay:PropTypes.func,
    selectFlights:PropTypes.array,
    onApplyFlightFilter:PropTypes.func,
    onApplyCarrierFilter:PropTypes.func,
    onClearFlightFilter:PropTypes.func,
    onClearCarrierFilter:PropTypes.func,
    carditView:PropTypes.string,
    toggleFlightPanel:PropTypes.func
}
export default FlightListPanel;

