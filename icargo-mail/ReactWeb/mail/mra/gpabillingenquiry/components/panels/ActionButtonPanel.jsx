import React, { Component } from 'react';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
const Aux =(props) =>props.children;


export default class ActionButtonPanel extends React.Component {

    constructor(props) {
        super(props);

    }

    onReRate=()=> {
       this.props.onReRate();
     }
    onCloseFunction=() =>{
       this.props.onCloseFunction(); 
    }
    navigateToRouting=() =>{
        const mailbag = this.props.mailbagsdetails.mailbagID;
        const data={mailbag}
        this.props.navigateToRouting(data);
    }
    onSurchargeDetailsPopup=() =>{
       this.props.onSurchargeDetailsPopup();
    }
    onChangeStatusPopup=() =>{
        this.props.onChangeStatusPopup();
    }
    onAutoMCA=() =>{
        this.props.onAutoMCA();
    }
    onListAccountingEntries=()=>{
        this.props.onListAccountingEntries();
    }
    render() {
        let isListed = this.props.displayMode !=="initial";    
        let automcaPrivilege = this.props.mcaPrivilege !=="Y";
        return (
            <Aux>
               <div class="footer-panel">
                    <IButton category="primary" onClick={this.onListAccountingEntries} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_RERATE'>List Acc.Entries</IButton>
                    <IButton category="primary" onClick={this.onAutoMCA} disabled={!automcaPrivilege && !isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_RERATE'>AutoMCA</IButton>
                    <IButton category="primary" onClick={this.onReRate} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_RERATE'>Re-Rate</IButton>
                    <IButton category="primary" onClick={this.navigateToRouting} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_ROUTE' >Routing</IButton>
                    <IButton category="primary" onClick={this.onSurchargeDetailsPopup} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_SURCHARGE' >Surcharge Details</IButton>
                    <IButton category="primary" onClick={this.onChangeStatusPopup} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_STATUS' >Change Billing Status</IButton>
                    <IButton category="default" bType="CLOSE" accesskey="O"  onClick={this.onCloseFunction} disabled={!isListed} componentId='CMP_MAIL_MRA_GPABILLINGENQUIRY_CLOSE' >Close</IButton>

                </div> 
            </Aux>
        );
    }
}
