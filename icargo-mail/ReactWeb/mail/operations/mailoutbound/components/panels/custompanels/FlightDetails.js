import React from 'react';
import { Row, Col } from "reactstrap";
import {  IDropdownToggle, IDropdownItem, IDropdownMenu, IButtonDropdown } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
class FlightDetails extends React.PureComponent {
    constructor(props) {
        super(props);
    }
    toggleView = () => {
        this.props.toggleCarditDisplay();
    }
    openAllContainersPopup = () => {
        this.props.openAllContainersPopup();
    }
    openAllMailbagsPopup = () => {
        this.props.openAllMailbagsPopup();
    }
    multiButtonClick=()=>{
        this.props.performFlightAction({type: 'CLOSE_FLIGHT'});
    }
    flightMultipleAction=(event)=>{
        let actionName = event.target.dataset.mode
        this.props.performFlightAction({ type: actionName});
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();

    }



    render() {
        return (
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
               
                {(this.props.displayMode === 'display') ?
                    <Col xs="auto">
                        <IButton category="link" disabled={this.props.flightActionsEnabled === 'false'} onClick={this.openAllContainersPopup}><i className="icon ico-allcontainers16x16 align-text-bottom m-0"></i> Containers Enquiry</IButton>
                        <IButton category="link" disabled={this.props.flightActionsEnabled === 'false'} onClick={this.openAllMailbagsPopup}><i className="icon ico-allmailbags16x16 align-text-bottom m-0"></i> Mailbags Enquiry</IButton>
                    </Col> :
                    <Col xs="auto">
                        <a href="#" className="mar-r-2sm"><i className="icon ico-allcontainers16x16 align-text-bottom"></i></a>
                        <a href="#" className="mar-r-2sm"><i className="icon ico-allmailbags16x16 align-text-bottom"></i></a>
                    </Col>}
                {(this.props.selectedFlights&&this.props.selectedFlights.length > 1) ?
                    <Col xs="auto">
                         <IButtonDropdown id="actions" split={true}
                         text="Close Flight"
                         category="default"
                         className="ic-multibutton"
                         onClick={this.multiButtonClick}
                         >
                            <IDropdownToggle className="dropdown-toggle btn btn-link no-pad more-toggle">
                                {/*<a href="#"><i className="icon ico-v-ellipsis"></i></a>*/}
                            </IDropdownToggle>
                            <IDropdownMenu right={true} >
                                <IDropdownItem data-mode="REOPEN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_REOPEN_FLIGHT" onClick={this.flightMultipleAction}>Open Flight</IDropdownItem>
                            </IDropdownMenu>
                        </IButtonDropdown>
                    </Col> : null}
            </Row>
        )
    }
}
FlightDetails.propTypes = {
    toggleCarditDisplay: PropTypes.func,
    openAllContainersPopup: PropTypes.func,
    openAllMailbagsPopup: PropTypes.func,
    performFlightAction: PropTypes.func,
    carditView: PropTypes.string,
    flightCarrierView: PropTypes.string,
    displayMode: PropTypes.string,
    selectedFlights: PropTypes.number,
}
export default FlightDetails

