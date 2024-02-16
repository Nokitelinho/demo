import React  from 'react';
import { Row, Col } from "reactstrap";
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import PropTypes from 'prop-types'

class FlightDetails extends React.PureComponent {
    constructor(props) {
        super(props);         
    }  

     commFlightAction = (event) => {
        let actionName = event.target.dataset.mode;
        let indexArray = [];
        let flightData=this.props.flightData.results;
        this.props.indexArray.forEach(function(element) {
            if (flightData[element].onlineAirportParam === 'N' && this.props.valildationforimporthandling === 'Y') {
                this.props.displayError('Function cannot be performed as ATD for the flight has not been captured from the preceding airport', '')   
            } 
            else{
                indexArray.push(element);
            }
        }, this);
        if (indexArray.length>0) {
        if(actionName==='CLOSE_FLIGHT'){
            let data={indexArray:indexArray,actionName:'CLOSE_FLIGHT',displayMode:this.props.displayMode,flightDetails:this.props.flightDetails}
            this.props.flightAction(data);
        }
        if(actionName==='OPEN_FLIGHT'){
            let data={indexArray:indexArray,actionName:'OPEN_FLIGHT',displayMode:this.props.displayMode,flightDetails:this.props.flightDetails}
            this.props.flightAction(data);
        }
        //this.props.flightAction({ type: actionName });
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }
    }
    
    render() {
      return ( 
            <Row className="align-items-center">
                <Col xs="auto">
                    <h4>Flights</h4>
                </Col>
                <Col className="text-right">
                    {(this.props.displayMode==='display')?
                        <button className="btn btn-link" onClick={this.props.allContainerAction}><i className="icon ico-allcontainers16x16 align-text-bottom m-0"></i> Containers Enquiry</button> :
                        <button className="btn btn-link m-0 pad-x-2xs" onClick={this.props.allContainerAction}><i className="icon ico-allcontainers16x16 m-0"></i></button>}
                    {(this.props.displayMode==='display')?
                        <button className="btn btn-link" onClick={this.props.allMailbagsAction}><i className="icon ico-allmailbags16x16 align-text-bottom m-0"></i> Mailbags Enquiry</button> :
                        <button className="btn btn-link m-0 pad-x-2xs" onClick={this.props.allMailbagsAction}><i className="icon ico-allmailbags16x16 m-0"></i></button>}
                </Col>
                    {(this.props.rowClkCount > 1) ?
                    <Col xs="auto">
                        <IDropdown>
                            <IDropdownToggle className="dropdown-toggle btn-link p-0 more-toggle">
                                    <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                </IDropdownToggle>
                                <IDropdownMenu right={true} >
                                    <IDropdownItem data-mode="CLOSE_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_CLOSE_FLIGHT" onClick={ this.commFlightAction} disabled={!this.props.flightActionsEnabled}> Close Flight  </IDropdownItem>
                                    <IDropdownItem data-mode="OPEN_FLIGHT" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_INBOUND_OPEN_FLIGHT" onClick={ this.commFlightAction} disabled={!this.props.flightActionsEnabled}> Open Flight </IDropdownItem>
                                </IDropdownMenu>
                        </IDropdown>
                    </Col> : null}
                </Row>
            
        )
    }
}

FlightDetails.propTypes = {
    allContainerAction:PropTypes.func,
    allMailbagsAction:PropTypes.func,
    flightAction:PropTypes.func,
    indexArray:PropTypes.array,
    displayMode:PropTypes.string,
    rowClkCount:PropTypes.number,
    flightDetails: PropTypes.object,
    displayError: PropTypes.func,
    valildationforimporthandling: PropTypes.string,
    flightData: PropTypes.object,

}
export default FlightDetails

