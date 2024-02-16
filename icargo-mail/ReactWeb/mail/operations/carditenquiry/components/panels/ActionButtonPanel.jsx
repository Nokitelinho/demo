import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import { isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import {IPrintMultiButton} from 'icoreact/lib/ico/framework/component/common/printmultibutton';




class ActionButtonPanel extends Component {

    constructor(props) {
        super(props);  
      this.loadListAWBPopup = this.loadListAWBPopup.bind(this);
    }
    filterArray = (reportId) => {
        const { ooe, doe, mailSubclass, despatchSerialNumber, conDocNo, fromDate, toDate,
            paCode, documentNumber, awbAttached, airportCode, mailStatus } = this.props.filterValues;
        const { flightNumber: { carrierCode, flightNumber } } = this.props.filterValues &&
            this.props.filterValues.flightnumber ? this.props.filterValues : { 'flightNumber': [{ 'carrierCode': '' }, { 'flightNumber': '' }] };
        let awbAttachedVal = awbAttached ? (awbAttached == "Y" ? "Yes" : "No") : "";
        let filterArray = [ooe ? ooe : "", doe ? doe : "", mailSubclass ? mailSubclass : "", despatchSerialNumber ? despatchSerialNumber : "", conDocNo ? conDocNo : "", fromDate ? fromDate : "", toDate ? toDate : "",
        paCode ? paCode : "", documentNumber ? documentNumber : "", awbAttachedVal, carrierCode ? carrierCode : "", flightNumber ? flightNumber : "",
            "", "", "", "", airportCode ? airportCode : "", mailStatus ? mailStatus : "", "", ""];
        return filterArray;
    }
    onClose=()=> {

this.props.onClose();

}	 

      loadListAWBPopup() {
        this.props.loadListAWBPopup();
       }

    render() {


        return (
            <div>

                {isSubGroupEnabled('KE_SPECIFIC') &&
                    <IPrintMultiButton category='default' screenId='MTK056' filterArray={this.filterArray} >Print Misc Reports</IPrintMultiButton>}	
                <IButton className="btn btn-default" category="primary" privilegeCheck={true} componentId ='CMP_MAIL_OPERATIONS_CARDITENQUIRY_LIST_AWB' onClick ={this.loadListAWBPopup}>List AWB</IButton>
              <IButton category="primary"  bType="DETACHAWB" componentId='CMP_Mail_Operations_CarditEnquiry_DetachAWB' onClick={this.props.detachAWB}>Detach AWB</IButton>
				<IButton category="primary" className="btn btn-primary"  componentId='CMP_Mail_Operations_CarditEnquiry_ListFlight' onClick={this.props.listFlightDetails}>List Flights</IButton>
                <IButton category="default" bType="CLOSE" accesskey="O" onClick={this.onClose} >Close</IButton>


            </div>
        );
    }
}
ActionButtonPanel.propTypes = {
    onClose: PropTypes.func
}
export default ActionButtonPanel;