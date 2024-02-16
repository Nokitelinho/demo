import React, { Component, Fragment } from 'react';
import FlightDetailsTable from './FlightDetailsTable.jsx';
import LoadPlanDetailsTable from './LoadPlanDetailsTable.jsx';

export default class DetailsPanel extends Component {

    render() {

        return (

                
            <Fragment>
                {this.props.activeTab === 'BookingView' &&
                    <div class="card animated fadeInUp">

                        <FlightDetailsTable flightDetails={this.props.flightDetails} getNewPage={this.props.listFlightDetails}
                            saveSelectedFlightIndex={this.props.saveSelectedFlightIndex} />
                    </div>}
                {this.props.activeTab === 'LoadPlanView' &&
                    <div class="card animated fadeInUp">

                        <LoadPlanDetailsTable flightDetails={this.props.flightDetails} getNewPage={this.props.listFlightDetails}
                            saveSelectedFlightIndex={this.props.saveSelectedFlightIndex} />
                    </div>}

            </Fragment>

        )
    }
}