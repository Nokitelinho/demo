import React, { Component } from 'react';
import RoutingTable from './RoutingTable.jsx';
import OtherDetails from './OtherDetails.jsx';

export default class AddConsignment extends Component {

    changeStyle = (numRows) => {
        this.props.changeStyle(numRows);
    }

    render() {
        return (
            <div className="pad-md pad-b-3xs">
                <div className="pad-b-3xs">
                    <OtherDetails oneTimeType={this.props.oneTimeType} oneTimeFlightType={this.props.oneTimeFlightType} oneTimeSubType={this.props.oneTimeSubType}
                        initialValues={this.props.initialValues} typeFlag={this.props.typeFlag} changeSubTypeFlag={this.props.changeSubTypeFlag} diableMailbagLevel={this.props.diableMailbagLevel}/>
                </div>
                <div className="pad-b-2xs border-bottom mar-b-2sm font-weight-bold">Add Routing</div>
                <div className="row" style={this.props.style}>
                    <div className="col">
                        <RoutingTable initialValues={this.props.initialValues} changeStyle={this.changeStyle} populateFlightNumber={this.props.populateFlightNumber}  diableMailbagLevel={this.props.diableMailbagLevel} oneTimeTransportStage={this.props.oneTimeTransportStage} transportStageQualifierDefaulting={this.props.transportStageQualifierDefaulting} isDomestic={this.props.isDomestic}/>
                    </div>
                </div>
            </div>
        );
    }
}