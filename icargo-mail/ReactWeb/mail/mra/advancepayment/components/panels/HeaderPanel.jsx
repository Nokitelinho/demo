import React from 'react';
import { Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { Key,ComponentId } from '../../constants/constants.js';
import {IMessage } from 'icoreact/lib/ico/framework/html/elements';

export default class HeaderPanel extends React.Component {
    constructor(props) {
        super(props);
        
        this.onAddPayment = this.onAddPayment.bind(this);
   }
   onAddPayment() {
    this.props.onAddPayment(); 
    }
    render() {
        return (
            <div className="card-header card-header-action no-border p-0">
                
                   <Col><h4>Payment Batch Details</h4></Col>                    
                    <div className="card-header-btns">
                        <IButton category="secondary" bType="ADDPAYMENT" accesskey="A" componentId={ComponentId.CMP_ADD_PAY} onClick={this.onAddPayment} ><IMessage msgkey={Key.ADD_PAY_LBL}  /></IButton>
                    </div>
 
            </div>
        );
    }
}
