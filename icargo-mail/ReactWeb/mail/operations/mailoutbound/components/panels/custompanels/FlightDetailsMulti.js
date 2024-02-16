import React  from 'react';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';

class FlightDetailsMulti extends React.PureComponent {
    constructor(props) {
        super(props);         
    }  
    toggleView=()=> {
        this.props.toggleCarditDisplay();
    }
    openAllContainersPopup = () => {
        this.props.openAllContainersPopup();
    }
    openAllMailbagsPopup = () => {
        this.props.openAllMailbagsPopup();
    }
    render() {
        return (
            <div className="card-header">
                <Row className="align-items-center">
                    <Col>
                    <div className="position-relative">
                         <a href="#" onClick={this.toggleView}>
                            <i className={this.props.carditView === 'initial' ? "icon  ico-expand-blue " : "icon ico-arrow-expand-left-hover"}></i>
                        </a>
                        {/* <a href="#" className="slide-toggle" onClick={this.toggleView}><i className={this.props.carditView === 'initial' ? "icon ico-expand-blue" : "icon ico-arrow-expand-left-hover"}></i></a> */}
                        <h4>{(this.props.flightCarrierView === 'Flight')? 'Flights' : 'Carriers'}</h4> {}
                        </div>
                    </Col>
                    <Col xs="auto">
                        <a href="#" className="mar-r-2sm" onClick={this.openAllContainersPopup}><i className="icon ico-allcontainers16x16"></i></a>
                        <a href="#" className="mar-r-2sm" onClick={this.openAllMailbagsPopup}><i className="icon ico-allmailbags16x16"></i></a>
                    </Col>
                </Row>
            </div>
        )
    }
}
FlightDetailsMulti.propTypes = {
    toggleCarditDisplay: PropTypes.func,
    openAllContainersPopup: PropTypes.func,
    openAllMailbagsPopup: PropTypes.func,
    performFlightAction: PropTypes.func,
    carditView: PropTypes.string,
    flightCarrierView: PropTypes.string,
    displayMode: PropTypes.string,
    selectedFlights: PropTypes.number,
}
export default FlightDetailsMulti

