import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ChangeStatusPopupPanel from '../popup/ChangeStatusPopupPanel.jsx'
import SurchargePopupPanel  from '../popup/SurchargePopupPanel.jsx';
import BillingDetailsTablePanel from './BillingDetailsTablePanel.jsx';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
export class PopupPanel extends React.Component {
    constructor(props) {
        super(props)
    }

    changeStatusPopulation=(billingStatus)=>{
        let status=null;
        let data=null;
        
        if (!isEmpty(this.props.oneTimeValuesStatus)) {
                 status =this.props.oneTimeValuesStatus['mailtracking.mra.gpabilling.gpabillingstatus'].map((value) => (
                     { value: value.fieldValue, label: value.fieldDescription }));
                status.forEach(function(element) {
                    if(element.label.toUpperCase()==billingStatus.toUpperCase()){
                        billingStatus=element.label;
                        data=element;
                    }
                }, this);
             }
            return data;
    }
    render() {
        return (
            <div>

                <ChangeStatusPopupPanel show={this.props.showChangeStatusPopup}
                    toggleFn={this.props.closeChangeStatusPopup}
                    dsn={this.props.dsn}
                    remarks={this.props.remarks} 
                    billingStatus={this.props.billingStatus}
                    oneTimeValuesStatus={this.props.oneTimeValuesStatus}
                    closeChangeStatusPopup={this.props.closeChangeStatusPopup}
                    saveChangeStatusPopup={this.props.saveChangeStatusPopup} 
                    initialValues={{billingStatus:this.changeStatusPopulation(this.props.billingStatus), remarks:this.props.remarks}} />
                <SurchargePopupPanel show={this.props.showSurchargePopup}
                    toggleFn={this.props.closeSurchargePopup}
                    surchargeDetails={this.props.surchargeDetails}
                    closeSurchargePopup={this.props.closeSurchargePopup}/>
                <BillingDetailsTablePanel show={this.props.comparerow}
                    toggleFn={this.props.closeBillingPopup}
                    mailbagsdetailslist={this.props.mailbagsdetailslist}
                    listmailbagdetails={this.props.listmailbagdetails}
                    initialValues={this.props.initialValues}/>  

            </div>
        );
    }
}
PopupPanel.propTypes = {
}