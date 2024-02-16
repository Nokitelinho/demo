import React, { Component } from 'react';
import FlightCarrierContainer from '../containers/flightcarriercontainer.js';

class FilterContainer extends Component {

    render() {
        return (
            <div className="header-panel animated fadeInDown">
                <div className="header-filter-panel flippane">
                    <div className="pad-x-md pad-t-xs pad-b-3xs">
                        <FlightCarrierContainer />
                    </div>
                </div>
            </div>
        );
    }


}


export default FilterContainer