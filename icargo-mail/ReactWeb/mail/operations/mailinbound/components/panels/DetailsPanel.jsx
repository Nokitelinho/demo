import React, { Fragment } from 'react';
import { Row, Col} from "reactstrap";
import ContainerTableContainer from '../containers/containertablecontainer.js'
import MailbagTableContainer from '../containers/mailbagtablecontainer.js'
import FlightTableContainer from '../containers/flighttablecontainer.js'
import PropTypes from 'prop-types'

export default class DetailsPanel extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <Fragment>
                    {(this.props.displayMode === 'display') ? (
                    <div className="card">
                    <div className="mail-inbound-flight-details-list">
                        <FlightTableContainer />
                    </div>
                </div>
                    ) : ( // displayMode == 'multi'
                            (this.props.displayMode === 'multi') ? (
                            <div className="card">
                    <Row className="h-100">
                        <Col xs="7" className="pr-0 d-flex">
                            <div className="mail-inbound-flight-details-list-small">
                                <FlightTableContainer />
                            </div>
                        </Col>
                        <Col xs="9" className="p-0 border-left border-right d-flex">
                            <div className="mail-inbound-container-details-list-small">
                                <ContainerTableContainer />
                            </div>
                        </Col>
                        <Col xs="8" className="pl-0 d-flex">
                            <div className="mail-inbound-mailbag-details-list-small">
                                <MailbagTableContainer />
                            </div>
                        </Col>
                    </Row>
                    <a className="flight-multi-panel-close"><i data-mode="display" className="icon ico-close-panel" onClick={this.props.onChangeDetailPanelMode}></i></a>
                </div>
                            ) : null
                        )}

            </Fragment>

        )
    }
}

DetailsPanel.propTypes = {
    displayMode: PropTypes.string,
    onChangeDetailPanelMode: PropTypes.func
}

