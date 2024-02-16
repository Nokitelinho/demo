import React, { Component , Fragment} from 'react';
import FlightDetailsTable from './FlightDetailsTable.jsx';
import CarrierDetailsTable from './CarrierDetailsTable.jsx';
import FlightDetailsMulti from './custompanels/FlightDetailsMulti.js';
import PropTypes from 'prop-types';

export default class FlightPanel extends Component {

    render() {
        return (
  
            <Fragment>
                <div className="card border-set-0" style={{ display: this.props.flightCarrierView === 'Flight' ? null : 'none' }}>

                    <div className="mail-outbound-flight-details-list-small" style={{ display: this.props.displayMode === 'multi' ? null : 'none' }}>
                        <FlightDetailsMulti flightCarrierView={this.props.flightCarrierView} carditView={this.props.carditView} toggleCarditDisplay={this.props.toggleCarditDisplay}
                            openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} />
                        <FlightDetailsTable flightDetails={this.props.flightlist} flights={this.props.flights} performFlightAction={this.props.performFlightAction} openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} getNewPage={this.props.onlistDetails} exportToExcel={this.props.exportToExcel} selectFlights={this.props.selectFlights} toggleCarditDisplay={this.props.toggleCarditDisplay} onApplyFlightFilter={this.props.onApplyFlightFilter} onClearFlightFilter={this.props.onClearFlightFilter} initialValues={this.props.initialValues} selectedMailflight={this.props.selectedMailflight} show={this.props.preadviceshow} flightValidation={this.props.flightValidation} selectedFlight={this.props.selectedFlight} onPreadviceOK={this.props.onPreadviceOK} onClickPrint={this.props.onClickPrint} updateSortVariables={this.props.updateSortVariables} flightActionsEnabled={this.props.flightActionsEnabled} carditView={this.props.carditView} flightCarrierView={this.props.flightCarrierView} selectedFlights={this.props.selectedFlights} flightCapacityDetails={this.props.flightCapacityDetails} stationWeightUnit={this.props.stationWeightUnit} {...this.props} />
                    </div>
                    <div className="mail-outbound-flight-details-list" style={{ display: this.props.displayMode === 'multi' ? 'none' : null }}>
                        <FlightDetailsTable flightDetails={this.props.flightlist} flightCarrierflag={this.props.flightCarrierflag} flights={this.props.flights} performFlightAction={this.props.performFlightAction} openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} getNewPage={this.props.onlistDetails} exportToExcel={this.props.exportToExcel} selectFlights={this.props.selectFlights} toggleCarditDisplay={this.props.toggleCarditDisplay} onApplyFlightFilter={this.props.onApplyFlightFilter} onClearFlightFilter={this.props.onClearFlightFilter} initialValues={this.props.initialValues}  selectedMailflight={this.props.selectedMailflight} show={this.props.preadviceshow} flightValidation={this.props.flightValidation} selectedFlight={this.props.selectedFlight} onPreadviceOK={this.props.onPreadviceOK} onClickPrint={this.props.onClickPrint} updateSortVariables={this.props.updateSortVariables} flightActionsEnabled={this.props.flightActionsEnabled} carditView={this.props.carditView} flightCarrierView={this.props.flightCarrierView} selectedFlights={this.props.selectedFlights} flightCapacityDetails={this.props.flightCapacityDetails} stationWeightUnit={this.props.stationWeightUnit} {...this.props} />
                    </div>
                </div>
                <div className="card border-set-0" style={{ display: this.props.flightCarrierView === 'Flight' ? 'none' : null }}>

                    <div className="mail-outbound-flight-details-list-small" style={{ display: this.props.displayMode === 'multi' ? null : 'none' }}>
                        <FlightDetailsMulti flightCarrierView={this.props.flightCarrierView} carditView={this.props.carditView} toggleCarditDisplay={this.props.toggleCarditDisplay}
                            openAllMailbagsPopup={this.props.openAllMailbagsPopup} openAllContainersPopup={this.props.openAllContainersPopup} />
                        <CarrierDetailsTable carrierDetails={this.props.carrierlist} carriers={this.props.carriers} getNewPage={this.props.onlistDetails} flightCarrierflag={this.props.flightCarrierflag} selectFlights={this.props.selectFlights} selectedMailflight={this.props.selectedMailflight} updateCarrierSortVariables={this.props.updateCarrierSortVariables} initialCarrierValues= {this.props.initialCarrierValues} onApplyCarrierFilter= {this.props.onApplyCarrierFilter} onClearCarrierFilter={this.props.onClearCarrierFilter} {...this.props} />
                    </div>
                    <div className="mail-outbound-flight-details-list" style={{ display: this.props.displayMode === 'multi' ? 'none' : null }}>
                        <CarrierDetailsTable carrierDetails={this.props.carrierlist} carriers={this.props.carriers} getNewPage={this.props.onlistDetails} selectFlights={this.props.selectFlights} selectedMailflight={this.props.selectedMailflight} updateCarrierSortVariables={this.props.updateCarrierSortVariables} onApplyCarrierFilter= {this.props.onApplyCarrierFilter} onClearCarrierFilter={this.props.onClearCarrierFilter} initialCarrierValues= {this.props.initialCarrierValues}  {...this.props} />
                    </div>
                </div>
            </Fragment>
        );
    }
}
FlightPanel.propTypes = {
    flightCarrierView:PropTypes.string,
    displayMode:PropTypes.string,
    openAllMailbagsPopup:PropTypes.func,
    carditView:PropTypes.string,
    toggleCarditDisplay:PropTypes.func,
    flightlist:PropTypes.array,
    flights:PropTypes.object,
    openAllContainersPopup:PropTypes.func,
    performFlightAction:PropTypes.func,
    onlistDetails:PropTypes.func,
    exportToExcel:PropTypes.func,
    selectFlights:PropTypes.func,
    onApplyFlightFilter:PropTypes.func,
    onApplyCarrierFilter:PropTypes.func,
    onClearFlightFilter:PropTypes.func,
    initialValues:PropTypes.object,
    selectedMailflight:PropTypes.object,
    preadviceshow:PropTypes.string,
    flightValidation:PropTypes.string,
    selectedFlight:PropTypes.array,
    onPreadviceOK:PropTypes.func,
    onClickPrint:PropTypes.func,
    updateSortVariables:PropTypes.func,
    flightActionsEnabled:PropTypes.string,
    selectedFlights:PropTypes.array,
    flightCapacityDetails:PropTypes.object,
    carrierlist:PropTypes.array,
    carriers:PropTypes.array,   
	updateCarrierSortVariables:PropTypes.func,
    flightCarrierflag:PropTypes.object
}